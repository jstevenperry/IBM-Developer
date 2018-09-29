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
    // Mutable Char Map
    val mutableCharMap = mutableMapOf<Char, Char>()
    var char = createChar()
    mutableCharMap[char] = char
    char = createChar()
    mutableCharMap[char] = char
    char = createChar()
    mutableCharMap[char] = char
    // Now try to add the same item more than once
    char = createChar()
    mutableCharMap[char] = char
    mutableCharMap[char] = char
    processMap(mutableCharMap)

    // Mutable Employee Map
    val mutableEmployeeMap = mutableMapOf<Int, Employee>()
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
    processMap(mutableEmployeeMap)
}

/**
 * Process the specified [map]
 */
private fun processMap(map: MutableMap<*, *>) {
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