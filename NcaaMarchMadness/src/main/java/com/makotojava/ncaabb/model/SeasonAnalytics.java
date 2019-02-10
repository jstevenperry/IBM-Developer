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

import java.math.BigDecimal;

import com.makotojava.ncaabb.util.StatsUtils;

/**
 * This class is used to model the v_season_analytics view, which is used to normalize
 * the data.
 * 
 * @author J Steven Perry
 * 
 *         Model object for the PostgreSQL view that has the following structure:
 * 
 *         View "public.v_season_analytics"
 *         Column | Type | Modifiers
 *         ----------------------------+---------+-----------
 *         year | integer |
 *         min_points_pg | numeric |
 *         max_points_pg | numeric |
 *         min_scoring_margin_pg | numeric |
 *         max_scoring_margin_pg | numeric |
 *         min_num_fg_attempts_pg | numeric |
 *         max_num_fg_attempts_pg | numeric |
 *         min_fg_percentage | numeric |
 *         max_fg_percentage | numeric |
 *         min_num_3p_per_game | numeric |
 *         max_num_3p_per_game | numeric |
 *         min_num_3p_attempts_pg | numeric |
 *         max_num_3p_attempts_pg | numeric |
 *         min_t3p_percentage | numeric |
 *         max_t3p_percentage | numeric |
 *         min_num_ft_attempts_pg | numeric |
 *         max_num_ft_attempts_pg | numeric |
 *         min_ft_percentage | numeric |
 *         max_ft_percentage | numeric |
 *         min_rebound_margin | numeric |
 *         max_rebound_margin | numeric |
 *         min_assists_pg | numeric |
 *         max_assists_pg | numeric |
 *         min_ato_ratio | numeric |
 *         max_ato_ratio | numeric |
 *         min_avg_opponent_points_pg | numeric |
 *         max_avg_opponent_points_pg | numeric |
 *         min_num_opp_fg_attempts_pg | numeric |
 *         max_num_opp_fg_attempts_pg | numeric |
 *         min_opp_fg_percentage | numeric |
 *         max_opp_fg_percentage | numeric |
 *         min_num_opp_3p_attempts_pg | numeric |
 *         max_num_opp_3p_attempts_pg | numeric |
 *         min_opp_3p_percentage | numeric |
 *         max_opp_3p_percentage | numeric |
 *         min_blocks_pg | numeric |
 *         max_blocks_pg | numeric |
 *         min_steals_pg | numeric |
 *         max_steals_pg | numeric |
 *         min_opp_turnovers_pg | numeric |
 *         max_opp_turnovers_pg | numeric |
 *         min_turnovers_pg | numeric |
 *         max_turnovers_pg | numeric |
 *         min_fouls_pg | numeric |
 *         max_fouls_pg | numeric |
 *         min_num_dq | integer |
 *         max_num_dq | integer |
 * 
 * @author sperry
 *
 */
public class SeasonAnalytics {

  private Integer year;
  private BigDecimal minAvgPointsPg;
  private BigDecimal maxAvgPointsPg;
  private BigDecimal minScoringMarginPg;
  private BigDecimal maxScoringMarginPg;
  private BigDecimal minNumFgAttemptsPg;
  private BigDecimal maxNumFgAttemptsPg;
  private BigDecimal minFgPercentage;
  private BigDecimal maxFgPercentage;
  private BigDecimal minNum3pPerGame;
  private BigDecimal maxNum3pPerGame;
  private BigDecimal minNum3pAttemptsPg;
  private BigDecimal maxNum3pAttemptsPg;
  private BigDecimal minT3pPercentage;
  private BigDecimal maxT3pPercentage;
  private BigDecimal minNumFtAttemptsPg;
  private BigDecimal maxNumFtAttemptsPg;
  private BigDecimal minFtPercentage;
  private BigDecimal maxFtPercentage;
  private BigDecimal minReboundMargin;
  private BigDecimal maxReboundMargin;
  private BigDecimal minAssistsPg;
  private BigDecimal maxAssistsPg;
  private BigDecimal minAtoRatio;
  private BigDecimal maxAtoRatio;
  private BigDecimal minAvgOpponentPointsPg;
  private BigDecimal maxAvgOpponentPointsPg;
  private BigDecimal minNumOppFgAttemptsPg;
  private BigDecimal maxNumOppFgAttemptsPg;
  private BigDecimal minOppFgPercentage;
  private BigDecimal maxOppFgPercentage;
  private BigDecimal minNumOpp3pAttemptsPg;
  private BigDecimal maxNumOpp3pAttemptsPg;
  private BigDecimal minOpp3pPercentage;
  private BigDecimal maxOpp3pPercentage;
  private BigDecimal minBlocksPg;
  private BigDecimal maxBlocksPg;
  private BigDecimal minStealsPg;
  private BigDecimal maxStealsPg;
  private BigDecimal minOppTurnoversPg;
  private BigDecimal maxOppTurnoversPg;
  private BigDecimal minTurnoversPg;
  private BigDecimal maxTurnoversPg;
  private BigDecimal minFoulsPg;
  private BigDecimal maxFoulsPg;
  private BigDecimal minNumDq;
  private BigDecimal maxNumDq;
  
  public SeasonAnalytics() {
    // Nothing to do
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public BigDecimal getMinAvgPointsPg() {
    return minAvgPointsPg;
  }

  public void setMinAvgPointsPg(BigDecimal minPointsPg) {
    this.minAvgPointsPg = minPointsPg;
  }

  public BigDecimal getMaxAvgPointsPg() {
    return maxAvgPointsPg;
  }

  public void setMaxAvgPointsPg(BigDecimal maxPointsPg) {
    this.maxAvgPointsPg = maxPointsPg;
  }

  public BigDecimal getMinScoringMarginPg() {
    return minScoringMarginPg;
  }

  public void setMinScoringMarginPg(BigDecimal minScoringMarginPg) {
    this.minScoringMarginPg = minScoringMarginPg;
  }

  public BigDecimal getMaxScoringMarginPg() {
    return maxScoringMarginPg;
  }

  public void setMaxScoringMarginPg(BigDecimal maxScoringMarginPg) {
    this.maxScoringMarginPg = maxScoringMarginPg;
  }

  public BigDecimal getMinNumFgAttemptsPg() {
    return minNumFgAttemptsPg;
  }

  public void setMinNumFgAttemptsPg(BigDecimal minNumFgAttemptsPg) {
    this.minNumFgAttemptsPg = minNumFgAttemptsPg;
  }

  public BigDecimal getMaxNumFgAttemptsPg() {
    return maxNumFgAttemptsPg;
  }

  public void setMaxNumFgAttemptsPg(BigDecimal maxNumFgAttemptsPg) {
    this.maxNumFgAttemptsPg = maxNumFgAttemptsPg;
  }

  public BigDecimal getMinFgPercentage() {
    return minFgPercentage;
  }

  public void setMinFgPercentage(BigDecimal minFgPercentage) {
    this.minFgPercentage = minFgPercentage;
  }

  public BigDecimal getMaxFgPercentage() {
    return maxFgPercentage;
  }

  public void setMaxFgPercentage(BigDecimal maxFgPercentage) {
    this.maxFgPercentage = maxFgPercentage;
  }

  public BigDecimal getMinNum3pPerGame() {
    return minNum3pPerGame;
  }

  public void setMinNum3pPerGame(BigDecimal minNum3pPerGame) {
    this.minNum3pPerGame = minNum3pPerGame;
  }

  public BigDecimal getMaxNum3pPerGame() {
    return maxNum3pPerGame;
  }

  public void setMaxNum3pPerGame(BigDecimal maxNum3pPerGame) {
    this.maxNum3pPerGame = maxNum3pPerGame;
  }

  public BigDecimal getMinNum3pAttemptsPg() {
    return minNum3pAttemptsPg;
  }

  public void setMinNum3pAttemptsPg(BigDecimal minNum3pAttemptsPg) {
    this.minNum3pAttemptsPg = minNum3pAttemptsPg;
  }

  public BigDecimal getMaxNum3pAttemptsPg() {
    return maxNum3pAttemptsPg;
  }

  public void setMaxNum3pAttemptsPg(BigDecimal maxNum3pAttemptsPg) {
    this.maxNum3pAttemptsPg = maxNum3pAttemptsPg;
  }

  public BigDecimal getMinT3pPercentage() {
    return minT3pPercentage;
  }

  public void setMinT3pPercentage(BigDecimal minT3pPercentage) {
    this.minT3pPercentage = minT3pPercentage;
  }

  public BigDecimal getMaxT3pPercentage() {
    return maxT3pPercentage;
  }

  public void setMaxT3pPercentage(BigDecimal maxT3pPercentage) {
    this.maxT3pPercentage = maxT3pPercentage;
  }

  public BigDecimal getMinNumFtAttemptsPg() {
    return minNumFtAttemptsPg;
  }

  public void setMinNumFtAttemptsPg(BigDecimal minNumFtAttemptsPg) {
    this.minNumFtAttemptsPg = minNumFtAttemptsPg;
  }

  public BigDecimal getMaxNumFtAttemptsPg() {
    return maxNumFtAttemptsPg;
  }

  public void setMaxNumFtAttemptsPg(BigDecimal maxNumFtAttemptsPg) {
    this.maxNumFtAttemptsPg = maxNumFtAttemptsPg;
  }

  public BigDecimal getMinFtPercentage() {
    return minFtPercentage;
  }

  public void setMinFtPercentage(BigDecimal minFtPercentage) {
    this.minFtPercentage = minFtPercentage;
  }

  public BigDecimal getMaxFtPercentage() {
    return maxFtPercentage;
  }

  public void setMaxFtPercentage(BigDecimal maxFtPercentage) {
    this.maxFtPercentage = maxFtPercentage;
  }

  public BigDecimal getMinReboundMargin() {
    return minReboundMargin;
  }

  public void setMinReboundMargin(BigDecimal minReboundMargin) {
    this.minReboundMargin = minReboundMargin;
  }

  public BigDecimal getMaxReboundMargin() {
    return maxReboundMargin;
  }

  public void setMaxReboundMargin(BigDecimal maxReboundMargin) {
    this.maxReboundMargin = maxReboundMargin;
  }

  public BigDecimal getMinAssistsPg() {
    return minAssistsPg;
  }

  public void setMinAssistsPg(BigDecimal minAssistsPg) {
    this.minAssistsPg = minAssistsPg;
  }

  public BigDecimal getMaxAssistsPg() {
    return maxAssistsPg;
  }

  public void setMaxAssistsPg(BigDecimal maxAssistsPg) {
    this.maxAssistsPg = maxAssistsPg;
  }

  public BigDecimal getMinAtoRatio() {
    return minAtoRatio;
  }

  public void setMinAtoRatio(BigDecimal minAtoRatio) {
    this.minAtoRatio = minAtoRatio;
  }

  public BigDecimal getMaxAtoRatio() {
    return maxAtoRatio;
  }

  public void setMaxAtoRatio(BigDecimal maxAtoRatio) {
    this.maxAtoRatio = maxAtoRatio;
  }

  public BigDecimal getMinAvgOpponentPointsPg() {
    return minAvgOpponentPointsPg;
  }

  public void setMinAvgOpponentPointsPg(BigDecimal minAvgOpponentPointsPg) {
    this.minAvgOpponentPointsPg = minAvgOpponentPointsPg;
  }

  public BigDecimal getMaxAvgOpponentPointsPg() {
    return maxAvgOpponentPointsPg;
  }

  public void setMaxAvgOpponentPointsPg(BigDecimal maxAvgOpponentPointsPg) {
    this.maxAvgOpponentPointsPg = maxAvgOpponentPointsPg;
  }

  public BigDecimal getMinNumOppFgAttemptsPg() {
    return minNumOppFgAttemptsPg;
  }

  public void setMinNumOppFgAttemptsPg(BigDecimal minNumOppFgAttemptsPg) {
    this.minNumOppFgAttemptsPg = minNumOppFgAttemptsPg;
  }

  public BigDecimal getMaxNumOppFgAttemptsPg() {
    return maxNumOppFgAttemptsPg;
  }

  public void setMaxNumOppFgAttemptsPg(BigDecimal maxNumOppFgAttemptsPg) {
    this.maxNumOppFgAttemptsPg = maxNumOppFgAttemptsPg;
  }

  public BigDecimal getMinOppFgPercentage() {
    return minOppFgPercentage;
  }

  public void setMinOppFgPercentage(BigDecimal minOppFgPercentage) {
    this.minOppFgPercentage = minOppFgPercentage;
  }

  public BigDecimal getMaxOppFgPercentage() {
    return maxOppFgPercentage;
  }

  public void setMaxOppFgPercentage(BigDecimal maxOppFgPercentage) {
    this.maxOppFgPercentage = maxOppFgPercentage;
  }

  public BigDecimal getMinNumOpp3pAttemptsPg() {
    return minNumOpp3pAttemptsPg;
  }

  public void setMinNumOpp3pAttemptsPg(BigDecimal minNumOpp3pAttemptsPg) {
    this.minNumOpp3pAttemptsPg = minNumOpp3pAttemptsPg;
  }

  public BigDecimal getMaxNumOpp3pAttemptsPg() {
    return maxNumOpp3pAttemptsPg;
  }

  public void setMaxNumOpp3pAttemptsPg(BigDecimal maxNumOpp3pAttemptsPg) {
    this.maxNumOpp3pAttemptsPg = maxNumOpp3pAttemptsPg;
  }

  public BigDecimal getMinOpp3pPercentage() {
    return minOpp3pPercentage;
  }

  public void setMinOpp3pPercentage(BigDecimal minOpp3pPercentage) {
    this.minOpp3pPercentage = minOpp3pPercentage;
  }

  public BigDecimal getMaxOpp3pPercentage() {
    return maxOpp3pPercentage;
  }

  public void setMaxOpp3pPercentage(BigDecimal maxOpp3pPercentage) {
    this.maxOpp3pPercentage = maxOpp3pPercentage;
  }

  public BigDecimal getMinBlocksPg() {
    return minBlocksPg;
  }

  public void setMinBlocksPg(BigDecimal minBlocksPg) {
    this.minBlocksPg = minBlocksPg;
  }

  public BigDecimal getMaxBlocksPg() {
    return maxBlocksPg;
  }

  public void setMaxBlocksPg(BigDecimal maxBlocksPg) {
    this.maxBlocksPg = maxBlocksPg;
  }

  public BigDecimal getMinStealsPg() {
    return minStealsPg;
  }

  public void setMinStealsPg(BigDecimal minStealsPg) {
    this.minStealsPg = minStealsPg;
  }

  public BigDecimal getMaxStealsPg() {
    return maxStealsPg;
  }

  public void setMaxStealsPg(BigDecimal maxStealsPg) {
    this.maxStealsPg = maxStealsPg;
  }

  public BigDecimal getMinOppTurnoversPg() {
    return minOppTurnoversPg;
  }

  public void setMinOppTurnoversPg(BigDecimal minOppTurnoversPg) {
    this.minOppTurnoversPg = minOppTurnoversPg;
  }

  public BigDecimal getMaxOppTurnoversPg() {
    return maxOppTurnoversPg;
  }

  public void setMaxOppTurnoversPg(BigDecimal maxOppTurnoversPg) {
    this.maxOppTurnoversPg = maxOppTurnoversPg;
  }

  public BigDecimal getMinTurnoversPg() {
    return minTurnoversPg;
  }

  public void setMinTurnoversPg(BigDecimal minTurnoversPg) {
    this.minTurnoversPg = minTurnoversPg;
  }

  public BigDecimal getMaxTurnoversPg() {
    return maxTurnoversPg;
  }

  public void setMaxTurnoversPg(BigDecimal maxTurnoversPg) {
    this.maxTurnoversPg = maxTurnoversPg;
  }

  public BigDecimal getMinFoulsPg() {
    return minFoulsPg;
  }

  public void setMinFoulsPg(BigDecimal minFoulsPg) {
    this.minFoulsPg = minFoulsPg;
  }

  public BigDecimal getMaxFoulsPg() {
    return maxFoulsPg;
  }

  public void setMaxFoulsPg(BigDecimal maxFoulsPg) {
    this.maxFoulsPg = maxFoulsPg;
  }

  public BigDecimal getMinNumDq() {
    return minNumDq;
  }

  public void setMinNumDq(BigDecimal minNumDq) {
    this.minNumDq = minNumDq.setScale(StatsUtils.SCALE);
  }

  public BigDecimal getMaxNumDq() {
    return maxNumDq;
  }

  public void setMaxNumDq(BigDecimal maxNumDq) {
    this.maxNumDq = maxNumDq.setScale(StatsUtils.SCALE);
  }

}
