[@nestedscript]
    [@js src="js/admin/posts/taxonomy.tag.js" /]
    [@js src="js/admin/posts/taxonomy.common.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="标签管理"]
<div class="easyui-layout" data-options="fit: true" id="tagLayout">
    <div data-options="region:'west',split:true,collapsible:false,border:false,title:'标签'" style="width: 400px;">
        <form id="taxonomyForm" method="post">
            <input type="hidden" id="taxonomyId" name="id"/>
            <input type="hidden" name="action" value="create"/>
            <input type="hidden" name="type" value="tag"/>
            <input type="hidden" name="display" value="true"/>
            <table class="tbform">
                <tr style="display: none;">
                    <td style="width: 80px;">模板：</td>
                    <td>
                        <select id="template" class="easyui-combobox" name="template" data-options="width:200">
                            [#list templates as template]
                                <option value="${template.name}">${template.des}</option>
                            [/#list]
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>名称：</td>
                    <td>
                        <input id="title" name="title" class="easyui-textbox" data-options="width:200,required:'required'" missingMessage="标签不能为空" invalidMessage="标签已存在" value="${taxonomy.title}" />
                    </td>
                </tr>
                <tr>
                    <td>别名：</td>
                    <td>
                        <input id="slug" name="slug" class="easyui-textbox" data-options="width:200,required:'required',validType:'length[2,200]'" value="${taxonomy.slug}" />
                    </td>
                </tr>
                <tr>
                    <td>描述：</td>
                    <td>
                        <textarea id="text" name="text" class="easyui-textbox" data-options="multiline:true,width:300,height:50">${taxonomy.text}</textarea>
                    </td>
                </tr>
                [#--<tr>--]
                    [#--<td>导航菜单：</td>--]
                    [#--<td>--]
                        [#--<input type="checkbox" name="linkToMenu" />--]
                    [#--</td>--]
                [#--</tr>--]
                <tr>
                    <td></td>
                    <td>
                        <button type="submit" class="btn">保存</button>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',split:true,border:false,title:'标签列表'" style="width: 300px;">
        <div id="taxonomyBar">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'fa fa-edit'" onclick="updateTaxonomy();">修改</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'fa fa-cog'" onclick="taxonomySetting();">设置</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'fa fa-remove'" onclick="deltetTaxonomy();">删除</a>
        </div>
        <div id="taxonomyDatagrid"></div>
    </div>
</div>
[/@insert]