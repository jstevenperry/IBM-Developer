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

// For reading/writing Sqlite DB
const database = require('sqlite3').verbose();

// Logger
const logger = require('./logger');
//logger.setLogLevel(logger.Level.DEBUG);

// Application settings
const appSettings = require('../config/app-settings');

// Common DB load module
const { loadFile, loadData } = require('./load-db');

/**
 * Creates all of the DB fixtures - this function is specific to a SQL DB.
 * 
 * @return {Promise}
 */
function createDbFixtures() {
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
            logger.info('Creating item table, done.', 'createDbFixtures()');
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
 * Handles brand table: inserts a single row into the table
 * using the specified DB module
 * 
 * @param {Array} fields - an array containing the field values
 */
function handleBrandRowForSqlDb(fields) {
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
                    description + ', manufacturer = ' + manufacturer + ', address = ' + 
                    address + ', website = ' + website);
            }
        });
}

/**
 * Handles item table: inserts a single row into the table
 * using the specified DB module and the fields provided
 * 
 * @param {Array} fields - an array containing the field values
 */
function handleItemRowForSqlDb(fields) {
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
                logger.error('Error occurred while inserting this record: upc = ' + upc + ', brand = ' + 
                brandDescription + ', item = ' + itemDescription, 'db.run()');
            }
        });
}

// Get or create the DB
logger.info('Creating database file: ' + appSettings.db_file_name);
let db = new database.Database(appSettings.db_file_name);

logger.info('Script start at: ' + new Date().toLocaleString(), 'mainline()');
// Create db fixtures (e.g., tables, if applicable)
let returnPromise = createDbFixtures();
returnPromise.then(() => {
    logger.info('Loading data for brand...', 'mainline:createDbFixtures(resolved Promise)');
    loadData(appSettings.brand_file_name, handleBrandRowForSqlDb).then(() => {
        logger.info('Loading brand data, done.', 'mainline:createDbFixtures(resolved Promise)');
        logger.info('Loading data for item...', 'mainline:createDbFixtures(resolved Promise)');
        loadData(appSettings.item_file_name, handleItemRowForSqlDb).then(() => {
            logger.info('Loading item data, done.', 'mainline:createDbFixtures(resolved Promise)');
            logger.info('Script finished at: '+ new Date().toLocaleString(), 
                'mainline:createDbFixtures(resolvedPromise)');
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
