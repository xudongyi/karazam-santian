[@insert template="admin/layout/default_layout" title="修改用户"]
<div>
    <form id="userForm" action="${ctx}/admin/user/${action}/${id}" method="POST">
        <table id="basicInfo" class="k-table">
            <tr>
                <td>登录名：</td>
                <td>
                    <input id="loginName" readonly disabled name="loginName" class="easyui-textbox" value="${user.loginName}" data-options="required:'required',validType:'mobile'"/>
                    <input id="action" name="action" value="update" hidden/>
                    <input id="pk" name="pk" value="${id}" hidden/>
                </td>
                <td>昵称：</td>
                <td>
                    <input id="name" name="name" value="${user.name}" class="easyui-textbox" data-options="required:'required',validType:'maxLength[20]'"/>
                </td>
            </tr>
            <tr>
                <td>密码：</td>
                <td>
                    <input id="password" type="password" class="easyui-textbox" name="password" data-options="validType:'length[6,20]'"/>
                </td>
                <td>确认密码：</td>
                <td>
                    <input id="passwordConfirm" type="password" class="easyui-textbox" name="passwordConfirm"  data-options="validType:'equals[$(\'#password\').val()]'"/>
                </td>
            </tr>
            <tr>
                <td>手机号：</td>
                <td>
                    <input id="mobile" name="mobile" value="${user.mobile}" class="easyui-textbox" data-options="required:'required',validType:'mobile'" />
                </td>
                <td>真实姓名：</td>
                <td>
                    <input id="realName" name="realName" class="easyui-textbox" value="${realName!""}" [#if realName!=null]disabled[/#if]  data-options="validType:['maxLength[20]','chinese']" />
                </td>
            </tr>
            <tr class="realyInfo">
                <td>身份证号：</td>
                <td>
                    <input id="idNo" name="idNo" class="easyui-textbox" value="${idNo!""}" [#if idNo!=null]disabled[/#if] />
                </td>
                <td>用户类型：</td>
                <td>
                    <select id="userType" name="userType" disabled class="easyui-combobox">
                        <option value="GENERAL" [#if user.type=='GENERAL']selected[/#if]>个人用户</option>
                        <option value="ENTERPRISE" [#if user.type=='ENTERPRISE']selected[/#if]>企业用户</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>修改意见：</td>
                <td>
                    <input id="cont" name="cont" class="easyui-textbox" type="text" data-options="required:'required'" />
                </td>
                 [#if user.type=='ENTERPRISE']
                <td>法人手机号：</td>
                <td>
                    <input id="ipsMobile" name="ipsMobile" value="${user.ipsMobile}" class="easyui-textbox" type="text" data-options="required:'required',validType:'mobile'" />
                </td>
                 [/#if]
            </tr>
        </table>
    </form>
</div>

[@js src="js/admin/user/updateUserForm.js" /]
[/@insert]