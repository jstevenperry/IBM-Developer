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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.jstevenperry.intro.common.BonusEligible;
import com.jstevenperry.intro.common.Employee;
import com.jstevenperry.intro.common.Executive;
import com.jstevenperry.intro.common.Gender;
import com.jstevenperry.intro.common.Manager;
import com.jstevenperry.intro.common.Person;

public class HumanResourcesApplication {
    
    public List<Person> filterBonusEligible(final List<Person> people) {
        return people
                .stream()
                .filter(person -> person instanceof BonusEligible)
                .collect(Collectors.toList());
    }
    
    public List<Person> filterSalaryGreaterThan(final Integer threshold, final List<Person> people) {
        return people
                .stream()
                .filter(person -> person instanceof Employee)
                .filter(person -> ((Employee)person).getSalary().intValue() > threshold)
                .collect(Collectors.toList());
    }
    
    public List<String> mapNames(final List<Person> people) {
        return people
                .stream()
                .map(person -> person.getName())
                .collect(Collectors.toList());
    }
    
    public List<Manager> mapManager(final List<Person> people) {
        return people
                .stream()
                .filter(person -> person instanceof Manager)
                .map(person -> (Manager)person)
                .collect(Collectors.toList());
    }
    
    public List<Manager> promoteBlueEyedEmployeesToManager(final List<Employee> employees) {
        return employees
                .stream()
                .filter(employee -> employee.getEyeColor().equalsIgnoreCase("BLUE"))
                .map(Manager::promote)
                .collect(Collectors.toList());
    }
    
    public List<String> mapEyeColors(final List<Person> people) {
        return people
                .stream()
                .map(Person::getEyeColor)
                .collect(Collectors.toList());
    }
    
    public Set<String> mapUniqueEyeColors(final List<Person> people) {
        return people
                .stream()
                .map(Person::getEyeColor)
                .collect(Collectors.toSet());
    }
    
    public Map<String, List<Person>> collectByEyeColor(final List<Person> people) {
        return people
                .stream()
                .collect(Collectors.groupingBy(Person::getEyeColor));
    }
    
    public List<Person> flattenEyeColorMap(final Map<String, List<Person>> eyeColorMap) {
        return eyeColorMap.keySet()
                .stream()
                .map(eyeColor -> eyeColorMap.get(eyeColor))
                .flatMap(listOfEyeColors -> listOfEyeColors.stream())
                .collect(Collectors.toList());
    }
    
    public int computeSum(final Stream<Integer> integers) {
        return integers.reduce(0, (current, value) -> current+value);
    }

    public static List<Person> createPeople() {
        List<Person> ret = new ArrayList<>();
        // First create and add some people
        ret.add(new Person("Jane Doe", 24, 150, 50, "BLUE", Gender.FEMALE));
        ret.add(new Person("James Doe", 25, 180, 80, "BLUE", Gender.MALE));
        //
        ret.add(new Person("Peter Parker", 34, 175, 76, "GREEN", Gender.MALE));
        ret.add(new Person("Allison Parker", 30, 165, 58, "HAZEL", Gender.FEMALE));
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
        ret.add(new Employee("Jon Smith", 45, 175, 75, "BLUE", Gender.MALE, "123-45-9999", "0001", BigDecimal.valueOf(80000.0)));
        ret.add(new Employee("Pankaj Patel", 26, 185, 85, "BROWN", Gender.MALE, "223-45-9999", "0002", BigDecimal.valueOf(90000.0)));
        ret.add(new Employee("Chris Johnson", 38, 165, 65, "HAZEL", Gender.PREFER_NOT_TO_SAY, "423-45-9999", "0004", BigDecimal.valueOf(90000.0)));
        ret.add(new Employee("Zhao Yu", 38, 165, 65, "BROWN", Gender.FEMALE, "423-45-9999", "0004", BigDecimal.valueOf(90000.0)));
        ret.add(new Employee("Jon Jones", 40, 185, 85, "BLUE", Gender.MALE, "223-45-9999", "0002", BigDecimal.valueOf(98000.0)));
        ret.add(new Employee("Lei Zhong", 35, 155, 55, "BROWN", Gender.FEMALE, "323-45-9999", "0003", BigDecimal.valueOf(100000.0)));
        ret.add(new Employee("Mary Smith", 35, 155, 55, "GREEN", Gender.FEMALE, "323-45-9999", "0003", BigDecimal.valueOf(105000.0)));
        ret.add(new Employee("Deepa Reddy", 30, 155, 85, "BROWN", Gender.MALE, "223-45-9999", "0002", BigDecimal.valueOf(110000.0)));
        // Return list of Employees
        return ret;
    }

    public static List<Manager> createManagers() {
        List<Manager> ret = new ArrayList<>();
        ret.add(new Manager("Clancy Snodgrass", 45, 180, 90, "BLUE", Gender.MALE, "456-45-9999", "0100", BigDecimal.valueOf(245000.0)));
        ret.add(new Manager("Mary Snodgrass", 44, 170, 55, "BLUE", Gender.FEMALE, "567-45-9999", "0101", BigDecimal.valueOf(250000.0)));
        ret.add(new Manager("Antonia Nada", 33, 178, 50, "BROWN", Gender.FEMALE, "567-45-1122", "0102", BigDecimal.valueOf(150000.0)));
        return ret;
    }

    public static List<Executive> createExecutives() {
        List<Executive> ret = new ArrayList<>();
        ret.add(new Executive("Pat Patford", 55, 190, 90, "GREEN", Gender.PREFER_NOT_TO_SAY, "123-99-1111", "0100", BigDecimal.valueOf(445000.0)));
        return ret;
    }

}
