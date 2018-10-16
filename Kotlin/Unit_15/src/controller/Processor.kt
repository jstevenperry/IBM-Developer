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

import com.makotogo.learn.kotlin.model.Identifiable
import com.makotogo.learn.kotlin.util.createPerson
import com.makotogo.learn.kotlin.util.createWorker

/**
 * Class to process objects through interfaces and classes.
 */
class Processor {
    /**
     * Process the identifiable object
     */
    fun process(identifiable: Identifiable) {
        println("Identifiable: ${identifiable.identify()}")
    }

}

fun workerDemo(processor: Processor) {
    //
    // Create Worker object
    val worker = createWorker()
    //
    // Process the worker through the Identifiable interface
    val identifiable = worker as Identifiable
    processor.process(identifiable)
}

fun personDemo(processor: Processor) {
    //
    // Create Person object
    val person = createPerson()
    //
    // Process the person through the Identifiable interface
    val identifiable = person as Identifiable
    processor.process(identifiable)
}

/**
 * The ubiquitous main function. We meet again.
 */
fun main(args: Array<String>) {
    //
    // Create Processor object
    val processor = Processor()
    //
    // Run the Person demo
    personDemo(processor)
    //
    // Run the Worker demo
    workerDemo(processor)
}