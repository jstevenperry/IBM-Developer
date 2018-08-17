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
const {after, afterEach, before, beforeEach, describe, it} = require('mocha');
// Assertions module - chai!
const {expect} = require('chai');
// Sinon for fakes, stubs, and spies
const sinon = require('sinon');

// The code under test
const logger = require('../logger');
const {Level} = logger;

// The Date.now() stub
let dateNowStub = null;

// Do this before every test
beforeEach(function() {
    dateNowStub = sinon.stub(Date, 'now').returns(1111111111);
});
// Do this after every test
afterEach(function() {
    dateNowStub.restore();
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
    // DEBUG
    describe('Level.DEBUG', function() {
        it('should have a priority order less than Level.INFO', function() {
            expect(Level.DEBUG.priority).to.be.lessThan(Level.INFO.priority);
        });
        it('should have an outputString value of DEBUG', function() {
            expect(Level.DEBUG.outputString).to.equal('DEBUG');
        });
    });
    // INFO
    describe('Level.INFO', function() {
        it('should have a priority order less than Level.WARN', function() {
            expect(Level.INFO.priority).to.be.lessThan(Level.WARN.priority);
        });
        it('should have specific outputString values', function() {
            expect(Level.INFO.outputString).to.equal('INFO');
        });
    });
    // WARN
    describe('Level.WARN', function() {
        it('should have a priority order less than Level.ERROR', function() {
            expect(Level.WARN.priority).to.be.lessThan(Level.ERROR.priority);
        });
        it('should have an outputString value of WARN', function() {
            expect(Level.WARN.outputString).to.equal('WARN');
        });
    });
    // ERROR
    describe('Level.ERROR', function() {
        it('should have a priority order less than Level.SEVERE', function() {
            expect(Level.ERROR.priority).to.be.lessThan(Level.SEVERE.priority);
        });
        it('should have an outputString value of ERROR', function() {
            expect(Level.ERROR.outputString).to.equal('ERROR');
        });
    });
    // SEVERE
    describe('Level.SEVERE', function() {
        it('should have a priority order less than Level.FATAL', function() {
            expect(Level.SEVERE.priority).to.be.lessThan(Level.FATAL.priority);
        });
        it('should have an outputString value of SEVERE', function() {
            expect(Level.SEVERE.outputString).to.equal('SEVERE');
        });
    });
    // FATAL
    describe('Level.FATAL', function() {
        it('should have a priority order less than Level.OFF', function() {
            expect(Level.FATAL.priority).to.be.lessThan(Level.OFF.priority);
        });
        it('should have an outputString value of FATAL', function() {
            expect(Level.FATAL.outputString).to.equal('FATAL');
        });
    });
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
            mockLogFunction = sinon.mock().exactly(5);
        });
        after(function() {
            mockLogFunction.verify();
        });
        // TRACE
        it('should not log a TRACE level message and return null', function() {
            expect(logger.trace('BAR', 'foo()', mockLogFunction)).to.be.null;
        });
        // DEBUG
        it('should not log a DEBUG level message and return null', function() {
            expect(logger.debug('BAR', 'foo()', mockLogFunction)).to.be.null;
        });
        // INFO
        it('should log an INFO level message', function() {
            expect(logger.info('BAR', 'foo()', mockLogFunction))
            .to.equal('1111111111:INFO: foo(): BAR');
        });
        // WARN
        it('should log a WARN level message', function() {
            expect(logger.warn('BAR', 'foo()', mockLogFunction))
            .to.equal('1111111111:WARN: foo(): BAR');
        });
        // ERROR
        it('should log an ERROR level message', function() {
            expect(logger.error('BAR', 'foo()', mockLogFunction))
            .to.equal('1111111111:ERROR: foo(): BAR');
        });
        // SEVERE
        it('should log an SEVERE level message', function() {
            expect(logger.severe('BAR', 'foo()', mockLogFunction))
            .to.equal('1111111111:SEVERE: foo(): BAR');
        });
        // FATAL
        it('should log a FATAL level message', function() {
            expect(logger.fatal('BAR', 'foo()', mockLogFunction))
            .to.equal('1111111111:FATAL: foo(): BAR');
        });
    });
});

/**
 * Tests - Log Levels and the APIs:
 */
describe('Log Levels and the API:', function() {
    describe('when current log Level=TRACE', function() {
        let mockLogFunction;
        before(function() {
            logger.setLogLevel(Level.TRACE);
            mockLogFunction = sinon.mock().exactly(7);
        });
        after(function() {
            mockLogFunction.verify();
        });
        // TRACE
        it('should output TRACE level message', function() {
            expect(logger.trace('whatever', 'whatever()', mockLogFunction))
            .to.equal('1111111111:TRACE: whatever(): whatever');
        });
        // DEBUG
        it('should output DEBUG level message', function() {
            expect(logger.debug('whatever', 'whatever()', mockLogFunction))
            .to.equal('1111111111:DEBUG: whatever(): whatever');
        });
        // INFO
        it('should output INFO level message', function() {
            expect(logger.info('whatever', 'whatever()', mockLogFunction))
            .to.equal('1111111111:INFO: whatever(): whatever');
        });
        // WARN
        it('should output WARN level message', function() {
            expect(logger.warn('whatever', 'whatever()', mockLogFunction))
            .to.equal('1111111111:WARN: whatever(): whatever');
        });
        // ERROR
        it('should output ERROR level message', function() {
            expect(logger.error('whatever', 'whatever()', mockLogFunction))
            .to.equal('1111111111:ERROR: whatever(): whatever');
        });
        // SEVERE
        it('should log an SEVERE level message', function() {
            expect(logger.severe('BAR', 'foo()', mockLogFunction))
            .to.equal('1111111111:SEVERE: foo(): BAR');
        });
        // FATAL
        it('should output FATAL level message', function() {
            expect(logger.fatal('whatever', 'whatever()', mockLogFunction))
            .to.equal('1111111111:FATAL: whatever(): whatever');
        });
    });
    describe('when current log Level=DEBUG', function() {
        let mockLogFunction;
        before(function() {
            logger.setLogLevel(Level.DEBUG);
            mockLogFunction = sinon.mock().exactly(6);
        });
        after(function() {
            mockLogFunction.verify();
        });
        it('should not output TRACE level message', function() {
            expect(logger.trace('whatever', 'whatever()', mockLogFunction)).to.be.null;
        });
        it('should output DEBUG level message', function() {
            expect(logger.debug('whatever', 'whatever()', mockLogFunction))
            .to.equal('1111111111:DEBUG: whatever(): whatever');
        });
        it('should output INFO level message', function() {
            expect(logger.info('whatever', 'whatever()', mockLogFunction))
            .to.equal('1111111111:INFO: whatever(): whatever');
        });
        it('should output WARN level message', function() {
            expect(logger.warn('whatever', 'whatever()', mockLogFunction))
            .to.equal('1111111111:WARN: whatever(): whatever');
        });
        it('should output ERROR level message', function() {
            expect(logger.error('whatever', 'whatever()', mockLogFunction))
            .to.equal('1111111111:ERROR: whatever(): whatever');
        });
        it('should log an SEVERE level message', function() {
            expect(logger.severe('BAR', 'foo()', mockLogFunction))
            .to.equal('1111111111:SEVERE: foo(): BAR');
        });
        it('should output FATAL level message', function() {
            expect(logger.fatal('whatever', 'whatever()', mockLogFunction))
            .to.equal('1111111111:FATAL: whatever(): whatever');
        });
    });
    describe('when current log Level=INFO', function() {
        let mockLogFunction;
        before(function() {
            logger.setLogLevel(Level.INFO);
            mockLogFunction = sinon.mock().exactly(5);
        });
        after(function() {
            mockLogFunction.verify();
        });
        it('should not output TRACE level message', function() {
            expect(logger.trace('whatever', 'whatever()', mockLogFunction)).to.be.null;
        });
        it('should not output DEBUG level message', function() {
            expect(logger.debug('whatever', 'whatever()', mockLogFunction)).to.be.null;
        });
        it('should output INFO level message', function() {
            expect(logger.info('whatever', 'whatever()', mockLogFunction))
            .to.equal('1111111111:INFO: whatever(): whatever');
        });
        it('should output WARN level message', function() {
            expect(logger.warn('whatever', 'whatever()', mockLogFunction))
            .to.equal('1111111111:WARN: whatever(): whatever');
        });
        it('should output ERROR level message', function() {
            expect(logger.error('whatever', 'whatever()', mockLogFunction))
            .to.equal('1111111111:ERROR: whatever(): whatever');
        });
        it('should log an SEVERE level message', function() {
            expect(logger.severe('BAR', 'foo()', mockLogFunction))
            .to.equal('1111111111:SEVERE: foo(): BAR');
        });
        it('should output FATAL level message', function() {
            expect(logger.fatal('whatever', 'whatever()', mockLogFunction))
            .to.equal('1111111111:FATAL: whatever(): whatever');
        });
    });
    describe('when current log Level=WARN', function() {
        let mockLogFunction;
        before(function() {
            logger.setLogLevel(Level.WARN);
            mockLogFunction = sinon.mock().exactly(4);
        });
        after(function() {
            mockLogFunction.verify();
        });
        it('should not output TRACE level message', function() {
            expect(logger.trace('whatever', 'whatever()', mockLogFunction)).to.be.null;
        });
        it('should not output DEBUG level message', function() {
            expect(logger.debug('whatever', 'whatever()', mockLogFunction)).to.be.null;
        });
        it('should not output INFO level message', function() {
            expect(logger.info('whatever', 'whatever()', mockLogFunction)).to.be.null;
        });
        it('should output WARN level message', function() {
            expect(logger.warn('whatever', 'whatever()', mockLogFunction))
            .to.equal('1111111111:WARN: whatever(): whatever');
        });
        it('should output ERROR level message', function() {
            expect(logger.error('whatever', 'whatever()', mockLogFunction))
            .to.equal('1111111111:ERROR: whatever(): whatever');
        });
        it('should log an SEVERE level message', function() {
            expect(logger.severe('BAR', 'foo()', mockLogFunction))
            .to.equal('1111111111:SEVERE: foo(): BAR');
        });
        it('should output FATAL level message', function() {
            expect(logger.fatal('whatever', 'whatever()', mockLogFunction))
            .to.equal('1111111111:FATAL: whatever(): whatever');
        });
    });
    describe('when current log Level=ERROR', function() {
        let mockLogFunction;
        before(function() {
            logger.setLogLevel(Level.ERROR);
            mockLogFunction = sinon.mock().exactly(3);
        });
        after(function() {
            mockLogFunction.verify();
        });
        it('should not output TRACE level message', function() {
            expect(logger.trace('whatever', 'whatever()', mockLogFunction)).to.be.null;
        });
        it('should not output DEBUG level message', function() {
            expect(logger.debug('whatever', 'whatever()', mockLogFunction)).to.be.null;
        });
        it('should not output INFO level message', function() {
            expect(logger.info('whatever', 'whatever()', mockLogFunction)).to.be.null;
        });
        it('should not output WARN level message', function() {
            expect(logger.warn('whatever', 'whatever()', mockLogFunction)).to.be.null;
        });
        it('should output ERROR level message', function() {
            expect(logger.error('whatever', 'whatever()', mockLogFunction))
            .to.equal('1111111111:ERROR: whatever(): whatever');
        });
        it('should log an SEVERE level message', function() {
            expect(logger.severe('BAR', 'foo()', mockLogFunction))
            .to.equal('1111111111:SEVERE: foo(): BAR');
        });
        it('should output FATAL level message', function() {
            expect(logger.fatal('whatever', 'whatever()', mockLogFunction))
            .to.equal('1111111111:FATAL: whatever(): whatever');
        });
    });
    describe('when current log Level=SEVERE', function() {
        let mockLogFunction;
        before(function() {
            logger.setLogLevel(Level.SEVERE);
            mockLogFunction = sinon.mock().exactly(2);
        });
        after(function() {
            mockLogFunction.verify();
        });
        it('should not output TRACE level message', function() {
            expect(logger.trace('whatever', 'whatever()', mockLogFunction)).to.be.null;
        });
        it('should not output DEBUG level message', function() {
            expect(logger.debug('whatever', 'whatever()', mockLogFunction)).to.be.null;
        });
        it('should not output INFO level message', function() {
            expect(logger.info('whatever', 'whatever()', mockLogFunction)).to.be.null;
        });
        it('should not output WARN level message', function() {
            expect(logger.warn('whatever', 'whatever()', mockLogFunction)).to.be.null;
        });
        it('should output ERROR level message', function() {
            expect(logger.error('whatever', 'whatever()', mockLogFunction)).to.be.null;
        });
        it('should log an SEVERE level message', function() {
            expect(logger.severe('BAR', 'foo()', mockLogFunction))
            .to.equal('1111111111:SEVERE: foo(): BAR');
        });
        it('should output FATAL level message', function() {
            expect(logger.fatal('whatever', 'whatever()', mockLogFunction))
            .to.equal('1111111111:FATAL: whatever(): whatever');
        });
    });
    describe('when current log Level=FATAL', function() {
        let mockLogFunction;
        before(function() {
            logger.setLogLevel(Level.FATAL);
            mockLogFunction = sinon.mock().exactly(1);
        });
        after(function() {
            mockLogFunction.verify();
        });
        it('should not output TRACE level message', function() {
            expect(logger.trace('whatever', 'whatever()', mockLogFunction)).to.be.null;
        });
        it('should not output DEBUG level message', function() {
            expect(logger.debug('whatever', 'whatever()', mockLogFunction)).to.be.null;
        });
        it('should not output INFO level message', function() {
            expect(logger.info('whatever', 'whatever()', mockLogFunction)).to.be.null;
        });
        it('should not output WARN level message', function() {
            expect(logger.warn('whatever', 'whatever()', mockLogFunction)).to.be.null;
        });
        it('should not output ERROR level message', function() {
            expect(logger.error('whatever', 'whatever()', mockLogFunction)).to.be.null;
        });
        it('should log an SEVERE level message', function() {
            expect(logger.severe('BAR', 'foo()', mockLogFunction)).to.be.null;
        });
        it('should output FATAL level message', function() {
            expect(logger.fatal('whatever', 'whatever()', mockLogFunction))
            .to.equal('1111111111:FATAL: whatever(): whatever');
        });
    });
    describe('when current log Level=OFF', function() {
        let mockLogFunction;
        before(function() {
            logger.setLogLevel(Level.OFF);
            mockLogFunction = sinon.mock().never();
        });
        after(function() {
            mockLogFunction.verify();
        });
        it('should not output TRACE level message', function() {
            expect(logger.trace('whatever', 'whatever()', mockLogFunction)).to.be.null;
        });
        it('should not output DEBUG level message', function() {
            expect(logger.debug('whatever', 'whatever()', mockLogFunction)).to.be.null;
        });
        it('should not output INFO level message', function() {
            expect(logger.info('whatever', 'whatever()', mockLogFunction)).to.be.null;
        });
        it('should not output WARN level message', function() {
            expect(logger.warn('whatever', 'whatever()', mockLogFunction)).to.be.null;
        });
        it('should not output ERROR level message', function() {
            expect(logger.error('whatever', 'whatever()', mockLogFunction)).to.be.null;
        });
        it('should log an SEVERE level message', function() {
            expect(logger.severe('BAR', 'foo()', mockLogFunction)).to.be.null;
        });
        it('should not output FATAL level message', function() {
            expect(logger.fatal('whatever', 'whatever()', mockLogFunction)).to.be.null;
        });
    });
});

/**
 * Ensure complete code coverage
 */
describe('Code Coverage:', function() {
    // Make sure INFO message will get logged
    before(function() {
        logger.setLogLevel(Level.INFO);
    });
    it('should invoke logMessage() at least once so coverage is 100%', function() {
        // Mock console.log so the output doesn't actually show up
        // The only functino that calls console.log() is logger.logMessage()
        let consoleLogMock = sinon.mock(console).expects('log');
        // Output the message (so logMessage() gets called)
        logger.info('Does not matter, this message will never be seen');
        // Now make sure console.log() was called
        expect(consoleLogMock.called).to.be.true;
        // Restore the actual console.log() function (or the report doesn't show up)
        consoleLogMock.restore();
    });
});
