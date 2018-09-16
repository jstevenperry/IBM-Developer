package com.makotogo.learn.kotlin.namedargs

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Creates a java.time.LocalDate object using the specified
 * [year], [month], and [day]
 */
fun createLocalDate(year: Int, month: Int, day: Int): LocalDate = LocalDate.of(year, month, day)

/**
 * Creates a java.time.LocalDateTime object using the specified
 * [year], [month], [day], [hour], [minute], and [second]
 */
fun createLocalDateTime(
        year: Int,
        month: Int,
        day: Int,
        hour: Int = 0,
        minute: Int = 0,
        second: Int = 0) : LocalDateTime = LocalDateTime.of(year, month, day, hour, minute, second)

/**
 * Creates a rather strangely focused java.time.LocalDateTime object using the specified
 * [year], [hour], [minute], and [second] that is always March 13th (national Pi day!)
 */
fun createLocalDateTimeAlwaysPiDay(year: Int, hour: Int, minute: Int, second: Int)
        : LocalDateTime = LocalDateTime.of(year, 3, 14, hour, minute, second)

/**
 * Utility function to format the specified [localDate] using the optional [formatString]
 * returns a String in the specified [formatString], or the default if none is specified
 */
fun formatLocalDate(localDate: LocalDate, formatString: String = "MM/dd/yyyy"): String {
    val dateTimeFormatter = DateTimeFormatter.ofPattern(formatString)
    return localDate.format(dateTimeFormatter)
}

/**
 * Utility function to format the specified [localDateTime] using the optional [formatString]
 * returns a String in the specified [formatString], or the default if none is specified
 */
fun formatDateTime(localDateTime: LocalDateTime, formatString: String = "MM/dd/yyyy HH:mm:ss"): String {
    val dateTimeFormatter = DateTimeFormatter.ofPattern(formatString)
    return localDateTime.format(dateTimeFormatter)
}

/**
 * The driving force behind this little program.
 * Literally. It drives the program.
 */
fun main(args: Array<String>) {
    // Exercise LocalDate functions
    val localDate = createLocalDate(year = 2018, month = 9, day = 2)
    println("Local date: $localDate")
    println("Local date (formatted): ${formatLocalDate(localDate = localDate)}")
    println("Local date (formatted): ${formatLocalDate(localDate = localDate, formatString = "E dd MMMM yyyy")}")

    // Exercise the LocalDateTime functions
    var localDateTime = createLocalDateTime(
            month = 9,
            year = 2018,
            day = 2,
            minute = 7,
            hour = 12,
            second = 23)
    println("Local date/time: $localDateTime")
    println("Local date/time (formatted): ${formatDateTime(localDateTime = localDateTime)}")
    println("Local date/time (formatted): ${formatDateTime(localDateTime = localDateTime, formatString = "EEEE dd MMMM yyyy hh:mm:ss a")}")

    // Exercise the LocalDateTime functions
    localDateTime = createLocalDateTimeAlwaysPiDay(second = 23, hour = 12, minute = 7, year = 2018) // Mix it up, still works!
    println("Local date/time: $localDateTime")
    println("Local date/time (formatted): ${formatDateTime(localDateTime = localDateTime)}")
    println("Local date/time (formatted): ${formatDateTime(localDateTime = localDateTime, formatString = "E dd MMMM yyyy hh:mm:ss a")}")
}