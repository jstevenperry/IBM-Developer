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
const ITERATIONS_MAX = 3;

// Iteration counter
let iteration = 0;

logger.info('START', 'MAINLINE');

const timeout = setInterval(() => {
    logger.info('START: setInterval', 'TIMERS PHASE');

    if (iteration < ITERATIONS_MAX) {
        setTimeout(() => {
            logger.info('TIMERS PHASE', 'setInterval.setTimeout');
        });
        fs.readdir('../data', (err, files) => {
            if (err) throw err;
            logger.info('fs.readdir() callback: Directory contains: ' + files.length + ' files', 'POLL PHASE');
        });
    } else {
        logger.info('Max interval count exceeded. Goodbye.', 'TIMERS PHASE');
        // Kill the interval timer
        clearInterval(timeout);
    }
    iteration++;

    logger.info('END: setInterval', 'TIMERS PHASE');
}, 0);

logger.info('END', 'MAINLINE');
