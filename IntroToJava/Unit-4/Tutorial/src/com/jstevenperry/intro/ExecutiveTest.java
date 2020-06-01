package com.jstevenperry.intro;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class ExecutiveTest {

	@Test
	void testProcessStockOptions() {
		Executive e = new Executive();
		
		e.processStockOptions(10000, BigDecimal.valueOf(1));
	}

}
