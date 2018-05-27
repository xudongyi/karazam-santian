/**
 * Created by limat on 2017/6/6.
 */
!function e(n, t, i) {
    function o(a, s) {
        if (!t[a]) {
            if (!n[a]) {
                var c = "function" == typeof require && require;
                if (!s && c)return c(a, !0);
                if (r)return r(a, !0);
                var d = new Error("Cannot find module '" + a + "'");
                throw d.code = "MODULE_NOT_FOUND", d
            }
            var u = t[a] = {"exports": {}};
            n[a][0].call(u.exports, function (e) {
                var t = n[a][1][e];
                return o(t ? t : e)
            }, u, u.exports, e, n, t, i)
        }
        return t[a].exports
    }

    for (var r = "function" == typeof require && require, a = 0; a < i.length; a++)o(i[a]);
    return o
}({
    "1": [function (e, n, t) {
        !function (e, n) {
            function t() {
                if ("object" == typeof localStorage)return localStorage;
                if ("object" == typeof globalStorage)return globalStorage[location.href];
                if ("object" == typeof userData)return globalStorage[location.href];
                throw new Error("no support localStore")
            }

            function i() {
            }

            var o = {
                "userData": null, "name": location.href, "init": function () {
                    if (!o.userData)try {
                        o.userData = n.createElement("INPUT"), o.userData.type = "hidden", o.userData.style.display = "none", o.userData.addBehavior("#default#userData"), n.body.appendChild(o.userData);
                        var e = new Date;
                        e.setDate(e.getDate() + 365), o.userData.expires = e.toUTCString()
                    } catch (t) {
                        return !1
                    }
                    return !0
                }, "setItem": function (e, n) {
                    o.init() && (o.userData.load(o.name), o.userData.setAttribute(e, n), o.userData.save(o.name))
                }, "getItem": function (e) {
                    return o.init() ? (o.userData.load(o.name), o.userData.getAttribute(e)) : void 0
                }, "removeItem": function (e) {
                    o.init() && (o.userData.load(o.name), o.userData.removeAttribute(e), o.userData.save(o.name))
                }
            }, r = t();
            i.prototype = {
                "setItem": function (n, t) {
                    e.localStorage ? r.setItem(n, t) : o.setItem(n, t)
                }, "getItem": function (n) {
                    return e.localStorage ? r.getItem(n) : o.getItem(n)
                }, "removeItem": function (n) {
                    e.localStorage ? r.removeItem(n) : o.removeItem(n)
                }
            }, e._localStorage = new i
        }(window, document), function (e) {
            function n(e) {
                var n = z.hostname.match(/\.inc|\.com/);
                return n = n ? ".paituo" + n : z.hostname, {"domain": n, "path": "/", "expires": e || 30}
            }

            function t() {
                var e, t, o, r, a, c, d, u, f, l, m = document.referrer, _ = Q("_fjrclflag");
                if (m) {
                    for (e = 0; e < X.length; e++)if (X[e][0].test(m)) {
                        Q("fjr_channel", "" + m.match(X[e][0]), n()), _ || Q("fjr_channel_code", X[e][1], n()), Q("fjr_channel_date", i(), n());
                        break
                    }
                } else _ || (Q("fjr_channel_code", U, n()), Q("fjr_channel_date", i(), n()));
                if (location.search)for (t = location.search.substring(1).split("&"), o = 0; o < t.length; o++)r = t[o].split("="), "fjr_did" == r[0] ? Q("fjr_did", r[1], $) : "fjr_uid" == r[0] ? Q("fjr_uid", r[1], n()) : "channel_code" == r[0] ? (Q("fjr_channel_code", r[1], n()), Q("fjr_channel_date", i(), n())) : ("channel" == r[0] || "c" == r[0]) && (Q("fjr_channel", r[1], n()), Q("fjr_channel_date", i(), n()), a = 0 | Q("fjr_ad_channel_date"), (!a || a && i() - a > 2592e6) && (Q("fjr_ad_channel", r[1], n()), Q("fjr_ad_channel_date", i(), n())));
                H("fjr_did") ? Q("fjr_did", H("fjr_did"), $) : (c = s(), H("fjr_did", c), Q("fjr_did", c, $)), d = new Date, u = 30, f = i(), l = 0 | Q("fjr_vts"), d.setTime(d.getTime() + 6e4 * u), Q("fjr_vts") ? Q("_fjrvts") || (Q("fjr_vts", l + 1, n(5e4)), Q("_fjrvts", 1, n(d)), Q("fjr_lst", Q("fjr_vct"), n(5e4)), Q("fjr_vct", f, n(5e4))) : (Q("fjr_vts", 1, n(5e4)), Q("_fjrvts") || Q("_fjrvts", 1, n(d))), Q("fjr_fst") || (Q("fjr_fst", f, n(5e4)), Q("fjr_lst", f, n(5e4)), Q("fjr_vct", f, n(5e4)))
            }

            function i() {
                return Date.now()
            }

            function o(e) {
                var n, t;
                return e = e || window.event, e.pageX || e.clientX && (n = document.documentElement.scrollLeft || document.body.scrollLeft, t = document.documentElement.scrollTop || document.body.scrollTop, e.pageX = e.clientX + n, e.pageY = e.clientY + t), e
            }

            function r() {
                var e = document;
                return Math.max(Math.max(e.body.scrollHeight, e.documentElement.scrollHeight), Math.max(e.body.offsetHeight, e.documentElement.offsetHeight), Math.max(e.body.clientHeight, e.documentElement.clientHeight))
            }

            function a() {
                var e = document;
                return Math.max(Math.max(e.body.scrollWidth, e.documentElement.scrollWidth), Math.max(e.body.offsetWidth, e.documentElement.offsetWidth), Math.max(e.body.clientWidth, e.documentElement.clientWidth))
            }

            function s() {
                return Q("fjr_did") || H("fjr_did") || c()
            }

            function c() {
                var e = (new Date).getTime(), n = "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(/[xy]/g, function (n) {
                    var t = 0 | (e + 16 * Math.random()) % 16;
                    return e = Math.floor(e / 16), ("x" == n ? t : 8 | 3 & t).toString(16)
                });
                return n
            }

            function d(n) {
                var t, o, r;
                if (q) {
                    if (g || l(), t = {
                            "key": "view",
                            "view": 0,
                            "type": n || 0,
                            "path_name": q,
                            "domain": z.hostname,
                            "origin": z.href,
                            "platform": g
                        }, t.duration = i() - M, 0 == n) {
                        o = {}, t.count = 1, t.timestamp = i();
                        for (r in t)o[J[r]] = t[r];
                        u({"events": JSON.stringify([o])}, 0)
                    } else e.add_event(t);
                    q = null
                }
            }

            function u(n, t) {
                var o, r, a, s, c;
                if (!e.ignore_bots || !C) {
                    n.app_key = e.app_key, n.device_id = Q("fjr_did") || H("fjr_did") || e.device_id, n.country_code = e.country_code, n.city = e.city, n.host_url = z.origin, n.ip_address = e.ip_address, n.channel = Q("fjr_channel") || "", n.channel_code = Q("fjr_channel_code") || "", n.resolution = h, n.user_id = Q("fjr_user_id") || "", n.system_id = window.system_id || e.system_id || Q("fjr_system_id") || "", n.source = window.source || e.source || Q("fjr_source") || "", n.timestamp = i(), n.channel_date = Q("fjr_channel_date") || "", n.sdk_version = b, n.visit_times = Q("fjr_vts") || "", o = document.referrer, r = {}, n.organic = o && A.test(o) ? o.match(A)[0] : "-";
                    for (a in n)n[a] && (r[J[a]] = n[a]);
                    if (Q("fjr_properties") && JSON)try {
                        s = JSON.parse(Q("fjr_properties"));
                        for (c in s)r[c] = s[c]
                    } catch (d) {
                    }
                    0 == t ? m(r) : (O.push(r), H("fjr_queue", O, !0))
                }
            }

            function f() {
                var n, t, o, r, a;
                if ("undefined" != typeof e.q && e.q.length > 0) {
                    for (t = 0; t < e.q.length; t++)n = e.q[t], "function" == typeof n ? n() : n.constructor === Array && n.length > 0 && "undefined" != typeof e[n[0]] && e[n[0]].apply(null, n.slice(1));
                    e.q = []
                }
                x && D && L && (o = i(), o - p > 60 && (e.session_duration(o - p), p = o)), W.length > 0 && (W.length <= 5 ? (u({"events": JSON.stringify(W)}), W = []) : (r = W.splice(0, 5), u({"events": JSON.stringify(r)})), H("fjr_event", W)), O.length > 0 && B && (a = O.shift(), m(a), H("fjr_queue", O, !0)), setTimeout(f, S)
            }

            function l() {
                var n, t, i, o, r, a, s, c, d, u, f, l, m, _, w, p, v, y, b = {};
                for (b[J.app_version] = e.app_version, screen.width && (n = screen.width ? screen.width : "", t = screen.height ? screen.height : "", b[J.resolution] = "" + n + "x" + t, h = "" + n + "x" + t), i = navigator.appVersion, o = F, r = navigator.appName, c = "Internet Explorer", d = [["Chrome"], ["Firefox"], ["MSIE", c], ["Trident", c], ["Android"], ["Safari"], ["iPhone"], ["Edge"], ["Opera"], ["OPR", "Opera"], ["Opera Mini"], ["IEMobile"], ["Chromium"], ["FxiOS", "Firefox"], ["FBAV/", "Facebook app"], ["baiduboxapp/", "Baidu Box App"], ["baidubrowser"], ["Dolfin"], ["Skyfire"], ["bolt"], ["teashark"], ["Blazer"], ["Tizen"], ["UCWEB", "UCBrowser"], ["UC.", "UCBrowser"], ["DiigoBrowser"], ["Puffin"], ["Mercury"], ["Obigo"], ["NF-Browser"], ["amaya"], ["Arora"], ["Avant"], ["BlackBerry"], ["RIM", "BlackBerry"], ["CFNetwork"], ["Camino"], ["Coast"], ["Dalvik"], ["Dillo"], ["DoCoMo"], ["ELinks"], ["Espial", "Espial TV Browser"], ["FlyFlow"], ["GSA", "Google App"], ["Google Desktop"], ["GooglePlus", "Google+ App"], ["IBrowse"], ["Jasmine"], ["Kindle"], ["Konqueror"], ["Links"], ["Lotus-Notes"], ["Lynx"], ["WAP"], ["MiuiBrowser"], ["Nokia"], ["OneBrowser"], ["Outlook"], ["Palm"], ["Pinterest", "Pinterest App"], ["Playstation"], ["PlayStation"], ["PLAYSTATION"], ["Polaris"], ["QQ"], ["Qt"], ["QuickTime"], ["SEMC-Browser"], ["SamsungBrowser"], ["SeaMonkey"], ["Sleipnir"], ["SmartTV"], ["Viera", "SmartViera"], ["Thunderbird"], ["Vivaldi"], ["wOSBrowser", "webOS"], ["WebTV"], ["wp-", "WordPress App"], ["YaBrowser"], ["iCab"], ["iTunes"], ["rekonq"], ["rekonq"]], u = 0; u < d.length; u++)if (-1 != o.indexOf(d[u][0])) {
                    f = d[u].length, r = 1 == f ? d[u][0] : d[u][1];
                    break
                }
                !r && (a = o.lastIndexOf(" ") + 1) < (s = o.lastIndexOf("/")) && (r = o.substring(a, s), r.toLowerCase() == r.toUpperCase() && (r = navigator.appName)), b[J.browser] = r, l = "unknown", m = [{
                    "s": "Windows ME",
                    "r": /(Win 9x 4.90|Windows ME)/
                }, {"s": "Windows 2000", "r": /(Windows NT 5.0|Windows 2000)/}, {
                    "s": "Windows XP",
                    "r": /(Windows NT 5.1|Windows XP)/
                }, {"s": "Windows Server 2003", "r": /Windows NT 5.2/}, {"s": "Windows Vista", "r": /Windows NT 6.0/}, {
                    "s": "Windows 7",
                    "r": /(Windows 7|Windows NT 6.1)/
                }, {"s": "Windows 8.1", "r": /(Windows 8.1|Windows NT 6.3)/}, {
                    "s": "Windows 8",
                    "r": /(Windows 8|Windows NT 6.2)/
                }, {"s": "Windows NT 4.0", "r": /(Windows NT 4.0|WinNT4.0|WinNT|Windows NT)/}, {
                    "s": "Windows ME",
                    "r": /Windows ME/
                }, {"s": "Windows Phone", "r": /Windows Phone/}, {"s": "Android", "r": /Android/}, {"s": "Open BSD", "r": /OpenBSD/}, {
                    "s": "Sun OS",
                    "r": /SunOS/
                }, {"s": "Linux", "r": /(Linux|X11)/}, {"s": "iOS", "r": /(iPhone|iPad|iPod)/}, {"s": "Mac OSX", "r": /Mac OS X/}, {
                    "s": "Mac OS",
                    "r": /(MacPPC|MacIntel|Mac_PowerPC|Macintosh)/
                }, {"s": "QNX", "r": /QNX/}, {"s": "UNIX", "r": /UNIX/}, {"s": "BeOS", "r": /BeOS/}, {"s": "OS/2", "r": /OS\/2/}, {
                    "s": "SearchBot",
                    "r": P
                }];
                for (_ in m)if (w = m[_], w.r.test(o)) {
                    l = w.s;
                    break
                }
                switch (p = "unknown", /Windows/.test(l) && "Windows Phone" != l && (p = /Windows (.*)/.exec(l)[1], l = "Windows"), l) {
                    case"Mac OSX":
                        p = /Mac OS X (10[\.\_\d]+)/.exec(o)[1];
                        break;
                    case"Windows Phone":
                        p = (/Windows Phone ([\.\_\d]+)/.exec(o) || ["", "8.0"])[1];
                        break;
                    case"Android":
                        p = /Android ([\.\_\d]+)/.exec(o)[1];
                        break;
                    case"iOS":
                        p = /OS (\d+)_(\d+)_?(\d+)?/.exec(i), p = p[1] + "." + p[2] + "." + (0 | p[3])
                }
                return b[J.os] = l, g = l, b[J.os_version] = p, v = navigator.language || navigator.browserLanguage || navigator.systemLanguage || navigator.userLanguage, "undefined" != typeof v && (b[J.locale] = v), "undefined" != typeof document.referrer && document.referrer.length && (y = I.exec(document.referrer), y && y[11] && y[11] != z.hostname && (b[J.referrer] = document.referrer)), b
            }

            function m(e) {
                var n = new Image(1, 1);
                //n.src = k + "a.gif?" + _(e)
            }

            function _(e) {
                var n, t = [];
                for (n in e)t.push(n + "=" + encodeURIComponent(e[n]));
                return t.join("&")
            }

            function w(e) {
                return e && "/" == e.substr(e.length - 1) ? e.substr(0, e.length - 1) : e
            }

            var p, g, h, v, y, b = "1.3", j = !1, x = !1, k = ("https:" == location.protocol ? "https://" : "http://") + "analysis.paituo.com/analysis/", S = 30, O = [], W = [], N = {}, D = !0, T = 0, q = null, M = 0, E = 0, B = !0, I = /^(((([^:\/#\?]+:)?(?:(\/\/)((?:(([^:@\/#\?]+)(?:\:([^:@\/#\?]+))?)@)?(([^:\/#\?\]\[]+|\[[^\/\]@#?]+\])(?:\:([0-9]+))?))?)?)?((\/?(?:[^\/\?#]+\/+)*)([^\?#]*)))?(\?[^#]+)?)(#.*)?/, P = /(nuhk|Googlebot|Yammybot|Openbot|Slurp|MSNBot|Ask Jeeves\/Teoma|ia_archiver|bingbot|Google Web Preview|Mediapartners-Google|AdsBot-Google|Baiduspider|Ezooms|YahooSeeker|AltaVista|AVSearch|Mercator|Scooter|InfoSeek|Ultraseek|Lycos|Wget|YandexBot|Yandex|YaDirectFetcher|SiteBot|Exabot|AhrefsBot|MJ12bot|TurnitinBot|magpie-crawler|Nutch Crawler|CMS Crawler|rogerbot|Domnutch|ssearch_bot|XoviBot|netseer|digincore|fr-crawler|wesee|AliasIO)/, A = /(sogou|soso|baidu|google|youdao|yahoo|bing|118114|biso|gougou|ifeng|ivc|sooule|niuhu|biso|360)/gi, C = !1, L = !0, U = "200034", X = [[/open\.weixin\.qq\.com/i, "200035"], [/wx\.qq\.com/i, "200035"], [/jr\.ifeng\.com/i, "200034"], [/www\.ifeng\.com/i, "200036"], [/finance\.ifeng\.com/i, "200036"], [/18\.ifeng\.com/i, "200036"], [/ds\.ifeng\.com/i, "200036"], [/3g\.ifeng\.com/i, "200036"], [/api\.newad\.ifeng\.com/i, "200036"], [/i\.ifeng\.com/i, "200036"], [/wap\.ifeng\.com/i, "200036"], [/app\.finance\.ifeng\.com/i, "200036"], [/chaogu\.ifeng\.com/i, "200036"], [/www\.google\.com/i, "200037"], [/www\.so\.com/i, "200038"], [/www\.sogou\.com/i, "200039"], [/wap\.sogou\.com/i, "200039"], [/m\.sogou\.com/i, "200039"], [/so\.m\.sm\.cn/i, "200040"], [/m\.yz\.sm\.cn/i, "200040"], [/yz\.m\.sm\.cn/i, "200040"], [/m\.sp\.sm\.cn/i, "200040"], [/cn\.bing\.com/i, "200041"], [/www\.bing\.com/i, "200041"], [/pos\.baidu\.com/i, "200042"], [/tieba\.baidu\.com/i, "200042"], [/m\.baidu\.com/i, "200042"], [/www\.baidu\.com/i, "200042"], [/ad\.too-b\.cn/i, "200043"], [/ad\.red-ad\.com/i, "200044"], [/123\.sogou\.com/i, "200045"], [/e\.firefoxchina\.cn/i, "200046"], [/www\.hao123\.com/i, "200047"], [/hao\.360\.cn/i, "200048"], [/www\.2345\.com/i, "200049"], [/bbs\.zhuankezhijia\.com/i, "200050"], [/i\.easou\.com/i, "200051"], [/www\.wdzj\.com/i, "200052"], [/www\.p2peye\.com/i, "200053"], [/www\.wangdaimishu\.com/i, "200054"], [/www\.wangdaileida\.com/i, "200055"], [/www\.wangdaijiamen\.com/i, "200056"], [/www\.panjinlian\.org/i, "200057"], [/cms\.shhuilin\.com/i, "200058"], [/jrifeng\.daichuqu\.com/i, "200059"], [/www\.daiyicha\.com/i, "200060"], [/www\.76676\.com/i, "200061"], [/www\.51wangdai\.com/i, "200062"], [/www\.lagou\.com/i, "200063"], [/pay\.soopay\.net/i, "200064"], [/mp\.weixinbridge\.com/i, "200065"], [/60\.10\.131\.83/i, "200065"], [/10\.254\.111\.73/i, "200065"], [/120\.132\.63\.203/i, "200065"], [/bzclk\.baidu\.com/i, "200065"], [/10\.254\.111\.32/i, "200065"], [/10\.255\.0\.10/i, "200065"], [/127\.0\.0\.1/i, "200065"], [/10\.255\.0\.230/i, "200065"], [/etradefengfd\.inc/i, "200065"], [/www\.google\.com\.hk/i, "200065"], [/10\.255\.52\.38/i, "200065"], [/10\.254\.111\.76/i, "200065"], [/localhost/i, "200066"], [/beta\.etrade\.fengfd\.com/i, "200066"], [/betaetrade\.fengfd\.com/i, "200066"]], J = {
                "sdk_version": "sv",
                "first_session_time": "fst",
                "last_session_time": "lst",
                "visit_create_time": "vct",
                "visit_times": "vts",
                "seq_num": "sqn",
                "app_key": "ak",
                "secret_key": "sk",
                "device_id": "did",
                "user_id": "uid",
                "channel": "cl",
                "channel_code": "clc",
                "channel_date": "clt",
                "timestamp": "t",
                "ip_address": "ip",
                "city": "cy",
                "country_code": "cc",
                "host_url": "hu",
                "begin_session": "bs",
                "session_duration": "sd",
                "end_session": "es",
                "events": "ets",
                "key": "k",
                "type": "tp",
                "count": "c",
                "duration": "d",
                "width": "w",
                "height": "h",
                "x": "x",
                "y": "y",
                "domain": "dm",
                "name": "n",
                "value": "ve",
                "segments": "sgt",
                "title": "tl",
                "referrer": "rfr",
                "origin": "o",
                "path_name": "pn",
                "view": "v",
                "last_view": "lv",
                "id": "id",
                "href": "hf",
                "text": "txt",
                "metrics": "ms",
                "os": "os",
                "os_version": "osv",
                "app_version": "av",
                "browser": "bs",
                "resolution": "r",
                "locale": "ln",
                "platform": "p",
                "source": "s",
                "system_id": "sysid",
                "organic": "ogc",
                "sum": "sm"
            }, V = !0, z = window.location, F = navigator.userAgent, Y = new Array("Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod"), G = window._localStorage, H = function (e, n, t) {
                t = t || !1;
                var i, o = !1;
                if ("undefined" != typeof localStorage) {
                    o = !0;
                    try {
                        G.setItem("testLocal", !0)
                    } catch (r) {
                        o = !1
                    }
                }
                if ("undefined" != typeof n && null !== n && ("object" == typeof n && (n = JSON.stringify(n)), o ? G.setItem(e, n) : t || Q(e, n, {"expires": 30})), "undefined" == typeof n) {
                    o ? i = G.getItem(e) : t || (i = Q(e));
                    try {
                        i = JSON.parse(i)
                    } catch (r) {
                        i = i
                    }
                    return i
                }
                null === n && (o ? G.removeItem(e) : t || Q(e, null))
            }, K = function (e, n) {
                for (var t in n)e[t] = n[t];
                return e
            }, Q = function (e, n, t) {
                var i, o, r, a, s, c, d, u, f;
                if ("undefined" == typeof n) {
                    if (c = null, document.cookie && "" != document.cookie)for (d = document.cookie.split(";"), u = 0; u < d.length; u++)if (f = d[u].replace(/(^\s*)|(\s*$)/g, ""), f.substring(0, e.length + 1) == e + "=") {
                        c = decodeURIComponent(f.substring(e.length + 1));
                        break
                    }
                    return c
                }
                t = t || {}, null === n && (n = "", t = K({}, t), t.expires = -1), i = "", t.expires && ("number" == typeof t.expires || t.expires.toUTCString) && ("number" == typeof t.expires ? (o = new Date, o.setTime(o.getTime() + 864e5 * t.expires)) : o = t.expires, i = "; expires=" + o.toUTCString()), r = t.path ? "; path=" + t.path : "", a = t.domain ? "; domain=" + t.domain : "", s = t.secure ? "; secure" : "", document.cookie = [e, "=", encodeURIComponent(n), i, r, a, s].join("")
            }, R = function (e, n, t) {
                return e.addEventListener ? e.addEventListener(n, t, !1) : e.attachEvent ? e.attachEvent("on" + n, t) : e["on" + n] = t
            }, $ = n(5e3);
            for (y = 0; y < Y.length; y++)if (F.indexOf(Y[y]) > 0) {
                V = !1;
                break
            }
            Date.now || (Date.now = function () {
                return (new Date).getTime() || +new Date
            }), t(), e.init = function (n) {
                j || (v = i(), j = !0, O = H("fjr_queue") || [], N = H("fjr_timed") || {}, W = H("fjr_event") || [], n = n || {}, S = n.interval || e.interval || S, e.debug = n.debug || e.debug || !1, e.app_key = n.app_key || e.app_key || null, e.device_id = n.device_id || e.device_id || s(), e.host_url = w(n.host_url || e.host_url), e.app_version = n.app_version || e.app_version || "0.1", e.country_code = n.country_code || e.country_code || "", e.city = n.city || e.city || null, e.ip_address = n.ip_address || e.ip_address || null, e.ignore_bots = n.ignore_bots || e.ignore_bots || !0, e.q = e.q || [], P.test(F) && (C = !0), e.q.constructor !== Array && (e.q = []), f())
            }, e._setProperty = function (e, t) {
                var i = "fjr_properties", o = {};
                if (JSON && JSON.stringify && e) {
                    if (Q(i))try {
                        o = JSON.parse(Q(i))
                    } catch (r) {
                    }
                    t ? o[e] = t : delete o[e], Q(i, JSON.stringify(o), n())
                }
            }, e._setUserId = function (e) {
                Q("fjr_user_id", e, n(182))
            }, e.begin_session = function (e) {
                if (!x) {
                    p = i(), x = !0, D = !e;
                    var n = {};
                    n.begin_session = 1, n.domain = z.hostname, n.path_name = z.pathname, n.origin = z.href, n.metrics = JSON.stringify(l()), u(n)
                }
            }, e.session_duration = function (e) {
                x && u({"session_duration": e})
            }, e.end_session = function (e) {
                d(0 == e || 2 == e ? e : 1), x && (x = !1, f())
            }, e.change_id = function (n, t) {
                var i = e.device_id;
                e.device_id = n, H("fjr_did", e.device_id), t && u({"old_device_id": i})
            }, e.add_event = function (e) {
                var n, t;
                if (e.key) {
                    e.domain = z.hostname, e.path_name = q || z.pathname, e.origin = z.href, e.timestamp = i(), e.seq_num = Q("fjr_sqn") || null, e.count || (e.count = 1), n = {};
                    for (t in e)(e[t] || 0 == e[t]) && (n[J[t]] = e[t]);
                    1 == n.v && K(n, l()), W.push(n), H("fjr_event", W)
                }
            }, e.custom_event = function (n) {
                n = n || {}, n.key = n.key || "custom_event", e.add_event(n)
            }, e.start_event = function (e) {
                N[e] || (N[e] = i(), H("fjr_timed", N))
            }, e.end_event = function (n) {
                "string" == typeof n && (n = {"key": n}), n.key && N[n.key] && (n.duration = i() - N[n.key], e.add_event(n), delete N[n.key], H("fjr_timed", N))
            }, e.stop_time = function () {
                L = !1, T = i() - p, E = i() - M
            }, e.start_time = function () {
                L = !0, p = i() - T, M = i() - E, E = 0
            }, e.track_sessions = function () {
                function n() {
                    document[t] ? e.stop_time() : e.start_time()
                }

                e.start_time(), window.onbeforeunload = function () {
                    e.end_session(0)
                }, R(window, "unload", function () {
                    e.end_session(0)
                });
                var t = "hidden";
                t in document ? document.addEventListener("visibilitychange", n) : (t = "mozHidden") in document ? document.addEventListener("mozvisibilitychange", n) : (t = "webkitHidden") in document ? document.addEventListener("webkitvisibilitychange", n) : (t = "msHidden") in document ? document.addEventListener("msvisibilitychange", n) : "onfocusin" in document ? (R(window, "focusin", function () {
                    e.start_time()
                }), R(window, "focusout", function () {
                    e.stop_time()
                })) : (R(window, "focus", function () {
                    e.start_time()
                }), R(window, "blur", function () {
                    e.stop_time()
                }), R(window, "pageshow", function () {
                    e.start_time()
                }), R(window, "pagehide", function () {
                    e.stop_time()
                }))
            }, e.track_pageview = function (t, o) {
                var r, a, s, c, d;
                t = t || z.pathname, q = t, M = i(), r = new Date, a = 30, s = 0 | Q("fjr_vts"), r.setTime(r.getTime() + 6e4 * a), s ? (c = 0 | Q("fjr_sqn"), Q("fjr_sqn", c + 1, n(r))) : Q("fjr_sqn", 1, n(r)), g || l(), d = {
                    "key": "view",
                    "view": 1,
                    "title": document.title,
                    "referrer": o || document.referrer || null,
                    "path_name": t,
                    "domain": z.hostname,
                    "origin": z.href,
                    "last_session_time": Q("fjr_lst") || null,
                    "first_session_time": Q("fjr_fst") || null,
                    "seq_num": Q("fjr_sqn") || null
                }, e.add_event(d)
            }, e.track_view = function (n, t) {
                e.track_pageview(n, t)
            }, e.track_clicks = function (n) {
                function t(n) {
                    if (i) {
                        i = !1, o(n);
                        var t = r(), s = a();
                        e.add_event({
                            "key": "action",
                            "type": "click",
                            "x": n.pageX,
                            "y": n.pageY,
                            "width": s,
                            "height": t,
                            "origin": z.href
                        }), setTimeout(function () {
                            i = !0
                        }, 300)
                    }
                }

                n = n || document;
                var i = !0;
                V ? R(n, "mousedown", t) : R(n, "click", t)
            }, e.track_links = function (n) {
                function t() {
                    function t(n, t) {
                        var i, r;
                        o(n), i = {
                            "key": "link_click",
                            "href": t.href,
                            "name": t.attributes.fen && t.attributes.fen.nodeValue || t.id || t.name || null,
                            "origin": z.href,
                            "x": n.pageX,
                            "y": n.pageY
                        }, e.add_event(i), "undefined" == typeof t.href || "_blank" === t.target || n.metaKey || n.altKey || n.ctrlKey || n.shiftKey || (r = t.href.replace(z.href.split("#")[0], ""), 0 !== r.indexOf("#") && e.end_session(2))
                    }

                    "undefined" != typeof n.getElementsByTagName && R(n, "click", function (e) {
                        var n, i;
                        e = e || event, n = e.target || e.srcElement, i = !1;
                        do"a" == n.nodeName.toLowerCase() ? (i = !1, t(e, n)) : (i = !0, n = n.parentNode); while (n && i)
                    })
                }

                n = n || document, "complete" === document.readyState ? t() : R(window, "load", t)
            }
        }(window._fja = window._fja || {}), _fja.q = _fja.q || [], _fja.q.push(["track_pageview"]), _fja.q.push(["track_sessions"]), _fja.q.push(["track_links"])
    }, {}]
}, {}, [1]);
