<form id="mainform">
    <input type="hidden" name="id" value="${taxonomy.id}"/>
    <table class="tbform">
        <tr>
            <td style="width: 100px;">是否显示</td>
            <td>
                <select id="display" class="easyui-combobox" name="display" data-options="width:200">
                    <option value="true" [#if taxonomy.display]selected[/#if]>是</option>
                    <option value="false" [#if !taxonomy.display]selected[/#if]>否</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>关键字</td>
            <td>
                <input id="metaKeywords" name="metaKeywords" class="easyui-textbox" data-options="width:450,height:100,multiline:true,validType:'length[2,200]'" value="${taxonomy.metaKeywords}" prompt="SEO关键字" />
            </td>
        </tr>
        <tr>
            <td>描述</td>
            <td>
                <input id="metaDescription" name="metaDescription" class="easyui-textbox" data-options="width:450,height:100,multiline:true,validType:'length[2,200]'" value="${taxonomy.metaDescription}" prompt="SEO描述内容" />
            </td>
        </tr>
    </table>
</form>