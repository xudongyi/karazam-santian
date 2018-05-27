[@insert template="admin/layout/default_layout" title="信息录入"]
<div>
    <form id="suggestionForm" action="" method="POST">
                <table id="basicInfo" class="k-table">
                    <tr>
                        [#if type=='connected']
                            <td>用户反馈：</td>
                        [#elseif type=='reject']
                            <td>驳回理由：</td>
                        [#else]
                            <td>通过理由：</td>
                        [/#if]
                        <td>
                            <textarea id="suggestion" name="suggestion" class="easyui-textbox" data-options="multiline:true,width:400,height:150,required:'required',validType:'length[2,100]'"></textarea>
                            <input id="action" name="action" value="suggestion" hidden/>
                            <input id="type" name="type" value="${type}" hidden/>
                            <input id="id" name="id" value="${id}" hidden/>
                        </td>
                    </tr>
                </table>
    </form>
</div>
[/@insert]