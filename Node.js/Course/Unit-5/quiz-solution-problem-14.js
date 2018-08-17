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
const logger = require('../common/event-logger');
const simpleUtils = require('../common/simple-utils');

(function mainline() {
    const ITERATIONS_MAX = 6;

    var iterationNumber = 0;
    
    var intervalTimeout = setInterval((startTime) => {
        
        let currentTime = simpleUtils.hrtimeToMillis(process.hrtime());

        if (iterationNumber < ITERATIONS_MAX) {
            logger.info('Elapsed time: ' + (currentTime - startTime), 'setInterval callback');
        } else {
            clearInterval(intervalTimeout);
        }
        iterationNumber++;
    }, 10, simpleUtils.hrtimeToMillis(process.hrtime()));
})();

