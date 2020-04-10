package com.jstevenperry.intro;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PersonTest {

	@Test
	public void testPerson() {
		Person p = new Person("Joe Q Author", 52, 174, 80, "Brown", "MALE");
		
		assertEquals("Joe Q Author", p.getName());
		assertEquals(52, p.getAge());
		assertEquals(174, p.getHeight());
		assertEquals(80, p.getWeight());
		assertEquals("Brown", p.getEyeColor());
		assertEquals("MALE", p.getGender());
	}

}
