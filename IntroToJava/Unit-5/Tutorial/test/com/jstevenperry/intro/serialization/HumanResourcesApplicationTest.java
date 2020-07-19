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

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HumanResourcesApplicationTest {

    private static final String ROOT_DIRECTORY = "./test/com/jstevenperry/intro/serialization/";

    private HumanResourcesApplication humanResourcesApplication;

    @BeforeEach
    void setUp() throws Exception {
        this.humanResourcesApplication = new HumanResourcesApplication();
    }

    @AfterEach
    void tearDown() throws Exception {
        this.humanResourcesApplication = null;
        Files.delete(Path.of(ROOT_DIRECTORY + "Employees-JUnit.ser"));
    }

    @Test
    void testSerializeToDisk() {
        List<Employee> employees = HumanResourcesApplication.createEmployees();
        String filename = ROOT_DIRECTORY + "Employees-JUnit.ser";
        boolean serializationSucceeded = humanResourcesApplication.serializeToDisk(filename, employees);
        assertTrue(serializationSucceeded);
    }

    @Test
    void testDeserializeFromDisk() {
        List<Employee> employees = HumanResourcesApplication.createEmployees();
        String filename = ROOT_DIRECTORY + "Employees-JUnit.ser";
        boolean serializationSucceeded = humanResourcesApplication.serializeToDisk(filename, employees);
        assertTrue(serializationSucceeded);
        List<Employee> deserializedEmployees = (List<Employee>) humanResourcesApplication.deserializeFromDisk(filename);
        assertEquals(employees.size(), deserializedEmployees.size());
        assertEquals(employees, deserializedEmployees);
    }

}
