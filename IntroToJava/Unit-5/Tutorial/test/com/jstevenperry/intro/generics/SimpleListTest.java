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
package com.jstevenperry.intro.generics;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

class SimpleListTest {

    private static final Logger log = Logger.getLogger(SimpleListTest.class.getName());

    @Test
    void testAdd() {
        SimpleList<BigDecimal> simpleList = new SimpleList<>();
        simpleList.add(BigDecimal.ONE);
        assertEquals(1, simpleList.size());
        log.info("SimpleList size is " + simpleList.size());
        
        simpleList.add(BigDecimal.ZERO);
        assertEquals(2, simpleList.size());
        log.info("SimpleList size is " + simpleList.size());
        
        simpleList.clear();
        assertEquals(0, simpleList.size());
        log.info("SimpleList size is " + simpleList.size());
    }

}
