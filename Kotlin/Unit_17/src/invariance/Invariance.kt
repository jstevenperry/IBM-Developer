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

package com.makotogo.learn.kotlin.invariance

import com.makotogo.learn.kotlin.ObjectContainer
import com.makotogo.learn.kotlin.model.Person
import com.makotogo.learn.kotlin.model.Worker
import com.makotogo.learn.kotlin.util.createPerson
import com.makotogo.learn.kotlin.util.createWorker

/**
 * Process Person object
 */
fun processPerson(person: Person) {
    println("Person: $person")
}

/**
 * Process ObjectContainer<Person>
 */
fun copyPeople(source: ObjectContainer<Person>): ObjectContainer<Person> {
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

fun main(args: Array<String>) {
    //
    // Create a Person object
    val person = createPerson()
    //
    // Create a Worker object
    val worker = createWorker()
    //
    // Process Person (through Person)
    processPerson(person)
    // Process Person (through Worker) - works great!
    processPerson(worker)
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
    // Spoiler alert: this won't work out so well...
    // Uncomment the lines below to get the compile error:
//    val otherWorkerContainer = copyPeople(workerObjectContainer)
//    println("Copy of Worker container:")
//    otherWorkerContainer.print()
}