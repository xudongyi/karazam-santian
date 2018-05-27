[@insert template="admin/layout/default_layout" title="借款修改"]
<div>
    <form id="referralForm" action="" method="POST">
        <table id="basicInfo" class="tbform">
            <tr>
                <td>推荐人昵称：</td>
                <td>
                    <input type="hidden" name="userId" id="userId" value="${referral.userId}" class="easyui-textbox" />
                    <input type="hidden" id="action" name="action" value="update"/>
                    <input type="hidden" id="pk" name="pk" value="${id}"/>
                    <input type="text" name="userNickName" id="userNickName" value="${referral.userNickName}" class="easyui-textbox" data-options="width:180, readonly:true" />
                    <span class="fa fa-search keasy-choose-user" style="cursor: pointer; padding:10px;" title="查询" data-options="id:'userId', show:'userNickName',singleSelect:true,userType:'GENERAL'"></span>
                    [#--<input id="userNickName" name="userNickName" class="easyui-validatebox" value="${referral.userNickName}" data-options="required:'required'"/>--]
                </td>
            </tr>
            <tr>
                <td>推荐人手机号：</td>
                <td>
                    <input id="userMobile" name="userMobile" class="easyui-textbox" value="${referral.userMobile}" data-options="required:'required'" disabled/>
                </td>
            </tr>
            <tr>
                <td>被推荐人昵称：</td>
                <td>
                    <input id="reUserNickName" class="easyui-textbox" name="reUserNickName" value="${referral.reUserNickName}" disabled/>
                </td>
            </tr>
            <tr>
                <td>被推荐人手机号：</td>
                <td>
                    <input id="reUserMobile" class="easyui-textbox" name="reUserMobile" value="${referral.reUserMobile}" disabled/>
                </td>
            </tr>
            <tr>
                <td>推荐费率：</td>
                <td>
                    <input id="referralFeeRate" name="referralFeeRate" value="${referral.referralFeeRate}" class="easyui-textbox" data-options="required:'required'"/>
                </td>
            </tr>
            <tr>
                <td>是否有效：</td>
                <td>
                    <select id="available" name="available" class="easyui-combobox">
                        <option value="true" [#if referral.available] selected[/#if]>是</option>
                        <option value="false" [#if !referral.available] selected[/#if]>否</option>
                    </select>
                </td>
            </tr>
        </table>
    </form>
</div>
[/@insert]