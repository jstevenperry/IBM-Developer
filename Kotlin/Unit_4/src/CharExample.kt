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
    println("The value of char('\n') is: $char")

    char = 'Z'
    println("The value of char('Z') is: $char")

    // Question: Why does this statement not compile?
    // printChar(10)
}
/**
 * The main function that drives the program
 */
fun main(args: Array<String>) {
    charExamples()
}