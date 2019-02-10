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

import com.makotojava.ncaabb.model.TournamentResult;

/**
 * TournamentResultDao interface implementation specifically for Spring.
 * 
 * @author J Steven Perry
 *
 */
public class TournamentResultJdbcDao extends JdbcDaoSupport implements TournamentResultDao {
  
  private static final Logger log = Logger.getLogger(TournamentResultJdbcDao.class);
  
  private static final String TABLE_NAME = "tournament_result";

  public TournamentResultJdbcDao(DataSource dataSource) {
    this.setDataSource(dataSource);
  }

  @Override
  public List<TournamentResult> fetchAllByYear(Integer year) {
    String sql = "SELECT t.* FROM " + TABLE_NAME + " t WHERE t.year = ? ORDER BY t.game_date";
    Object[] args = { year };
    // Run the query
    List<TournamentResult> results = getJdbcTemplate().query(sql, new TournamentResultRowMapper(), args);
    return results;
  }
  
  /**
   * Maps the following table:
                                      Table "public.tournament_result"
      Column       |         Type          |                           Modifiers                            
-------------------+-----------------------+----------------------------------------------------------------
 id                | integer               | not null default nextval('tournament_result_id_seq'::regclass)
 year              | integer               | not null
 game_date         | date                  | not null
 winning_team_name | character varying(64) | not null
 winning_score     | integer               | not null
 losing_team_name  | character varying(64) | not null
 losing_score      | integer               | not null
Indexes:
    "tournament_result_pkey" PRIMARY KEY, btree (id)
   * @author sperry
   *
   */
  public class TournamentResultRowMapper implements RowMapper<TournamentResult> {

    @Override
    public TournamentResult mapRow(ResultSet resultSet, int rowNum) throws SQLException {
      TournamentResult ret = new TournamentResult();
      //
      ret.setId(resultSet.getInt("id"));
      ret.setYear(resultSet.getInt("year"));
      ret.setGameDate(resultSet.getDate("game_date"));
      ret.setWinningTeamName(resultSet.getString("winning_team_name"));
      ret.setWinningScore(resultSet.getInt("winning_score"));
      ret.setLosingTeamName(resultSet.getString("losing_team_name"));
      ret.setLosingScore(resultSet.getInt("losing_score"));
      //
      if (log.isTraceEnabled()) {
        log.trace("Mapped row number " + rowNum + ", Object: " + ReflectionToStringBuilder.toString(ret));
      }
      //
      return ret;
    }
    
  }

}
