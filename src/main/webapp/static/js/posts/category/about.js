/**
 * Created by suhao on 2017/6/7.
 */
$(function () {
    var $taxonomies = $('.taxonomy');
    $.each($taxonomies, function (i, obj) {
        var type = $(obj).attr('taxonomyType');
        var slug = $(obj).attr('taxonomySlug');
        if ($(obj).hasClass('active')) {
            extracted(type, slug);
        }
        $(obj).on('click', function () {
            $(obj).addClass('active').siblings().removeClass('active');
            extracted(type, slug);
        });
    });
});

function extracted(type, slug) {
    CMS.getContents(type, slug, function (data, url) {
        var tpl = document.getElementById('dataTpl');
        laytpl(tpl.innerHTML).render(data, function (string) {
            if (data.contents.rows.length > 0) {
                $('#dataBox').html(string);
            } else {
                $('#dataBox').html("<h3 style='text-align: center;margin-top: 30px'>内容官正在紧急整理材料，敬请期待！</h3>");
            }
        });
        laypage({
            skin: '#50a846',
            cont: 'pagination'
            , pages: data.contents.pages
            , jump: function (e, first) { //触发分页后的回调
                if (!first) {
                    $.getJSON(url,
                        $.extend({}, {page: e.curr}),
                        function (rest) {
                            if (rest.data.contents.rows.length > 0) {
                                e.pages = e.last = rest.data.contents.pages; //重新获取总页数，一般不用写
                                laytpl(tpl.innerHTML).render(rest.data, function (stringt) {
                                    $('#dataBox').html(stringt);
                                });
                            } else {
                                $('#dataBox').html("<h3 style='text-align: center;margin-top: 30px'>内容官正在紧急整理材料，敬请期待！</h3>");
                            }
                        }
                    );
                }
            }
        });
    });
}