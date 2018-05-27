!function () {
    "use strict";
    function e(t, n, r) {
        return ("string" == typeof n ? n : n.toString()).replace(t.define || o, function (e, n, i, o) {
            return 0 === n.indexOf("def.") && (n = n.substring(4)), n in r || (":" === i ? (t.defineParams && o.replace(t.defineParams, function (e, t, i) {
                r[n] = {"arg": t, "text": i}
            }), n in r || (r[n] = o)) : new Function("def", "def['" + n + "']=" + o)(r)), ""
        }).replace(t.use || o, function (n, i) {
            t.useParams && (i = i.replace(t.useParams, function (e, t, n, i) {
                if (r[n] && r[n].arg && i) {
                    var o = (n + ":" + i).replace(/'|\\/g, "_");
                    return r.__exp = r.__exp || {}, r.__exp[o] = r[n].text.replace(new RegExp("(^|[^\\w$])" + r[n].arg + "([^\\w$])", "g"), "$1" + i + "$2"), t + "def.__exp['" + o + "']"
                }
            }));
            var o = new Function("def", "return " + i)(r);
            return o ? e(t, o, r) : o
        })
    }

    function t(e) {
        return e.replace(/\\('|\\)/g, "$1").replace(/[\r\t\n]/g, " ")
    }

    var n, r = {
        "version": "1.0.3",
        "templateSettings": {
            "evaluate": /\{\{([\s\S]+?(\}?)+)\}\}/g,
            "interpolate": /\{\{=([\s\S]+?)\}\}/g,
            "encode": /\{\{!([\s\S]+?)\}\}/g,
            "use": /\{\{#([\s\S]+?)\}\}/g,
            "useParams": /(^|[^\w$])def(?:\.|\[[\'\"])([\w$\.]+)(?:[\'\"]\])?\s*\:\s*([\w$\.]+|\"[^\"]+\"|\'[^\']+\'|\{[^\}]+\})/g,
            "define": /\{\{##\s*([\w\.$]+)\s*(\:|=)([\s\S]+?)#\}\}/g,
            "defineParams": /^\s*([\w$]+):([\s\S]+)/,
            "conditional": /\{\{\?(\?)?\s*([\s\S]*?)\s*\}\}/g,
            "iterate": /\{\{~\s*(?:\}\}|([\s\S]+?)\s*\:\s*([\w$]+)\s*(?:\:\s*([\w$]+))?\s*\}\})/g,
            "varname": "it",
            "strip": !0,
            "append": !0,
            "selfcontained": !1,
            "doNotSkipEncoded": !1
        },
        "template": void 0,
        "compile": void 0
    };
    r.encodeHTMLSource = function (e) {
        var t = {
            "&": "&#38;",
            "<": "&#60;",
            ">": "&#62;",
            '"': "&#34;",
            "'": "&#39;",
            "/": "&#47;"
        }, n = e ? /[&<>"'\/]/g : /&(?!#?\w+;)|<|>|"|'|\//g;
        return function (e) {
            return e ? e.toString().replace(n, function (e) {
                return t[e] || e
            }) : ""
        }
    }, n = function () {
        return this || (0, eval)("this")
    }(), "undefined" != typeof module && module.exports ? module.exports = r : "function" == typeof define && define.amd ? define(function () {
        return r
    }) : n.doT = r;
    var i = {
        "append": {"start": "'+(", "end": ")+'", "startencode": "'+encodeHTML("},
        "split": {"start": "';out+=(", "end": ");out+='", "startencode": "';out+=encodeHTML("}
    }, o = /$^/;
    r.template = function (a, s, u) {
        s = s || r.templateSettings;
        var l, c, f = s.append ? i.append : i.split, d = 0, h = s.use || s.define ? e(s, a, u || {}) : a;
        h = ("var out='" + (s.strip ? h.replace(/(^|\r|\n)\t* +| +\t*(\r|\n|$)/g, " ").replace(/\r|\n|\t|\/\*[\s\S]*?\*\//g, "") : h).replace(/'|\\/g, "\\$&").replace(s.interpolate || o, function (e, n) {
            return f.start + t(n) + f.end
        }).replace(s.encode || o, function (e, n) {
            return l = !0, f.startencode + t(n) + f.end
        }).replace(s.conditional || o, function (e, n, r) {
            return n ? r ? "';}else if(" + t(r) + "){out+='" : "';}else{out+='" : r ? "';if(" + t(r) + "){out+='" : "';}out+='"
        }).replace(s.iterate || o, function (e, n, r, i) {
            return n ? (d += 1, c = i || "i" + d, n = t(n), "';var arr" + d + "=" + n + ";if(arr" + d + "){var " + r + "," + c + "=-1,l" + d + "=arr" + d + ".length-1;while(" + c + "<l" + d + "){" + r + "=arr" + d + "[" + c + "+=1];out+='") : "';} } out+='"
        }).replace(s.evaluate || o, function (e, n) {
            return "';" + t(n) + "out+='"
        }) + "';return out;").replace(/\n/g, "\\n").replace(/\t/g, "\\t").replace(/\r/g, "\\r").replace(/(\s|;|\}|^|\{)out\+='';/g, "$1").replace(/\+''/g, ""), l && (s.selfcontained || !n || n._encodeHTML || (n._encodeHTML = r.encodeHTMLSource(s.doNotSkipEncoded)), h = "var encodeHTML = typeof _encodeHTML !== 'undefined' ? _encodeHTML : (" + r.encodeHTMLSource.toString() + "(" + (s.doNotSkipEncoded || "") + "));" + h);
        try {
            return new Function(s.varname, h)
        } catch (p) {
            throw"undefined" != typeof console, p
        }
    }, r.compile = function (e, t) {
        return r.template(e, null, t)
    }, r.combine = function () {
        var e = Array.prototype.slice.call(arguments);
        if ("object" == typeof e)for (var t = 0; t < e.length; t++);
    }
}(), function () {
    function e(e) {
        function t(t, n, r, i, o, a) {
            for (; o >= 0 && o < a; o += e) {
                var s = i ? i[o] : o;
                r = n(r, t[s], s, t)
            }
            return r
        }

        return function (n, r, i, o) {
            r = w(r, o, 4);
            var a = !M(n) && v.keys(n), s = (a || n).length, u = e > 0 ? 0 : s - 1;
            return arguments.length < 3 && (i = n[a ? a[u] : u], u += e), t(n, r, i, a, u, s)
        }
    }

    function t(e) {
        return function (t, n, r) {
            n = b(n, r);
            for (var i = T(t), o = e > 0 ? 0 : i - 1; o >= 0 && o < i; o += e)if (n(t[o], o, t))return o;
            return -1
        }
    }

    function n(e, t, n) {
        return function (r, i, o) {
            var a = 0, s = T(r);
            if ("number" == typeof o)e > 0 ? a = o >= 0 ? o : Math.max(o + s, a) : s = o >= 0 ? Math.min(o + 1, s) : o + s + 1; else if (n && o && s)return o = n(r, i), r[o] === i ? o : -1;
            if (i !== i)return o = t(c.call(r, a, s), v.isNaN), o >= 0 ? o + a : -1;
            for (o = e > 0 ? a : s - 1; o >= 0 && o < s; o += e)if (r[o] === i)return o;
            return -1
        }
    }

    function r(e, t) {
        var n = O.length, r = e.constructor, i = v.isFunction(r) && r.prototype || s, o = "constructor";
        for (v.has(e, o) && !v.contains(t, o) && t.push(o); n--;)o = O[n], o in e && e[o] !== i[o] && !v.contains(t, o) && t.push(o)
    }

    var i = this, o = i._, a = Array.prototype, s = Object.prototype, u = Function.prototype, l = a.push, c = a.slice, f = s.toString, d = s.hasOwnProperty, h = Array.isArray, p = Object.keys, m = u.bind, g = Object.create, y = function () {
    }, v = function (e) {
        return e instanceof v ? e : this instanceof v ? void(this._wrapped = e) : new v(e)
    };
    "undefined" != typeof exports ? ("undefined" != typeof module && module.exports && (exports = module.exports = v), exports._ = v) : i._ = v, v.VERSION = "1.8.3";
    var w = function (e, t, n) {
        if (void 0 === t)return e;
        switch (null == n ? 3 : n) {
            case 1:
                return function (n) {
                    return e.call(t, n)
                };
            case 2:
                return function (n, r) {
                    return e.call(t, n, r)
                };
            case 3:
                return function (n, r, i) {
                    return e.call(t, n, r, i)
                };
            case 4:
                return function (n, r, i, o) {
                    return e.call(t, n, r, i, o)
                }
        }
        return function () {
            return e.apply(t, arguments)
        }
    }, b = function (e, t, n) {
        return null == e ? v.identity : v.isFunction(e) ? w(e, t, n) : v.isObject(e) ? v.matcher(e) : v.property(e)
    };
    v.iteratee = function (e, t) {
        return b(e, t, 1 / 0)
    };
    var x = function (e, t) {
        return function (n) {
            var r = arguments.length;
            if (r < 2 || null == n)return n;
            for (var i = 1; i < r; i++)for (var o = arguments[i], a = e(o), s = a.length, u = 0; u < s; u++) {
                var l = a[u];
                t && void 0 !== n[l] || (n[l] = o[l])
            }
            return n
        }
    }, _ = function (e) {
        if (!v.isObject(e))return {};
        if (g)return g(e);
        y.prototype = e;
        var t = new y;
        return y.prototype = null, t
    }, k = function (e) {
        return function (t) {
            return null == t ? void 0 : t[e]
        }
    }, S = Math.pow(2, 53) - 1, T = k("length"), M = function (e) {
        var t = T(e);
        return "number" == typeof t && t >= 0 && t <= S
    };
    v.each = v.forEach = function (e, t, n) {
        t = w(t, n);
        var r, i;
        if (M(e))for (r = 0, i = e.length; r < i; r++)t(e[r], r, e); else {
            var o = v.keys(e);
            for (r = 0, i = o.length; r < i; r++)t(e[o[r]], o[r], e)
        }
        return e
    }, v.map = v.collect = function (e, t, n) {
        t = b(t, n);
        for (var r = !M(e) && v.keys(e), i = (r || e).length, o = Array(i), a = 0; a < i; a++) {
            var s = r ? r[a] : a;
            o[a] = t(e[s], s, e)
        }
        return o
    }, v.reduce = v.foldl = v.inject = e(1), v.reduceRight = v.foldr = e(-1), v.find = v.detect = function (e, t, n) {
        var r;
        if (r = M(e) ? v.findIndex(e, t, n) : v.findKey(e, t, n), void 0 !== r && r !== -1)return e[r]
    }, v.filter = v.select = function (e, t, n) {
        var r = [];
        return t = b(t, n), v.each(e, function (e, n, i) {
            t(e, n, i) && r.push(e)
        }), r
    }, v.reject = function (e, t, n) {
        return v.filter(e, v.negate(b(t)), n)
    }, v.every = v.all = function (e, t, n) {
        t = b(t, n);
        for (var r = !M(e) && v.keys(e), i = (r || e).length, o = 0; o < i; o++) {
            var a = r ? r[o] : o;
            if (!t(e[a], a, e))return !1
        }
        return !0
    }, v.some = v.any = function (e, t, n) {
        t = b(t, n);
        for (var r = !M(e) && v.keys(e), i = (r || e).length, o = 0; o < i; o++) {
            var a = r ? r[o] : o;
            if (t(e[a], a, e))return !0
        }
        return !1
    }, v.contains = v.includes = v.include = function (e, t, n, r) {
        return M(e) || (e = v.values(e)), ("number" != typeof n || r) && (n = 0), v.indexOf(e, t, n) >= 0
    }, v.invoke = function (e, t) {
        var n = c.call(arguments, 2), r = v.isFunction(t);
        return v.map(e, function (e) {
            var i = r ? t : e[t];
            return null == i ? i : i.apply(e, n)
        })
    }, v.pluck = function (e, t) {
        return v.map(e, v.property(t))
    }, v.where = function (e, t) {
        return v.filter(e, v.matcher(t))
    }, v.findWhere = function (e, t) {
        return v.find(e, v.matcher(t))
    }, v.max = function (e, t, n) {
        var r, i, o = -(1 / 0), a = -(1 / 0);
        if (null == t && null != e) {
            e = M(e) ? e : v.values(e);
            for (var s = 0, u = e.length; s < u; s++)r = e[s], r > o && (o = r)
        } else t = b(t, n), v.each(e, function (e, n, r) {
            i = t(e, n, r), (i > a || i === -(1 / 0) && o === -(1 / 0)) && (o = e, a = i)
        });
        return o
    }, v.min = function (e, t, n) {
        var r, i, o = 1 / 0, a = 1 / 0;
        if (null == t && null != e) {
            e = M(e) ? e : v.values(e);
            for (var s = 0, u = e.length; s < u; s++)r = e[s], r < o && (o = r)
        } else t = b(t, n), v.each(e, function (e, n, r) {
            i = t(e, n, r), (i < a || i === 1 / 0 && o === 1 / 0) && (o = e, a = i)
        });
        return o
    }, v.shuffle = function (e) {
        for (var t, n = M(e) ? e : v.values(e), r = n.length, i = Array(r), o = 0; o < r; o++)t = v.random(0, o), t !== o && (i[o] = i[t]), i[t] = n[o];
        return i
    }, v.sample = function (e, t, n) {
        return null == t || n ? (M(e) || (e = v.values(e)), e[v.random(e.length - 1)]) : v.shuffle(e).slice(0, Math.max(0, t))
    }, v.sortBy = function (e, t, n) {
        return t = b(t, n), v.pluck(v.map(e, function (e, n, r) {
            return {"value": e, "index": n, "criteria": t(e, n, r)}
        }).sort(function (e, t) {
            var n = e.criteria, r = t.criteria;
            if (n !== r) {
                if (n > r || void 0 === n)return 1;
                if (n < r || void 0 === r)return -1
            }
            return e.index - t.index
        }), "value")
    };
    var D = function (e) {
        return function (t, n, r) {
            var i = {};
            return n = b(n, r), v.each(t, function (r, o) {
                var a = n(r, o, t);
                e(i, r, a)
            }), i
        }
    };
    v.groupBy = D(function (e, t, n) {
        v.has(e, n) ? e[n].push(t) : e[n] = [t]
    }), v.indexBy = D(function (e, t, n) {
        e[n] = t
    }), v.countBy = D(function (e, t, n) {
        v.has(e, n) ? e[n]++ : e[n] = 1
    }), v.toArray = function (e) {
        return e ? v.isArray(e) ? c.call(e) : M(e) ? v.map(e, v.identity) : v.values(e) : []
    }, v.size = function (e) {
        return null == e ? 0 : M(e) ? e.length : v.keys(e).length
    }, v.partition = function (e, t, n) {
        t = b(t, n);
        var r = [], i = [];
        return v.each(e, function (e, n, o) {
            (t(e, n, o) ? r : i).push(e)
        }), [r, i]
    }, v.first = v.head = v.take = function (e, t, n) {
        if (null != e)return null == t || n ? e[0] : v.initial(e, e.length - t)
    }, v.initial = function (e, t, n) {
        return c.call(e, 0, Math.max(0, e.length - (null == t || n ? 1 : t)))
    }, v.last = function (e, t, n) {
        if (null != e)return null == t || n ? e[e.length - 1] : v.rest(e, Math.max(0, e.length - t))
    }, v.rest = v.tail = v.drop = function (e, t, n) {
        return c.call(e, null == t || n ? 1 : t)
    }, v.compact = function (e) {
        return v.filter(e, v.identity)
    };
    var C = function (e, t, n, r) {
        for (var i = [], o = 0, a = r || 0, s = T(e); a < s; a++) {
            var u = e[a];
            if (M(u) && (v.isArray(u) || v.isArguments(u))) {
                t || (u = C(u, t, n));
                var l = 0, c = u.length;
                for (i.length += c; l < c;)i[o++] = u[l++]
            } else n || (i[o++] = u)
        }
        return i
    };
    v.flatten = function (e, t) {
        return C(e, t, !1)
    }, v.without = function (e) {
        return v.difference(e, c.call(arguments, 1))
    }, v.uniq = v.unique = function (e, t, n, r) {
        v.isBoolean(t) || (r = n, n = t, t = !1), null != n && (n = b(n, r));
        for (var i = [], o = [], a = 0, s = T(e); a < s; a++) {
            var u = e[a], l = n ? n(u, a, e) : u;
            t ? (a && o === l || i.push(u), o = l) : n ? v.contains(o, l) || (o.push(l), i.push(u)) : v.contains(i, u) || i.push(u)
        }
        return i
    }, v.union = function () {
        return v.uniq(C(arguments, !0, !0))
    }, v.intersection = function (e) {
        for (var t = [], n = arguments.length, r = 0, i = T(e); r < i; r++) {
            var o = e[r];
            if (!v.contains(t, o)) {
                for (var a = 1; a < n && v.contains(arguments[a], o); a++);
                a === n && t.push(o)
            }
        }
        return t
    }, v.difference = function (e) {
        var t = C(arguments, !0, !0, 1);
        return v.filter(e, function (e) {
            return !v.contains(t, e)
        })
    }, v.zip = function () {
        return v.unzip(arguments)
    }, v.unzip = function (e) {
        for (var t = e && v.max(e, T).length || 0, n = Array(t), r = 0; r < t; r++)n[r] = v.pluck(e, r);
        return n
    }, v.object = function (e, t) {
        for (var n = {}, r = 0, i = T(e); r < i; r++)t ? n[e[r]] = t[r] : n[e[r][0]] = e[r][1];
        return n
    }, v.findIndex = t(1), v.findLastIndex = t(-1), v.sortedIndex = function (e, t, n, r) {
        n = b(n, r, 1);
        for (var i = n(t), o = 0, a = T(e); o < a;) {
            var s = Math.floor((o + a) / 2);
            n(e[s]) < i ? o = s + 1 : a = s
        }
        return o
    }, v.indexOf = n(1, v.findIndex, v.sortedIndex), v.lastIndexOf = n(-1, v.findLastIndex), v.range = function (e, t, n) {
        null == t && (t = e || 0, e = 0), n = n || 1;
        for (var r = Math.max(Math.ceil((t - e) / n), 0), i = Array(r), o = 0; o < r; o++, e += n)i[o] = e;
        return i
    };
    var E = function (e, t, n, r, i) {
        if (!(r instanceof t))return e.apply(n, i);
        var o = _(e.prototype), a = e.apply(o, i);
        return v.isObject(a) ? a : o
    };
    v.bind = function (e, t) {
        if (m && e.bind === m)return m.apply(e, c.call(arguments, 1));
        if (!v.isFunction(e))throw new TypeError("Bind must be called on a function");
        var n = c.call(arguments, 2), r = function () {
            return E(e, r, t, this, n.concat(c.call(arguments)))
        };
        return r
    }, v.partial = function (e) {
        var t = c.call(arguments, 1), n = function () {
            for (var r = 0, i = t.length, o = Array(i), a = 0; a < i; a++)o[a] = t[a] === v ? arguments[r++] : t[a];
            for (; r < arguments.length;)o.push(arguments[r++]);
            return E(e, n, this, this, o)
        };
        return n
    }, v.bindAll = function (e) {
        var t, n, r = arguments.length;
        if (r <= 1)throw new Error("bindAll must be passed function names");
        for (t = 1; t < r; t++)n = arguments[t], e[n] = v.bind(e[n], e);
        return e
    }, v.memoize = function (e, t) {
        var n = function (r) {
            var i = n.cache, o = "" + (t ? t.apply(this, arguments) : r);
            return v.has(i, o) || (i[o] = e.apply(this, arguments)), i[o]
        };
        return n.cache = {}, n
    }, v.delay = function (e, t) {
        var n = c.call(arguments, 2);
        return setTimeout(function () {
            return e.apply(null, n)
        }, t)
    }, v.defer = v.partial(v.delay, v, 1), v.throttle = function (e, t, n) {
        var r, i, o, a = null, s = 0;
        n || (n = {});
        var u = function () {
            s = n.leading === !1 ? 0 : v.now(), a = null, o = e.apply(r, i), a || (r = i = null)
        };
        return function () {
            var l = v.now();
            s || n.leading !== !1 || (s = l);
            var c = t - (l - s);
            return r = this, i = arguments, c <= 0 || c > t ? (a && (clearTimeout(a), a = null), s = l, o = e.apply(r, i), a || (r = i = null)) : a || n.trailing === !1 || (a = setTimeout(u, c)), o
        }
    }, v.debounce = function (e, t, n) {
        var r, i, o, a, s, u = function () {
            var l = v.now() - a;
            l < t && l >= 0 ? r = setTimeout(u, t - l) : (r = null, n || (s = e.apply(o, i), r || (o = i = null)))
        };
        return function () {
            o = this, i = arguments, a = v.now();
            var l = n && !r;
            return r || (r = setTimeout(u, t)), l && (s = e.apply(o, i), o = i = null), s
        }
    }, v.wrap = function (e, t) {
        return v.partial(t, e)
    }, v.negate = function (e) {
        return function () {
            return !e.apply(this, arguments)
        }
    }, v.compose = function () {
        var e = arguments, t = e.length - 1;
        return function () {
            for (var n = t, r = e[t].apply(this, arguments); n--;)r = e[n].call(this, r);
            return r
        }
    }, v.after = function (e, t) {
        return function () {
            if (--e < 1)return t.apply(this, arguments)
        }
    }, v.before = function (e, t) {
        var n;
        return function () {
            return --e > 0 && (n = t.apply(this, arguments)), e <= 1 && (t = null), n
        }
    }, v.once = v.partial(v.before, 2);
    var A = !{"toString": null}.propertyIsEnumerable("toString"), O = ["valueOf", "isPrototypeOf", "toString", "propertyIsEnumerable", "hasOwnProperty", "toLocaleString"];
    v.keys = function (e) {
        if (!v.isObject(e))return [];
        if (p)return p(e);
        var t = [];
        for (var n in e)v.has(e, n) && t.push(n);
        return A && r(e, t), t
    }, v.allKeys = function (e) {
        if (!v.isObject(e))return [];
        var t = [];
        for (var n in e)t.push(n);
        return A && r(e, t), t
    }, v.values = function (e) {
        for (var t = v.keys(e), n = t.length, r = Array(n), i = 0; i < n; i++)r[i] = e[t[i]];
        return r
    }, v.mapObject = function (e, t, n) {
        t = b(t, n);
        for (var r, i = v.keys(e), o = i.length, a = {}, s = 0; s < o; s++)r = i[s], a[r] = t(e[r], r, e);
        return a
    }, v.pairs = function (e) {
        for (var t = v.keys(e), n = t.length, r = Array(n), i = 0; i < n; i++)r[i] = [t[i], e[t[i]]];
        return r
    }, v.invert = function (e) {
        for (var t = {}, n = v.keys(e), r = 0, i = n.length; r < i; r++)t[e[n[r]]] = n[r];
        return t
    }, v.functions = v.methods = function (e) {
        var t = [];
        for (var n in e)v.isFunction(e[n]) && t.push(n);
        return t.sort()
    }, v.extend = x(v.allKeys), v.extendOwn = v.assign = x(v.keys), v.findKey = function (e, t, n) {
        t = b(t, n);
        for (var r, i = v.keys(e), o = 0, a = i.length; o < a; o++)if (r = i[o], t(e[r], r, e))return r
    }, v.pick = function (e, t, n) {
        var r, i, o = {}, a = e;
        if (null == a)return o;
        v.isFunction(t) ? (i = v.allKeys(a), r = w(t, n)) : (i = C(arguments, !1, !1, 1), r = function (e, t, n) {
            return t in n
        }, a = Object(a));
        for (var s = 0, u = i.length; s < u; s++) {
            var l = i[s], c = a[l];
            r(c, l, a) && (o[l] = c)
        }
        return o
    }, v.omit = function (e, t, n) {
        if (v.isFunction(t))t = v.negate(t); else {
            var r = v.map(C(arguments, !1, !1, 1), String);
            t = function (e, t) {
                return !v.contains(r, t)
            }
        }
        return v.pick(e, t, n)
    }, v.defaults = x(v.allKeys, !0), v.create = function (e, t) {
        var n = _(e);
        return t && v.extendOwn(n, t), n
    }, v.clone = function (e) {
        return v.isObject(e) ? v.isArray(e) ? e.slice() : v.extend({}, e) : e
    }, v.tap = function (e, t) {
        return t(e), e
    }, v.isMatch = function (e, t) {
        var n = v.keys(t), r = n.length;
        if (null == e)return !r;
        for (var i = Object(e), o = 0; o < r; o++) {
            var a = n[o];
            if (t[a] !== i[a] || !(a in i))return !1
        }
        return !0
    };
    var N = function (e, t, n, r) {
        if (e === t)return 0 !== e || 1 / e === 1 / t;
        if (null == e || null == t)return e === t;
        e instanceof v && (e = e._wrapped), t instanceof v && (t = t._wrapped);
        var i = f.call(e);
        if (i !== f.call(t))return !1;
        switch (i) {
            case"[object RegExp]":
            case"[object String]":
                return "" + e == "" + t;
            case"[object Number]":
                return +e !== +e ? +t !== +t : 0 === +e ? 1 / +e === 1 / t : +e === +t;
            case"[object Date]":
            case"[object Boolean]":
                return +e === +t
        }
        var o = "[object Array]" === i;
        if (!o) {
            if ("object" != typeof e || "object" != typeof t)return !1;
            var a = e.constructor, s = t.constructor;
            if (a !== s && !(v.isFunction(a) && a instanceof a && v.isFunction(s) && s instanceof s) && "constructor" in e && "constructor" in t)return !1
        }
        n = n || [], r = r || [];
        for (var u = n.length; u--;)if (n[u] === e)return r[u] === t;
        if (n.push(e), r.push(t), o) {
            if (u = e.length, u !== t.length)return !1;
            for (; u--;)if (!N(e[u], t[u], n, r))return !1
        } else {
            var l, c = v.keys(e);
            if (u = c.length, v.keys(t).length !== u)return !1;
            for (; u--;)if (l = c[u], !v.has(t, l) || !N(e[l], t[l], n, r))return !1
        }
        return n.pop(), r.pop(), !0
    };
    v.isEqual = function (e, t) {
        return N(e, t)
    }, v.isEmpty = function (e) {
        return null == e || (M(e) && (v.isArray(e) || v.isString(e) || v.isArguments(e)) ? 0 === e.length : 0 === v.keys(e).length)
    }, v.isElement = function (e) {
        return !(!e || 1 !== e.nodeType)
    }, v.isArray = h || function (e) {
            return "[object Array]" === f.call(e)
        }, v.isObject = function (e) {
        var t = typeof e;
        return "function" === t || "object" === t && !!e
    }, v.each(["Arguments", "Function", "String", "Number", "Date", "RegExp", "Error"], function (e) {
        v["is" + e] = function (t) {
            return f.call(t) === "[object " + e + "]"
        }
    }), v.isArguments(arguments) || (v.isArguments = function (e) {
        return v.has(e, "callee")
    }), "function" != typeof/./ && "object" != typeof Int8Array && (v.isFunction = function (e) {
        return "function" == typeof e || !1
    }), v.isFinite = function (e) {
        return isFinite(e) && !isNaN(parseFloat(e))
    }, v.isNaN = function (e) {
        return v.isNumber(e) && e !== +e
    }, v.isBoolean = function (e) {
        return e === !0 || e === !1 || "[object Boolean]" === f.call(e)
    }, v.isNull = function (e) {
        return null === e
    }, v.isUndefined = function (e) {
        return void 0 === e
    }, v.has = function (e, t) {
        return null != e && d.call(e, t)
    }, v.noConflict = function () {
        return i._ = o, this
    }, v.identity = function (e) {
        return e
    }, v.constant = function (e) {
        return function () {
            return e
        }
    }, v.noop = function () {
    }, v.property = k, v.propertyOf = function (e) {
        return null == e ? function () {
        } : function (t) {
            return e[t]
        }
    }, v.matcher = v.matches = function (e) {
        return e = v.extendOwn({}, e), function (t) {
            return v.isMatch(t, e)
        }
    }, v.times = function (e, t, n) {
        var r = Array(Math.max(0, e));
        t = w(t, n, 1);
        for (var i = 0; i < e; i++)r[i] = t(i);
        return r
    }, v.random = function (e, t) {
        return null == t && (t = e, e = 0), e + Math.floor(Math.random() * (t - e + 1))
    }, v.now = Date.now || function () {
            return (new Date).getTime()
        };
    var j = {"&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;", "'": "&#x27;", "`": "&#x60;"}, Y = v.invert(j), L = function (e) {
        var t = function (t) {
            return e[t]
        }, n = "(?:" + v.keys(e).join("|") + ")", r = RegExp(n), i = RegExp(n, "g");
        return function (e) {
            return e = null == e ? "" : "" + e, r.test(e) ? e.replace(i, t) : e
        }
    };
    v.escape = L(j), v.unescape = L(Y), v.result = function (e, t, n) {
        var r = null == e ? void 0 : e[t];
        return void 0 === r && (r = n), v.isFunction(r) ? r.call(e) : r
    };
    var H = 0;
    v.uniqueId = function (e) {
        var t = ++H + "";
        return e ? e + t : t
    }, v.templateSettings = {"evaluate": /<%([\s\S]+?)%>/g, "interpolate": /<%=([\s\S]+?)%>/g, "escape": /<%-([\s\S]+?)%>/g};
    var P = /(.)^/, F = {
        "'": "'",
        "\\": "\\",
        "\r": "r",
        "\n": "n",
        "\u2028": "u2028",
        "\u2029": "u2029"
    }, R = /\\|'|\r|\n|\u2028|\u2029/g, W = function (e) {
        return "\\" + F[e]
    };
    v.template = function (e, t, n) {
        !t && n && (t = n), t = v.defaults({}, t, v.templateSettings);
        var r = RegExp([(t.escape || P).source, (t.interpolate || P).source, (t.evaluate || P).source].join("|") + "|$", "g"), i = 0, o = "__p+='";
        e.replace(r, function (t, n, r, a, s) {
            return o += e.slice(i, s).replace(R, W), i = s + t.length, n ? o += "'+\n((__t=(" + n + "))==null?'':_.escape(__t))+\n'" : r ? o += "'+\n((__t=(" + r + "))==null?'':__t)+\n'" : a && (o += "';\n" + a + "\n__p+='"), t
        }), o += "';\n", t.variable || (o = "with(obj||{}){\n" + o + "}\n"), o = "var __t,__p='',__j=Array.prototype.join,print=function(){__p+=__j.call(arguments,'');};\n" + o + "return __p;\n";
        try {
            var a = new Function(t.variable || "obj", "_", o)
        } catch (s) {
            throw s.source = o, s
        }
        var u = function (e) {
            return a.call(this, e, v)
        }, l = t.variable || "obj";
        return u.source = "function(" + l + "){\n" + o + "}", u
    }, v.chain = function (e) {
        var t = v(e);
        return t._chain = !0, t
    };
    var I = function (e, t) {
        return e._chain ? v(t).chain() : t
    };
    v.mixin = function (e) {
        v.each(v.functions(e), function (t) {
            var n = v[t] = e[t];
            v.prototype[t] = function () {
                var e = [this._wrapped];
                return l.apply(e, arguments), I(this, n.apply(v, e))
            }
        })
    }, v.mixin(v), v.each(["pop", "push", "reverse", "shift", "sort", "splice", "unshift"], function (e) {
        var t = a[e];
        v.prototype[e] = function () {
            var n = this._wrapped;
            return t.apply(n, arguments), "shift" !== e && "splice" !== e || 0 !== n.length || delete n[0], I(this, n)
        }
    }), v.each(["concat", "join", "slice"], function (e) {
        var t = a[e];
        v.prototype[e] = function () {
            return I(this, t.apply(this._wrapped, arguments))
        }
    }), v.prototype.value = function () {
        return this._wrapped
    }, v.prototype.valueOf = v.prototype.toJSON = v.prototype.value, v.prototype.toString = function () {
        return "" + this._wrapped
    }, "function" == typeof define && define.amd && define("underscore", [], function () {
        return v
    })
}.call(this), function (e, t) {
    "object" == typeof exports && "undefined" != typeof module ? module.exports = t() : "function" == typeof define && define.amd ? define(t) : e.moment = t()
}(this, function () {
    "use strict";
    function e() {
        return mr.apply(null, arguments)
    }

    function t(e) {
        mr = e
    }

    function n(e) {
        return e instanceof Array || "[object Array]" === Object.prototype.toString.call(e)
    }

    function r(e) {
        return null != e && "[object Object]" === Object.prototype.toString.call(e)
    }

    function i(e) {
        var t;
        for (t in e)return !1;
        return !0
    }

    function o(e) {
        return "number" == typeof e || "[object Number]" === Object.prototype.toString.call(e)
    }

    function a(e) {
        return e instanceof Date || "[object Date]" === Object.prototype.toString.call(e)
    }

    function s(e, t) {
        var n, r = [];
        for (n = 0; n < e.length; ++n)r.push(t(e[n], n));
        return r
    }

    function u(e, t) {
        return Object.prototype.hasOwnProperty.call(e, t)
    }

    function l(e, t) {
        for (var n in t)u(t, n) && (e[n] = t[n]);
        return u(t, "toString") && (e.toString = t.toString), u(t, "valueOf") && (e.valueOf = t.valueOf), e
    }

    function c(e, t, n, r) {
        return yt(e, t, n, r, !0).utc()
    }

    function f() {
        return {
            "empty": !1,
            "unusedTokens": [],
            "unusedInput": [],
            "overflow": -2,
            "charsLeftOver": 0,
            "nullInput": !1,
            "invalidMonth": null,
            "invalidFormat": !1,
            "userInvalidated": !1,
            "iso": !1,
            "parsedDateParts": [],
            "meridiem": null
        }
    }

    function d(e) {
        return null == e._pf && (e._pf = f()), e._pf
    }

    function h(e) {
        if (null == e._isValid) {
            var t = d(e), n = yr.call(t.parsedDateParts, function (e) {
                return null != e
            }), r = !isNaN(e._d.getTime()) && t.overflow < 0 && !t.empty && !t.invalidMonth && !t.invalidWeekday && !t.nullInput && !t.invalidFormat && !t.userInvalidated && (!t.meridiem || t.meridiem && n);
            if (e._strict && (r = r && 0 === t.charsLeftOver && 0 === t.unusedTokens.length && void 0 === t.bigHour), null != Object.isFrozen && Object.isFrozen(e))return r;
            e._isValid = r
        }
        return e._isValid
    }

    function p(e) {
        var t = c(NaN);
        return null != e ? l(d(t), e) : d(t).userInvalidated = !0, t
    }

    function m(e) {
        return void 0 === e
    }

    function g(e, t) {
        var n, r, i;
        if (m(t._isAMomentObject) || (e._isAMomentObject = t._isAMomentObject), m(t._i) || (e._i = t._i), m(t._f) || (e._f = t._f), m(t._l) || (e._l = t._l), m(t._strict) || (e._strict = t._strict), m(t._tzm) || (e._tzm = t._tzm), m(t._isUTC) || (e._isUTC = t._isUTC), m(t._offset) || (e._offset = t._offset), m(t._pf) || (e._pf = d(t)), m(t._locale) || (e._locale = t._locale), vr.length > 0)for (n in vr)r = vr[n], i = t[r], m(i) || (e[r] = i);
        return e
    }

    function y(t) {
        g(this, t), this._d = new Date(null != t._d ? t._d.getTime() : NaN), this.isValid() || (this._d = new Date(NaN)), wr === !1 && (wr = !0, e.updateOffset(this), wr = !1)
    }

    function v(e) {
        return e instanceof y || null != e && null != e._isAMomentObject
    }

    function w(e) {
        return e < 0 ? Math.ceil(e) || 0 : Math.floor(e)
    }

    function b(e) {
        var t = +e, n = 0;
        return 0 !== t && isFinite(t) && (n = w(t)), n
    }

    function x(e, t, n) {
        var r, i = Math.min(e.length, t.length), o = Math.abs(e.length - t.length), a = 0;
        for (r = 0; r < i; r++)(n && e[r] !== t[r] || !n && b(e[r]) !== b(t[r])) && a++;
        return a + o
    }

    function _(t) {
        e.suppressDeprecationWarnings === !1 && "undefined" != typeof console && console.warn
    }

    function k(t, n) {
        var r = !0;
        return l(function () {
            if (null != e.deprecationHandler && e.deprecationHandler(null, t), r) {
                for (var i, o = [], a = 0; a < arguments.length; a++) {
                    if (i = "", "object" == typeof arguments[a]) {
                        i += "\n[" + a + "] ";
                        for (var s in arguments[0])i += s + ": " + arguments[0][s] + ", ";
                        i = i.slice(0, -2)
                    } else i = arguments[a];
                    o.push(i)
                }
                _(t + "\nArguments: " + Array.prototype.slice.call(o).join("") + "\n" + (new Error).stack), r = !1
            }
            return n.apply(this, arguments)
        }, n)
    }

    function S(t, n) {
        null != e.deprecationHandler && e.deprecationHandler(t, n), br[t] || (_(n), br[t] = !0)
    }

    function T(e) {
        return e instanceof Function || "[object Function]" === Object.prototype.toString.call(e)
    }

    function M(e) {
        var t, n;
        for (n in e)t = e[n], T(t) ? this[n] = t : this["_" + n] = t;
        this._config = e, this._ordinalParseLenient = new RegExp(this._ordinalParse.source + "|" + /\d{1,2}/.source)
    }

    function D(e, t) {
        var n, i = l({}, e);
        for (n in t)u(t, n) && (r(e[n]) && r(t[n]) ? (i[n] = {}, l(i[n], e[n]), l(i[n], t[n])) : null != t[n] ? i[n] = t[n] : delete i[n]);
        for (n in e)u(e, n) && !u(t, n) && r(e[n]) && (i[n] = l({}, i[n]));
        return i
    }

    function C(e) {
        null != e && this.set(e)
    }

    function E(e, t, n) {
        var r = this._calendar[e] || this._calendar.sameElse;
        return T(r) ? r.call(t, n) : r
    }

    function A(e) {
        var t = this._longDateFormat[e], n = this._longDateFormat[e.toUpperCase()];
        return t || !n ? t : (this._longDateFormat[e] = n.replace(/MMMM|MM|DD|dddd/g, function (e) {
            return e.slice(1)
        }), this._longDateFormat[e])
    }

    function O() {
        return this._invalidDate
    }

    function N(e) {
        return this._ordinal.replace("%d", e)
    }

    function j(e, t, n, r) {
        var i = this._relativeTime[n];
        return T(i) ? i(e, t, n, r) : i.replace(/%d/i, e)
    }

    function Y(e, t) {
        var n = this._relativeTime[e > 0 ? "future" : "past"];
        return T(n) ? n(t) : n.replace(/%s/i, t)
    }

    function L(e, t) {
        var n = e.toLowerCase();
        Ar[n] = Ar[n + "s"] = Ar[t] = e
    }

    function H(e) {
        return "string" == typeof e ? Ar[e] || Ar[e.toLowerCase()] : void 0
    }

    function P(e) {
        var t, n, r = {};
        for (n in e)u(e, n) && (t = H(n), t && (r[t] = e[n]));
        return r
    }

    function F(e, t) {
        Or[e] = t
    }

    function R(e) {
        var t = [];
        for (var n in e)t.push({"unit": n, "priority": Or[n]});
        return t.sort(function (e, t) {
            return e.priority - t.priority
        }), t
    }

    function W(t, n) {
        return function (r) {
            return null != r ? (q(this, t, r), e.updateOffset(this, n), this) : I(this, t)
        }
    }

    function I(e, t) {
        return e.isValid() ? e._d["get" + (e._isUTC ? "UTC" : "") + t]() : NaN
    }

    function q(e, t, n) {
        e.isValid() && e._d["set" + (e._isUTC ? "UTC" : "") + t](n)
    }

    function B(e) {
        return e = H(e), T(this[e]) ? this[e]() : this
    }

    function $(e, t) {
        if ("object" == typeof e) {
            e = P(e);
            for (var n = R(e), r = 0; r < n.length; r++)this[n[r].unit](e[n[r].unit])
        } else if (e = H(e), T(this[e]))return this[e](t);
        return this
    }

    function U(e, t, n) {
        var r = "" + Math.abs(e), i = t - r.length, o = e >= 0;
        return (o ? n ? "+" : "" : "-") + Math.pow(10, Math.max(0, i)).toString().substr(1) + r
    }

    function z(e, t, n, r) {
        var i = r;
        "string" == typeof r && (i = function () {
            return this[r]()
        }), e && (Lr[e] = i), t && (Lr[t[0]] = function () {
            return U(i.apply(this, arguments), t[1], t[2])
        }), n && (Lr[n] = function () {
            return this.localeData().ordinal(i.apply(this, arguments), e)
        })
    }

    function G(e) {
        return e.match(/\[[\s\S]/) ? e.replace(/^\[|\]$/g, "") : e.replace(/\\/g, "")
    }

    function V(e) {
        var t, n, r = e.match(Nr);
        for (t = 0, n = r.length; t < n; t++)Lr[r[t]] ? r[t] = Lr[r[t]] : r[t] = G(r[t]);
        return function (t) {
            var i, o = "";
            for (i = 0; i < n; i++)o += r[i] instanceof Function ? r[i].call(t, e) : r[i];
            return o
        }
    }

    function Q(e, t) {
        return e.isValid() ? (t = X(t, e.localeData()), Yr[t] = Yr[t] || V(t), Yr[t](e)) : e.localeData().invalidDate()
    }

    function X(e, t) {
        function n(e) {
            return t.longDateFormat(e) || e
        }

        var r = 5;
        for (jr.lastIndex = 0; r >= 0 && jr.test(e);)e = e.replace(jr, n), jr.lastIndex = 0, r -= 1;
        return e
    }

    function Z(e, t, n) {
        Kr[e] = T(t) ? t : function (e, r) {
            return e && n ? n : t
        }
    }

    function J(e, t) {
        return u(Kr, e) ? Kr[e](t._strict, t._locale) : new RegExp(K(e))
    }

    function K(e) {
        return ee(e.replace("\\", "").replace(/\\(\[)|\\(\])|\[([^\]\[]*)\]|\\(.)/g, function (e, t, n, r, i) {
            return t || n || r || i
        }))
    }

    function ee(e) {
        return e.replace(/[-\/\\^$*+?.()|[\]{}]/g, "\\$&")
    }

    function te(e, t) {
        var n, r = t;
        for ("string" == typeof e && (e = [e]), o(t) && (r = function (e, n) {
            n[t] = b(e)
        }), n = 0; n < e.length; n++)ei[e[n]] = r
    }

    function ne(e, t) {
        te(e, function (e, n, r, i) {
            r._w = r._w || {}, t(e, r._w, r, i)
        })
    }

    function re(e, t, n) {
        null != t && u(ei, e) && ei[e](t, n._a, n, e)
    }

    function ie(e, t) {
        return new Date(Date.UTC(e, t + 1, 0)).getUTCDate()
    }

    function oe(e, t) {
        return e ? n(this._months) ? this._months[e.month()] : this._months[(this._months.isFormat || fi).test(t) ? "format" : "standalone"][e.month()] : this._months
    }

    function ae(e, t) {
        return e ? n(this._monthsShort) ? this._monthsShort[e.month()] : this._monthsShort[fi.test(t) ? "format" : "standalone"][e.month()] : this._monthsShort
    }

    function se(e, t, n) {
        var r, i, o, a = e.toLocaleLowerCase();
        if (!this._monthsParse)for (this._monthsParse = [], this._longMonthsParse = [], this._shortMonthsParse = [], r = 0; r < 12; ++r)o = c([2e3, r]), this._shortMonthsParse[r] = this.monthsShort(o, "").toLocaleLowerCase(), this._longMonthsParse[r] = this.months(o, "").toLocaleLowerCase();
        return n ? "MMM" === t ? (i = ci.call(this._shortMonthsParse, a), i !== -1 ? i : null) : (i = ci.call(this._longMonthsParse, a), i !== -1 ? i : null) : "MMM" === t ? (i = ci.call(this._shortMonthsParse, a), i !== -1 ? i : (i = ci.call(this._longMonthsParse, a), i !== -1 ? i : null)) : (i = ci.call(this._longMonthsParse, a), i !== -1 ? i : (i = ci.call(this._shortMonthsParse, a), i !== -1 ? i : null))
    }

    function ue(e, t, n) {
        var r, i, o;
        if (this._monthsParseExact)return se.call(this, e, t, n);
        for (this._monthsParse || (this._monthsParse = [], this._longMonthsParse = [], this._shortMonthsParse = []), r = 0; r < 12; r++) {
            if (i = c([2e3, r]), n && !this._longMonthsParse[r] && (this._longMonthsParse[r] = new RegExp("^" + this.months(i, "").replace(".", "") + "$", "i"), this._shortMonthsParse[r] = new RegExp("^" + this.monthsShort(i, "").replace(".", "") + "$", "i")), n || this._monthsParse[r] || (o = "^" + this.months(i, "") + "|^" + this.monthsShort(i, ""), this._monthsParse[r] = new RegExp(o.replace(".", ""), "i")), n && "MMMM" === t && this._longMonthsParse[r].test(e))return r;
            if (n && "MMM" === t && this._shortMonthsParse[r].test(e))return r;
            if (!n && this._monthsParse[r].test(e))return r
        }
    }

    function le(e, t) {
        var n;
        if (!e.isValid())return e;
        if ("string" == typeof t)if (/^\d+$/.test(t))t = b(t); else if (t = e.localeData().monthsParse(t), !o(t))return e;
        return n = Math.min(e.date(), ie(e.year(), t)), e._d["set" + (e._isUTC ? "UTC" : "") + "Month"](t, n), e
    }

    function ce(t) {
        return null != t ? (le(this, t), e.updateOffset(this, !0), this) : I(this, "Month")
    }

    function fe() {
        return ie(this.year(), this.month())
    }

    function de(e) {
        return this._monthsParseExact ? (u(this, "_monthsRegex") || pe.call(this), e ? this._monthsShortStrictRegex : this._monthsShortRegex) : (u(this, "_monthsShortRegex") || (this._monthsShortRegex = pi), this._monthsShortStrictRegex && e ? this._monthsShortStrictRegex : this._monthsShortRegex)
    }

    function he(e) {
        return this._monthsParseExact ? (u(this, "_monthsRegex") || pe.call(this), e ? this._monthsStrictRegex : this._monthsRegex) : (u(this, "_monthsRegex") || (this._monthsRegex = mi), this._monthsStrictRegex && e ? this._monthsStrictRegex : this._monthsRegex)
    }

    function pe() {
        function e(e, t) {
            return t.length - e.length
        }

        var t, n, r = [], i = [], o = [];
        for (t = 0; t < 12; t++)n = c([2e3, t]), r.push(this.monthsShort(n, "")), i.push(this.months(n, "")), o.push(this.months(n, "")), o.push(this.monthsShort(n, ""));
        for (r.sort(e), i.sort(e), o.sort(e), t = 0; t < 12; t++)r[t] = ee(r[t]), i[t] = ee(i[t]);
        for (t = 0; t < 24; t++)o[t] = ee(o[t]);
        this._monthsRegex = new RegExp("^(" + o.join("|") + ")", "i"), this._monthsShortRegex = this._monthsRegex, this._monthsStrictRegex = new RegExp("^(" + i.join("|") + ")", "i"), this._monthsShortStrictRegex = new RegExp("^(" + r.join("|") + ")", "i")
    }

    function me(e) {
        return ge(e) ? 366 : 365
    }

    function ge(e) {
        return e % 4 === 0 && e % 100 !== 0 || e % 400 === 0
    }

    function ye() {
        return ge(this.year())
    }

    function ve(e, t, n, r, i, o, a) {
        var s = new Date(e, t, n, r, i, o, a);
        return e < 100 && e >= 0 && isFinite(s.getFullYear()) && s.setFullYear(e), s
    }

    function we(e) {
        var t = new Date(Date.UTC.apply(null, arguments));
        return e < 100 && e >= 0 && isFinite(t.getUTCFullYear()) && t.setUTCFullYear(e), t
    }

    function be(e, t, n) {
        var r = 7 + t - n, i = (7 + we(e, 0, r).getUTCDay() - t) % 7;
        return -i + r - 1
    }

    function xe(e, t, n, r, i) {
        var o, a, s = (7 + n - r) % 7, u = be(e, r, i), l = 1 + 7 * (t - 1) + s + u;
        return l <= 0 ? (o = e - 1, a = me(o) + l) : l > me(e) ? (o = e + 1, a = l - me(e)) : (o = e, a = l), {"year": o, "dayOfYear": a}
    }

    function _e(e, t, n) {
        var r, i, o = be(e.year(), t, n), a = Math.floor((e.dayOfYear() - o - 1) / 7) + 1;
        return a < 1 ? (i = e.year() - 1, r = a + ke(i, t, n)) : a > ke(e.year(), t, n) ? (r = a - ke(e.year(), t, n), i = e.year() + 1) : (i = e.year(), r = a), {
            "week": r,
            "year": i
        }
    }

    function ke(e, t, n) {
        var r = be(e, t, n), i = be(e + 1, t, n);
        return (me(e) - r + i) / 7
    }

    function Se(e) {
        return _e(e, this._week.dow, this._week.doy).week
    }

    function Te() {
        return this._week.dow
    }

    function Me() {
        return this._week.doy
    }

    function De(e) {
        var t = this.localeData().week(this);
        return null == e ? t : this.add(7 * (e - t), "d")
    }

    function Ce(e) {
        var t = _e(this, 1, 4).week;
        return null == e ? t : this.add(7 * (e - t), "d")
    }

    function Ee(e, t) {
        return "string" != typeof e ? e : isNaN(e) ? (e = t.weekdaysParse(e), "number" == typeof e ? e : null) : parseInt(e, 10)
    }

    function Ae(e, t) {
        return "string" == typeof e ? t.weekdaysParse(e) % 7 || 7 : isNaN(e) ? null : e
    }

    function Oe(e, t) {
        return e ? n(this._weekdays) ? this._weekdays[e.day()] : this._weekdays[this._weekdays.isFormat.test(t) ? "format" : "standalone"][e.day()] : this._weekdays
    }

    function Ne(e) {
        return e ? this._weekdaysShort[e.day()] : this._weekdaysShort
    }

    function je(e) {
        return e ? this._weekdaysMin[e.day()] : this._weekdaysMin
    }

    function Ye(e, t, n) {
        var r, i, o, a = e.toLocaleLowerCase();
        if (!this._weekdaysParse)for (this._weekdaysParse = [], this._shortWeekdaysParse = [], this._minWeekdaysParse = [], r = 0; r < 7; ++r)o = c([2e3, 1]).day(r), this._minWeekdaysParse[r] = this.weekdaysMin(o, "").toLocaleLowerCase(), this._shortWeekdaysParse[r] = this.weekdaysShort(o, "").toLocaleLowerCase(), this._weekdaysParse[r] = this.weekdays(o, "").toLocaleLowerCase();
        return n ? "dddd" === t ? (i = ci.call(this._weekdaysParse, a), i !== -1 ? i : null) : "ddd" === t ? (i = ci.call(this._shortWeekdaysParse, a), i !== -1 ? i : null) : (i = ci.call(this._minWeekdaysParse, a), i !== -1 ? i : null) : "dddd" === t ? (i = ci.call(this._weekdaysParse, a), i !== -1 ? i : (i = ci.call(this._shortWeekdaysParse, a),
            i !== -1 ? i : (i = ci.call(this._minWeekdaysParse, a), i !== -1 ? i : null))) : "ddd" === t ? (i = ci.call(this._shortWeekdaysParse, a), i !== -1 ? i : (i = ci.call(this._weekdaysParse, a), i !== -1 ? i : (i = ci.call(this._minWeekdaysParse, a), i !== -1 ? i : null))) : (i = ci.call(this._minWeekdaysParse, a), i !== -1 ? i : (i = ci.call(this._weekdaysParse, a), i !== -1 ? i : (i = ci.call(this._shortWeekdaysParse, a), i !== -1 ? i : null)))
    }

    function Le(e, t, n) {
        var r, i, o;
        if (this._weekdaysParseExact)return Ye.call(this, e, t, n);
        for (this._weekdaysParse || (this._weekdaysParse = [], this._minWeekdaysParse = [], this._shortWeekdaysParse = [], this._fullWeekdaysParse = []), r = 0; r < 7; r++) {
            if (i = c([2e3, 1]).day(r), n && !this._fullWeekdaysParse[r] && (this._fullWeekdaysParse[r] = new RegExp("^" + this.weekdays(i, "").replace(".", ".?") + "$", "i"), this._shortWeekdaysParse[r] = new RegExp("^" + this.weekdaysShort(i, "").replace(".", ".?") + "$", "i"), this._minWeekdaysParse[r] = new RegExp("^" + this.weekdaysMin(i, "").replace(".", ".?") + "$", "i")), this._weekdaysParse[r] || (o = "^" + this.weekdays(i, "") + "|^" + this.weekdaysShort(i, "") + "|^" + this.weekdaysMin(i, ""), this._weekdaysParse[r] = new RegExp(o.replace(".", ""), "i")), n && "dddd" === t && this._fullWeekdaysParse[r].test(e))return r;
            if (n && "ddd" === t && this._shortWeekdaysParse[r].test(e))return r;
            if (n && "dd" === t && this._minWeekdaysParse[r].test(e))return r;
            if (!n && this._weekdaysParse[r].test(e))return r
        }
    }

    function He(e) {
        if (!this.isValid())return null != e ? this : NaN;
        var t = this._isUTC ? this._d.getUTCDay() : this._d.getDay();
        return null != e ? (e = Ee(e, this.localeData()), this.add(e - t, "d")) : t
    }

    function Pe(e) {
        if (!this.isValid())return null != e ? this : NaN;
        var t = (this.day() + 7 - this.localeData()._week.dow) % 7;
        return null == e ? t : this.add(e - t, "d")
    }

    function Fe(e) {
        if (!this.isValid())return null != e ? this : NaN;
        if (null != e) {
            var t = Ae(e, this.localeData());
            return this.day(this.day() % 7 ? t : t - 7)
        }
        return this.day() || 7
    }

    function Re(e) {
        return this._weekdaysParseExact ? (u(this, "_weekdaysRegex") || qe.call(this), e ? this._weekdaysStrictRegex : this._weekdaysRegex) : (u(this, "_weekdaysRegex") || (this._weekdaysRegex = xi), this._weekdaysStrictRegex && e ? this._weekdaysStrictRegex : this._weekdaysRegex)
    }

    function We(e) {
        return this._weekdaysParseExact ? (u(this, "_weekdaysRegex") || qe.call(this), e ? this._weekdaysShortStrictRegex : this._weekdaysShortRegex) : (u(this, "_weekdaysShortRegex") || (this._weekdaysShortRegex = _i), this._weekdaysShortStrictRegex && e ? this._weekdaysShortStrictRegex : this._weekdaysShortRegex)
    }

    function Ie(e) {
        return this._weekdaysParseExact ? (u(this, "_weekdaysRegex") || qe.call(this), e ? this._weekdaysMinStrictRegex : this._weekdaysMinRegex) : (u(this, "_weekdaysMinRegex") || (this._weekdaysMinRegex = ki), this._weekdaysMinStrictRegex && e ? this._weekdaysMinStrictRegex : this._weekdaysMinRegex)
    }

    function qe() {
        function e(e, t) {
            return t.length - e.length
        }

        var t, n, r, i, o, a = [], s = [], u = [], l = [];
        for (t = 0; t < 7; t++)n = c([2e3, 1]).day(t), r = this.weekdaysMin(n, ""), i = this.weekdaysShort(n, ""), o = this.weekdays(n, ""), a.push(r), s.push(i), u.push(o), l.push(r), l.push(i), l.push(o);
        for (a.sort(e), s.sort(e), u.sort(e), l.sort(e), t = 0; t < 7; t++)s[t] = ee(s[t]), u[t] = ee(u[t]), l[t] = ee(l[t]);
        this._weekdaysRegex = new RegExp("^(" + l.join("|") + ")", "i"), this._weekdaysShortRegex = this._weekdaysRegex, this._weekdaysMinRegex = this._weekdaysRegex, this._weekdaysStrictRegex = new RegExp("^(" + u.join("|") + ")", "i"), this._weekdaysShortStrictRegex = new RegExp("^(" + s.join("|") + ")", "i"), this._weekdaysMinStrictRegex = new RegExp("^(" + a.join("|") + ")", "i")
    }

    function Be() {
        return this.hours() % 12 || 12
    }

    function $e() {
        return this.hours() || 24
    }

    function Ue(e, t) {
        z(e, 0, 0, function () {
            return this.localeData().meridiem(this.hours(), this.minutes(), t)
        })
    }

    function ze(e, t) {
        return t._meridiemParse
    }

    function Ge(e) {
        return "p" === (e + "").toLowerCase().charAt(0)
    }

    function Ve(e, t, n) {
        return e > 11 ? n ? "pm" : "PM" : n ? "am" : "AM"
    }

    function Qe(e) {
        return e ? e.toLowerCase().replace("_", "-") : e
    }

    function Xe(e) {
        for (var t, n, r, i, o = 0; o < e.length;) {
            for (i = Qe(e[o]).split("-"), t = i.length, n = Qe(e[o + 1]), n = n ? n.split("-") : null; t > 0;) {
                if (r = Ze(i.slice(0, t).join("-")))return r;
                if (n && n.length >= t && x(i, n, !0) >= t - 1)break;
                t--
            }
            o++
        }
        return null
    }

    function Ze(e) {
        var t = null;
        if (!Ci[e] && "undefined" != typeof module && module && module.exports)try {
            t = Si._abbr, require("./locale/" + e), Je(t)
        } catch (n) {
        }
        return Ci[e]
    }

    function Je(e, t) {
        var n;
        return e && (n = m(t) ? tt(e) : Ke(e, t), n && (Si = n)), Si._abbr
    }

    function Ke(e, t) {
        if (null !== t) {
            var n = Di;
            if (t.abbr = e, null != Ci[e])S("defineLocaleOverride", "use moment.updateLocale(localeName, config) to change an existing locale. moment.defineLocale(localeName, config) should only be used for creating a new locale See http://momentjs.com/guides/#/warnings/define-locale/ for more info."), n = Ci[e]._config; else if (null != t.parentLocale) {
                if (null == Ci[t.parentLocale])return Ei[t.parentLocale] || (Ei[t.parentLocale] = []), Ei[t.parentLocale].push({
                    "name": e,
                    "config": t
                }), null;
                n = Ci[t.parentLocale]._config
            }
            return Ci[e] = new C(D(n, t)), Ei[e] && Ei[e].forEach(function (e) {
                Ke(e.name, e.config)
            }), Je(e), Ci[e]
        }
        return delete Ci[e], null
    }

    function et(e, t) {
        if (null != t) {
            var n, r = Di;
            null != Ci[e] && (r = Ci[e]._config), t = D(r, t), n = new C(t), n.parentLocale = Ci[e], Ci[e] = n, Je(e)
        } else null != Ci[e] && (null != Ci[e].parentLocale ? Ci[e] = Ci[e].parentLocale : null != Ci[e] && delete Ci[e]);
        return Ci[e]
    }

    function tt(e) {
        var t;
        if (e && e._locale && e._locale._abbr && (e = e._locale._abbr), !e)return Si;
        if (!n(e)) {
            if (t = Ze(e))return t;
            e = [e]
        }
        return Xe(e)
    }

    function nt() {
        return kr(Ci)
    }

    function rt(e) {
        var t, n = e._a;
        return n && d(e).overflow === -2 && (t = n[ni] < 0 || n[ni] > 11 ? ni : n[ri] < 1 || n[ri] > ie(n[ti], n[ni]) ? ri : n[ii] < 0 || n[ii] > 24 || 24 === n[ii] && (0 !== n[oi] || 0 !== n[ai] || 0 !== n[si]) ? ii : n[oi] < 0 || n[oi] > 59 ? oi : n[ai] < 0 || n[ai] > 59 ? ai : n[si] < 0 || n[si] > 999 ? si : -1, d(e)._overflowDayOfYear && (t < ti || t > ri) && (t = ri), d(e)._overflowWeeks && t === -1 && (t = ui), d(e)._overflowWeekday && t === -1 && (t = li), d(e).overflow = t), e
    }

    function it(e) {
        var t, n, r, i, o, a, s = e._i, u = Ai.exec(s) || Oi.exec(s);
        if (u) {
            for (d(e).iso = !0, t = 0, n = ji.length; t < n; t++)if (ji[t][1].exec(u[1])) {
                i = ji[t][0], r = ji[t][2] !== !1;
                break
            }
            if (null == i)return void(e._isValid = !1);
            if (u[3]) {
                for (t = 0, n = Yi.length; t < n; t++)if (Yi[t][1].exec(u[3])) {
                    o = (u[2] || " ") + Yi[t][0];
                    break
                }
                if (null == o)return void(e._isValid = !1)
            }
            if (!r && null != o)return void(e._isValid = !1);
            if (u[4]) {
                if (!Ni.exec(u[4]))return void(e._isValid = !1);
                a = "Z"
            }
            e._f = i + (o || "") + (a || ""), ct(e)
        } else e._isValid = !1
    }

    function ot(t) {
        var n = Li.exec(t._i);
        return null !== n ? void(t._d = new Date((+n[1]))) : (it(t), void(t._isValid === !1 && (delete t._isValid, e.createFromInputFallback(t))))
    }

    function at(e, t, n) {
        return null != e ? e : null != t ? t : n
    }

    function st(t) {
        var n = new Date(e.now());
        return t._useUTC ? [n.getUTCFullYear(), n.getUTCMonth(), n.getUTCDate()] : [n.getFullYear(), n.getMonth(), n.getDate()]
    }

    function ut(e) {
        var t, n, r, i, o = [];
        if (!e._d) {
            for (r = st(e), e._w && null == e._a[ri] && null == e._a[ni] && lt(e), e._dayOfYear && (i = at(e._a[ti], r[ti]), e._dayOfYear > me(i) && (d(e)._overflowDayOfYear = !0), n = we(i, 0, e._dayOfYear), e._a[ni] = n.getUTCMonth(), e._a[ri] = n.getUTCDate()), t = 0; t < 3 && null == e._a[t]; ++t)e._a[t] = o[t] = r[t];
            for (; t < 7; t++)e._a[t] = o[t] = null == e._a[t] ? 2 === t ? 1 : 0 : e._a[t];
            24 === e._a[ii] && 0 === e._a[oi] && 0 === e._a[ai] && 0 === e._a[si] && (e._nextDay = !0, e._a[ii] = 0), e._d = (e._useUTC ? we : ve).apply(null, o), null != e._tzm && e._d.setUTCMinutes(e._d.getUTCMinutes() - e._tzm), e._nextDay && (e._a[ii] = 24)
        }
    }

    function lt(e) {
        var t, n, r, i, o, a, s, u;
        if (t = e._w, null != t.GG || null != t.W || null != t.E)o = 1, a = 4, n = at(t.GG, e._a[ti], _e(vt(), 1, 4).year), r = at(t.W, 1), i = at(t.E, 1), (i < 1 || i > 7) && (u = !0); else {
            o = e._locale._week.dow, a = e._locale._week.doy;
            var l = _e(vt(), o, a);
            n = at(t.gg, e._a[ti], l.year), r = at(t.w, l.week), null != t.d ? (i = t.d, (i < 0 || i > 6) && (u = !0)) : null != t.e ? (i = t.e + o, (t.e < 0 || t.e > 6) && (u = !0)) : i = o
        }
        r < 1 || r > ke(n, o, a) ? d(e)._overflowWeeks = !0 : null != u ? d(e)._overflowWeekday = !0 : (s = xe(n, r, i, o, a), e._a[ti] = s.year, e._dayOfYear = s.dayOfYear)
    }

    function ct(t) {
        if (t._f === e.ISO_8601)return void it(t);
        t._a = [], d(t).empty = !0;
        var n, r, i, o, a, s = "" + t._i, u = s.length, l = 0;
        for (i = X(t._f, t._locale).match(Nr) || [], n = 0; n < i.length; n++)o = i[n], r = (s.match(J(o, t)) || [])[0], r && (a = s.substr(0, s.indexOf(r)), a.length > 0 && d(t).unusedInput.push(a), s = s.slice(s.indexOf(r) + r.length), l += r.length), Lr[o] ? (r ? d(t).empty = !1 : d(t).unusedTokens.push(o), re(o, r, t)) : t._strict && !r && d(t).unusedTokens.push(o);
        d(t).charsLeftOver = u - l, s.length > 0 && d(t).unusedInput.push(s), t._a[ii] <= 12 && d(t).bigHour === !0 && t._a[ii] > 0 && (d(t).bigHour = void 0), d(t).parsedDateParts = t._a.slice(0), d(t).meridiem = t._meridiem, t._a[ii] = ft(t._locale, t._a[ii], t._meridiem), ut(t), rt(t)
    }

    function ft(e, t, n) {
        var r;
        return null == n ? t : null != e.meridiemHour ? e.meridiemHour(t, n) : null != e.isPM ? (r = e.isPM(n), r && t < 12 && (t += 12), r || 12 !== t || (t = 0), t) : t
    }

    function dt(e) {
        var t, n, r, i, o;
        if (0 === e._f.length)return d(e).invalidFormat = !0, void(e._d = new Date(NaN));
        for (i = 0; i < e._f.length; i++)o = 0, t = g({}, e), null != e._useUTC && (t._useUTC = e._useUTC), t._f = e._f[i], ct(t), h(t) && (o += d(t).charsLeftOver, o += 10 * d(t).unusedTokens.length, d(t).score = o, (null == r || o < r) && (r = o, n = t));
        l(e, n || t)
    }

    function ht(e) {
        if (!e._d) {
            var t = P(e._i);
            e._a = s([t.year, t.month, t.day || t.date, t.hour, t.minute, t.second, t.millisecond], function (e) {
                return e && parseInt(e, 10)
            }), ut(e)
        }
    }

    function pt(e) {
        var t = new y(rt(mt(e)));
        return t._nextDay && (t.add(1, "d"), t._nextDay = void 0), t
    }

    function mt(e) {
        var t = e._i, r = e._f;
        return e._locale = e._locale || tt(e._l), null === t || void 0 === r && "" === t ? p({"nullInput": !0}) : ("string" == typeof t && (e._i = t = e._locale.preparse(t)), v(t) ? new y(rt(t)) : (a(t) ? e._d = t : n(r) ? dt(e) : r ? ct(e) : gt(e), h(e) || (e._d = null), e))
    }

    function gt(t) {
        var r = t._i;
        void 0 === r ? t._d = new Date(e.now()) : a(r) ? t._d = new Date(r.valueOf()) : "string" == typeof r ? ot(t) : n(r) ? (t._a = s(r.slice(0), function (e) {
            return parseInt(e, 10)
        }), ut(t)) : "object" == typeof r ? ht(t) : o(r) ? t._d = new Date(r) : e.createFromInputFallback(t)
    }

    function yt(e, t, o, a, s) {
        var u = {};
        return o !== !0 && o !== !1 || (a = o, o = void 0), (r(e) && i(e) || n(e) && 0 === e.length) && (e = void 0), u._isAMomentObject = !0, u._useUTC = u._isUTC = s, u._l = o, u._i = e, u._f = t, u._strict = a, pt(u)
    }

    function vt(e, t, n, r) {
        return yt(e, t, n, r, !1)
    }

    function wt(e, t) {
        var r, i;
        if (1 === t.length && n(t[0]) && (t = t[0]), !t.length)return vt();
        for (r = t[0], i = 1; i < t.length; ++i)t[i].isValid() && !t[i][e](r) || (r = t[i]);
        return r
    }

    function bt() {
        var e = [].slice.call(arguments, 0);
        return wt("isBefore", e)
    }

    function xt() {
        var e = [].slice.call(arguments, 0);
        return wt("isAfter", e)
    }

    function _t(e) {
        var t = P(e), n = t.year || 0, r = t.quarter || 0, i = t.month || 0, o = t.week || 0, a = t.day || 0, s = t.hour || 0, u = t.minute || 0, l = t.second || 0, c = t.millisecond || 0;
        this._milliseconds = +c + 1e3 * l + 6e4 * u + 1e3 * s * 60 * 60, this._days = +a + 7 * o, this._months = +i + 3 * r + 12 * n, this._data = {}, this._locale = tt(), this._bubble()
    }

    function kt(e) {
        return e instanceof _t
    }

    function St(e) {
        return e < 0 ? Math.round(-1 * e) * -1 : Math.round(e)
    }

    function Tt(e, t) {
        z(e, 0, 0, function () {
            var e = this.utcOffset(), n = "+";
            return e < 0 && (e = -e, n = "-"), n + U(~~(e / 60), 2) + t + U(~~e % 60, 2)
        })
    }

    function Mt(e, t) {
        var n = (t || "").match(e);
        if (null === n)return null;
        var r = n[n.length - 1] || [], i = (r + "").match(Ri) || ["-", 0, 0], o = +(60 * i[1]) + b(i[2]);
        return 0 === o ? 0 : "+" === i[0] ? o : -o
    }

    function Dt(t, n) {
        var r, i;
        return n._isUTC ? (r = n.clone(), i = (v(t) || a(t) ? t.valueOf() : vt(t).valueOf()) - r.valueOf(), r._d.setTime(r._d.valueOf() + i), e.updateOffset(r, !1), r) : vt(t).local()
    }

    function Ct(e) {
        return 15 * -Math.round(e._d.getTimezoneOffset() / 15)
    }

    function Et(t, n) {
        var r, i = this._offset || 0;
        if (!this.isValid())return null != t ? this : NaN;
        if (null != t) {
            if ("string" == typeof t) {
                if (t = Mt(Xr, t), null === t)return this
            } else Math.abs(t) < 16 && (t = 60 * t);
            return !this._isUTC && n && (r = Ct(this)), this._offset = t, this._isUTC = !0, null != r && this.add(r, "m"), i !== t && (!n || this._changeInProgress ? Ut(this, Wt(t - i, "m"), 1, !1) : this._changeInProgress || (this._changeInProgress = !0, e.updateOffset(this, !0), this._changeInProgress = null)), this
        }
        return this._isUTC ? i : Ct(this)
    }

    function At(e, t) {
        return null != e ? ("string" != typeof e && (e = -e), this.utcOffset(e, t), this) : -this.utcOffset()
    }

    function Ot(e) {
        return this.utcOffset(0, e)
    }

    function Nt(e) {
        return this._isUTC && (this.utcOffset(0, e), this._isUTC = !1, e && this.subtract(Ct(this), "m")), this
    }

    function jt() {
        if (null != this._tzm)this.utcOffset(this._tzm); else if ("string" == typeof this._i) {
            var e = Mt(Qr, this._i);
            null != e ? this.utcOffset(e) : this.utcOffset(0, !0)
        }
        return this
    }

    function Yt(e) {
        return !!this.isValid() && (e = e ? vt(e).utcOffset() : 0, (this.utcOffset() - e) % 60 === 0)
    }

    function Lt() {
        return this.utcOffset() > this.clone().month(0).utcOffset() || this.utcOffset() > this.clone().month(5).utcOffset()
    }

    function Ht() {
        if (!m(this._isDSTShifted))return this._isDSTShifted;
        var e = {};
        if (g(e, this), e = mt(e), e._a) {
            var t = e._isUTC ? c(e._a) : vt(e._a);
            this._isDSTShifted = this.isValid() && x(e._a, t.toArray()) > 0
        } else this._isDSTShifted = !1;
        return this._isDSTShifted
    }

    function Pt() {
        return !!this.isValid() && !this._isUTC
    }

    function Ft() {
        return !!this.isValid() && this._isUTC
    }

    function Rt() {
        return !!this.isValid() && (this._isUTC && 0 === this._offset)
    }

    function Wt(e, t) {
        var n, r, i, a = e, s = null;
        return kt(e) ? a = {
            "ms": e._milliseconds,
            "d": e._days,
            "M": e._months
        } : o(e) ? (a = {}, t ? a[t] = e : a.milliseconds = e) : (s = Wi.exec(e)) ? (n = "-" === s[1] ? -1 : 1, a = {
            "y": 0,
            "d": b(s[ri]) * n,
            "h": b(s[ii]) * n,
            "m": b(s[oi]) * n,
            "s": b(s[ai]) * n,
            "ms": b(St(1e3 * s[si])) * n
        }) : (s = Ii.exec(e)) ? (n = "-" === s[1] ? -1 : 1, a = {
            "y": It(s[2], n),
            "M": It(s[3], n),
            "w": It(s[4], n),
            "d": It(s[5], n),
            "h": It(s[6], n),
            "m": It(s[7], n),
            "s": It(s[8], n)
        }) : null == a ? a = {} : "object" == typeof a && ("from" in a || "to" in a) && (i = Bt(vt(a.from), vt(a.to)), a = {}, a.ms = i.milliseconds, a.M = i.months), r = new _t(a), kt(e) && u(e, "_locale") && (r._locale = e._locale), r
    }

    function It(e, t) {
        var n = e && parseFloat(e.replace(",", "."));
        return (isNaN(n) ? 0 : n) * t
    }

    function qt(e, t) {
        var n = {"milliseconds": 0, "months": 0};
        return n.months = t.month() - e.month() + 12 * (t.year() - e.year()), e.clone().add(n.months, "M").isAfter(t) && --n.months, n.milliseconds = +t - +e.clone().add(n.months, "M"), n
    }

    function Bt(e, t) {
        var n;
        return e.isValid() && t.isValid() ? (t = Dt(t, e), e.isBefore(t) ? n = qt(e, t) : (n = qt(t, e), n.milliseconds = -n.milliseconds, n.months = -n.months), n) : {
            "milliseconds": 0,
            "months": 0
        }
    }

    function $t(e, t) {
        return function (n, r) {
            var i, o;
            return null === r || isNaN(+r) || (S(t, "moment()." + t + "(period, number) is deprecated. Please use moment()." + t + "(number, period). See http://momentjs.com/guides/#/warnings/add-inverted-param/ for more info."), o = n, n = r, r = o), n = "string" == typeof n ? +n : n, i = Wt(n, r), Ut(this, i, e), this
        }
    }

    function Ut(t, n, r, i) {
        var o = n._milliseconds, a = St(n._days), s = St(n._months);
        t.isValid() && (i = null == i || i, o && t._d.setTime(t._d.valueOf() + o * r), a && q(t, "Date", I(t, "Date") + a * r), s && le(t, I(t, "Month") + s * r), i && e.updateOffset(t, a || s))
    }

    function zt(e, t) {
        var n = e.diff(t, "days", !0);
        return n < -6 ? "sameElse" : n < -1 ? "lastWeek" : n < 0 ? "lastDay" : n < 1 ? "sameDay" : n < 2 ? "nextDay" : n < 7 ? "nextWeek" : "sameElse"
    }

    function Gt(t, n) {
        var r = t || vt(), i = Dt(r, this).startOf("day"), o = e.calendarFormat(this, i) || "sameElse", a = n && (T(n[o]) ? n[o].call(this, r) : n[o]);
        return this.format(a || this.localeData().calendar(o, this, vt(r)))
    }

    function Vt() {
        return new y(this)
    }

    function Qt(e, t) {
        var n = v(e) ? e : vt(e);
        return !(!this.isValid() || !n.isValid()) && (t = H(m(t) ? "millisecond" : t), "millisecond" === t ? this.valueOf() > n.valueOf() : n.valueOf() < this.clone().startOf(t).valueOf())
    }

    function Xt(e, t) {
        var n = v(e) ? e : vt(e);
        return !(!this.isValid() || !n.isValid()) && (t = H(m(t) ? "millisecond" : t), "millisecond" === t ? this.valueOf() < n.valueOf() : this.clone().endOf(t).valueOf() < n.valueOf())
    }

    function Zt(e, t, n, r) {
        return r = r || "()", ("(" === r[0] ? this.isAfter(e, n) : !this.isBefore(e, n)) && (")" === r[1] ? this.isBefore(t, n) : !this.isAfter(t, n))
    }

    function Jt(e, t) {
        var n, r = v(e) ? e : vt(e);
        return !(!this.isValid() || !r.isValid()) && (t = H(t || "millisecond"), "millisecond" === t ? this.valueOf() === r.valueOf() : (n = r.valueOf(), this.clone().startOf(t).valueOf() <= n && n <= this.clone().endOf(t).valueOf()))
    }

    function Kt(e, t) {
        return this.isSame(e, t) || this.isAfter(e, t)
    }

    function en(e, t) {
        return this.isSame(e, t) || this.isBefore(e, t)
    }

    function tn(e, t, n) {
        var r, i, o, a;
        return this.isValid() ? (r = Dt(e, this), r.isValid() ? (i = 6e4 * (r.utcOffset() - this.utcOffset()), t = H(t), "year" === t || "month" === t || "quarter" === t ? (a = nn(this, r), "quarter" === t ? a /= 3 : "year" === t && (a /= 12)) : (o = this - r, a = "second" === t ? o / 1e3 : "minute" === t ? o / 6e4 : "hour" === t ? o / 36e5 : "day" === t ? (o - i) / 864e5 : "week" === t ? (o - i) / 6048e5 : o), n ? a : w(a)) : NaN) : NaN
    }

    function nn(e, t) {
        var n, r, i = 12 * (t.year() - e.year()) + (t.month() - e.month()), o = e.clone().add(i, "months");
        return t - o < 0 ? (n = e.clone().add(i - 1, "months"), r = (t - o) / (o - n)) : (n = e.clone().add(i + 1, "months"), r = (t - o) / (n - o)), -(i + r) || 0
    }

    function rn() {
        return this.clone().locale("en").format("ddd MMM DD YYYY HH:mm:ss [GMT]ZZ")
    }

    function on() {
        var e = this.clone().utc();
        return 0 < e.year() && e.year() <= 9999 ? T(Date.prototype.toISOString) ? this.toDate().toISOString() : Q(e, "YYYY-MM-DD[T]HH:mm:ss.SSS[Z]") : Q(e, "YYYYYY-MM-DD[T]HH:mm:ss.SSS[Z]")
    }

    function an() {
        if (!this.isValid())return "moment.invalid(/* " + this._i + " */)";
        var e = "moment", t = "";
        this.isLocal() || (e = 0 === this.utcOffset() ? "moment.utc" : "moment.parseZone", t = "Z");
        var n = "[" + e + '("]', r = 0 < this.year() && this.year() <= 9999 ? "YYYY" : "YYYYYY", i = "-MM-DD[T]HH:mm:ss.SSS", o = t + '[")]';
        return this.format(n + r + i + o)
    }

    function sn(t) {
        t || (t = this.isUtc() ? e.defaultFormatUtc : e.defaultFormat);
        var n = Q(this, t);
        return this.localeData().postformat(n)
    }

    function un(e, t) {
        return this.isValid() && (v(e) && e.isValid() || vt(e).isValid()) ? Wt({
            "to": this,
            "from": e
        }).locale(this.locale()).humanize(!t) : this.localeData().invalidDate()
    }

    function ln(e) {
        return this.from(vt(), e)
    }

    function cn(e, t) {
        return this.isValid() && (v(e) && e.isValid() || vt(e).isValid()) ? Wt({
            "from": this,
            "to": e
        }).locale(this.locale()).humanize(!t) : this.localeData().invalidDate()
    }

    function fn(e) {
        return this.to(vt(), e)
    }

    function dn(e) {
        var t;
        return void 0 === e ? this._locale._abbr : (t = tt(e), null != t && (this._locale = t), this)
    }

    function hn() {
        return this._locale
    }

    function pn(e) {
        switch (e = H(e)) {
            case"year":
                this.month(0);
            case"quarter":
            case"month":
                this.date(1);
            case"week":
            case"isoWeek":
            case"day":
            case"date":
                this.hours(0);
            case"hour":
                this.minutes(0);
            case"minute":
                this.seconds(0);
            case"second":
                this.milliseconds(0)
        }
        return "week" === e && this.weekday(0), "isoWeek" === e && this.isoWeekday(1), "quarter" === e && this.month(3 * Math.floor(this.month() / 3)), this
    }

    function mn(e) {
        return e = H(e), void 0 === e || "millisecond" === e ? this : ("date" === e && (e = "day"), this.startOf(e).add(1, "isoWeek" === e ? "week" : e).subtract(1, "ms"))
    }

    function gn() {
        return this._d.valueOf() - 6e4 * (this._offset || 0)
    }

    function yn() {
        return Math.floor(this.valueOf() / 1e3)
    }

    function vn() {
        return new Date(this.valueOf())
    }

    function wn() {
        var e = this;
        return [e.year(), e.month(), e.date(), e.hour(), e.minute(), e.second(), e.millisecond()]
    }

    function bn() {
        var e = this;
        return {
            "years": e.year(),
            "months": e.month(),
            "date": e.date(),
            "hours": e.hours(),
            "minutes": e.minutes(),
            "seconds": e.seconds(),
            "milliseconds": e.milliseconds()
        }
    }

    function xn() {
        return this.isValid() ? this.toISOString() : null
    }

    function _n() {
        return h(this)
    }

    function kn() {
        return l({}, d(this))
    }

    function Sn() {
        return d(this).overflow
    }

    function Tn() {
        return {"input": this._i, "format": this._f, "locale": this._locale, "isUTC": this._isUTC, "strict": this._strict}
    }

    function Mn(e, t) {
        z(0, [e, e.length], 0, t)
    }

    function Dn(e) {
        return On.call(this, e, this.week(), this.weekday(), this.localeData()._week.dow, this.localeData()._week.doy)
    }

    function Cn(e) {
        return On.call(this, e, this.isoWeek(), this.isoWeekday(), 1, 4)
    }

    function En() {
        return ke(this.year(), 1, 4)
    }

    function An() {
        var e = this.localeData()._week;
        return ke(this.year(), e.dow, e.doy)
    }

    function On(e, t, n, r, i) {
        var o;
        return null == e ? _e(this, r, i).year : (o = ke(e, r, i), t > o && (t = o), Nn.call(this, e, t, n, r, i))
    }

    function Nn(e, t, n, r, i) {
        var o = xe(e, t, n, r, i), a = we(o.year, 0, o.dayOfYear);
        return this.year(a.getUTCFullYear()), this.month(a.getUTCMonth()), this.date(a.getUTCDate()), this
    }

    function jn(e) {
        return null == e ? Math.ceil((this.month() + 1) / 3) : this.month(3 * (e - 1) + this.month() % 3)
    }

    function Yn(e) {
        var t = Math.round((this.clone().startOf("day") - this.clone().startOf("year")) / 864e5) + 1;
        return null == e ? t : this.add(e - t, "d")
    }

    function Ln(e, t) {
        t[si] = b(1e3 * ("0." + e))
    }

    function Hn() {
        return this._isUTC ? "UTC" : ""
    }

    function Pn() {
        return this._isUTC ? "Coordinated Universal Time" : ""
    }

    function Fn(e) {
        return vt(1e3 * e)
    }

    function Rn() {
        return vt.apply(null, arguments).parseZone()
    }

    function Wn(e) {
        return e
    }

    function In(e, t, n, r) {
        var i = tt(), o = c().set(r, t);
        return i[n](o, e)
    }

    function qn(e, t, n) {
        if (o(e) && (t = e, e = void 0), e = e || "", null != t)return In(e, t, n, "month");
        var r, i = [];
        for (r = 0; r < 12; r++)i[r] = In(e, r, n, "month");
        return i
    }

    function Bn(e, t, n, r) {
        "boolean" == typeof e ? (o(t) && (n = t, t = void 0), t = t || "") : (t = e, n = t, e = !1, o(t) && (n = t, t = void 0), t = t || "");
        var i = tt(), a = e ? i._week.dow : 0;
        if (null != n)return In(t, (n + a) % 7, r, "day");
        var s, u = [];
        for (s = 0; s < 7; s++)u[s] = In(t, (s + a) % 7, r, "day");
        return u
    }

    function $n(e, t) {
        return qn(e, t, "months")
    }

    function Un(e, t) {
        return qn(e, t, "monthsShort")
    }

    function zn(e, t, n) {
        return Bn(e, t, n, "weekdays")
    }

    function Gn(e, t, n) {
        return Bn(e, t, n, "weekdaysShort")
    }

    function Vn(e, t, n) {
        return Bn(e, t, n, "weekdaysMin")
    }

    function Qn() {
        var e = this._data;
        return this._milliseconds = Ji(this._milliseconds), this._days = Ji(this._days), this._months = Ji(this._months), e.milliseconds = Ji(e.milliseconds), e.seconds = Ji(e.seconds), e.minutes = Ji(e.minutes), e.hours = Ji(e.hours), e.months = Ji(e.months), e.years = Ji(e.years), this
    }

    function Xn(e, t, n, r) {
        var i = Wt(t, n);
        return e._milliseconds += r * i._milliseconds, e._days += r * i._days, e._months += r * i._months, e._bubble()
    }

    function Zn(e, t) {
        return Xn(this, e, t, 1)
    }

    function Jn(e, t) {
        return Xn(this, e, t, -1)
    }

    function Kn(e) {
        return e < 0 ? Math.floor(e) : Math.ceil(e)
    }

    function er() {
        var e, t, n, r, i, o = this._milliseconds, a = this._days, s = this._months, u = this._data;
        return o >= 0 && a >= 0 && s >= 0 || o <= 0 && a <= 0 && s <= 0 || (o += 864e5 * Kn(nr(s) + a), a = 0, s = 0), u.milliseconds = o % 1e3, e = w(o / 1e3), u.seconds = e % 60, t = w(e / 60), u.minutes = t % 60, n = w(t / 60), u.hours = n % 24, a += w(n / 24), i = w(tr(a)), s += i, a -= Kn(nr(i)), r = w(s / 12), s %= 12, u.days = a, u.months = s, u.years = r, this
    }

    function tr(e) {
        return 4800 * e / 146097
    }

    function nr(e) {
        return 146097 * e / 4800
    }

    function rr(e) {
        var t, n, r = this._milliseconds;
        if (e = H(e), "month" === e || "year" === e)return t = this._days + r / 864e5, n = this._months + tr(t), "month" === e ? n : n / 12;
        switch (t = this._days + Math.round(nr(this._months)), e) {
            case"week":
                return t / 7 + r / 6048e5;
            case"day":
                return t + r / 864e5;
            case"hour":
                return 24 * t + r / 36e5;
            case"minute":
                return 1440 * t + r / 6e4;
            case"second":
                return 86400 * t + r / 1e3;
            case"millisecond":
                return Math.floor(864e5 * t) + r;
            default:
                throw new Error("Unknown unit " + e)
        }
    }

    function ir() {
        return this._milliseconds + 864e5 * this._days + this._months % 12 * 2592e6 + 31536e6 * b(this._months / 12)
    }

    function or(e) {
        return function () {
            return this.as(e)
        }
    }

    function ar(e) {
        return e = H(e), this[e + "s"]()
    }

    function sr(e) {
        return function () {
            return this._data[e]
        }
    }

    function ur() {
        return w(this.days() / 7)
    }

    function lr(e, t, n, r, i) {
        return i.relativeTime(t || 1, !!n, e, r)
    }

    function cr(e, t, n) {
        var r = Wt(e).abs(), i = mo(r.as("s")), o = mo(r.as("m")), a = mo(r.as("h")), s = mo(r.as("d")), u = mo(r.as("M")), l = mo(r.as("y")), c = i < go.s && ["s", i] || o <= 1 && ["m"] || o < go.m && ["mm", o] || a <= 1 && ["h"] || a < go.h && ["hh", a] || s <= 1 && ["d"] || s < go.d && ["dd", s] || u <= 1 && ["M"] || u < go.M && ["MM", u] || l <= 1 && ["y"] || ["yy", l];
        return c[2] = t, c[3] = +e > 0, c[4] = n, lr.apply(null, c)
    }

    function fr(e) {
        return void 0 === e ? mo : "function" == typeof e && (mo = e, !0)
    }

    function dr(e, t) {
        return void 0 !== go[e] && (void 0 === t ? go[e] : (go[e] = t, !0))
    }

    function hr(e) {
        var t = this.localeData(), n = cr(this, !e, t);
        return e && (n = t.pastFuture(+this, n)), t.postformat(n)
    }

    function pr() {
        var e, t, n, r = yo(this._milliseconds) / 1e3, i = yo(this._days), o = yo(this._months);
        e = w(r / 60), t = w(e / 60), r %= 60, e %= 60, n = w(o / 12), o %= 12;
        var a = n, s = o, u = i, l = t, c = e, f = r, d = this.asSeconds();
        return d ? (d < 0 ? "-" : "") + "P" + (a ? a + "Y" : "") + (s ? s + "M" : "") + (u ? u + "D" : "") + (l || c || f ? "T" : "") + (l ? l + "H" : "") + (c ? c + "M" : "") + (f ? f + "S" : "") : "P0D"
    }

    var mr, gr;
    gr = Array.prototype.some ? Array.prototype.some : function (e) {
        for (var t = Object(this), n = t.length >>> 0, r = 0; r < n; r++)if (r in t && e.call(this, t[r], r, t))return !0;
        return !1
    };
    var yr = gr, vr = e.momentProperties = [], wr = !1, br = {};
    e.suppressDeprecationWarnings = !1, e.deprecationHandler = null;
    var xr;
    xr = Object.keys ? Object.keys : function (e) {
        var t, n = [];
        for (t in e)u(e, t) && n.push(t);
        return n
    };
    var _r, kr = xr, Sr = {
        "sameDay": "[Today at] LT",
        "nextDay": "[Tomorrow at] LT",
        "nextWeek": "dddd [at] LT",
        "lastDay": "[Yesterday at] LT",
        "lastWeek": "[Last] dddd [at] LT",
        "sameElse": "L"
    }, Tr = {
        "LTS": "h:mm:ss A",
        "LT": "h:mm A",
        "L": "MM/DD/YYYY",
        "LL": "MMMM D, YYYY",
        "LLL": "MMMM D, YYYY h:mm A",
        "LLLL": "dddd, MMMM D, YYYY h:mm A"
    }, Mr = "Invalid date", Dr = "%d", Cr = /\d{1,2}/, Er = {
        "future": "in %s",
        "past": "%s ago",
        "s": "a few seconds",
        "m": "a minute",
        "mm": "%d minutes",
        "h": "an hour",
        "hh": "%d hours",
        "d": "a day",
        "dd": "%d days",
        "M": "a month",
        "MM": "%d months",
        "y": "a year",
        "yy": "%d years"
    }, Ar = {}, Or = {}, Nr = /(\[[^\[]*\])|(\\)?([Hh]mm(ss)?|Mo|MM?M?M?|Do|DDDo|DD?D?D?|ddd?d?|do?|w[o|w]?|W[o|W]?|Qo?|YYYYYY|YYYYY|YYYY|YY|gg(ggg?)?|GG(GGG?)?|e|E|a|A|hh?|HH?|kk?|mm?|ss?|S{1,9}|x|X|zz?|ZZ?|.)/g, jr = /(\[[^\[]*\])|(\\)?(LTS|LT|LL?L?L?|l{1,4})/g, Yr = {}, Lr = {}, Hr = /\d/, Pr = /\d\d/, Fr = /\d{3}/, Rr = /\d{4}/, Wr = /[+-]?\d{6}/, Ir = /\d\d?/, qr = /\d\d\d\d?/, Br = /\d\d\d\d\d\d?/, $r = /\d{1,3}/, Ur = /\d{1,4}/, zr = /[+-]?\d{1,6}/, Gr = /\d+/, Vr = /[+-]?\d+/, Qr = /Z|[+-]\d\d:?\d\d/gi, Xr = /Z|[+-]\d\d(?::?\d\d)?/gi, Zr = /[+-]?\d+(\.\d{1,3})?/, Jr = /[0-9]*['a-z\u00A0-\u05FF\u0700-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+|[\u0600-\u06FF\/]+(\s*?[\u0600-\u06FF]+){1,2}/i, Kr = {}, ei = {}, ti = 0, ni = 1, ri = 2, ii = 3, oi = 4, ai = 5, si = 6, ui = 7, li = 8;
    _r = Array.prototype.indexOf ? Array.prototype.indexOf : function (e) {
        var t;
        for (t = 0; t < this.length; ++t)if (this[t] === e)return t;
        return -1
    };
    var ci = _r;
    z("M", ["MM", 2], "Mo", function () {
        return this.month() + 1
    }), z("MMM", 0, 0, function (e) {
        return this.localeData().monthsShort(this, e)
    }), z("MMMM", 0, 0, function (e) {
        return this.localeData().months(this, e)
    }), L("month", "M"), F("month", 8), Z("M", Ir), Z("MM", Ir, Pr), Z("MMM", function (e, t) {
        return t.monthsShortRegex(e)
    }), Z("MMMM", function (e, t) {
        return t.monthsRegex(e)
    }), te(["M", "MM"], function (e, t) {
        t[ni] = b(e) - 1
    }), te(["MMM", "MMMM"], function (e, t, n, r) {
        var i = n._locale.monthsParse(e, r, n._strict);
        null != i ? t[ni] = i : d(n).invalidMonth = e
    });
    var fi = /D[oD]?(\[[^\[\]]*\]|\s)+MMMM?/, di = "January_February_March_April_May_June_July_August_September_October_November_December".split("_"), hi = "Jan_Feb_Mar_Apr_May_Jun_Jul_Aug_Sep_Oct_Nov_Dec".split("_"), pi = Jr, mi = Jr;
    z("Y", 0, 0, function () {
        var e = this.year();
        return e <= 9999 ? "" + e : "+" + e
    }), z(0, ["YY", 2], 0, function () {
        return this.year() % 100
    }), z(0, ["YYYY", 4], 0, "year"), z(0, ["YYYYY", 5], 0, "year"), z(0, ["YYYYYY", 6, !0], 0, "year"), L("year", "y"), F("year", 1), Z("Y", Vr), Z("YY", Ir, Pr), Z("YYYY", Ur, Rr), Z("YYYYY", zr, Wr), Z("YYYYYY", zr, Wr), te(["YYYYY", "YYYYYY"], ti), te("YYYY", function (t, n) {
        n[ti] = 2 === t.length ? e.parseTwoDigitYear(t) : b(t)
    }), te("YY", function (t, n) {
        n[ti] = e.parseTwoDigitYear(t)
    }), te("Y", function (e, t) {
        t[ti] = parseInt(e, 10)
    }), e.parseTwoDigitYear = function (e) {
        return b(e) + (b(e) > 68 ? 1900 : 2e3)
    };
    var gi = W("FullYear", !0);
    z("w", ["ww", 2], "wo", "week"), z("W", ["WW", 2], "Wo", "isoWeek"), L("week", "w"), L("isoWeek", "W"), F("week", 5), F("isoWeek", 5), Z("w", Ir), Z("ww", Ir, Pr), Z("W", Ir), Z("WW", Ir, Pr), ne(["w", "ww", "W", "WW"], function (e, t, n, r) {
        t[r.substr(0, 1)] = b(e)
    });
    var yi = {"dow": 0, "doy": 6};
    z("d", 0, "do", "day"), z("dd", 0, 0, function (e) {
        return this.localeData().weekdaysMin(this, e)
    }), z("ddd", 0, 0, function (e) {
        return this.localeData().weekdaysShort(this, e)
    }), z("dddd", 0, 0, function (e) {
        return this.localeData().weekdays(this, e)
    }), z("e", 0, 0, "weekday"), z("E", 0, 0, "isoWeekday"), L("day", "d"), L("weekday", "e"), L("isoWeekday", "E"), F("day", 11), F("weekday", 11), F("isoWeekday", 11), Z("d", Ir), Z("e", Ir), Z("E", Ir), Z("dd", function (e, t) {
        return t.weekdaysMinRegex(e)
    }), Z("ddd", function (e, t) {
        return t.weekdaysShortRegex(e)
    }), Z("dddd", function (e, t) {
        return t.weekdaysRegex(e)
    }), ne(["dd", "ddd", "dddd"], function (e, t, n, r) {
        var i = n._locale.weekdaysParse(e, r, n._strict);
        null != i ? t.d = i : d(n).invalidWeekday = e
    }), ne(["d", "e", "E"], function (e, t, n, r) {
        t[r] = b(e)
    });
    var vi = "Sunday_Monday_Tuesday_Wednesday_Thursday_Friday_Saturday".split("_"), wi = "Sun_Mon_Tue_Wed_Thu_Fri_Sat".split("_"), bi = "Su_Mo_Tu_We_Th_Fr_Sa".split("_"), xi = Jr, _i = Jr, ki = Jr;
    z("H", ["HH", 2], 0, "hour"), z("h", ["hh", 2], 0, Be), z("k", ["kk", 2], 0, $e), z("hmm", 0, 0, function () {
        return "" + Be.apply(this) + U(this.minutes(), 2)
    }), z("hmmss", 0, 0, function () {
        return "" + Be.apply(this) + U(this.minutes(), 2) + U(this.seconds(), 2)
    }), z("Hmm", 0, 0, function () {
        return "" + this.hours() + U(this.minutes(), 2)
    }), z("Hmmss", 0, 0, function () {
        return "" + this.hours() + U(this.minutes(), 2) + U(this.seconds(), 2)
    }), Ue("a", !0), Ue("A", !1), L("hour", "h"), F("hour", 13), Z("a", ze), Z("A", ze), Z("H", Ir), Z("h", Ir), Z("HH", Ir, Pr), Z("hh", Ir, Pr), Z("hmm", qr), Z("hmmss", Br), Z("Hmm", qr), Z("Hmmss", Br), te(["H", "HH"], ii), te(["a", "A"], function (e, t, n) {
        n._isPm = n._locale.isPM(e), n._meridiem = e
    }), te(["h", "hh"], function (e, t, n) {
        t[ii] = b(e), d(n).bigHour = !0
    }), te("hmm", function (e, t, n) {
        var r = e.length - 2;
        t[ii] = b(e.substr(0, r)), t[oi] = b(e.substr(r)), d(n).bigHour = !0
    }), te("hmmss", function (e, t, n) {
        var r = e.length - 4, i = e.length - 2;
        t[ii] = b(e.substr(0, r)), t[oi] = b(e.substr(r, 2)), t[ai] = b(e.substr(i)), d(n).bigHour = !0
    }), te("Hmm", function (e, t, n) {
        var r = e.length - 2;
        t[ii] = b(e.substr(0, r)), t[oi] = b(e.substr(r))
    }), te("Hmmss", function (e, t, n) {
        var r = e.length - 4, i = e.length - 2;
        t[ii] = b(e.substr(0, r)), t[oi] = b(e.substr(r, 2)), t[ai] = b(e.substr(i))
    });
    var Si, Ti = /[ap]\.?m?\.?/i, Mi = W("Hours", !0), Di = {
        "calendar": Sr,
        "longDateFormat": Tr,
        "invalidDate": Mr,
        "ordinal": Dr,
        "ordinalParse": Cr,
        "relativeTime": Er,
        "months": di,
        "monthsShort": hi,
        "week": yi,
        "weekdays": vi,
        "weekdaysMin": bi,
        "weekdaysShort": wi,
        "meridiemParse": Ti
    }, Ci = {}, Ei = {}, Ai = /^\s*((?:[+-]\d{6}|\d{4})-(?:\d\d-\d\d|W\d\d-\d|W\d\d|\d\d\d|\d\d))(?:(T| )(\d\d(?::\d\d(?::\d\d(?:[.,]\d+)?)?)?)([\+\-]\d\d(?::?\d\d)?|\s*Z)?)?$/, Oi = /^\s*((?:[+-]\d{6}|\d{4})(?:\d\d\d\d|W\d\d\d|W\d\d|\d\d\d|\d\d))(?:(T| )(\d\d(?:\d\d(?:\d\d(?:[.,]\d+)?)?)?)([\+\-]\d\d(?::?\d\d)?|\s*Z)?)?$/, Ni = /Z|[+-]\d\d(?::?\d\d)?/, ji = [["YYYYYY-MM-DD", /[+-]\d{6}-\d\d-\d\d/], ["YYYY-MM-DD", /\d{4}-\d\d-\d\d/], ["GGGG-[W]WW-E", /\d{4}-W\d\d-\d/], ["GGGG-[W]WW", /\d{4}-W\d\d/, !1], ["YYYY-DDD", /\d{4}-\d{3}/], ["YYYY-MM", /\d{4}-\d\d/, !1], ["YYYYYYMMDD", /[+-]\d{10}/], ["YYYYMMDD", /\d{8}/], ["GGGG[W]WWE", /\d{4}W\d{3}/], ["GGGG[W]WW", /\d{4}W\d{2}/, !1], ["YYYYDDD", /\d{7}/]], Yi = [["HH:mm:ss.SSSS", /\d\d:\d\d:\d\d\.\d+/], ["HH:mm:ss,SSSS", /\d\d:\d\d:\d\d,\d+/], ["HH:mm:ss", /\d\d:\d\d:\d\d/], ["HH:mm", /\d\d:\d\d/], ["HHmmss.SSSS", /\d\d\d\d\d\d\.\d+/], ["HHmmss,SSSS", /\d\d\d\d\d\d,\d+/], ["HHmmss", /\d\d\d\d\d\d/], ["HHmm", /\d\d\d\d/], ["HH", /\d\d/]], Li = /^\/?Date\((\-?\d+)/i;
    e.createFromInputFallback = k("value provided is not in a recognized ISO format. moment construction falls back to js Date(), which is not reliable across all browsers and versions. Non ISO date formats are discouraged and will be removed in an upcoming major release. Please refer to http://momentjs.com/guides/#/warnings/js-date/ for more info.", function (e) {
        e._d = new Date(e._i + (e._useUTC ? " UTC" : ""))
    }), e.ISO_8601 = function () {
    };
    var Hi = k("moment().min is deprecated, use moment.max instead. http://momentjs.com/guides/#/warnings/min-max/", function () {
        var e = vt.apply(null, arguments);
        return this.isValid() && e.isValid() ? e < this ? this : e : p()
    }), Pi = k("moment().max is deprecated, use moment.min instead. http://momentjs.com/guides/#/warnings/min-max/", function () {
        var e = vt.apply(null, arguments);
        return this.isValid() && e.isValid() ? e > this ? this : e : p()
    }), Fi = function () {
        return Date.now ? Date.now() : +new Date
    };
    Tt("Z", ":"), Tt("ZZ", ""), Z("Z", Xr), Z("ZZ", Xr), te(["Z", "ZZ"], function (e, t, n) {
        n._useUTC = !0, n._tzm = Mt(Xr, e)
    });
    var Ri = /([\+\-]|\d\d)/gi;
    e.updateOffset = function () {
    };
    var Wi = /^(\-)?(?:(\d*)[. ])?(\d+)\:(\d+)(?:\:(\d+)(\.\d*)?)?$/, Ii = /^(-)?P(?:(-?[0-9,.]*)Y)?(?:(-?[0-9,.]*)M)?(?:(-?[0-9,.]*)W)?(?:(-?[0-9,.]*)D)?(?:T(?:(-?[0-9,.]*)H)?(?:(-?[0-9,.]*)M)?(?:(-?[0-9,.]*)S)?)?$/;
    Wt.fn = _t.prototype;
    var qi = $t(1, "add"), Bi = $t(-1, "subtract");
    e.defaultFormat = "YYYY-MM-DDTHH:mm:ssZ", e.defaultFormatUtc = "YYYY-MM-DDTHH:mm:ss[Z]";
    var $i = k("moment().lang() is deprecated. Instead, use moment().localeData() to get the language configuration. Use moment().locale() to change languages.", function (e) {
        return void 0 === e ? this.localeData() : this.locale(e)
    });
    z(0, ["gg", 2], 0, function () {
        return this.weekYear() % 100
    }), z(0, ["GG", 2], 0, function () {
        return this.isoWeekYear() % 100
    }), Mn("gggg", "weekYear"), Mn("ggggg", "weekYear"), Mn("GGGG", "isoWeekYear"), Mn("GGGGG", "isoWeekYear"), L("weekYear", "gg"), L("isoWeekYear", "GG"), F("weekYear", 1), F("isoWeekYear", 1), Z("G", Vr), Z("g", Vr), Z("GG", Ir, Pr), Z("gg", Ir, Pr), Z("GGGG", Ur, Rr), Z("gggg", Ur, Rr), Z("GGGGG", zr, Wr), Z("ggggg", zr, Wr), ne(["gggg", "ggggg", "GGGG", "GGGGG"], function (e, t, n, r) {
        t[r.substr(0, 2)] = b(e)
    }), ne(["gg", "GG"], function (t, n, r, i) {
        n[i] = e.parseTwoDigitYear(t)
    }), z("Q", 0, "Qo", "quarter"), L("quarter", "Q"), F("quarter", 7), Z("Q", Hr), te("Q", function (e, t) {
        t[ni] = 3 * (b(e) - 1)
    }), z("D", ["DD", 2], "Do", "date"), L("date", "D"), F("date", 9), Z("D", Ir), Z("DD", Ir, Pr), Z("Do", function (e, t) {
        return e ? t._ordinalParse : t._ordinalParseLenient
    }), te(["D", "DD"], ri), te("Do", function (e, t) {
        t[ri] = b(e.match(Ir)[0], 10)
    });
    var Ui = W("Date", !0);
    z("DDD", ["DDDD", 3], "DDDo", "dayOfYear"),
        L("dayOfYear", "DDD"), F("dayOfYear", 4), Z("DDD", $r), Z("DDDD", Fr), te(["DDD", "DDDD"], function (e, t, n) {
        n._dayOfYear = b(e)
    }), z("m", ["mm", 2], 0, "minute"), L("minute", "m"), F("minute", 14), Z("m", Ir), Z("mm", Ir, Pr), te(["m", "mm"], oi);
    var zi = W("Minutes", !1);
    z("s", ["ss", 2], 0, "second"), L("second", "s"), F("second", 15), Z("s", Ir), Z("ss", Ir, Pr), te(["s", "ss"], ai);
    var Gi = W("Seconds", !1);
    z("S", 0, 0, function () {
        return ~~(this.millisecond() / 100)
    }), z(0, ["SS", 2], 0, function () {
        return ~~(this.millisecond() / 10)
    }), z(0, ["SSS", 3], 0, "millisecond"), z(0, ["SSSS", 4], 0, function () {
        return 10 * this.millisecond()
    }), z(0, ["SSSSS", 5], 0, function () {
        return 100 * this.millisecond()
    }), z(0, ["SSSSSS", 6], 0, function () {
        return 1e3 * this.millisecond()
    }), z(0, ["SSSSSSS", 7], 0, function () {
        return 1e4 * this.millisecond()
    }), z(0, ["SSSSSSSS", 8], 0, function () {
        return 1e5 * this.millisecond()
    }), z(0, ["SSSSSSSSS", 9], 0, function () {
        return 1e6 * this.millisecond()
    }), L("millisecond", "ms"), F("millisecond", 16), Z("S", $r, Hr), Z("SS", $r, Pr), Z("SSS", $r, Fr);
    var Vi;
    for (Vi = "SSSS"; Vi.length <= 9; Vi += "S")Z(Vi, Gr);
    for (Vi = "S"; Vi.length <= 9; Vi += "S")te(Vi, Ln);
    var Qi = W("Milliseconds", !1);
    z("z", 0, 0, "zoneAbbr"), z("zz", 0, 0, "zoneName");
    var Xi = y.prototype;
    Xi.add = qi, Xi.calendar = Gt, Xi.clone = Vt, Xi.diff = tn, Xi.endOf = mn, Xi.format = sn, Xi.from = un, Xi.fromNow = ln, Xi.to = cn, Xi.toNow = fn, Xi.get = B, Xi.invalidAt = Sn, Xi.isAfter = Qt, Xi.isBefore = Xt, Xi.isBetween = Zt, Xi.isSame = Jt, Xi.isSameOrAfter = Kt, Xi.isSameOrBefore = en, Xi.isValid = _n, Xi.lang = $i, Xi.locale = dn, Xi.localeData = hn, Xi.max = Pi, Xi.min = Hi, Xi.parsingFlags = kn, Xi.set = $, Xi.startOf = pn, Xi.subtract = Bi, Xi.toArray = wn, Xi.toObject = bn, Xi.toDate = vn, Xi.toISOString = on, Xi.inspect = an, Xi.toJSON = xn, Xi.toString = rn, Xi.unix = yn, Xi.valueOf = gn, Xi.creationData = Tn, Xi.year = gi, Xi.isLeapYear = ye, Xi.weekYear = Dn, Xi.isoWeekYear = Cn, Xi.quarter = Xi.quarters = jn, Xi.month = ce, Xi.daysInMonth = fe, Xi.week = Xi.weeks = De, Xi.isoWeek = Xi.isoWeeks = Ce, Xi.weeksInYear = An, Xi.isoWeeksInYear = En, Xi.date = Ui, Xi.day = Xi.days = He, Xi.weekday = Pe, Xi.isoWeekday = Fe, Xi.dayOfYear = Yn, Xi.hour = Xi.hours = Mi, Xi.minute = Xi.minutes = zi, Xi.second = Xi.seconds = Gi, Xi.millisecond = Xi.milliseconds = Qi, Xi.utcOffset = Et, Xi.utc = Ot, Xi.local = Nt, Xi.parseZone = jt, Xi.hasAlignedHourOffset = Yt, Xi.isDST = Lt, Xi.isLocal = Pt, Xi.isUtcOffset = Ft, Xi.isUtc = Rt, Xi.isUTC = Rt, Xi.zoneAbbr = Hn, Xi.zoneName = Pn, Xi.dates = k("dates accessor is deprecated. Use date instead.", Ui), Xi.months = k("months accessor is deprecated. Use month instead", ce), Xi.years = k("years accessor is deprecated. Use year instead", gi), Xi.zone = k("moment().zone is deprecated, use moment().utcOffset instead. http://momentjs.com/guides/#/warnings/zone/", At), Xi.isDSTShifted = k("isDSTShifted is deprecated. See http://momentjs.com/guides/#/warnings/dst-shifted/ for more information", Ht);
    var Zi = C.prototype;
    Zi.calendar = E, Zi.longDateFormat = A, Zi.invalidDate = O, Zi.ordinal = N, Zi.preparse = Wn, Zi.postformat = Wn, Zi.relativeTime = j, Zi.pastFuture = Y, Zi.set = M, Zi.months = oe, Zi.monthsShort = ae, Zi.monthsParse = ue, Zi.monthsRegex = he, Zi.monthsShortRegex = de, Zi.week = Se, Zi.firstDayOfYear = Me, Zi.firstDayOfWeek = Te, Zi.weekdays = Oe, Zi.weekdaysMin = je, Zi.weekdaysShort = Ne, Zi.weekdaysParse = Le, Zi.weekdaysRegex = Re, Zi.weekdaysShortRegex = We, Zi.weekdaysMinRegex = Ie, Zi.isPM = Ge, Zi.meridiem = Ve, Je("en", {
        "ordinalParse": /\d{1,2}(th|st|nd|rd)/,
        "ordinal": function (e) {
            var t = e % 10, n = 1 === b(e % 100 / 10) ? "th" : 1 === t ? "st" : 2 === t ? "nd" : 3 === t ? "rd" : "th";
            return e + n
        }
    }), e.lang = k("moment.lang is deprecated. Use moment.locale instead.", Je), e.langData = k("moment.langData is deprecated. Use moment.localeData instead.", tt);
    var Ji = Math.abs, Ki = or("ms"), eo = or("s"), to = or("m"), no = or("h"), ro = or("d"), io = or("w"), oo = or("M"), ao = or("y"), so = sr("milliseconds"), uo = sr("seconds"), lo = sr("minutes"), co = sr("hours"), fo = sr("days"), ho = sr("months"), po = sr("years"), mo = Math.round, go = {
        "s": 45,
        "m": 45,
        "h": 22,
        "d": 26,
        "M": 11
    }, yo = Math.abs, vo = _t.prototype;
    return vo.abs = Qn, vo.add = Zn, vo.subtract = Jn, vo.as = rr, vo.asMilliseconds = Ki, vo.asSeconds = eo, vo.asMinutes = to, vo.asHours = no, vo.asDays = ro, vo.asWeeks = io, vo.asMonths = oo, vo.asYears = ao, vo.valueOf = ir, vo._bubble = er, vo.get = ar, vo.milliseconds = so, vo.seconds = uo, vo.minutes = lo, vo.hours = co, vo.days = fo, vo.weeks = ur, vo.months = ho, vo.years = po, vo.humanize = hr, vo.toISOString = pr, vo.toString = pr, vo.toJSON = pr, vo.locale = dn, vo.localeData = hn, vo.toIsoString = k("toIsoString() is deprecated. Please use toISOString() instead (notice the capitals)", pr), vo.lang = $i, z("X", 0, 0, "unix"), z("x", 0, 0, "valueOf"), Z("x", Vr), Z("X", Zr), te("X", function (e, t, n) {
        n._d = new Date(1e3 * parseFloat(e, 10))
    }), te("x", function (e, t, n) {
        n._d = new Date(b(e))
    }), e.version = "2.17.1", t(vt), e.fn = Xi, e.min = bt, e.max = xt, e.now = Fi, e.utc = c, e.unix = Fn, e.months = $n, e.isDate = a, e.locale = Je, e.invalid = p, e.duration = Wt, e.isMoment = v, e.weekdays = zn, e.parseZone = Rn, e.localeData = tt, e.isDuration = kt, e.monthsShort = Un, e.weekdaysMin = Vn, e.defineLocale = Ke, e.updateLocale = et, e.locales = nt, e.weekdaysShort = Gn, e.normalizeUnits = H, e.relativeTimeRounding = fr, e.relativeTimeThreshold = dr, e.calendarFormat = zt, e.prototype = Xi, e
}), function (e, t) {
    "object" == typeof exports && "undefined" != typeof module && "function" == typeof require ? t(require("../moment")) : "function" == typeof define && define.amd ? define(["../moment"], t) : t(e.moment)
}(this, function (e) {
    "use strict";
    var t = e.defineLocale("zh-cn", {
        "months": "\u4e00\u6708_\u4e8c\u6708_\u4e09\u6708_\u56db\u6708_\u4e94\u6708_\u516d\u6708_\u4e03\u6708_\u516b\u6708_\u4e5d\u6708_\u5341\u6708_\u5341\u4e00\u6708_\u5341\u4e8c\u6708".split("_"),
        "monthsShort": "1\u6708_2\u6708_3\u6708_4\u6708_5\u6708_6\u6708_7\u6708_8\u6708_9\u6708_10\u6708_11\u6708_12\u6708".split("_"),
        "weekdays": "\u661f\u671f\u65e5_\u661f\u671f\u4e00_\u661f\u671f\u4e8c_\u661f\u671f\u4e09_\u661f\u671f\u56db_\u661f\u671f\u4e94_\u661f\u671f\u516d".split("_"),
        "weekdaysShort": "\u5468\u65e5_\u5468\u4e00_\u5468\u4e8c_\u5468\u4e09_\u5468\u56db_\u5468\u4e94_\u5468\u516d".split("_"),
        "weekdaysMin": "\u65e5_\u4e00_\u4e8c_\u4e09_\u56db_\u4e94_\u516d".split("_"),
        "longDateFormat": {
            "LT": "Ah\u70b9mm\u5206",
            "LTS": "Ah\u70b9m\u5206s\u79d2",
            "L": "YYYY-MM-DD",
            "LL": "YYYY\u5e74MMMD\u65e5",
            "LLL": "YYYY\u5e74MMMD\u65e5Ah\u70b9mm\u5206",
            "LLLL": "YYYY\u5e74MMMD\u65e5ddddAh\u70b9mm\u5206",
            "l": "YYYY-MM-DD",
            "ll": "YYYY\u5e74MMMD\u65e5",
            "lll": "YYYY\u5e74MMMD\u65e5Ah\u70b9mm\u5206",
            "llll": "YYYY\u5e74MMMD\u65e5ddddAh\u70b9mm\u5206"
        },
        "meridiemParse": /\u51cc\u6668|\u65e9\u4e0a|\u4e0a\u5348|\u4e2d\u5348|\u4e0b\u5348|\u665a\u4e0a/,
        "meridiemHour": function (e, t) {
            return 12 === e && (e = 0), "\u51cc\u6668" === t || "\u65e9\u4e0a" === t || "\u4e0a\u5348" === t ? e : "\u4e0b\u5348" === t || "\u665a\u4e0a" === t ? e + 12 : e >= 11 ? e : e + 12
        },
        "meridiem": function (e, t, n) {
            var r = 100 * e + t;
            return r < 600 ? "\u51cc\u6668" : r < 900 ? "\u65e9\u4e0a" : r < 1130 ? "\u4e0a\u5348" : r < 1230 ? "\u4e2d\u5348" : r < 1800 ? "\u4e0b\u5348" : "\u665a\u4e0a"
        },
        "calendar": {
            "sameDay": function () {
                return 0 === this.minutes() ? "[\u4eca\u5929]Ah[\u70b9\u6574]" : "[\u4eca\u5929]LT"
            }, "nextDay": function () {
                return 0 === this.minutes() ? "[\u660e\u5929]Ah[\u70b9\u6574]" : "[\u660e\u5929]LT"
            }, "lastDay": function () {
                return 0 === this.minutes() ? "[\u6628\u5929]Ah[\u70b9\u6574]" : "[\u6628\u5929]LT"
            }, "nextWeek": function () {
                var t, n;
                return t = e().startOf("week"), n = this.diff(t, "days") >= 7 ? "[\u4e0b]" : "[\u672c]", 0 === this.minutes() ? n + "dddAh\u70b9\u6574" : n + "dddAh\u70b9mm"
            }, "lastWeek": function () {
                var t, n;
                return t = e().startOf("week"), n = this.unix() < t.unix() ? "[\u4e0a]" : "[\u672c]", 0 === this.minutes() ? n + "dddAh\u70b9\u6574" : n + "dddAh\u70b9mm"
            }, "sameElse": "LL"
        },
        "ordinalParse": /\d{1,2}(\u65e5|\u6708|\u5468)/,
        "ordinal": function (e, t) {
            switch (t) {
                case"d":
                case"D":
                case"DDD":
                    return e + "\u65e5";
                case"M":
                    return e + "\u6708";
                case"w":
                case"W":
                    return e + "\u5468";
                default:
                    return e
            }
        },
        "relativeTime": {
            "future": "%s\u5185",
            "past": "%s\u524d",
            "s": "\u51e0\u79d2",
            "m": "1 \u5206\u949f",
            "mm": "%d \u5206\u949f",
            "h": "1 \u5c0f\u65f6",
            "hh": "%d \u5c0f\u65f6",
            "d": "1 \u5929",
            "dd": "%d \u5929",
            "M": "1 \u4e2a\u6708",
            "MM": "%d \u4e2a\u6708",
            "y": "1 \u5e74",
            "yy": "%d \u5e74"
        },
        "week": {"dow": 1, "doy": 4}
    });
    return t
}), function () {
    function e(e) {
        function t() {
        }

        var n;
        return Object.create ? n = Object.create(e) : (t.prototype = e, n = new t), n
    }

    var t = Object.prototype, n = Array.prototype, r = Function.prototype, i = String.prototype, o = t.hasOwnProperty, a = n.slice;
    r.bind || (r.bind = function (t) {
        function n() {
            if (this instanceof n) {
                var o = e(r.prototype), s = r.apply(o, i.concat(a.call(arguments)));
                return Object(s) === s ? s : o
            }
            return r.apply(t, i.concat(a.call(arguments)))
        }

        var r = this;
        if ("function" != typeof r)throw new TypeError("Bind must be called on a function");
        var i = a.call(arguments, 1);
        return n
    }), Object.keys || (Object.keys = function () {
        var e = !{"toString": ""}.propertyIsEnumerable("toString"), t = ["toString", "toLocaleString", "valueOf", "hasOwnProperty", "isPrototypeOf", "propertyIsEnumerable", "constructor"], n = t.length;
        return function (r) {
            if (r !== Object(r))throw new TypeError(r + " is not an object");
            var i = [];
            for (var a in r)o.call(r, a) && i.push(a);
            if (e)for (var s = 0; s < n; s++)o.call(r, t[s]) && i.push(t[s]);
            return i
        }
    }()), Array.isArray || (Array.isArray = function (e) {
        return "[object Array]" === t.toString.call(e)
    }), n.forEach || (n.forEach = function (e, t) {
        for (var n = 0, r = this.length >>> 0; n < r; n++)n in this && e.call(t, this[n], n, this)
    }), n.map || (n.map = function (e, t) {
        for (var n = this.length >>> 0, r = new Array(n), i = 0; i < n; i++)i in this && (r[i] = e.call(t, this[i], i, this));
        return r
    }), n.filter || (n.filter = function (e, t) {
        for (var n, r = [], i = 0, o = this.length >>> 0; i < o; i++)i in this && (n = this[i], e.call(t, n, i, this) && r.push(n));
        return r
    }), n.every || (n.every = function (e, t) {
        for (var n = 0, r = this.length >>> 0; n < r; n++)if (n in this && !e.call(t, this[n], n, this))return !1;
        return !0
    }), n.some || (n.some = function (e, t) {
        for (var n = 0, r = this.length >>> 0; n < r; n++)if (n in this && e.call(t, this[n], n, this))return !0;
        return !1
    }), n.reduce || (n.reduce = function (e) {
        if ("function" != typeof e)throw new TypeError(e + " is not an function");
        var t, n = this.length >>> 0, r = 0;
        if (arguments.length > 1)t = arguments[1]; else for (; ;) {
            if (r in this) {
                t = this[r++];
                break
            }
            if (++r >= n)throw new TypeError("reduce of empty array with on initial value")
        }
        for (; r < n; r++)r in this && (t = e.call(null, t, this[r], r, this));
        return t
    }), n.reduceRight || (n.reduceRight = function (e) {
        if ("function" != typeof e)throw new TypeError(e + " is not an function");
        var t, n = this.length >>> 0, r = n - 1;
        if (arguments.length > 1)t = arguments[1]; else for (; ;) {
            if (r in this) {
                t = this[r--];
                break
            }
            if (--r < 0)throw new TypeError("reduce of empty array with on initial value")
        }
        for (; r >= 0; r--)r in this && (t = e.call(null, t, this[r], r, this));
        return t
    }), n.indexOf || (n.indexOf = function (e, t) {
        var n = this.length >>> 0;
        for (t = Number(t) || 0, t = Math[t < 0 ? "ceil" : "floor"](t), t < 0 && (t = Math.max(t + n, 0)); t < n; t++)if (t in this && this[t] === e)return t;
        return -1
    }), n.lastIndexOf || (n.lastIndexOf = function (e, t) {
        var n = this.length >>> 0;
        for (t = Number(t) || n - 1, t = Math[t < 0 ? "ceil" : "floor"](t), t < 0 && (t += n), t = Math.min(t, n - 1); t >= 0; t--)if (t in this && this[t] === e)return t;
        return -1
    }), i.trim || (i.trim = function () {
        var e = ["\\s", "00A0", "1680", "180E", "2000-\\u200A", "200B", "2028", "2029", "202F", "205F", "3000"].join("\\u"), t = new RegExp("^[" + e + "]+"), n = new RegExp("[" + e + "]+$");
        return function () {
            return String(this).replace(t, "").replace(n, "")
        }
    }()), Date.now || (Date.now = function () {
        return +new Date
    })
}(), function (e, t) {
    "object" == typeof module && "object" == typeof module.exports ? module.exports = e.document ? t(e, !0) : function (e) {
        if (!e.document)throw new Error("jQuery requires a window with a document");
        return t(e)
    } : t(e)
}("undefined" != typeof window ? window : this, function (e, t) {
    function n(e) {
        var t = e.length, n = oe.type(e);
        return "function" !== n && !oe.isWindow(e) && (!(1 !== e.nodeType || !t) || ("array" === n || 0 === t || "number" == typeof t && t > 0 && t - 1 in e))
    }

    function r(e, t, n) {
        if (oe.isFunction(t))return oe.grep(e, function (e, r) {
            return !!t.call(e, r, e) !== n
        });
        if (t.nodeType)return oe.grep(e, function (e) {
            return e === t !== n
        });
        if ("string" == typeof t) {
            if (he.test(t))return oe.filter(t, e, n);
            t = oe.filter(t, e)
        }
        return oe.grep(e, function (e) {
            return oe.inArray(e, t) >= 0 !== n
        })
    }

    function i(e, t) {
        do e = e[t]; while (e && 1 !== e.nodeType);
        return e
    }

    function o(e) {
        var t = xe[e] = {};
        return oe.each(e.match(be) || [], function (e, n) {
            t[n] = !0
        }), t
    }

    function a() {
        me.addEventListener ? (me.removeEventListener("DOMContentLoaded", s, !1), e.removeEventListener("load", s, !1)) : (me.detachEvent("onreadystatechange", s), e.detachEvent("onload", s))
    }

    function s() {
        (me.addEventListener || "load" === event.type || "complete" === me.readyState) && (a(), oe.ready())
    }

    function u(e, t, n) {
        if (void 0 === n && 1 === e.nodeType) {
            var r = "data-" + t.replace(Me, "-$1").toLowerCase();
            if (n = e.getAttribute(r), "string" == typeof n) {
                try {
                    n = "true" === n || "false" !== n && ("null" === n ? null : +n + "" === n ? +n : Te.test(n) ? oe.parseJSON(n) : n)
                } catch (i) {
                }
                oe.data(e, t, n)
            } else n = void 0
        }
        return n
    }

    function l(e) {
        var t;
        for (t in e)if (("data" !== t || !oe.isEmptyObject(e[t])) && "toJSON" !== t)return !1;
        return !0
    }

    function c(e, t, n, r) {
        if (oe.acceptData(e)) {
            var i, o, a = oe.expando, s = e.nodeType, u = s ? oe.cache : e, l = s ? e[a] : e[a] && a;
            if (l && u[l] && (r || u[l].data) || void 0 !== n || "string" != typeof t)return l || (l = s ? e[a] = V.pop() || oe.guid++ : a), u[l] || (u[l] = s ? {} : {"toJSON": oe.noop}), "object" != typeof t && "function" != typeof t || (r ? u[l] = oe.extend(u[l], t) : u[l].data = oe.extend(u[l].data, t)), o = u[l], r || (o.data || (o.data = {}), o = o.data), void 0 !== n && (o[oe.camelCase(t)] = n), "string" == typeof t ? (i = o[t], null == i && (i = o[oe.camelCase(t)])) : i = o, i
        }
    }

    function f(e, t, n) {
        if (oe.acceptData(e)) {
            var r, i, o = e.nodeType, a = o ? oe.cache : e, s = o ? e[oe.expando] : oe.expando;
            if (a[s]) {
                if (t && (r = n ? a[s] : a[s].data)) {
                    oe.isArray(t) ? t = t.concat(oe.map(t, oe.camelCase)) : t in r ? t = [t] : (t = oe.camelCase(t), t = t in r ? [t] : t.split(" ")), i = t.length;
                    for (; i--;)delete r[t[i]];
                    if (n ? !l(r) : !oe.isEmptyObject(r))return
                }
                (n || (delete a[s].data, l(a[s]))) && (o ? oe.cleanData([e], !0) : re.deleteExpando || a != a.window ? delete a[s] : a[s] = null)
            }
        }
    }

    function d() {
        return !0
    }

    function h() {
        return !1
    }

    function p() {
        try {
            return me.activeElement
        } catch (e) {
        }
    }

    function m(e) {
        var t = Pe.split("|"), n = e.createDocumentFragment();
        if (n.createElement)for (; t.length;)n.createElement(t.pop());
        return n
    }

    function g(e, t) {
        var n, r, i = 0, o = typeof e.getElementsByTagName !== Se ? e.getElementsByTagName(t || "*") : typeof e.querySelectorAll !== Se ? e.querySelectorAll(t || "*") : void 0;
        if (!o)for (o = [], n = e.childNodes || e; null != (r = n[i]); i++)!t || oe.nodeName(r, t) ? o.push(r) : oe.merge(o, g(r, t));
        return void 0 === t || t && oe.nodeName(e, t) ? oe.merge([e], o) : o
    }

    function y(e) {
        Oe.test(e.type) && (e.defaultChecked = e.checked)
    }

    function v(e, t) {
        return oe.nodeName(e, "table") && oe.nodeName(11 !== t.nodeType ? t : t.firstChild, "tr") ? e.getElementsByTagName("tbody")[0] || e.appendChild(e.ownerDocument.createElement("tbody")) : e
    }

    function w(e) {
        return e.type = (null !== oe.find.attr(e, "type")) + "/" + e.type, e
    }

    function b(e) {
        var t = Ve.exec(e.type);
        return t ? e.type = t[1] : e.removeAttribute("type"), e
    }

    function x(e, t) {
        for (var n, r = 0; null != (n = e[r]); r++)oe._data(n, "globalEval", !t || oe._data(t[r], "globalEval"))
    }

    function _(e, t) {
        if (1 === t.nodeType && oe.hasData(e)) {
            var n, r, i, o = oe._data(e), a = oe._data(t, o), s = o.events;
            if (s) {
                delete a.handle, a.events = {};
                for (n in s)for (r = 0, i = s[n].length; r < i; r++)oe.event.add(t, n, s[n][r])
            }
            a.data && (a.data = oe.extend({}, a.data))
        }
    }

    function k(e, t) {
        var n, r, i;
        if (1 === t.nodeType) {
            if (n = t.nodeName.toLowerCase(), !re.noCloneEvent && t[oe.expando]) {
                i = oe._data(t);
                for (r in i.events)oe.removeEvent(t, r, i.handle);
                t.removeAttribute(oe.expando)
            }
            "script" === n && t.text !== e.text ? (w(t).text = e.text, b(t)) : "object" === n ? (t.parentNode && (t.outerHTML = e.outerHTML), re.html5Clone && e.innerHTML && !oe.trim(t.innerHTML) && (t.innerHTML = e.innerHTML)) : "input" === n && Oe.test(e.type) ? (t.defaultChecked = t.checked = e.checked, t.value !== e.value && (t.value = e.value)) : "option" === n ? t.defaultSelected = t.selected = e.defaultSelected : "input" !== n && "textarea" !== n || (t.defaultValue = e.defaultValue)
        }
    }

    function S(t, n) {
        var r = oe(n.createElement(t)).appendTo(n.body), i = e.getDefaultComputedStyle ? e.getDefaultComputedStyle(r[0]).display : oe.css(r[0], "display");
        return r.detach(), i
    }

    function T(e) {
        var t = me, n = et[e];
        return n || (n = S(e, t), "none" !== n && n || (Ke = (Ke || oe("<iframe frameborder='0' width='0' height='0'/>")).appendTo(t.documentElement), t = (Ke[0].contentWindow || Ke[0].contentDocument).document, t.write(), t.close(), n = S(e, t), Ke.detach()), et[e] = n), n
    }

    function M(e, t) {
        return {
            "get": function () {
                var n = e();
                if (null != n)return n ? void delete this.get : (this.get = t).apply(this, arguments)
            }
        }
    }

    function D(e, t) {
        if (t in e)return t;
        for (var n = t.charAt(0).toUpperCase() + t.slice(1), r = t, i = ht.length; i--;)if (t = ht[i] + n, t in e)return t;
        return r
    }

    function C(e, t) {
        for (var n, r, i, o = [], a = 0, s = e.length; a < s; a++)r = e[a], r.style && (o[a] = oe._data(r, "olddisplay"), n = r.style.display, t ? (o[a] || "none" !== n || (r.style.display = ""), "" === r.style.display && Ee(r) && (o[a] = oe._data(r, "olddisplay", T(r.nodeName)))) : o[a] || (i = Ee(r), (n && "none" !== n || !i) && oe._data(r, "olddisplay", i ? n : oe.css(r, "display"))));
        for (a = 0; a < s; a++)r = e[a], r.style && (t && "none" !== r.style.display && "" !== r.style.display || (r.style.display = t ? o[a] || "" : "none"));
        return e
    }

    function E(e, t, n) {
        var r = lt.exec(t);
        return r ? Math.max(0, r[1] - (n || 0)) + (r[2] || "px") : t
    }

    function A(e, t, n, r, i) {
        for (var o = n === (r ? "border" : "content") ? 4 : "width" === t ? 1 : 0, a = 0; o < 4; o += 2)"margin" === n && (a += oe.css(e, n + Ce[o], !0, i)), r ? ("content" === n && (a -= oe.css(e, "padding" + Ce[o], !0, i)), "margin" !== n && (a -= oe.css(e, "border" + Ce[o] + "Width", !0, i))) : (a += oe.css(e, "padding" + Ce[o], !0, i), "padding" !== n && (a += oe.css(e, "border" + Ce[o] + "Width", !0, i)));
        return a
    }

    function O(e, t, n) {
        var r = !0, i = "width" === t ? e.offsetWidth : e.offsetHeight, o = tt(e), a = re.boxSizing() && "border-box" === oe.css(e, "boxSizing", !1, o);
        if (i <= 0 || null == i) {
            if (i = nt(e, t, o), (i < 0 || null == i) && (i = e.style[t]), it.test(i))return i;
            r = a && (re.boxSizingReliable() || i === e.style[t]), i = parseFloat(i) || 0
        }
        return i + A(e, t, n || (a ? "border" : "content"), r, o) + "px"
    }

    function N(e, t, n, r, i) {
        return new N.prototype.init(e, t, n, r, i)
    }

    function j() {
        return setTimeout(function () {
            pt = void 0
        }), pt = oe.now()
    }

    function Y(e, t) {
        var n, r = {"height": e}, i = 0;
        for (t = t ? 1 : 0; i < 4; i += 2 - t)n = Ce[i], r["margin" + n] = r["padding" + n] = e;
        return t && (r.opacity = r.width = e), r
    }

    function L(e, t, n) {
        for (var r, i = (bt[t] || []).concat(bt["*"]), o = 0, a = i.length; o < a; o++)if (r = i[o].call(n, t, e))return r
    }

    function H(e, t, n) {
        var r, i, o, a, s, u, l, c, f = this, d = {}, h = e.style, p = e.nodeType && Ee(e), m = oe._data(e, "fxshow");
        n.queue || (s = oe._queueHooks(e, "fx"), null == s.unqueued && (s.unqueued = 0, u = s.empty.fire, s.empty.fire = function () {
            s.unqueued || u()
        }), s.unqueued++, f.always(function () {
            f.always(function () {
                s.unqueued--, oe.queue(e, "fx").length || s.empty.fire()
            })
        })), 1 === e.nodeType && ("height" in t || "width" in t) && (n.overflow = [h.overflow, h.overflowX, h.overflowY], l = oe.css(e, "display"), c = T(e.nodeName), "none" === l && (l = c), "inline" === l && "none" === oe.css(e, "float") && (re.inlineBlockNeedsLayout && "inline" !== c ? h.zoom = 1 : h.display = "inline-block")), n.overflow && (h.overflow = "hidden", re.shrinkWrapBlocks() || f.always(function () {
            h.overflow = n.overflow[0], h.overflowX = n.overflow[1], h.overflowY = n.overflow[2]
        }));
        for (r in t)if (i = t[r], gt.exec(i)) {
            if (delete t[r], o = o || "toggle" === i, i === (p ? "hide" : "show")) {
                if ("show" !== i || !m || void 0 === m[r])continue;
                p = !0
            }
            d[r] = m && m[r] || oe.style(e, r)
        }
        if (!oe.isEmptyObject(d)) {
            m ? "hidden" in m && (p = m.hidden) : m = oe._data(e, "fxshow", {}), o && (m.hidden = !p), p ? oe(e).show() : f.done(function () {
                oe(e).hide()
            }), f.done(function () {
                var t;
                oe._removeData(e, "fxshow");
                for (t in d)oe.style(e, t, d[t])
            });
            for (r in d)a = L(p ? m[r] : 0, r, f), r in m || (m[r] = a.start, p && (a.end = a.start, a.start = "width" === r || "height" === r ? 1 : 0))
        }
    }

    function P(e, t) {
        var n, r, i, o, a;
        for (n in e)if (r = oe.camelCase(n), i = t[r], o = e[n], oe.isArray(o) && (i = o[1], o = e[n] = o[0]), n !== r && (e[r] = o, delete e[n]), a = oe.cssHooks[r], a && "expand" in a) {
            o = a.expand(o), delete e[r];
            for (n in o)n in e || (e[n] = o[n], t[n] = i)
        } else t[r] = i
    }

    function F(e, t, n) {
        var r, i, o = 0, a = wt.length, s = oe.Deferred().always(function () {
            delete u.elem
        }), u = function () {
            if (i)return !1;
            for (var t = pt || j(), n = Math.max(0, l.startTime + l.duration - t), r = n / l.duration || 0, o = 1 - r, a = 0, u = l.tweens.length; a < u; a++)l.tweens[a].run(o);
            return s.notifyWith(e, [l, o, n]), o < 1 && u ? n : (s.resolveWith(e, [l]), !1)
        }, l = s.promise({
            "elem": e,
            "props": oe.extend({}, t),
            "opts": oe.extend(!0, {"specialEasing": {}}, n),
            "originalProperties": t,
            "originalOptions": n,
            "startTime": pt || j(),
            "duration": n.duration,
            "tweens": [],
            "createTween": function (t, n) {
                var r = oe.Tween(e, l.opts, t, n, l.opts.specialEasing[t] || l.opts.easing);
                return l.tweens.push(r), r
            },
            "stop": function (t) {
                var n = 0, r = t ? l.tweens.length : 0;
                if (i)return this;
                for (i = !0; n < r; n++)l.tweens[n].run(1);
                return t ? s.resolveWith(e, [l, t]) : s.rejectWith(e, [l, t]), this
            }
        }), c = l.props;
        for (P(c, l.opts.specialEasing); o < a; o++)if (r = wt[o].call(l, e, c, l.opts))return r;
        return oe.map(c, L, l), oe.isFunction(l.opts.start) && l.opts.start.call(e, l), oe.fx.timer(oe.extend(u, {
            "elem": e,
            "anim": l,
            "queue": l.opts.queue
        })), l.progress(l.opts.progress).done(l.opts.done, l.opts.complete).fail(l.opts.fail).always(l.opts.always)
    }

    function R(e) {
        return function (t, n) {
            "string" != typeof t && (n = t, t = "*");
            var r, i = 0, o = t.toLowerCase().match(be) || [];
            if (oe.isFunction(n))for (; r = o[i++];)"+" === r.charAt(0) ? (r = r.slice(1) || "*", (e[r] = e[r] || []).unshift(n)) : (e[r] = e[r] || []).push(n)
        }
    }

    function W(e, t, n, r) {
        function i(s) {
            var u;
            return o[s] = !0, oe.each(e[s] || [], function (e, s) {
                var l = s(t, n, r);
                return "string" != typeof l || a || o[l] ? a ? !(u = l) : void 0 : (t.dataTypes.unshift(l), i(l), !1)
            }), u
        }

        var o = {}, a = e === $t;
        return i(t.dataTypes[0]) || !o["*"] && i("*")
    }

    function I(e, t) {
        var n, r, i = oe.ajaxSettings.flatOptions || {};
        for (r in t)void 0 !== t[r] && ((i[r] ? e : n || (n = {}))[r] = t[r]);
        return n && oe.extend(!0, e, n), e
    }

    function q(e, t, n) {
        for (var r, i, o, a, s = e.contents, u = e.dataTypes; "*" === u[0];)u.shift(), void 0 === i && (i = e.mimeType || t.getResponseHeader("Content-Type"));
        if (i)for (a in s)if (s[a] && s[a].test(i)) {
            u.unshift(a);
            break
        }
        if (u[0] in n)o = u[0]; else {
            for (a in n) {
                if (!u[0] || e.converters[a + " " + u[0]]) {
                    o = a;
                    break
                }
                r || (r = a)
            }
            o = o || r
        }
        if (o)return o !== u[0] && u.unshift(o), n[o]
    }

    function B(e, t, n, r) {
        var i, o, a, s, u, l = {}, c = e.dataTypes.slice();
        if (c[1])for (a in e.converters)l[a.toLowerCase()] = e.converters[a];
        for (o = c.shift(); o;)if (e.responseFields[o] && (n[e.responseFields[o]] = t), !u && r && e.dataFilter && (t = e.dataFilter(t, e.dataType)), u = o, o = c.shift())if ("*" === o)o = u; else if ("*" !== u && u !== o) {
            if (a = l[u + " " + o] || l["* " + o], !a)for (i in l)if (s = i.split(" "), s[1] === o && (a = l[u + " " + s[0]] || l["* " + s[0]])) {
                a === !0 ? a = l[i] : l[i] !== !0 && (o = s[0], c.unshift(s[1]));
                break
            }
            if (a !== !0)if (a && e["throws"])t = a(t); else try {
                t = a(t)
            } catch (f) {
                return {"state": "parsererror", "error": a ? f : "No conversion from " + u + " to " + o}
            }
        }
        return {"state": "success", "data": t}
    }

    function $(e, t, n, r) {
        var i;
        if (oe.isArray(t))oe.each(t, function (t, i) {
            n || Vt.test(e) ? r(e, i) : $(e + "[" + ("object" == typeof i ? t : "") + "]", i, n, r)
        }); else if (n || "object" !== oe.type(t))r(e, t); else for (i in t)$(e + "[" + i + "]", t[i], n, r)
    }

    function U() {
        try {
            return new e.XMLHttpRequest
        } catch (t) {
        }
    }

    function z() {
        try {
            return new e.ActiveXObject("Microsoft.XMLHTTP")
        } catch (t) {
        }
    }

    function G(e) {
        return oe.isWindow(e) ? e : 9 === e.nodeType && (e.defaultView || e.parentWindow)
    }

    var V = [], Q = V.slice, X = V.concat, Z = V.push, J = V.indexOf, K = {}, ee = K.toString, te = K.hasOwnProperty, ne = "".trim, re = {}, ie = "1.11.0", oe = function (e, t) {
        return new oe.fn.init(e, t)
    }, ae = /^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, se = /^-ms-/, ue = /-([\da-z])/gi, le = function (e, t) {
        return t.toUpperCase()
    };
    oe.fn = oe.prototype = {
        "jquery": ie, "constructor": oe, "selector": "", "length": 0, "toArray": function () {
            return Q.call(this)
        }, "get": function (e) {
            return null != e ? e < 0 ? this[e + this.length] : this[e] : Q.call(this)
        }, "pushStack": function (e) {
            var t = oe.merge(this.constructor(), e);
            return t.prevObject = this, t.context = this.context, t
        }, "each": function (e, t) {
            return oe.each(this, e, t)
        }, "map": function (e) {
            return this.pushStack(oe.map(this, function (t, n) {
                return e.call(t, n, t)
            }))
        }, "slice": function () {
            return this.pushStack(Q.apply(this, arguments))
        }, "first": function () {
            return this.eq(0)
        }, "last": function () {
            return this.eq(-1)
        }, "eq": function (e) {
            var t = this.length, n = +e + (e < 0 ? t : 0);
            return this.pushStack(n >= 0 && n < t ? [this[n]] : [])
        }, "end": function () {
            return this.prevObject || this.constructor(null)
        }, "push": Z, "sort": V.sort, "splice": V.splice
    }, oe.extend = oe.fn.extend = function () {
        var e, t, n, r, i, o, a = arguments[0] || {}, s = 1, u = arguments.length, l = !1;
        for ("boolean" == typeof a && (l = a, a = arguments[s] || {}, s++), "object" == typeof a || oe.isFunction(a) || (a = {}), s === u && (a = this, s--); s < u; s++)if (null != (i = arguments[s]))for (r in i)e = a[r], n = i[r], a !== n && (l && n && (oe.isPlainObject(n) || (t = oe.isArray(n))) ? (t ? (t = !1, o = e && oe.isArray(e) ? e : []) : o = e && oe.isPlainObject(e) ? e : {}, a[r] = oe.extend(l, o, n)) : void 0 !== n && (a[r] = n));
        return a
    }, oe.extend({
        "expando": "jQuery" + (ie + Math.random()).replace(/\D/g, ""), "isReady": !0, "error": function (e) {
            throw new Error(e)
        }, "noop": function () {
        }, "isFunction": function (e) {
            return "function" === oe.type(e)
        }, "isArray": Array.isArray || function (e) {
            return "array" === oe.type(e)
        }, "isWindow": function (e) {
            return null != e && e == e.window
        }, "isNumeric": function (e) {
            return e - parseFloat(e) >= 0
        }, "isEmptyObject": function (e) {
            var t;
            for (t in e)return !1;
            return !0
        }, "isPlainObject": function (e) {
            var t;
            if (!e || "object" !== oe.type(e) || e.nodeType || oe.isWindow(e))return !1;
            try {
                if (e.constructor && !te.call(e, "constructor") && !te.call(e.constructor.prototype, "isPrototypeOf"))return !1
            } catch (n) {
                return !1
            }
            if (re.ownLast)for (t in e)return te.call(e, t);
            for (t in e);
            return void 0 === t || te.call(e, t)
        }, "type": function (e) {
            return null == e ? e + "" : "object" == typeof e || "function" == typeof e ? K[ee.call(e)] || "object" : typeof e
        }, "globalEval": function (t) {
            t && oe.trim(t) && (e.execScript || function (t) {
                e.eval.call(e, t)
            })(t)
        }, "camelCase": function (e) {
            return e.replace(se, "ms-").replace(ue, le)
        }, "nodeName": function (e, t) {
            return e.nodeName && e.nodeName.toLowerCase() === t.toLowerCase()
        }, "each": function (e, t, r) {
            var i, o = 0, a = e.length, s = n(e);
            if (r) {
                if (s)for (; o < a && (i = t.apply(e[o], r), i !== !1); o++); else for (o in e)if (i = t.apply(e[o], r), i === !1)break
            } else if (s)for (; o < a && (i = t.call(e[o], o, e[o]), i !== !1); o++); else for (o in e)if (i = t.call(e[o], o, e[o]), i === !1)break;
            return e
        }, "trim": ne && !ne.call("\ufeff\xa0") ? function (e) {
            return null == e ? "" : ne.call(e)
        } : function (e) {
            return null == e ? "" : (e + "").replace(ae, "")
        }, "makeArray": function (e, t) {
            var r = t || [];
            return null != e && (n(Object(e)) ? oe.merge(r, "string" == typeof e ? [e] : e) : Z.call(r, e)), r
        }, "inArray": function (e, t, n) {
            var r;
            if (t) {
                if (J)return J.call(t, e, n);
                for (r = t.length, n = n ? n < 0 ? Math.max(0, r + n) : n : 0; n < r; n++)if (n in t && t[n] === e)return n
            }
            return -1
        }, "merge": function (e, t) {
            for (var n = +t.length, r = 0, i = e.length; r < n;)e[i++] = t[r++];
            if (n !== n)for (; void 0 !== t[r];)e[i++] = t[r++];
            return e.length = i, e
        }, "grep": function (e, t, n) {
            for (var r, i = [], o = 0, a = e.length, s = !n; o < a; o++)r = !t(e[o], o), r !== s && i.push(e[o]);
            return i
        }, "map": function (e, t, r) {
            var i, o = 0, a = e.length, s = n(e), u = [];
            if (s)for (; o < a; o++)i = t(e[o], o, r), null != i && u.push(i); else for (o in e)i = t(e[o], o, r), null != i && u.push(i);
            return X.apply([], u)
        }, "guid": 1, "proxy": function (e, t) {
            var n, r, i;
            if ("string" == typeof t && (i = e[t], t = e, e = i), oe.isFunction(e))return n = Q.call(arguments, 2), r = function () {
                return e.apply(t || this, n.concat(Q.call(arguments)))
            }, r.guid = e.guid = e.guid || oe.guid++, r
        }, "now": function () {
            return +new Date
        }, "support": re
    }), oe.each("Boolean Number String Function Array Date RegExp Object Error".split(" "), function (e, t) {
        K["[object " + t + "]"] = t.toLowerCase()
    });
    var ce = function (e) {
        function t(e, t, n, r) {
            var i, o, a, s, u, l, f, p, m, g;
            if ((t ? t.ownerDocument || t : W) !== N && O(t), t = t || N, n = n || [], !e || "string" != typeof e)return n;
            if (1 !== (s = t.nodeType) && 9 !== s)return [];
            if (Y && !r) {
                if (i = ve.exec(e))if (a = i[1]) {
                    if (9 === s) {
                        if (o = t.getElementById(a), !o || !o.parentNode)return n;
                        if (o.id === a)return n.push(o), n
                    } else if (t.ownerDocument && (o = t.ownerDocument.getElementById(a)) && F(t, o) && o.id === a)return n.push(o), n
                } else {
                    if (i[2])return K.apply(n, t.getElementsByTagName(e)), n;
                    if ((a = i[3]) && k.getElementsByClassName && t.getElementsByClassName)return K.apply(n, t.getElementsByClassName(a)), n
                }
                if (k.qsa && (!L || !L.test(e))) {
                    if (p = f = R, m = t, g = 9 === s && e, 1 === s && "object" !== t.nodeName.toLowerCase()) {
                        for (l = d(e), (f = t.getAttribute("id")) ? p = f.replace(be, "\\$&") : t.setAttribute("id", p), p = "[id='" + p + "'] ", u = l.length; u--;)l[u] = p + h(l[u]);
                        m = we.test(e) && c(t.parentNode) || t, g = l.join(",")
                    }
                    if (g)try {
                        return K.apply(n, m.querySelectorAll(g)), n
                    } catch (y) {
                    } finally {
                        f || t.removeAttribute("id")
                    }
                }
            }
            return x(e.replace(ue, "$1"), t, n, r)
        }

        function n() {
            function e(n, r) {
                return t.push(n + " ") > S.cacheLength && delete e[t.shift()], e[n + " "] = r
            }

            var t = [];
            return e
        }

        function r(e) {
            return e[R] = !0, e
        }

        function i(e) {
            var t = N.createElement("div");
            try {
                return !!e(t)
            } catch (n) {
                return !1
            } finally {
                t.parentNode && t.parentNode.removeChild(t), t = null
            }
        }

        function o(e, t) {
            for (var n = e.split("|"), r = e.length; r--;)S.attrHandle[n[r]] = t
        }

        function a(e, t) {
            var n = t && e, r = n && 1 === e.nodeType && 1 === t.nodeType && (~t.sourceIndex || V) - (~e.sourceIndex || V);
            if (r)return r;
            if (n)for (; n = n.nextSibling;)if (n === t)return -1;
            return e ? 1 : -1
        }

        function s(e) {
            return function (t) {
                var n = t.nodeName.toLowerCase();
                return "input" === n && t.type === e
            }
        }

        function u(e) {
            return function (t) {
                var n = t.nodeName.toLowerCase();
                return ("input" === n || "button" === n) && t.type === e
            }
        }

        function l(e) {
            return r(function (t) {
                return t = +t, r(function (n, r) {
                    for (var i, o = e([], n.length, t), a = o.length; a--;)n[i = o[a]] && (n[i] = !(r[i] = n[i]))
                })
            })
        }

        function c(e) {
            return e && typeof e.getElementsByTagName !== G && e
        }

        function f() {
        }

        function d(e, n) {
            var r, i, o, a, s, u, l, c = $[e + " "];
            if (c)return n ? 0 : c.slice(0);
            for (s = e, u = [], l = S.preFilter; s;) {
                r && !(i = le.exec(s)) || (i && (s = s.slice(i[0].length) || s), u.push(o = [])), r = !1, (i = ce.exec(s)) && (r = i.shift(), o.push({
                    "value": r,
                    "type": i[0].replace(ue, " ")
                }), s = s.slice(r.length));
                for (a in S.filter)!(i = pe[a].exec(s)) || l[a] && !(i = l[a](i)) || (r = i.shift(), o.push({
                    "value": r,
                    "type": a,
                    "matches": i
                }), s = s.slice(r.length));
                if (!r)break
            }
            return n ? s.length : s ? t.error(e) : $(e, u).slice(0)
        }

        function h(e) {
            for (var t = 0, n = e.length, r = ""; t < n; t++)r += e[t].value;
            return r
        }

        function p(e, t, n) {
            var r = t.dir, i = n && "parentNode" === r, o = q++;
            return t.first ? function (t, n, o) {
                for (; t = t[r];)if (1 === t.nodeType || i)return e(t, n, o)
            } : function (t, n, a) {
                var s, u, l = [I, o];
                if (a) {
                    for (; t = t[r];)if ((1 === t.nodeType || i) && e(t, n, a))return !0
                } else for (; t = t[r];)if (1 === t.nodeType || i) {
                    if (u = t[R] || (t[R] = {}), (s = u[r]) && s[0] === I && s[1] === o)return l[2] = s[2];
                    if (u[r] = l, l[2] = e(t, n, a))return !0
                }
            }
        }

        function m(e) {
            return e.length > 1 ? function (t, n, r) {
                for (var i = e.length; i--;)if (!e[i](t, n, r))return !1;
                return !0
            } : e[0]
        }

        function g(e, t, n, r, i) {
            for (var o, a = [], s = 0, u = e.length, l = null != t; s < u; s++)(o = e[s]) && (n && !n(o, r, i) || (a.push(o), l && t.push(s)));
            return a
        }

        function y(e, t, n, i, o, a) {
            return i && !i[R] && (i = y(i)), o && !o[R] && (o = y(o, a)), r(function (r, a, s, u) {
                var l, c, f, d = [], h = [], p = a.length, m = r || b(t || "*", s.nodeType ? [s] : s, []), y = !e || !r && t ? m : g(m, d, e, s, u), v = n ? o || (r ? e : p || i) ? [] : a : y;
                if (n && n(y, v, s, u), i)for (l = g(v, h), i(l, [], s, u), c = l.length; c--;)(f = l[c]) && (v[h[c]] = !(y[h[c]] = f));
                if (r) {
                    if (o || e) {
                        if (o) {
                            for (l = [], c = v.length; c--;)(f = v[c]) && l.push(y[c] = f);
                            o(null, v = [], l, u)
                        }
                        for (c = v.length; c--;)(f = v[c]) && (l = o ? te.call(r, f) : d[c]) > -1 && (r[l] = !(a[l] = f))
                    }
                } else v = g(v === a ? v.splice(p, v.length) : v), o ? o(null, a, v, u) : K.apply(a, v)
            })
        }

        function v(e) {
            for (var t, n, r, i = e.length, o = S.relative[e[0].type], a = o || S.relative[" "], s = o ? 1 : 0, u = p(function (e) {
                return e === t
            }, a, !0), l = p(function (e) {
                return te.call(t, e) > -1
            }, a, !0), c = [function (e, n, r) {
                return !o && (r || n !== C) || ((t = n).nodeType ? u(e, n, r) : l(e, n, r))
            }]; s < i; s++)if (n = S.relative[e[s].type])c = [p(m(c), n)]; else {
                if (n = S.filter[e[s].type].apply(null, e[s].matches), n[R]) {
                    for (r = ++s; r < i && !S.relative[e[r].type]; r++);
                    return y(s > 1 && m(c), s > 1 && h(e.slice(0, s - 1).concat({"value": " " === e[s - 2].type ? "*" : ""})).replace(ue, "$1"), n, s < r && v(e.slice(s, r)), r < i && v(e = e.slice(r)), r < i && h(e))
                }
                c.push(n)
            }
            return m(c)
        }

        function w(e, n) {
            var i = n.length > 0, o = e.length > 0, a = function (r, a, s, u, l) {
                var c, f, d, h = 0, p = "0", m = r && [], y = [], v = C, w = r || o && S.find.TAG("*", l), b = I += null == v ? 1 : Math.random() || .1, x = w.length;
                for (l && (C = a !== N && a); p !== x && null != (c = w[p]); p++) {
                    if (o && c) {
                        for (f = 0; d = e[f++];)if (d(c, a, s)) {
                            u.push(c);
                            break
                        }
                        l && (I = b)
                    }
                    i && ((c = !d && c) && h--, r && m.push(c))
                }
                if (h += p, i && p !== h) {
                    for (f = 0; d = n[f++];)d(m, y, a, s);
                    if (r) {
                        if (h > 0)for (; p--;)m[p] || y[p] || (y[p] = Z.call(u));
                        y = g(y)
                    }
                    K.apply(u, y), l && !r && y.length > 0 && h + n.length > 1 && t.uniqueSort(u)
                }
                return l && (I = b, C = v), m
            };
            return i ? r(a) : a
        }

        function b(e, n, r) {
            for (var i = 0, o = n.length; i < o; i++)t(e, n[i], r);
            return r
        }

        function x(e, t, n, r) {
            var i, o, a, s, u, l = d(e);
            if (!r && 1 === l.length) {
                if (o = l[0] = l[0].slice(0), o.length > 2 && "ID" === (a = o[0]).type && k.getById && 9 === t.nodeType && Y && S.relative[o[1].type]) {
                    if (t = (S.find.ID(a.matches[0].replace(xe, _e), t) || [])[0], !t)return n;
                    e = e.slice(o.shift().value.length)
                }
                for (i = pe.needsContext.test(e) ? 0 : o.length; i-- && (a = o[i], !S.relative[s = a.type]);)if ((u = S.find[s]) && (r = u(a.matches[0].replace(xe, _e), we.test(o[0].type) && c(t.parentNode) || t))) {
                    if (o.splice(i, 1), e = r.length && h(o), !e)return K.apply(n, r), n;
                    break
                }
            }
            return D(e, l)(r, t, !Y, n, we.test(e) && c(t.parentNode) || t), n
        }

        var _, k, S, T, M, D, C, E, A, O, N, j, Y, L, H, P, F, R = "sizzle" + -new Date, W = e.document, I = 0, q = 0, B = n(), $ = n(), U = n(), z = function (e, t) {
            return e === t && (A = !0), 0
        }, G = "undefined", V = 1 << 31, Q = {}.hasOwnProperty, X = [], Z = X.pop, J = X.push, K = X.push, ee = X.slice, te = X.indexOf || function (e) {
                for (var t = 0, n = this.length; t < n; t++)if (this[t] === e)return t;
                return -1
            }, ne = "checked|selected|async|autofocus|autoplay|controls|defer|disabled|hidden|ismap|loop|multiple|open|readonly|required|scoped", re = "[\\x20\\t\\r\\n\\f]", ie = "(?:\\\\.|[\\w-]|[^\\x00-\\xa0])+", oe = ie.replace("w", "w#"), ae = "\\[" + re + "*(" + ie + ")" + re + "*(?:([*^$|!~]?=)" + re + "*(?:(['\"])((?:\\\\.|[^\\\\])*?)\\3|(" + oe + ")|)|)" + re + "*\\]", se = ":(" + ie + ")(?:\\(((['\"])((?:\\\\.|[^\\\\])*?)\\3|((?:\\\\.|[^\\\\()[\\]]|" + ae.replace(3, 8) + ")*)|.*)\\)|)", ue = new RegExp("^" + re + "+|((?:^|[^\\\\])(?:\\\\.)*)" + re + "+$", "g"), le = new RegExp("^" + re + "*," + re + "*"), ce = new RegExp("^" + re + "*([>+~]|" + re + ")" + re + "*"), fe = new RegExp("=" + re + "*([^\\]'\"]*?)" + re + "*\\]", "g"), de = new RegExp(se), he = new RegExp("^" + oe + "$"), pe = {
            "ID": new RegExp("^#(" + ie + ")"),
            "CLASS": new RegExp("^\\.(" + ie + ")"),
            "TAG": new RegExp("^(" + ie.replace("w", "w*") + ")"),
            "ATTR": new RegExp("^" + ae),
            "PSEUDO": new RegExp("^" + se),
            "CHILD": new RegExp("^:(only|first|last|nth|nth-last)-(child|of-type)(?:\\(" + re + "*(even|odd|(([+-]|)(\\d*)n|)" + re + "*(?:([+-]|)" + re + "*(\\d+)|))" + re + "*\\)|)", "i"),
            "bool": new RegExp("^(?:" + ne + ")$", "i"),
            "needsContext": new RegExp("^" + re + "*[>+~]|:(even|odd|eq|gt|lt|nth|first|last)(?:\\(" + re + "*((?:-\\d)?\\d*)" + re + "*\\)|)(?=[^-]|$)", "i")
        }, me = /^(?:input|select|textarea|button)$/i, ge = /^h\d$/i, ye = /^[^{]+\{\s*\[native \w/, ve = /^(?:#([\w-]+)|(\w+)|\.([\w-]+))$/, we = /[+~]/, be = /'|\\/g, xe = new RegExp("\\\\([\\da-f]{1,6}" + re + "?|(" + re + ")|.)", "ig"), _e = function (e, t, n) {
            var r = "0x" + t - 65536;
            return r !== r || n ? t : r < 0 ? String.fromCharCode(r + 65536) : String.fromCharCode(r >> 10 | 55296, 1023 & r | 56320)
        };
        try {
            K.apply(X = ee.call(W.childNodes), W.childNodes), X[W.childNodes.length].nodeType
        } catch (ke) {
            K = {
                "apply": X.length ? function (e, t) {
                    J.apply(e, ee.call(t))
                } : function (e, t) {
                    for (var n = e.length, r = 0; e[n++] = t[r++];);
                    e.length = n - 1
                }
            }
        }
        k = t.support = {}, M = t.isXML = function (e) {
            var t = e && (e.ownerDocument || e).documentElement;
            return !!t && "HTML" !== t.nodeName
        }, O = t.setDocument = function (e) {
            var t, n = e ? e.ownerDocument || e : W, r = n.defaultView;
            return n !== N && 9 === n.nodeType && n.documentElement ? (N = n, j = n.documentElement, Y = !M(n), r && r !== r.top && (r.addEventListener ? r.addEventListener("unload", function () {
                O()
            }, !1) : r.attachEvent && r.attachEvent("onunload", function () {
                O()
            })), k.attributes = i(function (e) {
                return e.className = "i", !e.getAttribute("className")
            }), k.getElementsByTagName = i(function (e) {
                return e.appendChild(n.createComment("")), !e.getElementsByTagName("*").length
            }), k.getElementsByClassName = ye.test(n.getElementsByClassName) && i(function (e) {
                    return e.innerHTML = "<div class='a'></div><div class='a i'></div>", e.firstChild.className = "i", 2 === e.getElementsByClassName("i").length
                }), k.getById = i(function (e) {
                return j.appendChild(e).id = R, !n.getElementsByName || !n.getElementsByName(R).length
            }), k.getById ? (S.find.ID = function (e, t) {
                if (typeof t.getElementById !== G && Y) {
                    var n = t.getElementById(e);
                    return n && n.parentNode ? [n] : []
                }
            }, S.filter.ID = function (e) {
                var t = e.replace(xe, _e);
                return function (e) {
                    return e.getAttribute("id") === t
                }
            }) : (delete S.find.ID, S.filter.ID = function (e) {
                var t = e.replace(xe, _e);
                return function (e) {
                    var n = typeof e.getAttributeNode !== G && e.getAttributeNode("id");
                    return n && n.value === t
                }
            }), S.find.TAG = k.getElementsByTagName ? function (e, t) {
                if (typeof t.getElementsByTagName !== G)return t.getElementsByTagName(e)
            } : function (e, t) {
                var n, r = [], i = 0, o = t.getElementsByTagName(e);
                if ("*" === e) {
                    for (; n = o[i++];)1 === n.nodeType && r.push(n);
                    return r
                }
                return o
            }, S.find.CLASS = k.getElementsByClassName && function (e, t) {
                    if (typeof t.getElementsByClassName !== G && Y)return t.getElementsByClassName(e)
                }, H = [], L = [], (k.qsa = ye.test(n.querySelectorAll)) && (i(function (e) {
                e.innerHTML = "<select t=''><option selected=''></option></select>", e.querySelectorAll("[t^='']").length && L.push("[*^$]=" + re + "*(?:''|\"\")"), e.querySelectorAll("[selected]").length || L.push("\\[" + re + "*(?:value|" + ne + ")"), e.querySelectorAll(":checked").length || L.push(":checked")
            }), i(function (e) {
                var t = n.createElement("input");
                t.setAttribute("type", "hidden"), e.appendChild(t).setAttribute("name", "D"), e.querySelectorAll("[name=d]").length && L.push("name" + re + "*[*^$|!~]?="), e.querySelectorAll(":enabled").length || L.push(":enabled", ":disabled"), e.querySelectorAll("*,:x"), L.push(",.*:")
            })), (k.matchesSelector = ye.test(P = j.webkitMatchesSelector || j.mozMatchesSelector || j.oMatchesSelector || j.msMatchesSelector)) && i(function (e) {
                k.disconnectedMatch = P.call(e, "div"), P.call(e, "[s!='']:x"), H.push("!=", se)
            }), L = L.length && new RegExp(L.join("|")), H = H.length && new RegExp(H.join("|")), t = ye.test(j.compareDocumentPosition), F = t || ye.test(j.contains) ? function (e, t) {
                var n = 9 === e.nodeType ? e.documentElement : e, r = t && t.parentNode;
                return e === r || !(!r || 1 !== r.nodeType || !(n.contains ? n.contains(r) : e.compareDocumentPosition && 16 & e.compareDocumentPosition(r)))
            } : function (e, t) {
                if (t)for (; t = t.parentNode;)if (t === e)return !0;
                return !1
            }, z = t ? function (e, t) {
                if (e === t)return A = !0, 0;
                var r = !e.compareDocumentPosition - !t.compareDocumentPosition;
                return r ? r : (r = (e.ownerDocument || e) === (t.ownerDocument || t) ? e.compareDocumentPosition(t) : 1, 1 & r || !k.sortDetached && t.compareDocumentPosition(e) === r ? e === n || e.ownerDocument === W && F(W, e) ? -1 : t === n || t.ownerDocument === W && F(W, t) ? 1 : E ? te.call(E, e) - te.call(E, t) : 0 : 4 & r ? -1 : 1)
            } : function (e, t) {
                if (e === t)return A = !0, 0;
                var r, i = 0, o = e.parentNode, s = t.parentNode, u = [e], l = [t];
                if (!o || !s)return e === n ? -1 : t === n ? 1 : o ? -1 : s ? 1 : E ? te.call(E, e) - te.call(E, t) : 0;
                if (o === s)return a(e, t);
                for (r = e; r = r.parentNode;)u.unshift(r);
                for (r = t; r = r.parentNode;)l.unshift(r);
                for (; u[i] === l[i];)i++;
                return i ? a(u[i], l[i]) : u[i] === W ? -1 : l[i] === W ? 1 : 0
            }, n) : N
        }, t.matches = function (e, n) {
            return t(e, null, null, n)
        }, t.matchesSelector = function (e, n) {
            if ((e.ownerDocument || e) !== N && O(e), n = n.replace(fe, "='$1']"), k.matchesSelector && Y && (!H || !H.test(n)) && (!L || !L.test(n)))try {
                var r = P.call(e, n);
                if (r || k.disconnectedMatch || e.document && 11 !== e.document.nodeType)return r
            } catch (i) {
            }
            return t(n, N, null, [e]).length > 0
        }, t.contains = function (e, t) {
            return (e.ownerDocument || e) !== N && O(e), F(e, t)
        }, t.attr = function (e, t) {
            (e.ownerDocument || e) !== N && O(e);
            var n = S.attrHandle[t.toLowerCase()], r = n && Q.call(S.attrHandle, t.toLowerCase()) ? n(e, t, !Y) : void 0;
            return void 0 !== r ? r : k.attributes || !Y ? e.getAttribute(t) : (r = e.getAttributeNode(t)) && r.specified ? r.value : null
        }, t.error = function (e) {
            throw new Error("Syntax error, unrecognized expression: " + e)
        }, t.uniqueSort = function (e) {
            var t, n = [], r = 0, i = 0;
            if (A = !k.detectDuplicates, E = !k.sortStable && e.slice(0), e.sort(z), A) {
                for (; t = e[i++];)t === e[i] && (r = n.push(i));
                for (; r--;)e.splice(n[r], 1)
            }
            return E = null, e
        }, T = t.getText = function (e) {
            var t, n = "", r = 0, i = e.nodeType;
            if (i) {
                if (1 === i || 9 === i || 11 === i) {
                    if ("string" == typeof e.textContent)return e.textContent;
                    for (e = e.firstChild; e; e = e.nextSibling)n += T(e)
                } else if (3 === i || 4 === i)return e.nodeValue
            } else for (; t = e[r++];)n += T(t);
            return n
        }, S = t.selectors = {
            "cacheLength": 50,
            "createPseudo": r,
            "match": pe,
            "attrHandle": {},
            "find": {},
            "relative": {
                ">": {"dir": "parentNode", "first": !0},
                " ": {"dir": "parentNode"},
                "+": {"dir": "previousSibling", "first": !0},
                "~": {"dir": "previousSibling"}
            },
            "preFilter": {
                "ATTR": function (e) {
                    return e[1] = e[1].replace(xe, _e), e[3] = (e[4] || e[5] || "").replace(xe, _e), "~=" === e[2] && (e[3] = " " + e[3] + " "), e.slice(0, 4)
                }, "CHILD": function (e) {
                    return e[1] = e[1].toLowerCase(), "nth" === e[1].slice(0, 3) ? (e[3] || t.error(e[0]), e[4] = +(e[4] ? e[5] + (e[6] || 1) : 2 * ("even" === e[3] || "odd" === e[3])), e[5] = +(e[7] + e[8] || "odd" === e[3])) : e[3] && t.error(e[0]), e
                }, "PSEUDO": function (e) {
                    var t, n = !e[5] && e[2];
                    return pe.CHILD.test(e[0]) ? null : (e[3] && void 0 !== e[4] ? e[2] = e[4] : n && de.test(n) && (t = d(n, !0)) && (t = n.indexOf(")", n.length - t) - n.length) && (e[0] = e[0].slice(0, t), e[2] = n.slice(0, t)), e.slice(0, 3))
                }
            },
            "filter": {
                "TAG": function (e) {
                    var t = e.replace(xe, _e).toLowerCase();
                    return "*" === e ? function () {
                        return !0
                    } : function (e) {
                        return e.nodeName && e.nodeName.toLowerCase() === t
                    }
                }, "CLASS": function (e) {
                    var t = B[e + " "];
                    return t || (t = new RegExp("(^|" + re + ")" + e + "(" + re + "|$)")) && B(e, function (e) {
                            return t.test("string" == typeof e.className && e.className || typeof e.getAttribute !== G && e.getAttribute("class") || "")
                        })
                }, "ATTR": function (e, n, r) {
                    return function (i) {
                        var o = t.attr(i, e);
                        return null == o ? "!=" === n : !n || (o += "", "=" === n ? o === r : "!=" === n ? o !== r : "^=" === n ? r && 0 === o.indexOf(r) : "*=" === n ? r && o.indexOf(r) > -1 : "$=" === n ? r && o.slice(-r.length) === r : "~=" === n ? (" " + o + " ").indexOf(r) > -1 : "|=" === n && (o === r || o.slice(0, r.length + 1) === r + "-"))
                    }
                }, "CHILD": function (e, t, n, r, i) {
                    var o = "nth" !== e.slice(0, 3), a = "last" !== e.slice(-4), s = "of-type" === t;
                    return 1 === r && 0 === i ? function (e) {
                        return !!e.parentNode
                    } : function (t, n, u) {
                        var l, c, f, d, h, p, m = o !== a ? "nextSibling" : "previousSibling", g = t.parentNode, y = s && t.nodeName.toLowerCase(), v = !u && !s;
                        if (g) {
                            if (o) {
                                for (; m;) {
                                    for (f = t; f = f[m];)if (s ? f.nodeName.toLowerCase() === y : 1 === f.nodeType)return !1;
                                    p = m = "only" === e && !p && "nextSibling"
                                }
                                return !0
                            }
                            if (p = [a ? g.firstChild : g.lastChild], a && v) {
                                for (c = g[R] || (g[R] = {}), l = c[e] || [], h = l[0] === I && l[1], d = l[0] === I && l[2], f = h && g.childNodes[h]; f = ++h && f && f[m] || (d = h = 0) || p.pop();)if (1 === f.nodeType && ++d && f === t) {
                                    c[e] = [I, h, d];
                                    break
                                }
                            } else if (v && (l = (t[R] || (t[R] = {}))[e]) && l[0] === I)d = l[1]; else for (; (f = ++h && f && f[m] || (d = h = 0) || p.pop()) && ((s ? f.nodeName.toLowerCase() !== y : 1 !== f.nodeType) || !++d || (v && ((f[R] || (f[R] = {}))[e] = [I, d]), f !== t)););
                            return d -= i, d === r || d % r === 0 && d / r >= 0
                        }
                    }
                }, "PSEUDO": function (e, n) {
                    var i, o = S.pseudos[e] || S.setFilters[e.toLowerCase()] || t.error("unsupported pseudo: " + e);
                    return o[R] ? o(n) : o.length > 1 ? (i = [e, e, "", n], S.setFilters.hasOwnProperty(e.toLowerCase()) ? r(function (e, t) {
                        for (var r, i = o(e, n), a = i.length; a--;)r = te.call(e, i[a]), e[r] = !(t[r] = i[a])
                    }) : function (e) {
                        return o(e, 0, i)
                    }) : o
                }
            },
            "pseudos": {
                "not": r(function (e) {
                    var t = [], n = [], i = D(e.replace(ue, "$1"));
                    return i[R] ? r(function (e, t, n, r) {
                        for (var o, a = i(e, null, r, []), s = e.length; s--;)(o = a[s]) && (e[s] = !(t[s] = o))
                    }) : function (e, r, o) {
                        return t[0] = e, i(t, null, o, n), !n.pop()
                    }
                }), "has": r(function (e) {
                    return function (n) {
                        return t(e, n).length > 0
                    }
                }), "contains": r(function (e) {
                    return function (t) {
                        return (t.textContent || t.innerText || T(t)).indexOf(e) > -1
                    }
                }), "lang": r(function (e) {
                    return he.test(e || "") || t.error("unsupported lang: " + e), e = e.replace(xe, _e).toLowerCase(), function (t) {
                        var n;
                        do if (n = Y ? t.lang : t.getAttribute("xml:lang") || t.getAttribute("lang"))return n = n.toLowerCase(), n === e || 0 === n.indexOf(e + "-"); while ((t = t.parentNode) && 1 === t.nodeType);
                        return !1
                    }
                }), "target": function (t) {
                    var n = e.location && e.location.hash;
                    return n && n.slice(1) === t.id
                }, "root": function (e) {
                    return e === j
                }, "focus": function (e) {
                    return e === N.activeElement && (!N.hasFocus || N.hasFocus()) && !!(e.type || e.href || ~e.tabIndex)
                }, "enabled": function (e) {
                    return e.disabled === !1
                }, "disabled": function (e) {
                    return e.disabled === !0
                }, "checked": function (e) {
                    var t = e.nodeName.toLowerCase();
                    return "input" === t && !!e.checked || "option" === t && !!e.selected
                }, "selected": function (e) {
                    return e.parentNode && e.parentNode.selectedIndex, e.selected === !0
                }, "empty": function (e) {
                    for (e = e.firstChild; e; e = e.nextSibling)if (e.nodeType < 6)return !1;
                    return !0
                }, "parent": function (e) {
                    return !S.pseudos.empty(e)
                }, "header": function (e) {
                    return ge.test(e.nodeName)
                }, "input": function (e) {
                    return me.test(e.nodeName)
                }, "button": function (e) {
                    var t = e.nodeName.toLowerCase();
                    return "input" === t && "button" === e.type || "button" === t
                }, "text": function (e) {
                    var t;
                    return "input" === e.nodeName.toLowerCase() && "text" === e.type && (null == (t = e.getAttribute("type")) || "text" === t.toLowerCase())
                }, "first": l(function () {
                    return [0]
                }), "last": l(function (e, t) {
                    return [t - 1]
                }), "eq": l(function (e, t, n) {
                    return [n < 0 ? n + t : n]
                }), "even": l(function (e, t) {
                    for (var n = 0; n < t; n += 2)e.push(n);
                    return e
                }), "odd": l(function (e, t) {
                    for (var n = 1; n < t; n += 2)e.push(n);
                    return e
                }), "lt": l(function (e, t, n) {
                    for (var r = n < 0 ? n + t : n; --r >= 0;)e.push(r);
                    return e
                }), "gt": l(function (e, t, n) {
                    for (var r = n < 0 ? n + t : n; ++r < t;)e.push(r);
                    return e
                })
            }
        }, S.pseudos.nth = S.pseudos.eq;
        for (_ in{"radio": !0, "checkbox": !0, "file": !0, "password": !0, "image": !0})S.pseudos[_] = s(_);
        for (_ in{"submit": !0, "reset": !0})S.pseudos[_] = u(_);
        return f.prototype = S.filters = S.pseudos, S.setFilters = new f, D = t.compile = function (e, t) {
            var n, r = [], i = [], o = U[e + " "];
            if (!o) {
                for (t || (t = d(e)), n = t.length; n--;)o = v(t[n]), o[R] ? r.push(o) : i.push(o);
                o = U(e, w(i, r))
            }
            return o
        }, k.sortStable = R.split("").sort(z).join("") === R, k.detectDuplicates = !!A, O(), k.sortDetached = i(function (e) {
            return 1 & e.compareDocumentPosition(N.createElement("div"))
        }), i(function (e) {
            return e.innerHTML = "<a href='#'></a>", "#" === e.firstChild.getAttribute("href")
        }) || o("type|href|height|width", function (e, t, n) {
            if (!n)return e.getAttribute(t, "type" === t.toLowerCase() ? 1 : 2)
        }), k.attributes && i(function (e) {
            return e.innerHTML = "<input/>", e.firstChild.setAttribute("value", ""), "" === e.firstChild.getAttribute("value")
        }) || o("value", function (e, t, n) {
            if (!n && "input" === e.nodeName.toLowerCase())return e.defaultValue
        }), i(function (e) {
            return null == e.getAttribute("disabled")
        }) || o(ne, function (e, t, n) {
            var r;
            if (!n)return e[t] === !0 ? t.toLowerCase() : (r = e.getAttributeNode(t)) && r.specified ? r.value : null
        }), t
    }(e);
    oe.find = ce, oe.expr = ce.selectors, oe.expr[":"] = oe.expr.pseudos, oe.unique = ce.uniqueSort, oe.text = ce.getText, oe.isXMLDoc = ce.isXML, oe.contains = ce.contains;
    var fe = oe.expr.match.needsContext, de = /^<(\w+)\s*\/?>(?:<\/\1>|)$/, he = /^.[^:#\[\.,]*$/;
    oe.filter = function (e, t, n) {
        var r = t[0];
        return n && (e = ":not(" + e + ")"), 1 === t.length && 1 === r.nodeType ? oe.find.matchesSelector(r, e) ? [r] : [] : oe.find.matches(e, oe.grep(t, function (e) {
            return 1 === e.nodeType
        }))
    }, oe.fn.extend({
        "find": function (e) {
            var t, n = [], r = this, i = r.length;
            if ("string" != typeof e)return this.pushStack(oe(e).filter(function () {
                for (t = 0; t < i; t++)if (oe.contains(r[t], this))return !0
            }));
            for (t = 0; t < i; t++)oe.find(e, r[t], n);
            return n = this.pushStack(i > 1 ? oe.unique(n) : n), n.selector = this.selector ? this.selector + " " + e : e, n
        }, "filter": function (e) {
            return this.pushStack(r(this, e || [], !1))
        }, "not": function (e) {
            return this.pushStack(r(this, e || [], !0))
        }, "is": function (e) {
            return !!r(this, "string" == typeof e && fe.test(e) ? oe(e) : e || [], !1).length
        }
    });
    var pe, me = e.document, ge = /^(?:\s*(<[\w\W]+>)[^>]*|#([\w-]*))$/, ye = oe.fn.init = function (e, t) {
        var n, r;
        if (!e)return this;
        if ("string" == typeof e) {
            if (n = "<" === e.charAt(0) && ">" === e.charAt(e.length - 1) && e.length >= 3 ? [null, e, null] : ge.exec(e), !n || !n[1] && t)return !t || t.jquery ? (t || pe).find(e) : this.constructor(t).find(e);
            if (n[1]) {
                if (t = t instanceof oe ? t[0] : t, oe.merge(this, oe.parseHTML(n[1], t && t.nodeType ? t.ownerDocument || t : me, !0)), de.test(n[1]) && oe.isPlainObject(t))for (n in t)oe.isFunction(this[n]) ? this[n](t[n]) : this.attr(n, t[n]);
                return this
            }
            if (r = me.getElementById(n[2]), r && r.parentNode) {
                if (r.id !== n[2])return pe.find(e);
                this.length = 1, this[0] = r
            }
            return this.context = me, this.selector = e, this
        }
        return e.nodeType ? (this.context = this[0] = e, this.length = 1, this) : oe.isFunction(e) ? "undefined" != typeof pe.ready ? pe.ready(e) : e(oe) : (void 0 !== e.selector && (this.selector = e.selector, this.context = e.context), oe.makeArray(e, this))
    };
    ye.prototype = oe.fn, pe = oe(me);
    var ve = /^(?:parents|prev(?:Until|All))/, we = {"children": !0, "contents": !0, "next": !0, "prev": !0};
    oe.extend({
        "dir": function (e, t, n) {
            for (var r = [], i = e[t]; i && 9 !== i.nodeType && (void 0 === n || 1 !== i.nodeType || !oe(i).is(n));)1 === i.nodeType && r.push(i), i = i[t];
            return r
        }, "sibling": function (e, t) {
            for (var n = []; e; e = e.nextSibling)1 === e.nodeType && e !== t && n.push(e);
            return n
        }
    }), oe.fn.extend({
        "has": function (e) {
            var t, n = oe(e, this), r = n.length;
            return this.filter(function () {
                for (t = 0; t < r; t++)if (oe.contains(this, n[t]))return !0
            })
        }, "closest": function (e, t) {
            for (var n, r = 0, i = this.length, o = [], a = fe.test(e) || "string" != typeof e ? oe(e, t || this.context) : 0; r < i; r++)for (n = this[r]; n && n !== t; n = n.parentNode)if (n.nodeType < 11 && (a ? a.index(n) > -1 : 1 === n.nodeType && oe.find.matchesSelector(n, e))) {
                o.push(n);
                break
            }
            return this.pushStack(o.length > 1 ? oe.unique(o) : o)
        }, "index": function (e) {
            return e ? "string" == typeof e ? oe.inArray(this[0], oe(e)) : oe.inArray(e.jquery ? e[0] : e, this) : this[0] && this[0].parentNode ? this.first().prevAll().length : -1
        }, "add": function (e, t) {
            return this.pushStack(oe.unique(oe.merge(this.get(), oe(e, t))))
        }, "addBack": function (e) {
            return this.add(null == e ? this.prevObject : this.prevObject.filter(e))
        }
    }), oe.each({
        "parent": function (e) {
            var t = e.parentNode;
            return t && 11 !== t.nodeType ? t : null
        }, "parents": function (e) {
            return oe.dir(e, "parentNode")
        }, "parentsUntil": function (e, t, n) {
            return oe.dir(e, "parentNode", n)
        }, "next": function (e) {
            return i(e, "nextSibling")
        }, "prev": function (e) {
            return i(e, "previousSibling")
        }, "nextAll": function (e) {
            return oe.dir(e, "nextSibling")
        }, "prevAll": function (e) {
            return oe.dir(e, "previousSibling")
        }, "nextUntil": function (e, t, n) {
            return oe.dir(e, "nextSibling", n)
        }, "prevUntil": function (e, t, n) {
            return oe.dir(e, "previousSibling", n)
        }, "siblings": function (e) {
            return oe.sibling((e.parentNode || {}).firstChild, e)
        }, "children": function (e) {
            return oe.sibling(e.firstChild)
        }, "contents": function (e) {
            return oe.nodeName(e, "iframe") ? e.contentDocument || e.contentWindow.document : oe.merge([], e.childNodes)
        }
    }, function (e, t) {
        oe.fn[e] = function (n, r) {
            var i = oe.map(this, t, n);
            return "Until" !== e.slice(-5) && (r = n), r && "string" == typeof r && (i = oe.filter(r, i)), this.length > 1 && (we[e] || (i = oe.unique(i)), ve.test(e) && (i = i.reverse())), this.pushStack(i)
        }
    });
    var be = /\S+/g, xe = {};
    oe.Callbacks = function (e) {
        e = "string" == typeof e ? xe[e] || o(e) : oe.extend({}, e);
        var t, n, r, i, a, s, u = [], l = !e.once && [], c = function (o) {
            for (n = e.memory && o, r = !0, a = s || 0, s = 0, i = u.length, t = !0; u && a < i; a++)if (u[a].apply(o[0], o[1]) === !1 && e.stopOnFalse) {
                n = !1;
                break
            }
            t = !1, u && (l ? l.length && c(l.shift()) : n ? u = [] : f.disable())
        }, f = {
            "add": function () {
                if (u) {
                    var r = u.length;
                    !function o(t) {
                        oe.each(t, function (t, n) {
                            var r = oe.type(n);
                            "function" === r ? e.unique && f.has(n) || u.push(n) : n && n.length && "string" !== r && o(n)
                        })
                    }(arguments), t ? i = u.length : n && (s = r, c(n))
                }
                return this
            }, "remove": function () {
                return u && oe.each(arguments, function (e, n) {
                    for (var r; (r = oe.inArray(n, u, r)) > -1;)u.splice(r, 1), t && (r <= i && i--, r <= a && a--)
                }), this
            }, "has": function (e) {
                return e ? oe.inArray(e, u) > -1 : !(!u || !u.length)
            }, "empty": function () {
                return u = [], i = 0, this
            }, "disable": function () {
                return u = l = n = void 0, this
            }, "disabled": function () {
                return !u
            }, "lock": function () {
                return l = void 0, n || f.disable(), this
            }, "locked": function () {
                return !l
            }, "fireWith": function (e, n) {
                return !u || r && !l || (n = n || [], n = [e, n.slice ? n.slice() : n], t ? l.push(n) : c(n)), this
            }, "fire": function () {
                return f.fireWith(this, arguments), this
            }, "fired": function () {
                return !!r
            }
        };
        return f
    }, oe.extend({
        "Deferred": function (e) {
            var t = [["resolve", "done", oe.Callbacks("once memory"), "resolved"], ["reject", "fail", oe.Callbacks("once memory"), "rejected"], ["notify", "progress", oe.Callbacks("memory")]], n = "pending", r = {
                "state": function () {
                    return n
                }, "always": function () {
                    return i.done(arguments).fail(arguments), this
                }, "then": function () {
                    var e = arguments;
                    return oe.Deferred(function (n) {
                        oe.each(t, function (t, o) {
                            var a = oe.isFunction(e[t]) && e[t];
                            i[o[1]](function () {
                                var e = a && a.apply(this, arguments);
                                e && oe.isFunction(e.promise) ? e.promise().done(n.resolve).fail(n.reject).progress(n.notify) : n[o[0] + "With"](this === r ? n.promise() : this, a ? [e] : arguments)
                            })
                        }), e = null
                    }).promise()
                }, "promise": function (e) {
                    return null != e ? oe.extend(e, r) : r
                }
            }, i = {};
            return r.pipe = r.then, oe.each(t, function (e, o) {
                var a = o[2], s = o[3];
                r[o[1]] = a.add, s && a.add(function () {
                    n = s
                }, t[1 ^ e][2].disable, t[2][2].lock), i[o[0]] = function () {
                    return i[o[0] + "With"](this === i ? r : this, arguments), this
                }, i[o[0] + "With"] = a.fireWith
            }), r.promise(i), e && e.call(i, i), i
        }, "when": function (e) {
            var t, n, r, i = 0, o = Q.call(arguments), a = o.length, s = 1 !== a || e && oe.isFunction(e.promise) ? a : 0, u = 1 === s ? e : oe.Deferred(), l = function (e, n, r) {
                return function (i) {
                    n[e] = this, r[e] = arguments.length > 1 ? Q.call(arguments) : i, r === t ? u.notifyWith(n, r) : --s || u.resolveWith(n, r)
                }
            };
            if (a > 1)for (t = new Array(a), n = new Array(a), r = new Array(a); i < a; i++)o[i] && oe.isFunction(o[i].promise) ? o[i].promise().done(l(i, r, o)).fail(u.reject).progress(l(i, n, t)) : --s;
            return s || u.resolveWith(r, o), u.promise()
        }
    });
    var _e;
    oe.fn.ready = function (e) {
        return oe.ready.promise().done(e), this
    }, oe.extend({
        "isReady": !1, "readyWait": 1, "holdReady": function (e) {
            e ? oe.readyWait++ : oe.ready(!0)
        }, "ready": function (e) {
            if (e === !0 ? !--oe.readyWait : !oe.isReady) {
                if (!me.body)return setTimeout(oe.ready);
                oe.isReady = !0, e !== !0 && --oe.readyWait > 0 || (_e.resolveWith(me, [oe]), oe.fn.trigger && oe(me).trigger("ready").off("ready"))
            }
        }
    }), oe.ready.promise = function (t) {
        if (!_e)if (_e = oe.Deferred(), "complete" === me.readyState)setTimeout(oe.ready); else if (me.addEventListener)me.addEventListener("DOMContentLoaded", s, !1), e.addEventListener("load", s, !1); else {
            me.attachEvent("onreadystatechange", s), e.attachEvent("onload", s);
            var n = !1;
            try {
                n = null == e.frameElement && me.documentElement
            } catch (r) {
            }
            n && n.doScroll && !function i() {
                if (!oe.isReady) {
                    try {
                        n.doScroll("left")
                    } catch (e) {
                        return setTimeout(i, 50)
                    }
                    a(), oe.ready()
                }
            }()
        }
        return _e.promise(t)
    };
    var ke, Se = "undefined";
    for (ke in oe(re))break;
    re.ownLast = "0" !== ke, re.inlineBlockNeedsLayout = !1, oe(function () {
        var e, t, n = me.getElementsByTagName("body")[0];
        n && (e = me.createElement("div"), e.style.cssText = "border:0;width:0;height:0;position:absolute;top:0;left:-9999px;margin-top:1px", t = me.createElement("div"), n.appendChild(e).appendChild(t), typeof t.style.zoom !== Se && (t.style.cssText = "border:0;margin:0;width:1px;padding:1px;display:inline;zoom:1", (re.inlineBlockNeedsLayout = 3 === t.offsetWidth) && (n.style.zoom = 1)), n.removeChild(e), e = t = null)
    }), function () {
        var e = me.createElement("div");
        if (null == re.deleteExpando) {
            re.deleteExpando = !0;
            try {
                delete e.test
            } catch (t) {
                re.deleteExpando = !1
            }
        }
        e = null
    }(), oe.acceptData = function (e) {
        var t = oe.noData[(e.nodeName + " ").toLowerCase()], n = +e.nodeType || 1;
        return (1 === n || 9 === n) && (!t || t !== !0 && e.getAttribute("classid") === t)
    };
    var Te = /^(?:\{[\w\W]*\}|\[[\w\W]*\])$/, Me = /([A-Z])/g;
    oe.extend({
        "cache": {},
        "noData": {"applet ": !0, "embed ": !0, "object ": "clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"},
        "hasData": function (e) {
            return e = e.nodeType ? oe.cache[e[oe.expando]] : e[oe.expando], !!e && !l(e)
        },
        "data": function (e, t, n) {
            return c(e, t, n)
        },
        "removeData": function (e, t) {
            return f(e, t)
        },
        "_data": function (e, t, n) {
            return c(e, t, n, !0)
        },
        "_removeData": function (e, t) {
            return f(e, t, !0)
        }
    }), oe.fn.extend({
        "data": function (e, t) {
            var n, r, i, o = this[0], a = o && o.attributes;
            if (void 0 === e) {
                if (this.length && (i = oe.data(o), 1 === o.nodeType && !oe._data(o, "parsedAttrs"))) {
                    for (n = a.length; n--;)r = a[n].name, 0 === r.indexOf("data-") && (r = oe.camelCase(r.slice(5)), u(o, r, i[r]));
                    oe._data(o, "parsedAttrs", !0)
                }
                return i
            }
            return "object" == typeof e ? this.each(function () {
                oe.data(this, e)
            }) : arguments.length > 1 ? this.each(function () {
                oe.data(this, e, t)
            }) : o ? u(o, e, oe.data(o, e)) : void 0
        }, "removeData": function (e) {
            return this.each(function () {
                oe.removeData(this, e)
            })
        }
    }), oe.extend({
        "queue": function (e, t, n) {
            var r;
            if (e)return t = (t || "fx") + "queue", r = oe._data(e, t), n && (!r || oe.isArray(n) ? r = oe._data(e, t, oe.makeArray(n)) : r.push(n)), r || []
        }, "dequeue": function (e, t) {
            t = t || "fx";
            var n = oe.queue(e, t), r = n.length, i = n.shift(), o = oe._queueHooks(e, t), a = function () {
                oe.dequeue(e, t)
            };
            "inprogress" === i && (i = n.shift(), r--), i && ("fx" === t && n.unshift("inprogress"), delete o.stop, i.call(e, a, o)), !r && o && o.empty.fire()
        }, "_queueHooks": function (e, t) {
            var n = t + "queueHooks";
            return oe._data(e, n) || oe._data(e, n, {
                    "empty": oe.Callbacks("once memory").add(function () {
                        oe._removeData(e, t + "queue"), oe._removeData(e, n)
                    })
                })
        }
    }), oe.fn.extend({
        "queue": function (e, t) {
            var n = 2;
            return "string" != typeof e && (t = e, e = "fx", n--), arguments.length < n ? oe.queue(this[0], e) : void 0 === t ? this : this.each(function () {
                var n = oe.queue(this, e, t);
                oe._queueHooks(this, e), "fx" === e && "inprogress" !== n[0] && oe.dequeue(this, e)
            })
        }, "dequeue": function (e) {
            return this.each(function () {
                oe.dequeue(this, e)
            })
        }, "clearQueue": function (e) {
            return this.queue(e || "fx", [])
        }, "promise": function (e, t) {
            var n, r = 1, i = oe.Deferred(), o = this, a = this.length, s = function () {
                --r || i.resolveWith(o, [o])
            };
            for ("string" != typeof e && (t = e, e = void 0), e = e || "fx"; a--;)n = oe._data(o[a], e + "queueHooks"), n && n.empty && (r++, n.empty.add(s));
            return s(), i.promise(t)
        }
    });
    var De = /[+-]?(?:\d*\.|)\d+(?:[eE][+-]?\d+|)/.source, Ce = ["Top", "Right", "Bottom", "Left"], Ee = function (e, t) {
        return e = t || e, "none" === oe.css(e, "display") || !oe.contains(e.ownerDocument, e)
    }, Ae = oe.access = function (e, t, n, r, i, o, a) {
        var s = 0, u = e.length, l = null == n;
        if ("object" === oe.type(n)) {
            i = !0;
            for (s in n)oe.access(e, t, s, n[s], !0, o, a)
        } else if (void 0 !== r && (i = !0, oe.isFunction(r) || (a = !0), l && (a ? (t.call(e, r), t = null) : (l = t, t = function (e, t, n) {
                return l.call(oe(e), n)
            })), t))for (; s < u; s++)t(e[s], n, a ? r : r.call(e[s], s, t(e[s], n)));
        return i ? e : l ? t.call(e) : u ? t(e[0], n) : o
    }, Oe = /^(?:checkbox|radio)$/i;
    !function () {
        var e = me.createDocumentFragment(), t = me.createElement("div"), n = me.createElement("input");
        if (t.setAttribute("className", "t"), t.innerHTML = "  <link/><table></table><a href='/a'>a</a>", re.leadingWhitespace = 3 === t.firstChild.nodeType, re.tbody = !t.getElementsByTagName("tbody").length, re.htmlSerialize = !!t.getElementsByTagName("link").length, re.html5Clone = "<:nav></:nav>" !== me.createElement("nav").cloneNode(!0).outerHTML, n.type = "checkbox", n.checked = !0, e.appendChild(n), re.appendChecked = n.checked, t.innerHTML = "<textarea>x</textarea>", re.noCloneChecked = !!t.cloneNode(!0).lastChild.defaultValue, e.appendChild(t), t.innerHTML = "<input type='radio' checked='checked' name='t'/>", re.checkClone = t.cloneNode(!0).cloneNode(!0).lastChild.checked, re.noCloneEvent = !0, t.attachEvent && (t.attachEvent("onclick", function () {
                re.noCloneEvent = !1
            }), t.cloneNode(!0).click()), null == re.deleteExpando) {
            re.deleteExpando = !0;
            try {
                delete t.test
            } catch (r) {
                re.deleteExpando = !1
            }
        }
        e = t = n = null
    }(), function () {
        var t, n, r = me.createElement("div");
        for (t in{
            "submit": !0,
            "change": !0,
            "focusin": !0
        })n = "on" + t, (re[t + "Bubbles"] = n in e) || (r.setAttribute(n, "t"), re[t + "Bubbles"] = r.attributes[n].expando === !1);
        r = null
    }();
    var Ne = /^(?:input|select|textarea)$/i, je = /^key/, Ye = /^(?:mouse|contextmenu)|click/, Le = /^(?:focusinfocus|focusoutblur)$/, He = /^([^.]*)(?:\.(.+)|)$/;
    oe.event = {
        "global": {},
        "add": function (e, t, n, r, i) {
            var o, a, s, u, l, c, f, d, h, p, m, g = oe._data(e);
            if (g) {
                for (n.handler && (u = n, n = u.handler, i = u.selector), n.guid || (n.guid = oe.guid++), (a = g.events) || (a = g.events = {}), (c = g.handle) || (c = g.handle = function (e) {
                    return typeof oe === Se || e && oe.event.triggered === e.type ? void 0 : oe.event.dispatch.apply(c.elem, arguments)
                }, c.elem = e), t = (t || "").match(be) || [""], s = t.length; s--;)o = He.exec(t[s]) || [], h = m = o[1], p = (o[2] || "").split(".").sort(), h && (l = oe.event.special[h] || {}, h = (i ? l.delegateType : l.bindType) || h, l = oe.event.special[h] || {}, f = oe.extend({
                    "type": h,
                    "origType": m,
                    "data": r,
                    "handler": n,
                    "guid": n.guid,
                    "selector": i,
                    "needsContext": i && oe.expr.match.needsContext.test(i),
                    "namespace": p.join(".")
                }, u), (d = a[h]) || (d = a[h] = [], d.delegateCount = 0, l.setup && l.setup.call(e, r, p, c) !== !1 || (e.addEventListener ? e.addEventListener(h, c, !1) : e.attachEvent && e.attachEvent("on" + h, c))), l.add && (l.add.call(e, f), f.handler.guid || (f.handler.guid = n.guid)), i ? d.splice(d.delegateCount++, 0, f) : d.push(f), oe.event.global[h] = !0);
                e = null
            }
        },
        "remove": function (e, t, n, r, i) {
            var o, a, s, u, l, c, f, d, h, p, m, g = oe.hasData(e) && oe._data(e);
            if (g && (c = g.events)) {
                for (t = (t || "").match(be) || [""], l = t.length; l--;)if (s = He.exec(t[l]) || [], h = m = s[1], p = (s[2] || "").split(".").sort(), h) {
                    for (f = oe.event.special[h] || {}, h = (r ? f.delegateType : f.bindType) || h, d = c[h] || [], s = s[2] && new RegExp("(^|\\.)" + p.join("\\.(?:.*\\.|)") + "(\\.|$)"), u = o = d.length; o--;)a = d[o], !i && m !== a.origType || n && n.guid !== a.guid || s && !s.test(a.namespace) || r && r !== a.selector && ("**" !== r || !a.selector) || (d.splice(o, 1), a.selector && d.delegateCount--, f.remove && f.remove.call(e, a));
                    u && !d.length && (f.teardown && f.teardown.call(e, p, g.handle) !== !1 || oe.removeEvent(e, h, g.handle), delete c[h])
                } else for (h in c)oe.event.remove(e, h + t[l], n, r, !0);
                oe.isEmptyObject(c) && (delete g.handle, oe._removeData(e, "events"))
            }
        },
        "trigger": function (t, n, r, i) {
            var o, a, s, u, l, c, f, d = [r || me], h = te.call(t, "type") ? t.type : t, p = te.call(t, "namespace") ? t.namespace.split(".") : [];
            if (s = c = r = r || me, 3 !== r.nodeType && 8 !== r.nodeType && !Le.test(h + oe.event.triggered) && (h.indexOf(".") >= 0 && (p = h.split("."), h = p.shift(), p.sort()), a = h.indexOf(":") < 0 && "on" + h, t = t[oe.expando] ? t : new oe.Event(h, "object" == typeof t && t), t.isTrigger = i ? 2 : 3, t.namespace = p.join("."), t.namespace_re = t.namespace ? new RegExp("(^|\\.)" + p.join("\\.(?:.*\\.|)") + "(\\.|$)") : null, t.result = void 0, t.target || (t.target = r), n = null == n ? [t] : oe.makeArray(n, [t]), l = oe.event.special[h] || {}, i || !l.trigger || l.trigger.apply(r, n) !== !1)) {
                if (!i && !l.noBubble && !oe.isWindow(r)) {
                    for (u = l.delegateType || h, Le.test(u + h) || (s = s.parentNode); s; s = s.parentNode)d.push(s), c = s;
                    c === (r.ownerDocument || me) && d.push(c.defaultView || c.parentWindow || e)
                }
                for (f = 0; (s = d[f++]) && !t.isPropagationStopped();)t.type = f > 1 ? u : l.bindType || h, o = (oe._data(s, "events") || {})[t.type] && oe._data(s, "handle"), o && o.apply(s, n), o = a && s[a], o && o.apply && oe.acceptData(s) && (t.result = o.apply(s, n), t.result === !1 && t.preventDefault());
                if (t.type = h, !i && !t.isDefaultPrevented() && (!l._default || l._default.apply(d.pop(), n) === !1) && oe.acceptData(r) && a && r[h] && !oe.isWindow(r)) {
                    c = r[a], c && (r[a] = null), oe.event.triggered = h;
                    try {
                        r[h]()
                    } catch (m) {
                    }
                    oe.event.triggered = void 0, c && (r[a] = c)
                }
                return t.result
            }
        },
        "dispatch": function (e) {
            e = oe.event.fix(e);
            var t, n, r, i, o, a = [], s = Q.call(arguments), u = (oe._data(this, "events") || {})[e.type] || [], l = oe.event.special[e.type] || {};
            if (s[0] = e, e.delegateTarget = this, !l.preDispatch || l.preDispatch.call(this, e) !== !1) {
                for (a = oe.event.handlers.call(this, e, u), t = 0; (i = a[t++]) && !e.isPropagationStopped();)for (e.currentTarget = i.elem, o = 0; (r = i.handlers[o++]) && !e.isImmediatePropagationStopped();)e.namespace_re && !e.namespace_re.test(r.namespace) || (e.handleObj = r, e.data = r.data, n = ((oe.event.special[r.origType] || {}).handle || r.handler).apply(i.elem, s), void 0 !== n && (e.result = n) === !1 && (e.preventDefault(), e.stopPropagation()));
                return l.postDispatch && l.postDispatch.call(this, e), e.result
            }
        },
        "handlers": function (e, t) {
            var n, r, i, o, a = [], s = t.delegateCount, u = e.target;
            if (s && u.nodeType && (!e.button || "click" !== e.type))for (; u != this; u = u.parentNode || this)if (1 === u.nodeType && (u.disabled !== !0 || "click" !== e.type)) {
                for (i = [], o = 0; o < s; o++)r = t[o], n = r.selector + " ", void 0 === i[n] && (i[n] = r.needsContext ? oe(n, this).index(u) >= 0 : oe.find(n, this, null, [u]).length), i[n] && i.push(r);
                i.length && a.push({"elem": u, "handlers": i})
            }
            return s < t.length && a.push({"elem": this, "handlers": t.slice(s)}), a
        },
        "fix": function (e) {
            if (e[oe.expando])return e;
            var t, n, r, i = e.type, o = e, a = this.fixHooks[i];
            for (a || (this.fixHooks[i] = a = Ye.test(i) ? this.mouseHooks : je.test(i) ? this.keyHooks : {}), r = a.props ? this.props.concat(a.props) : this.props, e = new oe.Event(o), t = r.length; t--;)n = r[t], e[n] = o[n];
            return e.target || (e.target = o.srcElement || me), 3 === e.target.nodeType && (e.target = e.target.parentNode), e.metaKey = !!e.metaKey, a.filter ? a.filter(e, o) : e
        },
        "props": "altKey bubbles cancelable ctrlKey currentTarget eventPhase metaKey relatedTarget shiftKey target timeStamp view which".split(" "),
        "fixHooks": {},
        "keyHooks": {
            "props": "char charCode key keyCode".split(" "), "filter": function (e, t) {
                return null == e.which && (e.which = null != t.charCode ? t.charCode : t.keyCode), e
            }
        },
        "mouseHooks": {
            "props": "button buttons clientX clientY fromElement offsetX offsetY pageX pageY screenX screenY toElement".split(" "),
            "filter": function (e, t) {
                var n, r, i, o = t.button, a = t.fromElement;
                return null == e.pageX && null != t.clientX && (r = e.target.ownerDocument || me, i = r.documentElement, n = r.body, e.pageX = t.clientX + (i && i.scrollLeft || n && n.scrollLeft || 0) - (i && i.clientLeft || n && n.clientLeft || 0), e.pageY = t.clientY + (i && i.scrollTop || n && n.scrollTop || 0) - (i && i.clientTop || n && n.clientTop || 0)), !e.relatedTarget && a && (e.relatedTarget = a === e.target ? t.toElement : a), e.which || void 0 === o || (e.which = 1 & o ? 1 : 2 & o ? 3 : 4 & o ? 2 : 0), e
            }
        },
        "special": {
            "load": {"noBubble": !0}, "focus": {
                "trigger": function () {
                    if (this !== p() && this.focus)try {
                        return this.focus(), !1
                    } catch (e) {
                    }
                }, "delegateType": "focusin"
            }, "blur": {
                "trigger": function () {
                    if (this === p() && this.blur)return this.blur(), !1
                }, "delegateType": "focusout"
            }, "click": {
                "trigger": function () {
                    if (oe.nodeName(this, "input") && "checkbox" === this.type && this.click)return this.click(), !1
                }, "_default": function (e) {
                    return oe.nodeName(e.target, "a")
                }
            }, "beforeunload": {
                "postDispatch": function (e) {
                    void 0 !== e.result && (e.originalEvent.returnValue = e.result)
                }
            }
        },
        "simulate": function (e, t, n, r) {
            var i = oe.extend(new oe.Event, n, {"type": e, "isSimulated": !0, "originalEvent": {}});
            r ? oe.event.trigger(i, null, t) : oe.event.dispatch.call(t, i), i.isDefaultPrevented() && n.preventDefault()
        }
    }, oe.removeEvent = me.removeEventListener ? function (e, t, n) {
        e.removeEventListener && e.removeEventListener(t, n, !1)
    } : function (e, t, n) {
        var r = "on" + t;
        e.detachEvent && (typeof e[r] === Se && (e[r] = null), e.detachEvent(r, n))
    }, oe.Event = function (e, t) {
        return this instanceof oe.Event ? (e && e.type ? (this.originalEvent = e, this.type = e.type, this.isDefaultPrevented = e.defaultPrevented || void 0 === e.defaultPrevented && (e.returnValue === !1 || e.getPreventDefault && e.getPreventDefault()) ? d : h) : this.type = e, t && oe.extend(this, t), this.timeStamp = e && e.timeStamp || oe.now(), void(this[oe.expando] = !0)) : new oe.Event(e, t)
    }, oe.Event.prototype = {
        "isDefaultPrevented": h, "isPropagationStopped": h, "isImmediatePropagationStopped": h,
        "preventDefault": function () {
            var e = this.originalEvent;
            this.isDefaultPrevented = d, e && (e.preventDefault ? e.preventDefault() : e.returnValue = !1)
        }, "stopPropagation": function () {
            var e = this.originalEvent;
            this.isPropagationStopped = d, e && (e.stopPropagation && e.stopPropagation(), e.cancelBubble = !0)
        }, "stopImmediatePropagation": function () {
            this.isImmediatePropagationStopped = d, this.stopPropagation()
        }
    }, oe.each({"mouseenter": "mouseover", "mouseleave": "mouseout"}, function (e, t) {
        oe.event.special[e] = {
            "delegateType": t, "bindType": t, "handle": function (e) {
                var n, r = this, i = e.relatedTarget, o = e.handleObj;
                return i && (i === r || oe.contains(r, i)) || (e.type = o.origType, n = o.handler.apply(this, arguments), e.type = t), n
            }
        }
    }), re.submitBubbles || (oe.event.special.submit = {
        "setup": function () {
            return !oe.nodeName(this, "form") && void oe.event.add(this, "click._submit keypress._submit", function (e) {
                    var t = e.target, n = oe.nodeName(t, "input") || oe.nodeName(t, "button") ? t.form : void 0;
                    n && !oe._data(n, "submitBubbles") && (oe.event.add(n, "submit._submit", function (e) {
                        e._submit_bubble = !0
                    }), oe._data(n, "submitBubbles", !0))
                })
        }, "postDispatch": function (e) {
            e._submit_bubble && (delete e._submit_bubble, this.parentNode && !e.isTrigger && oe.event.simulate("submit", this.parentNode, e, !0))
        }, "teardown": function () {
            return !oe.nodeName(this, "form") && void oe.event.remove(this, "._submit")
        }
    }), re.changeBubbles || (oe.event.special.change = {
        "setup": function () {
            return Ne.test(this.nodeName) ? ("checkbox" !== this.type && "radio" !== this.type || (oe.event.add(this, "propertychange._change", function (e) {
                "checked" === e.originalEvent.propertyName && (this._just_changed = !0)
            }), oe.event.add(this, "click._change", function (e) {
                this._just_changed && !e.isTrigger && (this._just_changed = !1), oe.event.simulate("change", this, e, !0)
            })), !1) : void oe.event.add(this, "beforeactivate._change", function (e) {
                var t = e.target;
                Ne.test(t.nodeName) && !oe._data(t, "changeBubbles") && (oe.event.add(t, "change._change", function (e) {
                    !this.parentNode || e.isSimulated || e.isTrigger || oe.event.simulate("change", this.parentNode, e, !0)
                }), oe._data(t, "changeBubbles", !0))
            })
        }, "handle": function (e) {
            var t = e.target;
            if (this !== t || e.isSimulated || e.isTrigger || "radio" !== t.type && "checkbox" !== t.type)return e.handleObj.handler.apply(this, arguments)
        }, "teardown": function () {
            return oe.event.remove(this, "._change"), !Ne.test(this.nodeName)
        }
    }), re.focusinBubbles || oe.each({"focus": "focusin", "blur": "focusout"}, function (e, t) {
        var n = function (e) {
            oe.event.simulate(t, e.target, oe.event.fix(e), !0)
        };
        oe.event.special[t] = {
            "setup": function () {
                var r = this.ownerDocument || this, i = oe._data(r, t);
                i || r.addEventListener(e, n, !0), oe._data(r, t, (i || 0) + 1)
            }, "teardown": function () {
                var r = this.ownerDocument || this, i = oe._data(r, t) - 1;
                i ? oe._data(r, t, i) : (r.removeEventListener(e, n, !0), oe._removeData(r, t))
            }
        }
    }), oe.fn.extend({
        "on": function (e, t, n, r, i) {
            var o, a;
            if ("object" == typeof e) {
                "string" != typeof t && (n = n || t, t = void 0);
                for (o in e)this.on(o, t, n, e[o], i);
                return this
            }
            if (null == n && null == r ? (r = t, n = t = void 0) : null == r && ("string" == typeof t ? (r = n, n = void 0) : (r = n, n = t, t = void 0)), r === !1)r = h; else if (!r)return this;
            return 1 === i && (a = r, r = function (e) {
                return oe().off(e), a.apply(this, arguments)
            }, r.guid = a.guid || (a.guid = oe.guid++)), this.each(function () {
                oe.event.add(this, e, r, n, t)
            })
        }, "one": function (e, t, n, r) {
            return this.on(e, t, n, r, 1)
        }, "off": function (e, t, n) {
            var r, i;
            if (e && e.preventDefault && e.handleObj)return r = e.handleObj, oe(e.delegateTarget).off(r.namespace ? r.origType + "." + r.namespace : r.origType, r.selector, r.handler), this;
            if ("object" == typeof e) {
                for (i in e)this.off(i, t, e[i]);
                return this
            }
            return t !== !1 && "function" != typeof t || (n = t, t = void 0), n === !1 && (n = h), this.each(function () {
                oe.event.remove(this, e, n, t)
            })
        }, "trigger": function (e, t) {
            return this.each(function () {
                oe.event.trigger(e, t, this)
            })
        }, "triggerHandler": function (e, t) {
            var n = this[0];
            if (n)return oe.event.trigger(e, t, n, !0)
        }
    });
    var Pe = "abbr|article|aside|audio|bdi|canvas|data|datalist|details|figcaption|figure|footer|header|hgroup|mark|meter|nav|output|progress|section|summary|time|video", Fe = / jQuery\d+="(?:null|\d+)"/g, Re = new RegExp("<(?:" + Pe + ")[\\s/>]", "i"), We = /^\s+/, Ie = /<(?!area|br|col|embed|hr|img|input|link|meta|param)(([\w:]+)[^>]*)\/>/gi, qe = /<([\w:]+)/, Be = /<tbody/i, $e = /<|&#?\w+;/, Ue = /<(?:script|style|link)/i, ze = /checked\s*(?:[^=]|=\s*.checked.)/i, Ge = /^$|\/(?:java|ecma)script/i, Ve = /^true\/(.*)/, Qe = /^\s*<!(?:\[CDATA\[|--)|(?:\]\]|--)>\s*$/g, Xe = {
        "option": [1, "<select multiple='multiple'>", "</select>"],
        "legend": [1, "<fieldset>", "</fieldset>"],
        "area": [1, "<map>", "</map>"],
        "param": [1, "<object>", "</object>"],
        "thead": [1, "<table>", "</table>"],
        "tr": [2, "<table><tbody>", "</tbody></table>"],
        "col": [2, "<table><tbody></tbody><colgroup>", "</colgroup></table>"],
        "td": [3, "<table><tbody><tr>", "</tr></tbody></table>"],
        "_default": re.htmlSerialize ? [0, "", ""] : [1, "X<div>", "</div>"]
    }, Ze = m(me), Je = Ze.appendChild(me.createElement("div"));
    Xe.optgroup = Xe.option, Xe.tbody = Xe.tfoot = Xe.colgroup = Xe.caption = Xe.thead, Xe.th = Xe.td, oe.extend({
        "clone": function (e, t, n) {
            var r, i, o, a, s, u = oe.contains(e.ownerDocument, e);
            if (re.html5Clone || oe.isXMLDoc(e) || !Re.test("<" + e.nodeName + ">") ? o = e.cloneNode(!0) : (Je.innerHTML = e.outerHTML, Je.removeChild(o = Je.firstChild)), !(re.noCloneEvent && re.noCloneChecked || 1 !== e.nodeType && 11 !== e.nodeType || oe.isXMLDoc(e)))for (r = g(o), s = g(e), a = 0; null != (i = s[a]); ++a)r[a] && k(i, r[a]);
            if (t)if (n)for (s = s || g(e), r = r || g(o), a = 0; null != (i = s[a]); a++)_(i, r[a]); else _(e, o);
            return r = g(o, "script"), r.length > 0 && x(r, !u && g(e, "script")), r = s = i = null, o
        }, "buildFragment": function (e, t, n, r) {
            for (var i, o, a, s, u, l, c, f = e.length, d = m(t), h = [], p = 0; p < f; p++)if (o = e[p], o || 0 === o)if ("object" === oe.type(o))oe.merge(h, o.nodeType ? [o] : o); else if ($e.test(o)) {
                for (s = s || d.appendChild(t.createElement("div")), u = (qe.exec(o) || ["", ""])[1].toLowerCase(), c = Xe[u] || Xe._default, s.innerHTML = c[1] + o.replace(Ie, "<$1></$2>") + c[2], i = c[0]; i--;)s = s.lastChild;
                if (!re.leadingWhitespace && We.test(o) && h.push(t.createTextNode(We.exec(o)[0])), !re.tbody)for (o = "table" !== u || Be.test(o) ? "<table>" !== c[1] || Be.test(o) ? 0 : s : s.firstChild, i = o && o.childNodes.length; i--;)oe.nodeName(l = o.childNodes[i], "tbody") && !l.childNodes.length && o.removeChild(l);
                for (oe.merge(h, s.childNodes), s.textContent = ""; s.firstChild;)s.removeChild(s.firstChild);
                s = d.lastChild
            } else h.push(t.createTextNode(o));
            for (s && d.removeChild(s), re.appendChecked || oe.grep(g(h, "input"), y), p = 0; o = h[p++];)if ((!r || oe.inArray(o, r) === -1) && (a = oe.contains(o.ownerDocument, o), s = g(d.appendChild(o), "script"), a && x(s), n))for (i = 0; o = s[i++];)Ge.test(o.type || "") && n.push(o);
            return s = null, d
        }, "cleanData": function (e, t) {
            for (var n, r, i, o, a = 0, s = oe.expando, u = oe.cache, l = re.deleteExpando, c = oe.event.special; null != (n = e[a]); a++)if ((t || oe.acceptData(n)) && (i = n[s], o = i && u[i])) {
                if (o.events)for (r in o.events)c[r] ? oe.event.remove(n, r) : oe.removeEvent(n, r, o.handle);
                u[i] && (delete u[i], l ? delete n[s] : typeof n.removeAttribute !== Se ? n.removeAttribute(s) : n[s] = null, V.push(i))
            }
        }
    }), oe.fn.extend({
        "text": function (e) {
            return Ae(this, function (e) {
                return void 0 === e ? oe.text(this) : this.empty().append((this[0] && this[0].ownerDocument || me).createTextNode(e))
            }, null, e, arguments.length)
        }, "append": function () {
            return this.domManip(arguments, function (e) {
                if (1 === this.nodeType || 11 === this.nodeType || 9 === this.nodeType) {
                    var t = v(this, e);
                    t.appendChild(e)
                }
            })
        }, "prepend": function () {
            return this.domManip(arguments, function (e) {
                if (1 === this.nodeType || 11 === this.nodeType || 9 === this.nodeType) {
                    var t = v(this, e);
                    t.insertBefore(e, t.firstChild)
                }
            })
        }, "before": function () {
            return this.domManip(arguments, function (e) {
                this.parentNode && this.parentNode.insertBefore(e, this)
            })
        }, "after": function () {
            return this.domManip(arguments, function (e) {
                this.parentNode && this.parentNode.insertBefore(e, this.nextSibling)
            })
        }, "remove": function (e, t) {
            for (var n, r = e ? oe.filter(e, this) : this, i = 0; null != (n = r[i]); i++)t || 1 !== n.nodeType || oe.cleanData(g(n)), n.parentNode && (t && oe.contains(n.ownerDocument, n) && x(g(n, "script")), n.parentNode.removeChild(n));
            return this
        }, "empty": function () {
            for (var e, t = 0; null != (e = this[t]); t++) {
                for (1 === e.nodeType && oe.cleanData(g(e, !1)); e.firstChild;)e.removeChild(e.firstChild);
                e.options && oe.nodeName(e, "select") && (e.options.length = 0)
            }
            return this
        }, "clone": function (e, t) {
            return e = null != e && e, t = null == t ? e : t, this.map(function () {
                return oe.clone(this, e, t)
            })
        }, "html": function (e) {
            return Ae(this, function (e) {
                var t = this[0] || {}, n = 0, r = this.length;
                if (void 0 === e)return 1 === t.nodeType ? t.innerHTML.replace(Fe, "") : void 0;
                if ("string" == typeof e && !Ue.test(e) && (re.htmlSerialize || !Re.test(e)) && (re.leadingWhitespace || !We.test(e)) && !Xe[(qe.exec(e) || ["", ""])[1].toLowerCase()]) {
                    e = e.replace(Ie, "<$1></$2>");
                    try {
                        for (; n < r; n++)t = this[n] || {}, 1 === t.nodeType && (oe.cleanData(g(t, !1)), t.innerHTML = e);
                        t = 0
                    } catch (i) {
                    }
                }
                t && this.empty().append(e)
            }, null, e, arguments.length)
        }, "replaceWith": function () {
            var e = arguments[0];
            return this.domManip(arguments, function (t) {
                e = this.parentNode, oe.cleanData(g(this)), e && e.replaceChild(t, this)
            }), e && (e.length || e.nodeType) ? this : this.remove()
        }, "detach": function (e) {
            return this.remove(e, !0)
        }, "domManip": function (e, t) {
            e = X.apply([], e);
            var n, r, i, o, a, s, u = 0, l = this.length, c = this, f = l - 1, d = e[0], h = oe.isFunction(d);
            if (h || l > 1 && "string" == typeof d && !re.checkClone && ze.test(d))return this.each(function (n) {
                var r = c.eq(n);
                h && (e[0] = d.call(this, n, r.html())), r.domManip(e, t)
            });
            if (l && (s = oe.buildFragment(e, this[0].ownerDocument, !1, this), n = s.firstChild, 1 === s.childNodes.length && (s = n), n)) {
                for (o = oe.map(g(s, "script"), w), i = o.length; u < l; u++)r = s, u !== f && (r = oe.clone(r, !0, !0), i && oe.merge(o, g(r, "script"))), t.call(this[u], r, u);
                if (i)for (a = o[o.length - 1].ownerDocument, oe.map(o, b), u = 0; u < i; u++)r = o[u], Ge.test(r.type || "") && !oe._data(r, "globalEval") && oe.contains(a, r) && (r.src ? oe._evalUrl && oe._evalUrl(r.src) : oe.globalEval((r.text || r.textContent || r.innerHTML || "").replace(Qe, "")));
                s = n = null
            }
            return this
        }
    }), oe.each({
        "appendTo": "append",
        "prependTo": "prepend",
        "insertBefore": "before",
        "insertAfter": "after",
        "replaceAll": "replaceWith"
    }, function (e, t) {
        oe.fn[e] = function (e) {
            for (var n, r = 0, i = [], o = oe(e), a = o.length - 1; r <= a; r++)n = r === a ? this : this.clone(!0), oe(o[r])[t](n), Z.apply(i, n.get());
            return this.pushStack(i)
        }
    });
    var Ke, et = {};
    !function () {
        var e, t, n = me.createElement("div"), r = "-webkit-box-sizing:content-box;-moz-box-sizing:content-box;box-sizing:content-box;display:block;padding:0;margin:0;border:0";
        n.innerHTML = "  <link/><table></table><a href='/a'>a</a><input type='checkbox'/>", e = n.getElementsByTagName("a")[0], e.style.cssText = "float:left;opacity:.5", re.opacity = /^0.5/.test(e.style.opacity), re.cssFloat = !!e.style.cssFloat, n.style.backgroundClip = "content-box", n.cloneNode(!0).style.backgroundClip = "", re.clearCloneStyle = "content-box" === n.style.backgroundClip, e = n = null, re.shrinkWrapBlocks = function () {
            var e, n, i, o;
            if (null == t) {
                if (e = me.getElementsByTagName("body")[0], !e)return;
                o = "border:0;width:0;height:0;position:absolute;top:0;left:-9999px", n = me.createElement("div"), i = me.createElement("div"), e.appendChild(n).appendChild(i), t = !1, typeof i.style.zoom !== Se && (i.style.cssText = r + ";width:1px;padding:1px;zoom:1", i.innerHTML = "<div></div>", i.firstChild.style.width = "5px", t = 3 !== i.offsetWidth), e.removeChild(n), e = n = i = null
            }
            return t
        }
    }();
    var tt, nt, rt = /^margin/, it = new RegExp("^(" + De + ")(?!px)[a-z%]+$", "i"), ot = /^(top|right|bottom|left)$/;
    e.getComputedStyle ? (tt = function (e) {
        return e.ownerDocument.defaultView.getComputedStyle(e, null)
    }, nt = function (e, t, n) {
        var r, i, o, a, s = e.style;
        return n = n || tt(e), a = n ? n.getPropertyValue(t) || n[t] : void 0, n && ("" !== a || oe.contains(e.ownerDocument, e) || (a = oe.style(e, t)), it.test(a) && rt.test(t) && (r = s.width, i = s.minWidth, o = s.maxWidth, s.minWidth = s.maxWidth = s.width = a, a = n.width, s.width = r, s.minWidth = i, s.maxWidth = o)), void 0 === a ? a : a + ""
    }) : me.documentElement.currentStyle && (tt = function (e) {
        return e.currentStyle
    }, nt = function (e, t, n) {
        var r, i, o, a, s = e.style;
        return n = n || tt(e), a = n ? n[t] : void 0, null == a && s && s[t] && (a = s[t]), it.test(a) && !ot.test(t) && (r = s.left, i = e.runtimeStyle, o = i && i.left, o && (i.left = e.currentStyle.left), s.left = "fontSize" === t ? "1em" : a, a = s.pixelLeft + "px", s.left = r, o && (i.left = o)), void 0 === a ? a : a + "" || "auto"
    }), function () {
        function t() {
            var t, n, r = me.getElementsByTagName("body")[0];
            r && (t = me.createElement("div"), n = me.createElement("div"), t.style.cssText = l, r.appendChild(t).appendChild(n), n.style.cssText = "-webkit-box-sizing:border-box;-moz-box-sizing:border-box;box-sizing:border-box;position:absolute;display:block;padding:1px;border:1px;width:4px;margin-top:1%;top:1%", oe.swap(r, null != r.style.zoom ? {"zoom": 1} : {}, function () {
                i = 4 === n.offsetWidth
            }), o = !0, a = !1, s = !0, e.getComputedStyle && (a = "1%" !== (e.getComputedStyle(n, null) || {}).top, o = "4px" === (e.getComputedStyle(n, null) || {"width": "4px"}).width), r.removeChild(t), n = r = null)
        }

        var n, r, i, o, a, s, u = me.createElement("div"), l = "border:0;width:0;height:0;position:absolute;top:0;left:-9999px", c = "-webkit-box-sizing:content-box;-moz-box-sizing:content-box;box-sizing:content-box;display:block;padding:0;margin:0;border:0";
        u.innerHTML = "  <link/><table></table><a href='/a'>a</a><input type='checkbox'/>", n = u.getElementsByTagName("a")[0], n.style.cssText = "float:left;opacity:.5", re.opacity = /^0.5/.test(n.style.opacity), re.cssFloat = !!n.style.cssFloat, u.style.backgroundClip = "content-box", u.cloneNode(!0).style.backgroundClip = "", re.clearCloneStyle = "content-box" === u.style.backgroundClip, n = u = null, oe.extend(re, {
            "reliableHiddenOffsets": function () {
                if (null != r)return r;
                var e, t, n, i = me.createElement("div"), o = me.getElementsByTagName("body")[0];
                if (o)return i.setAttribute("className", "t"), i.innerHTML = "  <link/><table></table><a href='/a'>a</a><input type='checkbox'/>", e = me.createElement("div"), e.style.cssText = l, o.appendChild(e).appendChild(i), i.innerHTML = "<table><tr><td></td><td>t</td></tr></table>", t = i.getElementsByTagName("td"), t[0].style.cssText = "padding:0;margin:0;border:0;display:none", n = 0 === t[0].offsetHeight, t[0].style.display = "", t[1].style.display = "none", r = n && 0 === t[0].offsetHeight, o.removeChild(e), i = o = null, r
            }, "boxSizing": function () {
                return null == i && t(), i
            }, "boxSizingReliable": function () {
                return null == o && t(), o
            }, "pixelPosition": function () {
                return null == a && t(), a
            }, "reliableMarginRight": function () {
                var t, n, r, i;
                if (null == s && e.getComputedStyle) {
                    if (t = me.getElementsByTagName("body")[0], !t)return;
                    n = me.createElement("div"), r = me.createElement("div"), n.style.cssText = l, t.appendChild(n).appendChild(r), i = r.appendChild(me.createElement("div")), i.style.cssText = r.style.cssText = c, i.style.marginRight = i.style.width = "0", r.style.width = "1px", s = !parseFloat((e.getComputedStyle(i, null) || {}).marginRight), t.removeChild(n)
                }
                return s
            }
        })
    }(), oe.swap = function (e, t, n, r) {
        var i, o, a = {};
        for (o in t)a[o] = e.style[o], e.style[o] = t[o];
        i = n.apply(e, r || []);
        for (o in t)e.style[o] = a[o];
        return i
    };
    var at = /alpha\([^)]*\)/i, st = /opacity\s*=\s*([^)]*)/, ut = /^(none|table(?!-c[ea]).+)/, lt = new RegExp("^(" + De + ")(.*)$", "i"), ct = new RegExp("^([+-])=(" + De + ")", "i"), ft = {
        "position": "absolute",
        "visibility": "hidden",
        "display": "block"
    }, dt = {"letterSpacing": 0, "fontWeight": 400}, ht = ["Webkit", "O", "Moz", "ms"];
    oe.extend({
        "cssHooks": {
            "opacity": {
                "get": function (e, t) {
                    if (t) {
                        var n = nt(e, "opacity");
                        return "" === n ? "1" : n
                    }
                }
            }
        },
        "cssNumber": {
            "columnCount": !0,
            "fillOpacity": !0,
            "fontWeight": !0,
            "lineHeight": !0,
            "opacity": !0,
            "order": !0,
            "orphans": !0,
            "widows": !0,
            "zIndex": !0,
            "zoom": !0
        },
        "cssProps": {"float": re.cssFloat ? "cssFloat" : "styleFloat"},
        "style": function (e, t, n, r) {
            if (e && 3 !== e.nodeType && 8 !== e.nodeType && e.style) {
                var i, o, a, s = oe.camelCase(t), u = e.style;
                if (t = oe.cssProps[s] || (oe.cssProps[s] = D(u, s)), a = oe.cssHooks[t] || oe.cssHooks[s], void 0 === n)return a && "get" in a && void 0 !== (i = a.get(e, !1, r)) ? i : u[t];
                if (o = typeof n, "string" === o && (i = ct.exec(n)) && (n = (i[1] + 1) * i[2] + parseFloat(oe.css(e, t)), o = "number"), null != n && n === n && ("number" !== o || oe.cssNumber[s] || (n += "px"), re.clearCloneStyle || "" !== n || 0 !== t.indexOf("background") || (u[t] = "inherit"), !(a && "set" in a && void 0 === (n = a.set(e, n, r)))))try {
                    u[t] = "", u[t] = n
                } catch (l) {
                }
            }
        },
        "css": function (e, t, n, r) {
            var i, o, a, s = oe.camelCase(t);
            return t = oe.cssProps[s] || (oe.cssProps[s] = D(e.style, s)), a = oe.cssHooks[t] || oe.cssHooks[s], a && "get" in a && (o = a.get(e, !0, n)), void 0 === o && (o = nt(e, t, r)), "normal" === o && t in dt && (o = dt[t]), "" === n || n ? (i = parseFloat(o), n === !0 || oe.isNumeric(i) ? i || 0 : o) : o
        }
    }), oe.each(["height", "width"], function (e, t) {
        oe.cssHooks[t] = {
            "get": function (e, n, r) {
                if (n)return 0 === e.offsetWidth && ut.test(oe.css(e, "display")) ? oe.swap(e, ft, function () {
                    return O(e, t, r)
                }) : O(e, t, r)
            }, "set": function (e, n, r) {
                var i = r && tt(e);
                return E(e, n, r ? A(e, t, r, re.boxSizing() && "border-box" === oe.css(e, "boxSizing", !1, i), i) : 0)
            }
        }
    }), re.opacity || (oe.cssHooks.opacity = {
        "get": function (e, t) {
            return st.test((t && e.currentStyle ? e.currentStyle.filter : e.style.filter) || "") ? .01 * parseFloat(RegExp.$1) + "" : t ? "1" : ""
        }, "set": function (e, t) {
            var n = e.style, r = e.currentStyle, i = oe.isNumeric(t) ? "alpha(opacity=" + 100 * t + ")" : "", o = r && r.filter || n.filter || "";
            n.zoom = 1, (t >= 1 || "" === t) && "" === oe.trim(o.replace(at, "")) && n.removeAttribute && (n.removeAttribute("filter"), "" === t || r && !r.filter) || (n.filter = at.test(o) ? o.replace(at, i) : o + " " + i)
        }
    }), oe.cssHooks.marginRight = M(re.reliableMarginRight, function (e, t) {
        if (t)return oe.swap(e, {"display": "inline-block"}, nt, [e, "marginRight"])
    }), oe.each({"margin": "", "padding": "", "border": "Width"}, function (e, t) {
        oe.cssHooks[e + t] = {
            "expand": function (n) {
                for (var r = 0, i = {}, o = "string" == typeof n ? n.split(" ") : [n]; r < 4; r++)i[e + Ce[r] + t] = o[r] || o[r - 2] || o[0];
                return i
            }
        }, rt.test(e) || (oe.cssHooks[e + t].set = E)
    }), oe.fn.extend({
        "css": function (e, t) {
            return Ae(this, function (e, t, n) {
                var r, i, o = {}, a = 0;
                if (oe.isArray(t)) {
                    for (r = tt(e), i = t.length; a < i; a++)o[t[a]] = oe.css(e, t[a], !1, r);
                    return o
                }
                return void 0 !== n ? oe.style(e, t, n) : oe.css(e, t)
            }, e, t, arguments.length > 1)
        }, "show": function () {
            return C(this, !0)
        }, "hide": function () {
            return C(this)
        }, "toggle": function (e) {
            return "boolean" == typeof e ? e ? this.show() : this.hide() : this.each(function () {
                Ee(this) ? oe(this).show() : oe(this).hide()
            })
        }
    }), oe.Tween = N, N.prototype = {
        "constructor": N, "init": function (e, t, n, r, i, o) {
            this.elem = e, this.prop = n, this.easing = i || "swing", this.options = t, this.start = this.now = this.cur(), this.end = r, this.unit = o || (oe.cssNumber[n] ? "" : "px")
        }, "cur": function () {
            var e = N.propHooks[this.prop];
            return e && e.get ? e.get(this) : N.propHooks._default.get(this)
        }, "run": function (e) {
            var t, n = N.propHooks[this.prop];
            return this.options.duration ? this.pos = t = oe.easing[this.easing](e, this.options.duration * e, 0, 1, this.options.duration) : this.pos = t = e, this.now = (this.end - this.start) * t + this.start, this.options.step && this.options.step.call(this.elem, this.now, this), n && n.set ? n.set(this) : N.propHooks._default.set(this), this
        }
    }, N.prototype.init.prototype = N.prototype, N.propHooks = {
        "_default": {
            "get": function (e) {
                var t;
                return null == e.elem[e.prop] || e.elem.style && null != e.elem.style[e.prop] ? (t = oe.css(e.elem, e.prop, ""), t && "auto" !== t ? t : 0) : e.elem[e.prop]
            }, "set": function (e) {
                oe.fx.step[e.prop] ? oe.fx.step[e.prop](e) : e.elem.style && (null != e.elem.style[oe.cssProps[e.prop]] || oe.cssHooks[e.prop]) ? oe.style(e.elem, e.prop, e.now + e.unit) : e.elem[e.prop] = e.now
            }
        }
    }, N.propHooks.scrollTop = N.propHooks.scrollLeft = {
        "set": function (e) {
            e.elem.nodeType && e.elem.parentNode && (e.elem[e.prop] = e.now)
        }
    }, oe.easing = {
        "linear": function (e) {
            return e
        }, "swing": function (e) {
            return .5 - Math.cos(e * Math.PI) / 2
        }
    }, oe.fx = N.prototype.init, oe.fx.step = {};
    var pt, mt, gt = /^(?:toggle|show|hide)$/, yt = new RegExp("^(?:([+-])=|)(" + De + ")([a-z%]*)$", "i"), vt = /queueHooks$/, wt = [H], bt = {
        "*": [function (e, t) {
            var n = this.createTween(e, t), r = n.cur(), i = yt.exec(t), o = i && i[3] || (oe.cssNumber[e] ? "" : "px"), a = (oe.cssNumber[e] || "px" !== o && +r) && yt.exec(oe.css(n.elem, e)), s = 1, u = 20;
            if (a && a[3] !== o) {
                o = o || a[3], i = i || [], a = +r || 1;
                do s = s || ".5", a /= s, oe.style(n.elem, e, a + o); while (s !== (s = n.cur() / r) && 1 !== s && --u)
            }
            return i && (a = n.start = +a || +r || 0, n.unit = o, n.end = i[1] ? a + (i[1] + 1) * i[2] : +i[2]), n
        }]
    };
    oe.Animation = oe.extend(F, {
        "tweener": function (e, t) {
            oe.isFunction(e) ? (t = e, e = ["*"]) : e = e.split(" ");
            for (var n, r = 0, i = e.length; r < i; r++)n = e[r], bt[n] = bt[n] || [], bt[n].unshift(t)
        }, "prefilter": function (e, t) {
            t ? wt.unshift(e) : wt.push(e)
        }
    }), oe.speed = function (e, t, n) {
        var r = e && "object" == typeof e ? oe.extend({}, e) : {
            "complete": n || !n && t || oe.isFunction(e) && e,
            "duration": e,
            "easing": n && t || t && !oe.isFunction(t) && t
        };
        return r.duration = oe.fx.off ? 0 : "number" == typeof r.duration ? r.duration : r.duration in oe.fx.speeds ? oe.fx.speeds[r.duration] : oe.fx.speeds._default, null != r.queue && r.queue !== !0 || (r.queue = "fx"), r.old = r.complete, r.complete = function () {
            oe.isFunction(r.old) && r.old.call(this), r.queue && oe.dequeue(this, r.queue)
        }, r
    }, oe.fn.extend({
        "fadeTo": function (e, t, n, r) {
            return this.filter(Ee).css("opacity", 0).show().end().animate({"opacity": t}, e, n, r)
        }, "animate": function (e, t, n, r) {
            var i = oe.isEmptyObject(e), o = oe.speed(t, n, r), a = function () {
                var t = F(this, oe.extend({}, e), o);
                (i || oe._data(this, "finish")) && t.stop(!0)
            };
            return a.finish = a, i || o.queue === !1 ? this.each(a) : this.queue(o.queue, a)
        }, "stop": function (e, t, n) {
            var r = function (e) {
                var t = e.stop;
                delete e.stop, t(n)
            };
            return "string" != typeof e && (n = t, t = e, e = void 0), t && e !== !1 && this.queue(e || "fx", []), this.each(function () {
                var t = !0, i = null != e && e + "queueHooks", o = oe.timers, a = oe._data(this);
                if (i)a[i] && a[i].stop && r(a[i]); else for (i in a)a[i] && a[i].stop && vt.test(i) && r(a[i]);
                for (i = o.length; i--;)o[i].elem !== this || null != e && o[i].queue !== e || (o[i].anim.stop(n), t = !1, o.splice(i, 1));
                !t && n || oe.dequeue(this, e)
            })
        }, "finish": function (e) {
            return e !== !1 && (e = e || "fx"), this.each(function () {
                var t, n = oe._data(this), r = n[e + "queue"], i = n[e + "queueHooks"], o = oe.timers, a = r ? r.length : 0;
                for (n.finish = !0, oe.queue(this, e, []), i && i.stop && i.stop.call(this, !0), t = o.length; t--;)o[t].elem === this && o[t].queue === e && (o[t].anim.stop(!0), o.splice(t, 1));
                for (t = 0; t < a; t++)r[t] && r[t].finish && r[t].finish.call(this);
                delete n.finish
            })
        }
    }), oe.each(["toggle", "show", "hide"], function (e, t) {
        var n = oe.fn[t];
        oe.fn[t] = function (e, r, i) {
            return null == e || "boolean" == typeof e ? n.apply(this, arguments) : this.animate(Y(t, !0), e, r, i)
        }
    }), oe.each({
        "slideDown": Y("show"),
        "slideUp": Y("hide"),
        "slideToggle": Y("toggle"),
        "fadeIn": {"opacity": "show"},
        "fadeOut": {"opacity": "hide"},
        "fadeToggle": {"opacity": "toggle"}
    }, function (e, t) {
        oe.fn[e] = function (e, n, r) {
            return this.animate(t, e, n, r)
        }
    }), oe.timers = [], oe.fx.tick = function () {
        var e, t = oe.timers, n = 0;
        for (pt = oe.now(); n < t.length; n++)e = t[n], e() || t[n] !== e || t.splice(n--, 1);
        t.length || oe.fx.stop(), pt = void 0
    }, oe.fx.timer = function (e) {
        oe.timers.push(e), e() ? oe.fx.start() : oe.timers.pop()
    }, oe.fx.interval = 13, oe.fx.start = function () {
        mt || (mt = setInterval(oe.fx.tick, oe.fx.interval))
    }, oe.fx.stop = function () {
        clearInterval(mt), mt = null
    }, oe.fx.speeds = {"slow": 600, "fast": 200, "_default": 400}, oe.fn.delay = function (e, t) {
        return e = oe.fx ? oe.fx.speeds[e] || e : e, t = t || "fx", this.queue(t, function (t, n) {
            var r = setTimeout(t, e);
            n.stop = function () {
                clearTimeout(r)
            }
        })
    }, function () {
        var e, t, n, r, i = me.createElement("div");
        i.setAttribute("className", "t"), i.innerHTML = "  <link/><table></table><a href='/a'>a</a><input type='checkbox'/>", e = i.getElementsByTagName("a")[0], n = me.createElement("select"), r = n.appendChild(me.createElement("option")), t = i.getElementsByTagName("input")[0], e.style.cssText = "top:1px", re.getSetAttribute = "t" !== i.className, re.style = /top/.test(e.getAttribute("style")), re.hrefNormalized = "/a" === e.getAttribute("href"), re.checkOn = !!t.value, re.optSelected = r.selected, re.enctype = !!me.createElement("form").enctype, n.disabled = !0, re.optDisabled = !r.disabled, t = me.createElement("input"), t.setAttribute("value", ""), re.input = "" === t.getAttribute("value"), t.value = "t", t.setAttribute("type", "radio"), re.radioValue = "t" === t.value, e = t = n = r = i = null
    }();
    var xt = /\r/g;
    oe.fn.extend({
        "val": function (e) {
            var t, n, r, i = this[0];
            {
                if (arguments.length)return r = oe.isFunction(e), this.each(function (n) {
                    var i;
                    1 === this.nodeType && (i = r ? e.call(this, n, oe(this).val()) : e, null == i ? i = "" : "number" == typeof i ? i += "" : oe.isArray(i) && (i = oe.map(i, function (e) {
                        return null == e ? "" : e + ""
                    })), t = oe.valHooks[this.type] || oe.valHooks[this.nodeName.toLowerCase()], t && "set" in t && void 0 !== t.set(this, i, "value") || (this.value = i))
                });
                if (i)return t = oe.valHooks[i.type] || oe.valHooks[i.nodeName.toLowerCase()], t && "get" in t && void 0 !== (n = t.get(i, "value")) ? n : (n = i.value, "string" == typeof n ? n.replace(xt, "") : null == n ? "" : n)
            }
        }
    }), oe.extend({
        "valHooks": {
            "option": {
                "get": function (e) {
                    var t = oe.find.attr(e, "value");
                    return null != t ? t : oe.text(e)
                }
            }, "select": {
                "get": function (e) {
                    for (var t, n, r = e.options, i = e.selectedIndex, o = "select-one" === e.type || i < 0, a = o ? null : [], s = o ? i + 1 : r.length, u = i < 0 ? s : o ? i : 0; u < s; u++)if (n = r[u], (n.selected || u === i) && (re.optDisabled ? !n.disabled : null === n.getAttribute("disabled")) && (!n.parentNode.disabled || !oe.nodeName(n.parentNode, "optgroup"))) {
                        if (t = oe(n).val(), o)return t;
                        a.push(t)
                    }
                    return a
                }, "set": function (e, t) {
                    for (var n, r, i = e.options, o = oe.makeArray(t), a = i.length; a--;)if (r = i[a], oe.inArray(oe.valHooks.option.get(r), o) >= 0)try {
                        r.selected = n = !0
                    } catch (s) {
                        r.scrollHeight
                    } else r.selected = !1;
                    return n || (e.selectedIndex = -1), i
                }
            }
        }
    }), oe.each(["radio", "checkbox"], function () {
        oe.valHooks[this] = {
            "set": function (e, t) {
                if (oe.isArray(t))return e.checked = oe.inArray(oe(e).val(), t) >= 0
            }
        }, re.checkOn || (oe.valHooks[this].get = function (e) {
            return null === e.getAttribute("value") ? "on" : e.value
        })
    });
    var _t, kt, St = oe.expr.attrHandle, Tt = /^(?:checked|selected)$/i, Mt = re.getSetAttribute, Dt = re.input;
    oe.fn.extend({
        "attr": function (e, t) {
            return Ae(this, oe.attr, e, t, arguments.length > 1)
        }, "removeAttr": function (e) {
            return this.each(function () {
                oe.removeAttr(this, e)
            })
        }
    }), oe.extend({
        "attr": function (e, t, n) {
            var r, i, o = e.nodeType;
            if (e && 3 !== o && 8 !== o && 2 !== o)return typeof e.getAttribute === Se ? oe.prop(e, t, n) : (1 === o && oe.isXMLDoc(e) || (t = t.toLowerCase(), r = oe.attrHooks[t] || (oe.expr.match.bool.test(t) ? kt : _t)), void 0 === n ? r && "get" in r && null !== (i = r.get(e, t)) ? i : (i = oe.find.attr(e, t), null == i ? void 0 : i) : null !== n ? r && "set" in r && void 0 !== (i = r.set(e, n, t)) ? i : (e.setAttribute(t, n + ""), n) : void oe.removeAttr(e, t))
        }, "removeAttr": function (e, t) {
            var n, r, i = 0, o = t && t.match(be);
            if (o && 1 === e.nodeType)for (; n = o[i++];)r = oe.propFix[n] || n, oe.expr.match.bool.test(n) ? Dt && Mt || !Tt.test(n) ? e[r] = !1 : e[oe.camelCase("default-" + n)] = e[r] = !1 : oe.attr(e, n, ""), e.removeAttribute(Mt ? n : r)
        }, "attrHooks": {
            "type": {
                "set": function (e, t) {
                    if (!re.radioValue && "radio" === t && oe.nodeName(e, "input")) {
                        var n = e.value;
                        return e.setAttribute("type", t), n && (e.value = n), t
                    }
                }
            }
        }
    }), kt = {
        "set": function (e, t, n) {
            return t === !1 ? oe.removeAttr(e, n) : Dt && Mt || !Tt.test(n) ? e.setAttribute(!Mt && oe.propFix[n] || n, n) : e[oe.camelCase("default-" + n)] = e[n] = !0, n
        }
    }, oe.each(oe.expr.match.bool.source.match(/\w+/g), function (e, t) {
        var n = St[t] || oe.find.attr;
        St[t] = Dt && Mt || !Tt.test(t) ? function (e, t, r) {
            var i, o;
            return r || (o = St[t], St[t] = i, i = null != n(e, t, r) ? t.toLowerCase() : null, St[t] = o), i
        } : function (e, t, n) {
            if (!n)return e[oe.camelCase("default-" + t)] ? t.toLowerCase() : null
        }
    }), Dt && Mt || (oe.attrHooks.value = {
        "set": function (e, t, n) {
            return oe.nodeName(e, "input") ? void(e.defaultValue = t) : _t && _t.set(e, t, n)
        }
    }), Mt || (_t = {
        "set": function (e, t, n) {
            var r = e.getAttributeNode(n);
            if (r || e.setAttributeNode(r = e.ownerDocument.createAttribute(n)), r.value = t += "", "value" === n || t === e.getAttribute(n))return t
        }
    }, St.id = St.name = St.coords = function (e, t, n) {
        var r;
        if (!n)return (r = e.getAttributeNode(t)) && "" !== r.value ? r.value : null
    }, oe.valHooks.button = {
        "get": function (e, t) {
            var n = e.getAttributeNode(t);
            if (n && n.specified)return n.value
        }, "set": _t.set
    }, oe.attrHooks.contenteditable = {
        "set": function (e, t, n) {
            _t.set(e, "" !== t && t, n)
        }
    }, oe.each(["width", "height"], function (e, t) {
        oe.attrHooks[t] = {
            "set": function (e, n) {
                if ("" === n)return e.setAttribute(t, "auto"), n
            }
        }
    })), re.style || (oe.attrHooks.style = {
        "get": function (e) {
            return e.style.cssText || void 0
        }, "set": function (e, t) {
            return e.style.cssText = t + ""
        }
    });
    var Ct = /^(?:input|select|textarea|button|object)$/i, Et = /^(?:a|area)$/i;
    oe.fn.extend({
        "prop": function (e, t) {
            return Ae(this, oe.prop, e, t, arguments.length > 1)
        }, "removeProp": function (e) {
            return e = oe.propFix[e] || e, this.each(function () {
                try {
                    this[e] = void 0, delete this[e]
                } catch (t) {
                }
            })
        }
    }), oe.extend({
        "propFix": {"for": "htmlFor", "class": "className"}, "prop": function (e, t, n) {
            var r, i, o, a = e.nodeType;
            if (e && 3 !== a && 8 !== a && 2 !== a)return o = 1 !== a || !oe.isXMLDoc(e), o && (t = oe.propFix[t] || t, i = oe.propHooks[t]), void 0 !== n ? i && "set" in i && void 0 !== (r = i.set(e, n, t)) ? r : e[t] = n : i && "get" in i && null !== (r = i.get(e, t)) ? r : e[t]
        }, "propHooks": {
            "tabIndex": {
                "get": function (e) {
                    var t = oe.find.attr(e, "tabindex");
                    return t ? parseInt(t, 10) : Ct.test(e.nodeName) || Et.test(e.nodeName) && e.href ? 0 : -1
                }
            }
        }
    }), re.hrefNormalized || oe.each(["href", "src"], function (e, t) {
        oe.propHooks[t] = {
            "get": function (e) {
                return e.getAttribute(t, 4)
            }
        }
    }), re.optSelected || (oe.propHooks.selected = {
        "get": function (e) {
            var t = e.parentNode;
            return t && (t.selectedIndex, t.parentNode && t.parentNode.selectedIndex), null
        }
    }), oe.each(["tabIndex", "readOnly", "maxLength", "cellSpacing", "cellPadding", "rowSpan", "colSpan", "useMap", "frameBorder", "contentEditable"], function () {
        oe.propFix[this.toLowerCase()] = this
    }), re.enctype || (oe.propFix.enctype = "encoding");
    var At = /[\t\r\n\f]/g;
    oe.fn.extend({
        "addClass": function (e) {
            var t, n, r, i, o, a, s = 0, u = this.length, l = "string" == typeof e && e;
            if (oe.isFunction(e))return this.each(function (t) {
                oe(this).addClass(e.call(this, t, this.className))
            });
            if (l)for (t = (e || "").match(be) || []; s < u; s++)if (n = this[s], r = 1 === n.nodeType && (n.className ? (" " + n.className + " ").replace(At, " ") : " ")) {
                for (o = 0; i = t[o++];)r.indexOf(" " + i + " ") < 0 && (r += i + " ");
                a = oe.trim(r), n.className !== a && (n.className = a)
            }
            return this
        }, "removeClass": function (e) {
            var t, n, r, i, o, a, s = 0, u = this.length, l = 0 === arguments.length || "string" == typeof e && e;
            if (oe.isFunction(e))return this.each(function (t) {
                oe(this).removeClass(e.call(this, t, this.className))
            });
            if (l)for (t = (e || "").match(be) || []; s < u; s++)if (n = this[s], r = 1 === n.nodeType && (n.className ? (" " + n.className + " ").replace(At, " ") : "")) {
                for (o = 0; i = t[o++];)for (; r.indexOf(" " + i + " ") >= 0;)r = r.replace(" " + i + " ", " ");
                a = e ? oe.trim(r) : "", n.className !== a && (n.className = a)
            }
            return this
        }, "toggleClass": function (e, t) {
            var n = typeof e;
            return "boolean" == typeof t && "string" === n ? t ? this.addClass(e) : this.removeClass(e) : oe.isFunction(e) ? this.each(function (n) {
                oe(this).toggleClass(e.call(this, n, this.className, t), t)
            }) : this.each(function () {
                if ("string" === n)for (var t, r = 0, i = oe(this), o = e.match(be) || []; t = o[r++];)i.hasClass(t) ? i.removeClass(t) : i.addClass(t); else n !== Se && "boolean" !== n || (this.className && oe._data(this, "__className__", this.className), this.className = this.className || e === !1 ? "" : oe._data(this, "__className__") || "")
            })
        }, "hasClass": function (e) {
            for (var t = " " + e + " ", n = 0, r = this.length; n < r; n++)if (1 === this[n].nodeType && (" " + this[n].className + " ").replace(At, " ").indexOf(t) >= 0)return !0;
            return !1
        }
    }), oe.each("blur focus focusin focusout load resize scroll unload click dblclick mousedown mouseup mousemove mouseover mouseout mouseenter mouseleave change select submit keydown keypress keyup error contextmenu".split(" "), function (e, t) {
        oe.fn[t] = function (e, n) {
            return arguments.length > 0 ? this.on(t, null, e, n) : this.trigger(t)
        }
    }), oe.fn.extend({
        "hover": function (e, t) {
            return this.mouseenter(e).mouseleave(t || e)
        }, "bind": function (e, t, n) {
            return this.on(e, null, t, n)
        }, "unbind": function (e, t) {
            return this.off(e, null, t)
        }, "delegate": function (e, t, n, r) {
            return this.on(t, e, n, r)
        }, "undelegate": function (e, t, n) {
            return 1 === arguments.length ? this.off(e, "**") : this.off(t, e || "**", n)
        }
    });
    var Ot = oe.now(), Nt = /\?/, jt = /(,)|(\[|{)|(}|])|"(?:[^"\\\r\n]|\\["\\\/bfnrt]|\\u[\da-fA-F]{4})*"\s*:?|true|false|null|-?(?!0\d)\d+(?:\.\d+|)(?:[eE][+-]?\d+|)/g;
    oe.parseJSON = function (t) {
        if (e.JSON && e.JSON.parse)return e.JSON.parse(t + "");
        var n, r = null, i = oe.trim(t + "");
        return i && !oe.trim(i.replace(jt, function (e, t, i, o) {
            return n && t && (r = 0), 0 === r ? e : (n = i || t, r += !o - !i, "")
        })) ? Function("return " + i)() : oe.error("Invalid JSON: " + t)
    }, oe.parseXML = function (t) {
        var n, r;
        if (!t || "string" != typeof t)return null;
        try {
            e.DOMParser ? (r = new DOMParser, n = r.parseFromString(t, "text/xml")) : (n = new ActiveXObject("Microsoft.XMLDOM"), n.async = "false", n.loadXML(t))
        } catch (i) {
            n = void 0
        }
        return n && n.documentElement && !n.getElementsByTagName("parsererror").length || oe.error("Invalid XML: " + t), n
    };
    var Yt, Lt, Ht = /#.*$/, Pt = /([?&])_=[^&]*/, Ft = /^(.*?):[ \t]*([^\r\n]*)\r?$/gm, Rt = /^(?:about|app|app-storage|.+-extension|file|res|widget):$/, Wt = /^(?:GET|HEAD)$/, It = /^\/\//, qt = /^([\w.+-]+:)(?:\/\/(?:[^\/?#]*@|)([^\/?#:]*)(?::(\d+)|)|)/, Bt = {}, $t = {}, Ut = "*/".concat("*");
    try {
        Lt = location.href
    } catch (zt) {
        Lt = me.createElement("a"), Lt.href = "", Lt = Lt.href
    }
    Yt = qt.exec(Lt.toLowerCase()) || [], oe.extend({
        "active": 0, "lastModified": {}, "etag": {}, "ajaxSettings": {
            "url": Lt,
            "type": "GET",
            "isLocal": Rt.test(Yt[1]),
            "global": !0,
            "processData": !0,
            "async": !0,
            "contentType": "application/x-www-form-urlencoded; charset=UTF-8",
            "accepts": {
                "*": Ut,
                "text": "text/plain",
                "html": "text/html",
                "xml": "application/xml, text/xml",
                "json": "application/json, text/javascript"
            },
            "contents": {"xml": /xml/, "html": /html/, "json": /json/},
            "responseFields": {"xml": "responseXML", "text": "responseText", "json": "responseJSON"},
            "converters": {"* text": String, "text html": !0, "text json": oe.parseJSON, "text xml": oe.parseXML},
            "flatOptions": {"url": !0, "context": !0}
        }, "ajaxSetup": function (e, t) {
            return t ? I(I(e, oe.ajaxSettings), t) : I(oe.ajaxSettings, e)
        }, "ajaxPrefilter": R(Bt), "ajaxTransport": R($t), "ajax": function (e, t) {
            function n(e, t, n, r) {
                var i, c, y, v, b, _ = t;
                2 !== w && (w = 2, s && clearTimeout(s), l = void 0, a = r || "", x.readyState = e > 0 ? 4 : 0, i = e >= 200 && e < 300 || 304 === e, n && (v = q(f, x, n)), v = B(f, v, x, i), i ? (f.ifModified && (b = x.getResponseHeader("Last-Modified"), b && (oe.lastModified[o] = b), b = x.getResponseHeader("etag"), b && (oe.etag[o] = b)), 204 === e || "HEAD" === f.type ? _ = "nocontent" : 304 === e ? _ = "notmodified" : (_ = v.state, c = v.data, y = v.error, i = !y)) : (y = _, !e && _ || (_ = "error", e < 0 && (e = 0))), x.status = e, x.statusText = (t || _) + "", i ? p.resolveWith(d, [c, _, x]) : p.rejectWith(d, [x, _, y]), x.statusCode(g), g = void 0, u && h.trigger(i ? "ajaxSuccess" : "ajaxError", [x, f, i ? c : y]), m.fireWith(d, [x, _]), u && (h.trigger("ajaxComplete", [x, f]), --oe.active || oe.event.trigger("ajaxStop")))
            }

            "object" == typeof e && (t = e, e = void 0), t = t || {};
            var r, i, o, a, s, u, l, c, f = oe.ajaxSetup({}, t), d = f.context || f, h = f.context && (d.nodeType || d.jquery) ? oe(d) : oe.event, p = oe.Deferred(), m = oe.Callbacks("once memory"), g = f.statusCode || {}, y = {}, v = {}, w = 0, b = "canceled", x = {
                "readyState": 0,
                "getResponseHeader": function (e) {
                    var t;
                    if (2 === w) {
                        if (!c)for (c = {}; t = Ft.exec(a);)c[t[1].toLowerCase()] = t[2];
                        t = c[e.toLowerCase()]
                    }
                    return null == t ? null : t
                },
                "getAllResponseHeaders": function () {
                    return 2 === w ? a : null
                },
                "setRequestHeader": function (e, t) {
                    var n = e.toLowerCase();
                    return w || (e = v[n] = v[n] || e, y[e] = t), this
                },
                "overrideMimeType": function (e) {
                    return w || (f.mimeType = e), this
                },
                "statusCode": function (e) {
                    var t;
                    if (e)if (w < 2)for (t in e)g[t] = [g[t], e[t]]; else x.always(e[x.status]);
                    return this
                },
                "abort": function (e) {
                    var t = e || b;
                    return l && l.abort(t), n(0, t), this
                }
            };
            if (p.promise(x).complete = m.add, x.success = x.done, x.error = x.fail, f.url = ((e || f.url || Lt) + "").replace(Ht, "").replace(It, Yt[1] + "//"), f.type = t.method || t.type || f.method || f.type, f.dataTypes = oe.trim(f.dataType || "*").toLowerCase().match(be) || [""], null == f.crossDomain && (r = qt.exec(f.url.toLowerCase()), f.crossDomain = !(!r || r[1] === Yt[1] && r[2] === Yt[2] && (r[3] || ("http:" === r[1] ? "80" : "443")) === (Yt[3] || ("http:" === Yt[1] ? "80" : "443")))), f.data && f.processData && "string" != typeof f.data && (f.data = oe.param(f.data, f.traditional)), W(Bt, f, t, x), 2 === w)return x;
            u = f.global, u && 0 === oe.active++ && oe.event.trigger("ajaxStart"), f.type = f.type.toUpperCase(), f.hasContent = !Wt.test(f.type), o = f.url, f.hasContent || (f.data && (o = f.url += (Nt.test(o) ? "&" : "?") + f.data, delete f.data), f.cache === !1 && (f.url = Pt.test(o) ? o.replace(Pt, "$1_=" + Ot++) : o + (Nt.test(o) ? "&" : "?") + "_=" + Ot++)), f.ifModified && (oe.lastModified[o] && x.setRequestHeader("If-Modified-Since", oe.lastModified[o]), oe.etag[o] && x.setRequestHeader("If-None-Match", oe.etag[o])), (f.data && f.hasContent && f.contentType !== !1 || t.contentType) && x.setRequestHeader("Content-Type", f.contentType), x.setRequestHeader("Accept", f.dataTypes[0] && f.accepts[f.dataTypes[0]] ? f.accepts[f.dataTypes[0]] + ("*" !== f.dataTypes[0] ? ", " + Ut + "; q=0.01" : "") : f.accepts["*"]);
            for (i in f.headers)x.setRequestHeader(i, f.headers[i]);
            if (f.beforeSend && (f.beforeSend.call(d, x, f) === !1 || 2 === w))return x.abort();
            b = "abort";
            for (i in{"success": 1, "error": 1, "complete": 1})x[i](f[i]);
            if (l = W($t, f, t, x)) {
                x.readyState = 1, u && h.trigger("ajaxSend", [x, f]), f.async && f.timeout > 0 && (s = setTimeout(function () {
                    x.abort("timeout")
                }, f.timeout));
                try {
                    w = 1, l.send(y, n)
                } catch (_) {
                    if (!(w < 2))throw _;
                    n(-1, _)
                }
            } else n(-1, "No Transport");
            return x
        }, "getJSON": function (e, t, n) {
            return oe.get(e, t, n, "json")
        }, "getScript": function (e, t) {
            return oe.get(e, void 0, t, "script")
        }
    }), oe.each(["get", "post"], function (e, t) {
        oe[t] = function (e, n, r, i) {
            return oe.isFunction(n) && (i = i || r, r = n, n = void 0), oe.ajax({"url": e, "type": t, "dataType": i, "data": n, "success": r})
        }
    }), oe.each(["ajaxStart", "ajaxStop", "ajaxComplete", "ajaxError", "ajaxSuccess", "ajaxSend"], function (e, t) {
        oe.fn[t] = function (e) {
            return this.on(t, e)
        }
    }), oe._evalUrl = function (e) {
        return oe.ajax({"url": e, "type": "GET", "dataType": "script", "async": !1, "global": !1, "throws": !0})
    }, oe.fn.extend({
        "wrapAll": function (e) {
            if (oe.isFunction(e))return this.each(function (t) {
                oe(this).wrapAll(e.call(this, t))
            });
            if (this[0]) {
                var t = oe(e, this[0].ownerDocument).eq(0).clone(!0);
                this[0].parentNode && t.insertBefore(this[0]), t.map(function () {
                    for (var e = this; e.firstChild && 1 === e.firstChild.nodeType;)e = e.firstChild;
                    return e
                }).append(this)
            }
            return this
        }, "wrapInner": function (e) {
            return oe.isFunction(e) ? this.each(function (t) {
                oe(this).wrapInner(e.call(this, t))
            }) : this.each(function () {
                var t = oe(this), n = t.contents();
                n.length ? n.wrapAll(e) : t.append(e)
            })
        }, "wrap": function (e) {
            var t = oe.isFunction(e);
            return this.each(function (n) {
                oe(this).wrapAll(t ? e.call(this, n) : e)
            })
        }, "unwrap": function () {
            return this.parent().each(function () {
                oe.nodeName(this, "body") || oe(this).replaceWith(this.childNodes)
            }).end()
        }
    }), oe.expr.filters.hidden = function (e) {
        return e.offsetWidth <= 0 && e.offsetHeight <= 0 || !re.reliableHiddenOffsets() && "none" === (e.style && e.style.display || oe.css(e, "display"))
    }, oe.expr.filters.visible = function (e) {
        return !oe.expr.filters.hidden(e)
    };
    var Gt = /%20/g, Vt = /\[\]$/, Qt = /\r?\n/g, Xt = /^(?:submit|button|image|reset|file)$/i, Zt = /^(?:input|select|textarea|keygen)/i;
    oe.param = function (e, t) {
        var n, r = [], i = function (e, t) {
            t = oe.isFunction(t) ? t() : null == t ? "" : t, r[r.length] = encodeURIComponent(e) + "=" + encodeURIComponent(t)
        };
        if (void 0 === t && (t = oe.ajaxSettings && oe.ajaxSettings.traditional), oe.isArray(e) || e.jquery && !oe.isPlainObject(e))oe.each(e, function () {
            i(this.name, this.value)
        }); else for (n in e)$(n, e[n], t, i);
        return r.join("&").replace(Gt, "+")
    }, oe.fn.extend({
        "serialize": function () {
            return oe.param(this.serializeArray())
        }, "serializeArray": function () {
            return this.map(function () {
                var e = oe.prop(this, "elements");
                return e ? oe.makeArray(e) : this
            }).filter(function () {
                var e = this.type;
                return this.name && !oe(this).is(":disabled") && Zt.test(this.nodeName) && !Xt.test(e) && (this.checked || !Oe.test(e))
            }).map(function (e, t) {
                var n = oe(this).val();
                return null == n ? null : oe.isArray(n) ? oe.map(n, function (e) {
                    return {"name": t.name, "value": e.replace(Qt, "\r\n")}
                }) : {"name": t.name, "value": n.replace(Qt, "\r\n")}
            }).get()
        }
    }), oe.ajaxSettings.xhr = void 0 !== e.ActiveXObject ? function () {
        return !this.isLocal && /^(get|post|head|put|delete|options)$/i.test(this.type) && U() || z()
    } : U;
    var Jt = 0, Kt = {}, en = oe.ajaxSettings.xhr();
    e.ActiveXObject && oe(e).on("unload", function () {
        for (var e in Kt)Kt[e](void 0, !0)
    }), re.cors = !!en && "withCredentials" in en, en = re.ajax = !!en, en && oe.ajaxTransport(function (e) {
        if (!e.crossDomain || re.cors) {
            var t;
            return {
                "send": function (n, r) {
                    var i, o = e.xhr(), a = ++Jt;
                    if (o.open(e.type, e.url, e.async, e.username, e.password), e.xhrFields)for (i in e.xhrFields)o[i] = e.xhrFields[i];
                    e.mimeType && o.overrideMimeType && o.overrideMimeType(e.mimeType), e.crossDomain || n["X-Requested-With"] || (n["X-Requested-With"] = "XMLHttpRequest");
                    for (i in n)void 0 !== n[i] && o.setRequestHeader(i, n[i] + "");
                    o.send(e.hasContent && e.data || null), t = function (n, i) {
                        var s, u, l;
                        if (t && (i || 4 === o.readyState))if (delete Kt[a], t = void 0, o.onreadystatechange = oe.noop, i)4 !== o.readyState && o.abort(); else {
                            l = {}, s = o.status, "string" == typeof o.responseText && (l.text = o.responseText);
                            try {
                                u = o.statusText
                            } catch (c) {
                                u = ""
                            }
                            s || !e.isLocal || e.crossDomain ? 1223 === s && (s = 204) : s = l.text ? 200 : 404
                        }
                        l && r(s, u, l, o.getAllResponseHeaders())
                    }, e.async ? 4 === o.readyState ? setTimeout(t) : o.onreadystatechange = Kt[a] = t : t()
                }, "abort": function () {
                    t && t(void 0, !0)
                }
            }
        }
    }), oe.ajaxSetup({
        "accepts": {"script": "text/javascript, application/javascript, application/ecmascript, application/x-ecmascript"},
        "contents": {"script": /(?:java|ecma)script/},
        "converters": {
            "text script": function (e) {
                return oe.globalEval(e), e
            }
        }
    }), oe.ajaxPrefilter("script", function (e) {
        void 0 === e.cache && (e.cache = !1), e.crossDomain && (e.type = "GET", e.global = !1)
    }), oe.ajaxTransport("script", function (e) {
        if (e.crossDomain) {
            var t, n = me.head || oe("head")[0] || me.documentElement;
            return {
                "send": function (r, i) {
                    t = me.createElement("script"), t.async = !0, e.scriptCharset && (t.charset = e.scriptCharset), t.src = e.url, t.onload = t.onreadystatechange = function (e, n) {
                        (n || !t.readyState || /loaded|complete/.test(t.readyState)) && (t.onload = t.onreadystatechange = null, t.parentNode && t.parentNode.removeChild(t), t = null, n || i(200, "success"))
                    }, n.insertBefore(t, n.firstChild)
                }, "abort": function () {
                    t && t.onload(void 0, !0)
                }
            }
        }
    });
    var tn = [], nn = /(=)\?(?=&|$)|\?\?/;
    oe.ajaxSetup({
        "jsonp": "callback", "jsonpCallback": function () {
            var e = tn.pop() || oe.expando + "_" + Ot++;
            return this[e] = !0, e
        }
    }), oe.ajaxPrefilter("json jsonp", function (t, n, r) {
        var i, o, a, s = t.jsonp !== !1 && (nn.test(t.url) ? "url" : "string" == typeof t.data && !(t.contentType || "").indexOf("application/x-www-form-urlencoded") && nn.test(t.data) && "data");
        if (s || "jsonp" === t.dataTypes[0])return i = t.jsonpCallback = oe.isFunction(t.jsonpCallback) ? t.jsonpCallback() : t.jsonpCallback, s ? t[s] = t[s].replace(nn, "$1" + i) : t.jsonp !== !1 && (t.url += (Nt.test(t.url) ? "&" : "?") + t.jsonp + "=" + i), t.converters["script json"] = function () {
            return a || oe.error(i + " was not called"), a[0]
        }, t.dataTypes[0] = "json", o = e[i], e[i] = function () {
            a = arguments
        }, r.always(function () {
            e[i] = o, t[i] && (t.jsonpCallback = n.jsonpCallback, tn.push(i)), a && oe.isFunction(o) && o(a[0]), a = o = void 0
        }), "script"
    }), oe.parseHTML = function (e, t, n) {
        if (!e || "string" != typeof e)return null;
        "boolean" == typeof t && (n = t, t = !1), t = t || me;
        var r = de.exec(e), i = !n && [];
        return r ? [t.createElement(r[1])] : (r = oe.buildFragment([e], t, i), i && i.length && oe(i).remove(), oe.merge([], r.childNodes))
    };
    var rn = oe.fn.load;
    oe.fn.load = function (e, t, n) {
        if ("string" != typeof e && rn)return rn.apply(this, arguments);
        var r, i, o, a = this, s = e.indexOf(" ");
        return s >= 0 && (r = e.slice(s, e.length), e = e.slice(0, s)), oe.isFunction(t) ? (n = t, t = void 0) : t && "object" == typeof t && (o = "POST"), a.length > 0 && oe.ajax({
            "url": e,
            "type": o,
            "dataType": "html",
            "data": t
        }).done(function (e) {
            i = arguments, a.html(r ? oe("<div>").append(oe.parseHTML(e)).find(r) : e)
        }).complete(n && function (e, t) {
                a.each(n, i || [e.responseText, t, e])
            }), this
    }, oe.expr.filters.animated = function (e) {
        return oe.grep(oe.timers, function (t) {
            return e === t.elem
        }).length
    };
    var on = e.document.documentElement;
    oe.offset = {
        "setOffset": function (e, t, n) {
            var r, i, o, a, s, u, l, c = oe.css(e, "position"), f = oe(e), d = {};
            "static" === c && (e.style.position = "relative"), s = f.offset(), o = oe.css(e, "top"), u = oe.css(e, "left"), l = ("absolute" === c || "fixed" === c) && oe.inArray("auto", [o, u]) > -1, l ? (r = f.position(), a = r.top, i = r.left) : (a = parseFloat(o) || 0, i = parseFloat(u) || 0), oe.isFunction(t) && (t = t.call(e, n, s)), null != t.top && (d.top = t.top - s.top + a), null != t.left && (d.left = t.left - s.left + i), "using" in t ? t.using.call(e, d) : f.css(d)
        }
    }, oe.fn.extend({
        "offset": function (e) {
            if (arguments.length)return void 0 === e ? this : this.each(function (t) {
                oe.offset.setOffset(this, e, t)
            });
            var t, n, r = {"top": 0, "left": 0}, i = this[0], o = i && i.ownerDocument;
            if (o)return t = o.documentElement, oe.contains(t, i) ? (typeof i.getBoundingClientRect !== Se && (r = i.getBoundingClientRect()), n = G(o), {
                "top": r.top + (n.pageYOffset || t.scrollTop) - (t.clientTop || 0),
                "left": r.left + (n.pageXOffset || t.scrollLeft) - (t.clientLeft || 0)
            }) : r
        }, "position": function () {
            if (this[0]) {
                var e, t, n = {"top": 0, "left": 0}, r = this[0];
                return "fixed" === oe.css(r, "position") ? t = r.getBoundingClientRect() : (e = this.offsetParent(), t = this.offset(), oe.nodeName(e[0], "html") || (n = e.offset()), n.top += oe.css(e[0], "borderTopWidth", !0), n.left += oe.css(e[0], "borderLeftWidth", !0)), {
                    "top": t.top - n.top - oe.css(r, "marginTop", !0),
                    "left": t.left - n.left - oe.css(r, "marginLeft", !0)
                }
            }
        }, "offsetParent": function () {
            return this.map(function () {
                for (var e = this.offsetParent || on; e && !oe.nodeName(e, "html") && "static" === oe.css(e, "position");)e = e.offsetParent;
                return e || on
            })
        }
    }), oe.each({"scrollLeft": "pageXOffset", "scrollTop": "pageYOffset"}, function (e, t) {
        var n = /Y/.test(t);
        oe.fn[e] = function (r) {
            return Ae(this, function (e, r, i) {
                var o = G(e);
                return void 0 === i ? o ? t in o ? o[t] : o.document.documentElement[r] : e[r] : void(o ? o.scrollTo(n ? oe(o).scrollLeft() : i, n ? i : oe(o).scrollTop()) : e[r] = i)
            }, e, r, arguments.length, null)
        }
    }), oe.each(["top", "left"], function (e, t) {
        oe.cssHooks[t] = M(re.pixelPosition, function (e, n) {
            if (n)return n = nt(e, t), it.test(n) ? oe(e).position()[t] + "px" : n
        })
    }), oe.each({"Height": "height", "Width": "width"}, function (e, t) {
        oe.each({"padding": "inner" + e, "content": t, "": "outer" + e}, function (n, r) {
            oe.fn[r] = function (r, i) {
                var o = arguments.length && (n || "boolean" != typeof r), a = n || (r === !0 || i === !0 ? "margin" : "border");
                return Ae(this, function (t, n, r) {
                    var i;
                    return oe.isWindow(t) ? t.document.documentElement["client" + e] : 9 === t.nodeType ? (i = t.documentElement, Math.max(t.body["scroll" + e], i["scroll" + e], t.body["offset" + e], i["offset" + e], i["client" + e])) : void 0 === r ? oe.css(t, n, a) : oe.style(t, n, r, a)
                }, t, o ? r : void 0, o, null)
            }
        })
    }), oe.fn.size = function () {
        return this.length
    }, oe.fn.andSelf = oe.fn.addBack, "function" == typeof define && define.amd && define("jquery", [], function () {
        return oe
    });
    var an = e.jQuery, sn = e.$;
    return oe.noConflict = function (t) {
        return e.$ === oe && (e.$ = sn), t && e.jQuery === oe && (e.jQuery = an), oe
    }, typeof t === Se && (e.jQuery = e.$ = oe), oe
}), function (e, t, n) {
    function r(e) {
        var t = {}, r = /^jQuery\d+$/;
        return n.each(e.attributes, function (e, n) {
            n.specified && !r.test(n.name) && (t[n.name] = n.value)
        }), t
    }

    function i(e, r) {
        var i = this, o = n(i);
        if (i.value == o.attr("placeholder") && o.hasClass("placeholder"))if (o.data("placeholder-password")) {
            if (o = o.hide().next().show().attr("id", o.removeAttr("id").data("placeholder-id")), e === !0)return o[0].value = r;
            o.focus()
        } else i.value = "", o.removeClass("placeholder"), i == t.activeElement && i.select()
    }

    function o() {
        var e, t = this, o = n(t), a = this.id;
        if ("" == t.value) {
            if ("password" == t.type) {
                if (!o.data("placeholder-textinput")) {
                    try {
                        e = o.clone().attr({"type": "text"})
                    } catch (s) {
                        e = n("<input>").attr(n.extend(r(this), {"type": "text"}))
                    }
                    e.removeAttr("name").data({
                        "placeholder-password": !0,
                        "placeholder-id": a
                    }).bind("focus.placeholder", i), o.data({"placeholder-textinput": e, "placeholder-id": a}).before(e)
                }
                o = o.removeAttr("id").hide().prev().attr("id", a).show()
            }
            o.addClass("placeholder"), o[0].value = o.attr("placeholder")
        } else o.removeClass("placeholder")
    }

    var a, s, u = "placeholder" in t.createElement("input"), l = "placeholder" in t.createElement("textarea"), c = n.fn, f = n.valHooks;
    u && l ? (s = c.placeholder = function () {
        return this
    }, s.input = s.textarea = !0) : (s = c.placeholder = function () {
        var e = this;
        return e.filter((u ? "textarea" : ":input") + "[placeholder]").not(".placeholder").bind({
            "focus.placeholder": i,
            "blur.placeholder": o
        }).data("placeholder-enabled", !0).trigger("blur.placeholder"), e
    }, s.input = u, s.textarea = l, a = {
        "get": function (e) {
            var t = n(e);
            return t.data("placeholder-enabled") && t.hasClass("placeholder") ? "" : e.value
        }, "set": function (e, r) {
            var a = n(e);
            return a.data("placeholder-enabled") ? ("" == r ? (e.value = r, e != t.activeElement && o.call(e)) : a.hasClass("placeholder") ? i.call(e, !0, r) || (e.value = r) : e.value = r, a) : e.value = r
        }
    }, u || (f.input = a), l || (f.textarea = a), n(function () {
        n(t).delegate("form", "submit.placeholder", function () {
            var e = n(".placeholder", this).each(i);
            setTimeout(function () {
                e.each(o)
            }, 10)
        })
    }), n(e).bind("beforeunload.placeholder", function () {
        n(".placeholder").each(function () {
            this.value = ""
        })
    }))
}(this, document, jQuery), function (e, t, n, r) {
    var i = e(t);
    e.fn.lazyload = function (o) {
        function a() {
            var t = 0;
            u.each(function () {
                var n = e(this);
                if (!l.skip_invisible || n.is(":visible"))if (e.abovethetop(this, l) || e.leftofbegin(this, l)); else if (e.belowthefold(this, l) || e.rightoffold(this, l)) {
                    if (++t > l.failure_limit)return !1
                } else n.trigger("appear"), t = 0
            })
        }

        var s, u = this, l = {
            "threshold": 0,
            "failure_limit": 0,
            "event": "scroll",
            "effect": "show",
            "container": t,
            "data_attribute": "original",
            "skip_invisible": !1,
            "appear": null,
            "load": null,
            "placeholder": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC"
        };
        return o && (r !== o.failurelimit && (o.failure_limit = o.failurelimit, delete o.failurelimit), r !== o.effectspeed && (o.effect_speed = o.effectspeed, delete o.effectspeed), e.extend(l, o)), s = l.container === r || l.container === t ? i : e(l.container), 0 === l.event.indexOf("scroll") && s.bind(l.event, function () {
            return a()
        }), this.each(function () {
            var t = this, n = e(t);
            t.loaded = !1, n.attr("src") !== r && n.attr("src") !== !1 || n.is("img") && n.attr("src", l.placeholder), n.one("appear", function () {
                if (!this.loaded) {
                    if (l.appear) {
                        var r = u.length;
                        l.appear.call(t, r, l)
                    }
                    e("<img />").bind("load", function () {
                        var r = n.attr("data-" + l.data_attribute);
                        n.hide(), n.is("img") ? n.attr("src", r) : n.css("background-image", "url('" + r + "')"), n[l.effect](l.effect_speed), t.loaded = !0;
                        var i = e.grep(u, function (e) {
                            return !e.loaded
                        });
                        if (u = e(i), l.load) {
                            var o = u.length;
                            l.load.call(t, o, l)
                        }
                    }).attr("src", n.attr("data-" + l.data_attribute))
                }
            }), 0 !== l.event.indexOf("scroll") && n.bind(l.event, function () {
                t.loaded || n.trigger("appear")
            })
        }), i.bind("resize", function () {
            a()
        }), /(?:iphone|ipod|ipad).*os 5/gi.test(navigator.appVersion) && i.bind("pageshow", function (t) {
            t.originalEvent && t.originalEvent.persisted && u.each(function () {
                e(this).trigger("appear")
            })
        }), e(n).ready(function () {
            a()
        }), this
    }, e.belowthefold = function (n, o) {
        var a;
        return a = o.container === r || o.container === t ? (t.innerHeight ? t.innerHeight : i.height()) + i.scrollTop() : e(o.container).offset().top + e(o.container).height(), a <= e(n).offset().top - o.threshold
    }, e.rightoffold = function (n, o) {
        var a;
        return a = o.container === r || o.container === t ? i.width() + i.scrollLeft() : e(o.container).offset().left + e(o.container).width(), a <= e(n).offset().left - o.threshold
    }, e.abovethetop = function (n, o) {
        var a;
        return a = o.container === r || o.container === t ? i.scrollTop() : e(o.container).offset().top, a >= e(n).offset().top + o.threshold + e(n).height()
    }, e.leftofbegin = function (n, o) {
        var a;
        return a = o.container === r || o.container === t ? i.scrollLeft() : e(o.container).offset().left, a >= e(n).offset().left + o.threshold + e(n).width()
    }, e.inviewport = function (t, n) {
        return !(e.rightoffold(t, n) || e.leftofbegin(t, n) || e.belowthefold(t, n) || e.abovethetop(t, n))
    }, e.extend(e.expr[":"], {
        "below-the-fold": function (t) {
            return e.belowthefold(t, {"threshold": 0})
        }, "above-the-top": function (t) {
            return !e.belowthefold(t, {"threshold": 0})
        }, "right-of-screen": function (t) {
            return e.rightoffold(t, {"threshold": 0})
        }, "left-of-screen": function (t) {
            return !e.rightoffold(t, {"threshold": 0})
        }, "in-viewport": function (t) {
            return e.inviewport(t, {"threshold": 0})
        }, "above-the-fold": function (t) {
            return !e.belowthefold(t, {"threshold": 0})
        }, "right-of-fold": function (t) {
            return e.rightoffold(t, {"threshold": 0})
        }, "left-of-fold": function (t) {
            return !e.rightoffold(t, {"threshold": 0})
        }
    })
}(jQuery, window, document), jQuery.easing.jswing = jQuery.easing.swing, jQuery.extend(jQuery.easing, {
    "def": "easeOutQuad",
    "swing": function (e, t, n, r, i) {
        return jQuery.easing[jQuery.easing.def](e, t, n, r, i)
    },
    "easeInQuad": function (e, t, n, r, i) {
        return r * (t /= i) * t + n
    },
    "easeOutQuad": function (e, t, n, r, i) {
        return -r * (t /= i) * (t - 2) + n
    },
    "easeInOutQuad": function (e, t, n, r, i) {
        return (t /= i / 2) < 1 ? r / 2 * t * t + n : -r / 2 * (--t * (t - 2) - 1) + n
    },
    "easeInCubic": function (e, t, n, r, i) {
        return r * (t /= i) * t * t + n
    },
    "easeOutCubic": function (e, t, n, r, i) {
        return r * ((t = t / i - 1) * t * t + 1) + n
    },
    "easeInOutCubic": function (e, t, n, r, i) {
        return (t /= i / 2) < 1 ? r / 2 * t * t * t + n : r / 2 * ((t -= 2) * t * t + 2) + n
    },
    "easeInQuart": function (e, t, n, r, i) {
        return r * (t /= i) * t * t * t + n
    },
    "easeOutQuart": function (e, t, n, r, i) {
        return -r * ((t = t / i - 1) * t * t * t - 1) + n
    },
    "easeInOutQuart": function (e, t, n, r, i) {
        return (t /= i / 2) < 1 ? r / 2 * t * t * t * t + n : -r / 2 * ((t -= 2) * t * t * t - 2) + n
    },
    "easeInQuint": function (e, t, n, r, i) {
        return r * (t /= i) * t * t * t * t + n
    },
    "easeOutQuint": function (e, t, n, r, i) {
        return r * ((t = t / i - 1) * t * t * t * t + 1) + n
    },
    "easeInOutQuint": function (e, t, n, r, i) {
        return (t /= i / 2) < 1 ? r / 2 * t * t * t * t * t + n : r / 2 * ((t -= 2) * t * t * t * t + 2) + n
    },
    "easeInSine": function (e, t, n, r, i) {
        return -r * Math.cos(t / i * (Math.PI / 2)) + r + n
    },
    "easeOutSine": function (e, t, n, r, i) {
        return r * Math.sin(t / i * (Math.PI / 2)) + n
    },
    "easeInOutSine": function (e, t, n, r, i) {
        return -r / 2 * (Math.cos(Math.PI * t / i) - 1) + n
    },
    "easeInExpo": function (e, t, n, r, i) {
        return 0 == t ? n : r * Math.pow(2, 10 * (t / i - 1)) + n
    },
    "easeOutExpo": function (e, t, n, r, i) {
        return t == i ? n + r : r * (-Math.pow(2, -10 * t / i) + 1) + n
    },
    "easeInOutExpo": function (e, t, n, r, i) {
        return 0 == t ? n : t == i ? n + r : (t /= i / 2) < 1 ? r / 2 * Math.pow(2, 10 * (t - 1)) + n : r / 2 * (-Math.pow(2, -10 * --t) + 2) + n
    },
    "easeInCirc": function (e, t, n, r, i) {
        return -r * (Math.sqrt(1 - (t /= i) * t) - 1) + n
    },
    "easeOutCirc": function (e, t, n, r, i) {
        return r * Math.sqrt(1 - (t = t / i - 1) * t) + n
    },
    "easeInOutCirc": function (e, t, n, r, i) {
        return (t /= i / 2) < 1 ? -r / 2 * (Math.sqrt(1 - t * t) - 1) + n : r / 2 * (Math.sqrt(1 - (t -= 2) * t) + 1) + n
    },
    "easeInElastic": function (e, t, n, r, i) {
        var o = 1.70158, a = 0, s = r;
        if (0 == t)return n;
        if (1 == (t /= i))return n + r;
        if (a || (a = .3 * i), s < Math.abs(r)) {
            s = r;
            var o = a / 4
        } else var o = a / (2 * Math.PI) * Math.asin(r / s);
        return -(s * Math.pow(2, 10 * (t -= 1)) * Math.sin((t * i - o) * (2 * Math.PI) / a)) + n
    },
    "easeOutElastic": function (e, t, n, r, i) {
        var o = 1.70158, a = 0, s = r;
        if (0 == t)return n;
        if (1 == (t /= i))return n + r;
        if (a || (a = .3 * i), s < Math.abs(r)) {
            s = r;
            var o = a / 4
        } else var o = a / (2 * Math.PI) * Math.asin(r / s);
        return s * Math.pow(2, -10 * t) * Math.sin((t * i - o) * (2 * Math.PI) / a) + r + n
    },
    "easeInOutElastic": function (e, t, n, r, i) {
        var o = 1.70158, a = 0, s = r;
        if (0 == t)return n;
        if (2 == (t /= i / 2))return n + r;
        if (a || (a = i * (.3 * 1.5)), s < Math.abs(r)) {
            s = r;
            var o = a / 4
        } else var o = a / (2 * Math.PI) * Math.asin(r / s);
        return t < 1 ? -.5 * (s * Math.pow(2, 10 * (t -= 1)) * Math.sin((t * i - o) * (2 * Math.PI) / a)) + n : s * Math.pow(2, -10 * (t -= 1)) * Math.sin((t * i - o) * (2 * Math.PI) / a) * .5 + r + n
    },
    "easeInBack": function (e, t, n, r, i, o) {
        return void 0 == o && (o = 1.70158), r * (t /= i) * t * ((o + 1) * t - o) + n
    },
    "easeOutBack": function (e, t, n, r, i, o) {
        return void 0 == o && (o = 1.70158), r * ((t = t / i - 1) * t * ((o + 1) * t + o) + 1) + n
    },
    "easeInOutBack": function (e, t, n, r, i, o) {
        return void 0 == o && (o = 1.70158), (t /= i / 2) < 1 ? r / 2 * (t * t * (((o *= 1.525) + 1) * t - o)) + n : r / 2 * ((t -= 2) * t * (((o *= 1.525) + 1) * t + o) + 2) + n
    },
    "easeInBounce": function (e, t, n, r, i) {
        return r - jQuery.easing.easeOutBounce(e, i - t, 0, r, i) + n
    },
    "easeOutBounce": function (e, t, n, r, i) {
        return (t /= i) < 1 / 2.75 ? r * (7.5625 * t * t) + n : t < 2 / 2.75 ? r * (7.5625 * (t -= 1.5 / 2.75) * t + .75) + n : t < 2.5 / 2.75 ? r * (7.5625 * (t -= 2.25 / 2.75) * t + .9375) + n : r * (7.5625 * (t -= 2.625 / 2.75) * t + .984375) + n
    },
    "easeInOutBounce": function (e, t, n, r, i) {
        return t < i / 2 ? .5 * jQuery.easing.easeInBounce(e, 2 * t, 0, r, i) + n : .5 * jQuery.easing.easeOutBounce(e, 2 * t - i, 0, r, i) + .5 * r + n
    }
}), window.formatAmount = function (e, t) {
    if (t = t > 0 && t <= 20 ? t : 0, e < 0) {
        var n = 0;
        return n.toFixed(t)
    }
    e = parseFloat((e + "").replace(/[^\d\.-]/g, "")).toFixed(t) + "";
    var r, i = e.split(".")[0].split("").reverse(), o = e.split(".")[1], a = "";
    for (r = 0; r < i.length; r++)a += i[r] + ((r + 1) % 3 == 0 && r + 1 != i.length ? "," : "");
    return o ? a.split("").reverse().join("") + "." + o : a.split("").reverse().join("")
}, window.toChinese = function (e, t) {
    if (t = t || !1, !t && !/^(0|[1-9]\d*)(\.\d+)?$/.test(e))return "\u6570\u636e\u975e\u6cd5";
    var n = "\u4edf\u4f70\u62fe\u4ebf\u4edf\u4f70\u62fe\u4e07\u4edf\u4f70\u62fe\u5143\u89d2\u5206", r = "";
    e += "00";
    var i = e.indexOf(".");
    i >= 0 && (e = e.substring(0, i) + e.substr(i + 1, 2)), n = n.substr(n.length - e.length);
    for (var o = 0; o < e.length; o++)r += "\u96f6\u58f9\u8d30\u53c1\u8086\u4f0d\u9646\u67d2\u634c\u7396".charAt(e.charAt(o)) + n.charAt(o);
    return r.replace(/\u96f6(\u4edf|\u4f70|\u62fe|\u89d2)/g, "\u96f6").replace(/(\u96f6)+/g, "\u96f6").replace(/\u96f6(\u4e07|\u4ebf|\u5143)/g, "$1").replace(/(\u4ebf)\u4e07|^\u58f9(\u62fe)/g, "$1$2").replace(/^\u5143\u96f6?|\u96f6\u5206/g, "").replace(/\u5143$/g, "\u5143")
}, window.bookmark = function () {
    var e = $.Event("keypress");
    if (e.which = 100, e.ctrlKey = !0, $(document).trigger(e), document.all)try {
        window.external.addFavorite(window.location.href, CC.client.shortName)
    } catch (t) {
        alert("\u8bf7\u4f7f\u7528 Ctrl+D \u8fdb\u884c\u6dfb\u52a0!")
    } else window.sidebar ? window.sidebar.addPanel(document.title, window.location.href, "") : alert("\u8bf7\u4f7f\u7528 Ctrl+D \u8fdb\u884c\u6dfb\u52a0")
}, window.timeTillNow = function (e) {
    var t = new Date(parseInt(e)), n = new Date, r = (n.valueOf() - t.valueOf()) / 1e3, i = parseInt(r / 60 / 60 / 24);
    if (i > 0)return i + "\u5929\u524d";
    var o = parseInt(r / 60 / 60);
    if (o > 0)return o + "\u5c0f\u65f6\u524d";
    var a = parseInt(r / 60);
    return a > 0 ? a + "\u5206\u949f\u524d" : "\u521a\u521a"
}, window.formatMobile = function (e) {
    return e.replace(/(\d{3})(\d{4})(\d{4})/, "$1-$2-$3")
}, window.formatDate = function (e, t) {
    var n = {
        "M+": e.getMonth() + 1,
        "d+": e.getDate(),
        "h+": e.getHours(),
        "m+": e.getMinutes(),
        "s+": e.getSeconds(),
        "q+": Math.floor((e.getMonth() + 3) / 3),
        "S": e.getMilliseconds()
    };
    /(y+)/.test(t) && (t = t.replace(RegExp.$1, (e.getFullYear() + "").substr(4 - RegExp.$1.length)));
    for (var r in n)new RegExp("(" + r + ")").test(t) && (t = t.replace(RegExp.$1, 1 === RegExp.$1.length ? n[r] : ("00" + n[r]).substr(("" + n[r]).length)));
    return t
}, window.formatDuration = function (e) {
    return e.replace("\u96f60\u4e2a\u6708\u96f6", "\u96f6").replace("0\u5e74\u96f6", "").replace("\u96f60\u5929", "")
}, window.getQueryString = function (e) {
    var t = new RegExp("(^|&)" + e + "=([^&]*)(&|$)"), n = window.location.search.substr(1).match(t);
    return null != n ? decodeURI(n[2]) : null
}, window.getBrowser = function () {
    var e = window.navigator.userAgent, t = e.indexOf("Opera") > -1;
    return t ? "Opera" : e.indexOf("Firefox") > -1 ? "FF" : e.indexOf("Chrome") > -1 ? "Chrome" : e.indexOf("Safari") > -1 ? "Safari" : e.indexOf("compatible") > -1 && e.indexOf("MSIE") > -1 && !t ? "IE" : /Trident/i.test(e) ? "IE" : void 0
}, window.msieOld = function () {
    return /msie\s*(8|7|6)/.test(navigator.userAgent.toLowerCase())
}, window.cookie = function (e, t, n) {
    if ("undefined" == typeof t) {
        var r = null;
        if (document.cookie && "" != document.cookie)for (var i = document.cookie.split(";"), o = 0; o < i.length; o++) {
            var a = jQuery.trim(i[o]);
            if (a.substring(0, e.length + 1) == e + "=") {
                r = decodeURIComponent(a.substring(e.length + 1));
                break
            }
        }
        return r
    }
    n = n || {}, null === t && (t = "", n = $.extend({}, n), n.expires = -1);
    var s = "";
    if (n.expires && ("number" == typeof n.expires || n.expires.toUTCString)) {
        var u;
        "number" == typeof n.expires ? (u = new Date, u.setTime(u.getTime() + 24 * n.expires * 60 * 60 * 1e3)) : u = n.expires, s = "; expires=" + u.toUTCString()
    }
    var l = n.path ? "; path=" + n.path : "", c = n.domain ? "; domain=" + n.domain : "", f = n.secure ? "; secure" : "";
    document.cookie = [e, "=", encodeURIComponent(t), s, l, c, f].join("")
}, window.regExp = {
    "isPositiveInt": function (e) {
        return /^[0-9]*[1-9][0-9]*$/.test(e)
    }, "isEmail": function (e) {
        return /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(e)
    }, "isMobile": function (e) {
        return /^0*(13|14|15|17|18)\d{9}$/.test(e)
    }
}, function (e) {
    "function" == typeof define && define.amd ? define(["jquery"], e) : e(jQuery)
}(function (e) {
    function t(e) {
        return s.raw ? e : encodeURIComponent(e)
    }

    function n(e) {
        return s.raw ? e : decodeURIComponent(e)
    }

    function r(e) {
        return t(s.json ? JSON.stringify(e) : String(e))
    }

    function i(e) {
        0 === e.indexOf('"') && (e = e.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, "\\"));
        try {
            e = decodeURIComponent(e.replace(a, " "))
        } catch (t) {
            return
        }
        try {
            return s.json ? JSON.parse(e) : e
        } catch (t) {
        }
    }

    function o(t, n) {
        var r = s.raw ? t : i(t);
        return e.isFunction(n) ? n(r) : r
    }

    var a = /\+/g, s = e.cookie = function (i, a, u) {
        if (void 0 !== a && !e.isFunction(a)) {
            if (u = e.extend({}, s.defaults, u), "number" == typeof u.expires) {
                var l = u.expires, c = u.expires = new Date;
                c.setDate(c.getDate() + l)
            }
            return document.cookie = [t(i), "=", r(a), u.expires ? "; expires=" + u.expires.toUTCString() : "", u.path ? "; path=" + u.path : "", u.domain ? "; domain=" + u.domain : "", u.secure ? "; secure" : ""].join("")
        }
        for (var f = i ? void 0 : {}, d = document.cookie ? document.cookie.split("; ") : [], h = 0, p = d.length; h < p; h++) {
            var m = d[h].split("="), g = n(m.shift()), y = m.join("=");
            if (i && i === g) {
                f = o(y, a);
                break
            }
            i || void 0 === (y = o(y)) || (f[g] = y)
        }
        return f
    };
    s.defaults = {}, e.removeCookie = function (t, n) {
        return void 0 !== e.cookie(t) && (e.cookie(t, "", e.extend({}, n, {"expires": -1})), !0)
    }
}), window.showFocusTip = function (e, t) {
    e.html(t).animate({"opacity": 1, "right": 50}, 500), e.html(t).animate({"opacity": 0, "right": 55}, 500, function () {
        e.css({"top": 20, "right": 40, "opacity": 0})
    })
};