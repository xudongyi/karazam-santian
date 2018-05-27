<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>${title}-管理中心</title>
    [#include "admin/include/common-header.ftl" /]
</head>
<body id="main" class="easyui-layout">
<div data-options="region:'north',border:false" class="super-north" style="height: 50px;">
    <!--顶部-->
    <div class="super-navigation">
        <div class="super-navigation-title">管理系统</div>
        <div class="super-navigation-main">
            <div class="super-setting-left">
                <ul>
                    <li><i class="fa fa-commenting-o color-three"></i></li>
                    <li><i class="fa fa-envelope-o color-three"></i></li>
                    <li><i class="fa fa-bell-o color-three"></i></li>
                </ul>
            </div>
            <div class="super-setting-right">
                <ul>
                    <li class="user">
                        <span>${currentUser.name}</span>
                    </li>
                    <li>
                        <div class="super-setting-icon">
                            <i class="fa fa-gears color-three"></i>
                        </div>
                        <div id="mm" class="easyui-menu">
                            <div>个人中心</div>
                            <div class="menu-sep"></div>
                            <div id="logout">退出</div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
<div id="easyui-layout-west" data-options="region:'west',title:'菜单',border:false">
    <div class="well well-small" style="padding: 5px 5px 5px 5px;">
        <ul id="layout_west_tree"></ul>
    </div>
</div>
<div data-options="region:'center'" style="overflow: hidden;">
    <div id="index_tabs" style="overflow: hidden;" data-options="tabHeight:34">

    </div>
</div>
<div data-options="region:'south',border:false" style="height: 30px;line-height:30px; overflow: hidden;text-align: center;background-color: #eee" >Copyright © 2017 power by <a href="http://www.klzan.com/" target="_blank">Karazam</a></div>
</body>
[#include "admin/include/common-footer.ftl" /]
</html>