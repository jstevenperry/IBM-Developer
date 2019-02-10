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
package com.makotojava.ncaabb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.makotojava.ncaabb.model.SeasonAnalytics;

/**
 * SeasonAnalyticsDao interface implementation specifically for Spring.
 * 
 * @author J Steven Perry
 *
 */
public class SeasonAnalyticsJdbcDao extends JdbcDaoSupport implements SeasonAnalyticsDao {
  
  private static final Logger log = Logger.getLogger(SeasonAnalyticsJdbcDao.class);
  
  private static final String TABLE_NAME = "v_season_analytics";

  public SeasonAnalyticsJdbcDao(DataSource dataSource) {
    this.setDataSource(dataSource);
  }

  @Override
  public SeasonAnalytics fetchByYear(Integer year) {
    SeasonAnalytics ret = null;
    // 
    String sql = "SELECT * FROM " + TABLE_NAME + " WHERE year = ?";
    Object[] args = { year };
    //
    List<SeasonAnalytics> results = getJdbcTemplate().query(sql, args, new SeasonAnalyticsRowMapper());
    //
    if (results.size() > 0) {
      if (results.size() > 1) {
        log.warn("Expected 1 result from query, instead got " + results.size() + "!");
      }
      ret = results.get(0);
    } else {
      log.warn("Requested year (" + year + ") does not exist in the DB!");
    }    
    return ret;
  }
  
  /**
   * Maps the following columns
         View "public.v_season_analytics"
           Column           |  Type   | Modifiers 
----------------------------+---------+-----------
 year                       | integer | 
 min_points_pg              | numeric | 
 max_points_pg              | numeric | 
 min_scoring_margin_pg      | numeric | 
 max_scoring_margin_pg      | numeric | 
 min_num_fg_attempts_pg     | numeric | 
 max_num_fg_attempts_pg     | numeric | 
 min_fg_percentage          | numeric | 
 max_fg_percentage          | numeric | 
 min_num_3p_per_game        | numeric | 
 max_num_3p_per_game        | numeric | 
 min_num_3p_attempts_pg     | numeric | 
 max_num_3p_attempts_pg     | numeric | 
 min_t3p_percentage         | numeric | 
 max_t3p_percentage         | numeric | 
 min_num_ft_attempts_pg     | numeric | 
 max_num_ft_attempts_pg     | numeric | 
 min_ft_percentage          | numeric | 
 max_ft_percentage          | numeric | 
 min_rebound_margin         | numeric | 
 max_rebound_margin         | numeric | 
 min_assists_pg             | numeric | 
 max_assists_pg             | numeric | 
 min_ato_ratio              | numeric | 
 max_ato_ratio              | numeric | 
 min_avg_opponent_points_pg | numeric | 
 max_avg_opponent_points_pg | numeric | 
 min_num_opp_fg_attempts_pg | numeric | 
 max_num_opp_fg_attempts_pg | numeric | 
 min_opp_fg_percentage      | numeric | 
 max_opp_fg_percentage      | numeric | 
 min_num_opp_3p_attempts_pg | numeric | 
 max_num_opp_3p_attempts_pg | numeric | 
 min_opp_3p_percentage      | numeric | 
 max_opp_3p_percentage      | numeric | 
 min_blocks_pg              | numeric | 
 max_blocks_pg              | numeric | 
 min_steals_pg              | numeric | 
 max_steals_pg              | numeric | 
 min_opp_turnovers_pg       | numeric | 
 max_opp_turnovers_pg       | numeric | 
 min_turnovers_pg           | numeric | 
 max_turnovers_pg           | numeric | 
 min_fouls_pg               | numeric | 
 max_fouls_pg               | numeric | 
 min_num_dq                 | integer | 
 max_num_dq                 | integer | 
   */
  public class SeasonAnalyticsRowMapper implements RowMapper<SeasonAnalytics> {

    @Override
    public SeasonAnalytics mapRow(ResultSet resultSet, int rowNum) throws SQLException {
      SeasonAnalytics ret = new SeasonAnalytics();
      //
      ret.setYear(resultSet.getInt("year"));
      ret.setMinAvgPointsPg(resultSet.getBigDecimal("min_avg_points_pg"));
      ret.setMaxAvgPointsPg(resultSet.getBigDecimal("max_avg_points_pg"));
      ret.setMinScoringMarginPg(resultSet.getBigDecimal("min_scoring_margin_pg"));
      ret.setMaxScoringMarginPg(resultSet.getBigDecimal("max_scoring_margin_pg"));
      ret.setMinNumFgAttemptsPg(resultSet.getBigDecimal("min_num_fg_attempts_pg"));
      ret.setMaxNumFgAttemptsPg(resultSet.getBigDecimal("max_num_fg_attempts_pg"));
      ret.setMinFgPercentage(resultSet.getBigDecimal("min_fg_percentage"));
      ret.setMaxFgPercentage(resultSet.getBigDecimal("max_fg_percentage"));
      ret.setMinNum3pPerGame(resultSet.getBigDecimal("min_num_3p_per_game"));
      ret.setMaxNum3pPerGame(resultSet.getBigDecimal("max_num_3p_per_game"));
      ret.setMinNum3pAttemptsPg(resultSet.getBigDecimal("min_num_3p_attempts_pg"));
      ret.setMaxNum3pAttemptsPg(resultSet.getBigDecimal("max_num_3p_attempts_pg"));
      ret.setMinT3pPercentage(resultSet.getBigDecimal("min_t3p_percentage"));
      ret.setMaxT3pPercentage(resultSet.getBigDecimal("max_t3p_percentage"));
      ret.setMinNumFtAttemptsPg(resultSet.getBigDecimal("min_num_ft_attempts_pg"));
      ret.setMaxNumFtAttemptsPg(resultSet.getBigDecimal("max_num_ft_attempts_pg"));
      ret.setMinFtPercentage(resultSet.getBigDecimal("min_ft_percentage"));
      ret.setMaxFtPercentage(resultSet.getBigDecimal("max_ft_percentage"));
      ret.setMinReboundMargin(resultSet.getBigDecimal("min_rebound_margin"));
      ret.setMaxReboundMargin(resultSet.getBigDecimal("max_rebound_margin"));
      ret.setMinAssistsPg(resultSet.getBigDecimal("min_assists_pg"));
      ret.setMaxAssistsPg(resultSet.getBigDecimal("max_assists_pg"));
      ret.setMinAtoRatio(resultSet.getBigDecimal("min_ato_ratio"));
      ret.setMaxAtoRatio(resultSet.getBigDecimal("max_ato_ratio"));
      ret.setMinAvgOpponentPointsPg(resultSet.getBigDecimal("min_avg_opponent_points_pg"));
      ret.setMaxAvgOpponentPointsPg(resultSet.getBigDecimal("max_avg_opponent_points_pg"));
      ret.setMinNumOppFgAttemptsPg(resultSet.getBigDecimal("min_num_opp_fg_attempts_pg"));
      ret.setMaxNumOppFgAttemptsPg(resultSet.getBigDecimal("max_num_opp_fg_attempts_pg"));
      ret.setMinOppFgPercentage(resultSet.getBigDecimal("min_opp_fg_percentage"));
      ret.setMaxOppFgPercentage(resultSet.getBigDecimal("max_opp_fg_percentage"));
      ret.setMinNumOpp3pAttemptsPg(resultSet.getBigDecimal("min_num_opp_3p_attempts_pg"));
      ret.setMaxNumOpp3pAttemptsPg(resultSet.getBigDecimal("max_num_opp_3p_attempts_pg"));
      ret.setMinOpp3pPercentage(resultSet.getBigDecimal("min_opp_3p_percentage"));
      ret.setMaxOpp3pPercentage(resultSet.getBigDecimal("max_opp_3p_percentage"));
      ret.setMinBlocksPg(resultSet.getBigDecimal("min_blocks_pg"));
      ret.setMaxBlocksPg(resultSet.getBigDecimal("max_blocks_pg"));
      ret.setMinStealsPg(resultSet.getBigDecimal("min_steals_pg"));
      ret.setMaxStealsPg(resultSet.getBigDecimal("max_steals_pg"));
      ret.setMinOppTurnoversPg(resultSet.getBigDecimal("min_opp_turnovers_pg"));
      ret.setMaxOppTurnoversPg(resultSet.getBigDecimal("max_opp_turnovers_pg"));
      ret.setMinTurnoversPg(resultSet.getBigDecimal("min_turnovers_pg"));
      ret.setMaxTurnoversPg(resultSet.getBigDecimal("max_turnovers_pg"));
      ret.setMinFoulsPg(resultSet.getBigDecimal("min_fouls_pg"));
      ret.setMaxFoulsPg(resultSet.getBigDecimal("max_fouls_pg"));
      ret.setMinNumDq(resultSet.getBigDecimal("min_num_dq"));
      ret.setMaxNumDq(resultSet.getBigDecimal("max_num_dq"));
      //
      if (log.isTraceEnabled()) {
        log.trace("Mapped row number " + rowNum);
        log.trace("Mapped Object: " + ReflectionToStringBuilder.toString(ret));
      }
      //
      return ret;
    }
    
  }

}