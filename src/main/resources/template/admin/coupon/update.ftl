[@insert template="admin/layout/default_layout" title="修改优惠券"]
<div>
    <form id="referralForm" action="" method="POST">
        <table id="basicInfo" class="tbform">
            <tr>
                <td>优惠券来源：</td>
                <td>
                    <select id="couponSource" name="couponSource" readonly="true" class="easyui-combobox" data-options="required:'required'">
                        [#list couponSources as couponSource]
                            <option value="${couponSource}"[#if couponSource==coupon.couponSource]selected[/#if]>${couponSource.displayName}</option>
                        [/#list]
                    </select>
                </td>
            </tr>
            <tr>
                <td>优惠券类型：</td>
                <td>
                    <select id="couponType" name="couponType" class="easyui-combobox" data-options="required:'required'">
                        [#list couponTypes as couponType]
                            <option value="${couponType}">${couponType.displayName}</option>
                        [/#list]
                    </select>
                    <input type="hidden" id="action" name="action" value="update"/>
                    <input type="hidden" id="pk" name="pk" value="${coupon.id}"/>
                </td>
            </tr>
            <tr>
                <td>是否随机金额：</td>
                <td>
                    <select id="isRandomAmount" name="isRandomAmount" class="easyui-combobox" data-options="required:'required'">
                        <option value="false" [#if !coupon.isRandomAmount]selected[/#if]>否</option>
                        <option value="true" [#if coupon.isRandomAmount]selected[/#if]>是</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>随机金额最小值：</td>
                <td>
                    <input id="randomAmountMix" name="randomAmountMix" value="${coupon.randomAmountMix}" class="easyui-textbox" data-options="required:'required'"/>
                </td>
            </tr>
            <tr>
                <td>随机金额最大值：</td>
                <td>
                    <input id="randomAmountMax" name="randomAmountMax" value="${coupon.randomAmountMax}" class="easyui-textbox" data-options="required:'required'"/>
                </td>
            </tr>
            <tr>
                <td>优惠券面额：</td>
                <td>
                    <input id="amount" name="amount" value="${coupon.amount}" class="easyui-textbox" data-options="required:'required'"/>
                </td>
            </tr>
            <tr>
                <td>是否可用：</td>
                <td>
                    <select id="available" name="available" class="easyui-combobox" data-options="required:'required'">
                        <option value="true" [#if coupon.available]selected[/#if]>是</option>
                        <option value="false" [#if !coupon.available]selected[/#if]>否</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>优惠券总数：</td>
                <td>
                    <input id="couponNumber" name="couponNumber" value="${coupon.couponNumber}"  class="easyui-textbox" data-options="required:'required'"/>
                </td>
            </tr>
            <tr>
                <td>是否以该类型优惠券失效日期为准：</td>
                <td>
                    <select id="availableByCategory" name="availableByCategory" class="easyui-combobox">
                        <option value="true" [#if coupon.availableByCategory]selected[/#if]>是</option>
                        <option value="false" [#if !coupon.availableByCategory]selected[/#if]>否</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>失效日期：</td>
                <td>
                    <input id="invalidDate" name="invalidDate" value="${coupon.invalidDate}"  class="easyui-datetimebox" data-options="required:'required'"/>
                </td>
            </tr>
            <tr>
                <td>有效期单位：</td>
                <td>
                    <select id="periodUnit" name="couponRule.periodUnit" class="easyui-combobox">
                        [#list periodUnits as periodUnit]
                            <option value="${periodUnit}" [#if periodUnit==couponRule.periodUnit]selected[/#if]>${periodUnit.displayName}</option>
                        [/#list]
                    </select>
                </td>
            </tr>
            <tr>
                <td>有效期：</td>
                <td>
                    <input id="beginPeriod" name="couponRule.term" value="${couponRule.term}"  class="easyui-textbox" data-options="required:'required'"/>
                </td>
            </tr>
            <tr>
                <td>使用起投金额：</td>
                <td>
                    <input id="beginAmount" name="couponRule.beginAmount" value="${couponRule.beginAmount}"  class="easyui-textbox" data-options="required:'required'"/>
                </td>
            </tr>
        </table>
    </form>
</div>
[/@insert]