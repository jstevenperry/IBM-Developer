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
const common = {
    dataDirectory : '../data'
};

const fileNames = {
    _50Words : common.dataDirectory + '/50kWords.txt',
    _1kWords : common.dataDirectory + '/1kWords.txt',
    _10kWords : common.dataDirectory + '/10kWords.txt',
    _100kWords : common.dataDirectory + '/100kWords.txt',
    _1mWords : common.dataDirectory + '/1mWords.txt',
    _10mWords : common.dataDirectory + '/10mWords.txt',
};

const example9 = {
    inputFileName : common.dataDirectory + '/10kWords.txt',
    outputFileName : common.dataDirectory + '/10kWords.txt.out'
};

const example10 = {
    inputFileName : common.dataDirectory + '/10kWords.txt'
};

module.exports.common = common;
module.exports.example9 = example9;
module.exports.example10 = example10;
module.exports.fileNames = fileNames;