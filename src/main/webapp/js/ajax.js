(function (AJAX, $, _, undefined) {
    AJAX.loadListOfUsers = _.bind($.get, $, 'resources/appuser');
    AJAX.removeUser = function(userName, cbk) {
        $.ajax({
            url: 'resources/appuser/' + userName,
            type: 'DELETE',
            success: cbk
        });
    };
    AJAX.updateUser = function (data, cbk) {
        $.ajax({
            url: 'resources/appuser',
            type: 'PUT',
            data: data,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            success: cbk
        });
    };
})(window.AJAX = window.AJAX || {}, jQuery, _);