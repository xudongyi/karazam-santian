[@nestedscript]
    [@js src="js/user/avatar.min.js" /]
[/@nestedscript]
[@insert template="layout/uc_layout" title="用户中心 - 投资记录" module="investment" nav="investment" currentUser=kuser user=kuser]

[#--列表--]
<div class="index-list">
    <div class="index-title">
        <div class="name pull-left">我的投资记录</div>
        <div class="name pull-right"><a href="${ctx}/uc/investment" style="color: #00a7ff; font-size: 14px;">返回</a></div>
    </div>
    <div class="index-table">
        <table>
            <tr>
                <th>编号</th>
                <th>投资金额</th>
                <th>状态</th>
                <th>操作方式</th>
                <th>创建时间</th>
            </tr>
            <tbody id="data">
                [#list investmentRecords as investmentRecord]
                    <tr>
                        <td>${investmentRecord.id}</td>
                        <td>${investmentRecord.amount?string("currency")}</td>
                        <td>${investmentRecord.stateDes}</td>
                        <td>${investmentRecord.operationMethodDes}</td>
                        <td>${investmentRecord.createDate?string("yyyy年MM月dd日 HH时mm分")}</td>
                    </tr>
                [/#list]
            </tbody>
        </table>
    </div>
</div>
[/@insert]

