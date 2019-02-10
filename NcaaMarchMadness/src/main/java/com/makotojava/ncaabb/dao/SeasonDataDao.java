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

import java.util.List;

import org.springframework.stereotype.Component;

import com.makotojava.ncaabb.model.SeasonData;

/**
 * Data Access Object interface for SeasonData model data.
 * 
 * @author J Steven Perry
 *
 */
@Component
public interface SeasonDataDao {

  /**
   * Fetch all {@link SeasonData} objects by the specified <code>year</code>
   * and return a <code>List<SeasonData></code>. The <code>List</code>
   * will be empty if no {@link SeasonData} objects could be found for
   * the specified year (which probably means you forgot to load the
   * data into the DB, shame, shame, shame).
   * 
   * @param year
   *          The year for which {@link SeasonData} objects are to be
   *          fetched.
   * @return <code>List<SeasonData></code> - the <code>List</code> of
   *         {@link SeasonData} objects if they could be located, or an empty list
   *         if not.
   */
  public List<SeasonData> fetchAllByYear(Integer year);
  
  /**
   * Fetch the {@link SeasonData} object associated with the specified
   * <code>year</code> and <code>teamName</code>. This combination is
   * guaranteed to be unique in the system.
   * 
   * @param year
   *          The year for which the {@link SeasonData} object is to be
   *          fetched.
   * @param teamName
   *          The team name for which the {@link SeasonData} object
   *          is to be fetched.
   * @return {@link SeasonData} - The {@link SeasonData} object associated
   *         with the unique combination of <code>year</code> and <code>teamName</code>
   *         or <code>null</code> if no such object could be found.
   */
  public SeasonData fetchByYearAndTeamName(Integer year, String teamName);

}
