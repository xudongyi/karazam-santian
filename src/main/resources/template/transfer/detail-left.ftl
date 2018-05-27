<div class="col-lg-3">
    <aside class="left-sidebar">
        <div class="widget">
            <h5 class="widgetheading">项目相关材料</h5>
            <ul class="cat">
                <li><i class="fa fa-angle-right"></i><a href="#projectIntroduce">产品详情</a></li>
                <li><i class="fa fa-angle-right"></i><a href="#projectRepaymentPlans">还款计划</a></li>
                <li><i class="fa fa-angle-right"></i><a href="#projectInvestmentRecord">转让记录</a><span> (${investmentRecords?size!0})</span></li>
            </ul>
        </div>
        [#if investmentRecords?size gt 0]
            <div class="widget">
                <h5 class="widgetheading">最新投资</h5>
                <ul class="recent">
                    [#list investmentRecords as investmentRecord]
                        [#if investmentRecord_index < 3]
                            <li>
                                <img src="[#if investmentRecord.investorAvatar??]${dfsUrl}${investmentRecord.investorAvatar}[#else]${ctx}/static/images/dummies/blog/65x65/thumb3.jpg[/#if]" class="pull-left" alt=""/>
                                <h6><a href="#">投资金额：${investmentRecord.amount?string("currency")}</a></h6>
                                <p>
                                    投资时间：${investmentRecord.createDate?string("yyyy年MM月dd日 HH时mm分")}
                                </p>
                                <p>
                                    投资人：${secrecy("mobile", investmentRecord.investorLoginName)}
                                </p>
                            </li>
                        [/#if]
                    [/#list]
                </ul>
            </div>
        [/#if]
        <div class="widget">
            <h5 class="widgetheading">快捷标签</h5>
            <ul class="tags">
                <li><a href="#projectIntroduce">转让说明</a></li>
                <li><a href="#projectTransferRule">债权转让规则</a></li>
                <li><a href="#projectRepaymentPlans">还款计划</a></li>
                <li><a href="#projectInvestmentRecord">投资记录</a></li>
                <li><a href="${ctx}/investment/${transfer.borrowing}">查看原标信息</a></li>
            </ul>
        </div>
    </aside>
</div>