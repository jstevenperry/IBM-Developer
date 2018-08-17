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

const logger = require('../common/logger');

// Iteration counter
let iteration = 0;

logger.info('START', 'MAINLINE');

const timeout = setInterval((startTime) => {
    logger.info('START iteration ' + iteration + ': setInterval', 'TIMERS PHASE');

    // Run for about 10 ms
    if (Date.now() < startTime + 10) {
        setTimeout((currentIteration) => {
            logger.info('TIMER EXPIRED (from iteration ' + currentIteration + '): setInterval.setTimeout', 'TIMERS PHASE');
            process.nextTick(() => {
                logger.info('setInterval.setTimeout.process.nextTick', 'TIMERS PHASE MICROTASK');
            });
        }, 0, iteration);
        setImmediate((startTime, currentIteration) => {
            logger.info('Callback executed (scheduled during iteration ' + currentIteration + '): setInterval.setImmediate', 'CHECK PHASE');
            logger.info('Elapsed time: ' + (Date.now() - startTime) + 'ms', 'CHECK PHASE');
            process.nextTick((startTime) => {
                logger.info('setInterval.setTimeout.process.nextTick', 'CHECK PHASE MICROTASK');
                logger.info('Elapsed time: ' + (Date.now() - startTime) + 'ms', 'CHECK PHASE');
            }, startTime);
        }, startTime, iteration);
    } else {
        logger.info('Max interval count exceeded. Goodbye.', 'TIMERS PHASE');
        // Kill the interval timer
        clearInterval(timeout);
    }
    logger.info('END iteration ' + iteration + ': setInterval', 'TIMERS PHASE');

    iteration++;
}, 0, Date.now());

logger.info('END', 'MAINLINE');
