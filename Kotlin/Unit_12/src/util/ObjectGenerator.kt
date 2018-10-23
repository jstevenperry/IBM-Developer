/*
 *    Copyright 2018 Makoto Consulting Group Inc
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

package com.makotogo.learn.kotlin.util

import com.makotogo.learn.kotlin.model.Guest
import java.time.LocalDate
import java.time.YearMonth
import java.util.Random

/**
 * Java pseudo-random number generator
 */
private val rng = Random(System.currentTimeMillis())

/**
 * Generate a random integer between zero and the [upperBoundExclusive]
 * and return it to the caller.
 */
internal fun generateRandomInt(upperBoundExclusive: Int): Int {
    return (generateRandomNumber() * upperBoundExclusive).toInt()
}

/**
 * Generate and return a random number between zero (inclusive)
 * and one (exclusive)
 */
private fun generateRandomNumber(): Float {
    return rng.nextFloat()
}

/**
 * A few (weird) family names. Not from around here.
 * As in anywhere on (this) Earth.
 */
private val FAMILY_NAME = arrayOf(
        "Anon",
        "Bazog",
        "Coln",
        "Daon",
        "Engan",
        "Fan",
        "Grale",
        "Horv",
        "Ix",
        "Jaxl",
        "Kath",
        "Lane",
        "Mord",
        "Naen",
        "Oon",
        "Ptal",
        "Tindale",
        "Ugzor",
        "Vahland",
        "Wragdhen",
        "Xntlh",
        "Yagnag",
        "Zhangth")

/**
 * Generate a random last name using the FAMILY_NAME array
 * along with a random index into the array.
 */
internal fun generateRandomFamilyName(): String {
    return FAMILY_NAME[generateRandomInt(FAMILY_NAME.size)]
}

/**
 * A few (weird) given names. Frankly, I'd give 'em back.
 */
private val GIVEN_NAME = arrayOf(
        "Ag",
        "Bog",
        "Cain",
        "Doan",
        "Erg",
        "Fon",
        "Gor",
        "Heg",
        "In",
        "Jar",
        "Kol",
        "Lar",
        "Mog",
        "Nor",
        "Ojon",
        "Ptal",
        "Quon",
        "Rag",
        "Sar",
        "Thag",
        "Uxl",
        "Verd",
        "Wrog",
        "Xlott",
        "Yogrl",
        "Zelx")

/**
 * Generate a random first name using the GIVEN_NAME array
 * along with a random index into the array.
 */
internal fun generateRandomGivenName(): String {
    return GIVEN_NAME[generateRandomInt(GIVEN_NAME.size)]
}

/**
 * Generate a random date of birth
 */
internal fun generateRandomYearMonthDayTriple(): Triple<Int, Int, Int> {
    val year = generateRandomYear(earliestYear = 1950, latestYear = 2000)
    val month = generateRandomMonth()
    val day = generateRandomDayOfMonth(year, month)

    // Return the YMD components as a Triple object
    return Triple(year, month, day)

}

/**
 * Extension function - extend Triple to convert the Triple to a LocalDate
 * Could we just pass these parameters to LocalDate.of()? Absolutely.
 * But where's the fun in that?? (pun intended)
 */
internal fun Triple<Int, Int, Int>.toLocalDate(): LocalDate =
        LocalDate.of(
                this.first, // Year
                this.second, // Month
                this.third) // Day

/**
 * Generate a random year between [earliestYear] and
 * [latestYear]
 */
private fun generateRandomYear(earliestYear: Int, latestYear: Int): Int {
    return (generateRandomInt(latestYear - earliestYear) + earliestYear)
}

/**
 * Generate a random month between 1-12
 */
private fun generateRandomMonth(): Int {
    return (generateRandomInt(12) + 1)
}

/**
 * Generate a random day of the month, create a
 * [java.time.YearMonth] (using the specified [year] and [month])
 * and its internal TemporalAdjuster to use the correct range
 * for that month (taking into account leap years and all that
 * jazz).
 */
private fun generateRandomDayOfMonth(year: Int, month: Int): Int {
    // Get the last day of the month for the year/month pair
    val maxDayOfMonth = YearMonth.of(year, month).atEndOfMonth().dayOfMonth
    return (generateRandomInt(upperBoundExclusive = maxDayOfMonth) + 1)
}

/**
 * Generate a random employeeId from 0 - 99999
 */
internal fun generateRandomEmployeeId() = generateRandomInt(99999)

/**
 * Some random title suffixes
 */
private val TITLE_SUFFIX = arrayOf("ist", "er", "onator", "erator", "o")

/**
 * Generate and return a random (and probably silly) title
 */
internal fun generateRandomTitle(employeeId: Int?): String {
    val ret: String
    val baseTitle = GIVEN_NAME[generateRandomInt(GIVEN_NAME.size)] +
            TITLE_SUFFIX[generateRandomInt(TITLE_SUFFIX.size)]

    ret = when (employeeId) {
        in 0..2000 -> {
            "Chief $baseTitle"
        }
        in 2001..30000 -> {
            "Sr. $baseTitle"
        }
        in 30001..70000 -> {
            "Assoc. $baseTitle"
        }
        else -> {
            "Jr. $baseTitle"
        }
    }
    return ret
}

/**
 * Some random purposes
 */
private val PURPOSE = arrayOf("Maintenance", "Package Delivery", "Consulting", "Family Member")

/**
 * Generate a random purpose
 */
internal fun generateRandomPurpose(): String {
    return PURPOSE[generateRandomInt(PURPOSE.size)]
}

/**
 * Generate random (fake) tax ID number of the form
 * 000-00-0000
 */
internal fun generateRandomTaxIdNumber(): String {

    // Simple but (more important) readable
    val part1 = "000"
    val part2 = "00" + generateRandomInt(99).toString()
    val part3 = "0000" + generateRandomInt(9999).toString()

    return "$part1-${part2.substring(part2.length - 2)}-${part3.substring(part3.length - 4)}"
}

fun createGuest(): Guest {
    return Guest(
            generateRandomFamilyName(),
            generateRandomGivenName(),
            generateRandomYearMonthDayTriple().toLocalDate(),
            generateRandomTaxIdNumber(),
            generateRandomPurpose()
    )
}

// JUnit test - if you want to run this test, make sure to add JUnit
// to your IDEA project.
//class ObjectGeneratorTest {
//
//    /**
//     * Simple histogram map. Stores the result and a running total
//     * of the number of times that result has come up.
//     */
//    private fun storeResults(result: Int, resultsMap: MutableMap<Int, Int>) {
//        // Get the
//        val results = resultsMap[result]
//        if (results == null) {
//            // Current result not in map, initialize it
//            resultsMap[result] = 1
//        } else {
//            // Current result seen before, increment the count
//            resultsMap[result] = results.toInt() + 1
//        }
//    }
//
//    /**
//     * Dump the results to the console via println from
//     * the specified [resultsMap]. Will sort the results
//     * if [isSorted] is true, otherwise it uses the MutableMap
//     * that is passed in as-is.
//     */
//    private fun dumpResults(title: String, resultsMap: MutableMap<Int, Int>, isSorted: Boolean = true) {
//        val mapToUse: Map<Int, Int> = if (isSorted) {
//            resultsMap.toSortedMap()
//        } else {
//            resultsMap
//        }
//        println("********** $title **********")
//        for ((key, value) in mapToUse) {
//            println("Year: $key, Number of hits: $value")
//        }
//    }
//
//    @Test
//    fun testGenerateRandomYear() {
//        val numberOfTests = 100000
//        val earliestYear = 1950
//        val latestYear = 2000
//        val resultsMap = linkedMapOf<Int, Int>()
//        // Run the test (so many tests)
//        for (testNumber in 1..numberOfTests) {
//            val year = generateRandomYear(earliestYear, latestYear)
//            storeResults(year, resultsMap)
//            assert(year in earliestYear..latestYear) {
//                "Failure on test number: $testNumber: Year ($year) is not between $earliestYear and $latestYear."
//            }
//        }
//        // Dump out the results
//        dumpResults("testGenerateRandomYear", resultsMap)
//    }
//
//    @Test
//    fun testGenerateRandomMonth() {
//        val numberOfTests = 100000
//        val minMonth = 1
//        val maxMonth = 12
//        val resultsMap = linkedMapOf<Int, Int>()
//        // Run the test (so many tests)
//        for (testNumber in 1..numberOfTests) {
//            val month = generateRandomMonth()
//            storeResults(month, resultsMap)
//            assert(month in minMonth..maxMonth) {
//                "Failure on test number: $testNumber: Month: $month is not between $minMonth and $maxMonth."
//            }
//        }
//        // Dump out the results
//        dumpResults("testGenerateRandomMonth", resultsMap)
//    }
//
//    @Test
//    fun testGenerateRandomDayOfMonth() {
//        testGenerateRandomDayOfMonthGuts(testTitle = "testGenerateRandomDayOfMonth: January 2000", month = 1, maxDay = 31)
//        testGenerateRandomDayOfMonthGuts(testTitle = "testGenerateRandomDayOfMonth: February 2000", month = 2, maxDay = 29)
//        testGenerateRandomDayOfMonthGuts(testTitle = "testGenerateRandomDayOfMonth: March 2000", month = 3, maxDay = 31)
//        testGenerateRandomDayOfMonthGuts(testTitle = "testGenerateRandomDayOfMonth: April 2000", month = 4, maxDay = 30)
//        testGenerateRandomDayOfMonthGuts(testTitle = "testGenerateRandomDayOfMonth: May 2000", month = 5, maxDay = 31)
//        testGenerateRandomDayOfMonthGuts(testTitle = "testGenerateRandomDayOfMonth: June 2000", month = 6, maxDay = 30)
//        testGenerateRandomDayOfMonthGuts(testTitle = "testGenerateRandomDayOfMonth: July 2000", month = 7, maxDay = 31)
//        testGenerateRandomDayOfMonthGuts(testTitle = "testGenerateRandomDayOfMonth: August 2000", month = 8, maxDay = 31)
//        testGenerateRandomDayOfMonthGuts(testTitle = "testGenerateRandomDayOfMonth: September 2000", month = 9, maxDay = 30)
//        testGenerateRandomDayOfMonthGuts(testTitle = "testGenerateRandomDayOfMonth: October 2000", month = 10, maxDay = 31)
//        testGenerateRandomDayOfMonthGuts(testTitle = "testGenerateRandomDayOfMonth: November 2000", month = 11, maxDay = 30)
//        testGenerateRandomDayOfMonthGuts(testTitle = "testGenerateRandomDayOfMonth: December 2000", month = 12, maxDay = 31)
//    }
//
//    /**
//     * Common code for testGenerateRnadomDayOfMonth()
//     */
//    private fun testGenerateRandomDayOfMonthGuts(testTitle: String,
//                                                 numberOfTests: Int = 100000,
//                                                 year: Int = 2000,
//                                                 month: Int,
//                                                 minDay: Int = 1,
//                                                 maxDay: Int,
//                                                 resultsMap: MutableMap<Int, Int> = linkedMapOf()) {
//
//        // Run the test (so many tests)
//        for (testNumber in 1..numberOfTests) {
//            val dayOfMonth = generateRandomDayOfMonth(year, month)
//            storeResults(dayOfMonth, resultsMap)
//            assert(dayOfMonth in minDay..maxDay) {
//                "Failure on test number: $testNumber: Day of month: $dayOfMonth is not between $minDay and $maxDay."
//            }
//        }
//        // Dump out the results
//        dumpResults(title = testTitle, resultsMap = resultsMap)
//    }
//}