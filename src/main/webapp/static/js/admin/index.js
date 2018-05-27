var index_tabs;
var layout_west_tree;
var indexTabsMenu;
$(function() {
    $('#index_layout').layout({fit : true});

    index_tabs = $('#index_tabs').tabs({
        fit : true,
        border : false,
        onContextMenu : function(e, title) {
            e.preventDefault();
            indexTabsMenu.menu('show', {
                left : e.pageX,
                top : e.pageY
            }).data('tabTitle', title);
        },
        tools : [{
            iconCls : 'fa fa-home',
            handler : function() {
                index_tabs.tabs('select', 0);
            }
        }, {
            iconCls : 'fa fa-refresh',
            handler : function() {
                refreshTab();
            }
        }, {
            iconCls : 'fa fa-close',
            handler : function() {
                var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
                var tab = index_tabs.tabs('getTab', index);
                if (tab.panel('options').closable) {
                    index_tabs.tabs('close', index);
                }
            }
        } ]
    });
    // 选项卡菜单
    indexTabsMenu = $('#tabsMenu').menu({
        onClick : function(item) {
            var curTabTitle = $(this).data('tabTitle');
            var type = $(item.target).attr('type');
            if (type === 'refresh') {
                refreshTab();
                return;
            }
            if (type === 'close') {
                var t = index_tabs.tabs('getTab', curTabTitle);
                if (t.panel('options').closable) {
                    index_tabs.tabs('close', curTabTitle);
                }
                return;
            }
            var allTabs = index_tabs.tabs('tabs');
            var closeTabsTitle = [];
            $.each(allTabs, function() {
                var opt = $(this).panel('options');
                if (opt.closable && opt.title != curTabTitle
                    && type === 'closeOther') {
                    closeTabsTitle.push(opt.title);
                } else if (opt.closable && type === 'closeAll') {
                    closeTabsTitle.push(opt.title);
                }
            });
            for ( var i = 0; i < closeTabsTitle.length; i++) {
                index_tabs.tabs('close', closeTabsTitle[i]);
            }
        }
    });

    layout_west_tree = $('#layout_west_tree').tree({
        url : ctx + '/admin/resource/i/menu.json',
        lines : true,
        onClick : function(node) {
            var opts = {
                title : node.text,
                border : false,
                closable : true,
                fit : true
            };
            if (node.iconCls) {
                opts.iconCls = node.iconCls
            }
            var url = node.url;
            if (url && url.indexOf("http") == -1) {
                url = ctx + '/' + url;
                opts.content = '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.5%;"></iframe>';
                addTab(opts);
            }
        },
        onSelect:function(node){
            var state = node.state;
            if (state == 'open') {
                $(this).tree('collapse',node.target);
            } else {
                $(this).tree('expand',node.target);
            }
        }
    });

    addTab({
        title : '首页',
        border : false,
        closable : false,
        fit : true,
        content : '<iframe src="' + ctx + '/admin/welcome' + '" frameborder="0" style="border:0;width:100%;height:99.5%;"></iframe>'
    })
});

function addTab(opts) {
    var t = $('#index_tabs');
    if (t.tabs('exists', opts.title)) {
        t.tabs('select', opts.title);
    } else {
        t.tabs('add', opts);
    }
}

function refreshTab() {
    var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
    var tab = index_tabs.tabs('getTab', index);
    var options = tab.panel('options');
    if (options.content) {
        index_tabs.tabs('update', {
            tab: tab,
            options: {
                content: options.content
            }
        });
    } else {
        tab.panel('refresh', options.href);
    }
}

/*设置按钮的下拉菜单*/
$('.super-setting-icon').on('click', function() {
    $('#mm').menu('show', {
        top: 50,
        left: document.body.scrollWidth - 130
    });
});

/*退出系统*/
$("#logout").on('click', function() {
    $.messager.confirm('提示', '确定退出系统？', function(r) {
        if(r) {
            window.location.href = ctx + '/admin/logout';
        }
    });
});