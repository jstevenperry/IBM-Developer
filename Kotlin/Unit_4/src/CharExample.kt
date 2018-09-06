/**
 * The main function that drives the program
 */
fun main(args: Array<String>) {
    charExamples()
}

/**
 * Run the example to demonstrate
 */
fun charExamples() {
    // Declare Char variable and initialize to a literal value
    var char: Char = 'a'
    println("The value of char('a') is: $char")

    char = '\"'
    println("The value of char('\"') is: $char")

    char = '\n'
    println("The value of char('\\n') is: $char")

    char = 'Z'
    println("The value of char('Z') is: $char")

    // Unicode symbols!
    char = '\u23f8'
    println("The value of char('\\u23f8') is: $char")

    char = '\u222b'
    println("The value of char('\\u222b') is: $char")

    char = '\u2654'
    println("The value of char('\\u2654') is: $char")

    // Question: Why does this statement not compile?
    //char = 10
}
