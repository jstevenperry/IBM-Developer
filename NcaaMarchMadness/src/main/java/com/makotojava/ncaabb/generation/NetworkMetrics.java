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

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import org.neuroph.util.NeuronProperties;

import com.makotojava.ncaabb.model.TournamentResult;

/**
 * Metrics for the Network. The Create/Train/Validate (CTV) cycle is
 * long and lots happens, so there needs to be a place where the info
 * is stored.
 * 
 * That place is here.
 * 
 * Some metrics are for all iterations of the network, and some are for
 * the current CTV iteration cycle only. I have tried to point those
 * out in the Javadoc comments for the properties below.
 * 
 * Quick note: each network gets its own NetworkMetrics object. So
 * when you read the comments below w/r/t what metrics are captured,
 * remember that metric applies ONLY to a single network.
 * 
 * @author J Steven Perry
 *
 */
public class NetworkMetrics implements Comparator<NetworkMetrics> {
  /**
   * Keep track of the incorrect picks. All CTV iterations.
   */
  private Set<TournamentResult> incorrectPicks;
  /**
   * The Neuroph NeuronProperties metadata object
   */
  private NeuronProperties neuronProperties;
  /**
   * String representation of the network structure of the network
   */
  private String layerStructure;
  /**
   * The years to be simulated when validating the network
   */
  private Integer[] simulationYears;
  /**
   * The number of CTV iterations so far
   */
  private int numberOfIterationsSoFar = 0;
  /**
   * The number of networks that have performed acceptably so far.
   * All CTV iterations.
   */
  private int numberOfAcceptableNetworks = 0;
  /**
   * The network with the best Win percentage so far. Initialized
   * to 0 so that the first network performs better than this and its
   * win % is saved.
   * All CTV iterations.
   */
  private double bestNetworkWinPercentage = 0.0;
  /**
   * The network with the worst win percentage so far. Initialized
   * to 100 so that the first network performs worse than this
   * and its win % is saved.
   * All CTV iterations.
   */
  private double worstNetworkWinPercentage = 100.0;// Think about it
  /**
   * Number of wins where the same team won as home but lost
   * as away (or vice versa). Indicates some positional bias
   * in the network.
   * All CTV iterations.
   */
  private int totalNumberOfAsymmetricWins = 0;
  /**
   * Number of wins there the same team won both home and away.
   * This is what we want ideally. Indicates no (or minimal)
   * positional bias in the network.
   * All CTV iterations.
   */
  private int totalNumberOfSymmetricWins = 0;
  /**
   * Number of losses as both home and away.
   * All CTV iterations.
   */
  private int totalNumberOfSymmetricLosses = 0;
  /**
   * Total number of wins (regardless of symmetry).
   * All CTV iterations.
   */
  private int totalNumberOfWins = 0;
  /**
   * Total number of simulated games for all iterations so far.
   * All CTV iterations.
   */
  private int totalNumberOfGames = 0;
  /**
   * Number of games for this network CTV iteration
   */
  private int numberOfGamesThisIteration = 0;
  /**
   * Number of wins where the same team won as home but lost
   * as away (or vice versa).
   * Current network CTV iteration only.
   * Indicates some positional bias in the network.
   */
  private int numberOfAsymmetricWinsThisIteration = 0;
  /**
   * Number of wins there the same team won both home and away.
   * This is what we want ideally. Indicates no (or minimal)
   * positional bias in the network.
   * Current network CTV iteration only.
   */
  private int numberOfSymmetricWinsThisIteration = 0;
  /**
   * Number of losses as both home and away.
   * Current network CTV iteration only.
   */
  private int numberOfSymmetricLossesThisIteration = 0;
  /**
   * Number of wins (regardless of symmetry).
   * Current network CTV iteration only.
   */
  private int numberOfWinsThisIteration = 0;
  /**
   * Start time in millis of the current iteration
   */
  private long iterationStartTime = 0;
  /**
   * Learn start time in millis for the current iteration
   */
  private long learnStartTime = 0;
  /**
   * Learn time in millis for this iteration
   */
  private long iterationLearnTime = 0;
  /**
   * Best learn time in millis.
   * All CTV iterations of this
   * network. Initialized to Long.MAX_VALUE so the
   * first network's learn time will be less than this no matter
   * how long it took to learn.
   */
  private long bestLearnTime = Long.MAX_VALUE;// Think about it
  /**
   * Worst learn time in millis.
   * All CTV iterations of this
   * network. Initialized to zero so the first network's
   * learn time will be higher than this.
   */
  private long worstLearnTime = 0;
  /**
   * Total learn time in millis.
   * All CTV iterations of this network.
   */
  private long totalLearnTime = 0;
  /**
   * CTV iteration time for the current iteration.
   */
  private long iterationTime = 0;
  /**
   * Total iteration time in millis, all CTV iterations.
   */
  private long totalIterationTime = 0;
  /**
   * Best iteration time in millis.
   * Initialized for the same reason as the other bestXxx.
   */
  private long bestIterationTime = Long.MAX_VALUE;
  /**
   * Worst iteration time in millis.
   * Initialized for the same reason as the other worstXxx
   */
  private long worstIterationTime = 0;
  /**
   * Number of runs where the decision was made to give up
   * rather than try and keep training the current network.
   */
  private int numberOfAbortedRuns = 0;
  /**
   * Number of rows of training data used to train the networks.
   * This is the same for all networks.
   */
  private int numberOfTrainingDataRows = 0;
  /**
   * The network error on the previous epoch break. Let's us
   * see how the network training is progressing.
   */
  private double previousEpochBreakNetworkError = 0.0;
  
  
  public int getNumberOfAcceptableNetworks() {
    return numberOfAcceptableNetworks;
  }
  public void setNumberOfAcceptableNetworks(int numberOfAcceptableNetworks) {
    this.numberOfAcceptableNetworks = numberOfAcceptableNetworks;
  }
  public double getBestNetworkWinPercentage() {
    return bestNetworkWinPercentage;
  }
  public void setBestNetworkWinPercentage(double bestNetworkWinPercentage) {
    this.bestNetworkWinPercentage = bestNetworkWinPercentage;
  }
  public double getWorstNetworkWinPercentage() {
    return worstNetworkWinPercentage;
  }
  public void setWorstNetworkWinPercentage(double worstNetworkWinPercentage) {
    this.worstNetworkWinPercentage = worstNetworkWinPercentage;
  }
  public int getTotalNumberOfWins() {
    return totalNumberOfWins;
  }
  public void setTotalNumberOfWins(int totalNumberOfWins) {
    this.totalNumberOfWins = totalNumberOfWins;
  }
  public int getTotalNumberOfGames() {
    return totalNumberOfGames;
  }
  public void setTotalNumberOfGames(int totalNumberOfGames) {
    this.totalNumberOfGames = totalNumberOfGames;
  }

  public long getIterationStartTime() {
    return iterationStartTime;
  }

  public void setIterationStartTime(long iterationStartTime) {
    this.iterationStartTime = iterationStartTime;
  }
  public long getLearnStartTime() {
    return learnStartTime;
  }
  public void setLearnStartTime(long learnStartTime) {
    this.learnStartTime = learnStartTime;
  }
  public long getIterationLearnTime() {
    return iterationLearnTime;
  }
  public void setIterationLearnTime(long iterationLearnTime) {
    this.iterationLearnTime = iterationLearnTime;
    if (iterationLearnTime > worstLearnTime) {
      worstLearnTime = iterationLearnTime;
    }
    if (iterationLearnTime < bestLearnTime) {
      bestLearnTime = iterationLearnTime;
    }

  }
  public long getBestLearnTime() {
    return bestLearnTime;
  }
  public void setBestLearnTime(long bestLearnTime) {
    this.bestLearnTime = bestLearnTime;
  }
  public long getWorstLearnTime() {
    return worstLearnTime;
  }
  public void setWorstLearnTime(long worstLearnTime) {
    this.worstLearnTime = worstLearnTime;
  }
  public long getTotalLearnTime() {
    return totalLearnTime;
  }
  public void setTotalLearnTime(long totalLearnTime) {
    this.totalLearnTime = totalLearnTime;
  }
  public long getTotalIterationTime() {
    return totalIterationTime;
  }
  public void setTotalIterationTime(long totalSimulationTime) {
    this.totalIterationTime = totalSimulationTime;
  }
  public long getBestIterationTime() {
    return bestIterationTime;
  }
  public void setBestIterationTime(long bestSimulationTime) {
    this.bestIterationTime = bestSimulationTime;
  }
  public long getWorstIterationTime() {
    return worstIterationTime;
  }
  public void setWorstIterationTime(long worstSimulationTime) {
    this.worstIterationTime = worstSimulationTime;
  }

  public long getIterationTime() {
    return iterationTime;
  }

  public void setIterationTime(long iterationTime) {
    this.iterationTime = iterationTime;
    if (iterationTime > worstIterationTime) {
      worstIterationTime = iterationTime;
    }
    if (iterationTime < bestIterationTime) {
      bestIterationTime = iterationTime;
    }

  }
  public String getLayerStructure() {
    return layerStructure;
  }
  public void setLayerStructure(String layerStructure) {
    this.layerStructure = layerStructure;
  }
  public int getTotalNumberOfAsymmetricWins() {
    return totalNumberOfAsymmetricWins;
  }

  public void setTotalNumberOfAsymmetricWins(int totalNumberOfAsymmetricWins) {
    this.totalNumberOfAsymmetricWins = totalNumberOfAsymmetricWins;
  }
  public int getTotalNumberOfSymmetricWins() {
    return totalNumberOfSymmetricWins;
  }

  public void setTotalNumberOfSymmetricWins(int totalNumberOfSymmetricWins) {
    this.totalNumberOfSymmetricWins = totalNumberOfSymmetricWins;
  }
 
  @Override
  public int compare(NetworkMetrics o1, NetworkMetrics o2) {
    int ret = o1.getLayerStructure().compareTo(o2.getLayerStructure());
    return ret;
  }

  public int getNumberOfIterationsSoFar() {
    return numberOfIterationsSoFar;
  }

  public void setNumberOfIterationsSoFar(int numberOfIterations) {
    this.numberOfIterationsSoFar = numberOfIterations;
  }

  public Integer[] getSimulationYears() {
    return simulationYears;
  }
  public void setSimulationYears(Integer[] simulationYears) {
    // Make a deep copy of the array
    this.simulationYears = new Integer[simulationYears.length];
    System.arraycopy(simulationYears, 0, this.simulationYears, 0, simulationYears.length);
  }

  public int getNumberOfAbortedRuns() {
    return numberOfAbortedRuns;
  }

  public void setNumberOfAbortedRuns(int numberOfAbortedRuns) {
    this.numberOfAbortedRuns = numberOfAbortedRuns;
  }

  public int getNumberOfGamesThisIteration() {
    return numberOfGamesThisIteration;
  }

  public void setNumberOfGamesThisIteration(int numberOfGamesThisIteration) {
    this.numberOfGamesThisIteration = numberOfGamesThisIteration;
  }

  public int getNumberOfAsymmetricWinsThisIteration() {
    return numberOfAsymmetricWinsThisIteration;
  }

  public void setNumberOfAsymmetricWinsThisIteration(int numberOfAsymmetricWinsThisIteration) {
    this.numberOfAsymmetricWinsThisIteration = numberOfAsymmetricWinsThisIteration;
  }

  public int getNumberOfSymmetricWinsThisIteration() {
    return numberOfSymmetricWinsThisIteration;
  }

  public void setNumberOfSymmetricWinsThisIteration(int numberOfSymmetricWinsThisIteration) {
    this.numberOfSymmetricWinsThisIteration = numberOfSymmetricWinsThisIteration;
  }

  public int getNumberOfWinsThisIteration() {
    return numberOfWinsThisIteration;
  }

  public void setNumberOfWinsThisIteration(int numberOfWinsThisIteration) {
    this.numberOfWinsThisIteration = numberOfWinsThisIteration;
  }

  public int getTotalNumberOfSymmetricLosses() {
    return totalNumberOfSymmetricLosses;
  }

  public void setTotalNumberOfSymmetricLosses(int totalNumberOfSymmetricLosses) {
    this.totalNumberOfSymmetricLosses = totalNumberOfSymmetricLosses;
  }

  public int getNumberOfSymmetricLossesThisIteration() {
    return numberOfSymmetricLossesThisIteration;
  }

  public void setNumberOfSymmetricLossesThisIteration(int numberOfSymmetricLossesThisIteration) {
    this.numberOfSymmetricLossesThisIteration = numberOfSymmetricLossesThisIteration;
  }

  public NeuronProperties getNeuronProperties() {
    return neuronProperties;
  }

  public void setNeuronProperties(NeuronProperties neuronProperties) {
    this.neuronProperties = neuronProperties;
  }

  public Set<TournamentResult> getIncorrectPicks() {
    if (incorrectPicks == null) {
      incorrectPicks = new TreeSet<>();
    }
    return incorrectPicks;
  }

  public void setIncorrectPicks(Set<TournamentResult> incorrectPicks) {
    this.incorrectPicks = incorrectPicks;
  }

  public int getNumberOfTrainingDataRows() {
    return numberOfTrainingDataRows;
  }

  public void setNumberOfTrainingDataRows(int numberOfTrainingDataRows) {
    this.numberOfTrainingDataRows = numberOfTrainingDataRows;
  }

  public double getPreviousEpochBreakNetworkError() {
    return previousEpochBreakNetworkError;
  }

  public void setPreviousEpochBreakNetworkError(double previousEpochBreakNetworkError) {
    this.previousEpochBreakNetworkError = previousEpochBreakNetworkError;
  }
  
}