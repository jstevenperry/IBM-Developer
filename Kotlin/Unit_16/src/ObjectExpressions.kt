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

import com.makotogo.learn.kotlin.model.Human
import com.makotogo.learn.kotlin.model.Identifiable
import com.makotogo.learn.kotlin.model.Person
import com.makotogo.learn.kotlin.util.generateRandomDateOfBirth
import com.makotogo.learn.kotlin.util.generateRandomFamilyName
import com.makotogo.learn.kotlin.util.generateRandomGivenName
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Create and return a [Human] object
 * It is also [Identifiable], but the JVM only supports
 * one return type so I had to pick one.
 */
fun createIdentifiableHuman(): Human {
    //
    // Create an anonymous inner class implementation of
    // the Human and Identifiable interfaces
    // (Yes, Kotlin will let you implement multiple interfaces
    // anonymously!)
    return object : Human, Identifiable {
        //
        // Override abstract property with an initializer
        override val dateOfBirth: LocalDate = generateRandomDateOfBirth()
        //
        // Override abstract property identity
        override val identity: String = "${dateOfBirth.toEpochDay()}"

        /**
         * toString() override
         */
        override fun toString(): String {
            return "Human,Identifiable(dateOfBirth=$dateOfBirth, identity=$identity)"
        }
    }
}

/**
 * Create and return an [Identifiable] object.
 * It is also [Human].
 */
fun createIdentifiable(): Identifiable {
    return createIdentifiableHuman() as Identifiable
}

/**
 * Create and return a [Person] object filled with
 * random data.
 */
fun createPerson(): Person {
    //
    // Create an anonymous inner class implementation of
    // the Person abstract class
    return object : Person(
            familyName = generateRandomFamilyName(),
            givenName = generateRandomGivenName(),
            dateOfBirth = generateRandomDateOfBirth()) {
        //
        // Override abstract property identity
        override val identity: String = "$familyName $givenName"
        //
        // Override abstract property with an initializer
        override val whenCreated: LocalDateTime = LocalDateTime.now()

        /**
         * toString() override
         */
        override fun toString(): String {
            return "Person(familyName=$familyName, givenName=$givenName, dateOfBirth=$dateOfBirth, whenCreated=$whenCreated, identity=$identity)"
        }
    }
}

/**
 * The ubiquitous main() function. We meet again.
 */
fun main(args: Array<String>) {
    //
    // Create a Human (also Identifiable)
    val human = createIdentifiableHuman()
    println("Human: $human")
    //
    // or
//    val identifiable = createIdentifiableHuman() as Identifiable
//    println("Identifiable: $identifiable")
    //
    // Now (unsafe) cast to Identifiable
    val identifiable = createIdentifiable()
    println("Identifiable: $identifiable")
    //
    // Create a Person object
    val person = createPerson()
    println("Person: $person")
}