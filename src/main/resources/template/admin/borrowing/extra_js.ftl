[#-- 借款材料 --]
var $extraDetail = $("#extraDetail");
var extraDetailIndex = ${((extras[0].details)?size)!"0"};

[#-- 添加借款材料 --]
[#--$("#addMaterial").click(function() {--]
function addExtraDetail() {

	[@compress single_line = true]
    	$extraDetail.append("
			<tr>
				<td>名称</td>
				<td>
					<input name=\"extras[0].details[" + extraDetailIndex + "].extraFieldDes\" value=\"车辆型号\" type=\"text\" class=\"easyui-textbox approvalDisabledElement\" data-options=\"required:'required'\"/>
				</td>
				<td>内容</td>
				<td>
					<input name=\"extras[0].details[" + extraDetailIndex + "].extraFieldValue\" value=\"Land Prado [普拉多]\" type=\"text\" class=\"easyui-textbox approvalDisabledElement\" data-options=\"required:'required'\"/>
				</td>
				<td>
                    <a href=\"javascript:deleteExtraDetail();\" class=\"easyui-linkbutton extraDetailDel\" iconCls=\"fa fa-remove\">删除</a>
				</td>
			</tr>

		");
		$.parser.parse($("#extraDetail"));
	[/@compress]
extraDetailIndex++;

	}
[#--});--]

[#-- 删除借款材料 --]
$extraDetail.on("click", "a.extraDetailDel", function(e) {
	e.preventDefault();
	var $this = $(this);
	$this.closest("tr").remove();
});