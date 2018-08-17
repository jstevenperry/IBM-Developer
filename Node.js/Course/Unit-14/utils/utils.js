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
// For Cloudant
const Cloudant = require('@cloudant/cloudant');
// Logger
const logger = require('../utils/logger');
//logger.setLogLevel(logger.Level.DEBUG);

// Application settings
const appSettings = require('../config/app-settings');

// vcap-local.json
const vcap = require('../config/vcap-local.json');

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
 * Connects to the Cloudant DB
 * @return {Promise} - when resolved, contains the db, ready to go
 */
function dbCloudantConnect() {
    return new Promise((resolve, reject) => {
        if (db === undefined) {
            Cloudant({  // eslint-disable-line
//                url: appSettings.cloudant_url
                url: vcap.services.cloudantNoSQLDB.credentials.url
            }, ((err, cloudant) => {
                if (err) {
                    logger.error('Connect failure: ' + err.message + ' for Cloudant DB: ' +
                        appSettings.cloudant_db_name);
                    reject(err);
                } else {
                    db = cloudant.use(appSettings.cloudant_db_name);
                    logger.info('Connect success! Connected to DB: ' + appSettings.cloudant_db_name);
                    resolve(db);
                }
            }));
        } else {
            resolve(db);
        }
    });
}

/**
 * Initializes the MongoDB.
 * 
 * @return {Promise}
 */
function dbMongoConnect() {
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
                    dbMongoClose();
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
function dbMongoClose() {
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
        logger.debug('Making GET request: ' + options, 'httpRequest()');
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

/**
 * Cripple the beautiful elegance of Node's asynchronous processing model
 * to accomodate IBM Cloud throughput throttling.
 * 
 * @param {Number} millisToEat - the number of milliseconds to chew up
 */
function eatCpu(millisToEat) {
    let then = Date.now();
    let now = then;
    while (now < then + millisToEat) {
        now = Date.now();
    }
}

// What's exported
module.exports.writeServerResponse = writeServerResponse;
module.exports.writeServerJsonResponse = writeServerJsonResponse;
module.exports.transformChunkIntoLines = transformChunkIntoLines;
module.exports.dbMongoConnect = dbMongoConnect;
module.exports.dbMongoClose = dbMongoClose;
module.exports.dbCloudantConnect = dbCloudantConnect;
module.exports.assertEqual = assertEqual;
module.exports.httpRequest = httpRequest;
module.exports.eatCpu = eatCpu;
