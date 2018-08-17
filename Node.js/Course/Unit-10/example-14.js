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
// Example 14 - External configuration (log4js-config.json)
// require log4js
const log4js = require('log4js');

// Logger configuration
log4js.configure('./config/log4js-config.json');

// Default logger
const logger = log4js.getLogger();

// Log a message with the default logger
logger.info('Howdy, Log4js!');
