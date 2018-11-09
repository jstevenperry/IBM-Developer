Feature: Tests related to Shippers

    Background:
        Given I have deployed the business network definition ..
        And I have added the following participants
        """
        [
        {"$class":"org.acme.shipping.perishable.Grower", "email":"grower@email.com", "address":{"$class":"org.acme.shipping.perishable.Address", "country":"USA"}, "accountBalance":0},
        {"$class":"org.acme.shipping.perishable.Shipper", "email":"shipper@email.com", "address":{"$class":"org.acme.shipping.perishable.Address", "country":"Paname"}, "accountBalance":0}
        ]
        """
        And I have issued the participant org.acme.shipping.perishable.Grower#grower@email.com with the identity grower1
        And I have issued the participant org.acme.shipping.perishable.Shipper#shipper@email.com with the identity shipper1
        And I have added the following asset of type org.acme.shipping.perishable.Contract
            | contractId | grower           | shipper               | importer              | arrivalDateTime  | unitPrice | minTemperature | maxTemperature | minPenaltyFactor | maxPenaltyFactor |
            | CON_001    | grower@email.com | shipper@email.com     | supermarket@email.com | 10/26/2018 00:00 | 0.5       | 2              | 10             | 0.2              | 0.1              | 
        And I have added the following asset of type org.acme.shipping.perishable.Shipment
            | shipmentId | type    | status     | unitCount | contract |
            | SHIP_001   | BANANAS | IN_TRANSIT | 5000      | CON_001  |
        When I use the identity shipper1

    Scenario: shipper1 can read Shipper assets
        Then I should have the following participants
        """
        [
        {"$class":"org.acme.shipping.perishable.Shipper", "email":"shipper@email.com", "address":{"$class":"org.acme.shipping.perishable.Address", "country":"Paname"}, "accountBalance":0}
        ]
        """
    
    Scenario: shipper1 invokes the ShipmentPickup transaction
        And I submit the following transaction of type org.acme.shipping.perishable.ShipmentPickup
            | shipment |
            | SHIP_001 |
        Then I should have received the following event of type org.acme.shipping.perishable.ShipmentPickupEvent
            | message                                  | shipment |
            | Shipment picked up for shipment SHIP_001 | SHIP_001 |

    Scenario: shipper1 invokes the ShipmentLoaded transaction
        And I submit the following transaction of type org.acme.shipping.perishable.ShipmentLoaded
            | shipment |
            | SHIP_001 |
        Then I should have received the following event of type org.acme.shipping.perishable.ShipmentLoadedEvent
            | message                               | shipment |
            | Shipment loaded for shipment SHIP_001 | SHIP_001 |

    Scenario: grower1 cannot invoke the ShipmentPickup transaction
        When I use the identity grower1
        And I submit the following transaction of type org.acme.shipping.perishable.ShipmentPickup
            | shipment |
            | SHIP_001 |
        Then I should get an error matching /Participant .* does not have 'CREATE' access to resource/

    Scenario: grower1 cannot invoke the ShipmentPickup transaction
        When I use the identity grower1
        And I submit the following transaction of type org.acme.shipping.perishable.ShipmentLoaded
            | shipment |
            | SHIP_001 |
        Then I should get an error matching /Participant .* does not have 'CREATE' access to resource/
