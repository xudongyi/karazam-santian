/**
 * Created by chenxinglin
 */
var options = {
    url : '/uc/point/canbuy/data',
    params : {},
    selector:{
        template : "dataTpl",
        data : ".data",
        pagination : ".pagination",
        empty : ".empty",
    }
};

$(function () {

    dataLoad(options);

});

var dataLoad = function (options) {
    $.getJSON(
        options.url,
        options.params,
        function (res) {
            var total = res.total;
            $('.dataCount').text(total);
            if (res.total > 0) {
                $(options.selector.empty).hide();
            } else {
                $(options.selector.empty).show();
            }
            //使用方式跟独立组件完全一样
            var tpl = document.getElementById(options.selector.template);
            laytpl(tpl.innerHTML).render(res, function (string) {
                $(options.selector.data).html(string);
                eventDo();
            });
            laypage({
                skin: '#d0d0d0',
                cont: $(options.selector.pagination)
                , pages: res.pages
                , jump: function (e, first) { //触发分页后的回调
                    if (!first) {
                        $.getJSON(
                            options.url,
                            $.extend({
                                page: e.curr,//当前页
                                rows: res.pageSize
                            }, options.params),
                            function (rest) {
                                e.pages = e.last = rest.pages; //重新获取总页数，一般不用写
                                //解析数据
                                laytpl(tpl.innerHTML).render(rest, function (stringt) {
                                    $(options.selector.data).html(stringt);
                                    eventDo();
                                });
                            });
                    }
                }
            });
        });
};


/**
 * 事件
 */
var eventDo = function() {

    //关注事件
    $('.followList').on('click', function () {
        $this = $(this);
        $.getJSON(ctx + "/mall/follow",
            {goodsId : $this.attr("goodsId")},
            function (res) {console.log(res);
                if(res.status == 'success'){
                    if ($this.hasClass("fa-heart-o")){ //关注
                        $this.removeClass("fa-heart-o").addClass("fa-heart orange");
                        showFocusTip($this.parent().find(".focus-tip"), "关注成功");
                        $this.attr("title", "已关注");
                    }else { //取消关注
                        $this.removeClass("fa-heart orange").addClass("fa-heart-o");
                        showFocusTip($this.parent().find(".focus-tip"), "取消关注");
                        $this.attr("title", "加关注");
                    }
                }else {console.log(res.message);
                    showFocusTip($this.parent().find(".focus-tip"), res.message);
                }
            });
    });

    //立即购买显示事件
    $("body").on("mouseenter mouseleave", ".box,.box-big", function (t) {
        switch (t.type) {
            case"mouseenter":
                $(this).find(".buy").stop().animate({"bottom": 15, "opacity": 1}, 200);
                break;
            case"mouseleave":
                $(this).find(".buy").stop().animate({"bottom": -30, "opacity": 0}, 200)
        }
    });
}

/**
 * 关注提示
 */
var showFocusTip = function (e, t) {
    e.html(t).animate({"opacity": 1, "right": 50}, 500), e.html(t).animate({
        "opacity": 0,
        "right": 55
    }, 500, function () {
        e.css({"top": 20, "right": 40, "opacity": 0})
    })
}