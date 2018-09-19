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
    floatExamples()
}

/**
 * Run the example to demonstrate
 */
fun floatExamples() {
    // Declare Float variables
    var floatMin: Float = Float.MIN_VALUE
    var floatMax: Float = Float.MAX_VALUE
    println("The value of floatMin is: $floatMin")
    println("The value of floatMax is $floatMax")
    println("The value of floatMax is $floatMax")

    // Declare Float variable and initialize to a literal value
    var float: Float = 1000000000.0f
    println("The value of float(1000000000.0f) is: $float")

    float = 1_000_000_000.0f
    println("The value of float(1_000_000_000.0f) is: $float")

    // Negate, with no loss in precision
    float = -Float.MAX_VALUE;
    println("The value of float(-Float.MAX_VALUE) is: $float")
}
