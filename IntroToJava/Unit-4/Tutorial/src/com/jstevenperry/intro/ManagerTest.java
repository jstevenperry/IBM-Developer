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

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class ManagerTest {

	@Test
	void testProcessStockOptions() {
		Manager m = new Manager();
		
		m.processStockOptions(1000, BigDecimal.valueOf(1.0));
	}
	
	@Test
	void testCalculateBonus() {
		Manager m = new Manager();
		m.setSalary(BigDecimal.valueOf(100000));
		
		BigDecimal bonus = m.calculateBonus();
		assertEquals(BigDecimal.valueOf(10000.0), bonus);
	}

}