[@nestedstyle]
    [@css href="css/cubeportfolio.min.css" /]
    [@css href="css/mate/base.css" /]
    <link rel="stylesheet" href="${ctx}/static/mall/base-51daa35356.css"/>
    <link rel="stylesheet" href="${ctx}/static/mall/common-36b6434b23.css"/>
    <link rel="stylesheet" href="${ctx}/static/mall/orderDetail.css"/>
    <link rel="stylesheet" href="${ctx}/static/lib/layui/css/layui.css"/>
[/@nestedstyle]
[@nestedscript]
    [@js src= "js/jquery.cubeportfolio.min.js"/]
    [@js src= "lib/layui/lay/dest/layui.all.js"/]
    [@js src= "mall/js/detail.js"/]
[/@nestedscript]
[@insert template="layout/mall_layout" title="积分商城" user=kuser]

<div class="wrapper ff-layout">
    <div class="container">
        <div class="breakCust"> 商城首页 <span> &gt; </span> 订单详情</div>
        <div class="orderTime">
            下单时间： <span class="orderT">${goodsOrder.createDate}</span>
            订单号： <span class="orderNO">${goodsOrder.orderNo}</span>
            状态： <span class="orderStatus orange">${goodsOrder.status.displayName}</span>
        </div>
        <div class="orderBox">
            <div class="tit"> 订单详情</div>
            <div class="box">
                <div class="personInfo_wrapper">
                    <div class="personInfo">
                        <div class="t1">收货人信息</div>
                        <div class="t2">
                            <span class="p" style="letter-spacing: 5px;">收货人</span>：
                            <span class="v">${goodsOrder.adrConsignee}</span>
                        </div>
                        <div class="t2"> 手  机：
                            <span class="v">${goodsOrder.adrMobile}</span>
                        </div>
                        <div class="t2"> 地  址：
                            <span class="v">${goodsOrder.areaObj.fullName}|${goodsOrder.adrAddress}</span>
                        </div>
                    </div>
                </div>
                <div class="productsInfo_wrapper">
                    <div class="personInfo sendInfo">
                        <div class="t1">配送信息</div>
                        <div class="t2">发货状态：
                            [#--<span class="p" style="letter-spacing: 5px;">发货状态</span>：--]
                            <span class="v orange">${goodsOrder.logisticsStatus.displayName}</span>
                        </div>
                        [#if goodsOrder.logisticsStatus != 'unshipped']
                            <div class="t2">
                                <span class="p" style="letter-spacing: 5px;">配送方</span>：
                                <span class="v">${setting.basic.siteName}平台</span>
                            </div>
                            <div class="t2"> 物流公司：
                                <span class="v">${goodsOrder.logisticsCorp}</span>
                            </div>
                            <div class="t2"> 运单号码：
                                <span class="v">${goodsOrder.logisticsNo}</span>
                            </div>
                            <div class="t2"> 发货时间：
                                <span class="v">${goodsOrder.sendDate}</span>
                            </div>
                            <div class="t2"> 运  费：
                                <span class="v">免运费</span>
                            </div>
                        [/#if]
                    </div>
                </div>
                <div class="sendInfo_wrapper">
                    <div class="personInfo productInfor">
                        <div class="t1">商品信息</div>
                        <table class="productF">
                            <thead>
                            <tr>
                                <td width="132">&nbsp;</td>
                                <td width="300">商品</td>
                                <td>单价(积分)</td>
                                <td align="center" width="180">数量</td>
                                <td align="center" width="180">小计(积分)</td>
                            </tr></thead>
                            <tbody>
                            <tr>
                                <td>
                                    <a href="/mall/goods/detail/${goods.id}" target="_blank">
                                        <img style="border:1px solid #e6e6e6;width:80px;height:80px;" src="${dfsUrl}${goods.image}" alt="">
                                    </a>
                                </td>
                                <td>
                                    [#--<a href="target=&quot;_blank&quot;">${goods.name}</a>--]${goods.name}
                                </td>
                                <td>${goods.point}</td>
                                <td>${goodsOrder.quantity}</td>
                                <td class="orange">${goodsOrder.point}</td>
                            </tr>
                            </tbody>
                        </table>
                        <div class="jf"> 实付总积分：
                            <span class="orange big">${goodsOrder.point}</span>
                            <span class="orange">积分</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

[/@insert]