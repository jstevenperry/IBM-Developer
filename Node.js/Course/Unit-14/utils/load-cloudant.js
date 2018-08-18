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

// UUID generator
const uuidv4 = require('uuid/v4');

// Logger
const logger = require('./logger');
logger.setLogLevel(logger.Level.DEBUG);

// Application settings
const appSettings = require('../config/app-settings');

// Common DB load module
const { loadData } = require('./load-db');

// Local Utils
const utils = require('./utils');

// Cloudant DB reference
let db;

// Handle notification of max items reached? (Only want to do this once)
let handleMaxItemsNotification = true;

// Item document count
let itemDocumentCount;

// Number of items in the current batch
let batchDocumentCount = 0;

// The batch queue
let batchQueue = [];

/**
 * Handles item document: inserts a single document into the collection
 * 
 * @param {Array} fields - an array containing the field values
 */
function handleItemDocument(fields) {
    // Throttle because of IBM Cloud throughput limitations
    if (appSettings.cloudant_max_items > itemDocumentCount) {
        itemDocumentCount++;
        batchDocumentCount++;
        batchQueue.push(fields);
        if (batchDocumentCount == appSettings.cloudant_bulk_batch_size) {
            processBatch();
            // Reset state
            batchQueue = [];
            batchDocumentCount = 0;
        }
    } else if (handleMaxItemsNotification) {
        logger.warn('Max items count of ' + appSettings.cloudant_max_items +
            ' has been reached. No more items will be loaded.', 
            'handleItemDocument()');
        // Notification handled, does not need to be handled again, like, ever
        handleMaxItemsNotification = false;
    }
}

let processedDocumentCount = 1;
/**
 * Processes the current batch
 */
function processBatch() {
    let items = {
        docs: []
    };
    let endRange = processedDocumentCount + batchQueue.length - 1;
    logger.debug('Creating documents: ' + processedDocumentCount + 
        '-' + endRange);
    batchQueue.forEach((fields) => {
        let itemId = uuidv4(); // I like surrogate keys. And turtles.
        let item = {
            _id: itemId,
            id: itemId,
            type: 'item',
            upc: fields[2],
            itemDescription: fields[4],
            brand: fields[3], // Brand description is brand id
            items: []
        };
        items.docs.push(item);
        processedDocumentCount++;
    });
    // Insert the record
    db.bulk(items, (err, result) => {
        if (err) {
            logger.error('Error occurred while creating item record: ' + JSON.stringify(items), 'handleItemDocument()'); // eslint-disable-line max-len
            throw err;
        }
        if (result.ok) {
            logger.debug('Inserted document: ' + JSON.stringify(items) + ', result: ' + JSON.stringify(result));
            itemDocumentCount++;
        }
    });
    utils.eatCpu(3000);
}

/**
 * Initializes the DB - removes the items and brands collections. Allows for
 * a clean slate reload of the DB.
 */
async function initializeDb() {
    // NOTHING TO DO (YET)
}

// Get or create the DB
utils.dbCloudantConnect().then((database) => {
    db = database;
    initializeDb().then(() => {
        logger.info('Initializing Cloudant... Done.', 'mainline()');
        itemDocumentCount = 0;
        logger.info('Script start at: ' + new Date().toLocaleString(), 'mainline()');
        logger.info('Loading data for item...', 'mainline()');
        loadData(appSettings.item_file_name, handleItemDocument).then(() => {
            // Cleanup any remaining items (corner case)
            processBatch();
            // TODO: Add indexes here so they don't have to be created in the Cloud dashboard
            logger.info('Loading item data, done.', 'mainline()');
            logger.info('Total item documents loaded: ' + itemDocumentCount);
            logger.info('Script finished at: '+ new Date().toLocaleString(), 'mainline()');
        }).catch((err) => {
            logger.error('Better luck next time: ' + err.message, 'mainline()()');
        });
    });
});
