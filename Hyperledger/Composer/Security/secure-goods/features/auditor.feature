#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

Feature: AuditorSecurity

    Background:
        Given I have deployed the business network definition ..
        And I have added the following participants of type com.makotogo.learn.composer.securegoods.participant.Auditor
            | id   | name |
            | AUD001 | Auditor1 |
            | AUD002 | Auditor2 |
        And I have added the following participants of type com.makotogo.learn.composer.securegoods.participant.Buyer
            | id   | name |
            | BUY001 | Buyer1 |
        And I have added the following participants of type com.makotogo.learn.composer.securegoods.participant.Seller
            | id   | name |
            | SELL001 | Seller1 |
        And I have added the following participants of type com.makotogo.learn.composer.securegoods.participant.Shipper
            | id   | name |
            | SHIP001 | Shipper1 |
        And I have added the following assets of type com.makotogo.learn.composer.securegoods.asset.Item
            | id | sku | description |
            | WIDGET001 | W001 | Widget number 1 |
            | WIDGET002 | W002 | Widget number 2 |
        And I have added the following assets
        """
        {
            "$class": "com.makotogo.learn.composer.securegoods.asset.Order",
            "id": "ORD0000001",
            "item": "WIDGET001",
            "quantity": "1000",
            "unitCost": {
                "$class": "com.makotogo.learn.composer.securegoods.common.Price",
                "currency": "USD", "amount": "1.5" 
            },
            "buyer": "BUY001",
            "seller": "SELL001",
            "shipper": "SHIP001",
            "shippingCost": {
                "$class": "com.makotogo.learn.composer.securegoods.common.Price",
                "currency": "USD", "amount": "100" 
            },
            "status": "SHIPPED"
        }
        """
        And I have issued the participant com.makotogo.learn.composer.securegoods.participant.Auditor#AUD001 with the identity AUDITOR001
        And I have issued the participant com.makotogo.learn.composer.securegoods.participant.Auditor#AUD002 with the identity AUDITOR002

    Scenario: Auditor 1 can read its own Auditor record
        When I use the identity AUDITOR001
        Then I should have the following participants of type com.makotogo.learn.composer.securegoods.participant.Auditor
            | id   | name |
            | AUD001 | Auditor1 |

    Scenario: Auditor 1 can update its own Auditor record
        When I use the identity AUDITOR001
        And I update the following participants of type com.makotogo.learn.composer.securegoods.participant.Auditor
            | id   | name |
            | AUD001 | Auditor1-UPDATED |
        Then I should have the following participants of type com.makotogo.learn.composer.securegoods.participant.Auditor
            | id   | name |
            | AUD001 | Auditor1-UPDATED |

    Scenario: Auditor 2 cannot update Auditor 1's record
        When I use the identity AUDITOR002
        And I update the following participants of type com.makotogo.learn.composer.securegoods.participant.Auditor
            | id   | name |
            | AUD001 | Auditor1-UPDATED |
        Then I should get an error matching /does not have .* access to resource/

    Scenario: Auditor 1 can read all Items
        When I use the identity AUDITOR001
        Then I should have the following assets of type com.makotogo.learn.composer.securegoods.asset.Item
            | id | sku | description |
            | WIDGET001 | W001 | Widget number 1 |
            | WIDGET002 | W002 | Widget number 2 |

    Scenario: Auditor can access Buyer's Participant Record
        When I use the identity AUDITOR001
        Then I should have the following participants of type com.makotogo.learn.composer.securegoods.participant.Buyer
            | id   | name |
            | BUY001 | Buyer1 |

    Scenario: Auditor can access Seller's Participant Record
        When I use the identity AUDITOR001
        Then I should have the following participants of type com.makotogo.learn.composer.securegoods.participant.Seller
            | id   | name |
            | SELL001 | Seller1 |

    Scenario: Auditor can access Shipper's Participant Record
        When I use the identity AUDITOR001
        Then I should have the following participants of type com.makotogo.learn.composer.securegoods.participant.Shipper
            | id   | name |
            | SHIP001 | Shipper1 |

    Scenario: Auditor 1 can read all Orders
        When I use the identity AUDITOR001
        Then I should have the following assets
        """
        {
            "$class": "com.makotogo.learn.composer.securegoods.asset.Order",
            "id": "ORD0000001",
            "item": "WIDGET001",
            "quantity": "1000",
            "unitCost": {
                "$class": "com.makotogo.learn.composer.securegoods.common.Price",
                "currency": "USD", "amount": "1.5" 
            },
            "buyer": "BUY001",
            "seller": "SELL001",
            "shipper": "SHIP001",
            "shippingCost": {
                "$class": "com.makotogo.learn.composer.securegoods.common.Price",
                "currency": "USD", "amount": "100" 
            },
            "status": "SHIPPED"
        }
        """

    Scenario: Auditor cannot invoke the CreateOrder transaction
        When I use the identity AUDITOR001
        And I submit the following transaction
        """
        { 
            "$class": "com.makotogo.learn.composer.securegoods.asset.CreateOrder",
            "item": "WIDGET001",
            "quantity": "1000",
            "unitCost": {
                "$class": "com.makotogo.learn.composer.securegoods.common.Price",
                "currency": "USD", "amount": "1.5" 
            },
            "buyer": "BUY001",
            "seller": "SELL001",
            "shipper": "SHIP001",
            "shippingCost": {
                "$class": "com.makotogo.learn.composer.securegoods.common.Price",
                "currency": "USD", "amount": "100" 
            }
        }
        """
        Then I should get an error matching /does not have .* access to resource/

    Scenario: Auditor cannot invoke the ShipOrder transaction
        When I use the identity AUDITOR001
        And I submit the following transaction of type com.makotogo.learn.composer.securegoods.asset.ShipOrder
            | order |
            | ORD0000001 |
        Then I should get an error matching /does not have .* access to resource/

    Scenario: Auditor cannot invoke the ReceiveShipment transaction
        When I use the identity AUDITOR002
        And I submit the following transaction of type com.makotogo.learn.composer.securegoods.asset.ReceiveOrder
            | order |
            | ORD0000001  |
        Then I should get an error matching /does not have .* access to resource/
