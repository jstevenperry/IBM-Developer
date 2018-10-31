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

import com.makotogo.learn.kotlin.model.Person
import com.makotogo.learn.kotlin.model.Worker
import com.makotogo.learn.kotlin.util.createWorker

/**
 * Extension function - addAll
 *
 * Adds all of the items in the specified ObjectContainer<out Person>
 * to the receiver.
 *
 * Items in the ObjectContainer<out T> parameter are guaranteed to be
 * of type T or a subclass of T.
 */
/*
fun ObjectContainer<Person>.addAll(other: ObjectContainer<out Person>) {
    //
    // Zip through the "other" container and add all of
    // its items to "this" container
    for (index in 0 until other.size()) {
        this.add(other[index])
    }
}
*/

/**
 * Extension function - addAll
 *
 * Demonstrates a generic function.
 *
 * Adds all of the items in the specified ObjectContainer<out T>
 * to the receiver.
 *
 * Items in the ObjectContainer<out T> parameter are guaranteed to be
 * of type T or a subclass of T.
 */
fun <T> ObjectContainer<T>.addAll(other: ObjectContainer<out T>) {
    //
    // Zip through the "other" container and add all of
    // its items to "this" container
    for (index in 0 until other.size()) {
        this.add(other[index])
    }
}

/**
 * The ubiquitous main() function. We meet again.
 */
fun main(args: Array<String>) {
    val workers = ObjectContainer<Worker>()
    workers.add(createWorker())
    workers.add(createWorker())

    val otherPeople = ObjectContainer<Person>()
    otherPeople.addAll(workers)
}