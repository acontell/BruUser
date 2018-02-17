(function (EVENTS, $, undefined) {
    EVENTS.trigger = function (name, args) {
        $(document).trigger(name, [args]);
    };
    EVENTS.triggerCurried = _.curry(EVENTS.trigger);
})(window.EVENTS = window.EVENTS || {}, jQuery);