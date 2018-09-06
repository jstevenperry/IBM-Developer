/**
 * The main function that drives the program
 */
fun main(args: Array<String>) {
    intExamples()
}

/**
 * Run the example to demonstrate
 */
fun intExamples() {
    // Declare Int variables
    println(Int.MIN_VALUE)
    println(Int.MAX_VALUE)

    // Declare Int variable and initialize to a literal value
    var int: Int = 1000000
    println("The value of int(1000000) is: $int")

    int = 1_000_000
    println("The value of int(1_000_000) is: $int")

    int = 0xF4240
    println("The value of int(0xF4240) is: $int")

    int = 0b11110100001001000000
    println("The value of int(0b11110100001001000000) is: $int")
}
