[@nestedstyle]
<style>
    div.div-inline {
        display: inline
    }
</style>
[/@nestedstyle]
[@nestedscript]
    [@js src="js/mobile/tools/rsa/jsbn.min.js,
    js/mobile/tools/rsa/prng4.min.js,
    js/mobile/tools/rsa/rng.min.js,
    js/mobile/tools/rsa/rsa.min.js,
   js/mobile/tools/base/base64.min.js" /]
[/@nestedscript]
[@insert template="layout/mobile_regist_layout" title="${setting.basic.siteName}" bodyClass="" bodyStyle="position:absolute;width:100%;height:100%;" htmlClass="js cssanimations"]
<!-- Header -->
[#--<header data-am-widget="header" class="am-header am-header-default am-no-layout">--]
[#--<div class="am-header-left am-header-nav">--]
[#--<a href="javascript:history.back(-1);">--]
[#--<i class="am-header-icon am-icon-angle-left"></i>--]
[#--</a>--]
[#--</div>--]
[#--<a class="am-header-title login-nav" href="javascript:history.go(-1);location.reload()">用户注册</a>--]
[#--</header>--]
<!-- end Header -->
<div style="height: 85%">
    <div class="login-banner clear">
        <img class="login-header" src="${ctx}/static/images/appdown/logo.png" alt=""><br>
    </div>
    <form id="form-with-tooltip" action="#" method="post" class="am-form">
        <fieldset class="border-t-b-1 am-margin-top-sm bcg-white">
            <div class="radio">
                <div class="radio div-inline">
                    <input type="radio" name="radio1" id="radio1" value="GENERAL" checked="">
                    <label for="radio1">
                        个人用户
                    </label>
                </div>
                <div class="radio div-inline">
                    <input type="radio" name="radio1" id="radio2" value="ENTERPRISE">
                    <label for="radio2">
                        企业用户
                    </label>
                </div>
            </div>
            <div class="am-input-group am-form-group">
            <span class="am-input-group-label">
                <i class="am-icon-mobile-phone am-icon-sm am-icon-fw" aria-hidden="true"></i>
            </span>
                <input type="text" id="doc-vld-name-2-0" class="am-form-field" placeholder="请输入手机号"
                       pattern="^1[3|4|5|7|8][0-9]{9}$" maxlength="11" required data-foolish-msg="请输入11位手机号">
            </div>
            <div class="am-input-group am-form-group">
            <span class="am-input-group-label">
                <i class="am-icon-lock am-icon-fw"></i>
            </span>
                <input type="password" id="password" class="am-form-field" required data-foolish-msg="请输入密码"
                       placeholder="请输入密码">
                <span class="am-input-group-label" style="background-color: white">
                <div class="tpl-switch">
                    <input type="checkbox" id="passwordSwitch" class="ios-switch bigswitch tpl-switch-btn" checked="">
                    <div class="tpl-switch-btn-view">
                        <div>
                        </div>
                    </div>
                </div>
            </span>
            </div>
            <div class="am-input-group am-form-group">
                <span class="am-input-group-label"><i class="am-icon-building-o am-icon-fw"></i></span>
                <input type="text" id="captcha" class="am-form-field" placeholder="请输入验证码" pattern="^[0-9]{6}$"
                       maxlength="6" required data-foolish-msg="请输入6位数字验证码">
                <span class="am-input-group-btn">
        <button class="am-btn am-btn-default" id="get-captcha" type="button" onclick="settime()">获取验证码</button>
      </span>
            </div>
            <div class="am-input-group am-form-group" style="margin-top: 10%">
                <span class="am-input-group-label"><i class="am-icon-group am-icon-fw"></i></span>
                <input type="text" id="referrer" class="am-form-field" placeholder="推荐人手机号（选填）" value="${inviteMobile}">
            </div>
        </fieldset>
        <input type="button" value="完成注册" class=" am-btn am-btn-block am-btn-danger am-margin-top-lg  f_mid am-round"
               style="width:70%; background-color:#1296db; border-color:#1296db"
               data-am-modal="{target: '#my-modal-loading'}" id="doc-confirm-toggle">
        <p class="fontcolor-register" style="font-size: small;text-align: center">点击上述按钮，即表示你同意<a
                href="${ctx}/mobile/regist/agreement">《${setting.basic.siteName}注册协议》</a></p>
    </form>
</div>
<footer class="am-footer am-footer-default" style="margin-top: 20px;>
    <div class="am-footer-switch">
        <a class="am-footer-desktop" href="${ctx}/mobile/others/appDownLoad">
            <i class="am-icon-mobile am-icon-fw"></i>移动客户端下载
        </a>
    </div>
</footer>


<!--注册成功弹出框样式-->
<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">注册成功</div>
        <div class="am-modal-bd">
            是否前往下载移动客户端？
        </div>
        <div class="am-modal-footer">
            <span class="am-modal-btn" data-am-modal-cancel>取消</span>
            <span class="am-modal-btn" data-am-modal-confirm>确定</span>
        </div>
    </div>
</div>
<!--注册失败弹出框样式-->
<div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">提示</div>
        <div class="am-modal-bd" id="tip">

        </div>
        <div class="am-modal-footer">
            <span class="am-modal-btn" data-am-modal-confirm>确定</span>
        </div>
    </div>
</div>
<script type="text/javascript">

    var rsaKey = new RSAKey();
    var modulus = "${modules}", exponent = "${exponent}";

    rsaKey.setPublic(b64tohex(modulus), b64tohex(exponent));

    var $alert = $('#my-alert');
    $(function () {
        var $form = $('#form-with-tooltip');
        var $tooltip = $('<div id="vld-tooltip" class="am-animation-fade am-animation-reverse am-animation-delay-10">提示信息！</div>');
        var userType = ($('input:radio:checked').val());
        $tooltip.appendTo(document.body);
        $form.validator();
        var validator = $form.data('amui.validator');
        $form.on('focusin focusout', '.am-form-error input', function (e) {
            if (e.type === 'focusin') {
                var $this = $(this);
                var offset = $this.offset();
                var msg = $this.data('foolishMsg') || validator.getValidationMessage($this.data('validity'));
                console.log(msg);
                $tooltip.text(msg).show().css({
                    left: offset.left + 10,
                    top: offset.top + $(this).outerHeight() + 10
                });
            } else {
                $tooltip.hide();
            }
        });
        $form.on('focusin focusout', '.am-form-success input', function (e) {
            $tooltip.hide();
        });

        $('#doc-confirm-toggle').on('click', function () {
            console.log($form.validator('isFormValid'));
            if ($form.validator('isFormValid')) {
                $.ajax({
                    type: "POST",
                    url: ctx + "/mobile/regist/scanSubmit",
                    dataType: "json",
                    data: {
                        mobile: $("#doc-vld-name-2-0").val(),

                        password: hex2b64(rsaKey.encrypt($("#password").val())),
                        referrer: $("#referrer").val(),
                        captcha: $("#captcha").val(),
                        type: userType
                    },
                    cache: false,
                    success: function (data) {
                        console.log(data);
                        if (data.success) {
                            $('#my-confirm').modal({
                                relatedTarget: this,
                                onConfirm: function (options) {
                                    //实名认证
                                    window.location.href = ctx + "/mobile/others/appDownLoad";
                                },
                                // closeOnConfirm: false,
                                onCancel: function () {
                                    //首页
//                                    window.location.href = ctx + "/homepage";
                                }
                            });
                        } else {
                            document.getElementById('tip').innerHTML = data.message;
                            $alert.modal({
                                relatedTarget: this,
                                onConfirm: function (options) {

                                }
                            });
                        }
                    },
                    error: function () {
                        console.log(111);
                        $(".pullrefresh").html("出错啦！");
                    }
                })
            } else {

            }
        });

        $('#get-captcha').on('click', function () {
            $.ajax({
                type: "post",
                url: ctx + "/mobile/regist/sendSms",
                data: {
                    mobile: $("#doc-vld-name-2-0").val()
                },
                dataType: "json",
                async: false,
                cache: false,
                success: function (data) {
                    console.log(data);
                    if (data.success) {
                        $tooltip.text(data.message).show().css({
                            left: 10,
                            top: 310
                        });
                        $("#vld-tooltip").fadeIn(3000);

                    } else {
                        $("#doc-vld-name-2-0").removeClass("am-field-valid");
                        $("#doc-vld-name-2-0").addClass("am-field-error");
                        $("#doc-vld-name-2-0").closest("div").removeClass("am-form-success");
                        $("#doc-vld-name-2-0").closest("div").addClass("am-form-error");
//                        $tooltip.text(data.message).show().css({
//                            left: 10,
//                            top: 230
//                        });
//                        setTimeout($tooltip.hide(),1000);

                    }
                },
                error: function () {
                    $(".pullrefresh").html("出错啦！");
                }
            })
        });

        $("#passwordSwitch").prop('checked', false);
        $("#passwordSwitch").on("change", function () {
            if ($(this).prop('checked')) {
                $("#password").attr("type", 'text');
            } else {
                $("#password").attr("type", 'password');
            }
        });
    });
    //获取验证码倒计时
    var InterValObj; //timer变量，控制时间
    var count = 60;  //间隔函数，1秒执行
    var curCount;//当前剩余秒数
    function settime() {
        var a = $('#get-captcha');
        curCount = count;
        //设置button效果，开始计时
        a.attr("disabled", "true");
        a.text("(" + curCount + "秒后重发)");
        console.log("(" + curCount + "秒后重发)");

        InterValObj = window.setInterval(function () {
            SetRemainTime(a)
        }, 1000);//启动计时器，1秒执行一次
    }
    //timer处理函数
    function SetRemainTime(a) {
        if (curCount == 0) {
            if (a.hasClass("timeac")) {
                a.removeClass("timeac")
            }
            ;
            window.clearInterval(InterValObj);//停止计时器
            a.removeAttr("disabled");//启用按钮
            a.text("获取验证码");
        }
        else {
            curCount--;
            a.text("(" + curCount + "秒后重发)");
            if (!a.hasClass("timeac")) {
                a.addClass("timeac")
            }
            ;
        }
    }
</script>
[/@insert]