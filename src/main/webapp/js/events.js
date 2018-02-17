(function (EVENTS, $, undefined) {
    EVENTS.trigger = function (name, args) {
        $(document).trigger(name, [args]);
    };
})(window.EVENTS = window.EVENTS || {}, jQuery);