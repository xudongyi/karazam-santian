[@insert template="layout/uc_layout" title="用户中心" module="notice" currentUser=kuser user=kuser]
<!--右边内容-->
<div class="invest">
    <div class="mes-maget">
        <!--通知管理的操作-->
        <table >
            <tr>
                <td>系统通知：</td>
            </tr>
            <tr>
                <td>
                   <br>
                &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea cols="90" rows="10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${notice.cont}</textarea>
                </td>
            </tr>
        </table>

    </div>
</div>

[/@insert]