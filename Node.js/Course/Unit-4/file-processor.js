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
// Need File System built-in package
const fs = require('fs');
// Need Crypto built-in package
const crypto = require('crypto');
//
const logger = require('./simple-logger');
//log('Module load: GREETINGS');
// Crtypto constants
const KEY_LENGTH = 32;
const NUMBER_OF_ITERATIONS = 100000;
const SALT = 'NaCl';// Get it? :)
const DIGEST = 'sha512';
//
/**
 * This function does several things:
 * - Reads the file whose name is specified by fileName.
 * - Uses the file contents as the password to compute a
 *   256 bit derived key
 * - Passes the derived key to the resultsCallback
 * 
 * And it does all of this stuff ASYNCHRONOUSLY using callbacks
 * 
 * Assumptions:
 * The file to be read must a text file
 * 
 * @param fileName - the name of the file to be read
 * @param resultsCallback - the callback to invoke when the fileContents'
 *      derived key has been computed.
 */
function processFile(fileName, resultsCallback) {
    // Make a note of the start time
    const startTime = process.hrtime();
    logger.trace('fs.readFile(): START', startTime);
    // Read the file (asynchronously, of course)
    fs.readFile(fileName, 'utf8', function(err, fileContents) {
        // If error occurred, rethrow err
        if (err) throw err;
        logger.debug('File read complete. File contents length: ' + fileContents.length);
        // Create a hash of the file's contents (asynchronously)
        crypto.pbkdf2(fileContents, SALT, NUMBER_OF_ITERATIONS, KEY_LENGTH, DIGEST, (err, derivedKey) => {
            var derivedKeyAsString;
            // If error occurred, log it (err will be passed to callback later)
            if (err) {
                logger.error('Something went horribly wrong: ' + err.message);
                throw err;
            } else {
                derivedKeyAsString = derivedKey.toString('hex');
                logger.debug('crypto.pbkdf2(): Derived key: \'' + derivedKeyAsString + '\'', startTime);
            }
            // Pass err and the results back (only one will be set)
            resultsCallback(err, derivedKeyAsString);
        });        
        logger.trace('fs.readFile(): END', startTime);
    }); 
};

/**
 * This function does several things:
 * - Reads the file whose name is specified by fileName.
 * - Uses the file contents as the password to compute a
 *   256 bit derived key
 * - Returns the derived key
 * 
 * And it does all of this stuff SYNCHRONOUSLY (and is named accordingly to
 * match the way the Node API names synchronous functions).
 * 
 * Assumptions:
 * The file to be read must a text file
 * 
 * @param fileName - the name of the file to be read
 * 
 * @returns derivedKeyAsString - the derived key using the contents of the file
 *      as the password
 */
function processFileSync(fileName) {
    // Make a note of the start time
    let startTime = process.hrtime();
    logger.trace('processFileSync(): START', startTime);
    // Read the file (asynchronously, of course)
    let fileContents = fs.readFileSync(fileName, 'utf8');
    // Create a hash of the file's contents (synchronously)
    let derivedKey = crypto.pbkdf2Sync(fileContents.toString(), SALT, NUMBER_OF_ITERATIONS, KEY_LENGTH, DIGEST);
    let derivedKeyAsString = derivedKey.toString('hex');
    logger.debug('processFileSync(): Computed hash: \'' + derivedKeyAsString + '\'', startTime);
    logger.trace('processFileSync(): END', startTime);
    // Return the hash as a String
    return derivedKeyAsString;
};

/**
 * This function does several things:
 * - Reads the file whose name is specified by fileName.
 * - Uses the file contents as the password to compute a
 *   256 bit derived key
 * - Writes the derived key to an output file
 * - Passes the derived key to the resultsCallback
 * 
 * And it does all of this stuff ASYNCHRONOUSLY using callbacks
 * 
 * Assumptions:
 * The file to be read must a text file
 * 
 * @param fileName - the name of the file to be read
 * @param resultsCallback - the callback to invoke when the fileContents
 *      derived key has been computed.
 */
function processFilePyramid(fileName, resultsCallback) {
    logger.trace('fs.readFile(): BEGIN');
    // First, read the file (asynchronously, of course)
    fs.readFile(fileName, 'utf8', function(err, fileContents) {
        logger.trace('fs.readFile(): BEGIN');
        // If error occurred, rethrow error
        if (err) throw err;
        // Then, create a hash of the words array (asynchronously)
        crypto.pbkdf2(fileContents, SALT, NUMBER_OF_ITERATIONS, KEY_LENGTH, DIGEST, (err, derivedKey) => {
            logger.trace('crypto.pbkdf2(): BEGIN');
            // If error occurred, rethrow error
            if (err) throw err;
            let derivedKeyAsString = derivedKey.toString('hex');
            logger.debug('crypto.pbkdf2(): Derived key as string: \'' + derivedKeyAsString + '\'');
            // Then, write the hash to a file
            let outputFileName = fileName + '.sha512';
            fs.writeFile(outputFileName, derivedKeyAsString, 'utf8', (err) => {
                logger.trace('fs.writeFile(): BEGIN');
                // If error occurred, log it (err will be passed to callback later)
                if (err) {
                    logger.error('fe.writeFile(): Something went horribly wrong: ' + err.message);
                    throw err;
                }
                // Then, pass the results (or err) back
                resultsCallback(err, outputFileName);
                logger.trace('fs.writeFile(): END');
            });
            logger.trace('crypto.pbkdf2(): END');
        });        
        logger.trace('fs.readFile(): END');
    }); // Bottom of the pyramid (sigh)
}

/**
 * This function does several things:
 * - Reads the file whose name is specified by fileName.
 * - Uses the file contents as the password to compute a
 *   256 bit derived key
 * - Writes the derived key to an output file
 * - Passes the output file name to the resolve() call
 * 
 * And it does all of this stuff ASYNCHRONOUSLY using callbacks
 * 
 * Assumptions:
 * The file to be read must a text file
 * 
 * @param fileName - the name of the file to be read
 * 
 * @returns Promise - a Promise, which, when resolved passes the output file name
 */
function processFilePromise(fileName) {
    // Make a note of the start time
    const startTime = process.hrtime();
    logger.trace('processFilePromise(): BEGIN');
    // First, read the file (asynchronously, of course)
    return new Promise((resolve, reject) => {
        fs.readFile(fileName, 'utf8', function(err, fileContents) {
            logger.trace('fs.readFile(): BEGIN');
            // If error occurred, rethrow error
            if (err) {
                reject(err);
            } else {
                logger.debug('fs.readFile(): File ' + fileName + ' read. File contents length: ' + fileContents.length);
                resolve(fileContents);
            }
            logger.trace('fs.readFile(): END');
        });
    }).then(fileContents => {
    // Then, crypto the file contents
        return new Promise((resolve, reject) => {
            crypto.pbkdf2(fileContents, SALT, NUMBER_OF_ITERATIONS, KEY_LENGTH, DIGEST, (err, derivedKey) => {
                logger.trace('crypto.pbkdf2(): BEGIN');
                // If error occurred, rethrow error
                if (err) {
                    reject(err);
                } else {
                    let derivedKeyAsString = derivedKey.toString('hex');
                    logger.debug('crypto.pbkdf2(): Derived key as string: \'' + derivedKeyAsString + '\'');
                    resolve(derivedKeyAsString);
                }
                logger.trace('crypto.pbkdf2(): END');
            });        
        });
    }).then(derivedKeyAsString => {
    // Then write the pbk to a file
        return new Promise((resolve, reject) => {
            let outputFileName = fileName + '.sha512';
            fs.writeFile(outputFileName, derivedKeyAsString, 'utf8', (err) => {
                logger.trace('fs.writeFile(): BEGIN');
                // If error occurred, log it (err will be passed to callback later)
                if (err) {
                    reject(err);
                } else {
                    logger.debug('fs.writeFile()Wrote derived key \'' + derivedKeyAsString + '\' to file: ' + outputFileName);
                    resolve(outputFileName);
                }
                logger.trace('fs.writeFile(): END');
            });
        });
    });
}

/**
 * THIS CODE DOES NOT WORK AND HAS BEEN PROVIDED AS AN ILLUSTRATION
 * OF HOW NOT TO WRITE NESTED PROMISES THAT USE ASYNCHRONOUS CODE.
 */
function processFileBrokenPromise(fileName) {
    // Make a note of the start time
    const startTime = process.hrtime();
    logger.trace('processFilePromise(): BEGIN');
    // First, read the file (asynchronously, of course)
    return new Promise((resolve, reject) => {
        fs.readFile(fileName, 'utf8', function(err, fileContents) {
            logger.trace('fs.readFile(): BEGIN');
            // If error occurred, rethrow error
            if (err) {
                reject(err);
            } else {
                logger.debug('fs.readFile(): File ' + fileName + ' read. File contents length: ' + fileContents.length);
                resolve(fileContents);
            }
            logger.trace('fs.readFile(): END');
        });
    }).then(fileContents => {
    // Then, crypto the file contents
        // Do we really need a Promise here? Nah, let's just run with it!
        // (This is incorrect, of course, hence the menacing method comment above)
        crypto.pbkdf2(fileContents, SALT, NUMBER_OF_ITERATIONS, KEY_LENGTH, DIGEST, (err, derivedKey) => {
            logger.trace('crypto.pbkdf2(): BEGIN');
            // If error occurred, rethrow error
            if (err) {
                reject(err);
            } else {
                let derivedKeyAsString = derivedKey.toString('hex');
                logger.debug('crypto.pbkdf2(): Derived key as string: \'' + derivedKeyAsString + '\'');
                return derivedKeyAsString
            }
            logger.trace('crypto.pbkdf2(): END');
        });        
    }).then(derivedKeyAsString => {
    // Then write the pbk to a file
        // Do we really need a Promise here? Nah, let's just run with it!
        // (This is incorrect, of course, hence the menacing method comment above)
        let outputFileName = fileName + '.sha512';
        fs.writeFile(outputFileName, derivedKeyAsString, 'utf8', (err) => {
            logger.trace('fs.writeFile(): BEGIN');
            // If error occurred, log it (err will be passed to callback later)
            if (err) {
                reject(err);
            } else {
                logger.debug('fs.writeFile()Wrote derived key \'' + derivedKeyAsString + '\' to file: ' + outputFileName);
                return outputFileName;
            }
            logger.trace('fs.writeFile(): END');
        });
    });
}

//************************ 
// EXPORTS SECTION
//
// Setup the exports - these are the fixtures that are to be made
/// available to other modules.
module.exports.processFile = processFile;
module.exports.processFilePyramid = processFilePyramid;
module.exports.processFilePromise = processFilePromise;
module.exports.processFileSync = processFileSync;
// or
// exports.processFile = processFile;
// exports.processFileSync = processFileSync;
// THIS IS A VERY BAD IDEA - USE AS NEGATIVE ILLUSTRATION ONLY:
module.exports.processFileBrokenPromise = processFileBrokenPromise;