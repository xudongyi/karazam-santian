[@nestedscript]
    [@js src="js/admin/recharge/setting.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="充值设置"]
<form id="pushForm" method="post">
    <input type="hidden" name="action" value="rechargesetting" />
    <table style="margin: 10px; border:1px solid #ccc; font-size: 12px;">
        <tr>
            <td style="width: 100px;">是否开启充值</td>
            <td>
                <select id="enable" name="enable" class="easyui-combobox">
                    <option value="true" [#if rechargeSetting.enable?string("true", "false")=='true']selected[/#if]>是</option>
                    <option value="false" [#if rechargeSetting.enable?string("true", "false")=='false']selected[/#if]>否</option>
                </select>
            </td>
        </tr>
        <tr style="display: none;">
            <td style="width: 200px;">环迅充值个人手续费率</td>
            <td>
                <input id="ipsGeneralFeeRate" name="ipsGeneralFeeRate" type="text" class="easyui-numberbox" data-options="required:'required',min:0,precision:2,validType:'intOrFloat[0,100]'" value="${rechargeSetting.ipsGeneralFeeRate}" />%/笔
            </td>
        </tr>
        <tr style="display: none;">
            <td>是否平台承担个人环迅充值手续费</td>
            <td>
                <select id="platformAssumeGeneralIpsFee" name="platformAssumeGeneralIpsFee" class="easyui-combobox">
                    <option value="false" [#if rechargeSetting.platformAssumeGeneralIpsFee?string("true", "false")=='false']selected[/#if]>否</option>
                    <option value="true" [#if rechargeSetting.platformAssumeGeneralIpsFee?string("true", "false")=='true']selected[/#if]>是</option>
                </select>
            </td>
        </tr>
        <tr style="display: none;">
            <td>个人充值环迅手续费扣除类型</td>
            <td>
                <select id="generalDeductionType" name="generalDeductionType" class="easyui-combobox">
                    <option value="1" [#if rechargeSetting.generalDeductionType=='1']selected[/#if]>内扣</option>
                    <option value="2" [#if rechargeSetting.generalDeductionType=='2']selected[/#if]>外扣</option>
                </select>
            </td>
        </tr>
        <tr style="display: none;">
            <td>环迅充值企业手续费</td>
            <td>
                <input id="ipsEnterpriseFee" name="ipsEnterpriseFee" type="text" class="easyui-numberbox" data-options="required:'required',min:0,precision:2,validType:'intOrFloat[0,100]'" value="${rechargeSetting.ipsEnterpriseFee}" />元/笔
            </td>
        </tr>
        <tr style="display: none;">
            <td>是否平台承担企业环迅充值手续费</td>
            <td>
                <select id="platformAssumeEnterpriseIpsFee" name="platformAssumeEnterpriseIpsFee" class="easyui-combobox">
                    <option value="false" [#if rechargeSetting.platformAssumeEnterpriseIpsFee?string("true", "false")=='false']selected[/#if]>否</option>
                    <option value="true" [#if rechargeSetting.platformAssumeEnterpriseIpsFee?string("true", "false")=='true']selected[/#if]>是</option>
                </select>
            </td>
        </tr>
        <tr style="display: none;">
            <td>企业充值环迅手续费扣除类型</td>
            <td>
                <select id="enterpriseDeductionType" name="enterpriseDeductionType" class="easyui-combobox">
                    <option value="1" [#if rechargeSetting.enterpriseDeductionType=='1']selected[/#if]>内扣</option>
                    <option value="2" [#if rechargeSetting.enterpriseDeductionType=='2']selected[/#if]>外扣</option>
                </select>
            </td>
        </tr>
        [#--<tr>--]
            [#--<td colspan="2">--]
                [#--<hr />--]
                [#--还款充值--]
            [#--</td>--]
        [#--</tr>--]
        [#--<tr>--]
            [#--<td>是否开启还款充值</td>--]
            [#--<td>--]
                [#--<select id="enableRepay" name="enableRepay" class="easyui-combobox">--]
                    [#--<option value="true" [#if rechargeSetting.enableRepay?string("true", "false")=='true']selected[/#if]>是</option>--]
                    [#--<option value="false" [#if rechargeSetting.enableRepay?string("true", "false")=='false']selected[/#if]>否</option>--]
                [#--</select>--]
            [#--</td>--]
        [#--</tr>--]
        [#--<tr>--]
            [#--<td>环迅还款充值费率</td>--]
            [#--<td>--]
                [#--<input id="ipsRepayRechargeFeeRate" name="ipsRepayRechargeFeeRate" type="text" class="easyui-numberbox" data-options="required:'required',min:0,precision:2,validType:'intOrFloat[0,100]'" value="${rechargeSetting.ipsRepayRechargeFeeRate}" />%/笔--]
            [#--</td>--]
        [#--</tr>--]
        [#--<tr>--]
            [#--<td>环迅还款充值最低收取</td>--]
            [#--<td>--]
                [#--<input id="ipsRepayRechargeMinFee" name="ipsRepayRechargeMinFee" type="text" class="easyui-numberbox" data-options="required:'required',min:0,precision:2,validType:'intOrFloat[0,100]'" value="${rechargeSetting.ipsRepayRechargeMinFee}" />元/笔--]
            [#--</td>--]
        [#--</tr>--]
        [#--<tr>--]
            [#--<td>环迅还款充值最高收取</td>--]
            [#--<td>--]
                [#--<input id="ipsRepayRechargeMaxFee" name="ipsRepayRechargeMaxFee" type="text" class="easyui-numberbox" data-options="required:'required',min:0,precision:2,validType:'intOrFloat[0,100]'" value="${rechargeSetting.ipsRepayRechargeMaxFee}" />元/笔--]
            [#--</td>--]
        [#--</tr>--]
        <tr>
            <td colspan="2" style="text-align: center;">
                <input class="fa fa-plus" type="submit" value="保存" />
            </td>
        </tr>
    </table>
</form>
[/@insert]