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
 * Sqlite3 implementation of the DAO interface for the
 * application. You should not need to make changes here.
 * If you find a bug, please open an issue.
 */

const logger = require('../utils/logger');

const db = require('../utils/utils').getDatabase();

/**
 * All of the SELECTs in this module look the same
 */
const SELECT = `SELECT item.id as item_id, 
    item.description as item_description, 
    item.upc, 
    item.when_created, 
    brand.id as brand_id, 
    brand.description as brand_description, 
    brand.manufacturer, 
    brand.location, 
    brand.website`;

/**
 * All of the FROMs in this module look the same
 */
const FROM = `FROM item JOIN brand ON item.brand_id = brand.id`;

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
        let sql = `${SELECT} ${FROM} WHERE item.id = ?`;
        db.get(sql, id, (err, row) => {
            if (err) {
                let message = `Error reading from the database: ${err.message}`;
                logger.error(message, 'findById()');
                reject(message);
            } else if (row) {
                resolve({ data: JSON.stringify(row), statusCode: 200 });
            } else {
                resolve({ data: JSON.stringify({}), statusCode: 404 });
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
 *          descriptin provided or an empty array if nothing
 *          could not be located for that partialDescription 
 *  reject(): the err object from the underlying data store
 */
function findByDescription(partialDescription) {
    return new Promise((resolve, reject) => {
        let sql = `${SELECT} ${FROM} WHERE item.description like ?`;
        db.all(sql, [`%${partialDescription}%`], (err, rows) => {
            if (err) {
                let message = `Error reading from the database: ${err.message}`;
                logger.error(message, 'findByDescription()');
                reject(message);
            } else {
                resolve({ data: JSON.stringify(rows), statusCode: (rows.length > 0) ? 200 : 404 });
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
        let sql = `${SELECT} ${FROM} WHERE item.upc = ?`;
        db.get(sql, upc, (err, row) => {
            if (err) {
                let message = `Error reading from the database: ${err.message}`;
                logger.error(message, 'findByUpc()');
                reject(message);
            } else if (row) {
                resolve({ data: JSON.stringify(row), statusCode: 200 });
            } else {
                resolve({ data: JSON.stringify({}), statusCode: 404 });
            }
        });
    });
}

module.exports.findById = findById;
module.exports.findByDescription = findByDescription;
module.exports.findByUpc = findByUpc;
