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

import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Person class - subclass of Human
 */
open class Person(
        val familyName: String,
        val givenName: String,
        val dateOfBirth: LocalDate) {

    /**
     * Private property - lateinit
     */
    private val whenCreated: LocalDateTime = LocalDateTime.now()

    /**
     * toString() override
     */
    override fun toString(): String {
        return "Person(familyName='$familyName', givenName='$givenName', dateOfBirth=$dateOfBirth, whenCreated=$whenCreated)"
    }
}

/**
 * Worker - subclass of Person
 */
open class Worker(familyName: String,
                  givenName: String,
                  dateOfBirth: LocalDate,
                  val taxIdNumber: String) : Person(familyName, givenName, dateOfBirth) {

    /**
     * toString() override
     */
    override fun toString(): String {
        return "Worker(${super.toString()}, taxIdNumber=$taxIdNumber)"
    }

}

/**
 * Worker - subclass of Person
 */
open class Employee(familyName: String,
                    givenName: String,
                    dateOfBirth: LocalDate,
                    taxIdNumber: String,
                    val employeeId: Int,
                    val title: String) : Worker(familyName, givenName, dateOfBirth, taxIdNumber) {
    /**
     * toString() override
     */
    override fun toString(): String {
        return "Employee(employeeId=$employeeId, title=$title) ${super.toString()}"
    }
}