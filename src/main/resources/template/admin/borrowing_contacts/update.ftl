[@insert template="admin/layout/default_layout" title="联系人修改"]
    <div>
        <form id="borrowingForm" action="${ctx}/admin/borrowing/contacts/${action}" method="POST">
            [#include "admin/borrowing_contacts/block/basic.ftl" /]
        </form>
    </div>
[/@insert]