[#--借款 基本信息--]
<div class="easyui-panel" data-options="height:460,border:false">
    <table class="tbform">
        <tr>
            <td>借款人：</td>
            <td>
                <input id="borrower" name="borrower" class="approvalDisabledElement progressDisabledElement disabledElement" value="${borrowing.borrower}" data-options="required:'required',editable:false"/>
            </td>
        </tr>
        <tr>
            <td>借款类型：</td>
            <td>
                <select name="type" value="${borrowing.type}" class="easyui-combobox approvalDisabledElement" data-options="required:'required', width:150,editable:false">
                    [#list types as type]
                        <option value="${type}" [#if borrowing.type==type]selected="selected"[/#if]>${type.alias}</option>
                    [/#list]
                </select>
            </td>
        </tr>
        <tr>
            <td>借款标题：</td>
            <td>
                <input id="title" name="title" value="${borrowing.title}" type="text" class="easyui-textbox approvalDisabledElement" data-options="required:'required',validType:'length[2,20]'"/>
                <span>
                    <a id="title-remark-add" class="fa fa-commenting-o" href="javascript:void(0);" onclick="addRemark('title')" title="添加备注" style="display: none;"></a>
                    <a id="title-remark-update" class="fa fa-pencil" href="javascript:void(0);" onclick="updateRemark('title')" title="修改备注" style="display: none;"></a>
                    <a id="title-remark-ok" class="fa fa-check-square-o" href="javascript:void(0);" onclick="okRemark('title')" title="完成备注" style="display: none;"></a>
                    <input id="title-remark" name="title-remark" style="display: none;">
                    <span id="title-remark-show" style="color: #ff0000;"></span>
                    <a id="title-remark-del" class="fa fa-trash-o" href="javascript:void(0);" onclick="delRemark('title')" title="删除备注" style="display: none;"></a>
                </span>
            </td>
        </tr>
        <tr>
            <td>借款金额：</td>
            <td>
                <input id="amount" name="amount" value="${borrowing.amount}" type="text" class="easyui-numberbox approvalDisabledElement progressDisabledElement disabledElement" value="" data-options="width: 180,required:'required',min:0,precision:2,prefix:'￥'"></input>
                <span>
                    <a id="amount-remark-add" class="fa fa-commenting-o" href="javascript:void(0);" onclick="addRemark('amount')" title="添加备注"></a>
                    <a id="amount-remark-update" class="fa fa-pencil" href="javascript:void(0);" onclick="updateRemark('amount')" title="修改备注" style="display: none;"></a>
                    <a id="amount-remark-ok" class="fa fa-check-square-o" href="javascript:void(0);" onclick="okRemark('amount')" title="完成备注" style="display: none;"></a>
                    <input id="amount-remark" name="amount-remark" style="display: none;">
                    <span id="amount-remark-show" style="color: #ff0000;"></span>
                    <a id="amount-remark-del" class="fa fa-trash-o" href="javascript:void(0);" onclick="delRemark('amount')" title="删除备注" style="display: none;"></a>
                </span>
            </td>
        </tr>
        <tr>
            <td>借款期限：</td>
            <td>
                <input id="period" name="period" value="${borrowing.period}" type="text" class="easyui-numberbox approvalDisabledElement" value="" data-options="width:180,required:'required',min:0,precision:0"></input>
                <select name="periodUnit" id="periodUnit" class="easyui-combobox disabledElement progressDisabledElement" data-options="editable:false" style="width:60px;height:auto;">
                    [#list periodUnits as periodUnit]
                        <option value="${periodUnit}" [#if borrowing.periodUnit==periodUnit]selected="selected"[/#if]>${periodUnit.displayName}</option>
                    [/#list]
                </select>
                <span>
                    <a id="period-remark-add" class="fa fa-commenting-o" href="javascript:void(0);" onclick="addRemark('period')" title="添加备注"></a>
                    <a id="period-remark-update" class="fa fa-pencil" href="javascript:void(0);" onclick="updateRemark('period')" title="修改备注" style="display: none;"></a>
                    <a id="period-remark-ok" class="fa fa-check-square-o" href="javascript:void(0);" onclick="okRemark('period')" title="完成备注" style="display: none;"></a>
                    <input id="period-remark" name="period-remark" style="display: none;">
                    <span id="period-remark-show" style="color: #ff0000;"></span>
                    <a id="period-remark-del" class="fa fa-trash-o" href="javascript:void(0);" onclick="delRemark('period')" title="删除备注" style="display: none;"></a>
                </span>
            </td>
        </tr>
        <tr>
            <td>计息方式：</td>
            <td>
                <select name="interestMethod" id="interestMethod" class="easyui-combobox disabledElement progressDisabledElement" style="width:120px;height:auto;" data-options="required:'required',editable:false">
                [#list interestMethods as interestMethod]
                    <option value="${interestMethod}" [#if borrowing.interestMethod==interestMethod]selected="selected"[/#if]>${interestMethod.displayBgName}</option>
                [/#list]
                </select>
            </td>
        </tr>
        <tr>
            <td>基本利率：</td>
            <td>
                <input name="interestRate" value="${borrowing.interestRate}" type="text" class="easyui-numberbox approvalDisabledElement progressDisabledElement disabledElement" data-options="width: 180,required:'required',min:1,precision:2,suffix:''"></input>%/年
            </td>
        </tr>
        <tr>
            <td>奖励利率：</td>
            <td>
                <input name="rewardInterestRate" value="${borrowing.rewardInterestRate}" type="text" class="easyui-numberbox approvalDisabledElement progressDisabledElement disabledElement" data-options="width: 180,min:0,precision:2,suffix:''"></input>%/年
            </td>
        </tr>
        <tr>
            <td>借款协议：</td>
            <td>
                <select name="agreementId" value="${borrowing.agreementId}" class="easyui-combobox approvalDisabledElement" data-options="required:'required', width:150,editable:false">
                [#list agreements as agreement]
                    <option value="${agreement.id}" [#if borrowing.agreementId==agreement.id]selected="selected"[/#if]>${agreement.name}--${agreement.agreementVersion}</option>
                [/#list]
                </select>
            </td>
        </tr>
        <tr>
            <td>转让人转让协议：</td>
            <td>
                <select name="transferAgreementId" value="${borrowing.transferAgreementId}" class="easyui-combobox approvalDisabledElement" data-options="required:'required', width:150,editable:false">
                [#list transferAgreements as transferAgreement]
                    <option value="${transferAgreement.id}" [#if borrowing.transferAgreementId==transferAgreement.id]selected="selected"[/#if]>${transferAgreement.name}--${transferAgreement.agreementVersion}</option>
                [/#list]
                </select>
            </td>
        </tr>
        <tr>
            <td>受让人转让协议：</td>
            <td>
                <select name="investTransferAgreementId" value="${borrowing.investTransferAgreementId}" class="easyui-combobox approvalDisabledElement" data-options="required:'required', width:150,editable:false">
                [#list borrowingTransferAgreements as borrowingTransferAgreement]
                    <option value="${borrowingTransferAgreement.id}" [#if borrowing.investTransferAgreementId==borrowingTransferAgreement.id]selected="selected"[/#if]>${borrowingTransferAgreement.name}--${borrowingTransferAgreement.agreementVersion}</option>
                [/#list]
                </select>
            </td>
        </tr>
        <tr>
            <td>协议签署地：</td>
            <td>
                <input name="agreementPlace" value="${borrowing.agreementPlace}" type="text" class="easyui-textbox approvalDisabledElement" data-options="validType:'length[0,100]'"/>
            </td>
        </tr>
        <tr>
            <td>项目标签：</td>
            <td>
                <input name="labels" value="${borrowing.labels}" type="text" class="easyui-textbox approvalDisabledElement" data-options="validType:'length[0,100]'"/>
            </td>
        </tr>
        <tr style="display: none;">
            <td>是否可用优惠券：</td>
            <td>
                <select name="surportCoupon" class="easyui-combobox approvalDisabledElement" data-options="required:'required', width:150,editable:false">
                    <option value="0" [#if !borrowing.surportCoupon]selected="selected"[/#if]>否</option>
                    <option value="1" [#if borrowing.surportCoupon]selected="selected"[/#if]>是</option>
                </select>
            </td>
        </tr>
        <tr style="display: none;">
            <td>是否启用自动投标：</td>
            <td>
                <select name="autoInvest" class="easyui-combobox approvalDisabledElement" data-options="required:'required', width:150,editable:false">
                    <option value="0" [#if !borrowing.autoInvest]selected="selected"[/#if]>否</option>
                    <option value="1" [#if borrowing.autoInvest]selected="selected"[/#if]>是</option>
                </select>
                <span style="color: #ff0000;">如果选择开启自动投标，最低投资金额必须是100</span>
            </td>
        </tr>
        <tr>
            <td>借款描述：</td>
            <td>
                <textarea name="description">${borrowing.description!""}</textarea>
            </td>
        </tr>
        <tr>
            <td>借款概述：</td>
            <td>
                <textarea name="subjectSituation">${borrowing.subjectSituation!""}</textarea>
            </td>
        </tr>
    </table>
</div>