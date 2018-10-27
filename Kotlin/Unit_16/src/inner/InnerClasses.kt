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

package com.makotogo.learn.kotlin.inner

/**
 * The outer class
 */
class Outer {

    val publicProperty = "Outer public property"
    protected val protectedProperty = "Outer protected property"
    private val privateProperty = "Outer private property"

    fun demo() {
        println("********** Outer.demo() **********")
        println("Outer.publicProperty = $publicProperty")
        println("Outer.protectedProperty = $protectedProperty")
        println("Outer.privateProperty = $privateProperty")
    }

    /**
     * An inner class
     */
    inner class Inner {

        val publicProperty = "Inner public property"
        protected val protectedProperty = "Inner protected property"
        private val privateProperty = "Inner private property"

        fun demo() {
            println("********** Inner.demo() **********")
            println("Outer.Inner.publicProperty = $publicProperty")
            println("Outer.Inner.protectedProperty = $protectedProperty")
            println("Outer.Inner.privateProperty = $privateProperty")
            println("\tOuter data:")
            println("\tOuter.Inner.publicProperty = ${this@Outer.publicProperty}")
            println("\tOuter.Inner.protectedProperty = ${this@Outer.protectedProperty}")
            println("\tOuter.Inner.privateProperty = ${this@Outer.privateProperty}")
        }
    }
}

fun main(args: Array<String>) {
    //
    // Create an Outer object and invoke its demo() function
    val outer = Outer()
    outer.demo()
    //
    // Create a Nested object through an object reference to
    // the Outer class and invoke the demo() function
    val inner = outer.Inner()
    inner.demo()
}