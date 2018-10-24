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
import com.makotogo.learn.kotlin.util.createEmployee
import com.makotogo.learn.kotlin.util.createPerson
import com.makotogo.learn.kotlin.util.createWorker

/**
 * A container of objects.
 *
 * Demonstrates a simple generic class.
 */
class ObjectContainer<T> {

    /**
     * The container's backing store
     */
    private val backingStore = ArrayList<T>()

    /**
     * Add the specified item
     */
    fun add(item: T) {
        backingStore.add(item)
    }

    /**
     * Clear the contents of the backing store
     */
    fun clear() {
        backingStore.clear()
    }

    /**
     * Get the item at the specified index
     */
    operator fun get(index: Int): T {
        return backingStore[index]
    }

    /**
     * Store the item at the specified index
     */
    operator fun set(index: Int, obj: T) {
        backingStore[index] = obj
    }

    /**
     * Return the size of the backing store
     */
    fun size() = backingStore.size

    /**
     * Print out the contents of the container.
     * One line of output for each item.
     */
    fun print() {
        for (item in backingStore) {
            println(item)
        }
    }

}

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
 * The ubiquitous main function. We meet again.
 * This particular main() is just a test harness.
 * We can't all be rock stars.
 */
fun main(args: Array<String>) {
    //
    // Create a container of Persons (that is, People)
    val humans: ObjectContainer<Person> = ObjectContainer() // This will work
    //
    // Create People of various types
    // Person
    humans.add(createPerson())
    // Worker
    humans.add(createWorker())
    // Employee
    humans.add(createEmployee())
    //
    // Now create another container of People
    val otherHumans = ObjectContainer<Person>() // This works too
    //
    // Add the humans to the otherHumans
    otherHumans.addAll(humans)
    //
    // Print out the new collection
    otherHumans.print()
}