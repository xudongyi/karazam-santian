[@nestedstyle]
    [@css href="css/build.css" /]
[/@nestedstyle]
[@nestedscript]
    <script>
        var redirectUrl = "${redirectUrl!""}";
    </script>
    [@js src="js/login/login_register.js" /]
    [@js src="js/login/publicjs.js" /]
[/@nestedscript]
[@insert template="layout/login_layout" title="登录"]
<div class="login-con-3" id="login_zz_img">
    <div class="content clear">
        <div class="login-con clear pull-right">
            <div class="pull-left login-conR">
                <div class="login-top clear">
                    <h3 class="fsz22">登录</h3>
                    <p>没有账号？<a href="${ctx}/regist">免费注册</a></p>
                </div>
                <p class="login-tip disNone">
                    <i class="icon-safe"></i>
                    <span style="margin-left:18px">您的信息通过256位SGC加密保护，数据传输安全。</span>
                </p>
                <div class="login-form basic-form">
                    <form role="form" id="loginForm">
                        <div class="radio">
                            <div class="radio radio-inline">
                                <input type="radio" name="radio1" id="radio1" value="GENERAL" checked="">
                                <label for="radio1">
                                    个人用户
                                </label>
                            </div>
                            <div class="radio radio-inline">
                                <input type="radio" name="radio1" id="radio2" value="ENTERPRISE">
                                <label for="radio2">
                                    企业用户
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-control nameIco" id="enrolname" name="username" placeholder="请输入手机号">
                            <span class="help-block" id="errorenrolname"></span>
                        </div>
                        <div class="form-group pos-r">
                            <input type="password" class="form-control passwordIco" id="passwords" name="password" placeholder="请输入登录密码">
                            <span class="help-block" id="errorpassword"></span>
                            <span class="login-eye"></span>
                        </div>
                        <div id="serverErrorMsg" style=""></div>
                        <div class="form-group">
                            <input id="checkcode" type="hidden" value="0">
                            <a href="javascript:void(0)" class="btn btn-primary btn-block formaBtn" id="tzj_zz_login" onclick="login()">立即登录</a>
                        </div>
                        <div class="login-check">
                            <div class="checkbox checkbox-inline">
                                <label style="display:none;">
                                    <input type="checkbox" value="0" id="ckbox" checked="checked">
                                    记住账户
                                </label>
                            </div>
                            <div class="checkbox-inline mar-left145">
                                <a class="login-forget" href="${ctx}/forgetPassword">忘记密码</a>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="txt-C mgt45 fsz12" style="color:#aab2bd;">理财有风险，投资需谨慎！</div>
            </div>
        </div>
    </div>
</div>
[/@insert]