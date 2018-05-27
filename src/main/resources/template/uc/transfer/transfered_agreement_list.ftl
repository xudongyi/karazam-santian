[@insert template="layout/uc_layout" title="用户中心 - 转让协议" module="transfer" nav="" currentUser=user user=user]

[#--列表--]
<div class="index-title">
    <div class="name pull-left"><a href="${ctx}/uc/transfer" style="color: #36adf7; font-size: 14px;padding-right: 5px">债权转让</a><i class="fa fa-angle-right" style="padding-right: 5px"></i>转让协议</div>
</div>
<div style="padding: 25px 25px 0 0;">
    <div style="float: right">
    </div>
</div>
<div class="answer" style="margin-top: 40px">
    <ul class="title">
        <li class="tar" style="width:15%;">标的名称</li>
        <li class="tac" style="width:15%;">购买金额</li>
        <li class="tac" style="width:20%;">签订时间</li>
        <li class="tal" style="width:15%;">操作</li>
    </ul>
    <div>
        [#list transferInvestments as transferInvestment]
            <ul class="content">
                <li class="tar" style="width:15%;">
                    <div class="titleName">
                        ${transferInvestment.title}
                    </div>
                </li>
                <li class="tac" style="width:15%;">${transferInvestment.capital?string("currency")}</li>
                <li class="tac" style="width:20%;">${transferInvestment.investmentDate?string("yyyy-MM-dd")}</li>
                <li class="tal" style="width:15%;margin-top: 0px">
                    <a target="_blank" href="${ctx}/uc/transfer/transferin/agreement/detail/${transferInvestment.id}"
                       style="color: #36adf7">查看</a>
                </li>
            </ul>
        [/#list]
    </div>
</div>
[/@insert]

