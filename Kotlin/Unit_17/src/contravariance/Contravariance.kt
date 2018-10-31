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
import com.makotogo.learn.kotlin.model.Person
import com.makotogo.learn.kotlin.model.Worker
import com.makotogo.learn.kotlin.util.createEmployee
import com.makotogo.learn.kotlin.util.createWorker
import com.makotogo.learn.kotlin.util.generateRandomEmployeeId
import com.makotogo.learn.kotlin.util.generateRandomTaxIdNumber
import com.makotogo.learn.kotlin.util.generateRandomTitle

/**
 * Hire the specified [Worker], then add them to the employee
 * pool (that is, the [ObjectContainer]). The ObjectContainer<in Employee>
 * variance declaration guarantees the objects within are of type Employee
 * (or a super-class thereof).
 */
fun hire(employees: ObjectContainer<in Employee>, candidate: Worker) {
    //
    // Generate an employee ID, then add them to the
    // container, thus hiring them
    val employeeId = generateRandomEmployeeId()
    val newHire = Employee(
            candidate.familyName,
            candidate.givenName,
            candidate.dateOfBirth,
            generateRandomTaxIdNumber(),
            employeeId,
            generateRandomTitle(employeeId))
    println("Hired Worker: $candidate as Employee: $newHire")
    employees.add(newHire)
}

/**
 * The ubiquitous main function. We meet again.
 */
fun main(args: Array<String>) {
    //
    // Create a container of Person objects
    val employees = ObjectContainer<Person>()
    //
    // Add a few employees
    employees.add(item = createEmployee())
    employees.add(item = createEmployee())
    //
    // Create a Worker
    val worker = createWorker()
    //
    // Now hire them
    hire(employees = employees, candidate = worker)
    //
    // Print out the employees
    println("Printing employees:")
    employees.print()
}