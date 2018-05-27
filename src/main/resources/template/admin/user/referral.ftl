[@nestedscript]
    [@js src="js/admin/user/referral.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="推荐关系列表"]
<div id="tb" style="padding:5px;height:auto">
    <form id="searchForm" style="margin-bottom: 5px;">
        <input type="text" name="filter_LIKES_b.mobile" class="easyui-textbox"
               data-options="width:150,prompt: '推荐人手机号'"/>
        <input type="text" name="filter_LIKES_c.mobile" class="easyui-textbox"
               data-options="width:150,prompt: '被推荐人手机号'"/>
        创建时间:<input type="text" name="filter_GED_a.create_date" class="easyui-datebox"
                    data-options="width:150,prompt: '开始日期'"/>
        - <input type="text" name="filter_LED_a.create_date" class="easyui-datebox"
                 data-options="width:150,prompt: '结束日期'"/>
        <span class="toolbar-item dialog-tool-separator"></span>
        <a href="javascript:query();" class="easyui-linkbutton" iconCls="fa fa-search">查询</a>
    </form>
    [@shiro.hasPermission name="user:referral:update"]
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-edit" onclick="update()">修改</a>
    [/@shiro.hasPermission]
</div>
<table id="dg"></table>
<div id="dialog"></div>
[/@insert]