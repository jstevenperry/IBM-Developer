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
    runByteExample()
}

/**
 * Run the example to demonstrate
 */
fun runByteExample() {
    // Declare Byte variables
    val byteMin: Byte = Byte.MIN_VALUE
    val byteMax: Byte = Byte.MAX_VALUE
    println("The value of byteMin is: $byteMin")
    println("The value of byteMax is: $byteMax")

    // Declare Byte variable and initialize to a literal value
    var byte : Byte = 100
    println("The value of byte(100) is: $byte")

    byte = 0x64
    println("The value of byte(0x64) is: $byte")

    byte = 0b01100100
    println("The value of byte(0b01100100) is: $byte")
}