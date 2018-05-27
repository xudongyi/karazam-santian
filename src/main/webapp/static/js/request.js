/**
 * Created by suhao on 2017/3/20.
 */
(function ($) {
    $.request = {
        ajax: function (c, sc, ec) {
            var options = {
                url: "",
                async: false,//true异步请求,false同步请求,注：同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行
                cache: false,
                type: "POST",
                dataType: "json",
                contentType:"application/x-www-form-urlencoded",
                success: function(_data,textStatus, xhr){
                    sc(_data,textStatus, xhr);
                },
                error:function(xhr, errorText, e) {
                    if(xhr.responseText != null && xhr.responseText != "") {
                        var result = eval("("+xhr.responseText+")");
                        if(e != null) {
                            ec(result, e);
                        } else {
                            $.messager.alert('提示信息',result.message,'error');
                        }
                    }
                }
            };
            var n = $.extend(true, options, c);
            $.ajax(n);
        },
        get: function(url, data, successCallback, errorCallback) {
            data._method="GET";
            var options = {
                type: "GET",
                url: ctx + url,
                data: data
            };
            this.ajax(options);
        }
    };
})(jQuery);