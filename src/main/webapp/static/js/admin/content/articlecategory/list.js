var datagrid;
var dialog;
datagrid = $('#datagrid').datagrid({
    method: "get",
    url: ctx+'/admin/article_category/list',
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
        {field: 'id', title: 'id', hidden: true, width:120},
        {field: 'name', title: '名称', sortable: true, width:120,
            formatter:function (val,row,index) {
               return  "<a href='#' onclick='nextCategory("+JSON.stringify(row)+")'>" + val + "</a>" ;
            }
        },
        {field: 'alias', title: '别名', sortable: true, width:120},
        {field: 'template', title: '模板', sortable: true, width:120},
        {field: 'builtin', title: '是否内置', sortable: true, width:120,
            formatter:function (val,row,index) {
                if(val){
                    return "是";
                }else{
                    return "否";
                }
            }
        },
        {field: 'sort', title: '排序', sortable: true, width:120},
        {field: 'createDate', title: '创建时间', sortable: true, width:120}
    ]],
    headerContextMenu: [
        {
            text: "冻结该列", disabled: function (e, field) {
            return datagrid.datagrid("getColumnFields", true).contains(field);
        },
            handler: function (e, field) {
                datagrid.datagrid("freezeColumn", field);
            }
        },
        {
            text: "取消冻结该列", disabled: function (e, field) {
            return datagrid.datagrid("getColumnFields", false).contains(field);
        },
            handler: function (e, field) {
                datagrid.datagrid("unfreezeColumn", field);
            }
        }
    ],
    enableHeaderClickMenu: true,
    enableHeaderContextMenu: true,
    enableRowContextMenu: false,
    toolbar: '#tb'
});

//弹窗输入新增文章分类
function create() {
    dialog=Keasy.dialog({
        title: '新增分类',
        width: 550,
        height: 400,
        href: ctx+'/admin/article_category/addEdit',
        maximizable: true,
        modal: true
    });
}
//弹窗输入新增用户信息
function update() {
    var row = datagrid.datagrid('getSelected');

    if(rowIsNull(row)){
        $.messager.alert("提示","请选择一行数据");
        return;
    }
    dialog=Keasy.dialog({
        title: '修改分类',
        width: 550,
        height: 400,
        href: ctx+'/admin/article_category/updateEdit/'+row.id,
        maximizable: true,
        modal: true
    });
}
function deleteCategory() {
    var row = datagrid.datagrid('getSelected');
    //var rows = { "userId": row.userId, "userInfoId": row.userInfoId };
    if(rowIsNull(row)){
        $.messager.alert("提示","请选择一行数据");
        return;
    }
    $.messager.confirm('确认对话框', '您想要删除该文章分类吗？', function(r){
        if (r){
            Keasy.delete("/admin/article_category/delete/"+row.id,{},function (data) {
                successTip(data, datagrid);
            })
        }
    });
}

function nextCategory(row){
    parent.addTab({
        title : row.name,
        border : false,
        closable : true,
        fit : true,
        content : '<iframe src="' + ctx + "/admin/article_category/pageListByIdDispatcher/"+row.id + '" frameborder="0" style="border:0;width:100%;height:99.5%;"></iframe>'
    });
}
//查询
function query() {
    var obj = $("#searchFrom").serializeObject();
    datagrid.datagrid('load', obj);
}

function doConfirmDialog(formData) {
    if(formData.action=='add'){
        Keasy.post("/admin/article_category/save", formData, function (data) {
            successTip(data, datagrid,dialog);
        })
    }
    if(formData.action=='update'){
        Keasy.post("/admin/article_category/update/"+formData.pk, formData, function (data) {
            console.log(data);
            successTip(data, datagrid,dialog);
        })
    }
}

function queryArticle() {
    var row = datagrid.datagrid('getSelected');
    //var rows = { "userId": row.userId, "userInfoId": row.userInfoId };
    if(rowIsNull(row)){
        $.messager.alert("提示","请选择一行数据");
        return;
    }
    parent.window.mainpage.mainTabs.addModule(row.name+"相关文章", ctx + "/admin/article_category/query_article/"+row.id, "icon-hamburg-home", true, true);
}