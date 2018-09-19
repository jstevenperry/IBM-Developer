/*
 * Copyright 2018 Makoto Consulting Group, Inc.
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

package com.makotogo.learn.kotlin.example4

fun main(args: Array<String>) {
    println(publicProperty)
    println(internalProperty)

    // println(privateProperty) // Compile error!
    // println(PrivateClass("Not allowed").name) // Compile error!

    val internalClass = InternalClass("Neighbor.internalClass")
    println(internalClass.name)

    val parent = Parent("Parent")
    println(parent.name)
    println(parent.internalClass.name)

    // val child = Child("Not allowed") // Compile error!
}