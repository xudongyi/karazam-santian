[#--借款 基本信息--]
<div class="easyui-panel" data-options="height:460,border:false">
    <table class="tbform">
        <tr>
            <td>用户ID：</td>
            <td>
                <input class="approvalDisabledElement" value="${user.id}"/>
            </td>
            <td>登陆名：</td>
            <td>
                <input class="approvalDisabledElement" value="${user.loginName}"/>
            </td>
        </tr>
        <tr>
            <td>昵称：</td>
            <td>
                <input class="approvalDisabledElement" value="${user.name}"/>
            </td>
            <td>手机号：</td>
            <td>
                <input class="approvalDisabledElement" value="${user.mobile}"/>
            </td>
        </tr>
        <tr>
            <td>真实姓名：</td>
            <td>
                <input class="approvalDisabledElement" value="${user.realName}"/>
            </td>
            <td>身份证号：</td>
            <td>
                <input class="approvalDisabledElement" value="${user.idNo}"/>
            </td>
        </tr>
        <tr>
            <td>出生日期：</td>
            <td>
                <input class="approvalDisabledElement" value="${user.birthday}"/>
            </td>
            <td>用户类型：</td>
            <td>
                <input class="approvalDisabledElement" value="${user.typeStr}"/>
            </td>
        </tr>
        <tr>
            <td>注册日期：</td>
            <td>
                <input class="approvalDisabledElement" value="${user.createDate}"/>
            </td>
            <td>是否注销：</td>
            <td>
                <input class="approvalDisabledElement" value="[#if user.deleted]已注销[#else ]未注销[/#if]"/>
            </td>
        </tr>
        <tr>
            <td>最后一次修改日期：</td>
            <td>
                <input class="approvalDisabledElement" value="${user.modifyDate}"/>
            </td>
            <td>最后一次登陆日期：</td>
            <td>
                <input class="approvalDisabledElement" value="${user.previousVisit}"/>
            </td>
        </tr>
    </table>
</div>