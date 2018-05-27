<div>
    <form id="corporationForm" action="${ctx}/admin/article_category/${action}" method="POST">
        <table id="basicInfo" class="tbform">
            <tr>
                <td>名称：</td>
                <td>
                    <input id="name" name="name" class="easyui-textbox" data-options="validType:'chsEngNum',required:'required'"/>
                    <input id="action" name="action" value="add" hidden/>
                </td>
            </tr>
            <tr>
                <td>类型：</td>
                <td>
                    <select id="typeStr" name="typeStr" class="easyui-combobox" data-options="width:180,required:'required'">
                        [#list types as type]
                            <option value="${type}">${type.displayName}</option>
                        [/#list]
                    </select>
                </td>
            </tr>
            <tr id="logoTr">
            </tr>
            <tr>
                <td>链接地址：</td>
                <td>
                    <input id="url" name="url" class="easyui-textbox" data-options="required:'required',validType:'url'"/>
                </td>
            </tr>
            <tr>
                <td>链接打开方式：</td>
                <td>
                    <select id="target" class="easyui-combobox" name="target">
                        <option value="_blank">新窗口中打开</option>
                        <option value="_self">相同的框架中打开</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>排序：</td>
                <td>
                    <input id="sort" name="sort" class="easyui-numberbox" invalidMessage="请输入数字" data-options="required:true,validType:'integer'"/>
                </td>
            </tr>
            <tr>
                <td>链接描述：</td>
                <td>
                    <textarea id="description" name="description" class="form-control kind-editor" cols="10" rows="5"></textarea>
                </td>
            </tr>
            <tr>
                <td>是否可见：</td>
                <td>
                    <select id="visible" class="easyui-combobox" name="visible">
                        <option value="false">否</option>
                        <option value="true">是</option>
                    </select>
                </td>
            </tr>
        </table>
    </form>
</div>
[@js src="lib/jquery/jquery.ajaxfileupload.js" /]
[@js src="js/admin/content/link/add.js" /]