/**
 * Created by suhao on 2017/3/30.
 */
var dg;
var dialog;
dg = $('#dg').datagrid({
    method: "get",
    url: ctx + '/admin/borrowing/list.json',
    fit: true,
    fitColumns: false,
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
        {field: 'id', title: '借款编号', sortable: true},
        {field: 'title', title: '标题', width: 100},
        {field: 'borrowerName', title: '借款人名称', width: 100},
        {field: 'borrowerMobile', title: '借款人手机号', width: 100},
        {field: 'progressDes', title: '进度', width: 100},
        {field: 'stateDes', title: '状态', width: 100},
        {field: 'typeDes', title: '类型', width: 100},
        {field: 'amount', title: '金额(元)', width: 100},
        /*{field: 'period', title: '期限', width: 100},*/
        {field: 'period', title: '期限', width: 100,
            formatter: function(value,row,index){
                return row.period + row.periodUnitDes;
            }
        },
        {field: 'interestRate', title: '利率(%)',width:100},
        {field: 'rewardInterestRate', title: '奖励利率(%)',width:100},
        {field: 'interestMethodDes', title: '计息方式',width:150},
        {field: 'aheadDes', title: '提前还款',width:100},
        {field: 'interestBeginDate', title: '计息时间',width:100},
        {field: 'repaymentFinishDateDes', title: '完成时间',width:100},
        {field: 'createDate', title: '创建日期',width:200}
    ]],
    enableHeaderClickMenu: true,
    enableHeaderContextMenu: true,
    enableRowContextMenu: false,
    toolbar: '#tb',
    onSelect:function(index, row){
        if (null == row) {
            return;
        }
        $(".update-button").show();
        $(".inquiry-button").hide();
        $(".approval-button").hide();
        $(".lending-button").hide();
        switch (row.progress){
            case 'INQUIRING':{
                $(".inquiry-button").show();
                $(".update-button").show();

                break;
            }
            case 'APPROVAL':{
                $(".approval-button").show();
                $(".update-button").show();
                break;
            }
            case 'INVESTING':{
                // $(".update-button").hide();
                break;
            }
            case 'LENDING':{
                $(".lending-button").show();
                // $(".update-button").hide();
                break;
            }
            case 'REPAYING':{
                // $(".update-button").hide();
                break;
            }
            case 'COMPLETED':{
                // $(".update-button").hide();
                break;
            }
            default:{
                break;
            }
        }
    }
});

//查询
function query() {
    var obj = $("#searchForm").serializeObject();
    dg.datagrid('load', obj);
}

//弹窗 查看
function view() {
    var row = dg.datagrid('getSelected');
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
    dg.datagrid('selectRecord', row.id);
}

//弹窗 复制
function copy() {
    var row = dg.datagrid('getSelected');
    if (rowIsNull(row)) return;
    dialog = $("#dialog").dialog({
        title: '申请',
        width: 1000,
        height: 600,
        href: ctx + '/admin/borrowing/apply?copy=1&id=' + row.id,
        maximizable: true,
        modal: true,
        buttons: [{
            text: '预览',
            handler: function () {
                $('#opinion').textbox('setValue', 'test');
                var $borrowingForm = $("#borrowingForm");
                var validate = $("#borrowingForm").form('validate');
                if (!validate) {
                    $.messager.alert("提示","请完善数据");
                    return;
                }
                $('#opinion').textbox('setValue', '');
                var formData = Keasy.serializeObject($borrowingForm);
                preview(formData);
            }
        }, {
            text: '提交',
            handler: function () {
                $("#borrowingForm").submit();
            }
        }, {
            text: '取消',
            handler: function () {
                dialog.panel('close');
            }
        }]
    });
    dg.datagrid('selectRecord', row.id);
}

//弹窗 申请
function apply() {
    dialog = $("#dialog").dialog({
        title: '申请',
        width: 1000,
        height: 600,
        href: ctx + '/admin/borrowing/apply',
        maximizable: true,
        modal: true,
        buttons: [{
            text: '预览',
            handler: function () {
                $('#opinion').textbox('setValue', 'test');
                var $borrowingForm = $("#borrowingForm");
                var validate = $("#borrowingForm").form('validate');
                if (!validate) {
                    $.messager.alert("提示","请完善数据");
                    return;
                }
                $('#opinion').textbox('setValue', '');
                var formData = Keasy.serializeObject($borrowingForm);
                preview(formData);
            }
        }, {
            text: '提交',
            handler: function () {
                $("#borrowingForm").submit();
            }
        }, {
            text: '取消',
            handler: function () {
                dialog.panel('close');
            }
        }]
    });
}

//弹窗 修改
function update() {
    var row = dg.datagrid('getSelected');
    if (rowIsNull(row)) return;
    dialog = $("#dialog").dialog({
        title: '修改',
        width: 1000,
        height: 600,
        href: ctx + '/admin/borrowing/update/' + row.id,
        maximizable: true,
        modal: true,
        buttons: [{
            text: '预览',
            handler: function () {
                $('#opinion').textbox('setValue', 'test');
                var $borrowingForm = $("#borrowingForm");
                var validate = $("#borrowingForm").form('validate');
                if (!validate) {
                    $.messager.alert("提示","请完善数据");
                    return;
                }
                $('#opinion').textbox('setValue', '');
                var formData = Keasy.serializeObject($borrowingForm);
                preview(formData);
            }
        }, {
            text: '提交',
            handler: function () {
                $('#borrowingForm').submit();
            }
        }, {
            text: '取消',
            handler: function () {
                dialog.panel('close');
            }
        }]
    });
    dg.datagrid('selectRecord', row.id);
}

//弹窗 调查
function inquiry() {
    var row = dg.datagrid('getSelected');
    if (rowIsNull(row)) return;
    dialog = $("#dialog").dialog({
        title: '调查',
        width: 1000,
        height: 600,
        href: ctx + '/admin/borrowing/inquiry/' + row.id,
        maximizable: true,
        modal: true,
        buttons: [{
            text: '预览',
            handler: function () {
                $('#opinion').textbox('setValue', 'test');
                var $borrowingForm = $("#borrowingForm");
                var validate = $("#borrowingForm").form('validate');
                if (!validate) {
                    $.messager.alert("提示","请完善数据");
                    return;
                }
                $('#opinion').textbox('setValue', '');
                var formData = Keasy.serializeObject($borrowingForm);
                preview(formData);
            }
        }, {
            text: '提交',
            handler: function () {
                $('#borrowingForm').submit();
            }
        }, {
            text: '取消',
            handler: function () {
                dialog.panel('close');
            }
        }]
    });
    dg.datagrid('selectRecord', row.id);
}

//弹窗 审批
function approval() {
    var row = dg.datagrid('getSelected');
    if (rowIsNull(row)) return;
    dialog = $("#dialog").dialog({
        title: '审核',
        width: 1000,
        height: 600,
        href: ctx + '/admin/borrowing/approval/' + row.id,
        maximizable: true,
        modal: true,
        buttons: [{
            text: '预览',
            handler: function () {
                $('#opinion').textbox('setValue', 'test');
                var $borrowingForm = $("#borrowingForm");
                var validate = $("#borrowingForm").form('validate');
                if (!validate) {
                    $.messager.alert("提示","请完善数据");
                    return;
                }
                $('#opinion').textbox('setValue', '');
                var formData = Keasy.serializeObject($borrowingForm);
                preview(formData);
            }
        }, {
            text: '提交',
            handler: function () {
                $('#borrowingForm').submit();
            }
        }, {
            text: '取消',
            handler: function () {
                dialog.panel('close');
            }
        }]
    });
    dg.datagrid('selectRecord', row.id);
}

//弹窗 出借
function lending() {
    var row = dg.datagrid('getSelected');
    if (rowIsNull(row)) return;
    dialog = $("#dialog").dialog({
        title: '出借',
        width: 1000,
        height: 600,
        href: ctx + '/admin/borrowing/lending/' + row.id,
        maximizable: true,
        modal: true,
        buttons: [{
            text: '预览',
            handler: function () {
                $('#opinion').textbox('setValue', 'test');
                var $borrowingForm = $("#borrowingForm");
                var validate = $("#borrowingForm").form('validate');
                if (!validate) {
                    $.messager.alert("提示","请完善数据");
                    return;
                }
                $('#opinion').textbox('setValue', '');
                var formData = Keasy.serializeObject($borrowingForm);
                preview(formData);
            }
        }, {
            text: '提交',
            handler: function () {
                $('#borrowingForm').submit();
            }
        }, {
            text: '取消',
            handler: function () {
                dialog.panel('close');
            }
        }]
    });
    dg.datagrid('selectRecord', row.id);
}

function preview(data) {
    var tempForm = document.createElement("form");
    tempForm.action = ctx + '/investment/preview';
    tempForm.target = "_blank";
    tempForm.method = "post";
    tempForm.style.display = "none";
    $.each(data, function (key, value) {
        var opt = document.createElement("input");
        opt.name = key;
        opt.value = value;
        tempForm.appendChild(opt);
    });
    document.body.appendChild(tempForm);
    tempForm.submit();
}

function getRemarks(borrowingId) {
    Keasy.get('/admin/borrowing/remarks.json', {borrowingId: borrowingId}, function (result) {
        if (result.success) {
            $.each(result.data, function (i, v) {
                if (v != '') {
                    var markName = i.split('-');
                    console.log(markName[0]);
                    $('#' + i).val(v);
                    $('#' + markName[0]).textbox('textbox').parent().addClass('textbox-invalid');
                    $('#' + i + '-show').text(v);
                    $('#' + i + '-add').hide();
                    $('#' + i + '-ok').hide();
                    $('#' + i + '-update').show();
                    $('#' + i + '-del').show();
                } else {
                    $('#' + i + '-add').show();
                    $('#' + i + '-ok').hide();
                    $('#' + i + '-update').hide();
                    $('#' + i + '-del').hide();
                }
            });
        }
    });
}

function addRemark(index) {
    $('#' + index + '-remark-ok').show();
    $('#' + index + '-remark-update').hide();
    $('#' + index + '-remark').show();
    $('#' + index + '-remark-show').hide();
    $('#' + index + '-remark-add').hide();
    $('#' + index + '-remark-del').hide();
}

function updateRemark(index) {
    $('#' + index + '-remark-ok').show();
    $('#' + index + '-remark-update').hide();
    $('#' + index + '-remark').show();
    $('#' + index + '-remark-show').hide();
    $('#' + index + '-remark-add').hide();
    $('#' + index + '-remark-del').hide();
}

function okRemark(index) {
    var input = $('#' + index + '-remark').val();
    $('#' + index + '-remark-ok').hide();
    $('#' + index + '-remark-update').show();
    $('#' + index + '-remark').hide();
    $('#' + index + '-remark-show').show().text(input);
    $('#' + index + '-remark-add').hide();
    $('#' + index + '-remark-del').show();

    if (input == '') {
        $('#' + index + '-remark-add').show();
        $('#' + index + '-remark-del').hide();
        $('#' + index + '-remark-update').hide();
    }
}

function delRemark(index) {
    $('#' + index + '-remark').val('');
    $('#' + index + '-remark-ok').hide();
    $('#' + index + '-remark-update').hide();
    $('#' + index + '-remark').hide();
    $('#' + index + '-remark-show').hide();
    $('#' + index + '-remark-add').show();
    $('#' + index + '-remark-del').hide();
}

function hideRemark() {
    $("[id*='-remark']").hide();
}