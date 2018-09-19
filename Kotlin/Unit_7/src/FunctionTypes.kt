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