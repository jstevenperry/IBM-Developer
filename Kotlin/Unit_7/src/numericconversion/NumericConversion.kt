/*
 * Copyright 2018 Makoto Consulting Group, Inc
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

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
    val double = 1.2345E100
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