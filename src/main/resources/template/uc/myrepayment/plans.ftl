[@nestedscript]
    [@js src="js/user/avatar.min.js" /]
[/@nestedscript]
[@insert template="layout/uc_layout" title="用户中心 - 回款明细" module="investment" nav="investment" currentUser=kuser user=kuser]

[#--列表--]
<div class="index-list">
    <div class="index-title">
        <div class="name pull-left">我的回款明细</div>
        <div class="name pull-right"><a href="${ctx}/uc/investment" style="color: #00a7ff; font-size: 14px;">返回</a></div>
    </div>
    <div class="index-table">
        <table>
            <tr>
                <th>期次</th>
                <th>还款日期</th>
                <th>计划本金</th>
                <th>计划利息</th>
                <th>逾期利息</th>
                <th>服务费</th>
                <th>实际还款</th>
                <th>状态</th>
            </tr>
            <tbody id="data">
                [#list repaymentPlans as repaymentPlan]
                    <tr>
                        [#--<td>${repaymentPlan.orderNo}</td>--]
                        <td>${repaymentPlan.repaymentRecordPeriod}/${borrowing.period}</td>
                        [#if repaymentPlan.paidDate?? ]
                            <td>${repaymentPlan.repaymentRecordPayDate?string("yyyy-MM-dd")}(实际日期：${repaymentPlan.paidDate?string("yyyy-MM-dd")})</td>
                        [#else ]
                            <td>${repaymentPlan.repaymentRecordPayDate?string("yyyy-MM-dd")}</td>
                        [/#if]
                        <td>${repaymentPlan.repaymentRecordCapital?string("currency")}</td>
                        <td>${repaymentPlan.repaymentRecordInterest?string("currency")}</td>
                        <td>${repaymentPlan.recoveryOverdueInterest?string("currency")}</td>
                        <td>${repaymentPlan.recoveryRecoveryFee?string("currency")}</td>
                        <td>${repaymentPlan.paidAmount?string("currency")}</td>
                        <td>${repaymentPlan.stateDes}</td>
                    </tr>
                [/#list]
            </tbody>
        </table>
    </div>
</div>
[/@insert]

