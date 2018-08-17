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
 * appSettings - all relative to the project root
 */
const appSettings = {
    mongodb_dbpath: '~/home/mongodb-data/db',
    mongodb_url: 'mongodb://localhost:27017',
    mongodb_db_name: 'shoppingList',
    
    brand_file_name: '../data/Grocery_Brands_Database.csv',
    item_file_name: '../data/Grocery_UPC_Database.csv',

    cloudant_db_name: 'shopping_list',
    cloudant_max_items: 200000,
    cloudant_bulk_batch_size: 10000,

    server_host: 'localhost',
    server_listen_port: 3000,
};

module.exports = appSettings;
