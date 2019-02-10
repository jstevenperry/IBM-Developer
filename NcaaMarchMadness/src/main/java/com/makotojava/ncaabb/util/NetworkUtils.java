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

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.nnet.learning.BackPropagation;

/**
 * A set of utilities that keeps all the code together for commonly performed
 * network-specific stuff.
 * 
 * @author J Steven Perry
 *
 */
public class NetworkUtils {

  private static final Logger log = Logger.getLogger(NetworkUtils.class);

  /**
   * Validates the year. If not between the specified range, an exception is thrown,
   * terminating the run.
   * 
   * Note: as you add years to the DB, you will need to modify this method accordingly.
   * 
   * TODO: Add a TournamentParticipantDao.fetchAllYears() method to return this list
   * so it is dynamic.
   * 
   * @param year
   *          The year to validate.
   * 
   * @throws RuntimeException
   *           - if the year to validate is outside the supported range.
   */
  public static void validateYear(Integer year) {
    if (year < 2010 || year > 2017) {
      throw new RuntimeException("Invalid year: " + year + " (must be between 2011 and 2017, inclusive)");
    }
  }

  /**
   * Returns a NxNxNxN style string showing the layer structure
   * of the specified network.
   * 
   * @param network
   * @return
   */
  public static String getNetworkStructure(NeuralNetwork<BackPropagation> network) {
    StringBuilder sb = new StringBuilder();
    //
    // First the inputs
    if (network != null) {
      sb.append(network.getInputsCount());
      //
      // Now for the hidden layers
      for (Layer layer : network.getLayers()) {
        sb.append("x");
        sb.append(layer.getNeuronsCount());
      }
      //
      // Finally, the outputs
      sb.append("x");
      sb.append(network.getOutputsCount());
    }
    return sb.toString();
  }

  /**
   * Loads all networks from the specified directory and returns them in a List.
   * 
   * @param networkArrayDirectory
   * @return
   */
  public static <T extends NeuralNetwork<BackPropagation>> List<T> loadNetworks(String networkArrayDirectory) {
    List<T> ret = new ArrayList<>();
    //
    // Load all of the networks in the specified directory
    File arrayDirectory = new File(networkArrayDirectory);
    if (arrayDirectory.exists()) {
      File[] networkFiles = arrayDirectory.listFiles(new FileFilter() {
        @Override
        public boolean accept(File pathname) {
          //
          // Only accept files that are neural networks
          return StringUtils.endsWithIgnoreCase(pathname.getName(), ".ann");
        }
      });
      log.info("Found " + networkFiles.length + " networks in directory '" + arrayDirectory.getPath() + "'...");
      for (File networkFile : networkFiles) {
        try {
          log.info("Loading network file '" + networkFile.getName() + "'...");
          @SuppressWarnings("unchecked")
          T network = (T) NeuralNetwork.createFromFile(networkFile);
          ret.add(network);
        } catch (Exception e) {
          String message = "Could not load file '" + networkFile.getName() + "'. Skipping...";
          log.warn(message, e);
        }
      }
    } else {
      String message = "** ERROR: directory '" + networkArrayDirectory
          + "' does not appear to exist. Check the name and try again.";
      log.error(message);
      throw new RuntimeException(message);
    }
    return ret;
  }

  /**
   * Runs the specified network using the Neuroph API.
   * 
   * @param network
   * @param input
   * @return
   */
  public static <T extends NeuralNetwork<BackPropagation>> double[] runNetwork(T network, double[] input) {
    double[] ret;
    network.setInput(input);
    network.calculate();
    // Return value is the network's output
    ret = network.getOutput();
    if (log.isTraceEnabled()) {
      log.trace("Input : " + Arrays.toString(input));
      log.trace("Output: " + Arrays.toString(ret));
    }
    return ret;
  }

  /**
   * Computes the training data file name based on the year and the NetworkProperties settings.
   * Provides consistency across the application.
   * 
   * @param year
   * @return
   */
  public static String computeTrainingDataFileName(Integer year) {
    String filename =
        NetworkProperties.getBaseDirectory() + File.separator + NetworkProperties.getTrainingDirectoryName()
            + File.separator +
            NetworkProperties.getTrainingDataFileBase() + "-" + year + NetworkProperties.getTrainingDataFileExtension();
    return filename;
  }

  /**
   * Fetches the name of the network directory using NetworkProperties settings
   * and returns that name to the caller.
   * 
   * @return
   */
  public static String fetchNetworkDirectoryAndCreateIfNecessary() {
    //
    // Compute the directory name from NetworkProperties settings
    String directoryName = NetworkProperties.getBaseDirectory() + File.separator
        + NetworkProperties.getNetworkDirectoryName();
    //
    // If necessary, create the directory
    createDirectoryIfNecessary(directoryName);
    //
    // Return the directory name to the caller
    return directoryName;
  }

  /**
   * Fetches the name of the training data directory using NetworkProperties settings
   * and returns that name to the caller.
   * 
   * @return
   */
  public static String fetchTrainingDirectoryAndCreateIfNecessary() {
    //
    // Compute the directory name from NetworkProperties settings
    String directoryName = NetworkProperties.getBaseDirectory() + File.separator
        + NetworkProperties.getTrainingDirectoryName();
    //
    // If necessary, create the directory
    createDirectoryIfNecessary(directoryName);
    //
    // Return the directory name to the caller
    return directoryName;
  }

  /**
   * Fetches the name of the simulation directory using NetworkProperties settings
   * and returns that name to the caller.
   * 
   * @return
   */
  public static String fetchSimulationDirectoryAndCreateIfNecessary() {
    //
    // Compute the directory name from NetworkProperties settings
    String directoryName = NetworkProperties.getBaseDirectory() + File.separator
        + NetworkProperties.getSimulationDirectoryName();
    //
    // If necessary, create the directory
    createDirectoryIfNecessary(directoryName);
    //
    // Return the directory name to the caller
    return directoryName;
  }

  /**
   * Helper method to create a directory. Insulates the caller from the details
   * of the Java API used to do this.
   * 
   * @param directoryName
   */
  private static void createDirectoryIfNecessary(String directoryName) {
    try {
      File directory = new File(directoryName);
      if (!directory.exists()) {
        log.warn("Directory '" + directoryName + "' does not exist. Creating it now...");
        // Create the directory.
        Path networkDirectoryPath = Files.createDirectory(Paths.get(directoryName));
        log.warn("Network directory '" + networkDirectoryPath.toString() + "' created.");
      }
    } catch (IOException e) {
      String message = "I/O Exception occurred while creating directory '" + directoryName + "'. Cannot continue!";
      log.error(message, e);
      throw new RuntimeException(message, e);
    }
  }

  /**
   * Returns a NxNxNxN style string showing the layer structure
   * of a network that would have the specified layer structure.
   * The 0th element contains the number of neurons in the input
   * layer. The (<code>neuronLayerDescriptor</code>.size()-1)th element
   * contains the number of neurons in the output layer.
   * 
   * @param neuronLayerDescriptor
   * @return
   */
  public static String generateLayerStructureString(List<Integer> neuronLayerDescriptor) {
    StringBuilder sb = new StringBuilder();
    int aa = 0;
    for (Integer neuronsCount : neuronLayerDescriptor) {
      if (aa++ > 0) {
        sb.append("x");
      }
      sb.append(Integer.toString(neuronsCount));
    }
    return sb.toString();
  }

}
