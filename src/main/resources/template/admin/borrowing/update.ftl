[@insert template="admin/layout/default_layout" title="借款修改"]
<div>
    <form id="borrowingForm" action="${ctx}/admin/borrowing/${action}" method="POST" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${borrowing.id}"/>
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
            <div title="修改意见">
                <table class="tbform">
                    <tr>
                        <td>是否推荐：</td>
                        <td>
                            <select name="isRecommend" value="${borrowing.isRecommend}" [#if borrowing.progress!='inquiring' && borrowing.progress!='approval']disabled="disabled"[/#if] class="easyui-combobox" data-options="required:'required',editable:false">
                                <option value="false" [#if !borrowing.isRecommend]selected="selected"[/#if]>否</option>
                                <option value="true" [#if borrowing.isRecommend]selected="selected"[/#if]>是</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>修改意见：</td>
                        <td>
                            <textarea id="opinion" name="opinion" class="easyui-textbox" multiline="true" data-options="required:'required'"></textarea>
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
        if("${borrowing.progress}"=="INVESTING"||"${borrowing.progress}"=="LENDING")
        {
            $(".disabledElement").attr("readonly", "readonly"); //禁用
        }
        if("${borrowing.progress}"=="REPAYING"||"${borrowing.progress}"=="COMPLETED")
        {
            $(".progressDisabledElement").attr("readonly", "readonly"); //禁用
        }
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
        editable:true,
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
//    var periodVal = $('#periodUnit').combobox('getValue');
//    $('#repaymentMethod').combobox({
//        url: ctx + '/admin/borrowing/repaymentMethods?dateUnit=' + periodVal,
//        valueField: 'id',
//        textField: 'text'
//    });
//    $('#interestMethod').combobox({
//        url: ctx + '/admin/borrowing/interestMethods?dateUnit=' + periodVal,
//        valueField: 'id',
//        textField: 'text'
//    });

    getRemarks(${borrowing.id});

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
    [#include "admin/borrowing/extra_js.ftl" /]
</script>
[/@insert]