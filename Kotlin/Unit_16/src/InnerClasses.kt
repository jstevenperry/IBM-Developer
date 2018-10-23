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
class InnerClassesOuter {

    val publicProperty = "InnerClassesOuter public property"

    protected val protectedProperty = "InnerClassesOuter protected property"

    private val privateProperty = "InnerClassesOuter private property"

    /**
     * An inner class
     */
    inner class Inner {
        val publicProperty = "Inner public property"

        protected val protectedProperty = "Inner protected property"

        private val privateProperty = "Inner private property"

        /**
         * An inner class within an inner class
         */
        inner class InnerInner {
            val publicProperty = "InnerInner public property"

            protected val protectedProperty = "InnerInner protected property"

            private val privateProperty = "InnerInner private property"

            fun demo() {
                println("********** InnerInner.demo() **********")
                println("InnerClassesOuter.Inner.InnerInner.publicProperty = $publicProperty")
                println("InnerClassesOuter.Inner.InnerInner.protectedProperty = $protectedProperty")
                println("InnerClassesOuter.Inner.InnerInner.privateProperty = $privateProperty")
            }
        }

        fun demo() {
            println("********** Inner.demo() **********")
            println("InnerClassesOuter.Inner.publicProperty = $publicProperty")
            println("InnerClassesOuter.Inner.protectedProperty = $protectedProperty")
            println("InnerClassesOuter.Inner.privateProperty = $privateProperty")
        }
    }

    fun demo() {
        println("********** InnerClassesOuter.demo() **********")
        println("InnerClassesOuter.publicProperty = $publicProperty")
        println("InnerClassesOuter.protectedProperty = $protectedProperty")
        println("InnerClassesOuter.privateProperty = $privateProperty")
    }
}

fun main(args: Array<String>) {
    //
    // Create an InnerClassesOuter object and invoke its demo() function
    InnerClassesOuter().demo()
    //
    // Create a Nested object through a static reference to
    // the InnerClassesOuter class and invoke the demo() function
    InnerClassesOuter().Inner().demo()
    //
    // Create a NestedNested object through a static
    // reference to InnerClassesOuter and a static reference to Nested
    InnerClassesOuter().Inner().InnerInner().demo()
}