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
package com.jstevenperry.intro;

import static org.junit.jupiter.api.Assertions.*;

import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

class PersonTest {

	@Test
	void testPerson() {
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
	
	@Test
	public void testPrintAudit() {
		Person p = new Person("Joe Q Author", 42, 173, 82, "Brown", "MALE");
		
		Logger l = Logger.getLogger(Person.class.getName());
		
		p.printAudit(l);
	}
}
