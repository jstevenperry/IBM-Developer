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

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class TournamentParticipant extends AbstractStrategy {

  private static final Logger LOG = Logger.getLogger(TournamentResult.class);

  public static final String TABLE_NAME = "tournament_participant";

  public final static String STATCAT_TOURNAMENT_PARTICIPANT = "Tournament Participants";

  public TournamentParticipant() {
    super(TournamentParticipant.STATCAT_TOURNAMENT_PARTICIPANT);
  }

  @Override
  public String generateSql(String year, List<String[]> data) {
    StringBuilder sb = new StringBuilder();
    LOG.debug("Generating SQL for stat cat => " + getStrategyName() + " and year => " + year);
    LOG.debug("There are " + data.size() + " lines of data to be processed...");
    for (String[] line : data) {
      String teamName = StringUtils.strip(line[0]);
      // Now for the SQL...
      String sql = "INSERT INTO " + /* SCHEMA_NAME + "." + */TABLE_NAME + "(" +
      // YEAR INTEGER NOT NULL,
      // TEAM_NAME VARCHAR(64) NOT NULL
          "YEAR, TEAM_NAME"
          + ") "
          + "VALUES(" +
          Integer.valueOf(year) + ", " +
          "'" + teamName + "'" +
          ");" + "\n";
      sb.append(sql);
      incrementNumberOfRowsProcessed();
    }
    return sb.toString();
  }

}
