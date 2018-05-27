$.getJSON(ctx + '/uc/investment/data', {page: 1, rows: 5}, function (res) {

    //使用方式跟独立组件完全一样
    var tpl = document.getElementById("dataTpl");
    laytpl(tpl.innerHTML).render(res, function (string) {
        if(res.total>0){
            $("#noDataTip").hide();
            $("#pagination").show();
            $(".answer").show();
        }else{
            $(".answer").hide();
            $("#pagination").hide();
            $("#noDataTip").show();
        }
        $('#dataDiv').html(string);
    });
    laypage({
        skin: '#B81B26',
        cont: 'pagination',
        pages: res.pages,
        jump: function (e, first) { //触发分页后的回调
            if (!first) {
                $.getJSON(ctx + '/uc/investment/data', {
                    page: e.curr,//当前页
                    rows: res.pageSize
                }, function (rest) {
                    e.pages = e.last = rest.pages; //重新获取总页数，一般不用写
                    //解析数据
                    laytpl(tpl.innerHTML).render(rest, function (stringt) {
                        $('#dataDiv').html(stringt);
                    });
                });
            }
        }
    });
});

// 加载回款计划
function loadInvestRefund(_this, id) {
    $(_this).siblings(".search-a-content").show();

    $.ajax({
        type: 'get',
        url: ctx + "/uc/myrepayment/plans/" + id,
        data: {},
        dataType:"json",
        success: function (obj) {
            if (obj.success) {
                huikuanContent(obj);
            } else {
                alert(obj.message);
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });
}

function huikuanContent(result) {
    if ($(".search-a-content-content")) {
        $(".search-a-content-content").remove();
    }
    var borrowing = result.data.borrowing;
    var plans = result.data.repaymentPlans;
    if (plans.length>0) {
        for (var i = 0; i < plans.length; i++) {
            var content = '<ul class="search-a-content-content">';
            content += '<li>' + plans[i].repaymentRecordPeriod + '/' + borrowing.repayPeriod + '</li>' +
                '<li>' + formatDate(plans[i].repaymentRecordPayDate) + '</li>' +
                '<li class="tar">' + formatMoney(plans[i].repaymentRecordCapital+plans[i].repaymentRecordInterest) + '</li>' +
                '<li class="tar">' + formatMoney(plans[i].repaymentRecordCapital) + '</li>' +
                '<li class="tar">' + formatMoney(plans[i].repaymentRecordInterest) + '</li>' +
                '<li>' + plans[i].stateDes + '</li>';
            content += '</ul>';
            $(".search-a-content>div").append($(content));
        }
    }
}

$("#dateRange ul li").click(function () {
    $(this).addClass("cur").siblings().removeClass("cur");
    setDateRange($(this).text());
    dataLoad();
});
var startRecoveryTime, endRecoveryTime;

function setDateRange(time) {
    switch (time) {
        case '全部':
        {
            $("#startDate").val('');
            endRecoveryTime = null;
            $("#endDate").val('');
            startRecoveryTime = null;
            break;
        }
        case '近1个月':
        {
            $("#endDate").val(new Date().format('yyyy-MM-dd'));
            endRecoveryTime = new Date().format('yyyy-MM-dd 23:59:59');
            $("#startDate").val(new Date(new Date().setMonth(new Date().getMonth() - 1)).format('yyyy-MM-dd'));
            startRecoveryTime = new Date(new Date().setMonth(new Date().getMonth() - 1)).format('yyyy-MM-dd 00:00:00');
            break;
        }
        case '近3个月':
        {
            $("#endDate").val(new Date().format('yyyy-MM-dd'));
            endRecoveryTime = new Date().format('yyyy-MM-dd 23:59:59');
            $("#startDate").val(new Date(new Date().setMonth(new Date().getMonth() - 3)).format('yyyy-MM-dd'));
            startRecoveryTime = new Date(new Date().setMonth(new Date().getMonth()- 3)).format('yyyy-MM-dd 00:00:00');
            break;
        }
        case '近1年':
        {
            $("#endDate").val(new Date().format('yyyy-MM-dd'));
            endRecoveryTime = new Date().format('yyyy-MM-dd 23:59:59');
            $("#startDate").val(new Date(new Date().setFullYear(new Date().getFullYear() - 1)).format('yyyy-MM-dd'));
            startRecoveryTime = new Date(new Date().setFullYear(new Date().getFullYear() - 1)).format('yyyy-MM-dd 00:00:00');
            break;
        }
    }
}

$("#riqiSearch").click(function(){

    var start = $('#startDate').val();
    startRecoveryTime = new Date(Date.parse(start)).format('yyyy-MM-dd 00:00:00');
    var end = $('#endDate').val();
    endRecoveryTime = new Date(Date.parse(end)).format('yyyy-MM-dd 23:59:59');
    dataLoad();
});

function dataLoad() {

    $.getJSON(ctx + '/uc/investment/data', {page: 1, rows: 5,"startDate":startRecoveryTime,"endDate":endRecoveryTime}, function (res) {

        //使用方式跟独立组件完全一样
        var tpl = document.getElementById("dataTpl");
        laytpl(tpl.innerHTML).render(res, function (string) {
            if(res.total>0){
                $("#noDataTip").hide();
                $("#pagination").show();
                $(".answer").show();
            }else{
                $(".answer").hide();
                $("#pagination").hide();
                $("#noDataTip").show();
            }
            $('#dataDiv').html(string);
        });
        laypage({
            skin: '#B81B26',
            cont: 'pagination',
            pages: res.pages,
            jump: function (e, first) { //触发分页后的回调
                if (!first) {
                    $.getJSON(ctx + '/uc/investment/data', {
                        page: e.curr,//当前页
                        rows: res.pageSize,
                        "startDate": startRecoveryTime,
                        "endDate": endRecoveryTime
                    }, function (rest) {
                        e.pages = e.last = rest.pages; //重新获取总页数，一般不用写
                        //解析数据
                        laytpl(tpl.innerHTML).render(rest, function (stringt) {
                            $('#dataDiv').html(stringt);
                        });
                    });
                }
            }
        });
    });
}

$(function(){
    //投资明细详情展示、隐藏
    $(document).on("mouseover", ".search-b", function () {
        var obj = $(this).children('.search-b-title');
        if (!obj.hasClass("hover")) {
            obj.addClass("hover");
        } else {
            obj.removeClass('hover');
        }
        $(this).children(".son-nav").show();
    });
    $(document).on("mouseout", ".search-b", function () {
        var obj = $(this).children('.search-b-title');
        if (obj.hasClass("hover")) {
            obj.removeClass("hover");
        }
        $(this).children(".son-nav").hide();
    });
    // 点击详情跳出弹框
    $(document).on('click', ".search-a-title", function () {
        $(".search-a-content").hide();
        if ($(this).siblings(".search-a-content").hasClass('cur')) {
            $(this).siblings(".search-a-content").removeClass('cur');
            $(".search-a-content.cur").hide();
        }
        else {
            $(".search-a-content").removeClass("cur");
            $(this).siblings(".search-a-content").addClass('cur');
            $(".search-a-content.cur").show();
        }
    });
    document.onclick = function (event) {
        var event = event || window.event;
        var target = event.target || event.srcElement;
        if (target.className == "undefined" || target.className != "search-a-title") {
            $(".search-a-content.cur").css("display", "none");
        }
    }
})