[@nestedscript]
    <script type="text/javascript">
        var datagrid;
        var dialog;
        datagrid = $('#datagrid').datagrid({
            toolbar: '#tb222',
            method: "get",
            url: '${ctx}/admin/postloan/repayment/repaymentPlan_list.json',
            queryParams: {borrowingId: ${borrowingId}},
            fit: true,
            fitColumns: true,
            border: true,
            idField: 'id',
            striped: true,
            pagination: false,
            pageNumber: 1,
            pageSize: 20,
            pageList: [10, 20, 30, 40, 50],
            singleSelect: true,
            columns: [[
                {field: 'id', title: 'ID', width:50},
                {field: 'stateDes', title: '状态', width:150},
                {field: 'repaymentRecordPeriod', title: '期数', width:150},
                {field: 'repaymentRecordCapital', title: '本金(元)', width:150},
                {field: 'repaymentRecordInterest', title: '利息(元)', width:150},
                {field: 'paidOverdueInterest', title: '已付逾期罚息(元)', width:150},
                {field: 'paidSeriousOverdueInterest', title: '已付严重逾期罚息(元)', width:150},
                {field: 'recoveryFee', title: '服务费(元)', width:150},
                {field: 'repaymentRecordPayDate', title: '计划回款日期', width:150},
                {field: 'paidAmount', title: '实际回款金额(元)', width:150},
                {field: 'paidDate', title: '实际回款日期', width:150},
                {field: 'investorName', title: '投资人', width:150},
                {field: 'investorMobile', title: '投资人手机号', width:150},
                {field: 'createDate', title: '创建日期',width:20, width:150}
            ]],
            enableHeaderClickMenu: true,
            enableHeaderContextMenu: true,
            enableRowContextMenu: false
        });

    </script>
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="回款列表"]
<div id="userManage" class="easyui-layout" data-options="fit: true">
    <div data-options="region:'center',split:true,border:false,title:'回款计划列表'" style="width: 500px">
        <table id="datagrid"></table>
        <div id="dialog"></div>
    </div>
</div>


[/@insert]