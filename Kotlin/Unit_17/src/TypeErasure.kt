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
import com.makotogo.learn.kotlin.util.createEmployee
import com.makotogo.learn.kotlin.util.createPerson
import com.makotogo.learn.kotlin.util.createWorker

fun process(objectContainer: ObjectContainer<Person>) {
    objectContainer.print()
}

// Uncomment and get a compile error:
//Error:(33, 1) Kotlin: Platform declaration clash: The following declarations have the same JVM signature (process(Lcom/makotogo/learn/kotlin/ObjectContainer;)V):
//fun process(objectContainer: ObjectContainer<Person>): Unit defined in com.makotogo.learn.kotlin in file TypeErasure.kt
//fun process(objectContainer: ObjectContainer<Worker>): Unit defined in com.makotogo.learn.kotlin in file TypeErasure.kt
/*
fun process(objectContainer: ObjectContainer<Worker>) {
    objectContainer.print()
}
*/

/**
 * The ubiquitous main function. We meet again.
 */
fun main(args: Array<String>) {
    val people = ObjectContainer<Person>()
    people.add(createPerson())
    people.add(createWorker())
    people.add(createEmployee())

    process(people)

    val workers = ObjectContainer<Worker>()
    workers.add(createWorker())
    workers.add(createEmployee())

    // Uncomment and get a compile error:
    // Error:(54, 13) Kotlin: Type mismatch: inferred type is ObjectContainer<Worker> but ObjectContainer<Person> was expected
    // process(workers)
}