#!/bin/bash

# Usage message
usage() {
  echo "Usage: $0 [-abnVh]"
  echo "Where:"
  echo "-a ID_CARD      : the ID card to use for authentication (REQUIRED)"
  echo "-b BNA_DIR      : the location of the BNA file to be installed (REQUIRED)"
  echo "-n NETWORK_NAME : the network name (REQUIRED)"
  echo "-V VERSION      : the network version number (REQUIRED)"
  echo "-h              : this message"
  echo ""
  echo "Example: $0 -a PeerAdmin@hlfv1 -b .. -V 0.2.2"
  echo ""
}

# defaults

# read the options
 
while getopts "a:b:n:V:h" opt; do
  case $opt in
    a)
      auth=$OPTARG
      ;;
    b)
      bnaDistDir=$OPTARG
      ;;
    n)
      networkName=$OPTARG
      ;;
    V)
      networkVersionNumber=$OPTARG
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
echo "bnaDistDir: $bnaDistDir" >&2
echo "networkName: $networkName" >&2
echo "networkVersionNumber: $networkVersionNumber" >&2

# Default: optimism
greenLight=YES

#
# Check for all required parameters
if [ -z $auth ]; then
    echo "ERROR: ID card missing!"
    greenLight=NO
fi
if [ -z $bnaDistDir ]; then
    echo "ERROR: BNA root directory not specified!"
    greenLight=NO
fi
if [ -z $networkName ]; then
    echo "ERROR: Network name not specified!"
    greenLight=NO
fi
if [ -z $networkVersionNumber ]; then
    echo "ERROR: Network version missing!"
    greenLight=NO
fi

# Do we have a green light?
if [ $greenLight = 'NO' ]; then
    # We do not
    usage
    exit 1
fi

# Green light - GO!

#
# Install the new network
echo "Install network version $networkVersionNumber..."
composer network install --card ${auth} --archiveFile ${bnaDistDir}/${networkName}.bna
if [ $? -ne 0 ]; then exit 1; fi
echo "Done."

#
# Upgrade the new network
echo "Upgrading the network (this may take a few minutes)..."
composer network upgrade -c ${auth} -n ${networkName} -V ${networkVersionNumber}
if [ $? -ne 0 ]; then exit 1; fi
echo "Done."

#
# Ping the new network
echo "Pinging the network (one moment)..."
composer network ping --card admin@${networkName} 
if [ $? -ne 0 ]; then exit 1; fi
echo "Done."
