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
package com.jstevenperry.intro.enumtypes;

import static org.junit.jupiter.api.Assertions.*;

import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

public class GenderTest {

    private static final Logger log = Logger.getLogger(GenderTest.class.getName());

    @Test
    void testGetDisplayName() {
        Gender[] values = Gender.values();
        for (Gender gender : values) {
            log.info("Gender: value = " + gender.toString() + " display name = " + gender.getDisplayName());
        }
        assertEquals("PREFER_NOT_TO_SAY", Gender.PREFER_NOT_TO_SAY.toString());
        assertEquals("PreferNotToSay", Gender.PREFER_NOT_TO_SAY.getDisplayName());
        assertEquals("FEMALE", Gender.FEMALE.toString());
        assertEquals("Female", Gender.FEMALE.getDisplayName());
        assertEquals("MALE", Gender.MALE.toString());
        assertEquals("Male", Gender.MALE.getDisplayName());
    }

}
