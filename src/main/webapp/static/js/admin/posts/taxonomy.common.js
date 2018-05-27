/**
 * Created by Sue on 2017/5/29.
 */

var taxonomyDatagrid;
var taxonomyDialog;
var taxonomySelect;

var formUrl = {
    create : {
        url : ctx + '/admin/posts/taxonomy/create'
    },
    update : {
        url : ctx + '/admin/posts/taxonomy/update'
    }
};

var actionUrl = formUrl.create.url;

$(function () {
    // 表单提交
    taxonomyFormSubmit();
    getTrees();

    $("input", $("#title").next("span")).blur(function(){
        var title = $(this).val();
        var taxonomyId = $('#taxonomyId').val();
        if (title != '') {
            Keasy.get(ctx + '/admin/posts/taxonomy/translate', {type:type.toUpperCase(), title:title, taxonomyId:taxonomyId}, function (result) {
                $('#slug').textbox('setValue', result.data);
            })
        }

    });

    $("input", $("#slug").next("span")).change(function(){
        var slug = $(this).val();
        var taxonomyId = $('#taxonomyId').val();
        if (slug != '') {
            Keasy.get(ctx + '/admin/posts/taxonomy/isExistSlug', {type:type.toUpperCase(), slug:slug, taxonomyId:taxonomyId}, function (result) {
                $('#slug').textbox('setValue', result.data);
            })
        }

    });

    taxonomyDatagrid = $('#taxonomyDatagrid').treegrid({
        method: "get",
        url: ctx + '/admin/posts/taxonomy/json?type=' + type,
        fit: true,
        fitColumns: true,
        border: false,
        idField: 'id',
        treeField: 'title',
        iconCls: 'icon',
        pageSize: 20,
        pageList: [10, 20, 30, 40, 50],
        pagination:true,
        animate: true,
        singleSelect: true,
        striped: true,
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            {field: 'title', title: '名称', width: 100},
            {field: 'text', title: '描述', width: 100},
            {field: 'contentCount', title: '总数', width: 100},
            {field: 'slug', title: 'slug', width: 100}
        ]],
        toolbar: '#taxonomyBar'
    });
});


//修改
function updateTaxonomy() {
    var row = taxonomyDatagrid.datagrid('getSelected');
    if (rowIsNull(row)) return;
    Keasy.get(ctx + '/admin/posts/taxonomy/update', {taxonomyId:row.id}, function (result) {
        $('#taxonomyForm').find('input[name=action]').val('update');
        var action = $('#taxonomyForm').find('input[name=action]').val();
        actionUrl = formUrl[action].url;
        $('#taxonomyForm').form('load', result.data);
        taxonomyFormSubmit();
    });
}

//删除
function deltetTaxonomy() {
    var row = taxonomyDatagrid.datagrid('getSelected');
    if (rowIsNull(row)) return;
    parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function (data) {
        if (data) {
            Keasy.delete("/admin/posts/taxonomy/delete/" + row.id, {}, function (result) {
                if (result.success) {
                    parent.$.messager.show({title: "提示", msg: result.message, position: "bottomRight"});
                    taxonomyDatagrid.treegrid('unselectAll');
                    taxonomyDatagrid.treegrid('reload');
                } else {
                    $.messager.alert('提示', result.message,'error');
                }
            });
        }
    });
}

//设置
function taxonomySetting() {
    var row = taxonomyDatagrid.datagrid('getSelected');
    if (rowIsNull(row)) return;
    taxonomyDialog = Keasy.dialog({
        dialogId: "taxonomySetting",
        title: '设置',
        width: 600,
        height: 340,
        href: ctx + '/admin/posts/taxonomy/setting?taxonomyId=' + row.id,
        maximizable: true,
        modal: true
    });
}

function doConfirmDialog(formData) {
    Keasy.post("/admin/posts/taxonomy/setting", formData, function (data) {
        successTip(data, taxonomyDatagrid, taxonomyDialog);
    });
}

function taxonomyFormSubmit() {
    $('#taxonomyForm').form({
        ajax:true,
        url: actionUrl,
        onSubmit: function () {
            return beforeSubmit.call(this);
        },
        success: function (data) {
            afterSubmit(this, data, taxonomyDatagrid);
            taxonomyDatagrid.treegrid('unselectAll');
            taxonomyDatagrid.treegrid('reload');
            $('#taxonomyForm').find('input[name=type]').val(type);
            $('#taxonomyForm').find('input[name=action]').val('create');
            $('#taxonomyForm').find('input[name=display]').val('true');
            getTrees();
        }
    });
}

function getTrees() {
    taxonomySelect = $('#parentId').combotree({
        method: 'GET',
        url: ctx + '/admin/posts/taxonomy/select.json?type=' + type,
        idField: 'id',
        textFiled: 'title',
        parentField: 'parentId',
        animate: true
    });
}