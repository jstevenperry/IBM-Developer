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

package com.makotogo.learn.kotlin.quiz

import com.makotogo.learn.kotlin.controller.processChar
import com.makotogo.learn.kotlin.util.createChar

fun main(args: Array<String>) {

    // Create an Array<List<Char>>
    val arrayOfList: Array<List<Char>> = arrayOf(
            arrayListOf(
                    createChar(),
                    createChar(),
                    createChar(),
                    createChar(),
                    createChar()
            ),
            arrayListOf(
                    createChar(),
                    createChar(),
                    createChar(),
                    createChar(),
                    createChar()
            ),
            arrayListOf(
                    createChar(),
                    createChar(),
                    createChar(),
                    createChar(),
                    createChar()
            )
    )

    // Process the array of lists
    for (list in arrayOfList) {
        println("********** Processing list: $list **********")
        for (char in list) {
            processChar(char)
        }
    }
}