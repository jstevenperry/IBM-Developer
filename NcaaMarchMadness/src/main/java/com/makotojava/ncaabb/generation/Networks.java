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
package com.makotojava.ncaabb.generation;

import com.makotojava.ncaabb.util.NetworkProperties;
import com.makotojava.ncaabb.util.NetworkUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 * This class is used to specify the hidden layers of the networks
 * to be used. The name is probably misleading (Networks).
 * <p>
 * The NETWORKS constant defines several networks' hidden layers only.
 * The input and output layers are defined elsewhere.
 *
 * @author sperry
 */
public class Networks {

  private static final Logger log = Logger.getLogger(Networks.class);

  /**
   * These are the networks that will be trained. Only the hidden layer
   * structure is shown (since the input and output layers are based on
   * the training data).
   *
   * From left (input) to right (output), the number represents the number
   * of neurons in that layer. For example, {10, 20, 10} would represent 10 neurons
   * in the first hidden layer, and 20 in the next, and 10 in the layer just
   * before the output layer for a total of 5 layers of neurons in the network.
   */
  private static final int[][] NETWORKS = {
    // Two hidden layers
    {92, 23},
    // Three hidden layers
    {23, 30, 10},
    // // Four hidden layers
    {92, 23, 12, 4},
    // Five hidden layers
    {92, 23, 30, 12, 4},
    // Six hidden layers
//      { 90, 35, 20, 50, 15 },
    // { 35, 45, 25, 30, 20, 10 },
    // { 25, 35, 15, 20, 10, 15 },
    // { 27, 33, 17, 21, 11, 17 },
  };

  /**
   * Attempt to read the networks from an external source. Failing that
   * use the NETWORKS defined above.
   *
   * The format of the network file is like this:
   *
   * NxNx...xN
   *
   * Where: N is the number of neurons in the layer, and x separates the layers.
   * Examples:
   *
   * 23x10x4 - three hidden layers: 23 neurons, 10 neurons, 4 neurons
   * 90x40x45x14 - four hidden layers: 90 neurond, 40 neurons, 45 neurons, 14 neurons
   */
  public static int[][] getNetworks() {
    int[][] ret = NETWORKS;
    String networksFileName = NetworkProperties.getNetworksFileName();
    if (StringUtils.isNotEmpty(networksFileName)) {
      try (BufferedReader bufferedReader = new BufferedReader(new FileReader(networksFileName))) {
        List<String> lines = new ArrayList<>();
        String line = bufferedReader.readLine();
        while (line != null) {
          lines.add(line);
          line = bufferedReader.readLine();
        }
        log.info(String.format("Attempting to process %d networks from file '%s'...", lines.size(), networksFileName));
        List<int[]> networks = lines.stream()
          .filter(s -> !s.startsWith("#") && StringUtils.isNotEmpty(s)) // allow for comment lines and empty in the network file, but ignore them
          .map(Networks::parseNetworkStructure)
          .collect(Collectors.toList());
        ret = new int[networks.size()][];
        // Could prolly use a stream for this but my brain hurts, punt and use a loop
        for (int aa = 0; aa < networks.size(); aa++) {
          ret[aa] = networks.get(aa);
        }
        log.info("Processing of externally defined networks complete.");
      } catch (IOException e) {
        String errorMessage = String.format("Error reading networks file '%s'", networksFileName);
        log.error(errorMessage, e);
        throw new RuntimeException(errorMessage, e);
      }
    }
    return ret;
  }

  /**
   * Parses the specified network structure into an int array.
   * See getNetworks() for a description of the structure.
   */
  protected static int[] parseNetworkStructure(String networkStructure) {
    List<Integer> structure = new ArrayList<>();
    StringTokenizer strtok = new StringTokenizer(networkStructure, "x");
    while (strtok.hasMoreTokens()) {
      try {
        int neuronsInLayer = Integer.parseInt(strtok.nextToken());
        structure.add(neuronsInLayer);
      } catch (NumberFormatException e) {
        String errorMessage = String.format("Cannot make sense out of network structure: '%s' (alpha characters in layers, maybe?)", networkStructure);
        log.error(errorMessage, e);
        throw new RuntimeException(errorMessage, e);
      }
    }
    log.info(String.format("Network layer structure: %s", NetworkUtils.generateLayerStructureString(structure)));

    return structure.stream().mapToInt(Integer::intValue).toArray();
  }

}
