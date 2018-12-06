#! /bin/bash

# Usage message
usage() {
  echo "Usage: $0 -b SELLER_ID -a ID_CARD"
  echo "Where:"
  echo "-a ID_CARD  : the ID card to use for authentication (REQUIRED)"
  echo "-s SELLER_ID : the Seller ID for whom to query Orders (REQUIRED)"
  echo "-c          : use the Composer client API to run the query directly"
  echo "-h          : this message"
  echo ""
  echo "Example: $0 -a sell001@secure-goods -b sell001"
}

# defaults
USE_CLIENT_API=false

# read the options
 
while getopts "a:s:ch" opt; do
  case $opt in
    a)
      AUTH_ID_CARD=$OPTARG
      ;;
    s)
      SELLER_ID=$OPTARG
      ;;
    c)
      USE_CLIENT_API=true
      ;;
    h)
      usage
      exit 1
      ;;
    \?)
      echo "Invalid option: -$OPTARG" >&2
      exit 1
      ;;
  esac
done

# Dump out the options
export AUTH_ID_CARD
export SELLER_ID
export USE_CLIENT_API

echo "auth: $AUTH_ID_CARD" >&2
echo "sellerId: $SELLER_ID" >&2
echo "useClientApi: $USE_CLIENT_API" >&2

# Default: optimism
greenLight=YES

# Complain, then exit, if no ID card is specified
if [ -z $AUTH_ID_CARD ]; then
  echo "No ID card specified, cannot continue!" >&2
  greenLight=NO
fi

# Complain, then exit, if no Buyer ID is specified
if [ -z $SELLER_ID ]; then
  echo "No Seller ID specified, cannot continue!" >&2
  greenLight=NO
fi

# Do we have a green light?
if [ $greenLight = 'NO' ]; then
  # We do not
  echo "" >&2
  usage
  exit 1
fi

# Run the JavaScript
node js/findOrdersForSeller