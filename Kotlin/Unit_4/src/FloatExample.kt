/**
 * Run the example to demonstrate
 */
fun floatExamples() {
    // Declare Float variables
    var floatMin: Float = Float.MIN_VALUE
    var floatMax: Float = Float.MAX_VALUE
    println("The value of floatMin is: $floatMin")
    println("The value of floatMax is $floatMax")

    // Declare Float variable and initialize to a literal value
    var float: Float = 1000000000.0f
    println("The value of float(1000000000.0f) is: $float")

    float = 1_000_000_000.0f
    println("The value of float(1_000_000_000.0f) is: $float")
}
/**
 * The main function that drives the program
 */
fun main(args: Array<String>) {
    floatExamples()
}