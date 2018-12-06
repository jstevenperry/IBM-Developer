#! /bin/bash

# Usage message
usage() {
  echo "Usage: $0 -b BUYER_ID -a ID_CARD"
  echo "Where:"
  echo "-a ID_CARD  : the ID card to use for authentication (REQUIRED)"
  echo "-b BUYER_ID : the Buyer ID for whom to query Orders (REQUIRED)"
  echo "-t          : invoke the query's transaction to run the query"
  echo "-h          : this message"
  echo ""
  echo "Example: $0 -a sell001@secure-goods -b buy001"
}

# defaults
USE_CLIENT_API=true

# read the options
 
while getopts "a:b:th" opt; do
  case $opt in
    a)
      AUTH_ID_CARD=$OPTARG
      ;;
    b)
      BUYER_ID=$OPTARG
      ;;
    t)
      USE_CLIENT_API=false
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
export BUYER_ID
export USE_CLIENT_API

echo "auth: $AUTH_ID_CARD" >&2
echo "buyerId: $BUYER_ID" >&2
echo "useClientApi: $USE_CLIENT_API" >&2
# Default: optimism
greenLight=YES

# Complain, then exit, if no ID card is specified
if [ -z $AUTH_ID_CARD ]; then
  echo "No ID card specified, cannot continue!" >&2
  greenLight=NO
fi

# Complain, then exit, if no Buyer ID is specified
if [ -z $BUYER_ID ]; then
  echo "No Buyer ID specified, cannot continue!" >&2
  greenLight=NO
fi

# Do we have a green light?
if [ $greenLight = 'NO' ]; then
  # We do not
  usage
  exit 1
fi

# Run the JavaScript
node js/findOrdersForBuyer