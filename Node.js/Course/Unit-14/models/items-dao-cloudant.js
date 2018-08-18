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

/**
 * This is the DAO interface for the application.
 * You will need to provide an implementation for each
 * function in the interface. The implementation has been
 * provided for you in the appropriately named *sqlite3.js
 * file located in this directory.
 */

// Local utils
const utils = require('../utils/utils');

// Local logger
const logger = require('../utils/logger');
// logger.setLogLevel(logger.Level.DEBUG);

// Cloudant DB reference
let db;

// Initialize the DB when this module is loaded
(function getDbConnection() {
    logger.info('Initializing Cloudant connection...', 'items-dao-cloudant.getDbConnection()');
    utils.dbCloudantConnect().then((database) => {
        logger.info('Cloudant connection initialized.', 'items-dao-cloudant.getDbConnection()');
        db = database;
    }).catch((err) => {
        logger.error('Error while initializing DB: ' + err.message, 'items-dao-cloudant.getDbConnection()');
        throw err;
    });
})();

/**
 * Find the Item object by the specified ID
 * using the underlying implementation.
 * 
 * @param {Number} id - the ID of the item record (SQL) or document (NoSQL)
 * to locate
 * 
 * @return {Promise} Promise - 
 *  resolve(): the Item object that matches the id
 *          or null if one could not be located for that id 
 *  reject(): the err object from the underlying data store
 */
function findById(id) {
    return new Promise((resolve, reject) => {
        db.get(id, (err, document) => {
            if (err) {
                if (err.message == 'missing') {
                    logger.warn(`Document id ${id} does not exist.`, 'findById()');
                    resolve({ data: {}, statusCode: 404 });
                } else {
                    logger.error('Error fetching document: ' + err.message, 'findById()');
                    reject(err);
                }
            } else {
                let data = JSON.stringify(document);
                logger.debug('Received response document: ' + data, 'findById()');
                resolve({ data: data, statusCode: 200});
            }
        });
    });
}

/**
 * Find all Items objects that match the specified
 * partial description.
 * 
 * @param {String} partialDescription
 * 
 * @return {Promise} Promise - 
 *  resolve(): all Item objects that contain the partial
 *          description provided or an empty array if nothing
 *          could not be located for that partialDescription 
 *  reject(): the err object from the underlying data store
 */
function findByDescription(partialDescription) {
    return new Promise((resolve, reject) => {
        let search = `.*${partialDescription}.*`;
        db.find({ 
            'selector': { 
                'itemDescription': {
                    '$regex': search 
                }
            } 
        }, (err, documents) => {
            if (err) {
                reject(err);
            } else {
                resolve({ data: JSON.stringify(documents.docs), statusCode: (documents.docs.length > 0) ? 200 : 404 });
            }
        });
    });
}

/**
 * Find the Item object that matches the specified
 * UPC exactly.
 * 
 * @param {String} upc - the UPC of the item record (SQL) or document (NoSQL)
 * to locate
 * 
 * @return {Promise} Promise - 
 *  resolve(): the Item object that matches the UPC symbol
 *          or null if one could not be located for that UPC
 *  reject()): the err object from the underlying data store.
 */
function findByUpc(upc) {
    return new Promise((resolve, reject) => {
        db.find({ 
            'selector': { 
                'upc': {
                    '$eq': upc 
                }
            }
        }, (err, document) => {
            if (err) {
                logger.error('Error occurred: ' + err.message, 'findById()');
                reject(err);
            } else {
                resolve({ data: JSON.stringify(document), statusCode: (document) ? 200 : 404 });
            }
        });
    });
}

module.exports.findById = findById;
module.exports.findByDescription = findByDescription;
module.exports.findByUpc = findByUpc;
