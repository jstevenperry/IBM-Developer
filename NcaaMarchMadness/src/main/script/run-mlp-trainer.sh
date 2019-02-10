#! 
#
# Script to run the MlpNetworkTrainer program.
#
# Set DEBUG to something other than true to turn it off
DEBUG=true

function usage {
  echo "Usage: $0 td_year [td_year2 ... td_yearN] v_year [,v_year2,...v_yearN]"
  echo "Description: creates training data for the specified years"
  echo "Where:"
  echo -e "\ttd_year1 is the first (and possibly only) year for which training data is to be used to train the network."
  echo -e "\tyear2 (optional) is the second year (if specified) for which training data is to be used to train the network."
  echo -e "\tyearN (optional) is the Nth year (if specified) for which training data is to be used to train the network."
  echo -e "\tv_year1..N is the comma-separated list of years against which the network is to be validated. (At least one"
  echo -e "\t\tv_year value is required)."
  echo "Examples:"
  echo "Train a network against data from 2011 through 2014 and validate against 2015 and 2016"
  echo -e "\t$0 2011 2012 2013 2014 2015,2016"
  echo "Train a network against data from 2010 through 2013 and validate against 2009, 2014, 2015 and 2016"
  echo -e "\t$0 2010 2011 2012 2013 2009,2014,2015,2016"
  echo   
}

# Process number of arguments
NUMARGS=$#
if [ "$DEBUG" == "true" ]; then echo -e \\n"Number of arguments: $NUMARGS"; fi
if [ "$NUMARGS" -eq 0 ]; then
  usage
  exit 1
fi
if [ "$DEBUG" == "true" ]; then echo "Script arguments: $@"; fi

# Below is an example that works on my Mac.
# Change this to match your source location.
ROOT_DIR=/Users/sperry/home/development/projects/developerWorks/NcaaMarchMadness

# Make sure ROOT_DIR is set or bail out
if [ -z "$ROOT_DIR" ]
then
  echo "ROOT_DIR is not set! This variable should be set to the source root of your project."
  echo "Make sure that you run a Maven build to create the necessary class files"
  echo "and library dependencies"
  exit 1
fi

if [ "$DEBUG" == "true" ]; then echo "ROOT_DIR = ${ROOT_DIR}"; fi

# Set the lib directory as a convenience
LIB_DIR=$ROOT_DIR/target/lib

SPRING_FRAMEWORK_VERSION=4.3.6.RELEASE

# Set the CLASSPATH to use.
CP=\
$LIB_DIR/neuroph-2.94.jar:\
$LIB_DIR/postgresql-9.1-901-1.jdbc4.jar:\
$LIB_DIR/commons-lang3-3.4.jar:\
$LIB_DIR/spring-context-$SPRING_FRAMEWORK_VERSION.jar:\
$LIB_DIR/spring-core-$SPRING_FRAMEWORK_VERSION.jar:\
$LIB_DIR/spring-beans-$SPRING_FRAMEWORK_VERSION.jar:\
$LIB_DIR/spring-aop-$SPRING_FRAMEWORK_VERSION.jar:\
$LIB_DIR/spring-expression-$SPRING_FRAMEWORK_VERSION.jar:\
$LIB_DIR/spring-jdbc-$SPRING_FRAMEWORK_VERSION.jar:\
$LIB_DIR/spring-tx-$SPRING_FRAMEWORK_VERSION.jar:\
$LIB_DIR/log4j-1.2.17.jar:\
$LIB_DIR/jcl-over-slf4j-1.7.22.jar:\
$LIB_DIR/slf4j-api-1.7.22.jar

if [ "$DEBUG" == "true" ]; then echo "CLASSPATH = $CP"; fi

# Fire up the program
java -jar $ROOT_DIR/target/MarchMadness-1.0.0.jar $@
