package com.jstevenperry.intro;

import static org.junit.jupiter.api.Assertions.*;

import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

class PersonTest {

	@Test
	public void testPerson() {
		Person p = new Person("Joe Q Author", 42, 173, 82, "Brown", "MALE");
		
		Logger l = Logger.getLogger(Person.class.getName());
		l.info("Created Person object named: " + p.getName());
		
		assertEquals("Joe Q Author", p.getName());
		assertEquals(42, p.getAge());
		assertEquals(173, p.getHeight());
		assertEquals(82, p.getWeight());
		assertEquals("Brown", p.getEyeColor());
		assertEquals("MALE", p.getGender());
	}

}
