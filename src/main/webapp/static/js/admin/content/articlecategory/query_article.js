var datagrid;
var dialog;
datagrid = $('#datagrid').datagrid({
    method: "get",
    url: ctx+'/admin/article_category/article_list/'+parentId,
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
        {field: 'category', title: '分类id', hidden: true},
        {field: 'title', title: '标题', sortable: true},
        {field: 'alias', title: '别名', sortable: true},
        {field: 'categoryName', title: '文章类别', sortable: false},
        {field: 'published', title: '是否发布', sortable: true,
            formatter:function (val,row,index) {
                if(val){
                    return "是";
                }else{
                    return "否";
                }
            }
        },
        {field: 'top', title: '是否置顶', sortable: true,
            formatter:function (val,row,index) {
                if(val){
                    return "是";
                }else{
                    return "否";
                }
            }
        },
        {field: 'author', title: '作者', sortable: true},
        {field: 'sort', title: '排序', sortable: true},
        {field: 'createDate', title: '创建时间', sortable: true}
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
    Keasy.dialog({
        title: '新增文章',
        width: 750,
        height: 850,
        href: ctx+'/admin/article/addEdit?parentId=' + parentId,
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
    Keasy.dialog({
        title: '修改分类',
        width: 550,
        height: 600,
        href: ctx+'/admin/article/updateEdit/'+row.id,
        maximizable: true,
        modal: true
    });
}
function deleteArticle() {
    var row = datagrid.datagrid('getSelected');
    //var rows = { "userId": row.userId, "userInfoId": row.userInfoId };
    if(rowIsNull(row)){
        $.messager.alert("提示","请选择一行数据");
        return;
    }
    $.messager.confirm('确认对话框', '您想要删除该文章吗？', function(r){
        if (r){
            Keasy.delete(ctx+"/admin/article/delete/"+row.id,{},function (data) {
                successTip(data, datagrid);
            })
        }
    });
}

function nextCategory(id){
    alert(id);
}
//查询
function query() {
    var obj = $("#searchFrom").serializeObject();
    datagrid.datagrid('load', obj);
}

function doConfirmDialog(formData) {

    console.log(formData);
    if(formData.action=='add'){
        Keasy.post("/admin/article/save", formData, function (data) {
            successTip(data, datagrid);
        })
    }
    if(formData.action=='update'){
        Keasy.post("/admin/article/update/"+formData.pk, formData, function (data) {
            console.log(data);
            successTip(data, datagrid);
        })
    }
}