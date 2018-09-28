/*
 *    Copyright 2018 Makoto Consulting Group Inc
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

package com.makotogo.learn.kotlin

import createChar
import createEmployee
import createFloat

/**
 * Create and return a FloatArray of five elements.
 * Each element is based on a random number between 0 and 100
 */
fun createFloatArray() = floatArrayOf(
        createFloat(),
        createFloat(),
        createFloat(),
        createFloat(),
        createFloat()
)

/**
 * Create and return a CharArray of five elements.
 * Each element is a printable ASCII character
 */
fun createCharArray() = charArrayOf(
        createChar(),
        createChar(),
        createChar(),
        createChar(),
        createChar()
)

/**
 * Create and return an Array<Employee> of five elements.
 * The Employee has attributes that are randomly generated.
 */
fun createEmployeeArray() = Array(5) { createEmployee() }


fun main(args: Array<String>) {

    // Create a Char array where each Char is a printable ASCII character
    val charArray = createCharArray()
    for (char in charArray) {
        print(char)
        print('(')
        print(char.toLong())
        print(')')
        print('|')
    }
    println()

    // Create a Float array where each Float is 1/r where r is a random number
    val floatArray = createFloatArray()
    for (float in floatArray) {
        println("Float: $float")
    }

    // Create an Employee array
    val employeeArray = createEmployeeArray()
    for (employee in employeeArray) {
        println("Employee: $employee")
    }

}