[@nestedstyle]
    [@css href="css/cubeportfolio.min.css" /]
    [@css href="css/salelist.css" /]
[/@nestedstyle]
[@nestedscript]
    [@js src= "js/jquery.cubeportfolio.min.js"/]
    [@js src= "js/transfer/transfer.detail.js"/]
[/@nestedscript]
[@insert template="layout/transfer_detail_layout" title="${borrowing.title}-转让专区" user=kuser]
<div class="sales-banner">
    <div class="layout">
        <div class="sales-info clearfix">
            <div class="left-sale" id="bidDetailLeft">
                <h2>
                    <span class="fl">${borrowing.title}</span>
                    [#--<span class="tags fl">续贷</span>--]
                    <a class="fl" href="${ctx}/investment/${borrowing.id}" target="_blank"><span class="fl bid-id">原借款编号：${borrowing.projectNo}</span></a>
                    <a href="${ctx}/transfer/agreement/${transfer.id}" target="_blank" class="fr">债权转让及受让协议</a>
                </h2>
                <div class="info-rate clearfix">
                    <div class="transfer-price item1">
                        <div class="first-box">
                            <strong>${transfer.worth}</strong>
                            <em>元</em>
                        </div>
                        <p>转让价格</p></div>
                    <div class="transfer-price item2">
                        <div class="first-box orange-box">
                            <strong>${borrowing.realInterestRate}</strong>
                            <em>%</em>
                        </div>
                        <p>预期年化</p>
                    </div>
                    <div class="transfer-progress item3">
                        <div class="progress-bar" data-width="${(transfer.transferedCapital/transfer.capital*100)?string("#.##")}%"><i style="width: ${(transfer.transferedCapital/transfer.capital*100)?string("#.##")}%;"></i></div>
                        <p>进度：<em>${(transfer.transferedCapital/transfer.capital*100)?string("#.##")}%</em></p></div>
                </div>
                <div class="transfer-intro clearfix">
                    <ul class="type-ul">
                        <li><label>债权本金：</label><span><em id="borrowDetialAmount">${transfer.capital}</em>元</span></li>
                        <li><label>剩余期限：</label><span>${transfer.residualPeriod}${transfer.residualUnit}</span></li>
                        <li><label>原标年化：</label><span>${borrowing.realInterestRate}</span></li>
                        <li><label>还款方式：</label><span>${borrowing.repaymentMethod.getDisplayName()}</span></li>
                        <li><label>计息时间：</label><span>T+0计息</span></li>
                        <li><label>最近待收日期：</label><span>${transfer.nextRepaymentDate?string("yyyy-MM-dd")}</span></li>
                    </ul>
                </div>
            </div>
            <div class="right-sale zhuanInvestment">
                <dl id="tender_balance" style="display: block;">
                    <ul style="padding-left: 0px;">
                        <li>剩余可承接债权：
                            <span>
                                <strong id="tenderable_amount">${transfer.surplusCapital}</strong>元
                            </span>
                        </li>
                        <li class="remainTime">剩余时间&nbsp;
                            <span class="countdown" id="transferExpireTime" expireDate="${expireDate}">
                                <span class="font-orange" id="expireLastD">00</span>天
                                <span class="font-orange" id="expireLastH">00</span>小时
                                <span class="font-orange" id="expireLastM">00</span>分
                                <span class="font-orange" id="expireLastS">00</span>秒
                            </span>
                        </li>
                    </ul>
                    <div class="base" id="user_blance_id">
                    <p>
                        [#if user??]
                            <span class="fl">账户余额：
                                    <strong class="Money">${currentUserFinance.available?string("currency")}</strong>元
                                </span>
                            <a href="${ctx}/uc/recharge" class="all-in font-blue" style="margin-left: 5px">充值 </a>
                            <a href="javascript:void(0)" id="tender_all_btn" class="fr all-in font-blue">全部承接 </a>
                        [#else]
                            <span class="fl">
                                    账户余额：
                                    <a href="javascript:quickLogin()" class="font-blue loginBtn">登录</a>后可见</p>
                            </span>
                        [/#if]
                        </p>
                    </div>
                    <div class="input calculates">
                        <a class="reduce" id="reduce" href="javascript:void(0);">-</a>
                        <input placeholder="100元起投" class="trTXt" type="text" id="tender_amount">
                        <input id="depositStatus" type="hidden" value="0">
                        <input id="depositSwitch" type="hidden" value="1">
                        <a class="plus" id="plus" href="javascript:void(0);">+</a>
                    </div>
                    <p class="realPay unVisible" id="input_error_id"><em id="errorTips"></em></p>
                    <a href="javascript:void(0)" id="tender_buy_now" transferId="${transfer.id}" class="button block button-normal radius-ssm buy">立即购买</a>
                    <p class="tc">温馨提示：出借有风险，选择需谨慎</p>
                </dl>
                <dl id="statusBox">
                    <div class="status">
                        <p></p>
                        <div class="img"></div>
                        <a href="/list/showBidList" class="button block button-normal radius-ssm buy">关注其他项目</a>
                    </div>
                </dl>
            </div>
        </div>
    </div>
</div>
<section id="content">
    <div class="container">
        <div class="row">
            [#-- 项目左侧信息 --]
            [#include "transfer/detail-left.ftl" /]
            [#-- 项目右侧详情信息 --]
            [#include "transfer/detail-right.ftl" /]
        </div>
    </div>
</section>

[#-- 项目投资 隐藏元素 --]
[#include "transfer/investBox.ftl" /]

[#-- 快捷登录隐藏框 --]
<div class="modal fade" id="quickLoginModal">
    <div class="modal-dialog" style="width: 400px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title">快捷登录</h4>
            </div>
            <form class="form-horizontal" id="quickLoginForm">
                <div class="modal-body" style="height: 190px; padding: 30px;">
                    <div class="errorcls">
                        <div class="form-group">
                            <label class="radio-inline">
                                <input type="radio" name="radio1" id="radio1" value="GENERAL" checked=""> 个人用户
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="radio1" id="radio2" value="ENTERPRISE"> 企业用户
                            </label>
                        </div>
                    </div>
                    <div class="errorcls">
                        <div class="form-group">
                            <input id="username" name="username" type="text" class="form-control" placeholder="请输入您的手机号" autocomplete="off" style="padding:6px 0px">
                        </div>
                        <div class="form-group clear notes errorMessage" style="color: #d41616"></div>
                    </div>
                    <div class="errorcls">
                        <div class="form-group">
                            <input id="password" name="password" type="password" class="form-control" id="exampleInputPassword1" placeholder="请输入您的密码" autocomplete="off" style="padding:6px 0px">
                        </div>
                        <div class="form-group clear notes errorMessage" style="color: #d41616"></div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-block btn-primary" style="padding:6px 0px">登&nbsp;&nbsp;&nbsp;&nbsp;录</button>
                </div>
            </form>
        </div>
    </div>
</div>
[/@insert]