/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

var capitalDatagrid;
var dialog;
$(function () {
    var dataColums = [[
        {field: 'id', title: 'ID', width: 60, "rowspan": 2, "colspan": 1},
        {
            field: 'userId', title: '用户ID', width: 120, "rowspan": 2, "colspan": 1,
            formatter: function (value, row, index) {
                if (row.userId == undefined) {
                    return "";
                }
                if (row.type == 'GENERAL') {
                    return "<span style='color: #ff0000'>个 </span>" + value;
                }
                if (row.type == 'MERCHANT') {
                    return "<span style='color: #ff0000'>商 </span>" + value;
                }
                if (row.type == 'ENTERPRISE') {
                    return "<span style='color: #ff0000'>企 </span>" + value;
                }
                return "<span style='color: #089eff'> </span>" + value;
            }
        },
        {field: 'typeStr', title: '类型', width: 100, "rowspan": 2, "colspan": 1},
        {field: 'methodStr', title: '方式', width: 100, "rowspan": 2, "colspan": 1},
        {"title": "收入/支出", "colspan": 2},
        {"title": "冻结/解冻", "colspan": 2},
        {
            field: 'balance', title: '余额', width: 100, "rowspan": 2, "colspan": 1,
            formatter: function (value, row, index) {
                if (value == 'X') {
                    return "";
                }
                return Keasy.formatToMoney(value, 2);
            }
        },
        {field: 'ip', title: 'ip', width: 100, "rowspan": 2, "colspan": 1},
        {field: 'memo', title: '备注', width: 100, "rowspan": 2, "colspan": 1},
        {field: 'createDate', title: '创建时间', width: 140, "rowspan": 2, "colspan": 1}
    ], [
        {
            field: 'credit', title: '收入', width: 100,
            formatter: function (value, row, index) {
                return Keasy.formatToMoney(value, 2);
            }
        },
        {
            field: 'debit', title: '支出', width: 100,
            formatter: function (value, row, index) {
                return Keasy.formatToMoney(value, 2);
            }
        },
        {
            field: 'frozen', title: '冻结', width: 100,
            formatter: function (value, row, index) {
                return Keasy.formatToMoney(value, 2);
            }
        },
        {
            field: 'unfrozen', title: '解冻', width: 100,
            formatter: function (value, row, index) {
                return Keasy.formatToMoney(value, 2);
            }
        }

    ]];
    if (toshow) {
        dataColums = [[
            {field: 'id', title: 'ID', width: 60, "rowspan": 2, "colspan": 1},
            {
                field: 'mobile', title: '手机号', width: 120, "rowspan": 2, "colspan": 1,
                formatter: function (value, row, index) {
                    if (row.userId == undefined) {
                        return "";
                    }
                    if (row.type == 'GENERAL') {
                        return "<span style='color: #ff0000'>个 </span>" + value;
                    }
                    if (row.type == 'MERCHANT') {
                        return "<span style='color: #ff0000'>商 </span>" + value;
                    }
                    if (row.type == 'ENTERPRISE') {
                        return "<span style='color: #ff0000'>企 </span>" + value;
                    }
                    return "<span style='color: #089eff'> </span>" + value;
                }
            },
            {field: 'typeStr', title: '类型', width: 100, "rowspan": 2, "colspan": 1},
            {field: 'methodStr', title: '方式', width: 100, "rowspan": 2, "colspan": 1},
            {"title": "收入/支出", "colspan": 2},
            {"title": "冻结/解冻", "colspan": 2},
            {
                field: 'balance', title: '余额', width: 100, "rowspan": 2, "colspan": 1,
                formatter: function (value, row, index) {
                    if (value == 'X') {
                        return "";
                    }
                    return Keasy.formatToMoney(value, 2);
                }
            },
            {field: 'operator', title: '操作员', width: 100, "rowspan": 2, "colspan": 1},
            {field: 'ip', title: 'ip', width: 100, "rowspan": 2, "colspan": 1},
            {field: 'memo', title: '备注', width: 100, "rowspan": 2, "colspan": 1},
            {field: 'createDate', title: '创建时间', width: 140, "rowspan": 2, "colspan": 1}
        ], [
            {
                field: 'credit', title: '收入', width: 100,
                formatter: function (value, row, index) {
                    return Keasy.formatToMoney(value, 2);
                }
            },
            {
                field: 'debit', title: '支出', width: 100,
                formatter: function (value, row, index) {
                    return Keasy.formatToMoney(value, 2);
                }
            },
            {
                field: 'frozen', title: '冻结', width: 100,
                formatter: function (value, row, index) {
                    return Keasy.formatToMoney(value, 2);
                }
            },
            {
                field: 'unfrozen', title: '解冻', width: 100,
                formatter: function (value, row, index) {
                    return Keasy.formatToMoney(value, 2);
                }
            }

        ]];
    }
    capitalDatagrid = Keasy.datagrid({
        id: "capitalDatagrid",
        url: ctx + '/admin/capital/json?toshow=' + toshow,
        rownumbers:false,
        onLoadSuccess: compute,
        fitColumns:false,
        columns: dataColums,
        toolbar: '#capitalToolbar'
    });
});

function compute() {//计算函数
    var realtotal;
    var rows = capitalDatagrid.datagrid('getRows')//获取当前的数据行
    realtotal=capitalDatagrid.datagrid('getData').total;
    // var balance=0,
    var credit = 0,
        debit=0,
        frozen = 0,
        unfrozen = 0;
    for (var i = 0; i < rows.length; i++) {
        // balance +=rows[i]['balance'];
        credit += rows[i]['credit'];
        debit += rows[i]['debit'];
        frozen += rows[i]['frozen'];
        unfrozen += rows[i]['unfrozen'];
    }
    //新增一行显示合计信息
    capitalDatagrid.datagrid('appendRow',
        {
            id: '本页合计',
            mobile: ' ',
            balance:'X',
            credit: credit,
            debit: debit,
            frozen: frozen,
            unfrozen: unfrozen
        });

    // var balanceTotal = 0,
    var creditTotal = 0,
        debitTotal = 0,
        frozenTotal = 0,
        unfrozenTotal = 0;
    $.ajax({
        url: ctx + "/admin/capital/findAllCapital",
        data: {
            mobile: $("input[name='filter_LIKES_mobile']").val(),
            type:$("input[name='filter_EQS_c.type']").val(),
            method:$("input[name='filter_EQS_method']").val(),
            createDateBegin:$("input[name='createDateBegin']:first").val(),
            createDateEnd:$("input[name='createDateEnd']:last").val()
        },
        type: "post",
        dataType: "json",
        cache: false,
        success: function(message) {
            if(message.status == "success") {
                for (var i = 0; i < message.data.length; i++) {
                    // balanceTotal += message.data[i]['balance'];
                    creditTotal += message.data[i]['credit'];
                    debitTotal += message.data[i]['debit'];
                    frozenTotal += message.data[i]['frozen'];
                    unfrozenTotal += message.data[i]['unfrozen'];
                }
                //新增一行显示合计信息
                capitalDatagrid.datagrid('appendRow',
                    {
                        id: '总计',
                        mobile: ' ',
                        balance:'X',
                        credit: creditTotal,
                        debit: debitTotal,
                        frozen: frozenTotal,
                        unfrozen: unfrozenTotal
                    });
            } else {

            }
            console.log(realtotal+'1112');
            $(".pagination").pagination('refresh',{	// 改变选项并刷新分页栏信息
                total:realtotal,
            });
        }
    });
}

function capitalQuery() {
    var obj = $("#capitalSearchForm").serializeObject();
    Keasy.reloadGrid(capitalDatagrid, obj);
}

function exportCapitalRecord() {
    var url = ctx + "/admin/capital/exportCapitalRecord";
    $("#capitalSearchForm").attr("action",url);
    $("#capitalSearchForm").submit();
}