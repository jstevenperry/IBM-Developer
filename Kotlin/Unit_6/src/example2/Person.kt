package com.makotogo.learn.kotlin.example2

import java.time.LocalDate

class Person(val givenName: String, val familyName: String, val dateOfBirth: LocalDate)

fun main(args: Array<String>) {
    val person = Person(
            "Susan",
            "Neumann",
            LocalDate.of(1980, 3, 17) // 17 Mar 1980
    )

    println("Person: Family Name=${person.familyName}, " +
            "Given Name=${person.givenName}, " +
            "Date of Birth=${person.dateOfBirth}")
}
