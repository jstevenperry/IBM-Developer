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
 * Fetch all shopping lists
 * 
 * @return {Promise}
 */
function fetchAll() {
    return new Promise((resolve, reject) => {
        const sql = 'SELECT * FROM shopping_list';
        db.all(sql, (err, row) => {
            if (err) {
                reject(err);
            } else {
                resolve({ data: JSON.stringify(row), statusCode: 200 });
            }
        });
    });
}

/**
 * Create a shopping list with the specified description
 * 
 * @param {String} description - the description to use
 * 
 * @return {Promise}
 */
function create(description) {
    return new Promise((resolve, reject) => {
        const sql = `INSERT INTO shopping_list(description) VALUES(?)`;
        // Run the SQL (note: must use named callback to get properties of the resulting Statement)
        db.run(sql, description, function callback(err) {
            if (err) {
                reject(err);
            } else {
                resolve({ data: `{ "createdId" : ${this.lastID} }`, statusCode: 201 });// eslint-disable-line no-invalid-this, max-len
            }
        });
    });
}

/**
 * Find the shopping list for the specified id
 * 
 * @param {number} id - the list id of the record to fetch
 * 
 * @return {Promise}
 */
function findById(id) {
    return new Promise((resolve, reject) => {
        const sql = `SELECT * FROM shopping_list WHERE id = ?`;
        db.get(sql, id, (err, row) => {
            if (err) {
                let message = `Error reading from the database: ${err.message}`;
                logger.error(message, 'findById()');
                reject(message);
            } else if (row) {
                resolve({ data: JSON.stringify(row), statusCode: 200 });
            } else {
                resolve({ data: '{}', statusCode: 404 });
            }
        });
    });
}

/**
 * Find the shopping list with the specified id,
 * return all items that match
 * 
 * @param {number} id - the list id of the record to fetch
 * 
 * @return {Promise}
 */
function findByIdWithAllItems(id) {
    return new Promise((resolve, reject) => {
        const sql = `
        SELECT  shopping_list.id as shopping_list_id,
                shopping_list.description as shopping_list_description,
                shopping_list.when_created as shopping_list_when_created,
                shopping_list.when_modified,
                item.id as item_id,
                item.upc,
                item.description as item_description,
                brand.id as brand_id,
                brand.description as brand_description,
                brand.manufacturer,
                brand.location,
                brand.website,
                shopping_list_item.quantity,
                shopping_list_item.picked_up
        FROM shopping_list
            JOIN shopping_list_item ON shopping_list.id = shopping_list_item.shopping_list_id
            JOIN item ON item.id = shopping_list_item.item_id 
            JOIN brand ON brand.id = item.brand_id
        WHERE shopping_list.id = ?`;
        db.all(sql, id, (err, row) => {
            if (err) {
                let message = `Error reading from the database: ${err.message}`;
                logger.error(message, 'findByIdWithAllItems()');
                reject(message);
            } else if (row) {
                resolve({ data: JSON.stringify(row), statusCode: 200 });
            } else {
                resolve({ data: '{}', statusCode: 404 });
            }
        });
    });
}

/**
 * Update the shopping list with the specified id
 * with new field values
 * 
 * @param {number} id - the list id of the record to fetch
 * @param {String} description - the updated description
 * 
 * @return {Promise}
 */
function update(id, description) {
    return new Promise((resolve, reject) => {
        const sql = `UPDATE shopping_list SET description = ?, when_modified = datetime('now') WHERE id = ?`;
        // Run the SQL (note: must use named callback to get properties of the resulting Statement)
        db.run(sql, description, id, function callback(err) {
            if (err) {
                reject(err);
            } else {
                resolve({ data: `{ "rowsAffected" : ${this.changes} }`, statusCode: 200 });// eslint-disable-line no-invalid-this, max-len
            }
        });
    });
}

/**
 * Add the specified item to the specified shopping
 * list, along with values for the relationship
 * 
 * @param {number} listId - the id of the shopping list record
 * to which the item is to be added
 * @param {itemId} itemId - the id of the item record to be added
 * @param {number} quantity - the quantity 
 * 
 * @return {Promise} - promise that will be resolved (or rejected)
 * when the call to the DB completes
 */
function addItem(listId, itemId, quantity) {
    return new Promise((resolve, reject) => {
        if (typeof quantity === 'undefined') {
            quantity = 1;
        }
        const sql = 'INSERT INTO shopping_list_item (item_id, shopping_list_id, quantity) VALUES(?, ?, ?)';
        // Run the SQL (note: must use named callback to get properties of the resulting Statement)
        db.run(sql, itemId, listId, quantity, function callback(err) {
            if (err) {
                reject(err);
            } else {
                resolve({ data: `{ "createdId" : ${this.lastID} }`, statusCode: 201 });// eslint-disable-line no-invalid-this, max-len
            }
        });
    });
}

/**
 * Update the specified item in the specified shopping
 * list, along with values for the relationship
 * 
 * @param {number} listId - the id of the shopping list record
 * to which the item is to be updated
 * @param {itemId} itemId - the id of the item record to be updated
 * @param {number} quantity - the quantity 
 * @param {number} pickedUp - if 1 the item has been picked up,
 * otherwise 0 (only 0 and 1 are allowed)
 * 
 * @return {Promise} - promise that will be resolved (or rejected)
 * when the call to the DB completes
 */
function updateItem(listId, itemId, quantity, pickedUp) {
    return new Promise((resolve, reject) => {
        if (typeof quantity === 'undefined') {
            quantity = 1;
        }
        if (typeof pickedUp === 'undefined') {
            pickedUp = 0;
        }
        const sql = 'UPDATE shopping_list_item SET quantity = ?, picked_up = ? WHERE shopping_list_id = ? AND item_id = ?';// eslint-disable-line max-len
        db.run(sql, quantity, pickedUp, listId, itemId, function callback(err) {
            if (err) {
                reject(err);
            } else {
                resolve({ data: `{ "rowsAffected" : ${this.changes} }`, statusCode: 200 });// eslint-disable-line no-invalid-this, max-len
            }
        });
    });
}

/**
 * Remove the specified item from the specified shopping
 * list
 * 
 * @param {number} listId - the id of the shopping list record
 * to which the item is to be removed
 * @param {itemId} itemId - the id of the item record to be removed
 * 
 * @return {Promise} - promise that will be resolved (or rejected)
 * when the call to the DB completes
 */
function removeItem(listId, itemId) {
    return new Promise((resolve, reject) => {
        const sql = 'DELETE FROM shopping_list_item WHERE shopping_list_id = ? AND item_id = ?';
        db.run(sql, listId, itemId, function callback(err) {
            if (err) {
                reject(err);
            } else {
                resolve({ data: `{ "rowsAffected" : ${this.changes} }`, statusCode: 200 });// eslint-disable-line no-invalid-this, max-len
            }
        });
    });
}

module.exports.fetchAll = fetchAll;
module.exports.create = create;
module.exports.findById = findById;
module.exports.findByIdWithAllItems = findByIdWithAllItems;
module.exports.update = update;
module.exports.addItem = addItem;
module.exports.updateItem = updateItem;
module.exports.removeItem = removeItem;
