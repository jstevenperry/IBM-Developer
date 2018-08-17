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
 * This module handles calling the DAO layer on behalf of the routes.
 * You will need to write code to make the DAO calls. Remember, they
 * are asynchronous so you have to "Promise" to carefully think through
 * what to do first.
 */

// TODO: Figure out what require()s you need here
const logger = require('../utils/logger');
const utils = require('../utils/utils');

const listsDao = require('../models/lists-dao');
/**
 * Handle (that is, resolve() or reject()) request for lists
 * to find by Id (e.g., GET /lists/123)
 * 
 * Call listsDao.findById()
 */
function handleListsFindById(request, resolve, reject, id) {
    // Call listsDao.findById()
    logger.debug(`Calling listsDao.findById(${id})`, 'handleListsFindById()');
    listsDao.findById(id).then((result) => {
        resolve(result);
    }).catch((err) => {
        reject(err)
    });
}

/**
 * Handle (i.e., resolve() or reject()) request for lists
 * to create new list (e.g., POST /lists)
 * 
 * Call listsDao.create() after getting body using utils.processRequestBody()
 */
function handleListsCreate(request, resolve, reject) {
    utils.processRequestBody(request).then((requestBody) => {
        logger.debug(`Calling listsDao.create() with request: ${requestBody}`, 'handleListsCreate()');
        let requestBodyJson = JSON.parse(requestBody);
        listsDao.create(requestBodyJson.description).then((result) => {
            resolve(result);
        }).catch((err) => {
            reject(err);
        });
    }).catch((err) => {
        logger.error(`Error processing request body: ${err.message}`, 'handleListsCreate()');
        reject(err);
    });
}

/**
 * Handle (i.e., resolve() or reject()) request for lists
 * to update the list (e.g., PUT /lists/123)
 * 
 * Call listsDao.update() after getting body using utils.processRequestBody()
 */
function handleListsUpdate(request, resolve, reject, id) {
    // TODO: WRITE CODE
    utils.processRequestBody(request).then((requestBody) => {
        logger.debug(`Calling listsDao.update() with request: ${requestBody}`, 'handleListsUpdate()');
        let requestBodyJson = JSON.parse(requestBody);
        listsDao.update(id, requestBodyJson.description).then((result) => {
            resolve(result);
        }).catch((err) => {
            reject(err);
        });
    }).catch((err) => {
        logger.error(`Error processing request body: ${err.message}`, 'handleListsUpdate()');
        reject(err);
    });
}

/**
 * Handle request for lists - find list by id and return all items
 * 
 * Make sure secondary resource is items, or reject with an error message
 * 
 * Call listsDao.findByIdWithAllItems()
 */
function handleListsFindByIdWithAllItems(request, resolve, reject ,id, secondaryResource) {
    // TODO: WRITE CODE
    if (secondaryResource == 'items') {
        logger.debug(`Calling listsDao.findByIdWithAllItems(listId=${id},secondaryResource=${secondaryResource})`, 'handleListsFindByIdWithAllItems()');
        listsDao.findByIdWithAllItems(id).then((result) => {
            resolve(result);
        }).catch((err) => {
            reject(err);
        });
    } else {
        let message = `secondaryResource: ${secondaryResource} is not supported.`;
        logger.error(message, 'handleListsFindByIdWithAllItems()');
        reject(message);
    }
}

/**
 * Handle requests for lists - add item to list
 * 
 * Make sure secondary resource is items, or reject with an error message
 * 
 * After getting request body using utils.processRequestBody(), call 
 * listsDao.addItem()
 */
function handleListsAddItem(request, resolve, reject, id, secondaryResource) {
    // TODO: WRITE CODE
    if (secondaryResource == 'items') {
        logger.debug(`Calling listsDao.addItem(listId=${id},secondaryResource=${secondaryResource})`, 'handleListsAddItem()');
        utils.processRequestBody(request).then((requestBody) => {
            logger.debug(`Calling listsDao.addItem() with request: ${requestBody}`, 'handleListsUpdate()');
            let requestBodyJson = JSON.parse(requestBody);
            listsDao.addItem(id, requestBodyJson.itemId, requestBodyJson.quantity).then((result) => {
                resolve(result);
            }).catch((err) => {
                reject(err);
            });
        }).catch((err) => {
            logger.error(`Error processing request body: ${err.message}`, 'handleListsUpdate()');
            reject(err);
        });
    } else {
        let message = `secondaryResource: ${secondaryResource} is not supported.`;
        logger.error(message, 'handleListsAddItem()');
        reject(message);
    }
}

/**
 * Handle requests for lists - update item in list
 * 
 * Make sure secondary resource is items, or reject with an error message
 * 
 * After getting request body using utils.processRequestBody(), call 
 * listsDao.updateItem()
 */
function handleListsWithItemsUpdate(request, resolve, reject, id, secondaryResource, secondaryResourceId) {
    // TODO: WRITE CODE
    if (secondaryResource == 'items') {
        logger.debug(`Calling listsDao.updateItem(listId=${id},secondaryResource=${secondaryResource},secondaryResourceId=${secondaryResourceId})`, 'handleListsWithItemsUpdate()');
        utils.processRequestBody(request).then((requestBody) => {
            logger.debug(`Calling listsDao.updateItem() with request: ${requestBody}`, 'handleListsWithItemsUpdate()');
            let requestBodyJson = JSON.parse(requestBody);
            listsDao.updateItem(id, secondaryResourceId, requestBodyJson.quantity, requestBodyJson.pickedUp).then((result) => {
                resolve(result);
            }).catch((err) => {
                reject(err);
            });
        }).catch((err) => {
            logger.error(`Error processing request body: ${err.message}`, 'handleListsWithItemsUpdate()');
            reject(err);
        });
    } else {
        let message = `secondaryResource: ${secondaryResource} is not supported.`;
        logger.error(message, 'handleListsWithItemsUpdate()');
        reject(message);
    }
}

/**
 * Handle requests for lists - delete item from list
 * 
 * Make sure secondary resource is items, or reject with an error message
 * 
 * Call listsDao.removeItem() (you're not actually deleting the item, just removing it from the list)
 */
function handleListsWithItemsDelete(request, resolve, reject, id, secondaryResource, secondaryResourceId) {
    // TODO: WRITE CODE
    if (secondaryResource == 'items') {
        let message = `Calling listsDao.deleteItem(listId=${id},secondaryResource=${secondaryResource},secondaryResourceId=${secondaryResourceId})`;
        logger.debug(message, 'handleListsWithItemsDelete()');
        listsDao.removeItem(id, secondaryResourceId).then((result) => {
            resolve(result);
        }).catch ((err) => {
            reject(err);
        });
    } else {
        let message = `secondaryResource: ${secondaryResource} is not supported.`;
        logger.error(message, 'handleListsWithItemsDelete()');
        reject(message);
    }
}

// TODO: Make sure to export the functions you want to be visible to the rest of the app
module.exports.handleListsWithItemsDelete = handleListsWithItemsDelete;
module.exports.handleListsWithItemsUpdate = handleListsWithItemsUpdate;
module.exports.handleListsAddItem = handleListsAddItem;
module.exports.handleListsUpdate = handleListsUpdate;
module.exports.handleListsCreate = handleListsCreate;
module.exports.handleListsFindById = handleListsFindById;
module.exports.handleListsFindByIdWithAllItems = handleListsFindByIdWithAllItems;