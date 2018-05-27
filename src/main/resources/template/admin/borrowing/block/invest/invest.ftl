[#--借款 投资总记录--]
<table class="easyui-datagrid" data-options="data:${investments},height:460">
    <thead>
        <tr>
            <th data-options="field:'id',width:100">投资编号</th>
            <th data-options="field:'investorLoginName',width:100,
                formatter:function (value, row, index) {
                    if (row.investorCorp) {
                        return '<span style=\'color: #ff0000\'>企</span>' + value;
                    }
                    return '<span style=\'color: #089eff\'>个</span>' + value;
                }">投资人</th>
            <th data-options="field:'amount',width:100">金额(元)</th>
            <th data-options="field:'stateDes',width:100">状态</th>
            <th data-options="field:'createDateDes', width:200">创建时间</th>
            <th data-options="field:'是否转让投资',width:100,
                formatter:function (value, row, index) {
                    if (row.transfer==null) {
                        return '否';
                    }
                    return '是';
                }">是否转让投资</th>
        </tr>
    </thead>
</table>
