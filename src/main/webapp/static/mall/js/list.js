/**
 * Created by chenxinglin
 */

var typeSearchLabel = "";
var data = {
    des : '',
    showPercent : 4,
    rows : []
};

$(function () {

    //积分区间事件
    $('.point-interval').on('change', function () {
        var point = $(this).val();
        if (isNaN(point)) {
            $(this).val("");
        }
        point = $(this).val();
        if (point) {
            $(this).val(parseInt(point));
        }
    });

    //排序查询事件
    $('.typeSearch').on('click', function () {
        //获取参数
        params.sort = $(this).attr("data-type");
        // pointParamDo();
        params.filter_GEI_point = $("#point-min").val();
        params.filter_LEI_point = $("#point-max").val();

        //排序标记
        if(typeSearchLabel && typeSearchLabel == params.sort){
            if(params.order == "asc"){
                params.order = "desc";
            }else {
                params.order = "asc";
            }
        }else{
            params.order = "desc";
            typeSearchLabel = params.sort;
        }
        //重置排序
        $('.typeSearch').removeClass("current");
        $(this).addClass("current");
        $('.typeSearch .fa').removeClass("fa-caret-up").removeClass("fa-caret-down");
        $('.typeSearch .fa').addClass("fa-caret-down");
        $(this).find(".fa").removeClass(".fa-caret-down");
        if(params.order == "asc"){
            $(this).find(".fa").addClass("fa-caret-up");
        }else {
            $(this).find(".fa").addClass("fa-caret-down");
        }

        getPageData();
    });

    //确认查询事件
    $('.btn-search').on('click', function () {
        // pointParamDo();
        params.filter_GEI_point = $("#point-min").val();
        params.filter_LEI_point = $("#point-max").val();
        getPageData();
    });

    //关注事件
    followDo();
    followHotDo();

    //首次获取数据
    getPageData();

});

//获取数据
var getPageData = function() {
    // var params = $.extend(baseParams, params);
    $.getJSON(url,
        params,
        function (res) {
            var total = res.total;
            $('#dataCount').text(total);
            data.rows = res.rows;
            var tpl = document.getElementById("dataTpl");
            laytpl(tpl.innerHTML).render(data, function (string) {
                $('#dataBox').html(string);
                if (total == 0) {
                    $('#dataBox').hide();
                    $('#pagination').hide();
                    $('#noDataTip').show();
                } else {
                    $('#dataBox').show();
                    $('#pagination').show();
                    $('#noDataTip').hide();
                }
                followDo();
                buyBox();
            });
            laypage({
                skin: '#d0d0d0',
                cont: 'pagination'
                , pages: res.pages
                , jump: function (e, first) { //触发分页后的回调
                    if (!first) {
                        $.getJSON(url,
                            $.extend(params, {page: e.curr}),
                            function (rest) {
                                e.pages = e.last = rest.pages; //重新获取总页数，一般不用写
                                $('#dataCount').text(rest.total);
                                // //解析数据
                                data.rows = rest.rows;
                                laytpl(tpl.innerHTML).render(data, function (stringt) {
                                    $('#dataBox').html(stringt);
                                    followDo();
                                    buyBox();
                                });
                            });
                    }
                }
            });
        }
    );
};

// /**
//  * 积分区间事件
//  */
// var pointIntervalDo = function() {
//
// }

/**
 * 关注事件
 */
var followDo = function() {
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
}

/**
 * 热门商品关注事件
 */
var followHotDo = function() {
    $('.followHot').on('click', function () {
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

/**
 * 立即购买显示事件
 */
var buyBox = function (e, t) {
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
