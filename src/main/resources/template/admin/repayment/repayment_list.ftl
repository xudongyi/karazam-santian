[@nestedscript]
<script type="text/javascript">
    var datagrid;
    var dialog;
    $(function () {
        datagrid = $('#datagrid').datagrid({
            method: "get",
            url: '${ctx}/admin/postloan/repayment/repayment_list.json',
            queryParams: {borrowingId: ${borrowingId}},
            fit: true,
            fitColumns: true,
            border: true,
            idField: 'id',
            striped: true,
            pagination: true,
            pageNumber: 1,
            pageSize: 20,
            pageList: [10, 20, 30, 40, 50],
            singleSelect: true,
            columns: [[
                {field: 'id', title: '<strong>id</strong>',width:50},
                {field: 'stateDes', title: '<strong>状态</strong>', width:150},
                {field: 'period', title: '<strong>期数</strong>',  width:150},
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
                {field: 'payDate', title: '<strong>计划还款日期</strong>', width:150},
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
                {field: 'paidDate', title: '<strong>实际还款日期</strong>', width:150},
                {field: 'borrowerName', title: '<strong>借款人</strong>', width:150},
                {field: 'borrowerMobile', title: '<strong>借款人手机号</strong>', width:150},
                {field: 'createDate', title: '<strong>创建日期</strong>',width:20,  width:150},
                {field: 'operation',title:'<strong>操作</strong>', width:150,
                    formatter: function(value,row,index){
                        var render = '';
                        if(row.state == 'REPAYING'){
                            render += '<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="repaymentConfirm();">还款</a>';
                            render += ' | <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="repaymentConfirm2();">平台代还</a>';
                        }
                        return render;
                    }
                }
            ]]
        });
    });


    // 还款确认
    function repaymentConfirm(msg, control) {
        $.messager.confirm("提示", "确认还款?", function (r) {
            if (r) {
                repayment(false);
                return true;
            }
        });
        return false;
    }

    // 还款确认
    function repaymentConfirm2(msg, control) {
        $.messager.confirm("提示", "确认平台代还?", function (r) {
            if (r) {
                repayment(true);
                return true;
            }
        });
        return false;
    }

    //还款
    function repayment(isInstead) {
        var row = datagrid.datagrid('getSelected');
        if (rowIsNull(row)) return;
        console.log(isInstead);
        Keasy.post(
                "/admin/postloan/repayment/repayment",
                {
                    repaymentId: row.id,
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

    /*//弹窗 提前还款确认
    function aheadRepaymentConfirm(msg, control) {
        $.ajax({
            url: "/admin/postloan/repayment/aheadRepaymentAmount",
            data: {
                borrowingId:
            },
            type: "post",
            dataType: "json",
            cache: false,
            beforeSend: function(request, settings) {
            },
            success: function(result) {
                if (result.success) {
                    $.messager.confirm("确认", "提前还款金额"+result.data.aheadRepaymentAmount+"元，确认还款？", function (r) {
                        if (r) {
                            aheadRepayment();
                            return true;
                        }
                    });
                } else {
                    parent.$.messager.show({title: "提示", msg: result.message, position: "bottomRight"});
                }
            },
            error: function() {
                parent.$.messager.show({title: "提示", msg: "系统错误", position: "bottomRight"});
            }
        });
        return false;
    }*/



    /*//提前还款
    function aheadRepayment() {
        Keasy.post(
                "/admin/postloan/repayment/aheadRepayment",
                {borrowingId:},
                function (_data) {
                    successTip(_data, datagrid);
                    location.reload();
                },
                function(_data){
                    successTip(_data, datagrid);
                    location.reload();
                }
        );
    }*/

</script>
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="还款列表"]
<div id="userManage" class="easyui-layout" data-options="fit: true">
    <div data-options="region:'center',split:true,border:false,title:'还款列表'" style="width: 500px">

    [#--<div id="tb" style="padding:5px;height:auto">
        [@shiro.hasPermission name="borrowing:view"]
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-hamburg-library" plain="true" onclick="aheadRepaymentConfirm();">提前还款</a>
            <span class="toolbar-item dialog-tool-separator"></span>
        [/@shiro.hasPermission]
    </div>--]

        <table id="datagrid"></table>
        <div id="dialog"></div>

    </div>
</div>



[/@insert]