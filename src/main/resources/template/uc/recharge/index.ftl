[@nestedstyle]
    [@css href="css/cubeportfolio.min.css" /]
    [@css href="css/center_index.css" /]
    [@css href="css/recharge.css" /]
[/@nestedstyle]
[@nestedscript]
    [@js src="js/util/assetDetail.js" /]
    [#--[@js src="js/util/bindbank.js" /]--]
    [#--[@js src="js/util/easyPay.js" /]--]
    [@js src="js/user/user.recharge.js" /]
[/@nestedscript]
[@insert template="layout/uc_layout" title="充值中心" module="index" currentUser=kuser user=kuser]
<div class="paypwd">
    <div class="save font-gray clearfix">
        <span><i class="security"></i>联合中金支付资金账户托管，保障资金安全</span>
        <span class="count">可用余额：<em class="font-orange">${userFinance.available?string("currency")}</em>元</span>
    </div>
    <div class="pay-type font-gray clearfix">
        <a href="${ctx}/uc/capital" class="blue save-records">充值记录</a>
    </div>
    <form id="netPayFrom" action="" method="post" target="_blank">
        <input type="hidden" name="rechargeBusType" id="rechargeBusType" value="GENERAL">
        <div class="pay-content" style="display:block;">
            <p class="onlyie">（部分银行只支持IE浏览器）</p>
            <div class="select-pay-type clearfix font-gray">
                <div class="select fl">选择支付：</div>
                <div class="pay-types-span fl">
					<span id="UMPAY_dep_net" data-yh="sft1" bankchannel="UMPAY_dep_net" class="cur">
					 	<img src="${ctx}/static/images/zjzf.png" style="width: 116px;height: 34px"><i></i>
                    </span>
                </div>
            </div>
            <p class="inputbox">充值金额：<input type="text" name="amount" placeholder="请输入充值金额" id="amount">&nbsp;&nbsp;元<label id="at"></label></p>
            <p class="btn1 clearfix" style="background: none"><input type="submit" class="button button-normal radius-sm savebtn" id="save-sure" value="确定充值"></p>
            <!--<p class="center_btn_c clearfix btn"><a class="button button-normal radius-sm savebtn" id="save-sure">确定充值</a></p>-->

        </div>
    </form>
</div>
[#-- 隐藏元素 --]
<div class="modal fade" id="rechargeTipBox" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">
                    充值信息提示
                </h4>
            </div>
            <hr style="color: #00a0e9">
            <div class="modal-body" style="text-align: center">
                <img style="width: 200px;height: 50px;margin-right: 20px" src="${ctx}/static/images/ptdlogo.png">
                <img style="width: 200px;height: 50px;margin-left: 20px;"
                     src="${ctx}/static/images/zjzf.png">
                <p style="margin-top: 30px;font-size: 16px">请您在新打开的页面中完成充值，成功之前请不要关闭此页面！</p>
            </div>
            <hr style="color: #00a0e9">
            <div class="btn-group" style="margin-left: 180px;margin-bottom: 10px;margin-top:10px" ;>
                <button type="button" class="btn btn-success" onclick="location.href='${ctx}/uc/order'">
                    已完成付款
                </button>
                <button type="button" style="margin-left: 10px" class="btn btn-danger"
                        onclick="location.href='${ctx}/t/category/help'">
                    付款遇到问题
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
    [#-- 未绑定提现卡隐藏框 --]
    <div class="modal fade" id="hasIdCard" tabindex="-1" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">充值信息提示</h4>
                </div>
                <form class="form-horizontal" id="identityForm" action="${ctx}/uc/ips/login" method="get" target="_blank">
                    <div class="modal-body">
                        <div class="modal-body" style="text-align: center">
                            <img style="width: 200px;height: 50px;margin-right: 20px" src="${ctx}/static/images/ptdlogo.png">
                            <img style="width: 200px;height: 50px;margin-left: 20px;"
                                 src="${ctx}/static/images/zjzf.png">
                            <p id="tips-message" style="margin-top: 30px;font-size: 16px"></p>
                        </div>
                    </div>
                    <div class="btn-group" style="margin-left: 180px;margin-bottom: 10px;margin-top:10px">
                        <button type="button" style="margin-left: 10px" class="btn btn-success"
                                onclick="location.href='${ctx}/uc/recharge'">
                            返回充值
                        </button>
                        <button type="submit" style="margin-left: 10px" class="btn btn-danger">立即前往</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
[/@insert]