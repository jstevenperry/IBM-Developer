#! /bin/sh

composer transaction submit --card buy001@secure-goods -d '{
"$class": "com.makotogo.learn.composer.securegoods.asset.ReceiveOrder",
"order": "resource:com.makotogo.learn.composer.securegoods.asset.Order#ORD0000100"
}'