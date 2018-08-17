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

const { body, validationResult } = require('express-validator/check');
const { sanitizeBody } = require('express-validator/filter');

const async = require('async');
const url = require('url');

const dateformat = require('dateformat');

const request = require('../utils/utils').httpRequest;

const logger = require('../utils/logger');

/**
 * Fetches all shopping lists and redirects to the
 * lists-all page
 * 
 * @param {Request} req - the Request object
 * @param {Response} res - the Response object
 * @param {Object} next - the next middleware function in the req/res cycle
 */
function renderFetchAllPage(req, res, next) {
    // TODO: Refactor into common method with callback
    request('GET', '/rest/lists', null, (err, data) => {
        if (err) {
            next(err);
        } else {
            // Parse the JSON object string coming back from the service
            let jsonData = JSON.parse(data);
            // Now render the page
            res.render('lists-view-all', { title: 'Shopping Lists', data: jsonData });
        }
    });
}

/**
 * Renders page to create a new shopping list
 * 
 * @param {Request} req - the Request object
 * @param {Response} res - the Response object
 * @param {Object} next - the next middleware function in the req/res cycle
 */
function renderCreatePage(req, res, next) {
    // Render the page to create a new shopping list
    res.render('lists-create', { title: 'Create Shopping List' });
}

/**
 * Render page to view a shopping list
 * 
 * @param {Request} req - the Request object
 * @param {Response} res - the Response object
 * @param {Object} next - the next middleware function in the req/res cycle
 */
function renderViewPage(req, res, next) {
    // Get the ID of the page to update from the request
    let listId = req.params.listId;
    // TODO: replace with common version shared with renderAddItemsPage
    async.parallel({
        list: function(callback) {
            request('GET', '/rest/lists/'+listId, null, (err, data) => {
                if (err) {
                    next(err);
                } else {
                    // Parse the JSON object string coming back from the service
                    let jsonData = JSON.parse(data);
                    callback(null, jsonData);
                }
            });
        },
    }, function(err, results) {
        if (err) {
            next(err);
        }
        let whenCreated = dateformat(new Date(results.list.whenCreated), 'mm-dd-yyyy hh:MM:ss');
        let whenModified = (results.list.whenModified) ? dateformat(new Date(results.list.whenModified), 'mm-dd-yyyy hh:MM:ss') : null;//eslint-disable-line
        // Now render the page
        res.render('lists-view', { 
            title: 'View Shopping List', 
            list: results.list, 
            items: results.list.items, 
            whenCreated: whenCreated, 
            whenModified: whenModified 
        });
    });
}

/**
 * Page to update list properties
 * @param {Request} req - the Request object
 * @param {Response} res - the Response object
 * @param {Object} next - the next middleware function in the req/res cycle
 */
function renderUpdatePage(req, res, next) {
    // Get the ID of the page to update from the request
    let listId = req.params.listId;
    // Get the data for the list, and if all is well, render the page
    // TODO: Refactor into common method with callback
    request('GET', '/rest/lists/'+listId, null, (err, data) => {
        if (err) {
            next(err);
        } else {
            // Parse the JSON object string coming back from the service
            logger.debug('Raw data: ' + data, 'renderUpdatePage()');
            let jsonData = JSON.parse(data);
            let whenCreated = dateformat(new Date(jsonData.whenCreated), 'mm-dd-yyyy hh:MM:ss');
            let whenModified = (jsonData.whenModified) ? dateformat(new Date(jsonData.whenModified), 'mm-dd-yyyy hh:MM:ss') : null;//eslint-disable-line
            // Now render the page
            res.render('lists-update', { 
                title: 'Update Shopping List', 
                list: jsonData,
                whenCreated: whenCreated,
                whenModified: whenModified
            });
        }
    });
}

/**
 * Page to search for Items to add to a shopping list
 * 
 * @param {Request} req - the Request object
 * @param {Response} res - the Response object
 * @param {Object} next - the next middleware function in the req/res cycle
 */
function renderItemSearchPage(req, res, next) {
    // Get the ID of the page to update from the request
    let listId = req.params.listId;
    // Get the data for the list, and if all is well, render the page
    // TODO: Refactor into common method with callback shared with renderViewPage
    async.parallel({
        list: function(callback) {
            request('GET', '/rest/lists/'+listId, null, (err, data) => {
                if (err) {
                    next(err);
                } else {
                    // Parse the JSON object string coming back from the service
                    let jsonData = JSON.parse(data);
                    callback(null, jsonData);
                }
            });
        },
        searchResults: function(callback) {
            let query = url.parse(req.url).query;
            if (query) {
                request('GET', '/rest/items?'+query, null, (err, data) => {
                    if (err) {
                        next(err);
                    } else {
                        let jsonData = JSON.parse(data);
                        callback(null, jsonData);
                    }
                });
            } else {
                callback(null, { });
            }
        }
    }, function(err, results) {
            if (err) {
                next(err);
            }
            let whenCreated = dateformat(new Date(results.list.whenCreated), 'mm-dd-yyyy hh:MM:ss');
            let whenModified = (results.list.whenModified) ? dateformat(new Date(results.list.whenModified), 'mm-dd-yyyy hh:MM:ss') : null;//eslint-disable-line
                // Now render the page
            res.render('lists-item-search', { 
                title: 'Search Items', 
                listId: listId, 
                list: results.list, 
                items: results.list.items, 
                searchResults: results.searchResults,
                whenCreated: whenCreated,
                whenModified: whenModified
            });
        }
    );
}

/**
 * Create the new Shopping List.
 * This is a function chain:
 * Validation input fields
 * Sanitize input fields
 * If errors, render page with errors
 * Else call the /rest/list REST service to create the list, 
 * then redirects back to the main page
 * 
 * @param {Request} req - the Request object
 * @param {Response} res - the Response object
 * @param {Object} next - the next middleware function in the req/res cycle
 */
const createList = [
    // TODO: Refactor into common method shared with updateList
    // Validate input(s)
    body('description', 'Description cannot be empty').isLength({ min: 1 }),
    // Sanitize fields.
    sanitizeBody('description').trim().escape(),
    // Check validation results
    (req, res, next) => {
        const errors = validationResult(req);
        if (!errors.isEmpty()) {
            let errorsArray = errors.array();
            logger.debug(`Found ${errorsArray.length} errors with the request`);
            res.render('lists-create', { title: 'Create Shopping List', data: '', errors: errorsArray });
        } else {
            logger.debug('Request is error free. Moving on...', 'createList()');
            next();
        }
    },
    // All is well (if we got this far). Send the request!
    (req, res, next) => {
        let requestBody = JSON.stringify(req.body);
        request('POST', '/rest/lists', requestBody, (err, data) => {
            if (err) {
                next(err);
            } else {
                // Redirect to main /lists page
                res.redirect('/lists');
            }
        });
    }
];

/**
 * Perform items search
 * This is a function chain:
 * Validation input fields
 * Sanitize input fields
 * If errors, render page with errors
 * Else call the /rest/list REST service to create the list, 
 * then redirects back to the main page
 * 
 * @param {Request} req - the Request object
 * @param {Response} res - the Response object
 * @param {Object} next - the next middleware function in the req/res cycle
 */
const addItemsSearch = [
    // Sanitize fields.
    sanitizeBody('description').trim().escape(),
    // Send the request!
    (req, res, next) => {
        let listId = req.params.listId;
        let description = req.body.description;
        res.redirect('/lists/'+listId+'/itemSearch?description='+description);
    }
];

/**
 * Update the specified Shopping List.
 * This is a function chain:
 * Validation input fields
 * Sanitize input fields
 * If errors, render page with errors
 * Else call the /rest/list REST service to create the list, 
 * then redirects back to the main page
 * 
 * @param {Request} req - the Request object
 * @param {Response} res - the Response object
 * @param {Object} next - the next middleware function in the req/res cycle
 */
const updateList = [
    // Validate input(s)
    body('description', 'Description cannot be empty').isLength({ min: 1 }),
    // Sanitize fields.
    sanitizeBody('description').trim().escape(),
    // Check validation results
    (req, res, next) => {
        const errors = validationResult(req);
        if (!errors.isEmpty()) {
            let errorsArray = errors.array();
            logger.debug(`Found ${errorsArray.length} errors with the request`);
            res.render('lists-create', { title: 'Create Shopping List', data: '', errors: errorsArray });
        } else {
            logger.debug('Request is error free. Moving on...', 'updateList()');
            next();
        }
    },
    // All is well (if we got this far). Send the request!
    (req, res, next) => {
        let listId = req.params.listId;
        let requestBody = JSON.stringify(req.body);
        request('PUT', '/rest/lists/'+listId, requestBody, (err, data) => {
            if (err) {
                next(err);
            } else {
                // Redirect to main /lists page
                res.redirect('/lists');
            }
        });
    }
];

/**
 * Update the specified Shopping List.
 * This is a function chain:
 * Validation input fields
 * Sanitize input fields
 * If errors, render page with errors
 * Else call the /rest/list REST service to create the list, 
 * then redirects back to the main page
 * 
 * @param {Request} req - the Request object
 * @param {Response} res - the Response object
 * @param {Object} next - the next middleware function in the req/res cycle
 */
const addListItems = [
    (req, res, next) => {
        let listId = req.params.listId;
        let itemsToAdd = [];
        let itemIds = req.body.selected;
        if (Array.isArray(itemIds)) {
            itemsToAdd = itemIds;
        } else {
            itemsToAdd.push(itemIds);
        }
        //itemIds.push(req.body.selected);
        if (itemsToAdd.length > 0) {
            async.eachOfSeries(itemsToAdd, function(id, key, callback) {
                request('POST', '/rest/lists/'+listId+'/items', `{ "itemId": "${id}" }`, (err, data) => {
                    if (err) {
                        next(err);
                    } else {
                        callback();
                    }
                });
            }, function(err) {
                if (err) {
                    next(err);
                } else {
                    res.redirect('/lists/'+listId);
                }
            });
        } else {
            logger.warn('Search produced no results, or nothing selected.', 'addListItems[]');
            res.redirect('/lists/'+listId);
        }
    }
];

/**
 * 
 * @param {Request} req - the Request object
 * @param {Response} res - the Response object
 * @param {Object} next - the next middleware function in the req/res cycle
 */
function updateListItem(req, res, next) {
    logger.info('updateItem');
    res.send('updateItem');
}

/**
 * 
 * @param {Request} req - the Request object
 * @param {Response} res - the Response object
 * @param {Object} next - the next middleware function in the req/res cycle
 */
const removeItems = [
    (req, res, next) => {
        let listId = req.params.listId;
        let itemsToDelete = [];
        let itemIds = req.body.selected;
        if (Array.isArray(itemIds)) {
            itemsToDelete = itemIds;
        } else {
            itemsToDelete.push(itemIds);
        }
        //itemIds.push(req.body.selected);
        if (itemsToDelete.length > 0) {
            async.eachOfSeries(itemsToDelete, function(id, key, callback) {
                logger.debug('Deleting item id ' + id, 'removeItems[]');
                request('DELETE', '/rest/lists/'+listId+'/items/'+id, '{}', (err, data) => {
                    if (err) {
                        next(err);
                    } else {
                        callback();
                    }
                });
            }, function(err) {
                if (err) {
                    next(err);
                } else {
                    res.redirect('/lists/'+listId);
                }
            });
        } else {
            logger.warn(`Nothing selected, returning to /lists/${listId}.`, 'removeItems[]');
            res.redirect('/lists/'+listId);
        }
    }
];

// Pages
module.exports.fetchAll = renderFetchAllPage;
module.exports.create = renderCreatePage;
module.exports.read = renderViewPage;
module.exports.update = renderUpdatePage;
module.exports.itemSearch = renderItemSearchPage;// input search criteria

// Services
module.exports.createList = createList;
module.exports.updateList = updateList;
module.exports.addItemsSearch = addItemsSearch;// perform item search
module.exports.addListItems = addListItems;// add items to list
module.exports.updateListItem = updateListItem;
module.exports.removeItems = removeItems;
