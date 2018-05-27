<script> var dizhi="${links.logo}"</script>
<div>
    <form id="corporationForm" action="${ctx}/admin/article_category/${action}" method="POST">
        <table id="basicInfo" class="tbform">
            <tr>
                <td>名称：</td>
                <td>
                    <input id="name" name="name" value="${links.name}" class="easyui-textbox" data-options="validType:'chsEngNum',required:'required'"/>
                    <input id="action" name="action" value="update" hidden/>
                    <input id="id" name="id" value="${links.id}" hidden/>
                </td>
            </tr>
            <tr>
                <td>类型：</td>
                <td>
                    <select id="type" name="type" class="easyui-commbox" data-options="required:'required'">
                        [#list types as tp]
                            <option value="${tp.name()}" [#if tp==links.type]selected[/#if]>${tp.displayName}</option>
                        [/#list]
                    </select>
                </td>
            </tr>
            <tr id="logoTr">
            <tr>
                <td>链接地址：</td>
                <td>
                    <input id="url" name="url" value="${links.url}" class="easyui-textbox" data-options="required:'required',validType:'url'"/>
                </td>
            </tr>
            <tr>
                <td>链接打开方式：</td>
                <td>
                    <select id="target" class="easyui-combobox" name="target">
                        <option value="_blank" [#if links.target==_blank]selected[/#if]>新窗口中打开</option>
                        <option value="_self" [#if links.target==_self]selected[/#if]>相同的框架中打开</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>排序：</td>
                <td>
                    <input id="sort" name="sort" value="${links.sort}" class="easyui-textbox" data-options="validType:'integer'"/>
                </td>
            </tr>
            <tr>
                <td>链接描述：</td>
                <td>
                    <textarea id="description" name="description" class="form-control kind-editor" cols="10" rows="5">${links.description}</textarea>
                </td>
            </tr>
            <tr>
                <td>是否可见：</td>
                <td>
                    <select id="visible" class="easyui-combobox" name="visible">
                        <option value="false" [#if !links.visible]selected[/#if]>否</option>
                        <option value="true" [#if links.visible]selected[/#if]>是</option>
                    </select>
                </td>
            </tr>
        </table>
    </form>
</div>
[@js src="lib/jquery/jquery.ajaxfileupload.js" /]
[@js src="js/admin/content/link/edit.js" /]