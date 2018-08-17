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
// require Winston
const winston = require('winston');

/**
 * appSettings
 */
const appSettings = {
    winston: {
        sillyLogConfig: {
            level: 'silly',
            transports: [
                new winston.transports.File({
                    filename: './logs/silly.log'
                }),
                new winston.transports.Console()
            ]
        },
    },
    log4js: {
        traceLogConfig: {
            appenders: {
                fileAppender: { type: 'file', filename: './logs/trace.log'},
                consoleAppender: { type: 'console' }
            },
            categories: {
                default: { appenders: ['fileAppender', 'consoleAppender'], level: 'trace'}
            }
        }
    }
};

module.exports = appSettings;
