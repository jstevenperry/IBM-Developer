/*
 * Copyright 2017 Makoto Consulting Group, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.makotojava.ncaabb.simulation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.makotojava.ncaabb.dao.SeasonAnalyticsDao;
import com.makotojava.ncaabb.dao.SeasonDataDao;
import com.makotojava.ncaabb.generation.DataCreator;
import com.makotojava.ncaabb.model.SeasonAnalytics;
import com.makotojava.ncaabb.model.SeasonData;
import com.makotojava.ncaabb.springconfig.ApplicationConfig;
import com.makotojava.ncaabb.util.NetworkProperties;
import com.makotojava.ncaabb.util.NetworkUtils;
import com.opencsv.CSVWriter;

/**
 * Runs one or more trained networks using normalized input data to predict
 * the results of a set of contests between a team and every other team in
 * the tournament. The result is a matrix.
 * 
 * The idea is that you can run this predictor before the tournament brackets
 * or matchups have been determined, so long as you know who is going to be in
 * the tournament, and have loaded their regular season data into the DB.
 * 
 * @author J Steven Perry
 *
 */
public class TournamentMatrixPredictor implements NetworkRunner<MultiLayerPerceptron> {

  private static final Logger log = Logger.getLogger(TournamentMatrixPredictor.class);

  /**
   * Constructor.
   * 
   * @param applicationContext
   *          The Spring ApplicationContext object.
   */
  public TournamentMatrixPredictor(ApplicationContext applicationContext) {
    seasonDataDao = applicationContext.getBean(SeasonDataDao.class);
    seasonAnalyticsDao = applicationContext.getBean(SeasonAnalyticsDao.class);
    // tournamentStatsDao = applicationContext.getBean(TournamentStatsDao.class);
  }

  /**
   * SeasonData Data Access Object
   */
  private SeasonDataDao seasonDataDao;

  /**
   * The SeasonAnalytics DAO - provides a view of the regular season data that
   * allows the data to be normalized.
   */
  private SeasonAnalyticsDao seasonAnalyticsDao;

  /**
   * Returns a SeasonData object for the specified year and teamName.
   * 
   * @param year
   * @param teamName
   * @return
   */
  public SeasonData pullSeasonData(Integer year, String teamName) {
    return seasonDataDao.fetchByYearAndTeamName(year, teamName);
  }

  /**
   * Retrieves SeasonAnalytics object for the specified year.
   * 
   * @param year
   * @return
   */
  public SeasonAnalytics pullSeasonAnalytics(Integer year) {
    return seasonAnalyticsDao.fetchByYear(year);
  }

  /**
   * Driver for the program.
   * 
   * @param args
   */
  public static void main(String[] args) {
    if (args.length < 1) {
      usage();
      System.exit(-1);
    }
    //
    // Get the tournament year
    Integer year = Integer.valueOf(args[0]);

    //
    // Validate the year
    NetworkUtils.validateYear(year);
    //
    // Instantiate the class and handoff
    TournamentMatrixPredictor simulator = new TournamentMatrixPredictor(
        new AnnotationConfigApplicationContext(ApplicationConfig.class));
    //
    // Let's go.
    // simulator.go(networkArrayDirectory, teamsFilename, matrixFilename, year);
    simulator.go(year);
  }

  /**
   * Usage message. Pretty self-explanatory.
   */
  protected static void usage() {
    System.out.println("Usage: ");
    System.out.println("\t" + TournamentMatrixPredictor.class.getSimpleName()
        + " YEAR");
    System.out.println("\t Where:");
    // System.out.println("\t NETWORK_DIRECTORY is the directory containing the trained networks to run as an array");
    // System.out.println("\t MATRIX_FILENAME is the output file containing the tournament matrix.");
    System.out.println("\t YEAR is the year in which the tournament occurs.");
  }

  /**
   * Loads all networks to be used in making predictions. The networks should
   * be located in a directory structured dictated by the NetworkProperties settings.
   */
  @Override
  public List<MultiLayerPerceptron> loadNetworks() {
    //
    // Network file location is, for example, /base_directory/network_directory.
    String networkArrayDirectory = NetworkProperties.getBaseDirectory() + File.separator
        + NetworkProperties.getNetworkDirectoryName();
    //
    // Using that location, load all the networks and return the List of them.
    return NetworkUtils.loadNetworks(networkArrayDirectory);
  }

  /**
   * Runs the specified network using the specified normalized input data.
   */
  @Override
  public double[] runNetwork(MultiLayerPerceptron network, double[] input) {
    return NetworkUtils.runNetwork(network, input);
  }

  /**
   * The "do it" method. Drives the entire matrix production.
   * Pretty cool, man.
   * 
   * @param tournamentYear
   */
  public void go(Integer tournamentYear) {
    //
    // Load the networks
    List<MultiLayerPerceptron> networks = loadNetworks();
    Set<String> teamNames = fetchTournamentTeams(tournamentYear);
    //
    // Now generate the matrix. Every team in the file against
    /// every other team in the file. This will let us make up
    /// different brackets without having to re-run the simulator.
    Map<String, List<GameSimulationResult<MultiLayerPerceptron>>> matrix =
        computeMatrix(tournamentYear, networks, teamNames);
    //
    // Write the matrix to CSV file (TODO: use POI?)
    writeMatrixFile(matrix);
  }

  /**
   * We need some way of knowing who is in the big dance, and this seems
   * as good as any.
   * 
   * @param teamsFile
   * @param year
   * @return
   */
  public Set<String> fetchTournamentTeams(Integer year) {
    Set<String> ret = new HashSet<>();
    List<SeasonData> seasonData = seasonDataDao.fetchAllByYear(year);
    for (SeasonData seasonDatum : seasonData) {
      ret.add(seasonDatum.getTeamName());
    }
    return ret;
  }

  /**
   * Uses the specified List of networks, and team names to compute a matrix of
   * GameSimulationResults, where each team plays every other team (except itself
   * of course).
   * 
   * @param year
   *          The tournament year
   * 
   * @param networks
   *          The trained networks to be used
   * 
   * @param teamNames
   *          The teams participating in the tournament
   * 
   * @return Map<String, List<GameSimulationResult<MultiLayerPerceptron>>> - a Map of
   *         List of GameSimulationResults. The Map is keyed by team name. The value is a List
   *         of GameSimulationResult objects, one for each simulated game between that team
   *         and every other team (except itself of course).
   */
  private Map<String, List<GameSimulationResult<MultiLayerPerceptron>>> computeMatrix(Integer year,
      List<MultiLayerPerceptron> networks, Set<String> teamNames) {
    Map<String, List<GameSimulationResult<MultiLayerPerceptron>>> ret = new TreeMap<>();
    //
    // We need to iterate over the same list in two different loops, and while
    /// we are not modifying the list in either loop, it's probably better to work
    /// on different copies, just to prevent bugs down the road
    List<String> opponentTeamNames = new ArrayList<>();
    opponentTeamNames.addAll(teamNames);
    Collections.sort(opponentTeamNames);
    SeasonAnalytics seasonAnalytics = pullSeasonAnalytics(year);
    //
    // Loop through the list of team names
    for (String currentTeamName : teamNames) {
      //
      // Create the List of GameSimulationResult objects
      List<GameSimulationResult<MultiLayerPerceptron>> gameSimulationResults = new ArrayList<>();
      SeasonData currentTeamSeasonData = fetchSeasonDataFromCache(year, currentTeamName);
      //
      // Now simulate the current team against every other team
      for (String opponentTeamName : opponentTeamNames) {
        log.debug("Current Team: " + currentTeamName + " versus " + opponentTeamName + "...");
        //
        // No need to match a team up against itself
        // if (opponentTeamName.equals(currentTeamName))
        // continue;
        //
        // Create the GameSimulationResult for this matchup
        GameSimulationResult<MultiLayerPerceptron> gameSimulationResult = new GameSimulationResult<>(currentTeamName,
            opponentTeamName);
        //
        // Loop through the list of networks
        SeasonData opponentSeasonData = fetchSeasonDataFromCache(year, opponentTeamName);
        int simulationNumber = 0;
        for (MultiLayerPerceptron network : networks) {
          log.trace("Network simulation # " + simulationNumber++ + "...");
          runNetworkSimulationAndComputeResult(network, seasonAnalytics, currentTeamSeasonData, gameSimulationResult,
              opponentSeasonData);
        }
        gameSimulationResults.add(gameSimulationResult);
      }
      //
      // Sort the list in alphabetical order or we will never be able to find anything
      gameSimulationResults.sort((simulationResult1, simulationResult2) -> simulationResult1.getOpponentName()
          .compareTo(simulationResult2.getOpponentName()));
      //
      // Add the List to the Map
      ret.put(currentTeamName, gameSimulationResults);
    }
    return ret;
  }

  /**
   * Run a single network simulation (as home and away to eliminate positional bias)
   * and compute the results.
   * 
   * @param network
   * @param seasonAnalytics
   * @param currentTeamSeasonData
   * @param gameSimulationResult
   * @param opponentTeamSeasonData
   */
  private void runNetworkSimulationAndComputeResult(MultiLayerPerceptron network, SeasonAnalytics seasonAnalytics,
      SeasonData currentTeamSeasonData, GameSimulationResult<MultiLayerPerceptron> gameSimulationResult,
      SeasonData opponentTeamSeasonData) {
    //
    // Simulate network
    gameSimulationResult.getNetworks().add(network);
    //
    // Home - in this simulation, currentTeam is "home", so their output is index 0,
    /// and the opponent is "away" so their output is index 1
    DataSetRow dataHome = DataCreator.processAsDataSetRowForSimulation(seasonAnalytics, currentTeamSeasonData,
        opponentTeamSeasonData);
    double[] homeResults = runNetwork(network, dataHome.getInput());
    gameSimulationResult.getTeamHomeResults().add(homeResults[0]);
    gameSimulationResult.getOpponentAwayResults().add(homeResults[1]);
    boolean homeWin = homeResults[0] > homeResults[1];
    //
    // Away - in this simulation, currentTeam is "away", so their output is index 1,
    /// and the opponent is "home" so their output is index 0
    DataSetRow dataAway = DataCreator.processAsDataSetRowForSimulation(seasonAnalytics, opponentTeamSeasonData,
        currentTeamSeasonData);
    double[] awayResults = runNetwork(network, dataAway.getInput());
    gameSimulationResult.getTeamAwayResults().add(awayResults[1]);
    gameSimulationResult.getOpponentHomeResults().add(awayResults[0]);
    boolean awayWin = awayResults[1] > awayResults[0];
    //
    // Compute results
    if (homeWin && awayWin) {
      gameSimulationResult.getNetworkPredictions().add(currentTeamSeasonData.getTeamName());
      gameSimulationResult.incrementNumberOfWins();
    } else if (!homeWin && !awayWin) {
      gameSimulationResult.getNetworkPredictions().add(opponentTeamSeasonData.getTeamName());
      gameSimulationResult.incrementNumberOfLosses();
    } else {
      // Push indicates there is some positional bias in this network.
      /// We do not want to count this result.
      gameSimulationResult.getNetworkPredictions().add(GameSimulationResult.PUSH);
      gameSimulationResult.incrementNumberOfPushes();
    }
  }

  /**
   * Cache the SeasonData. There is no reason to retrieve it over and over from
   * the Database. The map is keyed by team name.
   */
  private Map<String, SeasonData> seasonDataCache;

  /**
   * Retrieves the SeasonData object for the specified year and teamName from
   * the cache, if it is there, or from the DB if not (then stores it in the cache).
   * 
   * @param year
   * @param teamName
   * @return
   */
  private SeasonData fetchSeasonDataFromCache(Integer year, String teamName) {
    SeasonData ret = null;
    //
    // Create the cache itself lazily
    if (seasonDataCache == null) {
      seasonDataCache = new HashMap<>();
    }
    //
    // See if the SeasonData object is in the cache
    ret = seasonDataCache.get(teamName);
    if (ret == null) {
      // No it is not, retrieve it from the DB
      ret = pullSeasonData(year, teamName);
      // And store it in the cache
      seasonDataCache.put(teamName, ret);
    }
    return ret;
  }

  /**
   * Writes the specified matrix of GameSimulationResult to a CSV file.
   * The file contains the results of a simulated matchup of each team against every other
   * team in the tournament, in the form of a matrix (implemented as a Map of List of
   * GameResultSimulation, may is keyed by team name).
   * 
   * Of course, tournaments aren't played this way, but especially for bracket-style
   * tournaments (like the NCAA Men's Basketball Tournament, a.k.a., March Madness),having
   * the matchups in this format makes it easier when picking your brackets. You just look
   * at the team CSV file for the home team, locate their opponent, and see what they
   * network array predicted, and fill in that game. Then move to the next game, etc.
   * 
   * @param matrix
   *          A Map (keyed by team name) of List of GameSimulationResult objects.
   */
  private void writeMatrixFile(Map<String, List<GameSimulationResult<MultiLayerPerceptron>>> matrix) {
    //
    //
    String[] EMPTY_LINE = { "" };
    for (String teamName : matrix.keySet()) {
      String teamPredictionFilename = NetworkUtils.fetchSimulationDirectoryAndCreateIfNecessary() +
          File.separator + teamName + ".csv";
      //
      // Write out the file for the current team (teamName)
      try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(teamPredictionFilename))) {
        CSVWriter csvWriter = new CSVWriter(bufferedWriter);
        csvWriter.writeNext(EMPTY_LINE);
        if (log.isDebugEnabled()) {
          log.debug("**** TEAM -> " + teamName + ", file:  " + teamPredictionFilename + "***");
        }
        String[] teamSeparatorLine = { "", teamName.toUpperCase() + " VS", "NETWORK PREDICTED:", "NETWORK PREDICTED:",
            "NETWORK PREDICTED:" };
        csvWriter.writeNext(teamSeparatorLine);
        List<GameSimulationResult<MultiLayerPerceptron>> gameSimulationResults = matrix.get(teamName);
        String[] heading = generateHeading(gameSimulationResults);
        csvWriter.writeNext(heading);
        int index = 0;
        //
        // Write out the game simulation data for teamName against every other team
        /// in the tournament.
        for (GameSimulationResult<MultiLayerPerceptron> gsr : gameSimulationResults) {
          //
          // Output contains:
          // Team, Opponent name, percent wins, percent losses, percent push, plus
          // Network predictions for each participant
          int BASE_INDEX = 5;
          String[] output = new String[BASE_INDEX + gsr.getNumberOfNetworks()];
          if (index++ == 0) {
            output[0] = gsr.getTeamName();
          }
          output[1] = gsr.getOpponentName();
          output[2] = gsr.getPercentWins().setScale(2, RoundingMode.HALF_UP).toPlainString() + "%";
          output[3] = gsr.getPercentLosses().setScale(2, RoundingMode.HALF_UP).toPlainString() + "%";
          output[4] = gsr.getPercentPushes().setScale(2, RoundingMode.HALF_UP).toPlainString() + "%";
          StringBuilder sb = new StringBuilder();
          for (int aa = 0; aa < gsr.getNumberOfNetworks(); aa++) {
            if (aa > 0)
              sb.append(" | ");
            sb.append(gsr.getNetworkPredictions().get(aa));
            output[aa + BASE_INDEX] = gsr.getNetworkPredictions().get(aa);
            sb.append(" (H->");
            sb.append(BigDecimal.valueOf(gsr.getTeamHomeResults().get(aa)).setScale(3, RoundingMode.HALF_UP));
            sb.append(":");
            sb.append(BigDecimal.valueOf(gsr.getOpponentAwayResults().get(aa)).setScale(3, RoundingMode.HALF_UP));
            sb.append(" | A->");
            sb.append(BigDecimal.valueOf(gsr.getOpponentHomeResults().get(aa)).setScale(3, RoundingMode.HALF_UP));
            sb.append(":");
            sb.append(BigDecimal.valueOf(gsr.getTeamAwayResults().get(aa)).setScale(3, RoundingMode.HALF_UP));
            sb.append(")");
          }
          if (log.isDebugEnabled()) {
            log.debug("Opponent: " + gsr.getOpponentName() +
                " | Win % " + gsr.getPercentWins() +
                " | Loss %" + gsr.getPercentLosses() +
                " | Push % " + gsr.getPercentPushes() +
                ((log.isDebugEnabled()) ? " | Network Results -> " + sb.toString() : ""));
          }
          csvWriter.writeNext(output);
        }
        csvWriter.close();
      } catch (IOException e) {

      }
    }

  }

  /**
   * Generate a heading for the CSV file. Makes it easier to read when loaded into Open Office
   * or whatever Office product you use.
   * 
   * The first few headings are where the relevant info is including team (and even an entry
   * of the team against itself, which you will note is always PUSH), opponent, and average win/loss/push
   * percentages.
   * 
   * The heading will contain the result of each network used in the array so you can see
   * the individual network picks (if you care).
   * 
   * @param results
   *          The matrix of game simulations.
   * 
   * @return String[] - the headings, one element per header
   */
  private String[] generateHeading(List<GameSimulationResult<MultiLayerPerceptron>> results) {
    GameSimulationResult<MultiLayerPerceptron> result = results.get(0);
    String[] ret = new String[5 + result.getNumberOfNetworks()];
    //
    ret[0] = "TEAM";
    ret[1] = "OPPONENT";
    ret[2] = "WIN %";
    ret[3] = "LOSS %";
    ret[4] = "PUSH %";
    List<MultiLayerPerceptron> networks = result.getNetworks();
    int aa = 5;
    for (MultiLayerPerceptron network : networks) {
      ret[aa++] = NetworkUtils.getNetworkStructure(network);
    }
    return ret;
  }

}
