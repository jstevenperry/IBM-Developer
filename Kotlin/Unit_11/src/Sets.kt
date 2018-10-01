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

    val char = createChar()
    // Create a Set of Char
    val charSet: Set<Char> = setOf(
            createChar(),
            createChar(),
            createChar(),
            char,
            char
    )
    processSet(charSet)

    // Create a MutableSet of Employee
    val employeeSet: MutableSet<Employee> = mutableSetOf(
            createEmployee(),
            createEmployee(),
            createEmployee()
    )
    // Add the same Employee twice - never a good idea
    // (unless you're demonstrating a Set)
    val employee = createEmployee()
    employeeSet.add(employee)
    employeeSet.add(employee)
    processSet(employeeSet)
}

/**
 * Process the specified [set]
 */
private fun processSet(set: Set<Any>) {
    println("********** Process Set **********")
    for (item in set) {
        when (item) {
            is Char -> {
                processChar(item)
            }
            is Employee -> {
                processEmployee(item)
            }
            else -> println("Unknown item: $item")
        }
    }
}