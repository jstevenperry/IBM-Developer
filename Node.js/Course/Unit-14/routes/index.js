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

// GET home page.
router.get('/', function(req, res, next) {
    res.redirect('/lists');
});

module.exports = router;
