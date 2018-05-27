/**
 * Created by suhao on 2017/5/15.
 */
$().ready(function() {
    $.getJSON(ctx + '/transfer/data',
        {page: 1, rows: 10},
        function (res) {
            //使用方式跟独立组件完全一样
            var tpl = document.getElementById("dataTpl");
            laytpl(tpl.innerHTML).render(res, function (string) {
                $('#data').html(string);
            });
            laypage({
                skin: '#B81B26',
                cont: 'pagination'
                , pages: res.pages
                , jump: function (e, first) { //触发分页后的回调
                    if (!first) {
                        $.getJSON(ctx + '/transfer/data', {
                            page: e.curr,//当前页
                            rows: res.pageSize
                        }, function (rest) {
                            e.pages = e.last = rest.pages; //重新获取总页数，一般不用写
                            //解析数据
                            laytpl(tpl.innerHTML).render(rest, function (stringt) {
                                $('#data').html(stringt);
                            });
                        });
                    }
                }
            });
        }
    );
});