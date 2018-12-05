#! /bin/bash

# Usage message
usage() {
  echo "Usage: $0 -a ID_CARD"
  echo "Where:"
  echo "-a ID_CARD  : the ID card to use for authentication (REQUIRED)"
  echo "-h          : this message"
  echo ""
  echo "Example: $0 -a buy001@secure-goods"
}

# defaults
# NONE

# read the options
 
while getopts "a:h" opt; do
  case $opt in
    a)
      AUTH_ID_CARD=$OPTARG
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

echo "auth: $AUTH_ID_CARD" >&2

# Default: optimism
greenLight=YES

# Complain, then exit, if no ID card is specified
if [ -z $AUTH_ID_CARD ]; then
  echo "No ID card specified, cannot continue!" >&2
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
node js/findAllOrders