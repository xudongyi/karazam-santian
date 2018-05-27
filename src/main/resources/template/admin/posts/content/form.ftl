[@nestedscript]
    [@js src="ueditor/ueditor.config.js" /]
    [@js src="ueditor/ueditor.all.js" /]
    [@js src="js/admin/posts/content.form.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="撰写文章"]
<div class="easyui-layout" data-options="fit: true" id="tagLayout">
    <form id="contentForm" method="post">
        <div data-options="region:'center',split:true,collapsible:false,border:false,title:'[#if !content??]撰写[#else]编辑[/#if]文章'" style="width: 600px;">
            <input type="hidden" id="contentId" name="id" value="${content.id}"/>
            <input type="hidden" name="action" value="[#if !content??]create[#else]update[/#if]"/>
            <table class="tbform" style="width: 100%;">
                <tr>
                    <td style="width:50px;text-align: left;">标题：</td>
                    <td>
                        <input id="title" name="title" class="easyui-textbox" data-options="width:600,required:'required',validType:'length[2,200]'" missingMessage="标题不能为空" value="${content.title}" />
                    </td>
                </tr>
                <tr>
                    <td style="text-align: left;">别名：</td>
                    <td>
                        <input id="slug" name="slug" class="easyui-textbox" data-options="width:600,required:'required',validType:'length[2,200]'" value="${content.slug}" />
                    </td>
                </tr>
                <tr>
                    <td style="text-align: left;">内容：</td>
                    <td>
                        <textarea id="text" name="text">${content.text}</textarea>
                    </td>
                </tr>
            </table>
        </div>
        <div data-options="region:'east',split:true,border:false" style="width: 400px;">
            <div id="cc" class="easyui-layout" data-options="fit:true">
                <div data-options="region:'north',border:false,split:true" style="height:60px;">
                    <div id="taxonomyBar" style="margin: 10px;">
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'fa fa-arrow-up'" onclick="pushContent('PUBLISH');">发布</a>
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'fa fa-briefcase'" onclick="pushContent('DRAFT');">草稿</a>
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'fa fa-cart-arrow-down'" onclick="pushContent('GARBAGE');">回收</a>
                    </div>
                </div>
                <div data-options="region:'center',border:false" style="padding:5px;background:#eee;">
                    <div class="easyui-accordion" data-options="fit:true">
                        <div title="分类" data-options="iconCls:'fa fa-folder-open-o',selected:true" style="overflow:auto;padding:10px;">
                            <div id="categoryDatagrid"></div>
                        </div>
                        <div title="专题" data-options="iconCls:'fa fa-folder-open-o'" style="padding:10px;">
                            <div id="featureDatagrid"></div>
                        </div>
                        <div title="标签" data-options="iconCls:'fa fa-folder-open-o'">
                            <div id="tagDatagrid"></div>
                        </div>
                        <div title="属性" data-options="iconCls:'fa fa-folder-open-o'">
                            <div class="easyui-tabs" data-options="fit:true">
                                <div title="常用" style="padding:20px;">
                                    <table>
                                        <tr>
                                            <td style="width: 50px;">模板</td>
                                            <td>
                                                <select id="template" class="easyui-combobox" name="template" data-options="width:200">
                                                    [#list templates as template]
                                                        <option value="${template.name}" [#if content.template==template.name]selected[/#if]>${template.des}</option>
                                                    [/#list]
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>过期</td>
                                            <td>
                                                <input id="expire" name="expire" class="easyui-datetimebox" data-options="width:200,validType:'length[2,200]'" value="${content.expire}" prompt="过期时间" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>作者</td>
                                            <td>
                                                <input id="author" name="author" class="easyui-textbox" data-options="width:200,validType:'length[2,200]'" value="${content.author}" prompt="作者" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>摘要</td>
                                            <td>
                                                <input id="summary" name="summary" class="easyui-textbox" data-options="width:250,height:200,multiline:true,validType:'length[2,200]'" value="${content.summary}" prompt="文章摘要" />
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                                <div title="SEO" style="padding:20px;">
                                    <table>
                                        <tr>
                                            <td>关键字</td>
                                            <td>
                                                <input id="metaKeywords" name="metaKeywords" class="easyui-textbox" data-options="width:250,height:100,multiline:true,validType:'length[2,200]'" value="${content.metaKeywords}" prompt="SEO关键字" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>描述</td>
                                            <td>
                                                <input id="metaDescription" name="metaDescription" class="easyui-textbox" data-options="width:250,height:100,multiline:true,validType:'length[2,200]'" value="${content.metaDescription}" prompt="SEO描述内容" />
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                                <div title="备注" style="padding:20px;">
                                    <table>
                                        <tr>
                                            <td>备注信息</td>
                                            <td>
                                                <input id="remarks" name="remarks" class="easyui-textbox" data-options="width:250,height:100,multiline:true,validType:'length[2,200]'" value="${content.remarks}" prompt="备注信息" />
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
[/@insert]