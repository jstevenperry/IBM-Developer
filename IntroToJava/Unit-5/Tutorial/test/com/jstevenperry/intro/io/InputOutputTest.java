package com.jstevenperry.intro.io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


class InputOutputTest {

    private static final String ROOT_DIRECTORY = "./test/com/jstevenperry/intro/io/";

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
        // Q: Why? What happens if you run this test class twice without the code below (comment it out and see)?
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
            File file = inputOutput.createFileUnlessItExists(inputFilename);
            assertEquals(inputFilename, file.getPath());
        } catch (Exception e) {
            log.log(Level.SEVERE, "Exception occurred: ", e);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { ROOT_DIRECTORY + "10Words.txt", 
            ROOT_DIRECTORY + "10kWords.txt", 
            ROOT_DIRECTORY + "100kWords.txt",
            ROOT_DIRECTORY + "1000kWords.txt"
            })
    public void testReadWordsUnbufferedStream(final String inputFilename) {
        List<String> words = inputOutput.readWordsUnbufferedStream(inputFilename);
        assertFalse(words.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = { ROOT_DIRECTORY + "10Words.txt", 
            ROOT_DIRECTORY + "10kWords.txt", 
            ROOT_DIRECTORY + "100kWords.txt",
            ROOT_DIRECTORY + "1000kWords.txt"
            })
    public void testSaveWordsUnbufferedStream(final String inputFilename) {
        List<String> words = inputOutput.readWordsUnbufferedStream(inputFilename);
        assertFalse(words.isEmpty());
        String outputFilename = inputFilename + ".out";
        int numberOfWordsWritten = inputOutput.saveWordsUnbufferedStream(outputFilename, words);
        assertEquals(words.size(), numberOfWordsWritten);
    }

    @ParameterizedTest
    @ValueSource(strings = { ROOT_DIRECTORY + "10Words.txt", 
            ROOT_DIRECTORY + "10kWords.txt", 
            ROOT_DIRECTORY + "100kWords.txt",
            ROOT_DIRECTORY + "1000kWords.txt"
            })
    public void testReadWordsBufferedStream(final String inputFilename) {
        List<String> words = inputOutput.readWordsBufferedStream(inputFilename);
        assertFalse(words.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = { ROOT_DIRECTORY + "10Words.txt", 
            ROOT_DIRECTORY + "10kWords.txt", 
            ROOT_DIRECTORY + "100kWords.txt",
            ROOT_DIRECTORY + "1000kWords.txt"
            })
    public void testSaveWordsBufferedStream(final String inputFilename) {
        List<String> words = inputOutput.readWordsBufferedStream(inputFilename);
        assertFalse(words.isEmpty());
        String outputFilename = inputFilename + ".out";
        int numberOfWordsWritten = inputOutput.saveWordsBufferedStream(outputFilename, words);
        assertEquals(words.size(), numberOfWordsWritten);
    }

}
