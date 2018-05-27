[@nestedscript]
    [@js src="js/admin/coupon/list.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="优惠券列表"]
<div id="userManage" class="easyui-layout" data-options="fit: true">
    <div data-options="region:'center',split:true,border:false,title:'优惠券列表'" style="width: 500px">
        <div id="tb" style="padding:5px;height:auto">
            <form id="searchForm" style="margin-bottom: 5px;">
                <div>
                    <select name="type" class="easyui-combobox" data-options="width:160,required:'required'">
                        <option value="">--请选择--</option>
                        [#list couponTypes as couponType]
                            <option value="${couponType}">${couponType.displayName}</option>
                        [/#list]
                    </select>
                    <a href="javascript:query();" class="easyui-linkbutton" iconCls="fa fa-search">查询</a>
                    [@shiro.hasPermission name="coupon:admin:create"]
                        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-plus" onclick="createCoupon()">新增</a>
                    [/@shiro.hasPermission]
                    [@shiro.hasPermission name="coupon:admin:update"]
                        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-edit" onclick="updateCoupon()">修改</a>
                    [/@shiro.hasPermission]
                    [@shiro.hasPermission name="coupon:admin:delete"]
                        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-remove" onclick="deleteCoupon()">删除</a>
                    [/@shiro.hasPermission]
                </div>
            </form>
        </div>
        <table id="dg"></table>
        <div id="dialog"></div>
    </div>
</div>
[/@insert]