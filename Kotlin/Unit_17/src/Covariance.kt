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

import com.makotogo.learn.kotlin.model.Employee
import com.makotogo.learn.kotlin.model.Worker
import com.makotogo.learn.kotlin.util.createEmployee

/**
 * Copy all Worker (or subclass thereof) objects in the source
 * ObjectContainer to the destination.
 *
 * The <out Worker> site variance declaration guarantees that
 * source contains objects of type Worker, or a subclass thereof.
 */
fun copyWorkers(destination: ObjectContainer<Worker>, source: ObjectContainer<out Worker>) {
    //
    // Loop through the source and add all items to the destination
    for (index in 0 until source.size()) {
        destination.add(source[index])
    }
}

/**
 * The ubiquitous main function. We meet again.
 */
fun main(args: Array<String>) {
    //
    // Create a container of Employees
    val employees = ObjectContainer<Employee>()
    //
    // Add a few employees
    employees.add(item = createEmployee())
    employees.add(item = createEmployee())
    //
    // Print out the contents
    println("Printing employees:")
    employees.print()
    //
    // Make a copy of employees
    val workers = ObjectContainer<Worker>()
    copyWorkers(destination = workers, source = employees)
    println("Printing workers:")
    workers.print()
}