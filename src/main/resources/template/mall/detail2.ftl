[@nestedstyle]
    [@css href="css/cubeportfolio.min.css" /]
    [@css href="css/mate/base.css" /]
    <link rel="stylesheet" href="${ctx}/static/mall/base-51daa35356.css"/>
    <link rel="stylesheet" href="${ctx}/static/mall/common-36b6434b23.css"/>
    <link rel="stylesheet" href="${ctx}/static/mall/detail-32d54f9b5c.css"/>
[/@nestedstyle]
[@nestedscript]
    [@js src= "js/jquery.cubeportfolio.min.js"/]
    [@js src= "mall/js/detail.js"/]
[/@nestedscript]
[@insert template="layout/mall_layout" title="积分商城" user=kuser]

<div class="wrapper ff-layout">
    <div class="container pb50">
        <div class="link-source">
            <a href="/" target="_blank">商城首页</a><span>&gt;</span>
            <a href="https://mall.fengjr.com/benefits" target="_blank">${goods.type.displayName}</a> <span>&gt;</span>
            <span class="product-title">${goods.name}</span></div>
        <div class="prodIntro clearfix" data-product-id="8C0D4444-86F2-422B-9835-F31C92D51307" data-event-id="">
            <div class="left">
                <div class="p-img"><img src="${ctx}/static/mall/images/detail/d0c33a6e-169b-44a0-a681-0d48a1fa99fb.jpg" alt="天然碎乳胶颗粒婴童枕/">
                </div>
            </div>
            <div class="prodInfo"><p class="title">天然碎乳胶颗粒婴童枕</p>
                <p class="desc">给孩子一个适合自己的专属枕头</p>
                <div class="price-info"><span>会员价</span> <span class="price orange">14000</span> <span
                        class="unit">积分</span>
                    <p class="my"><span>我的积分</span> <span class="balance">0</span></p></div>
                <p class="mt20"><span>配送</span> <span class="ml40">仅限大陆地区</span> <span class="ml40">下单后3-5天内发货</span>
                    <span class="ml40">免运费</span></p>
                <p class="mt20"><span>服务</span> <span
                        class="ml40">本商品由</span><span>青海高领网络科技有限公司</span><span>提供配送及售后</span></p>
                <p class="mt20"><span>数量</span> <span class="tb-stock ml40"> <button class="subtract"
                                                                                     title="减1">-</button><input
                        style="ime-mode:disabled" class="bug-count" type="text" value="1" maxlength="8"
                        data-max-count="title=&quot;请输入购买量&quot;/"><button class="add" title="加1">+
                    </button> </span> <span class="ml20">剩余</span><span class="leftCount">10</span> <span
                        class="countUnit" style="display: none;">件</span></p>
                <div class="btn-group">
                    <a href="http://www.baidu.com" target="_blank" class="earn-points" data-buy-url="">积分不足，赚积分</a>
                    <button class="follow"><i class="fa fa-heart "></i><span class="favoriteText">加关注</span></button>
                    <span class="orange focus-tip-detail"></span></div>
            </div>
        </div>
        <div class="prodDetail">
            <div class="v-line"></div>
            <div class="tab-container">
                <div class="tab-btn tab-detail active" data-type="detail">商品详情</div>
                <div class="tab-btn tab-buyRecords " data-type="buyRecords">兑换明细</div>
            </div>
            <div class="content-container">
                <div class="tab-content detail active">
                    <p class="desc"></p>
                    <div class="detail-imgs">
                        <img src="${ctx}/static/mall/images/detail/378deb31-1099-413f-ad00-65ca755860f0.jpg" alt="">
                        <img src="${ctx}/static/mall/images/detail/e9df3884-2f10-450c-8450-02e7e11df73a.jpg" alt="">
                        <img src="${ctx}/static/mall/images/detail/8ec84942-0e12-45a3-8b58-c265c706385a.jpg" alt="">
                        <img src="${ctx}/static/mall/images/detail/541ed668-3f10-43d1-8352-5f7b74b25939.jpg" alt="">
                        <img src="${ctx}/static/mall/images/detail/da9b24ac-688f-4768-84bb-e9f0c3418542.jpg" alt="">
                        <img src="${ctx}/static/mall/images/detail/47c19a2b-42c1-4e1a-a516-5ad861964c83.jpg" alt="">
                    </div>
                </div>
                <div class="tab-content buyRecords clearfix">
                    <table class="records-table">
                        <thead>
                        <tr>
                            <th width="300">用户</th>
                            <th width="300">数量</th>
                            <th width="300">积分</th>
                            <th width="300">兑换时间</th>
                        </tr>
                        </thead>
                        <tbody>
                            [#list goodsOrders as order]
                                <td width="300">${order.userId}</td>
                                <td width="300">${order.quantity}</td>
                                <td width="300">${order.point}</td>
                                <td width="300">${order.createDate}</td>
                            [/#list]
                            <td width="300">test</td>
                            <td width="300">2</td>
                            <td width="300">13</td>
                            <td width="300">111</td>
                        </tbody>
                    </table>
                    <div class="page-bar clearfix"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="quick-bar" [#--style="display: none;"--]>
        <div class="container clearfix">
            <div class="left"><span class="title">天然碎乳胶颗粒婴童枕</span></div>
            <div class="right"><span class="price orange">14000</span> <span class="unit">积分</span>
                <a href="http://www.baidu.com" target="_blank" class="button earn-points">积分不足，赚积分</a>
            </div>
        </div>
    </div>
    <div class="dialog">
        <div class="dialog-head"><span class="title">提示</span> <span class="close"><i class="fa fa-close"></i></span>
        </div>
        <div class="dialog-content"><p class="text"></p>
            <div class="btn-group">
                <div class="button"></div>
            </div>
        </div>
    </div>
    <div class="dialog-bg"></div>
</div>

[/@insert]