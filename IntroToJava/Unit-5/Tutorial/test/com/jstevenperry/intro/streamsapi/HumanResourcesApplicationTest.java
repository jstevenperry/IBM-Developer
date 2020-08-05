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
package com.jstevenperry.intro.streamsapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class HumanResourcesApplicationTest {
    
    private static final Logger log = Logger.getLogger(HumanResourcesApplicationTest.class.getName());

    // Test fixtures
    private HumanResourcesApplication humanResourcesApplication;
    private List<Person> people;
    private List<Employee> employees;
    private List<Manager> managers;
    private List<Executive> executives;

    @BeforeEach
    void setUp() throws Exception {
        this.humanResourcesApplication = new HumanResourcesApplication();
        people = HumanResourcesApplication.createPeople();
        employees = HumanResourcesApplication.createEmployees();
        managers = HumanResourcesApplication.createManagers();
        executives = HumanResourcesApplication.createExecutives();

    }

    @AfterEach
    void tearDown() throws Exception {
        this.humanResourcesApplication = null;
        this.employees.clear();
        this.people.clear();
        this.managers.clear();
        this.executives.clear();
    }

    @Test
    void testFilterBonusEligible() {
        // Only managers are BonusEligible
        List<BonusEligible> expected = new ArrayList<>(this.managers);
        expected.addAll(executives);
        // Invoke the method under test
        List<Person> actual = humanResourcesApplication.filterBonusEligible(people);
        // The list of BonusEligible and the filtered list of BonusEligible should be
        // the same
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(ints = { 80000, 100000, 200000 })
    void testFilterSalaryGreaterThan(final int threshold) {
        // Invoke the method under test
        List<Person> actual = humanResourcesApplication.filterSalaryGreaterThan(threshold, people);
        // Look at each element in the list, and the salary attribute cannot be less than the threshold
        assertTrue(actual.size() > 0);
        actual.forEach(person -> {
            assertTrue(person instanceof Employee);
            BigDecimal actualSalary = ((Employee)person).getSalary();
            // Make sure the threshold is less than the actual salary
            assertTrue(firstIsLessThanSecond(BigDecimal.valueOf(threshold), actualSalary));
        });
    }
    
    @Test
    void testMapNames() {
        // Invoke the method under test
        List<String> names = humanResourcesApplication.mapNames(people);
        // There should be as many names as there are people (everyone has a name)
        assertEquals(people.size(), names.size());
        // Print the names out
        names.forEach(name -> log.info("Name: " + name));
    }

    @Test
    void testMapManager() {
        // Managers include Managers and Executives
        List<Manager> expected = new ArrayList<>(managers);
        expected.addAll(executives);
        // Invoke the method under test
        List<Manager> actual = humanResourcesApplication.mapManager(this.people);
        // The list of Employee and the mapped list of Person -> Employee should be the same
        assertEquals(expected, actual);
    }

    @Test
    void testPromoteBlueEyedEmployeesToManager() {
        List<Manager> actual = humanResourcesApplication.promoteBlueEyedEmployeesToManager(employees);
        assertFalse(actual.isEmpty());
        actual.forEach(manager -> {
            assertTrue(manager.getEyeColor().equalsIgnoreCase("BLUE"));
            log.info("Manager: " + manager);
        });
    }

    @Test
    void testMapEyeColors() {
        List<String> actual = humanResourcesApplication.mapEyeColors(this.people);
        assertFalse(actual.isEmpty());
        actual.forEach(eyeColor -> {
            log.info("Eye Color: " + eyeColor);
        });
    }

    @Test
    void testMapUniqueEyeColors() {
        Set<String> actual = humanResourcesApplication.mapUniqueEyeColors(this.people);
        assertFalse(actual.isEmpty());
        actual.forEach(eyeColor -> {
            log.info("Eye Color: " + eyeColor);
        });
    }

    @Test
    void testCollectByEyeColor() {
        Map<String, List<Person>> actual = humanResourcesApplication.collectByEyeColor(this.people);
        actual.keySet().forEach(eyeColor -> {
            log.info("Eye Color: " + eyeColor);
            List<Person> peopleWithThatEyeColor = actual.get(eyeColor);
            peopleWithThatEyeColor.forEach(person -> log.info("Person: " + person.getName() + " with eyes of " + person.getEyeColor()));
        });
    }

    @Test
    void testFlattenEyeColorMap() {
        Map<String, List<Person>> eyeColorMap = humanResourcesApplication.collectByEyeColor(this.people);
        List<Person> actual = humanResourcesApplication.flattenEyeColorMap(eyeColorMap);
        actual.forEach(person -> log.info("Person: " + person.getName() + " with eyes of " + person.getEyeColor()));
    }

    /**
     * Helper method - returns true if the left-hand-side argument is less
     * than the right-hand-side argument, false otherwise.
     */
    private static boolean firstIsLessThanSecond(final BigDecimal lhs, final BigDecimal rhs) {
        return lhs.compareTo(rhs) < 0;
    }

}
