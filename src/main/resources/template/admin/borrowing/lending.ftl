[@insert template="admin/layout/default_layout" title="借款出借"]
<div>
    <form id="borrowingForm" action="${ctx}/admin/borrowing/${action}" method="POST" enctype="multipart/form-data">
        <input type="hidden" class="writable" name="id" value="${borrowing.id}"/>
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
                </div>
            </div>
            <div title="投资信息">
                <div class="easyui-tabs" data-options="border:false">
                    <div title="投资"  style="padding:5px 0 0 5px;">
                        [#include "admin/borrowing/block/invest/invest.ftl" /]
                    </div>
                    <div title="投资记录">
                        [#include "admin/borrowing/block/invest/investRecord.ftl" /]
                    </div>
                </div>
            </div>
            <div title="还款信息">
                <div class="easyui-tabs" data-options="border:false">
                    <div title="还款" style="padding:5px 0 0 5px;">
                        [#include "admin/borrowing/block/repay/repay.ftl" /]
                    </div>
                    <div title="还款计划" style="padding:5px 0 0 5px;">
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
            <div title="出借意见">
                <table class="tbform">
                    <tr>
                        <td>出借状态：</td>
                        <td>
                            <select class="writable" name="state" class="easyui-combobox" data-options="editable:false">
                                <option value="WAIT">待下次出借</option>
                                <option value="SUCCESS">出借已通过</option>
                                [#--<option value="FAILURE">出借未通过</option>--]
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>出借意见：</td>
                        <td>
                            <textarea id="opinion" name="opinion" class="easyui-textbox writable" multiline="true" data-options="required:'required'"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td>通知借款人：</td>
                        <td>
                            <select name="noticeBorrower" class="easyui-combobox writable" data-options="required:'required', width:150,editable:false">
                                <option value="0" selected="selected">否</option>
                                <option value="1" >是</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>通知投资人：</td>
                        <td>
                            <select name="noticeInvestor" class="easyui-combobox writable" data-options="required:'required', width:150,editable:false">
                                <option value="0" selected="selected">否</option>
                                <option value="1" >是</option>
                            </select>
                        </td>
                    </tr>

                </table>
            </div>
        </div>
    </form>
</div>

<script type="text/javascript">

    //禁用部分form元素编辑
    $(document).ready(function(){
        $("#borrowingForm input").attr("disabled", "disabled"); //禁用
        $("#borrowingForm textarea").attr("disabled", "disabled"); //禁用
        $("#borrowingForm select").attr("disabled", "disabled"); //禁用
        $("#borrowingForm a").removeAttr('onclick');//禁用
        $("#borrowingForm .delete").removeAttr('class');//禁用
        $("#borrowingForm .writable").removeAttr("disabled"); //可写
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
        textField: 'loginName'
    });

    $('#periodUnit').combobox({
        onChange: function (newValue, oldValue) {
            $('#repaymentMethod').combobox({
                url: ctx + '/admin/borrowing/repaymentMethods?dateUnit=' + newValue,
                valueField: 'id',
                textField: 'text'
            });
            $('#interestMethod').combobox({
                url: ctx + '/admin/borrowing/interestMethods?dateUnit=' + newValue,
                valueField: 'id',
                textField: 'text'
            });
            $('#repaymentMethod').combobox('clear');
            $('#interestMethod').combobox('clear');
        }
    });
    var periodVal = $('#periodUnit').combobox('getValue');
    $('#repaymentMethod').combobox({
        url: ctx + '/admin/borrowing/repaymentMethods?dateUnit=' + periodVal,
        valueField: 'id',
        textField: 'text'
    });
    $('#interestMethod').combobox({
        url: ctx + '/admin/borrowing/interestMethods?dateUnit=' + periodVal,
        valueField: 'id',
        textField: 'text'
    });

    hideRemark();

    //表单提交
    $('#borrowingForm').form({
        onSubmit: function () {
            var isValid = $(this).form('validate');
            if (!isValid) {
                $.messager.alert('提示', '请完善必输选项','error');
            } else {
                $.messager.progress({
                    title: '执行中',
                    msg: '努力加载中...',
                    text: '{value}%',
                    interval: 100,
                });
            }
            return isValid;	// 返回false终止表单提交
        },
        success: function (data) {
            data = JSON.parse(data);
            $.messager.progress('close');
            successTip(data, dg, dialog);
        }
    });

    [#include "admin/borrowing/material_js.ftl" /]

</script>
[/@insert]