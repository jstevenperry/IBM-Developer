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

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.makotojava.ncaabb.model.TournamentAnalytics;

/**
 * TournamentAnalyticsDao interface implementation specifically for Spring.
 * 
 * @author J Steven Perry
 *
 */
public class TournamentAnalyticsJdbcDao extends JdbcDaoSupport implements TournamentAnalyticsDao {

  private static final Logger log = Logger.getLogger(SeasonAnalyticsJdbcDao.class);

  private static final String TABLE_NAME = "v_tournament_analytics";

  public TournamentAnalyticsJdbcDao(DataSource dataSource) {
    this.setDataSource(dataSource);
  }

  @Override
  public TournamentAnalytics fetchByYear(Integer year) {
    TournamentAnalytics ret = null;
    //
    String sql = "SELECT * FROM " + TABLE_NAME + " WHERE year = ?";
    Object[] args = { year };
    //
    List<TournamentAnalytics> results = getJdbcTemplate().query(sql, args, new TournamentAnalyticsRowMapper());
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
   * View "public.v_tournament_analytics"
   * Column | Type | Modifiers
   * -----------+---------+-----------
   * year | integer |
   * min_score | integer |
   * max_score | integer |
   * 
   * @author sperry
   *
   */
  public class TournamentAnalyticsRowMapper implements RowMapper<TournamentAnalytics> {

    @Override
    public TournamentAnalytics mapRow(ResultSet resultSet, int rowNum) throws SQLException {
      TournamentAnalytics ret = new TournamentAnalytics();

      ret.setYear(resultSet.getInt("year"));
      ret.setMinScore(resultSet.getInt("min_score"));
      ret.setMaxScore(resultSet.getInt("max_score"));

      return ret;
    }

  }

}
