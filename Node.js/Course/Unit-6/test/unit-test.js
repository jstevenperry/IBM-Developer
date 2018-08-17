/*
   Copyright 2018 Makoto Consulting Group, Inc.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
'use strict'

/**
 * Unit tests for test utilities.
 * 
 * To execute the test cases, run: 
 * 
 * npm run test-utils 
 * 
 * From the root directory
 */

// Node assert module
const assert = require('assert');

// Node url module
const url = require('url');

const utils = require('../utils/utils');
const assertEqual = utils.assertEqual;
const logger = require('../utils/logger');

// Fixtures for Chunk Transformer Testing
let chunk = '9077|No Jet Lag|1|||\n9078|Citra Drain|1|||\n9079|Slow-mag|1|Purdue Products L.P.||\n9080|No Fe';
let chunk2 = 'ar|1|South Beach Beverage Co., LLC|Purchase, NY 10577|www.nofear.com\n9081|Golden Class';
let chunk3 = 'ic|1|||\n9082|Golden Crisp|1|Post Foods, LLC|1 Upper Pond Road Parsippany, NJ 07054 USA|postfoods.com\n9083|Noni Paci';

let cornerCaseChunk = '9077|No Jet Lag|1|||\n9078|Citra Drain|1|||\n';
let cornerCaseChunk2 = '9079|Slow-mag|1|Purdue Products L.P.||\n9080|No Fear|1|';
let cornerCaseChunk3 = 'South Beach Beverage Co., LLC|Purchase, NY 10577|www.nofear.com\n9081|Golden Classic';

function testChunkTransformerOptimistic() {
    let transformedData = utils.transformChunkIntoLines(chunk);
    assert.equal(transformedData.fieldsArray.length, 3);
    assert.deepEqual(transformedData.fieldsArray[0], ['9077','No Jet Lag','1','','','']);
    assert.deepEqual(transformedData.fieldsArray[1], ['9078','Citra Drain','1','','','']);
    assert.deepEqual(transformedData.fieldsArray[2], ['9079','Slow-mag','1','Purdue Products L.P.','','']);
    assert.deepEqual(transformedData.leftOvers, '9080|No Fe');
    transformedData = utils.transformChunkIntoLines(transformedData.leftOvers + chunk2);
    assert.equal(transformedData.fieldsArray.length, 1);
    assert.deepEqual(transformedData.fieldsArray[0], ['9080','No Fear','1','South Beach Beverage Co., LLC','Purchase, NY 10577','www.nofear.com']);
    assert.deepEqual(transformedData.leftOvers, '9081|Golden Class');
    transformedData = utils.transformChunkIntoLines(transformedData.leftOvers + chunk3);
    assert.equal(transformedData.fieldsArray.length, 2);
    assert.deepEqual(transformedData.fieldsArray[0], ['9081','Golden Classic','1','','','']);
    assert.deepEqual(transformedData.fieldsArray[1], ['9082','Golden Crisp','1','Post Foods, LLC','1 Upper Pond Road Parsippany, NJ 07054 USA','postfoods.com']);
    assert.deepEqual(transformedData.leftOvers, '9083|Noni Paci');

    logger.info('Test passed', 'testChunkTransformerOptimistic()');
}

/**
 * If the chunk just so happens to occur on an even record
 * boundary, we need to make sure the next record does not
 * get dropped.
 */
function testChunkTransformerCornerCase() {
    let transformedData = utils.transformChunkIntoLines(cornerCaseChunk);
    assert.equal(transformedData.fieldsArray.length, 2);
    assert.deepEqual(transformedData.fieldsArray[0], ['9077','No Jet Lag','1','','','']);
    assert.deepEqual(transformedData.fieldsArray[1], ['9078','Citra Drain','1','','','']);
    assert.deepEqual(transformedData.leftOvers, '');
    transformedData = utils.transformChunkIntoLines(transformedData.leftOvers + cornerCaseChunk2);
    assert.equal(transformedData.fieldsArray.length, 1);
    assert.deepEqual(transformedData.fieldsArray[0], ['9079','Slow-mag','1','Purdue Products L.P.','','']);
    assert.deepEqual(transformedData.leftOvers, '9080|No Fear|1|');
    transformedData = utils.transformChunkIntoLines(transformedData.leftOvers + cornerCaseChunk3);
    assert.equal(transformedData.fieldsArray.length, 1);
    assert.deepEqual(transformedData.fieldsArray[0], ['9080','No Fear','1','South Beach Beverage Co., LLC','Purchase, NY 10577','www.nofear.com']);
    assert.deepEqual(transformedData.leftOvers, '9081|Golden Classic');

    logger.info('Test passed', 'testChunkTransformerCornerCase()');
}

/**
 * Test the parseUrl() function. Test it. Test it real good.
 */
function testUrlParserOptimistic() {
    let url0 = 'http://example.com';
    let url1 = 'http://example.com/foo';
    let url2 = 'http://example.com:8080/foo';
    let url3 = 'http://example.com:8080/foo/123';
    let url4 = 'http://example.com:8080/foo/123/bar';
    let url5 = 'http://example.com:8080/foo/123/bar/567';
    let url6 = 'http://example.com:8080/foo/123/bar/567/baz';

    testUrlComponents(utils.parseUrl(url0), 'http:', 'example.com', null, []);
    testUrlComponents(utils.parseUrl(url1), 'http:', 'example.com', null, ['foo']);
    testUrlComponents(utils.parseUrl(url2), 'http:', 'example.com', '8080', ['foo']);
    testUrlComponents(utils.parseUrl(url3), 'http:', 'example.com', '8080', ['foo', '123']);
    testUrlComponents(utils.parseUrl(url4), 'http:', 'example.com', '8080', ['foo', '123', 'bar']);
    testUrlComponents(utils.parseUrl(url5), 'http:', 'example.com', '8080', ['foo', '123', 'bar', '567']);
    testUrlComponents(utils.parseUrl(url6), 'http:', 'example.com', '8080', ['foo', '123', 'bar', '567', 'baz']);

    logger.info('Test passed', 'testUrlParserOptimistic()');
}

/**
 * Tests the individual components of the parsed URL, makes it
 * easier to write (and understand) the level of test just above this one
 */
function testUrlComponents(parsedUrl, protocol, hostname, port, pathComponents) {
    assertEqual(parsedUrl.protocol, protocol);
    assertEqual(parsedUrl.hostname, hostname);
    assertEqual(parsedUrl.port, port);
    for (let aa = 0; aa < parsedUrl.pathComponents.length; aa++) {
        assertEqual(parsedUrl.pathComponents[aa], pathComponents[aa]);
    }
}

/**
 * Mainline - drives this test
 */
(function mainline() {
    // Test the chunk transformer logic
    testChunkTransformerOptimistic();
    testChunkTransformerCornerCase();

    // Test the URL parser logic
    testUrlParserOptimistic();
})();