/**
 * Full syntax
 */
val fullSyntaxSum: (left: Int, right: Int) -> Int = {
    left, right -> left + right
}

// Same as

/**
 * Short syntax
 */
val sum = { left: Int, right: Int -> left + right }

fun main(args: Array<String>) {
    println("1 + 3 = ${sum(1, 3)}")
    println("3 + 6 = ${sum(3, 6)}")
    println("83 + 134 = ${sum(83, 134)}")

    // Just for fun
    println("1 + 3 = ${fullSyntaxSum(1, 3)}")
    println("3 + 6 = ${fullSyntaxSum(3, 6)}")
    println("83 + 134 = ${fullSyntaxSum(83, 134)}")
}