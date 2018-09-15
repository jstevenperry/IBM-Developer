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

