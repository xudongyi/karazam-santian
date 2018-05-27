[@nestedstyle]
    [@css href="css/build.css" /]
[/@nestedstyle]
[@nestedscript]
    <script type="text/javascript">
        var modules = "${modules}", exponent = "${exponent}";
    </script>
    [@js src="js/login/login_register.js" /]
    [@js src="js/login/publicjs.js" /]
[/@nestedscript]
[@insert template="layout/forgotps_layout" title="重置密码"]
<div class="content">
    <!--面包屑-->
    <div class="bread">
        <i class="glyphicon glyphicon-home"></i>
        <ol class="breadcrumb">
            <li><a href="/">首页</a></li>
            <li class="active">找回登录密码</li>
        </ol>
    </div>
    <!--面包end-->
    <div class="findpassword-con">
        <h4>忘记密码</h4>
        <!--步骤显示图片-->
        <div class="step-wrap  clear">
            <div class=" clear step actStep">
                <div class="circle">1</div>
                <div class="stepTxt">
                    <p class="p1">填写用户名</p>
                    <p class="p2">Fill in Account</p>
                </div>
            </div>
            <div class="jiantou">
                <img src= "/static/images/login/jiantou.png" />
            </div>
            <div class=" clear step">
                <div class="circle">2</div>
                <div class="stepTxt">
                    <p class="p1">验证身份</p>
                    <p class="p2">Verify ID</p>
                </div>
            </div>
            <div class="jiantou">
                <img src="/static/images/login/jiantou.png" />
            </div>
            <div class=" clear step">
                <div class="circle">3</div>
                <div class="stepTxt">
                    <p class="p1">设置新密码</p>
                    <p class="p2">Setting Password</p>
                </div>
            </div>
            <div class="jiantou">
                <img src="/static/images/login/jiantou.png" />
            </div>
            <div class=" clear step">
                <div class="circle">4</div>
                <div class="stepTxt">
                    <p class="p1">完成</p>
                    <p class="p2">Finish</p>
                </div>
            </div>
        </div>
        <div class="stepCon">
            <!--步骤1：填写用户名-->
            <div class="stepOne ">
                <form role="form" class="basic-form">
                    <div class="radio" style="margin-left: 100px">
                        <div class="radio radio-inline">
                            <input type="radio" name="radio1" id="radio1" value="GENERAL" checked="">
                            <label for="radio1" style="text-align: left">
                                个人用户
                            </label>
                        </div>
                        <div class="radio radio-inline">
                            <input type="radio" name="radio1" id="radio2" value="ENTERPRISE">
                            <label for="radio2" style="text-align: left">
                                企业用户
                            </label>
                        </div>
                    </div>
                    <div class="form-group clear">
                        <label  class="control-label pull-left">手机号：</label>
                        <div class="formCon">
                            <input type="text" id="soname" class="form-control" placeholder="已验证的手机号">
                            <span class="help-block" id="errorsoname"></span>
                        </div>
                    </div>
                    <div class="form-group clear">
                        <label  class="control-label pull-left">验证码：</label>
                        <div class="formCon findYanz">
                            <input type="text" class="form-control" id="socode"/><a href="javascript:changeImg(130,54)"> <img id="imgObj" src="/captcha?type=FIND_PASSWORD" border="0" style="width: 108px"/> </a>
                            <div style="clear:both"></div>
                            <span class="help-block" id="errorsocode"></span>
                        </div>

                    </div>
                    <div class="form-group clear">
                        <label  class="control-label pull-left"></label>
                        <div class="formCon">
                            <a class="btn btn-primary nextBtn" id="nextbtn">下一步</a>
                        </div>
                    </div>
                </form>
            </div>
            <!--步骤2：验证身份（其中2个div，手机验证、邮箱验证）-->
            <div class="stepTwo disNone">
                <form role="form" class="basic-form">
                    <div class="form-group clear" hidden="hidden">
                        <label  class="control-label pull-left">验证方式：</label>
                        <div class="formCon">
                            <select class="form-control">
                                <option value="0">已验证手机</option>
                            </select>
                        </div>
                    </div>
                </form>
                <form role="form" class="basic-form">
                    <div class="form-group clear" hidden="hidden">
                        <label  class="control-label pull-left">用户名：</label>
                        <div class="formCon">
                            <input type="hidden" id="memid" />
                            <div class="namePhone" id="swname"></div>
                        </div>
                    </div>
                    <div class="form-group clear">
                        <label  class="control-label pull-left">手机号码：</label>
                        <div class="formCon">
                            <input type="hidden" id="swhphone" />
                            <div class="namePhone" id="swphone"></div>
                        </div>
                    </div>
                    <div class="form-group mb0 clear">
                        <label  class="control-label pull-left">短信验证码：</label>
                        <div class="formCon findYanz-B ">
                            <input type="text" class="form-control " id="swcode" placeholder="">
                            <a class="btn btn-default" id="wjpwdyanzbtn">获取验证码</a>
                            <span class="help-block" style="clear:both" id="errorswcode"></span>
                        </div>
                    </div>
                    <div class="form-group clear">
                        <label  class="control-label pull-left"></label>
                        <div class="formCon">
                            <a class="btn btn-primary nextBtn" id="swbtn">下一步</a>
                        </div>
                    </div>
                </form>
                <form role="form" class="basic-form disNone">
                    <div class="form-group clear">
                        <label  class="control-label pull-left">用户名：</label>
                        <div class="formCon">
                            <div class="namePhone"></div>
                        </div>
                    </div>
                    <div class="form-group clear">
                        <label  class="control-label pull-left">手机号码：</label>
                        <div class="formCon">
                            <div class="namePhone"></div>
                        </div>
                    </div>
                    <div class="form-group clear">
                        <label  class="control-label pull-left"></label>
                        <div class="formCon">
                            <a class="btn btn-primary nextBtn">发送验证邮件</a>
                        </div>
                    </div>
                </form>
            </div>
            <!--步骤3：设置新密码-->
            <div class="stepThree disNone">
                <form role="form" class="basic-form">
                    <div class="form-group clear">
                        <label class="control-label pull-left" >新密码：</label>
                        <div class="formCon">
                            <input  type="password" id="stpwd" class="form-control">
                            <span class="help-block" id="errorstpwd"></span>
                        </div>
                    </div>
                    <div class="form-group clear">
                        <label class="control-label pull-left" >确认密码：</label>
                        <div class="formCon">
                            <input  id="stpwdagin" type="password" class="form-control">
                            <span class="help-block" id="errorstpwdagin"></span>
                        </div>
                    </div>
                    <div class="form-group clear">
                        <label  class="control-label pull-left"></label>
                        <div class="formCon">
                            <a class="btn btn-primary nextBtn" id="stbtn">确定</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <!--密码重置成功-->
        <div class="stepEnd disNone ">
            <img src="static/images/login/payWin.png" />
            <p class="p1">密码重置成功</p>
            <p class="p2">您可以返回<a href="${ctx}/login">登录页面</a>，重新登录。</p>
        </div>
    </div>
</div>
[/@insert]