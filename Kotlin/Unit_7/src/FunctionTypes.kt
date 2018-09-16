package com.makotogo.learn.kotlin.functiontypes

/**
 * Add the [first] and [second] parameter values and
 * return the result.
 *
 * This function is implemented as a Block Body Function
 */
fun addTwoBlock(first: Int, second: Int) : Int {
    return first + second
}

/**
 * Add the [first] and [second] parameter values and
 * return the result.
 *
 * This function is implemented as an Expression Body Function
 */
fun addTwoExpression(first: Int, second: Int) = first + second

/**
 * Main function to drive the program
 */
fun main(args: Array<String>) {
    println("Add 2 + 2 via Block Body Function: ${addTwoBlock(2, 2)}")
    println("Add 2 + 2 via Expression Body Function: ${addTwoExpression(2, 2)}")
}