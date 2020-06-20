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
