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
(function mainline() {
    process.nextTick(() => {
        console.log('Giedi Prime');
    });
    console.log('Ix');
    setTimeout(() => {
        console.log('Chapterhouse');
    });
    setImmediate(() => {
        console.log('Tleilax');
    });
    fs.readdir('./', 'utf8', (err, files) => {
        console.log('Caladan');
        setTimeout(() => {
            console.log('Arrakis');
        }, 0);
        setImmediate(() => {
            console.log('Kaitain');
        });
        process.nextTick(() => {
            console.log('Salusa Secundus');
        });
    });
    console.log('Corrin');
})();

