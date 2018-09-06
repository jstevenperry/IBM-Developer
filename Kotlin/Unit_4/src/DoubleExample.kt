/**
 * The main function that drives the program
 */
fun main(args: Array<String>) {
    doubleExamples()
}

/**
 * Run the example to demonstrate
 */
fun doubleExamples() {
    // Declare Double variables
    val doubleMin = Double.MIN_VALUE
    val doubleMax = Double.MAX_VALUE
    println("The value of doubleMin is: $doubleMin")
    println("The value of doubleMax is: $doubleMax")

    // Declare Double variable and initialize to a literal value
    var double: Double = 1000000000000.0001
    println("The value of double(1000000000000.0001) is: $double")

    double = 1.0e22
    println("The value of double(1.0e22) is: $double")

    // Negate, with no loss in precision
    double = -Double.MAX_VALUE;
    println("The value of double(-Double.MAX_VALUE) is: $double")

}
