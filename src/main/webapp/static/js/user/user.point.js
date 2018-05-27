/**
 * Created by chenxinglin
 */

var currentTab = "orderTab";  //orderTab pointTab followTab

var options = {
    orderTab:  {
        url : ctx + '/uc/goods_order/data',
        params : {
            type : "",
            status : "",
            month : "",
        },
        selector:{
            template : "orderTabTpl",
            data : ".orderTab.data",
            pagination : ".orderTab.pagination",
            empty : ".orderTab.empty",
        }
    },
    pointTab:  {
        url : ctx + '/uc/point/data',
        params : {
            type : "",
            month : "",
        },
        selector:{
            template : "pointTabTpl",
            data : ".pointTab.data",
            pagination : ".pointTab.pagination",
            empty : ".pointTab.empty",
        }
    },
    followTab:  {
        url : '/uc/point/follow/data',
        params : {},
        selector:{
            template : "followTabTpl",
            data : ".followTab.data",
            pagination : ".followTab.pagination",
            empty : ".followTab.empty",
        }
    }
};

$(function () {

    dataLoad(options[currentTab]);

    /**
     * 标签切换
     */
    $('.tab-col-3 li').on('click', function () {
        $('.tab-col-3 li').removeClass("active");
        $(this).addClass("active");
        $('.acc-content-tab').hide();
        $('.acc-content-tab.'+$(this).attr("data-type")).show();
        currentTab = $(this).attr("data-type");
        dataLoad(options[currentTab]);
    });

    /**
     * 订单查询条件切换
     */
    $('.orderTab .categories li').on('click', function () {
        $('.acc-content-tab .categories li').removeClass("selected");
        $(this).addClass("selected");
        options[currentTab].params.type = $(this).attr("data-category");
        console.log(options[currentTab]);
        dataLoad(options[currentTab]);
    });
    $('.orderTab .datetime li').on('click', function () {
        $('.acc-content-tab .datetime li').removeClass("selected");
        $(this).addClass("selected");
        options[currentTab].params.month = $(this).attr("data-date");
        console.log(options[currentTab]);
        dataLoad(options[currentTab]);
    });
    $('.orderTab .trade-status li').on('click', function () {
        $('.acc-content-tab .trade-status li').removeClass("selected");
        $(this).addClass("selected");
        options[currentTab].params.status = $(this).attr("data-status");
        console.log(options[currentTab]);
        dataLoad(options[currentTab]);
    });
    /**
     * 积分查询条件切换
     */
    $('.pointTab .categories li').on('click', function () {
        $('.acc-content-tab .categories li').removeClass("selected");
        $(this).addClass("selected");
        options[currentTab].params.type = $(this).attr("data-category");
        console.log(options[currentTab]);
        dataLoad(options[currentTab]);
    });
    $('.pointTab .datetime li').on('click', function () {
        $('.acc-content-tab .datetime li').removeClass("selected");
        $(this).addClass("selected");
        options[currentTab].params.month = $(this).attr("data-date");
        console.log(options[currentTab]);
        dataLoad(options[currentTab]);
    });


});

var dataLoad = function (options) {
    $.getJSON(
        options.url,
        options.params,
        function (res) {
            if (res.total > 0) {
                $(options.selector.empty).hide();
            } else {
                $(options.selector.empty).show();
            }
            //使用方式跟独立组件完全一样
            var tpl = document.getElementById(options.selector.template);
            laytpl(tpl.innerHTML).render(res, function (string) {
                $(options.selector.data).html(string);
                eventDo();
            });
            laypage({
                skin: '#d0d0d0',
                cont: $(options.selector.pagination)
                , pages: res.pages
                , jump: function (e, first) { //触发分页后的回调
                    if (!first) {
                        $.getJSON(
                            options.url,
                            $.extend({
                                page: e.curr,//当前页
                                rows: res.pageSize
                            }, options.params),
                            function (rest) {
                                e.pages = e.last = rest.pages; //重新获取总页数，一般不用写
                                //解析数据
                                laytpl(tpl.innerHTML).render(rest, function (stringt) {
                                    $(options.selector.data).html(stringt);
                                    eventDo();
                                });
                            }
                        );
                    }
                }
            });
        });
};

/**
 * 事件
 */
var eventDo = function() {
    //订单撤销
    $(".cancelOrder").on("click", function(){
        var orderId = $(this).attr("data-orderId");
        layer.open({
            title: '温馨提示',
            type: 1,
            skin: 'layui-layer-rim', //加上边框
            area: ['300px', '180px'], //宽高
            content: '<p style="padding-top: 30px;" align="center">确认撤销？</p>',
            btn: ['确认', '取消'],
            yes: function (index) {
                $.getJSON(ctx + "/uc/goods_order/cancel",
                    {
                        orderId : orderId
                    },
                    function (res) {
                        if(res.status == 'success'){
                            layer.msg(res.message,{icon: 1});
                            location.href = ctx + "/uc/point";
                        }else {
                            layer.msg(res.message,{icon: 1});
                        }
                    });

                layer.close(index);
                // $('#orderFormBox').hide();
            }, btn2: function (index) {
                // $('#orderFormBox').hide();
            },cancel: function(){
                // $('#orderFormBox').hide();
            }
        });
    });

    //订单确认收货
    $(".confirmReceipt").on("click", function(){
        var orderId = $(this).attr("data-orderId");
        layer.open({
            title: '温馨提示',
            type: 1,
            skin: 'layui-layer-rim', //加上边框
            area: ['300px', '180px'], //宽高
            content: '<p style="padding-top: 30px;" align="center">确认收货？</p>',
            btn: ['确认', '取消'],
            yes: function (index) {
                $.getJSON(ctx + "/uc/goods_order/receive",
                    {orderId : orderId},
                    function (res) {
                        if(res.status == 'success'){
                            layer.msg(res.message,{icon: 1});
                            location.href = ctx + "/uc/point";
                        }else {
                            layer.msg(res.message,{icon: 1});
                        }
                    });
                layer.close(index);
            }, btn2: function (index) {
            },cancel: function(){
            }
        });
    });

    // 关注事件
    $('.follow').on('click', function () {
        $this = $(this);
        $.getJSON(ctx + "/mall/follow",
            {goodsId: $this.attr("goodsId")},
            function (res) {
                if (res.status == 'success') {
                    layer.msg('已取消关注', {icon: 1});
                    dataLoad(options[currentTab]);
                    //$this.replaceWith('<a class="follow delete-focus" goodsId="' + $this.attr("goodsId") + '"href="javascript:;">再次关注</a>');
                } else {
                    layer.msg(res.message, {icon: 1});
                }
            });

    });
}