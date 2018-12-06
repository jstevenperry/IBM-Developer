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
/* global getParticipantRegistry getAssetRegistry getFactory emit query buildQuery */

/**
 * Transactions for queries. They don't all have to be in this file, the logic
 * is compact, and so it was convenient to implement it that way.
 */

/**
 * Query the asset registry and return all Orders for the specified buyer.
 * @param {com.makotogo.learn.composer.securegoods.querytx.FindOrdersForBuyer} querytx - The query transaction.
 * @returns {Order[]} An array of Order records that matched the query
 * @transaction
 */
async function findOrdersForBuyer(querytx) {
    //
    // Execute query and return the results
    return await query('FindOrdersForBuyerQuery', { buyerResource: 'resource:com.makotogo.learn.composer.securegoods.participant.Buyer#' + querytx.buyerResource });
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
    return await query('FindOrdersForSellerQuery', { sellerResource: 'resource:com.makotogo.learn.composer.securegoods.participant.Seller#' + querytx.sellerResource });
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
    return await query('FindOrdersForShipperQuery', { shipperResource: 'resource:com.makotogo.learn.composer.securegoods.participant.Shipper#' + querytx.shipperId });
}

/**
 * Query the asset registry and return all Orders that contain the specified Item.
 * @param {com.makotogo.learn.composer.securegoods.querytx.FindOrdersByItem} querytx - the query transaction.
 * @returns {Order[]}
 * @transaction
 */
async function findOrdersByItem(querytx) {
    //
    // Execute a dynamic query:
    // Put together the Composer QL
    const cql = 'SELECT com.makotogo.learn.composer.securegoods.asset.Order WHERE (item == _$itemResource)';
    const builtQuery = buildQuery(cql);
    // Execute the query
    const results = await query(builtQuery, { itemResource: 'resource:com.makotogo.learn.composer.securegoods.asset.Item#' + querytx.itemId });
    console.log('Query returned ' + results.length + ' results.');
    // Return the results
    return results;
}

/**
 * Query the asset registry and return all Orders that contain the specified Item.
 * @param {com.makotogo.learn.composer.securegoods.querytx.FindOrdersByCurrencyCode} querytx - the query transaction.
 * @returns {Order[]}
 * @transaction
 */
async function findOrdersByCurrencyCode(querytx) {
    //
    // Execute a dynamic query:
    // Put together the Composer QL
    const cql = 'SELECT com.makotogo.learn.composer.securegoods.asset.Order WHERE (unitCost.currency == _$currencyCode OR shippingCost.currency == _$currencyCode)';
    const builtQuery = buildQuery(cql);
    // Execute the query
    const results = await query(builtQuery, { currencyCode: querytx.currencyCode });
    console.log('Query returned ' + results.length + ' results.');
    // Return the results
    return results;
}

/**
 * Query the HistorianRegistry and return all records
 * @param {com.makotogo.learn.composer.securegoods.querytx.FindAllHistory} querytx - the query transaction
 * @returns {HistorianRecord[]} An array of all History records
 * @transaction
 */
async function findAllHistory(querytx) {
    const results = await findAllHistoryQuery();
    return results;
}

/**
 * Helper function. Shared code. You know the drill.
 */
async function findAllHistoryQuery() {
    //
    // Execute a dynamic query:
    // Put together the Composer QL
    const cql = 'SELECT org.hyperledger.composer.system.HistorianRecord';
    const builtQuery = buildQuery(cql);
    // Execute the query
    const results = await query(builtQuery);
    console.log('Query returned ' + results.length + ' results.');
    // Return the results
    return results;
}

/**
 * Query the HistorianRegistry and return only the Order records
 * @param {com.makotogo.learn.composer.securegoods.querytx.FindOrderHistory} querytx - the query transaction
 * @returns {HistorianRecord[]} An array of all History records
 * @transaction
 */
async function findOrderHistory(querytx) {
    const ret = [];
    //
    // Execute query and return the results
    const allHistory = await findAllHistoryQuery();
    //
    // Filter through the results and keep only the
    // HistorianRecord's that contain an Order
    allHistory.forEach(history => {
        // Only keep an HistorianRecord if it contains
        // an Order
        if (history.transactionInvoked.getNamespace().includes('com.makotogo.learn.composer.securegoods')) {
            ret.push(history);
        }
    });
    // Return to caller
    return ret;
}

