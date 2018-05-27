/**
 * Created by zhutao on 2017/4/05.
 */
var dg;
var dialog;
var corporateDag;
dg = $('#dg').datagrid({
    method: "get",
    url: ctx + '/admin/corporation/list.json',
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
        {field: 'id', title: 'id', hidden: true},
        {field: 'corpName', title: '公司名称', sortable: true, width: 120},
        {field: 'corporationName', title: '法人姓名', sortable: true, width: 120},
        {field: 'corporationMobile', title: '法人手机号', sortable: true, width: 120},
        // {field: 'corpType', title: '公司类别', sortable: true},
        // {field: 'corpDomain', title: '公司行业', sortable: true},
        // {field: 'corpScale', title: '公司规模', sortable: true},
        // {field: 'corpCertification', title: '公司认证', sortable: true},
        // {field: 'corpWithGuarantee', title: '公司有无担保资质', sortable: true,
        //     formatter:function (value,row,index) {
        //         if(value==true){
        //             return "有";
        //         }else{
        //             return "无";
        //         }
        //     }
        // },
        // {field: 'enterpriseBorrowingAbility', title: '企业放贷资质', sortable: true,
        //     formatter:function (value,row,index) {
        //         if(value==true){
        //             return "有";
        //         }else{
        //             return "无";
        //         }
        //     }
        // },
        // {field: 'corpIntro', title: '公司简介', sortable: true},
        // {field: 'corpAssetSize', title: '公司资产规模', sortable: true},
        // {field: 'corpPrevYearOperatedRevenue', title: '公司上年度经营额', sortable: true},
        // {field: 'corpRegisteredCapital', title: '公司注册资金', sortable: true},
        // {field: 'corpLocality', title: '公司所在地', sortable: true},
        // {field: 'corpAddr', title: '公司地址', sortable: true},
        // {field: 'corpZipcode', title: '公司邮编', sortable: true},
        {field: 'corpLicenseNo', title: '公司执照编号', sortable: true, width: 120},
        // {field: 'corpLicenseIssueDate', title: '公司执照签发日期', sortable: true},
        // {field: 'corpNationalTaxNo', title: '公司国税登记证编号', sortable: true},
        // {field: 'corpLandTaxNo', title: '公司地税登记证编号', sortable: true}
    ]],
    enableHeaderClickMenu: true,
    enableHeaderContextMenu: true,
    enableRowContextMenu: false,
    toolbar: '#tb'
});

//查询
function query() {
    var obj = $("#searchForm").serializeObject();
    dg.datagrid('load', obj);
}
function updateCorporate(){
     var row = dg.datagrid('getSelected');
    if (rowIsNull(row)) return;
    console.log(row);
    var rowIndex = row.id;
    corporateDag = Keasy.dialog({
         // dialogId: "#dialog",
        title: '修改公司信息',
        width: 600,
        height: 700,
        href:'/admin/corporation/update/' + row.id,
        maximizable: true,
        modal: true
     });
}
function doConfirmDialog(formData) {
    if(formData.action=='update'){
        Keasy.post("/admin/corporation/save/"+formData.id, formData, function (data) {
            successTip(data, dg, corporateDag);

        })
    }
}

function viewCorporate(){
    var row = dg.datagrid('getSelected');
    if (rowIsNull(row)) return;
    console.log(row);
    var rowIndex = row.id;
    corporateDag = Keasy.dialog({
        // dialogId: "#dialog",
        title: '公司信息',
        width: 600,
        height: 700,
        href:'/admin/corporation/update/' + row.id,
        maximizable: true,
        modal: true,
        buttons: [],
    }, function () {
        // $("#mainform").di
        // $("#mainform").find('input,select,textarea').attr('readonly',true);
        $("#mainform").find('input,select,textarea').attr('disabled', "disabled");
    });

}