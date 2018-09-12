package com.makotogo.learn.kotlin.example2

import java.time.LocalDate

object foo

class Person(val name: String, val dateOfBirth: LocalDate)

fun main(args: Array<String>) {
    val person = Person(
            "Joe",
            LocalDate.of(1980, 3, 17)
    )

    println("Person: Name=${person.name}, Date of Birth=${person.dateOfBirth}")
}
