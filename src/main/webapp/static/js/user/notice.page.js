$.getJSON(ctx + '/uc/getnotice', {page: 1, rows: 5}, function (res) {

    //使用方式跟独立组件完全一样
    var tpl = document.getElementById("noticeEach");
    laytpl(tpl.innerHTML).render(res, function (string) {
        $('#result').html(string);
    });
    laypage({
        cont: 'resultPage'
        , pages: res.pages
        , jump: function (e) { //触发分页后的回调
            $.getJSON(ctx + '/uc/getnotice', {
                page: e.curr,//当前页
                rows: res.pageSize
            }, function (rest) {
                e.pages = e.last = rest.pages; //重新获取总页数，一般不用写
                //解析数据
                laytpl(tpl.innerHTML).render(rest, function (stringt) {
                    $('#result').html(stringt);
                });
            });
        }
    });
});