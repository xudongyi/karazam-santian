<div>
    <form id="mainform" class="easyui-token" action="${ctx}/mng/user/${action}" method="post">
        <input type="hidden" name="id" value="${sysUser.id}"/>
        <input type="hidden" name="action" value="${action}"/>
        <table class="tbform">
            <tr>
                <td>登录名：</td>
                <td>
                    <input id="loginName" name="loginName" class="easyui-textbox" data-options="required:'required'" missingMessage="登录名不能空" invalidMessage="用户名已存在" value="${sysUser.loginName}">
                </td>
            </tr>
            <tr>
                <td>昵称：</td>
                <td>
                    <input name="name" type="text" value="${sysUser.name}" class="easyui-textbox" data-options="required:'required',validType:'length[2,20]'"/>
                </td>
            </tr>
            <tr>
                <td>手机：</td>
                <td>
                    <input type="text" name="mobile" value="${sysUser.mobile}" class="easyui-textbox" data-options="required:'required',validType:'mobile'"/>
                </td>
            </tr>
            [#if sysUser.loginName!='admin']
                <tr>
                    <td>密码：</td>
                    <td>
                        <input id="password" name="password" type="password" class="easyui-textbox" data-options="[#if action != 'update']required:'required',[/#if]validType:'length[6,20]'"/>
                    </td>
                </tr>
                <tr>
                    <td>确认密码：</td>
                    <td>
                        <input id="confirmPassword" name="confirmPassword" type="password" class="easyui-textbox" data-options="[#if action != 'update']required:'required',[/#if]validType:'equals[$(\'#password\').val()]'"/>
                    </td>
                </tr>
            [/#if]
            <tr>
                <td>出生日期：</td>
                <td>
                    <input name="birthday" type="text" class="easyui-datebox" data-options="dateFmt:'yyyy-MM-dd'" value="[#if sysUser.birthday??]${(user.birthday)?string('yyyy-MM-dd')}[/#if]"/>
                </td>
            </tr>
            <tr>
                <td>性别：</td>
                <td>
                    <input type="radio" id="male" name="gender" value="MALE"/><label for="MALE">男</label>
                    <input type="radio" id="female" name="gender" value="FEMALE"/><label for="FEMALE">女</label>
                </td>
            </tr>
            <tr>
                <td>状态：</td>
                <td>
                    <select name="status" class="easyui-combobox" data-options="width:170">
                        <option value="ENABLE" [#if sysUser.status == 'ENABLE']selected[/#if]>正常</option>
                        <option value="DISABLE" [#if sysUser.status == 'DISABLE']selected[/#if]>禁用</option>
                        <option value="LOCKED" [#if sysUser.status == 'LOCKED']selected[/#if]>锁定</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Email：</td>
                <td><input type="text" name="email" value="${sysUser.email }" class="easyui-textbox" data-options="validType:'email'"/></td>
            </tr>
            <tr>
                <td>描述：</td>
                <td>
                    <textarea class="easyui-textbox" data-options="width:300,height:50" name="description" style="font-size: 12px;font-family: '微软雅黑'">${sysUser.description}</textarea>
                </td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">
    $(function () {
        var action = "${action}";
        //用户 添加修改区分
        if (action == 'create') {
            $("input[name='gender'][value=MALE]").attr("checked", true);
            //用户名存在验证
            $('#loginName').textbox({
                required: true,
                validType: {
                    length: [2, 20],
                    remote: ["${ctx}/admin/sysuser/checkLoginName", "loginName"]
                }
            });
        } else if (action == 'update') {
            $("input[name='loginName']").attr('readonly', 'readonly');
            $("input[name='loginName']").css('background', '#eee')
            $("input[name='gender'][value=${sysUser.gender}]").attr("checked", true);
        }
    });

</script>