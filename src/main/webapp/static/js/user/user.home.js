$(function () {
    var Bool = false;
    var Scro = $("#scroll");
    var Scrp = $("#p");
    var Scrobd = $("#bd");
    var Scroul = $("#ul");
    var Scrp_Height = Scrp.outerHeight() / 2;
    var Num2 = Scro.outerHeight() - Scrp.outerHeight();
    var offsetX = 0;
    var offsetY = 0;
    Scrp.mousedown(function (e) {
        Bool = true;
    });
    $(document).mouseup(function () {
        Bool = false;
    });
    $(document).mousemove(function (e) {
        if (Bool) {
            var Num1 = e.clientY - Scro.position().top;
            var y = Num1 - Scrp_Height;
            if (y <= 1) {
                Scrll(0);
                Scrp.css("top", 1);
            } else if (y >= Num2) {
                Scrp.css("top", Num2);
                Scrll(Num2);
            } else {
                Scrll(y);
            }
        }
    });
    function Scrll(y) {
        Scrp.css("top", y);
        Scroul.css("margin-top", -(y / (Scro.outerHeight() - Scrp.outerHeight())) * (Scroul.outerHeight() - Scrobd.height()) / 2);
    }

    if (document.getElementById("scroll_bd").addEventListener)
        document.getElementById("scroll_bd").addEventListener('DOMMouseScroll', wheel, true);
    document.getElementById("scroll_bd").onmousewheel = wheel;
    var Distance = Num2 * 0.1;

    function wheel(e) {
        var evt = e || window.event;
        var wheelDelta = evt.wheelDelta || evt.detail;
        if (wheelDelta == -120 || wheelDelta == 3) {
            var Distances = Scrp.position().top + Distance;
            if (Distances >= Num2) {
                Scrp.css("top", Num2);
                Scrll(Num2);
            } else {
                Scrll(Distances);
            }
            return false;
        } else if (wheelDelta == 120 || wheelDelta == -3) {
            var Distances = Scrp.position().top - Distance;
            if (Distances <= 1) {
                Scrll(0);
                Scrp.css("top", 1);
            } else {
                Scrll(Distances);
            }
            return false;
        }
    }

    bindSelect();

    var totalMoney = $("#total-money").text();
    var totalInterst = $("#total-interest").text();
    var leftMoney = $("#left-money").text();
    $("#eye").click(function () {
        if ($(".total-data").hasClass('close-eye')) {
            $("#total-money").text(totalMoney);
            $("#total-interest").text(totalInterst);
            $("#left-money").text(leftMoney);
            $(".total-data").removeClass("close-eye");
            //$(this).html("&#xe612;");
            $(".total-data").removeClass("fz18");
            $("#eye").removeClass('fa fa-eye-slash');
            $("#eye").addClass('fa fa-eye');
        } else {
            $("ul.total-data li p span").not(".tixianBtn").text("*****");
            $("#eye").removeClass('fa fa-eye');
            $("#eye").addClass('fa fa-eye-slash');
            $(".total-data").addClass("close-eye");
            //$(this).html("&#xe613;");
            $(".total-data").addClass("fz18");
        }
    });

    $("#record-detail").click(function () {
        $(this).empty();
        if (!$(this).hasClass("fa-th-large")) {
            $(this).removeClass('fa fa-reorder');
            $(this).addClass('fa fa-th-large');
        } else if ($(this).hasClass("fa-th-large")) {
            $(this).removeClass("fa fa-th-large ");
            $(this).addClass('fa fa-reorder');
        }
        $(".month-data,.day-data").toggle();
        $(".detail-content").toggle();
    });

    dataLoad('recovering');

});

//阻止滚动条滚动
// left: 37, up: 38, right: 39, down: 40,
// spacebar: 32, pageup: 33, pagedown: 34, end: 35, home: 36
var keys = [37, 38, 39, 40];

function preventDefault(e) {
    e = e || window.event;
    if (e.preventDefault)
        e.preventDefault();
    e.returnValue = false;
}

function keydown(e) {
    for (var i = keys.length; i--;) {
        if (e.keyCode === keys[i]) {
            preventDefault(e);
            return;
        }
    }
}
function disable_scroll() {
    if (window.addEventListener) {
        window.addEventListener('DOMMouseScroll', wheel, false);
    }
    window.onmousewheel = document.onmousewheel = wheel;
    document.onkeydown = keydown;
}
function wheel(e) {
    preventDefault(e);
}
function enable_scroll() {
    if (window.removeEventListener) {
        window.removeEventListener('DOMMouseScroll', wheel, false);
    }
    window.onmousewheel = document.onmousewheel = document.onkeydown = null;
}
$(".select dd").mouseenter(function () {
    disable_scroll();
});
$(".select dd a").click(function () {
    enable_scroll();
});
// Select Menu
ie6 = false;
if (!-[1,] && !window.XMLHttpRequest) {
    ie6 = true;
}
var isIE = !!window.ActiveXObject;
var isIE6 = isIE && !window.XMLHttpRequest;
var gtIE8 = isIE && !!document.documentMode;
var isIE7 = isIE && !isIE6 && !gtIE8 || (document.documentMode == 7);

function bindSelect(_obj) {
    var obj = (arguments.length != 0) ? $(_obj) : $(".select");
    obj.delegate("dd a", "click", function () {
        var curselect = $(this).parents(".select");
        if (this.selected == "selected") {
            //curselect.parents("li").css({zIndex:0});
            var cpli = curselect.parents("li");
            cpli.css({zIndex: cpli.attr("data-default-zindex") != undefined ? cpli.attr("data-default-zindex") : ""});
            $(this).parents("dd").hide();
            $(this).parents(".bd").next("#scroll").hide();
            return;
        }
        var text = $(this).html();
        $(this).parents(".bd").prev("dt").find("a").html(text);

        $(this).parents("dd").find("a").each(function () {
            this.selected = "";
        });
        this.selected = "selected";
        $(this).parent().addClass("selected").siblings("li").removeClass("selected");
        //curselect.parents("li").css({zIndex:0});
        var cpli = curselect.parents("li");
        cpli.css({zIndex: cpli.attr("data-default-zindex") != undefined ? cpli.attr("data-default-zindex") : ""});
        curselect.siblings("input[name='" + curselect.attr("name") + "']").val($(this).attr("value"));
        curselect.removeClass("select_expand").trigger("change");

        $(this).parents("dd").hide();
        $(this).parents(".bd").next("#scroll").hide();
    });
    //}).end().find("dt").click(function(){
    obj.find("dt").click(function () {
        var curselect = $(this).parent(".select");
        if (curselect.is(".disabled")) {
            return false;
        }
        curselect.css({zIndex: 888}).find("dd").toggle();
        curselect.css({zIndex: 888}).find("#scroll").toggle();
        curselect.toggleClass("select_expand");
        curselect.parents("li").css({zIndex: 889});
        //Start: Options Width & Height Fix
        curselect.find("ul").css("width", "");
        curselect.find("dd").css({width: "", height: "", overflowY: "visible"});
        var ul_w = curselect.find("ul").width();
        var ul_h = curselect.find("ul").height();
        var dt_w = curselect.width();
        var max_w = null;
        var max_droph = parseInt(curselect.find("dd").attr("data-max-height") || curselect.find("dd").css("maxHeight") || curselect.find("dd").css("ie6maxheight") || 300);
        var overflowY = "hidden";
        if (ul_h - max_droph > 1) {
            ul_w += 15;
            ul_h = max_droph;
            //overflowY = "auto";
        }
        if (ul_w < dt_w) {
            curselect.find("ul").css({width: dt_w});
            ul_w = dt_w;
        }
        curselect.find("dd").css({width: ul_w, height: ul_h, overflowY: overflowY});
        if (ie6) {
            max_w = curselect.attr("data-max-width") || null;
            if (!max_w) {
                max_w = curselect.css("maxWidth") || curselect.css("width");
                max_w = ~~parseInt(max_w);
            }
            var dta = $(this).find("a");
            var dta_bl = ~~parseInt(dta.css("borderLeftWidth"));
            var dta_br = ~~parseInt(dta.css("borderRightWidth"));
            var dta_pl = ~~parseInt(dta.css("paddingLeft"));
            var dta_pr = ~~parseInt(dta.css("paddingRight"));
            var dta_ml = ~~parseInt(dta.css("marginLeft"));
            var dta_mr = ~~parseInt(dta.css("marginRight"));
            var sumw = dta_bl + dta_br + dta_pl + dta_pr + dta_ml + dta_mr;
            dta.css("width", "").css({width: max_w - sumw});
        }
        //End: Options Width & Height Fix
    });
    obj.each(function () {
        var val = $(this).find("dt a").html();
        $(this).find("dd a").each(function () {
            if ($(this).html() == val) {
                this.selected = "selected";
                var curselect = $(this).parents(".select");
                $(this).parent().addClass("selected");
                curselect.siblings("input[name='" + curselect.attr("name") + "']").val($(this).attr("value"));
            }
        });
    });
}

//全局参数
var dayShouldGet, dayNoGet, nowMonth, nowYear;
// 日历创建
var data = [];

var datapicker = new DatePicker('datapicker', data, function (year, month, render) {
    var _this = this;
    //$("#selectYear").val(year);
    //$("#selectMonth").val(month);
    $("#selectYearTxt").val(year);
    $("#selectMonthTxt").val(month);
    $("#selectYear").html(year + "年");
    $("#selectMonth").html(month + "月");
    nowMonth = month;
    nowYear = year;

    $.ajax({
        type: 'post',
        dataType: "JSON",
        url: ctx + '/uc/profit/json',
        data: {"year": year, "month": month},
        success: function (res) {
            //var this = _this;
            var _data = res.data;
            if (res.success == true) {
                if (_data.recoverAmount != null && _data.recoverAmount != undefined) {
                    $(".month-data span").eq(0).text(currency(_data.recoverAmount));
                } else {
                    $(".month-data span").eq(0).text("0");
                }
                if (_data.unRecoverAmount != null && _data.unRecoverAmount != undefined) {
                    $(".month-data span").eq(1).text(currency(_data.unRecoverAmount));
                } else {
                    $(".month-data span").eq(1).text("0");
                }
            } else {
            }
            dayShouldGet = _data != null ? _data.detailProfits : null;
            dayNoGet = _data != null ? _data.detailProfits : null;
            var newData = [];
            var arr = 0;
            if (_data) {
                if (_data.detailProfits) {
                    arr = _data.detailProfits.length;
                }
                for (i = 0; i < arr; i++) {
                    for (var key in _data.detailProfits[i]) {
                    }
                    newData = newData.concat({"day": _data.detailProfits[i].recoverDay});
                }
            }
            render.call(_this, year, month, newData);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });

});

//有数据的日期点击后显示对应数据
$(document).on('click', '.paituodai-datepicker-day span', function () {
    $("#dataLeft").empty();
    $(this).addClass("today").siblings().removeClass("today");
    var thisDay = $(this).children("a").text();
    var nowDay = new Date().getDate();
    if ($(this).hasClass("hasdata")) {
        var thisDay = $(this).children("a").text();
        var num = $(this).attr("data-index");
        var dataLeftHtml = '<h3 class="font-gray" style="z-index:3;">应收金额(元)<i class="fa fa-question-circle-o"></i><br/>' +
            '<span class="font-black DinFont">' + currency(dayShouldGet[num].recoverAmount) + '</span>' +
            '<div class="tixing"><i></i>' +
            '到期应收(元)：' + currency(dayShouldGet[num].expireRecoverAmount) + '<br/>' +
            '历史提前还款(元)：' + currency(dayShouldGet[num].historyRecoveredAmount) + '(今日到期应回款中借款人已提前还款的部分)<br/>' +
            '<span class="font-black">应收金额=到期应收-历史提前还款</span>' +
            '</div></h3><br/>' +
            '<h3  class="font-gray" style="z-index:1;">实收金额(元)<i class="fa fa-question-circle-o"></i><br/>' +
            '<span class="font-black DinFont">' + currency(dayNoGet[num].recoveredAmount) + '</span>' +
            '<div class="tixing"><i></i>' +
            '到期实收(元)：' + currency(dayShouldGet[num].todayRecoveredAmount) + '<br/>' +
            '今日提前还款(元)：' + currency(dayShouldGet[num].todayAdvanceAmount) + '(未来应收还款中借款人已提前至今日还款的部分)<br/>' +
            '<span class="font-black">实收金额=到期实收+今日提前还款</span>' +
            '</div></h3>';
        $("#dataLeft").append(dataLeftHtml);
        $("#today").html("<i class='rili'>" + thisDay + "</i>" + nowYear + "-" + nowMonth + "-" + thisDay);
        // 点击提醒查看详情
        $(".fa-question-circle-o").hover(function (event) {
            $(this).siblings(".tixing").toggle();
        }, function () {
            $(this).siblings(".tixing").toggle();
        });
    } else {
        var emptyHtml = '<div class="empty"><img src="' + staticUrl + '/static/images/safe/empty.png" /><p>当天没有项目回款</p><p><a href="/investment"' +
            ' class="blueColor">快去投资</a>吧...</p> </div>'
        $("#dataLeft").append(emptyHtml);
        if (thisDay == "今") {
            $("#today").html("<i class='rili'>" + thisDay + "</i>" + nowYear + "-" + nowMonth + "-" + nowDay);
        } else {
            $("#today").html("<i class='rili'>" + thisDay + "</i>" + nowYear + "-" + nowMonth + "-" + thisDay);
        }
    }

});


//上下个月切换
$(".preBtn,.nextBtn").on("click", function () {
    var _year = datapicker.slideDate.year;
    var _month = datapicker.slideDate.month;
    $('#selectMonthTxt').val(_month);
    $("#selectMonth").html(_month + "月");
    $('#selectYearTxt').val(_year);
    $("#selectYear").html(_year + "年");

});
//今天对应的数据（应收金额、未收金额）
function todayDate() {
    for (i = 0; i < $(".paituodai-datepicker-day span").length; i++) {
        if ($(".paituodai-datepicker-day span").eq(i).hasClass("today")) {
            $(".paituodai-datepicker-day span").eq(i).trigger("click");
            return false;
        }
    }
    setTimeout(todayDate, 100);
}
todayDate();

// 日历左侧内容
var today = new Date();
var thisYear = today.getFullYear();
var thisMonth = today.getMonth() + 1;
var thisDay = today.getDate();
$("#today").html("<i class='rili'>" + thisDay + "</i>" + thisYear + "-" + thisMonth + "-" + thisDay);
$("#selectMonth").html(thisMonth + "月");
$('#selectYearTxt').val(thisYear);
$('#selectMonthTxt').val(thisMonth);
datapicker.toNewYearAndMonth(thisYear, thisMonth);
//年月变动时日历的反应
//$('#selectYearTxt').on('change', selectChange);
$('#selectMonthTxt').on('change', selectChange);

$('.select dd a').on('click', selectChange);

function selectChange() {
    var _value = $(this).attr("data-value");
    $(this).parents(".select").prev(".input_txt").val(_value);
    var year = $('#selectYearTxt').val();
    var month = $('#selectMonthTxt').val();
    datapicker.toNewYearAndMonth(year, month);
}

$("body").click(function (event) {
    var len = $(".select_year,.select_month").find(event.target);
    if (len.length > 0) {
        return false;
    }
    $(".bd").hide();
    $("#scroll").hide();
});

function dataLoad(state) {
    var params = $.extend({
        page: 1,
        rows: 5
    }, {
        filter_GED_pay_date: datetimeFormatter(getCurrentMonthFirst(), "yyyy-MM-dd 00:00:00"),
        filter_LED_pay_date: datetimeFormatter(getCurrentMonthLast(), "yyyy-MM-dd 23:59:59")
    });
    $.getJSON(
        ctx + '/uc/recovery/' + state,
        params,
        function (res) {
            if (res.total > 0) {
                $('.answer').show();
                $('#pagination').show();
                $('#recoveriesEmpty').hide();
            } else {
                $('.answer').hide();
                $('#pagination').hide();
                $('#recoveriesEmpty').show();
            }
            var tpl = document.getElementById("recoveryTpl");
            laytpl(tpl.innerHTML).render(res, function (string) {
                $('#recoveryCont').html(string);
            });
        }
    );
}