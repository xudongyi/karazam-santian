/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

var mendrechargeDatagrid;
var dialog;
$(function () {
    mendrechargeDatagrid = Keasy.datagrid({
        id:'bgrechargeDatagrid',
        url: ctx + '/mng/mendRecharge/json',
        columns: [[
            {field: 'id', title: 'ID', width: 50},
            {field: 'userId', title: '用户ID', hidden:true},
            {field: 'mobile', title: '手机', width: 120},
            {field: 'typeStr', title: '类型', width: 100},
            {field: 'methodStr', title: '方式', width: 100},
            {field: 'amount', title: '补单金额(元)', width: 100,
                formatter: function (value, row, index) {
                    return Keasy.formatToMoney(value);
                }
            },
            {field: 'paymentOrderId', title: '订单ID', width: 50},
            {field: 'capitalId', title: '资金ID', width: 50},
            {field: 'orderNo', title: '订单号', width: 180},
            {field: 'operator', title: '操作人', width: 100},
            {field: 'ip', title: 'IP', width: 100},
            {field: 'createDate', title: '创建时间', width: 100}
        ]]
    });

});
