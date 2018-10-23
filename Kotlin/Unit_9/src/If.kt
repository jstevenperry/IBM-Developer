/*
 * Copyright 2018 Makoto Consulting Group, Inc
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

package com.makotogo.learn.kotlin.conditional

import com.makotogo.learn.kotlin.model.Person
import com.makotogo.learn.kotlin.model.Guest
import com.makotogo.learn.kotlin.model.Employee

fun formatName(person: Person) = "${person.familyName}, ${person.givenName}"

private fun purposeGrantsEntry(purpose: String) : Boolean {
    return purpose == "Maintenance"
    // Other purposes would go here
}

fun admitEntrance(person: Person) : Boolean {
    var ret = false
    if (person is Employee) {
        ret = true
        println("Employee access granted for ${person.title}: ${formatName(person)}.")
    } else if (person is Guest) {
        if (purposeGrantsEntry(person.purpose)) {
            ret = true
            println("Guest access granted for the purpose of ${person.purpose}: ${formatName(person)}.")
        } else {
            println("Access Denied, purpose: ${person.purpose}: ${formatName(person)}.")
        }
    } else {
        println("Access Denied, ${formatName(person)}, you are but a mere Person.")
    }
    return ret
}

fun admitEntranceWithExpression(person: Person) : Boolean {
    return if (person is Employee) {
        println("Employee access granted for ${person.title}: ${formatName(person)}.")
        true
    } else if (person is Guest) {
        if (purposeGrantsEntry(person.purpose)) {
            println("Guest access granted for the purpose of ${person.purpose}: ${formatName(person)}.")
            true
        } else {
            println("Access Denied, purpose: ${person.purpose}: ${formatName(person)}.")
            false
        }
    } else {
        println("Access Denied, ${formatName(person)}, you are but a mere Person.")
        false
    }
}

fun myFunc(isTrue: Boolean) : String {
    return if (isTrue) {
        true.toString()
    } else {
        false.toString()
    }
}

fun main(args: Array<String>) {
    val joeSmith = Person(givenName = "Joe", familyName = "Smith")
    val janeAnderson = Guest(purpose = "Maintenance", familyName = "Anderson", givenName = "Jane")
    val jackDavis = Guest(purpose = "Unknown", familyName = "Davis", givenName = "Jack")
    val valerieJones = Employee(title = "CEO", familyName = "Jones", givenName = "Valerie", employeeId = 1)

    admitEntrance(joeSmith)
    admitEntranceWithExpression(joeSmith)

    admitEntrance(janeAnderson)
    admitEntranceWithExpression(janeAnderson)

    admitEntrance(jackDavis)
    admitEntranceWithExpression(jackDavis)

    admitEntrance(valerieJones)
    admitEntranceWithExpression(valerieJones)

    println("My function returns: ${myFunc(true)}")
    println("My function returns: ${myFunc(false)}")
}