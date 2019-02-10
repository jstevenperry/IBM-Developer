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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.nnet.learning.BackPropagation;

/**
 * The result of a single simulated game between two teams.
 * 
 * The idea is that there are N > 0 networks used to make predictions.
 * These networks are trained and validated and have been saved for use
 * in simulating a tournament which has not yet happened (but for which
 * the participants are known, and their season data has been downloaded
 * and loaded into the DB).
 * 
 * This is basically a giant value object for storing information about
 * a single simulated game so the results can be printed out somehow
 * so you can enter your NCAA tournament brackets.
 * 
 * However, this class (as much of the code in this project) could be
 * easily extended to cover other sports as well.
 * 
 * @author J Steven Perry
 *
 * @param <T>
 *          The type of NeuralNetwork to use. Most likely this
 *          is a MultiLayerPerceptron. I created the class this way with the
 *          grand idea that I would try all kinds of networks, but there are
 *          about a million + 1 ways to configure everything, so I ended up
 *          focusing on the MLP only. But I left this class the way it is
 *          because, well, it works.
 */
public class GameSimulationResult<T extends NeuralNetwork<BackPropagation>> {

  /**
   * A "push" is where the network cannot predict a winner with any certainty.
   * The criteria for determining that are not set here. It could be that the
   * win probability has to be above 0.75 or it could be 0.5. It just depends
   * but the idea of a push has to be captured. Sometimes the network just
   * can't accurately pick a winner.
   */
  public static final String PUSH = "->PUSH";

  /**
   * The team for which we are simulating
   */
  private String teamName;
  /**
   * The team's opponent
   */
  private String opponentName;
  /**
   * The networks that went into deciding the result wrapped by this class
   */
  private List<T> networks;// read only
  /**
   * Predictions from all networks in a nice List of String
   * (for easy printing/logging)
   */
  private List<String> networkPredictions;// read only
  /**
   * The win probabilities for the team as the home team
   */
  private List<Double> teamHomeResults;// read only
  /**
   * The win probabilities for the team as the away team
   */
  private List<Double> teamAwayResults;// read only
  /**
   * The opposing team's win probabilities as the away team.
   */
  private List<Double> opponentAwayResults;// read only
  /**
   * The opposing team's win probabilities as the home team.
   */
  private List<Double> opponentHomeResults;// read only
  /**
   * The number of wins across all networks for the team
   */
  private Integer numberOfWins = 0;
  /**
   * The number of losses across all networks for the team
   */
  private Integer numberOfLosses = 0;
  /**
   * The number of pushes across all networks for the team
   */
  private Integer numberOfPushes = 0;
  
  /**
   * Constructor.
   * 
   * @param teamName
   * @param opponentName
   */
  public GameSimulationResult(String teamName, String opponentName) {
    this.teamName = teamName;
    this.opponentName = opponentName;
  }

  public String getTeamName() {
    return teamName;
  }

  public String getOpponentName() {
    return opponentName;
  }

  public List<T> getNetworks() {
    if (networks == null) {
      networks = new ArrayList<>();
    }
    return networks;
  }

  public List<String> getNetworkPredictions() {
    if (networkPredictions == null) {
      networkPredictions = new ArrayList<>();
    }
    return networkPredictions;
  }

  // Derived attribute
  public Integer getNumberOfNetworks() {
    return getNetworks().size();
  }

  public List<Double> getTeamHomeResults() {
    if (teamHomeResults == null) {
      teamHomeResults = new ArrayList<>();
    }
    return teamHomeResults;
  }

  public List<Double> getTeamAwayResults() {
    if (teamAwayResults == null) {
      teamAwayResults = new ArrayList<>();
    }
    return teamAwayResults;
  }

  public List<Double> getOpponentHomeResults() {
    if (opponentHomeResults == null) {
      opponentHomeResults = new ArrayList<>();
    }
    return opponentHomeResults;
  }

  public List<Double> getOpponentAwayResults() {
    if (opponentAwayResults == null) {
      opponentAwayResults = new ArrayList<>();
    }
    return opponentAwayResults;
  }

  public void incrementNumberOfWins() {
    this.numberOfWins += 1;
  }

  public void incrementNumberOfLosses() {
    this.numberOfLosses += 1;
  }

  public void incrementNumberOfPushes() {
    this.numberOfPushes += 1;
  }

  /**
   * Derived property: computes the average win percentage across all networks
   * and returns that value.
   * 
   * @return BigDecimal - the return value to 2 decimal places.
   */
  public BigDecimal getPercentWins() {
    return BigDecimal.valueOf(100.0 * numberOfWins / getNumberOfNetworks()).setScale(2, RoundingMode.HALF_UP);
  }

  /**
   * Derived property: computes the average loss percentage across all networks
   * and returns that value.
   * 
   * @return BigDecimal - the return value to 2 decimal places.
   */
  public BigDecimal getPercentLosses() {
    return BigDecimal.valueOf(100.0 * numberOfLosses / getNumberOfNetworks()).setScale(2, RoundingMode.HALF_UP);
  }

  /**
   * Derived property: computes the average push percentage across all networks
   * and returns that value.
   * 
   * @return BigDecimal - the return value to 2 decimal places.
   */
  public BigDecimal getPercentPushes() {
    return BigDecimal.valueOf(100.0 * numberOfPushes / getNumberOfNetworks()).setScale(2, RoundingMode.HALF_UP);
  }

}
