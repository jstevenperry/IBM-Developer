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

package com.makotogo.learn.kotlin.controller

import com.makotogo.learn.kotlin.model.Employee
import com.makotogo.learn.kotlin.model.Guest
import com.makotogo.learn.kotlin.model.Person
import com.makotogo.learn.kotlin.model.Worker

/**
 * Perform a security check
 */
class SecurityCheck() {

    /**
     * Returns true if the specified [purpose] is one that
     * grants entry, false otherwise.
     */
    private fun purposeGrantsEntry(purpose: String?): Boolean {
        return purpose == "Maintenance" ||
                purpose == "Package Delivery" ||
                purpose == "Consulting" ||
                purpose == "Family Member"
    }

    /**
     * Admit entrance to the facility based on the contents
     * of the [person] object that is passed.
     */
    fun admitEntrance(person: Person) {
        return when (person) {
            is Employee -> {
                println("Employee access granted for ${person.title}: ${formatName(person)}.")
            }
            is Guest -> {
                if (purposeGrantsEntry(person.purpose)) {
                    println("Guest access granted for the purpose of ${person.purpose}: ${formatName(person)}.")
                } else {
                    val message = "Access Denied, purpose: ${person.purpose}: ${formatName(person)}."
                    println(message)
                    throwAccessException(message = message)
                }
            }
            is Worker -> {
                val message = "Access Denied: ${formatName(person)}."
                println(message)
                throwAccessException(message = message)
            }
            else -> {
                val message = "Access Denied, ${formatName(person)}, you are but a mere Person."
                println(message)
                throwAccessException(message = message)
            }
        }
    }
}
