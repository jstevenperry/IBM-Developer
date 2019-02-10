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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.opencsv.CSVReader;

public class TournamentParticipantSqlGenerator extends SqlGenerator {

  private static final Logger log = Logger.getLogger(TournamentParticipantSqlGenerator.class);

  private Integer year;

  private Integer getYear() {
    if (year == null) {
      throw new RuntimeException("Configuration Error: Year not found: cannot continue!");
    }
    return year;
  }

  private void setYear(Integer year) {
    this.year = year;
  }

  public TournamentParticipantSqlGenerator(String outputDirectory, String year) {
    super(outputDirectory);
    setYear(Integer.valueOf(year));
  }

  public static void main(String[] args) {
    // Read the specified file from arg[0]
    String filename = (args.length > 0) ? args[0] : StringUtils.EMPTY;
    String year = (args.length > 1) ? args[1] : StringUtils.EMPTY;

    if (StringUtils.isEmpty(filename) || StringUtils.isEmpty(year)) {
      throw new IllegalArgumentException("Bad syntax. Usage:\n\t" + SqlGenerator.class.getSimpleName() + " filename");
    }

    File inputFile = new File(filename);
    try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(inputFile)))) {
      // Read the file and generate SQL from it
      String outputDirectory = StringUtils.left(inputFile.getPath(),
          StringUtils.lastIndexOf(inputFile.getPath(), File.separatorChar));
      TournamentParticipantSqlGenerator sqlGenerator = new TournamentParticipantSqlGenerator(outputDirectory, year);
      sqlGenerator.processFile(csvReader);
      csvReader.close();
      log.info("Done.");
    } catch (IOException e) {
      log.error("IOException occurred while reading the input file => " + inputFile, e);
    }
  }

  @Override
  protected boolean isProbablyData(String[] line) {
    return line != null && line.length > 0 && StringUtils.isNotBlank(line[0]);
  }

  @Override
  protected String fetchStatCat(String[] line) {
    return TournamentParticipant.STATCAT_TOURNAMENT_PARTICIPANT;
  }

  @Override
  protected String fetchYear(String[] line) {
    return getYear().toString();
  }

  @Override
  protected boolean isStatCategoryHeader(String[] line) {
    return true;
  }

  @Override
  protected void writeOutputFile(String year, String sql) {
    File outputFile = new File(
        getOutputDirectory() + File.separatorChar + "load_tournament_participants-" + getYear().toString() + ".sql");
    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile))) {
      bufferedWriter.write(sql);
      bufferedWriter.close();
    } catch (IOException e) {
      log.error(
          "IOException occurred while writing output file => " + getOutputDirectory() + File.separatorChar
              + outputFile.getName());
      ;
    }

  }

}
