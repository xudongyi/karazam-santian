/**
 * Created by suhao on 2017/6/1.
 */
var contentDatagrid;

$(function () {
    getTrees();
    contentDatagrid = $('#contentDatagrid').datagrid({
        method: "get",
        url: ctx + '/admin/posts/content/json',
        fit: true,
        border: false,
        idField: 'id',
        striped: true,
        pagination: true,
        pageNumber: 1,
        pageSize: 20,
        pageList: [10, 20, 30, 40, 50],
        singleSelect: true,
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            {field: 'title', title: '标题', width: 300},
            {field: 'categoryDes', title: '类别', width: 150},
            {field: 'viewCount', title: '访问量', width: 80},
            {field: 'expire', title: '过期时间', width: 150},
            {field: 'statusDes', title: '状态', width: 80},
            {field: 'createDate', title: '创建时间', width: 150}
        ]],
        toolbar: '#contentBar',
        onSelect:function(index, row){
            if (null == row) {
                return;
            }
            $('#publishContent').hide();
            $('#draftContent').hide();
            $('#garbageContent').hide();
            switch (row.status){
                case 'PUBLISH':{
                    $('#draftContent').show();
                    $('#garbageContent').show();
                    break;
                }
                case 'DRAFT':{
                    $('#publishContent').show();
                    $('#garbageContent').show();
                    break;
                }
                case 'GARBAGE':{
                    $('#publishContent').show();
                    $('#draftContent').show();
                    break;
                }
                default:{
                    break;
                }
            }
        }
    });
});

function updateContent() {
    var row = contentDatagrid.datagrid('getSelected');
    if (rowIsNull(row)) return;
    parent.addTab({
        title : "编辑[" + row.title + "]-" + row.id,
        border : false,
        closable : true,
        fit : true,
        content : '<iframe src="' + ctx + "/admin/posts/content/update?contentId="+row.id + '" frameborder="0" style="border:0;width:100%;height:99.5%;"></iframe>'
    });
}

function deleteContent() {
    var row = contentDatagrid.datagrid('getSelected');
    if (rowIsNull(row)) return;
    parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function (data) {
        if (data) {
            Keasy.delete("/admin/posts/content/delete/" + row.id, {}, function (result) {
                successTip(result, contentDatagrid);
            });
        }
    });
}

function publishContent() {
    var row = contentDatagrid.datagrid('getSelected');
    if (rowIsNull(row)) return;
    parent.$.messager.confirm('提示', '您确定要发布该内容？', function (data) {
        if (data) {
            Keasy.put("/admin/posts/content/status/" + row.id, {status:'PUBLISH'}, function (result) {
                successTip(result, contentDatagrid);
            });
        }
    });
}

function draftContent() {
    var row = contentDatagrid.datagrid('getSelected');
    if (rowIsNull(row)) return;
    parent.$.messager.confirm('提示', '您确定要将该内容置为草稿？', function (data) {
        if (data) {
            Keasy.put("/admin/posts/content/status/" + row.id, {status:'DRAFT'}, function (result) {
                successTip(result, contentDatagrid);
            });
        }
    });
}

function garbageContent() {
    var row = contentDatagrid.datagrid('getSelected');
    if (rowIsNull(row)) return;
    parent.$.messager.confirm('提示', '您确定要将该内容回收？', function (data) {
        if (data) {
            Keasy.put("/admin/posts/content/status/" + row.id, {status:'GARBAGE'}, function (result) {
                successTip(result, contentDatagrid);
            });
        }
    });
}

function queryContents() {
    var obj = $("#searchFrom").serializeObject();
    contentDatagrid.datagrid('load', obj);
}

function getTrees() {
    taxonomySelect = $('#categoryId').combotree({
        method: 'GET',
        url: ctx + '/admin/posts/taxonomy/select.json?type=category',
        idField: 'id',
        textFiled: 'title',
        parentField: 'parentId',
        animate: true
    });
}

window.top.reloadContentsDatagrid = function(){
    contentDatagrid.datagrid("reload");
};