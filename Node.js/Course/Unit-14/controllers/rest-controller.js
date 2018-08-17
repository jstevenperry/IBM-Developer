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
 * Controller for the Shopping List application's
 * REST interface.
 */

const url = require('url');

const logger = require('../utils/logger');
//logger.setLogLevel(logger.Level.DEBUG);

const utils = require('../utils/utils');
const listsDao = require('../models/lists-dao');
const itemsDao = require('../models/items-dao');
/**
 * 
 * Fetches all shopping lists and redirects to the
 * lists-all page
 * 
 * @param {Request} req - the Request object
 * @param {Response} res - the Response object
 * @param {Object} next - the next middleware function in the req/res cycle
 */
function fetchAll(req, res, next) {
    listsDao.fetchAll().then((result) => {
        logger.debug(`Writing JSON response: ${JSON.stringify(result)}`, 'fetchAll()');
        utils.writeServerJsonResponse(res, result.data, result.statusCode);
    }).catch((err) => {
        next(err);
    });
}

/**
 * Creates a new shopping list and redirects to the lists-all page
 * @param {Request} req - the Request object
 * @param {Response} res - the Response object
 * @param {Object} next - the next middleware function in the req/res cycle
 */
function create(req, res, next) {
    let requestBody = req.body;
    
    listsDao.create(requestBody.description).then((result) => {
        utils.writeServerJsonResponse(res, result.data, result.statusCode);
    }).catch((err) => {
        next(err);
    });
}

/**
 * Fetches the specified list and all of its items
 * and returns it to the caller.
 * 
 * @param {Request} req - the Request object
 * @param {Response} res - the Response object
 * @param {Object} next - the next middleware function in the req/res cycle
 */
function read(req, res, next) {
    let listId = req.params.listId;
    listsDao.findById(listId).then((result) => {
        utils.writeServerJsonResponse(res, result.data, result.statusCode);
    }).catch((err) => {
        next(err);
    });
}

/**
 * 
 * @param {Request} req - the Request object
 * @param {Response} res - the Response object
 * @param {Object} next - the next middleware function in the req/res cycle
 */
function update(req, res, next) {
    let listId = req.params.listId;
    let requestBody = req.body;
    
    listsDao.update(listId, requestBody.description).then((result) => {
        utils.writeServerJsonResponse(res, result.data, result.statusCode);
    }).catch((err) => {
        next(err);
    });
}

/**
 * 
 * @param {Request} req - the Request object
 * @param {Response} res - the Response object
 * @param {Object} next - the next middleware function in the req/res cycle
 */
function addItem(req, res, next) {
    let listId = req.params.listId;
    let requestBody = req.body;
    let itemId = requestBody.itemId;

    listsDao.addItem(listId, itemId).then((result) => {
        utils.writeServerJsonResponse(res, result.data, result.statusCode);
    }).catch((err) => {
        next(err);
    });
}

/**
 * 
 * @param {Request} req - the Request object
 * @param {Response} res - the Response object
 * @param {Object} next - the next middleware function in the req/res cycle
 */
function updateItem(req, res, next) {
    let listId = req.params.listId;
    let itemId = req.params.itemId;
    let requestBody = req.body;
    let quantity = requestBody.quantity;
    let pickedUp = requestBody.pickedUp;

    listsDao.updateItem(listId, itemId, quantity, pickedUp).then((result) => {
        utils.writeServerJsonResponse(res, result.data, result.statusCode);
    }).catch((err) => {
        logger.error(`Error occurred: ${err.message}`, 'updateItem()');
        next(err);
    });
}

/**
 * 
 * @param {Request} req - the Request object
 * @param {Response} res - the Response object
 * @param {Object} next - the next middleware function in the req/res cycle
 */
function removeItem(req, res, next) {
    let listId = req.params.listId;
    let itemId = req.params.itemId;

    listsDao.removeItem(listId, itemId).then((result) => {
        utils.writeServerJsonResponse(res, result.data, result.statusCode);
    }).catch((err) => {
        next(err);
    });
}

/**
 * 
 * @param {Request} req - the Request object
 * @param {Response} res - the Response object
 * @param {Object} next - the next middleware function in the req/res cycle
 */
function itemSearch(req, res, next) {
    let query = url.parse(req.url, true).query;
    if (query.description) {
        // Query DAO: 
        itemsDao.findByDescription(query.description).then((result) => {
            utils.writeServerJsonResponse(res, result.data, result.statusCode);
        }).catch((err) => {
            next(err);
        });
    // By upc?
    } else if (query.upc) {
        // Query DAO: 
        itemsDao.findByUpc(query.upc).then((result) => {
            utils.writeServerJsonResponse(res, result.data, result.statusCode);
        }).catch((err) => {
            next(err);
        });
    // By id?
    } else if (query.id) {
        itemsDao.findById(query.id).then((result) => {
            utils.writeServerJsonResponse(res, result.data, result.statusCode);
        }).catch((err) => {
            next(err);
        });
    } else {
        let message = `Unsupported search param: ${query}`;
        logger.error(message, 'itemSearch()');
        next(message);
    }
}

module.exports.fetchAll = fetchAll;
module.exports.create = create;
module.exports.read = read;
module.exports.update = update;
module.exports.addItem = addItem;
module.exports.updateItem = updateItem;
module.exports.removeItem = removeItem;
module.exports.itemSearch = itemSearch;
