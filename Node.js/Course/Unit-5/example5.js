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

// Max number of iterations to perform
const ITERATIONS_MAX = 2;

// Iteration counter
let iteration = 0;

process.nextTick(() => {
    // Microtask callback runs AFTER mainline, even though the code is here
    logger.info('process.nextTick', 'MAINLINE MICROTASK');
});

logger.info('START', 'MAINLINE');

const timeout = setInterval(() => {
    logger.info('START iteration ' + iteration + ': setInterval', 'TIMERS PHASE');

    if (iteration < ITERATIONS_MAX) {
        setTimeout((iteration) => {
            logger.info('TIMER EXPIRED (from iteration ' + iteration + '): setInterval.setTimeout', 'TIMERS PHASE');
            process.nextTick(() => {
                logger.info('setInterval.setTimeout.process.nextTick', 'TIMERS PHASE MICROTASK');
            });
        }, 0, iteration);
        fs.readdir('../data', (err, files) => {
            logger.info('fs.readdir() callback: Directory contains: ' + files.length + ' files', 'POLL PHASE');
            process.nextTick(() => {
                logger.info('setInterval.fs.readdir.process.nextTick', 'POLL PHASE MICROTASK');
            });
        });
        setImmediate(() => {
            logger.info('setInterval.setImmediate', 'CHECK PHASE');
            process.nextTick(() => {
                logger.info('setInterval.setTimeout.process.nextTick', 'CHECK PHASE MICROTASK');
            });
        });
    } else {
        logger.info('Max interval count exceeded. Goodbye.', 'TIMERS PHASE');
        // Kill the interval timer
        clearInterval(timeout);
    }
    logger.info('END iteration ' + iteration + ': setInterval', 'TIMERS PHASE');

    iteration++;
}, 0);

logger.info('MAINLINE: END');
