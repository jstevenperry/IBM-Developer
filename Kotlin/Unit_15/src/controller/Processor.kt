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

import com.makotogo.learn.kotlin.model.Configurable
import com.makotogo.learn.kotlin.model.Human
import com.makotogo.learn.kotlin.model.Identifiable
import com.makotogo.learn.kotlin.model.Marked
import com.makotogo.learn.kotlin.model.Nameable
import com.makotogo.learn.kotlin.util.createPerson
import com.makotogo.learn.kotlin.util.createWorker

/**
 * Class to process objects through interfaces and classes.
 */
class Processor {

    /**
     * Process the object through the Marked interface
     */
    fun process(marked: Marked) {
        println("This object is Marked: $marked")
    }

    /**
     * Process the object through the Configurable interface
     */
    fun process(configurable: Configurable) {
        println("This object is Configurable: $configurable")
    }

    /**
     * Process the object through the Identifiable interface
     */
    fun process(identifiable: Identifiable) {
        println("This object is Identifiable: $identifiable")
    }

    /**
     * Process the object through the Nameable interface
     */
    fun process(nameable: Nameable) {
        println("This object is Nameable: $nameable")
    }

    /**
     * Process the object through the Human interface
     */
    fun process(human: Human) {
        println("This object is Human: $human")
    }

}


fun workerDemo() {
    println("********** workerDemo() **********")
    //
    // Create Processor object
    val processor = Processor()
    //
    // Create Worker object
    val worker = createWorker()
    //
    // Marked
    processor.process(worker as Marked)
    //
    // Configurable
    processor.process(worker as Configurable)
    //
    // Identifiable
    processor.process(worker as Identifiable)
    //
    // Nameable
    processor.process(worker as Nameable)
    //
    // Human
    processor.process(worker as Human)
}

fun personDemo() {
    println("********** personDemo() **********")
    //
    // Create Processor object
    val processor = Processor()
    //
    // Create Person object
    val person = createPerson()
    //
    // Can't do this...
    // processor.process(person as Marked)
    //
    // Configurable
    processor.process(person as Configurable)
    //
    // Identifiable
    processor.process(person as Identifiable)
    //
    // Nameable
    processor.process(person as Nameable)
    //
    // Human
    processor.process(person as Human)
}

/**
 * The ubiquitous main function. We meet again.
 */
fun main(args: Array<String>) {
    //
    // Run the Person demo
    personDemo()
    //
    // Run the Worker demo
    workerDemo()
}