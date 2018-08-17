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
'use strict';

// Node http
const http = require('http');
// For tesing
const assert = require('assert');
// For MongoDB stuff
const mongodb = require('mongodb');
// Logger
const logger = require('../utils/logger');
//logger.setLogLevel(logger.Level.DEBUG);

// Application settings
const appSettings = require('../config/app-settings');
/**
 * Write the response from the server.
 * 
 * @param {IncomingMessage} response - the response object from the HTTP request callback
 * @param {ServerResponse} responseMessage - the message to write as a simple string
 * @param {number} statusCode - the HTTP status code for the response
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
 * @param {IncomingMessage} response - the response object from the HTTP request callback
 * @param {ServerResponse} responseJson - the message to write as a simple string
 * @param {number} statusCode - the HTTP status code for the response
 */
function writeServerJsonResponse(response, responseJson, statusCode) {
    logger.debug(JSON.stringify(responseJson), `writeServerJsonResponse(${statusCode})`);
    response.setHeader('Content-Type', 'application/json');
    response.status(statusCode).send(responseJson);
}

/**
 * Processes a chunk of data using the specified handler for
 * each row, which is specific to a particular table.
 * 
 * @param {String} chunk - the chunk of data to be transformed
 * 
 * @return {array} Array of field arrays. Each element in the returned
 * array is an array of fields that represent the data for a
 * single row to be written.
 */
function transformChunkIntoLines(chunk) {
    let ret = { fieldsArray: [], leftOvers: ''};
    // Split up the chunk into lines
    let lines = chunk.split(/\r?\n/);
    // Process all lines
    for (let aa = 0; aa < lines.length-1; aa++) {
        // Split up the line based on delimiter (| symbol, which NEVER occurs in the input data, by design)
        let fields = lines[aa].split('|');
        ret.fieldsArray.push(fields);
    }
    ret.leftOvers = lines[lines.length-1];
    // Return the array of field arrays
    return ret;
}

/**
 * A better assert.equal() - if the expected value does
 * not match the actual value, a meaningful message is
 * returned.
 * 
 * @param {Object} actual - the actual value
 * @param {Object} expected - the expected value
 */
function assertEqual(actual, expected) {
    assert.equal(actual, expected, `Assert failed: actual => ${actual}, expected => ${expected}`);
}

/**
 * The DB connection variable
 */
let db;
let mongodbClient;

/**
 * Initializes the MongoDB.
 * 
 * @return {Promise}
 */
function dbConnect() {
    return new Promise((resolve, reject) => {
        logger.debug('Connecting to MongoDB database: ' + appSettings.mongodb_dbpath, 'utils.dbConnect()');
        if (db) {
            logger.debug('MongoDB already connected, returning open connection.', 'utils.dbConnect()');
            resolve(db);
        } else {
            logger.debug('MongoDB not connected. Creating new MongoDB connection.', 'utils.dbConnect()');
            mongodb.MongoClient.connect(appSettings.mongodb_url, { useNewUrlParser: true }, function(err, client) {
                if (err) {
                    logger.error('Error connecting to the MongoDB URL: ' + appSettings.mongodb_url);
                    reject(err);
                }
                logger.debug('MongoDB connected.', 'utils.dbConnect()');
                mongodbClient = client;
                db = mongodbClient.db(appSettings.mongodb_db_name);
                // Make sure connection closes when Node exits
                process.on('exit', (code) => {
                    logger.debug(`Closing MongoDB connection (node exit code ${code})...`, 'dbConnect()');
                    dbClose();
                    logger.debug(`MongoDB connection closed.`, 'dbConnect()');
                });
                resolve(db);
            });    
        }
    });
}

/**
 * Closes the MongoDB client connection
 */
function dbClose() {
    if (mongodbClient && mongodbClient.isConnected()) {
        logger.debug('Closing MongoDB connection...');
        mongodbClient.close();
        logger.debug('MongoDB connection closed.');
    }
}

/**
 * Helper function:
 * Make a call to the specified requestPath, and when the
 * results are done, invoke the callback.
 * 
 * @param {String} requestMethod - the HTTP method (GET, POST, etc)
 * @param {String} requestPath - the request path (e.g., /lists, /items, etc)
 * @param {String} postData - a JSON string (must be well-formed) containing any
 * data that is to be sent in the request body
 * @param {Function} resultsCallback - function to be invoked once the results
 * have been received from the remote server
 */
function httpRequest(requestMethod, requestPath, postData, resultsCallback) {
    let options = '';
    if (requestMethod == 'GET') {
        options = `http://${appSettings.server_host}:${appSettings.server_listen_port}${requestPath}`;
        http.get(options, function requestCallback(response) {
            let data = '';
            response.on('data', (chunk) => {
                data += chunk;
            });
            response.on('end', () => {
                resultsCallback(null, data);
            });
            response.on('error', (err) => {
                resultsCallback(err, null);
            });
        });
    } else {
        // All others
        options = {
            hostname: appSettings.server_host,
            port: appSettings.server_listen_port,
            path: encodeURI(requestPath),
            method: requestMethod,
            headers: {
                'Content-Type': 'application/json',
                'Content-Length': Buffer.byteLength(postData)
              }
        };
        let req = http.request(options, function requestCallback(response) {
            let data = '';
            response.on('data', (chunk) => {
                data += chunk;
            });
            response.on('end', () => {
                resultsCallback(null, data);
            });
            response.on('error', (err) => {
                resultsCallback(err, null);
            });
        });
        req.write(postData);
        req.end();
    }
}


// What's exported
module.exports.writeServerResponse = writeServerResponse;
module.exports.writeServerJsonResponse = writeServerJsonResponse;
module.exports.transformChunkIntoLines = transformChunkIntoLines;
module.exports.dbConnect = dbConnect;
module.exports.dbClose = dbClose;
module.exports.assertEqual = assertEqual;
module.exports.httpRequest = httpRequest;
