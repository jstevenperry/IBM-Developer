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
package com.jstevenperry.intro.lvti;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class InputOutputTest {

    private static final String ROOT_DIRECTORY = "./data_files/";

    private static final Logger log = Logger.getLogger(InputOutputTest.class.getName());

    private InputOutput inputOutput;

    @BeforeEach
    public void setUp() throws Exception {
        this.inputOutput = new InputOutput();
    }

    @AfterEach
    public void tearDown() throws Exception {
        this.inputOutput = null;
    }

    @AfterAll
    public static void tearDownAfterAll() throws Exception {
        // Make sure to cleanup this file.
        // Q: Why? What happens if you run this test class twice without the code below
        // (comment it out and see)?
        Files.delete(Path.of(ROOT_DIRECTORY + "noSuchFile.txt"));
        Files.delete(Path.of(ROOT_DIRECTORY + "10Words.txt.out"));
        Files.delete(Path.of(ROOT_DIRECTORY + "10kWords.txt.out"));
        Files.delete(Path.of(ROOT_DIRECTORY + "100kWords.txt.out"));
        Files.delete(Path.of(ROOT_DIRECTORY + "1000kWords.txt.out"));
    }

    @ParameterizedTest
    @ValueSource(strings = { ROOT_DIRECTORY + "noSuchFile.txt", ROOT_DIRECTORY + "10kWords.txt" })
    public void testCreateFile(final String inputFilename) {
        try {
            var file = inputOutput.createFileUnlessItExists(inputFilename);
            assertEquals(inputFilename, file.getPath());
        } catch (Exception e) {
            log.log(Level.SEVERE, "Exception occurred: ", e);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { ROOT_DIRECTORY + "10Words.txt", ROOT_DIRECTORY + "10kWords.txt",
            ROOT_DIRECTORY + "100kWords.txt", ROOT_DIRECTORY + "1000kWords.txt" })
    public void testReadWordsUnbufferedStream(final String inputFilename) {
        var words = inputOutput.readWordsUnbufferedStream(inputFilename);
        assertFalse(words.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = { ROOT_DIRECTORY + "10Words.txt", ROOT_DIRECTORY + "10kWords.txt",
            ROOT_DIRECTORY + "100kWords.txt", ROOT_DIRECTORY + "1000kWords.txt" })
    public void testSaveWordsUnbufferedStream(final String inputFilename) {
        var words = inputOutput.readWordsUnbufferedStream(inputFilename);
        assertFalse(words.isEmpty());
        var outputFilename = inputFilename + ".out";
        var numberOfWordsWritten = inputOutput.saveWordsUnbufferedStream(outputFilename, words);
        assertEquals(words.size(), numberOfWordsWritten);
    }

    @ParameterizedTest
    @ValueSource(strings = { ROOT_DIRECTORY + "10Words.txt", ROOT_DIRECTORY + "10kWords.txt",
            ROOT_DIRECTORY + "100kWords.txt", ROOT_DIRECTORY + "1000kWords.txt" })
    public void testReadWordsBufferedStream(final String inputFilename) {
        var words = inputOutput.readWordsBufferedStream(inputFilename);
        assertFalse(words.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = { ROOT_DIRECTORY + "10Words.txt", ROOT_DIRECTORY + "10kWords.txt",
            ROOT_DIRECTORY + "100kWords.txt", ROOT_DIRECTORY + "1000kWords.txt" })
    public void testSaveWordsBufferedStream(final String inputFilename) {
        var words = inputOutput.readWordsBufferedStream(inputFilename);
        assertFalse(words.isEmpty());
        var outputFilename = inputFilename + ".out";
        var numberOfWordsWritten = inputOutput.saveWordsBufferedStream(outputFilename, words);
        assertEquals(words.size(), numberOfWordsWritten);
    }

}
