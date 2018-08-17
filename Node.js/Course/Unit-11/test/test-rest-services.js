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

// Do not check camelcase for this file
/* eslint camelcase: 0 */

const { before, describe, it } = require('mocha');
const { expect } = require('chai');

// Logger
const logger = require('../utils/logger');
//logger.setLogLevel(logger.Level.INFO);

// HTTP request helper
const request = require('../utils/utils').httpRequest;

before(function() {
    logger.warn('**********************************************************************************');
    logger.warn('* WARNING: Before running this functional test suite, make sure the DB is empty! *');
    logger.warn('*          If you do not, the tests will most likely fail! You have been warned. *');
    logger.warn('**********************************************************************************');
});

describe('GET /rest/items?id=8', function testItemFindById() {
    it('should return the item with id = 8 and upc 051600080015 (and lots more)', (done) => {
        const expected_item_id = 8;// eslint-disable-camelcase
        const expected_item_description = 'Lea & Perrins Marinade In-a-bag Cracked Peppercorn';
        const expected_upc = '051600080015';
        const expected_brand_id = 2788;
        const expected_brand_description = 'Lea & Perrins';
        const expected_brand_manufacturer = 'Lea & Perrins, Inc.';
        request('GET', `/rest/items?id=${expected_item_id}`, null, (err, data) => {
            if (err) {
                logger.error(`TEST FAILED with message: ${err.message} `, 'testItemFindById()');
                return;
            }
            // If not an error, then process the request data
            if (data) {
                try {
                    logger.debug('Raw response data: ' + data, 'testItemFindById()');
                    // This request returns JSON data
                    let jsonData = JSON.parse(data);
                    // Assert on enough data that we know it works
                    expect(jsonData.item_id).to.equal(expected_item_id);
                    expect(jsonData.item_description).to.equal(expected_item_description);
                    expect(jsonData.upc).to.equal(expected_upc);
                    expect(jsonData.brand_id).to.equal(expected_brand_id);
                    expect(jsonData.brand_description).to.equal(expected_brand_description);
                    expect(jsonData.manufacturer).to.equal(expected_brand_manufacturer);
                    logger.debug('TEST PASSED', 'testItemFindById()');
                } catch (err) {
                    logger.error(`TEST FAILED. Try again.`, 'testItemFindById()');
                    logger.error(`ERROR MESSAGE: ${err.message}.`, 'testItemFindById()');
                    throw new Error('Test failed with message: ' + err.message);
                }
                done();
            } else {
                let message = 'testFindItemById(): TEST FAILED. No data to be processed. Try again.';
                logger.error(message, 'testItemFindById()');
                throw new Error(message);
            }
        });
    });
});

describe('GET /rest/items?description=Free Range', function testItemFindByDescription() {
    it('should return 15 items', function(done) {
        const expectedNumberOfResults = 15;
        request('GET', `/rest/items?description=Free Range`, null, (err, data) => {
            if (err) {
                logger.error(`TEST FAILED with message: ${err.message} `, 'testItemFindByDescription()');
                return;
            }
            // If not an error, then process the request data
            if (data) {
                try {
                    logger.debug('Raw response data: ' + data, 'testItemFindByDescription()');
                    // This request returns JSON data
                    let jsonData = JSON.parse(data);
                    // Assert on enough data that we know it works
                    expect(jsonData.length).to.equal(expectedNumberOfResults);
                    logger.debug('TEST PASSED', 'testItemFindByDescription()');
                } catch (err) {
                    logger.error(`TEST FAILED. Try again.`, 'testItemFindByDescription()');
                    logger.error(`ERROR MESSAGE: ${err.message}.`, 'testItemFindByDescription()');
                    throw new Error('Test failed with message: ' + err.message);
                }
                done();
            } else {
                let message = 'testItemFindByDescription(): TEST FAILED. No data to be processed. Try again.';
                logger.error(message, 'testItemFindByDescription()');
                throw new Error(message);
            }
        });
    });
});

describe('GET /rest/items?upc=052603054294', function testItemFindByUpc() {
    it('should return the matching item', (done) => {
        const expected_item_id = 7878;
        const expected_item_description = 'Pacific Natural Foods Broth Free Range Chicken Organic - 4 Ct';
        const expected_upc = '052603054294';
        const expected_brand_id = 405;
        const expected_brand_description = 'Pacific';
        const expected_brand_manufacturer = 'Pacific Foods of Oregon, Inc.';
        request('GET', `/rest/items?upc=052603054294`, null, (err, data) => {
            if (err) {
                logger.error(`TEST FAILED with message: ${err.message} `, 'testItemFindByUpc()');
                return;
            }
            // If not an error, then process the request data
            if (data) {
                try {
                    logger.debug('Raw response data: ' + data, 'testItemFindByUpc()');
                    // This request returns JSON data
                    let jsonData = JSON.parse(data);
                    // Assert on enough data that we know it works
                    expect(jsonData.item_id).to.equal(expected_item_id);
                    expect(jsonData.item_description).to.equal(expected_item_description);
                    expect(jsonData.upc).to.equal(expected_upc);
                    expect(jsonData.brand_id).to.equal(expected_brand_id);
                    expect(jsonData.brand_description).to.equal(expected_brand_description);
                    expect(jsonData.manufacturer).to.equal(expected_brand_manufacturer);
                    logger.debug('TEST PASSED', 'testItemFindByUpc()');
                } catch (err) {
                    logger.error(`TEST FAILED. Try again.`, 'testItemFindByUpc()');
                    logger.error(`ERROR MESSAGE: ${err.message}.`, 'testItemFindByUpc()');
                    throw new Error('Test failed with message: ' + err.message);
                }
                done();
            } else {
                let message = 'testItemFindByUpc(): TEST FAILED. No data to be processed. Try again.';
                logger.error(message, 'testItemFindByUpc()');
                throw new Error(message);
            }
        });
    });
});

describe('POST /rest/lists', function testListsCreate() {
    it('should return the non-null ID of the newly created shopping list', (done) => {
        request('POST', `/rest/lists`, `{ "description" : "Test Shopping List" }`, (err, data) => {
            if (err) {
                logger.error(`TEST FAILED with message: ${err.message} `, 'testListsCreate()');
                return;
            }
            // If not an error, then process the request data
            if (data) {
                try {
                    logger.debug('Raw response data: ' + data, 'testListsCreate()');
                    // This request returns JSON data
                    let jsonData = JSON.parse(data);
                    // Expect there to be a non-null property called createdId
                    expect(jsonData.createdId).to.be.not.null;
                    logger.debug('TEST PASSED', 'testListsCreate()');
                } catch (err) {
                    logger.error(`TEST FAILED. Try again.`, 'testListsCreate()');
                    logger.error(`ERROR MESSAGE: ${err.message}.`, 'testListsCreate()');
                    throw new Error('Test failed with message: ' + err.message);
                }
                done();
            } else {
                let message = 'testListsCreate(): TEST FAILED. No data to be processed. Try again.';
                logger.error(message, 'testListsCreate()');
                throw new Error(message);
            }
        });
    });
 });

describe('GET /rest/lists/1', function testListsFindById() {
    it('should return the shopping list of id = 1', (done) => {
        const listId = 1;
        request('GET', `/rest/lists/${listId}`, '{}', (err, data) => {
            if (err) {
                logger.error(`TEST FAILED with message: ${err.message} `, 'testListsFindById()');
                return;
            }
            // If not an error, then process the request data
            if (data) {
                try {
                    logger.debug('Raw response data: ' + data, 'testListsFindById()');
                    // This request returns JSON data
                    let jsonData = JSON.parse(data);
                    // Assert on enough data that we know it works
                    // If we get back the ID, it worked
                    expect(jsonData.id).to.equal(listId);
                    logger.debug('TEST PASSED', 'testListsFindById()');
                } catch (err) {
                    logger.error(`TEST FAILED. Try again.`, 'testListsFindById()');
                    logger.error(`ERROR MESSAGE: ${err.message}.`, 'testListsFindById()');
                    throw new Error('Test failed with message: ' + err.message);
                }
                done();
            } else {
                let message = 'testListsFindById(): TEST FAILED. No data to be processed. Try again.';
                logger.error(message, 'testListsFindById()');
                throw new Error(message);
            }
        });
    });
});

describe('POST /rest/lists/1/items', function testListsAddItem() {
    it('should return the non-null ID of the newly created shopping list item', (done) => {
        const listId = 1;
        request('POST', `/rest/lists/${listId}/items`, `{ "itemId" : 10, "quantity" : 2 }`, (err, data) => {
            if (err) {
                logger.error(`TEST FAILED with message: ${err.message} `, 'testListsAddItem()');
                return;
            }
            // If not an error, then process the request data
            if (data) {
                try {
                    logger.debug('Raw response data: ' + data, 'testListsAddItem()');
                    // This request returns JSON data
                    let jsonData = JSON.parse(data);
                    // Expect there to be a non-null property called createdId
                    expect(jsonData.createdId).to.be.not.null;
                    logger.debug('TEST PASSED', 'testListsAddItem()');
                } catch (err) {
                    logger.error(`TEST FAILED. Try again.`, 'testListsAddItem()');
                    logger.error(`ERROR MESSAGE: ${err.message}.`, 'testListsAddItem()');
                    throw new Error('Test failed with message: ' + err.message);
                }
                done();
            } else {
                let message = 'testListsAddItem(): TEST FAILED. No data to be processed. Try again.';
                logger.error(message, 'testListsAddItem()');
                throw new Error(message);
            }
        });
    });
});

describe('GET /rest/lists/1/items', function testListsFindByIdWithAllItems() {
    it('should return a collection of lists whose length is > 0 and whose first item belongs to its shopping list', (done) => {// eslint-disable-line max-len
        const listId = 1;
        request('GET', `/rest/lists/${listId}/items`, '{}', (err, data) => {
            if (err) {
                logger.error(`TEST FAILED with message: ${err.message} `, 'testListsFindByIdWithAllItems()');
                return;
            }
            // If not an error, then process the request data
            if (data) {
                try {
                    logger.debug('Raw response data: ' + data, 'testListsFindByIdWithAllItems()');
                    // This request returns JSON data
                    let jsonData = JSON.parse(data);
                    // Assert on enough data that we know it works
                    expect(jsonData.length).to.be.greaterThan(0);
                    // Crude, but should work as a first approximation
                    expect(jsonData[0].shopping_list_id).to.equal(listId);
                    logger.debug('TEST PASSED', 'testListsFindByIdWithAllItems()');
                } catch (err) {
                    logger.error(`TEST FAILED. Try again.`, 'testListsFindByIdWithAllItems()');
                    logger.error(`ERROR MESSAGE: ${err.message}.`, 'testListsFindByIdWithAllItems()');
                    throw new Error('Test failed with message: ' + err.message);
                }
                done();
            } else {
                let message = 'testListsFindByIdWithAllItems(): TEST FAILED. No data to be processed. Try again.';
                logger.error(message, 'testListsFindByIdWithAllItems()');
                throw new Error(message);
            }
        });
    });
});

describe('PUT /rest/lists/', function testListsUpdate() {
    it('should successfully update one and only one row', (done) => {
        const listId = 1;
        request('PUT', `/rest/lists/${listId}`, 
            `{ "description" : "Updated description for Functional test" }`, (err, data) => {
            if (err) {
                logger.error(`TEST FAILED with message: ${err.message} `, 'testListsUpdate()');
                return;
            }
            // If not an error, then process the request data
            if (data) {
                try {
                    logger.debug('Raw response data: ' + data, 'testListsUpdate()');
                    // This request returns JSON data
                    let jsonData = JSON.parse(data);
                    // Expect there to be a property called rowsAffected
                    expect(jsonData.rowsAffected).to.exist;
                    // It should be equal to one. Not two. Not three. Four is right out.
                    expect(jsonData.rowsAffected).to.equal(1);
                    logger.debug('TEST PASSED', 'testListsUpdate()');
                } catch (err) {
                    logger.error(`TEST FAILED. Try again.`, 'testListsUpdate()');
                    logger.error(`ERROR MESSAGE: ${err.message}.`, 'testListsUpdate()');
                    throw new Error('Test failed with message: ' + err.message);
                }
                done();
            } else {
                let message = 'testListsUpdate(): TEST FAILED. No data to be processed. Try again.';
                logger.error(message, 'testListsUpdate()');
                throw new Error(message);
            }
        });
    });
});

describe('PUT /rest/lists/1/items/10', function testListsUpdateItem() {
    it('should update one and only one row', (done) => {
        const listId = 1;
        const itemId = 10;
        request('PUT', `/rest/lists/${listId}/items/${itemId}`, `{ "quantity" : 5 }`, (err, data) => {
            if (err) {
                logger.error(`TEST FAILED with message: ${err.message} `, 'testListsUpdateItem()');
                return;
            }
            // If not an error, then process the request data
            if (data) {
                try {
                    logger.debug('Raw response data: ' + data, 'testListsUpdateItem()');
                    // This request returns JSON data
                    let jsonData = JSON.parse(data);
                    // Expect there to be a property called rowsAffected
                    expect(jsonData.rowsAffected).to.exist;
                    // It should be equal to one. Not two. Not three. Four is right out.
                    expect(jsonData.rowsAffected).to.equal(1);
                    logger.debug('TEST PASSED', 'testListsUpdateItem()');
                } catch (err) {
                    logger.error(`TEST FAILED. Try again.`, 'testListsUpdateItem()');
                    logger.error(`ERROR MESSAGE: ${err.message}.`, 'testListsUpdateItem()');
                    throw new Error('Test failed with message: ' + err.message);
                }
                done();
            } else {
                let message = 'testListsUpdateItem(): TEST FAILED. No data to be processed. Try again.';
                logger.error(message, 'testListsUpdateItem()');
                throw new Error(message);
            }
        });
    });
});

describe('DELETE /rest/lists/1/items/10', function testListsRemoveItem() {
    it('should delete one and only one row', (done) => {
        const listId = 1;
        const itemId = 10;
        request('DELETE', `/rest/lists/${listId}/items/${itemId}`, `{}`, (err, data) => {
            if (err) {
                logger.error(`TEST FAILED with message: ${err.message} `, 'testListsRemoveItem()');
                return;
            }
            // If not an error, then process the request data
            if (data) {
                try {
                    logger.debug('Raw response data: ' + data, 'testListsRemoveItem()');
                    // This request returns JSON data
                    let jsonData = JSON.parse(data);
                    // Expect there to be a property called rowsAffected
                    expect(jsonData.rowsAffected).to.exist;
                    // It should be equal to one. Not two. Not three. Four is right out.
                    expect(jsonData.rowsAffected).to.be.greaterThan(0);
                    logger.debug('TEST PASSED', 'testListsRemoveItem()');
                } catch (err) {
                    logger.error(`TEST FAILED. Try again.`, 'testListsRemoveItem()');
                    logger.error(`ERROR MESSAGE: ${err.message}.`, 'testListsRemoveItem()');
                    throw new Error('Test failed with message: ' + err.message);
                }
                done();
            } else {
                let message = 'testListsRemoveItem(): TEST FAILED. No data to be processed. Try again.';
                logger.error(message, 'testListsRemoveItem()');
                throw new Error(message);
            }
        });
    });
});

describe('GET /rest/lists', function testListsFindAll() {
    it('should return a collection of shopping lists whose length is > 0', (done) => {
        request('GET', '/rest/lists', null, (err, data) => {
            if (err) {
                logger.error(`TEST FAILED with message: ${err.message}`, 'testFindAll()');
                return;
            }
            // If not an error, then process the request data
            if (data) {
                try {
                    logger.debug('Raw response data: ' + data, 'testFindAll()');
                    // This request returns JSON data
                    let jsonData = JSON.parse(data);
                    // Assert on enough data that we know it works
                    // If we get back the ID, it worked
                    expect(jsonData.length).to.be.greaterThan(0);
                    logger.debug('TEST PASSED', 'testFindAll()');
                } catch (err) {
                    logger.error(`TEST FAILED. Try again.`, 'testFindAll()');
                    logger.error(`ERROR MESSAGE: ${err.message}.`, 'testFindAll()');
                    throw new Error('Test failed with message: ' + err.message);
                }
                done();
            } else {
                let message = 'testFindAll(): TEST FAILED. No data to be processed. Try again.';
                logger.error(message, 'testFindAll()');
                throw new Error(message);
            }
        });
    });
});


