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

import com.makotogo.learn.kotlin.model.Employee
import com.makotogo.learn.kotlin.model.Person
import java.util.Random

private val rng = Random(System.currentTimeMillis())

/**
 * Create a Person object using randomly generated data.
 */
fun createPerson(): Person {
    return Person(generateRandomFamilyName(), generateRandomGivenName())
}

/**
 * Create an Employee object filled with random data
 */
fun createEmployee(): Employee {
    val familyName = generateRandomFamilyName()
    val givenName = generateRandomGivenName()
    val employeeId = generateRandomEmployeeId()
    val title = generateRandomTitle(employeeId)
    return Employee(familyName = familyName, givenName = givenName, employeeId = employeeId, title = title)
}

/**
 * Generate and return a random floating point number
 */
fun createFloat() = (1.0f / generateRandomNumber(100).toFloat()) * 1000

/**
 * Generate and return a random Char from one of the printable ASCII characters
 *
 */
fun createChar() = (generateRandomNumber(bound = (127 - 33)) + 33).toChar()

/**
 * Generate and return a random number between zero and [bound]
 */
internal fun generateRandomNumber(bound: Int): Int {
    // Warmup the generator first
    for (aa in 0..111) {
        rng.nextInt(bound)
    }
    // Generate a "random" number
    return rng.nextInt(bound)
}

private val FAMILY_NAME = arrayOf(
        "Anon",
        "Bazog",
        "Con",
        "Daon",
        "Engan",
        "Fan",
        "Grale",
        "Hor",
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
private fun generateRandomFamilyName(): String {
    return FAMILY_NAME[generateRandomNumber(FAMILY_NAME.size)]
}

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
        "Oon",
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
private fun generateRandomGivenName(): String {
    return GIVEN_NAME[generateRandomNumber(GIVEN_NAME.size)]
}

/**
 * Generate a random [employeeId] from 0 - 99999
 */
private fun generateRandomEmployeeId(): Int {
    return generateRandomNumber(99999)
}

/**
 * Some random title suffixes
 */
private val TITLE_SUFFIX = arrayOf("ist", "er", "onator", "erator", "o")

/**
 * Generate and return a random (and probably silly) [title]
 */
private fun generateRandomTitle(employeeId: Int?): String {
    var ret = ""
    val baseTitle = GIVEN_NAME[generateRandomNumber(GIVEN_NAME.size)] +
            TITLE_SUFFIX[generateRandomNumber(TITLE_SUFFIX.size)]
    when (employeeId) {
        in 0..2000 -> {
            ret = "Chief $baseTitle"
        }
        in 2001..30000 -> {
            ret = "Sr. $baseTitle"
        }
        in 30001..70000 -> {
            ret = "Assoc. $baseTitle"
        }
        else -> {
            ret = "Jr. $baseTitle"
        }
    }
    return ret
}