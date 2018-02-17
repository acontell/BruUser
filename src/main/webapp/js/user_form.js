(function (USER_FORM, $, undefined) {
    var $el;

    function setLoginFormState(isErrorVisible, isLoadingVisible, isSubmitDisabled) {
        $el.find('.notifications').toggle(isErrorVisible || isLoadingVisible);
        $el.find('.errors').toggle(isErrorVisible);
        $el.find('.loading').toggle(isLoadingVisible);
        $el.find('.submitBtn').prop('disabled', isSubmitDisabled);
    }

    function getSubmitData() {
        return JSON.stringify({
            userName: $('#userName').val(),
            fullName: $('#fullName').val(),
            password: $('#password').val()
        });
    }

    function dealWithSubmit(event) {
        event.preventDefault();
        //setLoginFormState(false, true, true);
        AJAX.updateUser(getSubmitData(), ajaxSuccessCbk);
    }

    function ajaxSuccessCbk(obj) {
        //setLoginFormState(!obj.isLoggedIn, false, false);
        reset();
        EVENTS.trigger('successUpdateUser', [obj]);
    }

    function init() {
        $el = $('#userForm');
        $el.submit(dealWithSubmit);
        return $el;
    }

    function reset() {
        setLoginFormState(false, false, false);
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
    };

    USER_FORM.update = function (ev, user) {
        reset();
        updateForm(user);
    };

})(window.USER_FORM = window.USER_FORM || {}, jQuery);