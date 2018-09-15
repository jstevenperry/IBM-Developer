package com.makotogo.learn.kotlin.nullable

import com.makotogo.learn.kotlin.defaultargs.createLocalDate
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun formatDateLenient(localDate: LocalDate, formatString: String? = null) : String {
    return formatDate(localDate, formatString ?: "yyyy/MM/dd")
}

fun formatDate(localDate: LocalDate, formatString: String) : String {
    val dateTimeFormatter = DateTimeFormatter.ofPattern(formatString)
    return localDate.format(dateTimeFormatter)
}

fun formatDateOldSchool(localDate: LocalDate, formatString: String?) : String {
    if (formatString == null) {
        return formatDateLenient(localDate)
    } else {
        return formatDate(localDate, formatString)
    }
}

fun formatDateNewSchool(localDate: LocalDate, formatString: String? = null) : String {
    formatString?.let {
        return formatDate(localDate, it)
    }
    return formatDateLenient(localDate)
}

fun main(args: Array<String>) {

    println("Hooray! It's: ${formatDateLenient(createLocalDate(1999, 12, 31))}")
    println("Hooray! It's: ${formatDateNewSchool(createLocalDate(1999, 12, 31))}")

    println("Hooray! It's: ${formatDate(createLocalDate(1999, 12, 31), "dd MMMM yyyy")}")

}