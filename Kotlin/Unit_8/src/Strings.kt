import java.time.LocalDateTime

/*
 * Copyright 2018 J Steven Perry
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

fun main(args: Array<String>) {

    println("********** literals() **********")
    // Demonstrate string literals
    literals()

    println("********** concatenate() **********")
    // Demonstrate string concatenation
    concatenate()

    println("********** template() **********")
    // Demonstrate string template use
    template()

    println("********** numericConversion() **********")
    // Demonstrate numeric conversion
    numericConversion()

    println("********** handyUtilities() **********")
    // Demonstrate some handy utilities
    handyUtilities()

}

/**
 * Demonstrate string literals
 */
fun literals() {
    // A few string literals
    val hello = "Hello"
    val world = "world"

    val tripleQuotedStringHello = """
        Hello,
        world!
    """

    println(hello)
    println(world)
    println(tripleQuotedStringHello)
}

/**
 * Demonstrate string concatenation
 */
fun concatenate() {
    val hello = "Hello"
    val world = "world"
    // Video: show warning as a tip that concatenation is not a great idea
    println(hello + ", " + world)

    var accumulatorString = hello
    accumulatorString += ", "
    accumulatorString += world
    println(accumulatorString)

}

/**
 * Demonstrate string template use
 */
fun template() {
    val hello = "Hello"
    val world = "world"

    val tripleQuotedStringHello = """
        The string template expression:
            "${'$'}hello, ${'$'}world"
        evaluates to:
            $hello, $world
    """

    println("${hello}, world")

    println("$hello, $world")

    val escapedStringTemplate = "The string template expression:\n\t\"\$hello, \$world\"\nevaluates to:\n\t$hello, $world"
    println(escapedStringTemplate)

    println(tripleQuotedStringHello)

    println(tripleQuotedStringHello.trimIndent())

    println("Triple quoted string, with trimIndent() call looks like:\n${tripleQuotedStringHello.trimIndent()}")
}

/**
 * Demonstrate numeric conversion
 */
fun numericConversion() {
    val numberAsString = "42"
    var numberAsInt = numberAsString.toInt()
    println("The string \"$numberAsString\" when converted to Int evaluates to $numberAsInt")
    println("The string \"42\" when converted to Int evaluates to ${"42".toInt()}")

    // Invalid numeric string
    println("The string \"42a\" when converted to Int evaluates to ${"42a".toIntOrNull()}")

    numberAsInt += 23
    val numericValue: String = numberAsInt.toString()
    println("The Int $numberAsInt when converted to String evaluates to \"$numericValue\"")

    // Convert number to numeric string when augmenting
    println("The expression \"$numberAsString\"+100 evaluates to ${numberAsString+100}")
    var numberAccumulator = numberAsString
    println("The statement \"$numberAccumulator\"+=100: ")
    numberAccumulator+= 100
    println("numberAccumulator = ${numberAccumulator}")

    val x = 10
    // x += "2" Compile error!
}

/**
 * Demonstrate some handy string utilities
 */
fun handyUtilities() {
    // trim()
    val paddedString = "  \t\t\n ,,This string was padded with whitespace,,  \t    "
    println("Trimmed string (good): \"${paddedString.trim()}\"")

    val trimmedString = paddedString.trim().trim {
        char -> char == ','
    }
    println("Trimmed string (better):\"$trimmedString\"")

    val caseString = "This sentence 42 no cent$ makes"
    val caseStringUpperCase = caseString.toUpperCase()
    println("Trimmed string (UPPER CASE): \"$caseStringUpperCase\"")

    println("Trimmed string (lower case): \"${caseStringUpperCase.toLowerCase()}\"")

    // filter()
    val stringWithCommas = "This, string had ,,lots of, commas,, ,,in,,,,, it"
    println("Filtered string: \"${stringWithCommas.filter { char -> char != ',' }}\"")

    // filterNot()
    val junkyString = "  This,_ String_& **was&& *Littered,, ,With& &^*All *&Kinds_^ _of^* ^Junk     "
    // Filter out junk, use separate line of code for readability
    val filteredString = junkyString
            .filterNot { char -> char == ',' }
            .filterNot { char -> char == '&' }
            .filterNot { char -> char == '^' }
            .filterNot { char -> char == '*' }
            .filterNot { char -> char == '_' }
            .trim()
    println("Filtered string: \"$filteredString\"")
}
