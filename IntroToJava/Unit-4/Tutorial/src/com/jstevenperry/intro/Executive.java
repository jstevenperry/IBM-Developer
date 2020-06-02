package com.jstevenperry.intro;

import java.math.BigDecimal;
import java.util.logging.Logger;

public class Executive extends Manager {
	
	private static final Logger log = Logger.getLogger(Executive.class.getName());
	
	public Executive() {
		super();
	}

	@Override
	public void processStockOptions(int numberOfOptions, BigDecimal price) {
		BigDecimal executivePrice = price.multiply(BigDecimal.valueOf(0.9)).setScale(2);
		
		log.info("Wow, I can't believe I got " + numberOfOptions + " at $" + executivePrice.toPlainString() + " each!");
	}
	
	@Override
	public BigDecimal calculateBonus() {
		return getSalary().multiply(BigDecimal.valueOf(0.2));
	}

}