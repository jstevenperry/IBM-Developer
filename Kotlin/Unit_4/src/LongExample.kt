/**
 * The main function that drives the program
 */
fun main(args: Array<String>) {
    runLongExample()
}

/**
 * Run the example to demonstrate
 */
fun runLongExample() {
    // Declare Long variables
    val longMin: Long = Long.MIN_VALUE
    val longMax: Long = Long.MAX_VALUE
    println("The value of longMin is: $longMin")
    println("The value of longMax is: $longMax")

    // Declare Long variable and initialize to a literal value
    var long: Long = 1000000000000L
    println("The value of long(1000000000000L) is: $long")

    long = 1_000_000_000_000L
    println("The value of long(1_000_000_000_000L) is: $long")

    long = 0xE8D4A51000
    println("The value of long(0xE8D4A51000) is: $long")

    long = 0xE8_D4_A5_10_00
    println("The value of long(0xE8_D4_A5_10_00) is: $long")

    long = 0b1110_1000_1101_0100_1010_0101_0001_0000_0000_0000
    println("The value of long(0b1110_1000_1101_0100_1010_0101_0001_0000_0000_0000) is: $long")

}
