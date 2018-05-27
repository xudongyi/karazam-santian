[@nestedstyle]
    [@css href="css/cubeportfolio.min.css" /]
    [@css href="css/center_index.css" /]
[/@nestedstyle]
[@nestedscript]
    <script>
        var beginAmount = ${beginAmount!50};
    </script>
    [@js src="js/util/assetDetail.js" /]
    [@js src="js/util/easyPay.js" /]
    [@js src="js/user/user.withdraw.js" /]
    [#if !hasWithdrawCard]
        <script>
            $('#hasWithdrawCard').modal({backdrop: 'static'});
        </script>
    [/#if]
[/@nestedscript]
[@insert template="layout/uc_layout" title="提现中心" module="index" currentUser=user user=user]
<div class="paypwd">
    <div class="save font-gray clearfix">
        <span><i class="security"></i>联合中金支付资金账户托管，保障资金安全</span>
        <span class="count">可提现余额：<em class="font-orange">${userFinance.available?string("currency")}</em>元</span>
    </div>
    <div class="bank pay-content">
        <form id="withdrawalForm" action="${ctx}/uc/withdraw" method="post">
            <p class="inputbox" style="margin-bottom: 20px;margin-left: -25px">
                <label style="margin-left: 0">提现金额：</label>
                <input type="text" style="height: 40px" name="amount" placeholder="${beginAmount}元起提" id="amount">&nbsp;&nbsp;元
                <span><img src="${ctx}/static/images/correct-icon.png" width="15" height="15" class="successIcon hide"/></span>
            <p class="annotate" style="color:red;"></p>
            </p>
            <div class="clearfix" style="font-weight: 500">
                <p class="fee fl"><label style="font-weight: 500;margin-left: 0">实际到账金额：</label>
                    <amount class="edu" id="paymentAmout">0.00</amount>
                    元
                </p>
                <div class="fee fl">
                    <label style="font-weight: 500;margin-left: 0">手续费</label>：
                    <amount1 class="edu" id="fee">0.00</amount1>
                    元<i class="fa fa-question-circle-o"></i>
                    <div class="feetip tixing poundage" style="display: none;">
                        提现手续费手续规则介绍。
                    </div>
                </div>
            </div>
            <p>
                <button type="submit" class="button button-normal radius-sm takebtn" id="saveBtn">提现</button>
            </p>
        </form>
    </div>
    [#--注意事项--]
    <fieldset class="layui-elem-field">
        <legend style="padding-bottom: 10px;padding-top: 50px">提现说明</legend>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th style="width: 150px">时间</th>
                <th style="width: 150px">预计到账时间</th>
                <th>支持银行</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>工作日16:00前</td>
                <td>2小时</td>
                <td rowspan="2">
                    中国工商银行、中国农业银行、中国银行、中国建设银行、交通银行、中国邮政储蓄银行、邮政储蓄、邮政银行、中国邮政、中国邮政储蓄、中信银行、中国光大银行、华夏银行、中国民生银行、广东发展银行、招商银行、兴业银行、上海浦东发展银行、浦东发展银行、平安银行股份有限公司、深圳发展银行、中国平安银行、深圳市平安银行、深圳平安银行、恒丰银行、渤海银行、浙商银行、北京银行
                    、上海银行
                </td>
            </tr>
            <tr>
                <td>工作日16:00后<br>节假日、周末</td>
                <td style="vertical-align: middle">T+1(工作日)09:00前</td>
            </tr>
            </tbody>
        </table>
    </fieldset>
</div>
[#if !hasWithdrawCard]
[#-- 未绑定提现卡隐藏框 --]
<div class="modal fade" id="hasWithdrawCard">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">提示</h4>
            </div>
            <form class="form-horizontal" id="identityForm" action="${ctx}/uc/ips/login" method="get" target="_blank">
                <div class="modal-body">
                    <div class="form-group">
                        <div class="col-md-8 col-sm-12">
                            您尚未绑定提现银行卡，请前往托管账户绑定。
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">立即前往</button>
                </div>
            </form>
        </div>
    </div>
</div>
[/#if]
[/@insert]