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

/* global getParticipantRegistry getAssetRegistry getFactory emit */

/**
 * A shipment has been received by an importer
 * @param {org.acme.shipping.perishable.ShipmentReceived} shipmentReceived - the ShipmentReceived transaction
 * @transaction
 */
async function receiveShipment(shipmentReceived) {  // eslint-disable-line no-unused-vars

    const contract = shipmentReceived.shipment.contract;
    const shipment = shipmentReceived.shipment;
    let payOut = contract.unitPrice * shipment.unitCount;

    console.log('Received at: ' + shipmentReceived.timestamp);
    console.log('Contract arrivalDateTime: ' + contract.arrivalDateTime);

    // set the status of the shipment
    shipment.status = 'ARRIVED';

    // if the shipment did not arrive on time the payout is zero
    if (shipmentReceived.timestamp > contract.arrivalDateTime) {
        payOut = 0;
        console.log('Late shipment');
    } else {
        // find the lowest temperature reading
        if (shipment.temperatureReadings) {
            // sort the temperatureReadings by centigrade
            shipment.temperatureReadings.sort(function (a, b) {
                return (a.centigrade - b.centigrade);
            });
            const lowestReading = shipment.temperatureReadings[0];
            const highestReading = shipment.temperatureReadings[shipment.temperatureReadings.length - 1];
            let penalty = 0;
            console.log('Lowest temp reading: ' + lowestReading.centigrade);
            console.log('Highest temp reading: ' + highestReading.centigrade);

            // does the lowest temperature violate the contract?
            if (lowestReading.centigrade < contract.minTemperature) {
                penalty += (contract.minTemperature - lowestReading.centigrade) * contract.minPenaltyFactor;
                console.log('Min temp penalty: ' + penalty);
            }

            // does the highest temperature violate the contract?
            if (highestReading.centigrade > contract.maxTemperature) {
                penalty += (highestReading.centigrade - contract.maxTemperature) * contract.maxPenaltyFactor;
                console.log('Max temp penalty: ' + penalty);
            }

            // apply any penalities
            payOut -= (penalty * shipment.unitCount);

            if (payOut < 0) {
                payOut = 0;
            }
        }
    }

    console.log('Payout: ' + payOut);
    contract.grower.accountBalance += payOut;
    contract.importer.accountBalance -= payOut;

    console.log('Grower: ' + contract.grower.$identifier + ' new balance: ' + contract.grower.accountBalance);
    console.log('Importer: ' + contract.importer.$identifier + ' new balance: ' + contract.importer.accountBalance);

    var NS = 'org.acme.shipping.perishable';
    // Store the ShipmentReceived transaction with the Shipment asset it belongs to
    shipment.shipmentReceived = shipmentReceived;

    var factory = getFactory();
    var shipmentReceivedEvent = factory.newEvent(NS, 'ShipmentReceivedEvent');
    var message = 'Shipment ' + shipment.$identifier + ' received';
    console.log(message);
    shipmentReceivedEvent.message = message;
    shipmentReceivedEvent.shipment = shipment;
    emit(shipmentReceivedEvent);

    // update the grower's balance
    const growerRegistry = await getParticipantRegistry('org.acme.shipping.perishable.Grower');
    await growerRegistry.update(contract.grower);

    // update the importer's balance
    const importerRegistry = await getParticipantRegistry('org.acme.shipping.perishable.Importer');
    await importerRegistry.update(contract.importer);

    // update the state of the shipment
    const shipmentRegistry = await getAssetRegistry('org.acme.shipping.perishable.Shipment');
    await shipmentRegistry.update(shipment);
}

/**
 * A temperature reading has been received for a shipment
 * @param {org.acme.shipping.perishable.TemperatureReading} temperatureReading - the TemperatureReading transaction
 * @transaction
 */
async function temperatureReading(temperatureReading) {  // eslint-disable-line no-unused-vars

    const NS = 'org.acme.shipping.perishable';
    const shipment = temperatureReading.shipment;
    const contract = shipment.contract;
    const factory = getFactory();

    console.log('Adding temperature ' + temperatureReading.centigrade + ' to shipment ' + shipment.$identifier);

    if (shipment.temperatureReadings) {
        shipment.temperatureReadings.push(temperatureReading);
    } else {
        shipment.temperatureReadings = [temperatureReading];
    }

    if (temperatureReading.centigrade < contract.minTemperature ||
        temperatureReading.centigrade > contract.maxTemperature) {
        var temperatureEvent = factory.newEvent(NS, 'TemperatureThresholdEvent');
        temperatureEvent.shipment = shipment;
        temperatureEvent.temperature = temperatureReading.centigrade;
        temperatureEvent.message = 'Temperature threshold violated! Emitting TemperatureEvent for shipment: ' + shipment.$identifier;
        console.log(temperatureEvent.message);
        emit(temperatureEvent);
    }

    // add the temp reading to the shipment
    const shipmentRegistry = await getAssetRegistry(NS + '.Shipment');
    await shipmentRegistry.update(shipment);
}

/**
 * A GPS reading has been received for a shipment
 * @param {org.acme.shipping.perishable.GpsReading} gpsReading - the GpsReading transaction
 * @transaction
 */
async function gpsReading(gpsReading) {  // eslint-disable-line no-unused-vars

    var factory = getFactory();
    var NS = 'org.acme.shipping.perishable';
    var shipment = gpsReading.shipment;
    var PORT_OF_NEW_YORK = '/LAT:40.6840N/LONG:74.0062W';

    var latLong = '/LAT:' + gpsReading.latitude + gpsReading.latitudeDir + '/LONG:' +
        gpsReading.longitude + gpsReading.longitudeDir;

    if (shipment.gpsReadings) {
        shipment.gpsReadings.push(gpsReading);
    } else {
        shipment.gpsReadings = [gpsReading];
    }

    if (latLong === PORT_OF_NEW_YORK) {
        var shipmentInPortEvent = factory.newEvent(NS, 'ShipmentInPortEvent');
        shipmentInPortEvent.shipment = shipment;
        var message = 'Shipment has reached the destination port of ' + PORT_OF_NEW_YORK;
        shipmentInPortEvent.message = message;
        console.log(message);
        emit(shipmentInPortEvent);
    }

    const shipmentRegistry = await getAssetRegistry(NS + '.Shipment');
    await shipmentRegistry.update(shipment);
}

/**
 * ShipmentPacked transaction - invoked when the Shipment is packed and ready for pickup.
 *
 * @param {org.acme.shipping.perishable.ShipmentPacked} shipmentPacked - the ShipmentPacked transaction
 * @transaction
 */
async function packShipment(shipmentPacked) {  // eslint-disable-line no-unused-vars
    var shipment = shipmentPacked.shipment;
    var NS = 'org.acme.shipping.perishable';
    var factory = getFactory();

    // Add the ShipmentPacked transaction to the ledger (via the Shipment asset)
    shipment.shipmentPacked = shipmentPacked;

    // Create the message
    var message = 'Shipment packed for shipment ' + shipment.$identifier;

    // Log it to the JavaScript console
    //console.log(message);

    // Emit a notification telling subscribed listeners that the shipment has been packed
    var shipmentPackedEvent = factory.newEvent(NS, 'ShipmentPackedEvent');
    shipmentPackedEvent.shipment = shipment;
    shipmentPackedEvent.message = message;
    emit(shipmentPackedEvent);

    // Update the Asset Registry
    const shipmentRegistry = await getAssetRegistry(NS + '.Shipment');
    await shipmentRegistry.update(shipment);
}

/**
 * ShipmentPickup - invoked when the Shipment has been picked up from the packer.
 *
 * @param {org.acme.shipping.perishable.ShipmentPickup} shipmentPickup - the ShipmentPickup transaction
 * @transaction
 */
async function pickupShipment(shipmentPickup) {  // eslint-disable-line no-unused-vars
    var shipment = shipmentPickup.shipment;
    var NS = 'org.acme.shipping.perishable';
    var factory = getFactory();

    // Add the ShipmentPacked transaction to the ledger (via the Shipment asset)
    shipment.shipmentPickup = shipmentPickup;

    // Create the message
    var message = 'Shipment picked up for shipment ' + shipment.$identifier;

    // Log it to the JavaScript console
    //console.log(message);

    // Emit a notification telling subscribed listeners that the shipment has been packed
    var shipmentPickupEvent = factory.newEvent(NS, 'ShipmentPickupEvent');
    shipmentPickupEvent.shipment = shipment;
    shipmentPickupEvent.message = message;
    emit(shipmentPickupEvent);

    // Update the Asset Registry
    const shipmentRegistry = await getAssetRegistry(NS + '.Shipment');
    await shipmentRegistry.update(shipment);
}

/**
 * ShipmentLoaded - invoked when the Shipment has been loaded onto the container ship.
 *
 * @param {org.acme.shipping.perishable.ShipmentLoaded} shipmentLoaded - the ShipmentLoaded transaction
 * @transaction
 */
async function loadShipment(shipmentLoaded) { // eslint-disable-line no-unused-vars
    var shipment = shipmentLoaded.shipment;
    var NS = 'org.acme.shipping.perishable';
    var factory = getFactory();

    // Add the ShipmentPacked transaction to the ledger (via the Shipment asset)
    shipment.shipmentLoaded = shipmentLoaded;

    // Create the message
    var message = 'Shipment loaded for shipment ' + shipment.$identifier;

    // Log it to the JavaScript console
    //console.log(message);

    // Emit a notification telling subscribed listeners that the shipment has been packed
    var shipmentLoadedEvent = factory.newEvent(NS, 'ShipmentLoadedEvent');
    shipmentLoadedEvent.shipment = shipment;
    shipmentLoadedEvent.message = message;
    emit(shipmentLoadedEvent);

    // Update the Asset Registry
    const shipmentRegistry = await getAssetRegistry(NS + '.Shipment');
    await shipmentRegistry.update(shipment);
}

/**
 * Initialize some test assets and participants useful for running a demo.
 * @param {org.acme.shipping.perishable.SetupDemo} setupDemo - the SetupDemo transaction
 * @transaction
 */
async function setupDemo(setupDemo) {  // eslint-disable-line no-unused-vars

    const factory = getFactory();
    const NS = 'org.acme.shipping.perishable';

    // create the grower
    const grower = factory.newResource(NS, 'Grower', 'farmer@email.com');
    const growerAddress = factory.newConcept(NS, 'Address');
    growerAddress.country = 'USA';
    grower.address = growerAddress;
    grower.accountBalance = 0;

    // create the importer
    const importer = factory.newResource(NS, 'Importer', 'supermarket@email.com');
    const importerAddress = factory.newConcept(NS, 'Address');
    importerAddress.country = 'UK';
    importer.address = importerAddress;
    importer.accountBalance = 0;

    // create the shipper
    const shipper = factory.newResource(NS, 'Shipper', 'shipper@email.com');
    const shipperAddress = factory.newConcept(NS, 'Address');
    shipperAddress.country = 'Panama';
    shipper.address = shipperAddress;
    shipper.accountBalance = 0;

    // create the contract
    const contract = factory.newResource(NS, 'Contract', 'CON_001');
    contract.grower = factory.newRelationship(NS, 'Grower', 'farmer@email.com');
    contract.importer = factory.newRelationship(NS, 'Importer', 'supermarket@email.com');
    contract.shipper = factory.newRelationship(NS, 'Shipper', 'shipper@email.com');
    const tomorrow = setupDemo.timestamp;
    tomorrow.setDate(tomorrow.getDate() + 1);
    contract.arrivalDateTime = tomorrow; // the shipment has to arrive tomorrow
    contract.unitPrice = 0.5; // pay 50 cents per unit
    contract.minTemperature = 2; // min temperature for the cargo
    contract.maxTemperature = 10; // max temperature for the cargo
    contract.minPenaltyFactor = 0.2; // we reduce the price by 20 cents for every degree below the min temp
    contract.maxPenaltyFactor = 0.1; // we reduce the price by 10 cents for every degree above the max temp

    // create the shipment
    const shipment = factory.newResource(NS, 'Shipment', 'SHIP_001');
    shipment.type = 'BANANAS';
    shipment.status = 'IN_TRANSIT';
    shipment.unitCount = 5000;
    shipment.contract = factory.newRelationship(NS, 'Contract', 'CON_001');

    // create the Temperature sensor
    var temperatureSensor = factory.newResource(NS, 'TemperatureSensor', 'SENSOR_TEMP001');

    // create the GPS sensor
    var gpsSensor = factory.newResource(NS, 'GpsSensor', 'SENSOR_GPS001');

    // add the growers
    const growerRegistry = await getParticipantRegistry(NS + '.Grower');
    await growerRegistry.addAll([grower]);

    // add the importers
    const importerRegistry = await getParticipantRegistry(NS + '.Importer');
    await importerRegistry.addAll([importer]);

    // add the shippers
    const shipperRegistry = await getParticipantRegistry(NS + '.Shipper');
    await shipperRegistry.addAll([shipper]);

    // add the temperature sensor
    const temperatureSensorRegistry = await getParticipantRegistry(NS + '.TemperatureSensor');
    await temperatureSensorRegistry.addAll([temperatureSensor]);

    // add the GPS sensor
    const gpsSensorRegistry = await getParticipantRegistry(NS + '.GpsSensor');
    await gpsSensorRegistry.addAll([gpsSensor]);

    // add the contracts
    const contractRegistry = await getAssetRegistry(NS + '.Contract');
    await contractRegistry.addAll([contract]);

    // add the shipments
    const shipmentRegistry = await getAssetRegistry(NS + '.Shipment');
    await shipmentRegistry.addAll([shipment]);
}