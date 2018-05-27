!
    function e(a, t, n) {
        function i(s, c) {
            if (!t[s]) {
                if (!a[s]) {
                    var o = "function" == typeof require && require;
                    if (!c && o) return o(s, !0);
                    if (r) return r(s, !0);
                    var u = new Error("Cannot find module '" + s + "'");
                    throw u.code = "MODULE_NOT_FOUND",
                        u
                }
                var l = t[s] = {
                    "exports": {}
                };
                a[s][0].call(l.exports,
                    function(e) {
                        var t = a[s][1][e];
                        return i(t ? t: e)
                    },
                    l, l.exports, e, a, t, n)
            }
            return t[s].exports
        }
        for (var r = "function" == typeof require && require,
                 s = 0; s < n.length; s++) i(n[s]);
        return i
    } ({
            "1": [function(e, a, t) {
                $(function() {
                    function e(e) {
                        if (e.success) {
                            0 === e.data.signinStat.signinCount && ($(".share").show(), e.data.signinStat.signinCount = 7);
                            for (var t = $(".dot li"), n = 0; n < e.data.signinStat.signinCount; n++) t.eq(n).addClass("done");
                            "ANSWER" === e.data.signinSatus ? ($(".shining-yellow").hide(),
                                $(".alert").show(),
                                $(".shadow").show(),
                                $(".sign-in").show(),
                                $(".no-prize").show()) : "SIGNIN_DRAW_END" === e.data.signinSatus && a(e.data.lotteryPrizeInfo[0], e.data.invest)
                        } else alert(e.error.message)
                    }
                    function a(e, a) {
                        "INSTANT_INTEREST" === e.prizeType ? (a ? ($(".explain").html("您历史最近一笔定期理财投资将享受" + e.durationDesc + e.faceValue + "%的加息奖励！<br/>加息奖励将在2个工作日内返现到您的账户"),
                            $(".sign-get .button").attr("href", redirect.my("/account/"))) : ($(".explain").html('很遗憾，您未有任何投资记录，此次无法获得加息奖励！<br/>' +
                            '<a target="_blank" href="' + redirect.lc("/") + '">快去投资</a>，下次即可获得加息奖励'),
                            $(".sign-get .button").css("background-image", "images/signin/btn_alert_true1.png"),
                            $(".sign-get .button").attr("href", redirect.lc("/"))),
                            $(".prize-name").append('<span class="value">' + e.faceValue + '%</span>' +
                                '<span class="type">' + e.prizeName + "</span>")) : "INTEGRAL" === e.prizeType ? ($(".explain").html('积分可在积分商城换购多种精选实物、卡券类商品;<br/>可在"' +
                            '<a target="_blank" href="' + redirect.my("/account/") + '">个人中心</a>"-"' +
                            '<a target="_blank" href="' + redirect.my("/account/jifen") + '">我的积分</a>"中查看'),
                            $(".sign-get .button").attr("href", redirect.my("/account/jifen")),
                            $(".prize-name").append('<span class="value">' + e.faceValue + '</span>' +
                                '<span class="type">' + e.prizeName + "</span>")) : "WEEKEND_REBATE" === e.prizeType || "REBATE" === e.prizeType ? ($(".explain").html('本周周末投资即可使用;<br/>详细使用规则可在"' +
                            '<a target="_blank" href="' + redirect.my("/account/") + '">个人中心</a>"-"' +
                            '<a target="_blank" href="' + redirect.my("/account/mykq") + '">我的卡券</a>"中查看'),
                            $(".sign-get .button").attr("href", redirect.my("/account/mykq")),
                            $(".prize-name").append('<span class="value">' + e.faceValue + '\u5143</span>' +
                                '<span class="type">' + e.prizeName + "</span>")) : "INTEREST" === e.prizeType && ($(".explain").html(e.faceValue + "%加息券加息期限" + e.durationDesc + "天，有效期" + e.expireDesc + '天;<br/>可用于下次投资时使用可在"' +
                            '<a target="_blank" href="' + redirect.my("/account/") + '">个人中心</a>"-"' +
                            '<a target="_blank" href="' + redirect.my("/account/mykq") + '">我的卡券</a>"中查看'), $(".sign-get .button").attr("href", redirect.my("/account/mykq")),
                            $(".prize-name").append('<span class="value">' + e.faceValue + '%</span>' +
                                '<span class="type">' + e.prizeName + "</span>")),
                            $(".alert").show(),
                            $(".shadow").show(),
                            $(".sign-in").show(),
                            $(".sign-get").show()
                    }
                    $(".tab li").click(function() {
                        $(".tab li").removeClass("active"),
                            $(this).addClass("active"),
                            $(".list").hide(),
                            $("." + $(this).data("type")).show()
                    }),
                        $("#getSignIn").click(function() {
                            $.get("signin.json?version=1.0").then(function(a) {
                                e(a)
                            })
                        }),
                        $("#getSignIn1").click(function() {
                            $.get("signin1.json?version=1.0").then(function(a) {
                                e(a)
                            })
                        }),
                        $(".a li").click(function() {
                            var e = $(this).data("id"),
                                a = $(".q").data("id");
                            $.get("/activity/api/v2/questionBank/MYSELF/getRightQuestion?questionId=" + a + "&questionOptionId=" + e + "&version=1.0").then(function(e) {
                                if ($(".qa").hide(), e.success) if (e.data.isRight) $(".answer-right").show();
                                else {
                                    var a;
                                    switch (e.data.questionOption.questionNo) {
                                        case 0:
                                            a = "A";
                                            break;
                                        case 1:
                                            a = "B";
                                            break;
                                        case 2:
                                            a = "C";
                                            break;
                                        case 3:
                                            a = "D"
                                    }
                                    $("#rAnswer").html("正确答案是" + a),
                                        $(".answer-wrong").show()
                                }
                            })
                        }),
                        $(".close,.qa-result .btn-close,.no-prize .button").click(function() {
                            location.reload()
                        })
                })
            },
                {}]
        },
        {},
        [1]);