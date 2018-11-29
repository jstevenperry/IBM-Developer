#! /bin/sh

composer transaction submit --card sell001@secure-goods -d '{
 "$class": "com.makotogo.learn.composer.securegoods.asset.CreateOrder",
  "item": "resource:com.makotogo.learn.composer.securegoods.asset.Item#WIDGET002",
  "quantity": 20,
  "unitCost": {
    "$class": "com.makotogo.learn.composer.securegoods.common.Price",
    "currency": "EUR",
    "amount": 12.50
  },
  "buyer": "resource:com.makotogo.learn.composer.securegoods.participant.Buyer#buy001",
  "seller": "resource:com.makotogo.learn.composer.securegoods.participant.Seller#sell001",
  "shipper": "resource:com.makotogo.learn.composer.securegoods.participant.Shipper#ship001",
  "shippingCost": {
    "$class": "com.makotogo.learn.composer.securegoods.common.Price",
    "currency": "EUR",
    "amount": 124.76
  }
}'