<script>
    var ctx = "${ctx}";
    layui.config({
        base: ctx + '/static/',
        version: "1.3.1"
    }).extend({
        elem: 'js/jqmodules/elem',
        tabmenu: 'js/jqmodules/tabmenu',
        jqmenu: 'js/jqmodules/jqmenu',
        ajax: 'js/jqmodules/ajax',
        request: 'js/jqmodules/request',
        test: 'js/jqmodules/test',
        dtable: 'js/jqmodules/dtable',
        jqdate: 'js/jqmodules/jqdate',
        modal: 'js/jqmodules/modal',
        tags: 'js/jqmodules/tags',
        jqform: 'js/jqmodules/jqform',
        echarts: 'lib/echarts',
        webuploader: 'lib/webuploader',
        index: 'js/admin/index',
        main: 'js/admin/main',
        list: 'js/admin/list'
    });
</script>