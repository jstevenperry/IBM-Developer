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

/**
 * Demonstrates the for loop
 */
fun demoForInts(intArray: IntArray) {
    println("********** demoForInts() **********")
    for (int in intArray) {
        println("The number: $int")
    }
    println("Loop is done.")
}

fun demoForIntsWithBreak(intArray: IntArray) {
    println("********** demoForInts() **********")
    for (int in intArray) {
        if (int === 5) {
            println("At object $int, terminating loop")
            break
        }
        println("The number: $int")
    }
    println("Loop is done.")
}

fun demoForStrings(stringArray: Array<String>) {
    println("********** demoForStrings() **********")
    for (string in stringArray) {
        println("The string: '$string'")
    }
    println("Loop is done.")
}

/**
 * The ubiquitous main() function, we meet again.
 */
fun main(args: Array<String>) {
    demoForInts(intArray)
    demoForIntsWithBreak(intArray)
    demoForStrings(stringArray)
}
