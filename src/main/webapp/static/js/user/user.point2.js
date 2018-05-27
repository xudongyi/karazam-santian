/**
 * Created by chenxinglin
 */

var dateSearchParams = {
    all: {},
    last_one_month: {
        filter_GED_create_date: '',
        filter_LED_create_date: ''
    },
    last_three_month: {
        filter_GED_create_date: '',
        filter_LED_create_date: ''
    },
    last_year: {
        filter_GED_create_date: '',
        filter_LED_create_date: ''
    }
};

var action = {
    pointRecords: 'pointRecords',
    goodsOrder: 'goodsOrder',
    // withdraw: 'withdraw'
};
var actionUrl = {
    pointRecords: ctx + '/uc/point/data',
    goodsOrder: ctx + '/uc/goods_order/data',
    // withdraw: ctx + '/uc/withdraw/json'
};
// var countActionUrl = {
//     pointRecords: '',
//     goodsOrder: ctx + '/uc/goodsOrder/count',
//     // withdraw: ctx + '/uc/withdraw/count'
// };
var dataTpl = {
    pointRecords: 'pointRecordsTpl',
    goodsOrder: 'goodsOrderTpl',
    // withdraw: 'withdrawTpl'
};
var typeSearchParams = {
    pointRecords: {
        name : 'method'
    },
    goodsOrder:  {
        name : 'status'
    }
};
var currentAction = action.pointRecords;
var reqParams = {};

$(function () {
    var searchType, dateSearch, typepointRecordschange;

    dataLoad(currentAction, reqParams);

    $('.typepointRecords').click(function () {
        var $this = $(this);
        searchType = $this.attr("searchType");
        if (searchType != "") {
            $('.riqi').hide();
        }
        else {
            $('.riqi').show();
        }
        currentAction = action[searchType];
        $('.' + currentAction + 'Type').show().siblings('.riqi').hide();
        $('.' + currentAction + '-records-count').show().siblings('.records-count').hide();
        $this.addClass('cur').siblings('.typepointRecords').removeClass('cur');
        $('#' + currentAction + 'Title').show().siblings('.title').hide();
        dataLoad(currentAction, reqParams);
        // dataCount(currentAction);
    });
    // $('.dateSearch').click(function () {
    //     var $this = $(this);
    //     $this.addClass('cur').siblings().removeClass('cur');
    //     dateSearch = $this.attr("value");
    //     // dateSearchChange(dateSearch);
    //     var name = typeSearchParams[currentAction].name;
    //     var tmp = {};
    //     tmp[name] = typepointRecordschange;
    //     dataLoad(currentAction, $.extend(reqParams, tmp));
    // });
    //
    // $.each(action, function (index, val) {
    //     $('.' + val + '-form-control').bind("change", function () {
    //         var $this = $(this);
    //         typepointRecordschange = $this.val();
    //         var name = typeSearchParams[currentAction].name;
    //         var tmp = {};
    //         tmp[name] = typepointRecordschange;
    //         dataLoad(currentAction, $.extend(reqParams, tmp));
    //     });
    // });
    // $(document).on('click', '.detail-title', function () {
    //     var index = $(this).attr('detailIndex');
    //     var domdetail = "zichan-detail-" + index;
    //     console.log(domdetail);
    //     $('.have-more').hide();
    //     $(('.zichan-detail-') + index).show();
    // });
    // $("body").on("click", function () {
    //     $(".zichan-detail").hide();
    //     $(".zichan-detail").removeClass('cur');
    // });
    // $('.button-special').click(function () {
    //     $.get('/uc/pointRecords/ucexportpointRecordsRecord', {
    //         typepointRecords: searchType,
    //         method: searchType
    //     })
    // });
});

var dataLoad = function (action, params) {
    $.getJSON(
        actionUrl[action],
        $.extend({
            page: 1,
            rows: 5
        }, params),
        function (res) {
            if (res.total > 0) {
                $('.answer').show();
                $('#pagination').show();
                $('.empty').hide();
            } else {
                $('.answer').hide();
                $('#pagination').hide();
                $('.empty').show();
            }
            //使用方式跟独立组件完全一样
            var tpl = document.getElementById(dataTpl[action]);
            laytpl(tpl.innerHTML).render(res, function (string) {
                if (string.trim()) {
                    $("#noDataTip").hide();
                } else {
                    $("#noDataTip").show();
                }
                $('#pointRecordss').html(string);
                orderDo();
            });
            laypage({
                skin: '#B81B26',
                cont: 'pagination'
                , pages: res.pages
                , jump: function (e, first) { //触发分页后的回调
                    if (!first) {
                        $.getJSON(actionUrl[action], $.extend({
                                page: e.curr,//当前页
                                rows: res.pageSize
                            }, params),
                            function (rest) {
                                e.pages = e.last = rest.pages; //重新获取总页数，一般不用写
                                //解析数据
                                laytpl(tpl.innerHTML).render(rest, function (stringt) {
                                    $('#pointRecordss').html(stringt);
                                    orderDo();
                                });
                            });
                    }
                }
            });
        });
};

// var dataCount = function (action) {
//     var countActUrl = countActionUrl[action];
//     if (countActUrl == '') {
//         return;
//     }
//     $.getJSON(
//         countActUrl,
//         {},
//         function (res) {
//             $('.' + action + '-records-count').find('em').eq(0).text(formatMoney(res.data.allAmount));
//             $('.' + action + '-records-count').find('em').eq(1).text(formatMoney(res.data.successAmount));
//         });
// };

// function dateSearchChange(value) {
//     var currentTime = new Date();
//     switch (value) {
//         case 'all': {
//             reqParams = {};
//             break;
//         }
//         case 'last_one_month': {
//             var endTime = currentTime.format('yyyy-MM-dd 23:59:59') ;
//             var date1Month = new Date(currentTime.setMonth(currentTime.getMonth() - 1));
//             var startTime = date1Month.format('yyyy-MM-dd 00:00:00');
//             dateSearchParams.last_one_month.filter_GED_create_date = startTime;
//             dateSearchParams.last_one_month.filter_LED_create_date = endTime;
//             reqParams = dateSearchParams.last_one_month;
//             break;
//         }
//         case 'last_three_month': {
//             var endTime = currentTime.format('yyyy-MM-dd 23:59:59');
//             var date3Month = new Date(currentTime.setMonth(currentTime.getMonth() - 3));
//             var startTime = date3Month.format('yyyy-MM-dd 00:00:00');
//             dateSearchParams.last_three_month.filter_GED_create_date = startTime;
//             dateSearchParams.last_three_month.filter_LED_create_date = endTime;
//             reqParams = dateSearchParams.last_three_month;
//             break;
//         }
//         case 'last_year': {
//             var endTime = currentTime.format('yyyy-MM-dd 23:59:59');
//             var dateYear = new Date(currentTime.setFullYear(currentTime.getFullYear() - 1));
//             var startTime = dateYear.format('yyyy-MM-dd 00:00:00');
//             dateSearchParams.last_year.filter_GED_create_date = startTime;
//             dateSearchParams.last_year.filter_LED_create_date = endTime;
//             reqParams = dateSearchParams.last_year;
//             break;
//         }
//     }
// }

function orderDo(value) {
    //撤销
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

    //确认收货
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

}

$(function(){

    orderDo();

    // // 点击提醒查看详情
    // $(".fa-question-circle-o").hover(function(){
    //     var index = $(this).parent().index();
    //     $(".showbox"+index).fadeIn();
    // },function(){
    //     var index = $(this).parent().index();
    //     $(".showbox"+index).hide();
    // });
    // $(".asset_title .fa-question-circle-o").hover(function(){
    //     $(this).siblings(".tixing").toggle();
    // },function(){
    //     $(this).siblings(".tixing").toggle();
    // });
    //
    // $(".otherBox .fa-question-circle-o").hover(function(){
    //     $(this).parents("li").children(".tixing").toggle();
    // },function(){
    //     $(this).parents("li").children(".tixing").toggle();
    // });
    //
    // $(".fa .fa-question-circle-o").hover(function(){
    //     $(this).siblings(".feetip .tixing .poundage").toggle();
    // },function(){
    //     $(this).siblings(".feetip .tixing .poundage").toggle();
    // });
    //
    // $(".fa .fa-question-circle-o").hover(function(){
    //     $(this).parents("li").children(".feetip .tixing .poundage").toggle();
    // },function(){
    //     $(this).parents("li").children(".feetip .tixing .poundage").toggle();
    // });
});
