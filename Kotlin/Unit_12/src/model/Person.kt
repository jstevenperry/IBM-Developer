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

import com.makotogo.learn.kotlin.controller.processGuest
import com.makotogo.learn.kotlin.util.createGuest
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Person class - the base class for all humans in this application
 * Class header formatting according to:
 * https://kotlinlang.org/docs/reference/coding-conventions.html#class-header-formatting
 */
open class Person(
        open val familyName: String,
        open val givenName: String,
        open val dateOfBirth: LocalDate) {

    /**
     * Class layout according to:
     * https://kotlinlang.org/docs/reference/coding-conventions.html#class-layout
     */
    // Declare properties first - good style
    //
    // Private property
    private var fullName: String = "$familyName $givenName"
    //
    // Private property - when the class was instantiated - initialized later
    private lateinit var whenInstantiated: LocalDateTime
    //
    // Private property - configured - write custom getter/setter
    protected var configured: Boolean = false
        get() {
            println("Person.getConfigured(): Accessing 'configured' property")
            return field
        }
        set(value) {
            println("Person.setConfigured($value): Setting 'configured' property")
            field = value
        }

    /**
     * Init block
     */
    init {
        println("*** Person Init Block start... ***")
        // Call configure
        configure()
        println("*** Person Init Block done. ***")
    }

    /**
     * Configure this instance:
     * Print some output indicating this is in progress
     * Set property values
     * Print output indicating configuration complete
     */
    open fun configure() {
        println("Person.configure(): start...")
        // Init when instantiated timestamp
        this.whenInstantiated = LocalDateTime.now()
        println("Person class instantiated at: $whenInstantiated")
        configured = true // calls setter
        println("Person.configure(): done.")
    }

    /**
     * You can have more than one init block (if needed)
     */
//    init {
//        println("*** Person 2nd Init Block start... ***")
//    }

    /**
     * Override toString() - because the default one is sub-optimal
     */
    override fun toString(): String {
        return "Person(familyName=$familyName, givenName=$givenName, fullName=$fullName, " +
                "dateOfBirth=$dateOfBirth, whenInstantiated=$whenInstantiated)"
    }
}

open class Worker(familyName: String,
                  givenName: String,
                  dateOfBirth: LocalDate,
                  open val taxIdNumber: String) : Person(familyName, givenName, dateOfBirth) {
    init {
        println("\t*** Init block: Worker start ***")
    }
    override fun toString(): String {
        return "Worker(${super.toString()}, taxIdNumber=$taxIdNumber)"
    }
}

/**
 * A Person, but a guest with a purpose
 */
class Guest : Worker {
    /**
     * Class layout according to:
     * https://kotlinlang.org/docs/reference/coding-conventions.html#class-layout
     */
    //
    // Class properties
    var purpose: String? = null

    /**
     * Init block
     */
    init {
        println("\t\t*** Guest Init Block start... ***")
        // If not configured, call configure()
        if (!configured) { // Invokes getter
            configure()
        }
        // Initialize full name (given name first)
        //this.fullName = "$givenName $familyName"
        println("\t\t*** Guest Init Block done. ***")
    }

    //
    // Secondary constructor
    constructor(familyName: String,
                givenName: String,
                dateOfBirth: LocalDate,
                taxIdNumber: String,
                purpose: String) : super(familyName, givenName, dateOfBirth, taxIdNumber) {
        println("\t\tGuest: secondary constructor start...")
        this.purpose = purpose
        println("\t\tGuest: secondary constructor done.")
    }

    /**
     * Configure the instance - delegate to parent class
     */
    override fun configure() {
        println("\t\tGuest.configure(): start...")
        super.configure()
        println("\t\tGuest.configure(): done.")
    }

    /**
     * Override toString()
     */
    override fun toString(): String {
        return "Guest(${super.toString()}, purpose=$purpose)"
    }
}

/**
 * A Person, but an employee of Megacorp (with title and everything!)
 * Class header formatting according to:
 * https://kotlinlang.org/docs/reference/coding-conventions.html#class-header-formatting
 */
data class Employee(
        override val familyName: String,
        override val givenName: String,
        override val dateOfBirth: LocalDate,
        override val taxIdNumber: String,
        val employeeId: Int,
        val title: String) : Worker(familyName, givenName, dateOfBirth, taxIdNumber)
// UNCOMMENT if you want to override toString() in this data class and still
// have all the other cool features of data classes...
//{
//    override fun toString(): String {
//        return "Employee(${super.toString()}, employeeId=$employeeId, title=$title)"
//    }
//}

/**
 * The ubiquitous main function. We meet again.
 */
fun main(args: Array<String>) {
    // Guest example
    val guest = createGuest()
    processGuest(guest = guest)

}