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

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public abstract class AbstractStrategy implements Strategy {
  
  private String strategyName;
  private int numberOfRowsProcessed;

  public AbstractStrategy(String strategyName) {
    setStrategyName(strategyName);
  }
  
  @Override
  public String getStrategyName() {
    return strategyName;
  }
  
  private void setStrategyName(String value) {
    if (StringUtils.isEmpty(value)) {
      throw new IllegalArgumentException("Strategy Name cannot be empty or null!");
    } else {
      strategyName = value;
    }
  }

  @Override
  public int getNumberOfRowsProcessed() {
    return numberOfRowsProcessed;
  }
  
  protected void incrementNumberOfRowsProcessed() {
    numberOfRowsProcessed++;
  }
  
  protected Date convertStringToDate(String dateAsString) {
    return convertStringToDate(dateAsString, "M/d/yy");
  }

  protected Date convertStringToDate(String dateAsString, String pattern) {
    Date ret = null;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
    LocalDate localDate = LocalDate.parse(dateAsString, dtf);
    
    ret = Date.from(localDate.atStartOfDay(ZoneOffset.systemDefault()).toInstant());
    
    return ret;
  }

}
