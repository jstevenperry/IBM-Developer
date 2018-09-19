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
    doubleExamples()
}

/**
 * Run the example to demonstrate
 */
fun doubleExamples() {
    // Declare Double variables
    val doubleMin = Double.MIN_VALUE
    val doubleMax = Double.MAX_VALUE
    println("The value of doubleMin is: $doubleMin")
    println("The value of doubleMax is: $doubleMax")

    // Declare Double variable and initialize to a literal value
    var double: Double = 1000000000000.0001
    println("The value of double(1000000000000.0001) is: $double")

    double = 1.0e22
    println("The value of double(1.0e22) is: $double")

    // Negate, with no loss in precision
    double = -Double.MAX_VALUE;
    println("The value of double(-Double.MAX_VALUE) is: $double")

}
