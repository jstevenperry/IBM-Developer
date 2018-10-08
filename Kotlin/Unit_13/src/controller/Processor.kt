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

package com.makotogo.learn.kotlin.controller

import com.makotogo.learn.kotlin.javainterop.MysteryBox
import com.makotogo.learn.kotlin.model.Employee
import com.makotogo.learn.kotlin.model.Guest
import com.makotogo.learn.kotlin.model.Person
import com.makotogo.learn.kotlin.model.Validator

/**
 * The MysteryBox - represents a third-party system
 * that provides data
 */
internal val mysteryBox = MysteryBox()

/**
 * Validator
 */
private val validator = Validator()

/**
 * Formats the name of the specified [person].
 */
fun formatName(person: Person) = "${person.familyName}, ${person.givenName}"

/**
 * SecurityCheck instance
 */
private val securityCheck = SecurityCheck()

/**
 * Function to throw an AccessException with the specified
 * [message]. Return type is [Nothing].
 */
fun throwAccessException(message: String): Nothing {
    throw AccessException(message)
}

/**
 * Process a [Person] object.
 * Attempt to admit them. If that goes well, then
 * print a message, otherwise handle the exception.
 */
fun process(person: Person) {
    println("Processing Person => $person")
    when (person) {
        is Guest -> process(person)
        is Employee -> process(person)
        else -> {
            try {
                // Validate the Person object
                validator.validate(person)
                securityCheck.admitEntrance(person)
                println("Person access granted.")
            } catch (e: Exception) {
                val message = "Exception while checking admittance for Person: $person"
                println(message)
                throw ProcessorException(message, e)
            }
        }
    }
}

/**
 * Process an [Employee] object.
 * Attempt to admit them. If that goes well, then
 * print a message, otherwise handle the exception.
 */
fun process(employee: Employee) {
    println("Processing Employee => $employee")
    try {
        validator.validate(employee)
        securityCheck.admitEntrance(employee)
        println("Employee access granted.")
    } catch (e: Exception) {
        val message = "Exception while checking admittance for Employee: $employee"
        println(message)
        throw ProcessorException(message, e)
    }
}

/**
 * Process a [Guest] object.
 * Attempt to admit them. If that goes well, then
 * print a message, otherwise handle the exception.
 */
fun process(guest: Guest) {
    println("Processing Guest => $guest")
    try {
        validator.validate(guest)
        securityCheck.admitEntrance(guest)
        println("Guest access granted.")
    } catch (e: Exception) {
        val message = "Exception while checking admittance for Guest: $guest"
        println(message)
        throw ProcessorException(message, e)
    }
}
