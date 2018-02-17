var initPage = _.flowRight(loadListOfUsers, initEnvironment);
    
function initEnvironment() {
    _.templateSettings = {
        evaluate: /\{\#([\s\S]+?)\#\}/g,
        interpolate: /\{\{([\s\S]+?)\}\}/g
    };
}

function loadListOfUsers() {
    AJAX.loadListOfUsers(_.partial(EVENTS.trigger, 'successListOfUsers'));
}

$(document)
        .on('ready', {}, initPage)
        .on('successListOfUsers', {}, USERS_LIST.update);
