/**
 * Created by Sue on 2017/3/28.
 */
var roleDg;	//角色datagrid
var roleDag; //弹窗
var permissionDg;	//权限datagrid
var rolePerData;	//用户拥有的权限
var roleId;	//双击选中的role
$(function () {
    roleDg = $('#roleDg').datagrid({
        method: "get",
        url: ctx + '/admin/role/json',
        fit: true,
        width:500,
        fitColumns: true,
        border: false,
        idField: 'id',
        pagination: true,
        rownumbers: true,
        pageNumber: 1,
        pageSize: 10,
        pageList: [10, 20, 30, 40, 50],
        singleSelect: true,
        striped: true,
        columns: [[
            {field: 'id', title: 'id', hidden: true, sortable: true, width: 50},
            {field: 'name', title: '角色名称', sortable: true, width: 50},
            {field: 'roleCode', title: '角色编码', sortable: true, width: 50},
            {
                field: 'action', title: '操作',
                formatter: function (value, row, index) {
                    return '<a href="javascript:lookP(' + row.id + ')"><div class="fa fa-search" style="width:16px;height:16px" title="查看权限"></div></a>';
                }
            }
        ]],
        enableHeaderClickMenu: false,
        enableHeaderContextMenu: false,
        enableRowContextMenu: false,
        toolbar: '#roleTb'
    });

    roleDg.datagrid({
        onClickRow:function (index, row) {
            lookP(row.id);
        }
    });

    permissionDg = $('#permissionDg').treegrid({
        method: "get",
        url: ctx + '/admin/resource/json',
        fit: true,
        fitColumns: true,
        border: false,
        idField: 'id',
        treeField: 'name',
        parentField: 'parentId',
        iconCls: 'icon',
        animate: true,
        rownumbers: true,
        striped: true,
        singleSelect: false,//需设置
        columns: [[
            {field: 'ck', checkbox: true, hidden: true, width: 100},
            {field: 'id', title: 'id', hidden: true, width: 100},
            {field: 'name', title: '名称', width: 100},
            {field: 'description', title: '描述', width: 100, tooltip: true}
        ]],
        onClickRow: function (row) {
            // 级联选择
            permissionDg.treegrid('cascadeCheck', {
                id: row.id, //节点ID
                deepCascade: false //深度级联
            });
        },
        toolbar: '#permissionsTb'
    });
});

//查看权限
function lookP(roleId) {
    //清空勾选的权限
    if (rolePerData) {
        permissionDg.treegrid('unselectAll');
        rolePerData = [];//清空
    }
    //获取角色拥有权限
    $.ajax({
        async: false,
        type: 'get',
        url: ctx + "/admin/role/" + roleId + "/json",
        contentType: 'application/json;charset=utf-8',
        success: function (data) {
            data=eval("("+data+")");
            if (typeof data == 'object') {
                rolePerData = data;
                for (var i = 0, j = data.length; i < j; i++) {
                    permissionDg.treegrid('select', data[i]);
                }
            } else {
                $.messager.alert("数据异常");
            }
        }
    });
}

//保存修改权限
function save() {
    var row = roleDg.datagrid('getSelected');
    if (rowIsNull(row)) return;
    var roleId = row.id;
    parent.$.messager.confirm('提示', '确认要保存修改？', function (data) {
        if (data) {
            var newPermissionList = [];
            var data = permissionDg.treegrid('getSelections');
            for (var i = 0, j = data.length; i < j; i++) {
                var items = data[i].id;
                if (items == null) {
                    continue;
                }
                newPermissionList.push(items);
            }

            if (roleId == null) {
                parent.$.messager.show({title: "提示", msg: "请选择角色！", position: "bottomRight"});
                return;
            }
            $.ajax({
                async: false,
                type: 'POST',
                data: JSON.stringify(newPermissionList),
                contentType: 'application/json;charset=utf-8',
                url: ctx + "/admin/role/" + roleId + "/updatePermission",
                success: function (result) {
                    result = JSON.parse(result);
                    successTip(result, permissionDg);
                }
            });
        }
    });
}

//弹窗增加
function addRole() {
    roleDag = Keasy.dialog({
        dialogId: "createRole",
        title: '添加角色',
        width: 500,
        height: 350,
        href: ctx + '/admin/role/create',
        maximizable: true,
        modal: true
    });
}

//删除
function delRole() {
    var row = roleDg.datagrid('getSelected');
    if (rowIsNull(row)) return;
    parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function (data) {
        if (data) {
            Keasy.delete("/admin/role/delete/" + row.id, {}, function (result) {
                successTip(result, roleDg);
                roleDg.datagrid('unselectAll');
                permissionDg.treegrid('unselectAll');
            });
        }
    });
}

//修改
function updRole() {
    var row = roleDg.datagrid('getSelected');
    if (rowIsNull(row)) return;
    var rowIndex = row.id;
    roleDag = Keasy.dialog({
        dialogId: "updateRole",
        title: '修改角色',
        width: 500,
        height: 350,
        href: ctx + '/admin/role/update/' + rowIndex,
        maximizable: true,
        modal: true
    });
}

//恢复权限选择
function back() {
    var row = roleDg.datagrid('getSelected');
    lookP(row.id);
}

function doConfirmDialog(formData) {
    var action = formData.action;
    if ('create' == action) {
        Keasy.post("/admin/role/create", formData, function (data) {
            successTip(data, roleDg, roleDag);
        })
    } else if ('update' == action) {
        Keasy.put("/admin/role/update", formData, function (data) {
            successTip(data, roleDg, roleDag);
        })
    }
}