/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

'use strict';

// Ignore the specified global functions (or the code won't lint)
/* global getParticipantRegistry getAssetRegistry getFactory emit query */

/**
 * Handle a transaction that returns an array of integers.
 * @param {com.makotogo.learn.composer.securegoods.querytx.FindOrdersForBuyer} querytx - The query transaction.
 * @returns {Order[]} An array of Order records that matched the query
 * @transaction
 */
async function findOrdersForBuyer(querytx) {

    let results = await query('FindOrdersForBuyer', querytx.username);

    results.forEach(value => {
        console.log('Query found value: ' + value);
    });

}