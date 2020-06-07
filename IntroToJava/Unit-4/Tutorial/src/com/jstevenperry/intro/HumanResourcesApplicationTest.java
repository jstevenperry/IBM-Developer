package com.jstevenperry.intro;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.jstevenperry.intro.HumanResourcesApplication.StockOptionProcessingCallback;

public class HumanResourcesApplicationTest {

	@Test
	public void testHandleStockOptions() {

		HumanResourcesApplication classUnderTest = new HumanResourcesApplication();

		List<Person> people = HumanResourcesApplication.createPeople();

		StockOptionProcessingCallback callback = new StockOptionProcessingCallback() {
			@Override
			public void process(StockOptionEligible stockOptionEligible) {
				BigDecimal reallyCheapPrice = BigDecimal.valueOf(0.01);
				int numberOfOptions = 10000;
				stockOptionEligible.processStockOptions(numberOfOptions, reallyCheapPrice);
			}
		};
		for (Person person : people) {
			classUnderTest.handleStockOptions(person, callback);
		}
	}
}