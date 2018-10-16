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
 * Abstract class - represents a Human
 */
abstract class Human {
    /**
     * Abstract function
     */
    protected abstract fun configure()

    /**
     * Abstract property
     */
    protected abstract val whenCreated: LocalDateTime

}

/**
 * Person class - subclass of Human
 */
open class Person(
        open val familyName: String,
        open val givenName: String,
        open val dateOfBirth: LocalDate) :Human() {

    /**
     * Abstract property implementation
     */
    override lateinit var whenCreated: LocalDateTime

    /**
     * Open property definition - can be overridden by subclass
     */
    open val identity: String = "$givenName $familyName"

    /**
     * Initializer block
     */
    init {
        //
        // Configure this instance
        configure()
    }

    /**
     * Abstract function implementation
     */
    override fun configure() {
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
     * toString() override
     */
    override fun toString(): String {
        return "Worker(${super.toString()}, taxIdNumber=$taxIdNumber)"
    }
}

/**
 * The ubiquitous main function - we meet again.
 */
fun main(args:Array<String>) {
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