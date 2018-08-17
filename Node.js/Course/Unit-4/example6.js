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
const MS_TO_SLEEP = 20; // For async
const MS_TO_BURN = 2; // For sync
//
// Wrap the file-processor in an HTTP server
const http = require('http');
//
const logger = require('./simple-logger');
//
const server = http.createServer((request, response) => {
    // Request processor
    // Get the METHOD and URL from the request
    const { method, url } = request;
    //
    if (method === 'POST') {
        request.on('error', (err) => {
            logger.log('ERROR processing request: ' + err.message);
        });
        let startTime = Date.now();
        if (url === '/async') {
            // Wake up in MS_TO_SLEEP and return to the caller
            setTimeout(() => {
                let endTime = Date.now();
                // Write the sorted unique words as the response
                writeServerResponse(response, 'Server request complete in ' + (endTime - startTime) + 'ms.\n', 200);
            }, MS_TO_SLEEP);
        } else if (url == '/sync') {
            // Burn CPU time
            let endTime = startTime;
            while (endTime < startTime + MS_TO_BURN) {
                // Chew up some CPU until MS_TO_BURN has elapsed
                endTime = Date.now();
            }
            // Now return to the caller
            writeServerResponse(response, 'Server request complete in ' + (endTime - startTime) + 'ms.\n', 200);
        } else {
            // URL not found
            writeServerResponse(response, 'The requested URL \'' + url + '\' is not recognized.\nOnly one of these URLS:\n\t/processFile\n\t/processFileSync.\nPlease try your request again.\n', 404);
        }
    } else {
        // Bad request
        writeServerResponse(response, 'Only POST method is allowed and either one of these URLS:\n\t/processFile\n\t/processFileSync.\nPlease try your request again.\n', 400);
    }
    response.on('error', (err) => {
        logger.log('ERROR on response: ' + err.message);
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
 * 
 * @param logResponseMessage - if set to a truthy value logs the responseMessage, otherwise not
 */
var writeServerResponse = function(response, responseMessage, statusCode) {
    logger.debug(responseMessage);
    response.statusCode = statusCode;
    response.write(responseMessage);
    response.end();
}