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
 */

package com.makotogo.learn.kotlin

import com.makotogo.learn.kotlin.controller.ProcessorException
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.util.ArrayList


/**
 * Demonstrate File I/O in Kotlin
 */
class SimpleFileReader {
    /**
     * Read the specified input file whose name is [fileName]
     * and return its contents as a List<String>.
     *
     * This only works for text files. The returned List<String>
     * has one entry per line in the input file.
     */
    fun readFile(fileName: String): List<String> {
        //
        // Return value - List<String>
        val ret = ArrayList<String>()

        try {
            BufferedReader(FileReader(fileName)).use { reader ->
                var line: String? = reader.readLine()
                while (line != null) {
                    ret.add(line)
                    line = reader.readLine()
                }
            }
        } catch (e: IOException) {
            val message = "Error occurred while processing the file: " + e.localizedMessage
            println(message)
            throw ProcessorException(message, e)
        }

        //
        // Return to the caller
        return ret
    }

}

fun goodReadDemo() {
    //
    // Instantiate the class that does the I/O
    val kotlinFileIo = SimpleFileReader()
    //
    // Read the file
    val lines = kotlinFileIo.readFile(fileName = "loremIpsum5p.txt")
    //
    // Dump out what was read, along with a
    // line number
    var lineNumber = 1
    for (line in lines) {
        println("Line # ${lineNumber++}: $line")
    }
}

fun badReadDemo() {
    //
    // Instantiate the class that does the I/O
    val kotlinFileIo = SimpleFileReader()
    //
    // Read the file
    kotlinFileIo.readFile(fileName = "FILE_DOES_NOT_EXIST.TXT")
}

fun main(args: Array<String>) {
    println("********** badReadDemo() **********")
    try {
        badReadDemo()
    } catch (e: Exception) {
        val message = "Exception during badReadDemo(): ${e.localizedMessage}"
        println(message)
        throw RuntimeException(message, e)
    }
    println("********** goodReadDemo() **********")
    goodReadDemo()
}