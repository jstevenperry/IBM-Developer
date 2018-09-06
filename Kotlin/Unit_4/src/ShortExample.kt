/**
 * The main function that drives the program
 */
fun main(args: Array<String>) {
    runShortExample()
}

/**
 * Run the example to demonstrate
 */
fun runShortExample() {
    // Declare Short variables
    val shortMin: Short = Short.MIN_VALUE
    val shortMax: Short = Short.MAX_VALUE
    println("The value of shortMin is: $shortMin")
    println("The value of shortMax is: $shortMax")

    // Declare Short variable and initialize to a literal value
    var short: Short = 1000
    println("The value of short(1000) is: $short")

    short = 1_000
    println("The value of short(1_000) is: $short")

    short = 0x3e8
    println("The value of short(0x3e8) is: $short")

    short = 0b001111101000
    println("The value of short(0b001111101000) is: $short")
}


