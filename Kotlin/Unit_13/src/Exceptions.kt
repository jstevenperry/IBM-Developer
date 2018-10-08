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

import com.makotogo.learn.kotlin.controller.mysteryBox
import com.makotogo.learn.kotlin.model.ValidationException
import com.makotogo.learn.kotlin.model.Validator

/**
 * Validator
 */
private val validator = Validator()

/**
 * Validation demo
 *
 * Create 100 Guest objects and validate them.
 * The MysteryBox occasionally provides bad data,
 * so it's important to validate it.
 */
fun validationDemo() {
    // Fetch 100 Guests and validate them
    for (index in 1..100) {
        val guest = mysteryBox.fetchGuest()
        try {
            validator.validate(guest)
            println("Valid guest: $guest")
        } catch (e: ValidationException) {
            println("Validation errors: $e")
        }
    }
}

/**
 * The ubiquitous main function. We meet again.
 */
fun main(args: Array<String>) {
    validationDemo()
}