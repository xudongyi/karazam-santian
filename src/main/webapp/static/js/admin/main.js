
layui.define(['jquery', 'form', 'layer', 'ajax', 'modal', 'jqmenu'], function(exports) {
    var $ = layui.jquery,
        layer = layui.layer,
        jqmenu = layui.jqmenu,
        ajax = layui.ajax,
        modal = layui.modal,
        menu = new jqmenu(),
        jqMain = function() {};

    /**
     *@todo 初始化方法
     */
    jqMain.prototype.init = function() {
        this.panelToggle();
        modal.init();
        menu.menuBind();
    };

    /**
     *@todo 绑定面板显示隐藏按钮单击事件
     */
    jqMain.prototype.panelToggle = function() {
        $('.panel-toggle').bind("click", function() {
            var obj = $(this).parent('.panel-heading').next('.panel-body');
            if (obj.css('display') == "none") {
                $(this).find('i').removeClass('icon-shangsvg');
                $(this).find('i').addClass('icon-xiasvg');
                obj.slideDown();
            } else {
                $(this).find('i').removeClass('icon-xiasvg');
                $(this).find('i').addClass('icon-shangsvg');
                obj.slideUp();
            }
        })
    };

    var main = new jqMain();
    main.init();
    exports('main', {});
});