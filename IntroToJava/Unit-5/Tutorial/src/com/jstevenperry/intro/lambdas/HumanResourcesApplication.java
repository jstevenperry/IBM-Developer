/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jstevenperry.intro.lambdas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class HumanResourcesApplication {

    private static final Logger log = Logger.getLogger(HumanResourcesApplication.class.getName());

    public boolean handleStockOptions(final Person person, final StockOptionProcessingCallback callback) {
        boolean retVal;
        if (person instanceof StockOptionEligible) {
            // Eligible Person, invoke the callback straight up
            callback.process((StockOptionEligible) person);
            retVal = true; // options awarded
        } else if (person instanceof Employee) {
            // Not eligible, but still an Employee. Let's create an
            /// anonymous inner class implementation for this
            callback.process(new StockOptionEligible() {
                @Override
                public void processStockOptions(int number, BigDecimal price) {
                    // This employee is not eligible
                    log.warning("Unfortunately, Employee " + person.getName() + " is not eligible for Stock Options!");
                }
            });
            retVal = false;
        } else {
            callback.process((number, price) -> {
                log.severe("Cannot consider awarding " + number + " options because " + person.getName() + " does not even work here!");
            });
            retVal = false;
        }
        return retVal;
    }

    public void handleDisplayName(final Displayable displayable) {
        log.info("Display name: " + displayable.getDisplayName());
    }

    public static List<Person> createPeople() {
        List<Person> ret = new ArrayList<>();
        // First create and add some people
        Person p = new Person("Jane Doe", 24, 150, 50, "BLUE", Gender.FEMALE);
        ret.add(p);
        //
        p = new Person("Peter Parker", 34, 175, 76, "GREEN", Gender.MALE);
        ret.add(p);
        // Now create some Employees
        ret.addAll(createEmployees());
        // Now create some Managers
        ret.addAll(createManagers());
        // Now create some Executives
        ret.addAll(createExecutives());
        return ret;
    }

    /**
     * Canned method to create Employees that will be part of the Human Resources
     * "database". For purely teaching purposes only. Once we create a List of
     * Employees we can do lots of different things with it: write out to disk (then
     * read back in), use for a search feature, etc.
     * 
     * @return List<Employee> - List of Employee objects
     */
    public static List<Employee> createEmployees() {
        List<Employee> ret = new ArrayList<>();
        Employee e = new Employee("Jon Smith", 45, 175, 75, "BLUE", Gender.MALE, "123-45-9999", "0001",
                BigDecimal.valueOf(100000.0));
        ret.add(e);
        //
        e = new Employee("Jon Jones", 40, 185, 85, "BROWN", Gender.MALE, "223-45-9999", "0002",
                BigDecimal.valueOf(110000.0));
        ret.add(e);
        //
        e = new Employee("Mary Smith", 35, 155, 55, "GREEN", Gender.FEMALE, "323-45-9999", "0003",
                BigDecimal.valueOf(120000.0));
        ret.add(e);
        //
        e = new Employee("Chris Johnson", 38, 165, 65, "HAZEL", Gender.PREFER_NOT_TO_SAY, "423-45-9999", "0004",
                BigDecimal.valueOf(90000.0));
        ret.add(e);
        // Return list of Employees
        return ret;
    }

    public static List<Manager> createManagers() {
        List<Manager> ret = new ArrayList<>();
        Manager m = new Manager("Clancy Snodgrass", 45, 180, 90, "BLUE", Gender.MALE, "456-45-9999", "0100",
                BigDecimal.valueOf(245000.0));
        ret.add(m);
        //
        m = new Manager("Mary Snodgrass", 44, 170, 55, "BLUE", Gender.FEMALE, "567-45-9999", "0101",
                BigDecimal.valueOf(250000.0));
        ret.add(m);
        //
        m = new Manager("Antonia Nada", 33, 178, 50, "BROWN", Gender.FEMALE, "567-45-1122", "0102",
                BigDecimal.valueOf(150000.0));
        ret.add(m);
        return ret;
    }

    public static List<Executive> createExecutives() {
        List<Executive> ret = new ArrayList<>();
        Executive e = new Executive("Pat Patford", 55, 190, 90, "GREEN", Gender.PREFER_NOT_TO_SAY, "123-99-1111", "0100",
                BigDecimal.valueOf(445000.0));
        ret.add(e);
        //
        return ret;
    }

}
