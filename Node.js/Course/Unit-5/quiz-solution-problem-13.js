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

const logger = require('../common/event-logger');

(function mainline() {
    const ITERATIONS_MAX = 3;

    let iterationNumber = 0;

    let intervalTimeout = setInterval(() => {
        if (iterationNumber < ITERATIONS_MAX) {
            fs.readdir('../data', (err, files) => {
                logger.info('setInterval.fs.readdir', 'POLL');
                // In poll phase
                process.nextTick(() => {
                    logger.info('setInterval.fs.readdir.process.nextTick', 'POLL MICROTASK');
                });
                setTimeout(() => {
                    logger.info('setInterval.fs.readdir.setTimeout', 'TIMERS');
                    process.nextTick(() => {
                        logger.info('setInterval.fs.readdir.setTimeout.process.nextTick', 'TIMERS MICROTASK')
                    });
                });
                setImmediate(() => {
                    logger.info('setInterval.fs.readdir.setImmediate', 'CHECK');
                    process.nextTick(() => {
                        logger.info('setInterval.fs.readdir.setImmediate.process.nextTick', 'CHECK MICROTASK')
                    });
                });
            });
        } else {
            clearInterval(intervalTimeout);
        }
        iterationNumber++;
    });
})();