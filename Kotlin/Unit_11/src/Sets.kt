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
 * Processes a [Set] of "things" of [Any] type.
 * Uses smart cast to determine exactly what the "thing"
 * is, and then delegates to the correct [Processor]
 * to handle it.
 */
fun processSet(things: Set<Any>, description: String? = null) {
    println("********** processSet(${description ?: ""}) **********")
    for (thing in things) {
        when (thing) {
            // Quiz question: Why Set Employee first?
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

    // Create a MutableSet of Char
    val mutableCharSet: MutableSet<Char> = mutableSetOf()
    // Add one element
    mutableCharSet.add(createChar())
    mutableCharSet.add(createChar())
    mutableCharSet.add(createChar())
    // Add element four times
    // (Or try to - sets do not allow duplicates)
    val char = createChar()
    mutableCharSet.add(char)
    mutableCharSet.add(char)

    // Create a Set of Float
    val floatSet = setOf(
            createFloat(index = 0),
            createFloat(index = 1),
            createFloat(index = 2),
            createFloat(index = 3),
            createFloat(index = 4)
    )
    // Quiz question: Why doesn't this work?
    // floatSet.add(createFloat(10))

    // Create a Set of Person
    val personSet = setOf(
            createPerson(),
            createPerson(),
            createPerson(),
            createPerson(),
            createPerson()
    )

    // Create a Set of Employee
    val employeeSet = mutableSetOf(
            createEmployee(),
            createEmployee(),
            createEmployee()
    )
    // Add the same Employee twice - never a good idea
    // (unless you're demonstrating a Set)
    val employee = createEmployee()
    employeeSet.add(employee)
    employeeSet.add(employee)

    // Process the Sets. Yeah, that's a thing.
    processSet(mutableCharSet, description = "mutableCharSet")
    processSet(floatSet, description = "floatSet")
    processSet(personSet, description = "personSet")
    processSet(employeeSet, description = "employeeSet")

}