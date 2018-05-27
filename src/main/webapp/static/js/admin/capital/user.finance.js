/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

var userFundDatagrid;
var dialog;
$(function () {
    var dataColumns = [[
        {field: 'id', title: 'ID', width:60, "rowspan":2,"colspan":1},
        {field: 'userId', title: '用户ID', width: 120,"rowspan":2,"colspan":1,
            formatter: function (value, row, index) {
                if (row.userId == undefined) {
                    return "";
                }
                if (row.type == 'GENERAL') {
                    return "<span style='color: #ff0000'>个 </span>" + value;
                }
                if (row.type == 'MERCHANT'){
                    return "<span style='color: #ff0000'>商 </span>" + value;
                }
                if (row.type == 'ENTERPRISE'){
                    return "<span style='color: #ff0000'>企 </span>" + value;
                }
                return "<span style='color: #089eff'> </span>" + value;
            }
        },
        {
            field: 'balance', title: '余额', width: 100,"rowspan":2,"colspan":1,
            formatter: function (value, row, index) {
                return Keasy.formatToMoney(value);
            }
        },
        {
            field: 'frozen', title: '冻结', width: 100,"rowspan":2,"colspan":1,
            formatter: function (value, row, index) {
                return Keasy.formatToMoney(value);
            }
        },
        {"title":"借款/投资","colspan":2},
        {"title":"待收/待还","colspan":2},
        // {
        //     field: 'experience', title: '体验金', width: 100,"rowspan":2,"colspan":1,
        //     formatter: function (value, row, index) {
        //         return Keasy.formatToMoney(value);
        //     }
        // },
        // {
        //     field: 'curBal', title: 'IPS账户余额', width: 100,"rowspan":2,"colspan":1,
        //     formatter: function (value, row, index) {
        //         if (value==null){
        //             return '';
        //         }else {
        //             return Keasy.formatToMoney(value);
        //         }
        //     }
        // },
        // {
        //     field: 'availBal', title: 'IPS可用余额', width: 100,"rowspan":2,"colspan":1,
        //     formatter: function (value, row, index) {
        //         if (value==null){
        //             return '';
        //         }else {
        //             return Keasy.formatToMoney(value);
        //         }
        //     }
        // },
        // {
        //     field: 'freezeBal', title: 'IPS冻结余额', width: 100,"rowspan":2,"colspan":1,
        //     formatter: function (value, row, index) {
        //         if (value==null){
        //             return '';
        //         }else {
        //             return Keasy.formatToMoney(value);
        //         }
        //     }
        // },
        // {
        //     field: 'repaymentBal', title: 'IPS还款专用余额', width: 100,"rowspan":2,"colspan":1,
        //     formatter: function (value, row, index) {
        //         if (value==null){
        //             return '';
        //         }else {
        //             return Keasy.formatToMoney(value);
        //         }
        //     }
        // },
        // {
        //     field: 'queryDate', title: 'IPS查询时间', width: 100,"rowspan":2,"colspan":1
        // },
        {field: 'createDate', title: '注册日期', width: 140,"rowspan":2,"colspan":1}
    ],[
        {
            field: 'borrowingAmts', title: '借款总额', width: 100,
            formatter: function (value, row, index) {
                return Keasy.formatToMoney(value, 2);
            }
        },
        {
            field: 'investmentAmts', title: '投资总额', width: 100,
            formatter: function (value, row, index) {
                return Keasy.formatToMoney(value, 2);
            }},
        {
            field: 'credit', title: '待收金额', width: 100,
            formatter: function (value, row, index) {
                return Keasy.formatToMoney(value, 2);
            }
        },
        {
            field: 'debit', title: '待还金额', width: 100,
            formatter: function (value, row, index) {
                return Keasy.formatToMoney(value, 2);
            }
        }
    ]];
    if (toshow) {
        dataColumns = [[
            {field: 'id', title: 'ID', width:60, "rowspan":2,"colspan":1},
            {field: 'userName', title: '登录名', width: 120,"rowspan":2,"colspan":1,
                formatter: function (value, row, index) {
                    if (row.userId == undefined) {
                        return "";
                    }
                    if (row.type == 'GENERAL') {
                        return "<span style='color: #ff0000'>个 </span>" + value;
                    }
                    if (row.type == 'MERCHANT'){
                        return "<span style='color: #ff0000'>商 </span>" + value;
                    }
                    if (row.type == 'ENTERPRISE'){
                        return "<span style='color: #ff0000'>企 </span>" + value;
                    }
                    return "<span style='color: #089eff'> </span>" + value;
                }
            },
            {field: 'realName', title: '姓名', width: 100,"rowspan":2,"colspan":1},
            {field: 'idNo', title: '身份证号', width: 150,"rowspan":2,"colspan":1},
            {field: 'mobile', title: '手机号', width: 100,"rowspan":2,"colspan":1},
            {
                field: 'balance', title: '余额', width: 100,"rowspan":2,"colspan":1,
                formatter: function (value, row, index) {
                    return Keasy.formatToMoney(value);
                }
            },
            {
                field: 'frozen', title: '冻结', width: 100,"rowspan":2,"colspan":1,
                formatter: function (value, row, index) {
                    return Keasy.formatToMoney(value);
                }
            },
            {"title":"借款/投资","colspan":2},
            {"title":"待收/待还","colspan":2},
            // {
            //     field: 'experience', title: '体验金', width: 100,"rowspan":2,"colspan":1,
            //     formatter: function (value, row, index) {
            //         return Keasy.formatToMoney(value);
            //     }
            // },
            // {
            //     field: 'curBal', title: 'IPS账户余额', width: 100,"rowspan":2,"colspan":1,
            //     formatter: function (value, row, index) {
            //         if (value==null){
            //             return '';
            //         }else {
            //             return Keasy.formatToMoney(value);
            //         }
            //     }
            // },
            // {
            //     field: 'availBal', title: 'IPS可用余额', width: 100,"rowspan":2,"colspan":1,
            //     formatter: function (value, row, index) {
            //         if (value==null){
            //             return '';
            //         }else {
            //             return Keasy.formatToMoney(value);
            //         }
            //     }
            // },
            // {
            //     field: 'freezeBal', title: 'IPS冻结余额', width: 100,"rowspan":2,"colspan":1,
            //     formatter: function (value, row, index) {
            //         if (value==null){
            //             return '';
            //         }else {
            //             return Keasy.formatToMoney(value);
            //         }
            //     }
            // },
            // {
            //     field: 'repaymentBal', title: 'IPS还款专用余额', width: 100,"rowspan":2,"colspan":1,
            //     formatter: function (value, row, index) {
            //         if (value==null){
            //             return '';
            //         }else {
            //             return Keasy.formatToMoney(value);
            //         }
            //     }
            // },
            // {
            //     field: 'queryDate', title: 'IPS查询时间', width: 100,"rowspan":2,"colspan":1
            // },
            {field: 'createDate', title: '注册日期', width: 140,"rowspan":2,"colspan":1}
        ],[
            {
                field: 'borrowingAmts', title: '借款总额', width: 100,
                formatter: function (value, row, index) {
                    return Keasy.formatToMoney(value, 2);
                }
            },
            {
                field: 'investmentAmts', title: '投资总额', width: 100,
                formatter: function (value, row, index) {
                    return Keasy.formatToMoney(value, 2);
                }},
            {
                field: 'credit', title: '待收金额', width: 100,
                formatter: function (value, row, index) {
                    return Keasy.formatToMoney(value, 2);
                }
            },
            {
                field: 'debit', title: '待还金额', width: 100,
                formatter: function (value, row, index) {
                    return Keasy.formatToMoney(value, 2);
                }
            }
        ]];
    }
    userFundDatagrid = Keasy.datagrid({
        id:'userFundDatagrid',
        url: ctx + '/admin/capital/userFund/json?toshow=' + toshow,
        rownumbers:false,
        onLoadSuccess: compute,
        fitColumns:false,
        singleSelect: false,
        columns: dataColumns,
        toolbar: '#tb'
    });

});

function query() {
    var obj = $("#searchFrom").serializeObject();
    Keasy.reloadGrid(userFundDatagrid, obj);
}

//查询并更新Ips信息
function queryIpsInfo() {
    var ids = [];
    var rows = userFundDatagrid.datagrid('getSelections');
    for(var i=0; i<rows.length; i++){
        ids.push(rows[i].userId);
        if (rows[i].state == "paid") {
            $.messager.alert("提示","");
            return;
        }
    }
    if(ids.length<1){
        $.messager.alert("提示","请至少选择一行数据");
        return;
    }
    $.messager.confirm('确认对话框', '共'+ ids.length +'条数据,确认查询？', function(r){
        if (r){
            Keasy.delete(ctx + "/admin/capital/ips/"+ids,{},function (r) {
                successTip(r,userFundDatagrid);
            });
        }
    });
}

function exportExcelUserFund() {
    var url = ctx + "/admin/capital/exportExcelUserFund";
    $("#searchFrom").attr("action",url);
    $("#searchFrom").submit();
}

function compute() {//计算函数
    var rows = userFundDatagrid.datagrid('getRows')//获取当前的数据行
    var realtotal=userFundDatagrid.datagrid('getData').total;
    var balance = 0,
        frozen = 0,
        borrowingAmts = 0,
        investmentAmts = 0,
        credit = 0,
        debit=0,
        experience = 0;
    for (var i = 0; i < rows.length; i++) {
        balance +=rows[i]['balance'];
        frozen += rows[i]['frozen'];
        borrowingAmts +=rows[i]['borrowingAmts'];
        investmentAmts +=rows[i]['investmentAmts'];
        credit += rows[i]['credit'];
        debit += rows[i]['debit'];
        experience += rows[i]['experience'];
    }
    //新增一行显示合计信息
    userFundDatagrid.datagrid('appendRow',
        {
            id: '本页合计',
            userName:' ',
            balance:balance,
            frozen:frozen,
            borrowingAmts:borrowingAmts,
            investmentAmts: investmentAmts,
            credit: credit,
            debit: debit,
            experience: experience,
            isBorrower:""
        });
    $.parser.parse(userFundDatagrid);
    var balanceTotal = 0,
        frozenTotal = 0,
        borrowingAmtsTotal = 0,
        investmentAmtsTotal = 0,
        creditTotal = 0,
        debitTotal = 0,
        experienceTotal = 0;
    $.ajax({
        url: ctx + "/admin/capital/findAllUserFund",
        data: {
            mobile: $("input[name='filter_LIKES_mobile']").val(),
            startCreateDate: $("input[name='filter_GTD_u.create_date']").val(),
            endCreateDate: $("input[name='filter_LTD_u.create_date']").val(),
        },
        type: "post",
        dataType: "json",
        cache: false,
        success: function(message) {
            if(message.status == "success") {
                for (var i = 0; i < message.data.length; i++) {
                    balanceTotal += message.data[i]['balance'];
                    frozenTotal += message.data[i]['frozen'];
                    borrowingAmtsTotal += message.data[i]['borrowingAmts'];
                    investmentAmtsTotal += message.data[i]['investmentAmts'];
                    creditTotal += message.data[i]['credit'];
                    debitTotal += message.data[i]['debit'];
                    experienceTotal += message.data[i]['experience'];
                }
                //新增一行显示合计信息
                userFundDatagrid.datagrid('appendRow',
                    {
                        id: '总计',
                        userName:' ',
                        balance:balanceTotal,
                        frozen:frozenTotal,
                        borrowingAmts:borrowingAmtsTotal,
                        investmentAmts: investmentAmtsTotal,
                        credit: creditTotal,
                        debit: debitTotal,
                        experience: experienceTotal,
                        isBorrower:""
                    });
            } else {

            }
            $(".pagination").pagination('refresh',{	// 改变选项并刷新分页栏信息
                total:realtotal,
            });
        }
    });
}
