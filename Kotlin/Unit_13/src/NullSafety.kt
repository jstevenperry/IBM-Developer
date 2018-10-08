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
    // Get a few Employees
    for (index in 1..100) {
        //
        // Get a new hire (Employee)
        val employee: Employee? = mysteryBox.fetchNewHire()
        //
        // The default attribute value (if null)
        val titleDefault = "ASSOCIATE"

        //
        // Demo:
        // Capitalize the Employee's title and print it out,
        // along with the Employee string representation

        //
        // Manual null checks
        if (employee != null) {
            if (employee.title == null) {
                println("(if) Employee: $titleDefault: $employee")
            } else {
                println("(else) Employee: ${employee.title.toUpperCase()}: $employee")
            }
        } else {
            println("(if) Employee: null: null")
        }

        //
        // Demonstrate null-safe operator
        println("(?) Employee: ${employee?.title?.toUpperCase()}: $employee")

        //
        // Demonstrate Elvis operator
        println("(?:) Employee: ${employee?.title?.toUpperCase() ?: titleDefault}: $employee")

        //
        // Demonstrate double-bang-operator - last resort
        println("(!!) Employee: ${employee?.title!!.toUpperCase()}: $employee")
    }
}