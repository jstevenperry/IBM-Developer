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

import com.makotogo.learn.kotlin.controller.processGuest
import com.makotogo.learn.kotlin.controller.processPerson
import com.makotogo.learn.kotlin.controller.processWorker
import com.makotogo.learn.kotlin.model.Guest
import com.makotogo.learn.kotlin.model.Person
import com.makotogo.learn.kotlin.model.Worker
import com.makotogo.learn.kotlin.util.generateRandomFamilyName
import com.makotogo.learn.kotlin.util.generateRandomGivenName
import com.makotogo.learn.kotlin.util.generateRandomPurpose
import com.makotogo.learn.kotlin.util.generateRandomTaxIdNumber
import com.makotogo.learn.kotlin.util.generateRandomYearMonthDayTriple
import com.makotogo.learn.kotlin.util.toLocalDate
import java.time.LocalDate

/**
 * Simple Person instantiation demo
 */
fun instantiatePerson() {
    println("********** instantiatePerson() **********")
    println("Calling Person() constructor")
    val person = Person(
            familyName = "Jones",
            givenName = "Mike",
            dateOfBirth = LocalDate.of(1980, 2, 3))

    println("Person: $person")
}

/**
 * Better Person instantiation demo, complete with
 * random attribute values.
 */
private fun betterPersonInstantiation() {
    println("********** betterPersonInstantiation() **********")
    // Generate random attribute values
    val familyName = generateRandomFamilyName()
    val givenName = generateRandomGivenName()
    val dateOfBirth = generateRandomYearMonthDayTriple().toLocalDate()
    //
    // Create a Person object with random attribute values
    // Invoke primary constructor
    println("Calling Person() constructor")
    val person = Person(familyName, givenName, dateOfBirth)
    //
    // Now process that Person object
    processPerson(person)
}

fun instantiateWorker() {
    println("********** instantiateWorker() **********")
    // Generate random attribute values
    val familyName = generateRandomFamilyName()
    val givenName = generateRandomGivenName()
    val dateOfBirth = generateRandomYearMonthDayTriple().toLocalDate()
    val taxIdNumber = generateRandomTaxIdNumber()
    //
    // Create a Worker object with random attribute values
    // Invoke primary constructor
    println("Calling Worker() constructor")
    val worker = Worker(familyName, givenName, dateOfBirth, taxIdNumber)
    //
    // Now process that Worker object
    processWorker(worker)
}

/**
 * Create a Guest object
 */
fun instantiateGuest() {
    println("********** instantiateGuest() **********")
    // Generate random attribute values
    val familyName = generateRandomFamilyName()
    val givenName = generateRandomGivenName()
    val dateOfBirth = generateRandomYearMonthDayTriple().toLocalDate()
    val taxIdNumber = generateRandomTaxIdNumber()
    //
    // Create a Guest - invoke secondary constructor
    println("Calling Guest() constructor")
    val guest = Guest(
            familyName = familyName,
            givenName = givenName,
            dateOfBirth = dateOfBirth,
            taxIdNumber = taxIdNumber,
            purpose = generateRandomPurpose()
    )
    //
    // Now process that Guest object
    processGuest(guest)
}


/**
 * The ubiquitous main function. We meet again.
 */
fun main(args: Array<String>) {

    // Basic instantiation
    instantiatePerson()

    // A better instantiation of Person
    betterPersonInstantiation()

    // Instantiate Worker
    instantiateWorker()

    // Instantiate a Guest
    instantiateGuest()

}

