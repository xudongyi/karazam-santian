[#--借款 材料信息--]
<div class="easyui-panel" data-options="height:460,border:false" style="padding:5px 0 0 5px;">
    <div class="form-group">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-plus" onclick="createMaterial()">添加</a>
    </div>
    <table id="materialTable" class="tbform" style="width: 97%;">
        <tr>
            <td>文件</td>
            <td>类型</td>
            <td>标题</td>
            <td>审核人</td>
            <td>排序</td>
            <td>操作</td>
        </tr>
        [#list materials as material]
            <input type="hidden" name="materials[${material_index}].id" value="${material.id}"/>
            <tr>
                <td>
                    <input type="text" name="materials[${material_index}].source" value="${material.source}" data-options="width:120"/>
                    <input class="easyui-filebox" accept="image/*" name="materials[${material_index}].file" value="" class="easyui-filebox" data-options="width:60,buttonText:'修改图片'"/>
                    <a href="${dfsUrl}${material.source}" class="easyui-linkbutton color-four" target="_blank">查看</a>
                </td>
                <td>
                    <select name="materials[${material_index}].type" value="${material.type}" class="easyui-combobox" data-options="width:150,required:'required'">
                        [#list materialTypes as type]
                            <option value="${type}" [#if type == material.type]selected="selected"[/#if]>${type.displayName}</option>
                        [/#list]
                    </select>
                </td>
                <td>
                    <input name="materials[${material_index}].title" value="${material.title}" type="text" class="easyui-textbox" data-options="width:150,required:'required'"/>
                </td>
                <td>
                    <input name="materials[${material_index}].operator" value="${material.operator}" type="text" class="easyui-textbox" data-options="width:150,required:'required'"/>
                </td>
                <td>
                    <input name="materials[${material_index}].sort" value="${material.sort}" type="text" class="easyui-numberbox" data-options="width:30,min:0,precision:0"/>
                </td>
                <td>
                    <a href="javascript:void(0)" class="deleteMaterial easyui-linkbutton color-four" iconCls="fa fa-remove">删除</a>
                </td>
            </tr>
        [/#list]
    </table>
</div>

