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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class MlpNetworkTrainerTest {

  @Test
  public void testComputeYearsToTrain() {
    //
    String[] args = {
        "2010", "2011", "2012", "2013,2014"
    };
    Integer[] expectedYearsToTrain = {
        2010, 2011, 2012
    };
    Integer[] actualYearsToTrain = MlpNetworkTrainer.computeYearsToTrain(args);
    assertArrayEquals(expectedYearsToTrain, actualYearsToTrain);
  }

  @Test
  public void testComputeYearsToSimulate() {
    //
    String[] args = {
        "2010", "2011", "2012", "2013,2014"
    };
    Integer[] expectedYearsToSimulate = {
        2013, 2014
    };
    Integer[] actualYearsToSimulate = MlpNetworkTrainer.computeYearsToSimulate(args);
    assertArrayEquals(expectedYearsToSimulate, actualYearsToSimulate);
  }

}
