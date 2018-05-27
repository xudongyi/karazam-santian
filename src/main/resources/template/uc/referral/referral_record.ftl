[@insert template="layout/uc_layout" title="用户中心" module="referral" nav="referral_record" currentUser=kuser user=kuser]
<!--右边内容-->
<div class="content1">
    <div class="recommend" style="margin-top:0px;">
        <table>
            <tr>
                <th>ID</th>
                <th>用户名</th>
                <th>推荐金额</th>
                <th>推荐费率</th>
                <th>推荐费</th>
                <th>计划结算时间</th>
                <th>实际结算时间</th>
                <th>状态</th>
            </tr>
            [#list referrals as referral]
                <tr>
                    <td>${referral.id}</td>
                    <td>${referral.reUserNickName}</td>
                    <td>${referral.referralAmt?string("currency")}</td>
                    <td>${referral.referralFeeRate}%</td>
                    <td>${referral.referralFee?string("currency")}</td>
                    <td>${referral.planPaymentDate?string("yyyy-MM-dd HH:mm:ss")}</td>
                    <td>${(referral.paymentDate?string("yyyy-MM-dd HH:mm:ss"))!"-"}</td>
                    <td>${referral.stateStr}</td>
                </tr>
            [/#list]
            [#if referrals?size == 0]
                <tr>
                    <td colspan="8">没有记录！</td>
                </tr>
            [/#if]
        </table>
    </div>
</div>
[/@insert]