/**
 * Created by Sue on 2017/6/6.
 */
$(function () {
    CMS.getTaxonomies('category', 'problem', function (data) {
        var tpl = document.getElementById('problemCategoryTpl');
        laytpl(tpl.innerHTML).render(data, function (string) {
            $('#problemCategoryBox').html(string);
        });
        // laypage({
        //     skin: '#50a846',
        //     cont: 'pagination'
        //     , pages: res.pages
        //     , jump: function (e) { //触发分页后的回调
        //         $.getJSON(url,
        //             $.extend({}, {page: e.curr}),
        //             function (rest) {
        //                 if(rest.data.rows.length>0) {
        //                     e.pages = e.last = rest.pages; //重新获取总页数，一般不用写
        //                     laytpl(tpl.innerHTML).render(rest.data, function (stringt) {
        //                         $('#problemCategoryBox').html(stringt);
        //                     });
        //                 } else {
        //                     $('#problemCategoryBox').html("<h3 style='text-align: center;margin-top: 30px'>内容官正在紧急整理材料，敬请期待！</h3>");
        //                 }
        //             });
        //     }
        // });
    });

    CMS.getTaxonomies('category', 'liu_cheng_zhi_yin', function (data) {
        var tpl = document.getElementById('flowBootCategoryTpl');
        laytpl(tpl.innerHTML).render(data, function (string) {
            $('#flowBootCategoryBox').html(string);
        });
    });
});

function getProblemChildTaxContents(type, slug, index) {
    CMS.getContents(type, slug, function (data) {
        // console.log(data);
        if (data.contents.rows.length == 0) {
            return;
        }
        var tpl = document.getElementById('problemChildTaxTpl');
        laytpl(tpl.innerHTML).render(data, function (string) {
            $('#problemChildTaxBox' + index).html(string);
        });
    })
}

function getFlowBootChildTaxContents(type, slug, index) {
    CMS.getContents(type, slug, function (data) {
        // console.log(data);
        if (data.contents.rows.length == 0) {
            return;
        }
        var tpl = document.getElementById('flowBootChildTaxTpl');
        laytpl(tpl.innerHTML).render(data, function (string) {
            $('#flowBootChildTaxBox' + index).html(string);
        });
    })
}