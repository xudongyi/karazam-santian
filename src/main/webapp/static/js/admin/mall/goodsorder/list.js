var datagrid;
var dialog;
datagrid = $('#datagrid').datagrid({
    method: "get",
    url: ctx+'/admin/goods_order/list',
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
        /*{field: 'name', title: '名称', sortable: true, width:120,
            formatter:function (val,row,index) {
               return  "<a href='#' onclick='nextCategory("+JSON.stringify(row)+")'>" + val + "</a>" ;
            }
        },*/
        {field: 'orderNo', title: '订单编号', sortable: true, width:120},
        {field: 'statusDes', title: '订单状态', sortable: true, width:120},
        {field: 'username', title: '用户', sortable: true, width:120},
        {field: 'goods', title: '商品ID', sortable: true, width:120},
        {field: 'quantity', title: '数量', sortable: true, width:120},
        /*{field: 'amount', title: '成交金额', sortable: true, width:120},*/
        {field: 'point', title: '成交积分', sortable: true, width:120},
        {field: 'confirmDate', title: '兑换时间', sortable: true, width:120},
        {field: 'sendDate', title: '发货时间', sortable: true, width:120},
        {field: 'receiveDate', title: '收货时间', sortable: true, width:120},
        {field: 'createDate', title: '创建时间', sortable: true, width:120}
    ]],
    /*headerContextMenu: [
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
    ],*/
    enableHeaderClickMenu: true,
    enableHeaderContextMenu: true,
    enableRowContextMenu: false,
    toolbar: '#tb'
});

//发货
function sendOut() {
    var row = datagrid.datagrid('getSelected');
    if(rowIsNull(row)){
        $.messager.alert("提示","请选择一行数据");
        return;
    }
    if(row.status != 'paid' && row.status != 'sending'){
        $.messager.alert("提示","该订单当前不能发货");
        return;
    }
    dialog=Keasy.dialog({
        title: '订单发货',
        width: 550,
        height: 400,
        href: ctx+'/admin/goods_order/sendView/'+row.id,
        maximizable: true,
        modal: true
    });
}

//弹窗输入新增文章分类
function create() {
    dialog=Keasy.dialog({
        title: '新增分类',
        width: 550,
        height: 400,
        href: ctx+'/admin/goods_category/add',
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
        href: ctx+'/admin/goods_category/edit/'+row.id,
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
    $.messager.confirm('确认对话框', '您想要删除该商品订单吗？', function(r){
        if (r){
            Keasy.delete("/admin/goods_category/delete/"+row.id,{},function (data) {
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
        content : '<iframe src="' + ctx + "/admin/goods_category/pageListByIdDispatcher/"+row.id + '" frameborder="0" style="border:0;width:100%;height:99.5%;"></iframe>'
    });
}
//查询
function query() {
    var obj = $("#searchFrom").serializeObject();
    datagrid.datagrid('load', obj);
}

function doConfirmDialog(formData) {
    if(formData.action=='add'){
        Keasy.post("/admin/goods_category/save", formData, function (data) {
            successTip(data, datagrid,dialog);
        })
    }
    if(formData.action=='update'){
        Keasy.post("/admin/goods_category/update/"+formData.pk, formData, function (data) {
            console.log(data);
            successTip(data, datagrid,dialog);
        })
    }
    if(formData.action=='send'){
        console.log(formData);
        Keasy.post("/admin/goods_order/send/"+formData.pk, formData, function (data) {
            console.log(data);
            successTip(data, datagrid,dialog);
        })
    }
}

function querygoods() {
    var row = datagrid.datagrid('getSelected');
    //var rows = { "userId": row.userId, "userInfoId": row.userInfoId };
    if(rowIsNull(row)){
        $.messager.alert("提示","请选择一行数据");
        return;
    }
    parent.window.mainpage.mainTabs.addModule(row.name+"相关文章", ctx + "/admin/goods_category/query_goods/"+row.id, "icon-hamburg-home", true, true);
}