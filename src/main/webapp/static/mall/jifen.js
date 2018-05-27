!
    function t(a, e, n) {
        function i(s, r) {
            if (!e[s]) {
                if (!a[s]) {
                    var d = "function" == typeof require && require;
                    if (!r && d) return d(s, !0);
                    if (l) return l(s, !0);
                    var c = new Error("Cannot find module '" + s + "'");
                    throw c.code = "MODULE_NOT_FOUND",
                        c
                }
                var o = e[s] = {
                    "exports": {}
                };
                a[s][0].call(o.exports,
                    function (t) {
                        var e = a[s][1][t];
                        return i(e ? e : t)
                    },
                    o, o.exports, t, a, e, n)
            }
            return e[s].exports
        }

        for (var l = "function" == typeof require && require,
                 s = 0; s < n.length; s++) i(n[s]);
        return i
    }({
            "1": [function (t, a, e) {
                "use strict";
                var n = t("../../../../server/views/account/jifen/tpl_list_all.def"),
                    i = t("../../../../server/views/account/jifen/tpl_list_attention.def"),
                    l = t("../../../../server/views/account/jifen/tpl_list_detail.def");
                !
                    function (t) {
                        function a(a, e, n) {
                            var i = "";
                            "all" == e ? i = "../json/members_point_order.json" : "detail" == e ? i = "../json/members_point_detail.json" : "attention" == e && (i = "../json/members_point_favorites.json"),
                                t.get(i, a,
                                    function (t) {
                                        n(t)
                                    })
                        }

                        function e(e) {
                            var e = e || h;
                            m.html(dotRender(o[d], {
                                "list": "loading"
                            }));
                            var n = C.categories,
                                i = C.datetime,
                                l = C["trade-status"],
                                u = t(".categories li").eq(n).data("category") || "",
                                g = t(".datetime li").eq(i).data("date") || "",
                                p = i > 0 ? moment().unix() : "";
                            "detail" == d ? (e.transactionType = u, i > 0 && (e.from = g, e.to = p)) : "all" == d && (e.catId = u, i && (e.dateStart = g, e.dateEnd = p), e.orderStatus = t(".trade-status li").eq(l).data("status") || ""),
                                a(e, d,
                                    function (a) {
                                        var n = [];
                                        if ("all" == d) {
                                            for (var i = a.data.results,
                                                     l = 0; l < a.data.results.length; l++) a.data.results[l].allDate = moment.unix(i[l].orderDate / 1e3).format("YYYY-MM-DD HH:mm:ss"),
                                                a.data.results[l].BacklStatus = c[i[l].orderStatus],
                                                a.data.results[l].numberUrl = domainUrl("/order/orderDetail?orderNumber=" + i[l].orderNumber, "mall"),
                                                a.data.results[l].productUrl = domainUrl("/products/detail/" + i[l].productId, "mall"),
                                                a.data.results[l].pointTotal = B.format.amount(i[l].total_points, 0),
                                            i[l].eventId && (a.data.results[l].bonusLink = redirect.my("/account/mykq?type=CASH&status=INACTIVE"));
                                            a.totalSize = a.data.totalSize,
                                                n = a.data.results,
                                                v.page = e.page
                                        } else if ("detail" == d) {
                                            for (var i = a.results,
                                                     l = 0; l < i.length; l++)"PRODUCE" == i[l].transactionType ? a.results[l].addOrDown = "+" : "CONSUME" == i[l].transactionType && (a.results[l].addOrDown = "-"),
                                                a.results[l].attenDate = moment.unix(i[l].createTime / 1e3).format("YYYY-MM-DD HH:mm:ss");
                                            n = a.results,
                                                f.pageNo = e.pageNo || 1
                                        } else if ("attention" == d) {
                                            for (var i = a.data.results,
                                                     l = 0; l < i.length; l++) a.data.results[l].detaiHref = redirect.mall("/products/detail/" + i[l].productId),
                                                a.data.results[l].productUrl = redirect.mall("/products/detail/" + i[l].productId);
                                            a.totalSize = a.data.totalSize,
                                                n = a.data.results,
                                                b.page = e.page || 1
                                        }
                                        m.html(dotRender(o[d], {
                                            "list": n
                                        }));
                                        for (var u in C) r("", u, C[u]);
                                        k || y.animate({
                                            "scrollTop": 400
                                        }),
                                        a.totalSize || t(".pagination").html(""),
                                        (1 == e.page || 1 == e.pageNo) && a.totalSize > e.pageSize && s(a.totalSize, e)
                                    })
                        }

                        function s(a, n) {
                            t(".pagination").html('<div class="page-bar"></div>'),
                                t(".page-bar").createPage({
                                    "pageCount": Math.ceil(a / u),
                                    "current": n.page || n.pageNo || 1,
                                    "backFn": function (t) {
                                        n.pageNo ? n.pageNo = t : n.page = t,
                                            e(n)
                                    }
                                })
                        }

                        function r(a, e, n) {
                            t("ul." + e + " li").removeClass("selected"),
                                n >= 0 ? (t("ul." + e + " li").eq(n).addClass("selected"), C[e] = n) : a && (a.addClass("selected"), C[e] = a.index())
                        }

                        var d, c = {
                                "-1": "交易取消",
                                "0": "交易失败",
                                "2": "待发货",
                                "3": "已发货",
                                "4": "已完成"
                            },
                            o = {
                                "all": n,
                                "detail": l,
                                "attention": i
                            },
                            u = 5,
                            g = t(".public-messagee-user").attr("un-id"),
                            p = t(".acc-tab ul li"),
                            m = t(".category-content"),
                            h = {
                                "page": 1,
                                "pageSize": u
                            },
                            v = {},
                            f = {
                                "pageSize": u
                            },
                            b = h,
                            y = t(window.opera ? "CSS1Compat" == document.compatMode ? "html" : "body" : "html,body"),
                            S = t(".acc-content"),
                            C = {
                                "categories": 0,
                                "datetime": 0,
                                "trade-status": 0
                            };
                        "detail" == getQueryString("type") ? (d = "detail", p.removeClass("active"), p.eq(1).addClass("active"), t(".account-nav .line-slide").css({
                            "left": 100
                        })) : "attention" == getQueryString("type") ? (d = "attention", p.removeClass("active"), p.eq(2).addClass("active"), t(".account-nav .line-slide").css({
                            "left": 200
                        })) : d = "all",
                            e(h),
                            p.click(function (a) {
                                var n = t(a.currentTarget);
                                n.hasClass("active") || (p.removeClass("active"), n.addClass("active"), d = n.data("type"), t(".pagination").html(""), "detail" == d ? (f.pageNo = 1, f.pageSize = u, e(f)) : (h.page = 1, e(h)), k = !1)
                            });
                        var k = !0;
                        m.on("click", ".delete-focus",
                            function () {
                                var a = t(this).attr("dataId");
                                AeroDialog.confirm("确定取消关注吗？", "提示",
                                    function (n) {
                                        "ok" == n && t.ajax({
                                            "type": "POST",
                                            "url": "/mall/api/v2/favorite/user/" + g + "/product/" + a + "/cancel",
                                            "success": function (t) {
                                                t.success ? e(b) : alert("取消关注成功")
                                            },
                                            "error": function (t) {
                                                alert("取消关注失败")
                                            }
                                        })
                                    })
                            }),
                            S.on("click", ".all-category .categories li",
                                function () {
                                    v = {
                                        "page": 1,
                                        "pageSize": u
                                    },
                                        t(".pagination").html(""),
                                        r(t(this), t(this).parent().attr("class")),
                                        e(v)
                                }),
                            S.on("click", ".all-category .datetime li",
                                function () {
                                    v = {
                                        "page": 1,
                                        "pageSize": u
                                    },
                                        t(".pagination").html(""),
                                        r(t(this), t(this).parent().attr("class")),
                                        e(v)
                                }),
                            S.on("click", ".all-category .trade-status li",
                                function () {
                                    v = {
                                        "page": 1,
                                        "pageSize": u
                                    },
                                        t(".pagination").html(""),
                                        r(t(this), t(this).parent().attr("class")),
                                        e(v)
                                }),
                            S.on("click", ".detail-category .categories li",
                                function () {
                                    f = {
                                        "pageNo": 1,
                                        "pageSize": u
                                    },
                                        t(".pagination").html(""),
                                        r(t(this), t(this).parent().attr("class")),
                                        e(f)
                                }),
                            S.on("click", ".detail-category .datetime li",
                                function () {
                                    f = {
                                        "pageNo": 1,
                                        "pageSize": u
                                    },
                                        t(".pagination").html(""),
                                        r(t(this), t(this).parent().attr("class")),
                                        e(f)
                                })
                    }(jQuery)
            },
                {
                    "../../../../server/views/account/jifen/tpl_list_all.def": 2,
                    "../../../../server/views/account/jifen/tpl_list_attention.def": 3,
                    "../../../../server/views/account/jifen/tpl_list_detail.def": 4
                }],
            "2": [function (t, a, e) {
                a.exports = '<div class="acc-categories all-category">\n  ' +
                    '<div class="cate-line">\n    ' +
                    '<span class="cate-head">类目：</span>\n    ' +
                    '<ul class="categories">\n      ' +
                    '<li data-category="" class="selected">全部</li>\n      ' +
                    '<li data-category="98C6EBCC-B964-44CE-8DAA-91C049A484D8">拍拖礼品店</li>\n      ' +
                    '<li data-category="0B115B4C-B7E2-4BBD-B944-6F5CDFB62A3D">购实惠</li>\n    ' +
                    '</ul>\n  </div>\n  <div class="cate-line">\n    ' +
                    '<span class="cate-head">时间：</span>\n    ' +
                    '<ul class="datetime">\n      ' +
                    '<li data-date="" class="selected">全部</li>\n      ' +
                    '<li data-date="{%=moment().subtract(1,\'month\').unix()%}">最近1个月</li>\n      ' +
                    '<li data-date="{%=moment().subtract(3,\'month\').unix()%}">最近3个月</li>\n      ' +
                    '<li data-date="{%=moment().subtract(6,\'month\').unix()%}">最近6个月</li>\n      ' +
                    '<li data-date="{%=moment().subtract(1,\'year\').unix()%}">最近1年</li>\n    ' +
                    '</ul>\n  </div>\n  <div class="cate-line">\n    ' +
                    '<span class="cate-head">状态：</span>\n    ' +
                    '<ul class="trade-status">\n      ' +
                    '<li data-status="" class="selected">全部</li>\n      ' +
                    '<li data-status="2">待发货</li>\n      ' +
                    '<li data-status="3">已发货</li>\n      ' +
                    '<li data-status="4">已完成</li>\n      ' +
                    '<li data-status="0">交易失败</li>\n      ' +
                    '<li data-status="-1">交易取消</li>\n    ' +
                    '</ul>\n  ' +
                    '</div>\n' +
                    '</div>\n{%?it.list == \'loading\'%}\n' +
                    '<div class="acc-loading">\n  ' +
                    '<img src="../../img/loading-42e5b86421.gif" alt="">\n' +
                    '</div>\n{%??it.list && it.list.length%}\n' +
                    '<table class="acc-table table-all">\n  ' +
                    '<thead>\n    ' +
                    '<tr>\n      ' +
                    '<th width="360">订单信息</th>\n      ' +
                    '<th width="260">积分</th>\n      ' +
                    '<th>数量</th>\n      ' +
                    '<th>操作</th>\n    ' +
                    '</tr>\n  ' +
                    '</thead>\n  ' +
                    '<tbody>\n  {%~it.list :l:i%}\n  ' +
                    '<tr>\n    ' +
                    '<td>\n      ' +
                    '<div class="item-img">\n        ' +
                    '<a href=\'{%=l.productUrl%}\' target="_blank">\n          ' +
                    '<img src=\'{%=l.pcImgPath%}\'/>\n        ' +
                    '</a>\n      ' +
                    '</div>\n      ' +
                    '<div class="item-text">\n        ' +
                    '<p>{%=l.allDate%}</p>\n        ' +
                    '<a href=\'{%=l.productUrl%}\' target="_blank">{%=l.pcName%}</a>\n        ' +
                    '<p>{%=l.orderNumber%}</p>\n      ' +
                    '</div>\n    ' +
                    '</td>\n    ' +
                    '<td class="fir">{%=l.pointTotal%} 积分</td>\n    ' +
                    '<td >{%=l.quantity%}个</td>\n    ' +
                    '<td width="130">\n      ' +
                    '<span class="{%=l.BacklStatus==\'已完成\'?\'grey\':\'orange\'%}">{%=l.BacklStatus%}</span>\n      ' +
                    '<br/>\n      ' +
                    '<a href=\'{%=l.numberUrl%}\' target="_blank">订单详情</a>\n      {%?l.bonusLink%}\n      ' +
                    '<br/>\n      ' +
                    ' {%?%}\n    ' +
                    '</td>\n  ' +
                    '</tr>\n  {%~%}\n  ' +
                    '</tbody>\n' +
                    '</table>\n{%??%}\n' +
                    '<div class="acc-no-data">\n  ' +
                    '<p>您还没有相关订单，前往\n    ' +
                    '<a href="{%=redirect.mall(\'/\')%}" class="orange">积分商城</a>随便逛逛吧！\n  ' +
                    '</p>\n' +
                    '</div>\n{%?%}'
            },
                {}],
            "3": [function (t, a, e) {
                a.exports =
                    '{%?it.list == \'loading\'%}\n' +
                    '<div class="acc-loading">\n  ' +
                    '<img src="../../img/loading-42e5b86421.gif" alt="">\n' +
                    '</div>\n{%??it.list && it.list.length%}\n<div>\n  <table class="acc-table table-attention">\n    ' +
                    '<thead>\n   ' +
                    ' <tr>\n      ' +
                    '<th width="410">商品</th>\n      ' +
                    '<th width="260">金额</th>\n      ' +
                    '<th width="260">操作</th>\n    ' +
                    '</tr>\n    ' +
                    '</thead>\n    ' +
                    '<tbody>\n    {%~it.list :l:i%}\n    ' +
                    '<tr>\n      ' +
                    '<td width="400">\n        ' +
                    '<div class="jifen-item clearfix">\n          ' +
                    '<div class="item-img">\n            ' +
                    '<a href=\'{%=l.productUrl%}\' target="_blank">\n              ' +
                    '<img src=\'{%=l.pcdetailheadImage%}\'/>\n            ' +
                    '</a>\n          ' +
                    '</div>\n          ' +
                    '<div class="item-text">' +
                    '<a href=\'{%=l.productUrl%}\' target="_blank">{%=l.namePc%}</a>\n          ' +
                    '</div>\n        ' +
                    '</div>\n      ' +
                    '</td>\n      ' +
                    '<td width="262">{%=l.pointsSpend%}\u79ef\u5206</td>\n      ' +
                    '<td width="264">\n        ' +
                    '<a href=\'{%=l.detaiHref%}\' class="orange" target="_blank">\u67e5\u770b</a>\n        ' +
                    '<br/>\n        ' +
                    '<a class="delete-focus" dataId=\'{%=l.productId%}\' href="javascript:;">取消关注</a>\n      ' +
                    '</td>\n    </tr>\n    {%~%}\n   ' +
                    '</tbody>\n  ' +
                    '</table>\n</div>\n{%??%}\n' +
                    '<div class="acc-no-data">\n  ' +
                    '<p>\n    您还没有添加关注商品，前往\n    ' +
                    '<a href="{%=redirect.mall(\'/\')%}" class="orange">积分商城</a>\n    随便逛逛吧！' +
                    '</p>\n' +
                    '</div>\n{%?%}\n'
            },
                {}],
            "4": [function (t, a, e) {
                a.exports = '<div class="acc-categories detail-category">\n  ' +
                    '<div class="cate-line">\n    ' +
                    '<span class="cate-head">\u7b5b\u9009\uff1a</span>\n    ' +
                    '<ul class="categories">\n      ' +
                    '<li data-category="" class="selected">\u5168\u90e8</li>\n      ' +
                    '<li data-category="PRODUCE">\u6536\u5165</li>\n      ' +
                    '<li data-category="CONSUME">\u652f\u51fa</li>\n    ' +
                    '</ul>\n  ' +
                    '</div>\n  ' +
                    '<div class="cate-line">\n    ' +
                    '<span class="cate-head">\u65f6\u95f4\uff1a</span>\n    ' +
                    '<ul class="datetime">\n      ' +
                    '<li data-date="" class="selected">\u5168\u90e8</li>\n      ' +
                    '<li data-date="{%=moment().subtract(1,\'month\').unix()%}">\u6700\u8fd11\u4e2a\u6708</li>\n      ' +
                    '<li data-date="{%=moment().subtract(3,\'month\').unix()%}">\u6700\u8fd13\u4e2a\u6708</li>\n      ' +
                    '<li data-date="{%=moment().subtract(6,\'month\').unix()%}">\u6700\u8fd16\u4e2a\u6708</li>\n      ' +
                    '<li data-date="{%=moment().subtract(1,\'year\').unix()%}">\u6700\u8fd11\u5e74</li>\n    ' +
                    '</ul>\n  </div>\n</div>\n\n{%?it.list == \'loading\'%}\n' +
                    '<div class="acc-loading">\n  ' +
                    '<img src="../../img/loading-42e5b86421.gif" alt="">\n' +
                    '</div>\n{%??it.list && it.list.length%}\n<table class="acc-table">\n  ' +
                    '<thead>\n  <tr>\n    <th width="300">\u65e5\u671f</th>\n    ' +
                    '<th width="300">积分</th>\n    ' +
                    '<th>说明</th>\n  ' +
                    '</tr>\n  ' +
                    '</thead>\n  ' +
                    '<tbody class="">\n  {%~it.list :l:i%}\n  ' +
                    '<tr>\n    ' +
                    '<td>{%=l.attenDate%}</td>\n    ' +
                    '<td>{%=l.addOrDown%}{%=l.points%}</td>\n    ' +
                    '<td>{%=l.remark%}</td>\n  ' +
                    '</tr>\n  {%~%}\n  ' +
                    '</tbody>\n' +
                    '</table>\n{%??%}\n' +
                    '<div class="acc-no-data">\n  暂无数据\n' +
                    '</div>\n{%?%}'
            },
                {}]
        },
        {},
        [1]);