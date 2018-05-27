[@insert template="admin/layout/default_layout" title="新增分类"]
<div>
    <form [#--action="${ctx}/admin/goods_category/${action}"--] method="POST">
        <table id="basicInfo" class="tbform">

            [#if goodsCategory??]
                <tr>
                    <td>上级分类：</td>
                    <td>
                        <input id="parentName" name="parentName" value="${goodsCategory.name}" readonly class="easyui-textbox" data-options="required:'required'"/>
                        <input id="parent" name="parent" value="${goodsCategory.id}" hidden/>
                    </td>
                </tr>
            [/#if]
            <tr>
                <td>名称：</td>
                <td>
                    <input id="name" name="name" class="easyui-textbox" data-options="required:'required'"/>
                    <input id="action" name="action" value="add" hidden/>
                </td>
            </tr>
            <tr>
                <td>排序：</td>
                <td>
                    <input id="sort" name="sort" class="easyui-textbox" data-options="validType:'integer'"/>
                </td>
            </tr>
            <tr>
                <td>页面标题：</td>
                <td>
                    <input id="seoTitle" name="seoTitle" class="easyui-textbox" [#--data-options="validType:'chsEngNum'"--]/>
                </td>
            </tr>
            <tr>
                <td>页面关键字：</td>
                <td>
                    <input id="seoKeywords" name="seoKeywords" class="easyui-textbox" [#--data-options="validType:'chsEngNum'"--]/>
                </td>
            </tr>
            <tr>
                <td>描述：</td>
                <td>
                    <textarea  id="seoDescription" type="" name="seoDescription" class="easyui-validatebox" data-options="validType:'maxLength[50]'"/>
                </td>
            </tr>
        </table>
    </form>
</div>
[/@insert]