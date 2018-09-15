package com.makotogo.learn.kotlin.numericconversion

fun widen() {
    // Widening conversions
    val byte: Byte = 1
    println("Byte value is $byte")

    // val short: Short = byte // Compile error!
    val short: Short = byte.toShort()
    println("Short value is $short")

    val int: Int = short.toInt()
    println("Int value is $int")

    val long: Long = int.toLong()
    println("Long value is $long")

    val float: Float = long.toFloat()
    println("Float value is $float")

    val double: Double = float.toDouble()
    println("Double value is $double")
}

fun narrow() {
    // Narrowing conversions truncate
    val double: Double = 1.2345E100
    println("Double value is $double")

    val float: Float = double.toFloat()
    println("Float value is $float")

    val long: Long = float.toLong()
    println("Long value is $long")

    val int: Int = long.toInt()
    println("Int value is $int")

    val short: Short = int.toShort()
    println("Short value is $short")

    val byte: Byte = short.toByte()
    println("Byte value is $byte")


}

fun main(args: Array<String>) {
    widen()
    narrow()
}