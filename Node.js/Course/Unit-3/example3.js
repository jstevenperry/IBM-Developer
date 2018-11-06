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
// Begin
var fs = require('fs');
console.log('Starting program...');
// Read a small file asynchronously
fs.readFile('../data/50Words.txt', 'utf8', function(err, fileContents) {
    // If error occurred, rethrow error
    if (err) throw err;
    // Count number of words
    let numberOfWords = fileContents.split(/[ ,.\n]+/).length;
    // Print to console
    console.log('There are ' + numberOfWords + ' words in this file.');
});
console.log('Program finished');