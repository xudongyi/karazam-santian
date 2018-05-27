[@insert template="admin/layout/default_layout" title="新增分类"]
<div>
    <form id="corporationForm" action="${ctx}/mng/article_category/${action}" method="POST">
                <table id="basicInfo" class="tbform">
                    [#if parentArticleCategory??]
                        <tr>
                            <td>上级分类：</td>
                            <td>
                                <input id="parentName" name="parentName" value="${parentArticleCategory.name}" readonly class="easyui-textbox" data-options="required:'required'"/>
                                <input id="parent" name="parent" value="${parentArticleCategory.id}" hidden/>
                            </td>
                        </tr>
                    [/#if]
                    <tr>
                        <td>名称：</td>
                        <td>
                            <input id="name" name="name" value="${articleCategory.name}" class="easyui-textbox" data-options="required:'required'"/>
                            <input id="action" name="action" value="update" hidden/>
                            <input id="pk" name="pk" value="${id}" hidden/>
                        </td>
                    </tr>
                    <tr>
                        <td>别名：</td>
                        <td>
                            <input id="alias" name="alias" value="${articleCategory.alias}" class="easyui-textbox" data-options="required:'required'"/>
                        </td>
                    </tr>
                    <tr>
                        <td>模板：</td>
                        <td>
                            <input id="template" name="template" value="${articleCategory.template}" class="easyui-textbox" data-options="required:'required'"/>
                        </td>
                    </tr>
                    <tr>
                        <td>排序：</td>
                        <td>
                            <input id="sort" name="sort" value="${articleCategory.sort}" class="easyui-textbox" data-options="validType:'integer'"/>
                        </td>
                    </tr>
                    <tr>
                        <td>页面标题：</td>
                        <td>
                            <input id="pageTitle" name="pageTitle" value="${articleCategory.seo.pageTitle}" class="easyui-textbox" data-options="validType:'chsEngNum'"/>
                        </td>
                    </tr>
                    <tr>
                        <td>页面关键字：</td>
                        <td>
                            <input id="keywords" name="keywords" value="${articleCategory.seo.keywords}" class="easyui-textbox" data-options="validType:'chsEngNum'"/>
                        </td>
                    </tr>
                    <tr>
                        <td>描述：</td>
                        <td>
                            <textarea  id="description" name="description"  class="easyui-validatebox" data-options="validType:'maxLength[200]'">${articleCategory.seo.description}</textarea>
                        </td>
                    </tr>
                </table>
    </form>
</div>
[/@insert]