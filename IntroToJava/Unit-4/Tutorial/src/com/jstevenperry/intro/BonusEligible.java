package com.jstevenperry.intro;

import java.math.BigDecimal;

public interface BonusEligible {
	
	BigDecimal getSalary();
	
	default BigDecimal calculateBonus() {
		// Default bonus: 10% of salary
		return getSalary().multiply(BigDecimal.valueOf(0.1));
	}

}