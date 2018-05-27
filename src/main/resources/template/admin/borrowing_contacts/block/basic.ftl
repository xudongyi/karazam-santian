[#--借款联系人--]
<table class="tbform">
    <input type="hidden" name="id" value="${contacts.id}" data-options="required:'required'"/>
    <tr>
        <td>借款：</td>
        <td>
            [#--<input id="borrowing" name="borrowing" class="easyui-combobox" value="${contacts.borrowing}" data-options="required:'required'"/>--]
            <select id="borrowing" name="borrowing"  class="easyui-combobox" data-options="width:180,required:'required'">
                [#list borrowings as borrowing]
                    <option value="${borrowing.id}" [#if borrowing.id==contacts.borrowing]selected[/#if]>${borrowing.title}</option>
                [/#list]
            </select>
        </td>
    </tr>
    <tr>
        <td>联系人类型：</td>
        <td>
            <select name="type"  class="easyui-combobox" data-options="width:180,required:'required'">
                [#list contactsTypes as type]
                    <option value="${type}" [#if type==contacts.type]selected[/#if]>${type.displayName}</option>
                [/#list]
            </select>
        </td>
    </tr>
    <tr>
        <td>姓名：</td>
        <td>
            <input name="name" value="${contacts.name}" type="text" class="easyui-textbox" data-options="width:180,required:'required',validType:'length[1,20]'"/>
        </td>
    </tr>
    <tr>
        <td>手机号码：</td>
        <td>
            <input name="mobile" value="${contacts.mobile}" type="text" class="easyui-textbox" data-options="width:180,validType:'length[11,11]'"/>
        </td>
    </tr>
    <tr>
        <td>座机号码：</td>
        <td>
            <input name="telephone" value="${contacts.telephone}" type="text" class="easyui-textbox" data-options="width:180,validType:'length[1,20]'"/>
        </td>
    </tr>
    <tr>
        <td>备注：</td>
        <td>
            <textarea name="memo" >${contacts.memo}</textarea>
        </td>
    </tr>
</table>