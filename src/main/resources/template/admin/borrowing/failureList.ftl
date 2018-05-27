[@nestedscript]
    [@js src="ueditor/ueditor.config.js" /]
    [@js src="ueditor/ueditor.all.js" /]
    <script type="text/javascript">
    var datagrid;
    var dialog;
    datagrid = $('#datagrid').datagrid({
        toolbar: '#tb222',

        method: "get",
        url: '${ctx}/admin/borrowing/failureList.json',
        fit: true,
        fitColumns: true,
        border: true,
        idField: 'id',
        striped: true,
        pagination: true,
        rownumbers: true,
        pageNumber: 1,
        pageSize: 20,
        pageList: [10, 20, 30, 40, 50],
        singleSelect: true,
        columns: [[
            {field: 'id', title: '借款编号', sortable: true},
            {field: 'title', title: '标题', sortable: true, width: 100},
            {field: 'borrowerName', title: '借款人名称', width: 100},
            {field: 'borrowerMobile', title: '借款人手机号', width: 100},
            {field: 'progressDes', title: '进度', sortable: false, width: 100},
            {field: 'stateDes', title: '状态', sortable: false, width: 100},
            {field: 'typeDes', title: '类型', sortable: false, width: 100},
            {field: 'amount', title: '金额(元)', sortable: true, width: 100},
            /*{field: 'period', title: '期限', sortable: true, width: 100},*/
            {field: 'period', title: '期限', width: 100,
                formatter: function(value,row,index){
                    return row.period + row.periodUnitDes;
                }
            },
            {field: 'interestRate', title: '利率(%)',width:100, sortable: true},
            {field: 'rewardInterestRate', title: '奖励利率(%)',width:100, sortable: true},
            {field: 'createDate', title: '创建日期',width:200, sortable: true}
        ]],
        enableHeaderClickMenu: true,
        enableHeaderContextMenu: true,
        enableRowContextMenu: false,
        toolbar: '#tb',
        onSelect:function (index, row) {
            if(row.progress=='INQUIRING' && row.state=='FAILURE'){
                $("#reInquiryLink").show();
            }else {
                $("#reInquiryLink").hide();
            }

            if(row.state=='EXPIRY' && row.progress=='EXPIRY'){
                $("#failureBidLink").show();
            }else {
                $("#failureBidLink").hide();
            }
        },
        onLoadSuccess:function (data) {
            $("#reInquiryLink").hide();
            $("#failureBidLink").hide();
        },
    });

    //查询
    function query() {
        var obj = $("#searchFrom").serializeObject();
        datagrid.datagrid('load', obj);
    }

    //弹窗 查看
    function view() {
        var row = datagrid.datagrid('getSelected');
        if (rowIsNull(row)) return;
        dialog = $("#dialog").dialog({
            title: '查看',
            width: 1000,
            height: 600,
            href: '${ctx}/admin/borrowing/view/' + row.id,
            maximizable: true,
            modal: true,
            buttons: [{
                text: '取消',
                handler: function () {
                    dialog.panel('close');
                }
            }]
        });
    }

    //弹窗 重新调查
    function reInquiry() {
        var row = datagrid.datagrid('getSelected');
        if (rowIsNull(row)) return;

        parent.$.messager.confirm('提示', '确认重新调查？', function (data) {
            if (data) {
                $.ajax({
                    type: "POST",
                    url: '${ctx}/admin/borrowing/reInquiry/' + row.id,
                    data: {},
                    success: function(result){
                        result = JSON.parse(result);
                        if(result.status != 'success'){
                            parent.$.messager.confirm('提示', result.message);
                        }
                        datagrid.datagrid('reload');
                    }
                });
            }
        });
    }

    //弹窗 手动流标
    function failureBid() {
        var row = datagrid.datagrid('getSelected');
        if (rowIsNull(row)) return;

        parent.$.messager.confirm('提示', '确认手动流标/获取流标结果？', function (data) {
            if (data) {
                $.ajax({
                    type: "POST",
                    url: '${ctx}/admin/borrowing/failureBid/' + row.id,
                    data: {},
                    success: function(result){
                        result = JSON.parse(result);
                        if(result.status != 'success'){
                            parent.$.messager.confirm('提示', result.message);
                        }else {
                            parent.$.messager.confirm('提示', result.message);
                        }
                        datagrid.datagrid('reload');
                    }
                });
            }
        });
    }
    </script>
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="借款失败列表"]
<div id="userManage" class="easyui-layout" data-options="fit: true">
    <div data-options="region:'center',split:true,border:false,title:'借款失败列表'" style="width: 500px">
        <div id="tb" style="padding:5px;height:auto">
            <form id="searchFrom">
                <div>
                    <input type="text" name="filter_LIKES_title" class="easyui-textbox"
                           data-options="width:150,prompt: '标题'"/>
                    <input type="text" name="filter_amountStart" class="easyui-numberbox"
                           data-options="width:150,min:0,precision:2,prefix:'￥',prompt: '借款金额起'"/>
                    - <input type="text" name="filter_amountEnd" class="easyui-numberbox"
                             data-options="width:150,min:0,precision:2,prefix:'￥',prompt: '借款金额止'"/>
                    <input type="text" name="filter_createDateStart" class="easyui-datetimebox" datefmt="yyyy-MM-dd"
                           data-options="width:150,showSeconds:true,prompt: '创建日期起'"/>
                    - <input type="text" name="filter_createDateEnd" class="easyui-datetimebox" datefmt="yyyy-MM-dd"
                             data-options="width:150,showSeconds:true,prompt: '创建日期止'"/>
                    <span class="toolbar-item dialog-tool-separator"></span>
                    <a href="javascript:(0)" class="easyui-linkbutton" iconCls="fa fa-search" [#--plain="true" --]onclick="query()">查询</a>
                    [@shiro.hasPermission name="borrowing:view"]
                        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-eye" [#--plain="true"--] onclick="view()">查看</a>
                        <span class="toolbar-item dialog-tool-separator"></span>
                    [/@shiro.hasPermission]
                    <a href="javascript:void(0)" id="reInquiryLink" hidden class="easyui-linkbutton" iconCls="fa fa-crosshairs" [#--plain="true"--] onclick="reInquiry()">重新调查</a>
                    <a href="javascript:void(0)" id="failureBidLink" hidden class="easyui-linkbutton" iconCls="fa fa-crosshairs" [#--plain="true"--] onclick="failureBid()">手动流标</a>
                </div>
            </form>
        </div>
        <table id="datagrid"></table>
        <div id="dialog"></div>
    </div>
</div>

[/@insert]