(function (AJAX, $, _, undefined) {
    AJAX.loadListOfUsers = _.bind($.get, $, 'resources/appuser');
    AJAX.removeUser = function(userName, cbk) {
        $.ajax({
            url: 'resources/appuser/' + userName,
            type: 'DELETE',
            success: cbk
        });
    };
})(window.AJAX = window.AJAX || {}, jQuery, _);