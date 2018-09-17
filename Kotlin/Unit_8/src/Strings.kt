val hello = "Hello"
val world = "world"
val money = "$"

val tripleQuotedStringHello = """
    Hello,
    world!
"""

fun main(args: Array<String>) {
    println(hello + ", " + world)

    var accumulatorString = hello
    accumulatorString += ", "
    accumulatorString += world
    println(accumulatorString)

    println(tripleQuotedStringHello)
    println(tripleQuotedStringHello.trimIndent())

    val stringTemplate = "$hello, $world"
    println(stringTemplate)

    val escapedStringTemplate = "The string template \"\$hello, \$world\" evaluates to: $hello, $world"
    println(escapedStringTemplate)

    println("Triple quoted string, with trimIndent() call looks like:\n${tripleQuotedStringHello.trimIndent()}")

    var numberAsString = "42"
    var numberAsInt = numberAsString.toInt()
    println("The string \"$numberAsString\" when converted to Int evaluates to $numberAsInt")
    numberAsInt += 23
    numberAsString = numberAsInt.toString()
    println("The Int $numberAsInt when converted to String evaluates to \"$numberAsString\"")

    val stringWithCommas = "This, string had ,,lots of, commas,, ,,in,,,,, it"
    println("Filtered string: \"${stringWithCommas.filter { char -> char != ',' }}\"")

    val paddedString = "    This string was padded with blanks      "
    println("Trimmed string: \"${paddedString.trim()}\"")

    val junkyString = "  This,_ String_& **was&& *Littered,, ,With& &^*All *&Kinds_^ _of^* ^Junk     "

    // Filter out junk, use separate line of code for readability
    val filteredString = junkyString
            .filter { char -> char != ',' }
            .filterNot { char -> char == '&' }
            .filterNot { char -> char == '^' }
            .filterNot { char -> char == '*' }
            .filterNot { char -> char == '_' }
            .trim()
    println("Filtered string: \"$filteredString\"")

    println("FILTERED STRING: \"${filteredString.toUpperCase()}\"")

    println("filtered string: \"${filteredString.toLowerCase()}\"")

}