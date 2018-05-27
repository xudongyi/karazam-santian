<div>
    <form id="suggestionForm" action="" method="POST">
        <table id="basicInfo" class="k-table">
            <tr>
                <td>审批意见：</td>
                <td>
                    <textarea id="suggestion" name="suggestion" class="easyui-textbox" data-options="multiline:true,width:300,height:150,required:'required',validType:'length[2,100]'"></textarea>
                    <input id="action" name="action" value="suggestion" hidden/>
                    <input id="referralFeeId" name="referralFeeId" value="${referralFeeId}" hidden/>
                </td>
            </tr>
        </table>
    </form>
</div>