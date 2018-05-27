// JavaScript Document

$(document).ready(function() {
    $(":radio").change(function(){
        $("#username").val("");
        $("#password").val("");
    });
    $('.line').slideUp();
});

switchAlpha($(".switchAlpha"),5000,200)


//首页banner切换（带下标的图片切换）
function switchAlpha(a,t1,t2){
    var n=a.find(".list").children("li").length;
    var dot=a.parent().find(".dot-l").children("a");
    a.find(".list").children("li").slice(1).css("opacity","0");
    var i=0;
    function toAlpha(t){
        if(i<n-1){i++;}else{i=0;}
        a.find(".list").children("li").eq(i).css("z-index","2").animate({opacity:1},t).siblings().css("z-index","1").animate({opacity:0},t)
        dot.eq(i).addClass("hover").siblings().removeClass("hover")
    }
    var timeac=setInterval(function(){toAlpha(t2)},t1);
    a.find(".list").children("li").hover(function(){clearInterval(timeac);},function(){timeac=setInterval(function(){toAlpha(t2)},t1)})
    dot.click(function(){
        $(this).addClass("hover").siblings().removeClass("hover");
        i=$(this).index();
        a.find("li").eq(i).css("z-index","2").animate({opacity:1},t2).siblings().css("z-index","1").animate({opacity:0},t2)
        clearInterval(timeac);
        timeac=setInterval(function(){toAlpha(t2)},t1)

    })

}

evenColor($(".history table"),'#f8f8f8')
evenColor($(".tap_i table"),'#f8f8f8')
evenColorth($(".tap_i .tableinfo"),'#f4f4f4')
evenColor($(".prolist table"),'#f8f8f8')
//隔行变色通用
function evenColor(a,b){
    a.find("tr:even").children("td").css('background',b)
}
function evenColorth(a,b){
    a.find("tr:even").children("th").css('background',b)
}

//taps切换
tapsswitch($(".pinfo_b"))
tapsswitch($(".inactive .history"))
function tapsswitch(a){
    a.find(".tap_t li").click(function(){
        $(this).addClass("cur").siblings().removeClass("cur");
        var n=$(this).index();
        a.find(".tap_i li").eq(n).addClass("cur").siblings().removeClass("cur");
    })
}
tapsswitch2($(".sy"))
function tapsswitch2(a){
    $(".sy li").click(function(){
        $(this).addClass("active").siblings().removeClass("active");
        var n=$(this).index();
        a.find(".content").eq(n).addClass("cur").siblings().removeClass("cur");
    })
}

$(".bank0 .select ").click(function(){
    $("#select_bank_warning").html('');})

//左右两列同高
$(".menu").height($(".section").height())
$(".menu").height($(".right").height())

$(".hint span").height($("#hint").height())

evenColor($(".nextlist"),'#f8f8f8')
evenColor($(".tap_i table"),'#f8f8f8')
evenColor($(".tapinfo table"),'#f8f8f8')
evenColor($(".list_l table"),'#f8f8f8')
evenColor($(".list_r table"),'#f8f8f8')
evenColor($(".tabsuc table"),'#f8f8f8')
evenColor($(".record table"),'#f8f8f8')
//隔行变色通用
function evenColor(a,b){
    a.find("tr:even").children("td").css('background',b)
}

//taps切换
tapsswitch($(".tap_t li"))
function tapsswitch(a){
    a.click(function(){
        $(this).addClass("cur").siblings().removeClass("cur");
        var n=$(this).index();
        $(this).parent().siblings(".tap_i").children("li").eq(n).addClass("cur").siblings().removeClass("cur");
    })
}
tapsswitch2($(".tap_t li"))
function tapsswitch2(d){
    d.click(function(){
        $(this).addClass("cur").parent().siblings().find('li').removeClass("cur");
        var n=$(this).index();
        $(this).parent().siblings(".tap_i").children("li").eq(n).addClass("cur").siblings().removeClass("cur");
    })
}
tapsswitch2($(".inv_list"))
function tapsswitch2(a){
    a.find(".taptitle dd").click(function(){
        $(this).addClass("cur").siblings().removeClass("cur");
        var n=$(this).index();
        a.find(".tapinfo dd").eq(n).addClass("cur").siblings().removeClass("cur");
    })
}
tapsswitch2($(".inv_list"))
function tapsswitch2(b){
    b.find(".zs-list dd").click(function(){
        $(this).addClass("cur").parent().siblings().find('dd').removeClass("cur");
        var n=$(this).index();
        b.find(".tapinfo dd").eq(n).addClass("cur").siblings().removeClass("cur");
    })
}


//消息中心伸缩展示
hideshow($(".mes_list em"))
function hideshow(a){
    a.click(function(){
        if($(this).parent().hasClass("new")){$(this).parent().removeClass("new")}
        if($(this).parent().next().css("display")=="block"){$(this).parent().next().hide()
        }else{$(this).parent().next().show()}
    })
}


//勾选内容
checkedac($(".message i"))
function checkedac(a){
    a.click(function(){
        if($(this).hasClass("focus")){$(this).removeClass("focus")}else{$(this).addClass("focus")}
    })
}


//身份证位数判断
$(".setbox .idcard").focusout(function(){
    if($(this).val().length!=0){
        if($(this).val().length!=18){ $(this).next().show()}else{$(this).next().hide()}
    }
})

//获取验证码倒计时
var InterValObj; //timer变量，控制时间
var count = 60;  //间隔函数，1秒执行
var curCount;//当前剩余秒数
function sendMessage(a) {
    curCount = count;
    //设置button效果，开始计时
    a.attr("disabled", "true");
    a.val("(" + curCount + "秒后重新获取)").addClass("timeac");
    InterValObj = window.setInterval(function(){SetRemainTime(a)}, 1000);//启动计时器，1秒执行一次
}
//timer处理函数
function SetRemainTime(a) {
    if (curCount == 0) {
        if(a.hasClass("timeac")){a.removeClass("timeac")};
        window.clearInterval(InterValObj);//停止计时器
        a.removeAttr("disabled");//启用按钮
        a.val("获取验证码");
    }
    else {
        curCount--;
        a.val("(" + curCount + "秒后重新获取)");
        if(!a.hasClass("timeac")){a.addClass("timeac")};
    }
}
$("#yd").mouseover(function() {
    $("#iph1").show().animate({
            'right': '0'
        },
        300);
    $("#iph").hide();
}) ;
$("#yd").mouseout(function() {
    $("#iph1").hide();
    $("#iph").show();
    init();
});
$("input[type='password']").attr('autocomplete', 'off');
$("form[method='post']").attr('autocomplete', 'off');

$("#te").mouseover(function() {
    $("#iph2").show().animate({
            'right': '0'
        },
        300);
    $("#iph").hide();
});
$("#te").mouseout(function() {
    $("#iph2").hide();
    $("#iph").show();
    init();
});
$("input[type='password']").attr('autocomplete', 'off');
$("form[method='post']").attr('autocomplete', 'off');

$("#js").mouseover(function() {
    $("#iph3").show().animate({
            'right': '0'
        },
        300);
    $("#iph").hide();
});
$("#js").mouseleave(function() {
    $("#iph3").hide();
    $("#iph").show();
    $("#iph3").find(":input,em").each(function(index,dom){
        $(this).val('');
        $(this).html('');
        init();
    });
});
$("input[type='password']").attr('autocomplete', 'off');
$("form[method='post']").attr('autocomplete', 'off');

$('.b-num').keyup(function(){
    $('.big-num').show();
    var value=$(this).val().replace(/\s/g,'').replace(/(\d{4})(?=\d)/g,"$1 ");
    $('.big-num').text(value);
}).keypress(function(event) {
    var keyCode = event.which;
    if (keyCode == 46 || (keyCode >= 48 && keyCode <=57) || keyCode == 8)//8是删除键
        return true;
    else
        return false;
}).focus(function() {
    this.style.imeMode='disabled';
}).blur(function(){
    $('.big-num').hide();});

$('.input_money').keypress(function(event) {
    var keyCode = event.which;
    var input_money = $(this).val();
    var decimalNumber = input_money.split('.')[1];
    if(decimalNumber && decimalNumber.length >1 && keyCode != 8) {
        return false
    }

    if (keyCode == 46 || (keyCode >= 48 && keyCode <=57) || keyCode == 8) {//8是删除键 46shi xiaoshudian


        return true;
    }
    else
        return false;
}).focus(function() {
    this.style.imeMode='disabled';
});

$(window).scroll(function (){
    if($(window).scrollTop()>100){
        $(".header").addClass('head-fixed');
    }else{ $(".header").removeClass('head-fixed');

    }
});

$(".user-main").hover(
    function () {
        $(this).addClass("user-hover");
    },
    function () {
        $(this).removeClass("user-hover");
    }
);
$(".box-mt-p").hover(
    function () {
        $(this).addClass("box-mt-p-hover");
    },
    function () {
        $(this).removeClass("box-mt-p-hover");
    }
);
$(".newlv").hover(function () {
    $(this).parent().parent().next(".hintbox0").show()
}, function () {
    $(this).parent().parent().next(".hintbox0").hide()
});


//banner图片预加载
$(".switchAlpha li").hide();
var img00=new Image();img01=new Image();img02=new Image();img03=new Image();img04=new Image();img05=new Image();img06=new Image();
img00.src="../../static/images/index/banner-default.png";
// img01.src="http://test.paituodai.com/attach/file/attach_1495901124994.jpg";
// img02.src="http://test.paituodai.com/attach/file/attach_1495901124994.jpg";
// img03.src="http://test.paituodai.com/attach/file/attach_1495901124994.jpg";
// img04.src="http://test.paituodai.com/attach/file/attach_1495901124994.jpg";
// img05.src="http://test.paituodai.com/attach/file/attach_1495901124994.jpg";
// img06.src="http://test.paituodai.com/attach/file/attach_1495901124994.jpg";



var imgload=0;
img00.onload=function(){imgload++;if(imgload=$(".switchAlpha li").length){addbgimg()}}
img01.onload=function(){imgload++;if(imgload=$(".switchAlpha li").length){addbgimg()}}
img02.onload=function(){imgload++;if(imgload=$(".switchAlpha li").length){addbgimg()}}
img03.onload=function(){imgload++;if(imgload=$(".switchAlpha li").length){addbgimg()}}
img04.onload=function(){imgload++;if(imgload=$(".switchAlpha li").length){addbgimg()}}
img05.onload=function(){imgload++;if(imgload=$(".switchAlpha li").length){addbgimg()}}
function addbgimg() {
    for (var i = 0; i < $(".switchAlpha li").length; i++) {
        var temp = $(".switchAlpha li").eq(i).attr("bgimg");
        $(".switchAlpha li").eq(i).css({"background-image": temp});
        $(".switchAlpha li").show();
    }
}

$(document).ready(function() {
    $('#first_dot').click();
    if ($.cookie("cookieenrolname") && $.cookie("cookieenrolname")!=null) {
        $("#username").val($.cookie("cookieenrolname"));
    } else {
        $("#username").val("");
        //$("#ckbox:checked").removeAttr('checked');
    }
    var user_id = $("#userId").val();
    if(user_id != ''){
        $("#login_box").hide();
        $("#logined_box").show();
    }
});

//全屏弹窗
$(".fullbox .part01").addClass("move");
var cur = 0;
var h = $(window).height();
$(".ta_show li").height(h);
$(window).resize(function () {
    h = $(window).height();
    $(".ta_show li").height(h);
    $(".ta_show").css({marginTop: -h * cur});
})
$(".part01").click(function () {
    $(".dot a").eq(1).addClass("cur").siblings().removeClass("cur");
    $(".ta_show").animate({marginTop: -h}, 600);
    $(".fullbox .ta_show li").eq(1).addClass("move").siblings().removeClass("move")

})
$(".part02").click(function () {
    $(".dot a").eq(2).addClass("cur").siblings().removeClass("cur");
    $(".ta_show").animate({marginTop: -h * 2}, 600);
    $(".fullbox .ta_show li").eq(2).addClass("move").siblings().removeClass("move")

})
$(".part03").click(function () {
    $(".fullbox").fadeOut();
})

$(".dot a").click(function () {
    cur = $(".dot a").index(this)
    $(this).addClass("cur").siblings().removeClass("cur");
    $(".ta_show").animate({marginTop: -h * cur}, 600);
    $(".fullbox .ta_show li").eq(cur).addClass("move").siblings().removeClass("move")
})

window.console = window.console || (function () {
        var c = {};
        c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.profile
            = c.clear = c.exception = c.trace = c.assert = function () {
        };
        return c;
    })();

$(document).on("mousewheel DOMMouseScroll", function (e) {

    var delta = (e.originalEvent.wheelDelta && (e.originalEvent.wheelDelta > 0 ? 1 : -1)) ||  // chrome & ie
        (e.originalEvent.detail && (e.originalEvent.detail > 0 ? -1 : 1));              // firefox


    if (delta > 0) {
        // 向上滚
        if (cur > 0) {
            cur--;
            partac()
        }
    } else if (delta < 0) {
        // 向下滚
        if (cur < 2) {
            cur++;
            partac()
        }
    }
});


function partac() {
    $(".dot a").eq(cur).addClass("cur").siblings().removeClass("cur")
    $(".ta_show").animate({marginTop: -h * cur}, 300);
    $(".fullbox .ta_show li").eq(cur).addClass("move").siblings().removeClass("move")
}

$("#fclo").click(function () {
    $(".fullbox").fadeOut();

})
$("#wytz").click(function () {
    $(".fullbox").fadeOut();

})


//hover 闪动延时处理
$.fn.hoverDelay = function(options){
    var defaults = {
        hoverDuring:0,
        outDuring: 300,
        hoverEvent: function(){
            $.noop();
        },
        outEvent: function(){
            $.noop();
        }
    };
    var sets = $.extend(defaults,options || {});
    var hoverTimer, outTimer;
    return $(this).each(function(){
        $(this).hover(function(){
            clearTimeout(outTimer);
            hoverTimer = setTimeout(sets.hoverEvent, sets.hoverDuring);
        },function(){
            clearTimeout(hoverTimer);
            outTimer = setTimeout(sets.outEvent, sets.outDuring);
        });
    });
}


$(".header-s").hoverDelay({
    hoverDuring: 0,
    outDuring: 400,
    hoverEvent: function(){
        $(".header-t").fadeIn(300);
        $(".header-s").addClass("hover");
    },
    outEvent: function(){
        $(".header-t").fadeOut(300);
        $(".header-s").removeClass("hover");
    }
});

$(function(){
    //登录/注册切换
    $('.login_switch a').mouseover(function(){
        var liindex = $('.login_switch a').index(this);
        $(this).addClass('cur').siblings().removeClass('cur');
        $('.login_box div.login_m').eq(liindex).fadeIn(150).siblings('div.login_m').hide();
    });
});
function hrefs(urls) {
    //window.location.href = "" + urls + "";
    window.open(urls, '');
}

$(".appt").mouseover(function () {
    $(".apps").show()
});
$(".appt").mouseout(function () {
    $(".apps").hide()
});

//隐藏金额
$(".main .shortinfo i").click(function(){
    if($(this).prev().css("display")=="none"){$(this).prev().show()}else{$(this).prev().hide()}
})

function checkMobile(mobile) {
    if (mobile.trim() == ''){
        $("#mt").text("请输入手机号");
        return;
    }
    var reg = /^1[3|4|5|7|8][0-9]{9}$/;
    if (!mobile.match(reg)){
        $("#mt").text("请输入正确的手机号");
        return;
    }
    var type = $('input:radio:checked').val();

    $.ajax({
        url: ctx + "/regist/check_mobile?type=" + type,
        type: "post",
        data: {
            mobile: mobile
        },
        dataType: "json",
        cache: false,
        success: function(data) {
            if (data) {
                $("#mt").text("手机号不存在");
                $("#loginnow").prop("disabled", true);
            } else {
                $("#loginnow").css("background-color","#e54");
                $("#loginnow").prop("disabled", false);
                $("#mt").text("");
            }
        }
    });
}

function checkPasswd(password) {
    if (password.trim() == ''){
        $("#mt").text("密码不能为空");
        $("#loginnow").prop("disabled", true);
        return;
    }else {
        $("#loginnow").css("background-color","#e54");
        $("#loginnow").prop("disabled", false);
    }
}

// function clearData(type) {
//     $("#username").val("");
//     $("#password").val("");
// }

function login(){
    cookiename();
    var rsaKey = new RSAKey();
    rsaKey.setPublic(b64tohex(modules), b64tohex(exponent));
    var url = ctx + "/login";
    var reg = /^1[3|4|5|7|8][0-9]{9}$/;
    if (!$("#username").val().trim().match(reg)){
        $("#mt").text("请输入正确的手机号");
        return;
    }
    if ($("#password").val().trim() == ''){
        $("#mt").text("请输入密码");
        return;
    }
    $.ajax
    ({
        type: "post",
        dataType: "json",
        url: url,
        data: {
            username: $.trim($("#username").val()),
            password: hex2b64(rsaKey.encrypt($("#password").val())),
            type: $('input:radio:checked').val()
        },
        cache: false,
        beforeSend: function (request, settings) {
            request.setRequestHeader("token", $.cookie("token"));
        },
        success: function (result) {
            if (result.status == "success") {
                window.location.href = ctx + "/uc";
            }
            else {
                console.log(result);
                $("#mt").text(result.message);
                $("#loginnow").css("background-color","#C0C0C0");
                $("#loginnow").prop("disabled", true);
                modules = result.data.modules;
                exponent = result.data.exponent;
            }
        }
    });
}

$("#rem").click(function () {
    var cookie = $.cookie("cookieenrolname"); //读取cookie
    var checkLength = $("#rem:checked").length;
    if (checkLength == 0) {
        if (cookie) {
            $.cookie("cookieenrolname", null, {path: '/'});
        }
    }
});
//用户名存cookie
function cookiename() {
    var cookie = $.cookie("cookieenrolname"); //读取cookie
    var checkLength = $("#rem:checked").length;
    if (checkLength == 0) {
        if (cookie) {
            $.cookie("cookieenrolname", "", {path: '/'});
        }
    } else {
        if (cookie) {
            if (cookie == $("#username").val()) {
            } else {
                $.cookie("cookieenrolname",//写入cookie
                    $("#username").val(),//需要cookie写入的业务
                    {
                        "path": "/", //cookie的默认属性
                        "expires": 100 //有效天数
                    });
            }

        } else {
            $.cookie("cookieenrolname",//写入cookie
                $("#username").val(),//需要cookie写入的业务
                {
                    "path": "/", //cookie的默认属性
                    "expires": 100 //有效天数
                });
        }

    }
}

$("#choose").click(function(){
    if($(this).children(".login_index i").hasClass("focus")){$(this).children(".login_index i").removeClass("focus")
        $(this).children('#rem').prop('checked',false);
    }else{$(this).children(".login_index i").addClass("focus")
        $(this).children('#rem').prop('checked',true);
    }
})