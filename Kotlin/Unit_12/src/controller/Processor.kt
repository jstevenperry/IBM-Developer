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

package com.makotogo.learn.kotlin.controller

import com.makotogo.learn.kotlin.model.Employee
import com.makotogo.learn.kotlin.model.Guest
import com.makotogo.learn.kotlin.model.Person
import com.makotogo.learn.kotlin.model.Worker

/**
 * Process an [Employee] object.
 * For this simple exercise, that just means printing it out.
 */
fun processEmployee(employee: Employee) {
    println("Processing Employee => $employee")
}

/**
 * Process a [Person] object.
 * For this simple exercise, that just means printing it out.
 */
fun processPerson(person: Person) {
    println("Processing Person => $person")
}

fun processWorker(worker: Worker) {
    println("Processing Worker => $worker")
}

/**
 * Process a [Guest] object.
 * For this simple exercise, that just means printing it out.
 */
fun processGuest(guest: Guest) {
    println("Processing Guest => $guest")
}

/**
 * Process a [Float] object.
 * For this simple exercise, that just means printing it out.
 */
fun processFloat(float: Float) {
    println("Processing Float => $float")
}

/**
 * Process a [Char] object.
 * For this simple exercise, that just means printing it out.
 */
fun processChar(char: Char) {
    println("Char => $char (${char.toLong()})")
}