(function (USER_FORM, $, _, undefined) {
    var $el;

    function showNotifications(isErrorVisible, isSuccessVisible) {
        $('#errors').toggle(isErrorVisible);
        $('#success').toggle(isSuccessVisible);
    }

    function getSubmitData() {
        return JSON.stringify({
            userName: $('#userName').val(),
            fullName: $('#fullName').val(),
            password: $('#password').val() || null
        });
    }

    function dealWithSubmit(event) {
        event.preventDefault();
        AJAX.updateUser(getSubmitData(), ajaxSuccessCbk, ajaxErrCbk);
    }

    function ajaxSuccessCbk(obj) {
        showNotifications(false, true);
        EVENTS.trigger('successUpdateUser', [obj]);
    }

    function ajaxErrCbk(err) {
        if (_.isEqual(err.status, 400)) {
            showNotifications(true, false);
        }
    }

    function init() {
        $el = $('#userForm');
        $el.submit(dealWithSubmit);
        $el.find('.reset').click(_.partial(showNotifications, false, false));
        return $el;
    }

    function reset() {
        $el.find('.reset').click();
    }

    function updateForm(user) {
        _.forEach(user, updateField);
    }

    function updateField(value, key) {
        $('#' + key).val(value);
    }

    USER_FORM.init = function () {
        $el = $el || init();
        reset();
    };

    USER_FORM.update = function (ev, user) {
        reset();
        updateForm(user);
    };

})(window.USER_FORM = window.USER_FORM || {}, jQuery, _);