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

/**
 * The outer class
 */
class Outer {

    val publicProperty = "Outer public property"
    protected val protectedProperty = "Outer protected property"
    private val privateProperty = "Outer private property"

    /**
     * Demo the outer class
     */
    fun demo() {
        println("********** Outer.demo() **********")
        println("Outer.publicProperty = $publicProperty")
        println("Outer.protectedProperty = $protectedProperty")
        println("Outer.privateProperty = $privateProperty")
    }

    /**
     * A nested class
     */
    class Nested {
        val publicProperty = "Nested public property"
        protected val protectedProperty = "Nested protected property"
        private val privateProperty = "Nested private property"

        /**
         * Demo the nested class
         */
        fun demo() {
            println("********** Nested.demo() **********")
            println("Outer.Nested.publicProperty = $publicProperty")
            println("Outer.Nested.protectedProperty = $protectedProperty")
            println("Outer.Nested.privateProperty = $privateProperty")
        }
    }
}

fun main(args: Array<String>) {
    //
    // Create an InnerClassesOuter object and invoke its demo() function
    val outer = Outer()
    outer.demo()
    //
    // Create a Nested object through a static reference to
    // the Outer class and invoke the demo() function
    val nested = Outer.Nested()
    nested.demo()
}