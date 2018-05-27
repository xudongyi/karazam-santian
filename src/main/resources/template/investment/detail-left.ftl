<div class="col-lg-3">
    <aside class="left-sidebar">
        <div class="widget">
            <h5 class="widgetheading">项目相关材料</h5>
            <ul class="cat">
                <li>
                    <i class="fa fa-angle-right"></i>
                    <a href="#projectIntroduce">产品详情</a>
                    <span></span>
                </li>
                <li>
                    <i class="fa fa-angle-right"></i>
                    <a href="#securityAudit">安全审核</a>
                    <span></span>
                </li>
                <li>
                    <i class="fa fa-angle-right"></i>
                    <a href="#investmentRecords">投资记录</a>
                    <span> (${investmentRecords?size!0})</span>
                </li>
                <li>
                    <i class="fa fa-angle-right"></i>
                    <a href="#securityAudit">审核记录</a>
                    <span></span>
                </li>
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
                <li><a href="#projectIntroduce">项目介绍</a></li>
                <li><a href="#userInfo">用户信息</a></li>
                [#if extras?? && extras?size > 0]
                    <li><a href="#projectCarInfo">借款信息</a></li>
                [/#if]
                <li><a href="#borrowRecord">借款记录</a></li>
                <li><a href="#financialSecurity">资金安全保障</a></li>
                <li><a href="#securityAudit">安全审核</a></li>
                <li><a href="#investmentRecords">投资记录</a></li>
            </ul>
        </div>
    </aside>
</div>