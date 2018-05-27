/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

var paymentOrdersDg;
var paymentBgOrdersDg;
var dialog;
$(function () {
    var hasMendRecharge = $("#hasMendRecharge").val();
    var paymentOrdersColumns = [[
        {field: 'id', title: '订单ID', hidden: false,width:80},
        {field: 'userId', title: '用户ID', width: 100},
        {field: 'orderNo', title: '订单号', width: 250},
        {field: 'createDate', title: '创建时间', width: 150},
        {field: 'typeStr', title: '订单类型', width: 100},
        {field: 'statusStr', title: '订单状态', width: 80},
        {field: 'methodStr', title: '支付方式', width: 80},
        {
            field: 'amount', title: '金额', width: 100,
            formatter: function (value, row, index) {
                return Keasy.formatToMoney(value, 2);
            }
        },
        {field: 'memo', title: '备注', width: 300}
    ]];
    if (toshow) {
        paymentOrdersColumns = [[
            {field: 'id', title: '订单ID', hidden: false,width:80},
            {field: 'mobile', title: '手机号', width: 100},
            {field: 'orderNo', title: '订单号', width: 250},
            {field: 'createDate', title: '创建时间', width: 150},
            {field: 'typeStr', title: '订单类型', width: 100},
            {field: 'statusStr', title: '订单状态', width: 80},
            {field: 'methodStr', title: '支付方式', width: 80},
            {
                field: 'amount', title: '金额', width: 100,
                formatter: function (value, row, index) {
                    return Keasy.formatToMoney(value, 2);
                }
            },
            {field: 'memo', title: '备注', width: 300}
        ]];
    }
    if (hasMendRecharge) {
        paymentOrdersColumns[0].push({
            field: 'method', title: '操作', width: 100,
            formatter: function (value, row, index) {
                if (value == "GATEWAY_PAY") {
                    return '<a href="javascript:void(0);" onclick="goToMendRecharge(\''+row.orderNo+'\')">补单</a>';
                }
                return "";
            }
        });
    }
    paymentOrdersDg = Keasy.datagrid({
        id: "paymentOrdersDatagrid",
        url: ctx + '/admin/paymentOrder/order/json?toshow=' + toshow,
        rownumbers:false,
        fitColumns:false,
        columns: paymentOrdersColumns,
        onLoadSuccess: computeOrders,
        toolbar:"#paymentOrderTb"
    }, $("#paymentOrdersDg"));

    var paymentBgOrderColumns = [[
        {field: 'id', title: '订单ID', hidden: false,width:60},
        {field: 'userId', title: '用户ID', width: 100},
        {field: 'orderNo', title: '订单号', width: 250},
        {field: 'createDate', title: '创建时间', width: 150},
        {field: 'typeStr', title: '订单类型', width: 100},
        {field: 'statusStr', title: '订单状态', width: 80},
        {field: 'methodStr', title: '支付方式', width: 80},
        {
            field: 'amount', title: '金额', width: 100,
            formatter: function (value, row, index) {
                return Keasy.formatToMoney(value, 2);
            }
        },
        {field: 'memo', title: '备注', width: 300}
    ]];
    if (toshow) {
        paymentBgOrderColumns = [[
            {field: 'id', title: '订单ID', hidden: false,width:60},
            {field: 'mobile', title: '手机号', width: 100},
            {field: 'orderNo', title: '订单号', width: 250},
            {field: 'createDate', title: '创建时间', width: 150},
            {field: 'typeStr', title: '订单类型', width: 100},
            {field: 'statusStr', title: '订单状态', width: 80},
            {field: 'methodStr', title: '支付方式', width: 80},
            {
                field: 'amount', title: '金额', width: 100,
                formatter: function (value, row, index) {
                    return Keasy.formatToMoney(value, 2);
                }
            },
            {field: 'memo', title: '备注', width: 300}
        ]];
    }
    paymentBgOrdersDg = Keasy.datagrid({
        id: "paymentBgOrdersDatagrid",
        url: ctx + '/admin/paymentOrder/bgorder/json?toshow=' + toshow,
        rownumbers:false,
        onLoadSuccess: computeBgOrders,
        fitColumns:false,
        columns: paymentBgOrderColumns,
        toolbar:"#paymentBgOrderTb"
    }, $("#paymentBgOrdersDg"));
});

function paymentOrderQuery() {
    var obj = $("#paymentOrderSearchForm").serializeObject();
    Keasy.reloadGrid(paymentOrdersDg, obj);
}
function exportPaymentOrder() {
    var url = ctx + "/admin/paymentOrder/exportPaymentOrder";
    $("#paymentOrderSearchForm").attr("action",url);
    $("#paymentOrderSearchForm").submit();
}
function paymentBgOrderQuery() {
    var obj = $("#paymentBgOrderSearchForm").serializeObject();
    Keasy.reloadGrid(paymentBgOrdersDg, obj);
}
function exportPaymentBgOrder() {
    var url = ctx + "/admin/paymentOrder/exportPaymentBgOrder";
    $("#paymentBgOrderSearchForm").attr("action",url);
    $("#paymentBgOrderSearchForm").submit();
}

function goToMendRecharge(orderNo) {
    console.log(orderNo);
    parent.$.messager.confirm('提示', '是否确定补单？', function (data) {
        if (data) {
            Keasy.post("/admin/paymentOrder/goToMendRecharge", {orderNo:orderNo}, function (data) {
                successTip(data, paymentOrdersDg);
            })
        }
    });
}

function computeOrders() {//计算函数
    var rows = paymentOrdersDg.datagrid('getRows')//获取当前的数据行
    var realtotal=paymentOrdersDg.datagrid('getData').total;
    var amount = 0;
    for (var i = 0; i < rows.length; i++) {
        amount += rows[i]['amount'];
    }
    //新增一行显示合计信息
    paymentOrdersDg.datagrid('appendRow',
        {
            id: '本页合计',
            amount: amount
        });

    var amountTotal = 0;
    $.ajax({
        url: ctx + "/admin/paymentOrder/findAllOrder",
        data: {
            mobile: $("#paymentOrderSearchForm").find("[name='filter_LIKES_mobile']").val(),
            orderNo:$("#paymentOrderSearchForm").find("[name='filter_LIKES_order_no']").val(),
            type:$("#paymentOrderSearchForm").find("[name='filter_EQS_p.type']").val(),
            status:$("#paymentOrderSearchForm").find("[name='filter_EQS_p.status']").val(),
            method:$("#paymentOrderSearchForm").find("[name='filter_EQS_method']").val(),
            startCreateDate:$("#paymentOrderSearchForm").find("[name='filter_GTD_p.create_date']").val(),
            endCreateDate:$("#paymentOrderSearchForm").find("[name='filter_LTD_p.create_date']").val()
        },
        type: "post",
        dataType: "json",
        cache: false,
        success: function(message) {
            if(message.status == "success") {
                for (var i = 0; i < message.data.length; i++) {
                    amountTotal += message.data[i]['amount'];
                }
                //新增一行显示合计信息
                paymentOrdersDg.datagrid('appendRow',
                    {
                        id: '总计',
                        amount: amountTotal
                    });
            } else {

            }
            $(".pagination").eq(0).pagination('refresh',{	// 改变选项并刷新分页栏信息
                total:realtotal,
            });
        }
    });
}
//-----
function computeBgOrders() {//计算函数
    var realtotal_H=paymentBgOrdersDg.datagrid('getData').total;
    var rows = paymentBgOrdersDg.datagrid('getRows')//获取当前的数据行
    var amount = 0;
    for (var i = 0; i < rows.length; i++) {
        amount += rows[i]['amount'];
    }
    //新增一行显示合计信息
    paymentBgOrdersDg.datagrid('appendRow',
        {
            id: '本页合计',
            amount: amount
        });

    var amountTotal = 0;
    $("#paymentBgOrderSearchForm").find()
    $.ajax({
        url: ctx + "/admin/paymentOrder/findAllBgOrder",
        data: {
            mobile: $("#paymentBgOrderSearchForm").find("[name='filter_LIKES_mobile']").val(),
            orderNo:$("#paymentBgOrderSearchForm").find("[name='filter_LIKES_order_no']").val(),
            type:$("#paymentBgOrderSearchForm").find("[name='filter_EQS_p.type']").val(),
            status:$("#paymentBgOrderSearchForm").find("[name='filter_EQS_p.status']").val(),
            method:$("#paymentBgOrderSearchForm").find("[name='filter_EQS_method']").val(),
            startCreateDate:$("#paymentOrderSearchForm").find("[name='filter_GTD_p.create_date']").val(),
            endCreateDate:$("#paymentOrderSearchForm").find("[name='filter_LTD_p.create_date']").val()
        },
        type: "post",
        dataType: "json",
        cache: false,
        success: function(message) {
            if(message.status == "success") {
                for (var i = 0; i < message.data.length; i++) {
                    amountTotal += message.data[i]['amount'];
                }
                //新增一行显示合计信息
                paymentBgOrdersDg.datagrid('appendRow',
                    {
                        id: '总计',
                        amount: amountTotal
                    });
            } else {

            }
            $(".pagination").eq(1).pagination('refresh',{	// 改变选项并刷新分页栏信息
                total:realtotal_H,
            });
        }
    });
}