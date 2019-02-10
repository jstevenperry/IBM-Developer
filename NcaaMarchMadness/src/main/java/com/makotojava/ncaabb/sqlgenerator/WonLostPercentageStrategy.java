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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.log4j.Logger;

public class WonLostPercentageStrategy extends AbstractStrategy {
  
  private static final Logger LOG = Logger.getLogger(WonLostPercentageStrategy.class);
  
  public static final String TABLE_NAME = "won_lost_percentage";

  public final static String STATCAT_WON_LOST_PERCENTAGE = "Won-Lost Percentage";
  
  public WonLostPercentageStrategy() {
    super(WonLostPercentageStrategy.STATCAT_WON_LOST_PERCENTAGE);
  }
  
  @Override
  public String generateSql(String year, List<String[]> data) {
    //
    StringBuilder sb = new StringBuilder();
    LOG.debug("Generating SQL for stat cat => " + getStrategyName() + " and year => " + year);
    LOG.debug("There are " + data.size() + " lines of data to be processed...");
    for (String[] line : data) {
      int numWins = Integer.valueOf(line[2]);
      int numLosses = Integer.valueOf(line[3]);
      int numGames = numWins + numLosses;
      BigDecimal winLossPercentage = BigDecimal.valueOf((double)numWins/numGames).setScale(5,  RoundingMode.HALF_UP);
      String sql = "INSERT INTO " + /*SCHEMA_NAME + "." +*/ TABLE_NAME + "(" +
          "YEAR, TEAM_NAME, NUM_WINS, NUM_LOSSES, WIN_PERCENTAGE)"
          + "VALUES(" +
          Integer.valueOf(year) + "," +
          "'" + line[1] + "'," +
          numWins + "," +
          numLosses + "," +
          winLossPercentage.doubleValue() +
          ");" + "\n"
          ;
      sb.append(sql);
      incrementNumberOfRowsProcessed();
    }
    return sb.toString();
  }

}