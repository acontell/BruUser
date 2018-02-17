(function (USERS_LIST, $, undefined) {
    var $el;

    function setLoginFormState(isErrorVisible, isLoadingVisible, isSubmitDisabled) {
        $el.find('.notifications').toggle(isErrorVisible || isLoadingVisible);
        $el.find('.errors').toggle(isErrorVisible);
        $el.find('.loading').toggle(isLoadingVisible);
        $el.find('.submitBtn').prop('disabled', isSubmitDisabled);
    }

    function getSubmitData() {
        return JSON.stringify({userName: $el.find('.username').val(), password: $el.find('.password').val()});
    }

    function dealWithSubmit(event) {
        event.preventDefault();
        setLoginFormState(false, true, true);
        AJAX.authenticate(getSubmitData(), ajaxSuccessCbk);
    }

    function ajaxSuccessCbk(obj) {
        setLoginFormState(!obj.isLoggedIn, false, false);
        EVENTS.triggerIf(obj.isLoggedIn, 'successfulLogin', [obj]);
    }

    function init() {
        $el = $('#loginForm');
        $el.find('.submitFrm').submit(dealWithSubmit);
        return $el;
    }

    function reset() {
        setLoginFormState(false, false, false);
        $el.find('.username').val('');
        $el.find('.password').val('');
    }

    LOGIN_FORM.toggle = function (isVisible) {
        return function (ev, obj) {
            $el = $el || init();
            reset();
            $el.toggle(isVisible);
            return obj;
        };
    };

})(window.USERS_LIST = window.USERS_LIST || {}, jQuery);