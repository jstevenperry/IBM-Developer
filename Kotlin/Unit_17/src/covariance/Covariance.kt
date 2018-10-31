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
import com.makotogo.learn.kotlin.util.createPerson
import com.makotogo.learn.kotlin.util.createWorker

/**
 * Copy all Person (or subclass thereof) objects in the source
 * ObjectContainer to a new ObjectContainer and return it.
 *
 * The <out Person> site variance declaration guarantees that
 * source contains objects of type Person, or a subclass thereof.
 */
fun copyPeople(source: ObjectContainer<out Person>): ObjectContainer<Person> {
    //
    // Return value is a new container with a copy of the source container
    val ret = ObjectContainer<Person>()
    //
    // Loop through the source and add all items to the destination
    for (index in 0 until source.size()) {
        ret.add(source[index])
    }
    //
    return ret
}

/**
 * The ubiquitous main function. We meet again.
 */
fun main(args: Array<String>) {
    //
    // Create a ObjectContainer<Person>
    val personObjectContainer = ObjectContainer<Person>()
    personObjectContainer.add(createPerson())
    //
    // Copy Person container (through ObjectContainer<Person>)
    val otherPersonContainer = copyPeople(personObjectContainer)
    println("Copy of Person container:")
    otherPersonContainer.print()
    //
    // Create a ObjectContainer<Worker>
    val workerObjectContainer = ObjectContainer<Worker>()
    workerObjectContainer.add(createWorker())
    //
    // Copy Person container (through ObjectContainer<Worker>)
    val otherWorkerContainer = copyPeople(workerObjectContainer)
    println("Copy of Worker container:")
    otherWorkerContainer.print()
}