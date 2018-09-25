/*
 * Copyright 2018 Makoto Consulting Group, Inc
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

/*
 * Copyright 2018 Makoto Consulting Group, Inc
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

import java.lang.StringBuilder

private val string = "Hello, my name is Inigo Montoya."

class Ranges {
    fun demoIn() {
        println("********** Ranges.demoIn() **********")
        for (int in 0..4) {
            println("The number is: ${intArray[int]}")
        }
    }

    fun demoIn(intArray: IntArray) {
        println("********** Ranges.demoIn(IntArray) **********")
        var accum = 0
        val maxIndex = intArray.size - 1 // index is zero-based
        for (index in 0..maxIndex) {
            accum += intArray[index]
        }
        println("The total is: $accum")
    }

    fun demoInStep(stringArray: Array<String>, stepSize: Int) {
        println("********** Ranges.demoInStep($string, $stepSize) **********")
        val maxIndex = stringArray.size - 1
        val stringAccum = StringBuilder()
        for (index in 0..maxIndex step stepSize) {
            stringAccum.append(stringArray[index])
            stringAccum.append('|')
        }
        println("The stringAccum says: $stringAccum")
    }
}

class RangesUnderTheHood {
    fun demoIn() {
        println("********** RangesUnderTheHood.demoIn() **********")
        for (int in 0.rangeTo(4)) {
            println("The number is: ${intArray[int]}")
        }
    }

    fun demoIn(intArray: IntArray) {
        println("********** Ranges.demoIn(IntArray) **********")
        var accum = 0
        val maxIndex = intArray.size - 1 // index is zero-based
        for (index in 0.rangeTo(maxIndex)) {
            accum += intArray[index]
        }
        println("The total is: $accum")
    }

    fun demoInStep(stringArray: Array<String>, stepSize: Int) {
        println("********** Ranges.demoInStep($string, $stepSize) **********")
        val maxIndex = stringArray.size - 1
        val stringAccum = StringBuilder()
        for (index in 0.rangeTo(maxIndex).step(stepSize)) {
            stringAccum.append(stringArray[index])
            stringAccum.append('|')
        }
        println("The stringAccum says: $stringAccum")
    }
}

fun main(args: Array<String>) {
    val ranges = Ranges()
    ranges.demoIn()
    ranges.demoIn(intArray = intArray)
    ranges.demoInStep(stringArray = stringArray, stepSize = 2)
    ranges.demoInStep(stringArray = stringArray, stepSize = 3)

    val underTheHood = RangesUnderTheHood()
    underTheHood.demoIn()
    underTheHood.demoIn(intArray = intArray)
    underTheHood.demoInStep(stringArray = stringArray, stepSize = 2)
    underTheHood.demoInStep(stringArray = stringArray, stepSize = 3)
}