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

private val string = "Hello, my name is Inigo Montoya."

/**
 * Demonstrates Range expressions
 */
fun demoIn() {
    val simpleArray: IntArray = intArrayOf(1, 4, 67, 400, 0x34)
    println("********** Ranges.demoIn() **********")
    // rangeTo()
    for (index in 0..4) {
        println("The number is: ${simpleArray[index]}")
    }
    // until()
    for (index in 0 until simpleArray.size) {
        println("The number is: ${simpleArray[index]}")
    }
}

fun demoIn(intArray: IntArray) {
    println("********** Ranges.demoIn(IntArray) **********")
    var accum = 0
    val begin = 0
    val maxIndex = intArray.size - 1 // index is zero-based
    for (index in begin..maxIndex) {
        accum += intArray[index]
    }
    println("The total is: $accum")
}

fun demoInWithStep(stringArray: Array<String>, stepSize: Int) {
    println("********** Ranges.demoInWithStep($string, $stepSize) **********")
    val maxIndex = stringArray.size - 1
    val stringAccum = StringBuilder()
    for (index in 0..maxIndex step stepSize) {
        stringAccum.append(stringArray[index])
        stringAccum.append('|')
    }
    println("The stringAccum says: $stringAccum")
}

fun demoInDownTo(intArray: IntArray, minIndex: Int, stepSize: Int) {
    println("********** Ranges.demoInDownTo(IntArray) **********")
    val maxIndex = intArray.size - 1
    for (index in maxIndex downTo minIndex step stepSize) {
        println("The int is: ${intArray[index]}")
    }
}

/**
 * Demo what's going on under the hood.
 * Comments give an intro into what's going on.
 * This is great debugger fodder, as you see in
 * the video for unit 10.
 */
class RangesUnderTheHood {
    fun demoIn() {
        val simpleArray: IntArray = intArrayOf(1, 4, 67, 400, 0x34)
        println("********** RangesUnderTheHood.demoIn() **********")
        fun rangeTo() {
            println("********** rangeTo() **********")
            // Under the hood: Int.rangeTo() => IntRange.iterator() => Iterator
            // Intermediate variables tell the tale...
            val intRange = 0.rangeTo(4)           // IntRange
            val iterator = intRange.iterator()     // Iterator
            while (iterator.hasNext()) {
                val index = iterator.next()
                println("The number is: ${simpleArray[index]}")
            }
        }

        fun until() {
            println("********** until() **********")
            // Under the hood: Int.until() => IntRange.iterator() => Iterator
            // Intermediate variables tell the tale...
            val intRange = 0.until(simpleArray.size)           // IntRange
            val iterator = intRange.iterator()     // Iterator
            while (iterator.hasNext()) {
                val index = iterator.next()
                println("The number is: ${simpleArray[index]}")
            }
        }
        // Call the local functions
        rangeTo()
        until()
    }

    fun demoIn(intArray: IntArray) {
        println("********** RangesUnderTheHood.demoIn(IntArray) **********")
        var accum = 0
        val maxIndex = intArray.size - 1 // index is zero-based
        // Under the hood: Int.rangeTo() => IntRange.iterator() => Iterator
        // Intermediate variables tell the tale...
        val intRange = 0.rangeTo(maxIndex) // IntRange
        val iterator = intRange.iterator() // Iterator
        while (iterator.hasNext()) {
            val index = iterator.next()
            accum += intArray[index]
        }
        println("The total is: $accum")
    }

    fun demoInWithStep(stringArray: Array<String>, stepSize: Int) {
        println("********** RangesUnderTheHood.demoInWithStep($string, $stepSize) **********")
        val maxIndex = stringArray.size - 1
        val stringAccum = StringBuilder()
        // Under the hood: Int.rangeTo() => IntRange.step() => IntProgression.iterator() => Iterator
        // Intermediate variables tell the tale...
        val intRange = 0.rangeTo(maxIndex)           // IntRange
        val intProgression = intRange.step(stepSize) // IntProgression
        val iterator = intProgression.iterator()     // Iterator
        while (iterator.hasNext()) {
            stringAccum.append(stringArray[iterator.next()]) // access the next index (Int)
            stringAccum.append('|')
        }
        println("The stringAccum says: $stringAccum")
    }
}

/**
 * The ubiquitous main() function, we meet again.
 */
fun main(args: Array<String>) {
    demoIn()
    demoIn(intArray = intArray)
    demoInWithStep(stringArray = stringArray, stepSize = 2)
    demoInDownTo(intArray = intArray, minIndex = 0, stepSize = 1)

    // A little unnecessary fun
    demoInWithStep(stringArray = stringArray, stepSize = 3)
    demoInDownTo(intArray = intArray, minIndex = 3, stepSize = 1)
    demoInDownTo(intArray = intArray, minIndex = 0, stepSize = 2)

    // Check out what's going on under the hood
    // I recommend you set breakpoints and walk through the code
    // and watch what is returned, and the state of the intermediate
    // objects that are used.
    val underTheHood = RangesUnderTheHood()
    underTheHood.demoIn()
    underTheHood.demoIn(intArray = intArray)
    underTheHood.demoInWithStep(stringArray = stringArray, stepSize = 2)
    underTheHood.demoInWithStep(stringArray = stringArray, stepSize = 3)
}