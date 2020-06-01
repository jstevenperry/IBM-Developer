package com.jstevenperry.intro;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class ManagerTest {

	@Test
	void testProcessStockOptions() {
		Manager m = new Manager();
		
		m.processStockOptions(1000, BigDecimal.valueOf(1));
	}

}