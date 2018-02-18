'use strict';

var _ = require('./util/functional'),
    config = {
    testServer: {
        url: 'http://localhost',
        port: 8080,
        app: 'bruuser',
        getScreenshotFilePath: function (scenarioName) {
            var uniqueName = _.uniqueId(scenarioName.replace(/ /g, '_').substring(0, 20));
            return process.cwd() + '/features/screenshots/' + uniqueName + '.png';
        }
    }
};

module.exports = config;
