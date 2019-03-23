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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Properties;

/**
 * Provides access to the network properties.
 * Every property supported by this component has a default
 * value. <strong>You should only add a property under two circumstances
 * to the properties file:</strong>
 * <ol>
 * <li>The default value will not work (e.g., base.directory has a value that will not work under any circumstances,
 * which forces you to create one).</li>
 * <li>You don't like the default value and want to override it.</li>
 * </ol>
 * Other than that, putting ANYTHING in the properties file is unnecessary.
 * 
 * @author J Steven Perry
 *
 */
public class NetworkProperties {

  /**
   * The one property that is absolutely required (if externalized)
   */
  public static final String BASE_DIRECTORY = "base.directory";
  public static final String NETWORK_PROPERTIES_FILE = "network.properties.file";

  /**
   * Private constructor.
   */
  private NetworkProperties() {
    // Can't touch this
  }

  private static final Logger log = Logger.getLogger(NetworkProperties.class);

  /**
   * The underlying PropertiesFile object
   */
  private static PropertiesFile propertiesFile;

  /**
   * The properties file
   */
  private static final String PROPERTIES_FILE_NAME = "network.properties";

  /**
   * Lazy-loads the underlying properties file.
   * 
   * @return PropertiesFile file object.
   */
  private static PropertiesFile getPropertiesFile() {
    if (propertiesFile == null) {
      propertiesFile = new PropertiesFile(PROPERTIES_FILE_NAME);
      log.info("Loaded properties file from WAR (the default behavior)");
      String propertiesFileNameFromEnvironmentVariable = System.getProperty(NETWORK_PROPERTIES_FILE);
      if (StringUtils.isNotEmpty(propertiesFileNameFromEnvironmentVariable)) {
        log.info(String.format("Override found for property (%s). Attempting to load properties file: %s", NETWORK_PROPERTIES_FILE, propertiesFileNameFromEnvironmentVariable));
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(propertiesFileNameFromEnvironmentVariable))){
          Properties properties = new Properties();
          properties.load(bufferedInputStream);
          propertiesFile = new PropertiesFile(properties);
          log.info(String.format("Base directory: '%s' (doesn't mean everything is okay, but is a good sign).", propertiesFile.getProperty(BASE_DIRECTORY)));
        } catch (IOException e) {
          String errorMessage = String.format("Error attempting to read file '%s'", propertiesFileNameFromEnvironmentVariable, e);
          log.error(errorMessage, e);
          throw new RuntimeException(errorMessage, e);
        }
        log.info(String.format("Loaded properties file: %s", propertiesFileNameFromEnvironmentVariable));
      }
    }
    // Sanity check
    String baseDirectory = propertiesFile.getProperty(BASE_DIRECTORY);
    if (baseDirectory.equalsIgnoreCase("REPLACE_WITH_YOUR_VALUE")) {
      String warningMessage = String.format("You should specify a value for %s. The default value of '.' will be used.", BASE_DIRECTORY);
      log.warn(warningMessage);
    }
    return propertiesFile;
  }

  /**
   * The base directory. Used a LOT by the application. You MUST PROVIDE A
   * VALUE IN THE PROPERTIES FILE FOR THIS PROPERTY!
   * <p>
   * This is the ONLY property like this.
   * 
   * @return String The Base directory property value.
   */
  public static String getBaseDirectory() {
    return getStringPropertyValue(BASE_DIRECTORY, ".");
  }

  public static String getDatabaseName() {
    return getStringPropertyValue("database.name", "ncaabb");
  }

  public static String getDatabaseUser() {
    return getStringPropertyValue("database.user", "sperry");
  }

  public static String getDatePrologue() {
    return getStringPropertyValue("date.prologue", "Through Games");
  }

  public static Integer getEpochBreakIterationCount() {
    return getIntegerPropertyValue("epoch.break.iteration.count", 3000);
  }

  public static String getHeaderRank() {
    return getStringPropertyValue("header.rank", "Rank");
  }

  public static Boolean getLearningRuleIsBatchMode() {
    return getBooleanPropertyValue("learning.rule.is.batch.mode", false);
  }

  public static Double getLearningRuleLearningRate() {
    return getDoublePropertyValue("learning.rule.learning.rate", 0.05);
  }

  public static Double getLearningRuleMomentumMax() {
    return getDoublePropertyValue("learning.rule.momentum.max", 0.25);
  }

  public static Double getLearningRuleMomentumMin() {
    return getDoublePropertyValue("learning.rule.momentum.min", 0.1);
  }

  public static Integer getMaxLearningIterations() {
    return getIntegerPropertyValue("max.learning.iterations", 20000);
  }

  public static Double getMaxNetworkError() {
    return getDoublePropertyValue("max.network.error", 0.0075);
  }

  public static Double getMaxNetworkErrorUptrend() {
    return getDoublePropertyValue("max.network.error.uptrend", -0.001);
  }

  public static Integer getMaxNetworkIterations() {
    return getIntegerPropertyValue("max.network.iterations", 10);
  }

  public static Double getMaxWeight() {
    return getDoublePropertyValue("max.weight", 1.0);
  }

  public static Double getMinMaxWeight() {
    return getDoublePropertyValue("min.max.weight", 0.7);
  }

  public static Double getMinMinWeight() {
    return getDoublePropertyValue("min.min.weight", -0.7);
  }

  public static Double getMinWeight() {
    return getDoublePropertyValue("min.weight", -1.0);
  }

  public static Double getMomentumDefaultValue() {
    return getDoublePropertyValue("momentum.default.value", 0.10);
  }

  public static String getNetworkDirectoryName() {
    return getStringPropertyValue("network.directory.name", "Networks");
  }

  public static String getNetworkFileBase() {
    return getStringPropertyValue("network.file.base", "NCAA-BB-MLP-Network");
  }

  public static String getNetworkFileExtension() {
    return getStringPropertyValue("network.file.extension", ".ann");
  }

  public static String getNetworksFileName() {
    return getStringPropertyValue("networks.file.name", "networks.txt");
  }

  public static Integer getNumberOfInputs() {
    return getIntegerPropertyValue("number.of.inputs", 46);
  }

  public static Integer getNumberOfOutputs() {
    return getIntegerPropertyValue("number.of.outputs", 2);
  }

  public static BigDecimal getPerformanceThreshold() {
    Integer performanceThreshold = getIntegerPropertyValue("performance.threshold", 70);
    return BigDecimal.valueOf(performanceThreshold).setScale(0, RoundingMode.HALF_UP);
  }

  public static BigDecimal getSymmetricPerformanceThreshold() {
    Integer performanceThreshold = getIntegerPropertyValue("symmetric.performance.threshold", 60);
    return BigDecimal.valueOf(performanceThreshold).setScale(0, RoundingMode.HALF_UP);
  }

  public static String getSimulationDirectoryName() {
    return getStringPropertyValue("simulation.directory.name", "Simulation");
  }

  public static String getStatcatHeader() {
    return getStringPropertyValue("statcat.header", "NCAA Men's Basketball");
  }

  public static String getStatcatPrologue() {
    return getStringPropertyValue("statcat.prologue", "Division I");
  }

  public static Boolean getRandomizeMomentum() {
    return getBooleanPropertyValue("randomize.momentum", Boolean.TRUE);
  }

  public static String getTeamMatrixFileName() {
    return getStringPropertyValue("team.matrix.file.name", "all-vs");
  }

  public static String getTrainingDataFileBase() {
    return getStringPropertyValue("training.data.file.base", "NCAA-BB-TRAINING_DATA");
  }

  public static String getTrainingDataFileExtension() {
    return getStringPropertyValue("training.data.file.extension", ".trn");
  }

  public static String getTrainingDirectoryName() {
    return getStringPropertyValue("training.directory", "TrainingData");
  }

  public static Boolean getUseBiasNeurons() {
    return getBooleanPropertyValue("use.bias.neurons", Boolean.TRUE);
  }

  // ****************************
  // * HELPER METHODS - PRIVATE *
  // ****************************

  /**
   * Returns the value found in the underlying properties file, converted
   * to a Boolean, or the specified defaultValue if the property value
   * could not be found.
   * 
   * @param propertyName
   *          The name of the property.
   * @param defaultValue
   *          If the defaultValue is <code>null</code>, then the property is
   *          considered to be <em>required</em> and if not found, a <code>RuntimeException</code>
   *          is thrown.
   * @return Boolean - the value of the specified property, converted to a
   *         Boolean.
   * @throws RuntimeException
   *           If the <code>defaultValue</code> is null, then the
   *           property is considered to be <em>required</em> and if it cannot be located in the
   *           properties file, this exception is thrown.
   */
  private static Boolean getBooleanPropertyValue(String propertyName, Boolean defaultValue) {
    Boolean ret = defaultValue;
    String propertyValue = getPropertiesFile().getProperty(propertyName);
    if (propertyValue != null) {
      ret = Boolean.valueOf(propertyValue);
    }
    if (ret == null) {
      throw new RuntimeException(
          "You must specify a value for property '" + propertyName + "' in " + PROPERTIES_FILE_NAME + "!");
    }
    return ret;
  }

  /**
   * Returns the value found in the underlying properties file, converted
   * to a Double, or the specified defaultValue if the property value
   * could not be found.
   * 
   * @param propertyName
   *          The name of the property.
   * @param defaultValue
   *          If the defaultValue is <code>null</code>, then the property is
   *          considered to be <em>required</em> and if not found, a <code>RuntimeException</code>
   *          is thrown.
   * @return Double - the value of the specified property, converted to a
   *         Double.
   * @throws NumberFormatException
   *           If the property value cannot be parsed, or is not assignable
   *           to a double precision value
   * @throws RuntimeException
   *           If the <code>defaultValue</code> is null, then the
   *           property is considered to be <em>required</em> and if it cannot be located in the
   *           properties file, this exception is thrown.
   */
  private static Double getDoublePropertyValue(String propertyName, Double defaultValue) {
    Double ret = defaultValue;
    String propertyValue = getPropertiesFile().getProperty(propertyName);
    if (propertyValue != null) {
      ret = Double.valueOf(propertyValue);
    }
    if (ret == null) {
      throw new RuntimeException(
          "You must specify a value for property '" + propertyName + "' in " + PROPERTIES_FILE_NAME + "!");
    }
    return ret;
  }

  /**
   * Returns the value found in the underlying properties file, converted
   * to an Integer, or the specified defaultValue if the property value
   * could not be found.
   * 
   * @param propertyName
   *          The name of the property.
   * @param defaultValue
   *          If the defaultValue is <code>null</code>, then the property is
   *          considered to be <em>required</em> and if not found, a <code>RuntimeException</code>
   *          is thrown.
   * @return Integer - the value of the specified property, converted to an
   *         integer.
   * @throws NumberFormatException
   *           If the property value cannot be parsed, or is not assignable
   *           to an integer value
   * @throws RuntimeException
   *           If the <code>defaultValue</code> is null, then the
   *           property is considered to be <em>required</em> and if it cannot be located in the
   *           properties file, this exception is thrown.
   */
  private static Integer getIntegerPropertyValue(String propertyName, Integer defaultValue) {
    Integer ret = defaultValue;
    String propertyValue = getPropertiesFile().getProperty(propertyName);
    if (propertyValue != null) {
      ret = Integer.valueOf(propertyValue);
    }
    if (ret == null) {
      throw new RuntimeException(
          "You must specify a value for property '" + propertyName + "' in " + PROPERTIES_FILE_NAME + "!");
    }
    return ret;
  }

  /**
   * Returns the value found in the underlying properties file,
   * or the specified <code>defaultValue</code> if the property value
   * could not be found
   * (and <code>defaultvalue</code> is not null, in which
   * case the property is <em>required</em>).
   * 
   * @param propertyName
   *          The name of the property.
   * @param defaultValue
   *          If the defaultValue is <code>null</code>, then the property is
   *          considered to be <em>required</em> and if not found, a <code>RuntimeException</code>
   *          is thrown.
   * @return String - The value of the specified property.
   * @throws RuntimeException
   *           If the <code>defaultValue</code> is null, then the
   *           property is considered to be <em>required</em> and if it cannot be located in the
   *           properties file, this exception is thrown.
   */
  private static String getStringPropertyValue(String propertyName, String defaultValue) {
    String ret = defaultValue;
    String propertyValue = getPropertiesFile().getProperty(propertyName);
    if (propertyValue != null) {
      ret = propertyValue;
    }
    if (ret == null) {
      throw new RuntimeException(
          "You must specify a value for property '" + propertyName + "' in " + PROPERTIES_FILE_NAME + "!");
    }
    return ret;
  }

}
