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
// Example 9 - Multiple categories
// Get logger from external configuration source
const winstonConfig = require('./config/winston-config');

// require Winston
const winston = require('winston');

let defaultLogger = winstonConfig.defaultLogger;
defaultLogger.info('Beginning program execution...');

// The module is a category (functional area)
const MODULE = 'example-9';

// Add another logger with the category specific to this module
winston.loggers.add(MODULE, winstonConfig.createLoggerConfig(MODULE));
// Module-specific logger
const moduleLogger = winston.loggers.get(MODULE);

// Log messages with the module-specific logger
moduleLogger.info('Howdy, Winston!');
moduleLogger.warn('WARNING, Winston!');
moduleLogger.error('ERROR, Winston!');

// Add some more categories
const FOO = 'foo';
const BAR = 'bar';

// Add their log configurations
winston.loggers.add(FOO, winstonConfig.createLoggerConfig(FOO));
winston.loggers.add(BAR, winstonConfig.createLoggerConfig(BAR));

// Get references to them and log a message
const fooLogger = winston.loggers.get(FOO);
fooLogger.info('Howdy, Winston!');

const barLogger = winston.loggers.get(BAR);
barLogger.info('Howdy, Winston!');

defaultLogger.info('Program terminated.');
