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

package com.makotogo.learn.kotlin.smartcast

import com.makotogo.learn.kotlin.model.Person
import com.makotogo.learn.kotlin.model.Guest
import com.makotogo.learn.kotlin.model.Employee

fun formatName(person: Person) = "${person.familyName}, ${person.givenName}"

private fun purposeGrantsEntry(purpose: String) : Boolean {
    return purpose == "Maintenance"
    // Other purposes would go here
}

fun admitEntrance(something: Any) : Boolean {
    var ret: Boolean = false
    if (something is Employee) {
        ret = true
        println("Employee access granted for ${something.title}: ${formatName(something)}.")
    } else if (something is Guest) {
        if (purposeGrantsEntry(something.purpose)) {
            ret = true
            println("Guest access granted for the purpose of ${something.purpose}: ${formatName(something)}.")
        } else {
            println("Access Denied, purpose: ${something.purpose}: ${formatName(something)}.")
        }
    } else if (something is Person) {
        println("Access Denied, ${formatName(something)}, you are but a mere Person.")
    } else {
        println("Access denied, $something, you could be anything (including not a Person)")
    }
    return ret
}

fun main(args: Array<String>) {
    val joeSmith = Person(givenName = "Joe", familyName = "Smith")
    val janeAnderson = Guest(purpose = "Maintenance", familyName = "Anderson", givenName = "Jane")
    val jackDavis = Guest(purpose = "Unknown", familyName = "Davis", givenName = "Jack")
    val valerieJones = Employee(title = "CEO", familyName = "Jones", givenName = "Valerie", employeeId = 1)
    val gardenGnome = Any()

    admitEntrance(joeSmith)
    admitEntrance(janeAnderson)
    admitEntrance(jackDavis)
    admitEntrance(valerieJones)
    admitEntrance(gardenGnome)
}