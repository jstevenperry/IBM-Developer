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

import com.makotogo.learn.kotlin.controller.process
import com.makotogo.learn.kotlin.javainterop.createGuest
import java.time.LocalDate

/**
 * Person class - the base class for all humans in this application
 * Class header formatting according to:
 * https://kotlinlang.org/docs/reference/coding-conventions.html#class-header-formatting
 */
open class Person(
        open val familyName: String?,
        open val givenName: String?,
        open val dateOfBirth: LocalDate?) {
    /**
     * Override toString() - because the default one is sub-optimal
     */
    override fun toString(): String {
        return "Person(familyName=$familyName, givenName=$givenName, dateOfBirth=$dateOfBirth)"
    }
}

open class Worker(familyName: String?,
                  givenName: String?,
                  dateOfBirth: LocalDate?,
                  open val taxIdNumber: String?) : Person(familyName, givenName, dateOfBirth) {
    override fun toString(): String {
        return "Worker(${super.toString()}, taxIdNumber=$taxIdNumber)"
    }
}

/**
 * A Person, but a guest with a purpose
 */
class Guest(familyName: String?,
            givenName: String?,
            dateOfBirth: LocalDate?,
            taxIdNumber: String?,
            val purpose: String?) : Worker(familyName, givenName, dateOfBirth, taxIdNumber) {
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
class Employee(
        override val familyName: String?,
        override val givenName: String?,
        override val dateOfBirth: LocalDate?,
        override val taxIdNumber: String?,
        val employeeId: Int?,
        val title: String?) : Worker(familyName, givenName, dateOfBirth, taxIdNumber) {
    override fun toString(): String {
        return "Employee(${super.toString()}, employeeId=$employeeId, title=$title)"
    }
}

/**
 * The ubiquitous main function. We meet again.
 */
fun main(args: Array<String>) {
    // Guest example
    val guest = createGuest()
    process(guest = guest)

}