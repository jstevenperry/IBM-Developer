/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

'use strict';

const composerSteps = require('composer-cucumber-steps');
const cucumber = require('cucumber');

module.exports = function () {
    composerSteps.call(this);
};

//
// Custom step - Participants
// Works exactly the same as "I should have the following participants"
// but is more readable for security-based tests (where "should have" is
// misleading)
cucumber.Then(/^I attempt to read the following participants? of type ([.\w]+)\.(\w+)$/, function (namespace, name, table) {
    return this.composer.testParticipants(namespace, name, table);
});

//
// Custom step - Assets
// Works exactly the same as "I should have the following assets"
// but is more readable for security-based tests (where "should have" is
// misleading)
cucumber.Then(/^I attempt to read the following assets? of type ([.\w]+)\.(\w+)$/, function (namespace, name, table) {
    return this.composer.testAssets(namespace, name, table);
});
// Same as above, but for docStrings
cucumber.Then(/^I attempt to read the following assets?$/, function (docString) {
    return this.composer.testAssets(null, null, docString);
});
if (cucumber.defineSupportCode) {
    cucumber.defineSupportCode((context) => {
        module.exports.call(context);
    });
}
