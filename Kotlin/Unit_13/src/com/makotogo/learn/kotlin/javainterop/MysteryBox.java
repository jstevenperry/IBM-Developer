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

package com.makotogo.learn.kotlin.javainterop;

import com.makotogo.learn.kotlin.model.Employee;
import com.makotogo.learn.kotlin.model.Guest;
import com.makotogo.learn.kotlin.model.Person;
import com.makotogo.learn.kotlin.model.Worker;

/**
 * Represents a third-party system with which sometimes you're required
 * to interact. You can't control what it does, and you can't choose
 * another system (a common situation in corporate IT dev).
 */
@SuppressWarnings({"UnusedReturnValue", "ConstantConditions"})
public class MysteryBox {

    /**
     * Use the Kotlin ObjectGenerator to create and return an Employee object
     *
     * @return - an Employee object
     */
    public Employee fetchNewHire() {

        Employee ret = ObjectGeneratorKt.createEmployee();
        if (ret != null) {
            System.out.println("Created Employee: " +
                    "FamilyName=" + ret.getFamilyName() + "," +
                    "GivenName=" + ret.getGivenName() + "," +
                    "DateOfBirth=" + ret.getDateOfBirth() + "," +
                    "TaxIdNumber=" + ret.getTaxIdNumber() + "," +
                    "EmployeeId=" + ret.getEmployeeId() + "," +
                    "Title=" + ret.getTitle()
            );
        } else {
            System.out.println("Created Employee: null");
        }
        return ret;
    }

    /**
     * Use the Kotlin ObjectGenerator to create and return a Guest object
     *
     * @return - a Guest object
     */
    private Guest fetchGuest() {
        Guest ret = ObjectGeneratorKt.createGuest();
        if (ret != null) {
            System.out.println("Created Guest: " +
                    "FamilyName=" + ret.getFamilyName() + "," +
                    "GivenName=" + ret.getGivenName() + "," +
                    "DateOfBirth=" + ret.getDateOfBirth() + "," +
                    "TaxIdNumber=" + ret.getTaxIdNumber() + "," +
                    "Purpose=" + ret.getPurpose()
            );
        } else {
            System.out.println("Created Guest: null");
        }
        return ret;
    }

    /**
     * Use the Kotlin ObjectGenerator to create and return a Worker object
     *
     * @return - a Worker object
     */
    private Worker fetchWorker() {
        Worker ret = ObjectGeneratorKt.createWorker();
        if (ret != null) {
            System.out.println("Created Worker: " +
                    "FamilyName=" + ret.getFamilyName() + "," +
                    "GivenName=" + ret.getGivenName() + "," +
                    "DateOfBirth=" + ret.getDateOfBirth() + "," +
                    "TaxIdNumber=" + ret.getTaxIdNumber()
            );
        } else {
            System.out.println("Created Worker: null");
        }
        return ret;
    }

    /**
     * Use the Kotlin ObjectGenerator to create and return a Person object
     *
     * @return - a Person object
     */
    private Person fetchPerson() {
        Person ret = ObjectGeneratorKt.createPerson();
        if (ret != null) {
            System.out.println("Created Person: " +
                    "FamilyName=" + ret.getFamilyName() + "," +
                    "GivenName=" + ret.getGivenName() + "," +
                    "DateOfBirth=" + ret.getDateOfBirth()
            );
        } else {
            System.out.println("Created Person: null");
        }
        return ret;
    }

    /**
     * Use the Kotlin ObjectGenerator to return an object. Ostensibly a
     * Person object, but not always.
     *
     * ~10% of the time null will be returned.
     *
     * Why? It's a mystery.
     *
     * @return - a Person object (as Object)
     */
    public Object mysteryObject() {
        //
        // Default return value: Person object
        Object ret = fetchPerson();
        //
        // Generate a random int between 0 and 9 (inclusive0
        int randomInt = ObjectGeneratorKt.generateRandomInt(100);
        //
        // Depending on the value of randomInt, return a different object
        if (10 <= randomInt && randomInt < 30) {
            ret = fetchGuest();
        } else if (30 <= randomInt && randomInt < 60) {
            ret = fetchNewHire();
        } else if (60 <= randomInt && randomInt < 80) {
            ret = fetchWorker();
        } else if (80 <= randomInt && randomInt < 90) {
            ret = null;
        } else if (randomInt >= 90) {
            if (randomInt < 95) {
                // If between 90 and 94, return a Char
                ret = ObjectGeneratorKt.createChar();
            } else {
                // Return a Float
                ret = ObjectGeneratorKt.createFloat(0);
            }
        }
        //
        // Return the object
        return ret;
    }
}

