!function t(e, n, i) {
    function a(r, o) {
        if (!n[r]) {
            if (!e[r]) {
                var l = "function" == typeof require && require;
                if (!o && l)return l(r, !0);
                if (s)return s(r, !0);
                var u = new Error("Cannot find module '" + r + "'");
                throw u.code = "MODULE_NOT_FOUND", u
            }
            var d = n[r] = {"exports": {}};
            e[r][0].call(d.exports, function (t) {
                var n = e[r][1][t];
                return a(n ? n : t)
            }, d, d.exports, t, e, n, i)
        }
        return n[r].exports
    }

    for (var s = "function" == typeof require && require, r = 0; r < i.length; r++)a(i[r]);
    return a
}({
    "1": [function (t, e, n) {
        function i(t) {
            this.lazy = this.baseAPI.getElementsByClass(t), this.fnLoad = this.baseAPI.bind(this, this.load), this.load(), this.baseAPI.on(window, "scroll", this.fnLoad), this.baseAPI.on(window, "resize", this.fnLoad)
        }

        i.prototype = {
            "load": function () {
                var t = document.documentElement.scrollTop || document.body.scrollTop, e = document.documentElement.clientHeight + t, n = 0, i = 0, a = 0, s = this.loaded(0);
                if (this.loaded(1).length != this.lazy.length) {
                    var r = s.length;
                    for (n = 0; n < r; n++) {
                        i = this.baseAPI.pageY(s[n]) - 100, a = this.baseAPI.pageY(s[n]) + s[n].offsetHeight + 100;
                        var o = i > t && i < e, l = a > t && a < e;
                        if (o || l) {
                            var u = this.baseAPI.attr(s[n], "js-src") || "";
                            if (u) {
                                var d = document.createElement("script");
                                d.type = "text/javascript", d.src = u, document.body.appendChild(d)
                            }
                            this.baseAPI.hasClass(s[n], "loaded") || ("" != s[n].className ? s[n].className += " loaded" : s[n].className = "loaded")
                        }
                    }
                }
            }, "loaded": function (t) {
                var e = [], n = 0;
                for (n = 0; n < this.lazy.length; n++) {
                    var i = this.baseAPI.hasClass(this.lazy[n], "loaded");
                    t || i || e.push(this.lazy[n]), t && i && e.push(this.lazy[n])
                }
                return e
            }, "baseAPI": {
                "on": function (t, e, n) {
                    return t.addEventListener ? t.addEventListener(e, n, !1) : t.attachEvent("on" + e, n)
                }, "bind": function (t, e) {
                    return function () {
                        return e.apply(t, arguments)
                    }
                }, "pageX": function (t) {
                    return t.offsetLeft + (t.offsetParent ? arguments.callee(t.offsetParent) : 0)
                }, "pageY": function (t) {
                    return t.offsetTop + (t.offsetParent ? arguments.callee(t.offsetParent) : 0)
                }, "hasClass": function (t, e) {
                    return new RegExp("(^|\\s)" + e + "(\\s|$)").test(t.className)
                }, "attr": function (t, e, n) {
                    return 2 == arguments.length ? t.attributes[e] ? t.attributes[e].nodeValue : void 0 : void(3 == arguments.length && t.setAttribute(e, n))
                }, "getElementsByClass": function (t) {
                    for (var e = [], n = document.getElementsByTagName("*"), i = n.length, a = 0; a < i; a++)this.hasClass(n[a], t) && e.push(n[a]);
                    return e
                }
            }
        }, window.LazyLoadJS = i
    }, {}], "2": [function (t, e, n) {
        function i(t) {
            this.settings = $.extend({
                "targetEle": null, "callback": function () {
                }
            }, t), this.speed = 700, this.autoSpeed = 6e3, this.$targetEle = $(this.settings.targetEle), this.$itemList = this.$targetEle.find(".ps-item"), this.$itemBgList = this.$targetEle.find(".ps-bg-item"), this.$triggerList = this.$targetEle.find(".trigger-item"), this.$triggerPre = this.$targetEle.find(".ps-trigger-pre"), this.$triggerNext = this.$targetEle.find(".ps-trigger-next"), this.animateLock = !1, this.autoTimer = null, this.currentIndex = 0, this.init()
        }

        i.prototype.init = function () {
            this.initStyle(), this.bindEvent(), this.autoPlay()
        }, i.prototype.bindEvent = function () {
            var t = this;
            this.$targetEle.find(".trigger-item").on("click", function () {
                var e = $(this), n = e.index();
                t.animateLock || t.switchTo(n)
            }), this.$targetEle.find(".item-puzzle").on({
                "mouseenter": function () {
                    clearInterval(t.autoTimer)
                }, "mouseleave": function () {
                    t.autoPlay()
                }
            }), this.$targetEle.find(".ps-trigger").on({
                "mouseenter": function () {
                    clearInterval(t.autoTimer)
                }
            }), this.$targetEle.find(".ps-trigger-pre").on({
                "click": function () {
                    var e = t.$itemList.length, n = t.getCurrentIndex(), i = 0 === n ? e - 1 : n - 1;
                    t.animateLock || t.switchTo(i)
                }, "mouseenter": function () {
                    clearInterval(t.autoTimer)
                }, "mouseleave": function () {
                    t.autoPlay()
                }
            }), this.$targetEle.find(".ps-trigger-next").on({
                "click": function () {
                    var e = t.$itemList.length, n = t.getCurrentIndex(), i = n === e - 1 ? 0 : n + 1;
                    t.animateLock || t.switchTo(i)
                }, "mouseenter": function () {
                    clearInterval(t.autoTimer)
                }, "mouseleave": function () {
                    t.autoPlay()
                }
            }), this.$targetEle.on({
                "mouseenter": function () {
                    t.$targetEle.find(".ps-trigger-pre").stop().fadeIn(), t.$targetEle.find(".ps-trigger-next").stop().fadeIn()
                }, "mouseleave": function () {
                    t.$targetEle.find(".ps-trigger-pre").stop().fadeOut(), t.$targetEle.find(".ps-trigger-next").stop().fadeOut()
                }
            })
        }, i.prototype.getCurrentIndex = function () {
            var t = this.$targetEle.find(".trigger-item.current").index();
            return t
        }, i.prototype.initStyle = function () {
            this.$itemBgList.eq(0).css({"z-index": "1", "opacity": "1"}).siblings().css({
                "z-index": "0",
                "opacity": "0"
            }), this.$itemList.eq(0).css({"z-index": "5"}).siblings().css({"z-index": "4"}), this.resetOtherFrames(0)
        }, i.prototype.switchTo = function (t) {
            var e = this, n = this.$itemList.eq(t), i = this.$itemBgList.eq(t);
            this.animateLock = !0, this.$triggerList.eq(t).addClass("current").siblings().removeClass("current"), i.siblings().css({"z-index": "0"}), i.css({"zIndex": "1"}).stop(!0).animate({"opacity": "1"}, this.speed, "easeInOutQuint", function () {
                $(this).siblings().css({"opacity": "0"})
            }), n.siblings().css({"z-index": "4"}), n.css({"zIndex": "5"}).find(".puzzle-item.item-1 .puzzle-item-block").stop(!0).animate({"margin-left": "0px"}, this.speed, "easeInOutQuint", function () {
                e.resetOtherFrames(e.getCurrentIndex()), e.animateLock = !1
            }), n.find(".puzzle-item.item-2 .puzzle-item-block").stop(!0).animate({"margin-left": "0px"}, this.speed - 200, "easeInOutQuint"), n.find(".puzzle-item.item-3 .puzzle-item-block").stop(!0).animate({"margin-left": "0px"}, this.speed - 100, "easeInOutQuint")
        }, i.prototype.resetOtherFrames = function (t) {
            var e = this.$itemList.eq(t);
            e.siblings().each(function () {
                $(this).css({"z-index": "4"}).removeClass("current").removeClass("init"), $(this).find(".puzzle-item").find(".puzzle-item-block").removeAttr("style")
            })
        }, i.prototype.resetFrame = function (t) {
            var e = this.$itemList.eq(t);
            e.css({"z-index": "4"}).removeClass("current").removeClass("init"), e.find(".puzzle-item").find(".puzzle-item-block").removeAttr("style")
        }, i.prototype.autoPlay = function () {
            var t = this, e = this.$itemList.length;
            clearInterval(t.autoTimer), this.autoTimer = setInterval(function () {
                var n = t.getCurrentIndex(), i = n === e - 1 ? 0 : n + 1;
                t.switchTo(i)
            }, t.autoSpeed)
        }, window.Slide = i
    }, {}], "3": [function (t, e, n) {
        t("../plugins/slide.js");
        var i = t("../../snippet/banner.def"), a = t("../../snippet/index_top_ads.def");
        t("../plugins/lazy.loadjs.js"), function (t) {
            new LazyLoadJS("lazyloadjs"), doAjax("/static/mall/banner_ads.json").done(function (e) {
                function n(t) {
                    d = t, r.eq(d).addClass("lunboone").siblings().removeClass("lunboone"), l.animate({"opacity": 0}, 1e3), l.eq(d).stop().animate({"opacity": 1}, 1e3), u && (l.eq(d).attr("data-href") ? (u.attr("href", l.eq(d).attr("data-href")), u.attr("target") || u.attr("target", "_blank"), u.attr("style", "cursor:pointer;")) : (u.attr("href", "javascript:;"), u.removeAttr("target"), u.attr("style", "cursor:default;")))
                }

                function s() {
                    d++, d == r.length && (d = 0), n(d)
                }

                if (e.topBanner.length > 0) {
                    t(".ps-wrap").html(dotRender(i, {"data": e.topBanner || []}));
                    var r = t("#lunbonum li"), o = null, l = t("#lunhuanback p");
                    if (r.length > 1) {
                        if (t("#showhref").length > 0)var u = t("#showhref").eq(0);
                        var d = 0;
                        r.each(function (e) {
                            t(this).click(function () {
                                d = e, n(d)
                            })
                        }), o = setInterval(s, 7e3), r.hover(function () {
                            clearInterval(o)
                        }, function () {
                            o = setInterval(s, 7e3)
                        }), t(".ps-wrap").hover(function () {
                            t(".ps-trigger-pre,.ps-trigger-next").fadeIn(500)
                        }, function () {
                            t(".ps-trigger-pre,.ps-trigger-next").fadeOut(500)
                        }), t(".ps-trigger-next").click(function () {
                            l.stop(), d++, d > r.length - 1 && (d = 0), n(d)
                        }), t(".ps-trigger-pre").click(function () {
                            l.stop(), d--, d < 0 && (d = r.length - 1), n(d)
                        }), t(".ps-trigger-pre,.ps-trigger-next").hover(function () {
                            clearInterval(o)
                        }, function () {
                            o = setInterval(s, 7e3)
                        })
                    }
                }
                e.top_ads.length > 0 && t(".banner-static").html(dotRender(a, {"data": e.top_ads || []}))
            })
        }(jQuery)
    }, {
        "../../snippet/banner.def": 4,
        "../../snippet/index_top_ads.def": 5,
        "../plugins/lazy.loadjs.js": 1,
        "../plugins/slide.js": 2
    }], "4": [function (t, e, n) {
        e.exports = '<div class="lunhuan">\n    <div id="lunhuanback">\n        <a id="showhref" href={%=(it.data.length>0 && it.data[0].imageUrl)?it.data[0].imageUrl:\'javascript:;\'%} target={%=it.data.length>0 && it.data[0].imageUrl?\'_blank\':\'\'%} style={%=it.data.length>0 && it.data[0].imageUrl?"cursor:pointer;":"cursor:default;"%}>\n            {%~it.data:item:index%}\n                {%?index===0%}\n                <p style="background: url(\'{%=item.uri%}\') center center no-repeat;opacity: 1;filter:alpha(opacity=100);" data-href={%=item.imageUrl||\'\'%}></p>\n                {%??%}\n                <p style="background: url(\'{%=item.uri%}\') center center no-repeat;" data-href={%=item.imageUrl||\'\'%}></p>\n                {%?%}\n            {%~%}\n        </a>\n    </div>\n    {%?it.data.length>1%}\n        <div class="lunhuan_main">\n            <ul id="lunbonum">\n                {%~it.data:item:index%}\n                    {%?index===0%}\n                        <li class="lunboone"></li>\n                    {%??%}\n                        <li></li>\n                    {%?%}\n                {%~%}\n            </ul>\n        </div>\n    {%?%}\n</div>\n{%?it.data.length>0%}\n<div class="ps-trigger-pre" style="display: none; opacity: 1;"></div>\n<div class="ps-trigger-next" style="display: none; opacity: 1;"></div>\n{%?%}'
    }, {}], "5": [function (t, e, n) {
        e.exports = '{%~it.data:item:index%}\n<a title="{%=item.title%}" href="{%=item.imageUrl%}">\n  <img alt="{%=item.title%}" src="{%=item.uri%}">\n</a>\n{%~%}'
    }, {}]
}, {}, [3]);