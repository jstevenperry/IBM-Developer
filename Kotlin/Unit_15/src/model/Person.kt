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

import com.makotogo.learn.kotlin.util.createPerson
import com.makotogo.learn.kotlin.util.createWorker
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Interface - for objects that are configurable
 */
interface Configurable {
    /**
     * Function - configure the object
     */
    fun configure()
}

/**
 * Interface - for things that have identity
 */
interface Identifiable {
    /**
     * Property - identity for the object
     */
    val identity: String

    /**
     * Function - identify the object
     */
    fun identify(): String {
        return "Identity: $identity"
    }
}

/**
 * Interface - marks an object as Human
 */
interface Human : Configurable {
    /**
     * Property - when the Human was born
     */
    val dateOfBirth: LocalDate

}

/**
 * Person class - subclass of Human
 */
open class Person(
        private val familyName: String,
        private val givenName: String,
        override val dateOfBirth: LocalDate) : Human, Identifiable {

    /**
     * Private property - lateinit
     */
    private lateinit var whenCreated: LocalDateTime

    /**
     * Open property definition - can be overridden by subclass
     */
    override val identity: String = "$givenName $familyName"

    /**
     * Initializer block
     */
    init {
        //
        // Call Human.init() function
        configure()
    }

    /**
     * Abstract function implementation
     */
    final override fun configure() {
        whenCreated = LocalDateTime.now()
    }

    /**
     * toString() override
     */
    override fun toString(): String {
        return "Person(identity=$identity, whenCreated=$whenCreated, familyName=$familyName, givenName=$givenName, dateOfBirth=$dateOfBirth)"
    }
}

/**
 * Worker - subclass of Person
 */
class Worker(familyName: String,
             givenName: String,
             dateOfBirth: LocalDate,
             val taxIdNumber: String) : Person(familyName, givenName, dateOfBirth) {
    /**
     * Override identity property from parent class
     *
     * Use taxIdNumber property (filter out dashes)
     */
    override val identity = taxIdNumber.filter { charAt -> charAt != '-' }

    /**
     * Override function from interface.
     * Print the string representation.
     */
    override fun identify(): String {
        return toString()
    }

    /**
     * toString() override
     */
    override fun toString(): String {
        return "Worker(${super.toString()}, taxIdNumber=$taxIdNumber)"
    }

}

/**
 * The ubiquitous main function. We meet again.
 */
fun main(args: Array<String>) {
    //
    // Create a Human. Oh, wait, you can't instantiate an abstract class
    // val human = Human()

    //
    // Create a Person. Print it out to see what's up.
    val person = createPerson()
    println("Person object: $person")

    //
    // Create a Worker. Print it out to see what's up.
    val worker = createWorker()
    println("Worker object: $worker")
}