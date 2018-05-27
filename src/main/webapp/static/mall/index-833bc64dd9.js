!function t(e, n, i) {
    function a(s, o) {
        if (!n[s]) {
            if (!e[s]) {
                var l = "function" == typeof require && require;
                if (!o && l)return l(s, !0);
                if (r)return r(s, !0);
                var c = new Error("Cannot find module '" + s + "'");
                throw c.code = "MODULE_NOT_FOUND", c
            }
            var u = n[s] = {"exports": {}};
            e[s][0].call(u.exports, function (t) {
                var n = e[s][1][t];
                return a(n ? n : t)
            }, u, u.exports, t, e, n, i)
        }
        return n[s].exports
    }

    for (var r = "function" == typeof require && require, s = 0; s < i.length; s++)a(i[s]);
    return a
}({
    "1": [function (t, e, n) {
        function i() {
            return String((new Date).getTime()).replace(/\D/gi, "")
        }

        window.AeroDialog = {
            "minWidth": 250,
            "maxWidth": 333,
            "Cover": {"opacity": .5, "background": "#000"},
            "Flash": !1,
            "WinFlash": {"Speed": 300, "FlashMode": "easeOutCubic"},
            "Btntxt": {
                "OK": "\u786e \u5b9a",
                "NO": " \u5426 ",
                "YES": " \u662f ",
                "CANCEL": "\u53d6 \u6d88",
                "CLOSE": "\u5173\u95ed"
            },
            "links": {"OK": null, "NO": null, "YES": null, "CANCEL": null, "CLOSE": null}
        }, function (t) {
            function e(t, e, n) {
                return ['<div class="AeroWindow">', '<div class="inner-container">', '<div class="titles winBtns"><span class="title">', t, "</span>", e, "</div>", '<div class="tb-mm-container">', n, "</div>", "</div></div>"].join("")
            }

            function n() {
                return '<div id="win-dialog-cover" style="width:100%; height:100%; margin:0px; padding:0px; position:fixed;top:0px; left:0px;z-index:10000;background-color:' + u.Cover.background + "; opacity:" + u.Cover.opacity + ';filter:alpha(opacity=50);display:block; "></div>'
            }

            function a(e) {
                var n, i = u.btn.CLOSE.concat(e.buttons);
                t.each(i, function (i, a) {
                    t("#" + e.id + "_" + a.result).click(function (i) {
                        var r = t(this);
                        return r.attr("disabled", "disabled"), n = e.callback(a.result), ("undefined" == typeof n || n) && u.close(e.id), r.removeAttr("disabled")
                    })
                })
            }

            function r(e, n) {
                var a = {
                    "id": "window-" + i(),
                    "icon": "",
                    "title": "",
                    "type": "window",
                    "content": "",
                    "top": 0,
                    "left": 0,
                    "callback": "undefined" == typeof n ? t.noop : n
                }, r = t.extend(!0, a, e);
                r.WinTitle = r.title, r.buttons = [], o(r)
            }

            function s(e, n, a, r) {
                var s = "dialog" + i() + "-" + r, l = {
                    "id": s,
                    "icon": r,
                    "WinTitle": "undefined" == typeof n ? r : n,
                    "type": r,
                    "content": e,
                    "top": 0,
                    "left": 0,
                    "callback": "undefined" == typeof a ? t.noop : a
                };
                switch (l.buttons = u.btn.OK, r) {
                    case"confirm":
                        l.buttons = u.btn.OKCANCEL;
                        break;
                    case"warning":
                        l.buttons = u.btn.YESNOCANCEL
                }
                o(l)
            }

            function o(i) {
                var r = '<a id="' + i.id + "_" + u.btn.CLOSE[0].result + '" href="javascript:void(0)" class="win-closebtn fa fa-close" title="' + u.btn.CLOSE[0].title + '"></a>';
                t(window.top.document).find("body").append(n()), t("body").append('<div id="' + i.id + '">' + e(i.WinTitle, r, c(i)) + "</div>");
                var s = t(".dialog-content").width();
                t(".dialog-content").css({"width": s > u.minWidth ? s : u.maxWidth});
                var o = t("#" + i.id), l = o.find(".AeroWindow"), p = t(window).height() / 2 - l.height() / 2 - 20, d = t(window).width() / 2 - l.width() / 2;
                o.css({
                    "width": l.width(),
                    "height": l.height(),
                    "position": "fixed",
                    "z-index": 10001,
                    "top": "0px",
                    "left": d + "px",
                    "background-color": "#ffffff"
                }), o.animate({"top": p}, {"duration": 300}), a(i)
            }

            function l(e) {
                var n = [];
                return t.each(e.buttons, function (t, i) {
                    n.push('<a class="d-btn d-' + i.result + '" id="', e.id, "_", i.result, '" href="', i.link ? i.link : "javascript:void(0)", '"><span> ', i.value, " </span></a>")
                }), n.join("")
            }

            function c(t) {
                var e = "";
                return ['<div class="tb-mm-dialog">', '<table border="0" cellspacing="0" cellpadding="0">', "  <tr>", e, '\t<td><div class="dialog-content">', t.content, "</div></td>", "\t</tr>", "\t<tr>", '\t  <td colspan="2" class="d-btns">', l(t), "\t  </td>", "\t</tr>", "</table>", "</div>"].join("")
            }

            var u = AeroDialog;
            u.btn = {
                "OK": [{"value": u.Btntxt.OK, "link": u.links.OK, "result": "ok"}],
                "NO": [{"value": u.Btntxt.NO, "link": u.links.NO, "result": "no"}],
                "YES": [{"value": u.Btntxt.YES, "link": u.links.YES, "result": "yes"}],
                "CANCEL": [{"value": u.Btntxt.CANCEL, "link": u.links.CANCEL, "result": "cancel"}],
                "CLOSE": [{"title": u.Btntxt.CLOSE, "result": "close"}]
            }, u.btn.OKCANCEL = u.btn.OK.concat(u.btn.CANCEL), u.btn.YESNO = u.btn.YES.concat(u.btn.NO), u.btn.YESNOCANCEL = u.btn.CANCEL.concat(u.btn.NO).concat(u.btn.YES), u.btn.WINDOW = u.btn.CLOSE, u.close = function (e) {
                t("#" + e).remove(), t(window.top.document).find("#win-dialog-cover").remove()
            }, u.window = function (t, e) {
                r(t, e)
            }, u.alert = function (t, e, n) {
                s(t, e, n, "alert")
            }, u.html = function (t, e, n) {
                s(t, e, n, "html")
            }, u.confirm = function (t, e, n) {
                s(t, e, n, "confirm")
            }, u.success = function (t, e, n) {
                s(t, e, n, "success")
            }, u.warning = function (t, e, n) {
                s(t, e, n, "warning")
            }, u.error = function (t, e, n) {
                s(t, e, n, "error")
            }, u.question = function (t, e, n) {
                s(t, e, n, "question")
            }
        }(jQuery)
    }, {}], "2": [function (t, e, n) {
        !function (t) {
            var e = {
                "init": function () {
                    return function () {
                        e.fillHtml(), e.bindEvent()
                    }()
                }, "fillHtml": function () {
                    return function () {
                        e.element.empty(), e.args.current > 1 ? e.element.append('<div class="btn-page pre"><</div>') : (e.element.remove(".pre"), e.element.append('<div class="btn-page disabled"><</div>')), 1 != e.args.current && e.args.current >= 4 && 4 != e.args.pageCount && e.element.append('<div class="btn-page tcdNumber">1</div>'), e.args.current - 2 > 2 && e.args.current <= e.args.pageCount && e.args.pageCount > 5 && e.element.append('<div class="btn-page more">...</div>');
                        var t = e.args.current - 2, n = e.args.current + 2;
                        for ((t > 1 && e.args.current < 4 || 1 == e.args.current) && n++, e.args.current > e.args.pageCount - 4 && e.args.current >= e.args.pageCount && t--; t <= n; t++)t <= e.args.pageCount && t >= 1 && (t != e.args.current ? e.element.append('<div class="btn-page tcdNumber">' + t + "</div>") : e.element.append('<div class="btn-page current">' + t + "</div>"));
                        e.args.current + 2 < e.args.pageCount - 1 && e.args.current >= 1 && e.args.pageCount > 5 && e.element.append('<div class="btn-page more">...</div>'), e.args.current != e.args.pageCount && e.args.current < e.args.pageCount - 2 && 4 != e.args.pageCount && e.element.append('<div class="btn-page tcdNumber">' + e.args.pageCount + "</div>"), e.args.current < e.args.pageCount ? e.element.append('<div class="btn-page next">></div>') : (e.element.remove(".next"), e.element.append('<div class="btn-page disabled">></div>'))
                    }()
                }, "bindEvent": function () {
                    return function () {
                        e.element.on("click", ".tcdNumber", function () {
                            if ("function" == typeof e.args.beforeFn) {
                                var n = e.args.beforeFn();
                                if (!n)return
                            }
                            var i = parseInt(t(this).text());
                            e.args.current = i, e.fillHtml(), "function" == typeof e.args.backFn && e.args.backFn(i)
                        }), e.element.on("click", ".pre", function () {
                            if ("function" == typeof e.args.beforeFn) {
                                var t = e.args.beforeFn();
                                if (!t)return
                            }
                            var n = parseInt(e.element.children(".current").text());
                            e.args.current = n - 1, e.fillHtml(), "function" == typeof e.args.backFn && e.args.backFn(n - 1)
                        }), e.element.on("click", ".next", function () {
                            if ("function" == typeof e.args.beforeFn) {
                                var t = e.args.beforeFn();
                                if (!t)return
                            }
                            var n = parseInt(e.element.children(".current").text());
                            e.args.current = n + 1, e.fillHtml(), "function" == typeof e.args.backFn && e.args.backFn(n + 1)
                        })
                    }()
                }, "reset": function (n) {
                    e.args = t.extend(e.args, n), e.fillHtml()
                }
            };
            t.fn.createPage = function (n) {
                var i = t.extend({
                    "pageCount": 10, "current": 1, "backFn": function () {
                    }, "beforeFn": function () {
                        return !0
                    }
                }, n);
                return e.element = this, e.args = i, e.init(), e
            }
        }(jQuery)
    }, {}], "3": [function (t, e, n) {
        function i(t) {
            this.lazy = this.baseAPI.getElementsByClass(t), this.fnLoad = this.baseAPI.bind(this, this.load), this.load(), this.baseAPI.on(window, "scroll", this.fnLoad), this.baseAPI.on(window, "resize", this.fnLoad)
        }

        i.prototype = {
            "load": function () {
                var t = document.documentElement.scrollTop || document.body.scrollTop, e = document.documentElement.clientHeight + t, n = 0, i = 0, a = 0, r = this.loaded(0);
                if (this.loaded(1).length != this.lazy.length) {
                    var s = r.length;
                    for (n = 0; n < s; n++) {
                        i = this.baseAPI.pageY(r[n]) - 100, a = this.baseAPI.pageY(r[n]) + r[n].offsetHeight + 100;
                        var o = i > t && i < e, l = a > t && a < e;
                        if (o || l) {
                            var c = this.baseAPI.attr(r[n], "js-src") || "";
                            if (c) {
                                var u = document.createElement("script");
                                u.type = "text/javascript", u.src = c, document.body.appendChild(u)
                            }
                            this.baseAPI.hasClass(r[n], "loaded") || ("" != r[n].className ? r[n].className += " loaded" : r[n].className = "loaded")
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
    }, {}], "4": [function (t, e, n) {
        "use strict";
        var i = t("../products/product_list.js"), a = t("../common/getMyFavorites");
        a($, function (t) {
            new i({"myFavorites": t})
        })
    }, {"../common/getMyFavorites": 5, "../products/product_list.js": 6}], "5": [function (t, e, n) {
        "use strict";
        var i = function (t, e) {
            var n = [];
            t("[role=navigation]").attr("un-id") ? t.when(t.get("favorites.json?page=1&pageSize=99999999")).then(function (t) {
                if (t.success && t.data.results.length > 0)for (var i = t.data.results, a = 0; a < i.length; a++)n.push(i[a].productId);
                e(n)
            }, function () {
                e(n)
            }) : e(n)
        };
        e.exports = i
    }, {}], "6": [function (t, e, n) {
        "use strict";
        function i(e) {
            this.opts = $.extend({
                "apiurl": "list1.json",
                "filter": {
                    "cid1": "0B115B4C-B7E2-4BBD-B944-6F5CDFB62A3D",
                    "cid2": "",
                    "ev": "",
                    "psort": 1,
                    "page": 1,
                    "pageSize": 16
                },
                "myFavorites": [],
                "$listContainer": $(".benefits-list .list"),
                "$currentPage": $(".find-count .current-page"),
                "$totalCount": $(".benefits-list .total-count"),
                "$totalPage": $(".benefits-list .total-page"),
                "$pageBar": $(".page-bar"),
                "tpl": t("../../../snippet/product_list.def")
            }, e), this.init()
        }

        t("../../plugins/jquery.page.js"), t("../../plugins/lazy.loadjs.js"), t("../../plugins/jquery.aerowindow"), i.prototype.init = function () {
            this.pageBar = null, this.renderList(), this.bindEvents(), new LazyLoadJS("lazyloadjs")
        }, i.prototype.checkSearchBox = function () {
            var t = "", e = $("input[name=price-min]").val(), n = $("input[name=price-max]").val();
            return "" != e && isNaN(e) || e < 0 ? (AeroDialog.alert("\u8f93\u5165\u5185\u5bb9\u4e0d\u6b63\u786e\uff0c\u8bf7\u91cd\u65b0\u8f93\u5165\u6570\u5b57\uff01", "\u63d0\u793a"), !1) : "" != n && isNaN(n) || n < 0 ? (AeroDialog.alert("\u8f93\u5165\u5185\u5bb9\u4e0d\u6b63\u786e\uff0c\u8bf7\u91cd\u65b0\u8f93\u5165\u6570\u5b57\uff01", "\u63d0\u793a"), !1) : t = "" == e && "" == n ? "" : "exprice_" + ("" == e ? "min" : e) + "-" + ("" == n ? "max" : n)
        }, i.prototype.renderList = function () {
            var t = this;
            $.when($.get(t.opts.apiurl, t.opts.filter)).then(function (e) {
                var n = 0;
                if (e.data && e.data.results) {
                    n = e.data.totalSize;
                    for (var i = e.data.results, a = [], r = 0; r < i.length; r++) {
                        var s = {};
                        s.id = i[r].id, s.url = redirect.mall("/products/detail/" + i[r].id), s.imgUrl = i[r].images.length > 0 ? i[r].images[0] : "", s.name = i[r].name, s.price = i[r].pointsNeeded, s.leftCount = i[r].inventory, s.desc = i[r].briefProduct || "", s.isFavorited = _.contains(t.opts.myFavorites, i[r].id), a.push(s)
                    }
                    var o = '<p class="nodata"><i class="bg"></i>\u672a\u627e\u5230\u5546\u54c1\uff01</p>';
                    t.opts.$listContainer.html(a.length > 0 ? dotRender(t.opts.tpl, a) : o), t.opts.$currentPage.html(t.opts.filter.page), t.opts.$totalCount.html(n), t.opts.$totalPage.html(Math.ceil(n / t.opts.filter.pageSize)), $(".my-points .mynum").html(n), Math.ceil(n / t.opts.filter.pageSize) > 1 ? t.opts.$pageBar.show() : t.opts.$pageBar.hide(), t.pageBar ? t.pageBar.reset({
                        "pageCount": Math.ceil(n / t.opts.filter.pageSize),
                        "current": t.opts.filter.page
                    }) : t.pageBar = t.opts.$pageBar.createPage({
                        "pageCount": Math.ceil(n / t.opts.filter.pageSize),
                        "current": t.opts.filter.page,
                        "beforeFn": function () {
                            if ($("input[name=price-min]").length > 0) {
                                var e = t.checkSearchBox();
                                return e !== !1 && (t.opts.filter.ev = e, !0)
                            }
                            return !0
                        },
                        "backFn": function (e) {
                            t.opts.filter.page = e, t.opts.$currentPage.html(e), t.renderList()
                        }
                    })
                }
            })
        }, i.prototype.bindEvents = function () {
            var t = this, e = $(".classify-bottom-line").css("left");
            $(".classify .item").hover(function () {
                var t = 200 * $(this).index();
                $(".classify-bottom-line").stop().animate({"left": t}, 200)
            }, function () {
                $(".classify-bottom-line").stop().animate({"left": e}, 200)
            }), $(".classify .item").click(function () {
                $(this).hasClass("current") || ($(this).addClass("current").siblings().removeClass("current"), $(".classify-bottom-line").css("left", 200 * $(this).index()), e = 200 * $(this).index(), t.opts.filter.cid2 = $(this).attr("data-id"), t.renderList())
            }), $(".sort-type").click(function () {
                if ($(this).hasClass("by-point") || !$(this).hasClass("current")) {
                    if ($("input[name=price-min]").length > 0) {
                        var e = t.checkSearchBox();
                        if (e === !1)return;
                        t.opts.filter.ev = e
                    }
                    $(this).addClass("current").siblings().removeClass("current");
                    var n = $(this).attr("data-type"), i = n;
                    $(this).hasClass("by-point") ? (i = 4 == n ? 5 : 4, $(this).attr("data-type", i)) : $(".by-point").attr("data-type", ""), t.opts.filter.page = 1, t.opts.filter.psort = i, t.renderList()
                }
            }), $(".search-box .btn-search").click(function () {
                var e = t.opts.filter.ev, n = t.checkSearchBox();
                n !== !1 && e != n && (t.opts.filter.ev = n, t.opts.filter.page = 1, t.renderList())
            }), $(".find-count .pre-page").click(function () {
                var e = parseInt(t.opts.$currentPage.html());
                if (!(e <= 1)) {
                    if ($("input[name=price-min]").length > 0) {
                        var n = t.checkSearchBox();
                        if (n === !1)return;
                        t.opts.filter.ev = n
                    }
                    t.opts.filter.page = e - 1, t.renderList()
                }
            }), $(".find-count .next-page").click(function () {
                var e = parseInt(t.opts.$currentPage.html()), n = parseInt(t.opts.$totalPage.html());
                if (!(e >= n)) {
                    if ($("input[name=price-min]").length > 0) {
                        var i = t.checkSearchBox();
                        if (i === !1)return;
                        t.opts.filter.ev = i
                    }
                    t.opts.filter.page = e + 1, t.renderList()
                }
            }), $("body").on("click", ".box .follow,.box-big .follow", function () {
                var e = !!$("[role=navigation]").attr("un-id");
                if (!e)return void toLogin();
                var n = $(this), i = $(this).parent().attr("data-product-id");
                n.hasClass("orange") ? $.when($.post("/mall/api/v2/favorite/user/MYSELF/product/" + i + "/cancel")).then(function (e) {
                    e.success ? (showFocusTip(n.parent().find(".focus-tip"), "\u53d6\u6d88\u5173\u6ce8"), n.removeClass("fa-heart orange").addClass("fa-heart-o"), n.attr("title", "\u52a0\u5173\u6ce8"), t.opts.myFavorites = _.without(t.opts.myFavorites, i)) : showFocusTip(n.parent().find(".focus-tip"), "\u53d6\u6d88\u5173\u6ce8\u5931\u8d25")
                }) : $.when($.post("/mall/api/v2/favorite/user/MYSELF/product/" + i)).then(function (e) {
                    e.success ? (showFocusTip(n.parent().find(".focus-tip"), "\u5173\u6ce8\u6210\u529f"), n.removeClass("fa-heart-o").addClass("fa-heart orange"), n.attr("title", "\u5df2\u5173\u6ce8"), t.opts.myFavorites.push(i)) : showFocusTip(n.parent().find(".focus-tip"), "\u5173\u6ce8\u5931\u8d25")
                })
            }), $("body").on("mouseenter mouseleave", ".box,.box-big", function (t) {
                switch (t.type) {
                    case"mouseenter":
                        $(this).find(".buy").stop().animate({"bottom": 15, "opacity": 1}, 200);
                        break;
                    case"mouseleave":
                        $(this).find(".buy").stop().animate({"bottom": -30, "opacity": 0}, 200)
                }
            })
        }, e.exports = i
    }, {
        "../../../snippet/product_list.def": 7,
        "../../plugins/jquery.aerowindow": 1,
        "../../plugins/jquery.page.js": 2,
        "../../plugins/lazy.loadjs.js": 3
    }], "7": [function (t, e, n) {
        e.exports = '{%~it:item:index%}\n<div class="box" data-product-id={%=item.id%}>\n  <span class="orange focus-tip"></span>\n  <button class="follow fa {%= item.isFavorited ? \'fa-heart orange\' : \'fa-heart-o\' %}" title={%= item.isFavorited ? \'\u5df2\u5173\u6ce8\' : \'\u52a0\u5173\u6ce8\' %}></button>\n  <div class="p-img">\n    <a href={%=item.url%} target="_blank">\n      <img src={%=item.imgUrl%} alt={%=item.name%}/>\n    </a>\n  </div>\n  <div class="info">\n    <a href={%=item.url%} target="_blank" class="title">{%=item.name%}</a>\n    <div class="mt17 clearfix">\n      <p class="price orange">\n        <span class="price-item">{%=item.price%}</span>\n        <span class="unit">\u79ef\u5206</span>\n      </p>\n      <p class="left-count">\n        <span>\u5269\u4f59\uff1a</span>\n        <span class="count">{%=item.leftCount%}</span>\n      </p>\n    </div>\n    <div class="buy"><a href={%=item.url%} class="orange">\u7acb\u5373\u6362\u8d2d</a></div>\n  </div>\n</div>\n{%~%}'
    }, {}]
}, {}, [4]);