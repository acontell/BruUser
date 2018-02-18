'use strict';

var extendedDriver = require(process.cwd() + '/util/fe_tests_driver_factory'),
    HomePage = require('../pages/home_page');

function World() {
    // There's only one page, so no need for flow handler and we can set it here.
    this.driver = extendedDriver;
    this.page = new HomePage(extendedDriver);
}

module.exports = function () {
    this.World = World;
};
