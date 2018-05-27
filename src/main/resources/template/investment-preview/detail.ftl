[@nestedstyle]
	[@css href="css/cubeportfolio.min.css" /]
	[@css href="css/salelist.css" /]
[/@nestedstyle]
[@nestedscript]
	[@js src= "js/jquery.cubeportfolio.min.js"/]
	[@js src= "js/investment/detail.preview.js"/]
[/@nestedscript]
[@insert template="layout/invest_detail_preview_layout" title="项目预览" user=kuser]
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
            <div class="right-sale zhuanInvestment">
                <dl id="statusBox" style="display: block;">
                    <div class="status">
                        <p>该项目是预览状态，请关注其他项目！</p>
                        <div class="img closed"></div>
                        <a href="${ctx}/investment" class="button block button-normal radius-ssm buy">关注其他项目</a>
                    </div>
                </dl>
            </div>
        </div>
    </div>
</div>
<section id="content">
    <div class="container" style="width: 1230px">
        <div class="row">
            [#-- 项目左侧信息 --]
            [#include "investment-preview/detail-left.ftl" /]
            [#-- 项目右侧详情信息 --]
            [#include "investment-preview/detail-right.ftl" /]
        </div>
    </div>
</section>

[/@insert]