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
 * Query the asset registry and return all Orders for the specified
 * buyer.
 * @param {com.makotogo.learn.composer.securegoods.querytx.FindOrdersForBuyer} querytx - The query transaction.
 * @returns {Order[]} An array of Order records that matched the query
 * @transaction
 */
async function findOrdersForBuyer(querytx) {

    console.log('Querying all Orders for buyer: ' + querytx.buyer);

    let results = await query('FindOrdersForBuyer', { buyer: querytx.buyer });

    console.log('Query returned: ' + results.length + ' items.');

    results.forEach(value => {
        console.log('Query found value: ' + value);
    });

    return results;

}

/**
 * Query the HistorianRegistry and return all records
 * @param {com.makotogo.learn.composer.securegoods.querytx.FindAllHistory} querytx - the query transaction
 * @returns {HistorianRecord[]} An array of all History records
 * @transaction
 */
async function findAllHistory(querytx) {
    console.log('Querying all history...');

    let results = await query('FindAllHistory');

    console.log('Query returned: ' + results.length + ' items.');

    results.forEach(value => {
        console.log('Historian Record: ' + value);
    });

    return results;
}