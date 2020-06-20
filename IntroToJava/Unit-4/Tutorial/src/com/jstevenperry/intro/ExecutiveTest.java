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

class ExecutiveTest {

	@Test
	void testProcessStockOptions() {
		Executive e = new Executive();
		
		e.processStockOptions(10000, BigDecimal.valueOf(1.0));
    }
    	
	@Test
	void testCalculateBonus() {
		Executive e = new Executive();
		e.setSalary(BigDecimal.valueOf(1000000));
		
		assertEquals(BigDecimal.valueOf(200000.0), e.calculateBonus());
	}


}
