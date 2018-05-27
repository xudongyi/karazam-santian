var datagrid;
var dialog;
$(function () {
    var dataColums = [[
        {field: 'id', title: '记录ID',sortable: true, width:120},
        {field: 'investor', title: '投资人ID', width:120},
        {field: 'borrowing', title: '借款ID', width:120},
        {field: 'orderNo', title: '订单号', hidden: true, width:120},
        {field: 'amount', title: '投资金额', width:120},
        {field: 'stateStr', title: '投资状态', width:120},
        {field: 'operationMethodStr', title: '操作方式', width:120},
        // {field: 'couponCode', title: '优惠码',  sortable: true},
        // {field: 'preferentialAmount', title: '优惠金额',  sortable: true},
        {field: 'isExperience', title: '是否为体验投资', width:120,
            formatter:function (val,row,index) {
                if(val=='total'){
                    return "";
                }
                if(val){
                    return "是";
                }else{
                    return "否";
                }
            }
        },
        {field: 'title', title: '借款标题', width:120},
        {field: 'borrowingAmount', title: '借款总金额', width:120},
        {field: 'borrowingStateStr', title: '借款状态', width:120},
        {field: 'isTransfer', title: '是否转让', width:120,
            formatter:function (val,row,index) {
                if(val=='total'){
                    return "";
                }
                if(val){
                    return "是";
                }else{
                    return "否";
                }
            }
        },
        {field: 'createDate', title: '投资时间',sortable: true, width:120},
        {field: 'memo', title: '备注', width:120},
        {field: 'deviceTypeStr', title: '投标来源', width:120}
    ]];
    if (toshow) {
        dataColums = [[
            {field: 'id', title: '记录ID',sortable: true, width:120},
            {field: 'investor', title: '投资人ID', width:120},
            {field: 'borrowing', title: '借款ID', width:120},
            {field: 'orderNo', title: '订单号', hidden: true, width:120},
            {field: 'realName', title: '投资人姓名', width:120},
            {field: 'mobile', title: '投资人手机号', width:120},
            {field: 'amount', title: '投资金额', width:120},
            {field: 'stateStr', title: '投资状态', width:120},
            {field: 'operationMethodStr', title: '操作方式', width:120},
            {field: 'isExperience', title: '是否为体验投资', width:120,
                formatter:function (val,row,index) {
                    if(val=='total'){
                        return "";
                    }
                    if(val){
                        return "是";
                    }else{
                        return "否";
                    }
                }
            },
            {field: 'title', title: '借款标题', width:120},
            {field: 'borrowingAmount', title: '借款总金额', width:120},
            {field: 'borrowingStateStr', title: '借款状态', width:120},
            {field: 'isTransfer', title: '是否转让', width:120,
                formatter:function (val,row,index) {
                    if(val=='total'){
                        return "";
                    }
                    if(val){
                        return "是";
                    }else{
                        return "否";
                    }
                }
            },
            {field: 'createDate', title: '投资时间',sortable: true, width:120},
            {field: 'memo', title: '备注', width:120},
            {field: 'deviceTypeStr', title: '投标来源', width:120},
            {field: 'operator', title: '操作员', width:120}
        ]];
    }
    datagrid = $('#datagrid').datagrid({
        method: "get",
        url: ctx+'/admin/investment/detailrecordlist.json?toshow=' + toshow,
        fit: true,
        fitColumns: true,
        border: false,
        idField: 'id',
        striped: true,
        pagination: true,
        rownumbers: false,
        pageNumber: 1,
        pageSize: 20,
        pageList: [10, 20, 30, 40, 50],
        singleSelect: true,
        columns: dataColums,
        headerContextMenu: [
            {
                text: "冻结该列", disabled: function (e, field) {
                return datagrid.datagrid("getColumnFields", true).contains(field);
            },
                handler: function (e, field) {
                    datagrid.datagrid("freezeColumn", field);
                }
            },
            {
                text: "取消冻结该列", disabled: function (e, field) {
                return datagrid.datagrid("getColumnFields", false).contains(field);
            },
                handler: function (e, field) {
                    datagrid.datagrid("unfreezeColumn", field);
                }
            }
        ],
        enableHeaderClickMenu: true,
        enableHeaderContextMenu: true,
        enableRowContextMenu: false,
        toolbar: '#tb',
        onLoadSuccess: compute,
    });

});

function compute() {//计算函数
    var realtotal;
    var rows = datagrid.datagrid('getRows')//获取当前的数据行
    realtotal=datagrid.datagrid('getData').total;
    var amount = 0,
        borrowingAmount = 0;
    for (var i = 0; i < rows.length; i++) {
        amount += rows[i]['amount'];
        borrowingAmount += rows[i]['borrowingAmount'];
    }
    //新增一行显示合计信息
    datagrid.datagrid('appendRow',
        {
            id: '本页合计',
            amount:amount,
            borrowingAmount: borrowingAmount,
            isExperience: 'total',
            isTransfer: 'total'
        });

    var amountTotal = 0,
        borrowingAmountTotal = 0;
    $.ajax({
        url: ctx + "/admin/investment/investmentRecordSum",
        data: {
            realName: $("input[name='filter_LIKES_realName']").val(),
            mobile:$("input[name='filter_LIKES_mobile']").val(),
            startDate:$("input[name='startDate']").val(),
            endDate:$("input[name='endDate']:first").val()
        },
        type: "post",
        dataType: "json",
        cache: false,
        success: function(message) {
            if(message.status == "success") {
                amountTotal += message.data.amountTotal;
                borrowingAmountTotal += message.data.borrowingAmountTotal;
                //新增一行显示合计信息
                datagrid.datagrid('appendRow',
                    {
                        id: '总计',
                        amount: amountTotal,
                        borrowingAmount: borrowingAmountTotal,
                        isExperience: 'total',
                        isTransfer: 'total'
                    });
            } else {

            }
            $(".pagination").pagination('refresh',{	// 改变选项并刷新分页栏信息重新设置记录数
                total:realtotal,
            });
        }
    });
}

//查询
function query() {
    var obj = $("#searchFrom").serializeObject();
    datagrid.datagrid('load', obj);
}