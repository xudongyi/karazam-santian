[@nestedstyle]
    [@css href="css/cubeportfolio.min.css" /]
    [@css href="css/mate/base.css" /]
    <link rel="stylesheet" href="${ctx}/static/mall/base-51daa35356.css"/>
    <link rel="stylesheet" href="${ctx}/static/mall/common-36b6434b23.css"/>
    <link rel="stylesheet" href="${ctx}/static/mall/detail-32d54f9b5c.css"/>
    <link rel="stylesheet" href="${ctx}/static/lib/layui/css/layui.css"/>
[/@nestedstyle]
[@nestedscript]
    [@js src= "js/jquery.cubeportfolio.min.js"/]
    [@js src= "lib/layui/lay/dest/layui.all.js"/]
    [@js src= "mall/js/detail.js"/]
[/@nestedscript]
[@insert template="layout/mall_layout" title="积分商城" user=kuser]

<div class="wrapper ff-layout">
    <div class="container pb50">
        <div class="link-source">
            <a href="${ctx}/mall">商城首页</a><span>&gt;</span>
            <a href="${ctx}/mall/${goods.type}">${goods.type.displayName}</a> <span>&gt;</span>
            <span class="product-title">${goods.name}</span></div>
        <div class="prodIntro clearfix" data-product-id="8C0D4444-86F2-422B-9835-F31C92D51307" data-event-id="">
            <div class="left">
                <div class="p-img"><img src="${dfsUrl}${goods.image}" alt="${goods.name}/">
                </div>
            </div>
            <div class="prodInfo"><p class="title">${goods.name}</p>
                <p class="desc">${goods.subhead}</p>
                <div class="price-info">
                    <span>会员价</span> <span class="price orange" id="goodsPoint">${goods.point}</span> <span class="unit">积分</span>
                    [#if userPoint??]
                        <p class="my"><span>我的积分</span> <span class="balance">${userPoint.point}</span></p>
                    [#else ]
                        <p class="my"><span><a href="${ctx}/login?redirectUrl=/mall/goods/detail/${goods.id}" class="orange">未登录</a></span></p>
                    [/#if]
                </div>
                <p class="mt20"><span>配送</span> <span class="ml40">仅限大陆地区</span> <span class="ml40">下单后3-5天内发货</span><span class="ml40">免运费</span></p>
                <p class="mt20"><span>服务</span> <span class="ml40">本商品由</span><span>青海高领网络科技有限公司</span><span>提供配送及售后</span></p>
                <p class="mt20"><span>数量</span>
                    <span class="tb-stock ml40"><button class="subtract" title="减1">-</button><input id="buyQuantity" data-stock="${goods.stock}" style="ime-mode:disabled" class="bug-count" type="text" value="1" maxlength="8" data-max-count="title=请输入购买量"><button class="add" title="加1">+</button> </span>
                    <span class="ml20">剩余</span><span class="leftCount">${goods.stock}</span> <span class="countUnit" [#--style="display: none;"--]>件</span>
                </p>
                <div class="btn-group">
                    [#if currentUser??]
                        [#if userPoint?? && goods.point<userPoint.point]
                            <button class="buy" id="buy">立即换购</button>
                        [#else ]
                            <a href="#" target="_blank" class="earn-points" data-buy-url="">积分不足，赚积分</a>
                        [/#if]
                        [#if goods.currentFollow()]
                            <button class="follow" goodsId="${goods.id}"><i class="fa fa-heart orange followed"></i><span class="favoriteText">加关注</span></button>
                        [#else]
                            <button class="follow" goodsId="${goods.id}"><i class="fa fa-heart unfollow"></i><span class="favoriteText">加关注</span></button>
                        [/#if]
                        <span class="orange focus-tip-detail" style="margin-left: 0px"></span>
                    [#else]
                        <a href="${ctx}/login?redirectUrl=/mall/goods/detail/${goods.id}">
                            <button class="buy" id="buy">立即换购</button>
                            <button class="follow"><i class="fa fa-heart "></i><span class="favoriteText">加关注</span></button>
                            <span class="orange focus-tip" style="float: left"></span>
                        </a>
                    [/#if]
                </div>
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
                    [#--${goods.introduction}--]
                        [#if goods.introduction?? && goods.introduction!='']
                            [#noescape]${goods.introduction}[/#noescape]
                        [#else]

                        [/#if]
                    [#--<p class="desc"></p>
                    <div class="detail-imgs">
                        <img src="${ctx}/static/mall/images/detail/378deb31-1099-413f-ad00-65ca755860f0.jpg" alt="">
                        <img src="${ctx}/static/mall/images/detail/e9df3884-2f10-450c-8450-02e7e11df73a.jpg" alt="">
                        <img src="${ctx}/static/mall/images/detail/8ec84942-0e12-45a3-8b58-c265c706385a.jpg" alt="">
                        <img src="${ctx}/static/mall/images/detail/541ed668-3f10-43d1-8352-5f7b74b25939.jpg" alt="">
                        <img src="${ctx}/static/mall/images/detail/da9b24ac-688f-4768-84bb-e9f0c3418542.jpg" alt="">
                        <img src="${ctx}/static/mall/images/detail/47c19a2b-42c1-4e1a-a516-5ad861964c83.jpg" alt="">
                    </div>--]
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
                                <td width="300">${secrecy("username", order.username)}</td>
                                <td width="300">${order.quantity}</td>
                                <td width="300">${order.point}</td>
                                <td width="300">${order.createDate}</td>
                            [/#list]
                        </tbody>
                    </table>
                    <div class="page-bar clearfix"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="quick-bar" [#--style="display: none;"--]>
        <div class="container clearfix">
            <div class="left"><span class="title">${goods.name}</span></div>
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

    <div id="orderFormBox" style="display: none">
        <div class="site-text site-block" style="padding: 10px 10px 10px 0px">
        <form id="orderForm" class="layui-form" action="">
            <input type="hidden" name="goodsId" value="${goods.id}" />
            <input type="hidden" name="quantity" />
            <input type="hidden" name="point" />
            <div class="layui-form-item">
                <label class="layui-form-label" style="width: 90px">商品名称</label>
                <div class="layui-input-block" style="vertical-align: middle">
                    <p style="padding-top: 11px">${goods.name}</p>
                    </div>
                </div>
            <div class="layui-form-item">
                <label class="layui-form-label" style="width: 90px">收货地址</label>
                <div class="layui-input-block">
                    <div class="layui-unselect layui-form-select" id="confirm">
                        [#if addresses?? && addresses?size > 0]
                            <select id="address">
                                [#list addresses as address]
                                    <option value="${address.id}">${address.areaObj.fullName}|${address.address}|${address.consignee}|${address.mobile}</option>
                                [/#list]
                            </select>
                        [#else ]
                            <a href="${ctx}/uc/shipping_address" target="_blank" class="orange">您还没有收货地址，立即添加?</a>
                        [/#if]
                    </div>
                </div>
            <div class="layui-form-item">
                <label class="layui-form-label" style="width: 90px">使用积分</label>
                <div class="layui-input-block" style="vertical-align: middle">
                    <p style="padding-top: 11px"><span id="payPoint">0</span>积分</p>
                    </div>
                </div>
            </form>
        </div>
    </div>

</div>



[/@insert]