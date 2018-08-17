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
// Example 13 - External configuration (app-settings.js)
// require log4js
const log4js = require('Log4js');

// App settings
const { traceLogConfig } = require('./config/app-settings').log4js;

// Logger configuration
log4js.configure(traceLogConfig);

// Create the logger
const logger = log4js.getLogger();

// Log a message
logger.trace('Trace, Log4js!');
logger.debug('Debug, Log4js!');
logger.info('Hello, Log4js!');
logger.warn('Heads up, Log4js!');
logger.error('Danger, Log4js!');
logger.fatal('Fatal, Log4js!');
