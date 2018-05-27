/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

var bgrechargeDatagrid;
var dialog;
$(function () {
    var hasBgRechargeAudit = $("#hasBgRechargeAudit").val();
    var dfsUrl = $("#dfsUrl").val();
    var columns = [[
        {field: 'id', title: 'ID', width: 50},
        {field: 'userName', title: '用户名', width: 80},
        {field: 'mobile', title: '手机号', width: 80},
        {field: 'typeStr', title: '类型', width: 50},
        {field: 'amount', title: '金额(元)', width: 100,
            formatter: function (value, row, index) {
                return Keasy.formatToMoney(value);
            }
        },
        {field: 'stateStr', title: '状态', width: 100},
        {field: 'proof', title: '凭证', width: 100,
            formatter: function (value, row, index) {
                if(value){
                    return "<a href=" + dfsUrl + value +" target='_blank'\>查看\</a>";
                }
                return "";
            }
        },
        {field: 'remark', title: '备注', width: 120},
        {field: 'createDate', title: '创建时间', width: 100},
        {field: 'state', title: '操作', width: 100,
            formatter: function (value, row, index) {
                if (value == 'WAIT_AUDIT' && hasBgRechargeAudit) {
                    return '<a href="javascript:void(0)" onclick="bgrechargeAudit(' + row.id + ',\'' + row.typeStr + '\')" title="后台' + row.typeStr +'审核">审核</a>';
                }
                return "";
            }
        }
    ]];
    if (!hasBgRechargeAudit) {
        columns = [[
            {field: 'id', title: 'ID', width: 50},
            {field: 'userName', title: '用户名', width: 80},
            {field: 'mobile', title: '手机号', width: 80},
            {field: 'typeStr', title: '类型', width: 50},
            {field: 'amount', title: '金额(元)', width: 100,
                formatter: function (value, row, index) {
                    return Keasy.formatToMoney(value);
                }
            },
            {field: 'stateStr', title: '状态', width: 100},
            {field: 'remark', title: '备注', width: 120},
            {field: 'createDate', title: '创建时间', width: 100}
        ]];
    }
    bgrechargeDatagrid = Keasy.datagrid({
        id:'bgrechargeDatagrid',
        url: ctx + '/mng/bgrecharge/json',
        rownumbers:false,
        onLoadSuccess: compute,
        columns: columns,
        toolbar: '#toolbar'
    });

});

function bgrechargeAudit(bgRechargeId, typeStr) {
    dialog = Keasy.dialog({
        dialogId: "bgRecharge",
        title: '后台' + typeStr + '审核',
        width: 500,
        height: 400,
        href: ctx + '/mng/bgrecharge/audit/' + bgRechargeId,
        maximizable: true,
        modal: true
    });
}

function doConfirmDialog(formData) {
    var bgrechargeId = formData.bgrechargeId;
    Keasy.post("/mng/bgrecharge/audit/" + bgrechargeId, formData, function (data) {
        successTip(data, bgrechargeDatagrid);
    })
}

//查询
function query() {
    var obj = $("#searchForm").serializeObject();
    bgrechargeDatagrid.datagrid('load', obj);
}
function exportBgRecharge() {
    var url = ctx + "/mng/bgrecharge/exportBgRecharge";
    $("#searchForm").attr("action",url);
    $("#searchForm").submit();
}

function compute() {//计算函数
    var rows = bgrechargeDatagrid.datagrid('getRows')//获取当前的数据行
    var amount = 0;
    for (var i = 0; i < rows.length; i++) {
        amount += rows[i]['amount'];
    }
    //新增一行显示合计信息
    bgrechargeDatagrid.datagrid('appendRow',
        {
            id: '本页合计',
            amount: amount
        });

    var amountTotal = 0;
    $.ajax({
        url: ctx + "/mng/bgrecharge/findAllRecord",
        data: {
            mobile: $("input[name='mobile']").val(),
            state:$("input[name='state']").val(),
            type:$("input[name='type']").val()
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
                bgrechargeDatagrid.datagrid('appendRow',
                    {
                        id: '总计',
                        amount: amountTotal
                    });
            } else {

            }
        }
    });
}