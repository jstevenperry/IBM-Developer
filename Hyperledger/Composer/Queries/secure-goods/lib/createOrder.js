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

/**
 * Pass the nextId and return the next order ID
 */
function getNextOrderId(nextId) {
    let padZeroes = '0000000';
    return 'ORD' + (padZeroes+nextId).slice(-padZeroes.length);
}

/**
 * Create the specified order.
 * @param {com.makotogo.learn.composer.securegoods.asset.CreateOrder} transaction
 * @transaction
 */
async function createOrder(transaction) {
    const NSAsset = 'com.makotogo.learn.composer.securegoods.asset';

    // Get the next Order id to use
    const orderSequenceRegistry = await getAssetRegistry(NSAsset + '.OrderSequence');
    let orderSequence = await orderSequenceRegistry.get('ORDER_SEQ');
    let nextId = getNextOrderId(orderSequence.nextId);
    orderSequence.nextId++; // Increment

    //
    // Create a new Order asset
    const factory = getFactory();
    const order = factory.newResource(NSAsset, 'Order', nextId);
    //
    // Populate it with the data from the transaction
    order.item = transaction.item;
    order.quantity = transaction.quantity;
    order.unitCost = transaction.unitCost;
    order.buyer = transaction.buyer;
    order.seller = transaction.seller;
    order.shipper = transaction.shipper;
    order.shippingCost = transaction.shippingCost;
    order.status = 'CREATED';

    // Add the order asset to the Asset Registry
    console.log('Placing order for item: ' + transaction.item.id);
    const assetRegistry = await getAssetRegistry(NSAsset + '.Order');
    await assetRegistry.add(order);
    //
    // Update OrderSequence asset
    await orderSequenceRegistry.update(orderSequence);
}