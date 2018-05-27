<div class="col-lg-9">
    <article>
        <div class="post-image">
            <div class="post-heading">
                <h3 id="projectIntroduce">项目介绍</h3>
            </div>
        </div>
        <div>
            [#if project.intro?? && project.intro!='']
                [#noescape]${project.intro}[/#noescape]
            [#else]
                [#--<img src="${ctx}/static/images/investmentdetail/chedidai.png" alt="" class="img-responsive"/>--]
                <p>
                    本标的周转借款，借款人情况属实。投资人通过${setting.basic.siteName}投资此标获得对应收益，稳健可靠。
                </p>
            [/#if]
        </div>
    </article>
    <article>
        <div class="post-image">
            <div class="post-heading">
                <h3 id="userInfo">用户信息</h3>
            </div>
            <div class="transfer-intro clearfix ledinfo">
                <ul class="type-ul">
                    [#if borrower.type == 'GENERAL']
                        <li>
                            <label>姓名：</label>
                            <span>${secrecy("username", borrowerInfo.realName)}</span>
                        </li>
                        <li>
                            <label>性别：</label>
                            <span>${borrowerInfo.gender.displayName!"-"}</span>
                        </li>
                        <li>
                            <label>年龄：</label>
                            <span>[#if borrowerInfo.birthday??]${(.now?string("yyyy"))?number - (borrowerInfo.birthday?string("yyyy"))?number}[#else]-[/#if]</span>
                        </li>
                        [#--<li>--]
                            [#--<label>婚姻状况：</label>--]
                            [#--<span>${borrowerInfo.marriageStr!"-"}</span>--]
                        [#--</li>--]
                        <li>
                            <label>证件号码：</label>
                            <span>${secrecy("idNo", borrowerInfo.idNo)}</span>
                        </li>
                        [#--<li>--]
                            [#--<label>籍贯：</label>--]
                            [#--<span>${borrowerInfo.birthplace!"-"}</span>--]
                        [#--</li>--]
                    [#else]
                        <li>
                            <label>公司：</label>
                            <span>${secrecy("corpName", borrowerInfo.corpName)}</span>
                        </li>
                        <li>
                            <label>营业执照：</label>
                            <span>${secrecy("corpLicenseNo", borrowerInfo.corpLicenseNo)}</span>
                        </li>
                    [/#if]
                </ul>
                [#--<ul class="clear type-ul">--]
                    [#--<li>--]
                        [#--<label>借款用途：</label>--]
                        [#--<span>${project.purpose!"-"}</span>--]
                    [#--</li>--]
                [#--</ul>--]
            </div>
        </div>
    </article>
    [#if extras?? && extras?size > 0]
        <article>
            <div class="post-image">
                <div class="post-heading">
                    [#--<h3 id="projectCarInfo">${extras[0].extraValue}</h3>--]
                    <h3 id="projectCarInfo">借款信息</h3>
                </div>
                <div class="transfer-intro clearfix ledinfo">
                    <ul class="type-ul">
                        [#list extras[0].details as detail]
                            <li>
                                <label>${detail.extraFieldDes}：</label>
                                <span>${detail.extraFieldValue}</span>
                            </li>
                        [/#list]
                    </ul>
                </div>
            </div>
        </article>
    [/#if]
    <article>
        <div class="post-image">
            <div class="post-heading">
                <h3 id="borrowRecord">借款记录</h3>
            </div>
            <p>历史还清期数，待还款，历史逾期次数指该借款人所有借款的对应信息，不单指本标的借款信息</p>
            <div class="transfer-intro clearfix ledinfo">
                <ul class="type-ul">
                    <li>
                        <label>历史还清期数：</label>
                        <span>${repaidPeriodCount} 期</span>
                    </li>
                    <li>
                        <label>待还款：</label>
                        <span>${repayingPeriodCount} 期</span>
                    </li>
                    <li>
                        <label>历史逾期次数：</label>
                        <span>${overduePeriodCount} 期</span>
                    </li>
                </ul>
            </div>
        </div>
    </article>
    <article>
        <div class="post-image">
            <div class="post-heading">
                <h3 id="financialSecurity">资金安全保障</h3>
            </div>
            <p>${setting.basic.siteName}只提供稳健可靠的项目投资</p>
            <div class="tabDl2 clearfix">
                <dl>
                    <dd><img src="${ctx}/static/images/safe/bannerBid1.png"></dd>
                    <dt>环环相扣<br>确保按时还款</dt>
                </dl>
                <dl>
                    <dd><img src="${ctx}/static/images/safe/bannerBid2.png"></dd>
                    <dt>精挑细选<br>仅筛选优质借款人</dt>
                </dl>
                <dl>
                    <dd><img src="${ctx}/static/images/safe/bannerBid3.png"></dd>
                    <dt>多重防线<br>有效抵御风险</dt>
                </dl>
            </div>
        </div>
    </article>
    [#if materials?size gt 0]
        <article>
            <div class="post-image">
                <div class="post-heading">
                    <h3 id="securityAudit">安全审核</h3>
                </div>
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>审核资料</th>
                        <th>审核情况</th>
                        <th>审核人</th>
                    </tr>
                    </thead>
                    <tbody>
                        [#list materials as material]
                            <tr>
                                <th scope="row">${material_index+1}</th>
                                <td>${material.title}</td>
                                <td>通过</td>
                                <td>${secrecy("username", material.operator)}</td>
                            </tr>
                        [/#list]
                    </tbody>
                </table>
            </div>
            <section id="content1">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-12">
                            <div id="grid-container" class="cbp-l-grid-projects">
                                <ul>
                                    [#list materials as material]
                                        <li class="cbp-item graphic">
                                            <div class="cbp-caption">
                                                <div class="cbp-caption-defaultWrap">
                                                    <img src="${dfsUrl}/${material.source}" alt="${material.title}" />
                                                </div>
                                                <div class="cbp-caption-activeWrap">
                                                    <div class="cbp-l-caption-alignCenter">
                                                        <div class="cbp-l-caption-body">
                                                            <a href="${dfsUrl}/${material.source}" class="cbp-lightbox cbp-l-caption-buttonRight"
                                                               data-title="${material.title}">查看大图</a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="cbp-l-grid-projects-title">${material.title}</div>
                                        </li>
                                    [/#list]
                                </ul>
                            </div>

                            [#--<div class="cbp-l-loadMore-button">--]
                                [#--<a href="ajax/loadMore.html" class="cbp-l-loadMore-button-link">查看更多</a>--]
                            [#--</div>--]

                        </div>
                    </div>
                </div>
            </section>
        </article>
    [/#if]
    <article>
        <div class="post-image">
            <div class="post-heading">
                <h3 id="investmentRecords">投资记录</h3>
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
            <div id="pagination" style="float: right"></div>
        </div>
    </article>
</div>

<script id="pageInvestmentRecordsTpl" type="text/html">
    {{#  $.each(d.rows, function(index, item){ }}
        <tr>
            <th scope="row">{{ index + 1}}</th>
            <td>{{ secrecy('mobile',item.investor)}}</td>
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