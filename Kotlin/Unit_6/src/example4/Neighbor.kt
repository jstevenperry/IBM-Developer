package com.makotogo.learn.kotlin.example4

fun main(args: Array<String>) {
    println("$publicProperty")
    println("$internalProperty")
    // println("$privateProperty") // Doesn't work - why not?

    val parent = Parent("Parent")
    println("${parent.name}")
    println("${parent.internalClass.name}")

    val internalClass = InternalClass("Neighbor.internalClass")
    println("${internalClass.name}")
}