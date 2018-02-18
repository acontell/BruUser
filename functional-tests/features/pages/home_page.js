'use strict';
var _ = require('../../util/functional');

function HomePage(driver) {
    var containers = {
        usersTable: '#mainTable',
        form: {
            locator: '#userForm',
            fields: {
                userName: '#userName',
                fullName: '#fullName',
                password: '#password'
            },
            sendButton: '#sendButton'
        }
    };
    this.pageLoadedElement = containers.usersTable;
    this.timeout = 6000;

    this.waitForPageToBeLoaded = function () {
        return driver
                .findElement(this.pageLoadedElement)
                .isDisplayed();
    };

    this.fillUserForm = function (userData) {
        return _.reduce(userData, function (promise, value, key) {
            return promise.then(function () {
                return driver.sendKeys(containers.form.fields[key], value);
            });
        }, driver.findElement(containers.form.locator));
    };

    this.clickSendButton = function () {
        return driver.clickElement(containers.form.sendButton)
                .then(_.constant(null));
    };

    this.testUserTableLengthEq = function (number) {
        return driver.findElements(containers.usersTable + " > tr")
                .then(function (els) {
                    return els.length === number ? null : 'ERROR';
                });
    };
}

module.exports = HomePage;
