[@insert template="admin/layout/default_layout" title="订单发货"]
<div>
    <form method="POST">
        <input id="action" name="action" value="send" hidden/>
        <input id="pk" name="pk" value="${goodsOrder.id}" hidden/>
        <table id="basicInfo" class="tbform">
            <tr>
                <td>订单号：</td>
                <td>
                    <input id="name" name="name" value="${goodsOrder.orderNo}" class="easyui-textbox" readonly />
                </td>
            </tr>
            <tr>
                <td>物流公司：</td>
                <td>
                    <input name="logisticsCorp" class="easyui-textbox" data-options="validType:'maxLength[100]'" />
                </td>
            </tr>
            <tr>
                <td>物流公司网址：</td>
                <td>
                    <input name="logisticsCorpUrl" class="easyui-textbox" data-options="validType:'maxLength[100]'" />
                </td>
            </tr>
            <tr>
                <td>运单号：</td>
                <td>
                    <input name="logisticsNo" class="easyui-textbox" data-options="validType:'engNum'" />
                </td>
            </tr>
        </table>
    </form>
</div>
[/@insert]