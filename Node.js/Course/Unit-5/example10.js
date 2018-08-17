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

const fs = require('fs');

const simpleUtils = require('../common/simple-utils');

const logger = require('../common/event-logger');
logger.setLogLevel(logger.Level.DEBUG);

const readableStream = fs.createReadStream(require('../settings/app-settings').example10.inputFileName, 'utf8');

const __mapEventLoop = true;

const intervalTimeout = setInterval(() => {
    logger.debug('TIMERS PHASE: GREETINGS', 'setInterval');
    if (__mapEventLoop) {
        process.nextTick(() => {
            logger.debug('TIMERS PHASE DONE', 'ReadableStream.on(open).callback(process.nextTick)');
        });
    }
}, 0);

readableStream.on('open', (fd) => {
    logger.info('open event received', 'ReadableStream.on(open).callback');
    if (__mapEventLoop) {
        process.nextTick(() => {
            logger.debug('POLL PHASE DONE', 'ReadableStream.on(open).callback(process.nextTick)');
        });
        // The next two calls prove where the ready event is coming from
        setTimeout(() => {
            logger.debug('TIMER EXPIRED', 'ReadableStream.on(ready).callback(setTimeout)');
            process.nextTick(() => {
                logger.debug('TIMERS PHASE DONE', 'ReadableStream.on(ready).callback(setTimeout.process.nextTick)');
            });
        }, 0);
        setImmediate(() => {
            logger.debug('CHECK PHASE: GREETINGS', 'ReadableStream.on(ready).callback(setImmediate)');
            process.nextTick(() => {
                logger.debug('CHECK PHASE DONE', 'ReadableStream.on(ready).callback(setImmediate.process.nextTick)');
            });
        });
        // Eat some CPU to make sure the setTimeout(0) has expired before we let the event loop continue
        simpleUtils.eatCpu(3);
    }
});

readableStream.on('data', (chunk) => {
    logger.info('data event received: chunk size: ' + chunk.length, 'ReadableStream.on(data).callback');
    if (__mapEventLoop) {
            process.nextTick(() => {
            logger.debug('POLL PHASE DONE', 'ReadableStream.on(data).callback(process.nextTick)');
        });
        setTimeout(() => {
            logger.debug('TIMER EXPIRED', 'ReadableStream.on(data).callback(setTimeout)');
            process.nextTick(() => {
                logger.debug('TIMERS PHASE DONE', 'ReadableStream.on(data).callback(setTimeout.process.nextTick)');
            });
        }, 0);
        setImmediate(() => {
            logger.debug('CHECK PHASE: GREETINGS', 'ReadableStream.on(data).callback(setImmediate)');
            process.nextTick(() => {
                logger.debug('CHECK PHASE DONE', 'ReadableStream.on(data).callback(setImmediate.process.nextTick)');
            });
        });
    }
});

readableStream.on('close', () => {
    logger.info('close event received', 'ReadableStream.on(close).callback');
    if (__mapEventLoop) {
        process.nextTick(() => {
            logger.debug('POLL PHASE DONE', 'ReadableStream.on(close).callback(process.nextTick)');
        });
        // The next two calls prove where the close event is coming from
        // If we see the setTimeout(0) callback next, then the close event is coming from the close phase
        setTimeout(() => {
            logger.debug('TIMER EXPIRED', 'ReadableStream.on(close).callback(setTimeout)');
            process.nextTick(() => {
                logger.debug('TIMERS PHASE DONE', 'ReadableStream.on(close).callback(setTimeout.process.nextTick)');
            });
        }, 0);
        // If we see the setImmediate() callback next, then close event is coming from the poll phase
        setImmediate(() => {
            logger.debug('CHECK PHASE: GREETINGS', 'ReadableStream.on(close).callback(setImmediate)');
            process.nextTick(() => {
                logger.debug('CHECK PHASE DONE', 'ReadableStream.on(close).callback(setImmediate.process.nextTick)');
            });
        });
        // Eat some CPU to make sure the setTimeout(0) has expired before we let the event loop continue
        simpleUtils.eatCpu(3);
    }
    clearInterval(intervalTimeout);
});

readableStream.on('error', (error) => {
    logger.info('ERROR: ' + error);
    if (__mapEventLoop) {
        process.nextTick(() => {
            logger.debug('POLL PHASE DONE', 'ReadableStream.on(error).callback(process.nextTick)');
        });
        // The next two calls prove where the error event is coming from
        // If we see the setTimeout(0) callback next, then the error event is coming from the close phase
        setTimeout(() => {
            logger.debug('TIMER EXPIRED', 'ReadableStream.on(error).callback(setTimeout)');
            process.nextTick(() => {
                logger.debug('TIMERS PHASE DONE', 'ReadableStream.on(error).callback(setTimeout.process.nextTick)');
            });
        }, 0);
        // If we see the setImmediate() callback next, then error event is coming from the poll phase
        setImmediate(() => {
            logger.debug('CHECK PHASE: GREETINGS', 'ReadableStream.on(error).callback(setImmediate)');
            process.nextTick(() => {
                logger.debug('CHECK PHASE DONE', 'ReadableStream.on(error).callback(setImmediate.process.nextTick)');
            });
        });
        // Eat some CPU to make sure the setTimeout(0) has expired before we let the event loop continue
        simpleUtils.eatCpu(3);
    }
    clearInterval(intervalTimeout);
});