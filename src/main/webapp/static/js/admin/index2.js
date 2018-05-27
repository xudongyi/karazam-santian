
layui.define(['jquery', 'elem', 'jqmenu', 'layer'], function(exports) {
    var $ = layui.jquery,
        element = layui.elem(),
        menu = layui.jqmenu,
        layer = layui.layer,
        oneMenu = new menu();
    jqIndex = function() {};
    /**
     *@todo 初始化方法
     */
    jqIndex.prototype.init = function(options) {

        oneMenu.init('menu-tpl');
        this.showMenu();
        this.refresh();
    };

    /**
     *@todo 绑定刷新按钮单击事件
     */
    jqIndex.prototype.refresh = function() {
        $('.fresh-btn').bind("click", function() {
            $('.jqadmin-body .layui-show').children('iframe')[0].contentWindow.location.reload(true);
        })
    };

    /**
     *@todo 绑定左侧菜单显示隐藏按钮单击事件
     */
    jqIndex.prototype.showMenu = function() {
        $('.menu-type').bind("click", function() {
            if ($(window).width() < 450) {
                $('.jqadmin-main-menu .layui-nav').show();
            }
            var type = parseInt($(this).data('type'));
            oneMenu.menuShowType($(this).data('type'));
            if (type >= 3) type = 0;
            $(this).data('type', type + 1);

        })

        $('.menu-btn').click(function() {
            oneMenu.showLeftMenu($(this));
        })
    };
    $('.admin-side-full').on('click', function() {
        var docElm = document.documentElement;
        //W3C

        if(docElm.requestFullscreen) {
            docElm.requestFullscreen();
        }
        //FireFox

        else if(docElm.mozRequestFullScreen) {
            docElm.mozRequestFullScreen();
        }
        //Chrome等

        else if(docElm.webkitRequestFullScreen) {
            docElm.webkitRequestFullScreen();
        }
        //IE11

        else if(elem.msRequestFullscreen) {
            elem.msRequestFullscreen();
        }
        layer.msg('按Esc即可退出全屏');
    });

    var index = new jqIndex();
    index.init();
    exports('index', {});
});