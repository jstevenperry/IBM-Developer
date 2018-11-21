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
/* global getParticipantRegistry getAssetRegistry getFactory emit */

//
// This would normally be in the DB somewhere
var orderSequence = 100;

/**
 * Function to imitate a sequence generator (normally part
 * of the implementation in, say, the relational DB)
 */
function getNextId() {
    let padZeroes = '0000000';
    let ret = 'ORD' + (padZeroes+orderSequence).slice(-padZeroes.length);
    orderSequence++;
    return ret;
}

/**
 * Place the specified order.
 * @param {com.makotogo.learn.composer.securegoods.asset.PlaceOrder} transaction
 * @transaction
 */
async function placeOrder(transaction) {
    const NSAsset = 'com.makotogo.learn.composer.securegoods.asset';

    //
    // Create a new Order asset
    const factory = getFactory();
    const order = factory.newResource(NSAsset, 'Order', getNextId());
    //
    // Populate it with the data from the transaction
    order.item = transaction.item;
    order.quantity = transaction.quantity;
    order.unitCost = transaction.unitCost;
    order.buyer = transaction.buyer;
    order.seller = transaction.seller;
    order.shipper = transaction.shipper;
    order.shippingCost = transaction.shippingCost;
    order.status = 'PLACED';

    // Add the order asset to the Asset Registry
    const assetRegistry = await getAssetRegistry(NSAsset + '.Order');
    console.log('Placing order for item: ' + transaction.item.id);
    await assetRegistry.add(order);

    // Emit OrderPlaced event
    let message = 'Order ' + order.id + ' placed.';
    const event = factory.newEvent(NSAsset, 'OrderPlaced');
    event.message = message;
    event.order = order;
    emit(event);
}