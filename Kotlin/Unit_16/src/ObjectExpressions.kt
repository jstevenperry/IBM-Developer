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
import com.makotogo.learn.kotlin.model.Person
import com.makotogo.learn.kotlin.util.generateRandomDateOfBirth
import com.makotogo.learn.kotlin.util.generateRandomFamilyName
import com.makotogo.learn.kotlin.util.generateRandomGivenName
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Create and return a
 */
fun createHuman(): Human {
    //
    // Create an anonymous inner class implementation of
    // the Human interface
    return object : Human {
        //
        // Override abstract property with an initializer
        override val dateOfBirth: LocalDate = generateRandomDateOfBirth()
        //
        // Override abstract property identity
        override val identity: String = "$dateOfBirth"
    }
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
    }
}

