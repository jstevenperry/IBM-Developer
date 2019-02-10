-- 
-- The main build script. Use this script to build a fresh database.
-- If you need to rebuild the DB, dropping tables and views
-- will be required, and you should run rebuild.sh
--
\set ROOT_DIR ''/Users/sperry/home/development/projects/developerWorks/NcaaMarchMadness/src/main''
\set SQL_ROOT_DIR :ROOT_DIR/sql
\set DATA_ROOT_DIR :ROOT_DIR/data
\set LOAD_SCRIPT_ROOT_DIR ''/Users/sperry/l/MarchMadness/data''

-- Tee output to log file
\o :LOAD_SCRIPT_ROOT_DIR/DB_BUILD_LOG.txt

\echo 'BUILDING DB...'
\qecho 'BUILDING DB...'

\echo 'Script variables:'
\echo 'ROOT_DIR ==> ' :ROOT_DIR
\echo 'SQL_ROOT_DIR ==> ' :SQL_ROOT_DIR
\echo 'DATA_ROOT_DIR ==> ' :DATA_ROOT_DIR
\echo 'LOAD_SCRIPT_ROOT_DIR ==> ' :LOAD_SCRIPT_ROOT_DIR

\qecho 'Script variables:'
\qecho 'ROOT_DIR ==> ' :ROOT_DIR
\qecho 'SQL_ROOT_DIR ==> ' :SQL_ROOT_DIR
\qecho 'DATA_ROOT_DIR ==> ' :DATA_ROOT_DIR
\qecho 'LOAD_SCRIPT_ROOT_DIR ==> ' :LOAD_SCRIPT_ROOT_DIR

\echo 'CREATING ALL TABLES...'
\qecho 'CREATING ALL TABLES...'
\i :SQL_ROOT_DIR/create_tables.sql

\echo 'CREATING ALL VIEWS...'
\qecho 'CREATING ALL VIEWS...'
\i :SQL_ROOT_DIR/create_views.sql

\echo 'LOADING ALL TABLES:'
\qecho 'LOADING ALL TABLES:'

\echo 'YEAR: 2010...'
\qecho 'YEAR: 2010...'
\set YEAR 2010
\i :LOAD_SCRIPT_ROOT_DIR/load_season_data_:YEAR.sql
\i :DATA_ROOT_DIR/load_tournament_participants-2010.sql

\echo 'YEAR: 2011...'
\qecho 'YEAR: 2011...'
\set YEAR 2011
\i :LOAD_SCRIPT_ROOT_DIR/load_season_data_:YEAR.sql
\i :DATA_ROOT_DIR/load_tournament_participants-2011.sql

\echo 'YEAR: 2012...'
\qecho 'YEAR: 2012...'
\set YEAR 2012
\i :LOAD_SCRIPT_ROOT_DIR/load_season_data_:YEAR.sql
\i :DATA_ROOT_DIR/load_tournament_participants-2012.sql

\echo 'YEAR: 2013...'
\qecho 'YEAR: 2013...'
\set YEAR 2013
\i :LOAD_SCRIPT_ROOT_DIR/load_season_data_:YEAR.sql
\i :DATA_ROOT_DIR/load_tournament_participants-2013.sql

\echo 'YEAR: 2014...'
\qecho 'YEAR: 2014...'
\set YEAR 2014
\i :LOAD_SCRIPT_ROOT_DIR/load_season_data_:YEAR.sql
\i :DATA_ROOT_DIR/load_tournament_participants-2014.sql

\echo 'YEAR: 2015...'
\qecho 'YEAR: 2015...'
\set YEAR 2015
\i :LOAD_SCRIPT_ROOT_DIR/load_season_data_:YEAR.sql
\i :DATA_ROOT_DIR/load_tournament_participants-2015.sql

\echo 'YEAR: 2016...'
\qecho 'YEAR: 2016...'
\set YEAR 2016
\i :LOAD_SCRIPT_ROOT_DIR/load_season_data_:YEAR.sql
\i :DATA_ROOT_DIR/load_tournament_participants-2016.sql

\echo 'YEAR: 2017...'
\qecho 'YEAR: 2017...'
\set YEAR 2017
\i :LOAD_SCRIPT_ROOT_DIR/load_season_data_:YEAR.sql
\i :DATA_ROOT_DIR/load_tournament_participants-2017.sql

\echo 'LOADING TOURNAMENT RESULT DATA FOR ALL YEARS...'
\qecho 'LOADING TOURNAMENT RESULT DATA FOR ALL YEARS...'
\i :SQL_ROOT_DIR/load_tournament_result.sql

\echo 'FIND MISSING TEAM NAMES...'
\qecho 'FIND MISSING TEAM NAMES...'
\i :SQL_ROOT_DIR/find_missing_team_names.sql

--
\echo 'DATABASE BUILD COMPLETE.'
\qecho 'DATABASE BUILD COMPLETE.'
-- Turn off output to log file
\o 
