
layui.define(['jquery', 'dtable', 'jqdate', 'jqform', 'upload'], function(exports) {
    var $ = layui.jquery,
        list = layui.dtable,
        laydate = layui.laydate,
        form = layui.jqform,
        oneList = new list();
    form.set({
        "change": true,
        "form": "#form1"
    }).init();
    oneList.init('list-tpl');

    //上传文件设置
    layui.upload({
        url: '/php/upload.php',
        before: function(input) {
            box = $(input).parent('form').parent('div').parent('.layui-input-block');
            if (box.next('div').length > 0) {
                box.next('div').html('<div class="imgbox"><p>上传中...</p></div>');
            } else {
                box.after('<div class="layui-input-block"><div class="imgbox"><p>上传中...</p></div></div>');
            }
        },
        success: function(res) {
            if (res.status == 200) {
                box.next('div').find('div.imgbox').html('<img src="' + res.url + '" alt="..." class="img-thumbnail">');
                box.find('input[type=hidden]').val(res.url);
                form.check(box.find('input[type=hidden]'));
            } else {
                box.next('div').find('p').html('上传失败...')
            }
        }
    });

    exports('list', {});
});