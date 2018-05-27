[[@nestedscript]
    [@js src="js/admin/sysuser.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="新增广告位"]
    <form id="corporationForm" action="" method="POST">
        [#--<div class="easyui-tabs" style="width:auto;height:auto;">--]
            [#--<div title="广告位信息" style="padding:auto;">--]
                <table id="basicInfo" class="tbform">
                    <tr>
                        <td>名称：</td>
                        <td>
                            <input id="name" name="name" class="easyui-textbox" data-options="validType:'chsEngNum',required:'required'"/>
                            <input id="action" name="action" value="add" hidden/>
                        </td>
					</tr>
                    <tr>
                        <td>标示：</td>
                        <td>
                            <input id="ident" name="ident" class="easyui-textbox" data-options="validType:'eng',required:'required'"/>
                        </td>
					</tr>
                    <tr>
                        <td>宽度：</td>
                        <td>
                            <input id="width" name="width" class="easyui-textbox" data-options="validType:'num'"/>
                        </td>
                    </tr>
                    <tr>
                        <td>高度：</td>
                        <td>
                            <input id="height" name="height" class="easyui-textbox" data-options="validType:'num'"/>
                        </td>
                    </tr>
                    <tr>
                        <td>描述：</td>
                        <td>
                            <textarea id="description" name="description" class="form-control kind-editor" cols="10" rows="5"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td>模板：</td>
                        <td>
                            <textarea id="template" name="template" class="form-control kind-editor" data-options="required:'required'" cols="10" rows="5"></textarea>
                        </td>
                    </tr>
                </table>
            [#--</div>--]
        [#--</div>--]
    </form>
</div>
[/@insert]