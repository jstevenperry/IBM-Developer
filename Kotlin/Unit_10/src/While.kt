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
 * Demonstrates the while oop
 */
class WhileLoop {

    fun demoIntIterator(intArray: IntArray) {
        println("********** WhileLoop.demoIntIterator() **********")
        val iterator = intArray.iterator()
        while (iterator.hasNext()) {
            val int = iterator.next()
            println("The number: $int")
        }
    }

}

/**
 * Demonstrates the do/while loop
 */
class DoWhileLoop {

    fun demoStringIterator(stringArray: Array<String>) {
        println("********** DoWhileLoop.demoStringIterator **********")
        val iterator = stringArray.iterator()
        val stringAccum = StringBuilder()
        if (iterator.hasNext()) do {
            val string = iterator.next()
            stringAccum.append(string)
        } while(iterator.hasNext())
        println("The accumulated string: $stringAccum")
    }

}

/**
 * The ubiquitous main() function, we meet again.
 */
fun main(args: Array<String>) {
    val whileLoop = WhileLoop()
    whileLoop.demoIntIterator(intArray)
    whileLoop.demoIntIterator(emptyIntArray)

    val doWhileLoop = DoWhileLoop()
    doWhileLoop.demoStringIterator(stringArray)
    doWhileLoop.demoStringIterator(emptyStringArray)
}