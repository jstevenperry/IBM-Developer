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
package com.makotojava.ncaabb.simulation;

import java.util.List;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.nnet.learning.BackPropagation;

/**
 * Interface implemented by any class whose job it is to run a network.
 * 
 * @author J Steven Perry
 *
 * @param <T>
 *          - The implementation class, must be a NeuralNetwork with
 *          BackPropagation learning rule.
 */
public interface NetworkRunner<T extends NeuralNetwork<BackPropagation>> {

  /**
   * Loads all of the networks to be used. It is up to the implementation
   * to decide where these networks are stored, and this method encapsulates
   * callers from that.
   * 
   * @return List<T> - a List of the networks to be used.
   */
  public List<T> loadNetworks();
  
  /**
   * Run the specified network using the specified normalized input data.
   * 
   * @param network
   *          The Network to be run
   * 
   * @param input
   *          The input data. Must be normalized or the results will
   *          be unpredictable.
   * 
   * @return The output data. The exact format depends on the network
   *         that is run.
   */
  public double[] runNetwork(T network, double[] input);
}
