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
 * This is the DAO interface for the application.
 * You will need to provide an implementation for each
 * function in the interface. The implementation has been
 * provided for you in the appropriately named *sqlite3.js
 * file located in this directory.
 */

const itemsDaoImpl = require('./items-dao-sqlite3');

/**
 * Find the Item object by the specified ID
 * using the underlying implementation.
 * 
 * @param {Number} id - the ID of the item record (SQL) or document (NoSQL)
 * to locate
 * 
 * @return {Promise} Promise - 
 *  resolve(): the Item object that matches the id
 *          or null if one could not be located for that id 
 *  reject(): the err object from the underlying data store
 */
function findById(id) {
    return itemsDaoImpl.findById(id);
}

/**
 * Find all Items objects that match the specified
 * partial description.
 * 
 * @param {String} partialDescription
 * 
 * @return {Promise} Promise - 
 *  resolve(): all Item objects that contain the partial
 *          descriptin provided or an empty array if nothing
 *          could not be located for that partialDescription 
 *  reject(): the err object from the underlying data store
 */
function findByDescription(partialDescription) {
    return itemsDaoImpl.findByDescription(partialDescription);
}

/**
 * Find the Item object that matches the specified
 * UPC exactly.
 * 
 * @param {String} upc - the UPC of the item record (SQL) or document (NoSQL)
 * to locate
 * 
 * @return {Promise} Promise - 
 *  resolve(): the Item object that matches the UPC symbol
 *          or null if one could not be located for that UPC
 *  reject()): the err object from the underlying data store.
 */
function findByUpc(upc) {
    return itemsDaoImpl.findByUpc(upc);
}

module.exports.findById = findById;
module.exports.findByDescription = findByDescription;
module.exports.findByUpc = findByUpc;
