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

import com.makotogo.learn.kotlin.util.createEmployee
import com.makotogo.learn.kotlin.util.createPerson
import com.makotogo.learn.kotlin.util.createWorker

/**
 * A specific class - a container of Long objects
 */
class LongObjectContainer {

    private val backingStore = ArrayList<Long>()

    fun add(item: Long) {
        backingStore.add(item)
    }

    fun clear() {
        backingStore.clear()
    }

    operator fun get(index: Int): Long {
        return backingStore[index]
    }

    operator fun set(index: Int, obj: Long) {
        backingStore[index] = obj
    }

    fun size() = backingStore.size

    fun print() {
        for (item in backingStore) {
            println(item)
        }
    }
}

fun demoLongObjectContainer() {
    println("********** demoLongObjectContainer() **********")
    val objectContainer = LongObjectContainer()

    objectContainer.add(10L)
    objectContainer.add(238L)
    objectContainer.add(5280L)

    println("LongObjectContainer contains: ${objectContainer.size()} items.")

    objectContainer.print()

    objectContainer.clear()
}


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
 * Generic function - declare an ObjectContainer of type T
 * and add objects to it using the supplied object factory.
 *
 * Create another container, then add objects from the first
 * container to it using the addAll() function.
 *
 * Then print out the second container's contents.
 */
fun <T> demoGenericObjectContainer(objectFactory: () -> T) {
    println("********** demoGenericObjectContainer() **********")
    //
    // Create a container of T
    val container: ObjectContainer<T> = ObjectContainer() // This will work
    //
    // Add a few objects using the specified factory
    container.add(objectFactory())
    container.add(objectFactory())
    container.add(objectFactory())
    //
    // Now create another container of T
    val otherContainer = ObjectContainer<T>() // This works too
    //
    // Add the humans to the otherContainer
    otherContainer.addAll(container)
    //
    // Print out the new collection
    otherContainer.print()

    container.clear()
    otherContainer.clear()
}

/**
 * The ubiquitous main function. We meet again.
 * This particular main() is just a test harness.
 * We can't all be rock stars.
 */
fun main(args: Array<String>) {
    //
    // Demo the specific IntObjectContainer class
    demoLongObjectContainer()
    //
    // Demo the generic ObjectContainer<T> class using Person
    demoGenericObjectContainer { createPerson() }
    //
    // Demo the generic ObjectContainer<T> class using Worker
    demoGenericObjectContainer { createWorker() }
    //
    // Demo the generic ObjectContainer<T> class using Employee
    demoGenericObjectContainer { createEmployee() }
}