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

package com.makotogo.learn.kotlin.equality

import com.makotogo.learn.kotlin.model.Person

fun equalsNumber() {
    println("********** equalsNumber() **********")
    println("200: hashCode = ${200.hashCode()}")
    println("300: hashCode = ${300.hashCode()}")
    println("200+100 = ${200+100}, hashCode = ${(200+100).hashCode()}")
    println("200.equals(200): ${200.equals(200)}")
    println("200.equals(300): ${200.equals(300)}")
    println("200.equals(200+100): ${300.equals(200+100)}")
}

fun equalsNumericString() {
    println("********** equalsNumericString() **********")
    println("\"200\": hashCode = ${"200".hashCode()}")
    println("\"300\": hashCode = ${"300".hashCode()}")
    println("200.toString() = \"${200.toString()}\", hashCode = ${200.toString().hashCode()}")
    println("\"200\".equals(\"200\"): ${"200".equals("200")}")
    println("\"200\".equals(\"300\"): ${"200".equals("300")}")
    println("\"200\".equals(200.toString()): ${"200".equals(200.toString())}")
}

fun equalsPerson() {
    val doug = Person("Davis", "Douglas")
    val dougClone = Person("Davis", "Douglas")
    val sameDoug = doug

    println("********** equalsPerson() **********")
    println("Doug: $doug, hashCode = ${doug.hashCode()}")
    println("Doug's clone: $dougClone, hashCode = ${dougClone.hashCode()}")
    println("doug.equals(dougClone): ${doug.equals(dougClone)}")
    println("doug.equals(sameDoug): ${doug.equals(sameDoug)}")

}

fun structuralEqualsNumber() {
    println("********** structuralEqualsNumber() **********")
    println("200: hashCode = ${200.hashCode()}")
    println("300: hashCode = ${300.hashCode()}")
    println("200+100: hashCode = ${(200+100).hashCode()}")
    println("200 == 200: ${200 == 200}")
    println("200 != 200: ${200 != 200}")
    println("200 == 300: ${200 == 300}")
    println("200 != 300: ${200 != 300}")
    println("300 == (200+100): ${300 == (200+100)}")
    println("300 != (200+100): ${300 != (200+100)}")

}

fun structuralEqualsNumericString() {
    println("********** structuralEqualsNumericString() **********")
    println("\"200\": hashCode = ${"200".hashCode()}")
    println("\"300\": hashCode = ${"300".hashCode()}")
    println("200.toString() = \"${200.toString()}\", hashCode = ${200.toString().hashCode()}")
    println("\"200\" == \"200\": ${"200" == "200"}")
    println("\"200\" != \"200\": ${"200" != "200"}")
    println("\"200\" == \"300\": ${"200" == "300"}")
    println("\"200\" != \"300\": ${"200" != "300"}")
    println("\"200\" == 200.toString(): ${"200" == 200.toString()}")
    println("\"200\" != 200.toString(): ${"200" != 200.toString()}")
}

fun structuralEqualsPerson() {
    val doug = Person("Davis", "Douglas")
    val dougClone = Person("Davis", "Douglas")
    val sameDoug = doug

    println("********** structuralEqualsPerson() **********")
    println("doug: $doug, hashCode = ${doug.hashCode()}")
    println("dougClone: $dougClone, hashCode = ${dougClone.hashCode()}")
    println("anotherPerson: $sameDoug, hashCode = ${sameDoug.hashCode()}")
    println("doug == dougClone: ${doug == dougClone}")
    println("doug != dougClone: ${doug != dougClone}")
    println("doug == sameDoug: ${doug == sameDoug}")
    println("doug != sameDoug: ${doug != sameDoug}")
}

fun referentialEqualsNumber() {
    println("********** referentialEqualsNumber() **********")
    println("200: hashCode = ${200.hashCode()}")
    println("300: hashCode = ${300.hashCode()}")
    println("200+100: hashCode = ${(200+100).hashCode()}")
    println("200 === 200: ${200 === 300}")
    println("200 !== 200: ${200 !== 300}")
    println("200 === 300: ${200 === 300}")
    println("200 !== 300: ${200 !== 300}")
    println("300 === (200+100): ${300 === (200+100)}")
    println("300 !== (200+100): ${300 !== (200+100)}")
}

fun referentialEqualsNumericString() {
    println("********** referentialEqualsNumericString() **********")
    println("\"200\": hashCode = ${"200".hashCode()}")
    println("\"300\": hashCode = ${"300".hashCode()}")
    println("200.toString(): hashCode = ${200.toString().hashCode()}")
    println("\"200\" === \"200\": ${"200" === "200"}")
    println("\"200\" !== \"200\": ${"200" !== "200"}")
    println("\"200\" === \"300\": ${"200" === "300"}")
    println("\"200\" !== \"300\": ${"200" !== "300"}")
    println("\"200\" === 200.toString(): ${"200" === 200.toString()}")
    println("\"200\" !== 200.toString(): ${"200" !== 200.toString()}")
}

fun referentialEqualsPerson() {
    val doug = Person("Davis", "Douglas")
    val dougClone = Person("Davis", "Douglas")
    val sameDoug = doug

    println("********** referentialEqualsPerson() **********")
    println("Doug: $doug, hashCode = ${doug.hashCode()}")
    println("Doug's clone: $dougClone, hashCode = ${dougClone.hashCode()}")
    println("anotherPerson: $sameDoug, hashCode = ${sameDoug.hashCode()}")
    println("doug === dougClone: ${doug === dougClone}")
    println("doug !== dougClone: ${doug !== dougClone}")
    println("doug === sameDoug: ${doug === sameDoug}")
    println("doug !== sameDoug: ${doug !== sameDoug}")
}

fun main(args: Array<String>) {
    // .equals()
    equalsNumber()
    equalsNumericString()
    equalsPerson()
    // ==
    structuralEqualsNumber()
    structuralEqualsNumericString()
    structuralEqualsPerson()
    // ===
    referentialEqualsNumber()
    referentialEqualsNumericString()
    referentialEqualsPerson()
}

