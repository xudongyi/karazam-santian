/**
 * Created by suhao on 2017/3/20.
 */
layui.use(['jquery', 'form', 'jqdate', 'request'], function(){
    var $ = layui.jquery,
        form = layui.form(),
        request = layui.request;
    var action = $("input[name=action]").val();
    // 表单提交
    form.on('submit(user-submit)', function(data){
        if (action == 'create') {
            // POST请求
            request.post(ctx+ '/admin/sysuser/create', data.field/*, function(res){
                if(res.status == "success"){
                    layer.msg(res.message,{time:1800},function(){
                        // 关闭父级弹窗
                        parent.layer.closeAll();
                        // 父级刷新
                        parent.location.reload();
                    });
                }
                layer.msg(res.message);
            }*/);
        } else
        if (action == 'update') {
            // PUT请求
            request.put(ctx+ '/admin/sysuser/update', data.field/*, function(res){
                if(res.status == "success"){
                    layer.msg(res.message,{time:1800},function(){
                        // 关闭父级弹窗
                        parent.layer.closeAll();
                        // 父级刷新
                        parent.location.reload();;
                    });
                }
                layer.msg(res.message);
            }*/);
        }

        // 阻止提交
        return false;
    });

});