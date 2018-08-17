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
 * This is the DAO interface for the application.
 * You will need to provide an implementation for each
 * function in the interface. The implementation has been
 * provided for you in the appropriately named *sqlite3.js
 * file located in this directory.
 */

// MongoDB
const mongodb = require('mongodb');

// Local utils
const utils = require('../utils/utils');

// Local logger
const logger = require('../utils/logger');
//logger.setLogLevel(logger.Level.DEBUG);

// MongoDB reference
let db;

// Get a DB connection when this module is loaded
(function getDbConnection() {
    logger.info('Initializing MongoDB connection...', 'lists-dao-mongogb.getDbConnection()');
    utils.dbConnect().then((database) => {
        logger.info('MongoDB connection initialized.', 'lists-dao-mongogb.getDbConnection()');
        db = database;
    }).catch((err) => {
        logger.error('Error while initializing DB: ' + err.message, 'lists-dao-mongogb.getDbConnection()');
        throw err;
    });
})();

/**
 * Fetch all shopping lists
 * 
 * @return {Promise} - promise that will be resolved (or rejected)
 * when the call to the DB completes
 */
function fetchAll() {
    return new Promise((resolve, reject) => {
        let lists = db.collection('shoppingLists');
        lists.find({}).toArray((err, documents) => {
            if (err) {
                logger.error('Error occurred: ' + err.message, 'fetchAll()');
                reject(err);
            } else {
                logger.debug('Raw data: ' + JSON.stringify(documents), 'fetchAll()');
                resolve({ data: JSON.stringify(documents), statusCode: (documents.length > 0) ? 200 : 404 });
            }
        });
    });
}

/**
 * Create a shopping list with the specified description
 * 
 * @param {String} description - the description to use
 * 
 * @param {Number} id - optional ID if the caller wants to provide the ID
 * 
 * @return {Promise} - promise that will be resolved (or rejected)
 * when the call to the DB completes
 */
function create(description) {
    return new Promise((resolve, reject) => {
        let lists = db.collection('shoppingLists');
        let listId = new mongodb.ObjectId();
        let whenCreated = Date.now();
        let item = {
            _id: listId,
            id: listId,
            description: description,
            whenCreated: whenCreated,
            whenUpdated: null
        };
        lists.insertOne(item, (err, result) => {
            if (err) {
                logger.error('Error occurred: ' + err.message, 'create()');
                reject(err);
            } else {
                resolve({ data: { createdId: result.insertedId }, statusCode: 201 });
            }
        });
    });
}

/**
 * Find the shopping list with the specified id
 * 
 * @param {number} id - the id of the shopping list record
 * to be fetched.
 * 
 * @return {Promise} - promise that will be resolved (or rejected)
 * when the call to the DB completes
 */
function findById(id) {
    return new Promise((resolve, reject) => {
        let lists = db.collection('shoppingLists');
        lists.findOne({ _id: new mongodb.ObjectID(id) }).then((document) => {
            if (document) {
                resolve({ data: JSON.stringify(document), statusCode: 200 });
            } else {
                let message = 'No document matching id: ' + id + ' could be found!';
                logger.error(message, 'findById()');
                reject(message);
            }
        }).catch((err) => {
            logger.error('Error occurred: ' + err.message, 'findById()');
            reject(err);
        });
    });
}

/**
 * Find the shopping list with the specified id
 * and return all items associated with it
 * 
 * @param {number} id - the id of the shopping list record
 * to be fetched.
 * 
 * @return {Promise} - promise that will be resolved (or rejected)
 * when the call to the DB completes
 */
function findByIdWithAllItems(id) {
    return new Promise((resolve, reject) => {
        let lists = db.collection('shoppingLists');
        lists.aggregate([
            { $unwind: '$items' },
            { $match: { _id: new mongodb.ObjectID(id) } },
            { $lookup: { from: 'items', localField: 'items', foreignField: '_id', as: 'items'} }
        ], (err, cursor) => {
            if (err) {
                logger.error('Error occurred: ' + err.message, 'findById()');
                reject(err);
            } else {
                cursor.toArray((err, results) => {
                    if (err) {
                        reject(err);
                    } else {
                        logger.debug('Raw response: ' + JSON.stringify(results), 'findByIdWithAllItems()');
                        resolve({ data: JSON.stringify(results), statusCode: (results) ? 200 : 404 });        
                    }
                });
            }
        });
    });
}

/**
 * Update the shopping list with the specified id
 * with new field values
 * 
 * @param {number} id - the id of the shopping list record
 * to be updated.
 * @param {String} description - the updated description
 * 
 * @return {Promise} - promise that will be resolved (or rejected)
 * when the call to the DB completes
 */
function update(id, description) {
    return new Promise((resolve, reject) => {
        let lists = db.collection('shoppingLists');
        lists.updateOne({ _id: new mongodb.ObjectID(id) }, 
            { $set: { description: description, whenModified: Date.now() } }, 
            (err, result) => {
                if (err) {
                    logger.error('Error occurred: ' + err.message, 'update()');
                    reject(err);
                } else {
                    resolve({ data: { rowsAffected: result.modifiedCount }, statusCode: 200 });
                }
            }
        );
    });
}

/**
 * Add the specified item to the specified shopping
 * list, along with values for the relationship
 * 
 * @param {number} listId - the id of the shopping list record
 * to which the item is to be added
 * @param {itemId} itemId - the id of the item record to be added
 * 
 * @return {Promise} - promise that will be resolved (or rejected)
 * when the call to the DB completes
 */
function addItem(listId, itemId) {
    return new Promise((resolve, reject) => {
        let lists = db.collection('shoppingLists');
        lists.updateOne({ _id: new mongodb.ObjectID(listId) }, 
            { $push: { items: new mongodb.ObjectID(itemId) }}, 
            (err, document) => {
                if (err) {
                    logger.error('Error occurred: ' + err.message, 'findById()');
                    reject(err);
                } else {
                    resolve({ data: JSON.stringify(document), statusCode: (document) ? 200 : 404 });
                }
            }
        );
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
        let lists = db.collection('shoppingLists');
        lists.updateOne({ _id: new mongodb.ObjectID(listId) }, 
            { $pull: { items: new mongodb.ObjectId(itemId) }}, 
            (err, result) => {
                if (err) {
                    logger.error('Error occurred: ' + err.message, 'findById()');
                    reject(err);
                } else {
                    resolve({ data: { rowsAffected: result.modifiedCount }, statusCode: 200 });
                }
            }
        );
    });
}

module.exports.fetchAll = fetchAll;
module.exports.create = create;
module.exports.findById = findById;
module.exports.findByIdWithAllItems = findByIdWithAllItems;
module.exports.update = update;
module.exports.addItem = addItem;
module.exports.removeItem = removeItem;
