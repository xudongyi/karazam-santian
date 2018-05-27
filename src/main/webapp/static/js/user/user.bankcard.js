$.getJSON(ctx + '/uc/bank_card/list.json', {page: 1, rows: 5}, function (res) {

    //使用方式跟独立组件完全一样
    var tpl = document.getElementById("dataTpl");
    laytpl(tpl.innerHTML).render(res, function (string) {
        $('#dataCards').html(string);
    });
    laypage({
        skin: '#B81B26',
        cont: 'pagination'
        , pages: res.pages
        , jump: function (e, first) { //触发分页后的回调
            if (!first) {
                $.getJSON(ctx + '/uc/bank_card/list.json', {
                    page: e.curr,//当前页
                    rows: res.pageSize
                }, function (rest) {
                    e.pages = e.last = rest.pages; //重新获取总页数，一般不用写
                    //解析数据
                    laytpl(tpl.innerHTML).render(rest, function (stringt) {
                        $('#dataCards').html(stringt);
                    });
                });
            }
        }
    });
});

