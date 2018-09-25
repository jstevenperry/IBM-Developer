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

package com.makotogo.learn.kotlin.hashcode

import com.makotogo.learn.kotlin.model.Person

fun hashCodeNumber() {
    println("********** hashCodeNumber() **********")

    val oneHundredByte: Byte = 100
    println("$oneHundredByte: oneHundredByte.hashCode() = ${oneHundredByte.hashCode()}")
    println("${oneHundredByte + oneHundredByte}: (oneHundredByte+oneHundredByte).hashCode() = ${(oneHundredByte + oneHundredByte).hashCode()}")

    val twoHundredShort: Short = 200
    println("$twoHundredShort: twoHundredShort.hashCode() = ${twoHundredShort.hashCode()}")
    println("${twoHundredShort + twoHundredShort}: (twoHundredShort+twoHundredShort).hashCode() = ${(twoHundredShort + twoHundredShort).hashCode()}")

    val twoHundredInt = 200
    println("$twoHundredInt: twoHundredInt.hashCode() = ${twoHundredInt.hashCode()}")
    println("${twoHundredInt + twoHundredInt}: (twoHundredInt+threeHundredInt).hashCode() = ${(twoHundredInt + twoHundredInt).hashCode()}")

    val twoHundredLong = 200L
    println("$twoHundredLong: twoHundredLong.hashCode() = ${twoHundredLong.hashCode()}")
    println("${(twoHundredLong + twoHundredLong)} (twoHundredLong+threeHundredLong).hashCode() = ${(twoHundredLong + twoHundredLong).hashCode()}")

    val twoHundredFloat = 200.0f
    println("$twoHundredFloat: twoHundredFloat.hashCode() = ${twoHundredFloat.hashCode()}")

    val twoHundredDouble = 200.0
    println("$twoHundredDouble: twoHundredDouble.hashCode() = ${twoHundredDouble.hashCode()}")
}

fun hashCodeChar() {
    println("********** hashCodeChar() **********")

    val aChar = 'a'
    println("$aChar: aChar.hashCode() = ${aChar.hashCode()}")

    val AChar = 'A'
    println("$AChar: AChar.hashCode() = ${AChar.hashCode()}")

    val lcurlyBraceChar = '{'
    println("$lcurlyBraceChar: lcurlyBraceChar.hashCode() = ${lcurlyBraceChar.hashCode()}")

    val rcurlyBraceChar = '}'
    println("$rcurlyBraceChar: rcurlyBraceChar.hashCode() = ${rcurlyBraceChar.hashCode()}")
}

fun hashCodeBoolean() {
    println("********** hashCodeBoolean() **********")

    val btrue = true
    println("$btrue: btrue.hashCode() = ${btrue.hashCode()}")

    val bfalse = false
    println("$bfalse: bfalse.hashCode() = ${bfalse.hashCode()}")
}

fun hashCodeNumericString() {
    println("********** hashCodeNumericString() **********")

    val twoHundredString = "200"
    println("$twoHundredString: twoHundredString.hashCode() = ${twoHundredString.hashCode()}")

    val threeHundredString = "300"
    println("$threeHundredString: threeHundredString.hashCode() = ${threeHundredString.hashCode()}")

    val banana = "banana"
    println("$banana: banana.hashCode() = ${banana.hashCode()}")

}

fun hashCodePerson() {
    println("********** hashCodePerson() **********")

    val doug = Person("Davis", "Douglas")
    println("$doug: doug.hashCode() = ${doug.hashCode()}")

    val dougClone = Person("Davis", "Douglas")
    println("$dougClone: dougClone.hashCode() = ${dougClone.hashCode()}")

    val anotherPerson = doug
    println("$anotherPerson: anotherPerson.hashCode() = ${anotherPerson.hashCode()}")

}

fun main(args: Array<String>) {
    hashCodeNumber()
    hashCodeChar()
    hashCodeBoolean()
    hashCodeNumericString()
    hashCodePerson()
}