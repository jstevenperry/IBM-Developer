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

var publicProperty: String = "publicPackageProperty"
internal var internalProperty: String = "internalPackageProperty"
private var privateProperty: String = "privatePackageProperty"
// protected var protectedProperty = "Not Allowed" // Compile error!

internal class InternalClass(val name: String)
private class PrivateClass(val name: String)

open class Parent(val name: String) {
    private val privateClass = PrivateClass("$name.privateClass")
    internal val internalClass = InternalClass("$name.internalClass")
    protected val protectedString = "$name.protectedString"

    protected fun disclosePrivate() {
        println(privateClass.name)
    }
}

private class Child(name: String) : Parent(name) {
    fun disclose() {
        println(protectedString)
        disclosePrivate()
    }
}

fun main(args: Array<String>) {
    println(publicProperty)
    println(internalProperty)
    println(privateProperty)

    val parent = Parent("Parent")
    println(parent.name)
    println(parent.internalClass.name)
    //parent.protectedString // Compile error!
    //parent.privateClass // Compile error!

    val child = Child("Child")
    println(child.name)
    println(child.internalClass.name)
    child.disclose()
}

