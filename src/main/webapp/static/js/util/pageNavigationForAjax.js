/**
 * Created by limat on 2017/4/19.
 */
/**
 * 生成分页按钮组
 */
(function(factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD. Register as anonymous module.
        define(['jquery'], factory);
    } else if (typeof exports === 'object') {
        // Node / CommonJS
        factory(require('jquery'));
    } else {
        // Browser globals.
        factory(jQuery);
    }
})(function($) {
    $.creatPageNav = function(setting) {
        var totalpage = +setting.totalpage;
        var page = +setting.page;
        var selector = setting.selector;
        var clickCallback = setting.clickCallback;
        var currentPage = 1;

        init(page);

        function init(newpage) {
            $(selector).empty();
            currentPage = newpage;
            $(document).off('click.pageNav',selector+'>a');

            if (!totalpage || totalpage <= 0) {
                return false;
            }

            if(totalpage >= 2) {
                var pageNavPrev = '<a class="page" href="javascript:void(0)">&lt;</a>';
                var pageNavNum = '<a class="page" href="javascript:void(0)"></a>';
                var pageNavNext = '<a class="page" href="javascript:void(0)">&gt;</a>';
                var point = '<em>...</em>';

                //插入上一页
                if (newpage != 1) {
                    $(selector).append($(pageNavPrev));
                }
                //向前边界
                if (newpage > 3) {
                    var firstNum = newpage - 3;
                } else {
                    var firstNum = 1;
                }
                //向后边界
                if (newpage < totalpage - 3) {
                    var lastNum = newpage + 3;
                } else {
                    var lastNum = totalpage;
                }
                //单独插入第一页按钮
                if (newpage > 4) {
                    $(selector).append($(pageNavNum).text('1'));
                }
                //插入前置点
                if (newpage > 5) {
                    $(selector).append($(point));
                }
                //插入页按钮
                var numRang = lastNum - firstNum;
                for (var i = 0; i <= numRang; i++) {
                    var current = firstNum + i;
                    if (newpage == current) {
                        $(selector).append($(pageNavNum).addClass('previous hidden').text(current));
                    } else {
                        $(selector).append($(pageNavNum).text(current));
                    }
                }
                //插入后置点
                if (newpage < totalpage - 5) {
                    $(selector).append($(point));
                }
                //单独插入最后一页按钮
                if (newpage < totalpage - 3) {
                    $(selector).append($(pageNavNum).text(totalpage));
                }
                //插入下一页
                if (newpage != totalpage) {
                    $(selector).append($(pageNavNext));
                }
            }
            $(document).on('click.pageNav',selector+'>a',clickPage);
        }


        function clickPage(){
            var curpage = $(this).text();

            if(curpage=='<'){
                curpage = currentPage-1;
            }
            if(curpage=='>'){
                curpage = currentPage+1;
            }

            init(+curpage);
            clickCallback(+curpage);
        }
    }
})