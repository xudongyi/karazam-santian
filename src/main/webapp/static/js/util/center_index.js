/**
 * Created by limat on 2017/4/19.
 */
// 左边导航栏创建
mnav.activity(1);
//托管信息
var depositStatus;
var depositSwitch;
var isFromLogin;
var depositStatusVo;
var civilSubjectType;
var certType;
//模拟滚动条
/*$(function () {
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

    depositStatus = $("#depositStatus").val();
    depositSwitch = $("#depositSwitch").val();
    isFromLogin = $("#isFromLogin").val();
    depositStatusVo = $('#depositStatusVo').val();
    civilSubjectType = $("#civilSubjectType").val();
    certType = $("#certType").val();
    // 0, "未开通";1, "未激活";2, "正常"
    //到账户总览页面时，只有从登陆页面跳过来，才会立马展示开通托管通信息，从其他页面跳过来，则不自动展示开通对话框
    //个人用户
    if (civilSubjectType == 1 && certType == 1) {
        if (isFromLogin && (depositStatus == 0 || depositStatus == 1 )) {
            $("#cunguanTips").fadeIn();
        }
    }


    $("#openNow").click(function () {
        $("#cunguanResult").show();
        window.open('/deposit/toActiviteOrRegist');
    })
});*/
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

var bindSelect = function (_obj) {
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
$(function () {
    bindSelect();
});
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
        url: 'receivableDetail4Calendar.json',
        data: {"year": year, "month": month},
        success: function (res) {
            //var this = _this;
            var _data = res.data;
            if (res.success == true) {
                if (_data.monthRecoverAmount != null && _data.monthRecoverAmount != undefined) {
                    $(".month-data span").eq(0).text(formatMoney(_data.monthRecoverAmount));
                } else {
                    $(".month-data span").eq(0).text("0");
                }
                if (_data.monthUnRecoverAmount != null && _data.monthUnRecoverAmount != undefined) {
                    $(".month-data span").eq(1).text(formatMoney(_data.monthUnRecoverAmount));
                } else {
                    $(".month-data span").eq(1).text("0");
                }
            } else {
            }
            dayShouldGet = _data != null ? _data.receivable4DayList : null;
            dayNoGet = _data != null ? _data.receivable4DayList : null;
            var newData = [];
            var arr = 0;
            if (_data) {
                if (_data.receivable4DayList) {
                    arr = _data.receivable4DayList.length;
                }
                for (i = 0; i < arr; i++) {
                    for (var key in _data.receivable4DayList[i]) {
                    }
                    newData = newData.concat({"day": _data.receivable4DayList[i].recoverDay});
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

        var dataLeftHtml = '<h3 class="font-gray" style="z-index:3;">应收金额(元)<i class="iconfont icon-tixing">&#xe606;</i><br/>' +
            '<span class="font-black DinFont">' + formatMoney(dayShouldGet[num].recoverAmount) + '</span>' +
            '<div class="tixing"><i></i>' +
            '到期应收(元)：' + formatMoney(dayShouldGet[num].expireRecoverAmount) + '<br/>' +
            '历史提前还款(元)：' + formatMoney(dayShouldGet[num].historyRecoveredAmount) + '(今日到期应回款中借款人已提前还款的部分)<br/>' +
            '<span class="font-black">应收金额=到期应收-历史提前还款</span>' +
            '</div></h3>' +
            '<h3  class="font-gray" style="z-index:1;">实收金额(元)<i class="iconfont icon-tixing">&#xe606;</i><br/>' +
            '<span class="font-black DinFont">' + formatMoney(dayNoGet[num].recoveredAmount) + '</span>' +
            '<div class="tixing"><i></i>' +
            '到期实收(元)：' + formatMoney(dayShouldGet[num].todayRecoveredAmount) + '<br/>' +
            '今日提前还款(元)：' + formatMoney(dayShouldGet[num].todayAdvanceAmount) + '(未来应收还款中借款人已提前至今日还款的部分)<br/>' +
            '<span class="font-black">实收金额=到期实收+今日提前还款</span>' +
            '</div></h3>';
        $("#dataLeft").append(dataLeftHtml);
        $("#today").html("<i class='rili'>" + thisDay + "</i>" + nowYear + "-" + nowMonth + "-" + thisDay);
        // 点击提醒查看详情
        $(".icon-tixing").hover(function (event) {
            $(this).siblings(".tixing").toggle();
        }, function () {
            $(this).siblings(".tixing").toggle();
        });
    } else {
        var emptyHtml = '<div class="empty"><img src="' + staticUrl + '/static/images/safe/empty.png" /><p>当天没有项目回款</p><p><a href="/list/showBidList"' +
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

// 页面效果js
var totalMoney = $("#total-money").text();
var totalInterst = $("#total-interest").text();
var leftMoney = $("#left-money").text();

$(".fa-eye").click(function () {
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

//tips hover
$(".userInfo li").mouseenter(function () {
    $(this).find(".tip_info").show();
    $(this).siblings().find(".tip_info").hide();
});
$(".userInfo li").mouseleave(function () {
    var _this = this;
    setTimeout(function () {
        $(_this).find(".tip_info").hide();
    }, 500)
});

//收款明细
$.ajax({
    type: 'post',
    url: 'receivablesDetailList.json',
    //data: {"year": year, "month": month},
    success: function (res) {
        //var this = _this;
        var _data = res.data;
        if (res.success == true) {
            var _collectHtml = '';
            var arr = _data.data != null ? _data.data.length : null;
            // console.log("收款数据："+_data.data);
            // console.log(arr);
            if (arr) {
                for (i = 0; i < arr; i++) {
                    var _periods = _data.data[i].periods, //期数
                        _assetId = _data.data[i].assetId, //标ID
                        _hashAssetId = _data.data[i].hashAssetId, //hash标ID
                        _period = _data.data[i].period, //当前期数
                        _assetName = _data.data[i].assetName,  //项目名称
                        _recoverTime = new Date(parseInt(_data.data[i].recoverTime)).format('yyyy-MM-dd'), //待收日期
                        _recoverAmount = _data.data[i].recoverAmount, //待收总额(元)or待收本息？
                        _recoverPrincipal = _data.data[i].recoverPrincipal, //待收本金(元)
                        _recoverInterest = _data.data[i].recoverInterest,  //待收利息(元)
                        _recoverStatus = '待回款';  //状态
                    for (var key in _data.data[i]) {
                        var viewUrl = urlbase + '/bid/showBidDetail?';
                        if (_hashAssetId) {
                            viewUrl += 'hash=' + _hashAssetId;
                        } else {
                            viewUrl += 'bid=' + _assetId;
                        }

                        _collectHtml = "<ul class='content'>" +
                            "<li class='tal'>" + _period + "/" + _periods + "</li>" +
                            "<li><a href='" + viewUrl + "'>" + formatBidName(_assetName) + "</a></li>" +
                            "<li>" + _recoverTime + "</li>" +
                            "<li class='tar'>" + formatMoney(_recoverAmount) + "</li>" +
                            "<li class='tar'>" + formatMoney(_recoverPrincipal) + "</li>" +
                            "<li class='tar'>" + formatMoney(_recoverInterest) + "</li>" +
                            "<li>" + _recoverStatus + "</li>" +
                            "</ul>";
                    }
                    // console.log(_collectHtml);
                    $(".answer").append(_collectHtml);
                }
            }
        } else {
        }

    },
    error: function (jqXHR, textStatus, errorThrown) {
        console.log(errorThrown);
    }
});

//资产明细
function createOption(sum, color, data, title) {
    var option = {
        tooltip: {
            trigger: 'item',
            formatter: "{b}:{c}"
        },
        itemStyle: {
            trigger: 'item',
            borderColor: "#3cf"
        },
        calculable: false,
        title: {
            text: title,
            subtext: sum,
            x: 185,
            y: 122,
            textAlign: 'center',
            itemGap: 10,
            textStyle: {
                fontSize: 14,
                color: '#9e9e9e',
                fontFamily: 'Microsoft YaHei',
                align: 'center',
                fontWeight: 'normal'
            },
            subtextStyle: {
                fontSize: 18,
                color: '#212121',
                fontFamily: 'DIN-Medium,sans-serif',
                align: 'center'

            }
        },
        color: color,
        series: [
            {
                name: '',
                type: 'pie',
                center: ['183', '142'],
                radius: ['72', '90'],
                itemStyle: {
                    normal: {
                        label: {
                            position: 'center',
                            show: false,
                            textStyle: {
                                fontSize: '16',
                                color: '#212121'
                            }
                        },
                        labelLine: {
                            show: false
                        },
                        borderWidth: 0,
                        borderColor: '#fff',
                        itemGap: 5,
                        padding: 10,
                        selectedMode: false
                    },
                    emphasis: {
                        padding: 0,
                        label: {
                            show: false,
                            distance: 10
                        }
                    }
                },
                data: data
            }
        ]
    };
    return option;
}

//点击托管弹框上面购买成功按钮
$("#openSucc").click(function () {
    $("#cunguanResult").hide();
    window.location.reload();
});

// 点击充值按钮进行判断
$("#recharge").click(function () {
    return verification(1);
});


// 点击提现按钮进行判断
$("#cash").click(function () {
    return verification(2);
});


// 点击银行卡按钮进行判断
$("li:contains('银行卡')").click(function () {
    return verification(3);
});


// 对投标输入框验证
function verification(type) {
    //企业用户和港澳台用户禁止，体现、充值、绑定银行卡
    if (civilSubjectType == 2 || (civilSubjectType == 1 && certType != 1)) {
        if (!depositStatusVo || depositStatusVo != 2) {
            return false;
        }
    }

    if (userInfoData.regName == 0) {// 未实名认证
        //showDialogBox(3);
        var url = "/account/personal/safetyRealName";
        if (depositSwitch == 1) {
            $("#cunguanTips").fadeIn();
        } else {
            submitTenderForm(url);
        }
        return false;
    } else if (type == 1) {// 实名认证并且请求充值
        return depositProcess("/account/easyPay.html");
    } else if (type == 2) {// 实名认证并且请求提现
        return depositProcess("/account/bankWithdrawal.html");
    } else if (type == 3) {// 实名认证并且查看银行卡信息
        return submitTenderForm("/account/bank.html");
    }
    else {
        return false;
    }
}

function depositProcess(url) {
    //托管开关关闭，获取托管开通后，则直接跳转到后台，后台根据用户的托管状态跳转到不通的页面
    if (depositSwitch == 0 || depositStatus == 2) {
        //激活后
        return submitTenderForm(url);
    }
    else if (depositSwitch == -1 || depositStatus == -1) { //未开通或者未激活时，弹出提示框
        showAlertBox("获取用户托管信息失败");
    } else if (depositStatus == 0 || depositStatus == 1) {
        $("#cunguanTips").fadeIn();
    }
}


function submitTenderForm(url) {
    var requestStr = url;
    window.location.href = requestStr;
}

$("body").click(function (event) {
    var len = $(".select_year,.select_month").find(event.target);
    if (len.length > 0) {
        return false;
    }
    $(".bd").hide();
    $("#scroll").hide();
});

//点击月度账单进行会员判断能否查看
$(".monthBill").click(function () {
    if (+totalMoney < 50000) {
        $("#vipAlert").show();
        setTimeout(function () {
            $("#vipAlert").hide();
        }, 2800)
        $(this).attr("href", "javascript:volid(0)");
    } else {
        $(this).attr("href", window.location.origin + "/account/monthBill");
    }
})