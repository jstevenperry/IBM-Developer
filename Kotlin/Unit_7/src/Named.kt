package com.makotogo.learn.kotlin.namedargs

import com.makotogo.learn.kotlin.defaultargs.createLocalDate
import com.makotogo.learn.kotlin.defaultargs.createLocalDateTime

fun main(args: Array<String>) {
    val newYearsEve1999 = createLocalDate(year = 1999, month = 12, day = 31)

    println("Let's party all day! It's $newYearsEve1999")

    val newYearsEve1999Midnight = createLocalDateTime(
            year = 1999,
            month = 12,
            day = 31,
            hour = 23,
            minute = 59,
            second = 59)

    println("Happy Y2K (almost): $newYearsEve1999Midnight")
}