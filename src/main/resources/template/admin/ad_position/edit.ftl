[@insert template="admin/layout/default_layout" title="修改广告位"]
<div>
    <form id="corporationForm" action="" method="POST">
        [#--<div class="easyui-tabs" style="width:auto;height:auto;">--]
            [#--<div title="广告位信息" style="padding:auto;">--]
                <table id="basicInfo" class="tbform">
                    <tr>
                        <td>名称：</td>
                        <td>
                            <input id="name" name="name" value="${adPosition.name}" class="easyui-textbox" data-options="validType:'chsEngNum',required:'required'"/>
                            <input id="action" name="action" value="update" hidden/>
                            <input id="pk" name="pk" value="${adPosition.id}" hidden/>
                        </td>
                    </tr>
                    <tr>
                        <td>标示：</td>
                        <td>
                            <input id="ident" name="ident" value="${adPosition.ident}" class="easyui-textbox" data-options="validType:'eng',required:'required'"/>
                        </td>
                    </tr>
                    <tr>
                        <td>宽度：</td>
                        <td>
                            <input id="width" name="width" value="${adPosition.width}" class="easyui-textbox" data-options="validType:'num'"/>
                        </td>
                    </tr>
                    <tr>
                        <td>高度：</td>
                        <td>
                            <input id="height" name="height" value="${adPosition.height}" class="easyui-textbox" data-options="validType:'num'"/>
                        </td>
                    </tr>
                    <tr>
                        <td>描述：</td>
                        <td>
                            <textarea id="description" name="description" class="form-control kind-editor" cols="10" rows="5">${adPosition.description}</textarea>
                        </td>
                    </tr>
                    <tr>
                        <td>模板：</td>
                        <td>
                            <textarea id="template" name="template" class="form-control kind-editor" data-options="required:'required'" cols="10" rows="5">${adPosition.template}</textarea>
                        </td>
                    </tr>
                </table>
            [#--</div>--]
        [#--</div>--]
    </form>
</div>
[/@insert]