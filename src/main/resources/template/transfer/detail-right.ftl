<div class="col-lg-9">
    <article>
        <div class="post-image">
            <div class="post-heading">
                <h3 id="projectIntroduce">转让说明</h3>
            </div>

        </div>
        <p>
            债权转让，是指投资人因个人原因急需资金，在${setting.basic.siteName}投资的借款项目转让给其他用户。债权转让能提高投资者资金的流动性，当你需要流动资金时，可以通过出售你名下拥有的。符合相应条件的债权给其他投资人，从而完成债权转让，获得流动资金。
        </p>
        [#--<img src="${ctx}/static/images/investmentdetail/chedidai.png" alt="" class="img-responsive"/>--]
    </article>
    <article>
        <div class="post-image">
            <div class="post-heading">
                <h3 id="projectTransferRule">债权转让规则</h3>
            </div>
            <p>1.有效投资1天后方可进行债权转让。</p>
            <p>2.当前标转让期间回款，转让自动失效。</p>
            <p>3.债权转让标有效期是24小时，24小时后转让没成交则自动失效。</p>
        </div>
    </article>
    <article>
        <div class="post-image">
            <div class="post-heading">
                <h3 id="projectRepaymentPlans">还款计划</h3>
            </div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>期数</th>
                    <th>还款金额（元）</th>
                    <th>本金（元）</th>
                    <th>利息（元）</th>
                    <th>还款日期</th>
                    <th>还款状态</th>
                </tr>
                </thead>
                <tbody>
                    [#list repayments as repayment]
                        <tr>
                            <th scope="row">第${repayment.period}期</th>
                            <td>${repayment.capitalInterest?string("currency")}</td>
                            <td>${repayment.capital?string("currency")}</td>
                            <td>${repayment.interest?string("currency")}</td>
                            <td>${repayment.payDate?string("yyyy-MM-dd")}</td>
                            <td>${repayment.stateDes}</td>
                        </tr>
                    [/#list]
                </tbody>
            </table>
            <div id="paginationRepaymemtPlan" style="float: right"></div>
        </div>
    </article>
    <article>
        <div class="post-image">
            <div class="post-heading">
                <h3 id="projectInvestmentRecord">转让记录</h3>
            </div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>投标人</th>
                    <th>投资金额（元）</th>
                    <th>投标时间</th>
                </tr>
                </thead>
                <tbody id="pageInvestmentRecords"></tbody>
            </table>
            <div id="paginationInvestment" style="float: right"></div>
        </div>
    </article>
</div>

<script id="pageInvestmentRecordsTpl" type="text/html">
    {{#  $.each(d.rows, function(index, item){ }}
    <tr>
        <th scope="row">{{ index + 1}}</th>
        <td>{{ secrecy('mobile', item.investor)}}</td>
        <td>{{ currency(item.amount, '￥') }}</td>
        <td>{{ item.buyTime }}</td>
    </tr>
    {{#  }); }}
    {{#if (d.rows.length == 0) { }}
    <tr>
        <td colspan="4" align="center">暂无记录</td>
    </tr>
    {{# } }}
</script>