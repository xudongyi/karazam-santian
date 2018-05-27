$(function() {
    $(".userInfo li").mouseenter(function () {
        $(this).find(".tip_info").show();
        $(this).siblings().find(".tip_info").hide();
    });
    $(".userInfo li").mouseleave(function () {
        var _this = this;
        setTimeout(function () {
            $(_this).find(".tip_info").hide();
        }, 500)
    });
});