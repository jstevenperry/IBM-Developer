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
const log4js = require('log4js');
// Logger configuration
log4js.configure({
  appenders: { 
      fileAppender: { type: 'file', filename: './logs/unit-11.log' },
      console: { type: 'console' } 
  },
  categories: { 
      default: { appenders: ['fileAppender', 'console'], level: 'debug' } 
  }
});
const log4jsLogger = log4js.getLogger();

//* *********************************************************
//* makoto-logger                                           *
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
//* *********************************************************
const Level = {
  TRACE: {priority: 0, outputString: 'TRACE'},
  DEBUG: {priority: 100, outputString: 'DEBUG'},
  INFO: {priority: 200, outputString: 'INFO'},
  WARN: {priority: 300, outputString: 'WARN'},
  ERROR: {priority: 400, outputString: 'ERROR'},
  FATAL: {priority: 500, outputString: 'FATAL'},
  OFF: {priority: 1000, outputString: 'OFF'},
};
// The default log level
const DEFAULT_LOG_LEVEL = Level.INFO;
// The current Log level
let logLevel = DEFAULT_LOG_LEVEL;

/**
 * Allows dependent module to mutate the log level
 * to the new level value.
 * @param {Level} newLevel - the new log Level
 */
function setLogLevel(newLevel) {
  logLevel = newLevel;
}

/**
 * This function computes a message
 *
 * @param {Level} messageLogLevel - the Level of the message to be logged.
 *
 * @param {String} message - the Message to be logged. Required.
 *
 * @param {String} source - the source of the message. What that means is really
 * up to the one who defines the message. It could mean, for example, the
 * function within which the message originated.
 * Optional. If not set, just the message passed in is logged.
 *
 * @param {Function} logFunction - the actual logging function to use. If not
 * defined, uses console.log().
 *
 * @return {String} computedMessage - the actual computed message (complete with
 * any decorations), or an empty string if the messageLogLevel was below
 * the current log level at the moment this function was called.
 */
function log(messageLogLevel, message, source, logFunction) {
  let computedMessage = null;
  if (messageLogLevel.priority >= logLevel.priority) {
    // Compute the message text based on log level output string, and whether
    // / or not the startTime was present
    let now = Date.now();
    let outputString = now.toString() + ':' + messageLogLevel.outputString;
    computedMessage = outputString + ': ' + ((source) ? source + ': ' : '') +
      message;
    // Now log the computed message
    // If the caller passed in a logging function to use, use that, otherwise
    // / use this module's default, which is logMessage()
    (logFunction) ? logFunction(computedMessage) : logMessage(computedMessage);
  }
  return computedMessage;
}

/**
 * Performs the actual logging of the computed message.
 *
 * @param {String} computedMessage - the message to log (computed elsewhere)
 */
function logMessage(computedMessage) {
  // TODO: Make this better
  log4jsLogger.info(computedMessage);
}

/**
 * Helper function - TRACE level messages
 * @param {String} message - the message to log
 * @param {String} source - the message source
 * @param {Function} logFunction (optional) - the log function to use, allows
 * caller to override the default behavior, which is to use console.log()
 * @return {String} computedMessage - the actual computed message (complete with
 * any decorations), or an empty string if the messageLogLevel was below
 * the current log level at the moment this function was called.
 */
function trace(message, source, logFunction) {
  return log(Level.TRACE, message, source, logFunction);
}

/**
 * Helper function - DEBUG level messages
 * @param {String} message - the message to log
 * @param {String} source - the message source
 * @param {Function} logFunction (optional) - the log function to use, allows
 * caller to override the default behavior, which is to use console.log()
 * @return {String} computedMessage - the actual computed message (complete with
 * any decorations), or an empty string if the messageLogLevel was below
 * the current log level at the moment this function was called.
 */
function debug(message, source, logFunction) {
  return log(Level.DEBUG, message, source, logFunction);
}

/**
 * Helper function - INFO level messages
 * @param {String} message - the message to log
 * @param {String} source - the message source
 * @param {Function} logFunction (optional) - the log function to use, allows
 * caller to override the default behavior, which is to use console.log()
 * @return {String} computedMessage - the actual computed message (complete with
 * any decorations), or an empty string if the messageLogLevel was below
 * the current log level at the moment this function was called.
 */
function info(message, source, logFunction) {
  return log(Level.INFO, message, source, logFunction);
}

/**
 * Helper function - WARN messages
 * @param {String} message - the message to log
 * @param {String} source - the message source
 * @param {Function} logFunction (optional) - the log function to use, allows
 * caller to override the default behavior, which is to use console.log()
 * @return {String} computedMessage - the actual computed message (complete with
 * any decorations), or an empty string if the messageLogLevel was below
 * the current log level at the moment this function was called.
 */
function warn(message, source, logFunction) {
  return log(Level.WARN, message, source, logFunction);
}

/**
 * Helper function - ERROR messages
 * @param {String} message - the message to log
 * @param {String} source - the message source
 * @param {Function} logFunction (optional) - the log function to use, allows
 * caller to override the default behavior, which is to use console.log()
 * @return {String} computedMessage - the actual computed message (complete with
 * any decorations), or an empty string if the messageLogLevel was below
 * the current log level at the moment this function was called.
 */
function error(message, source, logFunction) {
  return log(Level.ERROR, message, source, logFunction);
}

/**
 * Helper function - FATAL messages
 * @param {String} message - the message to log
 * @param {String} source - the message source
 * @param {Function} logFunction (optional) - the log function to use, allows
 * caller to override the default behavior, which is to use console.log()
 * @return {String} computedMessage - the actual computed message (complete with
 * any decorations), or an empty string if the messageLogLevel was below
 * the current log level at the moment this function was called.
 */
function fatal(message, source, logFunction) {
  return log(Level.FATAL, message, source, logFunction);
}

//* ***********************
// EXPORTS SECTION
//
// Setup the exports - these are the fixtures that are to be made
// / available to other modules.
module.exports.Level = Level;
module.exports.DEFAULT_LOG_LEVEL = DEFAULT_LOG_LEVEL;
// Lets the dependent modules set the log level
module.exports.setLogLevel = setLogLevel;
//
module.exports.trace = trace;
module.exports.debug = debug;
module.exports.info = info;
module.exports.warn = warn;
module.exports.error = error;
module.exports.fatal = fatal;
// Low-level API function
module.exports.log = log;
