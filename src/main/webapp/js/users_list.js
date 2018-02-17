(function (USERS_LIST, $, _, undefined) {
    var $el, currentUsers, templateFnc;

    function init() {
        $el = $('#mainTable');
        return $el;
    }

    function fillResultData(users) {
        modifyEvents('off');
        $el.html(buildTableBody(users));
        modifyEvents('on');
    }

    function modifyEvents(action) {
        $el.find('.edit')[action]('click', editHandler);
        $el.find('.remove')[action]('click', removeHandler);
    }

    function editHandler() {
        EVENTS.trigger('updateUserRequest', findUserByUserName($(this).data('username')));
    }

    function removeHandler() {
        AJAX.removeUser($(this).data('username'), EVENTS.triggerCurried('successRemoveUser'));
    }

    function buildTableBody(users) {
        return _.reduce(users, function (acc, user) {
            return acc + templateFnc(parseUser(user));
        }, '');
    }

    function parseUser(user) {
        var lastUpdate = user.lastUpdate;
        user.lastUpdate = new Date(lastUpdate).toISOString();
        return user;
    }

    function findUserByUserName(userName) {
        return _.find(currentUsers, function (user) {
            return user.userName === userName;
        });
    }

    USERS_LIST.fetchUsers = function () {
        AJAX.loadListOfUsers(EVENTS.triggerCurried('successFetchUsers'));
    };

    USERS_LIST.paint = function (ev, users) {
        $el = $el || init();
        templateFnc = templateFnc || _.template($('#userRowTemplate').html());
        currentUsers = users;
        fillResultData(users);
    };

})(window.USERS_LIST = window.USERS_LIST || {}, jQuery, _);