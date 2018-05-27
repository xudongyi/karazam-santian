[@insert template="admin/layout/default_layout" title="新增优惠券"]
<div>
    <form id="referralForm" action="" method="POST">
        <table id="basicInfo" class="tbform">
            <tr>
                <td>优惠券来源：</td>
                <td>
                    <select id="couponSource" name="couponSource" class="easyui-combobox" data-options="required:'required'">
                        [#list couponSources as couponSource]
                            <option value="${couponSource}">${couponSource.displayName}</option>
                        [/#list]
                    </select>
                </td>
            </tr>
            <tr>
                <td>优惠券类型：</td>
                <td>
                    <select id="couponType" name="couponType" class="easyui-combobox" data-options="required:'required'">
                        [#list couponTypes as couponType]
                            <option value="${couponType}">${couponType.displayName}</option>
                        [/#list]
                    </select>
                    <input type="hidden" id="action" name="action" value="create"/>
                </td>
            </tr>
            <tr>
                <td>是否随机金额：</td>
                <td>
                    <select id="isRandomAmount" name="isRandomAmount" onchange="changeRadnom()" class="easyui-validatebox" data-options="required:'required'">
                        <option value="true">是</option>
                        <option value="false">否</option>
                    </select>
                </td>
            </tr>
            <tr class="random-range">
                <td>随机金额最小值：</td>
                <td>
                    <input id="randomAmountMix" name="randomAmountMix" class="easyui-textbox" data-options="required:'required',validType:'integerRange[1,50]'"/>
                </td>
            </tr>
            <tr class="random-range">
                <td>随机金额最大值：</td>
                <td>
                    <input id="randomAmountMax" name="randomAmountMax" class="easyui-textbox" data-options="required:'required',validType:'integerRange[1,50]'"/>
                </td>
            </tr>
            <tr class="coupon-amount">
                <td>优惠券面额：</td>
                <td>
                    <input id="amount" name="amount" class="easyui-textbox" data-options="required:'required',validType:'integerRange[1,50]'"/>
                </td>
            </tr>
            <tr>
                <td>是否可用：</td>
                <td>
                    <select id="available" name="available" class="easyui-combobox" data-options="required:'required'">
                        <option value="true">是</option>
                        <option value="false">否</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>优惠券总数：</td>
                <td>
                    <input id="couponNumber" name="couponNumber"  class="easyui-textbox" data-options="required:'required',validType:'integer'"/>
                </td>
            </tr>
            <tr>
                <td>是否以该类型优惠券失效日期为准：</td>
                <td>
                    <select id="availableByCategory" name="availableByCategory" class="easyui-combobox">
                        <option value="true">是</option>
                        <option value="false">否</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>失效日期：</td>
                <td>
                    <input id="invalidDate" name="invalidDate"  class="easyui-datetimebox" data-options="required:'required'"/>
                </td>
            </tr>
            <tr>
                <td>有效期单位：</td>
                <td>
                    <select id="periodUnit" name="couponRule.periodUnit" class="easyui-combobox">
                        [#list periodUnits as periodUnit]
                            <option value="${periodUnit}">${periodUnit.displayName}</option>
                        [/#list]
                    </select>
                </td>
            </tr>
            <tr>
                <td>有效期：</td>
                <td>
                    <input id="beginPeriod" name="couponRule.term"  class="easyui-textbox" data-options="required:'required',validType:'integer'"/>
                </td>
            </tr>
            <tr>
                <td>使用起投金额：</td>
                <td>
                    <input id="beginAmount" name="couponRule.beginAmount"  class="easyui-textbox" data-options="required:'required'"/>
                </td>
            </tr>
            [#--<tr>--]
                [#--<td>使用起投期限：</td>--]
                [#--<td>--]
                    [#--<input id="beginPeriod" name="couponRule.beginPeriod"  class="easyui-textbox" data-options="required:'required'"/>--]
                [#--</td>--]
            [#--</tr>--]
        </table>
    </form>
</div>
<script type="text/javascript">

    function changeRadnom(){
        var n = $("#isRandomAmount").val();
        if (n == 'true'){
            console.log(111);
//            document.getElementById("amount").disabled = true;
            $("#amount").prop("disabled",true);
            $("#randomAmountMix").prop("disabled",false);
            $("#randomAmountMax").prop("disabled",false);
        }else {
            console.log(222);
            $("#randomAmountMix").prop("disabled",true);
            $("#randomAmountMax").prop("disabled",true);
//            document.getElementById("amount").disabled = false;
            $("#amount").prop("disabled","");
        }
    }

    $(document).ready(function(){
//        document.getElementById("amount").disabled = true;
//        $("#amount").prop("disabled",true);
//
//        $("#isRandomAmount").combobox({
//            onChange: function (n, o) {
//                if (n == 'true'){
//                    console.log(111);
//                    $("#amount").prop("disabled",true);
//                    $("#randomAmountMix").prop("disabled",false);
//                    $("#randomAmountMax").prop("disabled",false);
//                }else {
//                    console.log(222);
//                    $("#randomAmountMix").prop("disabled",true);
//                    $("#randomAmountMax").prop("disabled",true);
//                    $("#amount").prop("disabled",false);
//                }
//            }
//        })

    });

</script>
[/@insert]