<script id="menu-tpl" type="text/html" data-params='{"url":"${ctx}/admin/resource/i/menu.json","listid":"menu","icon":"true"}'>
    {{# layui.each(d.list, function(index, item){ }}
    <li class="layui-nav-item {{# if(index==0){ }}layui-this{{# } }}">
        <a href="javascript:void(0);" data-title="{{item.name}}"><i class="iconfont {{item.icon}}" data-icon='{{item.icon}}'></i><span>{{item.name}}</span></a>
    </li>
    {{# }); }}
</script>

<script id="submenu-tpl" type="text/html">
    {{# layui.each(d.list, function(index, menu){ }}
    <div class="sub-menu">
        <ul class="layui-nav layui-nav-tree">
            {{# layui.each(menu.childs, function(index, submenu){ }} {{# if(submenu.childs && submenu.childs.length>0){ }}
            <li class="layui-nav-item" data-bind="0">
                <a href="javascript:void(0);" data-title="{{submenu.name}}">
                    <i class="iconfont {{submenu.icon}}" data-icon='{{submenu.icon}}'></i>
                    <span>{{submenu.name}}</span>
                    <em class="layui-nav-more"></em>
                </a>
                <dl class="layui-nav-child">
                    {{# layui.each(submenu.childs, function(index, secMenu){ }}
                    <dd>
                        <a href="javascript:void(0);" data-url="${ctx}/{{secMenu.url}}" data-title="{{secMenu.name}}">
                            <i class="iconfont {{secMenu.icon}}" data-icon='{{secMenu.icon}}'></i>
                            <span>{{secMenu.name}}</span>
                        </a>
                    </dd>
                    {{# }); }}
                </dl>
            </li>
            {{# }else{ }}
            <li class="layui-nav-item">
                <a href="javascript:void(0);" data-url="${ctx}/{{submenu.url}}" data-title="{{submenu.name}}">
                    <i class="iconfont {{submenu.icon}}" data-icon='{{submenu.icon}}'></i>
                    <span>{{submenu.name}}</span>
                </a>
            </li>
            {{# } }} {{# }); }}
        </ul>
    </div>
    {{# }); }}
</script>