/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

var capitalDatagrid;
var dialog;
$(function () {
    var isPlatform = $("#isPlatform").val();
    capitalDatagrid = Keasy.datagrid({
        url: ctx + '/admin/capital/platFormCapitals',
        rownumbers: false,
        onLoadSuccess: compute,
        columns: [[
            {field: 'id', title: '记录ID', width:60},
            {field: 'typeStr', title: '类型', width: 100},
            {field: 'methodStr', title: '方式', width: 100},
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
            {field: 'userLoginName', title: '关联记录账户', width: 100},
            {field: 'operator', title: '操作员', width: 100},
            {field: 'ip', title: 'ip', width: 100},
            {field: 'memo', title: '备注', width: 100},
            {field: 'createDate', title: '创建时间', width: 100}
        ]],
        toolbar: '#capitalToolbar'
    });
});


function query() {
    var obj = $("#searchFrom").serializeObject();
    Keasy.reloadGrid(capitalDatagrid, obj);
}
function exportExcelPlatForm() {
    var url = ctx + "/admin/capital/exportExcelPlatForm";
    $("#searchFrom").attr("action",url);
    $("#searchFrom").submit();
}

function compute() {//计算函数
    var realtotal=capitalDatagrid.datagrid('getData').total;
    var rows = capitalDatagrid.datagrid('getRows')//获取当前的数据行
    var credit = 0,
        debit=0;
    for (var i = 0; i < rows.length; i++) {
        credit += rows[i]['credit'];
        debit += rows[i]['debit'];
    }
    //新增一行显示合计信息
    capitalDatagrid.datagrid('appendRow',
        {
            id: '本页合计',
            credit: credit,
            debit: debit
        });

    var creditTotal = 0,
        debitTotal = 0;
    $.ajax({
        url: ctx + "/admin/capital/findAllPlatFormAmt",
        data: {
            operator: $("input[name='operator']").val(),
            type:$("input[name='type']").val(),
            method:$("input[name='method']").val(),
            createDateBegin:$("input[name='createDateBegin']").val(),
            createDateEnd:$("input[name='createDateEnd']").val()
        },
        type: "post",
        dataType: "json",
        cache: false,
        success: function(message) {
            if(message.status == "success") {
                for (var i = 0; i < message.data.length; i++) {
                    creditTotal += message.data[i]['credit'];
                    debitTotal += message.data[i]['debit'];
                }
                //新增一行显示合计信息
                capitalDatagrid.datagrid('appendRow',
                    {
                        id: '总计',
                        credit: creditTotal,
                        debit: debitTotal
                    });
            } else {

            }
            $(".pagination").pagination('refresh',{	// 改变选项并刷新分页栏信息
                total:realtotal,
            });
        }
    });
}