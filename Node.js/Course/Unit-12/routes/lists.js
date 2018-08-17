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
const express = require('express');

// We have no control over stuff like this, so tell eslint to chill
/* eslint new-cap: 0 */
const router = express.Router();

const listsController = require('../controllers/lists-controller');

// Pages
// Page to display all shopping lists
router.get('/', listsController.fetchAll);
// Page to create new shopping list
router.get('/create', listsController.create);
// Page to fetch shopping list by id
router.get('/:listId', listsController.read);
// Page to update shopping list
router.get('/:listId/update', listsController.update);
// Page to search for items
router.get('/:listId/itemSearch', listsController.itemSearch);

// REST Calls
// Call REST service to create new shopping list
router.post('/', listsController.createList);
// Call REST service to update shopping list
router.post('/:listId', listsController.updateList);
// Call REST service to search for Items
router.post('/:listId/itemSearch', listsController.addItemsSearch);
// Call REST service to add an Item to a shopping list
router.post('/:listId/addItems', listsController.addListItems);
// Call REST service to remove items (DELETE does not work for HTML forms)
router.post('/:listId/items/delete', listsController.removeItems);

module.exports = router;
