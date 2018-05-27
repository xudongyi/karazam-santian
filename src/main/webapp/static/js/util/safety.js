/**
 * Created by limat on 2017/4/19.
 */
// 左边导航栏创建
mnav.activity(5);
//个人资料
$.ajax({
    type: 'get',
    url: "userInfo.json",
    success: function (res) {
        var _data = res.data;
        var _loginName = _data.regName, //登录名
            _realStatus = _data.regName,  //实名状态
            _realName = _data.userName,  //真实姓名
            _idCard = _data.idNumber,  //身份证号
            _mobile = _data.mobile,  //手机号
            _mobileStatus = _data.regMobile,  //手机绑定状态
            _emailStatus = _data.regEmail //电子邮箱绑定状态
        //实名认证
        if (_realStatus == 0) {
            $(".realname span").html("保障账户安全，只有完成实名认证才能投资");
        } else if (_realStatus == 1) {
            $(".realname span").html(_realName + "&nbsp|&nbsp" + _idCard + "身份已认证");
            $(".realname a").attr("href", "/account/personal/safetyRealName");
            $(".realname a").html("查看");
        }
        //手机号码
        if (_mobileStatus == 0) {
            $(".safetyPhone span").html("账户资金变动实时短信通知");
        } else if (_mobileStatus == 1) {
            $(".safetyPhone span").html(_mobile);
            $(".safetyPhone a").html("查看");
        }
    },
    error: function (jqXHR, textStatus, errorThrown) {
        alert(errorThrown);
    }
});

//弹框
function alert(msg, url) {
    if (!$("#msgTips").length) {
        var alertHtml = '<div class="success" id="msgTips">' + msg + '</div>';
        $("body").append(alertHtml);
    } else {
        $("#msgTips").text(msg);
    }
    $("#msgTips").show();

    setTimeout(function () {
        $("#msgTips").hide();
    }, 1500)
    if (url != null && url != "") {
        setTimeout(function () {
            window.location = url;
        }, 2000)
    }
}