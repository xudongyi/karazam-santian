[#-- 借款材料 --]
var $materialTable = $("#materialTable tbody");
var materialIndex = ${(materials?size)!"0"};

[#-- 添加借款材料 --]
[#--$("#addMaterial").click(function() {--]
function createMaterial() {

	[@compress single_line = true]
		$materialTable.append("
			<tr id=\"materialTableTr" + materialIndex + "\">
				<td>
					<input name=\"materials[" + materialIndex + "].file\" class=\"easyui-filebox\" data-options=\"width:280,required:'required',buttonText:'选择图片'\">
				</td>
				<td>
					<select name=\"materials[" + materialIndex + "].type\" class=\"easyui-combobox\" data-options=\"width:180,required:'required'\">
						[#list materialTypes as type]
							<option value=\"${type}\">${type.displayName}</option>
						[/#list]
					</select>
				</td>
				<td>
					<input name=\"materials[" + materialIndex + "].title\" type=\"text\" class=\"easyui-textbox\" data-options=\"width:150,required:'required'\"/>
				</td>
                <td>
                    <input name=\"materials[" + materialIndex + "].operator\" type=\"text\" class=\"easyui-textbox\" data-options=\"width:150,required:'required'\"/>
                </td>
				<td>
                    <input name=\"materials[" + materialIndex + "].sort\" type=\"text\" class=\"easyui-numberbox\" data-options=\"width:30,value:0,min:0,precision:0\"/>
				</td>
				<td>
					<a href=\"javascript:void(0)\" class=\"delete deleteMaterial easyui-linkbutton color-four\" iconCls=\"fa fa-remove\">删除</a>
				</td>
			</tr>
		");
		$.parser.parse($("#materialTableTr"+materialIndex));
	[/@compress]
	materialIndex++;

	}
[#--});--]

[#-- 删除借款材料 --]
$materialTable.on("click", "a.deleteMaterial", function(e) {
	e.preventDefault();
	var $this = $(this);
	$this.closest("tr").remove();
});