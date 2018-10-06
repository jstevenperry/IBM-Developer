/*
 *    Copyright 2018 Makoto Consulting Group Inc
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
package com.makotogo.learn.kotlin

import com.makotogo.learn.kotlin.util.toLocalDate
import java.time.LocalDate

/**
 * Demonstrate a simple extension function
 */
fun tripleDemo() {
    val triple: Triple<Int, Int, Int> = Triple(1980, 4, 5)

    val april4th1980: LocalDate = triple.toLocalDate()

    println("Triple: $triple as LocalDate: $april4th1980")
}

/**
 * The ubiquitous main function. We meet again.
 */
fun main(args: Array<String>) {
    tripleDemo()
}