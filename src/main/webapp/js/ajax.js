(function (AJAX, $, _, undefined) {
    AJAX.loadListOfUsers = _.bind($.get, $, 'resources/appuser');
})(window.AJAX = window.AJAX || {}, jQuery, _);