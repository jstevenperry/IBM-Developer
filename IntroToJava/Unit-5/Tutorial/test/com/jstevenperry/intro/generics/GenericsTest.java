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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GenericsTest {

    private Generics generics;

    @BeforeEach
    public void setUp() {
        generics = new Generics();
    }

    @AfterEach
    public void tearDown() {
        generics = null;
    }

    @Test
    public void testListing3() {
        generics.listing3();
    }

    @Test
    public void testListing4() {
        assertThrows(ClassCastException.class, () -> generics.listing4());
    }

    @Test
    public void testListing5_compileError() {
        generics.listing5();
    }

    @Test
    public void testIteratingWithGenerics() {
        generics.iteratingWithGenerics();
    }

    @Test
    public void testFormatArray() {
        // Integer array
        Integer[] integerArray = { Integer.valueOf(1), Integer.valueOf(2) };
        String expected = generics.formatArray(integerArray);
        assertEquals("Element 0 => 1, Element 1 => 2", expected);

        // String array
        String[] stringArray = { "Hello", "there" };
        expected = generics.formatArray(stringArray);
        assertEquals("Element 0 => Hello, Element 1 => there", expected);
    }

}
