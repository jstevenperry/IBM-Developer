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
package com.jstevenperry.intro.lambdas;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class HumanResourcesApplicationTest {

    private HumanResourcesApplication humanResourcesApplication;
    
    @BeforeEach
    public void setUp() throws Exception {
        this.humanResourcesApplication = new HumanResourcesApplication();
    }

    @AfterEach
    public void tearDown() throws Exception {
        this.humanResourcesApplication = null;
    }

    @Nested
    @DisplayName("JUnit tests for handling stock options")
    public class StockOptionsTests {

        @Test
        @DisplayName("When using the long form")
        public void testLongForm() {
            List<Person> people = HumanResourcesApplication.createPeople();
            for (Person person : people) {
                humanResourcesApplication.handleStockOptions(person, new StockOptionProcessingCallback() {
    
                    @Override
                    public void process(StockOptionEligible employee) {
                        employee.processStockOptions(1000, BigDecimal.valueOf(1.0));
    
                    }
                });
            }
        }
    
        @Test
        @DisplayName("When using a lambda expression")
        public void testUsingLambdaExpression() {
            List<Person> people = HumanResourcesApplication.createPeople();
            people.forEach(person -> {
                boolean optionsAwarded = humanResourcesApplication.handleStockOptions(person, (stockOptionEligible -> {
                    stockOptionEligible.processStockOptions(1000, BigDecimal.valueOf(1.0));
                }));
                if (person instanceof StockOptionEligible) {
                    assertTrue(optionsAwarded);
                } else {
                    assertFalse(optionsAwarded);
                }
            });
        }
    }
    
    @Nested
    @DisplayName("JUnit tests for displaying stuff")
    public class DisplayableTests {
        
        @Test
        @DisplayName("When using the long form")
        public void testLongForm() {
            // People
            List<Person> people = HumanResourcesApplication.createPeople();
            for (Person person : people) {
                humanResourcesApplication.handleDisplayName(new Displayable() {
                    @Override
                    public String getDisplayName() {
                        return person.getName();
                    }
                });
            }
        }
    
        @Test
        @DisplayName("When using a lambda expression")
        public void testUsingambdaExpression() {
            List<Person> people = HumanResourcesApplication.createPeople();
            people.forEach(person -> humanResourcesApplication.handleDisplayName(person::getName));
    
        }
    }
}
