[@nestedscript]
    [@js src="js/user/notice.page.js" /]
[/@nestedscript]
[@insert template="layout/uc_layout" title="用户中心" module="notice" currentUser=kuser user=kuser]
<!--右边内容-->
<div class="invest">
    <div class="mes-maget">
        <!--通知管理的操作-->
        <div class="maget-crbox">
            <div id="table-one" class="maget-cr">
                <div class="imessage">
                    <div class=" thead">
                        <ul>
                            <li>时间</li>
                            <li>类别</li>
                            <li>发送人</li>
                            <li>摘要</li>
                        </ul>
                    </div>
                    <div class="isread" id="result">

                    </div>
                    <div id="resultPage">

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script id="noticeEach" type="text/html">
    {{#  $.each(d.rows, function(index, item){ }}
    <ul>
        <li>{{item.createDate}}</li>
        <li>系统消息</li>
        <li>华善管理员</li>
        <li><a href="getNoticeDetail/{{item.id}}">{{item.cont}}</a></li>
    </ul><br>
    {{#  }); }}
</script>
[/@insert]