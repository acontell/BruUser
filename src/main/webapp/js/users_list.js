(function (USERS_LIST, $, _, undefined) {
    var $el, templateFnc;

    function init() {
        $el = $('#mainTable');
        return $el;
    }

    function fillResultData(users) {
        $el.html(buildTableBody(users));
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

    USERS_LIST.fetchUsers = _.partial(AJAX.loadListOfUsers, _.partial(EVENTS.trigger, 'successFetchUsers'));

    USERS_LIST.paint = function (ev, users) {
        $el = $el || init();
        templateFnc = templateFnc || _.template($('#userRowTemplate').html());
        fillResultData(users);
    };

})(window.USERS_LIST = window.USERS_LIST || {}, jQuery, _);