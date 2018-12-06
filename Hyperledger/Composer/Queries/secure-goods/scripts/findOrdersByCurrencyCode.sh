#! /bin/bash

# Usage message
usage() {
  echo "Usage: $0 -i ITEM_ID -a ID_CARD"
  echo "Where:"
  echo "-a ID_CARD       : the ID card to use for authentication (REQUIRED)"
  echo "-c CURRENCY_CODE : the currency code for which to query Orders (REQUIRED)"
  echo "-t               : invoke the query's transaction to run the query"
  echo "-h               : this message"
  echo ""
  echo "Example: $0 -a buy001@secure-goods -c EUR"
}

# defaults
USE_CLIENT_API=true

# read the options
 
while getopts "a:c:th" opt; do
  case $opt in
    a)
      AUTH_ID_CARD=$OPTARG
      ;;
    c)
      CURRENCY_CODE=$OPTARG
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
export CURRENCY_CODE
export USE_CLIENT_API

echo "auth: $AUTH_ID_CARD" >&2
echo "currencyCode: $CURRENCY_CODE" >&2
echo "useClientApi: $USE_CLIENT_API" >&2
# Default: optimism
greenLight=YES

# Complain, then exit, if no ID card is specified
if [ -z $AUTH_ID_CARD ]; then
  echo "No ID card specified, cannot continue!" >&2
  greenLight=NO
fi

# Complain, then exit, if no currency code is specified
if [ -z $CURRENCY_CODE ]; then
  echo "No currency code specified, cannot continue!" >&2
  greenLight=NO
fi

# Do we have a green light?
if [ $greenLight = 'NO' ]; then
  # We do not
  usage
  exit 1
fi

# Run the JavaScript
node js/findOrdersByCurrencyCode