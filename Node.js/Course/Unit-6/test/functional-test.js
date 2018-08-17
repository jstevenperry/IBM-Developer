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
 * This test class has been provided for you so you know when you're
 * finished writing code. This is test-first, or test-driven, development
 * (or one of a hundred other names this technique goes by).
 * 
 * After you run npm run load-db to load the database on your machine,
 * open a terminal window and run: 
 * 
 * npm start
 * 
 * Then open **another terminal window** and run this test: 
 * 
 * npm test 
 * 
 * and watch it fail. Study the code, look
 * for the TODOs throughout, and write code until the functional
 * tests in this class pass. At that point, you're finished.
 * 
 * Cheers,
 * The Test Lead
 */

 // Node http
const http = require('http');
const assert = require('assert');

// App settings
const appSettings = require('../config/app-settings');
// Logger
const logger = require('../utils/logger');

// A slightly more helpful assertEqual than Node provides
const { assertEqual } = require('../utils/utils');

/**
 * Helper function:
 * Make a call to the specified requestPath, and when the
 * results are done, invoke the callback.
 * Hm, this looks useful, move it into utils.js??
 */
function request(requestMethod, requestPath, postData, resultsCallback) {
    let options = '';
    if (requestMethod == 'GET') {
        options = `http://${appSettings.server_host}:${appSettings.server_listen_port}${requestPath}`;
        http.get(options, function requestCallback(response) {
            let data = '';
            response.on('data', (chunk) => {
                data += chunk;
            });
            response.on('end', () => {
                resultsCallback(null, data);
            });
            response.on('error', (err) => {
                resultsCallback(err, null);
            });
        });
    } else {
        // All others
        options = {
            hostname: appSettings.server_host,
            port: appSettings.server_listen_port,
            path: encodeURI(requestPath),
            method: requestMethod,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'Content-Length': Buffer.byteLength(postData)
              }
        };
        let req = http.request(options, function requestCallback(response) {
            let data = '';
            response.on('data', (chunk) => {
                data += chunk;
            });
            response.on('end', () => {
                resultsCallback(null, data);
            });
            response.on('error', (err) => {
                resultsCallback(err, null);
            });
        });
        req.write(postData);
        req.end();
    }
}

/**
 * Functional tests for Shopping List MVP
 * 
 * Pre-requisites: 
 * (0) Write implementation code for all REST paths
 * (1) Make sure the data is loaded into the DB before running these tests!
 * (2) Run npm start to launch node index.js
 */
(function mainline() {
    // The code in these tests runs asynchronously.
    // The tests return a Promise, so they can be chained
    /// together and run in a specific order
    testItemFindById()
    .then(() => { return testItemFindByDescription() })
    .then(() => { return testItemFindByUpc() })
    .then(() => { return testListsCreate() })
    .then(() => { return testListsFindById() })
    .then(() => { return testListsAddItem() })
    .then(() => { return testListsFindByIdWithAllItems() })
    .then(() => { return testListsUpdate() })
    .then(() => { return testListsUpdateItem() })
    .then(() => { return testListsRemoveItem() });
})();

/**
 * Functional test - find the specific item by id
 */
function testItemFindById() {
    return new Promise((resolve, reject) => {
        const expected_item_id = 8;
        const expected_item_description = 'Lea & Perrins Marinade In-a-bag Cracked Peppercorn';
        const expected_upc = '051600080015';
        const expected_brand_id = 2788;
        const expected_brand_description = 'Lea & Perrins';
        const expected_brand_manufacturer = 'Lea & Perrins, Inc.';
        request('GET', `/items?id=${expected_item_id}`, null, (err, data) => {
           if (err) {
               logger.error(`TEST FAILED with message: ${err.message} `, 'testItemFindById()')
               reject(err);
               return;
           }
           // If not an error, then process the request data
           if (data) {
               try {
                    // This request returns JSON data
                    let jsonData = JSON.parse(data);
                    // Assert on enough data that we know it works
                    assertEqual(jsonData.item_id, expected_item_id);
                    assertEqual(jsonData.item_description, expected_item_description);
                    assertEqual(jsonData.upc, expected_upc);
                    assertEqual(jsonData.brand_id, expected_brand_id);
                    assertEqual(jsonData.brand_description, expected_brand_description);
                    assertEqual(jsonData.manufacturer, expected_brand_manufacturer);
                    logger.info('TEST PASSED', 'testItemFindById()');
                    resolve();
                } catch(err) {
                    logger.error(`TEST FAILED. Try again.`, 'testItemFindById()');
                    logger.error(`ERROR MESSAGE: ${err.message}.`, 'testItemFindById()');
                       reject(err);
               }
           } else {
               let message = 'testFindItemById(): TEST FAILED. No data to be processed. Try again.'
               logger.error(message, 'testItemFindById()');
               reject(message);
           }
       });
    });
}

/**
 * Functional test - find all items matching the specified partial description
 */
function testItemFindByDescription() {
    return new Promise((resolve, reject) => {
        const expectedNumberOfResults = 15;
        request('GET', `/items?description=Free Range`, null, (err, data) => {
            if (err) {
                logger.error(`TEST FAILED with message: ${err.message} `, 'testItemFindByDescription()')
                reject(err);
                return;
            }
            // If not an error, then process the request data
            if (data) {
                try {
                    // This request returns JSON data
                    let jsonData = JSON.parse(data);
                    // Assert on enough data that we know it works
                    assertEqual(jsonData.length, expectedNumberOfResults);
                    logger.info('TEST PASSED', 'testItemFindByDescription()');
                    resolve();
                } catch(err) {
                    logger.error(`TEST FAILED. Try again.`, 'testItemFindByDescription()');
                    logger.error(`ERROR MESSAGE: ${err.message}.`, 'testItemFindByDescription()');
                    reject(err);
                }
            } else {
                let message = 'testItemFindByDescription(): TEST FAILED. No data to be processed. Try again.'
                logger.error(message, 'testItemFindByDescription()');
                throw new Error(message);
            }
        });
    });
}

/**
 * Functional test - find the specific item by UPC
 */
function testItemFindByUpc() {
    return new Promise((resolve, reject) => {
        const expected_item_id = 7878;
        const expected_item_description = 'Pacific Natural Foods Broth Free Range Chicken Organic - 4 Ct';
        const expected_upc = '052603054294'
        const expected_brand_id = 405;
        const expected_brand_description = 'Pacific';
        const expected_brand_manufacturer = 'Pacific Foods of Oregon, Inc.';
        request('GET', `/items?upc=052603054294`, null, (err, data) => {
            if (err) {
                logger.error(`TEST FAILED with message: ${err.message} `, 'testItemFindByUpc()')
                reject(err);
                return;
            }
            // If not an error, then process the request data
            if (data) {
                try {
                    // This request returns JSON data
                    let jsonData = JSON.parse(data);
                    // Assert on enough data that we know it works
                    assertEqual(jsonData.item_id, expected_item_id);
                    assertEqual(jsonData.item_description, expected_item_description);
                    assertEqual(jsonData.upc, expected_upc);
                    assertEqual(jsonData.brand_id, expected_brand_id);
                    assertEqual(jsonData.brand_description, expected_brand_description);
                    assertEqual(jsonData.manufacturer, expected_brand_manufacturer);
                    logger.info('TEST PASSED', 'testItemFindByUpc()');
                    resolve();
                } catch(err) {
                    logger.error(`TEST FAILED. Try again.`, 'testItemFindByUpc()');
                    logger.error(`ERROR MESSAGE: ${err.message}.`, 'testItemFindByUpc()');
                    reject(err);
                }
            } else {
                let message = 'testItemFindByUpc(): TEST FAILED. No data to be processed. Try again.'
                logger.error(message, 'testItemFindByUpc()');
                throw new Error(message);
            }
        });
    });
}/**
 * Functional test - create a new shopping list
 */
function testListsCreate() {
    return new Promise((resolve, reject) => {
        request('POST', `/lists`, `{ "description" : "Test Shopping List" }`, (err, data) => {
            if (err) {
                logger.error(`TEST FAILED with message: ${err.message} `, 'testListsCreate()')
                reject(err);
                return;
            }
            // If not an error, then process the request data
            if (data) {
                try {
                    // This request returns JSON data
                    let jsonData = JSON.parse(data);
                    // Assert on enough data that we know it works
                    // Crude, but it should work as a first approximation
                    assert(jsonData.createdId);
                    logger.info('TEST PASSED', 'testListsCreate()');
                    resolve();
                } catch(err) {
                    logger.error(`TEST FAILED. Try again.`, 'testListsCreate()');
                    logger.error(`ERROR MESSAGE: ${err.message}.`, 'testListsCreate()');
                    reject(err);
                }
            } else {
                let message = 'testListsCreate(): TEST FAILED. No data to be processed. Try again.'
                logger.error(message, 'testListsCreate()');
                throw new Error(message);
            }
        });
    });
}

/**
 * Functional test - find shopping list by id
 */
function testListsFindById() {
    return new Promise((resolve, reject) => {
        const listId = 1;
        request('GET', `/lists/${listId}`, '{}', (err, data) => {
            if (err) {
                logger.error(`TEST FAILED with message: ${err.message} `, 'testListsFindById()')
                reject(err);
                return;
            }
            // If not an error, then process the request data
            if (data) {
                try {
                    // This request returns JSON data
                    let jsonData = JSON.parse(data);
                    // Assert on enough data that we know it works
                    // If we get back the ID, it worked
                    assertEqual(jsonData.id, listId);
                    logger.info('TEST PASSED', 'testListsFindById()');
                    resolve();
                } catch(err) {
                    logger.error(`TEST FAILED. Try again.`, 'testListsFindById()');
                    logger.error(`ERROR MESSAGE: ${err.message}.`, 'testListsFindById()');
                    reject();
                }
            } else {
                let message = 'testListsFindById(): TEST FAILED. No data to be processed. Try again.'
                logger.error(message, 'testListsFindById()');
                throw new Error(message);
            }
        });
    });
}

/**
 * Functional test - add item to shopping list
 */
function testListsAddItem() {
    return new Promise((resolve, reject) => {
        const listId = 1;
        request('POST', `/lists/${listId}/items`, `{ "itemId" : 10, "quantity" : 2 }`, (err, data) => {
            if (err) {
                logger.error(`TEST FAILED with message: ${err.message} `, 'testListsAddItem()')
                reject(err);
                return;
            }
            // If not an error, then process the request data
            if (data) {
                try {
                    // This request returns JSON data
                    let jsonData = JSON.parse(data);
                    // Assert on enough data that we know it works
                    // Crude, but it should work as a first approximation
                    assert(jsonData.createdId);
                    logger.info('TEST PASSED', 'testListsAddItem()');
                    resolve();
                    // } else {
                    //     throw new Error('createdId not found!');
                    // }
                } catch(err) {
                    logger.error(`TEST FAILED. Try again.`, 'testListsAddItem()');
                    logger.error(`ERROR MESSAGE: ${err.message}.`, 'testListsAddItem()');
                    reject(err);
                }
            } else {
                let message = 'testListsAddItem(): TEST FAILED. No data to be processed. Try again.'
                logger.error(message, 'testListsAddItem()');
                throw new Error(message);
            }
        });
    });
}

/**
 * Functional test - find shopping list and return all items
 */
function testListsFindByIdWithAllItems() {
    return new Promise((resolve, reject) => {
        const listId = 1
        request('GET', `/lists/${listId}/items`, '{}', (err, data) => {
            if (err) {
                logger.error(`TEST FAILED with message: ${err.message} `, 'testListsFindByIdWithAllItems()')
                reject(err);
                return;
            }
            // If not an error, then process the request data
            if (data) {
                try {
                    // This request returns JSON data
                    let jsonData = JSON.parse(data);
                    // Assert on enough data that we know it works
                    assertEqual(jsonData.length > 0, true);
                    // Crude, but should work as a first approximation
                    assertEqual(jsonData[0].shopping_list_id, listId);
                    logger.info('TEST PASSED', 'testListsFindByIdWithAllItems()');
                    resolve();
                } catch(err) {
                    logger.error(`TEST FAILED. Try again.`, 'testListsFindByIdWithAllItems()');
                    logger.error(`ERROR MESSAGE: ${err.message}.`, 'testListsFindByIdWithAllItems()');
                    reject(err);
                }
            } else {
                let message = 'testListsFindByIdWithAllItems(): TEST FAILED. No data to be processed. Try again.'
                logger.error(message, 'testListsFindByIdWithAllItems()');
                throw new Error(message);
            }
        });
    });
}

/**
 * Functional test - update shopping list
 */
function testListsUpdate() {
    return new Promise((resolve, reject) => {
        const listId = 1;
        request('PUT', `/lists/${listId}`, `{ "description" : "Updated description for Functional test" }`, (err, data) => {
            if (err) {
                logger.error(`TEST FAILED with message: ${err.message} `, 'testListsUpdate()')
                reject(err);
                return;
            }
            // If not an error, then process the request data
            if (data) {
                try {
                    // This request returns JSON data
                    let jsonData = JSON.parse(data);
                    // Assert on enough data that we know it works
                    // Crude, but it should work as a first approximation
                    assert(jsonData.rowsAffected);
                    assertEqual(jsonData.rowsAffected, 1);
                    logger.info('TEST PASSED', 'testListsUpdate()');
                    resolve();
                } catch(err) {
                    logger.error(`TEST FAILED. Try again.`, 'testListsUpdate()');
                    logger.error(`ERROR MESSAGE: ${err.message}.`, 'testListsUpdate()');
                    reject(err);
                }
            } else {
                let message = 'testListsUpdate(): TEST FAILED. No data to be processed. Try again.'
                logger.error(message, 'testListsUpdate()');
                throw new Error(message);
            }
        });
    });
}

/**
 * Functional test - update item in shopping list
 */
function testListsUpdateItem() {
    return new Promise((resolve, reject) => {
        const listId = 1;
        const itemId = 10;
        request('PUT', `/lists/${listId}/items/${itemId}`, `{ "quantity" : 5 }`, (err, data) => {
            if (err) {
                logger.error(`TEST FAILED with message: ${err.message} `, 'testListsUpdateItem()')
                reject(err);
                return;
            }
            // If not an error, then process the request data
            if (data) {
                try {
                    // This request returns JSON data
                    let jsonData = JSON.parse(data);
                    // Assert on enough data that we know it works
                    // Crude, but it should work as a first approximation
                    assert(jsonData.rowsAffected);
                    assertEqual(jsonData.rowsAffected, 1);
                    logger.info('TEST PASSED', 'testListsUpdateItem()');
                    resolve();
                } catch(err) {
                    logger.error(`TEST FAILED. Try again.`, 'testListsUpdateItem()');
                    logger.error(`ERROR MESSAGE: ${err.message}.`, 'testListsUpdateItem()');
                    reject(err);
                }
            } else {
                let message = 'testListsUpdateItem(): TEST FAILED. No data to be processed. Try again.'
                logger.error(message, 'testListsUpdateItem()');
                throw new Error(message);
            }
        });
    });
}

/**
 * Functional test - remove item from shopping list
 */
function testListsRemoveItem() {
    return new Promise((resolve, reject) => {
        const listId = 1;
        const itemId = 10;
        request('DELETE', `/lists/${listId}/items/${itemId}`, `{}`, (err, data) => {
            if (err) {
                logger.error(`TEST FAILED with message: ${err.message} `, 'testListsRemoveItem()')
                reject(err);
                return;
            }
            // If not an error, then process the request data
            if (data) {
                try {
                    // This request returns JSON data
                    let jsonData = JSON.parse(data);
                    // Assert on enough data that we know it works
                    // Crude, but it should work as a first approximation
                    assert(jsonData.rowsAffected);
                    assertEqual(jsonData.rowsAffected, 1);
                    logger.info('TEST PASSED', 'testListsRemoveItem()');
                    resolve();
                } catch(err) {
                    logger.error(`TEST FAILED. Try again.`, 'testListsRemoveItem()');
                    logger.error(`ERROR MESSAGE: ${err.message}.`, 'testListsRemoveItem()');
                    reject(err);
                }
            } else {
                let message = 'testListsRemoveItem(): TEST FAILED. No data to be processed. Try again.'
                logger.error(message, 'testListsRemoveItem()');
                throw new Error(message);
            }
        });
    });
}

