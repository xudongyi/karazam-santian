[@insert template="admin/layout/default_layout" title="还款列表${overdue}"]
<div id="userManage" class="easyui-layout" data-options="fit: true">
    <div data-options="region:'center',split:true,border:false,title:'还款列表'" style="width: 500px">
        [#if overdue!='true']
            <form id="searchFrom" action="">
                <div>
                    <input type="text" id="payDate" name="payDate" value="${payDate}" class="easyui-datebox" datefmt="yyyy-MM-dd"
                           data-options="width:150,showSeconds:true,prompt: '还款日期'"/>
                    <span class="toolbar-item dialog-tool-separator"></span>
                    <a href="javascript:query();" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
                </div>
            </form>
        [/#if ]

        <table id="datagrid"></table>
        <div id="dialog"></div>

        <div class="easyui-dialog-t" style="width:600px;height:300px" data-options="title:'My Dialog',buttons:'#bb',modal:true">
            对话框窗口内容。
        </div>
        <div id="bb">
            <a href="#" class="easyui-linkbutton">保存</a>
            <a href="#" class="easyui-linkbutton">关闭</a>
        </div>


    </div>
</div>

<script type="text/javascript">
    var datagrid;
    var dialog;
    [#if overdue!='true']
        var $payDate = $("#payDate").val();
    [/#if ]
    datagrid = $('#datagrid').datagrid({
        toolbar: '#tb222',
        method: "get",
        url: '${ctx}/admin/postloan/repayment/repayments_list.json',
        [#if overdue!='true']
            queryParams: {payDate: $payDate},
        [#else ]
            queryParams: {overdue: true},
        [/#if ]
        fit: true,
        fitColumns: true,
        border: true,
        idField: 'id',
        striped: true,
        pagination: true,
        /*rownumbers: true,*/
        pageNumber: 1,
        pageSize: 20,
        pageList: [10, 20, 30, 40, 50],
        singleSelect: true,
        columns: [[
            {field: 'id', title: '<strong>id</strong>', hidden: true},
            {field: 'stateDes', title: '<strong>状态</strong>', sortable: false},
            {field: 'period', title: '<strong>期数</strong>', sortable: false},
            {field: 'test', title: '<strong>计划还款金额</strong>',
                formatter: function(value,row,index){
                    var str = '';
                    str += '本金：' + row.capital + '元';
                    str += '<br/>利息：' + row.interest + '元';
                    str += '<br/>服务费：' + row.repaymentFee + '元';
                    return str;
                }
            },
            {field: 'test3', title: '<strong>逾期计算</strong>',
                formatter: function(value,row,index){
                    var str = '';
                    str += '逾期天数：' + row.overduePeriod + '天';
                    str += '<br/>逾期罚息：' + row.overdueInterest + '元';
                    str += '<br/>严重逾期天数：' + row.seriousOverduePeriod + '天';
                    str += '<br/>严重逾期罚息：' + row.seriousOverdueInterest + '元';
                    return str;
                }
            },
            {field: 'payDate', title: '<strong>计划还款日期</strong>',sortable: false},
            {field: 'test2', title: '<strong>实际还款金额</strong>',
                formatter: function(value,row,index){
                    var str = '';
                    if (row.state == 'repaid'){
                        str += '本金：' + row.capital + '元';
                        if(row.advance){
                            str += '<br/>利息：' + row.aheadInterest + '元';
                        }else{
                            str += '<br/>利息：' + row.interest + '元';
                        }
                        str += '<br/>服务费：' + row.repaymentFee + '元';
                        str += '<br/>逾期罚息：' + row.paidOverdueInterest + '元';
                        str += '<br/>严重逾期罚息：' + row.paidSeriousOverdueInterest + '元';
                    }else {
                        str += '本金：0元';
                        str += '<br/>利息：0元';
                        str += '<br/>服务费：0元';
                        str += '<br/>逾期罚息：0元';
                        str += '<br/>严重逾期罚息：0元';
                    }
                    return str;
                }
            },
            {field: 'paidDate', title: '<strong>实际还款日期</strong>',sortable: false},
            {field: 'borrowing', title: '<strong>借款编号</strong>',sortable: false},
            {field: 'borrowingTitle', title: '<strong>借款标题</strong>',sortable: false},
            {field: 'borrowerName', title: '<strong>借款人</strong>',sortable: false},
            {field: 'borrowerMobile', title: '<strong>借款人手机号</strong>',sortable: false},
            {field: 'createDate', title: '<strong>创建日期</strong>',width:20, sortable: false},
            {field: 'operation',title:'<strong>操作</strong>', width:80,
                formatter: function(value,row,index){
                    if(row.state == 'repaying'){
                        return '<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="repaymentConfirm();">还款</a>';
                    }
                    return '';
                }
            }
        ]],
        enableHeaderClickMenu: true,
        enableHeaderContextMenu: true,
        enableRowContextMenu: false,
        toolbar: '#tb',
        onSelect:function(index, row){

        }
    });

    //查询
    function query() {
        var obj = $("#searchFrom").serializeObject();
        datagrid.datagrid('load', obj);
    }

    // 还款确认
    function repaymentConfirm(msg, control) {
        $.messager.confirm("确认", msg, function (r) {
            if (r) {
                repayment();
                return true;
            }
        });
        return false;
    }

    //还款
    function repayment() {
        var row = datagrid.datagrid('getSelected');
        if (rowIsNull(row)) return;
        Keasy.post(
                "/admin/postloan/repayment/repayment",
                {repaymentId: row.id},
                function (_data) {
                    successTip(_data, datagrid);
                    location.reload();
                },
                function(_data){
                    successTip(_data, datagrid);
                    location.reload();
                }
        );
    }

</script>

[/@insert]