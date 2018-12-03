#!/bin/bash

# defaults
auth=sell001@secure-goods
widgetId=WIDGET001
buyerId=buy001
sellerId=sell001
shipperId=ship001
currency=EUR

# read the options
 
while getopts "a:w:b:s:S:c:" opt; do
  case $opt in
    a)
      auth=$OPTARG
      echo "auth: $auth" >&2
      ;;
    w)
      widgetId=$OPTARG
      echo "widgetId: $widgetId" >&2
      ;;
    b)
      buyerId=$OPTARG
      echo "buyerId: $buyerId" >&2
      ;;
    s)
      sellerId=$OPTARG
      echo "sellerId: $sellerId" >&2
      ;;
    S)
      shipperId=$OPTARG
      echo "shipperId: : $shipperId" >&2
      ;;
    c)
      currency=$OPTARG
      echo "currency: : $currency" >&2
      ;;
    \?)
      echo "Invalid option: -$OPTARG" >&2
      exit 1
      ;;
  esac
done

#
# Now execute the command using the parameters values supplied
# or the defaults
composer transaction submit --card ${auth} -d '{
 "$class": "com.makotogo.learn.composer.securegoods.asset.CreateOrder",
  "item": "resource:com.makotogo.learn.composer.securegoods.asset.Item#'${widgetId}'",
  "quantity": 20,
  "unitCost": {
    "$class": "com.makotogo.learn.composer.securegoods.common.Price",
    "currency": "'${currency}'",
    "amount": 12.50
  },
  "buyer": "resource:com.makotogo.learn.composer.securegoods.participant.Buyer#'${buyerId}'",
  "seller": "resource:com.makotogo.learn.composer.securegoods.participant.Seller#'${sellerId}'",
  "shipper": "resource:com.makotogo.learn.composer.securegoods.participant.Shipper#'${shipperId}'",
  "shippingCost": {
    "$class": "com.makotogo.learn.composer.securegoods.common.Price",
    "currency": "'${currency}'",
    "amount": 124.76
  }
}'