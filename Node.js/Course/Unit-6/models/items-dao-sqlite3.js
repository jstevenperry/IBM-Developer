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
 * Sqlite3 implementation of the DAO interface for the
 * application. You should not need to make changes here.
 * If you find a bug, please open an issue.
 */

const sqlite3 = require('sqlite3').verbose();
const logger = require('../utils/logger');

const appSettings = require('../config/app-settings');

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
 * Find by Id - sqlite3 implementation
 */
function findById(id) {
    logger.debug(`Reading item with id = ${id}`, 'findById()');
    return new Promise((resolve, reject) => {
        let sql = `${SELECT} ${FROM} WHERE item.id = ?`;
        db.get(sql, id, (err, row) => {
            if (err) {
                let message = `Error reading from the database: ${err.message}`;
                logger.error(message, 'findById()');
                reject(message);
            } else if (row) {
                resolve({ data : JSON.stringify(row), statusCode: 200 });
            } else {
                resolve({ data : JSON.stringify({}), statusCode: 404 });
            }
        });
    });
}

/**
 * Find all by partial description - sqlite3 implementation
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
                resolve({ data : JSON.stringify(rows), statusCode: (rows.length > 0) ? 200 : 404 });
            }
        });
    });
}

/**
 * Find by UPC - sqlite3 implementation
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
                resolve({ data : JSON.stringify(row), statusCode: 200 });
            } else {
                resolve({ data : JSON.stringify({}), statusCode: 404 });
            }
        });
    });
}

module.exports.findById = findById;
module.exports.findByDescription = findByDescription;
module.exports.findByUpc = findByUpc;
