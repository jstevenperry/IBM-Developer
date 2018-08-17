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

// MongoDB
const mongodb = require('mongodb');

// Logger
const logger = require('./logger');
//logger.setLogLevel(logger.Level.DEBUG);

// Application settings
const appSettings = require('../config/app-settings');

// Common DB load module
const { loadData } = require('./load-db');

// Local Utils
const utils = require('./utils');

// MongoDB reference
let db;

// brands collection
let brands;

// items collection
let items;

/**
 * Handles brand document: inserts a single document into the collection
 * 
 * @param {Array} fields - an array containing the field values
 */
function handleBrandDocument(fields) {    
    // Generate an ObjectID
    let brandId = new mongodb.ObjectId();
    // Create the document
    let brand = {
        _id: brandId,
        id: brandId, // for convenience and backward-compatibility
        description: fields[1], // Brand description
        manufacturer: (fields[3]) ? fields[3] : null, // Manufacturer (optional)
        address: (fields[4]) ? fields[4] : null, // Address (optional)
        website: (fields[5]) ? fields[5] : null// Website (optional)
    };
    // Insert the record
    brands.insertOne(brand, (err, result) => {
        if (err) {
            logger.error('Error occurred while creating brand record: ' + JSON.stringify(brand), 'handleBrandDocument()');// eslint-disable-line max-len
            throw err;
        }
        if (result.insertedCount != 1) {
            let errorMessage = 'Expected to insert one record, instead inserted ' + result.insertedCount + 
            ' with no error object. Cannot continue!';
            logger.error(errorMessage, 'handleBrandDocument()');
            throw new Error(errorMessage);
        }
    });
}

/**
 * Handles item document: inserts a single document into the collection
 * 
 * @param {Array} fields - an array containing the field values
 */
function handleItemDocument(fields) {
    // Fetch the brandId by description (should be unique)
    let brandDescription = fields[3];
    //let brands = db.collection('brands');
    let brand = { description: brandDescription };
    brands.find(brand).toArray(function(err, brandDocs) {
        if (err) {
            logger.error('Error occurred while finding brand record: ' + JSON.stringify(brand), 'handleItemDocument()');// eslint-disable-line max-len
            throw err;
        }
        if (brandDocs.length == 1) {
            let brandId = brandDocs[0]._id;
            // Create the document
            let itemId = new mongodb.ObjectID();
            let item = {
                _id: itemId,
                id: itemId, // for convenience and backward-compatibility
                upc: fields[2], // UPC    
                itemDescription: fields[4], // Item description
                brandId: brandId
            };
            // Insert the record
            items.insertOne(item, (err, result) => {
                if (err) {
                    logger.error('Error occurred while creating item record: ' + JSON.stringify(item), 'handleItemDocument()');// eslint-disable-line max-len
                    throw err;
                }
                if (result.insertedCount != 1) {
                    let errorMessage = 'Expected to insert one record, instead inserted ' + result.insertedCount + 
                    ' with no error object. Cannot continue!';
                    logger.error(errorMessage, 'handleBrandDocument()');
                    throw new Error(errorMessage);
                }
            });
        } else {
            let errorMessage = 'Expected one document from brands query using description: ' + brandDescription +
                ' but instead got back ' + brandDocs.length + ' documents. Cannot continue!';
            logger.error(errorMessage, 'handleItemDocument()');
            throw new Error(errorMessage);
        }    
    });
}

/**
 * Initializes the DB - removes the items and brands collections. Allows for
 * a clean slate reload of the DB.
 */
async function initializeDb() {
    logger.info('Dropping lists collection', 'initializeDb()');
    await db.dropCollection('shoppingLists').then(() => {
        logger.info('Successfully dropped shoppingLists collection', 'initializeDb()');
    }).catch((err) => {
        // Most likely this is "ns not found" which means the collection doesn't exist
        logger.warn('Error while initializing database: ' + err.message, 'initializeDb()');
    });
    logger.info('Dropping items collection', 'initializeDb()');
    await db.dropCollection('items').then(() => {
        logger.info('Successfully dropped items collection', 'initializeDb()');
    }).catch((err) => {
        // Most likely this is "ns not found" which means the collection doesn't exist
        logger.warn('Error while initializing database: ' + err.message, 'initializeDb()');
    });
    logger.info('Dropping brands collection', 'initializeDb()');
    await db.dropCollection('brands').then(() => {
        logger.info('Successfully dropped brands collection', 'initializeDb()');
    }).catch((err) => {
        // Most likely this is "ns not found" which means the collection doesn't exist
        logger.warn('Error while initializing database: ' + err.message, 'initializeDb()');
    });
}

// Get or create the DB
utils.dbConnect().then((database) => {
    db = database;
    initializeDb().then(() => {
        logger.info('Initializing MongoDB... Done.', 'mainline()');
        brands = db.collection('brands');
        items = db.collection('items');
        //items.createIndex({ itemDescription: "text" });
        logger.info('Script start at: ' + new Date().toLocaleString(), 'mainline()');
        logger.info('Loading data for brand...', 'mainline');
        loadData(appSettings.brand_file_name, handleBrandDocument).then(() => {
            logger.info('Loading brand data, done.', 'mainline()');
            logger.info('Loading data for item...', 'mainline()');
            loadData(appSettings.item_file_name, handleItemDocument).then(() => {
                logger.info('Loading item data, done.', 'mainline()');
                logger.info('Script finished at: '+ new Date().toLocaleString(), 'mainline()');
                // Wait a few seconds to let MongoDB finish.
                setTimeout(() => {
                    // Close the DB or the Node process will hang
                    logger.info('Closing database connection...');
                    utils.dbClose();
                    logger.info('Database connection closed.');
                }, 20000);
            });
        }).catch((err) => {
            logger.error('Better luck next time: ' + err.message, 'mainline()()');
        });
    });
});
