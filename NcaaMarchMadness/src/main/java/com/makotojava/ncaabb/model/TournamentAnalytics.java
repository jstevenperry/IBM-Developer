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
package com.makotojava.ncaabb.model;

/**
 * This class represents the view:
 * 
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
public class TournamentAnalytics implements Comparable<TournamentAnalytics> {

  private Integer year;
  private Integer minScore;
  private Integer maxScore;

  /**
   * Constructor
   */
  public TournamentAnalytics() {
    // Nothing to do
  }

  @Override
  public int compareTo(TournamentAnalytics o) {
    return getYear().compareTo(o.getYear());
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public Integer getMinScore() {
    return minScore;
  }

  public void setMinScore(Integer minScore) {
    this.minScore = minScore;
  }

  public Integer getMaxScore() {
    return maxScore;
  }

  public void setMaxScore(Integer maxScore) {
    this.maxScore = maxScore;
  }

}
