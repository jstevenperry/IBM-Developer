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

const logger = require('../utils/logger');

const itemsDao = require('../models/items-dao');

 /**
  * Handle (that is, resolve() or reject()) request for items search
  * (e.g., /items?upc=123456789012)
  * 
  * @param request - the original request
  * @param resolve - the resolve() function to the promise: 
  *     { data : the_result_json_data, 
  *       statusCode = http_status_code }
  * @param reject - the reject() function of the promise
  * @param parsedUrl - the parsed url from the caller
  */
 function handleItemsSearch(request, resolve, reject, parsedUrl) {
    // Now get the query string from the URL
    let query = parsedUrl.query;
    // By description?
    if (query.description) {
        // Node developer: use this as a template for the other DAO calls
        logger.debug(`Query by description: ${query.description}`, 'handleItemsSearch()');
        // Query DAO: 
        itemsDao.findByDescription(query.description).then((result) => {
            resolve(result);// ok if query results in no data
        }).catch((err) => {
            reject(err);
        });
    // By upc?
    } else if (query.upc) {
        logger.debug(`Query by UPC: ${query.upc}`, 'handleItemsSearch()');
        // Query DAO: 
        itemsDao.findByUpc(query.upc).then((result) => {
            resolve(result);// exact match or not found
        }).catch((err) => {
            reject(err);
        });
    // By id?
    } else if (query.id) {
        logger.debug(`Query by ID: ${query.id}`, 'handleItemsSearch()');
        itemsDao.findById(query.id).then((result) => {
            resolve(result);// exact match or not found
        }).catch((err) => {
            reject(err);
        });
    } else {
        let message = `Unsupported search param: ${parsedUrl.search}`;
        logger.error(message, 'handleItemsSearch()');
        reject(message);
    }
}

module.exports.handleItemsSearch = handleItemsSearch;