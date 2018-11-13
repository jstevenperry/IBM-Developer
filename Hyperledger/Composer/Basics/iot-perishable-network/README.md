# IoT Perishable Goods Network

> Example business network that shows growers, shippers and importers defining contracts for the price of perishable goods, based on temperature readings from IoT sensors in the shipping containers.

The business network defines a contract between growers and importers. The contract stipulates that: On receipt of the shipment the importer pays the grower the unit price x the number of units in the shipment. Shipments that arrive late are free. Shipments that have breached the low temperate threshold have a penalty applied proportional to the magnitude of the breach x a penalty factor. Shipments that have breached the high temperate threshold have a penalty applied proportional to the magnitude of the breach x a penalty factor.

This business network defines:

**Participants**
`Grower` `Importer` `Shipper`

**Assets**
`Contract` `Shipment`

**Transactions**
`TemperatureReading` `GpsReading` `ShipmentReceived` `SetupDemo`

**Events**
`TemperatureThresholdEvent` `ShipmentInPortEvent`

To test this Business Network Definition in the **Test** tab:

Submit a `SetupDemo` transaction:

```
{
  "$class": "org.acme.shipping.perishable.SetupDemo"
}
```

This transaction populates the Participant Registries with a `Grower`, an `Importer` and a `Shipper`. The Asset Registries will have a `Contract` asset and a `Shipment` asset.

Submit a `TemperatureReading` transaction:

```
{
  "$class": "org.acme.shipping.perishable.TemperatureReading",
  "centigrade": 8,
  "shipment": "resource:org.acme.shipping.perishable.Shipment#SHIP_001"
}
```

If the temperature reading falls outside the min/max range of the contract, the price received by the grower will be reduced, and a `TemperatureThresholdEvent` is emitted. You may submit several readings if you wish. Each reading will be aggregated within `SHIP_001` Shipment Asset Registry.

Submit a `ShipmentReceived` transaction for `SHIP_001` to trigger the payout to the grower, based on the parameters of the `CON_001` contract:

```
{
  "$class": "org.acme.shipping.perishable.ShipmentReceived",
  "shipment": "resource:org.acme.shipping.perishable.Shipment#SHIP_001"
}
```

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

If the GPS reading indicates the ship's location is the Port of New Jersey/New York (40.6840,-74.0062) then a `ShipmentInPortEvent` is emitted.

Enjoy!

## License <a name="license"></a>
Hyperledger Project source code files are made available under the Apache License, Version 2.0 (Apache-2.0), located in the LICENSE file. Hyperledger Project documentation files are made available under the Creative Commons Attribution 4.0 International License (CC-BY-4.0), available at http://creativecommons.org/licenses/by/4.0/.
