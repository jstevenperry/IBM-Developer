# IoT Perishable Goods Network
Example business network that shows growers, shippers and importers defining contracts for the price of perishable goods, based on temperature readings from IoT sensors in the shipping containers.

The business network defines a contract between growers and importers. The contract stipulates that: On receipt of the shipment the importer pays the grower the unit price x the number of units in the shipment. Shipments that arrive late are free. Shipments that have breached the low temperate threshold have a penalty applied proportional to the magnitude of the breach x a penalty factor. Shipments that have breached the high temperate threshold have a penalty applied proportional to the magnitude of the breach x a penalty factor.

The order of events in this scenario is something like this:

The shipment must first be packed by the Grower, and once that occurs, the Grower submits the ``ShipmentPacked`` transaction to update the ledger. The transaction chaincode also broadcasts a ``ShipmentPackedEvent`` letting interested parties know the Shipment is packed and ready for pickup.

The Shipper picks up the Shipment from the Grower and submits the ``ShipmentPickup`` transaction to update the ledger. The transaction chaincode also broadcasts a ``ShipmentPickupEvent`` letting interested parties know the Shipment has been picked up and is on its way to be loaded for transport.

The Shipper loads the Shipment onto the container ship and submits the ``ShipmentLoaded`` transaction to uploade the ledger. The transaction chaincode also broadcasts a ``ShipmentLoaded`` letting interested parties know the Shipment has been loaded onto the container ship.

Along the way, IoT sensors in the cargo container update the blockchain with information about the Shipment

* Temperature Readings - submitted by ``TemperatureSensor`` Participant via ``TemperatureReading`` transactions as the container travels to its destination
* GPS Readings - submitted by ``GpsSensor`` Participant via ``GpsReading`` transactions as the container travels to its destination

During transport, if the chaincode for the ``TemperatureReading`` transaction detects that the agreed-upon temperature boundaries are breached during transport, a ``TemperatureThresholdEvent`` is broadcast to interested parties.

When the container ship reaches its destination (Port of New Jersey/New York) a ``ShipmentInPortEvent`` is broadcast to interested parties.

When the shipment has been received by the Importer a ``ShipmentReceived`` event is broadcast to interested parties.

This business network defines:

**Participants**
`Grower` `Importer` `Shipper` `IoTDevice` `TemperatureSensor` `GpsSensor`

**Assets**
`Contract` `Shipment`

**Transactions**
`ShipmentPacked` `ShipmentPickup` `ShipmentLoaded` `TemperatureReading` `GpsReading` `ShipmentReceived` `SetupDemo`

**Events**
`ShipmentPackedEvent` `ShipmentPickupEvent` `ShipmentLoadedEvent` `TemperatureThresholdEvent` `ShipmentInPortEvent` `ShipmentReceivedEvent`

Submit a `ShipmentPacked` transaction:

```
{
  "$class"; "org.acme.shipping.perishable.ShipmentPacked",
  "shipment": "resource:org.acme.shipping.perishable.Shipment#SHIP_001"
}
```

Submit a `ShipmentPickup` transaction:

```
{
  "$class"; "org.acme.shipping.perishable.ShipmentPickup",
  "shipment": "resource:org.acme.shipping.perishable.Shipment#SHIP_001"
}
```

Submit a `ShipmentLoaded` transaction:

```
{
  "$class"; "org.acme.shipping.perishable.ShipmentLoaded",
  "shipment": "resource:org.acme.shipping.perishable.Shipment#SHIP_001"
}
```

Submit a `TemperatureReading` transaction:

```
{
  "$class": "org.acme.shipping.perishable.TemperatureReading",
  "centigrade": 8,
  "shipment": "resource:org.acme.shipping.perishable.Shipment#SHIP_001"
}
```

If the temperature reading falls outside the min/max range of the contract, the price received by the grower will be reduced. You may submit several readings if you wish. Each reading will be aggregated within `SHIP_001` Shipment Asset Registry.

If the date-time of the `ShipmentReceived` transaction is after the `arrivalDateTime` on `CON_001` then the grower will no receive any payment for the shipment.

Submit a `GpsReading` transaction:

```
{
  "$class": "org.acme.shipping.perishable.GpsReading",
  "readingTime": "120000",
  "readingDate": "20171024",
  "latitude":"40.6840",
  "latitudeDir":"N",
  "longitude":"74.0062",
  "laongitudeDir":"W",
}
```

Submit a `ShipmentReceived` transaction for `SHIP_001` to trigger the payout to the grower, based on the parameters of the `CON_001` contract:

```
{
  "$class": "org.acme.shipping.perishable.ShipmentReceived",
  "shipment": "resource:org.acme.shipping.perishable.Shipment#SHIP_001"
}
```

To test this Business Network Definition in the **Test** tab:

Submit a `SetupDemo` transaction:

```
{
  "$class": "org.acme.shipping.perishable.SetupDemo"
}
```

This transaction populates the Participant Registries with a `Grower`, an `Importer` and a `Shipper`. The Asset Registries will have a `Contract` asset and a `Shipment` asset.

## License <a name="license"></a>
Hyperledger Project source code files are made available under the Apache License, Version 2.0 (Apache-2.0), located in the LICENSE file. Hyperledger Project documentation files are made available under the Creative Commons Attribution 4.0 International License (CC-BY-4.0), available at http://creativecommons.org/licenses/by/4.0/.
