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
class NestedClassesOuter {

    val publicProperty = "InnerClassesOuter public property"

    protected val protectedProperty = "InnerClassesOuter protected property"

    private val privateProperty = "InnerClassesOuter private property"

    /**
     * A nested class
     */
    class Nested {
        val publicProperty = "Nested public property"

        protected val protectedProperty = "Nested protected property"

        private val privateProperty = "Nested private property"

        /**
         * A nested class within a nested class
         */
        class NestedNested {
            val publicProperty = "NestedNested public property"

            protected val protectedProperty = "NestedNested protected property"

            private val privateProperty = "NestedNested private property"

            /**
             * Demo the nested nested class
             */
            fun demo() {
                println("********** Nested.Nested.demo() **********")
                println("NestedClassesOuter.Nested.Nested.publicProperty = $publicProperty")
                println("NestedClassesOuter.Nested.Nested.protectedProperty = $protectedProperty")
                println("NestedClassesOuter.Nested.Nested.privateProperty = $privateProperty")
            }
        }

        /**
         * Demo the nested class
         */
        fun demo() {
            println("********** Nested.demo() **********")
            println("NestedClassesOuter.Nested.publicProperty = $publicProperty")
            println("NestedClassesOuter.Nested.protectedProperty = $protectedProperty")
            println("NestedClassesOuter.Nested.privateProperty = $privateProperty")
        }
    }

    /**
     * Demo the outer class
     */
    fun demo() {
        println("********** InnerClassesOuter.demo() **********")
        println("NestedClassesOuter.publicProperty = $publicProperty")
        println("NestedClassesOuter.protectedProperty = $protectedProperty")
        println("NestedClassesOuter.privateProperty = $privateProperty")
    }
}

fun main(args: Array<String>) {
    //
    // Create an InnerClassesOuter object and invoke its demo() function
    NestedClassesOuter().demo()
    //
    // Create a Nested object through a static reference to
    // the InnerClassesOuter class and invoke the demo() function
    NestedClassesOuter.Nested().demo()
    //
    // Create a NestedNested object through a static
    // reference to InnerClassesOuter and a static reference to Nested
    NestedClassesOuter.Nested.NestedNested().demo()
}