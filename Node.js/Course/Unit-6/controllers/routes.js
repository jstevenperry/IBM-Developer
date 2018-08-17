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
 * This module handles all routes supported by the application.
 * You should use it as-is with no changes. If you find yourself
 * making changes to this module (unless to fix a bug, in which case
 * please log an issue), you have probably gone off the tracks
 * somewhere.
 */
// Utilities
const utils = require('../utils/utils');
// Logger
const logger = require('../utils/logger');
logger.setLogLevel(logger.Level.DEBUG);

// Route Handlers
const listsHandler = require('./lists-handler');
const itemsHandler = require('./items-handler');

/**
 * Handle request for all supported /items paths
 */
function routeItemsRequest(request, parsedUrl) {
    return new Promise((resolve, reject) => {
        logger.debug('Cracking message', 'routeItemsRequest()');
        let urlPath = utils.parseUrl(parsedUrl.pathname).pathComponents;
        switch (urlPath.length) {
            case 1:
                routeItemsOnly(request, resolve, reject, parsedUrl, request.method);
                break;
            default:
                var message = `Invalid path: ${parsedUrl} for method: ${request.method}`;
                logger.error(message, 'routeItemsRequest()');
                reject(message);
                break;
        }    
    });
}

/**
 * Route request for all supported /lists paths:
 * /lists/
 * /lists/id (e.g., /lists/123)
 * /lists/id/items (e.g. /lists/123/items)
 * /lists/id/items/itemId (e.g., /lists/123/items/567)
 */
function routeListsRequest(request, parsedUrl) {
    return new Promise((resolve, reject) => {
        // Crack on length first, then method (in the next layer)
        let urlPath = utils.parseUrl(parsedUrl.pathname).pathComponents;
        switch (urlPath.length) {
            case 1:
                // Handle lists (e.g., /lists)
                routeListsOnly(request, resolve, reject, parsedUrl);
                break;
            case 2:
                // Handle lists with id (e.g., /lists/123)
                routeListsWithId(request, resolve, reject, parsedUrl, urlPath[1]);
                break;
            case 3:
                // Handle lists with items (if third path component is items)
                /// (e.g., /lists/123/items)
                routeListsWithItems(request, resolve, reject, parsedUrl, urlPath[1], urlPath[2]);
                break;
            case 4:
                // Handle lists with items and item id (e.g., /lists/123/items/567)
                routeListsWithItemsAndItemId(request, resolve, reject, parsedUrl, urlPath[1], urlPath[2], urlPath[3]);
                break;
            default:
                let message = `Unsupported path: ${parsedUrl.pathname}`;
                logger.error(message, 'routeListsRequest()');
                reject(message);
        }
    });
}

/**
 * Routes request for GET for items only (e.g., /items)
 */
function routeItemsOnly(request, resolve, reject, parsedUrl) {
    switch (request.method) {
        case 'GET':
            itemsHandler.handleItemsSearch(request, resolve, reject, parsedUrl);
            break;
        default:
            let message = `Unsupported method: ${request.method}`;
            logger.error(message, 'routeItemsRequest()');
            reject(message);
            break;
    }
}

/**
 * Route request for GET and POST for /lists URL only (e.g., /lists)
 */
function routeListsOnly(request, resolve, reject, parsedUrl) {
    switch (request.method) {
        case 'POST':
            // create shopping List
            listsHandler.handleListsCreate(request, resolve, reject);
            break;
        default:
            var message = `HTTP Method ${request.method} is not supported for ${parsedUrl.pathname}`;
            logger.error(message, 'routeListsRequest()');
            reject(message);
            break;
    }
}

/**
 * Route request for GET and PUT for /lists/id (e.g., /lists/123)
 */
function routeListsWithId(request, resolve, reject, parsedUrl, id) {
    switch (request.method) {
        case 'GET':
            // findById
            listsHandler.handleListsFindById(request, resolve, reject, id);
            break;
        case 'PUT':
            // create shopping List
            listsHandler.handleListsUpdate(request, resolve, reject, id);
            break;
        default:
            var message = `HTTP Method ${request.method} is not supported for ${parsedUrl.pathname}`;
            logger.error(message, 'routeListsRequest()');
            reject(message);
            break;
    }
}

/**
 * Route request for POST for /lists/id/items (e.g., /lists/123/items)
 */
function routeListsWithItems(request, resolve, reject, parsedUrl, id, secondaryResource) {
    switch (request.method) {
        case 'GET':
            // Get shopping list and all items added to it
            listsHandler.handleListsFindByIdWithAllItems(request, resolve, reject, id, secondaryResource);
            break;
        case 'POST':
            // add item to shopping List
            listsHandler.handleListsAddItem(request, resolve, reject, id, secondaryResource);
            break;
        default:
            var message = `HTTP Method ${request.method} is not supported for ${parsedUrl.pathname}`;
            logger.error(message, 'routeListsRequest()');
            reject(message);
            break;
    }
}

/**
 * Route request for GET, PUT, and DELETE for /lists/id/items/id (e.g., /lists/123/items/567)
 */
function routeListsWithItemsAndItemId(request, resolve, reject, parsedUrl, id, secondaryResource, secondaryResourceId) {
    switch (request.method) {
        case 'PUT':
            // Update item within shopping list
            listsHandler.handleListsWithItemsUpdate(request, resolve, reject, id, secondaryResource, secondaryResourceId);
            break;
        case 'DELETE':
            // Delete item from shopping list
            listsHandler.handleListsWithItemsDelete(request, resolve, reject, id, secondaryResource, secondaryResourceId);
            break;
        default:
            var message = `HTTP Method ${request.method} is not supported for ${parsedUrl.pathname}`;
            logger.error(message, 'routeListsRequest()');
            reject(message);
            break;
    }
}

// What's exported
module.exports.routeItemsRequest = routeItemsRequest;
module.exports.routeListsRequest = routeListsRequest;