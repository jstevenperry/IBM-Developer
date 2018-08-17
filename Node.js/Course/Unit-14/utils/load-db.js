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

/**
 * Utility module used to load the database from the
 * CSV data downloaded from the
 * Open Grocery Database Project: http://www.grocery.com/open-grocery-database-project/
 * The data is free to download and use. From the link above:
 * "This project aims to... give everyone free and 
 * unrestricted access to simple downloadable database files containing UPC 
 * centric information about hundreds of thousands of grocery products."
 */

// For reading CSV files
const fs = require('fs');

// Logger
const logger = require('./logger');
//logger.setLogLevel(logger.Level.DEBUG);

// Simple utils
const utils = require('../utils/utils');

/**
 * Loads the specified file name and returns its contents
 * in the resolved promise. If an error occurs, the Promise
 * is rejected with that err object.
 * 
 * @param {String} filename - the name of the file to be read
 * @return {Promise}
 */
function loadFile(filename) {
    return new Promise((resolve, reject) => {
        fs.readFile(filename, 'utf8', (err, data) => {
            if (err) {
                reject(err);
            }
            resolve(data);
        });
    });
}

/**
 * The cache of unread data. Not all data can be processed
 * for a single chunk, which is most certainly going to cross
 * record boundaries, leaving us with an incomplete record
 * at the end of the chunk. So we cache that here, then add
 * it to the front of the next chunk. And so it goes.
 */
let chunkCache = '';

/**
 * Loads the data from the Grocery database CSV files
 * @param {String} fileName - the name of the SQL file containing
 * the load statements
 * @param {Function} handleTableRecord - the function to handle a single record
 * 
 * @return {Promise} 
 */
function loadData(fileName, handleTableRecord) {
    return new Promise((resolve, reject) => {
        logger.info('Loading data files...', 'loadData()');
        // Read the brand data
        const readableStream = fs.createReadStream(fileName, { highWaterMark: 64*1024 });
        readableStream.on('open', (fd) => {
            logger.info('Opened file: ' + fileName, 'loadData():readableStream.on(open)');
        }).on('data', (chunk) => {
            logger.trace('Got chunk of data (size): ' + chunk.length, 'loadData():readableStream.on(data)');
            let actualChunk = chunkCache + chunk;
            logger.trace('Passing a chunk of size (includes leftovers): ' + actualChunk.length, 'loadData()');
            let lines = utils.transformChunkIntoLines(actualChunk);
            for (let aa = 0; aa < lines.fieldsArray.length; aa++) {
                handleTableRecord(lines.fieldsArray[aa]);
            }
            chunkCache = lines.leftOvers;
            logger.trace('Leftovers: ' + chunkCache, 'loadData()');
        }).on('error', (err) => {
            logger.error('Error: ' + err.message, 'loadData():readableStream.on(error)');
            reject(err);
        }).on('close', () => {
            logger.info('Closed file: ' + fileName, 'loadData():readableStream.on(close)');
            resolve();
        });
    });
}

module.exports.loadFile = loadFile;
module.exports.loadData = loadData;
