//显示样式
function checkP_block(pid) {
    $("#" + pid).css({
        display: 'block'
    });
}
//隐藏样式
function checkP_none(pid) {
    $("#" + pid).css({
        display: 'none'
    });
}

//登录注册充值页面的头部底部
function tophref() {
}

//充值页面的左边菜单
function lefthref() {
}

//退出登录
function onlogin(falg) {
    var url = "";
    var returnUrl = location.href.split("#")[0];

    if (falg == 1) {
        url = getLoginUrlExt("logout");// + "SY810S";
    } else {
        url = getLoginUrl("logout");// + "SY810S";
    }
    $.ajax({
        type: "post",
        dataType: "json",
        url: url,
        data: {},
        success: function (datas) {
            //location.href = getLocalhostPath() + "/MemberCenter/login.html";
            location.href = returnUrl;
        }

    });
}

//验证登录注册密码
function checkEnrolpassword(password, errorpassword) {
    var password = $("#" + password).val();
    var pwdReg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$/;
    if ("" == password) {
        $("#" + errorpassword).text("密码不能为空");
        loginPwd = 1;
        return;
    } else if (password.length < 6 || password.length > 18) {
        $("#" + errorpassword).text("密码需为6-18位字符");
        loginPwd = 1;
        return;
    } else if(!pwdReg.test(password)){
        $("#" + errorpassword).text("密码必须是数字和字母的组合");
        loginPwd = 1;
        return;
    }else {
        $("#" + errorpassword).text("");
        loginPwd = 0;
    }

}
//验证手机号码
function checkphone(phone, errorphone) {
    if ($("#" + phone).val() == "") {
        regphone = 1;
        $("#" + errorphone).text("请输入您的手机号码！");
        return;
    } else if (isNaN($("#" + phone).val())) {
        regphone = 1;
        $("#" + errorphone).text("请输入您的手机号码！");
        return;
    } else if (regrphone.test($("#" + phone).val()) == false) {
        regphone = 1;
        $("#" + errorphone).text("您输入的手机号码有误，请重新输入！");
        return;
    } else if ($("#" + phone).val().substring(0, 1) != 1) {
        regphone = 1;
        $("#" + errorphone).text("您输入的手机号码有误，请重新输入！");
        return;
    } else {
        regphone = 0;
        $("#" + errorphone).val("");
    }
}

//判断手机是否存在
function rphone(phone, errorphone) {
    var moble = $("#" + phone).val();
    var url = getHpoleFrontURL() + "portalEnrolnameExistService:phoneExist";
    $.ajax({
        type: "post",
        dataType: "json",
        url: url,
        async: false,
        data: {'phone': moble},
        success: function (datas) {
            if (datas.code == "Succeeded") {
                //手机号存在
                regphone = 1;
            } else {
                //手机号不存在
                regphone = 0;
            }
        }
    });
}

//验证图形验证码
function checkCode(imgCode, errorimgcode) {
    if ("" == imgCode) {
        //验证码不能为空
        $("#" + errorimgcode).text("图形验证码不能为空");
        codeid = 1;
        return;
    } else if ($.trim(imgCode).length != 4) {
        //图形验证码的长度
        $("#" + errorimgcode).text("您输入的图形验证码错误！");
        codeid = 1;
        return;
    } else {
        $("#" + errorimgcode).text("");
        codeid = 0;
    }
}

/*
 * 分页控件加载样式
 * 调用参考myinvest.js
 * 分页点击事件参考 myinvest.js中的pageclick(id)方法
 * 20150312
 */

function pagination(id) {
    var pages = $("#pages").val();
    var page = $("#page").val();
    var total = $("#total").val();
    var pages1 = $("#pages1").val();
    var page1 = $("#page1").val();
    var total1 = $("#total1").val();
    var varhtml = "";
    if (id == 1) {
        if (page1 == "") {
            page1 = 1;
        }
        if (pages1 > 0) {
            varhtml += "<li>";
            varhtml += "<a href=javascript:void(0) onclick=perclick() aria-label=Previous>";
            varhtml += " <span aria-hidden=\"true\">&laquo;</span>";
            varhtml += "</a>";
            varhtml += "</li>";
            for (var i = 0; i < pages1; i++) {
                if ((i + 1) == page1) {//默认选中当前页
                    varhtml += "<li class=active><a href=javascript:void(0) onclick=pageclick("
                        + (i + 1) + ")>" + (i + 1) + "</a></li>";
                } else if (i > 3 && pages1 - i > 1 && pages1 > 5) {
                    if (varhtml.split("<li><a disable>.&nbsp;.&nbsp;.</a></li>").length == 1) {
                        varhtml += "<li><a disable>.&nbsp;.&nbsp;.</a></li>";
                    }
                } else {
                    varhtml += "<li><a href=javascript:void(0) onclick=pageclick("
                        + (i + 1) + ")>" + (i + 1) + "</a></li>";
                }
            }

            varhtml += "<li>";
            varhtml += " <a href=javascript:void(0) onclick=nextclick() aria-label=Next>";
            varhtml += "<span aria-hidden=\"true\">&raquo;</span>";
            varhtml += "</a>";
            varhtml += "</li>";
        } else {
//			varhtml+="<span style=font-size:14px;>&nbsp;&nbsp;暂&nbsp;&nbsp;无&nbsp;&nbsp;数&nbsp;&nbsp;据&nbsp;&nbsp;</span>";
        }
        $(".pagination1").html(varhtml);
    } else {
        if (page == "") {
            page = 1;
        }
        if (pages > 0) {
            varhtml += "<li>";
            varhtml += "<a href=javascript:void(0) onclick=perclick() aria-label=Previous>";
            varhtml += " <span aria-hidden=\"true\">&laquo;</span>";
            varhtml += "</a>";
            varhtml += "</li>";
            for (var i = 0; i < pages; i++) {
                if ((i + 1) == page) {//默认选中当前页
                    varhtml += "<li class=active><a href=javascript:void(0) onclick=pageclick("
                        + (i + 1) + ")>" + (i + 1) + "</a></li>";
                } else if (i > 3 && pages - i > 1 && pages > 5) {
                    if (varhtml.split("<li><a disable>.&nbsp;.&nbsp;.</a></li>").length == 1) {
                        varhtml += "<li><a disable>.&nbsp;.&nbsp;.</a></li>";
                    }
                } else {
                    varhtml += "<li><a href=javascript:void(0) onclick=pageclick("
                        + (i + 1) + ")>" + (i + 1) + "</a></li>";
                }
            }

            varhtml += "<li>";
            varhtml += " <a href=javascript:void(0) onclick=nextclick() aria-label=Next>";
            varhtml += "<span aria-hidden=\"true\">&raquo;</span>";
            varhtml += "</a>";
            varhtml += "</li>";
        } else {
//			varhtml+="<span style=font-size:14px;>&nbsp;&nbsp;暂&nbsp;&nbsp;无&nbsp;&nbsp;数&nbsp;&nbsp;据&nbsp;&nbsp;</span>";
        }
        $(".pagination").html(varhtml);
    }

}

var ck = 0;
var cname = "";
var ck1 = 0;
/*
 * 点击发送短信验证码
 * code 交易码
 * phone 手机号码
 * str 按钮id
 */
function sendMsgauthenode(code, phone, str, temp, memid) {
    $("#" + str).addClass("btn disabled");
    var url = "";
    if (code == "forgetPsSendMs") {
        url = ctx + "/forgetPassword/sendMessage";
    }
    if ("" == memid) {

    } else {
        url += "&memid=" + memid;
    }
    cname = str;
    $.ajax({
        type: "post",
        dataType: "json",
        url: url,
        async: false,
        data: {
            'mobile': phone,
            'trancode': code,
            'temp': temp,
            'type': $('input:radio:checked').val()
        },
        success: function (datas) {
            if (datas.status == "success") {
                show_time(60);//成功将按钮修改
                MOBILEPHONE = 0;
            } else {
                MOBILEPHONE = 1;
                ck = 0;
                $("#" + cname).removeClass('btn disabled');
                sameModal((datas.message != "" ? datas.message : "发送失败."));
                $("#errorswcode").text(datas.message != "" ? datas.message : "发送失败.");
            }
        },
        error: function () {
            $("#errorswcode").text("发送失败");
        }
    });
}

//发送倒计时
function show_time(n) {
    n = n - 1;
    var timer = document.getElementById(cname);
    var str_time = n + "秒后重发";
    timer.innerHTML = str_time;
    $("#" + cname).text(str_time);
    if (n == 0) {
        ck = 0;
        ck1 = 0;
        timer.innerHTML = "发送验证码";
        $("#" + cname).text("发送验证码");
        $("#" + cname).prop('disabled', false).removeClass("ui-disabled");
        $("#" + cname).removeClass("disabled");
//		$("#" + cname).removeClass('btn disabled');
        return;
    }
    setTimeout("show_time(" + n + ")", 1000);
}
/**
 * 验证验证码
 * @param a
 * @param b
 */
function valCode(veryCode, errorverycode, isflag, localtion) {
    var code = $("#" + veryCode).val();
    //code = "c=" + code;
    code = {'c': code};
    var flag = false;
    var url = "";
    if (isflag != 1) {
        url = getHpoleURL() + "/portalLoginController.do?resultVerify";
    } else {
        url = getHpoleURLhs() + "/portalLoginController.do?resultVerify";
    }
    $.ajax({
        type: "POST",
        url: url,
        async: false,
        data: code/*{'c':code}*/,
        success: function (postData) {
            jsonData = eval("(" + postData + ")");
            if (jsonData.result) {
                $("#" + errorverycode).text("");
                flag = true;
            } else {
                changeImg(100, 50);
                $("#" + errorverycode).text('验证码错误!');
                flag = false;
            }

        }
    });
    return flag;
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

//弹出框
function sameModal(context, local, _formWdith, _formHeight) {
    $("#modalbody").html(context);
    if (_formWdith != undefined) {
        $("#sameModal").find(".modal-dialog").width(_formWdith);
    }
    if (_formHeight != undefined) {
        $("#sameModal").find(".modal-dialog").height(_formHeight);
    }
    $("#modalbody").html(context);
    if (local == undefined) {
    } else if ("self" == local) {
        $("#closeup").bind("click", function () {
            location.reload();
        });
        $("#closeupd").bind("click", function () {
            location.reload();
        });
    } else {
        $("#closeup").bind("click", function () {
            //window.location.href = local;
            $("#sameModal").modal('hide');
        });
        $("#closeupd").bind("click", function () {
            //window.location.href = local;
            $("#sameModal").modal('hide');
        });
    }
    $("#sameModal").modal('show');
}


//判断是手机还是电脑
function browserRedirect() {
    var sUserAgent = navigator.userAgent.toLowerCase();
    var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
    var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
    var bIsMidp = sUserAgent.match(/midp/i) == "midp";
    var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
    var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";
    var bIsAndroid = sUserAgent.match(/android/i) == "android";
    var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";
    var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";
    var hname = window.location.hostname;
    var pname = window.location.pathname;
    var hrefName = window.location.href;
    var lindex0 = hrefName.indexOf('mobile');
    var lindex1 = hrefName.indexOf('investment');
    var lindex2 = hrefName.indexOf('detail_');
    var lindex3 = hrefName.indexOf('creditassign');
    var name = getUrlParam();
    if (!(bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM)) {

    } else {
        if (lindex0 != -1) {

        } else {
            if (lindex1 != -1) {
                if (lindex2 != -1) {
                    window.location.href = getm33lend() + "/mobile/detail_" + name + ".html";
                } else {
                    window.location.href = getm33lend() + "/mobile/mobile.html";
                }
            }
            else if (pname == "/") {
                window.location.href = getm33lend() + "/mobile/mobile.html";
            } else {
                window.location.href = getm33lend() + "/mobile/MemberCenter.html";
            }
        }
    }
}

//显示错误提示窗口  旁边提示
function showErrorContent(errorConID, errorShowID, errorCon, type) {
    if (type == 0) {//关闭
        $("#" + errorConID).html("<span class='false-icon'></span>" + errorCon + "");
        $("#" + errorShowID).hide();
    } else {//显示
        $("#" + errorConID).html("<span class='false-icon'></span>" + errorCon + "");
        $("#" + errorShowID).show();
    }

}

//============  图片验证  =====================//
//检验图片验证
function checkVeryCode(sid, code, phone, str, temp, memid, localtion) {
    var veryCode = $("#veryCode1").val();
    if (veryCode == "" || veryCode == null) {
        $("#error_code").html("请输入图形验证码！");
        return;
    } else {
        $("#error_code").html('');
    }

    $("#" + str).addClass("btn disabled");
    var url = "";
    var urls = window.location.href;
    var checkUrl = "/captcha/verify?type=REGIST";
    checkUrl += "&imageCaptcha=" + veryCode;

    phone = $("#" + phone).val();
    cname = str;
    data = "mobile=" + phone + "&type=" + $("input:checked").val();

    //校验验证码,并且发送手机短信
    $.ajaxf(checkUrl, data, function (datas) {
        if (datas.status == "success") {
            hideVerCode();
            MOBILEPHONE = 0;
            $.ajax({
                url: ctx + "/regist/sendMessage",
                data: data,
                type: "post",
                dataType: "json",
                cache: false,
                success: function (data) {
                    if (data.status == "success") {
                        $("#errorcapcha").html("");
                        show_time(60);//成功将按钮修改
                    } else {
                        $("#" + cname).removeClass('btn disabled');
                        $("#errorcapcha").html(data.message);
                    }
                }
            });
        } else {
            MOBILEPHONE = 1;
            ck = 0;
            $("#" + cname).removeClass('btn disabled');
            $("#error_code").html(datas.message);
        }
    });
}

//显示图形验证码
function showVerCode(sid, code, phone, str, temp, memid, localtion) {
    var vHtml = "";
    vHtml += "    <div class='modal-backdrop fade in' style='height: 100%;'></div>";
    vHtml += "    <div class='modal-dialog modalMw suiji_yan_tk'>";
    vHtml += "        <div class='modal-content suiji_yaz '>";
    vHtml += "            <div class='modal-header'>";
    vHtml += "				   <button data-dismiss='modal' class='close' type='button'  onclick='hideVerCode()'><span aria-hidden='true'>×</span><span class='sr-only'>Close</span></button>";
    vHtml += "				   <h4 class='modal-title'>随机验证码</h4>";
    vHtml += "			  </div>";
    vHtml += "		      <div class='modal-body'>";
    vHtml += "                <div class='yanz_connet'>";
    vHtml += "                   <input id='veryCode1' class='form-control loginYanz' maxlength='6' name='veryCode' type='text'  placeholder='请输入图形验证码'/>";
    vHtml += "                   <span class='yanz_img'><a href='javascript:changeImg(130,54)'><img id='imgObj' src='"+ctx+"/captcha?type=REGIST' border='0'/></a></span>";
    vHtml += "                </div>";
    vHtml += "           	  <div class='suiji_pro'>";
    vHtml += "           	  <span  id='error_code'></span>";
    vHtml += "           	  </div>";
    vHtml += "           	  <div class='yanz_btn'>";
    vHtml += "					 <a href='javascript:;' onclick='checkVeryCode(\"" + sid + "\",\"" + code + "\",\"" + phone + "\",\"" + str + "\",\"" + temp + "\",\"" + memid + "\",\"" + localtion + "\")'  class='btn btn-primary' type='button'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;确&nbsp;定&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>";
    vHtml += "                </div> ";
    vHtml += "           </div>";
    vHtml += "        </div>";
    vHtml += "    </div>";
    $("#reg_VerCode").html(vHtml).show();
}

//影藏图形验证码
function hideVerCode() {

    $("#reg_VerCode").hide();
}


//*************　手机端  *****************//
//显示手机端图形验证码
function showMobileVerCode(num) {
    var vHtml = "";
    vHtml += " <div class='groupform ui-grid-a yanzhenform'>";
    vHtml += "   <div class='ui-block-a'>";
    vHtml += "      <div class='ui-input-text ui-body-inherit ui-corner-all ui-shadow-inset'>";
    vHtml += "        <input id='veryCode1' class='import formPadding keyIco' maxlength='6' name='veryCode' type='text' placeholder='请输入图形验证码'  />";
    vHtml += "      </div>";
    vHtml += "   </div>";
    vHtml += "   <div class='ui-block-b'>";
    vHtml += "       <span class='yanzImg register-yanzBtn'><a href='javascript:changeImg(130,54)'><img id='imgObj' src=" + getHpoleURL() + "/servlet/imageVerifyCodeServlet' border='0' /></a></span>";
    vHtml += "   </div>";
    vHtml += " </div>";
    if (num == 1) {
        $("#MobileVerCode_yanzhen").before(vHtml);
    } else {
        $(".MobileVerCode div:eq(2)").after(vHtml);
    }
}

//手机端校验图形验证码
function checkMoBileVerCode(code, phone, str, temp, memid, veryCode) {//veryCode图形验证码ID
    var url = getHpoleFrontURL() + "portalMobileRecomService:sendMobileRecom";
    veryCode = $("#" + veryCode).val();
    if (veryCode == "" || veryCode == null) {
        $("#error_code").html("请输入图形验证码！");
        return;
    } else {
        $("#error_code").html('');
    }

    if ("" == memid) {
        url += "&ccode=" + veryCode;
    } else {
        url += "&memid=" + memid;
    }
    cname = str;
    phone = $("#" + phone).val();
    var data = "&phone=" + phone + "&trancode=" + code + "&temp=" + temp;
    $.ajaxf(url, data, function (datas) {

        if (datas.code == "Succeeded") {
            show_time(120);//成功将按钮修改
        } else {
            $("#" + cname).prop('disabled', false).removeClass("ui-disabled");
            alert(datas.message)
            ck1 = 0;
        }
    });

}


//===== 特殊 ==
//显示图形验证码
function showVerCodes(sid, code, phone, str, temp, memid, localtion) {
    var vHtml = "";
    vHtml += "    <div class='modal-backdrop fade in' style='height: 100%;'></div>";
    vHtml += "    <div class='modal-dialog modalMw suiji_yan_tk'>";
    vHtml += "        <div class='modal-content suiji_yaz '>";
    vHtml += "            <div class='modal-header'>";
    vHtml += "				   <button data-dismiss='modal' class='close' type='button'  onclick='hideVerCode()'><span aria-hidden='true'>×</span><span class='sr-only'>Close</span></button>";
    vHtml += "				   <h4 class='modal-title'>随机验证码</h4>";
    vHtml += "			  </div>";
    vHtml += "		      <div class='modal-body'>";
    vHtml += "                <div class='yanz_connet'>";
    vHtml += "                   <input id='veryCode1' class='form-control loginYanz' maxlength='6' name='veryCode' type='text'  placeholder='请输入图形验证码'/>";
    vHtml += "                   <span class='yanz_img'><a href='javascript:changeImgs(130,54)'><img id='imgObjs' src='" + getHpoleURL() + "/portalLoginController.do?verify&localtion=" + localtion + "' border='0'/></a></span>";
    vHtml += "                </div>";
    vHtml += "           	  <div class='suiji_pro'>";
    vHtml += "           	  <span  id='error_code'></span>";
    vHtml += "           	  </div>";
    vHtml += "           	  <div class='yanz_btn'>";
    vHtml += "					 <a href='javascript:;' onclick='checkVeryCode(\"" + sid + "\",\"" + code + "\",\"" + phone + "\",\"" + str + "\",\"" + temp + "\",\"" + memid + "\",\"" + localtion + "\")'  class='btn btn-primary' type='button'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;确&nbsp;定&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>";
    vHtml += "                </div> ";
    vHtml += "           </div>";
    vHtml += "        </div>";
    vHtml += "    </div>";
    $("#reg_VerCode").html(vHtml).show();
}

//通过该函数进行登录时，会将当前页面地址带过去，以便登录成功后返回登录前的页面；
function loginedAndReturn() {

    var loginUrl = "/MemberCenter/login.html";
    var returnUrl = location.href;
    location.href = loginUrl + "?returnUrl=" + returnUrl;
}

function getKuaYuURL() {//跨域URL
    var urls = window.location.href;
    var url = "";
    if (urls.split("login").length > 1 || urls.split("Register").length > 1 || urls.split("find_0").length > 1 || urls.split("find_2").length > 1 || urls.split("33lend-landing").length > 1 || urls.split("fresh-register").length > 1 || urls.split("iam33").length > 1) {
        url = getHpoleFrontURLhs();
    } else {
        url = getHpoleFrontURL();
    }
    return url;
}

//谷歌GA统计代码
(function (i, s, o, g, r, a, m) {
    i['GoogleAnalyticsObject'] = r;
    i[r] = i[r] || function () {
            (i[r].q = i[r].q || []).push(arguments)
        }, i[r].l = 1 * new Date();
    a = s.createElement(o),
        m = s.getElementsByTagName(o)[0];
    a.async = 1;
    a.src = g;
    m.parentNode.insertBefore(a, m)
})(window, document, 'script', '//www.google-analytics.com/analytics.js', 'ga');

ga('create', 'UA-75972224-1', 'auto');
ga('send', 'pageview');

