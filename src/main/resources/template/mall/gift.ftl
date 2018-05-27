[@nestedstyle]
    [@css href="css/cubeportfolio.min.css" /]
    [@css href="css/mate/base.css" /]
[/@nestedstyle]
[@nestedscript]
    <link rel="stylesheet" href="${ctx}/static/mall/base-51daa35356.css"/>
    <link rel="stylesheet" href="${ctx}/static/mall/common-36b6434b23.css"/>
    <link rel="stylesheet" href="${ctx}/static/mall/index-8d1117e649.css"/>
    [@js src= "js/jquery.cubeportfolio.min.js"/]
    [@js src= "mall/js/list.js"/]
    <script>
        var url = ctx + '/mall/data';
        var params = {
//            page: 1,
            type : "gift",
            sort : "createDate",
            order : "desc",
            filter_GEI_point : "",
            filter_LEI_point : ""
        };
    </script>
[/@nestedscript]
[@insert template="layout/gift_layout" title="积分商城" user=kuser]
<div class="wrapper ff-layout">
    <div class="banner">
        <div class="text"><p class="tit">积聚您的独有价值</p>
            <p class="subtit">${setting.basic.siteName}礼品店 超群体验</p></div>
    </div>
    <div class="container">
        <div class="gift-list">
            <div class="head-bar clearfix">
                <div class="sort clearfix">
                    <div class="typeSearch sort-type current" data-type="createDate"><span>最新</span> <i class="fa fa-caret-up"></i></div>
                    <div class="typeSearch sort-type" data-type="sales"><span>销量</span> <i class="fa fa-caret-down"></i></div>
                    <div class="typeSearch sort-type" data-type="follow"><span>人气</span> <i class="fa fa-caret-down"></i></div>
                    <div class="sort-type by-point" data-type=""><span>积分</span><i class="fa fa-caret-up point-up"></i><i class="fa fa-caret-down point-down"></i></div>
                </div>
                <div class="search-box">
                    <input id="point-min" name="point-min" class="point-interval" type="text" style="ime-mode:disabled"> <span>-</span>
                    <input id="point-max" name="point-max" class="point-interval" type="text" style="ime-mode:disabled">
                    <button class="btn-search">确定</button>
                </div>
                <div class="find-count">
                    <span>找到</span><span id="dataCount" class="total-count bold">0</span><span>个商品</span> <span>&nbsp;</span>
                    [#--<span class="pre-page bold">&lt;</span> <span>&nbsp;</span> <span class="current-page">1</span><span>/</span><span class="total-page">1</span>
                    <span>&nbsp;</span> <span class="next-page bold">&gt;</span>--]
                </div>
            </div>
            <div class="list clearfix">
                <span id="dataBox"></span>
            </div>
            <div class="page-bar clearfix">
                <div id="pagination" class="page-bar"></div>
            </div>
            <div id="noDataTip" class="inesnttist-box" style="display: none">
                <div style="font-size: 20px;text-align:center;line-height: 50px;margin:20px 0 20px 0;color: #b0b0b0;">
                    没有啦
                </div>
            </div>
        </div>
        <div class="hot">
            <div class="hot-title clearfix">
                <div class="line left"></div>
                <span>热门商品</span>
                <div class="line right"></div>
            </div>
            <div class="hot-list clearfix lazyloadjs loaded" js-src="hot_list-b7f7930e07.js" data-cid="98C6EBCC-B964-44CE-8DAA-91C049A484D8">

                [#list hots as goods]
                    <div class="box" data-product-id="44DDC58C-E09D-470A-85F3-8CB2CCDDF073">
                        <span class="orange focus-tip"></span>
                        [#if goods.currentFollow]
                            <button class="follow followHot fa fa-heart orange" goodsId="${goods.id}" title="已关注"></button>
                        [#else ]
                            <button class="follow followHot fa fa-heart-o" goodsId="${goods.id}" title="加关注"></button>
                        [/#if]
                        <div class="p-img">
                            <a href="${ctx}/mall/goods/detail/${goods.id}" target="_blank">
                                <img src="${dfsUrl}${goods.image}" alt="${goods.name}/"> </a></div>
                        <div class="info">
                            <a href="${ctx}/mall/goods/detail/${goods.id}" target="_blank" class="title">${goods.name}</a>
                            <div class="mt17 clearfix">
                                <p class="price orange"><span class="price-item">${goods.point}</span> <span class="unit">积分</span></p>
                                <p class="left-count"><span>剩余：</span> <span class="count">${goods.stock}</span></p>
                            </div>
                            <div class="buy">
                                <a href="${ctx}/mall/goods/detail/${goods.id}" class="orange">立即换购</a>
                            </div>
                        </div>
                    </div>
                [/#list]

            </div>
        </div>
    </div>
</div>

<script id="dataTpl" type="text/html">
    {{#  $.each(d.rows, function(index, item){ }}

        <div class="box-big" data-product-id="2D2C0AC2-C296-460B-BA68-339EB09C6526">
            <span class="orange focus-tip"></span>
            {{#  if(item.currentFollow){  }}
                <button class="follow followList fa fa-heart orange" goodsId="{{ item.id }}" title="已关注"></button>
            {{#  }else{ }}
                <button class="follow followList fa fa-heart-o" goodsId="{{ item.id }}" title="加关注"></button>
            {{#  } }}
            <div class="p-img">
                <a href="${ctx}/mall/goods/detail/{{ item.id }}" target="_blank">
                    <img src="${dfsUrl}{{ item.image }}" alt="{{ item.name }}" 限量艺术品=""> </a>
            </div>
            <div class="info">
                <p class="title">
                    <a href="${ctx}/mall/goods/detail/{{ item.id }}" target="_blank" class="name">{{ item.name }}</a>
                    <span class="price orange">{{ item.point }}</span>
                    <span class="unit orange">积分</span>
                </p>
                <p class="desc">{{ item.name }}</p>
                <a href="${ctx}/mall/goods/detail/{{ item.id }}" class="bug orange">立即换购</a>
                <p class="left-count"><span>剩余：</span> <span class="count">{{ item.stock }}</span></p>
            </div>
        </div>

    {{#  }); }}
</script>

[/@insert]


