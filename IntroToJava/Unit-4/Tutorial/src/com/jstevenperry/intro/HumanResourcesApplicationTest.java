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