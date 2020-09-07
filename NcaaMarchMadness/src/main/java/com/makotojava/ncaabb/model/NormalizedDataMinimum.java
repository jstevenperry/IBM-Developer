package com.makotojava.ncaabb.model;

import com.makotojava.ncaabb.util.StatsUtils;

public class NormalizedDataMinimum extends NormalizedData {
  /**
   * Constructor - the one and only way to create an instance of this class.
   * The data is normalized at the moment of creation.
   *
   * @param seasonAnalytics The SeasonAnalytics object needed to normalize the data
   *                        (you know, contains all those neat little MIN and MAX values)
   * @param team1           The LHS SeasonData object with raw values (un-normalized)
   * @param team2
   */
  public NormalizedDataMinimum(SeasonAnalytics seasonAnalytics, SeasonData team1, SeasonData team2) {
    super(seasonAnalytics, team1, team2);
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
//      left.getAvgOpponentPointsPerGame().doubleValue(), right.getAvgOpponentPointsPerGame().doubleValue(),
//      left.getBlocksPerGame().doubleValue(), right.getBlocksPerGame().doubleValue(),
//      left.getNumOpp3pAttemptsPerGame().doubleValue(), right.getNumOpp3pAttemptsPerGame().doubleValue(),
//      left.getOpp3pPercentage().doubleValue(), right.getOpp3pPercentage().doubleValue(),
//      left.getNumOppFgAttemptsPerGame().doubleValue(), right.getNumOppFgAttemptsPerGame().doubleValue(),
      left.getOppFgPercentage().doubleValue(), right.getOppFgPercentage().doubleValue(),
      left.getReboundMargin().doubleValue(), right.getReboundMargin().doubleValue(),
      left.getStealsPerGame().doubleValue(), right.getStealsPerGame().doubleValue(),
//      left.getOppTurnoversPerGame().doubleValue(), right.getOppTurnoversPerGame().doubleValue(),
      // Errors
      left.getFoulsPerGame().doubleValue(), right.getFoulsPerGame().doubleValue(),
//      left.getNumDq().doubleValue(), right.getNumDq().doubleValue(),
      left.getTurnoversPerGame().doubleValue(), right.getTurnoversPerGame().doubleValue(),
      // Offense
      left.getAvgPointsPerGame().doubleValue(), right.getAvgPointsPerGame().doubleValue(),
//      left.getScoringMarginPerGame().doubleValue(), right.getScoringMarginPerGame().doubleValue(),
//      left.getNumFgAttemptsPerGame().doubleValue(), right.getNumFgAttemptsPerGame().doubleValue(),
      left.getFgPercentage().doubleValue(), right.getFgPercentage().doubleValue(),
//      left.getNumFtAttemptsPerGame().doubleValue(), right.getNumFtAttemptsPerGame().doubleValue(),
      left.getFtPercentage().doubleValue(), right.getFtPercentage().doubleValue(),
//      left.getNum3pAttemptsPerGame().doubleValue(), right.getNum3pAttemptsPerGame().doubleValue(),
      left.getNum3pPerGame().doubleValue(), right.getNum3pPerGame().doubleValue(),
//      left.getT3pPercentage().doubleValue(), right.getT3pPercentage().doubleValue(),
      left.getAssistsPerGame().doubleValue(), right.getAssistsPerGame().doubleValue(),
//      left.getAtoRatio().doubleValue(), right.getAtoRatio().doubleValue(),
      //
      // The outputs are at the end
      0.0, 0.0
    };
    //
    // Return the line
    return ret;

  }

  protected void normalizeData(SeasonAnalytics seasonAnalytics, SeasonData normalized, SeasonData raw) {
    normalized.setTeamName(raw.getTeamName());
    normalized.setAvgPointsPerGame(StatsUtils.normalize(raw.getAvgPointsPerGame(), seasonAnalytics.getMinAvgPointsPg(), seasonAnalytics.getMaxAvgPointsPg()));
//    normalized.setScoringMarginPerGame(StatsUtils.normalize(raw.getScoringMarginPerGame(), seasonAnalytics.getMinScoringMarginPg(), seasonAnalytics.getMaxScoringMarginPg()));
//    normalized.setNumFgAttemptsPerGame(StatsUtils.normalize(raw.getNumFgAttemptsPerGame(), seasonAnalytics.getMinNumFgAttemptsPg(), seasonAnalytics.getMaxNumFgAttemptsPg()));
    normalized.setFgPercentage(StatsUtils.normalize(raw.getFgPercentage(), seasonAnalytics.getMinFgPercentage(), seasonAnalytics.getMaxFgPercentage()));
//    normalized.setNumFtAttemptsPerGame(StatsUtils.normalize(raw.getNumFtAttemptsPerGame(), seasonAnalytics.getMinNumFtAttemptsPg(), seasonAnalytics.getMaxNumFtAttemptsPg()));
    normalized.setFtPercentage(StatsUtils.normalize(raw.getFtPercentage(), seasonAnalytics.getMinFtPercentage(), seasonAnalytics.getMaxFtPercentage()));
//    normalized.setNum3pAttemptsPerGame(StatsUtils.normalize(raw.getNum3pAttemptsPerGame(), seasonAnalytics.getMinNum3pAttemptsPg(), seasonAnalytics.getMaxNum3pAttemptsPg()));
    normalized.setNum3pPerGame(StatsUtils.normalize(raw.getNum3pPerGame(), seasonAnalytics.getMinNum3pPerGame(), seasonAnalytics.getMaxNum3pPerGame()));
//    normalized.setT3pPercentage(StatsUtils.normalize(raw.getT3pPercentage(), seasonAnalytics.getMinT3pPercentage(), seasonAnalytics.getMaxT3pPercentage()));
    normalized.setAssistsPerGame(StatsUtils.normalize(raw.getAssistsPerGame(), seasonAnalytics.getMinAssistsPg(), seasonAnalytics.getMaxAssistsPg()));
//    normalized.setAtoRatio(StatsUtils.normalize(raw.getAtoRatio(), seasonAnalytics.getMinAtoRatio(), seasonAnalytics.getMaxAtoRatio()));
//    normalized.setAvgOpponentPointsPerGame(StatsUtils.normalizeInverted(raw.getAvgOpponentPointsPerGame(), seasonAnalytics.getMinAvgOpponentPointsPg(), seasonAnalytics.getMaxAvgOpponentPointsPg()));
//    normalized.setBlocksPerGame(StatsUtils.normalize(raw.getBlocksPerGame(), seasonAnalytics.getMinBlocksPg(), seasonAnalytics.getMaxBlocksPg()));
//    normalized.setNumOpp3pAttemptsPerGame(StatsUtils.normalizeInverted(raw.getNumOpp3pAttemptsPerGame(), seasonAnalytics.getMinNumOpp3pAttemptsPg(), seasonAnalytics.getMaxNumOpp3pAttemptsPg()));
//    normalized.setNumOppFgAttemptsPerGame(StatsUtils.normalizeInverted(raw.getNumOppFgAttemptsPerGame(), seasonAnalytics.getMinNumOppFgAttemptsPg(), seasonAnalytics.getMaxNumOppFgAttemptsPg()));
//    normalized.setOpp3pPercentage(StatsUtils.normalizeInverted(raw.getOpp3pPercentage(), seasonAnalytics.getMinOpp3pPercentage(), seasonAnalytics.getMaxOpp3pPercentage()));
    normalized.setOppFgPercentage(StatsUtils.normalizeInverted(raw.getOppFgPercentage(), seasonAnalytics.getMinOppFgPercentage(), seasonAnalytics.getMaxOppFgPercentage()));
//    normalized.setOppTurnoversPerGame(StatsUtils.normalize(raw.getOppTurnoversPerGame(), seasonAnalytics.getMinOppTurnoversPg(), seasonAnalytics.getMaxOppTurnoversPg()));
    normalized.setReboundMargin(StatsUtils.normalize(raw.getReboundMargin(), seasonAnalytics.getMinReboundMargin(), seasonAnalytics.getMaxReboundMargin()));
    normalized.setStealsPerGame(StatsUtils.normalize(raw.getStealsPerGame(), seasonAnalytics.getMinStealsPg(), seasonAnalytics.getMaxStealsPg()));
    normalized.setFoulsPerGame(StatsUtils.normalizeInverted(raw.getFoulsPerGame(), seasonAnalytics.getMinFoulsPg(), seasonAnalytics.getMaxFoulsPg()));
//    normalized.setNumDq(StatsUtils.normalizeInverted(raw.getNumDq(), seasonAnalytics.getMinNumDq(), seasonAnalytics.getMaxNumDq()));
    normalized.setTurnoversPerGame(StatsUtils.normalizeInverted(raw.getTurnoversPerGame(), seasonAnalytics.getMinTurnoversPg(), seasonAnalytics.getMaxTurnoversPg()));
  }


}
