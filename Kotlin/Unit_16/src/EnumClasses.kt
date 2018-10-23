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
             val purpose: PURPOSE) : Person(familyName, givenName, dateOfBirth) {
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

/**
 * Create and return a [Worker] object filled with
 * random data.
 */
fun createWorker(): Worker {
    return Worker(
            familyName = generateRandomFamilyName(),
            givenName = generateRandomGivenName(),
            dateOfBirth = generateRandomDateOfBirth(),
            taxIdNumber = generateRandomTaxIdNumber(),
            purpose = generateRandomPurpose())
}

/**
 * Some random purposes
 */
enum class PURPOSE {
    MAINTENANCE,
    PACKAGE_DELIVERY,
    CONSULTING,
    FAMILY_MEMBER,
    OTHER,
    LOST
}

/**
 * Generate a random purpose. Occasionally returns null to simulate the
 * real world (sigh).
 */
internal fun generateRandomPurpose(): PURPOSE {
    val purposes = PURPOSE.values()
    return purposes[generateRandomInt(purposes.size)]
}

