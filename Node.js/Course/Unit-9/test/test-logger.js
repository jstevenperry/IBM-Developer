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
// Mocha API
// TODO: require() mocha
// Assertions module - chai!
// TODO: require() chai
// Sinon for fakes, stubs, and spies
// TODO: require() sinon

// The code under test
const logger = require('../logger');
const {Level} = logger;

// The Date.now() stub
let dateNowStub = null;

// Do this before every test
beforeEach(function() {
    // TODO: create sinon stub for Date.now()
    // that returns 1111111111 (that is, 10 1s)
});
// Do this after every test
afterEach(function() {
    // TODO: restore original Date.now() function
});

/**
 * Tests - Module-level features:
 */
describe('Module-level features:', function() {
    // TRACE
    describe('when log level isLevel.TRACE', function() {
        it('should have a priority order lower than Level.DEBUG', function() {
            expect(Level.TRACE.priority).to.be.lessThan(Level.DEBUG.priority);
        });
        it('should have outputString value of TRACE', function() {
            expect(Level.TRACE.outputString).to.equal('TRACE');
        });
    });
    // TODO: DEBUG
    // TODO: INFO
    // TODO: WARN
    // TODO: ERROR
    // TODO: FATAL
    // OFF
    describe('Level.OFF', function() {
        it('should have an outputString value of OFF', function() {
            expect(Level.OFF.outputString).to.equal('OFF');
        });
    });
    describe('Default log level of INFO', function() {
        let mockLogFunction;
        before(function() {
            // Make sure the default is correct
            logger.setLogLevel(logger.DEFAULT_LOG_LEVEL);
            mockLogFunction = sinon.mock().exactly(4);
        });
        after(function() {
            mockLogFunction.verify();
        });        // TRACE
        it('should not log a TRACE level message and return null', function() {
            expect(logger.trace('BAR', 'foo()')).to.be.null;
        });
        // TODO: DEBUG
        // TODO: INFO
        // TODO: WARN
        // TODO: ERROR
        // TODO: FATAL
    });
});

/**
 * Tests - Log Levels and the APIs:
 */
describe('Log Levels and the API:', function() {
    describe('when current log Level=TRACE', function() {
        let mockLogFunction;
        before(function() {
            // TODO: Set the log level to TRACE
            // TODO: Create mock and set the correct expectation
        });
        after(function() {
            mockLogFunction.verify();
        });        // TRACE
        // TRACE
        it('should output TRACE level message', function() {
            expect(logger.trace('whatever', 'whatever()', fakeLogFunction))
            .to.equal('1111111111:TRACE: whatever(): whatever');
        });
        // TODO: DEBUG
        // TODO: INFO
        // TODO: WARN
        // TODO: ERROR
        // TODO: FATAL
    });
    // TODO: DEBUG
    // TODO: INFO
    // TODO: WARN
    // TODO: ERROR
    // TODO: FATAL
    // TODO: OFF
});

/**
 * Ensure complete code coverage
 */
describe('Code Coverage:', function() {
    // Make sure INFO message will get logged
    before(function() {
        // TODO: Set the log level to INFO
    });
    it('should invoke logMessage() at least once so coverage is 100%', function() {
        // TODO: Stub console.log so the output doesn't actually show up
        // TODO: Output the message (so logMessage() gets called)
        // TODO: Now make sure console.log() was called
        // TODO: Restore the actual console.log() function (or the report doesn't show up)
    });
});
