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

// Ignore the specified global functions (or the code won't lint)
/* global getParticipantRegistry getAssetRegistry getFactory emit */

// Namespaces
const NSBase = "com.makotogo.learn.composer.securegoods";
const NSAsset = NSBase + ".asset";
const NSParticipant = NSBase + ".participant";

/**
 * Load the Asset and Participant registries with data
 * 
 * @param {com.makotogo.learn.composer.securegoods.transaction.LoadRegistries} tx - the transaction object
 * @transaction
 */
async function loadRegistry(tx) {
    //
    // Create sellers
    createSellers();

    //
    // Create Buyers
    createBuyers();

    //
    // Create Shippers
    createShippers();
}

/**
 * Create Sellers and add them to the Participant registry
 */
async function createSellers() {
    console.log('createSellers(): HELLO from ' + NSParticipant);
    //
    // Get a reference to the Sellers in the participant registry
    const sellerRegistry = await getParticipantRegistry(NSParticipant + '.Seller');
    //
    // Create new Seller instances
    let sellers = []
    const factory = getFactory();
    let seller = factory.newResource(NSParticipant, 'Seller', 'SELL001');
    seller.name = 'Selljestic';
    sellers.push(seller);
    //
    seller = factory.newResource(NSParticipant, 'Seller', 'SELL002');
    seller.name = 'Selltabulous';
    sellers.push(seller);
    //
    // Add the new Seller instances to the registry
    await sellerRegistry.addAll(sellers);
}

/**
 * Create Buyers and add them to the Participant registry
 */
async function createBuyers() {
    console.log('createBuyers(): HELLO from ' + NSParticipant);
    //
    // Get a reference to the Buyers in the participant registry
    const buyerRegistry = await getParticipantRegistry(NSParticipant + '.Buyer');
    //
    // Create new Buyer instances
    let buyers = []
    const factory = getFactory();
    let buyer = factory.newResource(NSParticipant, 'Buyer', 'BUY001');
    buyer.name = 'Buytastic';
    buyers.push(buyer);
    //
    buyer = factory.newResource(NSParticipant, 'Buyer', 'BUY002');
    buyer.name = 'Buycorp';
    buyers.push(buyer);
    //
    // Add the new Buyer instances to the registry
    await buyerRegistry.addAll(buyers);
}

/**
 * Create Shippers and add them to the Participant registry
 */
async function createShippers() {
    console.log('createShippers(): HELLO from ' + NSParticipant);
    //
    // Get a reference to the Shippers in the participant registry
    const shipperRegistry = await getParticipantRegistry(NSParticipant + '.Shipper');
    //
    // Create new Buyer instances
    let shippers = []
    const factory = getFactory();
    let shipper = factory.newResource(NSParticipant, 'Shipper', 'SHIP001');
    shipper.name = 'Shipmagic';
    shippers.push(shipper);
    //
    shipper = factory.newResource(NSParticipant, 'Shipper', 'SHIP002');
    shipper.name = 'Shipinc';
    shippers.push(shipper);
    //
    // Add the new Buyer instances to the registry
    await shipperRegistry.addAll(shippers);
}