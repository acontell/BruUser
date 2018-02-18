'use strict';
var _ = require('../../util/functional');

function HomePage(driver) {
    var containers = {
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
    this.pageLoadedElement = '#mainTable';
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
        return driver.clickElement(containers.form.sendButton);
    };
}

module.exports = HomePage;
