package com.makotogo.learn.kotlin.example3

import java.time.LocalDate

class Person(val givenName: String, val familyName: String, val dateOfBirth: LocalDate) {
    override fun toString(): String {
        return "[givenName=$givenName, familyName=$familyName, dateOfBirth=$dateOfBirth]"
    }
}

fun main(args: Array<String>) {
    val person = Person(
            "Susan",
            "Neumann",
            LocalDate.of(1980, 3, 17) // 17 Mar 1980
    )
    println(person)
}
