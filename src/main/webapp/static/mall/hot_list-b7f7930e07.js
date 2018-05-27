!function t(n, e, i) {
    function a(s, o) {
        if (!e[s]) {
            if (!n[s]) {
                var l = "function" == typeof require && require;
                if (!o && l)return l(s, !0);
                if (r)return r(s, !0);
                var c = new Error("Cannot find module '" + s + "'");
                throw c.code = "MODULE_NOT_FOUND", c
            }
            var u = e[s] = {"exports": {}};
            n[s][0].call(u.exports, function (t) {
                var e = n[s][1][t];
                return a(e ? e : t)
            }, u, u.exports, t, n, e, i)
        }
        return e[s].exports
    }

    for (var r = "function" == typeof require && require, s = 0; s < i.length; s++)a(i[s]);
    return a
}({
    "1": [function (t, n, e) {
        "use strict";
        var i = function (t, n) {
            var e = [];
            t("[role=navigation]").attr("un-id") ? t.when(t.get("static/mall/favorites.json?page=1&pageSize=99999999")).then(function (t) {
                if (t.success && t.data.results.length > 0)for (var i = t.data.results, a = 0; a < i.length; a++)e.push(i[a].productId);
                n(e)
            }, function () {
                n(e)
            }) : n(e)
        };
        n.exports = i
    }, {}], "2": [function (t, n, e) {
        var i = t("../../../snippet/product_list.def"), a = t("../common/getMyFavorites");
        !function (t) {
            var n = [], e = t(".hot .hot-list").attr("data-cid"), r = {"cid1": e};
            a(t, function (e) {
                n = e, t.when(t.get("static/mall/hotlist.json", r)).then(function (e) {
                    if (e.data && e.data.length) {
                        for (var a = e.data.slice(0, 4), r = [], s = 0; s < a.length; s++) {
                            var o = {};
                            o.id = a[s].id, o.url = redirect.mall("/products/detail/" + a[s].id), o.imgUrl = a[s].images.length > 0 ? a[s].images[0] : "", o.name = a[s].name, o.price = a[s].pointsNeeded, o.leftCount = a[s].inventory, o.isFavorited = _.contains(n, a[s].id), r.push(o)
                        }
                        var l = '<p class="nodata"><i class="bg"></i>\u672a\u627e\u5230\u5546\u54c1\uff01</p>';
                        t(".hot .hot-list").html(r.length > 0 ? dotRender(i, r) : l)
                    }
                })
            })
        }(jQuery)
    }, {"../../../snippet/product_list.def": 3, "../common/getMyFavorites": 1}], "3": [function (t, n, e) {
        n.exports = '{%~it:item:index%}\n<div class="box" data-product-id={%=item.id%}>\n  <span class="orange focus-tip"></span>\n  <button class="follow fa {%= item.isFavorited ? \'fa-heart orange\' : \'fa-heart-o\' %}" title={%= item.isFavorited ? \'\u5df2\u5173\u6ce8\' : \'\u52a0\u5173\u6ce8\' %}></button>\n  <div class="p-img">\n    <a href={%=item.url%} target="_blank">\n      <img src={%=item.imgUrl%} alt={%=item.name%}/>\n    </a>\n  </div>\n  <div class="info">\n    <a href={%=item.url%} target="_blank" class="title">{%=item.name%}</a>\n    <div class="mt17 clearfix">\n      <p class="price orange">\n        <span class="price-item">{%=item.price%}</span>\n        <span class="unit">\u79ef\u5206</span>\n      </p>\n      <p class="left-count">\n        <span>\u5269\u4f59\uff1a</span>\n        <span class="count">{%=item.leftCount%}</span>\n      </p>\n    </div>\n    <div class="buy"><a href={%=item.url%} class="orange">\u7acb\u5373\u6362\u8d2d</a></div>\n  </div>\n</div>\n{%~%}'
    }, {}]
}, {}, [2]);