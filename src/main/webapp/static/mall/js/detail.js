/**
 * Created by chenxinglin
 */
$(function () {

    tabSwitchover();

    followDo();

    var $orderForm = $("#orderForm"),$goodsId = $orderForm.find("[name='goodsId']"),$point = $orderForm.find("[name='point']"),$quantity = $orderForm.find("[name='quantity']");

    $("#buy").on('click', function () {

        var goodsPoint = $("#goodsPoint").text();
        var buyQuantity = $("#buyQuantity").val();
        var payPoint = goodsPoint*buyQuantity;
        $("#payPoint").text(payPoint);
        $quantity.val(buyQuantity);
        $point.val(payPoint);

        //购买确认
        layer.open({
            title: '换购确认',
            type: 1,
            skin: 'layui-layer-rim', //加上边框
            area: ['600px', '400px'], //宽高
            content:$('#orderFormBox'),
            btn: ['确认', '取消'],
            yes: function (index) {
                $.getJSON(ctx + "/uc/goods_order/add",
                    {
                        goodsId : $goodsId.val(),
                        quantity : $quantity.val(),
                        point : $point.val(),
                        address : $("#address").val(),
                    },
                    function (res) {
                        if(res.status == 'success'){
                            layer.msg('兑换成功，请注意查收您的包裹',{icon: 1});
                            location.href = ctx + "/uc/goods_order/detail/"+res.message;
                        }else {
                            layer.msg(res.message,{icon: 1});
                        }
                    });

                layer.close(index);
                $('#orderFormBox').hide();
            }, btn2: function (index) {
                $('#orderFormBox').hide();
            },cancel: function(){
                $('#orderFormBox').hide();
            }
        });
    });

    $(".tb-stock .add").on('click', function () {
        var buyQuantity = parseInt($("#buyQuantity").val());
        var stock = parseInt($("#buyQuantity").attr("data-stock"));
        if(stock <= buyQuantity){
            $("#buyQuantity").val(stock);
        }else {
            $("#buyQuantity").val(buyQuantity+1);
        }
    });
    $(".tb-stock .subtract").on('click', function () {
        var buyQuantity = parseInt($("#buyQuantity").val());
        if(buyQuantity>1){
            $("#buyQuantity").val(buyQuantity-1);
        }
    });
});

/**
 * 详情标签切换
 */
var tabSwitchover = function () {
    $('.tab-btn').on('click', function () {
        $('.tab-btn').removeClass("active");
        $('.tab-content').removeClass("active");
        $(this).addClass("active");
        if($(this).attr("data-type") == "detail"){
            $('.detail').addClass("active");
        }else {
            $('.tab-content.buyRecords').addClass("active");
        }
    });
}

/**
 * 关注事件
 */
var followDo = function() {
    $('.follow').on('click', function () {
        $this = $(this);
        $.getJSON(ctx + "/mall/follow",
            {goodsId : $this.attr("goodsId")},
            function (res) {
                if(res.status == 'success'){
                    if ($this.find("i").hasClass("unfollow")){ //关注
                        $this.find("i").removeClass("unfollow").addClass("orange followed");
                        // showFocusTip($this.parent().find(".focus-tip-detail"), "关注成功");
                        $this.attr("title", "已关注");
                    }else { //取消关注
                        $this.find("i").removeClass("orange followed").addClass("unfollow");
                        // showFocusTip($this.parent().find(".focus-tip-detail"), "取消关注");
                        $this.attr("title", "加关注");
                    }
                }else {
                    // showFocusTip($this.parent().find(".focus-tip-detail"), res.message);
                }
            });
    });
}

// /**
//  * 关注提示
//  */
// var showFocusTip = function (e, t) {
//     e.html(t).animate({"opacity": 1, "right": 50}, 500), e.html(t).animate({
//         "opacity": 0,
//         "right": 55
//     }, 500, function () {
//         e.css({"top": 20, "right": 40, "opacity": 0})
//     })
// }

