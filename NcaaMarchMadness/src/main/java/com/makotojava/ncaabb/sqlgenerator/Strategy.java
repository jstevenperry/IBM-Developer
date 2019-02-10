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

/**
 * Strategy pattern interface.
 * 
 * See: https://en.wikipedia.org/wiki/Strategy_pattern
 * 
 * @author sperry
 *
 */
public interface Strategy {
  /**
   * Generates SQL for the Statistics Category associated with
   * the Strategy implementation.
   * 
   * @param year
   *          The tournament year the SQL is to be generated for.
   * 
   * @param data
   *          The data to be inserted via SQL.
   * 
   * @return String - the SQL INSERT statements to massage the specified
   *         data into the DB.
   */
  String generateSql(String year, List<String[]> data);

  /**
   * Returns the name of the Strategy implementation.
   * 
   * @return String - the name of this Strategy implementation.
   */
  String getStrategyName();

  /**
   * Returns the number of rows processed by this Strategy.
   * 
   * @return int - the number of rows processed
   */
  int getNumberOfRowsProcessed();

}