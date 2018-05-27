/**
 * Created by suhao on 2017/3/24.
 */

layui.define('layer', function(exports){ //提示：模块也可以依赖其它模块，如：layui.define('layer', callback);
    var obj = {
        hello: function(str){
            layer.msg('Hello '+ (str||'test'));
        }
    };

    //输出test接口
    exports('test', obj);
});
