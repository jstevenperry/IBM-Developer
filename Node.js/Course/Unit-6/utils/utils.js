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

// For URL parsing
const url = require('url');
// For tesing
const assert = require('assert');
// For DB stuff
const sqlite3 = require('sqlite3');
// Logger
const logger = require('./logger');
// Application settings
const appSettings = require('../config/app-settings');
/**
 * Write the response from the server.
 * 
 * @param response - the response object from the HTTP request callback
 * 
 * @param responseMessage - the message to write as a simple string
 * 
 * @param statusCode - the HTTP status code for the response
 * 
 * @param logResponseMessage - if set to a truthy value logs the responseMessage, otherwise not
 */
function writeServerResponse(response, responseMessage, statusCode) {
    logger.debug(responseMessage, `writeServerResponse(${statusCode})`);
    response.statusCode = statusCode;
    response.write(responseMessage);
    response.end();
}

/**
 * Write the response from the server.
 * 
 * @param response - the response object from the HTTP request callback
 * 
 * @param responseJson - the message to write as a JSON string
 * 
 * @param statusCode - the HTTP status code for the response
 * 
 * @param logResponseMessage - if set to a truthy value logs the responseMessage, otherwise not
 */
function writeServerJsonResponse(response, responseJson, statusCode) {
    logger.debug(responseJson, `writeServerJsonResponse(${statusCode})`);
    response.statusCode = statusCode;
    response.setHeader('Content-Type', 'application/json');
    response.write(responseJson);
    response.end();
}

/**
 * Helper function to process request body
 * 
 * @param request - The request object from the HTTP request
 * 
 * @returns Promise, when resolved, passes request body. If rejected,
 * passes the Error object received from the on(error) function.
 */
function processRequestBody(request) {
    return new Promise((resolve, reject) => {
        let body = [];
        request.on('data', (chunk) => {
            body.push(chunk);
        }).on('end', () => {
            body = Buffer.concat(body).toString();
            logger.debug(`Request body processed. Length: ${body.length}.`, 'processRequestBody()');
            resolve(body);
        }).on('error', (err) => {
            logger.error(`Error occurred while processing request body, message: ${err.message}`, 'processRequestBody()');
            reject(err.message);
        });
    });
}

/**
 * Processes a chunk of data using the specified handler for
 * each row, which is specific to a particular table.
 * 
 * @param chunk - the chunk of data to be transformed
 * 
 * @returns Array of field arrays. Each element in the returned
 * array is an array of fields that represent the data for a
 * single row to be written.
 */
function transformChunkIntoLines(chunk) {
    let ret = { fieldsArray : [], leftOvers : ''};
    // Split up the chunk into lines
    let lines = chunk.split(/\r?\n/);
    // Process all lines
    for (let aa = 0; aa < lines.length-1; aa++) {
        // Split up the line based on delimiter (| symbol, which NEVER occurs in the input data, by design)
        var fields = lines[aa].split('|');
        ret.fieldsArray.push(fields);
    }
    ret.leftOvers = lines[lines.length-1];
    // Return the array of field arrays
    return ret;
}

/**
 * Parses the specified URL into its components pieces using
 * Node's url module.
 * 
 * Also parses any parts of the path into an array
 * called pathComponents where each element in the array
 * is another component of the path.
 * For example the path /a/b/c/d would result in a
 * pathComponents array of elements [ 'a', 'b', 'c', 'd']
 * 
 * @param urlString - the URL (as a String) to be parsed.
 * 
 * @returns parsedUrl - the parsed URL, an augmented object
 * from url.parse, with pathComponents as described above.
 */
function parseUrl(urlString) {
    let parsedUrl = url.parse(urlString, true, true);
    let splitPath = parsedUrl.pathname.split('/');
    let pathComponents = [];
    for (let aa = 0; aa < splitPath.length; aa++) {
        if (splitPath[aa] != '') {
            pathComponents.push(splitPath[aa]);
        }
    }
    parsedUrl.pathComponents = pathComponents;
    return parsedUrl;
}

/**
 * A better assert.equal()
 */
function assertEqual(actual, expected) {
    assert.equal(actual, expected, `Assert failed: actual => ${actual}, expected => ${expected}`);
}

/**
 * The DB connection variable
 */
var db;

function getDatabase() {
    if (typeof db === 'undefined') {
        db = db_init();
    }
    return db;
}
/**
 * Initializes the module:
 * - DB connection. An on(exit) handler is registered to close the DB connection
 * when Node terminates.
 */
function db_init() {
    let db = new sqlite3.Database(appSettings.db_file_name, (err) => {
        if (err) {
            logger.error(`Error occurred while opening database file: ${appSettings.db_file_name}: ${err.message}`, 'init()');
        } else {
            logger.info(`Database ${appSettings.db_file_name} is open for business!`);
            // Make sure to close this database connection when Node exits
            process.on('exit', (code) => {
                logger.info(`CLOSING Database ${appSettings.db_file_name}, exit code: ${code}`);
                db.close((err) => {
                    logger.error(`Error closing DB with message: ${err.message}: and code ${code}`);
                });
            });
        }
    });
    return db;
};

// What's exported
module.exports.writeServerResponse = writeServerResponse;
module.exports.writeServerJsonResponse = writeServerJsonResponse;
module.exports.processRequestBody = processRequestBody;
module.exports.transformChunkIntoLines = transformChunkIntoLines;
module.exports.parseUrl = parseUrl;
module.exports.getDatabase = getDatabase;
module.exports.assertEqual = assertEqual;