/**
 * Created by suhao on 2017/4/5.
 */
var datagrid;
var dialog;
datagrid = $('#datagrid').datagrid({
    toolbar: '#tb222',
    method: "get",
    url: ctx + '/admin/postloan/repayment/list.json',
    queryParams: {progress: 'REPAYING'},
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
        {field: 'id', title: '借款编号', sortable: true, hidden: false},
        {field: 'title', title: '标题', sortable: true, width:100},
        {field: 'progressDes', title: '进度', sortable: false, width:100},
        {field: 'stateDes', title: '状态', sortable: false, width:100},
        {field: 'typeDes', title: '类型', sortable: false, width:100},
        {field: 'amount', title: '金额(元)', sortable: true, width:100},
        {field: 'repaymentMethodDes', title: '还款方式', sortable: false, width:200},
        {field: 'period', title: '期限', width: 100,
            formatter: function(value,row,index){
                return row.period + row.periodUnitDes;
            }
        },
        {field: 'interestRate', title: '利率(%)',width:100, sortable: false},
        {field: 'rewardInterestRate', title: '奖励利率(%)',width:100, sortable: false},
        {field: 'repaymentProgress', title: '还款进度',width:100, sortable: false},
        {field: 'nextPayDate', title: '当期应还款日期', width:200, sortable: false},
        {field: 'aheadDes', title: '提前还款',width:100, sortable: false},
        {field: 'lendingDate', title: '计息时间',width:100, sortable: false},
        {field: 'repaymentFinishDateDes', title: '完成时间',width:100, sortable: false},
        {field: 'createDate', title: '创建日期', width:200, sortable: false}
    ]],
    enableHeaderClickMenu: true,
    enableHeaderContextMenu: true,
    enableRowContextMenu: false,
    toolbar: '#tb',
    onSelect:function(index, row){
        if(row.progress == 'REPAYING'){
            $(".aheadRepayment-button").show();
        }else {
            $(".aheadRepayment-button").hide();
        }
    }
});

//查询
function query() {
    var obj = $("#searchFrom").serializeObject();
    datagrid.datagrid('load', obj);
}

//弹窗 查看
function view() {
    var row = datagrid.datagrid('getSelected');
    if (rowIsNull(row)) return;
    dialog = $("#dialog").dialog({
        title: '查看',
        width: 1000,
        height: 600,
        href: ctx + '/admin/borrowing/view/' + row.id,
        maximizable: true,
        modal: true,
        buttons: [{
            text: '取消',
            handler: function () {
                dialog.panel('close');
            }
        }]
    });
}

//新标签 还款
function repayment() {
    var row = datagrid.datagrid('getSelected');
    if (rowIsNull(row)) return;
    parent.window.mainTabs.addModule('借款[' + row.id + "]还款", ctx + '/admin/postloan/repayment/repayment_list?borrowingId=' + row.id, "fa fa-align-left", true);
}

//新标签 还款计划
function repaymentPlan() {
    var row = datagrid.datagrid('getSelected');
    if (rowIsNull(row)) return;
    parent.window.mainTabs.addModule('借款[' + row.id + "]还款计划", ctx + '/admin/postloan/repayment/repaymentPlan_list?borrowingId=' + row.id, "fa fa-align-left", true);
}

//新标签 提前还款
function aheadRepayment() {
    var row = datagrid.datagrid('getSelected');
    if (rowIsNull(row)) return;
    //弹窗 提前还款校验
    Keasy.post("/admin/postloan/repayment/aheadRepaymentCheck",
        {borrowingId: row.id},
        function (_data) {
            // successTip(_data, datagrid);
            if(_data.success){
                parent.window.mainTabs.addModule("提前还款", ctx + '/admin/postloan/repayment/repayment_ahead_list?borrowingId=' + row.id, "fa fa-align-left", true);
            }
//                    location.reload();
        },
        function(_data){
            successTip(_data, datagrid);
            location.reload();
        }
    );
    // $.ajax({
    //      url: "/admin/postloan/repayment/aheadRepaymentCheck",
    //      data: {
    //         borrowingId: row.id
    //      },
    //      // type: "post",
    //      // dataType: "json",
    //      // cache: false,
    //      beforeSend: function(request, settings) {
    //         console.log(1);
    //      },
    //      success: function(result) {
    //          console.log(result);
    //          if (result.success) {
    //             parent.window.mainpage.mainTabs.addModule("提前还款", '${ctx}/mng/postloan/repayment/repayment_ahead_list?borrowingId=' + row.id, "icon-hamburg-folder", true, true);
    //          /*$.messager.confirm("确认", "提前还款金额"+result.data.aheadRepaymentAmount+"元，确认还款？", function (r) {
    //          if (r) {
    //          aheadRepayment();
    //          return true;
    //          }
    //          });*/
    //          } else {
    //             parent.$.messager.show({title: "提示", msg: result.message, position: "bottomRight"});
    //          }
    //      },
    //      error: function() {
    //         parent.$.messager.show({title: "提示", msg: "系统错误", position: "bottomRight"});
    //      }
    //  });
}

function hideRemark() {
    $("[id*='-remark']").hide();
}