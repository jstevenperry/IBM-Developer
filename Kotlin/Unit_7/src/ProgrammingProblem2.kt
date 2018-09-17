fun smartCast(number: Number) {
    if (number is Byte) println("The number parameter is of type Byte")
    else if (number is Short) println("The number parameter is of type Short")
    else if (number is Int) println("The number parameter is of type Int")
    else if (number is Long) println("The number parameter is of type Long")
    else if (number is Float) println("The number parameter is of type Float")
    else if (number is Double) println("The number parameter is of type Double")
    else println("The number parameter is of type UNKNOWN")
}

fun main(args: Array<String>) {
    val number = 23
    smartCast(number.toByte())
    smartCast(number.toShort())
    smartCast(number)
    smartCast(number.toLong())
    smartCast(number.toFloat())
    smartCast(number.toDouble())
}