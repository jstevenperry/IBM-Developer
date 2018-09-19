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
    runShortExample()
}

/**
 * Run the example to demonstrate
 */
fun runShortExample() {
    // Declare Short variables
    val shortMin: Short = Short.MIN_VALUE
    val shortMax: Short = Short.MAX_VALUE
    println("The value of shortMin is: $shortMin")
    println("The value of shortMax is: $shortMax")

    // Declare Short variable and initialize to a literal value
    var short: Short = 1000
    println("The value of short(1000) is: $short")

    short = 1_000
    println("The value of short(1_000) is: $short")

    short = 0x3e8
    println("The value of short(0x3e8) is: $short")

    short = 0b001111101000
    println("The value of short(0b001111101000) is: $short")
}


