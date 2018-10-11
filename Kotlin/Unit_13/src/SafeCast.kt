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
import com.makotogo.learn.kotlin.javainterop.createGuest
import com.makotogo.learn.kotlin.javainterop.createPerson
import com.makotogo.learn.kotlin.javainterop.createWorker
import com.makotogo.learn.kotlin.javainterop.generateRandomInt
import com.makotogo.learn.kotlin.model.Person

/**
 * Returns a random object from the mysteryArray.
 */
fun mysteryObject(): Any? {
    val mysteryArray = arrayOf(
            null,
            23,
            createGuest(),
            42L,
            "Hello?",
            '>',
            createPerson(),
            1.0f,
            Double.NaN,
            true,
            createWorker())
    return mysteryArray[generateRandomInt(mysteryArray.size)]
}

fun mysteryBoxObject() {
    println("********** mysteryBoxObject() **********")
    //
    // Get an object from the mystery box
    val mysteryObject = mysteryBox.mysteryObject()
    //
    // Safe cast - gives null if the type is wrong at runtime
    val safePerson: Person? = mysteryObject as? Person
    println("Safe-cast Person: $safePerson")
    //
    // Unsafe cast - gives ClassCastException if the type is wrong at runtime
    val unsafePerson: Person = mysteryObject as Person
    println("UNSAFE-CAST Person: $unsafePerson")
}

fun localMysteryObject() {
    println("********** localMysteryObject() **********")
    //
    // Let's try this again with the mysteryObject function
    val mysteryBoxObject = mysteryObject()
    //
    // Safe cast
    println("Safe-cast Person: ${(mysteryBoxObject as? Person)}")
    //
    // Unsafe cast
    println("UNSAFE-CAST Person: ${mysteryBoxObject as Person?}")
}

/**
 * The ubiquitous main function. We meet again.
 */
fun main(args: Array<String>) {
    for (iteration in 1..10) {
        println("********** iteration # $iteration **********")
        mysteryBoxObject()
        localMysteryObject()
    }
}
