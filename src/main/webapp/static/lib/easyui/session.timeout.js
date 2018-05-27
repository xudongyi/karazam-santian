$(function () {
    var parentWin = window.parent;
    var iframeCount = 0;
    if (parentWin != null) {
        iframeCount = $(parentWin.document).find("iframe").length;
    }
    if (window.location.href.indexOf("admin/login") != -1 && iframeCount > 0) {
        window.parent.location.href = ctx + "/admin/login";
    }

    $.ajaxSetup({
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            if (XMLHttpRequest.status == 403) {
                alert('您没有权限访问此资源或进行此操作');
                return false;
            }
        },
        complete: function (XMLHttpRequest, textStatus) {
            var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus"); //通过XMLHttpRequest取得响应头,sessionstatus，
            if (sessionstatus == 'timeout') {
                //如果超时就处理 ，指定要跳转的页面
                var top = getTopWinow(); //获取当前页面的顶层窗口对象
                alert('登录超时, 请重新登录.');
                top.location.href = ctx + "/admin/login"; //跳转到登陆页面
            }
        }
    });
});

function getTopWinow(){
    var p = window;
    while(p != p.parent){
        p = p.parent;
    }
    return p;
}