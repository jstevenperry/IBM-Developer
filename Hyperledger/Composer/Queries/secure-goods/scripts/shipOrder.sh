#!/bin/bash

# defaults
auth=ship001@secure-goods
#orderId=ORD0000100

# read the options
 
while getopts "a:o:" opt; do
  case $opt in
    a)
      auth=$OPTARG
      echo "auth: $auth" >&2
      ;;
    o)
      orderId=$OPTARG
      echo "OrderId: $orderId" >&2
      ;;
    \?)
      echo "Invalid option: -$OPTARG" >&2
      exit 1
      ;;
  esac
done

composer transaction submit --card ${auth} -d '{
"$class": "com.makotogo.learn.composer.securegoods.asset.ShipOrder",
"order": "resource:com.makotogo.learn.composer.securegoods.asset.Order#'${orderId}'"
}'