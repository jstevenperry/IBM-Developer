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
/**
 * This module is modeled after simple-logger, except that it uses Node's
 * event processing architecture to catch log messages. I'm not sure it's
 * all that useful, other than to demonstrate some of what you can do
 * with Events.
 * 
 *@author J Steven Perry
 */
// Set to true to enable DEBUG mode
const __debug = false;

const EventEmitter = require('events');

// Simple Utilities
const simpleUtils = require('../common/simple-utils');

// The event that is emitted
const { EventInfo } = require('../common/event-info');

// Reference to simple-logger module
const logger = require('./logger');
// Just the message, please
logger.setDecorateOutputMessage(false);
// Set the default log level
var currentLogLevel = logger.Level.INFO;

logger.setLogLevel(currentLogLevel);
/**
 * Mutate the current Log Level
 */
function setLogLevel(newLevel) {
    // TODO: Q: Should we do some kind of sanity check on newLevel?
    // Set the current log level
    currentLogLevel = newLevel;
    // Set the value of the log level in the underlying simple-logger
    logger.setLogLevel(currentLogLevel);
}


// The EventEmitter to emit and listen for events
const eventEmitter = new EventEmitter();

// The event list
var eventList = new Array();

// The max number of events the list should hold before logging them
const EVENT_LIST_SIZE_MAX = 100;

/**
 * Register all of the event listeners supported by this module
 */
function registerEventListeners() {
    // TRACE
    eventEmitter.on(logger.Level.TRACE.outputString, function eventListener(eventInfo) {
        push(logger.Level.TRACE, eventInfo);
    });
    // DEBUG
    eventEmitter.on(logger.Level.DEBUG.outputString, function eventListener(eventInfo) {
        push(logger.Level.DEBUG, eventInfo);
    });
    // INFO
    eventEmitter.on(logger.Level.INFO.outputString, function eventListener(eventInfo) {
        push(logger.Level.INFO, eventInfo);
    });
    // WARN
    eventEmitter.on(logger.Level.WARN.outputString, function eventListener(eventInfo) {
        push(logger.Level.WARN, eventInfo);
    });
    // ERROR
    eventEmitter.on(logger.Level.ERROR.outputString, function eventListener(eventInfo) {
        push(logger.Level.ERROR, eventInfo);
    });
    // FATAL
    eventEmitter.on(logger.Level.FATAL.outputString, function eventListener(eventInfo) {
        push(logger.Level.FATAL, eventInfo);
    });
    // When Node exits, make sure to flush the event list
    process.on('exit', (code) => {
        // Node is going bye bye. Flush the event list.
        flush();
    });
}

/**
 * Push the specified EventInfo onto the Event List, along with its
 * corresponding Log Level
 * 
 * @param logLevel - The Level object (@see simple-logger.js)
 * @param eventInfo - The EventInfo object
 */
function push(logLevel, eventInfo) {
    if (eventList.length > EVENT_LIST_SIZE_MAX) {
        flush();
    }
    // Store this event in the event list
    eventList.push({ logLevel : logLevel, eventInfo : eventInfo });
}

/**
 * Flush out the event list
 */
function flush() {
    // Iterate over all items in the event list and log them
    eventList.forEach((currentValue) => {
        switch(currentValue.logLevel) {
            case logger.Level.TRACE:
                logger.trace(currentValue.eventInfo);
                break;
            case logger.Level.DEBUG:
                logger.debug(currentValue.eventInfo);
                break;
            case logger.Level.INFO:
                logger.info(currentValue.eventInfo);
                break;
            case logger.Level.WARN:
                logger.warn(currentValue.eventInfo);
                break;
            case logger.Level.ERROR:
                logger.error(currentValue.eventInfo);
                break;
            case logger.Level.FATAL:
                logger.fatal(currentValue.eventInfo);
                break;
            default:
                // Don't know this, drop it
                if (__debug) console.log('Unknown log level \'' + currentValue.logLevel.outputString + '\'. Dropping this message: ' + currentValue.eventInfo);
                break;
        }
    });
    // Create a new Array (leave the old one for GC)
    eventList = new Array();
}

/**
 * Common emitter function, called by all event listeners
 */
function emit(logLevel, message, source) {
    // If the message to be emitted is not at or above the
    /// current log level threshold, drop it on the floor
    if (logLevel.priority >= currentLogLevel.priority) {
        if (!source) {
            source = 'UNKNOWN';
        }
        // Get high resolution time from process.hrtime()
        // Timestamp is in milliseconds:
        const timestamp = simpleUtils.hrtimeToMillis(process.hrtime());
        const eventName = logLevel.outputString;
        eventEmitter.emit(eventName, new EventInfo(eventName, message, source, timestamp ));
    } else if (__debug) {
        console.log('Message: ' + message + ' at logLevel: ' + logLevel.outputString + ' dropped (current log level = ' + logLevel.outputString + ').');
    }
}

/**
 * TRACE function - emits the corresponding informational event
 * This is for backward compatibility with the simple-logger module
 */
function trace(message, source) {
    emit(logger.Level.TRACE, message, source);
}

/**
 * DEBUG function - emits the corresponding informational event
 * This is for backward compatibility with the simple-logger module
 */
function debug(message, source) {
    emit(logger.Level.DEBUG, message, source);
}

/**
 * INFO function - emits the corresponding informational event
 * This is for backward compatibility with the simple-logger module
 */
function info(message, source) {
    emit(logger.Level.INFO, message, source);
}

/**
 * WARN function - emits the corresponding informational event
 * This is for backward compatibility with the simple-logger module
 */
function warn(message, source) {
    emit(logger.Level.WARN, message, source);
}

/**
 * ERROR function - emits the corresponding informational event
 * This is for backward compatibility with the simple-logger module
 */
function error(message, source) {
    emit(logger.Level.ERROR, message, source);
}

/**
 * FATAL function - emits the corresponding informational event
 * This is for backward compatibility with the simple-logger module
 */
function fatal(message, source) {
    emit(logger.Level.FATAL, message, source);
}

// IIFE - runs when module is loaded
(function loadModule() {
    registerEventListeners();
})();

// Export the functions that are called from the outside
module.exports.trace = trace;
module.exports.debug = debug;
module.exports.info = info;
module.exports.warn = warn;
module.exports.error = error;
module.exports.fatal = fatal;
module.exports.flush = flush;

module.exports.setLogLevel = setLogLevel;
module.exports.Level = logger.Level;