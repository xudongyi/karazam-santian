[@insert template="admin/layout/default_layout" title="新增用户"]
<div>
    <form id="userForm" action="${ctx}/admin/user/${action}" method="POST">
                <table id="basicInfo" class="tbform">
                    <tr>
                        <td>登录名：</td>
                        <td>
                            <input id="loginName" name="loginName" class="easyui-textbox" data-options="required:'required',validType:'mobile'" />
                            <input id="action" name="action" value="add" hidden/>
                        </td>
                        <td>用户类型：</td>
                        <td>
                            <select id="userType" name="userType" onchange="changeType()" class="easyui-combobox">
                                <option value="GENERAL">个人用户</option>
                                <option value="ENTERPRISE">企业用户</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>昵称：</td>
                        <td>
                            <input id="name" name="name" class="easyui-textbox" data-options="required:'required',validType:'maxLength[20]'"/>
                        </td>
                        <td>密码：</td>
                        <td>
                            <input id="password" type="password" class="easyui-textbox" name="password" data-options="required:'required',validType:'length[6,20]'"/>
                        </td>
                    </tr>
                    <tr>
                        <td>确认密码：</td>
                        <td>
                            <input id="passwordConfirm" type="password" class="easyui-textbox" name="passwordConfirm"  data-options="required:'required',validType:'equals[$(\'#password\').val()]'"/>
                        </td>
                        <td>手机号：</td>
                        <td>
                            <input id="mobile" name="mobile" class="easyui-textbox" type="text" class="easyui-textbox" data-options="required:'required',validType:'mobile'" />
                        </td>
                    </tr>
                    <tr>
                        <td>注册意见：</td>
                        <td>
                            <input id="cont" name="cont" class="easyui-textbox" type="text" data-options="required:'required'" />
                        </td>
                        <td>法人手机号：</td>
                        <td>
                            <input id="ipsMobile" name="ipsMobile" class="easyui-textbox" type="text" class="easyui-textbox" data-options="required:'required',validType:'mobile'" />
                        </td>
                    </tr>
                    <tr>
                        <td>立刻实名认证：</td>
                        <td>
                            <span class="radioSpan">
                                <input type="checkbox" name="identifyFlag" value="0" onclick="isIdentify()">
                            </span>
                        </td>
                    </tr>
                    <tr class="realyInfo">
                        <td>真实姓名：</td>
                        <td>
                            <input id="realName" name="realName" class="easyui-textbox" style="width: 175px;"  data-options="validType:['maxLength[20]','chinese']" />
                        </td>
                        <td>身份证号：</td>
                        <td>
                            <input id="idNo" name="idNo" class="easyui-textbox" style="width: 175px;"  />
                        </td>
                    </tr>
                    <tr style="display: none">
                        <td>
                            <input id="flag" name="flag" value="false" />
                        </td>
                    </tr>
                </table>
    </form>
</div>

[@js src="js/admin/user/userForm.js" /]
[/@insert]