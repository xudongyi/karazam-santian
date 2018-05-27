[@nestedstyle]
    [@css href="css/cubeportfolio.min.css" /]
    [@css href="css/mate/base.css" /]
[/@nestedstyle]
[@nestedscript]
<link rel="stylesheet" href="${ctx}/static/mall/base-51daa35356.css"/>
<link rel="stylesheet" href="${ctx}/static/mall/common-36b6434b23.css"/>
[#--<link rel="stylesheet" href="${ctx}/static/mall/index-8d1117e649.css"/>--]
<link rel="stylesheet" href="${ctx}/static/mall/index-cdf4a85047.css"/>
    [@js src= "js/jquery.cubeportfolio.min.js"/]
    [@js src= "mall/js/canbuy.js"/]
[/@nestedscript]
[@insert template="layout/mall_layout" title="积分商城" user=kuser]
<div class="wrapper ff-layout">
    <div class="container">
        <div class="my-points"><span>我的可用积分：</span>
            <span class="orange mypoint">${userPoint.point}</span>
            <span class="ml50">可购商品数：</span>
            <span class="mynum dataCount">0</span>
            <span>个</span>
        </div>
        <div class="benefits-list">
            <div class="head-bar clearfix">
                <div class="sort clearfix">
                    <div class="sort-type by-point current" data-type="4">
                        <span>积分</span>
                        <i class="fa fa-caret-up point-up"></i>
                        <i class="fa fa-caret-down point-down"></i>
                    </div>
                </div>
                <div class="find-count">
                    <span>找到</span>
                    <span id="dataCount" class="dataCount total-count bold">0</span>
                    <span>个商品</span>
                    <span>&nbsp;</span>
                </div>
            </div>
            <div class="list clearfix data">
            </div>
            <div class="page-bar clearfix">
                <div id="pagination" class="pagination page-bar"></div>
            </div>
            <div id="noDataTip" class="empty inesnttist-box" style="display: none">
                <div style="font-size: 20px;text-align:center;line-height: 50px;margin:20px 0 20px 0;color: #b0b0b0;">
                    没有啦
                </div>
            </div>
        </div>
    </div>
</div>

<script id="dataTpl" type="text/html">
    {{#  $.each(d.rows, function(index, item){ }}
        <div class="box">
            <span class="orange focus-tip"></span>
            {{# if(item.currentFollow){ }}
                <button class="follow followList fa fa-heart orange" goodsId="{{ item.id }}" title="已关注"></button>
            {{# }else{ }}
                <button class="follow followList fa fa-heart-o" goodsId="{{ item.id }}" title="加关注"></button>
            {{# } }}
            <div class="p-img">
                <a href="${ctx}/mall/goods/detail/{{ item.id }}" target="_blank">
                    <img src="${dfsUrl}{{ item.image }}" alt="{{ item.name }}/">
                </a>
            </div>
            <div class="info">
                <a href="${ctx}/mall/goods/detail/{{ item.id }}" target="_blank" class="title">{{ item.name }}</a>
                <div class="mt17 clearfix">
                    <p class="price orange">
                        <span class="price-item">{{ item.point }}</span>
                        <span class="unit">积分</span>
                    </p>
                    <p class="left-count"><span>剩余：</span> <span class="count">{{ item.stock }}</span></p>
                </div>
                <div class="buy" style="bottom: -30px; opacity: 0;">
                    <a href="${ctx}/mall/goods/detail/{{ item.id }}" class="orange">立即换购</a>
                </div>
            </div>
        </div>
    {{#  }); }}
</script>
[/@insert]


