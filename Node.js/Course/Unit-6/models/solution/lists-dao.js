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
 * This is the DAO interface for the application.
 * You will need to provide an implementation for each
 * function in the interface. The implementation has been
 * provided for you in the appropriately named *sqlite3.js
 * file located in this directory.
 */

// TODO: figure out what "require"ments you need here
const listsDaoImpl = require('./lists-dao-sqlite3');

/**
 * Create a shopping list with the specified description
 */
function create(description) {
    return listsDaoImpl.create(description);
}

/**
 * Find the shopping list with the specified id
 */
function findById(id) {
    return listsDaoImpl.findById(id);
}

/**
 * Find the shopping list with the specified id
 * and return all items associated with it
 */
function findByIdWithAllItems(id) {
    return listsDaoImpl.findByIdWithAllItems(id);
}

/**
 * Update the shopping list with the specified id
 * with new field values
 */
function update(id, description) {
    return listsDaoImpl.update(id, description);
}

/**
 * Add the specified item to the specified shopping
 * list, along with values for the relationship
 */
function addItem(listId, itemId, quantity) {
    return listsDaoImpl.addItem(listId, itemId, quantity);
}

/**
 * Update the specified item in the specified shopping
 * list, along with values for the relationship
 */
function updateItem(listId, itemId, quantity, pickedUp) {
    return listsDaoImpl.updateItem(listId, itemId, quantity, pickedUp);
}

/**
 * Remove the specified item from the specified shopping
 * list
 */
function removeItem(listId, itemId) {
    return listsDaoImpl.removeItem(listId, itemId);
}

module.exports.create = create;
module.exports.findById = findById;
module.exports.findByIdWithAllItems = findByIdWithAllItems;
module.exports.update = update;
module.exports.addItem = addItem;
module.exports.updateItem = updateItem;
module.exports.removeItem = removeItem;