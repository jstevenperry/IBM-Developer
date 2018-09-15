package com.makotogo.learn.kotlin.defaultargs

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun createLocalDate(year: Int, month: Int, day: Int) : LocalDate {
    return LocalDate.of(year, month, day)
}

fun createLocalDateTime(year: Int, month: Int, day: Int, hour: Int = 0, minute: Int = 0, second: Int = 0) : LocalDateTime? {
    return null
}

fun formatDate(localDate: LocalDate, formatString: String = "MM/dd/yyyy") : String {
    val dateTimeFormatter = DateTimeFormatter.ofPattern(formatString)
    return localDate.format(dateTimeFormatter)
}