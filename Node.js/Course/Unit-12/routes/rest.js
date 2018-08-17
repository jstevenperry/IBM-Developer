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
const router = express.Router();//eslint-disable-line new-cap

// The rest controller that handles the requests
const restController = require('../controllers/rest-controller');

// REST service - fetch all shopping lists
router.get('/lists', restController.fetchAll);
// REST service - create new shopping list
router.post('/lists', restController.create);
// REST service - fetch shopping list by ID
router.get('/lists/:listId', restController.read);
// REST service - update the specified list
router.put('/lists/:listId', restController.update);
// REST service - add an item to the specified shopping list
router.post('/lists/:listId/items', restController.addItem);
// REST service - fetch all items for the specified shopping list
router.get('/lists/:listId/items', restController.fetchAllItems);
// REST service - update the specified item for the specified list
router.put('/lists/:listId/items/:itemId', restController.updateItem);
// REST service - remove the specified item from the specified list
router.delete('/lists/:listId/items/:itemId', restController.removeItem);
// REST service - search for items
router.get('/items', restController.itemSearch);

module.exports = router;
