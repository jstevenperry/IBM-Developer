#!/bin/bash

# Usage message
usage() {
  echo "Usage: $0 [-aibsScvh]"
  echo "Where:"
  echo "-a ID_CARD    : the ID card to use for authentication (default: sell001@secure-goods)"
  echo "-i ITEM_ID    : the Item ID to exchange (default: WIDGET001)"
  echo "-b BUYER_ID   : the Buyer ID (default: buy001)"
  echo "-s SELLER_ID  : the Seller ID (default: sell001)"
  echo "-S SHIPPER_ID : the Shipper ID (default: ship001)"
  echo "-c CURRENCY   : the Currency code to use (default: EUR)"
  echo "-h            : this message"
  echo ""
  echo "Example: $0 -a sell002@secure-goods -i WIDGET002 -b buy001 -s sell002 -S ship001 -c AUD"
  echo ""
}

# defaults
auth=sell001@secure-goods
itemId=WIDGET001
buyerId=buy001
sellerId=sell001
shipperId=ship001
currency=EUR

# read the options
 
while getopts "a:i:b:s:S:c:h" opt; do
  case $opt in
    a)
      auth=$OPTARG
      ;;
    i)
      itemId=$OPTARG
      ;;
    b)
      buyerId=$OPTARG
      ;;
    s)
      sellerId=$OPTARG
      ;;
    S)
      shipperId=$OPTARG
      ;;
    c)
      currency=$OPTARG
      ;;
    h)
      usage
      exit 1
      ;;
    \?)
      echo "Invalid option: -$OPTARG" >&2
      usage
      exit 1
      ;;
  esac
done

# Dump out the options
echo "auth: $auth" >&2
echo "itemId: $itemId" >&2
echo "buyerId: $buyerId" >&2
echo "sellerId: $sellerId" >&2
echo "shipperId: : $shipperId" >&2
echo "currency: : $currency" >&2

#
# Now execute the command using the parameters values supplied
# or the defaults
composer transaction submit --card ${auth} -d '{
 "$class": "com.makotogo.learn.composer.securegoods.asset.CreateOrder",
  "item": "resource:com.makotogo.learn.composer.securegoods.asset.Item#'${itemId}'",
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