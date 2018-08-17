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
// Some constants (Q: Move these someone else?)
const PORT = 8080;
// Location of the data directory
const DATA_DIR = '../data';
// The file name to be processed
const FILE_NAME = "1mWords.txt";
//
// Wrap the file-processor in an HTTP server
const http = require('http');
//
const logger = require('./simple-logger');
const fileProcessor = require('./file-processor');
//
const server = http.createServer((request, response) => {
    // Request processor
    // Get the METHOD and URL from the request
    const { method, url } = request;
    //
    if (method === 'POST') {
        request.on('error', (err) => {
            logger.error('ERROR processing request: ' + err.message);
        });
        if (url === '/processFile') {
            // Invoke processFile()
            fileProcessor.processFile(DATA_DIR + '/' + FILE_NAME, (err, derivedKeyAsString) => {
                if (err) {
                    logger.error('Something has gone horribly wrong: ' + err.message);
                } else {
                    // Write the sorted unique words as the response
                    writeServerResponse(response, 'File contents:\n' + derivedKeyAsString, 200);
                }
            });
        } else if (url == '/processFileSync') {
            // Invoke processFileSync()
            let derivedKeyAsString = fileProcessor.processFileSync(DATA_DIR + '/' + FILE_NAME);
            writeServerResponse(response, 'File contents:\n' + derivedKeyAsString, 200);
        } else {
            // URL not found
            writeServerResponse(response, 'The requested URL \'' + url + '\' is not recognized.\nOnly one of these URLS:\n\t/processFile\n\t/processFileSync.\nPlease try your request again.\n', 404);
        }
    } else {
        // Bad request
        writeServerResponse(response, 'Only POST method is allowed and either one of these URLS:\n\t/processFile\n\t/processFileSync.\nPlease try your request again.\n', 400);
    }
    response.on('error', (err) => {
        logger.error('ERROR on response: ' + err.message);
    });
}).listen(PORT);

/**
 * Write the response from the server.
 * 
 * @param response - the response object from the HTTP request callback
 * 
 * @param responseMessage - the message to write
 * 
 * @param statusCode - the HTTP status code for the response
 */
var writeServerResponse = function(response, responseMessage, statusCode) {
    logger.debug(responseMessage);
    response.statusCode = statusCode;
    response.write(responseMessage);
    response.end();
}