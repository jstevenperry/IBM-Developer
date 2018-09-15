package com.makotogo.learn.kotlin.lambda

import java.time.LocalDate
import java.time.format.DateTimeFormatter

val localDateFactory: (Int, Int, Int) -> LocalDate = {
    year, month, day -> LocalDate.of(year, month, day)
}

val newYearsDayFactory: (Int) -> LocalDate = {
    year -> LocalDate.of(year, 1, 1)
}

val piDay2019Factory = {
    LocalDate.of(2019, 3, 14)
}

val now = {
    LocalDate.now()
}

fun formatLocalDate(localDate: LocalDate, formatString: String = "E MM/dd/yyyy") : String {
    val dateTimeFormatter = DateTimeFormatter.ofPattern(formatString)
    return localDate.format(dateTimeFormatter)
}

fun main(args: Array<String>) {
    var localDate = localDateFactory(2018, 3, 14)
    println("The LocalDate is: ${formatLocalDate(localDate = localDate)}")

    localDate = localDateFactory(2019, 4, 23)

    val year = 2019
    localDate = newYearsDayFactory(year)
    println("New year's day $year is: ${formatLocalDate(localDate = localDate)}")

    localDate = piDay2019Factory()
    println("National Pi day 2018 is: ${formatLocalDate(localDate = localDate)}")

    localDate = now()
    println("Now is ${formatLocalDate(localDate)}")
}