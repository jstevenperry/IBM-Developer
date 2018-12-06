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

query();

/**
 * The program mainline
 */
async function query() {
    // Catch any exceptions and exit if any are thrown
    try {
        // Get the Composer client API BusinessConnection
        const BusinessNetworkConnection = require('composer-client').BusinessNetworkConnection;
        const bnc = new BusinessNetworkConnection();

        // Get the required parameters and exit if all not present
        const { authIdCard } = checkRequiredParameters();

        // Connect to the business network
        await bnc.connect(authIdCard);

        // The query results
        let results = [];

        console.log('Using transaction to run the query...');
        // Get the factory and create a new transaction
        const factory = bnc.getBusinessNetwork().getFactory();
        const transaction = factory.newTransaction('com.makotogo.learn.composer.securegoods.querytx', 'FindAllHistory');

        // // Submit the transaction
        results = await bnc.submitTransaction(transaction);

        // Process the HistorianRecords
        const historianRecords = processHistorianRecords(results);
        console.log(historianRecords.length + ' transaction record(s) found.');

        // Collect the records we care about
        // Print out the results
        console.log('Transactions (Oldest first):');
        historianRecords.forEach(record => {
            console.log(record.transactionTimestamp + ': ' +
                record.transactionInvoked.getType() + ' (' +
                record.participantInvoking.getIdentifier() + ')'
            );
        });

        // Disconnect so Node can exit
        await bnc.disconnect();
    } catch (err) {
        console.log('Error occurred: ' + err.message + ', Node process exiting.');
        process.exit(1);
    }
}

/**
 * Check for the required parameters.
 * If they are all found, this function returns them in
 * an object. If not, process.exit() is called to bail
 * out, since there's no point in continuing.
 */
function checkRequiredParameters() {
    // The ID card used for authentication
    const authIdCard = process.env.AUTH_ID_CARD;
    if (authIdCard === null) {
        console.log('ID card must be specified, cannot continue!');
        process.exit(1);
    }

    return { authIdCard };
}

/**
 * Process the HistorianRecords:
 * Filter out those with unknown participants
 * Sort them by timestamp (asending)
 */
function processHistorianRecords(results) {

    let historianRecords = [];

    // Process the results
    results.forEach(record => {
        if (record.participantInvoking != null) { //eslint-disable-line eqeqeq
            historianRecords.push(record);
        }
    });

    // Sort them by timestamp
    historianRecords.sort((left, right) => {
        // Uncomment for descending
        // descending sort (latest first)
        // return right.transactionTimestamp - left.transactionTimestamp;
        // ascending sort (earliest first)
        return left.transactionTimestamp - right.transactionTimestamp;
    });

    return historianRecords;

}