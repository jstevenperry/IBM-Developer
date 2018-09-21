package com.makotogo.learn.kotlin.comparison

const val twoInt = 2
const val threeInt = 3

const val twoString = "2"
const val threeString = "3"

fun lessThan() {
    println("********** lessThan() **********")

    println("Expression 2 < 3: ${twoInt < threeInt}")
    println("Expression 3 < 2: ${threeInt < twoInt}")
    println("Expression 2 < 2: ${twoInt < twoInt}")

    println("********** lessThanNumericString() **********")

    println("Expression \"2\" < \"3\": ${twoString < threeString}")
    println("Expression \"3\" < \"2\": ${threeString < twoString}")
    println("Expression \"2\" < \"2\": ${twoString < twoString}")
}

fun lessThanOrEqualTo() {
    println("********** lessThanOrEqualTo() **********")

    println("Expression 2 <= 3: ${twoInt <= threeInt}")
    println("Expression 3 <= 2: ${threeInt <= twoInt}")
    println("Expression 2 <= 2: ${twoInt <= twoInt}")

    println("********** lessThanOrEqualToNumericString() **********")

    println("Expression \"2\" <= \"3\": ${twoString <= threeString}")
    println("Expression \"3\" <= \"2\": ${threeString <= twoString}")
    println("Expression \"2\" <= \"2\": ${twoString <= twoString}")
}

fun greaterThan() {
    println("********** greaterThan() **********")

    println("Expression 2 > 3: ${twoInt > threeInt}")
    println("Expression 3 > 2: ${threeInt > twoInt}")
    println("Expression 2 > 2: ${twoInt > twoInt}")

    println("********** greaterThanNumericString() **********")

    println("Expression \"2\" > \"3\": ${twoString > threeString}")
    println("Expression \"3\" > \"2\": ${threeString > twoString}")
    println("Expression \"2\" > \"2\": ${twoString > twoString}")
}

fun greaterOrEqualToThan() {
    println("********** greaterOrEqualToThan() **********")

    println("Expression 2 >= 3: ${twoInt >= threeInt}")
    println("Expression 3 >= 2: ${threeInt >= twoInt}")
    println("Expression 2 >= 2: ${twoInt >= twoInt}")

    println("********** greaterOrEqualToThanNumericString() **********")

    println("Expression \"2\" >= \"3\": ${twoString >= threeString}")
    println("Expression \"3\" >= \"2\": ${threeString >= twoString}")
    println("Expression \"2\" >= \"2\": ${twoString >= twoString}")
}

fun main(args: Array<String>) {
    // <
    lessThan()
    // <=
    lessThanOrEqualTo()
    // >
    greaterThan()
    // >=
    greaterOrEqualToThan()
}