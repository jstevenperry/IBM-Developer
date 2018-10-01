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

import com.makotogo.learn.kotlin.controller.processChar
import com.makotogo.learn.kotlin.controller.processEmployee
import com.makotogo.learn.kotlin.model.Employee
import com.makotogo.learn.kotlin.util.createChar
import com.makotogo.learn.kotlin.util.createEmployee

/**
 * The ubiquitous main() function. We meet again.
 */
fun main(args: Array<String>) {

    // Create a Char array where each Char is a printable ASCII character
    val charArray: CharArray = charArrayOf(
            createChar(),
            ' ', // placeholder
            createChar(),
            ' ', // placeholder
            ' '  // placeholder
    )
    println("********** Pre-Process charArray **********")
    for (c in charArray) {
        processChar(c)
    }
    // Arrays are mutable, overwrite the blank placeholder Chars at
    // the specified indices
    charArray[1] = createChar()
    charArray[3] = createChar()
    charArray[4] = createChar()

    println("********** Process charArray **********")
    for (c in charArray) {
        processChar(c)
    }

    val employee = createEmployee()
    // Create an Employee array
    val employeeArray: Array<Employee> = arrayOf(
            createEmployee(),
            createEmployee(),
            createEmployee(),
            employee,
            employee // same as above
    )
    println("********** Process employeeArray **********")
    for (e in employeeArray) {
        processEmployee(e)
    }
}
