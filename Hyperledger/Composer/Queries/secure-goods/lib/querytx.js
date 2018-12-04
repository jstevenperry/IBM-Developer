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
 * Query the asset registry and return all Orders for the specified buyer.
 * @param {com.makotogo.learn.composer.securegoods.querytx.FindOrdersForBuyer} querytx - The query transaction.
 * @returns {Order[]} An array of Order records that matched the query
 * @transaction
 */
async function findOrdersForBuyer(querytx) {
    //
    // Execute query and return the results
    return await query('FindOrdersForBuyerQuery', { buyerResource: querytx.buyerResource });
}

/**
 * Query the asset registry and return all Orders for the specified seller.
 * @param {com.makotogo.learn.composer.securegoods.querytx.FindOrdersForSeller} querytx - The query transaction.
 * @returns {Order[]} An array of Order records that matched the query
 * @transaction
 */
async function findOrdersForSeller(querytx) {
    //
    // Execute query and return the results
    return await query('FindOrdersForSellerQuery', { sellerResource: querytx.sellerResource });
}

/**
 * Query the asset registry and return all Orders for the specified shipper.
 * @param {com.makotogo.learn.composer.securegoods.querytx.FindOrdersForShipper} querytx - The query transaction.
 * @returns {Order[]} An array of Order records that matched the query
 * @transaction
 */
async function findOrdersForShipper(querytx) {
    //
    // Execute query and return the results
    return await query('FindOrdersForShipperQuery', { shipperResource: querytx.shipperResource });
}

/**
 * Query the HistorianRegistry and return all records
 * @param {com.makotogo.learn.composer.securegoods.querytx.FindAllHistory} querytx - the query transaction
 * @returns {HistorianRecord[]} An array of all History records
 * @transaction
 */
async function findAllHistory(querytx) {
    //
    // Execute query and return the results
    return await query('FindAllHistoryQuery');
}