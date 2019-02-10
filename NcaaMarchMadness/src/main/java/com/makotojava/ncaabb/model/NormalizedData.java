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
package com.makotojava.ncaabb.model;

import org.apache.commons.lang3.tuple.Pair;

import com.makotojava.ncaabb.util.StatsUtils;

/**
 * Wrapper around {@link SeasonData} to use when training or running the network.
 * 
 * @author J Steven Perry
 *
 */
public class NormalizedData {

  /**
   * Commons lang3 Pair object to hold the pair of SeasonData objects containing
   * normalized data.
   */
  private Pair<SeasonData, SeasonData> seasonDataPair;

  private Pair<SeasonData, SeasonData> getSeasonDataPair() {
    if (seasonDataPair == null) {
      throw new RuntimeException("Configuration Error! The seasonDataPair object cannot be null!");
    }
    return seasonDataPair;
  }
  
  /**
   * Constructor - the one and only way to create an instance of this class.
   * The data is normalized at the moment of creation.
   * 
   * @param seasonAnalytics
   *          The SeasonAnalytics object needed to normalize the data
   *          (you know, contains all those neat little MIN and MAX values)
   * 
   * @param team1
   *          The LHS SeasonData object with raw values (un-normalized)
   * 
   * @param team2
   *          The RHS SeasonData object with raw values (un-normalized)
   */
  public NormalizedData(SeasonAnalytics seasonAnalytics, SeasonData team1, SeasonData team2) {
    //
    // Create the Pair object
    seasonDataPair = Pair.of(new SeasonData(), new SeasonData());

    //
    // Normalize the left-hand data
    normalizeData(seasonAnalytics, getSeasonDataPair().getLeft(), team1);
    //
    // Normalize the right-hand data
    normalizeData(seasonAnalytics, getSeasonDataPair().getRight(), team2);
  }

  /**
   * Performs the normalization. This class seems like the place to encapsulate that
   * logic.
   * 
   * @param seasonAnalytics
   *          Needed to perform the normalization.
   * @param normalized
   *          The SeasonData object where the normalized data goes (i.e., the destination)
   * @param raw
   *          The SeasonData object where the source data comes from (i.e., the source)
   */
  private void normalizeData(SeasonAnalytics seasonAnalytics, SeasonData normalized, SeasonData raw) {
    normalized.setTeamName(raw.getTeamName());
    normalized
        .setAvgPointsPerGame(StatsUtils.normalize(raw.getAvgPointsPerGame(), seasonAnalytics.getMinAvgPointsPg(),
            seasonAnalytics.getMaxAvgPointsPg()));
    normalized.setScoringMarginPerGame(StatsUtils.normalize(raw.getScoringMarginPerGame(),
        seasonAnalytics.getMinScoringMarginPg(), seasonAnalytics.getMaxScoringMarginPg()));
    normalized.setNumFgAttemptsPerGame(StatsUtils.normalize(raw.getNumFgAttemptsPerGame(),
        seasonAnalytics.getMinNumFgAttemptsPg(), seasonAnalytics.getMaxNumFgAttemptsPg()));
    normalized
        .setFgPercentage(StatsUtils.normalize(raw.getFgPercentage(), seasonAnalytics.getMinFgPercentage(),
            seasonAnalytics.getMaxFgPercentage()));
    normalized.setNumFtAttemptsPerGame(StatsUtils.normalize(raw.getNumFtAttemptsPerGame(),
        seasonAnalytics.getMinNumFtAttemptsPg(), seasonAnalytics.getMaxNumFtAttemptsPg()));
    normalized
        .setFtPercentage(StatsUtils.normalize(raw.getFtPercentage(), seasonAnalytics.getMinFtPercentage(),
            seasonAnalytics.getMaxFtPercentage()));
    normalized.setNum3pAttemptsPerGame(StatsUtils.normalize(raw.getNum3pAttemptsPerGame(),
        seasonAnalytics.getMinNum3pAttemptsPg(), seasonAnalytics.getMaxNum3pAttemptsPg()));
    normalized
        .setNum3pPerGame(StatsUtils.normalize(raw.getNum3pPerGame(), seasonAnalytics.getMinNum3pPerGame(),
            seasonAnalytics.getMaxNum3pPerGame()));
    normalized
        .setT3pPercentage(StatsUtils.normalize(raw.getT3pPercentage(), seasonAnalytics.getMinT3pPercentage(),
            seasonAnalytics.getMaxT3pPercentage()));
    normalized
        .setAssistsPerGame(StatsUtils.normalize(raw.getAssistsPerGame(), seasonAnalytics.getMinAssistsPg(),
            seasonAnalytics.getMaxAssistsPg()));
    normalized.setAtoRatio(
        StatsUtils.normalize(raw.getAtoRatio(), seasonAnalytics.getMinAtoRatio(), seasonAnalytics.getMaxAtoRatio()));
    normalized
        .setAvgOpponentPointsPerGame(StatsUtils.normalizeInverted(raw.getAvgOpponentPointsPerGame(),
            seasonAnalytics.getMinAvgOpponentPointsPg(), seasonAnalytics.getMaxAvgOpponentPointsPg()));
    normalized.setBlocksPerGame(
        StatsUtils.normalize(raw.getBlocksPerGame(), seasonAnalytics.getMinBlocksPg(),
            seasonAnalytics.getMaxBlocksPg()));
    normalized
        .setNumOpp3pAttemptsPerGame(StatsUtils.normalizeInverted(raw.getNumOpp3pAttemptsPerGame(),
            seasonAnalytics.getMinNumOpp3pAttemptsPg(), seasonAnalytics.getMaxNumOpp3pAttemptsPg()));
    normalized
        .setNumOppFgAttemptsPerGame(StatsUtils.normalizeInverted(raw.getNumOppFgAttemptsPerGame(),
            seasonAnalytics.getMinNumOppFgAttemptsPg(), seasonAnalytics.getMaxNumOppFgAttemptsPg()));
    normalized.setOpp3pPercentage(StatsUtils.normalizeInverted(raw.getOpp3pPercentage(),
        seasonAnalytics.getMinOpp3pPercentage(), seasonAnalytics.getMaxOpp3pPercentage()));
    normalized.setOppFgPercentage(StatsUtils.normalizeInverted(raw.getOppFgPercentage(),
        seasonAnalytics.getMinOppFgPercentage(), seasonAnalytics.getMaxOppFgPercentage()));
    normalized.setOppTurnoversPerGame(StatsUtils.normalize(raw.getOppTurnoversPerGame(),
        seasonAnalytics.getMinOppTurnoversPg(), seasonAnalytics.getMaxOppTurnoversPg()));
    normalized
        .setReboundMargin(StatsUtils.normalize(raw.getReboundMargin(), seasonAnalytics.getMinReboundMargin(),
            seasonAnalytics.getMaxReboundMargin()));
    normalized.setStealsPerGame(
        StatsUtils.normalize(raw.getStealsPerGame(), seasonAnalytics.getMinStealsPg(),
            seasonAnalytics.getMaxStealsPg()));
    normalized
        .setFoulsPerGame(StatsUtils.normalizeInverted(raw.getFoulsPerGame(), seasonAnalytics.getMinFoulsPg(),
            seasonAnalytics.getMaxFoulsPg()));
    normalized.setNumDq(
        StatsUtils.normalizeInverted(raw.getNumDq(), seasonAnalytics.getMinNumDq(), seasonAnalytics.getMaxNumDq()));
    normalized.setTurnoversPerGame(StatsUtils.normalizeInverted(raw.getTurnoversPerGame(),
        seasonAnalytics.getMinTurnoversPg(), seasonAnalytics.getMaxTurnoversPg()));
  }

  /**
   * Converts the contents of this object to a double array for processing
   * by the network.
   * 
   * @return double[] - The contents of this object, including the outputs
   */
  public double[] asInputAndOutput() {
    //
    // Create the training data line
    SeasonData left = getSeasonDataPair().getLeft();
    SeasonData right = getSeasonDataPair().getRight();
    double[] ret = {
        // Defense
        left.getAvgOpponentPointsPerGame().doubleValue(), right.getAvgOpponentPointsPerGame().doubleValue(),
        left.getBlocksPerGame().doubleValue(), right.getBlocksPerGame().doubleValue(),
        left.getNumOpp3pAttemptsPerGame().doubleValue(), right.getNumOpp3pAttemptsPerGame().doubleValue(),
        left.getOpp3pPercentage().doubleValue(), right.getOpp3pPercentage().doubleValue(),
        left.getNumOppFgAttemptsPerGame().doubleValue(), right.getNumOppFgAttemptsPerGame().doubleValue(),
        left.getOppFgPercentage().doubleValue(), right.getOppFgPercentage().doubleValue(),
        left.getTurnoversPerGame().doubleValue(), right.getTurnoversPerGame().doubleValue(),
        left.getReboundMargin().doubleValue(), right.getReboundMargin().doubleValue(),
        left.getStealsPerGame().doubleValue(), right.getStealsPerGame().doubleValue(),
        // Errors
        left.getFoulsPerGame().doubleValue(), right.getFoulsPerGame().doubleValue(),
        left.getNumDq().doubleValue(), right.getNumDq().doubleValue(),
        left.getOppTurnoversPerGame().doubleValue(), right.getOppTurnoversPerGame().doubleValue(),
        // Offense
        left.getAvgPointsPerGame().doubleValue(), right.getAvgPointsPerGame().doubleValue(),
        left.getScoringMarginPerGame().doubleValue(), right.getScoringMarginPerGame().doubleValue(),
        left.getNumFgAttemptsPerGame().doubleValue(), right.getNumFgAttemptsPerGame().doubleValue(),
        left.getFgPercentage().doubleValue(), right.getFgPercentage().doubleValue(),
        left.getNumFtAttemptsPerGame().doubleValue(), right.getNumFtAttemptsPerGame().doubleValue(),
        left.getFtPercentage().doubleValue(), right.getFtPercentage().doubleValue(),
        left.getNum3pAttemptsPerGame().doubleValue(), right.getNum3pAttemptsPerGame().doubleValue(),
        left.getNum3pPerGame().doubleValue(), right.getNum3pPerGame().doubleValue(),
        left.getT3pPercentage().doubleValue(), right.getT3pPercentage().doubleValue(),
        left.getAssistsPerGame().doubleValue(), right.getAssistsPerGame().doubleValue(),
        left.getAtoRatio().doubleValue(), right.getAtoRatio().doubleValue(),
        //
        // The outputs are at the end
        0.0, 0.0
    };
    //
    // Return the line
    return ret;

  }

}
