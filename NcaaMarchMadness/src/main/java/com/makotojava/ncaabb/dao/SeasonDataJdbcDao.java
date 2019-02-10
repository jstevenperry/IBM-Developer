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

import com.makotojava.ncaabb.model.SeasonData;

/**
 * SeasonDataDao interface implementation specifically for Spring.
 * 
 * @author J Steven Perry
 *
 */
public class SeasonDataJdbcDao extends JdbcDaoSupport implements SeasonDataDao {
  
  private static final Logger log = Logger.getLogger(SeasonDataJdbcDao.class);
  
  private static final String TABLE_NAME = "v_season_data";

  public SeasonDataJdbcDao(DataSource dataSource) {
    this.setDataSource(dataSource);
  }

  @Override
  public List<SeasonData> fetchAllByYear(Integer year) {
    List<SeasonData> ret = null;
    
    String sql = "SELECT t.* FROM " + TABLE_NAME + " t WHERE t.year = ?";
    Object[] args = { year };
    // Run the query
    ret = getJdbcTemplate().query(sql, args, new SeasonDataRowMapper());
    
    return ret;
  }

  @Override
  public SeasonData fetchByYearAndTeamName(Integer year, String teamName) {
    SeasonData ret = null;
    String sql = "SELECT t.* FROM " + TABLE_NAME + " t WHERE t.year = ? AND t.team_name = ?";
    Object[] args = { year, teamName };
    // Run the query
    List<SeasonData> results = getJdbcTemplate().query(sql, args, new SeasonDataRowMapper());
    if (results.size() > 0) {
      if (results.size() > 1) {
        log.warn("Expected 1 result from query, instead got " + results.size() + "!");
      }
      ret = results.get(0);
    } else {
      log.warn("Requested team/year combination (" + teamName + "/" + year + ") does not exist in the DB!");
    }
    
    return ret;
  }
  
  /**
   * Maps the following columns from v_season_data:
                   View "public.v_season_data"
            Column            |         Type          | Modifiers 
------------------------------+-----------------------+-----------
 year                         | integer               | 
 team_name                    | character varying(64) | 
 avg_points_per_game          | numeric(5,2)          | 
 scoring_margin_per_game      | numeric(5,2)          | 
 num_fg_attempts_per_game     | numeric(7,5)          | 
 fg_percentage                | numeric(7,5)          | 
 num_3p_per_game              | numeric(5,2)          | 
 num_3p_attempts_per_game     | numeric(5,2)          | 
 t3p_percentage               | numeric(7,5)          | 
 num_ft_attempts_per_game     | numeric(5,2)          | 
 ft_percentage                | numeric(7,5)          | 
 rebound_margin               | numeric(7,5)          | 
 assists_per_game             | numeric(7,5)          | 
 ato_ratio                    | numeric(7,5)          | 
 avg_opponent_points_per_game | numeric(5,2)          | 
 num_opp_fg_attempts_per_game | numeric(7,5)          | 
 opp_fg_percentage            | numeric(7,5)          | 
 num_opp_3p_attempts_per_game | numeric(5,2)          | 
 opp_3p_percentage            | numeric(7,5)          | 
 blocks_per_game              | numeric(7,5)          | 
 steals_per_game              | numeric(7,5)          | 
 opp_turnovers_per_game       | numeric(7,2)          | 
 turnovers_per_game           | numeric(7,5)          | 
 fouls_per_game               | numeric(7,5)          | 
 num_dq                       | integer               | 
     * @author sperry
   *
   */
  public class SeasonDataRowMapper implements RowMapper<SeasonData> {

    /**
     * Maps the following columns
     */
    @Override
    public SeasonData mapRow(ResultSet resultSet, int rowNum) throws SQLException {
      SeasonData ret = new SeasonData();
      // Map from the ResultSet to a SeasonData object
      ret.setYear(resultSet.getInt("year"));
      ret.setTeamName(resultSet.getString("team_name"));
      ret.setAvgPointsPerGame(resultSet.getBigDecimal("avg_points_per_game"));
      ret.setScoringMarginPerGame(resultSet.getBigDecimal("scoring_margin_per_game"));
      ret.setNumFgAttemptsPerGame(resultSet.getBigDecimal("num_fg_attempts_per_game"));
      ret.setFgPercentage(resultSet.getBigDecimal("fg_percentage"));
      ret.setNum3pPerGame(resultSet.getBigDecimal("num_3p_per_game"));
      ret.setNum3pAttemptsPerGame(resultSet.getBigDecimal("num_3p_attempts_per_game"));
      ret.setT3pPercentage(resultSet.getBigDecimal("t3p_percentage"));
      ret.setNumFtAttemptsPerGame(resultSet.getBigDecimal("num_ft_attempts_per_game"));
      ret.setFtPercentage(resultSet.getBigDecimal("ft_percentage"));
      ret.setReboundMargin(resultSet.getBigDecimal("rebound_margin"));
      ret.setAssistsPerGame(resultSet.getBigDecimal("assists_per_game"));
      ret.setAtoRatio(resultSet.getBigDecimal("ato_ratio"));
      ret.setAvgOpponentPointsPerGame(resultSet.getBigDecimal("avg_opponent_points_per_game"));
      ret.setNumOppFgAttemptsPerGame(resultSet.getBigDecimal("num_opp_fg_attempts_per_game"));
      ret.setOppFgPercentage(resultSet.getBigDecimal("opp_fg_percentage"));
      ret.setNumOpp3pAttemptsPerGame(resultSet.getBigDecimal("num_opp_3p_attempts_per_game"));
      ret.setOpp3pPercentage(resultSet.getBigDecimal("opp_3p_percentage"));
      ret.setBlocksPerGame(resultSet.getBigDecimal("blocks_per_game"));
      ret.setStealsPerGame(resultSet.getBigDecimal("steals_per_game"));
      ret.setOppTurnoversPerGame(resultSet.getBigDecimal("opp_turnovers_per_game"));
      ret.setTurnoversPerGame(resultSet.getBigDecimal("turnovers_per_game"));
      ret.setFoulsPerGame(resultSet.getBigDecimal("fouls_per_game"));
      ret.setNumDq(resultSet.getBigDecimal("num_dq"));
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
