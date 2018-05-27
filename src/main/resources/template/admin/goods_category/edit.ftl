[@insert template="admin/layout/default_layout" title="修改分类"]
<div>
    <form [#--action="${ctx}/admin/goods_category/${action}"--] method="POST">
        <input id="action" name="action" value="update" hidden/>
        <input id="action" name="pk" value="${goodsCategory.id}" hidden/>
        <table id="basicInfo" class="tbform">
            [#if parentgoodsCategory??]
                <tr>
                    <td>上级分类：</td>
                    <td>
                        <input id="parentName" name="parentName" value="${parentgoodsCategory.name}" readonly class="easyui-textbox" data-options="required:'required'"/>
                        <input id="parent" name="parent" value="${parentgoodsCategory.id}" hidden/>
                    </td>
                </tr>
            [/#if]
            <tr>
                <td>名称：</td>
                <td>
                    <input id="name" name="name" value="${goodsCategory.name}" class="easyui-textbox" data-options="required:'required'"/>
                </td>
            </tr>
            <tr>
                <td>排序：</td>
                <td>
                    <input id="sort" name="sort" value="${goodsCategory.sort}" class="easyui-textbox" data-options="validType:'integer'"/>
                </td>
            </tr>
            <tr>
                <td>页面标题：</td>
                <td>
                    <input id="seoTitle" name="seoTitle" value="${goodsCategory.seoTitle}" class="easyui-textbox" [#--data-options="validType:'chsEngNum'"--]/>
                </td>
            </tr>
            <tr>
                <td>页面关键字：</td>
                <td>
                    <input id="seoKeywords" name="seoKeywords" value="${goodsCategory.seoKeywords}" class="easyui-textbox" [#--data-options="validType:'chsEngNum'"--]/>
                </td>
            </tr>
            <tr>
                <td>描述：</td>
                <td>
                    <textarea  id="seoDescription" type="" name="seoDescription" value="" class="easyui-validatebox" data-options="validType:'maxLength[50]'">${goodsCategory.seoDescription}</textarea>
                </td>
            </tr>
        </table>
    </form>
</div>
[/@insert]