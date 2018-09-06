/**
 * The main function that drives the program
 */
fun main(args: Array<String>) {
    runByteExample()
}

/**
 * Run the example to demonstrate
 */
fun runByteExample() {
    // Declare Byte variables
    val byteMin: Byte = Byte.MIN_VALUE
    val byteMax: Byte = Byte.MAX_VALUE
    println("The value of byteMin is: $byteMin")
    println("The value of byteMax is: $byteMax")

    // Declare Byte variable and initialize to a literal value
    var byte : Byte = 100
    println("The value of byte(100) is: $byte")

    byte = 0x64
    println("The value of byte(0x64) is: $byte")

    byte = 0b01100100
    println("The value of byte(0b01100100) is: $byte")
}