[@insert template="layout/uc_layout" title="用户中心 - 还款明细" module="borrowing" nav="" currentUser=user user=user]

[#--列表--]
<div class="index-title">
    <div class="name pull-left"><a href="${ctx}/uc/borrowing" style="color: #36adf7; font-size: 14px;padding-right: 5px">我的借款</a><i class="fa fa-angle-right" style="padding-right: 5px"></i>还款计划</div>
</div>
<div style="padding: 25px 25px 0 0;">
    [#if borrowing.progress == "REPAYING"]
        <div style="float: right">
            <a href="${ctx}/uc/repayment/prepayment_view/${borrowing.id}" type="button" class="center_btn_c">提前还款</a>
        </div>
    [/#if]
</div>
<div class="answer" style="margin-top: 40px">
    <ul class="title">
        <li class="tar" style="width:5%;">期数</li>
        <li class="tac" style="width:27%;">计划还款日期</li>
        <li class="tac" style="width:9%;">计划本金</li>
        <li class="tac" style="width:9%;">计划利息</li>
        <li class="tac" style="width:9%">逾期罚息</li>
        <li class="tac" style="width:9%;">服务费</li>
        <li class="tac" style="width:9%;">计划还款</li>
        <li class="tac" style="width:9%;">实际还款</li>
        <li class="tac" style="width:9%;">状态</li>
        <li class="tal" style="width:5%;">操作</li>
    </ul>
    <div>
        [#list repayments as repayment]
            <ul class="content">

                <li class="tar" style="width:5%;">
                    <div class="titleName">
                    ${repayment.period}/${borrowing.repayPeriod}
                    </div>
                </li>
                [#if repayment.paidDate??]
                    <li class="tac" style="width:27%;">
                        实际还款：${repayment.paidDate?string("yyyy-MM-dd")}（${repayment.payDate?string("yyyy-MM-dd")}）
                    </li>
                [#else]
                    <li class="tac" style="width:27%;">${repayment.payDate?string("yyyy-MM-dd")}</li>
                [/#if]
                <li class="tac" style="width:9%;">${repayment.capital?string("currency")}</li>
                <li class="tac" style="width:9%;">${repayment.interest?string("currency")}</li>
                <li class="tac" style="width:9%;">${repayment.repaymentOverdueInterest()?string("currency")}</li>
                <li class="tac" style="width:9%;">${repayment.repaymentFee()?string("currency")}</li>
                <li class="tac" style="width:9%;">${(repayment.capital+repayment.interest+repayment.repaymentOverdueInterest()+repayment.repaymentFee())?string("currency")}</li>
                [#if repayment.paidDate??]
                    <li class="tac" style="width:9%;">${repayment.paidAmount?string("currency")}</li>
                [#else]
                    <li class="tac" style="width:9%;">-</li>
                [/#if]
                [#if repayment.state=='REPAYING' && repayment.isOverdue]
                    <li class="tac" style="width:9%;color: red">已逾期(${repayment.getOverdueTime()}天)</li>
                [#else ]
                    <li class="tac" style="width:9%;">${repayment.stateDes}</li>
                [/#if]
                <li class="tal" style="width:5%;margin-top: 0px">
                    [#if repayment.state=='REPAYING' && repayment.period == period ]
                        <a id="repaymentBox" data-id="${repayment.id}" href="#" style="color: #36adf7">还款</a>
                    [#else ]
                        <a href="javascript:void(0);" style="color:#9e9e9e;">还款</a>
                    [/#if]
                </li>
            </ul>
        [/#list]
    </div>
</div>


<div class="modal fade" id="repaymentBoxModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title">确认还款</h4>
            </div>
            <div class="modal-body" style="text-align: center;">
                确认还款？
            </div>
            <div class="modal-footer" style="text-align: center;">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="submit" class="btn btn-primary" id="repaymentBtn">确认</button>
            </div>
        </div>
    </div>
</div>



[/@insert]

<script>
    $(document).ready(function () {

        $('#repaymentBox').click(function () {
            $('#repaymentBoxModal').modal({
                backdrop : 'static'
            });
        });
        $('#repaymentBtn').click(function () {
            var dataId = $('#repaymentBox').attr("data-id");
            $.ajax({
                type: "POST",
                url: ctx + "/uc/repayment/repayment/"+dataId,
                data: {},
                success: function(data){
                    $('#repaymentBoxModal').hide();
                    data = JSON.parse(data);
                    console.log(data);
                    if(data.code == '000'){
                        layer.msg(data.message);
                    }else {
                        layer.msg(data.message);
                    }
                    setTimeout("window.location.reload()", 3000);
//                    window.location.reload();
                }
            });
        });

    });

</script>

