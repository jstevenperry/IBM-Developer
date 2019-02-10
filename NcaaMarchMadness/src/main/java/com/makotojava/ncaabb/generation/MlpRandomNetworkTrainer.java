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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.makotojava.ncaabb.springconfig.ApplicationConfig;
import com.makotojava.ncaabb.util.NetworkProperties;

/**
 * Just a little experiment. Trying to figure out the perfect
 * number of hidden layers. Let nature be the guide. Randomize
 * the mutations until something works better than what we
 * already have.
 * 
 * There are general guidelines, but they can always be
 * changed. See {@link #randomizeNetwork()} comments for more info.
 * 
 * ***************************
 * **** WARNING ****
 * **** EXPERIMENTAL CODE ****
 * **** USE AT YOUR OWN ****
 * **** RISK ****
 * ***************************
 * 
 * @author J Steven Perry
 *
 */
public class MlpRandomNetworkTrainer extends MlpNetworkTrainer {

  private static final Logger log = Logger.getLogger(MlpRandomNetworkTrainer.class);

  private static final int MAX_RANDOM_NETWORKS = 5;
  private static final int MAX_HIDDEN_LAYERS = 4;

  public MlpRandomNetworkTrainer(ApplicationContext applicationContext) {
    super(applicationContext);
  }

  /**
   * The main entry point for the application.
   * 
   * @param args
   */
  public static void main(String[] args) {
    if (args.length < 2) {
      usage();
      System.exit(-1);
    }
    //
    // Create the network generator
    MlpRandomNetworkTrainer generator = new MlpRandomNetworkTrainer(
        new AnnotationConfigApplicationContext(ApplicationConfig.class));
    generator.go(args);
  }

  @Override
  protected List<List<Integer>> setupNetworksToTry() {
    List<List<Integer>> ret = new ArrayList<>();
    log.info("*** RANDOMIZING NETWORKS ***");
    for (int aa = 0; aa < MAX_RANDOM_NETWORKS; aa++) {
      ret.add(randomizeNetwork());
    }
    return ret;
  }

  /**
   * Randomize, well, pretty much everything about the hidden layers,
   * including the number of hidden layers, and the number of neurons
   * in each layer.
   * 
   * @return List<Integer> The network layer topology.
   */
  private List<Integer> randomizeNetwork() {
    List<Integer> ret = new ArrayList<>();
    log.info("Creating random network...");
    //
    // The number of inputs is fixed, as is the number of outputs
    // (based on the training data)
    int numberOfInputs = NetworkProperties.getNumberOfInputs();
    log.debug("Number of inputs : " + numberOfInputs);
    int numberOfOutputs = NetworkProperties.getNumberOfOutputs();
    log.debug("Number of outputs : " + numberOfOutputs);
    //
    // Create random hidden layers
    // TODO: Use a ANN to get this right. Hardwire for now.
    Random randHiddenLayers = new Random();
    int numberOfHiddenLayers = 0;
    //
    // Always want at least (numberOfOutputs) hidden layers
    while (numberOfHiddenLayers < numberOfOutputs) {
      numberOfHiddenLayers = randHiddenLayers.nextInt(MAX_HIDDEN_LAYERS);
    }
    log.debug("Network will have " + numberOfHiddenLayers + " hidden layers");
    int[] hiddenNeurons = new int[numberOfHiddenLayers];
    for (int aa = 0; aa < numberOfHiddenLayers; aa++) {
      Random randNeurons = new Random();
      int numberOfNeuronsInThisLayer = 0;
      if (aa == 0) {
        //
        // The minimum number of neurons in the first hidden layer needs to be
        /// at least 1/3 the number of inputs
        while (numberOfNeuronsInThisLayer < numberOfInputs / 3) {
          numberOfNeuronsInThisLayer = randNeurons.nextInt(numberOfInputs / 2) + numberOfOutputs;
        }
      } else if (aa % 2 != 0) {
        //
        // Every odd layer has up to twice as many neurons in the layer before it
        int numberOfNeuronsInPreviousLayer = hiddenNeurons[aa - 1];
        while (numberOfNeuronsInThisLayer < (numberOfOutputs * 2)) {
          numberOfNeuronsInThisLayer = randNeurons.nextInt(numberOfNeuronsInPreviousLayer * 2);
        }
      } else {
        // Even layers must have at least (numberOfInputs / 2 * loop index) neurons
        while (numberOfNeuronsInThisLayer < (numberOfInputs / (2 * aa))) {
          numberOfNeuronsInThisLayer = randNeurons.nextInt(numberOfInputs / aa) + numberOfOutputs;
        }
      }
      hiddenNeurons[aa] = numberOfNeuronsInThisLayer;
      log.debug("Number of neurons : " + hiddenNeurons[aa] + " (hidden layer #" + (aa + 1) + ")");
    }
    //
    ret = setupNeuronLayers(numberOfInputs, numberOfOutputs, hiddenNeurons);
    return ret;
  }

}
