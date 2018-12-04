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

mainline();

/**
 * The program mainline
 */
async function mainline() {

    const BusinessNetworkConnection = require('composer-client').BusinessNetworkConnection;
    const bnc = new BusinessNetworkConnection();

    const authIdCard = process.env.AUTH_ID_CARD;
    if (authIdCard === null) {
        console.log('ID card must be specified, cannot continue!');
        process.exit(1);
    }

    const buyerId = process.env.BUYER_ID;
    if (buyerId === null) {
        console.log('Buyer ID must be specified, cannot continue!');
        process.exit(1);
    }

    // Connect (do not need return value, I assume?)
    await bnc.connect(authIdCard);

    // Get the factory
    const factory = bnc.getBusinessNetwork().getFactory();

    // Create a new transaction
    const transaction = factory.newTransaction('com.makotogo.learn.composer.securegoods.querytx', 'FindOrdersForBuyer');
    transaction.buyer = 'resource:com.makotogo.learn.composer.securegoods.participant.Buyer#' + buyerId;

    // Submit the transaction
    const results = await bnc.submitTransaction(transaction);

    // Process the results
    results.forEach(value => {
        console.log('Value: ' + value);
    });

    // Disconnect so Node can exit
    await bnc.disconnect();
}
