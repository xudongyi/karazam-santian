[@nestedscript]
[#--[@js src="js/admin/login.min.js" /]--]
[/@nestedscript]
[@nestedstyle]
    [@css href="lib/bootstrap/css/bootstrap.min.css,
                lib/layui/css/layui.css,
                css/admin/jqadmin.css,
                css/admin/main.css
                " /]
[/@nestedstyle]
[@insert template="admin/layout/default_layout" title="欢迎页"]
<div class="container-fluid larry-wrapper">
    <div class="row">
        <div class="col-xs-6 col-sm-4 col-md-2">
            <section class="panel">
                <div class="symbol bgcolor-blue"> <i class="fa fa-address-book"></i>
                </div>
                <div class="value tab-menu">
                    <a href="javascript:" data-url="${ctx}/admin/user-info" data-parent="true" data-title="用户总量"><i data-icon='fa-address-book'></i>
                        <h1>${userNum}</h1>
                    </a>

                    <a href="javascript:" data-url="${ctx}/admin/user-info" data-parent="true" data-title="用户总量"> <i data-icon='fa-address-book'></i><span>用户总量</span></a>

                </div>
            </section>
        </div>
        <div class="col-xs-6 col-sm-4 col-md-2">
            <section class="panel">
                <div class="symbol bgcolor-commred"> <i class="fa fa-user-plus"></i>
                </div>
                <div class="value tab-menu">
                    <a href="javascript:" data-url="${ctx}/admin/user-info" data-parent="true" data-title="今日注册"> <i data-icon='fa-user-plus'></i>
                        <h1>${todayUserNum}</h1>
                    </a>

                    <a href="javascript:" data-url="${ctx}/admin/user-info" data-parent="true" data-title="今日注册"> <i data-icon='fa-user-plus'></i><span>今日注册</span></a>

                </div>
            </section>
        </div>

        <div class="col-xs-6 col-sm-4 col-md-2">
            <section class="panel">
                <div class="symbol bgcolor-dark-green"> <i class="fa fa-cny"></i>
                </div>
                <div class="value tab-menu">
                    <a href="javascript:" data-url="${ctx}/admin/user-info" data-parent="true" data-title="今日充值"> <i data-icon='fa-cny'></i>
                        <h1>${todayRechargeNum}</h1>
                    </a>
                    <a href="javascript:" data-url="${ctx}/admin/user-info" data-parent="true" data-title="今日充值"> <i data-icon='fa-cny'></i><span>今日充值</span></a>
                </div>
            </section>
        </div>

        <div class="col-xs-6 col-sm-4 col-md-2">
            <section class="panel">
                <div class="symbol bgcolor-yellow-green"> <i class="fa fa-envira"></i>
                </div>
                <div class="value tab-menu">
                    <a href="javascript:" data-url="${ctx}/admin/user-info" data-parent="true" data-title="今日标的"> <i data-icon='fa-envira'></i>
                        <h1>${todayBorrowingsNum}</h1>
                    </a>
                    <a href="javascript:" data-url="${ctx}/admin/user-info" data-parent="true" data-title="今日标的"> <i data-icon='fa-envira'></i><span>今日标的</span></a>
                </div>
            </section>
        </div>

        <div class="col-xs-6 col-sm-4 col-md-2">
            <section class="panel">
                <div class="symbol bgcolor-orange"> <i class="fa fa-telegram"></i>
                </div>
                <div class="value tab-menu">
                    <a href="javascript:" data-url="${ctx}/admin/user-info" data-parent="true" data-title="今日投资"> <i data-icon='fa-telegram'></i>
                        <h1>${todayInvestmentNum}</h1>
                    </a>
                    <a href="javascript:" data-url="${ctx}/admin/user-info" data-parent="true" data-title="今日投资"> <i data-icon='fa-telegram'></i><span>今日投资</span></a>
                </div>
            </section>
        </div>
    </div>

    <div class="row">
        <div class="col-xs-12 col-md-6">
            <section class="panel">
                <div class="panel-heading">
                    待处理任务
                    <a href="javascript:" class="pull-right panel-toggle"><i class="iconfont icon-xiasvg"></i></a>
                </div>
                <div class="panel-body">
                    <table class="layui-table">
                        <tbody>
                            <tr>
                                <td>
                                    <strong>待处理借款申请数</strong>：
                                </td>
                                <td>
                                    [#if size??]
                                        <a href="javascript:toBorrowingApply();" style="color: red">${size} 条</a>
                                    [#else ]
                                        <a href="javascript:">无</a>
                                    [/#if ]
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </section>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12 col-md-6">
            <section class="panel">
                <div class="panel-heading">
                    收益信息
                    <a href="javascript:" class="pull-right panel-toggle"><i class="iconfont icon-xiasvg"></i></a>
                </div>
                <div class="panel-body">
                    <table class="layui-table">
                        <tbody>
                        <tr>
                            <td>
                                <strong>累计撮合金额</strong>：
                            </td>
                            <td>
                                <a href="javascript:">${dealAmount}元</a>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <strong>为投资者带来收益</strong>：
                            </td>
                            <td>
                                <a href="javascript:">${earnInterest}元</a>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <strong>即将为投资者带来收益</strong>：
                            </td>
                            <td>
                                <a href="javascript:">${interest}元</a>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </section>

        </div>
    </div>
</div>
<div class="testcatch" style="display: none;">
    <p>这是一个捕获弹窗</p>
</div>
<script type="application/javascript">
    function toBorrowingApply() {
        parent.window.mainTabs.addModule('官网借款申请', ctx + '/admin/borrowing/borrowingApplyFromWebSite', "fa fa-align-left", true);
    }
</script>
[/@insert]