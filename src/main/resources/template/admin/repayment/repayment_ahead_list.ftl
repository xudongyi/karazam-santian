[@nestedscript]
<script type="text/javascript">
    var datagrid;
    var dialog;
    datagrid = $('#datagrid').datagrid({
        toolbar: '#tb222',
        method: "get",
        url: '/admin/postloan/repayment/repayment_ahead_list.json',
        queryParams: {borrowingId: ${borrowingId}},
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
            {field: 'period', title: '<strong>期数</strong>', sortable: true},
            {field: 'test', title: '<strong>计划还款金额</strong>',
                formatter: function(value,row,index){
                    var str = '';
                    str += '本金：' + row.capital + '元';
                    str += '<br/>利息：' + row.interest + '元';
                    str += '<br/>服务费：' + row.repaymentFee + '元';
                    return str;
                }
            },
            {field: 'test3', title: '<strong>提前还款计算</strong>',
                formatter: function(value,row,index){
                    var str = '';
                    str += '本金：' + row.capital + '元';
                    if(row.advance){
                        str += '<br/>利息：' + row.aheadInterest + '元';
                    }else{
                        str += '<br/>利息：' + row.interest + '元';
                    }
                    str += '<br/>服务费：' + row.repaymentFee + '元';
                    return str;
                }
            },
            {field: 'payDate', title: '<strong>计划还款日期</strong>',sortable: true},
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
            {field: 'paidDate', title: '<strong>实际还款日期</strong>',sortable: true},
            {field: 'borrowerName', title: '<strong>借款人</strong>',sortable: true},
            {field: 'borrowerMobile', title: '<strong>借款人手机号</strong>',sortable: true},
            {field: 'createDate', title: '<strong>创建日期</strong>',width:20, sortable: true},
        ]],
        enableHeaderClickMenu: true,
        enableHeaderContextMenu: true,
        enableRowContextMenu: false,
        toolbar: '#tb',
        onSelect:function(index, row){

        }
    });


    // 还款确认
    function aheadRepaymentConfirm(msg, control) {
        $.messager.confirm("提示", "一键还款？", function (r) {
            if (r) {
                aheadRepayment(false);
                return true;
            }
        });
        return false;
    }

    // 还款确认
    function aheadRepaymentConfirm2(msg, control) {
        $.messager.confirm("提示", "一键平台代还？", function (r) {
            if (r) {
                aheadRepayment(true);
                return true;
            }
        });
        return false;
    }

    //提前还款
    function aheadRepayment(isInstead) {
        Keasy.post(
                "/admin/postloan/repayment/aheadRepayment",
                {
                    borrowingId: ${borrowingId},
                    isInstead: isInstead
                },
                function (_data) {
                    successTip(_data, datagrid);
//                    location.reload();
                },
                function(_data){
                    successTip(_data, datagrid);
//                    location.reload();
                }
        );
    }

</script>
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="提前还款列表"]
<div id="userManage" class="easyui-layout" data-options="fit: true">
    <div data-options="region:'center',split:true,border:false,title:'提前还款列表'" style="width: 500px">

    <div id="tb" style="padding:5px;height:auto">
        [@shiro.hasPermission name="borrowing:view"]
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="fa fa-recycle" [#--plain="true" --]onclick="aheadRepaymentConfirm();">一键还款</a>
            [#--<span class="toolbar-item dialog-tool-separator"></span>--]
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="fa fa-recycle"[#-- plain="true" --]onclick="aheadRepaymentConfirm2();">一键平台代还</a>
            [#--<span class="toolbar-item dialog-tool-separator"></span>--]
        [/@shiro.hasPermission]

        <span>还款总额：${aheadRepaymentAmount}元</span>
    </div>

        <table id="datagrid"></table>
        <div id="dialog">

        </div>

        <div class="easyui-dialog-t" style="width:600px;height:300px" data-options="title:'My Dialog',buttons:'#bb',modal:true">
            对话框窗口内容。
        </div>
        <div id="bb">
            <a href="#" class="easyui-linkbutton">保存</a>
            <a href="#" class="easyui-linkbutton">关闭</a>
        </div>


    </div>
</div>
[/@insert]

