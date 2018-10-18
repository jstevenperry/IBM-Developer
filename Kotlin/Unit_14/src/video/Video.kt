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

package com.makotogo.learn.kotlin.video

import com.makotogo.learn.kotlin.util.generateRandomDateOfBirth
import com.makotogo.learn.kotlin.util.generateRandomFamilyName
import com.makotogo.learn.kotlin.util.generateRandomGivenName
import com.makotogo.learn.kotlin.util.generateRandomTaxIdNumber
import java.time.LocalDate
import java.time.LocalDateTime

abstract class Human {
    protected abstract val identity: String

    abstract val dateOfBirth: LocalDate

    abstract fun configure()

    open fun identify(): String {
        return ("Human Identity: $identity")
    }
}

open class Person(familyName: String,
                  givenName: String,
                  override val dateOfBirth: LocalDate) : Human() {

    private lateinit var whenCreated: LocalDateTime

    override val identity: String = "$givenName $familyName"

    init {
        configure()
    }

    override fun configure() {
        whenCreated = LocalDateTime.now()
    }

    override fun toString(): String {
        return "Person(dateOfBirth=$dateOfBirth, whenCreated=$whenCreated, identity='$identity')"
    }
}

class Worker(familyName: String,
             givenName: String,
             dateOfBirth: LocalDate,
             val taxIdNumber: String) : Person(familyName, givenName, dateOfBirth) {

    override val identity: String = taxIdNumber

    override fun identify(): String {
        return "Worker Identity: $identity"
    }

    override fun toString(): String {
        return "Worker(taxIdNumber='$taxIdNumber') ${super.toString()}"
    }
}

fun main(args: Array<String>) {
    // Human()

    // Person()

    val familyName = generateRandomFamilyName()
    val givenName = generateRandomGivenName()
    val dateOfBirth = generateRandomDateOfBirth()

    val person = Person(familyName, givenName, dateOfBirth)
    println("Person(${person.identify()}): $person")

    val worker = Worker(familyName, givenName, dateOfBirth, generateRandomTaxIdNumber())
    println("Worker(${worker.identify()}): $worker")

}