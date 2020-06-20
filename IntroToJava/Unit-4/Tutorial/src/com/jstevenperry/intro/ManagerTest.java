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