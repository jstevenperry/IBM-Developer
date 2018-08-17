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
// Example 3 - Multiple transports
// require Winston
const winston = require('winston');

// Logger configuration
const logConfiguration = {
    transports: [
        new winston.transports.Console({
            level: 'verbose'
        }),
        new winston.transports.File({
            level: 'error',
            filename: './logs/example-3.log'
        })
    ]
};

// Create the logger
const logger = winston.createLogger(logConfiguration);

// Log some messages
logger.silly('Trace message, Winston!');
logger.debug('Debug message, Winston!');
logger.verbose('A bit more info, Winston!');
logger.info('Hello, Winston!');
logger.warn('Heads up, Winston!');
logger.error('Danger, Winston!');
