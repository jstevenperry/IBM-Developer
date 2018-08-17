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
'use strict'

// The Node EventEmitter
const EventEmitter = require('events');
// Create an instance of EventEmitter
const eventEmitter = new EventEmitter();

// The common logger
const logger = require('../common/logger');

logger.info('START', 'MAINLINE');

logger.info('Registering simpleEvent handler', 'MAINLINE');
eventEmitter.on('simpleEvent', (eventName, message, source, timestamp) => {
    logger.info('Received event: ' + timestamp + ': ' + source + ':[' + eventName + ']: ' + message, 'EventEmitter.on()');
});

// Get the current time
let hrtime = process.hrtime();
eventEmitter.emit('simpleEvent', 'simpleEvent', 'Custom event says what?', 'MAINLINE', (hrtime[0] * 1e9 + hrtime[1] ) / 1e6);

logger.info('END', 'MAINLINE');