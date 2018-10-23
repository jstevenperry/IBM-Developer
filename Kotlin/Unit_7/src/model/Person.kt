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

package com.makotogo.learn.kotlin.model

/**
 * Person class - the base class for all humans in this application
 */
open class Person(val familyName: String, val givenName: String)

/**
 * A Person, but a guest with a purpose
 */
class Guest(familyName: String, givenName: String, val purpose: String) : Person(familyName, givenName)

/**
 * A Person, but an employee of Megacorp (with title and everything!)
 */
class Employee(familyName: String, givenName: String, val employeeId: Int, val title: String) : Person(familyName, givenName)

