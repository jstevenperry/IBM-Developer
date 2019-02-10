#! 
#
# Script to run the SqlGenerator program.
#
# Set DEBUG to something other than true to turn it off
DEBUG=true

function usage {
  echo "Usage: $0 tournament_participants_file year"
  echo "Description: creates SQL load script for the specified NCAA Basketball tournament participants"
  echo "  and writes it out to the same location for the specified year."
  echo "Where:"
  echo -e "\ttournament_participants_file is the fully qualified path to the file "
  echo -e "\tcontaining the raw data from which SQL load scripts are to be created."
  echo -e "\tyear is the tournament year."
  echo
  echo "Examples:"
  echo "Create SQL load scripts from /Users/sperry/developerWorks/NcaaMarchMadness/src/main/data/tourney_teams_file_2009.txt"
  echo "   for tournament year 2009"
  echo -e "\t$0 /Users/sperry/l/MarchMadness/data/tourney_teams_file_2009.txt 2009"
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
$LIB_DIR/slf4j-api-1.7.22.jar:\
$LIB_DIR/opencsv-3.6.jar

if [ "$DEBUG" == "true" ]; then echo "CLASSPATH = $CP"; fi

# Fire up the program
java -cp $CP:$ROOT_DIR/target/classes com.makotojava.ncaabb.sqlgenerator.TournamentParticipantSqlGenerator $@