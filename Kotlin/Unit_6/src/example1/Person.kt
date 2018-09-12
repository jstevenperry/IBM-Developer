package com.makotogo.learn.kotlin.example1

class Person(val name: String)

fun main(args: Array<String>) {
    val person = Person("Joe")

    println("Person: Name=${person.name}")
}