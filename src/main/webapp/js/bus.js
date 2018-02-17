var initEnvironment = _.flowRight(USER_FORM.init, USERS_LIST.init, initTemplateSettings);

function initTemplateSettings() {
    _.templateSettings = {
        evaluate: /\{\#([\s\S]+?)\#\}/g,
        interpolate: /\{\{([\s\S]+?)\}\}/g
    };
}

$(document)
        .on('ready', {}, _.flowRight(USERS_LIST.fetchUsers, initEnvironment))
        .on('successFetchUsers', {}, USERS_LIST.paint)
        .on('successRemoveUser', {}, USERS_LIST.fetchUsers)
        .on('successUpdateUser', {}, USERS_LIST.fetchUsers)
        .on('updateUserRequest', {}, USER_FORM.update);
