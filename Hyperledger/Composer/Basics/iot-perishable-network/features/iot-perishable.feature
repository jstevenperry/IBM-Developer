Feature: IoT Perishable Network
 
Background:
    Given I have deployed the business network definition ..
    And I have added the following participants
    """
    [
    {"$class":"org.acme.shipping.perishable.Grower", "email":"grower@email.com", "address":{"$class":"org.acme.shipping.perishable.Address", "country":"USA"}, "accountBalance":0},
    {"$class":"org.acme.shipping.perishable.Importer", "email":"supermarket@email.com", "address":{"$class":"org.acme.shipping.perishable.Address", "country":"UK"}, "accountBalance":0},
    {"$class":"org.acme.shipping.perishable.Shipper", "email":"shipper@email.com", "address":{"$class":"org.acme.shipping.perishable.Address", "country":"Panama"}, "accountBalance":0}
    ]
    """
    And I have added the following asset of type org.acme.shipping.perishable.Contract
        | contractId | grower           | shipper               | importer           | arrivalDateTime  | unitPrice | minTemperature | maxTemperature | minPenaltyFactor | maxPenaltyFactor |
        | CON_001    | grower@email.com | supermarket@email.com | supermarket@email.com | 10/26/2019 00:00 | 0.5       | 2              | 10             | 0.2              | 0.1              | 
        
    And I have added the following asset of type org.acme.shipping.perishable.Shipment
        | shipmentId | type    | status     | unitCount | contract |
        | SHIP_001   | BANANAS | IN_TRANSIT | 5000      | CON_001  |
    When I submit the following transactions of type org.acme.shipping.perishable.TemperatureReading
        | shipment | centigrade |
        | SHIP_001 | 4          |
        | SHIP_001 | 5          |
        | SHIP_001 | 10         |

Scenario: When the temperature range is within the agreed-upon boundaries
    When I submit the following transaction of type org.acme.shipping.perishable.ShipmentReceived
        | shipment |
        | SHIP_001 |
     
    Then I should have the following participants
    """
    [
    {"$class":"org.acme.shipping.perishable.Grower", "email":"grower@email.com", "address":{"$class":"org.acme.shipping.perishable.Address", "country":"USA"}, "accountBalance":2500},
    {"$class":"org.acme.shipping.perishable.Importer", "email":"supermarket@email.com", "address":{"$class":"org.acme.shipping.perishable.Address", "country":"UK"}, "accountBalance":-2500},
    {"$class":"org.acme.shipping.perishable.Shipper", "email":"shipper@email.com", "address":{"$class":"org.acme.shipping.perishable.Address", "country":"Panama"}, "accountBalance":0}
    ]
    """
Scenario: When the low/min temperature threshold is breached by 2 degrees C
    Given I submit the following transaction of type org.acme.shipping.perishable.TemperatureReading
        | shipment | centigrade |
        | SHIP_001 | 0          |
 
    When I submit the following transaction of type org.acme.shipping.perishable.ShipmentReceived
        | shipment |
        | SHIP_001 |
     
    Then I should have the following participants
    """
    [
    {"$class":"org.acme.shipping.perishable.Grower", "email":"grower@email.com", "address":{"$class":"org.acme.shipping.perishable.Address", "country":"USA"}, "accountBalance":500},
    {"$class":"org.acme.shipping.perishable.Importer", "email":"supermarket@email.com", "address":{"$class":"org.acme.shipping.perishable.Address", "country":"UK"}, "accountBalance":-500},
    {"$class":"org.acme.shipping.perishable.Shipper", "email":"shipper@email.com", "address":{"$class":"org.acme.shipping.perishable.Address", "country":"Panama"}, "accountBalance":0}
    ]
    """
Scenario: When the hi/max temperature threshold is breached by 2 degrees C
    Given I submit the following transaction of type org.acme.shipping.perishable.TemperatureReading
        | shipment | centigrade |
        | SHIP_001 | 12          |
 
    When I submit the following transaction of type org.acme.shipping.perishable.ShipmentReceived
        | shipment |
        | SHIP_001 |
     
    Then I should have the following participants
    """
    [
    {"$class":"org.acme.shipping.perishable.Grower", "email":"grower@email.com", "address":{"$class":"org.acme.shipping.perishable.Address", "country":"USA"}, "accountBalance":1500},
    {"$class":"org.acme.shipping.perishable.Importer", "email":"supermarket@email.com", "address":{"$class":"org.acme.shipping.perishable.Address", "country":"UK"}, "accountBalance":-1500},
    {"$class":"org.acme.shipping.perishable.Shipper", "email":"shipper@email.com", "address":{"$class":"org.acme.shipping.perishable.Address", "country":"Panama"}, "accountBalance":0}
    ]
    """
Scenario: Test TemperatureThresholdEvent is emitted when the min temperature threshold is violated
    When I submit the following transactions of type org.acme.shipping.perishable.TemperatureReading
        | shipment | centigrade |
        | SHIP_001 | 0          |
     
    Then I should have received the following event of type org.acme.shipping.perishable.TemperatureThresholdEvent
        | message                                                                          | temperature | shipment |
        | Temperature threshold violated! Emitting TemperatureEvent for shipment: SHIP_001 | 0           | SHIP_001 |
 
 
Scenario: Test TemperatureThresholdEvent is emitted when the max temperature threshold is violated
    When I submit the following transactions of type org.acme.shipping.perishable.TemperatureReading
        | shipment | centigrade |
        | SHIP_001 | 11         |
     
    Then I should have received the following event of type org.acme.shipping.perishable.TemperatureThresholdEvent
        | message                                                                          | temperature | shipment |
        | Temperature threshold violated! Emitting TemperatureEvent for shipment: SHIP_001 | 11          | SHIP_001 |
 
 
Scenario: Test ShipmentInPortEvent is emitted when GpsReading indicates arrival at destination port
    When I submit the following transaction of type org.acme.shipping.perishable.GpsReading
        | shipment | readingTime | readingDate | latitude | latitudeDir | longitude | longitudeDir |
        | SHIP_001 | 120000      | 20171025    | 40.6840  | N           | 74.0062   | W            |
 
    Then I should have received the following event of type org.acme.shipping.perishable.ShipmentInPortEvent
        | message                                                                           | shipment |
        | Shipment has reached the destination port of /LAT:40.6840N/LONG:74.0062W | SHIP_001 |
