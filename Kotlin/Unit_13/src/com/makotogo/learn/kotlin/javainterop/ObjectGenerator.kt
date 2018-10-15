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
 */
//@file:JvmName("ObjectGenerator")

package com.makotogo.learn.kotlin.javainterop

import com.makotogo.learn.kotlin.model.Employee
import com.makotogo.learn.kotlin.model.Guest
import com.makotogo.learn.kotlin.model.Person
import com.makotogo.learn.kotlin.model.Worker
import java.time.LocalDate
import java.time.YearMonth
import java.util.Random

/**
 * Slightly modified version of ObjectGenerator from previous units.
 * This one is evil: when generating an attribute value (say, family name),
 * occasionally the generated value is null.
 *
 * This is to simulate the bad data that you must interact with. Kotlin's
 * null vanguard is awesome, but you will have to interact with applications
 * you didn't write, and that may not be written in a language that has
 * such a conscientious approach to null safety.
 *
 * I've left the class written in Kotlin for two reasons:
 * 1. I'm somewhat lazy - I've tested this code extensively and I know it
 * works. If I translated it to Java I'd be forced to retest (that's just
 * how I roll).
 *
 * 2. This serves as a demonstrate of Java to Kotlin interop
 *
 * 3. This is a Kotlin course, so most of the code *should be* in Kotlin
 *
 * 4. That is three reasons.
 *
 * 5. I apparently cannot count.
 */

/**
 * Java pseudo-random number generator
 */
private var rng = Random(System.currentTimeMillis())

/**
 * Generate a random floating point number between zero
 * and 1000, and return it.
 *
 * Make sure the denominator is never zero
 */
fun createFloat(index: Int) = (generateRandomNumber(upperBoundExclusive = index) + index)

/**
 * Generate and return a random Char from one of the printable ASCII characters
 * (that is, 33 - 126)
 */
fun createChar() = (generateRandomInt(upperBoundExclusive = (127 - 33)) + 33).toChar()

/**
 * Generate a random integer between zero and the [upperBoundExclusive]
 * and return it to the caller.
 */
internal fun generateRandomInt(upperBoundExclusive: Int): Int {
    return (generateRandomNumber() * upperBoundExclusive).toInt()
}

/**
 * Generate a random floating point number between zero and 1.0f (exclusive)
 * and return it to the caller.
 */
internal fun generateRandomNumber(upperBoundExclusive: Int): Float {
    return (generateRandomNumber() * upperBoundExclusive.toFloat())
}

/**
 * Generate and return a random number between zero (inclusive)
 * and one (exclusive)
 */
private fun generateRandomNumber(): Float {
    //
    // Refresh the Pseudo random number generator
    rng = Random()
    return rng.nextFloat()
}

/**
 * This function has roughly a 1/10 chance of returning true
 */
private fun tenPercentChance(): Boolean {
    val randomInt = generateRandomInt(upperBoundExclusive = 100)
    return (randomInt % 10 == 0)
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
 *
 * There is ~10% chance null will be returned instead.
 */
internal fun generateRandomFamilyName(): String? {
    if (tenPercentChance()) {
        return null
    }
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
 *
 * There is ~10% chance null will be returned instead.
 */
internal fun generateRandomGivenName(): String? {
    if (tenPercentChance()) {
        return null
    }
    return GIVEN_NAME[generateRandomInt(GIVEN_NAME.size)]
}

/**
 * Generate a random date of birth
 *
 * There is ~10% chance null will be returned instead.
 */
internal fun generateRandomYearMonthDayTriple(): Triple<Int, Int, Int>? {
    if (tenPercentChance()) {
        return null
    }
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
internal fun generateRandomTitle(employeeId: Int?): String? {
    if (tenPercentChance()) {
        return null
    }
    val baseTitle = GIVEN_NAME[generateRandomInt(GIVEN_NAME.size)] +
            TITLE_SUFFIX[generateRandomInt(TITLE_SUFFIX.size)]
    return when (employeeId) {
        in 0..2000 -> {
            "Chief $baseTitle"
        }
        in 2001..30000 -> {
            "Sr. $baseTitle"
        }
        in 30001..50000 -> {
            "Assoc. $baseTitle"
        }
        else -> {
            "Jr. $baseTitle"
        }
    }
}

/**
 * Some random purposes
 */
private val PURPOSE = arrayOf("Maintenance", "Package Delivery", "Consulting", "Family Member", "Other", "Lost", null)

/**
 * Generate a random purpose. Occasionally returns null to simulate the
 * real world (sigh).
 * There is ~10% chance null will be returned instead.
 */
internal fun generateRandomPurpose(): String? {
    if (tenPercentChance()) {
        return null
    }
    return PURPOSE[generateRandomInt(PURPOSE.size)]
}

/**
 * Generate random (fake) tax ID number of the form
 * 000-00-0000.
 *
 * There is ~10% chance null will be returned instead.
 */
internal fun generateRandomTaxIdNumber(): String? {
    if (tenPercentChance()) {
        return null
    }
    //
    // Simple but (more important) readable
    val part1 = "000"
    val part2 = "00" + generateRandomInt(99).toString()
    val part3 = "0000" + generateRandomInt(9999).toString()

    return "$part1-${part2.substring(part2.length - 2)}-${part3.substring(part3.length - 4)}"
}

/**
 * Create and return a [Worker] object filled with
 * random data.
 * There is ~10% chance null will be returned instead.
 */
fun createPerson(): Person? {
    if (tenPercentChance()) {
        return null
    }
    return Person(
            generateRandomFamilyName(),
            generateRandomGivenName(),
            generateRandomYearMonthDayTriple()?.toLocalDate()
    )
}

/**
 * Create and return a [Worker] object filled with
 * random data.
 * There is ~10% chance null will be returned instead.
 */
fun createWorker(): Worker? {
    if (tenPercentChance()) {
        return null
    }
    return Worker(
            familyName = generateRandomFamilyName(),
            givenName = generateRandomGivenName(),
            dateOfBirth = generateRandomYearMonthDayTriple()?.toLocalDate(),
            taxIdNumber = generateRandomTaxIdNumber())
}

/**
 * Create and return a [Guest] object.
 * There is ~10% chance null will be returned instead.
 */
fun createGuest(): Guest? {
    if (tenPercentChance()) {
        return null
    }
    return Guest(
            familyName = generateRandomFamilyName(),
            givenName = generateRandomGivenName(),
            dateOfBirth = generateRandomYearMonthDayTriple()?.toLocalDate(),
            taxIdNumber = generateRandomTaxIdNumber(),
            purpose = generateRandomPurpose()
    )
}

/**
 * Create and return a new [Employee] object.
 * There is ~10% chance null will be returned instead.
 */
fun createEmployee(): Employee? {
    //
    // ~10% of the time, this function will return null
    if (tenPercentChance()) {
        //
        // Looks like this is one of those times...
        return null
    }
    //
    // Go ahead and build out the Employee
    val familyName = generateRandomFamilyName()
    val givenName = generateRandomGivenName()
    val dateOfBirth = generateRandomYearMonthDayTriple()?.toLocalDate()
    val employeeId = generateRandomEmployeeId()
    val title = generateRandomTitle(employeeId = employeeId)
    val taxIdNumber = generateRandomTaxIdNumber()

    return Employee(familyName = familyName,
            givenName = givenName,
            dateOfBirth = dateOfBirth,
            employeeId = employeeId,
            title = title,
            taxIdNumber = taxIdNumber)
}
