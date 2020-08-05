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
package com.jstevenperry.intro.streamsapi;

import java.math.BigDecimal;
import java.util.logging.Logger;

public class Executive extends Manager {

    /**
     * 
     */
    private static final long serialVersionUID = -8150702603364610459L;

    private static final Logger log = Logger.getLogger(Executive.class.getName());

    public Executive() {
        super();
    }
    
    public Executive(String name, int age, int height, int weight, String eyeColor, Gender gender,
            String taxpayerIdNumber, String employeeNumber, BigDecimal salary) {
        super(name, age, height, weight, eyeColor, gender, taxpayerIdNumber, employeeNumber, salary);
    }

    @Override
    public void processStockOptions(int numberOfOptions, BigDecimal price) {
        BigDecimal executivePrice = price.multiply(BigDecimal.valueOf(0.9)).setScale(2);

        log.info("Wow, I, " + getName() + ", can't believe I got " + numberOfOptions + " options at $" + executivePrice.toPlainString() + " each!");
    }

    @Override
    public BigDecimal calculateBonus() {
        return getSalary().multiply(BigDecimal.valueOf(0.2));
    }

}