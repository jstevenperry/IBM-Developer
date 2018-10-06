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
package com.makotogo.learn.kotlin

import com.makotogo.learn.kotlin.model.Employee
import com.makotogo.learn.kotlin.model.Worker
import com.makotogo.learn.kotlin.util.generateRandomEmployeeId
import com.makotogo.learn.kotlin.util.generateRandomFamilyName
import com.makotogo.learn.kotlin.util.generateRandomGivenName
import com.makotogo.learn.kotlin.util.generateRandomTaxIdNumber
import com.makotogo.learn.kotlin.util.generateRandomTitle
import com.makotogo.learn.kotlin.util.generateRandomYearMonthDayTriple
import com.makotogo.learn.kotlin.util.toLocalDate
import java.time.LocalDate

/**
 * An PoKoEmployee, subclass of PokoEmployee with only one difference:
 * data
 *
 * To see this code in action, run the main() function,
 * run it once, then uncomment the "data" keyword and
 * run it again. What a different, huh? Huh?
 *
 */
class PoKoEmployee(override val familyName: String,
                   override val givenName: String,
                   override val dateOfBirth: LocalDate,
                   override val taxIdNumber: String,
                   val employeeId: Int,
                   val title: String) : Worker(familyName, givenName, dateOfBirth, taxIdNumber) {
    override fun toString(): String {
        return "PoKoEmployee(${super.toString()}, employeeId=$employeeId, title=$title)"
    }
}

/**
 * Demo the auto-generated
 * equals() function
 */
fun equalsDemo(employee1: Worker, employee2: Worker) {
    //
    println("********** equalsDemo() **********")

    if (employee1 == employee2) {
        println("The two Employees are equal.")
    } else {
        println("The two Employees are not equal!")
    }
}

/**
 * Demo the auto-generated
 * hashCode() function
 */
fun hashCodeDemo(employee1: Worker, employee2: Worker) {
    //
    println("********** hashCodeDemo() **********")

    println("Employee 1 hashCode(): ${employee1.hashCode()}")
    println("Employee 2 hashCode(): ${employee2.hashCode()}")

}

/**
 * Demo the auto-generated
 * toString() function
 */
fun toStringDemo(poKoEmployee1: Worker, poKoEmployee2: Worker) {
    //
    println("********** toStringDemo() **********")

    println("Employee 1 toString(): $poKoEmployee1")
    println("Employee 2 toString(): $poKoEmployee2")

}

/**
 * Demo the auto-generated
 * copy() function
 *
 * Uncomment this function when you have the "data" keyword
 * in the definition of Employee
 */
fun copyDemo(employee1: Employee, employee2: Employee) {
    //
    println("********** copyDemo() **********")
    //
    // Print out the Employee
    println("Employee 1: $employee1, hashCode=${employee2.hashCode()}")
    //
    // Make a copy of the poKoEmployee1 object and just change the
    // family name (add the word COPY)
    val employeeCopy = employee1.copy(familyName = employee2.familyName + "COPY")
    //
    // Print it out
    println("Employee 1 COPY: $employeeCopy, hashCode=${employeeCopy.hashCode()}")
}

/**
 * Create a data class PoKoEmployee
 */
fun createPoKoEmployee(familyName: String,
                       givenName: String,
                       dateOfBirth: LocalDate,
                       taxIdNumber: String,
                       employeeId: Int,
                       title: String) =
        PoKoEmployee(
                familyName = familyName,
                givenName = givenName,
                dateOfBirth = dateOfBirth,
                taxIdNumber = taxIdNumber,
                employeeId = employeeId,
                title = title)

/**
 * The ubiquitous main function. We meet again.
 */
fun main(args: Array<String>) {

    // Random attributes
    val familyName = generateRandomFamilyName()
    val givenName = generateRandomGivenName()
    val dateOfBirth = generateRandomYearMonthDayTriple().toLocalDate()
    val taxIdNumber = generateRandomTaxIdNumber()
    val employeeId = generateRandomEmployeeId()
    val title = generateRandomTitle(employeeId)

    // PoKoEmployee 1 - Plain Old Kotlin Object Class
    val poKoEmployee1 = createPoKoEmployee(familyName, givenName, dateOfBirth, taxIdNumber, employeeId, title)
    // PoKoEmployee 2 (same as poKoEmployee 1)
    val poKoEmployee2 = createPoKoEmployee(familyName, givenName, dateOfBirth, taxIdNumber, employeeId, title)

    // Employee 1 - Data Class
    val employee1 = Employee(familyName, givenName, dateOfBirth, taxIdNumber, employeeId, title)
    // Employee 2 - same as employee 1
    val employee2 = Employee(familyName, givenName, dateOfBirth, taxIdNumber, employeeId, title)

    // equals() demo
    equalsDemo(poKoEmployee1, poKoEmployee2)
    equalsDemo(employee1, employee2)

    // hashCode() demo
    hashCodeDemo(poKoEmployee1, poKoEmployee2)
    hashCodeDemo(employee1, employee2)

    // toString() demo
    toStringDemo(poKoEmployee1, poKoEmployee2)
    toStringDemo(employee1, employee2)

    // copy() demo
    // No copy() function on PoKoEmployee
    copyDemo(employee1, employee2)
}