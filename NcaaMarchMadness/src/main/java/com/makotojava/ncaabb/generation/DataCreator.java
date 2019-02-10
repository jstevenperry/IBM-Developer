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
package com.makotojava.ncaabb.generation;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.makotojava.ncaabb.dao.SeasonAnalyticsDao;
import com.makotojava.ncaabb.dao.SeasonDataDao;
import com.makotojava.ncaabb.dao.TournamentAnalyticsDao;
import com.makotojava.ncaabb.dao.TournamentResultDao;
import com.makotojava.ncaabb.model.NormalizedData;
import com.makotojava.ncaabb.model.SeasonAnalytics;
import com.makotojava.ncaabb.model.SeasonData;
import com.makotojava.ncaabb.model.TournamentAnalytics;
import com.makotojava.ncaabb.model.TournamentResult;
import com.makotojava.ncaabb.springconfig.ApplicationConfig;
import com.makotojava.ncaabb.util.NetworkProperties;
import com.makotojava.ncaabb.util.NetworkUtils;
import com.makotojava.ncaabb.util.StatsUtils;

/**
 * Creates data used to train Multi-layer Perceptron (MLP) networks.
 * 
 * @author J Steven Perry
 *
 */
public class DataCreator {

  private static final Logger log = Logger.getLogger(DataCreator.class);

  private SeasonDataDao seasonDataDao;
  private TournamentResultDao tournamentResultDao;
  private SeasonAnalyticsDao seasonAnalyticsDao;
  private TournamentAnalyticsDao tournamentAnalyticsDao;

  /**
   * The main (driver) method for this class.
   * 
   * @param args
   */
  public static void main(String[] args) {
    if (args.length < 1) {
      usage();
      System.exit(-1);
    }
    //
    // Let's kick the tires and light the fires
    DataCreator trainingDataCreator = new DataCreator(
        new AnnotationConfigApplicationContext(ApplicationConfig.class));
    //
    // First let's figure out what year(s) we are running.
    Integer[] yearsForTraining = trainingDataCreator.computeYearsToTrain(args);
    log.info("*********** CREATING TRAINING DATA **************");
    log.info(
        "Using data from the following years for training: " + ReflectionToStringBuilder.toString(yearsForTraining));
    //
    // Now create the data
    trainingDataCreator.go(yearsForTraining);

  }

  /**
   * Entry point for creating the training data for the specified years.
   * 
   * @param yearsForTraining
   *          The years for which training data is to be
   *          created.
   */
  public void go(Integer[] yearsForTraining) {
    //
    // Now we create the training data for those years
    for (Integer year : yearsForTraining) {
      //
      // Pull the current year's tournament results
      List<TournamentResult> tournamentResults = pullTournamentResults(year);
      //
      // Pull the current year's season analytics
      SeasonAnalytics seasonAnalytics = pullSeasonAnalytics(year);
      //
      // Pull the current year's tournament analytics
      TournamentAnalytics tournamentAnalytics = pullTournamentAnalytics(year);
      //
      // This is the data that gets written out.
      DataSet trainingData = new DataSet(NetworkProperties.getNumberOfInputs(), NetworkProperties.getNumberOfOutputs());
      //
      // Loop through each game played in the current year's tournament.
      // Pull the season data for both winner and loser.
      // Create a row of training data with the winner as the LHS of the data, loser as RHS.
      // Create another row that is just the opposite.
      // The idea is to correct for positional bias in the model. If the winner's data
      /// is always on the LHS, then whatever data is run in the final simulation/predication
      /// on the LHS will be the winner. However, for the true prediction, we don't know
      /// who the winner is, because the games haven't been played.
      for (TournamentResult tournamentResult : tournamentResults) {
        //
        // Each tournament game
        String winningTeamName = tournamentResult.getWinningTeamName();
        String losingTeamName = tournamentResult.getLosingTeamName();
        SeasonData seasonDataWinning = pullSeasonData(year, winningTeamName);
        SeasonData seasonDataLosing = pullSeasonData(year, losingTeamName);
        // Winner is LHS, Loser is RHS
        DataSetRow dataSetRow =
            processAsDataSetRowForTraining(seasonAnalytics, tournamentAnalytics, tournamentResult, seasonDataWinning,
                seasonDataLosing);
        trainingData.addRow(dataSetRow);
        // Loser is LHS, Winner is RHS
        dataSetRow =
            processAsDataSetRowForTraining(seasonAnalytics, tournamentAnalytics, tournamentResult, seasonDataLosing,
                seasonDataWinning);
        trainingData.addRow(dataSetRow);
      }
      if (log.isTraceEnabled()) {
        List<DataSetRow> rows = trainingData.getRows();
        log.trace("Dumping out training data:");
        for (DataSetRow row : rows) {
          log.trace("Row: " + ReflectionToStringBuilder.toString(row));
        }
      }
      log.info("*********** SAVING TRAINING DATA **************");
      String filename = NetworkUtils.computeTrainingDataFileName(year);
      trainingData.save(filename);
      int numberOfRows = trainingData.getRows().size();
      log.info("Saved " + numberOfRows + " rows of training data '" + filename + "'");
    }
  }

  /**
   * Constructor.
   * 
   * @param applicationContext
   *          The Spring ApplicationContext object that
   *          contains the environment.
   */
  public DataCreator(ApplicationContext applicationContext) {
    seasonDataDao = applicationContext.getBean(SeasonDataDao.class);
    tournamentResultDao = applicationContext.getBean(TournamentResultDao.class);
    seasonAnalyticsDao = applicationContext.getBean(SeasonAnalyticsDao.class);
    tournamentAnalyticsDao = applicationContext.getBean(TournamentAnalyticsDao.class);
  }

  /**
   * Pulls season data for the specified year and team.
   * 
   * @param year
   * @param teamName
   * @return
   */
  protected SeasonData pullSeasonData(Integer year, String teamName) {
    return seasonDataDao.fetchByYearAndTeamName(year, teamName);
  }

  /**
   * 
   * @param year
   * @return
   */
  protected List<TournamentResult> pullTournamentResults(Integer year) {
    return tournamentResultDao.fetchAllByYear(year);
  }

  protected SeasonAnalytics pullSeasonAnalytics(Integer year) {
    return seasonAnalyticsDao.fetchByYear(year);
  }

  protected TournamentAnalytics pullTournamentAnalytics(Integer year) {
    return tournamentAnalyticsDao.fetchByYear(year);
  }

  protected static boolean isTournamentGameWinner(SeasonData teamSeasonData, TournamentResult tournamentResult) {
    return teamSeasonData.getTeamName().equalsIgnoreCase(tournamentResult.getWinningTeamName());
  }

  /**
   * Creates a single row of normalized training data based on the specified
   * {@link SeasonAnalytics}, {@link TournamentResult} data, along with {@link SeasonData} for
   * "team 1" and "team 2". They are opaque in this manner because we don't know (or care) which
   * is the winner and loser of the specified game.
   * 
   * @param seasonAnalytics
   *          Season Analytics, used to compute the normalized data.
   * @param tournamentResult
   *          Represents the results of a single Tournament game.
   * @param team1SeasonData
   *          Team 1's {@link SeasonData}.
   * @param team2SeasonData
   *          Team 2's {@link SeasonData}.
   * @return DataSetRow - the row of normalized data that will be used for training the network.
   */
  public DataSetRow processAsDataSetRowForTraining(SeasonAnalytics seasonAnalytics,
      TournamentAnalytics tournamentAnalytics,
      TournamentResult tournamentResult,
      SeasonData team1SeasonData, SeasonData team2SeasonData) {
    DataSetRow ret;
    //
    // Normalize the data
    NormalizedData normalizedData = new NormalizedData(seasonAnalytics, team1SeasonData, team2SeasonData);
    //
    // Process the results into a double[] that Neuroph likes
    double[] inputAndOutput = normalizedData.asInputAndOutput();
    //
    // For training, we need to set the output values based on each team's score in the game.
    /// This only works for sports that do not allow ties (like Basketball, for example)
    setScoresInOutputData(inputAndOutput, seasonAnalytics, tournamentAnalytics, tournamentResult, team1SeasonData,
        team2SeasonData);
    if (log.isDebugEnabled()) {
      StringBuilder sb = new StringBuilder();
      sb.append("Training Data:");
      sb.append(Arrays.toString(inputAndOutput));
      log.debug(sb.toString());
    }
    //
    // Create the DataSetRow object the Neuroph framework expects
    ret = createDataSetRow(inputAndOutput);
    //
    // Return to base
    return ret;
  }

  /**
   * 
   * @param inputAndOutput
   * @param seasonAnalytics
   * @param tournamentResult
   * @param team1SeasonData
   * @param team2SeasonData
   */
  private static void setScoresInOutputData(double[] inputAndOutput, SeasonAnalytics seasonAnalytics,
      TournamentAnalytics tournamentAnalytics,
      TournamentResult tournamentResult, SeasonData team1SeasonData, SeasonData team2SeasonData) {
    //
    // The output scores are at the end of the array. The last index is for team2, and the next
    /// to last is for team 1.
    //
    /// The scores are the normalized scores from the historical contest between the two teams
    int team1ScoreIndex = inputAndOutput.length - 2;
    int team2ScoreIndex = inputAndOutput.length - 1;
    //
    // First, figure out which team is team1 in the tournament data
    String winningTeamName = tournamentResult.getWinningTeamName();
    BigDecimal winningScore = BigDecimal.valueOf(tournamentResult.getWinningScore().longValue());
    BigDecimal losingScore = BigDecimal.valueOf(tournamentResult.getLosingScore().longValue());
    //
    // Set the team scores based on who won the contest
    BigDecimal team1Score = (winningTeamName.equals(team1SeasonData.getTeamName())) ? winningScore : losingScore;
    BigDecimal team2Score = (winningTeamName.equals(team2SeasonData.getTeamName())) ? winningScore : losingScore;
    //
    // Compute Team1's normalized score
    BigDecimal team1NormalizedScore =
        StatsUtils.normalize(team1Score, BigDecimal.valueOf(tournamentAnalytics.getMinScore()).setScale(5),
            BigDecimal.valueOf(tournamentAnalytics.getMaxScore()).setScale(5));
    BigDecimal team2NormalizedScore =
        StatsUtils.normalize(team2Score, BigDecimal.valueOf(tournamentAnalytics.getMinScore()).setScale(5),
            BigDecimal.valueOf(tournamentAnalytics.getMaxScore()).setScale(5));
    //
    // Set the scores in their places in the array
    inputAndOutput[team1ScoreIndex] = team1NormalizedScore.doubleValue();
    inputAndOutput[team2ScoreIndex] = team2NormalizedScore.doubleValue();
  }

  /**
   * Creates a single row of normalized data used to run against a trained network (i.e., a simulation,
   * or prediction).
   * 
   * @param seasonAnalytics
   *          Season Analytics, used to compute the normalized data.
   * @param homeTeamSeasonData
   *          The Home Team's {@link SeasonData}.
   * @param awayTeamSeasonData
   *          The Away Team's {@link SeasonData}.
   * @return DataSetRow - the row of normalized data that will be used for training the network.
   */
  public static DataSetRow processAsDataSetRowForSimulation(SeasonAnalytics seasonAnalytics, SeasonData team1SeasonData,
      SeasonData team2SeasonData) {
    DataSetRow ret;
    //
    // Normalize the data
    NormalizedData normalizedData = new NormalizedData(seasonAnalytics, team1SeasonData, team2SeasonData);
    //
    // Convert the normalized data to a double[] containing both input and output data
    /// so it can be run through the network
    double[] inputAndOutput = normalizedData.asInputAndOutput();
    //
    // Create the DataSetRow object Neuroph is expecting
    ret = createDataSetRow(inputAndOutput);
    return ret;
  }

  /**
   * Transform the normalized data into the double[] required by Neuroph.
   * 
   * @param team1SeasonData
   *          Team 1's {@link SeasonData}.
   * @param team2SeasonData
   *          Team 2's {@link SeasonData}.
   * @return
   */
  /**
   * Create a Neuroph DataSetRow from the specified double array.
   * 
   * @param inputAndOutput
   *          The double[] of data containing both input and output.
   * 
   * @return
   */
  private static DataSetRow createDataSetRow(double[] inputAndOutput) {
    DataSetRow ret = new DataSetRow();
    //
    // Validate/Sanity check
    int expectedInputAndOutputLength = NetworkProperties.getNumberOfInputs().intValue()
        + NetworkProperties.getNumberOfOutputs().intValue();
    //
    // Bail out if something doesn't look right
    if (inputAndOutput.length != expectedInputAndOutputLength) {
      throw new RuntimeException(
          "The expected size of " + expectedInputAndOutputLength + " does not match the actual size of "
              + inputAndOutput.length);
    }
    //
    // Split up the array into input and output
    // Input
    double[] input = new double[NetworkProperties.getNumberOfInputs()];
    System.arraycopy(inputAndOutput, 0, input, 0, NetworkProperties.getNumberOfInputs());
    //
    // Output
    double[] output = new double[NetworkProperties.getNumberOfOutputs()];
    //
    // The output is at the end of the <code>inputAndOutput</code> parameter
    output[0] = inputAndOutput[NetworkProperties.getNumberOfInputs()];
    output[1] = inputAndOutput[NetworkProperties.getNumberOfInputs() + 1];
    //
    // Now set the input and output
    ret.setInput(input);
    //
    // Set the "answer"
    ret.setDesiredOutput(output);
    return ret;
  }

  /**
   * Displayed if the program was invoked incorrectly.
   */
  protected static void usage() {
    System.out.println("Usage: ");
    System.out.println("\t" + DataCreator.class.getSimpleName()
        + " TRAINING_YEAR_1 TRAINING_YEAR_2 ... TRAINING_YEAR_N");
    System.out.println("\t Where:");
    System.out.println("\t TRAINING_YEAR_x is the year for which training data is to be generated");
    System.out.println("\t Multiple years are separated by spaces.");
    System.out.println("Example:");
    System.out.println("\t" + DataCreator.class.getSimpleName()
        + " 2011 2012 2014");
    System.out.println("Generates data for 2011, 2012, and 2014");
  }

  /**
   * Parses the String array of years into an Integer array of years
   * that is used to generate data. This method validates the years to
   * ensure they are valid.
   * 
   * @param args
   *          The input String array. Each element is a year in character numeric format.
   * @return Integer[] - the Integer array.
   */
  protected Integer[] computeYearsToTrain(String[] args) {
    Integer[] ret = new Integer[args.length];
    for (int aa = 0; aa < args.length; aa++) {
      Integer year = Integer.valueOf(args[aa]);
      ret[aa] = year;
    }
    // Validate
    for (Integer year : ret) {
      NetworkUtils.validateYear(year);
    }
    return ret;
  }

}
