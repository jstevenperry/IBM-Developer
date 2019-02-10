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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.makotojava.ncaabb.util.NetworkProperties;
import com.opencsv.CSVReader;

public class SqlGenerator {

  private static final Logger log = Logger.getLogger(SqlGenerator.class);

  public static void main(String[] args) {
    // Read the specified file from arg[0]
    String filename = (args.length > 0) ? args[0] : StringUtils.EMPTY;

    if (StringUtils.isEmpty(filename)) {
      throw new IllegalArgumentException("Bad syntax. Usage:\n\t" + SqlGenerator.class.getSimpleName() + " filename");
    }

    File inputFile = new File(filename);
    try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(inputFile)))) {
      // Read the file and generate SQL from it
      String outputDirectory = StringUtils.left(inputFile.getPath(),
          StringUtils.lastIndexOf(inputFile.getPath(), File.separatorChar));
      SqlGenerator sqlGenerator = new SqlGenerator(outputDirectory);
      sqlGenerator.processFile(csvReader);
      csvReader.close();
      log.info("Done.");
    } catch (IOException e) {
      log.error("IOException occurred while reading the input file => " + inputFile, e);
    }
  }

  private String outputDirectory;

  protected String getOutputDirectory() {
    return outputDirectory;
  }

  /**
   * Constructor
   * 
   * @param outputDirectory
   *          The directory where the generated SQL files
   *          are to be placed.
   */
  public SqlGenerator(String outputDirectory) {
    this.outputDirectory = outputDirectory;
  }

  /**
   * Processes the file wrapped by the specified CSVReader object.
   * 
   * @param csvReader
   *          The CSVReader that wraps the file that we want
   *          to read.
   * 
   * @throws IOException
   */
  protected void processFile(CSVReader csvReader) throws IOException {
    //
    // Accumulate the SQL to be written out in a StringBuilder
    /// It should be more than adequate for our purposes.
    StringBuilder sql = new StringBuilder();
    //
    // Save the year to pass to writeFile()
    String year = null;
    String[] line = {};
    List<String[]> data = new ArrayList<>();
    List<String> missingStrategies = new ArrayList<>();
    int numberOfStatCategories = 0;
    while (!isDone(line)) {
      // Flush empty lines and lines that are not the first header
      // It is possible that we are out of lines
      line = flushToNextHeader(csvReader);
      if (!isDone(line)) {
        // Process the headers and the lines in this stat category section
        String header1 = StringUtils.strip(line[0]);
        log.debug("Processing new block of data:" + header1);
        // Get the next line, which will contain a specific prologue of text
        /// followed by the new statistics category.
        line = csvReader.readNext();
        String statCategory = fetchStatCat(line);
        log.debug("Stat Category ==> " + statCategory);
        numberOfStatCategories++;
        // Get the last line in the header, which contains the date.
        /// We use this to figure out the tournament year so we don't have
        /// to do something lame like pass it on the command line.
        line = csvReader.readNext();
        year = fetchYear(line);
        log.debug("For Year      ==> " + year);
        // Now fetch the next line of data, if it exists. It should, but
        /// I'm guessing these files can change without warning. If it
        /// does exist, it is the header line.
        line = flushToNextDataLine(csvReader);
        // Reuse the ArrayList
        data.clear();
        // While we are probably reading data, add those lines to the
        /// data to be processed into SQL statements.
        // Note: make sure to stop if we hit the next stat category line!
        while (isProbablyData(line)) {
          log.trace("Adding line => " + Arrays.toString(line));
          data.add(line);
          // Fetch the next line. Note: this becomes the "next" line at the
          /// bottom of the loop.
          line = csvReader.readNext();
        }
        // Now figure out which Strategy to use for this block of data
        Context context = new Context(computeStrategy(statCategory));
        if (context.hasStrategy()) {
          cleanupData(data);
          sql.append("--NEXT STATISTICAL CATEGORY FOLLOWS:\n");
          sql.append(context.executeStrategy(statCategory, year, data));
          if (StringUtils.isNotEmpty(sql)) {
          }
          Strategy strategy = context.getStrategy();
          log.info("Strategy summary: name => " + strategy.getStrategyName() + ", number of rows => "
              + strategy.getNumberOfRowsProcessed());
        } else {
          missingStrategies.add(statCategory);
        }
      }
    }
    log.debug("SQL Generated:\n" + sql);
    // Now write the SQL to an output file
    writeOutputFile(year, sql.toString());
    log.info("Of " + numberOfStatCategories + " statistics categories, there are " + missingStrategies.size()
        + " missing a Strategy.");
    // Now print an audit of missing Strategies if there are any
    if (!missingStrategies.isEmpty()) {
      for (String statCategory : missingStrategies) {
        log.warn("Missing Strategy ==> " + statCategory);
      }
    }
  }

  /**
   * Return the statistical category (if present)
   * 
   * @param lines
   * @return
   */
  protected String fetchStatCat(String[] line) {
    return StringUtils.substringAfter(line[0], NetworkProperties.getStatcatPrologue()).trim();
  }

  /**
   * Fetch the year (if present)
   * 
   * @param line
   * @return
   */
  protected String fetchYear(String[] line) {
    return StringUtils.right(StringUtils.substringAfter(line[0], NetworkProperties.getDatePrologue()).trim(), 4);
  }

  /**
   * Cleans up the data. Removes characters that will offend the SQL
   * gods (e.g., parens). Cleans up the data in place.
   * 
   * @param data
   *          The data to be cleaned up. It will be cleaned up in
   *          place.
   */
  protected void cleanupData(List<String[]> data) {
    for (String[] line : data) {
      // Now clean that sh*t up
      for (int aa = 0; aa < line.length; aa++) {
        String dataElement = line[aa];
        // Remove any left paren
        dataElement = StringUtils.remove(dataElement, '(');
        // Remove any right paren
        dataElement = StringUtils.remove(dataElement, ')');
        // Remove any single quote
        dataElement = StringUtils.remove(dataElement, '\'');
        // Remove any period
        dataElement = StringUtils.remove(dataElement, '.');
        // Remove any ampersand
        dataElement = StringUtils.remove(dataElement, '&');
        // Place cleaned data back into the String[]
        line[aa] = dataElement;
      }
    }
  }

  /**
   * Writes the specified SQL to the output directory specified on the
   * constructor call. The statCategory is used as the file name.
   * 
   * @param year
   *          The year of the data
   * 
   * @param sql
   *          The SQL to be written to the output file
   */
  protected void writeOutputFile(String year, String sql) {
    File outputFile = new File(outputDirectory + File.separatorChar + "load_season_data_" + year + ".sql");
    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile))) {
      bufferedWriter.write(sql);
      bufferedWriter.close();
    } catch (IOException e) {
      log.error(
          "IOException occurred while writing output file => " + outputDirectory + File.separatorChar
              + outputFile.getName());
      ;
    }
  }

  /**
   * Helper method. Used to flush the reader up to the next line header
   * from the current position. The result is the line header is
   * returned, or we are done with the file. At the point we call this
   * method, we better be pretty sure the next thing we will read is a
   * stat header, an empty line, or EOF.
   * 
   * @param csvReader
   *          The CSVReader that wraps the file that we want
   *          to read.
   * 
   * @return String[] - either the first line header or null, which indicates
   *         that we have run out of file.
   * 
   * @throws IOException
   */
  protected String[] flushToNextHeader(CSVReader csvReader) throws IOException {
    String[] line = csvReader.readNext();
    // Flush past any non-stat category header lines
    while (!isStatCategoryHeader(line) && !isDone(line)) {
      line = csvReader.readNext();
    }
    return line;
  }

  /**
   * Fetches the next data line from the CSVReader, flushing empty lines
   * and other non-data lines in the process. At the point we call this
   * method, we better be pretty sure the next thing we will read is a line
   * of good data, an empty line, or EOF.
   * 
   * @param csvReader
   *          The CSVReader that wraps the file that we want
   *          to read.
   * 
   * @return String[] - either the line of data or null, which indicates
   *         that we have run out of file.
   * 
   * @throws IOException
   */
  protected String[] flushToNextDataLine(CSVReader csvReader) throws IOException {
    String[] line = csvReader.readNext();
    // Flush past any non-data lines
    while (!isProbablyData(line) && !isDone(line)) {
      log.trace("Ignoring line: " + Arrays.toString(line));
      line = csvReader.readNext();
    }
    log.debug("Returning next data line:" + Arrays.toString(line));
    return line;
  }

  /**
   * When CSVReader is finished, it returns null, which is our cue that
   * the party is over. You don't have to go home, but you can't stay
   * here. That kind of thing.
   * 
   * @param line
   *          The String[] to check for done-ness.
   * 
   * @return boolean - true if we are done, false otherwise.
   */
  protected boolean isDone(String[] line) {
    // We are "done" if the String[] is null.
    boolean ret = false;
    ret = line == null;
    if (ret) {
      log.trace("EOF - Goodbye!");
    }
    return ret;
  }

  /**
   * Checks the specified line to see if it contains the first stat
   * category header line.
   * 
   * @param line
   *          The String[] to check for header-ness.
   * 
   * @return boolean - true if we have the first header line, false otherwise.
   */
  protected boolean isStatCategoryHeader(String[] line) {
    return
    // Sanity check
    !isDone(line) &&
    // Stat Category header will not be an empty line
        line.length > 0 &&
        // Stat Category header contains very specific text in all cases
        StringUtils.strip(line[0]).contains(NetworkProperties.getStatcatHeader());
  }

  /**
   * Determines whether or not the specified line is probably data.
   * That means we have not hit EOF, and the line contains more than
   * one element. There is no data line that will not contain more than
   * one element.
   * 
   * @param line
   *          The String[] to check for data-ness.
   * 
   * @return boolean - true if the line is probably data, false otherwise.
   */
  protected boolean isProbablyData(String[] line) {
    boolean ret =
        // Sanity check
        !isDone(line) &&
        // There is no data line that will contain a single element.
        /// All data lines contain at least 2 or more column values.
            line.length > 1 &&
            // All headers contain the RANK
            !line[0].contains(NetworkProperties.getHeaderRank());
    if (!ret) {
      log.debug("Line => " + Arrays.toString(line) + " **IS NOT DATA**");
    }
    return ret;
  }

  /**
   * Based on the specified statistics category, this method computes
   * and returns an appropriate strategy, or null if no appropriate
   * strategy could be computed.
   * 
   * @param statCategory
   *          The statistics category for which a strategy is
   *          to be computed.
   * 
   * @return Strategy - the concrete Strategy implementation appropriate
   *         for the specified statistics category, or null if no Strategy could
   *         be computed/inferred from the statCategory parameter.
   */
  protected Strategy computeStrategy(String statCategory) {
    Strategy ret = null;
    if (statCategory.equalsIgnoreCase(WonLostPercentageStrategy.STATCAT_WON_LOST_PERCENTAGE)) {
      ret = new WonLostPercentageStrategy();
    } else if (statCategory.equalsIgnoreCase(ScoringOffense.STATCAT_SCORING_OFFENSE)) {
      ret = new ScoringOffense();
    } else if (statCategory.equalsIgnoreCase(ScoringDefense.STATCAT_SCORING_DEFENSE)) {
      ret = new ScoringDefense();
    } else if (statCategory.equalsIgnoreCase(ScoringMargin.STATCAT_SCORING_MARGIN)) {
      ret = new ScoringMargin();
    } else if (statCategory.equalsIgnoreCase(FieldGoalPercentage.STATCAT_FIELD_GOAL_PERCENTAGE)) {
      ret = new FieldGoalPercentage();
    } else if (statCategory.equalsIgnoreCase(FieldGoalPercentageDefense.STATCAT_FIELD_GOAL_PERCENTAGE_DEFENSE)) {
      ret = new FieldGoalPercentageDefense();
    } else if (statCategory.equalsIgnoreCase(ThreePointFieldGoalsPerGame.STATCAT_THREE_POINT_FIELD_GOALS_PER_GAME)) {
      ret = new ThreePointFieldGoalsPerGame();
    } else if (statCategory.equalsIgnoreCase(ThreePointPercentage.STATCAT_THREE_POINT_PERCENTAGE)) {
      ret = new ThreePointPercentage();
    } else if (statCategory.equalsIgnoreCase(ThreePointPercentageDefense.STATCAT_THREE_POINT_PERCENTAGE_DEFENSE)) {
      ret = new ThreePointPercentageDefense();
    } else if (statCategory.equalsIgnoreCase(FreeThrowPercentage.STATCAT_FREE_THROW_PERCENTAGE)) {
      ret = new FreeThrowPercentage();
    } else if (statCategory.equalsIgnoreCase(ReboundMargin.STATCAT_REBOUND_MARGIN)) {
      ret = new ReboundMargin();
    } else if (statCategory.equalsIgnoreCase(AssistsPerGame.STATCAT_ASSISTS_PER_GAME)) {
      ret = new AssistsPerGame();
    } else if (statCategory.equalsIgnoreCase(AssistTurnoverRatio.STATCAT_ASSIST_TURNOVER_RATIO)) {
      ret = new AssistTurnoverRatio();
    } else if (statCategory.equalsIgnoreCase(BlockedShotsPerGame.STATCAT_BLOCKED_SHOTS_PER_GAME)) {
      ret = new BlockedShotsPerGame();
    } else if (statCategory.equalsIgnoreCase(StealsPerGame.STATCAT_STEALS_PER_GAME)) {
      ret = new StealsPerGame();
    } else if (statCategory.equalsIgnoreCase(TurnoversPerGame.STATCAT_TURNOVERS_PER_GAME)) {
      ret = new TurnoversPerGame();
    } else if (statCategory.equalsIgnoreCase(TurnoverMargin.STATCAT_TURNOVER_MARGIN)) {
      ret = new TurnoverMargin();
    } else if (statCategory.equalsIgnoreCase(PersonalFoulsPerGame.STATCAT_PERSONAL_FOULS_PER_GAME)) {
      ret = new PersonalFoulsPerGame();
    } else if (statCategory.equalsIgnoreCase(TournamentParticipant.STATCAT_TOURNAMENT_PARTICIPANT)) {
      ret = new TournamentParticipant();
    } else if (statCategory.equalsIgnoreCase(TournamentResult.STATCAT_TOURNAMENT_RESULTS)) {
      ret = new TournamentResult();
    } else {
      log.warn("No strategy could be inferred for statistics category ==> " + statCategory
          + ". No SQL will be generated.");
    }
    return ret;
  }

  /**
   * Executes the Strategy, passing it the required data.
   * 
   * @author sperry
   *
   */
  public class Context {
    /**
     * The underlying Strategy implementation
     */
    private Strategy _strategy;

    /**
     * Creates a new Context object, which is used to employ the
     * specified Strategy to generate SQL.
     * 
     * @param strategy
     *          The concrete Strategy implementation in use
     *          to generate SQL, or null if one could not be computed.
     */
    public Context(Strategy strategy) {
      _strategy = strategy;
    }

    /**
     * Indicates to the user of this Context object whether or not
     * there is an underlying Strategy.
     * 
     * @return boolean - true if there is an underlying Strategy,
     *         false otherwise.
     */
    public boolean hasStrategy() {
      return _strategy != null;
    }

    /**
     * Returns the underlying Strategy implementation object.
     * 
     * @return Strategy - the underlying Strategy implementation.
     */
    public Strategy getStrategy() {
      return _strategy;
    }

    /**
     * Executes the Strategy associated with this Context object,
     * that was computed elsewhere. If no Strategy was computed, then
     * this method simply returns an empty string. No harm, no foul.
     * 
     * @param statCategory
     *          The statistics category
     * 
     * @param year
     *          The year in which the tournament occurred.
     * 
     * @param data
     *          The data associated with the underlying strategy.
     * 
     * @return String - a block of SQL that can be used to INSERT the
     *         data into the DB. The data and SQL is specific to the particular
     *         Strategy in use. An empty string is returned if no underlying
     *         Strategy exists.
     */
    public String executeStrategy(String statCategory, String year, List<String[]> data) {
      String ret = StringUtils.EMPTY;
      if (_strategy != null) {
        ret = _strategy.generateSql(year, data);
      } else {
        log.warn("No strategy will be employed for statistics category ==> " + statCategory
            + ". Maybe you forgot to add one?");
      }
      return ret;
    }

  }

}