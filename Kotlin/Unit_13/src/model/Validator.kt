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
 */

package com.makotogo.learn.kotlin.model

class Validator {
    /**
     * Validates the specified [person] object from
     * the ground up.
     *
     */
    fun validate(person: Person?): Boolean {
        //
        // The things that are wrong with the Person
        val validationErrors = ArrayList<String>()
        //
        // Check the various Person subclasses. Use smart casts.
        //
        // Person checks
        validatePerson(person, validationErrors)
        //
        // Worker checks
        if (person is Worker) {
            validateWorker(person, validationErrors)
        }
        //
        // Guest checks
        if (person is Guest) {
            validateGuest(person, validationErrors)
        }
        //
        // Employee checks
        if (person is Employee) {
            validateEmployee(person, validationErrors)
        }
        //
        // If there were any validation errors, throw an exception
        if (validationErrors.size > 0) {
            throw ValidationException("Validation errors: $validationErrors")
        }
        //
        // Made it this far, object is valid
        return true
    }

    /**
     * Perform Person-specific validations
     */
    private fun validatePerson(person: Person?, validationErrors: ArrayList<String>) {
        //
        // First things first, is the reference null?
        if (person == null) {
            throw IllegalArgumentException("Person reference cannot be null")
        }
        if (person.familyName == null) {
            validationErrors.add("Family name is null")
        }
        if (person.givenName == null) {
            validationErrors.add("Given name is null")
        }
        if (person.dateOfBirth == null) {
            validationErrors.add("Date of birth is null")
        }
    }

    /**
     * Perform Worker-specific validations
     */
    private fun validateWorker(person: Worker, validationErrors: ArrayList<String>) {
        if (person.taxIdNumber == null) {
            validationErrors.add("TaxID is null")
        }
    }

    /**
     * Perform Guest-specific validations
     */
    private fun validateGuest(person: Guest, validationErrors: ArrayList<String>) {
        if (person.purpose == null) {
            validationErrors.add("Purpose is null")
        }
    }

    /**
     * Perform Employee-specific validations
     */
    private fun validateEmployee(person: Employee, validationErrors: ArrayList<String>) {
        if (person.employeeId == null) {
            validationErrors.add("EmployeeID is null")
        }
        if (person.title == null) {
            validationErrors.add("Title is null")
        }
    }

}
