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
    // Create a few chars
    val chars = charArrayOf(
            createChar(),
            createChar(),
            createChar(),
            createChar()
    )
    // Immutable Char Map
    val charMap: Map<Char, Char> = mapOf(
            Pair(chars[0], chars[0]), //
            Pair(chars[1], chars[1]), //
            Pair(chars[2], chars[2]), //
            Pair(chars[3], chars[3]), // Try to add same char again
            Pair(chars[3], chars[3])  // and again
    )
    processMap(charMap)

    // Mutable Employee Map
    val mutableEmployeeMap: MutableMap<Int, Employee> = mutableMapOf()
    var employee = createEmployee()
    mutableEmployeeMap[employee.employeeId] = employee
    employee = createEmployee()
    mutableEmployeeMap[employee.employeeId] = employee
    employee = createEmployee()
    mutableEmployeeMap[employee.employeeId] = employee
    // Now try and add the same item more than once
    employee = createEmployee()
    mutableEmployeeMap[employee.employeeId] = employee
    mutableEmployeeMap[employee.employeeId] = employee
    mutableEmployeeMap[employee.employeeId] = employee
    processMap(mutableEmployeeMap)
}

/**
 * Process the specified [map]
 */
private fun processMap(map: Map<*, *>) {
    println("********** Process Map **********")
    for ((key, value) in map) {
        when (value) {
            is Char -> {
                processChar(value)
            }
            is Employee -> {
                processEmployee(value)
            }
            else -> {
                println("Unknown item: $value")
            }
        }
    }
}