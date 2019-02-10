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
package com.makotojava.ncaabb.sqlgenerator;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class TournamentResult extends AbstractStrategy {

  private static final Logger LOG = Logger.getLogger(TournamentResult.class);
  
  public static final String TABLE_NAME = "tournament_result";

  // CONSTANTS for Statistics Categories. We use these to determine which
  /// SQL Generation strategy to employ, which will vary for each type
  /// of data block (i.e., different column values).
  public final static String STATCAT_TOURNAMENT_RESULTS = "Tournament Results";
  
  public TournamentResult() {
    super(TournamentResult.STATCAT_TOURNAMENT_RESULTS);
  }

  @Override
  public String generateSql(String year, List<String[]> data) {
    StringBuilder sb = new StringBuilder();
    LOG.debug("Generating SQL for stat cat => " + getStrategyName() + " and year => " + year);
    LOG.debug("There are " + data.size() + " lines of data to be processed...");
    for (String[] line : data) {
      Date gameDate = convertStringToDate(line[0]);
      String winningTeamName = StringUtils.strip(line[1]);
      int winningTeamScore = Integer.valueOf(StringUtils.strip(line[2]));
      String losingTeamName = StringUtils.strip(line[3]);
      int losingTeamScore = Integer.valueOf(StringUtils.strip(line[4]));
      // Now for the SQL...
      String sql = "INSERT INTO " + /*SCHEMA_NAME + "." + */TABLE_NAME + "(" +
//          YEAR INTEGER NOT NULL,
//          GAME_DATE DATE NOT NULL,
//          WINNING_TEAM_NAME VARCHAR(64) NOT NULL,
//          WINNING_SCORE INTEGER NOT NULL,
//          LOSING_TEAM_NAME VARCHAR(64) NOT NULL,
//          LOSING_SCORE INTEGER NOT NULL
          "YEAR, GAME_DATE, WINNING_TEAM_NAME, WINNING_SCORE, LOSING_TEAM_NAME, LOSING_SCORE"
          + ")"
          + "VALUES(" +
          Integer.valueOf(year) + "," +
          "'" + gameDate.toString() + "'," +
          "'" + winningTeamName + "'," +
          Integer.valueOf(winningTeamScore) + "," +
          "'" + losingTeamName + "'," +
          Integer.valueOf(losingTeamScore) +
          ");" + "\n"
          ;
      sb.append(sql);
      incrementNumberOfRowsProcessed();
    }
    return sb.toString();
  }

}
