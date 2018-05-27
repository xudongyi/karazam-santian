[#--借款 担保信息--]
<div class="easyui-panel" data-options="height:460,border:false">
    <table class="tbform">
        <tr>
            <td>担保方式：</td>
            <td>
                <select name="guaranteeMethod" class="easyui-combobox" data-options="width: 180,required:'required',editable:false">
                    [#list guaranteeMethods as guaranteeMethod]
                        <option value="${guaranteeMethod}" [#if guaranteeMethod==borrowing.guaranteeMethod]selected="selected"[/#if]>${guaranteeMethod.displayName}</option>
                    [/#list]
                </select>
            </td>
        </tr>
        <tr>
            <td>担保公司：</td>
            <td>
                <input id="guaranteeCorp" name="guaranteeCorp" value="${borrowing.guaranteeCorp}" data-options="editable:false"/>
            </td>
        </tr>
        <tr>
            <td>担保措施：</td>
            <td>
                <textarea name="guarantee">${borrowing.guarantee!""}</textarea>
            </td>
        </tr>
        <tr>
            <td>抵押物：</td>
            <td>
                <textarea name="collateral">${borrowing.collateral!""}</textarea>
            </td>
        </tr>

        <tr>
            <td>银行账户开户行：</td>
            <td>
                <input id="bankID" name="bankID" value="${borrowing.bankID}" data-options="editable:false"/>
            </td>
        </tr>
        <tr>
            <td>银行账户名称：</td>
            <td>
                <input id="bankAccountName" name="bankAccountName" value="${borrowing.bankAccountName}" data-options="editable:false"/>
            </td>
        </tr>
        <tr>
            <td>银行账户号码：</td>
            <td>
                <input id="bankAccountNumber" name="bankAccountNumber" value="${borrowing.bankAccountNumber}" data-options="editable:false"/>
            </td>
        </tr>
        <tr>
            <td>银行账户分支行：</td>
            <td>
                <input id="bankBranchName" name="bankBranchName" value="${borrowing.bankBranchName}" data-options="editable:false"/>
            </td>
        </tr>
        <tr>
            <td>银行账户省份：</td>
            <td>
                <input id="bankProvince" name="bankProvince" value="${borrowing.bankProvince}" data-options="editable:false"/>
            </td>
        </tr>
        <tr>
            <td>银行账户城市：</td>
            <td>
                <input id="bankCity" name="bankCity" value="${borrowing.bankCity}" data-options="editable:false"/>
            </td>
        </tr>

    </table>
</div>