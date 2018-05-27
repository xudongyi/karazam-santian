/************limate 20170518  登录注册***************/
var regrphone = /^1[3|4|5|7|8][0-9]{9}$/;//验证手机号码
var registerRphone=0;
$(document).ready(function () {
    var rsaKey = new RSAKey();
    rsaKey.setPublic(b64tohex(modules), b64tohex(exponent));
    hiddenrecom();
    $("#regname").keyup(function () {
        var regname = $.trim($("#regname").val());
        $("#regname").val(regname.replace(/[^\d]/g, ""));
    });
});

$(function () {
    var hrefName = window.location.href;
    var lindex0 = hrefName.indexOf('login');
    var lindex1 = hrefName.indexOf('Register');

    if (lindex0 != -1) {//判断是登录还是注册

        tophref();
        $('.header-top .nav-topR li.dropdown').hover(function () {
            $(this).find('.dropdown-menu').stop(true, true).delay(200).fadeIn(500);
        }, function () {
            $(this).find('.dropdown-menu').stop(true, true).delay(200).fadeOut(500);
        });
        if ($.cookie("cookieenrolname")) {
            $("#enrolname").val($.cookie("cookieenrolname"));
        } else {
            //$("#ckbox:checked").removeAttr('checked');
        }
    } else {
        if (lindex1 != -1) {

            tophref();
            $('.header-top .nav-topR li.dropdown').hover(function () {
                $(this).find('.dropdown-menu').stop(true, true).delay(200).fadeIn(500);
            }, function () {
                $(this).find('.dropdown-menu').stop(true, true).delay(200).fadeOut(500);
            });
            loginCount(2);
            var rcode = getvParam("rphone");
            if (rcode == undefined || rcode == null || rcode == "") {

            } else {
                $("#recomcode").val(rcode);
                $("#recomcode").attr("disabled", true);
                checkrphone(rcode);
            }
        }
    }

});


//注册
function registerbtn() {
    var rsaKey = new RSAKey();
    rsaKey.setPublic(b64tohex(modules), b64tohex(exponent));
    //loginCount(2);disabled
    $(".regBtna").addClass("disabled");
    $("#serverErrorMsg").slideUp();
    var recomcode = getvParam("rphone");
    var mark = getvParam("mark");
    if (recomcode == null) {
        recomcode = $("#recomcode").val();
    }
    var mcode = $("#mcode").val();
    var rphone = $("#recomcode").val();
    var enrolname = $("#regname").val();
    var password = $("#regpassword").val();
    var password1 = $("#regpassword1").val();
    var email = $("#email").val();
    //var rphone = $("#recomcode").val();
    var checkBox = $("#checkBox").attr("checked");
    checkRegName();//验证用户名
    if (registerName == 1) {
        $(".regBtna").removeClass("disabled");
        return;
    }
    if ("" == mcode) {
        $(".regBtna").removeClass("disabled");
        $("#errorcapcha").text("请输入验证码!");
        return;
    }
    //验证密码
    checkEnrolpassword("regpassword", "errorregpwd");
    if (loginPwd == 1) {
        $(".regBtna").removeClass("disabled");
        return;
    }
    //验证确认密码
    checkRegPwdAgin("regpassword", "regpassword1", "errorregpwd1");
    if (registerPwd1 == 1) {
        $(".regBtna").removeClass("disabled");
        return;
    }
    //验证邮箱
    checkEmail();
    if (registerEmail == 1) {
        $(".regBtna").removeClass("disabled");
        return;
    }
    //验证推荐码
    checkrphone($("#recomcode").val());
    if (registerRphone == 1) {
        $(".regBtna").removeClass("disabled");
        return;
    }
//	if($("#checkcode1").val()==1){
//		if(valCode("veryCode2","errorverycode2",1)==false){
//			return;
//		}
//	}
    var checkLength = $("#incheck:checked").length;
    if (checkLength == 0) {
        $("#serverErrorMsg").html("请先阅读并同意《注册及服务协议》");
        $("#serverErrorMsg").slideDown();
        $(".regBtna").removeClass("disabled");
        return;
    }
    var param = "";
//	var backUrl = getHpoleURL()+"/payfor/httpsfor2.jsp";
    if (mark == 1) {
        param = {
            'type':$('input:radio:checked').val(),
            'mobile': $.trim(enrolname),
            'password': hex2b64(rsaKey.encrypt(password)),
            // 'confpass': password1,
            'referrer': $.trim(recomcode),
            'email': $.trim(email),
            'rphone': $.trim(rphone),
            'captcha': mcode,
            'mark': mark,
            'modules': modules,
            'exponent': exponent
        };
    } else {
        param = {
            'type':$('input:radio:checked').val(),
            'mobile': $.trim(enrolname),
            'password': hex2b64(rsaKey.encrypt(password)),
            // 'confpass': password1,
            'referrer': $.trim(recomcode),
            'email': $.trim(email),
            'rphone': $.trim(rphone),
            'captcha': mcode,
            'modules': modules,
            'exponent': exponent
        };
    }
    $.ajax({
        type: "post",
        dataType: "json",
        url: ctx + "/regist/do",
        data: param,
        cache: false,
        success: function(data) {
            if (data.status == "success") {
                $('#myModal').modal({
                    backdrop:'static'
                });
            } else {
                $(".regBtna").removeClass("disabled");
                //注册失败页面
                $("#regpassword").val("");
                $("#regpassword1").val("");
                $("#email").val("");
                $("#recomcode").val("");
                $("#serverErrorMsg").html(data.message==""?"注册失败，请刷新页面重试":data.message);
                $("#serverErrorMsg").slideDown();
            }
        },
        error: function() {
            $("#serverErrorMsg").html("注册失败，请刷新页面重试");
            $("#serverErrorMsg").slideDown();
        }
    });
}


//登录
$("#login").click(function () {
    login();
    $("#serverErrorMsg").slideUp();
});

function hiddenrecom() {
    var recomcode = getvParam("rphone");
    if (recomcode != null) {
        $("#recomdiv").hide();
    }
}


//回车键登录
$(function () {
    document.onkeydown = function (e) {
        var ev = document.all ? window.event : e;
        if (ev.keyCode == 13) {
            if ($("#passwords").val() == undefined || $("#enrolname").val() == undefined) {
                return;
            }
            if ($("#enrolname").val() != "" || $("#passwords").val() != "") {
                checkEnrolpassword("passwords", "errorpassword");
                if (loginPwd == 1) {
                    return;
                }
                var str = document.getElementById("tzj_zz_login").attributes["onclick"].nodeValue;
                if (str == 'tzjLogin()') {
                    tzjLogin();
                } else {
                    login();
                }
            }
        }
    }
});


$("#ckbox").click(function () {
    var cookie = $.cookie("cookieenrolname"); //读取cookie
    var checkLength = $("#ckbox:checked").length;
    if (checkLength == 0) {
        if (cookie) {
            $.cookie("cookieenrolname", null, {path: '/'});
        }
    }
});
//用户名存cookie
function cookiename() {
    var cookie = $.cookie("cookieenrolname"); //读取cookie
    var checkLength = $("#ckbox:checked").length;
    if (checkLength == 0) {
        if (cookie) {
            $.cookie("cookieenrolname", null, {path: '/'});
        }
    } else {
        if (cookie) {
            if (cookie == $("#enrolname").val()) {
            } else {
                $.cookie("cookieenrolname",//写入cookie
                    $("#enrolname").val(),//需要cookie写入的业务
                    {
                        "path": "/", //cookie的默认属性
                        "expires": 100 //有效天数
                    });
            }

        } else {
            $.cookie("cookieenrolname",//写入cookie
                $("#enrolname").val(),//需要cookie写入的业务
                {
                    "path": "/", //cookie的默认属性
                    "expires": 100 //有效天数
                });
        }

    }
}

function login() {
    cookiename();
    var enrolname = $("#enrolname").val();
    var password = $("#passwords").val();
    var captcha = $("#veryCode1").val();
    checkEnrolname();
    checkEnrolpassword("passwords", "errorpassword");
    if (loginPwd == 1) {
        return;
    }
    doLogin(enrolname, password, captcha);
}

/**
 * 登录操作。
 * @param enrolname 用户名。
 * @param password 密码。
 */
function doLogin(enrolname, password, captcha) {
    var rsaKey = new RSAKey();
    rsaKey.setPublic(b64tohex(modules), b64tohex(exponent));
    var url = ctx + "/login";
    // var pram = {"enrolname": $.trim(enrolname), "password": password, "type": "0", "captcha":captcha};
    $.ajax
    ({
        type: "post",
        dataType: "json",
        url: url,
        data: {
            username: $.trim(enrolname),
            password: hex2b64(rsaKey.encrypt(password)),
            type: $('input:radio:checked').val(),
            redirectUrl : redirectUrl
        },
        cache: false,
        beforeSend: function (request, settings) {
            request.setRequestHeader("token", $.cookie("token"));
        },
        success: function (result) {
            if (result.status == "success") {
                var top = getTopWinow();
                var redirectUrl = result.data.redirectUrl;
                if (redirectUrl != undefined && redirectUrl != '') {
                    top.location.href = ctx + redirectUrl;
                } else {
                    top.location.href = ctx + "/uc";
                }
            }
            else {
                $("#serverErrorMsg").html(result.message);
                $("#serverErrorMsg").slideDown();
                modules = result.data.modules;
                exponent = result.data.exponent;
            }
        },
        error: function () {
            $("#serverErrorMsg").html(result.message);
            $("#serverErrorMsg").slideDown();
        }
    });
};

//登录用户名鼠标移动
$("#enrolname").blur(function () {
    checkEnrolname();
});

//登录用户名获取焦点
$("#enrolname").focus(function () {
    $("#serverErrorMsg").slideUp();
});

//登录密码鼠标移动
$("#passwords").blur(function () {
    checkEnrolpassword("passwords", "errorpassword");
});


//登录密码获取焦点
$("#passwords").focus(function () {
    $("#serverErrorMsg").slideUp();
});

//登录密码鼠标移动
$("#veryCode1").focus(function () {
    $("#serverErrorMsg").slideUp();
});

//注册用户名鼠标移动
$("#regname").blur(function () {
    checkRegName();
});

//注册用户名获取焦点
$("#regname").focus(function () {
    $("#serverErrorMsg").slideUp();
});

//注册用户名获取焦点
$("#mcode").focus(function () {
    $("#serverErrorMsg").slideUp();
});


//注册密码鼠标移动
$("#regpassword").blur(function () {
    checkEnrolpassword("regpassword", "errorregpwd");
});

//注册密码获取焦点
$("#regpassword").focus(function () {
    $("#serverErrorMsg").slideUp();
});
//注册确认密码鼠标移动
$("#regpassword1").blur(function () {
    checkRegPwdAgin("regpassword", "regpassword1", "errorregpwd1");
});
//注册确认密码获取焦点
$("#regpassword1").focus(function () {
    $("#serverErrorMsg").slideUp();
});
//注册邮箱鼠标移动
$("#regpassword1").blur(function () {
    checkEmail();
});

//注册推荐码鼠标移动
$("#recomcode").blur(function () {
    checkrphone($(this).val());
});
//注册推荐码获取焦点
$("#recomcode").focus(function () {
    $("#serverErrorMsg").slideUp();
});

//个人复选框选中事件
$("#incheck").click(function () {

    $("#serverErrorMsg").slideUp();
    var checkLength = $("#incheck:checked").length;
    if (checkLength == 0) {
        $(".regBtna").addClass("disabled");
        $("#serverErrorMsg").html("请先阅读并同意《注册及服务协议》");
        $("#serverErrorMsg").slideDown();
    } else {
        $(".regBtna").removeClass("disabled");
    }

});


//验证登录用户名
function checkEnrolname() {
    if ("" == $("#enrolname").val()) {
        $("#errorenrolname").text("手机号不能为空");
        loginName = 1;
        return;
    }
    checkerolname($("#enrolname").val(), 1);//判断登录用户是否存在
    if (loginName == 1) {//如果为一则没有这个用户
        return;
    } else {
        $("#errorenrolname").text("");
        loginName = 0;
    }
}

$("#regyanzbtn").on("click", function () {
    $(this).addClass("disabled");
    checkRegName();
    if (registerName == 1) {
        $(this).removeClass("disabled");
        return;
    } else {
        //sendMsgauthenode("portalMobileRecomService:sendMobileRecom","regname","regyanzbtn",0,"");
        showVerCode("portalMobileRecomService:sendMobileRecom", "portalMobileRecomService:sendMobileRecom", "regname", "regyanzbtn", 0, "", "LoginYZM");//显示图形验证码
    }
});
//验证注册用户名
function checkRegName() {
    var regname = $("#regname").val();
    if ("" == $("#regname").val()) {
        $("#errorregname").text("手机号不能为空");
        registerName = 1;
        return;
    } else if (regrphone.test($.trim(regname)) == false) {
        $("#errorregname").text("请输入正确的手机号码！");
        registerName = 1;
        return;
    } else {
        console.log("11111");
        checkerolname($("#regname").val(), 2);//判断注册用户是否存在
        if (registerName == 1) {//如果为1则有这个用户
            return;
        } else {
            $("#errorregname").text("");
        }
    }
}

//验证确认密码
function checkRegPwdAgin(pwd, pwdagin, erroppwdagin) {
    var password = $("#" + pwd).val();
    var password1 = $("#" + pwdagin).val();
//	alert(password);
//	alert(password1);
    var pwdReg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$/;
    if ("" == password1) {
        $("#" + erroppwdagin).text("确认密码不能为空");
        registerPwd1 = 1;
        return;
    } else if(!pwdReg.test(password1)){
        $("#" + erroppwdagin).text("密码必须是数字和字母的组合");
        loginPwd = 1;
        return;
    }else if (password != password1) {
        $("#" + erroppwdagin).text("请输入与登录密码一致的内容");
        registerPwd1 = 1;
        return;
    } else {
        $("#" + erroppwdagin).text("");
        registerPwd1 = 0;
    }
}

//验证邮箱
function checkEmail() {
    var email = $("#email").val();
    if ("" != email) {
        if (regemail.test($.trim(email)) == false) {
            $("#erroremail").text("请输入正确的邮箱");
            registerEmail = 1;
            return;
        } else {
            $("#erroremail").text("");
            registerEmail = 0;
        }
    } else {
        $("#erroremail").text("");
        registerEmail = 0;
    }
}
//recomcode
function checkrphone(recomcode) {
    if (recomcode != null) {
        if (recomcode.length > 5) {
            if (regrphone.test($.trim(recomcode)) == false) {
                $("#tjrid").html("");
                $("#errorrecomcode").text("请输入正确的手机号");
                registerRphone = 1;
                return;
            }
        }
        if (recomcode != "") {
            checkphoneC(recomcode);
            if (registerRphone == 1) {
                return;
            } else {
                $("#errorrecomcode").text("");
                registerRphone = 0;
            }
        } else {
            $("#tjrid").html("");
        }
    } else {
        $("#tjrid").html("");
        $("#errorrecomcode").text("");
        registerRphone = 0;
    }
}

function checkphoneC(recomcode) {
    var url = ctx + "/regist/check_referrer";
    var pram = {'phone': $.trim(recomcode)};
    $.ajax({
        type: "post",
        dataType: "json",
        url: url,
        data: pram,
        async: false,
        success: function (datas) {
            if (datas == true) {
                $("#errorrecomcode").text("");
                registerRphone = 0;
            } else {
                $("#tjrid").html("");
                $("#errorrecomcode").text("您输入的推荐码或手机号不存在");
                registerRphone = 1;
                return;
            }
        }
    });
}

// 忘记密码页面
function forget() {
    window.open(getHpoleURL() + "/MemberCenter/forgotPassword.html");
    //location.href=getHpoleURL()+"/MemberCenter/forgotPassword.html";
}

// 注册页面
function register() {
    window.open(getHpoleURL() + "/MemberCenter/Register.html");
}

////异步判断登录注册用户名是否存在
function checkerolname(enrolname, str) {
    var url = ctx + "/forgetPassword/check_mobile";
    var pram = {'mobile': $.trim(enrolname),'type':$('input:radio:checked').val()};
    $.ajax({
        type: "post",
        dataType: "json",
        url: url,
        data: pram,
        async: false,
        success: function (datas) {
            if(datas == false){
                if(str==1){
                    //登录用户名不存在，不可以用
                    $("#errorenrolname").text("您输入的手机号不存在，请重新输入");
                    loginName=1;
                }else if(str==2){
                    //注册用户名不存在，可用
                    $("#errorregname").text("");
                    registerName=0;
                }else if(str==3){//忘记登录密码
                    //登录用户名不存在，不可以用
                    $("#errorsoname").text("您输入的手机号不存在，请重新输入");
                    findpwdName=1;
                }
            }else{
                if(str==1){
                    //登录用户名可以用
                    $("#errorenrolname").text("");
                    loginName=0;
                }else if(str==2){
                    $("#errorregname").text("手机号码已被注册");
                    //注册用户名已经存在，不可用
                    registerName=1;
                }else if(str==3){//忘记登录密码
                    //登录用户名可以用
                    $("#errorsoname").text("");
                    findpwdName=0;
                }
            }
        }
    });
}

//验证忘记密码用户名
function checkfindName() {
    var regname = $("#soname").val();
    if ("" == $("#soname").val()) {
        $("#errorsoname").text("手机号不能为空");
        findpwdName = 1;
        return;
    }
    checkerolname($("#soname").val(), 3);//判断注册用户是否存在
    if (findpwdName == 1) {//如果为1则没有这个用户
        return;
    } else {
        $("#errorsoname").text("");
        findpwdName = 0;
    }
}

/*
 * 忘记登录密码开始
 */
/**
 * 第一步开始
 */
//验证码


$("#wjpwdyanzbtn").click(function () {
    sendMsgauthenode("forgetPsSendMs",$("#swphone").text(),"wjpwdyanzbtn",1,$("#memid").val());
    // showVerCodes("portalMobileRecomService:sendMobileRecom", "portalMemberPasswordService:forgetPassword", "swhphone", "wjpwdyanzbtn", 1, $("#memid").val(), "sendcaptcha");//显示图形验证码
});

$("#soname").blur(function () {
    checkfindName();
});

$("#socode").blur(function () {
    checkCode($(this).val(), "errorsocode");
});
$("#nextbtn").click(function () {
//	if(valCode("socode","errorsocode")==false){
//		return;
//	}
    checkfindName();
    if (findpwdName == 1) {
        return;
    }
    checkCode($("#socode").val(), "errorsocode");
    if (codeid == 1) {
        return;
    }
    findloginpwd(1);
});
/**
 * 第一步结束
 */

/**
 * 第二步开始
 */

$("#swbtn").click(function () {
    var swcode = $("#swcode").val();
    if (swcode == "" || typeof(swcode) == "undefined") {
        layer.msg("请输入短信验证码！");
        return;
    }
    findloginpwd(2);
});

/**
 * 第二步结束
 */

/**
 * 第三步开始
 */

//注册密码鼠标移动
$("#stpwd").blur(function () {
    checkEnrolpassword("stpwd", "errorstpwd");
});
//注册确认密码鼠标移动
$("#stpwdagin").blur(function () {
    checkRegPwdAgin("stpwd", "stpwdagin", "errorstpwdagin");
});

$("#stbtn").click(function () {
    //验证密码
    checkEnrolpassword("stpwd", "errorstpwd");
    if (loginPwd == 1) {
        return;
    }
    //验证确认密码
    checkRegPwdAgin("stpwd", "stpwdagin", "errorstpwdagin");
    if (registerPwd1 == 1) {
        return;
    }
    findloginpwd(3);
});
/**
 * 第三步结束
 * $(".stepOne").addClass('disNone');
 *  $(".stepTwo").addClass('disNone');
 *  $(".stepThree").addClass('disNone');
 *   $(".stepEnd").removeClass('disNone');
 */

function findloginpwd(str) {
    var rsaKey = new RSAKey();
    rsaKey.setPublic(b64tohex(modules), b64tohex(exponent));
    var url = "";
    var param = "";
    if (str == 1) {
        //用户名  验证码
        param = {'type': 'FIND_PASSWORD', 'mobile': $("#soname").val(), 'imageCaptcha': $("#socode").val()};
        url = ctx + "/verifyCaptcha";
    } else if (str == 2) {
        //用户名  短信验证码
        param = {'type': 1, 'mobile': $("#soname").val(),'validateCode': $("#swcode").val()};
        url = ctx + "/forgetPassword/verifyMessage";
    } else if (str == 3) {
        //用户名  验证码
        param = {
            'password': hex2b64(rsaKey.encrypt($("#stpwd").val())),
            'type': $('input:radio:checked').val(),
            "mobile":$("#soname").val(),
            'captcha': $("#swcode").val()
        };
        url = ctx + "/forgetPassword/do"
    }
    $.ajax({
        type: "post",
        dataType: "json",
        url: url,
        data: param,
        success: function (datas) {
            if (datas.status == "success") {
                if (str == 1) {
                    $("#swname").html(datas.data.enrolname);
                    $("#swhphone").val(datas.data.iphone);
                    $("#swphone").html(datas.data.mobile);
                    $("#memid").val(datas.data.memid);
                    $(".stepOne").addClass('disNone');
                    $(".stepTwo").removeClass('disNone');
                    $(".step:eq(1)").addClass('actStep');
                } else if (str == 2) {
                    $(".stepOne").addClass('disNone');
                    $(".stepTwo").addClass('disNone');
                    $(".stepThree").removeClass('disNone');
                    $(".step:eq(2)").addClass('actStep');
                } else if (str == 3) {
                    $(".stepOne").addClass('disNone');
                    $(".stepTwo").addClass('disNone');
                    $(".stepThree").addClass('disNone');
                    $(".stepEnd").removeClass('disNone');
                    $(".step:eq(3)").addClass('actStep');
                }

            } else {
                layer.msg(datas.message);
            }

        }

    });
}
/*
 * 忘记登录密码结束
 */

function getTopWinow() {
    var p = window;
    while (p != p.parent) {
        p = p.parent;
    }
    return p;
}

//获取url中的参数
function getvParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg); //匹配目标参数

    if (r != null) {
        return decodeURI(r[2]);
    } else {
        return null; //返回参数值
    }
}

/**
 * 获取验证码
 * @param a
 * @param b
 */
function changeImg(a, b) {
    var imgSrc = $("#imgObj");
    var src = imgSrc.attr("src");
    var urls = chgUrl(src);
    imgSrc.attr("src", urls);
    // 时间戳
    // 为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳
    function chgUrl(url) {
        var timestamp = (new Date()).valueOf();
        urlurl = url.substring(0, 33);
        if ((url.indexOf("?") >= 0)) {
            urlurl = url + "&t=" + timestamp;
        } else {
            urlurl = url + "?t=" + timestamp + "&ImageWidth=" + a
                + "&ImageWidth=" + b;
        }

        return urlurl;
    }
}

//同步  (url地址，data参数数组,callbacks回调方法)
function ajaxf(url,data,callbacks){
    $.ajax({
        type : "POST",
        url : url,
        async:false,
        data : data,
        dataType : "json",
        success : function(msg) {
            if(callbacks!=null){

                callbacks(msg);
            }else if(callbacks==null){

            }
        },
        error : function() {

        }
    });
}

//同步
$.ajaxf = function (url,data,callbacks){
    $.ajax({
        type : "POST",
        url : url,
        async:false,
        data : data,
        dataType : "json",
        success : function(msg) {
            if(callbacks!=null){
                callbacks(msg);
            }else if(callbacks==null){
                callbacks(null);
            }
        },
        error : function() {
        }
    });
}

function toToUc() {
    window.location.href = ctx + "/uc/index";
}
function goToSecurity() {
    window.location.href = ctx + "/uc/security";
}