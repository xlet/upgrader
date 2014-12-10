$(function () {

    $('#side-menu').metisMenu();


    Note.init({
        "selector": ".bb-alert"
    });


});

//Loads the correct sidebar on window load,
//collapses the sidebar on window resize.
// Sets the min-height of #page-wrapper to window size
$(function () {
    $(window).bind("load resize", function () {
        topOffset = 50;
        width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
        if (width < 768) {
            $('div.navbar-collapse').addClass('collapse')
            topOffset = 100; // 2-row-menu
        } else {
            $('div.navbar-collapse').removeClass('collapse')
        }

        height = (this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height;
        height = height - topOffset;
        if (height < 1) height = 1;
        if (height > topOffset) {
            $("#page-wrapper").css("min-height", (height) + "px");
        }
    })

})

// disable text selected after double click
document.ondblclick = function (evt) {
    if (window.getSelection)
        window.getSelection().removeAllRanges();
    else if (document.selection)
        document.selection.empty();
}

var Note = (function () {
    "use strict";

    var elem,
        hideHandler,
        that = {};

    that.init = function (options) {
        elem = $(options.selector);
    };

    that.show = function (text) {
        clearTimeout(hideHandler);

        elem.find(".content").html(text);
        elem.delay(200).fadeIn().delay(4000).fadeOut();
    };

    that.show = function (text, timeout) {
        clearTimeout(hideHandler);
        elem.find(".content").html(text);
        elem.delay(200).fadeIn().delay(timeout).fadeOut();
    };


    return that;
}());

