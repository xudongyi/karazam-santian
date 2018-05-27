<div>
    <form id="mainform" action="${ctx}/admin/corporation/save">
        <input type="hidden" name="id" value="${corporation.id}"/>
        <input type="hidden" name="action" value="${action}"/>
        <table class="tbform">
            <tr>
                <td>公司名称：</td>
                <td>
                    <input id="name" name="corpName" type="text" value="${corporation.corpName}" class="easyui-textbox"
                           data-options="width: 150"/>
                </td>
            </tr>
            <tr>
                <td>法人姓名：</td>
            <td>
                <input id="role" name="corporationName" type="text" value="${corporation.corporationName}" class="easyui-textbox"
                       data-options="width: 150"/>
            </td>
        </tr>
            <tr>
                <td>手机号码：</td>
                <td>
                    <input id="sort" name="corporationMobile" type="text" value="${corporation.corporationMobile}" class="easyui-numberbox" data-options="width: 150" readonly="readonly" />
                </td>
            </tr>
            <tr>
                <td>公司类别：</td>
                <td>
                    <textarea rows="3" cols="30" name="corpType" style="font-size: 12px;font-family: '微软雅黑'" class="easyui-textbox">${corporation.corpType}</textarea>
                </td>
            </tr>
            <tr>
                <td>公司行业：</td>
                <td>
                    <textarea rows="3" cols="30" name="corpDomain" style="font-size: 12px;font-family: '微软雅黑'" class="easyui-textbox">${corporation.corpDomain}</textarea>
                </td>
            </tr>
            <tr>
                <td>公司规模：</td>
                <td>
                    <textarea rows="3" cols="30" name="corpScale" style="font-size: 12px;font-family: '微软雅黑'" class="easyui-textbox">${corporation.corpScale}</textarea>
                </td>
            </tr>
            <tr>
                <td>公司认证：</td>
                <td>
                    <textarea rows="3" cols="30" name="corpCertification" style="font-size: 12px;font-family: '微软雅黑'" class="easyui-textbox">${corporation.corpCertification}</textarea>
                </td>
            </tr>
            <tr>
                <td>公司有无担保资质：</td>
                <td>
                    <select id="top" class="easyui-combobox" name="corpWithGuarantee">
                        <option value="false" [#if !corporation.corpWithGuarantee]selected="selected"[/#if]>否</option>
                        <option value="true"  [#if corporation.corpWithGuarantee]selected="selected"[/#if]>是</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>企业放贷资质：</td>
                <td>
                    <select id="top" class="easyui-combobox" name="enterpriseBorrowingAbility">
                        <option value="false" [#if !corporation.enterpriseBorrowingAbility]selected="selected"[/#if]>否</option>
                        <option value="true"  [#if corporation.enterpriseBorrowingAbility]selected="selected"[/#if]>是</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>公司简介：</td>
                <td>
                    <textarea rows="3" cols="30" name="corpIntro" style="font-size: 12px;font-family: '微软雅黑'" class="easyui-textbox">${corporation.corpIntro}</textarea>
                </td>
            </tr>
            <tr>
                <td>公司资产规模：</td>
                <td>
                    <textarea rows="3" cols="30" name="corpAssetSize" style="font-size: 12px;font-family: '微软雅黑'" class="easyui-textbox">${corporation.corpAssetSize}</textarea>
                </td>
            </tr>
            <tr>
                <td>公司上年度经营额：</td>
                <td>
                    <textarea rows="3" cols="30" name="corpPrevYearOperatedRevenue" style="font-size: 12px;font-family: '微软雅黑'" class="easyui-textbox">${corporation.corpPrevYearOperatedRevenue}</textarea>
                </td>
            </tr>
            <tr>
                <td>公司注册资金：</td>
                <td>
                    <textarea rows="3" cols="30" name="corpRegisteredCapital" style="font-size: 12px;font-family: '微软雅黑'" class="easyui-textbox">${corporation.corpRegisteredCapital}</textarea>
                </td>
            </tr>
            <tr>
                <td>公司所在地：</td>
                <td>
                    <textarea rows="3" cols="30" name="corpLocality" style="font-size: 12px;font-family: '微软雅黑'" class="easyui-textbox">${corporation.corpLocality}</textarea>
                </td>
            </tr>
            <tr>
                <td>公司地址：</td>
                <td>
                    <textarea rows="3" cols="30" name="corpAddr" style="font-size: 12px;font-family: '微软雅黑'" class="easyui-textbox">${corporation.corpAddr}</textarea>
                </td>
            </tr>
            <tr>
                <td>公司邮编：</td>
                <td>
                    <textarea rows="3" cols="30" name="corpZipcode" style="font-size: 12px;font-family: '微软雅黑'" class="easyui-textbox">${corporation.corpZipcode}</textarea>
                </td>
            </tr>
            <tr>
                <td>公司执照编码：</td>
                <td>
                    <textarea rows="3" cols="30" name="corpLicenseNo" style="font-size: 12px;font-family: '微软雅黑'" class="easyui-textbox">${corporation.corpLicenseNo}</textarea>
                </td>
            </tr>
            <tr>
                <td>公司执照签发日期：</td>
                <td>
                    <textarea rows="3" cols="30" name="corpLicenseIssueDate" style="font-size: 12px;font-family: '微软雅黑'" class="easyui-datebox">${corporation.corpLicenseIssueDate}</textarea>
                </td>
            </tr>
            <tr>
                <td>公司国税登记证编号：</td>
                <td>
                    <textarea rows="3" cols="30" name="corpNationalTaxNo" style="font-size: 12px;font-family: '微软雅黑'" class="easyui-textbox">${corporation.corpNationalTaxNo}</textarea>
                </td>
            </tr>
            <tr>
                <td>公司地税登记证编号：</td>
                <td>
                    <textarea rows="3" cols="30" name="corpLandTaxNo" style="font-size: 12px;font-family: '微软雅黑'" class="easyui-textbox">${corporation.corpLandTaxNo}</textarea>
                </td>
            </tr>

        </table>
    </form>
</div>

<script type="text/javascript">
    var action = "${action}";

    $('#mainform').form({
        onSubmit: function () {
            var isValid = $(this).form('validate');
            return isValid;	// 返回false终止表单提交
        },
        success: function (data) {
            data = JSON.parse(data);
            successTip(data, dg, d);
        }
    });

</script>