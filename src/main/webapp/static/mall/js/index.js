/**
 * Created by chenxinglin
 */

$(function () {

    followDo();
    buyBox();
    bannerDo();
    rollDo();

});


/**
 * 关注事件
 */
var followDo = function() {
    $('.follow').on('click', function () {
        $this = $(this);
        $.getJSON(ctx + "/mall/follow",
            {goodsId : $this.attr("goodsId")},
            function (res) {console.log(res);
                if(res.status == 'success'){
                    if ($this.hasClass("fa-heart-o")){ //关注
                        $this.removeClass("fa-heart-o").addClass("fa-heart orange");
                        showFocusTip($this.parent().find(".focus-tip"), "关注成功");
                        $this.attr("title", "已关注");
                    }else { //取消关注
                        $this.removeClass("fa-heart orange").addClass("fa-heart-o");
                        showFocusTip($this.parent().find(".focus-tip"), "取消关注");
                        $this.attr("title", "加关注");
                    }
                }else {console.log(res.message);
                    showFocusTip($this.parent().find(".focus-tip"), res.message);
                }
            });

    });
}

/**
 * 关注提示
 */
var showFocusTip = function (e, t) {
    e.html(t).animate({"opacity": 1, "right": 50}, 500), e.html(t).animate({
        "opacity": 0,
        "right": 55
    }, 500, function () {
        e.css({"top": 20, "right": 40, "opacity": 0})
    })
}

/**
 * 立即购买显示事件
 */
var buyBox = function (e, t) {
    $("body").on("mouseenter mouseleave", ".benefit-item,.gift-item,.box-big", function (t) {
        switch (t.type) {
            case"mouseenter":
                $(this).find(".buy").stop().animate({"bottom": 15, "opacity": 1}, 200);
                break;
            case"mouseleave":
                $(this).find(".buy").stop().animate({"bottom": -30, "opacity": 0}, 200)
        }
    });
}

/**
 * banner事件
 */
var bannerDo = function() {
    var e = window.event;
    function n(t) {
        d = t, r.eq(d).addClass("lunboone").siblings().removeClass("lunboone"), l.animate({"opacity": 0}, 1e3), l.eq(d).stop().animate({"opacity": 1}, 1e3), u && (l.eq(d).attr("data-href") ? (u.attr("href", l.eq(d).attr("data-href")), u.attr("target") || u.attr("target", "_blank"), u.attr("style", "cursor:pointer;")) : (u.attr("href", "javascript:;"), u.removeAttr("target"), u.attr("style", "cursor:default;")))
    }

    function s() {
        d++, d == r.length && (d = 0), n(d)
    }

    if (3 > 0) {
        // $(".ps-wrap").html(dotRender(i, {"data": e.topBanner || []}));
        var r = $("#lunbonum li"), o = null, l = $("#lunhuanback p");
        if (r.length > 1) {
            if ($("#showhref").length > 0)var u = $("#showhref").eq(0);
            var d = 0;
            r.each(function (e) {
                $(this).click(function () {
                    d = e, n(d)
                })
            }), o = setInterval(s, 7e3), r.hover(function () {
                clearInterval(o)
            }, function () {
                o = setInterval(s, 7e3)
            }), $(".ps-wrap").hover(function () {
                $(".ps-trigger-pre,.ps-trigger-next").fadeIn(500)
            }, function () {
                $(".ps-trigger-pre,.ps-trigger-next").fadeOut(500)
            }), $(".ps-trigger-next").click(function () {
                l.stop(), d++, d > r.length - 1 && (d = 0), n(d)
            }), $(".ps-trigger-pre").click(function () {
                l.stop(), d--, d < 0 && (d = r.length - 1), n(d)
            }), $(".ps-trigger-pre,.ps-trigger-next").hover(function () {
                clearInterval(o)
            }, function () {
                o = setInterval(s, 7e3)
            })
        }
    }
    n(0);
    // e.top_ads.length > 0 && $(".banner-static").html(dotRender(a, {"data": e.top_ads || []}))
};

/**
 * 滚动事件
 */
var rollDo = function() {
    if(giftsCount>1){
        var i = 0, j = 1198, k = giftsCount-1;
        $('.bx-prev').on('click', function () {
            i -= j;
            if(i < 0){
                i = j * k;
            }
            console.log(i);
            var str = "translate3d(-" + i + "px, 0px, 0px)";
            $(".gift-shop-slide").css("transform", str);
        });
        $('.bx-next').on('click', function () {
            i += j;
            if(i > j * k){
                i = 0;
            }
            console.log(i);
            var str = "translate3d(-" + i + "px, 0px, 0px)";
            $(".gift-shop-slide").css("transform", str);
        });
    }
};
