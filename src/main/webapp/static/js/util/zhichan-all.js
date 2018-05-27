/**
 * Created by limat on 2017/4/19.
 */
var startTime = '';//开始时间
var trade = $("#allStatus").attr("data");//交易类型
var page = 1;//页数

$(function () {
    // 左侧导航栏显示当前内容
    mnav.activity(2);

    $("#dateRange ul li").click(function () {
        $(this).addClass("cur").siblings().removeClass("cur");
        startTime = getDateRangeStartTime($(this).text());
        page = 1;
        reload();
    });


    // 点击条件重新加载
    $(".selector-title").click(function () {
        // $(this).children('i').toggleClass("rotate");
        $(".selector-son").toggle();
        if ($(this).parent().hasClass("hover")) {
            $(this).removeClass('hover')
        } else {
            $(this).addClass("hover");
        }
    });
    $(".selector-son span").click(function () {
        var datafld = $(this).attr("datafld");
        var data = $(this).text();
        $(".selector").attr("data", datafld);
        $(".selector-son").toggle();
        $(".selector-title span").text(data);
        trade = $("#allStatus").attr("data");
        //将页码初始到第一页
        page = 1;
        reload();
    });
    // 点击详情跳出弹框
    $(document).on('click', ".detail-title", function () {

        var man = $(this).siblings(".zichan-detail");
        $(".zichan-detail").hide();
        if (man.hasClass('cur')) {
            man.removeClass('cur');
        }
        else {
            man.addClass('cur').show();
        }
    });
    // 点击背景隐藏详情
    $("body").on("click", function () {

        $(".zichan-detail").hide();
        $(".zichan-detail").removeClass('cur');
    })

    reload();

    $("#excel").click(function () {
        // startTime和endTime都是空的时候下载全部
        //其余情况startTime和endTime都不为空
        var current = new Date();
        var endTime = current.format('yyyy-MM-dd');

        var url = window.action.rootPath + window.action.interface.zichanAllExport;

        if (startTime != null && endTime != null) {
            url += "?startTime=" + startTime.split(" ")[0] + "&endTime=" + endTime;
        }
        if (trade != null && trade != "") {
            url += "&tradeCode=" + trade;
        }
        window.location.href = url;
    });

});

function reload() {
    $.ajax({
        type: 'POST',
        url: 'accountFlowList.json',
        data: {
            "startTime": startTime,
            "tradeCode": trade,
            "page": page,
            "rows": "10"
        },
        success: function (result) {
            if (result.success) {
                if (!result.data || result.data.count == 0) {
                    title();
                    $(".empty").show();
                    $(".answer").hide();
                    $(".pageList").hide();
                } else {
                    title();
                    $(".empty").hide();
                    $(".answer").show();
                    $(".pageList").show();

                    allData(result.data);
                    // 分页效果
                    $.creatPageNav({
                        selector: '.pageList',
                        page: page,
                        totalpage: Math.ceil(result.data.count / 10),
                        clickCallback: function (newpage) {
                            page = newpage;
                            reload();
                        }
                    });
                }
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            //console.log(result.data);
            alert(errorThrown);
        }
    });
}

function title() {
    var title = '<ul class="title">' +
        '<li class="tal" style="box-sizing:border-box;padding-left:2%;+width:17%;">时间</li>' +
        '<li class="tar" style="width:17%;">类型</li>' +
        '<li class="tar" style="width:17%;">交易金额(元)</li>' +
        '<li class="tar" style="width:21%;">交易后金额(元)</li>' +
        '<li class="tar" style="width:13%;">状态</li>' +
        '<li class="detail tar" style="width: 14%;box-sizing:border-box;padding-right:1%;+width:10%;">操作</li>' +
        '</ul>';
    $(".answer").html(title);
}

function allData(result) {
    if ($(".content")) {
        $(".content").remove();
    }

    for (var i = 0; i < result.data.length; i++) {
        var type = '收入';
        if (result.data[i].amount < 0) {
            type = '支出';
        }
        var content = '<ul class="content">';
        content += '<li class="tal" style="box-sizing:border-box;padding-left:2%;+width:17%;">' + result.data[i].finishedTime + '</li>' +
            '<li class="tar" style="width:17%;">' + result.data[i].bizCode + '</li>' +
            '<li class="tar" style="width:17%;">' + result.data[i].amount + '</li>' +
            '<li class="tar" style="width:21%;">' + result.data[i].balance + '(' + result.data[i].accountType + ')</li>' +
            '<li class="tar" style="width:13%;">' + result.data[i].status + '</li>' +
            '<li class="detail tar" style="width: 14%;box-sizing:border-box;padding-right:1%;+width:10%;">' +
            '<div class="detail-title blue">详情</div>' +
            '<div class="zichan-detail have-more"><i></i>' +
            '<p>' +
            '<span class="fl">时间</span>' +
            '<span class="fr">' + result.data[i].finishedTime + '</span>' +
            '</p>' +
            '<p>' +
            '<span class="fl">类型</span>' +
            '<span class="fr">' + result.data[i].bizCode + '</span>' +
            '</p>' +
            '<p>' +
            '<span class="fl jine-name">' + type + '</span>' +
            '<span class="fr">' + result.data[i].amount + '元</span>' +
            '</p>' +
            '<p>' +
            '<span class="fl">流水</span>' +
            '<span class="fr">' + result.data[i].flowNo + '</span>' +
            '</p>' +
            '<p>' +
            '<span class="fl">备注</span>' +
            '<span class="fr">' + result.data[i].memo + '</span></p></div>' +
            '</li>';

        content += '</ul>';

        $('.answer').append($(content));

    }
}

// 点击提醒查看详情
$(".icon-tixing").hover(function (event) {
    $(this).siblings(".tixing").toggle();
}, function () {
    $(this).siblings(".tixing").toggle();
});