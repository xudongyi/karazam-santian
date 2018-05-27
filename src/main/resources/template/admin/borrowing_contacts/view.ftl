[@insert template="admin/layout/default_layout" title="借款详情"]
    <div>
        <form id="borrowingForm" action="${ctx}/admin/borrowing/${action}" method="POST">
            [#include "admin/borrowing_contacts/block/basic.ftl" /]
        </form>
    </div>
[/@insert]