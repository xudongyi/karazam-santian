/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
var permissionDg;
var resourceDag;
var menuDg;
var menuId = 0;
var parentPermId = 1;
$(function () {
    menuDg = $('#menuDg').treegrid({
        method: "get",
        url: ctx + '/admin/resource/menu/json',
        fit: true,
        fitColumns: true,
        border: false,
        idField: 'id',
        treeField: 'name',
        parentField: 'parentId',
        iconCls: 'icon',
        animate: true,
        rownumbers: true,
        singleSelect: true,
        striped: true,
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            {field: 'name', title: '名称', width: 100}
        ]],
        enableHeaderClickMenu: false,
        enableHeaderContextMenu: false,
        enableRowContextMenu: false,
        dataPlain: true,
        onClickRow: function (rowData) {
            menuId = rowData.id;
            if (menuId == 1) {
                parent.$.messager.show({ title : "提示",msg: "不能选择根节点！", position: "bottomRight" });
                menuDg.treegrid("unselectAll");
                return;
            }
            parentPermId = rowData.id;
            permissionDg.datagrid('reload', {pid: menuId});
        },
        toolbar: '#menuTb'
    });

    permissionDg = $('#permissionDg').datagrid({
        method: "get",
        url: ctx + '/admin/resource/ope/json',
        fit: true,
        fitColumns: true,
        border: false,
        idField: 'id',
        treeField: 'name',
        parentField: 'parentId',
        iconCls: 'icon',
        animate: true,
        rownumbers: true,
        singleSelect: true,
        striped: true,
        columns: [[
            {field: 'id', title: 'id', hidden: true, width: 100},
            {field: 'name', title: '名称', width: 100},
            {field: 'permission', title: '权限编码', width: 100},
            {field: 'sort', title: '排序'},
            {field: 'description', title: '描述', width: 100}
        ]],
        toolbar: '#permissionTb',
        dataPlain: true
    });

});

//弹窗增加
function addMenu() {
    //父级权限
    var row = menuDg.treegrid('getSelected');
    if (row) {
        parentPermId = row.id;
    }

    resourceDag = $('#resourceDag').dialog({
        title: '添加菜单',
        width: 500,
        height: 420,
        closed: false,
        cache: false,
        maximizable: true,
        resizable: true,
        href: ctx + '/admin/resource/menu/create',
        modal: true,
        buttons: [{
            text: '确认',
            handler: function () {
                $("#mainform").submit();
            }
        }, {
            text: '取消',
            handler: function () {
                resourceDag.panel('close');
            }
        }]
    });
}

//删除
function delMenu() {
    var row = menuDg.treegrid('getSelected');
    if (rowIsNull(row)) return;
    parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function (data) {
        if (data) {
            $.ajax({
                type: 'get',
                url: ctx + "/admin/resource/delete/" + row.id,
                success: function (data) {
                    if (successTip(JSON.parse(data), menuDg)) {
                        menuDg.treegrid('reload');
                    }
                }
            });
            //dg.datagrid('reload'); //grid移除一行,不需要再刷新
        }
    });

}

//修改
function updMenu() {
    var row = menuDg.treegrid('getSelected');
    if (rowIsNull(row)) return;
    //父级权限
    parentPermId = row.parentId;
    resourceDag = $("#resourceDag").dialog({
        title: '修改菜单',
        width: 500,
        height: 420,
        href: ctx + '/admin/resource/menu/update/' + row.id,
        maximizable: true,
        modal: true,
        buttons: [{
            text: '确认',
            handler: function () {
                $("#mainform").submit();
            }
        }, {
            text: '取消',
            handler: function () {
                resourceDag.panel('close');
            }
        }]
    });

}

//弹窗增加
function addPermission() {
    var row = menuDg.treegrid('getSelected');
    if (rowIsNull(row)) return;
    resourceDag = $('#resourceDag').dialog({
        title: '添加权限',
        width: 500,
        height: 380,
        closed: false,
        cache: false,
        maximizable: true,
        resizable: true,
        href: ctx + '/admin/resource/create',
        modal: true,
        buttons: [{
            text: '确认',
            handler: function () {
                $("#mainform").submit();
            }
        }, {
            text: '取消',
            handler: function () {
                resourceDag.panel('close');
            }
        }]
    });
}

//删除
function delPermission() {
    var row = permissionDg.datagrid('getSelected');
    if (rowIsNull(row)) return;
    parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function (data) {
        if (data) {
            $.ajax({
                type: 'get',
                url: ctx + "/admin/resource/delete/" + row.id,
                success: function (data) {
                    successTip(JSON.parse(data), permissionDg);
                }
            });
            //dg.datagrid('reload'); //grid移除一行,不需要再刷新
        }
    });

}

//修改
function updPermission() {
    var row = permissionDg.datagrid('getSelected');
    if (rowIsNull(row)) return;
    resourceDag = $("#resourceDag").dialog({
        title: '修改权限',
        width: 500,
        height: 380,
        href: ctx + '/admin/resource/update/' + row.id,
        maximizable: true,
        modal: true,
        buttons: [{
            text: '确认',
            handler: function () {
                $("#mainform").submit();
            }
        }, {
            text: '取消',
            handler: function () {
                resourceDag.panel('close');
            }
        }]
    });

}