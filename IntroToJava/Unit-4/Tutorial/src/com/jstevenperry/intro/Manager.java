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
import java.util.logging.Logger;

public class Manager extends Employee implements StockOptionEligible, BonusEligible {
	
	private static final Logger log = Logger.getLogger(Manager.class.getName());
	
	public Manager() {
		super();
	}
	
	
	public Manager(String name, int age, int height, int weight, String eyeColor, String gender,
			String taxpayerIdNumber, String employeeNumber, BigDecimal salary) {
		super(name, age, height, weight, eyeColor, gender, taxpayerIdNumber, employeeNumber, salary);
	}



	public Manager(String name, int age, int height, int weight, String eyeColor, String gender) {
		super(name, age, height, weight, eyeColor, gender);
	}



	@Override
	public void processStockOptions(int numberOfOptions, BigDecimal price) {
		log.info("Wow, I can't believe I got " + numberOfOptions + " options at $" + price.toPlainString() + "each!");
	}

}
