[@nestedscript]
    [@js src="js/user/user.prepayment.js" /]
[/@nestedscript]
[@insert template="layout/uc_layout" title="用户中心 - 提前还款明细" module="borrowing" nav="" currentUser=user user=user]
[#--列表--]
<div class="chongzhiTop">
    <h3>提前还款明细</h3>
</div>

<div style="padding: 25px 25px 0 0;">
    提前还款金额：${preRepaymentAmount?string("currency")}元
    <div style="float: right">
        <a id="repaymentBox" data-id="${borrowing.id}" href="#" type="button" class="center_btn_c">一键还款</a>
    </div>
</div>

<div class="answer" style="margin-top: 40px">
    <ul class="title">
        <li class="tar" style="width:5%;">期数</li>
        <li class="tac" style="width:20%;">计划还款日期</li>
        <li class="tac" style="width:10%;">计划本金</li>
        <li class="tac" style="width:10%;">计划利息</li>
        <li class="tac" style="width:10%">逾期罚息</li>
        <li class="tac" style="width:10%;">服务费</li>
        <li class="tac" style="width:20%;">提前还款金额</li>
        <li class="tac" style="width:10%;">状态</li>
    </ul>
    <div>
        [#list repayingRepayments as repayment]
            <ul class="content">

                <li class="tar" style="width:5%;">
                    <div class="titleName">
                    ${repayment.period}/${borrowing.repayPeriod}
                    </div>
                </li>
                [#if repayment.paidDate??]
                    <li class="tac" style="width:20%;">
                        实际还款：${repayment.paidDate?string("yyyy-MM-dd")}（${repayment.payDate?string("yyyy-MM-dd")}）
                    </li>
                [#else]
                    <li class="tac" style="width:20%;">${repayment.payDate?string("yyyy-MM-dd")}</li>
                [/#if]
                <li class="tac" style="width:10%;">${repayment.capital?string("currency")}</li>
                <li class="tac" style="width:10%;">${repayment.interest?string("currency")}</li>
                <li class="tac" style="width:10%;">${repayment.repaymentOverdueInterest()?string("currency")}</li>
                <li class="tac" style="width:10%;">${repayment.repaymentFee()?string("currency")}</li>
                <li class="tac" style="width:20%;">${repayment.getRepaymentAmount()?string("currency")}</li>
                <li class="tac" style="width:10%;">${repayment.stateDes}</li>
            </ul>
        [/#list]
    </div>
</div>

<div class="modal fade" id="repaymentBoxModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title">确认提前还款</h4>
            </div>
        [#--<form class="form-horizontal" action="${ctx}/pay/termination?agreementNo=${payAccountInfo.investAgreementNo}" method="post">--]
            <div class="modal-body" style="text-align: center;">
                确认提前还款？
            </div>
            <div class="modal-footer" style="text-align: center;">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="submit" class="btn btn-primary" id="repaymentBtn">确认</button>
            </div>
        [#-- </form>--]
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
                url: ctx + "/uc/repayment/prepayment/"+dataId,
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

//                    data = JSON.parse(data);
//                    if(data.code == '000'){
//                        layer.msg(data.message);
//                        window.location.reload();
//                        return ;
//                    }
//                    layer.msg(data.message);
                }
            });
        });

    });

</script>

