/**
 * Created by Sue on 2017/6/6.
 */
$(function () {
    CMS.getTaxonomies('category', 'problem', function (data) {
        var tpl = document.getElementById('problemCategoryTpl');
        laytpl(tpl.innerHTML).render(data, function (string) {
            $('#problemCategoryBox').html(string);
            $('li.test>a.category').on('click', function () {
                var $this = $(this);
                $('#help-menu').find('a').removeClass('active');
                $this.addClass('active').parent().siblings('.test').find('a').removeClass('active');
                getProblemChildTaxContents($this.attr('taxonomyType'), $this.attr('taxonomySlug'))
            });
            var active = $('li.test>a.active');
            getProblemChildTaxContents(active.attr('taxonomyType'), active.attr('taxonomySlug'))
        });
    });

    CMS.getTaxonomies('category', 'liu_cheng_zhi_yin', function (data) {
        var tpl = document.getElementById('flowBootCategoryTpl');
        laytpl(tpl.innerHTML).render(data.taxonomy, function (string) {
            $('#flowBootCategoryBox').html(string);
            $('li.test>a.category').on('click', function () {
                var $this = $(this);
                $('#help-menu').find('a').removeClass('active');
                $this.addClass('active').parent().siblings('.test').find('a').removeClass('active');
                getFlowBootChildTaxContents($this.attr('taxonomyType'), $this.attr('taxonomySlug'))
            });
            var active = $('li.test>a.active');
            // getFlowBootChildTaxContents(active.attr('taxonomyType'), active.attr('taxonomySlug'))
        });
    });
    CMS.getTaxonomies('category', 'zi_zhu_fu_wu', function (data) {
        var tpl = document.getElementById('flowBootCategoryTpl');
        laytpl(tpl.innerHTML).render(data.taxonomy, function (string) {
            $('#selfServiceCategoryBox').html(string);
            $('li.test>a.category').on('click', function () {
                var $this = $(this);
                $('#help-menu').find('a').removeClass('active');
                $this.addClass('active').parent().siblings('.test').find('a').removeClass('active');
                getFlowBootChildTaxContents($this.attr('taxonomyType'), $this.attr('taxonomySlug'))
            });
            var active = $('li.test>a.active');
            // getFlowBootChildTaxContents(active.attr('taxonomyType'), active.attr('taxonomySlug'))
        });
    });
});

function getProblemChildTaxContents(type, slug) {
    $('#problemChildTaxBox').html('');
    CMS.getContents(type, slug, function (data) {
        if (data.contents.rows.length == 0) {
            $('#problemChildTaxBox').html("<h3 style='text-align: center;margin-top: 30px'>内容官正在紧急整理材料，敬请期待！</h3>");
            return;
        }
        var tpl = document.getElementById('problemChildTaxTpl');
        laytpl(tpl.innerHTML).render(data, function (string) {
            $('#problemChildTaxBox').html(string);
            $('.question a.contentDetail').on('click', function () {
                var $this = $(this);
                CMS.getContentById($this.attr('contentId'), function (data1) {
                    var tpl2 = document.getElementById('problemContentTpl');
                    laytpl(tpl2.innerHTML).render(data1, function (string) {
                        $('#problemChildTaxBox').html(string);
                    });
                })
            })
        });
    })
}

function getFlowBootChildTaxContents(type, slug) {
    CMS.getContentsUnder(type, slug, function (data) {
        if (data.contents.rows.length == 0) {
            $('#problemChildTaxBox').html("<h3 style='text-align: center;margin-top: 30px'>内容官正在紧急整理材料，敬请期待！</h3>");
            return;
        }
        var tpl = document.getElementById('problemChildTaxTpl');
        laytpl(tpl.innerHTML).render(data, function (string) {
            $('#problemChildTaxBox').html(string);
            $('.question a.contentDetail').on('click', function () {
                var $this = $(this);
                CMS.getContentById($this.attr('contentId'), function (data1) {
                    var tpl2 = document.getElementById('problemContentTpl');
                    laytpl(tpl2.innerHTML).render(data1, function (string) {
                        $('#problemChildTaxBox').html(string);
                    });
                })
            })
        });
    })
}