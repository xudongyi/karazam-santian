[@nestedscript]
    [@js src="js/admin/withdraw/setting.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="提现设置"]
<form id="pushForm" method="post">
    <input type="hidden" name="action" value="withdrawsetting" />
    <table style="margin: 10px; border:1px solid #ccc; font-size: 12px;">
        <tr>
            <td>是否开启提现</td>
            <td>
                <select id="enable" name="enable" class="easyui-combobox">
                    <option value="true" [#if withdrawSetting.enable?string("true", "false")=='true']selected[/#if]>是</option>
                    <option value="false" [#if withdrawSetting.enable?string("true", "false")=='false']selected[/#if]>否</option>
                </select>
            </td>
        </tr>
        <tr style="display: none;">
            <td>环迅手续费</td>
            <td>
                <input id="ipsFee" name="ipsFee" type="text" class="easyui-numberbox" data-options="required:'required',min:0,precision:2,validType:'intOrFloat[0,100]'" value="${withdrawSetting.ipsFee}" />元/笔
            </td>
        </tr>
        <tr style="display: none;">
            <td>是否平台承担环迅手续费</td>
            <td>
                <select id="platformAssumeIpsFee" name="platformAssumeIpsFee" class="easyui-combobox">
                    <option value="false" [#if withdrawSetting.platformAssumeIpsFee?string("true", "false")=='false']selected[/#if]>否</option>
                    <option value="true" [#if withdrawSetting.platformAssumeIpsFee?string("true", "false")=='true']selected[/#if]>是</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>最小提现金额</td>
            <td>
                <input id="beginAmount" name="beginAmount" type="text" class="easyui-numberbox" data-options="required:'required',min:0,precision:2,validType:'intOrFloat[0,100]'" value="${withdrawSetting.beginAmount}" />元
            </td>
        </tr>
        <tr>
            <td>手续费比例</td>
            <td>
                <input id="feeRate" name="feeRate" type="text" class="easyui-numberbox" data-options="required:'required',min:0,precision:2,validType:'intOrFloat[0,100]'" value="${withdrawSetting.feeRate}" />%
            </td>
        </tr>
        <tr>
            <td>
                大于
                <input type="text" class="easyui-numberbox" value="0" readonly />
                小于等于
                <input id="minAmount" name="minAmount" type="text" class="easyui-numberbox" data-options="validType:'intOrFloat[0,9999999]'" value="${withdrawSetting.minAmount}" />
                元，收取
            </td>
            <td>
                <input id="minFee" name="minFee" type="text" class="easyui-numberbox" data-options="validType:'intOrFloat[0,9999999]'" value="${withdrawSetting.minFee}" />元
            </td>
        </tr>
        <tr>
            <td>
                大于
                <input id="minAmountShow" name="minAmountShow" type="text" class="easyui-numberbox" data-options="validType:'intOrFloat[0,9999999]'" value="${withdrawSetting.minAmount!'x,xxx.xx'}" readonly />
                小于等于
                <input id="maxAmount" name="maxAmount" type="text" class="easyui-numberbox" data-options="validType:'intOrFloat[0,9999999]'" value="${withdrawSetting.maxAmount}" />
                元，按
            </td>
            <td>
                <input id="feeRateShow" name="feeRateShow" type="text" class="easyui-numberbox" data-options="required:'required',min:0,precision:2,validType:'intOrFloat[0,100]'" value="${withdrawSetting.feeRate!'x.xx'}" />%收取
            </td>
        </tr>
        <tr>
            <td>
                大于
                <input id="maxAmountShow" name="maxAmountShow" type="text" class="easyui-numberbox" data-options="validType:'intOrFloat[0,9999999]'" value="${withdrawSetting.maxAmount!'x,xxx.xx'}" readonly />元
                收取
            </td>
            <td>
                <input id="maxFee" name="maxFee" type="text" class="easyui-numberbox" data-options="min:0,precision:2" value="${withdrawSetting.maxFee}" />元
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center;">
                <input class="fa fa-plus" type="submit" value="保存" />
            </td>
        </tr>
    </table>
</form>
[/@insert]