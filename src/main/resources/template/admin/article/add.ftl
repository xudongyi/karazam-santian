<div>
    <form id="corporationForm" action="${ctx}/admin/article_category/${action}" method="POST">
        <div>
            <table id="basicInfo" class="tbform">

                <tr>
                    <td>文章分类：</td>
                    <td>
                        <input id="firstCategory" name="firstCategory" class="easyui-combobox" data-options="required:'required',valueField: 'id',textField: 'text',url: '${ctx}/admin/article/getSelect',
                                                                            onSelect: function(rec){
                                                                                console.log(rec);
                                                                                var url = '${ctx}/admin/article/getSecondSelect/'+rec.id;
                                                                                $('#secondCategory').combobox('reload', url);
                                                                            }" />
                        <input id="secondCategory" name="secondCategory" class="easyui-combobox" data-options="required:'required',valueField: 'id',textField: 'text',url: '${ctx}/admin/article/getSelect',
                                                                            onSelect: function(rec){
                                                                                console.log(rec);
                                                                                var url = '${ctx}/admin/article/getSecondSelect/'+rec.id;
                                                                                $('#thirdCategory').combobox('reload', url);
                                                                            }" />
                        <input id="thirdCategory" name="thirdCategory" class="easyui-combobox" data-options="valueField:'id',textField:'text'" />
                    </td>
                </tr>
                <tr>
                    <td>标题：</td>
                    <td>
                        <input id="title" name="title" class="easyui-textbox" data-options="required:'required'"/>
                        <input id="action" name="action" value="add" hidden/>
                    </td>
                </tr>
                <tr>
                    <td>别名：</td>
                    <td>
                        <input id="alias" name="alias" class="easyui-textbox" data-options="validType:'engNum',required:'required'"/>
                    </td>
                </tr>
                <tr>
                    <td>LOGO：</td>
                    <td>
                        <div class="input-group">
                            <input id="logo" name="logo" class="easyui-textbox" />
                            <label class="input-group-addon" onclick="uploadBanner('logo');">上传</label>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>封面：</td>
                    <td>
                        <input id="cover" name="cover" class="easyui-textbox" data-options="validType:'url'"/>
                    </td>
                </tr>
                <tr>
                    <td>作者：</td>
                    <td>
                        <input id="author" name="author" class="easyui-textbox" data-options="validType:'chinese'"/>
                    </td>
                </tr>
                <tr>
                    <td>排序：</td>
                    <td>
                        <input id="sort" name="sort" class="easyui-textbox" data-options="validType:'integer',required:'required'"/>
                    </td>
                </tr>
                <tr>
                    <td>摘要：</td>
                    <td>
                        <input id="remark" name="remark" class="easyui-textbox" data-options="width: 530"/>
                    </td>
                </tr>
                <tr>
                    <td>内容：</td>
                    <td>
                        <textarea id="container" name="cont"></textarea>
                    </td>
                </tr>
                <tr>
                    <td>是否发布：</td>
                    <td>
                        <select id="published" class="easyui-combobox" name="published">
                            <option value="false">否</option>
                            <option value="true">是</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>是否置顶：</td>
                    <td>
                        <select id="top" class="easyui-combobox" name="top">
                            <option value="false">否</option>
                            <option value="true">是</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>页面标题：</td>
                    <td>
                        <input id="pageTitle" name="pageTitle" class="easyui-textbox" data-options="validType:'chsEngNum'"/>
                    </td>
                </tr>
                <tr>
                    <td>页面关键字：</td>
                    <td>
                        <input id="keywords" name="keywords" class="easyui-textbox" data-options="validType:'chsEngNum'"/>
                    </td>
                </tr>
                <tr>
                    <td>描述：</td>
                    <td>
                        <textarea id="description"name="description" class="easyui-validatebox" data-options="validType:'maxLength[200]'" style="width:200px;"></textarea>
                    </td>
                </tr>
            </table>
        </div>
    </form>
</div>
<script type="text/javascript" charset="UTF-8">
    if (typeof ue != 'undefined') {
        ue.destroy();
    }
    var ue = UE.getEditor('container');
</script>