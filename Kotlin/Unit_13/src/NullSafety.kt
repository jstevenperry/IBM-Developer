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
 */

package com.makotogo.learn.kotlin

import com.makotogo.learn.kotlin.controller.mysteryBox
import com.makotogo.learn.kotlin.model.Employee

fun main(args: Array<String>) {
    //
    // ArrayList of Employees
    val employees = ArrayList<Employee>()
    //
    // Get a few Employees
    for (index in 1..100) {
        employees.add(mysteryBox.fetchNewHire())
    }

    for (index in 0 until employees.size) {
        val employee = employees[index]
        //
        //
        // Capitalize the chosen and print it out,
        // along with the Employee string representation

        // TODO: Break these out into separate functions
        //
        // Demonstrate null-safe operator
        println("(?) Employee: ${employee.title?.toUpperCase()}: $employee")

        //
        // The default attribute value (if null)
        val titleDefault = "ASSOCIATE"

        //
        // Manual null check
        if (employee.title == null) {
            println("(if) Employee: $titleDefault: $employee")
        } else {
            println("(else) Employee: ${employee.title.toUpperCase()}: $employee")
        }

        //
        // Demonstrate Elvis operator
        println("(?:) Employee: ${employee.title?.toUpperCase() ?: titleDefault}: $employee")

        //
        // Demonstrate double-bang-operator - last resort
        println("(!!) Employee: ${employee.title!!.toUpperCase()}: $employee")
    }
}