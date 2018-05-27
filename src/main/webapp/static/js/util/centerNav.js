/**
 * Created by limat on 2017/4/19.
 */
//placeholder兼容IE8
$(function () {
    if (!('placeholder' in document.createElement('input'))) {
        var titleName = $(".answer ul li div.titleName").text();
        $(".answer ul li div.titleName").text(titleName.substring(0, 10));
        $('input[placeholder],textarea[placeholder]').each(function () {
            var that = $(this),
                text = that.attr('placeholder');
            if (that.val() === "") {
                that.val(text).addClass('placeholder');
            }
            that.focus(function () {
                    if (that.val() === text) {
                        that.val("").removeClass('placeholder');
                    }
                })
                .blur(function () {
                    if (that.val() === "") {
                        that.val(text).addClass('placeholder');
                    }
                })
                .closest('form').submit(function () {
                if (that.val() === text) {
                    that.val('');
                }
            });
        });
    }

})

function CenterNav(setting) {
    //this.iconfont = setting.iconfont;
    //this.name = setting.name;
    //this.icon = setting.icon;
    this.init = function () {
        for (var i = 0; i < 3; i++) {
           console.log("初始化");
        }
    };
    this.activity = function (n) {
        $("#mNavId ul li").eq(n - 1).addClass("cur");
    }
}

var setting = window.centerNavSetting;

var mnav = new CenterNav(setting);
mnav.init();
$("#mNavId ul li").click(function () {
    var cur = $(this).index();
    $(this).addClass("cur").siblings().removeClass("cur");
    window.location.href = setting.href[cur];
});

//首页判断是否绑定认证
function changeStatus(status, classname, tipinfo, url) {
    var tipHtml1 = "<div class='tip_info tl' style='font-size: 12px'>您还未" + tipinfo + "<a href=" + url + ">管理</a></div>";
    var tipHtml2 = "<div class='tip_info tl' style='font-size: 12px'>您已" + tipinfo + "<a href=" + url + ">查看</a></div>";
    if (status == 0) {
        $(classname).addClass("unbind");
        $(classname).parent().append(tipHtml1);
    } else if (status == 1) {
        $(classname).addClass("hasbind");
        $(classname).parent().append(tipHtml2);
    }
};

//全局用户信息
var userInfoData;
//用户个人信息
$.ajax({
    type: 'get',
    url: 'userInfo.json',
    success: function (res) {
        var _data = res.data;
        var _level = "1",
            _headImgUrl = _data.headImgUrl,
            _uid = _data.uid,
            _username = _data.userName,
            _realStatus = _data.regName,
            _bankStatus = _data.regBank,
            _mobile = _data.mobile,
            _mobileStatus = _data.regMobile,
            _payPass = _data.payPwd,
            _accountType = _data.accountType;
        _depositStatus = _data.depositStatus;
        userInfoData = _data;
        if (_headImgUrl != null && _headImgUrl != "") {
            $("#headImg").attr("src", _headImgUrl);
        }
        if (_level != null && _level != "") {
            $("#level").html("");
        }
        if (_mobile != undefined && _mobile != null) {
            _mobile = tools.hideTelephone(_mobile);
            $("#loginName").html(_mobile);
        } else {
            $("#loginName").html(_username);
        }
        ;
        var realnameUrl = window.action.rootPath + window.action.interface.personalSafety;
        var bankUrl = window.action.rootPath + window.action.interface.personalSafety;
        var mobileUrl = window.action.rootPath + window.action.interface.personalSafety;
        var payPassUrl = window.action.rootPath + window.action.interface.personalSafety;
        var cunguanUrl = window.action.rootPath + window.action.interface.personalSafety;
        changeStatus(_realStatus, "#realname", "进行实名认证", realnameUrl);
        changeStatus(_bankStatus, "#bankAuth", "绑定银行卡", bankUrl);
        changeStatus(_mobileStatus, "#mobile", "绑定手机", mobileUrl);
        if (_accountType == 1) {
            var tipHtml1 = "<div class='tip_info tl' style='font-size: 12px;padding-left: 10px;'>您还未开通托管账户<a href=" + cunguanUrl + ">开通</a></div>";
            var tipHtml2 = "<div class='tip_info tl' style='font-size: 12px'>您已开通托管账户<a href=" + cunguanUrl + ">查看</a></div>";
            if (_depositStatus == 0 || _depositStatus == 1) {
                $("#cunguan").addClass("unbind");
                $("#cunguan").parent().append(tipHtml1);
            } else if (_depositStatus == 2) {
                $("#cunguan").addClass("hasbind");
                $("#cunguan").parent().append(tipHtml2);
                _payPass = 1;
            }
        }
        changeStatus(_payPass, "#payPass", "设置交易密码", payPassUrl);
    },
    error: function (jqXHR, textStatus, errorThrown) {
        alert(errorThrown);
    }
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

function getDateRangeStartTime(time) {
    var startTime = '';
    var currentTime = new Date();
    switch (time) {
        case '全部':
        {
            return startTime;
            break;
        }
            ;
        case '近1个月':
        {
            currentTime = new Date(currentTime.setMonth(currentTime.getMonth() - 1));
            break;
        }
            ;
        case '近3个月':
        {
            currentTime = new Date(currentTime.setMonth(currentTime.getMonth() - 3));
            break;
        }
            ;
        case '近1年':
        {
            currentTime = new Date(currentTime.setFullYear(currentTime.getFullYear() - 1));
            break;
        }
            ;
    }
    return currentTime.format('yyyy-MM-dd 00:00:00');
}

// 列表页面类型展示后，点击其他地方消失
$(function () {
    $(document).click(function (event) {
            event = event || window.event;
            var target = event.target || event.srcElement;
            var title = $(target).parents(".selector-title");
            var son = $(target).parents(".selector-son");
            if (title.size() == 0 && son.size() == 0) {
                $(".selector-son").hide();
            }
        }
    )
})

$(function () {
    var times = 1;
    var max = 10;
    var fun = function () {
        if (times > max) {
            clearInterval(interval);
        }
        else {
            var rightheight = $(".memberContent").outerHeight() > 900 ? $(".memberContent").outerHeight() : 900;
            var leftheight = $(".memberLeft").outerHeight() > 900 ? $(".memberLeft").outerHeight() : 900;
            $(".memberLeft").animate({"height": (rightheight > leftheight ? rightheight : leftheight) + "px"}, 50);
            times++;
        }
    }
    var interval = setInterval(fun, 100);
})



