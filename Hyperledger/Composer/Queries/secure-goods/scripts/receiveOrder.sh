#! /bin/sh

# defaults
auth=ship001@secure-goods

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

composer transaction submit --card buy001@secure-goods -d '{
"$class": "com.makotogo.learn.composer.securegoods.asset.ReceiveOrder",
"order": "resource:com.makotogo.learn.composer.securegoods.asset.Order#'${orderId}'"
}'