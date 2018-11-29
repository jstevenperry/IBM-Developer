#! /bin/sh

composer transaction submit --card ship001@secure-goods -d '{
"$class": "com.makotogo.learn.composer.securegoods.asset.ShipOrder",
"order": "resource:com.makotogo.learn.composer.securegoods.asset.Order#ORD0000100"
}'