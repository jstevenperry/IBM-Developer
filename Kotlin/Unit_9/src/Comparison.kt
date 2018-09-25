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

package com.makotogo.learn.kotlin.comparison

fun lessThan() {
    println("********** lessThan() - Numbers **********")

    println("2 < 3: ${2 < 3}")
    println("3 < 2: ${3 < 2}")
    println("2 < 2: ${2 < 2}")

    println("********** lessThan() - Numeric String **********")

    println("\"2\" < \"3\": ${"2" < "3"}")
    println("\"3\" < \"2\": ${"3" < "2"}")
    println("\"2\" < \"2\": ${"2" < "2"}")
    println("\"aaaaa\" < \"aaaab\": ${"aaaaa" < "aaaab"}")

}

fun lessThanOrEqualTo() {
    println("********** lessThanOrEqualTo() - Numbers **********")

    println("2 <= 3: ${2 <= 3}")
    println("3 <= 2: ${3 <= 2}")
    println("2 <= 2: ${2 <= 2}")

    println("********** lessThanOrEqualTo() - NumericString **********")

    println("\"2\" <= \"3\": ${"2" <= "3"}")
    println("\"3\" <= \"2\": ${"3" <= "2"}")
    println("\"2\" <= \"2\": ${"2" <= "2"}")
    println("\"aaaaa\" <= \"aaaab\": ${"aaaaa" <= "aaaab"}")

}

fun greaterThan() {
    println("********** greaterThan() - Numbers **********")

    println("2 > 3: ${2 > 3}")
    println("3 > 2: ${3 > 2}")
    println("2 > 2: ${2 > 2}")

    println("********** greaterThan() - NumericString **********")

    println("\"2\" > \"3\": ${"2" > "3"}")
    println("\"3\" > \"2\": ${"3" > "2"}")
    println("\"2\" > \"2\": ${"2" > "2"}")
    println("\"aaaaa\" > \"aaaab\": ${"aaaaa" > "aaaab"}")

}

fun greaterOrEqualToThan() {
    println("********** greaterOrEqualTo() - Numbers **********")

    println("2 >= 3: ${2 >= 3}")
    println("3 >= 2: ${3 >= 2}")
    println("2 >= 2: ${2 >= 2}")

    println("********** greaterOrEqualTo() - NumericString **********")

    println("\"2\" >= \"3\": ${"2" >= "3"}")
    println("\"3\" >= \"2\": ${"3" >= "2"}")
    println("\"2\" >= \"2\": ${"2" >= "2"}")
    println("\"aaaaa\" >= \"aaaab\": ${"aaaaa" >= "aaaab"}")

}

fun underTheHood() {
    println("********** Comparison operators - Under the hood **********")
    println("2 < 3 (2.compareTo(3) < 0): ${2.compareTo(3) < 0}")
    println("3 < 3 (3.compareTo(3) < 0): ${3.compareTo(3) < 0}")
    println("2 <= 3 (2.compareTo(3) <= 0): ${2.compareTo(3) <= 0}")
    println("3 <= 3 (3.compareTo(3) <= 0): ${3.compareTo(3) <= 0}")
    println("2 > 3 (2.compareTo(3) > 0): ${2.compareTo(3) > 0}")
    println("3 > 2 (3.compareTo(2) > 0): ${3.compareTo(2) > 0}")
    println("2 >= 3 (2.compareTo(3) >= 0): ${2.compareTo(3) >= 0}")
    println("3 >= 3 (3.compareTo(3) >= 0): ${3.compareTo(3) >= 0}")
}

fun and() {
    println("********** and() **********")

    println("2 < 3 && 3 > 2: ${2 < 3 && 3 > 2}")
    println("2 < 3 && 3 < 2: ${2 < 3 && 3 < 2}")
}

fun or() {
    println("********** or() **********")

    println("2 > 3 || 3 > 2: ${2 > 3 || 3 > 2}")
    println("2 > 3 || 3 < 2: ${2 > 3 || 3 < 2}")
    println("true || false: ${true || false}")
    println("false || false: ${false || false}")

}

fun not() {
    println("********** not() **********")

    println("!false || false: ${!false || false}")
    println("!true && false: ${!true && false}")
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
    // Now go under the hood with comparison operators
    underTheHood()
    // &&
    and()
    // ||
    or()
    // !
    not()
}