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

// For reading/writing Sqlite DB
const database = require('sqlite3').verbose();

// Logger
const logger = require('../utils/logger');
//logger.setLogLevel(logger.Level.DEBUG);

// Simple utils
const utils = require('../utils/utils');

const appSettings = require('../config/app-settings');

/**
 * Loads the specified file name and returns its contents
 * in the resolved promise. If an error occurs, the Promise
 * is rejected with that err object.
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
 * Creates all of the DB fixtures.
 */
function createDbFixtures(db) {
    return new Promise((resolve, reject) => {
        return new Promise((resolve, reject) => {
            logger.info('Dropping all tables...', 'createDbFixtures()');
            db.run('DROP TABLE IF EXISTS shopping_list_item');
            db.run('DROP TABLE IF EXISTS shopping_list');
            db.run('DROP TABLE IF EXISTS item');
            db.run('DROP TABLE IF EXISTS brand');
            logger.info('Dropping all tables, done.', 'createDbFixtures()');
            resolve();
        }).then(() => {
            return loadFile(appSettings.create_sql.item);
        }).then((itemSql) => {
            logger.info('Creating item table...', 'createDbFixtures()');
            db.run(itemSql);
            logger.info('Creating item table, done.', 'createDbFixtures()')
            return loadFile(appSettings.create_sql.brand);
        }).then((brandSql) => {
            logger.info('Creating brand table...', 'createDbFixtures()');
            db.run(brandSql);
            logger.info('Creating brand table, done.', 'createDbFixtures()');
            return loadFile(appSettings.create_sql.shopping_list);
        }).then((shoppingListSql) => {
            logger.info('Creating shopping_list table...', 'createDbFixtures()');
            db.run(shoppingListSql);
            logger.info('Creating shopping_list table, done.', 'createDbFixtures()');
            return loadFile(appSettings.create_sql.shopping_list_item);
        }).then((shoppingListItemSql) => {
            logger.info('Creating shopping_list_item table...', 'createDbFixtures()');
            db.run(shoppingListItemSql);
            logger.info('Creating shopping_list_item table, done.', 'createDbFixtures()');
            return Promise.resolve();
        }).catch((err) => {
            logger.error('Something has gone horribly wrong: ' + err.message);
        }).then(() => {
            logger.info('DONE', 'createDbFixtures()');
            resolve();
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
var chunkCache = '';

/**
 * Loads the data from the Grocery database CSV files
 */
function loadData(db, fileName, handleTableRow) {
    return new Promise((resolve, reject) => {
        logger.info('Loading data files...', 'loadData()');
        // Read the brand data
        const readableStream = fs.createReadStream(fileName, { highWaterMark : 64*1024 });
        readableStream.on('open', (fd) => {
            logger.info('Opened file: ' + fileName, 'loadData():readableStream.on(open)');
        }).on('data', (chunk) => {
            logger.debug('Got chunk of data (size): ' + chunk.length, 'loadData():readableStream.on(data)');
            let actualChunk = chunkCache + chunk;
            logger.debug('Passing a chunk of size (includes leftovers): ' + actualChunk.length, 'loadData()');
            let lines = utils.transformChunkIntoLines(actualChunk);
            for (let aa = 0; aa < lines.fieldsArray.length; aa++) {
                handleTableRow(db, lines.fieldsArray[aa]);
            }
            chunkCache = lines.leftOvers;
            logger.debug('Leftovers: ' + chunkCache, 'loadData()');
        }).on('error', (err) => {
            logger.error('Error: ' + err.message, 'loadData():readableStream.on(error)');
            reject(err);
        }).on('close', () => {
            logger.info('Closed file: ' + fileName, 'loadData():readableStream.on(close)');
            resolve();
        });
    });
}

/**
 * Handles brand table: inserts a single row into the table
 * using the specified DB module
 */
function handleBrandRowForSqlDb(db, fields) {
    // Brand description
    let description = fields[1];
    // Manufacturer (optional)
    let manufacturer = (fields[3]) ? fields[3] : null;
    // Address (optional)
    let address = (fields[4]) ? fields[4] : null;
    // Website (optional)
    let website = (fields[5]) ? fields[5] : null;
    // Insert the row
    db.run('INSERT INTO brand (description, manufacturer, location, website) VALUES (?, ?, ?, ?)', 
        description, manufacturer, address, website,
        (err) => {
            if (err) {
                logger.error('Error occurred while inserting record: description = ' + 
                    description + ', manufacturer = ' + manufacturer + ', address = ' + address + ', website = ' + website);
            }
        });
}

/**
 * Handles item table: inserts a single row into the table
 * using the specified DB module and the fields provided
 */
function handleItemRowForSqlDb(db, fields) {
    // UPC
    let upc = fields[2];
    // Brand description
    let brandDescription = fields[3];
    // Item description
    let itemDescription = fields[4];
    // Insert the row
    db.run('INSERT INTO item (upc, description, brand_id) VALUES (?, ?, (SELECT id FROM brand WHERE description = ?))', 
        upc, itemDescription, brandDescription, 
        (err) => {
            if (err) {
                logger.error('Error occurred while inserting this record: upc = ' + upc + ', brand = ' + brandDescription + ', item = ' + itemDescription, 'db.run()');
            }
        });
}

/**
 * This is.... the mainline!
 */
(function mainline() {
    logger.info('Script start at: ' + new Date().toLocaleString(), 'mainline()');
    // Get or create the DB
    logger.info('Creating database file: ' + appSettings.db_file_name);
    let db = new database.Database(appSettings.db_file_name);
    // Create db fixtures (e.g., tables, if applicable)
    let returnPromise = createDbFixtures(db);
    returnPromise.then(() => {
        logger.info('Loading data for brand...', 'mainline:createDbFixtures(resolved Promise)');
        loadData(db, appSettings.brand_file_name, handleBrandRowForSqlDb).then(() => {
            logger.info('Loading brand data, done.', 'mainline:createDbFixtures(resolved Promise)');
            logger.info('Loading data for item...', 'mainline:createDbFixtures(resolved Promise)');
            loadData(db, appSettings.item_file_name, handleItemRowForSqlDb).then(() => {
                logger.info('Loading item data, done.', 'mainline:createDbFixtures(resolved Promise)');
                logger.info('Script finished at: '+ new Date().toLocaleString(), 'mainline:createDbFixtures(resolvedPromise)');
            });
        });
    }).catch((err) => {
        logger.error('Better luck next time: ' + err.message, 'mainline():createDbFixtures(rejected Promise)');
    });

    process.on('exit', (code) => {
        db.close((err) => {
            logger.error('Error closing DB: ' + err.message);
        });
    });
})();
