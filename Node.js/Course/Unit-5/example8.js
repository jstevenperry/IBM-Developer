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
const EventEmitter = require('events');
const eventEmitter = new EventEmitter();

// EventInfo module
const { EventInfo } = require('../common/event-info');
// Event Logger module
const logger = require('../common/event-logger');
// Simple utilities module
const simpleUtils = require('../common/simple-utils');

// Constants (from constants module)
const { MAINLINE, START, END } = require('../common/constants');

// The custom event name
const EVENT_NAME = 'simpleEvent';

/**
 * The mainline function.
 */
(function mainline() {

    logger.info(START, MAINLINE);

    logger.info('Registering ' + EVENT_NAME + ' handler', MAINLINE);
    eventEmitter.on(EVENT_NAME, (eventInfo) => {
        logger.info('Received event: ' + eventInfo.toString(), 'EventEmitter.on()');
    });
    // Emit the event
    eventEmitter.emit(EVENT_NAME, new EventInfo(EVENT_NAME, 'Custom event says what?', MAINLINE, simpleUtils.hrtimeToMillis(process.hrtime())));
    
    logger.info(END, MAINLINE);

})();
