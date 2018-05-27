[@nestedscript]
    <script>var toshow = ${toshow?string("true", "false")}</script>
    [@js src="js/admin/capital/payment.order.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="支付订单首页"]
<input id="hasMendRecharge" type="hidden" value="${hasMendRecharge}" />
<div id="paymentOrderTable" class="easyui-tabs" data-options="fit:true,border:false">
    [@shiro.hasPermission name="capital:paymentOrder:order:view"]
    <div id="orders" title="前台订单" data-options="refreshable:false">
        <div id="paymentOrderTb" style="padding:5px;height:auto">
            <form id="paymentOrderSearchForm" action="">
                手机号:<input type="text" name="filter_LIKES_mobile" class="easyui-textbox" data-options="prompt: '手机号'"/>
                订单号:<input type="text" name="filter_LIKES_order_no" class="easyui-textbox" data-options="prompt: '订单号'"/>
                订单类型:<select class="easyui-combobox" name="filter_EQS_p.type" style="width:200px;">
                            <option value="">请选择</option>
                            [#list types as type]
                                <option value="${type.name()}">${type.displayName}</option>
                            [/#list]
                        </select>
                订单状态:<select class="easyui-combobox" name="filter_EQS_p.status" style="width:200px;">
                            <option value="">请选择</option>
                            [#list statuses as status]
                                <option value="${status.name()}">${status.displayName}</option>
                            [/#list]
                        </select>
                支付方式:<select class="easyui-combobox" name="filter_EQS_method" style="width:200px;">
                            <option value="">请选择</option>
                            [#list methods as method]
                                <option value="${method.name()}">${method.displayName}</option>
                            [/#list]
                         </select>
                <input type="text" name="filter_GTD_p.create_date" class="easyui-datetimebox" datefmt="yyyy-MM-dd"
                       data-options="width:150,showSeconds:true,prompt: '创建日期起'"/>
                - <input type="text" name="filter_LTD_p.create_date" class="easyui-datetimebox" datefmt="yyyy-MM-dd"
                         data-options="width:150,showSeconds:true,prompt: '创建日期止'"/>
                <span class="toolbar-item dialog-tool-separator"></span>
                <a href="javascript:(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="paymentOrderQuery()">查询</a>
                <a href="javascript:(0)" class="easyui-linkbutton" iconCls="icon-print" plain="true" onclick="exportPaymentOrder()">Excel导出</a>
            </form>
            [#--<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="createUser();">添加</a>--]
            [#--<span class="toolbar-item dialog-tool-separator"></span>--]
        </div>
        <div id="paymentOrdersDg"></div>
    </div>
    [/@shiro.hasPermission]
    [@shiro.hasPermission name="capital:paymentOrder:bgorder:view"]
    <div id="bgorders" title="后台订单" data-options="refreshable:false">
        <div id="paymentBgOrderTb" style="padding:5px;height:auto">
            <form id="paymentBgOrderSearchForm">
                手机号:<input type="text" name="filter_LIKES_mobile" class="easyui-textbox" data-options="prompt: '手机号'"/>
                订单号:<input type="text" name="filter_LIKES_order_no" class="easyui-textbox" data-options="prompt: '订单号'"/>
                订单类型:<select class="easyui-combobox" name="filter_EQS_p.type" style="width:200px;">
                            <option value="">请选择</option>
                            [#list types as type]
                                <option value="${type.name()}">${type.displayName}</option>
                            [/#list]
                         </select>
                订单状态:<select class="easyui-combobox" name="filter_EQS_p.status" style="width:200px;">
                            <option value="">请选择</option>
                            [#list statuses as status]
                                <option value="${status.name()}">${status.displayName}</option>
                            [/#list]
                         </select>
                支付方式:<select class="easyui-combobox" name="filter_EQS_method" style="width:200px;">
                            <option value="">请选择</option>
                            [#list methods as method]
                                <option value="${method.name()}">${method.displayName}</option>
                            [/#list]
                         </select>
                <input type="text" name="filter_GTD_p.create_date" class="easyui-datetimebox" datefmt="yyyy-MM-dd"
                       data-options="width:150,showSeconds:true,prompt: '创建日期起'"/>
                - <input type="text" name="filter_LTD_p.create_date" class="easyui-datetimebox" datefmt="yyyy-MM-dd"
                         data-options="width:150,showSeconds:true,prompt: '创建日期止'"/>
                <span class="toolbar-item dialog-tool-separator"></span>
                <a href="javascript:(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="paymentBgOrderQuery()">查询</a>
                <a href="javascript:(0)" class="easyui-linkbutton" iconCls="icon-print" plain="true" onclick="exportPaymentBgOrder()">Excel导出</a>
            </form>
        </div>
        <div id="paymentBgOrdersDg"></div>
    </div>
    [/@shiro.hasPermission]
</div>
[/@insert]