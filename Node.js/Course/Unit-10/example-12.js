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
// Example 12 - Multiple appenders
// require Log4js
const log4js = require('log4js');

// Logger configuration
log4js.configure({
    appenders: { 
        fileAppender: { type: 'file', filename: './logs/example-12.log' },
        console: { type: 'console' } 
    },
    categories: { 
        default: { appenders: ['fileAppender', 'console'], level: 'error' } 
    }
});

// Create the logger
const logger = log4js.getLogger();

// Log a message
logger.trace('Trace, log4js!');
logger.debug('Debug, log4js!');
logger.info('Hello, log4js!');
logger.warn('Heads up, log4js!');
logger.error('Danger, log4js!');
logger.fatal('Fatal, log4js!');
