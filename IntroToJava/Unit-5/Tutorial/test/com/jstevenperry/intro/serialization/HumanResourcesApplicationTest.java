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
