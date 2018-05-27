 <form id="corporationForm" action="${ctx}/admin/article_category/${action}" method="POST">
        <table id="basicInfo" class="tbform">
            <tr>
                <td>文章分类：</td>
                <td>
                    <input id="firstCategory" name="firstCategory" value="${firstCategory}" class="easyui-combobox" data-options="required:'required',valueField: 'id',textField: 'text',url: '${ctx}/admin/article/getSelect',
                                                                        onSelect: function(rec){
                                                                            console.log(rec);
                                                                            var url = '${ctx}/admin/article/getSecondSelect/'+rec.id;
                                                                            $('#secondCategory').combobox('reload', url);
                                                                        }" />
                    <input id="secondCategory" name="secondCategory" value="${secondCategory}" class="easyui-combobox" data-options="required:'required',valueField: 'id',textField: 'text',url: '${ctx}/admin/article/getSelect',
                                                                        onSelect: function(rec){
                                                                            console.log(rec);
                                                                            var url = '${ctx}/admin/article/getSecondSelect/'+rec.id;
                                                                            $('#thirdCategory').combobox('reload', url);
                                                                        }" />
                    <input id="thirdCategory" name="thirdCategory" value="${thirdCategory}" class="easyui-combobox" data-options="valueField:'id',textField:'text'" />
                </td>
            </tr>
            <tr>
                <td>标题：</td>
                <td>
                    <input id="title" name="title" value="${article.title}" class="easyui-textbox" data-options="required:'required'"/>
                    <input id="action" name="action" value="update" hidden/>
                    <input id="pk" name="pk" value="${article.id}" hidden/>
                </td>
            </tr>
            <tr>
                <td>别名：</td>
                <td>
                    <input id="alias" name="alias" value="${article.alias}" class="easyui-textbox" data-options="validType:'engNum',required:'required'"/>
                </td>
            </tr>
            <tr>
                <td>LOGO：</td>
                <td>
                    <div class="input-group">
                        <input id="logo" name="logo" class="easyui-textbox" value="${article.logo}"/>
                        <label class="input-group-addon" onclick="uploadBanner('logo');">上传</label>
                    </div>
                </td>
            </tr>
            <tr>
                <td>封面：</td>
                <td>
                    <input id="cover" name="cover" value="${article.cover}" class="easyui-textbox" data-options="validType:'url'"/>
                </td>
            </tr>
            <tr>
                <td>作者：</td>
                <td>
                    <input id="author" name="author" value="${article.author}" class="easyui-textbox" data-options="validType:'chinese'"/>
                </td>
            </tr>
            <tr>
                <td>排序：</td>
                <td>
                    <input id="sort" name="sort" value="${article.sort}" class="easyui-textbox" data-options="validType:'integer',required:'required'"/>
                </td>
            </tr>
            <tr>
                <td>摘要：</td>
                <td>
                    <input id="remark" name="remark" value="${article.remark}" class="easyui-textbox" data-options="width: 530";/>
                </td>
            </tr>
            <tr>
                <td>内容：</td>
                <td>
                    <textarea id="container" name="cont">${article.cont}</textarea>
                </td>
            </tr>
            <tr>
                <td>是否发布：</td>
                <td>
                    <select id="published" class="easyui-combobox" name="published">
                        <option value="false" [#if !article.published]selected="selected"[/#if]>否</option>
                        <option value="true"  [#if article.published]selected="selected"[/#if]>是</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>是否置顶：</td>
                <td>
                    <select id="top" class="easyui-combobox" name="top">
                        <option value="false" [#if !article.top]selected="selected"[/#if]>否</option>
                        <option value="true"  [#if article.top]selected="selected"[/#if]>是</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>页面标题：</td>
                <td>
                    <input id="pageTitle" name="pageTitle" value="${article.pageTitle}" class="easyui-textbox" data-options="validType:'chsEngNum'"/>
                </td>
            </tr>
            <tr>
                <td>页面关键字：</td>
                <td>
                    <input id="keywords" name="keywords" value="${article.keywords}" class="easyui-textbox" data-options="validType:'chsEngNum'"/>
                </td>
            </tr>
            <tr>
                <td>描述：</td>
                <td>
                    <textarea  id="description" type="" name="description" class="easyui-validatebox" data-options="validType:'maxLength[200]'" >${article.description}</textarea>
                </td>
            </tr>
        </table>
    </form>
</div>

<<script type="text/javascript" charset="UTF-8">
    [#--window.UEDITOR_HOME_URL = '${ctx}/ueditor/';--]
    if (typeof ue != 'undefined') {
        ue.destroy();
    }
    var ue = UE.getEditor('container');
</script>