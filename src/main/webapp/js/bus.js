function initEnvironment() {
    _.templateSettings = {
        evaluate: /\{\#([\s\S]+?)\#\}/g,
        interpolate: /\{\{([\s\S]+?)\}\}/g
    };
}

$(document)
        .on('ready', {}, _.flowRight(USERS_LIST.fetchUsers, initEnvironment))
        .on('successFetchUsers', {}, USERS_LIST.paint)
        .on('successRemoveUser', {}, USERS_LIST.fetchUsers)
        .on('updateUserRequest', {}, console.log);
