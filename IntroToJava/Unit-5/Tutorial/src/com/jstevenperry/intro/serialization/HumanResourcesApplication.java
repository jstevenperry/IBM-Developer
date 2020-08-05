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
package com.jstevenperry.intro.serialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jstevenperry.intro.common.Employee;
import com.jstevenperry.intro.common.Gender;

public class HumanResourcesApplication {

    private static final Logger log = Logger.getLogger(HumanResourcesApplication.class.getName());

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

    public boolean serializeToDisk(String filename, List<Employee> employees) {
        boolean ret = false;// default: failed
        File file = new File(filename);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            log.info("Writing " + employees.size() + " Employee objects to disk (using Java serialization)...");
            outputStream.writeObject(employees);
            ret = true; // Looks good
            log.info("Done.");
        } catch (IOException e) {
            log.log(Level.SEVERE, "Cannot find file " + file.getName() + ", message = " + e.getLocalizedMessage(), e);
        }
        return ret;
    }

    public List<Employee> deserializeFromDisk(String filename) {
        List<Employee> ret = new ArrayList<>();
        File file = new File(filename);
        int numberOfEmployees = 0;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            @SuppressWarnings("unchecked")
            List<Employee> employees = (List<Employee>) inputStream.readObject();
            log.info("Deserialized List says it contains " + employees.size() + " objects...");
            for (Employee employee : employees) {
                log.info("Read Employee: " + employee.toString());
                numberOfEmployees++;
            }
            ret = employees;
            log.info("Read " + numberOfEmployees + " Employee objects from disk (using Java serialization).");
        } catch (FileNotFoundException e) {
            log.log(Level.SEVERE, "Cannot find file " + file.getName() + ", message = " + e.getLocalizedMessage(), e);
        } catch (IOException e) {
            log.log(Level.SEVERE, "IOException occurred: message = " + e.getLocalizedMessage(), e);
        } catch (ClassNotFoundException e) {
            log.log(Level.SEVERE, "ClassNotFoundException: message = " + e.getLocalizedMessage(), e);
        }
        return ret;
    }

}
