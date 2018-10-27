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

package com.makotogo.learn.kotlin.model

import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Interface - for things that have identity
 */
interface Identifiable {
    /**
     * Property - identity for the object
     */
    val identity: String

    /**
     * Function - identify the object
     */
    fun identify(): String {
        return "Identity: $identity"
    }
}

/**
 * Interface - marks an object as Human
 * Also Identifiable and Configurable
 */
interface Human {
    /**
     * Property - when the Human was born
     */
    val dateOfBirth: LocalDate
}

/**
 * Person class - subclass of Human
 */
abstract class Person(
        val familyName: String,
        val givenName: String,
        final override val dateOfBirth: LocalDate) : Human, Identifiable {

    /**
     * Abstract property
     */
    abstract val whenCreated: LocalDateTime

    /**
     * toString() override
     */
    override fun toString(): String {
        return "Person(identity=$identity, whenCreated=$whenCreated, familyName=$familyName, givenName=$givenName, dateOfBirth=$dateOfBirth)"
    }
}

