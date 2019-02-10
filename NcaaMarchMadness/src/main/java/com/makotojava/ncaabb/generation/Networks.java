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

/**
 * This class is used to specify the hidden layers of the networks
 * to be used. The name is probably misleading (Networks).
 * 
 * The NETWORKS constant defines several networks' hidden layers only.
 * The input and output layers are defined elsewhere.
 * 
 * 
 * 
 * @author sperry
 *
 */
public class Networks {

  private static final int[][] NETWORKS = {
      // Two hidden layers
      // { 90, 16 },
      // { 20, 10 },
      // { 23, 13 },
      // Three hidden layers
      { 90, 30, 20 },
      { 20, 30, 10 },
      { 23, 31, 11 },
      // // Four hidden layers
      { 90, 30, 120, 20 },
      { 90, 55, 20, 15 },
      // { 30, 55, 20, 15 },
      // { 23, 16, 12, 6 },
      // { 20, 35, 10, 5 },
      // { 22, 37, 13, 5 },
      // Five hidden layers
      { 90, 30, 60, 20, 15 },
      // { 35, 20, 40, 15, 10 },
      // { 30, 25, 40, 15, 10 },
      // { 33, 23, 43, 13, 11 },
      // Six hidden layers
      { 90, 35, 20, 50, 15 },
      // { 35, 45, 25, 30, 20, 10 },
      // { 25, 35, 15, 20, 10, 15 },
      // { 27, 33, 17, 21, 11, 17 },
  };

  public static int[][] getNetworks() {
    return NETWORKS;
  }

}
