[#assign userDetail = k_userinfo(user.id) /]
[#--左边内容--]
<div class="memberLeft" style="padding-bottom: 30px;">
    <div class="userInfo">
        <img src="[#if userDetail.avatar??]${dfsUrl}${userDetail.avatar}[#else]${ctx}/static/images/safe/user-logo.png[/#if]" id="headImg">
        <p>
            <span id="loginName">${secrecy("mobile",userDetail.loginName)}</span>
        </p>
        <div class="icon">
            <ul>
                <li>
                    <a href="${ctx}/uc/security" tgrz="${userDetail.payAccountNo}" title="托管认证" id="cunguan" class="icon_btn e [#if userDetail.payAccountNo??]hasbind[#else]unbind[/#if]"></a>
                    [#if userDetail.payAccountNo??]
                        <div class='tip_info tl' style='font-size: 12px'>您已开通托管账户<a href="${ctx}/uc/security">查看</a></div>
                    [#else]
                        <div class='tip_info tl' style='font-size: 12px;padding-left: 10px;'>您还未开通托管账户<a href="${ctx}/uc/security" style="">开通</a></div>
                    [/#if]
                </li>
                <li>
                    <a href="${ctx}/uc/security" smrz="${userDetail.idNo}" title="实名认证" id="realname" class="icon_btn a [#if userDetail.idNo??]hasbind[#else]unbind[/#if]"></a>
                [#if k_userinfo(currentUser.id).idNo??]
                    <div class='tip_info tl' style='font-size: 12px'>您已实名认证<a href="${ctx}/uc/security">查看</a></div>
                [#else]
                    <div class='tip_info tl' style='font-size: 12px;padding-left: 10px;'>您还未进行实名认证<a href="${ctx}/uc/security" style="">开通</a></div>
                [/#if]
                </li>
                <li>
                    <a href="${ctx}/uc/security"  sjrz="${userDetail.mobile}" title="手机认证" id="mobile" class="icon_btn c [#if userDetail.mobile??]hasbind[#else]unbind[/#if]"></a>
                [#if userDetail.mobile??]
                    <div class='tip_info tl' style='font-size: 12px'>您已手机认证<a href="${ctx}/uc/security">查看</a></div>
                [#else]
                    <div class='tip_info tl' style='font-size: 12px;padding-left: 10px;'>您还未进行手机认证<a href="${ctx}/uc/security" style="">开通</a></div>
                [/#if]
                </li>
            </ul>
        </div>
    </div>
    <div class="mNav" id="mNavId">
        <ul>
            <li [#if module=='index']class="cur"[/#if] action="index"><i class="icon fa fa-tasks"></i>账户总览</li>
            <li [#if module=='capital']class="cur"[/#if] action="capital"><i class="icon fa fa-money"></i>资金管理</li>
            <li [#if module=='investment']class="cur"[/#if] action="investments"><i class="icon fa fa-bar-chart-o"></i>投资记录</li>
            <li [#if module=='recovery']class="cur"[/#if] action="recovery"><i class="icon fa fa-briefcase"></i>收款明细</li>
            <li [#if module=='borrowing']class="cur"[/#if] action="borrowing"><i class="icon fa fa-suitcase"></i>我的借款</li>
            [#if setting.transferSetting.transferEnable]
                <li [#if module=='transfer']class="cur"[/#if] action="transfer"><i class="icon fa fa-refresh"></i>债权转让</li>
            [/#if]
            <li [#if module=='security']class="cur"[/#if] action="security"><i class="icon fa fa-address-book"></i>个人资料</li>
            <li [#if module=='referral']class="cur"[/#if] action="referral"><i class="icon fa fa-sign-language"></i>邀请奖励</li>
        </ul>
    </div>
</div>

[#-- 修改头像隐藏框 --]
<div class="modal fade modal-fullscreen" id="modifyAvatarModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title">上传头像</h4>
            </div>
            <form class="form-horizontal" [#--id="loginPasswordModifForm"--] action="${ctx}/uc/avatar/upload" method="post" enctype="multipart/form-data">
                <div class="modal-body">
                    <div class="form-group">
                        <input class="photo-file" type="file" name="imgFile" id="fcupload" onchange="readURL(this);" />
                        <img alt="" src="" id="cutimg" />
                        <input type="hidden" id="x" name="x" />
                        <input type="hidden" id="y" name="y" />
                        <input type="hidden" id="w" name="w" />
                        <input type="hidden" id="h" name="h" />
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