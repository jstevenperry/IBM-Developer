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

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.input.WeightedSum;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.NeuronProperties;
import org.neuroph.util.TransferFunctionType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.makotojava.ncaabb.dao.SeasonAnalyticsDao;
import com.makotojava.ncaabb.dao.SeasonDataDao;
import com.makotojava.ncaabb.dao.TournamentResultDao;
import com.makotojava.ncaabb.model.SeasonAnalytics;
import com.makotojava.ncaabb.model.SeasonData;
import com.makotojava.ncaabb.model.TournamentResult;
import com.makotojava.ncaabb.springconfig.ApplicationConfig;
import com.makotojava.ncaabb.util.NetworkProperties;
import com.makotojava.ncaabb.util.NetworkUtils;

/**
 * Trains Multilayer Perceptron Networks. Saves the ones that perform above the threshold.
 * 
 * @author sperry
 *
 */
public class MlpNetworkTrainer implements LearningEventListener {

  private static final Logger log = Logger.getLogger(MlpNetworkTrainer.class);

  /**
   * Use the Sigmoid firing function
   */
  public static final TransferFunctionType NEURON_PROPERTY_TRANSFER_FUNCTION = TransferFunctionType.SIGMOID;

  private SeasonDataDao seasonDataDao;
  private TournamentResultDao tournamentResultDao;
  private SeasonAnalyticsDao seasonAnalyticsDao;

  /**
   * The network metrics cache
   */
  private Map<MultiLayerPerceptron, NetworkMetrics> networkMetricsCache =
      new HashMap<MultiLayerPerceptron, NetworkMetrics>();

  /**
   * The network cache
   */
  private Map<List<Integer>, MultiLayerPerceptron> networkCache = new HashMap<>();

  public SeasonData pullSeasonData(Integer year, String teamName) {
    return seasonDataDao.fetchByYearAndTeamName(year, teamName);
  }

  public List<TournamentResult> pullTournamentResults(Integer year) {
    return tournamentResultDao.fetchAllByYear(year);
  }

  public SeasonAnalytics pullSeasonAnalytics(Integer year) {
    return seasonAnalyticsDao.fetchByYear(year);
  }

  /**
   * Constructor. Uses Spring's ApplicationContext to feed this class
   * with the DAOs it needs to function.
   * 
   * @param applicationContext
   */
  public MlpNetworkTrainer(ApplicationContext applicationContext) {
    seasonDataDao = applicationContext.getBean(SeasonDataDao.class);
    tournamentResultDao = applicationContext.getBean(TournamentResultDao.class);
    seasonAnalyticsDao = applicationContext.getBean(SeasonAnalyticsDao.class);
  }

  /**
   * The main entry point for the application.
   * 
   * @param args
   */
  public static void main(String[] args) {
    if (args.length < 2) {
      usage();
      System.exit(-1);
    }
    //
    // Create the network generator
    MlpNetworkTrainer generator = new MlpNetworkTrainer(
        new AnnotationConfigApplicationContext(ApplicationConfig.class));
    generator.go(args);
  }

  /**
   * Entry point for kicking off a training/validation run.
   * 
   * @param args
   */
  public void go(String[] args) {
    Integer[] yearsForTrainingData = computeYearsToTrain(args);
    Integer[] yearsToSimulate = computeYearsToSimulate(args);
    //
    // Load the training data. It's the same for all networks, for all iterations,
    /// so just do it once.
    DataSet trainingData = loadTrainingData(yearsForTrainingData);
    //
    // Setup the networks we will try out
    List<List<Integer>> networks = setupNetworksToTry();
    log.info("**** NETWORKS TO BE USED (LAYER STRUCTURE) ****");
    for (List<Integer> network : networks) {
      String layerStructure = NetworkUtils.generateLayerStructureString(network);
      log.info(layerStructure);
    }
    //
    // Start at iteration #1
    int iterationNumber = 1;
    //
    // The MLP Network
    MultiLayerPerceptron network;
    //
    // Iterate until max iterations is reached.
    while (iterationNumber <= NetworkProperties.getMaxNetworkIterations()) {
      //
      for (List<Integer> neuronLayerDescriptor : networks) {
        //
        // Loop through the current network a bunch of times
        log.info("Training the network with DataSet from years " + Arrays.toString(yearsForTrainingData) + " and with "
            + trainingData.size() + " rows...");
        log.info("*********** CREATING NEURAL NETWORK **************");
        //
        // Now create the network itself
        network = createNetwork(neuronLayerDescriptor, yearsToSimulate);
        //
        // Now train the neural network
        log.info("*********** TRAINING NEURAL NETWORK **************");
        trainNetwork(trainingData, network);
        //
        // Training complete. Check to see if we're above the max error toleration. If so, abort.
        if (network.getLearningRule().getTotalNetworkError() > NetworkProperties.getMaxNetworkError()) {
          //
          // Fetch the metrics
          NetworkMetrics metrics = networkMetricsCache.get(network);
          log.error("***** NETWORK ERROR (" + network.getLearningRule().getTotalNetworkError()
              + ") HIGHER THAN THRESHOLD MAX ("
              + BigDecimal.valueOf(NetworkProperties.getMaxNetworkError() * 100.0).setScale(2, RoundingMode.HALF_UP)
                  .toString()
              + "%). ABORTING! *****");
          metrics.setNumberOfAbortedRuns(metrics.getNumberOfAbortedRuns() + 1);
        } else {
          // We're good to go. Validate the trained network.
          log.info("*********** VALIDATING NEURAL NETWORK **************");
          validateNetwork(network);
        }
        //
        // Log iterationStats
        logIterationStatsForNetwork(network);
      }
      iterationNumber++;
    }
    logFinalGeneratorStats();
  }

  /**
   * Takes a String array of character numeric values (years for which to train the network)
   * and return an array of their Integer counterparts for all but the last element.
   * Just in case you're wondering why this isn't a generic String[] -> Integer[]
   * conversion.
   * <p>
   * <em>If the command line argument format changes for this program this method
   * will most likely break!</em>
   * 
   * @param args
   *          The String array of years for which the network is to be trained.
   *          Assumes the last argument in the array is not used.
   * 
   * @return Integer[] - the years to train as an array of Integer.
   */
  protected static Integer[] computeYearsToTrain(String[] args) {
    Integer[] ret = new Integer[args.length - 1];
    for (int aa = 0; aa < args.length - 1; aa++) {
      Integer year = Integer.valueOf(args[aa]);
      // Validate year
      NetworkUtils.validateYear(year);
      ret[aa] = year;
    }
    return ret;
  }

  /**
   * Takes the last argument in a String[], which should be a comma-delimited
   * list of years to simulate, tokenizes it and computes the years it contains.
   * <p>
   * <em>If the command line argument format changes for this program this method
   * will most likely break!</em>
   * 
   * @param args
   *          The input String array, the last element of which is a comma-delimited
   *          list of years to simulate when validating the network.
   *          Assumes only the last argument in the array is used.
   * @return
   */
  protected static Integer[] computeYearsToSimulate(String[] args) {
    Integer[] ret = null;
    //
    // Only care about the last argument.
    String yearsToSimulate = args[args.length - 1];
    StringTokenizer strtok = new StringTokenizer(yearsToSimulate, ",");
    List<Integer> yts = new ArrayList<>();
    while (strtok.hasMoreTokens()) {
      Integer year = Integer.valueOf(strtok.nextToken());
      // Validate year
      NetworkUtils.validateYear(year);
      yts.add(year);
    }
    ret = yts.toArray(new Integer[yts.size()]);
    return ret;
  }

  /**
   * Creates and returns the MultiLayerPerceptron network having an inner layer
   * structure as specified by <code>neuronLayerDescriptor</code> and properties
   * as specified by <code>neuronProperties</code>.
   * 
   * @param neuronLayerDescriptor
   *          The complete layer structure (includes both the
   *          input layer and output layers)
   * 
   * @param neuronProperties
   *          {@link NeuronProperties}
   * @return
   */
  private MultiLayerPerceptron createNetwork(List<Integer> neuronLayerDescriptor,
      Integer[] yearsToSimulate) {
    //
    // First create the NeuronProperties
    NeuronProperties neuronProperties = new NeuronProperties();
    neuronProperties.setProperty("transferFunction", NEURON_PROPERTY_TRANSFER_FUNCTION);
    neuronProperties.setProperty("inputFunction", WeightedSum.class);
    neuronProperties.setProperty("useBias", NetworkProperties.getUseBiasNeurons());

    MultiLayerPerceptron network = networkCache.get(neuronLayerDescriptor);
    if (network == null) {
      log.info("*********** CREATING NETWORK **************");
      network = new MultiLayerPerceptron(neuronLayerDescriptor, neuronProperties);
      networkCache.put(neuronLayerDescriptor, network);
    }
    MomentumBackpropagation learningRule = (MomentumBackpropagation) network.getLearningRule();
    //
    // Only use this callback if not in batch mode because Neuroph does not
    /// properly set the total network error, and we get NumberFormatExceptions
    /// when executing that code.
    if (NetworkProperties.getLearningRuleIsBatchMode() == false) {
      learningRule.addListener(this);
    }
    learningRule.setMaxError(NetworkProperties.getMaxNetworkError());
    learningRule.setMomentum(randomizeMomentum());
    learningRule.setLearningRate(NetworkProperties.getLearningRuleLearningRate());
    learningRule.setBatchMode(NetworkProperties.getLearningRuleIsBatchMode());
    randomizeNetworkWeights(network);
    learningRule.setMaxIterations(NetworkProperties.getMaxLearningIterations());

    //
    // Create the network metrics (used all over the place)
    NetworkMetrics metrics = createNetworkMetrics(network, yearsToSimulate, neuronLayerDescriptor, neuronProperties);
    log.info("* Layer structure of this network  --> " + metrics.getLayerStructure());
    log.info("* Iteration number for this network --> " + metrics.getNumberOfIterationsSoFar());

    return network;
  }

  /**
   * Create the {@link NetworkMetrics} object. It is used to keep track of information about
   * the networks produced by this run.
   * 
   * @param yearsToSimulate
   *          The years for which simulations are to be run against the trained
   *          networks produced by the program to validate them.
   * 
   * @param neuronLayerDescriptor
   *          The network descriptor, each Integer in the array represents
   *          the number of neurons in that layer. The 0th element represents the input layer, the last element
   *          represents the output layer, with hidden layers between them.
   * 
   * @param neuronProperties
   *          The {@link NeuronProperties} Neuroph metadata object. Used when creating
   *          a new network as a convenient way to set a bunch of properties all at once.
   * 
   * @return {@link NetworkMetrics} - the metrics object.
   */
  private NetworkMetrics createNetworkMetrics(MultiLayerPerceptron network, Integer[] yearsToSimulate,
      List<Integer> neuronLayerDescriptor, NeuronProperties neuronProperties) {
    String neuronLayerDescriptorString = NetworkUtils.generateLayerStructureString(neuronLayerDescriptor);
    //
    // Create metrics
    log.info("*********** FETCHING NETWORK METRICS **************");
    NetworkMetrics metrics = networkMetricsCache.get(network);
    if (metrics == null) {
      log.info("*********** CREATED NEW NETWORK METRICS FOR THIS NETWORK (" + neuronLayerDescriptorString
          + ") **************");
      metrics = new NetworkMetrics();
      networkMetricsCache.put(network, metrics);
    }
    metrics.setNeuronProperties(neuronProperties);
    metrics.setIterationStartTime(System.currentTimeMillis());
    metrics.setLearnStartTime(System.currentTimeMillis());
    metrics.setLayerStructure(neuronLayerDescriptorString);
    metrics.setNumberOfIterationsSoFar(metrics.getNumberOfIterationsSoFar() + 1);
    metrics.setSimulationYears(yearsToSimulate);
    return metrics;
  }

  /**
   * Train the specified MLP network using the specified training data, store metrics in the
   * specified metrics object.
   * 
   * @param trainingData
   *          The data used to train the network.
   * @param network
   *          The MLP network to be trained.
   * @param metrics
   *          The {@link NetworkMetrics} object where metrics info is stored.
   */
  private void trainNetwork(DataSet trainingData, MultiLayerPerceptron network) {
    //
    // Shuffle the training data. Adds an element of randomness to the data.
    trainingData.shuffle();
    //
    // Now learn, you!
    network.learn(trainingData);
    //
    // Learning complete. Set metrics.
    NetworkMetrics metrics = networkMetricsCache.get(network);
    metrics.setIterationLearnTime(System.currentTimeMillis() - metrics.getLearnStartTime());
    metrics.setTotalLearnTime(metrics.getTotalLearnTime() + metrics.getIterationLearnTime());
    metrics.setNumberOfAsymmetricWinsThisIteration(0);
    metrics.setNumberOfSymmetricWinsThisIteration(0);
    metrics.setNumberOfGamesThisIteration(0);
  }

  /**
   * Loads training data for the specified years. The data is assumed to be at a location
   * specified by the {@link NetworkProperties} object (or <code>network.properties</code> file
   * if the default location is to be overridden).
   * 
   * @param yearsForTrainingData
   *          The years for which training data is to be loaded. Each element
   *          should contain a separate year.
   * 
   * @return Neuroph {@link DataSet} object containing all of the training data to be used.
   */
  protected DataSet loadTrainingData(Integer[] yearsForTrainingData) {
    DataSet ret = new DataSet(NetworkProperties.getNumberOfInputs(), NetworkProperties.getNumberOfOutputs());
    List<DataSet> dataSets = new ArrayList<>();
    //
    // Build out the expected file name based on the constants
    /// and the years in the parameter
    for (Integer year : yearsForTrainingData) {
      String filename = NetworkUtils.computeTrainingDataFileName(year);
      // NetworkProperties.getBaseDirectory() + File.separator + trainingDataDirectory
      // + File.separator +
      // NetworkProperties.getTrainingDataFileBase() + "-" + year + NetworkProperties.getTrainingDataFileExtension();
      log.info("Loading training data from file: '" + filename + "'...");
      DataSet loadedDataSet = DataSet.load(filename);
      log.info("Training data loaded: " + loadedDataSet.getRows().size() + " rows.");
      dataSets.add(loadedDataSet);
    }
    //
    // Now combine all of the data sets into one
    for (DataSet dataSet : dataSets) {
      for (DataSetRow row : dataSet.getRows()) {
        ret.addRow(row);
      }
    }
    log.info("Combined " + dataSets.size() + " data sets, consisting of a total of " + ret.size() + " rows.");
    //
    log.info("Shuffling training data...");
    ret.shuffle();
    return ret;
  }

  /**
   * Reads the network definitions in Networks.getNetworks() to get the 2D int array
   * that specifies <strong>only</strong> their hidden layers.
   * 
   * The complete layer descriptor is built from the NumberOfInputs and NumberOfOutputs properties.
   * 
   * A List of network descriptors is returned (a network descriptor in this context is a List of
   * Integer, where each Integer represents the number of neurons in each layer, with the 0th element
   * representing the input layer).
   * 
   * @return List of List<Integer>, or List of network descriptors. There is one List<Integer> for
   *         each network that is to be trained and validated, described by that network descriptor.
   */
  protected List<List<Integer>> setupNetworksToTry() {
    List<List<Integer>> ret = new ArrayList<>();
    int[][] networksToTry = Networks.getNetworks();
    //
    // For each network to be tried ("tried" means created, trained, and validated)...
    for (int aa = 0; aa < networksToTry.length; aa++) {
      // ret.add(randomizeNetwork());
      int[] hiddenNeurons = networksToTry[aa];
      //
      // Now setup the complete network descriptor for this network
      List<Integer> networkToTry = setupNeuronLayers(NetworkProperties.getNumberOfInputs(),
          NetworkProperties.getNumberOfOutputs(), hiddenNeurons);
      ret.add(networkToTry);
    }
    return ret;
  }

  /**
   * Create a network neuron descriptor based on the number of neurons in each
   * specified layer.
   * 
   * @param numberOfInputs
   *          The number of neurons in the input layer
   * @param numberOfOutputs
   *          The number of neurons in the output layer
   * @param hiddenNeurons
   *          The number of neurons in each hidden layer. hiddenNeurons.length
   *          is the number of hidden layers.
   * 
   * @return List<Integer> - a complete neuron descriptor for the network. Complete in this
   *         context means it contains the number of neurons in ALL layers (not just hidden layers
   *         as is the case in {@link Networks#getNetworks()}).
   */
  protected List<Integer> setupNeuronLayers(int numberOfInputs, int numberOfOutputs, int... hiddenNeurons) {
    List<Integer> neuronLayerDescriptor = new ArrayList<>();
    neuronLayerDescriptor.add(numberOfInputs);
    for (int neuronCount : hiddenNeurons) {
      neuronLayerDescriptor.add(neuronCount);
    }
    neuronLayerDescriptor.add(numberOfOutputs);
    return neuronLayerDescriptor;
  }

  /**
   * Randomizes the network weights based on settings in {@link NetworkProperties}, rather than
   * letting Neuroph do it for us.
   * 
   * @param network
   *          The network (must support BackPropagation learning rule) for which its weights
   *          are to be randomized.
   */
  private void randomizeNetworkWeights(NeuralNetwork<BackPropagation> network) {
    Random randWeight = new Random();
    double minWeight =
        randWeight.nextDouble() * (NetworkProperties.getMinWeight() - NetworkProperties.getMinMinWeight())
            + NetworkProperties.getMinMinWeight();
    double maxWeight =
        randWeight.nextDouble() * (NetworkProperties.getMaxWeight() - NetworkProperties.getMinMaxWeight())
            + NetworkProperties.getMinMaxWeight();
    log.info("Randomizing weights: min=" + minWeight + ", max=" + maxWeight);
    network.randomizeWeights(minWeight, maxWeight);
  }

  /**
   * Randomizes the momentum between MIN and MAX values as specified in {@NetworkProperties}.
   * The learning momentum is a component that determines how fast the weights are adjusted between
   * each epoch, meaning, it is a component in how fast a network can be trained. But the value should
   * not be too high, or the weights will be adjusted so fast the network can't be trained, or so low
   * that is takes forever for the network to be trained.
   * 
   * Only randomize momentum if the system is configured to do so.
   * 
   * @return The new momentum value.
   */
  private double randomizeMomentum() {
    double ret = NetworkProperties.getMomentumDefaultValue();
    //
    // If the system is configured to randomize momentum, then calculate
    /// a new random value between the MIN and MAX
    if (NetworkProperties.getRandomizeMomentum()) {
      Random momentumRandomNg = new Random();
      double momentum = momentumRandomNg.nextDouble()
          * (NetworkProperties.getLearningRuleMomentumMax() - NetworkProperties.getLearningRuleMomentumMin())
          + NetworkProperties.getLearningRuleMomentumMin();
      ret = momentum;
    }
    log.info("Momentum value: " + ret);
    return ret;
  }

  /**
   * Validate the network for the specified years.
   * 
   * @param network
   */
  private void validateNetwork(MultiLayerPerceptron network) {
    int numberOfWinners = 0;
    int numberOfGames = 0;

    //
    // Get NetworkMetrics object from cache
    NetworkMetrics metrics = networkMetricsCache.get(network);
    log.info("********* BEGINNING VALIDATION FOR YEARS " + Arrays.toString(metrics.getSimulationYears())
        + " **************");
    for (Integer yearToSimulate : metrics.getSimulationYears()) {
      int numberOfWinnersThisYear = 0;
      int numberOfGamesThisYear = 0;
      List<TournamentResult> tournamentResults = pullTournamentResults(yearToSimulate);
      SeasonAnalytics seasonAnalytics = pullSeasonAnalytics(yearToSimulate);
      //
      // Let's test the network - winner (LHS) and loser (RHS)
      log.debug("Testing the network with data from year..." + yearToSimulate);
      //
      // Simulate all games for the current yearToSimulate
      for (TournamentResult tournamentResult : tournamentResults) {
        numberOfWinnersThisYear += computeSimulatedGameCorrectPicks(network, seasonAnalytics, tournamentResult);
      }
      //
      // Each game is simulated twice
      numberOfGamesThisYear = tournamentResults.size() * 2;
      numberOfGames += numberOfGamesThisYear;
      numberOfWinners += numberOfWinnersThisYear;
      //
      // This year?
      log.info("WINNING PERCENTAGE (" + yearToSimulate + "): " +
          BigDecimal.valueOf(numberOfWinnersThisYear * 100.0 / numberOfGamesThisYear).setScale(2, RoundingMode.HALF_UP)
          +
          "% (" + numberOfWinnersThisYear + "/" + numberOfGamesThisYear + ")");
    }
    //
    // Set metrics
    metrics.setNumberOfGamesThisIteration(numberOfGames);
    metrics.setNumberOfWinsThisIteration(numberOfWinners);
    metrics.setIterationTime(System.currentTimeMillis() - metrics.getIterationStartTime());
    log.info("WINNING PERCENTAGE (TOTAL): " +
        BigDecimal.valueOf(numberOfWinners * 100.0 / numberOfGames).setScale(2, RoundingMode.HALF_UP) +
        "% (" + numberOfWinners + "/" + numberOfGames + ")");
    //
    // Calculate stats across all the simulated years and save the network
    /// if the average over all games picked in those years exceeds the threshold.
    computeStatsAndSaveNetworkIfNecessary(network);
  }

  /**
   * Using the specified <em>trained</em> <code>network</code>, run a simulation of the two teams in the
   * specified <code>tournamentResult</code>, to see what happens. For now, we're just trying to see how
   * good the network is.
   * 
   * A single execution of this method represents a single simulation of a single historical
   * tournament game, using a trained network.
   * 
   * @param network
   *          The trained MLP network to run in the simulation.
   * 
   * @param incorrectPicks
   *          The number of incorrect picks for this network. Used for reporting.
   * 
   * @param seasonAnalytics
   *          The {@link SeasonAnalytics} object, used to normalize the data.
   * 
   * @param tournamentResult
   *          The historical tournament game that will be simulated. Since it
   *          already happened, we know, well, what happened, which is exactly what we need when validating
   *          the network.
   * 
   * @return int - The number of correct picks for this simulation.
   */
  protected int computeSimulatedGameCorrectPicks(MultiLayerPerceptron network,
      SeasonAnalytics seasonAnalytics, TournamentResult tournamentResult) {
    //
    // Get the network metrics
    NetworkMetrics metrics = networkMetricsCache.get(network);
    //
    // Now simulate a single historical tournament game and see how many picks were correct.
    int numberOfCorrectPicks = simulateSingleHistoricalTournamentGame(network, seasonAnalytics, tournamentResult);
    //
    // Symmetric wins/losses are wins/losses where the same team wins/loses as both home (LHS) and away (RHS).
    /// This means the relationships in the network picked them as a winner/loser regardless of LHS/RHS,
    /// so for a symmetric win/loss there is no positional bias (that's the idea, anyway).
    // An asymmetric win/loss is one where the team one as LHS and lost as RHS (or vice versa).
    if (numberOfCorrectPicks == 2) {
      // Increment number of Symmetric wins
      metrics.setNumberOfSymmetricWinsThisIteration(
          metrics.getNumberOfSymmetricWinsThisIteration() + numberOfCorrectPicks);
      metrics.setTotalNumberOfSymmetricWins(metrics.getTotalNumberOfSymmetricWins() + numberOfCorrectPicks);
    } else if (numberOfCorrectPicks == 0) {
      // Increment number of symmetric losses
      metrics
          .setNumberOfSymmetricLossesThisIteration(
              metrics.getNumberOfSymmetricLossesThisIteration() + 2);
      metrics.setTotalNumberOfSymmetricLosses(
          metrics.getTotalNumberOfSymmetricLosses() + 2);
    } else {
      // Increment number of asymmetric wins
      metrics.setNumberOfAsymmetricWinsThisIteration(
          metrics.getNumberOfAsymmetricWinsThisIteration() + numberOfCorrectPicks);
      metrics.setTotalNumberOfAsymmetricWins(metrics.getTotalNumberOfAsymmetricWins() + numberOfCorrectPicks);
    }
    return numberOfCorrectPicks;
  }

  /**
   * Simulate a single historical tournament game using the trained MLP network.
   * 
   * @param network
   * @param seasonAnalytics
   * @param tournamentResult
   * 
   * @return int The number of correct picks for this simulation.
   */
  private int simulateSingleHistoricalTournamentGame(MultiLayerPerceptron network,
      SeasonAnalytics seasonAnalytics, TournamentResult tournamentResult) {
    int numberOfCorrectPicks = 0;
    //
    // Get the winner and loser from the historical tournament game, and create an array
    /// to hold their season data.
    String winningTeamName = tournamentResult.getWinningTeamName();
    String losingTeamName = tournamentResult.getLosingTeamName();
    //
    // Let's store the season data in such a way that we can blindly retrieve it
    /// using the simulation index of the loop.
    // Simulate the game twice, once with the home team as LHS, then with home
    /// team as RHS. This is an attempt to eliminate positional bias in the network.
    SeasonData[] seasonDataHomeTeam = new SeasonData[2];
    SeasonData[] seasonDataAwayTeam = new SeasonData[2];
    Integer yearToSimulate = tournamentResult.getYear();
    for (int simulationIndex = 0; simulationIndex < 2; simulationIndex++) {
      //
      // Switch up the team order of every other game in this loop.
      /// This presumes the numberOfSimulationsPerGame is an even number.
      if (simulationIndex % 2 == 0) {
        seasonDataHomeTeam[simulationIndex] = pullSeasonData(yearToSimulate, winningTeamName);
        seasonDataAwayTeam[simulationIndex] = pullSeasonData(yearToSimulate, losingTeamName);
      } else {
        seasonDataHomeTeam[simulationIndex] = pullSeasonData(yearToSimulate, losingTeamName);
        seasonDataAwayTeam[simulationIndex] = pullSeasonData(yearToSimulate, winningTeamName);
      }
      //
      // Create the simulation data and validate the network's predictive capabilities
      // If true is returned, the network predicted correctly.
      //
      // Get the normalized data for the home and away teams. Each iteration of the loop
      /// the actual teams switch places to hopefully eliminate any positional bias in the network,
      /// the idea being to get better picks.
      DataSetRow dataSetRow =
          DataCreator.processAsDataSetRowForSimulation(seasonAnalytics, seasonDataHomeTeam[simulationIndex],
              seasonDataAwayTeam[simulationIndex]);
      //
      // Run the network and capture the output, which contains the win(index 0) and loss(index 1) percentages.
      double[] networkOutput = runNetwork(network, dataSetRow.getInput());
      //
      // Check whether network predicted the outcome of this game correctly.
      boolean networkPredictionIsCorrect = computeNetworkPrediction(network, tournamentResult, seasonDataHomeTeam,
          seasonDataAwayTeam, simulationIndex, networkOutput);
      //
      // Increment number of correct picks if network prediction was correct
      if (networkPredictionIsCorrect) {
        numberOfCorrectPicks++;
      }
    }
    return numberOfCorrectPicks;
  }

  /**
   * Figure out (compute) what the network predicted for the specified simulated historical
   * game. Returns true if the network predicted correctly, or false if it did not.
   * 
   * @param network
   * @param tournamentResult
   * @param seasonDataHomeTeam
   * @param seasonDataAwayTeam
   * @param simulationIndex
   * @param networkOutput
   * 
   * @return - Returns true if the network predicted correctly, or false if it did not.
   */
  private boolean computeNetworkPrediction(MultiLayerPerceptron network, TournamentResult tournamentResult,
      SeasonData[] seasonDataHomeTeam, SeasonData[] seasonDataAwayTeam, int simulationIndex, double[] networkOutput) {
    boolean ret = false;
    // Default to LHS (index 0 in the output array)
    String predictedWinner = seasonDataHomeTeam[simulationIndex].getTeamName();
    String predictedLoser = seasonDataAwayTeam[simulationIndex].getTeamName();
    Double predictedWinningProbability = networkOutput[0];
    Double predictedLosingProbability = networkOutput[1];
    NetworkMetrics metrics = networkMetricsCache.get(network);
    if (networkOutput[1] >= networkOutput[0]) {
      // Unless RHS (index 1 in the output array) wins
      predictedWinner = seasonDataAwayTeam[simulationIndex].getTeamName();
      predictedLoser = seasonDataHomeTeam[simulationIndex].getTeamName();
      predictedWinningProbability = networkOutput[1];
      predictedLosingProbability = networkOutput[0];
    }
    //
    // If the predicted winner is the actual winner, the network picked correctly.
    if (predictedWinner.equalsIgnoreCase(tournamentResult.getWinningTeamName())) {
      ret = true;
    } else {
      // Otherwise, the network did not pick correctly
      metrics.getIncorrectPicks().add(tournamentResult);
    }
    if (log.isDebugEnabled()) {
      log.debug(
          "Predicted results: " + predictedWinner + " should defeat " + predictedLoser +
              " (W/L Probabilities: " + predictedWinningProbability + " / " + predictedLosingProbability + "), " +
              "Actual winner: " + tournamentResult.getWinningTeamName() + "(" + tournamentResult.getGameDate() +
              ": def. " + tournamentResult.getLosingTeamName() + ": score " + tournamentResult.getWinningScore() + "-"
              + tournamentResult.getLosingScore() + ")");
    }
    return ret;
  }

  /**
   * Computes final iteration stats and saves this network (for later use when running a real
   * prediction) if it meets the criteria (e.g., if it performs above a certain winning % threshold).
   * 
   * @param network
   */
  private void computeStatsAndSaveNetworkIfNecessary(MultiLayerPerceptron network) {
    //
    // Set total metrics
    NetworkMetrics metrics = networkMetricsCache.get(network);
    metrics.setTotalIterationTime(metrics.getTotalIterationTime() + metrics.getIterationTime());
    metrics.setTotalNumberOfGames(metrics.getTotalNumberOfGames() + metrics.getNumberOfGamesThisIteration());
    metrics.setTotalNumberOfWins(metrics.getTotalNumberOfWins() + metrics.getNumberOfWinsThisIteration());
    //
    // Calculate winning percentage
    BigDecimal winningPercentage = BigDecimal
        .valueOf(100.0 * metrics.getNumberOfWinsThisIteration() / metrics.getNumberOfGamesThisIteration()).setScale(5,
            RoundingMode.HALF_UP);
    //
    // Keep track of best and worst so far
    if (winningPercentage.doubleValue() > metrics.getBestNetworkWinPercentage()) {
      metrics.setBestNetworkWinPercentage(winningPercentage.doubleValue());
    }
    if (winningPercentage.doubleValue() < metrics.getWorstNetworkWinPercentage()) {
      metrics.setWorstNetworkWinPercentage(winningPercentage.doubleValue());
    }
    //
    // If the network should be saved, then save this network
    /// for running simulations later.
    if (networkShouldBeSaved(network)) {
      saveNetworkToFile(network);
      metrics.setNumberOfAcceptableNetworks(metrics.getNumberOfAcceptableNetworks() + 1);
    }
    log.info("Number of winners: " + metrics.getNumberOfWinsThisIteration() + " out of "
        + metrics.getNumberOfGamesThisIteration()
        + " games played.");
    log.info("WINNING PERCENTAGE: " + winningPercentage.toPlainString() + "%");
    //
    // Calculate the "symmetric" winning percentage - that is, the number of wins that were both
    /// from Home and Away (no positional bias in the network).
    BigDecimal symmetricWinningPercentage = BigDecimal
        .valueOf(100.0 * metrics.getNumberOfSymmetricWinsThisIteration() / metrics.getNumberOfGamesThisIteration())
        .setScale(5,
            RoundingMode.HALF_UP);
    log.info("SYMMETRIC WINNING PERCENTAGE: " + symmetricWinningPercentage.toPlainString() + "%");
  }

  /**
   * Determines whether the specified network should be saved or not.
   * Keeps the logic separate for making this determination. Down the road I might
   * make this more complicated, or subclasses could determine this however they
   * want.
   *
   * @param network
   * 
   * @return - Returns true if the network should be saved, false otherwise.
   */
  protected boolean networkShouldBeSaved(MultiLayerPerceptron network) {
    //
    // Get the network metrics
    NetworkMetrics metrics = networkMetricsCache.get(network);
    //
    // Current method of determing whether or not network should be saved:
    // Compute winning percentage and round it to an integer number and compare it
    /// the threshold value in NetworkProperties
    BigDecimal winningPercentage = BigDecimal
        .valueOf(100.0 * metrics.getNumberOfWinsThisIteration() / metrics.getNumberOfGamesThisIteration()).setScale(5,
            RoundingMode.HALF_UP);
    BigDecimal currentPerformance =
        BigDecimal.valueOf(winningPercentage.doubleValue()).setScale(0, RoundingMode.HALF_UP);
    //
    // Return true if network performed above threshold, false otherwise
    return currentPerformance.doubleValue() >= NetworkProperties.getPerformanceThreshold().doubleValue();
  }

  /**
   * Saves the specified network to a file. Lots of information is encoded in the file name.
   * Neuroph doesn't do a great job of storing this information in saved networks, so it's lost
   * (at least as of the time I write this). The idea is that you can glance at the name of the
   * saved network file and get a sense of what the network is about.
   * <p>
   * I won't lie to you. File names are LONG (because of all the encoded information). Maybe too long?
   * If you think so, "Use the source, Luke!" For those of you who live under a rock and didn't
   * catch the allusion: "scroll down, and change the code."
   * 
   * @param network
   *          The MLP network to be saved. Kind of important.
   */
  private void saveNetworkToFile(MultiLayerPerceptron network) {
    MomentumBackpropagation learningRule = (MomentumBackpropagation) network.getLearningRule();
    NetworkMetrics metrics = networkMetricsCache.get(network);
    NeuronProperties neuronProperties = metrics.getNeuronProperties();
    String useBias = neuronProperties.getProperty("useBias").toString();
    // String learningRate = Double.toString(learningRule.getLearningRate());
    String numberOfTrainingDataRows = Integer.toString(metrics.getNumberOfTrainingDataRows());
    String maxError = Double.toString(learningRule.getMaxError());
    // String momentum = BigDecimal.valueOf(learningRule.getMomentum()).setScale(5,
    // RoundingMode.HALF_UP).toPlainString();
    String networkError =
        BigDecimal.valueOf(100.0 * learningRule.getTotalNetworkError()).setScale(2, RoundingMode.HALF_UP)
            .toPlainString();
    BigDecimal winningPercentage = BigDecimal
        .valueOf(100.0 * metrics.getNumberOfWinsThisIteration() / metrics.getNumberOfGamesThisIteration()).setScale(5,
            RoundingMode.HALF_UP);
    String date = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
    String numberOfInputs = Integer.toString(network.getInputsCount());
    // String numberOfLayers = Integer.toString(network.getLayersCount());
    // String numberOfOutputs = Integer.toString(network.getOutputsCount()).toString();
    String yearsToSimulate = arrayToFilename(metrics.getSimulationYears());
    String networkDirectory = NetworkUtils.fetchNetworkDirectoryAndCreateIfNecessary();
    String filename = networkDirectory
        + File.separator
        + NetworkProperties.getNetworkFileBase() +
        "-SimYrs_" + yearsToSimulate
        // "-Type_" + networkType + "-xferfn_" +
        // neuronProperties.getTransferFunction().getName() + "-ipfn_" +
        // inputFunction +
        + "-bias_" + useBias
        // + "-LrnRt_" + learningRate
        + "-NmInp_" + numberOfInputs + "-"// + "-NmLayrs_" + numberOfLayers + "-"
        + metrics.getLayerStructure()
        // + "-NmOutp_" + numberOfOutputs
        + "-NmTrRows_" + numberOfTrainingDataRows + "-MxErr_" + maxError
        // + "-Mom_" + momentum
        + "-Dt_" + date
        + "-TotErr_" + networkError
        + "-winpct_" + winningPercentage + "_" + metrics.getNumberOfAcceptableNetworks()
        + NetworkProperties.getNetworkFileExtension();
    log.info("*** PERFORMANCE THRESHOLD EXCEEDED ON ITERATION " + metrics.getNumberOfIterationsSoFar() + " ***");
    logNetworkInfo(metrics, neuronProperties, (MomentumBackpropagation) network.getLearningRule());
    // Network has exceeded performance. Saving it (so we don't lose it)....
    log.info("*********** SAVING NEURAL NETWORK **************");
    network.save(filename);
    log.info("Network saved as: \n" + filename);
  }

  /**
   * Prints final stats for the network.
   */
  protected void logFinalGeneratorStats() {
    log.info("**** PRINTING STATS FOR ALL NETWORKS... ****");
    for (MultiLayerPerceptron network : networkMetricsCache.keySet()) {
      logIterationStatsForNetwork(network);
    }
  }

  /**
   * Logs/prints metrics for the current CTV iteration of the network.
   * 
   * @param metrics
   *          The ubiquitous @{link NetworkMetrics} object.
   * 
   */
  private void logIterationStatsForNetwork(MultiLayerPerceptron network) {
    NetworkMetrics metrics = networkMetricsCache.get(network);
    log.info("*********************************************************************");
    log.info("* Network Layer Structure         : " + metrics.getLayerStructure());
    log.info(
        "* Iteration duration              : " + (metrics.getIterationTime()) / 1000 + "s");
    log.info("* Number of aborted runs          : " + metrics.getNumberOfAbortedRuns());
    log.info("* Best Performing network         : "
        + ((metrics.getBestNetworkWinPercentage() == 0.0) ? "N/A"
            : BigDecimal.valueOf(metrics.getBestNetworkWinPercentage()).setScale(2, RoundingMode.HALF_UP) + "%"));
    log.info("* Worst Performing network        : "
        + ((metrics.getWorstNetworkWinPercentage() == 100.0) ? "N/A"
            : BigDecimal.valueOf(metrics.getWorstNetworkWinPercentage()).setScale(2, RoundingMode.HALF_UP) + "%"));
    log.info("* Number of acceptable networks   : " + metrics.getNumberOfAcceptableNetworks());
    log.info("* Learn Time (this loop)          : " + metrics.getIterationLearnTime() / 1000 + "s");
    log.info("* Best Learn time                 : "
        + ((metrics.getBestLearnTime() == Long.MAX_VALUE) ? "N/A" : metrics.getBestLearnTime() / 1000 + "s"));
    log.info("* Worst Learn time                : "
        + ((metrics.getWorstLearnTime() == Long.MIN_VALUE) ? "N/A" : metrics.getWorstLearnTime() / 1000 + "s"));
    log.info("* Average Learn time              : "
        + BigDecimal.valueOf((double) metrics.getTotalLearnTime() / 1000 / metrics.getNumberOfIterationsSoFar())
            .setScale(2,
                RoundingMode.HALF_UP)
        + "s");
    log.info("* Iteration Time (this loop)      : " + metrics.getIterationTime() / 1000 + "s");
    log.info("* Best Iteration time             : "
        + ((metrics.getBestIterationTime() == Long.MAX_VALUE) ? "N/A" : metrics.getBestIterationTime() / 1000 + "s"));
    log.info("* Worst Iteration time            : "
        + ((metrics.getWorstIterationTime() == Long.MIN_VALUE) ? "N/A" : metrics.getWorstIterationTime() / 1000 + "s"));
    log.info("* Average Iteration time          : "
        + BigDecimal.valueOf((double) metrics.getTotalIterationTime() / 1000 / metrics.getNumberOfIterationsSoFar())
            .setScale(2,
                RoundingMode.HALF_UP)
        + "s");
    log.info("* Number of games                 : " + metrics.getNumberOfGamesThisIteration());
    log.info("* Number of symetric wins         : " + metrics.getNumberOfSymmetricWinsThisIteration());
    log.info("* Number of symetric losses       : " + metrics.getNumberOfSymmetricLossesThisIteration());
    log.info("* Number of asymetric wins        : " + metrics.getNumberOfAsymmetricWinsThisIteration());
    log.info("* Total Number of games           : " + metrics.getTotalNumberOfGames());
    log.info("* Total Number wins               : " + metrics.getTotalNumberOfWins());
    log.info("* Total Number of symetric wins   : " + metrics.getTotalNumberOfSymmetricWins());
    log.info("* Total Number of symetric losses : " + metrics.getTotalNumberOfSymmetricLosses());
    log.info("* Total Number of asymetric wins  : " + metrics.getTotalNumberOfAsymmetricWins());
  }

  /**
   * Formats the objects in the specified array as a string.
   * Uses underscores between constituents, since the primary purpose
   * of calling this method is to create a file name using the string
   * representations of the array's contents.
   * 
   * @param objArray
   *          The object array
   * 
   * @return The String to be used as a filename.
   */
  private static String arrayToFilename(Object[] objArray) {
    StringBuilder sb = new StringBuilder();
    int aa = 0;
    for (Object o : objArray) {
      if (aa++ > 0) {
        sb.append("_");
      }
      sb.append(o.toString());
    }
    return sb.toString();
  }

  /**
   * Yet another method to log network info. I probably should combine all these at
   * some point.
   * 
   * @param metrics
   *          The ubiquitous {@link NetworkMetrics} object.
   * @param neuronProperties
   *          The Neuroph neuron properties metadata object
   * @param learningRule
   *          The learning rule in use
   */
  private static void logNetworkInfo(NetworkMetrics metrics, NeuronProperties neuronProperties,
      MomentumBackpropagation learningRule) {
    StringBuilder sb;
    String useBias = neuronProperties.getProperty("useBias").toString();
    String learningRate = Double.toString(learningRule.getLearningRate());
    String maxError = Double.toString(learningRule.getMaxError());
    String momentum = Double.toString(learningRule.getMomentum());
    log.info("*** NETWORK INFO ***");

    sb = new StringBuilder();
    sb.append("Network Info:\n");
    sb.append("\tUse Bias            : " + useBias + "\n");
    sb.append("\tLearning Rate       : " + learningRate + "\n");
    sb.append("\tMax Error           : " + maxError + "\n");
    sb.append("\tMomentum            : " + momentum + "\n");
    sb.append("\tLayer Structure     : " + metrics.getLayerStructure() + "\n");
    sb.append("\tTotal Network Error : "
        + BigDecimal.valueOf(learningRule.getTotalNetworkError() * 100.0).setScale(2, RoundingMode.HALF_UP));
    log.info(sb.toString());

  }

  /**
   * Runs the specified network using the double array as input data. This data should
   * be normalized or things will get real weird, real quick.
   * 
   * @param network
   *          The MLP network to be run
   * @param input
   *          The normalized input data to use to run the network
   * @return double[] - The network's "answers" from running the network. For the networks
   *         CTV'd with input data created by DataCreator, this means two doubles, whose range is
   *         between 0.0 and 1.0:
   *         <ol>
   *         <li>ret[0] - The probability the Home team wins (Away team loses)</li>
   *         <li>ret[1] - The probability the Home team loses (Away team wins)</li>
   *         </ol>
   */
  protected double[] runNetwork(MultiLayerPerceptron network, double[] input) {
    double[] ret;
    network.setInput(input);
    network.calculate();
    // Return value is the network's output
    ret = network.getOutput();
    if (log.isTraceEnabled()) {
      StringBuilder sb = new StringBuilder();
      sb.append("Comparison: Input to Output:\n");
      sb.append("Input : ");
      sb.append(Arrays.toString(input));
      sb.append('\n');
      sb.append("Output: ");
      sb.append(Arrays.toString(ret));
      log.trace(sb.toString());
    }
    if (log.isTraceEnabled()) {
      log.trace("Network Input : " + Arrays.toString(input));
      log.trace("Network Output: " + Arrays.toString(ret));
    }
    return ret;
  }

  /**
   * Handles the Neuroph LearningEvent event triggered by the Neuroph framework.
   * After each epoch we need to determine what the network error is doing: is it getting
   * better (and by how much)? Is it getting worse? If it is getting worse as a trend,
   * it's safe to just bail, let the network readjust the weights, momentum and try
   * another CTV iteration. Only try a certain number of epochs (configurable) before
   * giving up. Only consider the network "trained" when it is below the max threshold
   * (configurable).
   */
  @SuppressWarnings("unchecked")
  @Override
  public void handleLearningEvent(LearningEvent event) {
    MomentumBackpropagation mbp = (MomentumBackpropagation) event.getSource();
    double currentNetworkError = mbp.getTotalNetworkError();
    if (mbp.getCurrentIteration() == 1) {
      log.info("Epoch: " + mbp.getCurrentIteration() + " | Network error: " +
          BigDecimal.valueOf(currentNetworkError * 100.0).setScale(2, RoundingMode.HALF_UP) + "%");
      NetworkMetrics metrics = networkMetricsCache.get(mbp.getNeuralNetwork());
      metrics.setPreviousEpochBreakNetworkError(currentNetworkError);
    }
    if (event.getEventType().equals(LearningEvent.Type.LEARNING_STOPPED)) {
      log.info("Epoch: " + mbp.getCurrentIteration() + " | Network error: " +
          BigDecimal.valueOf(currentNetworkError * 100.0).setScale(2, RoundingMode.HALF_UP) + "%");
      log.info("Training completed in " + mbp.getCurrentIteration() + " Epochs");
    } else {
      if (log.isTraceEnabled() && mbp.getCurrentIteration() % 100 == 0) {
        log.trace("Epoch: " + mbp.getCurrentIteration() + " | Network error: " +
            BigDecimal.valueOf(currentNetworkError * 100.0).setScale(2, RoundingMode.HALF_UP) + "%");
      }
      //
      // Every epoch break, let's see what's going on
      if (mbp.getCurrentIteration() % NetworkProperties.getEpochBreakIterationCount() == 0) {
        NetworkMetrics metrics = networkMetricsCache.get(mbp.getNeuralNetwork());
        double networkErrorUptrend = currentNetworkError - metrics.getPreviousEpochBreakNetworkError();
        //
        // If the network error is going up, we will randomize the momentum and weights
        /// to nudge the network along (rather than just bailing on this CTV iteration)
        if (metrics.getPreviousEpochBreakNetworkError() != 0.0 &&
            currentNetworkError > metrics.getPreviousEpochBreakNetworkError()
            && (networkErrorUptrend >= NetworkProperties.getMaxNetworkErrorUptrend())) {
          log.warn("* Network error trending upwards: previous error: "
              + BigDecimal.valueOf(metrics.getPreviousEpochBreakNetworkError() * 100.0).setScale(2,
                  RoundingMode.HALF_UP)
              +
              "%, current error: " + BigDecimal.valueOf(currentNetworkError * 100.0).setScale(2, RoundingMode.HALF_UP) +
              ", trend is " + BigDecimal.valueOf(networkErrorUptrend * 100.0).setScale(2, RoundingMode.HALF_UP)
              + "% (max allowable is "
              + BigDecimal.valueOf(NetworkProperties.getMaxNetworkErrorUptrend() * 100.0).setScale(2,
                  RoundingMode.HALF_UP)
              + "%), computing new Momentum value...");
          //
          // Alter the momentum to see if it helps get us out of this upward error trend,
          /// rather than just giving up on this CTV iteration
          mbp.setMomentum(randomizeMomentum());
          randomizeNetworkWeights(mbp.getNeuralNetwork());
          log.info("Adding another 500 iterations to the max...");
          mbp.setMaxIterations(mbp.getMaxIterations() + 500);
        }
        //
        // Log stats for the current "epoch break"
        log.info("Epoch: " + mbp.getCurrentIteration() + " | " +
            "Current network error: " +
            BigDecimal.valueOf(currentNetworkError * 100.0).setScale(5, RoundingMode.HALF_UP) +
            // currentNetworkError * 100.0 +
            "%, "
            +
            "Previous network error: " +
            BigDecimal.valueOf(metrics.getPreviousEpochBreakNetworkError() * 100.0).setScale(5, RoundingMode.HALF_UP)
            // previousNetworkError * 100.0
            + "%, " +
            "Error Trend: " +
            BigDecimal.valueOf(networkErrorUptrend * 100.0).setScale(5, RoundingMode.HALF_UP) +
            // networkErrorUptrend * 100.0 +
            "%");
        metrics.setPreviousEpochBreakNetworkError(currentNetworkError);
      }
    }
  }

  /**
   * Usage message. Pretty self-explanatory.
   */
  protected static void usage() {
    System.out.println("Usage: ");
    System.out.println("\t" + MlpNetworkTrainer.class.getSimpleName()
        + " TRAINING_YEAR_1, TRAINING_YEAR_2, ..., TRAINING_YEAR_N SIMULATED_YEAR");
    System.out.println("\t Where:");
    System.out.println("\t TRAINING_YEAR_x is the year for which training data is to be loaded");
    System.out.println("\t SIMULATED_YEAR is the year for which the sumlation is to run (to build the network)");
  }

}
