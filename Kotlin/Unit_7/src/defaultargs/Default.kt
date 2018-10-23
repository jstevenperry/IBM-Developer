/*
 * Copyright 2018 Makoto Consulting Group, Inc
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package com.makotogo.learn.kotlin.defaultargs

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun createLocalDate(year: Int, month: Int, day: Int) : LocalDate {
    return LocalDate.of(year, month, day)
}

fun createLocalDateTime(
        year: Int,
        month: Int,
        day: Int,
        hour: Int = 0,
        minute: Int = 0,
        second: Int = 0) : LocalDateTime = LocalDateTime.of(year, month, day, hour, minute, second)

fun formatLocalDate(localDate: LocalDate, formatString: String = "MM/dd/yyyy") : String {
    val dateTimeFormatter = DateTimeFormatter.ofPattern(formatString)
    return localDate.format(dateTimeFormatter)
}

fun formatDateTime(localDateTime: LocalDateTime, formatString: String = "MM/dd/yyyy HH:mm:ss") : String {
    val dateTimeFormatter = DateTimeFormatter.ofPattern(formatString)
    return localDateTime.format(dateTimeFormatter)
}

fun main(args: Array<String>) {
    val localDate = createLocalDate(2018, 9, 15)
    println("Local date: $localDate")
    println("Local date (formatted): ${formatLocalDate(localDate)}")
    println("Local date (formatted): ${formatLocalDate(localDate, "dd MMMM yyyy")}")

    val localDateTime = createLocalDateTime(2018, 9, 15, 12, 7, 23)
    println("Local date/time: $localDateTime")
    println("Local date/time (formatted): ${formatDateTime(localDateTime)}")
    println("Local date/time (formatted): ${formatDateTime(localDateTime, "dd MMMM yyyy hh:mm:ss a")}")
}