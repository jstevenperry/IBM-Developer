package com.makotogo.learn.kotlin.unit

fun print(stringToPrint: String) {
    println(stringToPrint)
}

// Same as

fun printString(stringToPrint: String) : Unit {
    println(stringToPrint)
}

fun main(args: Array<String>) {
    print(stringToPrint = "Hello, Unit")
    printString("Hello, Unit")
}