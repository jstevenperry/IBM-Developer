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

package com.makotogo.learn.kotlin

import com.makotogo.learn.kotlin.controller.ProcessorException
import com.makotogo.learn.kotlin.controller.mysteryBox
import com.makotogo.learn.kotlin.controller.process
import com.makotogo.learn.kotlin.model.Person
import com.makotogo.learn.kotlin.model.ValidationException
import com.makotogo.learn.kotlin.model.Validator

/**
 * Validator
 */
private val validator = Validator()

fun noExceptionHandlingDemo() {
    println("********** noExceptionHandlingDemo() **********")
    //
    // Get a mystery object from the box
    // It should be a Person (or a subclass thereof)
    // but use a safe cast just to be, well, safe.
    val person = mysteryBox.mysteryObject() as? Person
    //
    // Run the mystery object through the Validator
    validator.validate(person)
    //
    // Looks like the object is valid
    println("Valid person: $person")
    //
    // Process the Person
    if (person is Person) {
        process(person)
    }
}

fun handleValidationExceptionDemo() {
    println("********** handleValidationExceptionDemo() **********")
    //
    // Get a mystery object from the box
    // It should be a Person (or a subclass thereof)
    // but use a safe cast just to be, well, safe.
    val person = mysteryBox.mysteryObject() as? Person
    try {
        //
        // Run the mystery object through the Validator
        validator.validate(person)
        //
        // Looks like the object is valid
        println("Valid person: $person")
        //
        // Process the Person
        if (person is Person) {
            process(person)
        }
    } catch (e: ValidationException) {
        //
        // Something is wrong with that object
        println("Validation errors: $e, stack trace follows...")
        println(e.printStackTrace())
    }
}

fun handleAllAppExceptionsDemo() {
    println("********** handleAllAppExceptionsDemo() **********")
    //
    // Get a mystery object from the box
    // It should be a Person (or a subclass thereof)
    // but use a safe cast just to be, well, safe.
    val person = mysteryBox.mysteryObject() as? Person
    try {
        //
        // Run the mystery object through the Validator
        validator.validate(person)
        //
        // Looks like the object is valid
        println("Valid person: $person")
        //
        // Process the Person
        if (person is Person) {
            process(person)
        }
    } catch (e: ValidationException) {
        //
        // Something is wrong with that object
        println("Validation errors: $e, stack trace follows...")
        println(e.printStackTrace())
    } catch (e: ProcessorException) {
        //
        // Something went wrong during processing
        println("Processing error: $e, stack trace follows...")
        println(e.printStackTrace())
    }
}

/**
 * The ubiquitous main function. We meet again.
 */
fun main(args: Array<String>) {
    //
    // Let all exceptions percolate up to the runtime
    noExceptionHandlingDemo()
    //
    // Handle only ValidationException
    handleValidationExceptionDemo()
    //
    // Handle all application-level Exceptions
    handleAllAppExceptionsDemo()
}