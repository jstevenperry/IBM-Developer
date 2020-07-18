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
package com.jstevenperry.intro.regex;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpression {

    private static final Logger logger = Logger.getLogger(RegularExpression.class.getName());

    public void matches() {
        Pattern pattern = Pattern.compile("[Aa].*string");
        Matcher matcher = pattern.matcher("A string");
        boolean didMatch = matcher.matches();
        logger.info("Did match using matches() ==> " + didMatch);
        int patternStartIndex = matcher.start();
        logger.info("Pattern Start Index ==> " + patternStartIndex);
        int patternEndIndex = matcher.end();
        logger.info("Pattern End Index ==> " + patternEndIndex);
    }

    public void lookingAt() {
        Pattern pattern = Pattern.compile("[Aa].*string");
        Matcher matcher = pattern.matcher("A string with more than just the pattern");
        boolean didMatch = matcher.lookingAt();
        logger.info("Did match using lookingAt() ==> " + didMatch);
        int patternStartIndex = matcher.start();
        logger.info("Pattern Start Index ==> " + patternStartIndex);
        int patternEndIndex = matcher.end();
        logger.info("Pattern End Index ==> " + patternEndIndex);
    }

    public void wikiWord() {
        String input = "Here is a WikiWord followed by AnotherWikiWord, then SomeWikiWord.";
        Pattern pattern = Pattern.compile("[A-Z][a-z]*([A-Z][a-z]*)+");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            logger.info("Found this wiki word: " + matcher.group());
        }
    }

    public void replace() {
        String input = "Here is a WikiWord followed by AnotherWikiWord, then SomeWikiWord.";
        Pattern pattern = Pattern.compile("[A-Z][a-z]*([A-Z][a-z]*)+");
        Matcher matcher = pattern.matcher(input);
        logger.info("Before: " + input);
        String result = matcher.replaceAll("replacement");
        logger.info("After: " + result);
    }

    public void replaceFirst() {
        String input = "Here is a WikiWord followed by AnotherWikiWord, then SomeWikiWord.";
        Pattern pattern = Pattern.compile("[A-Z][a-z]*([A-Z][a-z]*)+");
        Matcher matcher = pattern.matcher(input);
        logger.info("Before: " + input);
        String result = matcher.replaceFirst("replacement");
        logger.info("After: " + result);
    }

    public void matchingGroups() {
        String input = "Here is a WikiWord followed by AnotherWikiWord, then SomeWikiWord.";
        Pattern pattern = Pattern.compile("[A-Z][a-z]*([A-Z][a-z]*)+");
        Matcher matcher = pattern.matcher(input);
        Logger.getAnonymousLogger().info("Before: " + input);
        String result = matcher.replaceAll("blah$0blah");
        Logger.getAnonymousLogger().info("After: " + result);
    }

    public void matchingGroupsWithStringBuffer() {
        String input = "Here is a WikiWord followed by AnotherWikiWord, then SomeWikiWord.";
        Pattern pattern = Pattern.compile("[A-Z][a-z]*([A-Z][a-z]*)+");
        Matcher matcher = pattern.matcher(input);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, "blah$0blah");
        }
        matcher.appendTail(buffer);
        logger.info("After: " + buffer.toString());
    }

}
