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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class InputOutput {

    private static final Logger log = Logger.getLogger(InputOutput.class.getName());

    public File createFileUnlessItExists(String filename) throws IOException {

        var file = new File(filename);
        if (file.exists()) {
            // File exists. Process it...
            log.info("File '" + file.getName() + "' exists. Cannot create it.");
        } else {
            // File doesn't exist. Create it...
            log.info("Creating file '" + file.getPath() + "'.");
            file.createNewFile();
        }

        return file;

    }

    public List<String> readWordsUnbufferedStream(String wordsFilename) {
        var startTime = System.currentTimeMillis();

        // Return value: list of strings
        var ret = new ArrayList<String>();

        var wordsFile = new File(wordsFilename);

        var numberOfWords = 0;
        try (var reader = new InputStreamReader(new FileInputStream(wordsFile))) {
            var done = false;
            // While there is more in the file to read
            while (!done) {
                var charRead = reader.read();
                if (charRead == -1) {
                    done = true;
                } else {
                    StringBuilder word = new StringBuilder();
                    // Read the current word (file has one word per line)
                    while (charRead != -1 && charRead != '\n' && charRead != '\r') {
                        word.append(charRead);
                        charRead = reader.read();
                    }
                    ret.add(word.toString());
                    numberOfWords++;
                }
            }
        } catch (IOException e) {
            log.log(Level.SEVERE, "IOException occurred, message = " + e.getLocalizedMessage(), e);
        }

        log.info("Read " + numberOfWords + " words in " + Long.toString(System.currentTimeMillis() - startTime) + "ms");

        return ret;

    }

    public int saveWordsUnbufferedStream(String filename, List<String> words) {
        var startTime = System.currentTimeMillis();

        // Return value: number of words written
        var wordCount = 0;

        var file = new File(filename);
        try (var writer = new OutputStreamWriter(new FileOutputStream(file))) {
            for (var word : words) {
                if (wordCount > 0) {
                    writer.append(' ');
                }
                writer.write(word);
                wordCount++;
            }
        } catch (IOException e) {
            log.log(Level.SEVERE, "IOException occurred, message = " + e.getLocalizedMessage(), e);
        }

        log.info("Wrote " + wordCount + " words in " + Long.toString(System.currentTimeMillis() - startTime) + "ms");

        return wordCount;
    }

    public List<String> readWordsBufferedStream(String wordsFilename) {
        var startTime = System.currentTimeMillis();

        // Return value: list of strings
        var ret = new ArrayList<String>();

        var wordsFile = new File(wordsFilename);

        var numberOfWords = 0;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(wordsFile)))) {
            var line = reader.readLine();
            // While there is more in the file to read
            while (line != null) {
                ret.add(line);
                numberOfWords++;
                line = reader.readLine();
            }
        } catch (IOException e) {
            log.log(Level.SEVERE, "IOException occurred, message = " + e.getLocalizedMessage(), e);
        }

        log.info("Read " + numberOfWords + " words in " + Long.toString(System.currentTimeMillis() - startTime) + "ms");

        return ret;

    }

    public int saveWordsBufferedStream(String filename, List<String> words) {
        var startTime = System.currentTimeMillis();

        // Return value: number of words written
        var wordCount = 0;

        var file = new File(filename);
        try (var writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
            for (var word : words) {
                if (wordCount > 0) {
                    writer.append(' ');
                }
                writer.write(word);
                wordCount++;
            }
        } catch (IOException e) {
            log.log(Level.SEVERE, "IOException occurred, message = " + e.getLocalizedMessage(), e);
        }

        log.info("Wrote " + wordCount + " words in " + Long.toString(System.currentTimeMillis() - startTime) + "ms");

        return wordCount;
    }

    public Map<Character, Map<String, Long>> computeAlphabeticalWordMap(final List<String> words) {
        return words.stream().collect(Collectors.groupingBy(word -> Character.valueOf(Character.toUpperCase(word.charAt(0))),
                Collectors.groupingBy(Function.identity(), Collectors.counting())));
    }

}
