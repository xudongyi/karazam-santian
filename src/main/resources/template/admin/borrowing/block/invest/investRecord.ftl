[#--借款 投资记录--]
<table class="easyui-datagrid" data-options="data:${investmentRecords},height:460">
    <thead>
        <tr>
            <th data-options="field:'id',width:130">投资记录编号</th>
            <th data-options="field:'investment',width:130">投资编号</th>
            <th data-options="field:'investorLoginName',width:150,
                formatter:function (value, row, index) {
                    if (row.investorType=='GENERAL') {
                        return '<span style=\'color: #089eff\'>个</span>' + value;
                    }
                    return '<span style=\'color: #ff0000\'>企</span>' + value;
                }">投资人</th>
            <th data-options="field:'amount',width:150">金额(元)</th>
            <th data-options="field:'stateDes',width:150">状态</th>
            <th data-options="field:'createDateDes', width:180">创建时间</th>
            <th data-options="field:'是否转让投资',width:100,
                formatter:function (value, row, index) {
                    if (row.transfer) {
                        return '是';
                    }
                    return '否';
                }">是否转让投资</th>
        </tr>
    </thead>
</table>