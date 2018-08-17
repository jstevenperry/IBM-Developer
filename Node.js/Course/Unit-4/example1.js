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
console.log(Date.now().toString() + ': mainline: BEGIN');
// Burn CPU time
const startTime = Date.now();
let endTime = startTime;
// Chew up some CPU until 20ms has elapsed
while (endTime < startTime + 20) {
    endTime = Date.now();
}
console.log(Date.now().toString() + ': mainline: END');