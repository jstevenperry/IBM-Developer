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

/**
 * Factory function for creating EventInfo objects.
 * 
 * @param eventName - the event eventName
 * @param message - the message that accompanies the event
 * @param source - the source of the message
 * @param timestamp - timestamp for the event
 */
function EventInfo(eventName, message, source, timestamp) {
    this.eventName = eventName;
    this.message = message;
    this.source = source;
    this.timestamp = timestamp;
}

/**
 * Function to return the string representation of
 * this EventInfo object
 * 
 * @returns JSON String representation of the object's contents
 */
EventInfo.prototype.toJSONString = function() {
    return '{ eventName : \'' + this.eventName + '\', ' +
            'message : \'' + this.message + '\', ' +
            'source : \'' + this.source + '\', ' +
            'timestamp : ' + this.timestamp + ' }'; 
}

EventInfo.prototype.toString = function() {
    return this.timestamp + ': ' + this.source + ':[' + this.eventName + ']: ' + this.message;
}

// Export the EventInfo factory function
module.exports.EventInfo = EventInfo;
