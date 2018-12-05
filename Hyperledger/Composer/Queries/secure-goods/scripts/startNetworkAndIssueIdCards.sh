#! /bin/bash

BNA_ROOT=${1:-../dist}

echo 'Install network...'
composer network install --card PeerAdmin@hlfv1 --archiveFile ${BNA_ROOT}/secure-goods.bna
if [ $? -ne 0 ]; then exit 1; fi
echo 'Done.'
echo 'Start network...'
composer network start --networkName secure-goods --networkVersion 1.0.0 --networkAdmin admin --networkAdminEnrollSecret adminpw --card PeerAdmin@hlfv1 --file admin.card
if [ $? -ne 0 ]; then exit 1; fi
echo 'Done.'
echo 'Process admin@secure-goods ID card...'
composer card import --card admin@secure-goods --file admin.card
if [ $? -ne 0 ]; then exit 1; fi
composer network ping --card admin@secure-goods
if [ $? -ne 0 ]; then exit 1; fi
echo 'Done.'

echo 'Loading registries...'
composer transaction submit --card admin@secure-goods -d '{"$class": "com.makotogo.learn.composer.securegoods.common.LoadRegistries"}'
if [ $? -ne 0 ]; then exit 1; fi
echo 'Done.'

echo 'Issue identities...'

echo '--> sell001@secure-goods'
composer identity issue --card admin@secure-goods --file sell001.card --newUserId sell001 --participantId 'resource:com.makotogo.learn.composer.securegoods.participant.Seller#sell001'
if [ $? -ne 0 ]; then exit 1; fi
composer card import --card sell001@secure-goods --file sell001.card
if [ $? -ne 0 ]; then exit 1; fi
composer network ping --card sell001@secure-goods
if [ $? -ne 0 ]; then exit 1; fi
echo 'Done.'

echo '--> sell002@secure-goods'
composer identity issue --card admin@secure-goods --file sell002.card --newUserId sell002 --participantId 'resource:com.makotogo.learn.composer.securegoods.participant.Seller#sell002'
if [ $? -ne 0 ]; then exit 1; fi
composer card import --card sell002@secure-goods --file sell002.card
if [ $? -ne 0 ]; then exit 1; fi
composer network ping --card sell002@secure-goods
if [ $? -ne 0 ]; then exit 1; fi
echo 'Done.'
echo '--> ship001@secure-goods'
composer identity issue --card admin@secure-goods --file ship001.card --newUserId ship001 --participantId 'resource:com.makotogo.learn.composer.securegoods.participant.Shipper#ship001'
if [ $? -ne 0 ]; then exit 1; fi
composer card import --card ship001@secure-goods --file ship001.card
if [ $? -ne 0 ]; then exit 1; fi
composer network ping --card ship001@secure-goods
if [ $? -ne 0 ]; then exit 1; fi
echo 'Done.'
echo '--> ship002@secure-goods'
composer identity issue --card admin@secure-goods --file ship002.card --newUserId ship002 --participantId 'resource:com.makotogo.learn.composer.securegoods.participant.Shipper#ship002'
if [ $? -ne 0 ]; then exit 1; fi
composer card import --card ship002@secure-goods --file ship002.card
if [ $? -ne 0 ]; then exit 1; fi
composer network ping --card ship002@secure-goods
if [ $? -ne 0 ]; then exit 1; fi
echo 'Done.'
echo '--> buy001@secure-goods'
composer identity issue --card admin@secure-goods --file buy001.card --newUserId buy001 --participantId 'resource:com.makotogo.learn.composer.securegoods.participant.Buyer#buy001'
if [ $? -ne 0 ]; then exit 1; fi
composer card import --card buy001@secure-goods --file buy001.card
if [ $? -ne 0 ]; then exit 1; fi
composer network ping --card buy001@secure-goods
if [ $? -ne 0 ]; then exit 1; fi
echo 'Done.'
echo '--> buy002@secure-goods'
composer identity issue --card admin@secure-goods --file buy002.card --newUserId buy002 --participantId 'resource:com.makotogo.learn.composer.securegoods.participant.Buyer#buy002'
if [ $? -ne 0 ]; then exit 1; fi
composer card import --card buy002@secure-goods --file buy002.card
if [ $? -ne 0 ]; then exit 1; fi
composer network ping --card buy002@secure-goods
if [ $? -ne 0 ]; then exit 1; fi
echo 'Done.'

echo 'Removing (useless) card files...'
rm admin.card
rm sell*.card
rm ship*.card
rm buy*.card
echo 'Done.'
