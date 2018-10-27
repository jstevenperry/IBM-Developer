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

import com.makotogo.learn.kotlin.model.Person
import com.makotogo.learn.kotlin.util.generateRandomDateOfBirth
import com.makotogo.learn.kotlin.util.generateRandomFamilyName
import com.makotogo.learn.kotlin.util.generateRandomGivenName
import com.makotogo.learn.kotlin.util.generateRandomInt
import com.makotogo.learn.kotlin.util.generateRandomTaxIdNumber
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Worker - subclass of Person
 */
class Worker(familyName: String,
             givenName: String,
             dateOfBirth: LocalDate,
             val taxIdNumber: String,
             val purpose: Purpose) : Person(familyName, givenName, dateOfBirth) {
    /**
     * Override property identity
     */
    override val identity: String = taxIdNumber

    /**
     * Override property whenCreated
     */
    override val whenCreated: LocalDateTime = LocalDateTime.now()

    /**
     * toString() override
     */
    override fun toString(): String {
        return "Worker(${super.toString()}, taxIdNumber=$taxIdNumber, purpose=$purpose)"
    }

}

// BAD IDEA
/*
const val MAINTENANCE = "MAINTENANCE"
const val PACKAGE_DELIVERY = "PACKAGE_DELIVERY"
const val CONSULTING = "CONSULTING"
const val FAMILY_MEMBER = "FAMILY_MEMBER"
const val OTHER = "OTHER"

val PURPOSE: Array<String> = arrayOf(
        MAINTENANCE,
        PACKAGE_DELIVERY,
        CONSULTING,
        FAMILY_MEMBER,
        OTHER
)
*/

// YET ANOTHER BAD IDEA
/*
const val MAINTENANCE = 100
const val PACKAGE_DELIVERY = 200
const val CONSULTING = 300
const val FAMILY_MEMBER = 400
const val OTHER = 500

val PURPOSE = intArrayOf(
        MAINTENANCE,
        PACKAGE_DELIVERY,
        CONSULTING,
        FAMILY_MEMBER,
        OTHER
)
*/

// This is it!
enum class Purpose {
    MAINTENANCE,
    PACKAGE_DELIVERY,
    CONSULTING,
    FAMILY_MEMBER,
    OTHER,
}

/**
 * Create and return a [Worker] object filled with
 * random data.
 */
fun createWorker(): Worker {
    //
    // Pick a PURPOSE at random
    val purposes = Purpose.values()
    val purpose = purposes[generateRandomInt(purposes.size)]
    //
    // Return a new Worker object
    return Worker(
            familyName = generateRandomFamilyName(),
            givenName = generateRandomGivenName(),
            dateOfBirth = generateRandomDateOfBirth(),
            taxIdNumber = generateRandomTaxIdNumber(),
            purpose = purpose)
}
