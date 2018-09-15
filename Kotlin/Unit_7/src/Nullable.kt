package com.makotogo.learn.kotlin.nullable

import com.makotogo.learn.kotlin.defaultargs.createLocalDate
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun formatLocalDateLenient(localDate: LocalDate, formatString: String? = null) : String {
    return formatLocalDate(localDate, formatString ?: "yyyy/MM/dd")
}

fun formatLocalDate(localDate: LocalDate, formatString: String) : String {
    val dateTimeFormatter = DateTimeFormatter.ofPattern(formatString)
    return localDate.format(dateTimeFormatter)
}

fun formatLocalDateOldSchool(localDate: LocalDate, formatString: String?) : String {
    if (formatString == null) {
        return formatLocalDateLenient(localDate)
    } else {
        return formatLocalDate(localDate, formatString)
    }
}

fun formatLocalDateNewSchool(localDate: LocalDate, formatString: String? = null) : String {
    formatString?.let {
        return formatLocalDate(localDate, it)
    }
    return formatLocalDateLenient(localDate)
}

fun main(args: Array<String>) {

    println("Hooray! It's: ${formatLocalDateLenient(createLocalDate(1999, 12, 31))}")
    println("Hooray! It's: ${formatLocalDateNewSchool(createLocalDate(1999, 12, 31))}")

    println("Hooray! It's: ${formatLocalDate(createLocalDate(1999, 12, 31), "dd MMMM yyyy")}")

}