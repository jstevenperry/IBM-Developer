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

fun smartCast(number: Number) {
    if (number is Byte) println("The number parameter is of type Byte")
    else if (number is Short) println("The number parameter is of type Short")
    else if (number is Int) println("The number parameter is of type Int")
    else if (number is Long) println("The number parameter is of type Long")
    else if (number is Float) println("The number parameter is of type Float")
    else if (number is Double) println("The number parameter is of type Double")
    else println("The number parameter is of type UNKNOWN")
}

fun main(args: Array<String>) {
    val number = 23
    smartCast(number.toByte())
    smartCast(number.toShort())
    smartCast(number)
    smartCast(number.toLong())
    smartCast(number.toFloat())
    smartCast(number.toDouble())
}