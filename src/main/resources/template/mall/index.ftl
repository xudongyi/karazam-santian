[@nestedstyle]
    [@css href="css/cubeportfolio.min.css" /]
    [@css href="css/mate/base.css" /]
[/@nestedstyle]
[@nestedscript]
<link rel="stylesheet" href="${ctx}/static/mall/base-51daa35356.css"/>
<link rel="stylesheet" href="${ctx}/static/mall/common-36b6434b23.css"/>
<link rel="stylesheet" href="${ctx}/static/mall/index-758b30f102.css"/>
    <script>
        var giftsCount = ${gifts?size!"0"};
    </script>
    [@js src= "mall/js/index.js"/]
[/@nestedscript]
[@insert template="layout/mall_layout" title="积分商城" user=kuser]

<div class="ff-floors" id="ff-floors">
    <div class="wrapper ff-layout">
        <div class="paituo-banner prime-slide">
            <div class="ps-wrap">
                <div class="lunhuan">
                    <div id="lunhuanback">
                        <a id="showhref" href="javascript:;" style="cursor:default;">
                            [#list banners as banner]
                                <p style="background: url(&quot;${dfsUrl}/${banner.path}&quot;) center center no-repeat; opacity: 1;" data-href="${banner.url}"></p>
                            [/#list]
                        </a>
                    </div>
                    <div class="lunhuan_main">
                        <ul id="lunbonum">
                            <li class="lunboone"></li>
                            <li class=""></li>
                            <li class=""></li>
                        </ul>
                    </div>
                </div>
                <div class="ps-trigger-pre" style="opacity: 1;"></div>
                <div class="ps-trigger-next" style="opacity: 1;"></div>
            </div>
        </div>

        [#--<div class="fengjf-ads">
            <div class="banner-static clearfix">
                <a title="周末红包" href=""> <img alt="周末红包" src="${ctx}/static/mall/images/paituo-F76E74F1AE5544A9B55CE45301728AB6.png"></a>
                <a title="积分商城介绍" href=""> <img alt="积分商城介绍" src="${ctx}/static/mall/images/paituo-8EAB6859D0364BDB88C4545E3965191C.png"></a>
                <a title="vip介绍" href=""> <img alt="vip介绍" src="${ctx}/static/mall/images/paituo-4B653D51C16646F8A342E68F4B6E7F8A.jpg"></a>
            </div>
        </div>--]

        <div class="fengjf-gift-shop clearfix lazyloadjs" [#--js-src="${ctx}/static/mall/gift_shop-863b076852.js"--]>
            <div class="container">
                <div class="floor-title"><h2><a href="${ctx}/mall/gift">${setting.basic.siteName}礼品店</a></h2></div>
                <div class="floor-desc"><p>我的态度，您的选择</p> <a href="${ctx}/mall/gift">更多<i class="orange more-row">></i></a>
                </div>
                <div class="clearfix gift-shop-box">
                    <div class="bx-wrapper" style="max-width: 100%;">
                        <div class="bx-viewport" style="width: 100%; overflow: hidden; position: relative; height: 380px;">
                            <ul class="gift-shop-slide" style="width: 615%; position: relative; transition-duration: 0.5s; transform: translate3d(-0px, 0px, 0px);">
                                [#list gifts as goods]
                                    <li class="gift-item slide bx-clone" style="float: left; list-style: none; position: relative; width: 1198px;">
                                        <div class="item-left">
                                            <div class="box">
                                                [#if goods.currentFollow]
                                                    <a class="follow focus fa fa-heart orange" goodsId="${goods.id}" title="已关注"></a>
                                                [#else ]
                                                    <a class="follow focus fa fa-heart-o" goodsId="${goods.id}" title="加关注"></a>
                                                [/#if]
                                                <span class="orange focus-tip"></span>
                                                <a href="${ctx}/mall/goods/detail/${goods.id}"><img src="${dfsUrl}${goods.image}" alt=""> </a>
                                            </div>
                                        </div>
                                        <div class="item-right">
                                            <a target="_blank" class="actions buy" href="${ctx}/mall/goods/detail/${goods.id}">立即换购</a>
                                            <h1><a target="_blank" href="">${goods.name}</a></h1>
                                            <p class="desc"> ${goods.seoDescription}</p>
                                            <div class="box">
                                                <span class="price orange"> <span>${goods.point}</span>积分 </span>
                                                <span class="surplus"> 剩余：<span>${goods.stock}</span> </span>
                                            </div>
                                        </div>
                                    </li>
                                [/#list]
                            </ul>
                        </div>
                        <div class="bx-controls bx-has-pager bx-has-controls-direction">
                            <div class="bx-pager bx-default-pager">
                                <div class="bx-pager-item"><a href="" data-slide-index="0"
                                                              class="bx-pager-link ">1</a></div>
                                <div class="bx-pager-item"><a href="" data-slide-index="1"
                                                              class="bx-pager-link ">2</a></div>
                                <div class="bx-pager-item"><a href="" data-slide-index="2"
                                                              class="bx-pager-link active">3</a></div>
                                <div class="bx-pager-item"><a href="" data-slide-index="3"
                                                              class="bx-pager-link">4</a></div>
                            </div>
                            [#if gifts?? && gifts?size>1]
                                <div class="bx-controls-direction">
                                    <a class="bx-prev" href="javascript:;">Prev</a>
                                    <a class="bx-next" href="javascript:;">Next</a>
                                </div>
                            [/#if]

                        </div>
                    </div>
                </div>
                <div class="lazyloadjs" [#--js-src="${ctx}/static/mall/recommend_list-10c3668286.js"--]></div>
            </div>
        </div>
    <div class="fengjf-benefit">
        <div class="container">
            <div class="floor-title"><h2><a href="${ctx}/mall/benefit">购实惠</a></h2></div>
            <div class="floor-desc"><p>品质优选，品味生活</p> <a href="${ctx}/mall/benefit">更多<span
                    class="orange more-row">></span></a></div>
            <div class="benefit-box clearfix">
                <ul>
                    [#list benefits as goods]
                        <li class="benefit-item" pid="C77243BD-E373-488C-B6C4-EDE5139EEF1F">
                        [#--<span class="orange focus-tip"></span>--]
                            <div class="goods">
                            [#--<a class="follow focus fa fa-heart-o" href="javascript:void(0)"></a>--]
                                [#if goods.currentFollow]
                                    <a class="follow focus fa fa-heart orange" goodsId="${goods.id}" title="已关注"></a>
                                [#else ]
                                    <a class="follow focus fa fa-heart-o" goodsId="${goods.id}" title="加关注"></a>
                                [/#if]
                                <span class="orange focus-tip"></span>
                                <a target="_blank" href="${ctx}/mall/goods/detail/${goods.id}"> <img src="${dfsUrl}${goods.image}"></a>
                            </div>
                            <div class="desc">
                                <a target="_blank" class="buy" href="${ctx}/mall/goods/detail/${goods.id}" style="bottom: -30px; opacity: 0;">立即换购</a>
                                <a href="${ctx}/mall/goods/detail/${goods.id}" target="_blank">${goods.name}</a>
                                <div class="box">
                                    <span class="price orange"><span>${goods.point}</span>积分 </span>
                                    <span class="surplus"> 剩余<span class="colon">:</span><span>${goods.stock}</span></span>
                                </div>
                            </div>
                        </li>
                    [/#list]
                </ul>
            </div>
        </div>
    </div>
    <div class="fengjf-bottom-ads lazyloadjs" [#--js-src="${ctx}/static/mall/bottom_ads-56c9b99a28.js"--]>
        <div class="container">
            <div class="static-middle clearfix">
                <div class="static-content"><a target="_blank" class="static-content-inner" href=""> <img
                        src="${ctx}/static/mall/images/paituo-CD93CC8E19444D3B9A5BCA82EECBE9D6.jpg"> <span class="text">每日签到赢好礼 积分加息送不停</span>
                    <div class="shop-now" href="">立即前往<i class="fa fa-long-arrow-right"></i></div>
                </a></div>
                <div class="static-content"><a target="_blank" class="static-content-inner" href=""> <img
                        src="${ctx}/static/mall/images/paituo-BD69E56FE1CE4A589FE609049CAEC1E8.jpg"> <span class="text">投资双重利 有“分”又有“息”</span>
                    <div class="shop-now" href="">立即前往<i class="fa fa-long-arrow-right"></i></div>
                </a></div>
                <div class="static-content"><a target="_blank" class="static-content-inner" href=""> <img
                        src="${ctx}/static/mall/images/paituo-1FFC09F984EB41DAB7F2F0E46CAFE398.jpg"> <span class="text">积分一触即得 手机用户专享</span>
                    <div class="shop-now" href="">立即前往<i class="fa fa-long-arrow-right"></i></div>
                </a></div>
            </div>
        </div>
    </div>
</div>
</div>

[/@insert]