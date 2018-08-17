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
// Example 6 - Custom Log Levels
// require Winston
const winston = require('winston');

// Custom log levels
const noteLevels = {
    values: {
        doe: 10,
        ray: 20,
        me: 30,
        far: 40,
        sew: 50,
        la: 60,
        tea: 70
    }
};

// Logger configuration
const logConfiguration = {
    level: 'far',
    levels: noteLevels.values,
    transports: [
        new winston.transports.Console()
    ]
};

// Create the logger
const logger = winston.createLogger(logConfiguration);

/**
 * 
 */
function doLogging() {
    // Log some messages
    logger.tea('Tea, Winston!');
    logger.la('La, Winston!');
    logger.sew('Sew, Winston!');
    logger.far('Far, Winston!');
    logger.me('Me, Winston!');
    logger.ray('Ray, Winston!');
    logger.doe('Doe, Winston!');
}

// Do some logging as the logger was setup
logger.doe(`Logging messages, current log level: ${logger.level}`);
doLogging();

// Now modify the level
logger.level = 'tea';
logger.doe(`Logging messages, current log level: ${logger.level}`);
doLogging();

try {
    logger.info('The previously used log methods no longer work!');
} catch (err) {
    logger.doe(`${err.message}`);
}
