[@nestedstyle]
    <link rel="stylesheet" href="${ctx}/static/lib/layui/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/mall/jifen.css">
[/@nestedstyle]
[@nestedscript]
    [@js src= "lib/layui/lay/dest/layui.all.js" /]
    [@js src="js/user/user.point.js" /]
[#--<script src="${ctx}/static/mall/jifen.js"></script>--]
[/@nestedscript]
[@insert template="layout/uc_layout" title="用户中心-积分管理" memberContentId="zichan-all" module="point" nav="point" currentUser=kuser user=kuser]

<div class="account-layout-wrap">
    <div class="account-layout">
        <div class="acc-panel">
            <div class="acc-title"><span class="acc-title-text">我的积分</span></div>
            <div class="acc-body">
                <div class="acc-content">
                    <div class="jifen-top"><p class="small-title">可用积分</p>
                        <div><span class="number">${userPoint.available()}</span> <span class="unit">分</span>
                            <a href="${ctx}/uc/point/canbuy" class="blue-link">我的可换购商品</a>
                            <a href="${ctx}/uc/shipping_address" class="blue-link">管理收货地址</a>
                            <a href="#" class="blue-link">积分帮助中心</a>
                        </div>
                        <div class="block">
                            <a href="#" target="_blank" class="ff-btn ff-btn-orange earn-jifen">赚积分</a>
                            <a href="${ctx}/mall/" target="_blank" class="ff-btn use-jifen">花积分</a>
                            <a href="${ctx}/uc/sign_in" target="_blank" class="ff-btn use-jifen">签到送积分</a>
                        </div>
                    </div>
                </div>
                <div class="acc-tab">
                    <ul class="tab-col-3">
                        <li data-type="orderTab" class="active" [#--data-tab="orderTab"--]>全部订单</li>
                        <li data-type="pointTab" class="" [#--data-tab="pointTab"--]>积分明细</li>
                        <li data-type="followTab" class="" [#--data-tab="followTab"--]>我的关注</li>
                    </ul>
                </div>

                <div class="acc-content acc-content-tab all orderTab">
                    <div class="category-content">
                        <div class="acc-categories all-category">
                            <div class="cate-line">
                                <span class="cate-head">类目：</span>
                                <ul class="categories">
                                    <li data-category="" class="selected">全部</li>
                                    [#list goodsTypes as type]
                                        <li data-category="${type}">${type.displayName}</li>
                                    [/#list]
                                </ul>
                            </div>
                            <div class="cate-line">
                                <span class="cate-head">时间：</span>
                                <ul class="datetime">
                                    <li data-date="" class="selected">全部</li>
                                    <li data-date="1">最近1个月</li>
                                    <li data-date="3">最近3个月</li>
                                    <li data-date="6">最近6个月</li>
                                    <li data-date="12">最近1年</li>
                                </ul>
                            </div>
                            <div class="cate-line">
                                <span class="cate-head">状态：</span>
                                <ul class="trade-status">
                                    <li data-status="" class="selected">全部</li>
                                    [#list goodsOrderStatus as status]
                                        <li data-status="${status}">${status.displayName}</li>
                                    [/#list]
                                </ul>
                            </div>
                        </div>
                        <table class="acc-table table-all">
                            <thead>
                                <tr>
                                    <th width="360">订单信息</th>
                                    <th width="260">积分</th>
                                    <th>数量</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody class="orderTab data">
                            </tbody>
                        </table>
                        <div class="orderTab empty inesnttist-box" style="display: none">
                            <div style="font-size: 20px;text-align:center;line-height: 50px;margin:20px 0 20px 0;color: #b0b0b0;">
                                暂无记录
                            </div>
                        </div>
                    </div>
                    <div class="orderTab pagination"></div>
                </div>

                <div class="acc-content acc-content-tab detail pointTab" style="display: none">
                    <div class="category-content">
                        <div class="acc-categories detail-category">
                            <div class="cate-line"><span class="cate-head">筛选：</span>
                                <ul class="categories">
                                    <li data-category="" class="selected">全部</li>
                                    [#list pointTypes as type]
                                        <li data-category="${type}">${type.displayName}</li>
                                    [/#list]
                                </ul>
                            </div>
                            <div class="cate-line"><span class="cate-head">时间：</span>
                                <ul class="datetime">
                                    <li data-date="" class="selected">全部</li>
                                    <li data-date="1">最近1个月</li>
                                    <li data-date="3">最近3个月</li>
                                    <li data-date="6">最近6个月</li>
                                    <li data-date="12">最近1年</li>
                                </ul>
                            </div>
                        </div>
                        <table class="acc-table">
                            <thead>
                                <tr>
                                    <th width="300">日期</th>
                                    <th width="300">积分</th>
                                    <th>说明</th>
                                </tr>
                            </thead>
                            <tbody class="pointTab data">
                                <tr>
                                    <td>2017-06-08 15:39:52</td>
                                    <td>+3</td>
                                    <td>签到奖励</td>
                                </tr>
                            </tbody>
                        </table>
                        <div class="pointTab empty inesnttist-box" style="display: none">
                            <div style="font-size: 20px;text-align:center;line-height: 50px;margin:20px 0 20px 0;color: #b0b0b0;">
                                暂无记录
                            </div>
                        </div>
                    </div>
                    <div class="pointTab pagination"></div>
                </div>

                <div class="acc-content acc-content-tab attention followTab" style="display: none">
                    <div class="category-content">
                        <div>
                            <table class="acc-table table-attention">
                                <thead>
                                <tr>
                                    <th width="410">商品</th>
                                    <th width="260">金额</th>
                                    <th width="260">操作</th>
                                </tr>
                                </thead>
                                <tbody class="followTab data">

                                </tbody>
                            </table>
                            <div class="followTab empty inesnttist-box" style="display: none">
                                <div style="font-size: 20px;text-align:center;line-height: 50px;margin:20px 0 20px 0;color: #b0b0b0;">
                                    暂无记录
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="followTab pagination"></div>
                </div>


            </div>
        </div>
    </div>
</div>


[#-- 订单记录 --]
<script id="orderTabTpl" type="text/html">
    {{#  $.each(d.rows, function(index, item){ }}
        <tr>
            <td>
                <div class="item-img">
                    <a href="${ctx}/mall/goods/detail/{{ item.goodsObj.id }}" target="_blank"><img src="${dfsUrl}{{ item.goodsObj.image }}"></a>
                </div>
                <div class="item-text">
                    <p>{{ item.createDate }}</p>
                    <a href="${ctx}/mall/goods/detail/{{ item.goodsObj.id }}" target="_blank">{{ item.goodsObj.name }}</a>
                    <p>{{ item.orderNo }}</p>
                </div>
            </td>
            <td class="fir">{{ item.point }} 积分</td>
            <td>1个</td>
            <td width="130">
                <span class="orange">{{ item.statusDes }}</span><br>
                {{# if(item.status=='paid' ){ }}
                <a class="detail-title blue cancelOrder" data-orderId="{{ item.id }}">撤销</a><br>
                {{# } }}
                {{# if(item.status=='send' ){ }}
                <a class="detail-title blue confirmReceipt" data-orderId="{{ item.id }}">确认收货</a><br>
                {{# } }}
                <a href="${ctx}/uc/goods_order/detail/{{ item.id }}" target="_blank">订单详情</a><br>
            </td>
        </tr>
    {{#  }); }}
</script>

[#-- 积分记录 --]
<script id="pointTabTpl" type="text/html">
    {{#  $.each(d.rows, function(index, item){ }}

        <tr>
            <td>{{ item.createDate }}</td>
            {{# if(item.type=='credit' ){ }}
                <td>+{{ item.point }}</td>
            {{# }else{ }}
                <td>-{{ item.point }}</td>
            {{# } }}
            <td>{{ item.methodDes }}</td>
        </tr>

    {{#  }); }}
</script>

[#-- 关注记录 --]
<script id="followTabTpl" type="text/html">
    {{#  $.each(d.rows, function(index, item){ }}

        <tr>
            <td width="400">
                <div class="jifen-item clearfix">
                    <div class="item-img">
                        <a href="${ctx}/mall/goods/detail/{{ item.goodsObj.id }}" target="_blank">
                            <img src="${dfsUrl}{{ item.goodsObj.image }}"> </a>
                    </div>
                    <div class="item-text">
                        <a href="${ctx}/mall/goods/detail/{{ item.goodsObj.id }}" target="_blank">{{ item.goodsObj.name }}</a>
                    </div>
                </div>
            </td>
            <td width="262">{{ item.goodsObj.point }}积分</td>
            <td width="264">
                <a href="${ctx}/mall/goods/detail/{{ item.goodsObj.id }}" class="orange" target="_blank">查看</a>
                <br>
                <a class="follow delete-focus" goodsId="{{ item.goodsObj.id }}" href="javascript:;">取消关注</a>
            </td>
        </tr>

    {{#  }); }}
</script>

[/@insert]

