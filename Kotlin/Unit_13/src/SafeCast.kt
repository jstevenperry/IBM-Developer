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
import com.makotogo.learn.kotlin.model.Person

/**
 * The ubiquitous main function. We meet again.
 */
fun main(args: Array<String>) {
    //
    // Run this several times. Since the MysteryBox produces good
    // data most of the time, we need to run this a few times
    // before getting bad data...
    for (iteration in 1..100) {
        println("********** Iteration# $iteration **********")
        //
        // Perform safe cast
        val person: Person? = mysteryBox.mysteryObject() as? Person
        //
        // Now process unless it is null (thanks, safe cast!)
        if (person != null) {
            println("Safe-cast Person: $person")
        } else {
            //
            // Value was safe-cast to null
            println("Safe-cast Person: Expecting Person object, but got some other type instead (possibly null).")
        }
        //
        // Perform unsafe cast
        val unsafePerson: Person? = mysteryBox.mysteryObject() as Person?
        if (unsafePerson != null) {
            println("UNSAFE-CAST Person: $unsafePerson")
        } else {
            println("UNSAFE-CAST Person: Expecting Person object, but got null instead.")
        }
    }
}
