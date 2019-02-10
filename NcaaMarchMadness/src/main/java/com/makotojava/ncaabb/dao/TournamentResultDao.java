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

import com.makotojava.ncaabb.model.TournamentResult;

/**
 * Data Access Object interface for {@link TournamentResult} model data.
 * 
 * @author J Steven Perry
 *
 */
public interface TournamentResultDao {
  
  /**
   * Fetch all {@link TournamentResult} objects by the specified <code>year</code>
   * and return a <code>List<TournamentResult></code>. The <code>List</code>
   * will be empty if no {@link TournamentResult} objects could be found for
   * the specified year (which probably means you forgot to load the
   * data into the DB, shame, shame, shame).
   * 
   * @param year
   *          The year for which {@link TournamentResult} objects are to be
   *          fetched.
   * @return <code>List<TournamentResult></code> - the <code>List</code> of
   *         {@link TournamentResult} objects if they could be located, or an empty list
   *         if not.
   */
  public List<TournamentResult> fetchAllByYear(Integer year);
  
}
