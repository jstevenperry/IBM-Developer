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
    // Create a MutableList of Char
    val charList: List<Char> = listOf(
            createChar(),
            createChar(),
            createChar(),
            char,
            char // same as above
    )
    processList(charList)

    // Create a List of Employee
    val employeeList: MutableList<Employee> = MutableList(3) { createEmployee() }
    val employee = createEmployee()
    employeeList.add(employee)
    employeeList.add(employee) // same as above
    processList(employeeList)
}

/**
 * Process the specified [list]
 */
private fun processList(list: List<Any>) {
    println("********** Process List **********")
    for (item in list) {
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
