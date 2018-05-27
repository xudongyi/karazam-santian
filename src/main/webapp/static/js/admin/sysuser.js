/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
var sysUserDatagrid;
var sysUserDialog;
$(function () {
    sysUserDatagrid = $('#sysUserDatagrid').datagrid({
        method: "get",
        url: ctx + '/admin/sysuser/json',
        fit: true,
        fitColumns: true,
        border: false,
        idField: 'id',
        striped: true,
        pagination: true,
        rownumbers: true,
        pageNumber: 1,
        pageSize: 20,
        pageList: [10, 20, 30, 40, 50],
        singleSelect: true,
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            {field: 'name', title: '昵称', width: 100},
            {field: 'loginName', title: '帐号', width: 100},
            {field: 'mobile', title: '手机', width: 100},
            {field: 'genderDisplay', title: '性别', width: 100},
            {field: 'email', title: 'email', width: 150},
            {field: 'loginCount', title: '登录次数', width: 100},
            {field: 'previousVisit', title: '上一次登录', width: 150},
            {field: 'statusStr', title: '状态', width: 100}
        ]],
        toolbar: '#sysUserToolBar'
    });
});

//弹窗增加
function createUser() {
    sysUserDialog = Keasy.dialog({
        dialogId: "createUser",
        title: '添加用户',
        width: 600,
        height: 480,
        href: ctx + '/admin/sysuser/create',
        maximizable: true,
        modal: true
    });
}

//删除
function deleteUser() {
    var row = sysUserDatagrid.datagrid('getSelected');
    if (rowIsNull(row)) return;
    parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function (data) {
        if (data) {
            Keasy.delete("/admin/sysuser/delete/" + row.id, {}, function (result) {
                successTip(result, sysUserDatagrid);
            });
        }
    });
}

//弹窗修改
function updateUser() {
    var row = sysUserDatagrid.datagrid('getSelected');
    if (rowIsNull(row)) return;
    sysUserDialog = Keasy.dialog({
        dialogId: "updateUser",
        title: '修改用户',
        width: 600,
        height: 480,
        href: ctx + '/admin/sysuser/update/' + row.id,
        maximizable: true,
        modal: true
    });
}

//用户角色弹窗
function addUserRoles() {
    var row = sysUserDatagrid.datagrid('getSelected');
    if (rowIsNull(row)) return;
    $.ajaxSetup({type: 'GET'});
    sysUserDialog = $("#sysUserDialog").dialog({
        title: '关联用户角色',
        width: 580,
        height: 350,
        href: ctx + '/admin/sysuser/' + row.id + '/userRole',
        maximizable: true,
        modal: true,
        buttons: [{
            text: '确认',
            handler: function () {
                saveUserRole();
                sysUserDialog.panel('close');
            }
        }, {
            text: '取消',
            handler: function () {
                sysUserDialog.panel('close');
            }
        }]
    });
}

//查看
function look() {
    var row = sysUserDatagrid.datagrid('getSelected');
    if (rowIsNull(row)) return;
    d = $("#dlg").dialog({
        title: '修改用户',
        width: 380,
        height: 340,
        href: ctx + '/admin/sysuser/update/' + row.id,
        maximizable: true,
        modal: true,
        buttons: [{
            text: '取消',
            handler: function () {
                d.panel('close');
            }
        }]
    });
}

//创建查询对象并查询
function querySysUser() {
    var obj = $("#searchFrom").serializeObject();
    sysUserDatagrid.datagrid('load', obj);
}

function doConfirmDialog(formData) {
    var action = formData.action;
    if ('create' == action) {
        Keasy.post("/admin/sysuser/create", formData, function (data) {
            successTip(data, sysUserDatagrid, sysUserDialog);
        })
    } else if ('update' == action) {
        Keasy.put("/admin/sysuser/update", formData, function (data) {
            successTip(data, sysUserDatagrid, sysUserDialog);
        })
    }
}