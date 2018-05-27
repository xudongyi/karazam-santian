/**
 * Created by suhao on 2017/3/24.
 */

layui.define(['jquery', 'layer'], function (exports) {
    var $ = layui.jquery,
        layer = layui.layer;
    var obj = {
        initOptions: {
            method: "GET",
            contentType: "application/json",
            dataType: "json",
            timeout: 10000,
            cache: false,
            url: '',
            data: ''
        },
        ajax: function (options, callback) {
            var _this = this,
                l = layer.load(1);
            $.extend(_this.initOptions, options);
            $.ajax({
                method: _this.initOptions.method,
                url: _this.initOptions.url,
                dataType: _this.initOptions.dataType,
                data: _this.initOptions.data,
                cache: _this.initOptions.cache,
                timeout: _this.initOptions.timeout,
                error: function (xhr, status, error) {
                    layer.close(l);
                    layer.msg('网络繁忙，请稍后重试...');
                    return;
                },
                success: function (ret) {
                    layer.close(l);
                    if (typeof callback === "function") {
                        callback(ret);
                    } else {
                        layer.msg(ret.message, {time: 1800}, function () {
                            // 关闭父级弹窗
                            parent.layer.closeAll();
                            if(ret.status == "success") {
                                // 父级刷新
                                parent.location.reload();
                            }
                        });
                    }

                }
            });
        },
        get: function (url, data, callback) {
            var _this = this;
            data._method = 'GET';
            var options = {
                method: 'GET',
                url: url,
                data: data
            };
            _this.ajax(options, callback);
        },
        post: function (url, data, callback) {
            var _this = this;
            data._method = 'POST';
            var options = {
                method: 'POST',
                url: url,
                data: data
            };
            _this.ajax(options, callback);
        },
        put: function (url, data, callback) {
            var _this = this;
            data._method = 'PUT';
            var options = {
                method: 'POST',
                url: url,
                data: data
            };
            _this.ajax(options, callback);
        },
        delete: function (url, data, callback) {
            var _this = this;
            data._method = 'DELETE';
            var options = {
                method: 'POST',
                url: url,
                data: data
            };
            _this.ajax(options, callback);
        }
    };

    exports('request', obj);
});
// /**
//  * 获取数据ajax-get请求
//  * @author laixm
//  */
// $.sanjiGetJSON = function (url,data,callback){
//     $.ajax({
//         url:url,
//         type:"get",
//         contentType:"application/json",
//         dataType:"json",
//         timeout:10000,
//         data:data,
//         success:function(data){
//             callback(data);
//         }
//     });
// };
//
// /**
//  * 提交json数据的post请求
//  * @author laixm
//  */
// $.postJSON = function(url,data,callback){
//     $.ajax({
//         url:url,
//         type:"post",
//         contentType:"application/json",
//         dataType:"json",
//         data:data,
//         timeout:60000,
//         success:function(msg){
//             callback(msg);
//         },
//         error:function(xhr,textstatus,thrown){
//
//         }
//     });
// };
//
// /**
//  * 修改数据的ajax-put请求
//  * @author laixm
//  */
// $.putJSON = function(url,data,callback){
//     $.ajax({
//         url:url,
//         type:"put",
//         contentType:"application/json",
//         dataType:"json",
//         data:data,
//         timeout:20000,
//         success:function(msg){
//             callback(msg);
//         },
//         error:function(xhr,textstatus,thrown){
//
//         }
//     });
// };
// /**
//  * 删除数据的ajax-delete请求
//  * @author laixm
//  */
// $.deleteJSON = function(url,data,callback){
//     $.ajax({
//         url:url,
//         type:"delete",
//         contentType:"application/json",
//         dataType:"json",
//         data:data,
//         success:function(msg){
//             callback(msg);
//         },
//         error:function(xhr,textstatus,thrown){
//
//         }
//     });
// };