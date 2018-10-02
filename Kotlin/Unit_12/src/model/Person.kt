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
import com.makotogo.learn.kotlin.controller.processGuest
import com.makotogo.learn.kotlin.util.createEmployee
import com.makotogo.learn.kotlin.util.createGuest
import java.time.LocalDate
import java.time.LocalDateTime

open abstract class Human(open val dateOfBirth: LocalDate) {
    open abstract fun configure()
}

/**
 * Person class - the base class for all humans in this application
 */
open class Person(open val familyName: String,
                  open val givenName: String,
                  dateOfBirth: LocalDate) : Human(dateOfBirth) {
    // Init block
    init {
        println("*** Person Init Block running... ***")
        configure()
        println("*** Person Init Block done. ***")
    }

    // Configure the instance
    override fun configure() {
        println("*** Person configure() running... ***")
        this.whenInstantiated = LocalDateTime.now()
        println("Person class instantiated at: $whenInstantiated")
        this.fullName = "$familyName/$givenName"
        println("*** Person configure() done. ***")
    }

    // Private property - has access to constructor properties
    private var fullName: String? = null

    // Private property - when the class was instantiated
    protected var whenInstantiated: LocalDateTime? = null

    override fun toString(): String {
        return "Person(familyName=$familyName, givenName=$givenName, fullName=$fullName, dateOfBirth=$dateOfBirth)"
    }
}

/**
 * A Person, but a guest with a purpose
 */
data class Guest(override val familyName: String,
                 override val givenName: String,
                 override val dateOfBirth: LocalDate) : Person(familyName, givenName, dateOfBirth) {
    // Init block
    init {
        println("*** Guest Init Block running... ***")
        println("*** Guest Init Block done. ***")
    }

    override fun configure() {
        println("*** Guest configure() running... ***")
        super.configure()
        println("*** Guest configure() done. ***")
    }

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

    override fun toString(): String {
        return "Guest(Parent=${super.toString()}, purpose=$purpose)"
    }
}

/**
 * A Person, but an employee of Megacorp (with title and everything!)
 */
data class Employee(override val familyName: String,
                    override val givenName: String,
                    override val dateOfBirth: LocalDate,
                    val employeeId: Int,
                    val title: String) : Person(familyName, givenName, dateOfBirth) {
    // Init block - REMOVE?
    init {
        println("Employee class instantiated at: ${super.whenInstantiated}")
    }
}

/**
 * The ubiquitous main function. We meet again.
 */
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

    val guest = createGuest()
    processGuest(guest)
}