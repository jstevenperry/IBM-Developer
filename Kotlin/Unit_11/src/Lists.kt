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
import com.makotogo.learn.kotlin.controller.processFloat
import com.makotogo.learn.kotlin.controller.processPerson
import com.makotogo.learn.kotlin.model.Employee
import com.makotogo.learn.kotlin.model.Person
import com.makotogo.learn.kotlin.util.createChar
import com.makotogo.learn.kotlin.util.createEmployee
import com.makotogo.learn.kotlin.util.createFloat
import com.makotogo.learn.kotlin.util.createPerson

/**
 * Processes a [List] of "things" of [Any] type.
 * Uses smart cast to determine exactly what the "thing"
 * is, and then delegates to the correct [Processor]
 * to handle it.
 */
fun processList(things: List<Any>, description: String? = null) {
    println("********** processList(${description ?: ""}) **********")
    for (thing in things) {
        when (thing) {
            // Quiz question: Why list Employee first?
            is Employee -> processEmployee(employee = thing)
            is Person -> processPerson(person = thing)
            is Float -> processFloat(float = thing)
            is Char -> processChar(char = thing)
            else -> println("Unknown thing: $thing")
        }
    }
}

/**
 * The ubiquitous main() function. We meet again.
 */
fun main(args: Array<String>) {

    val char = createChar()
    // Create a MutableList of Char
    val mutableCharList = mutableListOf(
            createChar(),
            createChar(),
            createChar(),
            // Add the same element four times
            // (Lists allow duplicates)
            char,
            char
    )
    // Create a List of Float
    val floatList = List(5) { index -> createFloat(index) }
    // Quiz question: Why doesn't this work?
    // floatList.add(createFloat(10))

    // Create a List of Person
    val personList = List(5) { _ -> createPerson() }

    // Create a List of Employee
    val employeeList = MutableList(3) { createEmployee() }
    // Add the same Employee twice - never a good idea
    // (unless you're demonstrating a List)
    val employee = createEmployee()
    employeeList.add(employee)
    employeeList.add(employee)

    // Process the Lists. Yeah, that's a thing.
    processList(mutableCharList, description = "mutableCharList")
    processList(floatList, description = "floatList")
    processList(personList, description = "personList")
    processList(employeeList, description = "employeeList")

}