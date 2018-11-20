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
 * Receive the order having the specified orderId
 * @param {com.makotogo.learn.composer.securegoods.asset.ReceiveOrder} transaction
 * @transaction
 */
async function receiveOrder(transaction) {
    const NSBase = 'com.makotogo.learn.composer.securegoods';
    const NSAsset = NSBase + '.asset';

    console.log('Receive shipment for orderId: ' + transaction.order.id);
    const order = transaction.order;

    const assetRegistry = await getAssetRegistry(NSAsset + '.Order');

    order.status = 'RECEIVED';
    await assetRegistry.update(order);
}