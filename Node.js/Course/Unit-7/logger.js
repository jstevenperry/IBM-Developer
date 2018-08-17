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

//* **********************************************************
//* simple-logger                                           *
//* To use this class, require() it and access one of       *
//* its helper functions to log output from your            *
//* JavaScript code. Call the function that corresponds     *
//* to the level of granularity that you want. The message  *
//* will appear if its Level is >= the current log level    *
//* (default: INFO). Standard logging stuff. No surprises.  *
//*                                                         *
//* trace() - log a trace message (finest granularity)      *
//* debug()                                                 *
//* info()                                                  *
//* warn()                                                  *
//* error()                                                 *
//* fatal() - log a fatal message (coarsest granularity)    *
//*                                                         *
//* setLogLevel() - sets the log level to the specified     *
//* Level.                                                  *
//* Setting the LogLevel to Level.OFF turns off logging.    *
//* **********************************************************
const Level = {
  TRACE: { priority: 0, outputString: 'TRACE' },
  DEBUG: { priority: 100, outputString: 'DEBUG' },
  INFO: { priority: 200, outputString: 'INFO' },
  WARN: { priority: 300, outputString: 'WARN' },
  ERROR: { priority: 400, outputString: 'ERROR' },
  FATAL: { priority: 500, outputString: 'FATAL' },
  OFF: { priority: 1000, outputString: 'OFF'},
};
// The current Log level
var logLevel = Level.INFO;
// console.log('Current log level: priority => ' + logLevel.priority + ', outputString => ' + logLevel.outputString);

// Output only the message (or include other decorations)
var decorateOutputMessage = true;
function setDecorateOutputMessage(value) {
  decorateOutputMessage = value;
}

/**
 * This function logs a message, along with the elapsed time.
 * Assumptions:
 * - The startTime has been created somewhere upstream
 *
 * @param message - the Message to be logged. Required.
 *
 * @param source - the source of the message. What that means is really
 * up to the one who defines the message. It could mean, for example, the
 * function within which the message originated.
 * Optional. If not set, just the message passed in is logged.
 */
function log(messageLogLevel, message, source) {
  // console.log('Current log level: ' + logLevel.outputString);
  if (messageLogLevel.priority >= logLevel.priority) {
    let computedMessage = message.toString();
    if (decorateOutputMessage === true) {
      // Compute the message text based on log level output string, and whether
      // / or not the startTime was present
      let now = Date.now();
      let outputString = now.toString() + ':' + messageLogLevel.outputString;
      computedMessage = outputString + ': ' + ((source) ? source + ': ' : '') + message;
      // Now log the computed message
    }
    console.log(computedMessage);
  }
}

/**
 * Allows dependent module to mutate the log level
 */
function setLogLevel(newLevel) {
  logLevel = newLevel;
}

function setMessageOnlyOutput(value) {
  messageOnlyOutput = value;
}

/**
 * Helper function - TRACE level messages
 */
function trace(message, source) {
  log(Level.TRACE, message, source);
}

/**
 * Helper function - DEBUG level messages
 */
function debug(message, source) {
  log(Level.DEBUG, message, source);
}

/**
 * Helper function - INFO level messages
 */
function info(message, source) {
  log(Level.INFO, message, source);
}

/**
 * Helper function - WARN messages
 */
function warn(message, source) {
  log(Level.WARN, message, source);
}

/**
 * Helper function - ERROR messages
 */
function error(message, source) {
  log(Level.ERROR, message, source);
}

/**
 * Helper function - FATAL messages
 */
function fatal(message, source) {
  log(Level.FATAL, message, source);
}

//* ***********************
// EXPORTS SECTION
//
// Setup the exports - these are the fixtures that are to be made
// / available to other modules.
module.exports.Level = Level;
module.exports.setLogLevel = setLogLevel;// Lets the dependent modules set the log level
module.exports.setDecorateOutputMessage = setDecorateOutputMessage;
//
module.exports.trace = trace;
module.exports.debug = debug;
module.exports.info = info;
module.exports.warn = warn;
module.exports.error = error;
module.exports.fatal = fatal;
// or
// exports.log = log;
