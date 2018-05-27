[#assign userDetail = k_userinfo(kuser.id) /]
[@nestedstyle]
    [@css href="css/safety.css" /]
[/@nestedstyle]
[@nestedscript]
    [#-- RSA加密 --]
    [#--[@js src="lib/tools/rsa/jsbn.min.js" /]--]
    [#--[@js src="lib/tools/rsa/prng4.min.js" /]--]
    [#--[@js src="lib/tools/rsa/rng.min.js" /]--]
    [#--[@js src="lib/tools/rsa/rsa.min.js" /]--]
    [#--[@js src="lib/tools/base/base64.min.js" /]--]
    [#-- validate 验证器 --]
    [#--[@js src="lib/validate/jquery.validate.min.js" /]--]
    [#--[@js src="lib/validate/jquery.validate.method.min.js" /]--]
    [@js src="js/user/user.security.js" /]
    [#-- 身份认证 --]
    <script>
        var payAccountNo = '${userDetail.payAccountNo}';
        var userType = '${userDetail.type}';
        var investAgreementNo = '${(payAccountInfo.investAgreementNo)!""}';
        var chargeAgreementNo = '${(payAccountInfo.chargeAgreementNo)!""}';
    </script>
    [#if !userDetail.idNo]
        [@js src="js/user/identity.min.js" /]
    [/#if]
    [@js src="js/user/identity.min.js" /]
    [#-- 登录密码 --]
    [@js src="js/user/login_password.modif.min.js" /]
    <script type="text/javascript">
        jQuery(document).ready(function($) {
            if(${isAuth?string("true", "false")}){
                $('#isOpenBtn').modal({
                    backdrop : 'static'
                });
            }
        });
    </script>
[/@nestedscript]
[@insert template="layout/uc_layout" title="安全中心" memberContentId="safety" module="security" nav="uc" currentUser=kuser user=kuser]
<div class="chongzhiTop">
    <h3>个人资料</h3>
</div>
<div class="safetyList clearfix">
    <ul>
        <!-- 企业用户开始 -->
        <li id="noOpen">
            <label>托管账户</label>
            <span>与中金支付合作资金托管，保障您的资金安全</span>
            [#if !userDetail.hasPayAccount()]
                [#if !userDetail.idNo??]
                    <a href="javascript:void(0);" class="fr" id="openBtn">请先<em id="authRealToOpenAcc" style="color: #EA644A;">[实名认证]</em></a>
                [#else]
                    <a href="${ctx}/pay/open-account" class="fr" id="openBtn">开通</a>
                [/#if]
            [#elseif payAccountInfo.accountState == '15']
                <a href="${ctx}/pay/open-account" class="fr" id="openBtn">待认证[继续认证]</a>
            [#elseif payAccountInfo.accountState == '40']
                <a href="${ctx}/pay/open-account" class="fr" id="openBtn">审核驳回[重新认证]</a>
            [#else]
                <a href="${ctx}/pay/login" target="_blank" class="fr">登录</a>
                <a href="javascript:banlanceQuery()" class="fr" id="openBtn">查询余额</a>
                <a href="javascript:void(0);" class="fr">已开通</a>
            [/#if]
        </li>

        <!-- 实名认证开始 -->
        <li class="realname">
            <label>实名认证</label>
            <span>保障账户安全，只有完成实名认证才能投资</span>
            [#if !userDetail.idNo??]
                <a href="javascript:void(0);" class="fr" id="authReal">未认证</a>
            [#else]
                <a href="javascript:void(0);" class="fr" id="hasAuthReal">已认证</a>
            [/#if]
        </li>
        <!-- 实名认证结束 -->

        <li class="loginPsw">
            <label>登录密码</label>
            <span>登录时需要输入的密码</span>
            <a href="javascript:void(0);" class="fr" id="modifyPassword">修改</a>
        </li>

        [#--<li class="loginPsw">--]
            [#--<label>自动还款签约</label>--]
            [#--<span>自动还款</span>--]
            [#--[#assign autoRepaySign=userMeta("AUTO_REPAYMENT_SIGN", user.id) /]--]
            [#--[#if autoRepaySign?? && autoRepaySign.autoRepaySign]--]
                [#--<a href="javascript:void(0);" class="fr">已签约</a>--]
                [#--<a href="javascript:void(0);" class="fr" id="autoRepaymentSignView">查看</a>--]
            [#--[#else]--]
                [#--<a href="javascript:void(0);" class="fr" id="autoRepaymentSign">未签约</a>--]
            [#--[/#if]--]
        [#--</li>--]

        <li class="safetyPhone">
            <label>手机号码</label>
            <span>${secrecy("mobile", userDetail.mobile)}</span>
            <a href="javascript:void(0);" class="fr">已绑定</a>
        </li>

        [#if userDetail.hasPayAccount() && payAccountInfo?? ]
            <li class="safetyPhone">
                <label>银行卡</label>
                <span>[#if cards?size == 0]未绑卡[#else]已绑定${cards?size}张银行卡[/#if]</span>
                [#if cards?size == 0]
                    <a href="${ctx}/pay/bankcard/bind" class="fr">绑定</a>
                [#else]
                    <a href="${ctx}/uc/bank_card" class="fr">查看</a>
                [/#if]
            </li>

            <li class="safetyPhone">
                <label>支付账户签约</label>
                [#if payAccountInfo.chargeAgreementNo == "" ]
                    <a href="${ctx}/pay/signed?agreementType=20" class="fr">自动还款签约</a>
                [#else]
                    <a href="#" class="fr chargeTermination">自动还款解约</a>
                [/#if]
                [#--[#if payAccountInfo.investAgreementNo == ""]
                    <a href="${ctx}/pay/signed?agreementType=60" class="fr">自动投资签约</a>
                [#else]
                    <a href="#" class="fr investTermination">自动投资解约</a>
                [/#if]--]
            </li>
        [/#if]

        <!-- 企业用户开始 -->
    </ul>

</div>

[#-- 自动投资解约 --]
<div class="modal fade" id="affirmInvestTerminationModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title">自动投资解约</h4>
            </div>
            [#--<form class="form-horizontal" action="${ctx}/pay/termination?agreementNo=${payAccountInfo.investAgreementNo}" method="post">--]
                <div class="modal-body" style="text-align: center;">
                    确认自动投资解约？
                </div>
                <div class="modal-footer" style="text-align: center;">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="submit" class="btn btn-primary" id="investTerminationBtn">确认</button>
                </div>
           [#-- </form>--]
        </div>
    </div>
</div>
<div class="modal fade" id="affirmChargeTerminationModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title">自动还款解约</h4>
            </div>
            <div class="modal-body" style="text-align: center;">
                确认自动还款解约？
            </div>
            <div class="modal-footer" style="text-align: center;">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="submit" class="btn btn-primary" id="chargeTerminationBtn">确认</button>
            </div>
        </div>
    </div>
</div>

[#-- 实名认证隐藏框 --]
<div class="modal fade" id="hasAuthModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title">[#if userDetail.type=='GENERAL']个人实名认证[#else ]企业实名认证[/#if]</h4>
            </div>
            <form class="form-horizontal" id="identityForm" action="${ctx}/uc/security/identity" method="post">
                <div class="modal-body">
                    <input type="hidden" name="type" value="${type}">
                    <div id="identityBox"></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="submit" class="btn btn-primary">保存</button>
                </div>
            </form>
        </div>
    </div>
</div>

[#-- 实名认证后跳转开通环迅账户 隐藏框 --]
<div class="modal fade" id="isOpenBtn">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title">提示</h4>
            </div>
            <form action="${ctx}/pay/open-account" method="post">
                <div class="modal-body">
                    是否立即开通中金托管账户？
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="submit" class="btn btn-primary">立即开通</button>
                </div>
            </form>
        </div>
    </div>
</div>

[#-- 修改登录密码隐藏框 --]
<div class="modal fade" id="modifyPasswordModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title">修改登录密码</h4>
            </div>
            <form class="form-horizontal" id="loginPasswordModifForm" action="${ctx}/uc/security/login_password" method="post">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="currentPassword" class="col-sm-4">原登录密码</label>
                        <div class="col-md-6 col-sm-10">
                            <input id="currentPassword" type="password" name="currentPassword" value="" class="form-control" placeholder="请输入原登录密码" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password" class="col-sm-4">新登录密码</label>
                        <div class="col-md-6 col-sm-10">
                            <input id="password" type="password" name="password" value="" class="form-control" placeholder="请输入新登录密码" maxlength="18" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="rePassword" class="col-sm-4">重复新登录密码</label>
                        <div class="col-md-6 col-sm-10">
                            <input id="rePassword" type="password" name="rePassword" value="" class="form-control" placeholder="重复登录密码" maxlength="18" />
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="submit" class="btn btn-primary">保存</button>
                </div>
            </form>
        </div>
    </div>
</div>

[#-- 自动还款签约隐藏框 --]
<div class="modal fade" id="autoRepaymentSignModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title">自动还款签约</h4>
            </div>
            <form class="form-horizontal" id="autoRepaymentSignForm" action="${ctx}/uc/ips/auto_sign" method="post">
                <input type="hidden" name="signType" value="AUTO_REPAYMENT_SIGN" />
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-sm-4">有效期</label>
                        <div class="col-md-6 col-sm-10">
                            <input id="validity" type="text" name="validity" value="${autoRepaySign.validity}" class="form-control" placeholder="有效期" />
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="submit" class="btn btn-primary autoSign">签约</button>
                    <a href="${ctx}/uc/ips/un_sign?signType=AUTO_REPAYMENT_SIGN" class="btn btn-primary autoSign" style="display: none;">解约</a>
                </div>
            </form>
        </div>
    </div>
</div>

<script type="text/html" id="identityPer">
    <div class="form-group">
        <label class="col-sm-4" style="text-align: right">真实姓名</label>
        <div class="col-md-6 col-sm-10">
            <input type="text" value="${userDetail.realName}" name="realName" id="realName" class="form-control" placeholder="请输入真实姓名" onkeyup="this.value=this.value.replace(/^ +| +$/g,'')" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-4" style="text-align: right">身份证号</label>
        <div class="col-md-6 col-sm-10">
            <input type="text" value="${secrecy("idNo", userDetail.idNo)}" name="idNo" id="idNo" class="form-control" placeholder="请输入真实身份证号码" onkeyup="this.value=this.value.replace(/^ +| +$/g,'')" />
        </div>
    </div>
</script>
<script type="text/html" id="identityEps">
    <div class="form-group">
        <label class="col-sm-4" style="text-align: right">企业名称</label>
        <div class="col-md-6 col-sm-10">
            <input type="text" value="${userDetail.corpName}" name="corpName" id="corpName" class="form-control" placeholder="请输入真实企业名称" onkeyup="this.value=this.value.replace(/^ +| +$/g,'')" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-4" style="text-align: right">营业执照/社会统一信用码</label>
        <div class="col-md-6 col-sm-10">
            <input type="text" value="${secrecy("corpLicenseNo", userDetail.corpLicenseNo!"")}" name="corpLicenseNo" id="corpLicenseNo" class="form-control" placeholder="请输入真实营业执照/社会统一信用码" onkeyup="this.value=this.value.replace(/^ +| +$/g,'')" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-4" style="text-align: right">法人姓名</label>
        <div class="col-md-6 col-sm-10">
            <input type="text" value="${userDetail.realName}" name="realName" id="realName" class="form-control" placeholder="请输入真实法人姓名" onkeyup="this.value=this.value.replace(/^ +| +$/g,'')" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-4" style="text-align: right">法人身份证</label>
        <div class="col-md-6 col-sm-10">
            <input type="text" value="${secrecy("idNo", userDetail.idNo)}" name="idNo" id="idNo" class="form-control" placeholder="请输入真实法人身份证号码" onkeyup="this.value=this.value.replace(/^ +| +$/g,'')" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-4" style="text-align: right">法人手机号</label>
        <div class="col-md-6 col-sm-10">
            <input type="text" value="${secrecy("mobile", userDetail.legalMobile)}" name="ipsMobile" id="ipsMobile" class="form-control" placeholder="请输入法人手机号" onkeyup="this.value=this.value.replace(/^ +| +$/g,'')" />
        </div>
    </div>
</script>

<script id="balanceTpl" type="text/html">
    <table style="margin: 10px; border:1px solid #ccc;" border="1" cellpadding="0">
        <tr>
            <td style="width:300px;height: 40px; line-height: 40px;">托管账户</td>
            <td style="width:300px;height: 40px; line-height: 40px;">可用余额</td>
        </tr>
        <tr>
            <td style="height: 40px; line-height: 40px;">{{ d.data.paymentAccountNumber }}</td>
            <td style="height: 40px; line-height: 40px;">{{ currency(d.data.balance/100, '￥') }}</td>
        </tr>
    </table>
</script>
[/@insert]