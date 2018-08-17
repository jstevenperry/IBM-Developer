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

const logger = require('../common/logger');

const { MAINLINE, START, END } = require('../common/constants');

(function mainline() {
    logger.info(START, MAINLINE);
    
    process.nextTick(() => {
        logger.info('mainline:process.nextTick() says: hello!', 'MICROTASK')
    });

    let iteration = 0;
    let intervalTimeout = setInterval(() => {
        if (iteration < 3) {
            setTimeout((iteration) => {
                logger.info('setInterval(' + iteration + '):setTimeout() says: Timer expired!', 'TIMERS');
                process.nextTick((iteration) => {
                    logger.info('setInterval():setTimeout(' + iteration + '):process.nextTick() says: Delimit TIMERS phase!', 'MICROTASK');
                }, iteration);
            }, 0, iteration);
        } else {
            logger.info('setInterval(' + iteration + ') says: Goodbye!', 'TIMERS');
            clearInterval(intervalTimeout);
        }
        iteration++;
    });

    logger.info(END, MAINLINE)
})();