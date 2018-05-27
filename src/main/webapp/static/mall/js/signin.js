/**
 * Created by chenxinglin
 */
$(function () {

    $('#signInBox').hide();
    $('#signInBtn').on('click', function () {
        $.getJSON(ctx + "/uc/sign_in/sign_in",
            {},
            function (res) {
                if(res.status == 'success'){
                    var str = '<span class="value">'+res.data.point+'</span><span class="type">积分</span>';
                    if(res.data.coupon > 0){
                        str += '+<span class="value">'+res.data.coupon+'</span><span class="type">元优惠券</span>';
                    }
                    var conSignInCount= res.data.conSignInCount%7;//签到次数
                    var pointStr="";
                    for (var i=1;i<=6;i++){
                        if (conSignInCount>=i){
                            pointStr += '<li class=done>'+'<p class="t"></p>'+'<p class="b">'+i+'</p></li>';
                        }
                       else{
                            pointStr += '<li>'+'<p class="t"></p>'+'<p class="b">'+i+'</p></li>';
                        }
                    }
                    if (conSignInCount==0){
                        pointStr+=' <li class="t-last done">'+'<p class="last"></p>'+'<p class="b">7</p></li>';
                    }else{
                        pointStr+=' <li class="t-last">'+'<p class="last"></p>'+'<p class="b">7</p></li>';
                    }
                    console.log(pointStr);
                    $(".dot").html("");
                    $(".dot").append(pointStr);
                    $("#signInRes").append(str);
                    $("#signInBtn").replaceWith('<a href="javascript:;" class="btn1"><img src="/static/mall/images/signin/btn2.png"></a>');
                    $('#signInBox').show();
                    $('.today').text("√");
                    // $(".done").next("li").addClass("done");
                }else {
                    layer.msg(res.message,{icon: 1});
                }
            });
    });

    $(".close").on('click', function() {
        $('#signInBox').hide();
    })

    $(".tab li").on('click', function() {
        $(".tab li").removeClass("active");
        $(this).addClass("active");
        $(".list").hide();
        $("." + $(this).attr("data-type")).show();
    });

});
