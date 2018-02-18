'use strict';

var _ = require(process.cwd() + '/util/functional'),
        steps = function () {
            this.Given('the user has reached home page', function (callback) {
                this.page
                        .waitForPageToBeLoaded()
                        .then(_.constant(null), _.identity)
                        .then(callback);
            });

            this.When('the user fills user form', function (callback) {
                this.page
                        .fillUserForm({
                            userName: 'acontell',
                            fullName: 'Arcadio Contell Monje',
                            password: '4f3dDdcadf234'
                        })
                        .then(callback);
            });

            this.When('the user clicks send button in user form', function (callback) {
                this.page
                        .clickSendButton()
                        .then(callback);
            });
        };

module.exports = steps;
