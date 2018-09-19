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
    intExamples()
}

/**
 * Run the example to demonstrate
 */
fun intExamples() {
    // Declare Int variables
    println(Int.MIN_VALUE)
    println(Int.MAX_VALUE)

    // Declare Int variable and initialize to a literal value
    var int: Int = 1000000
    println("The value of int(1000000) is: $int")

    int = 1_000_000
    println("The value of int(1_000_000) is: $int")

    int = 0xF4240
    println("The value of int(0xF4240) is: $int")

    int = 0b11110100001001000000
    println("The value of int(0b11110100001001000000) is: $int")
}
