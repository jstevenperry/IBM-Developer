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

/**
 * The main function that drives the program
 */
fun main(args: Array<String>) {
    runLongExample()
}

/**
 * Run the example to demonstrate
 */
fun runLongExample() {
    // Declare Long variables
    val longMin: Long = Long.MIN_VALUE
    val longMax: Long = Long.MAX_VALUE
    println("The value of longMin is: $longMin")
    println("The value of longMax is: $longMax")

    // Declare Long variable and initialize to a literal value
    var long: Long = 1000000000000L
    println("The value of long(1000000000000L) is: $long")

    long = 1_000_000_000_000L
    println("The value of long(1_000_000_000_000L) is: $long")

    long = 0xE8D4A51000
    println("The value of long(0xE8D4A51000) is: $long")

    long = 0xE8_D4_A5_10_00
    println("The value of long(0xE8_D4_A5_10_00) is: $long")

    long = 0b1110_1000_1101_0100_1010_0101_0001_0000_0000_0000
    println("The value of long(0b1110_1000_1101_0100_1010_0101_0001_0000_0000_0000) is: $long")

}
