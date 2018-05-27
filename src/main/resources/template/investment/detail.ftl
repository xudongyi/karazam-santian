[@nestedstyle]
	[@css href="css/cubeportfolio.min.css" /]
	[@css href="css/salelist.css" /]
[/@nestedstyle]
[@nestedscript]
	[@js src= "js/jquery.cubeportfolio.min.js"/]
	[@js src= "js/investment/detail.js"/]
[/@nestedscript]
[@insert template="layout/invest_detail_layout" title="我要投资" user=kuser]
<div class="sales-banner">
    <div class="layout">
        <div class="sales-info clearfix">
            <div class="left-sale" id="bidDetailLeft">
                <h2>
                    <span class="fl">${project.title}</span>
                    [#list labels as label]
                        <span class="tags fl">${label}</span>
                    [/#list]
                    <span class="fl bid-id">借款编号：${project.projectNo}</span>
                    <a href="${ctx}/investment/agreement/${project.id}" class="fr">借款协议范本</a>
                </h2>
                <span style="display:none" id="apId">1561010</span>
                <span style="display:none" id="borrowUid">2578533</span>
                <div class="info-rate clearfix">
                    <div class="transfer-price item1">
                        <div class="first-box orange-box">
                            <strong>
                                [#if project.rewardInterestRate??]
                                    ${project.interestRate + project.rewardInterestRate}
                                [#else]
                                    ${project.interestRate}
                                [/#if]
                            </strong>
                            %
                        </div>
                        <p>预期年化</p></div>
                    <div class="transfer-price item2">
                        <div class="first-box">
                            <strong>${project.period}</strong>
                            <em>${project.periodUnit.getDisplayName()}</em>
                        </div>
                        <p>项目期限</p>
                    </div>
                    <div class="transfer-progress item3">
                        <div class="progress-bar" data-width="100%">
                            <i style="width: ${project.investmentProgress}%;"></i>
                        </div>
                        <p>进度：<em>${project.investmentProgress}%</em></p></div>
                </div>
                <div class="transfer-intro clearfix">
                    <ul class="type-ul">
                        <li>
                            <label>项目总额：</label>
                            <span>
                                <em id="borrowDetialAmount">${project.amount}</em>元
                            </span>
                        </li>
                        [#--<li>--]
                            [#--<label>项目类型：</label>--]
                            [#--<span>${project.typeDes} </span>--]
                        [#--</li>--]
                        <li>
                            <label>起息时间：</label>
                            <span>募集完成审核后（${project.interestMethod.displayName}）</span>
                        </li>
                        <li>
                            <label>还款方式：</label>
                            <span>${project.repaymentMethod.getDisplayName()}</span>
                        </li>
                        [#if project.guaranteeMethod.getDisplayName??]
                            <li><label>风险保障：</label><span title="${project.guarantee}">${abbreviate(project.guarantee, 20, "...")}</span></li>
                        [/#if]
                    </ul>
                </div>
            </div>
            [#if project.showInvesting && !project.isFailure]
            <div class="right-sale zhuanInvestment">
                <dl id="tender_balance" style="display: block;">
                    <ul style="padding-left: 0px;">
                        <li>
                            剩余可投：
                            <span>
                                <strong id="tenderable_amount">${project.surplusInvestmentAmount?string("currency")}</strong>元
                            </span>
                        </li>
                    </ul>
                    [#if user??]
                        <div class="base" id="user_blance_id">
                            <p>
                                <span class="fl">
                                    账户余额：
                                    <strong class="Money" id="amount">${currentUserFinance.available?string("currency")}</strong>元
                                    <input id="userAmount" value="${currentUserFinance.available}" hidden>
                                    &nbsp;&nbsp;
                                    <a href="${ctx}/uc/recharge" class="font-blue">充值</a>
                                </span>
                                <a href="javascript:void(0)" id="tender_all_btn" class="fr all-in font-blue">全投</a>
                            </p>
                        </div>
                    [#else]
                        <div class="base" id="user_blance_id">
                            <p>
                                <span class="fl">
                                    账户余额
                                    <a href="javascript:quickLogin(1)" class="font-blue loginBtn">登录</a>后可见</p>
                                </span>
                            </p>
                        </div>
                    [/#if]
                    <div class="input calculates">
                        <a class="reduce" id="reduce" href="javascript:void(0);">-</a>
                        <input placeholder="${project.investmentMinimum}元起投" class="trTXt" type="text" id="tender_amount">
                        <input id="investmentMinimum" type="hidden" value="${project.investmentMinimum}">
                        <input id="depositStatus" type="hidden" value="0">
                        <input id="depositSwitch" type="hidden" value="1">
                        <a class="plus" id="plus" href="javascript:void(0);">+</a>
                    </div>
                    <div class="income">
                        <span>预期收益<em id="preview_interest">0.00</em>元</span>
                        <input type="hidden" id="calInterestRate" value="${project.realInterestRate}" />
                        <input type="hidden" id="calRepaymentMethod" value="${project.repaymentMethod}" />
                        <input type="hidden" id="calPeriod" value="${project.period}" />
                        <input type="hidden" id="calPeriodUnit" value="${project.periodUnit}" />
                    </div>
                    <p class="realPay unVisible" id="input_error_id"><em id="errorTips"></em></p>
                    [#-- 项目状态 --]
                    [#if project.isFailure]
                        <a href="javascript:void(0)" class="button block button-normal radius-ssm buy">已流标</a>
                    [#else]
                        [#if currentUser??]
                            [#if project.progress == "PREVIEWING"]
                                <a href="javascript:void(0)" class="button block button-normal radius-ssm buy countdown" startTime="${project.investmentStartDate}">预告中</a>
                                <a href="javascript:void(0)" id="tender_buy_now" class="button block button-normal radius-ssm buy" style="display: none;">立即购买</a>
                            [#elseif project.progress == "AUTOINVESTING"]
                                <a href="javascript:void(0)" class="button block button-normal radius-ssm buy">自动投标中</a>
                            [#elseif project.progress == "INVESTING"]
                                <a href="javascript:void(0)" id="tender_buy_now" class="button block button-normal radius-ssm buy">立即购买</a>
                            [/#if]
                        [#else]
                            <a href="javascript:quickLogin(1)" class="button block button-normal radius-ssm buy">立即购买(请先登录)</a>
                        [/#if]
                    [/#if]
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
            [/#if]
            [#if project.isFailure]
                <div class="right-sale zhuanInvestment">
                    <dl id="statusBox" style="display: block;">
                        <div class="status">
                            <p>该项目已下架，请关注其他项目！</p>

                            <div class="img closed"></div>
                            <a href="${ctx}/investment" class="button block button-normal radius-ssm buy">关注其他项目</a>
                        </div>
                    </dl>
                </div>
            [/#if]
            [#if project.progress == "REPAYING"]
                <div class="right-sale zhuanInvestment">
                    <dl id="statusBox" style="display: block;">
                        <div class="status">
                            <p>该项目已在还款中，请关注其他项目！</p>

                            <div class="img confirmed"></div>
                            <a href="${ctx}/investment" class="button block button-normal radius-ssm buy">关注其他项目</a>
                        </div>
                    </dl>
                </div>
            [/#if]
            [#if project.progress == "LENDING"]
                <div class="right-sale zhuanInvestment">
                    <dl id="statusBox" style="display: block;">
                        <div class="status">
                            <p>该项目已投满，请关注其他项目！</p>

                            <div class="img oos"></div>
                            <a href="${ctx}/investment" class="button block button-normal radius-ssm buy">关注其他项目</a>
                        </div>
                    </dl>
                </div>
            [/#if]
            [#if project.progress == "COMPLETED" && project.state == "SUCCESS"]
                <div class="right-sale zhuanInvestment">
                    <dl id="statusBox" style="display: block;">
                        <div class="status">
                            <p>该项目已完成，请关注其他项目！</p>

                            <div class="img repayment"></div>
                            <a href="${ctx}/investment" class="button block button-normal radius-ssm buy">关注其他项目</a>
                        </div>
                    </dl>
                </div>
            [/#if]
        </div>
    </div>
</div>
<section id="content">
    <div class="container" style="width: 1230px">
        <div class="row">
            [#-- 项目左侧信息 --]
            [#include "investment/detail-left.ftl" /]
            [#-- 项目右侧详情信息 --]
            [#include "investment/detail-right.ftl" /]
        </div>
    </div>
</section>
[#-- 项目投资 隐藏元素 --]
[#include "investment/investBox.ftl" /]

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