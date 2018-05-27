<form id="mainform" class="easyui-token" method="post">
    <input id="optionsType" type="hidden" value="${type}"/>
    <input id="optionsTypeName" type="hidden" value="${type.displayName}"/>
    <table class="tbform">
        [#list records as record]
            <tr>
                <td>${record.name}</td>
                <td>
                    [#if record.dataType == 'BOOLEAN']
                        <select id="keyValue_${record.id}" name="keyValue_${record.id}" class="easyui-combobox">
                            <option value="true" [#if record.keyValue=='true']selected[/#if]>是</option>
                            <option value="false" [#if record.keyValue=='false']selected[/#if]>否</option>
                        </select>
                    [#elseif record.dataType == 'DATE']
                        <input id="keyValue_${record.id}" name="keyValue_${record.id}" class="easyui-datebox" data-options="height:25,dateFmt:'yyyy-MM-dd'" value="${record.keyValue}"/>
                    [#elseif record.dataType == 'DATETIME']
                        <input id="keyValue_${record.id}" name="keyValue_${record.id}" class="easyui-datetimebox" data-options="height:25,dateFmt:'yyyy-MM-dd'" value="${record.keyValue}"/>
                    [#elseif record.dataType == 'DOUBLE']
                        <div class="input-group">
                            <input id="keyValue_${record.id}" name="keyValue_${record.id}" class="easyui-textbox" data-options="validType:'intOrFloat[0,100]'" value="${record.keyValue}" />
                            <label class="input-group-addon">${record.dataUnit.addonName}</label>
                        </div>
                    [#elseif record.dataType == 'INTEGER']
                        <div class="input-group">
                            <input id="keyValue_${record.id}" name="keyValue_${record.id}" class="easyui-textbox" data-options="validType:'integerRange[1,90]'" value="${record.keyValue}" />
                            <label class="input-group-addon">${record.dataUnit.addonName}</label>
                        </div>
                    [#elseif record.dataType == 'EMAIL']
                        <input id="keyValue_${record.id}" name="keyValue_${record.id}" class="easyui-textbox" data-options="validType:'email'" value="${record.keyValue}"/>
                    [#elseif record.dataType == 'IMAGE']
                        [#--<input id="keyValue" name="keyValue_${record.id}" class="easyui-filebox" data-options="width: 300,height:25,buttonText:'上传LOGO',accept:'.png'" value="${record.keyValue}"/>--]
                        <div class="input-group">
                            <input id="keyValue_${record.id}" name="keyValue_${record.id}" class="easyui-textbox" value="${record.keyValue}" />
                            <label class="input-group-addon" onclick="uploadLogo('keyValue_${record.id}');">上传</label>
                        </div>
                    [#else]
                        <input id="keyValue_${record.id}" name="keyValue_${record.id}" class="easyui-textbox" value="${record.keyValue}">
                    [/#if]
                </td>
            </tr>
        [/#list]
    </table>
</form>
[@js src="lib/jquery/jquery.ajaxfileupload.js" /]