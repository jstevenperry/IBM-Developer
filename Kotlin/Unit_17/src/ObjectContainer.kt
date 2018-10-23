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

import com.makotogo.learn.kotlin.model.Human
import com.makotogo.learn.kotlin.util.createEmployee
import com.makotogo.learn.kotlin.util.createPerson
import com.makotogo.learn.kotlin.util.createWorker
import com.makotogo.learn.kotlin.util.generateRandomDateOfBirth
import java.time.LocalDate

class ObjectContainer<T> {

    private val backingStore = ArrayList<T>()

    fun add(item: T) {
        backingStore.add(item)
    }

    fun get(index: Int): T {
        return backingStore[index]
    }

    fun print() {
        for (item in backingStore) {
            println(item)
        }
    }

}

fun main(args: Array<String>) {
    val objectContainer: ObjectContainer<Human> = ObjectContainer()

    objectContainer.add(createPerson())

    objectContainer.add(createWorker())

    objectContainer.add(createEmployee())

    objectContainer.add(object : Human {
        override val dateOfBirth: LocalDate
            get() = generateRandomDateOfBirth()

        override fun toString(): String {
            return "(Anonymous Human): dateOfBirth=$dateOfBirth"
        }
    })

    objectContainer.print()
}