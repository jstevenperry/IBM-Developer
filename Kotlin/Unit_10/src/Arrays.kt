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

// An array of Int
internal val intArray: IntArray = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

// Empty array of Int
internal val emptyIntArray = intArrayOf()

// An array of String
internal val stringArray: Array<String> = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")

// Empty String array
internal val emptyStringArray: Array<String> = emptyArray()

fun main(args: Array<String>) {
    // Calls toString() on the object (spoiler alert: not very interesting)
    println("intArray = $intArray")
    println("emptyIntArray = $emptyIntArray")

    println("stringArray = $stringArray")
    println("emptyStringArray = $emptyStringArray")
}