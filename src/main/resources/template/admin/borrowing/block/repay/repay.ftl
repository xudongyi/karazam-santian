[#--借款 还款--]
<table class="easyui-datagrid" data-options="data:${repayments}, height:460">
    <thead>
    <tr>
        <th data-options="field:'id',width:70">还款编号</th>
        <th data-options="field:'borrowing',width:70">借款编号</th>
        <th data-options="field:'borrowerName',width:100,
                formatter:function (value, row, index) {
                    if (row.borrowerType=='GENERAL') {
                        return '<span style=\'color: #089eff\'>个</span>' + value;
                    }
                    return '<span style=\'color: #ff0000\'>企</span>' + value;
                }">借款人</th>
        <th data-options="field:'period',width:100">期数</th>
        <th data-options="field:'capital',width:100,
                formatter:function (value, row, index) {
                    if (row.state=='REPAYING' && row.capital==0) {
                        return '-';
                }
                return row.capital;
        }"">本金(元)</th>
        <th data-options="field:'interest',width:100,
                formatter:function (value, row, index) {
                    if (row.state=='REPAYING' && row.interest==0) {
                        return '-';
                }
                return row.interest;
        }"">利息(元)</th>
        <th data-options="field:'payDateDes',width:100">计划还款日期</th>
        <th data-options="field:'paidAmount',width:100,
                formatter:function (value, row, index) {
                    if (row.state=='REPAYING' && row.paidAmount==0) {
                        return '-';
                    }
                    return row.paidAmount;
                }"">实际还款金额(元)</th>
        <th data-options="field:'paidDateDes',width:100">实际还款日期</th>
        <th data-options="field:'overdueInterest',width:100,
                formatter:function (value, row, index) {
                    if (row.state=='REPAYING' && row.overdueInterest==0) {
                        return '-';
                }
                return row.overdueInterest;
        }"">已付逾期利息</th>
        <th data-options="field:'seriousOverdueInterest',width:100,
                formatter:function (value, row, index) {
                    if (row.state=='REPAYING' && row.seriousOverdueInterest==0) {
                        return '-';
                }
                return row.seriousOverdueInterest;
        }"">已付严重逾期利息</th>
        <th data-options="field:'stateDes',width:100">状态</th>
        <th data-options="field:'advanceDes',width:100">提前还款</th>
        <th data-options="field:'createDateDes', width:150">创建时间</th>
    </tr>
    </thead>
</table>