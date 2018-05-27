[@insert template="admin/layout/default_layout" title="借款详情"]
<form id="borrowingForm">
    <div class="easyui-tabs" data-options="border:false">
        <div title="借款信息">
            <div class="easyui-tabs" data-options="border:false">
                <div title="基本信息">
                    [#include "admin/borrowing/block/borrowing/basic.ftl" /]
                </div>
                <div title="项目介绍">
                    [#include "admin/borrowing/block/intro/index.ftl" /]
                </div>
                <div title="调查信息">
                    [#include "admin/borrowing/block/borrowing/inquiry.ftl" /]
                </div>
                <div title="担保信息">
                    [#include "admin/borrowing/block/borrowing/guarantee.ftl" /]
                </div>
                <div title="风控信息">
                    [#include "admin/borrowing/block/borrowing/riskContorl.ftl" /]
                </div>
                <div title="材料信息">
                    [#include "admin/borrowing/block/borrowing/datum.ftl" /]
                </div>
                <div title="附加信息">
                    [#include "admin/borrowing/block/extra/extra.ftl" /]
                </div>
            </div>
        </div>
        <div title="投资信息">
            <div class="easyui-tabs"  data-options="border:false">
                <div title="投资" style="padding:5px 0 0 5px;">
                    [#include "admin/borrowing/block/invest/invest.ftl" /]
                </div>
                <div title="投资记录" style="padding:5px 0 0 5px;">
                    [#include "admin/borrowing/block/invest/investRecord.ftl" /]
                </div>
            </div>
        </div>
        <div title="还款信息">
            <div class="easyui-tabs" data-options="border:false">
                <div title="还款" style="padding:5px 0 0 5px;">
                    [#include "admin/borrowing/block/repay/repay.ftl" /]
                </div>
                <div title="回款计划" style="padding:5px 0 0 5px;">
                    [#include "admin/borrowing/block/repay/repayPlan.ftl" /]
                </div>
            </div>
        </div>
        <div title="标的联系人">
            [#include "admin/borrowing/block/contacts/contacts.ftl" /]
        </div>
        <div title="意见信息">
            [#include "admin/borrowing/block/opinion/opinion.ftl" /]
        </div>
    </div>
</form>

<script type="text/javascript">

    //禁用form编辑
    $(document).ready(function(){
        var action = ${action};

        $("#borrowingForm input").attr("disabled", "disabled"); //禁用
        $("#borrowingForm textarea").attr("disabled", "disabled"); //禁用
        $("#borrowingForm select").attr("disabled", "disabled"); //禁用
        $("#borrowingForm a").removeAttr('onclick');//禁用
        $("#borrowingForm .delete").removeAttr('class');//禁用

    });

    //借款人
    $('#borrower').combogrid({
        width:300,
        panelWidth: 300,
        delay: 500,
        mode: 'remote',
        url: '${ctx}/admin/borrowing/borrower/search.json',
        idField: 'id',
        textField: 'realName',
        editable:false,
        showHeader:false,
        columns: [[
            {field:'mobile',title:'手机号',width:290,
                formatter: function(value,row,index){
                    if(row.corpName){
                        return "<span style='color: #ff0000'>企-</span>" + row.mobile + '-' + row.realName;
                    }
                    return "<span style='color: #089eff'>个-</span>" + row.mobile + '-' + row.realName;
                }
            },
            {field:'realName',title:'用户',width:150,hidden:true,
                formatter: function(value,row,index){
                    row.realName = (row.corpName ? '企' : '个') + '-' + row.mobile + '-' + row.realName;
                    return row.realName;
                }
            }
        ]]
    });

    //担保公司
    $('#guaranteeCorp').combobox({
        width: 180,
        method: 'GET',
        url: '${ctx}/admin/borrowing/guaranteeCorp/json',
        valueField: 'id',
        textField: 'loginName',
    });

    hideRemark();

    //表单提交
    $('#borrowingForm').form({
        onSubmit: function () {
            var isValid = $(this).form('validate');
            return isValid;	// 返回false终止表单提交
        },
        success: function (data) {
            data = JSON.parse(data);
            successTip(data, orgDg, d);
        }
    });

</script>
[/@insert]