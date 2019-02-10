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

public class FieldGoalPercentageDefense extends AbstractStrategy {

  private static final Logger LOG = Logger.getLogger(FieldGoalPercentageDefense.class);
  
  public static final String TABLE_NAME = "field_goal_percentage_defense";

  public final static String STATCAT_FIELD_GOAL_PERCENTAGE_DEFENSE = "Field-Goal Percentage Defense";

  public FieldGoalPercentageDefense() {
    super(FieldGoalPercentageDefense.STATCAT_FIELD_GOAL_PERCENTAGE_DEFENSE);
  }

  @Override
  public String generateSql(String year, List<String[]> data) {
    //
    StringBuilder sb = new StringBuilder();
    LOG.debug("Generating SQL for stat cat => " + getStrategyName() + " and year => " + year);
    LOG.debug("There are " + data.size() + " lines of data to be processed...");
    for (String[] line : data) {
      String teamName = line[1];
      int numGames = Integer.valueOf(line[2]);
      int numFgMade = Integer.valueOf(line[4]);
      int numFgAtt = Integer.valueOf(line[5]);
      BigDecimal numFgPg = BigDecimal.valueOf((double)numFgMade/numGames).setScale(5,  RoundingMode.HALF_UP);
      BigDecimal numFgAttPg = BigDecimal.valueOf((double)numFgAtt/numGames).setScale(5,  RoundingMode.HALF_UP);
      BigDecimal fgPct = BigDecimal.valueOf((double)numFgMade/numFgAtt).setScale(5,  RoundingMode.HALF_UP);
      String sql = "INSERT INTO " + /*SCHEMA_NAME + "." +*/ TABLE_NAME + "(" +
          "YEAR, TEAM_NAME, NUM_GAMES, NUM_OPP_FG_MADE, NUM_OPP_FG_ATTEMPTS, NUM_OPP_FG_PER_GAME, NUM_OPP_FG_ATTEMPTS_PER_GAME, OPP_FG_PERCENTAGE)"
          + "VALUES(" +
          Integer.valueOf(year) + "," +
          "'" + teamName + "'," +
          numGames + "," +
          numFgMade + "," +
          numFgAtt + "," +
          numFgPg.doubleValue() + "," +
          numFgAttPg.doubleValue() + "," +
          fgPct.doubleValue() +
          ");" + "\n"
          ;
      sb.append(sql);
      incrementNumberOfRowsProcessed();
    }
    return sb.toString();
  }

}
