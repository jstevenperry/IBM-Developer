-- You should probably run this inside of a TX block
\set ROOT_DIR ''/Users/sperry/home/development/projects/developerWorks/NcaaMarchMadness/src/main''
\set SQL_ROOT_DIR :ROOT_DIR/sql
\set DATA_ROOT_DIR :ROOT_DIR/data
\set LOAD_SCRIPT_ROOT_DIR ''/Users/sperry/l/MarchMadness/data''
\o :ROOT_DIR/LOG.txt

\echo 'REBUILDING DB...'
\qecho 'REBUILDING DB...'

\echo 'DROPPING ALL VIEWS...'
\qecho 'DROPPING ALL VIEWS...'
\i :SQL_ROOT_DIR/drop_views.sql

\echo 'DROPPING ALL TABLES...'
\qecho 'DROPPING ALL TABLES...'
\i :SQL_ROOT_DIR/drop_tables.sql

-- Run the main build script
\i :SQL_ROOT_DIR/build_db.sql

\o