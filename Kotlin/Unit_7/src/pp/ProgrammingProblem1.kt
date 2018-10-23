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

package com.makotogo.learn.kotlin.pp

/**
 * Full syntax
 */
val fullSyntaxSum: (left: Int, right: Int) -> Int = { left, right ->
    left + right
}

// Same as

/**
 * Short syntax
 */
val sum = { left: Int, right: Int -> left + right }

fun main(args: Array<String>) {
    println("1 + 3 = ${sum(1, 3)}")
    println("3 + 6 = ${sum(3, 6)}")
    println("83 + 134 = ${sum(83, 134)}")

    // Just for fun
    println("1 + 3 = ${fullSyntaxSum(1, 3)}")
    println("3 + 6 = ${fullSyntaxSum(3, 6)}")
    println("83 + 134 = ${fullSyntaxSum(83, 134)}")

    // Also

    println("1 + 3 = " + sum(1, 3))
    println("3 + 6 = " + sum(3, 6))
    println("83 + 134 = " + sum(83, 134))
}