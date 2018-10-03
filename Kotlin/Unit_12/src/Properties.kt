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

import com.makotogo.learn.kotlin.model.Guest
import com.makotogo.learn.kotlin.util.generateRandomFamilyName
import com.makotogo.learn.kotlin.util.generateRandomGivenName
import com.makotogo.learn.kotlin.util.generateRandomPurpose
import com.makotogo.learn.kotlin.util.generateRandomTaxIdNumber
import com.makotogo.learn.kotlin.util.generateRandomYearMonthDayTriple
import com.makotogo.learn.kotlin.util.toLocalDate

fun accessProperty() {
    println("********** accessProperty() **********")
    // Generate random attribute values
    val familyName = generateRandomFamilyName()
    val givenName = generateRandomGivenName()
    val dateOfBirth = generateRandomYearMonthDayTriple().toLocalDate()
    val taxIdNumber = generateRandomTaxIdNumber()
    //
    // Create a Guest - invoke secondary constructor
    println("Instantiating Guest")
    val guest = Guest(
            familyName = familyName,
            givenName = givenName,
            dateOfBirth = dateOfBirth,
            taxIdNumber = taxIdNumber,
            purpose = generateRandomPurpose()
    )

    println("Guest: familyName=${guest.familyName}, givenName=${guest.givenName}, " +
            "dateOfBirth=${guest.dateOfBirth}, taxIdNumber=${guest.taxIdNumber}, " +
            "purpose=${guest.purpose}")
}

fun main(args: Array<String>) {
    accessProperty()
}