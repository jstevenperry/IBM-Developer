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

public class ThreePointPercentageDefense extends AbstractStrategy {

  private static final Logger LOG = Logger.getLogger(ThreePointPercentageDefense.class);
  
  public static final String TABLE_NAME = "three_point_percentage_defense";

  public final static String STATCAT_THREE_POINT_PERCENTAGE_DEFENSE = "Three Pt FG Defense";

  public ThreePointPercentageDefense() {
    super(ThreePointPercentageDefense.STATCAT_THREE_POINT_PERCENTAGE_DEFENSE);
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
      int numMade = Integer.valueOf(line[5]);
      int numAtt = Integer.valueOf(line[4]);// WTF, NCAA? These are switched.. inconsistent with all the other data...
      BigDecimal numAttPg = BigDecimal.valueOf((double)numAtt/numGames).setScale(5,  RoundingMode.HALF_UP);
      BigDecimal numPg = BigDecimal.valueOf((double)numMade/numGames).setScale(5,  RoundingMode.HALF_UP);
      BigDecimal pct = BigDecimal.valueOf((double)numMade/numAtt).setScale(5,  RoundingMode.HALF_UP);
      String sql = "INSERT INTO " + /*SCHEMA_NAME + "." +*/ TABLE_NAME + "(" +
          "YEAR, TEAM_NAME, NUM_GAMES, NUM_OPP_3P_MADE, NUM_OPP_3P_ATTEMPTS, NUM_OPP_3P_PER_GAME, NUM_OPP_3P_ATTEMPTS_PER_GAME, OPP_3P_PERCENTAGE)"
          + "VALUES(" +
          Integer.valueOf(year) + "," +
          "'" + teamName + "'," +
          numGames + "," +
          numMade + "," +
          numAtt + "," +
          numPg.doubleValue() + "," +
          numAttPg.doubleValue() + "," +
          pct.doubleValue() +
          ");" + "\n"
          ;
      sb.append(sql);
      incrementNumberOfRowsProcessed();
    }
    return sb.toString();
  }

}
