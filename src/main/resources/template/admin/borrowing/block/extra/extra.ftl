[#--借款 附加信息--]
<div class="easyui-panel" data-options="height:460,border:false">
    <table class="tbform">
        <tr>
            <td>附加信息标题：</td>
            <td>
                <input id="borrower" name="extras[0].extraValue" class="easyui-textbox approvalDisabledElement" value="借款车辆信息" data-options="required:'required'" readonly />
                <a href="javascript:addExtraDetail();" class="easyui-linkbutton" iconCls="fa fa-plus">添加</a>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <table class="extratb">
                    <tbody id="extraDetail">
                        [#list extras[0].details as detail]
                            <tr>
                                <td>名称</td>
                                <td>
                                    <input name="extras[0].details[${detail_index}].extraFieldDes" value="${detail.extraFieldDes}" type="text"
                                           class="easyui-textbox approvalDisabledElement" data-options="required:'required'"/>
                                </td>
                                <td>内容</td>
                                <td>
                                    <input name="extras[0].details[${detail_index}].extraFieldValue" value="${detail.extraFieldValue}" type="text"
                                           class="easyui-textbox approvalDisabledElement" data-options="required:'required'"/>
                                </td>
                                <td>
                                    <a href="javascript:deleteExtraDetail();" class="easyui-linkbutton extraDetailDel" iconCls="fa fa-remove">删除</a>
                                </td>
                            </tr>
                        [/#list]
                    </tbody>
                </table>
            </td>
        </tr>
    </table>
</div>