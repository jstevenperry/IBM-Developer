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
package com.makotojava.ncaabb.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.log4j.Logger;

public class StatsUtils {
  
  private static final Logger log = Logger.getLogger(StatsUtils.class);
  
  public static final int SCALE = 5;

  public static final double MIN_ALLOWABLE_VALUE = 0.00001;

  public static final double MAX_ALLOWABLE_VALUE = 0.99999;

  private StatsUtils() {
    // Can't touch this
  }
  
  public static BigDecimal normalize(BigDecimal value, BigDecimal minValue, BigDecimal maxValue) {
    BigDecimal ret;
    //
    // If the value is null, then set it to the min (nulls cause the program to crash)
    if (value == null) {
      value = minValue;
    }
    //
    ret = value.subtract(minValue).divide(maxValue.subtract(minValue), RoundingMode.HALF_UP);
    if (ret.doubleValue() >= 1.0) {
      ret = BigDecimal.valueOf(MAX_ALLOWABLE_VALUE);
    } else if (ret.doubleValue() <= 0.0) {
      ret = BigDecimal.valueOf(MIN_ALLOWABLE_VALUE);
    }
    //
    log.trace("Normalized value: " + ret + " = " + "(" + value + " - " + minValue + ") / (" + maxValue + " - " + minValue + ")");
    return ret;
  }
  
  /**
   * Some values (Opponent defensive values, for example) are more meaningful the lower
   * they are, so we need to invert those values to put them on the same footing as the
   * values where bigger is better, so that bigger-is-better works for all numbers
   * 
   * @param value
   * @param minValue
   * @param maxValue
   * @return
   */
  public static BigDecimal normalizeInverted(BigDecimal value, BigDecimal minValue, BigDecimal maxValue) {
    BigDecimal ret;
    BigDecimal normalizedValue = normalize(value, minValue, maxValue);
    BigDecimal one = BigDecimal.ONE.setScale(normalizedValue.scale(), RoundingMode.HALF_UP);
    // Return 1 - normalizedValue
    ret = one.subtract(normalizedValue);
    log.trace("Returning Inverted value: " + ret);
    return ret;
  }
  
}
