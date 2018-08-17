/*
   Copyright 2018 Makoto Consulting Group, Inc.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
// Example 17 - Log Categories
// require log4js
const log4js = require('log4js');

// The module is a category (functional area)
const MODULE = 'example-17';

const defaultLogger = log4js.getLogger();
defaultLogger.level = 'info';
defaultLogger.info('Beginning program execution...');

// Logger configuration
log4js.configure('./config/log4js-config.json');

// Module-specific logger
const moduleLogger = log4js.getLogger(MODULE);

// Log a message with the default logger
moduleLogger.info('Howdy, Log4js!');
moduleLogger.warn('WARNING, Log4js!');
moduleLogger.error('ERROR, Log4js!');

// Add some more categories
const FOO = 'foo';
const BAR = 'bar';

// Get references to them and log a message
const fooLogger = log4js.getLogger(FOO);
fooLogger.info('Howdy, Log4js!');

const barLogger = log4js.getLogger(BAR);
barLogger.info('Howdy, Log4js!');

defaultLogger.info('Program terminated.');
