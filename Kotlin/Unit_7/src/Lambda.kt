package com.makotogo.learn.kotlin.lambda

import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Function that takes a [LocalDate] factory function called [localDateFactory].
 * The factory function is invoked, and the returned LocalDate object is
 * formatted to a String and that String is returned to the caller.
 */
fun formatLocalDate(localDateFactory: () -> LocalDate): String {
    return localDateFactory().format(DateTimeFormatter.ofPattern("E MM/dd/yyyy"))
}

/**
 * Function that takes a LocalDate factory, a year, and optional format String.
 * Works just like its namesake above, except that the LocalDate factory function
 * can be passed a year value, making it a little more generic.
 */
fun formatLocalDate(localDateFactory: (Int, Int, Int) -> LocalDate, year: Int, month: Int, day: Int) : String {
    return localDateFactory(year, month, day).format(DateTimeFormatter.ofPattern("E MM/dd/yyyy"))
}

/**
 * Lambda expression to generate a [LocalDate] that corresponds to "now"
 * Note, the [LocalDate] will be "now" when the function is actually invoked,
 * not when it is defined or passed as a value.
 */
val now = {
    LocalDate.now()
}

/**
 * Lambda expression to generate a [LocalDate] for the specified [year], [month], [day]
 */
val localDateFactory: (year: Int, month: Int, day: Int) -> LocalDate = { year, month, day ->
    LocalDate.of(year, month, day)
}

/**
 * The ubiquitous main function. Don't leave home without it.
 */
fun main(args: Array<String>) {
    // Define the function on the fly
    var formattedLocalDate = formatLocalDate { LocalDate.now() }
    // same as
    // formattedLocalDate = formatLocalDate({ LocalDate.now() })
    println("The LocalDate is: $formattedLocalDate")

    // Use the function stored in a variable
    formattedLocalDate = formatLocalDate(localDateFactory = now)
    println("The LocalDate is: $formattedLocalDate")

    // Use the function stored in a variable
    formattedLocalDate = formatLocalDate(localDateFactory, 2019, 3, 14)
    println("The LocalDate is: $formattedLocalDate")

    // Pi day, in the year 2023
    formattedLocalDate = formatLocalDate(localDateFactory, 2023, 3, 14)
    println("The LocalDate is: $formattedLocalDate")
}