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

import java.math.BigDecimal;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

public class EmployeeTest {

	@SuppressWarnings("deprecation")
	@Test
	public void test() {
		int int1 = 1;
		int int2 = 1;
		Logger l = Logger.getLogger(EmployeeTest.class.getName());

		l.info("Q: int1 == int2?           A: " + (int1 == int2));
		Integer integer1 = Integer.valueOf(int1);
		Integer integer2 = Integer.valueOf(int2);
		l.info("Q: Integer1 == Integer2?   A: " + (integer1 == integer2));
		integer1 = new Integer(int1);
		integer2 = new Integer(int2);
		l.info("Q: Integer1 == Integer2?   A: " + (integer1 == integer2));
		Employee employee1 = new Employee();
		Employee employee2 = new Employee();
		l.info("Q: Employee1 == Employee2? A: " + (employee1 == employee2));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void anotherTest() {
		Logger l = Logger.getLogger(Employee.class.getName());
		Integer integer1 = Integer.valueOf(1);
		Integer integer2 = Integer.valueOf(1);
		l.info("Q: integer1 == integer2 ? A: " + (integer1 == integer2));
		l.info("Q: integer1.equals(integer2) ? A: " + integer1.equals(integer2));
		integer1 = new Integer(integer1);
		integer2 = new Integer(integer2);
		l.info("Q: integer1 == integer2 ? A: " + (integer1 == integer2));
		l.info("Q: integer1.equals(integer2) ? A: " + integer1.equals(integer2));
		Employee employee1 = new Employee();
		Employee employee2 = new Employee();
		l.info("Q: employee1 == employee2 ? A: " + (employee1 == employee2));
		l.info("Q: employee1.equals(employee2) ? A : " + employee1.equals(employee2));
	}
	
	@SuppressWarnings("null")
	@Test
	public void yetAnotherTest() {
		Logger l = Logger.getLogger(Employee.class.getName());

		//    Employee employee1 = new Employee();
		try {
			Employee employee1 = null;
			employee1.setName("J Smith");
			Employee employee2 = new Employee();
			employee2.setName("J Smith");
			l.info("Q: employee1 == employee2?      A: " + (employee1 == employee2));
			l.info("Q: employee1.equals(employee2)? A: " + employee1.equals(employee2));
		} catch (Exception e) {
			l.severe("Caught exception: " + e.getMessage());
		} finally {
			// Always executes
		}	
	}

	@Test
	public void testPrintAudit() {
		Employee e = new Employee("Joe Q Author", 42, 173, 82, "Brown", "MALE");
		e.setSalary(BigDecimal.valueOf(95000L));
		e.setEmployeeNumber("A123");
		e.setTaxpayerIdNumber("123-45-6789");

		Logger l = Logger.getLogger(EmployeeTest.class.getName());

		e.printAudit(l);
	}
	
}
