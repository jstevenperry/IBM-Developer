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

const AdminConnection = require('composer-admin').AdminConnection;
const BusinessNetworkConnection = require('composer-client').BusinessNetworkConnection;
const { BusinessNetworkDefinition, CertificateUtil, IdCard } = require('composer-common');
const path = require('path');

require('chai').should();
let sinon = require('sinon');

const namespace = 'org.acme.shipping.perishable';
let grower_id = 'farmer@email.com';
let importer_id = 'supermarket@email.com';

describe('Perishable Shipping Network', () => {
    // In-memory card store for testing so cards are not persisted to the file system
    const cardStore = require('composer-common').NetworkCardStoreManager.getCardStore( { type: 'composer-wallet-inmemory' } );
    let adminConnection;
    let businessNetworkConnection;
    let factory;
    let clock;

    before(async () => {
        // Embedded connection used for local testing
        const connectionProfile = {
            name: 'embedded',
            'x-type': 'embedded'
        };
        // Generate certificates for use with the embedded connection
        const credentials = CertificateUtil.generate({ commonName: 'admin' });

        // PeerAdmin identity used with the admin connection to deploy business networks
        const deployerMetadata = {
            version: 1,
            userName: 'PeerAdmin',
            roles: [ 'PeerAdmin', 'ChannelAdmin' ]
        };
        const deployerCard = new IdCard(deployerMetadata, connectionProfile);
        const deployerCardName = 'PeerAdmin';
        deployerCard.setCredentials(credentials);

        // setup admin connection
        adminConnection = new AdminConnection({ cardStore: cardStore });
        await adminConnection.importCard(deployerCardName, deployerCard);
        await adminConnection.connect(deployerCardName);

        const adminUserName = 'admin';
        const businessNetworkDefinition = await BusinessNetworkDefinition.fromDirectory(path.resolve(__dirname, '..'));

        businessNetworkConnection = new BusinessNetworkConnection({ cardStore: cardStore });

        // Install the Composer runtime for the new business network
        await adminConnection.install(businessNetworkDefinition);

        // Start the business network and configure an network admin identity
        const startOptions = {
            networkAdmins: [
                {
                    userName: adminUserName,
                    enrollmentSecret: 'adminpw'
                }
            ]
        };
        const adminCards = await adminConnection.start(businessNetworkDefinition.getName(), businessNetworkDefinition.getVersion(), startOptions);

        // Import the network admin identity for us to use
        const adminCardName = `${adminUserName}@${businessNetworkDefinition.getName()}`;
        await adminConnection.importCard(adminCardName, adminCards.get(adminUserName));

        // Connect to the business network using the network admin identity
        await businessNetworkConnection.connect(adminCardName);

        factory = businessNetworkConnection.getBusinessNetwork().getFactory();
        const setupDemo = factory.newTransaction(namespace, 'SetupDemo');
        await businessNetworkConnection.submitTransaction(setupDemo);
    });

    beforeEach(() => {
        clock = sinon.useFakeTimers();
    });

    afterEach(function () {
        clock.restore();
    });

    describe('#shipment', () => {

        it('should receive base price for a shipment within temperature range', async () => {
            // submit the temperature reading
            const tempReading = factory.newTransaction(namespace, 'TemperatureReading');
            tempReading.shipment = factory.newRelationship(namespace, 'Shipment', 'SHIP_001');
            tempReading.centigrade = 4.5;
            await businessNetworkConnection.submitTransaction(tempReading);

            // submit the shipment received
            const received = factory.newTransaction(namespace, 'ShipmentReceived');
            received.shipment = factory.newRelationship(namespace, 'Shipment', 'SHIP_001');
            await businessNetworkConnection.submitTransaction(received);

            // check the grower's balance
            const growerRegistry = await businessNetworkConnection.getParticipantRegistry(namespace + '.Grower');
            const newGrower = await growerRegistry.get(grower_id);
            newGrower.accountBalance.should.equal(2500);

            // check the importer's balance
            const importerRegistry = await businessNetworkConnection.getParticipantRegistry(namespace + '.Importer');
            const newImporter = await importerRegistry.get(importer_id);
            newImporter.accountBalance.should.equal(-2500);

            // check the state of the shipment
            const shipmentRegistry = await businessNetworkConnection.getAssetRegistry(namespace + '.Shipment');
            const shipment = await shipmentRegistry.get('SHIP_001');
            shipment.status.should.equal('ARRIVED');
        });

        it('should receive nothing for a late shipment', async () => {
            // submit the temperature reading
            const tempReading = factory.newTransaction(namespace, 'TemperatureReading');
            tempReading.shipment = factory.newRelationship(namespace, 'Shipment', 'SHIP_001');
            tempReading.centigrade = 4.5;
            // advance the javascript clock to create a time-advanced test timestamp
            clock.tick(1000000000000000);
            await businessNetworkConnection.submitTransaction(tempReading);

            // submit the shipment received
            const received = factory.newTransaction(namespace, 'ShipmentReceived');
            received.shipment = factory.newRelationship(namespace, 'Shipment', 'SHIP_001');
            await businessNetworkConnection.submitTransaction(received);

            // check the grower's balance
            const growerRegistry = await businessNetworkConnection.getParticipantRegistry(namespace + '.Grower');
            const newGrower = await growerRegistry.get(grower_id);
            newGrower.accountBalance.should.equal(2500);

            // check the importer's balance
            const importerRegistry = await businessNetworkConnection.getParticipantRegistry(namespace + '.Importer');
            const newImporter = await importerRegistry.get(importer_id);
            newImporter.accountBalance.should.equal(-2500);
        });

        it('should apply penalty for min temperature violation', async () => {
            // submit the temperature reading
            const tempReading = factory.newTransaction(namespace, 'TemperatureReading');
            tempReading.shipment = factory.newRelationship(namespace, 'Shipment', 'SHIP_001');
            tempReading.centigrade = 1;
            await businessNetworkConnection.submitTransaction(tempReading);

            // submit the shipment received
            const received = factory.newTransaction(namespace, 'ShipmentReceived');
            received.shipment = factory.newRelationship(namespace, 'Shipment', 'SHIP_001');
            await businessNetworkConnection.submitTransaction(received);

            // check the grower's balance
            const growerRegistry = await businessNetworkConnection.getParticipantRegistry(namespace + '.Grower');
            const newGrower =  await growerRegistry.get(grower_id);
            newGrower.accountBalance.should.equal(4000);

            // check the importer's balance
            const importerRegistry = await businessNetworkConnection.getParticipantRegistry(namespace + '.Importer');
            const newImporter = await importerRegistry.get(importer_id);
            newImporter.accountBalance.should.equal(-4000);
        });

        it('should apply penalty for max temperature violation', async () => {
            // submit the temperature reading
            const tempReading = factory.newTransaction(namespace, 'TemperatureReading');
            tempReading.shipment = factory.newRelationship(namespace, 'Shipment', 'SHIP_001');
            tempReading.centigrade = 11;
            await businessNetworkConnection.submitTransaction(tempReading);

            // submit the shipment received
            const received = factory.newTransaction(namespace, 'ShipmentReceived');
            received.shipment = factory.newRelationship(namespace, 'Shipment', 'SHIP_001');
            await businessNetworkConnection.submitTransaction(received);

            // check the grower's balance
            const growerRegistry = await businessNetworkConnection.getParticipantRegistry(namespace + '.Grower');
            const newGrower = await growerRegistry.get(grower_id);
            newGrower.accountBalance.should.equal(5000);

            // check the importer's balance
            const importerRegistry = await businessNetworkConnection.getParticipantRegistry(namespace + '.Importer');
            const newImporter = await importerRegistry.get(importer_id);
            newImporter.accountBalance.should.equal(-5000);
        });
    });
});
