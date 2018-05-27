[#--借款 风控信息--]
<div class="easyui-panel" data-options="height:460,border:false">
    <table class="tbform">
        [#--<tr>
            <td>风险度：</td>
            <td>
                <input name="riskDegree" value="${borrowing.riskDegree}" type="text" class="easyui-numberbox disabledElement" data-options="width: 180,min:0,precision:2,suffix:''"></input>%
            </td>
        </tr>
        <tr>
            <td>信用评级：</td>
            <td>
                <select name="creditRating" class="easyui-combobox disabledElement" data-options="width: 180,required:'required'">
                    [#list creditRatings as creditRating]
                        <option value="${creditRating}" [#if creditRating==borrowing.creditRating]selected="selected"[/#if]>${creditRating.displayName}</option>
                    [/#list]
                </select>
            </td>
        </tr>--]
        <tr>
            <td>投资方式：</td>
            <td>
                <select name="investmentMethod" class="easyui-combobox approvalDisabledElement progressDisabledElement" data-options="width: 180,required:'required',editable:false">
                    [#list investmentMethods as investmentMethod]
                        <option value="${investmentMethod}" [#if investmentMethod==borrowing.investmentMethod]selected="selected"[/#if]>${investmentMethod.displayName}</option>
                    [/#list]
                </select>
            </td>
        </tr>
        <tr>
            <td>最低投资：</td>
            <td>
                <input id="investmentMinimum" name="investmentMinimum" value="${borrowing.investmentMinimum}" type="text" class="easyui-numberbox approvalDisabledElement progressDisabledElement" data-options="width: 180,required:'required',min:0,precision:2,prefix:'￥'"></input>
                <span>
                    <a id="investmentMinimum-remark-add" class="fa fa-commenting-o" href="javascript:void(0);" onclick="addRemark('investmentMinimum')" title="添加备注"></a>
                    <a id="investmentMinimum-remark-update" class="fa fa-pencil" href="javascript:void(0);" onclick="updateRemark('investmentMinimum')" title="修改备注" style="display: none;"></a>
                    <a id="investmentMinimum-remark-ok" class="fa fa-check-square-o" href="javascript:void(0);" onclick="okRemark('investmentMinimum')" title="完成备注" style="display: none;"></a>
                    <input id="investmentMinimum-remark" name="investmentMinimum-remark" style="display: none;">
                    <span id="investmentMinimum-remark-show" style="color: #ff0000;"></span>
                    <a id="investmentMinimum-remark-del" class="fa fa-trash-o" href="javascript:void(0);" onclick="delRemark('investmentMinimum')" title="删除备注" style="display: none;"></a>
                </span>
            </td>
        </tr>
        [#--<tr>
            <td>加价幅度：</td>
            <td>
                <input name="multiple" value="${borrowing.multiple}" type="text" class="easyui-numberbox disabledElement" data-options="width: 180,min:0"></input>
            </td>
        </tr>--]
        <tr>
            <td>最高投资：</td>
            <td>
                <input name="investmentMaximum" value="${borrowing.investmentMaximum}" type="text" class="easyui-numberbox approvalDisabledElement progressDisabledElement" data-options="width: 180,min:0,precision:2,prefix:'￥'"></input>
            </td>
        </tr>
        <tr>
            <td>投资开始日期：</td>
            <td>
                <input id="investmentStartDate" name="investmentStartDate" value="${borrowing.investmentStartDate}" type="text" class="easyui-datetimebox approvalDisabledElement progressDisabledElement" data-options="width:180,showSeconds:true,prompt: '投资开始日期'"></input>
                <span>
                    <a id="investmentStartDate-remark-add" class="fa fa-commenting-o" href="javascript:void(0);" onclick="addRemark('investmentStartDate')" title="添加备注"></a>
                    <a id="investmentStartDate-remark-update" class="fa fa-pencil" href="javascript:void(0);" onclick="updateRemark('investmentStartDate')" title="修改备注" style="display: none;"></a>
                    <a id="investmentStartDate-remark-ok" class="fa fa-check-square-o" href="javascript:void(0);" onclick="okRemark('investmentStartDate')" title="完成备注" style="display: none;"></a>
                    <input id="investmentStartDate-remark" name="investmentStartDate-remark" style="display: none;">
                    <span id="investmentStartDate-remark-show" style="color: #ff0000;"></span>
                    <a id="investmentStartDate-remark-del" class="fa fa-trash-o" href="javascript:void(0);" onclick="delRemark('investmentStartDate')" title="删除备注" style="display: none;"></a>
                </span>
            </td>
        </tr>
        <tr>
            <td>投资结束日期：</td>
            <td>
                <input name="investmentEndDate" value="${borrowing.investmentEndDate}" type="text" class="easyui-datetimebox approvalDisabledElement progressDisabledElement" data-options="width:180,showSeconds:true,prompt: '投资结束日期'"></input>
            </td>
        </tr>
        [#--<tr>
            <td>投资返利率：</td>
            <td>
                <input name="investmentRebateRate" value="${borrowing.investmentRebateRate}" type="text" class="easyui-numberbox disabledElement" data-options="width: 180,min:0,precision:2,suffix:''"></input>%
            </td>
        </tr>
        <tr>
            <td>投资密码：</td>
            <td>
                <input name="investmentPassword" value="${borrowing.investmentPassword}" type="password" class="easyui-validatebox disabledElement" data-options="width:180,validType:'length[2,20]'"/>
            </td>
        </tr>--]
        <tr>
            <td>出借时间：</td>
            <td>
                <select name="lendingTime" id="lendingTime" class="easyui-combobox approvalDisabledElement progressDisabledElement disabledElement" data-options="width: 180,required:'required',editable:false">
                    [#list lendingTimes as lendingTime]
                        [#if lendingTime=='AFTER_AUDIT']
                            <option value="${lendingTime}" [#if lendingTime==borrowing.lendingTime]selected="selected"[/#if]>${lendingTime.displayName}</option>
                        [/#if]
                    [/#list]
                </select>
            </td>
        </tr>
        <tr>
            <td>还款方式：</td>
            <td>
                <select id="repaymentMethod" name="repaymentMethod" id="repaymentMethod" class="easyui-combobox approvalDisabledElement progressDisabledElement disabledElement" data-options="width: 180,required:'required',editable:false">
                    [#list repaymentMethods as repaymentMethod]
                        <option value="${repaymentMethod}" [#if repaymentMethod==borrowing.repaymentMethod]selected="selected"[/#if]>${repaymentMethod.displayName}</option>
                    [/#list]
                </select>
                <span>
                    <a id="repaymentMethod-remark-add" class="fa fa-commenting-o" href="javascript:void(0);" onclick="addRemark('repaymentMethod')" title="添加备注"></a>
                    <a id="repaymentMethod-remark-update" class="fa fa-pencil" href="javascript:void(0);" onclick="updateRemark('repaymentMethod')" title="修改备注" style="display: none;"></a>
                    <a id="repaymentMethod-remark-ok" class="fa fa-check-square-o" href="javascript:void(0);" onclick="okRemark('repaymentMethod')" title="完成备注" style="display: none;"></a>
                    <input id="repaymentMethod-remark" name="repaymentMethod-remark" style="display: none;">
                    <span id="repaymentMethod-remark-show" style="color: #ff0000;"></span>
                    <a id="repaymentMethod-remark-del" class="fa fa-trash-o" href="javascript:void(0);" onclick="delRemark('repaymentMethod')" title="删除备注" style="display: none;"></a>
                </span>
            </td>
        </tr>
        <tr>
            <td>借款服务费率：</td>
            <td>
                <input name="feeRate" value="${borrowing.feeRate}" type="text" class="easyui-numberbox approvalDisabledElement progressDisabledElement disabledElement" data-options="width: 180,required:'required',min:0,precision:2,suffix:''"></input>%
            </td>
        </tr>
        <tr>
            <td>还款服务费收取方式：</td>
            <td>
                <select name="repaymentFeeMethod" class="easyui-combobox approvalDisabledElement progressDisabledElement" data-options="width: 180,required:'required',editable:false">
                    [#list repaymentFeeMethods as repaymentFeeMethod]
                        <option value="${repaymentFeeMethod}" [#if repaymentFeeMethod==borrowing.repaymentFeeMethod]selected="selected"[/#if]>${repaymentFeeMethod.displayName}</option>
                    [/#list]
                </select>
            </td>
        </tr>
        <tr>
            <td>还款服务费率：</td>
            <td>
                <input name="repaymentFeeRate" value="${borrowing.repaymentFeeRate}" type="text" class="easyui-numberbox approvalDisabledElement progressDisabledElement" data-options="width: 180,required:'required',min:0,precision:2,suffix:''"></input>%
            </td>
        </tr>
        <tr>
            <td>回收服务费率：</td>
            <td>
                <input name="recoveryFeeRate" value="${borrowing.recoveryFeeRate}" type="text" class="easyui-numberbox approvalDisabledElement progressDisabledElement" data-options="width: 180,required:'required',min:0,precision:2,suffix:''"></input>%
            </td>
        </tr>
        <tr>
            <td>转入方服务费率：</td>
            <td>
                <input name="inTransferFeeRate" value="${borrowing.inTransferFeeRate}" type="text" class="easyui-numberbox approvalDisabledElement progressDisabledElement" data-options="width: 180,required:'required',min:0,precision:2,suffix:''"></input>%
            </td>
        </tr>
        <tr>
            <td>转出方服务费率：</td>
            <td>
                <input name="outTransferFeeRate" value="${borrowing.outTransferFeeRate}" type="text" class="easyui-numberbox approvalDisabledElement progressDisabledElement" data-options="width: 180,required:'required',min:0,precision:2,suffix:''"></input>%
            </td>
        </tr>
        <tr>
            <td>逾期利率：</td>
            <td>
                <input name="overdueInterestRate" value="${borrowing.overdueInterestRate}" type="text" class="easyui-numberbox approvalDisabledElement progressDisabledElement" data-options="width: 180,required:'required',min:0,precision:2,suffix:''"></input>%/天
            </td>
        </tr>
        <tr>
            <td>严重逾期开始期限：</td>
            <td>
                <input name="seriousOverdueStartPeriod" value="${borrowing.seriousOverdueStartPeriod}" type="text" class="easyui-numberbox approvalDisabledElement progressDisabledElement" data-options="width: 180,required:'required',min:0,suffix:''"></input>天
            </td>
        </tr>
        <tr>
            <td>严重逾期利率：</td>
            <td>
                <input name="seriousOverdueInterestRate" value="${borrowing.seriousOverdueInterestRate}" type="text" class="easyui-numberbox approvalDisabledElement progressDisabledElement" data-options="width: 180,required:'required',min:0.01,precision:2,suffix:''"></input>%/天
            </td>
        </tr>
        <tr>
            <td>风险分析：</td>
            <td>
                <textarea name="riskAnalysis" class="approvalDisabledElement progressDisabledElement">${borrowing.riskAnalysis!""}</textarea>
            </td>
        </tr>
    </table>
</div>