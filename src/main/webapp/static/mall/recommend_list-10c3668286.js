!function n(e, a, r) {
    function i(s, o) {
        if (!a[s]) {
            if (!e[s]) {
                var c = "function" == typeof require && require;
                if (!o && c)return c(s, !0);
                if (t)return t(s, !0);
                var f = new Error("Cannot find module '" + s + "'");
                throw f.code = "MODULE_NOT_FOUND", f
            }
            var l = a[s] = {"exports": {}};
            e[s][0].call(l.exports, function (n) {
                var a = e[s][1][n];
                return i(a ? a : n)
            }, l, l.exports, n, e, a, r)
        }
        return a[s].exports
    }

    for (var t = "function" == typeof require && require, s = 0; s < r.length; s++)i(r[s]);
    return i
}({
    "1": [function (n, e, a) {
        !function (e) {
            function a() {
                doAjax("static/mall/focus.json").done(function (n) {
                    var a = n.success ? n.data : [];
                    e(".benefit-box ul li").each(function (n, r) {
                        var i = e(r).attr("pid");
                        inArray(i, a) && e(r).find("a.focus").removeClass("fa-heart-o").addClass("fa-heart orange")
                    }), e(".gift-shop-slide li").each(function (n, r) {
                        if (!e(this).hasClass("bx-clone")) {
                            var i = e(r).attr("pid");
                            inArray(i, a) && e(r).find("a.focus").removeClass("fa-heart-o").addClass("fa-heart orange")
                        }
                    })
                })
            }

            var r = n("../../../snippet/index_recommend_list.def");
            doAjax("static/mall/recommend_list.json").done(function (n) {
                e(".benefit-box").html(dotRender(r, n)), a()
            })
        }(jQuery)
    }, {"../../../snippet/index_recommend_list.def": 2}], "2": [function (n, e, a) {
        e.exports = '<ul>\n  {%~it.data:item:index%}\n  <li class="benefit-item" pid="{%=item.productId%}">\n    <span class="orange focus-tip"></span>\n\n    <div class="goods">\n      <a class="focus fa fa-heart-o" href="javascript:void(0)"></a>\n      <a target="_blank" href="{%=item.url%}">\n        <img src="{%=item.imgUrl%}"/>\n      </a>\n    </div>\n\n    <div class="desc">\n      <a target="_blank" class="buy" href="{%=item.url%}">\u7acb\u5373\u6362\u8d2d</a>\n      <a href="{%=item.url%}" target="_blank">{%=item.title%}</a>\n\n      <div class="box">\n                    <span class="price orange">\n                        <span>{%=item.price%}</span>\u79ef\u5206\n                     </span>\n        <span class="surplus">\n                        \u5269\u4f59<span class="colon">:</span><span>{%=item.surplus%}</span>\n                    </span>\n      </div>\n    </div>\n  </li>\n  {%~%}\n</ul>'
    }, {}]
}, {}, [1]);