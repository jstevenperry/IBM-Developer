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

package com.makotogo.learn.kotlin.model

import com.makotogo.learn.kotlin.controller.processEmployee
import com.makotogo.learn.kotlin.util.createEmployee
import java.time.LocalDate

/**
 * Person class - the base class for all humans in this application
 */
open class Person(open var familyName: String, open var givenName: String, open val dateOfBirth: LocalDate) {
    override fun toString(): String {
        return "Person: [familyName=$familyName, givenName=$givenName]"
    }
}

/**
 * A Person, but a guest with a purpose
 */
data class Guest(override var familyName: String,
                 override var givenName: String,
                 override var dateOfBirth: LocalDate) : Person(familyName, givenName, dateOfBirth) {
    //
    // Private properties
    private var purpose: String? = null

    //
    // Secondary constructor
    constructor(familyName: String,
                givenName: String,
                dateOfBirth: LocalDate,
                purpose: String) : this(familyName, givenName, dateOfBirth) {
        this.purpose = purpose
    }
}

/**
 * A Person, but an employee of Megacorp (with title and everything!)
 */
data class Employee(override var familyName: String,
                    override var givenName: String,
                    override val dateOfBirth: LocalDate) : Person(familyName, givenName, dateOfBirth) {
    //
    // Private properties
    private var employeeId: Int? = null
    private var title: String? = null

    operator fun component4() = employeeId
    operator fun component5() = title

    //
    // Secondary constructor
    constructor(familyName: String,
                givenName: String,
                dateOfBirth: LocalDate,
                employeeId: Int,
                title: String) : this(familyName, givenName, dateOfBirth) {
        this.employeeId = employeeId
        this.title = title
    }
}

fun main(args: Array<String>) {
    var employee: Employee = createEmployee()

    processEmployee(employee)

    // Create new Employee
    employee = createEmployee()

    // Destructure its attributes
    val (familyName, givenName, dateOfBirth, employeeId, title) = employee
    // Process the Employee
    processEmployee(employee)
    // Print out the destructured attributes
    println("Destructured properties: FamilyName=$familyName, givenName=$givenName, dateOfBirth=$dateOfBirth, employeeId=$employeeId, title=$title")
}