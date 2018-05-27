/**
 * Created by suhao on 2017/4/23.
 */
var startRecoveryTime, endRecoveryTime;
var searchRecoveryParams = {
    all : {},
    next_one_month : {
        filter_GED_pay_date : '',
        filter_LED_pay_date : ''
    },
    next_three_month : {
        filter_GED_pay_date : '',
        filter_LED_pay_date : ''
    },
    next_year : {
        filter_GED_pay_date : '',
        filter_LED_pay_date : ''
    }
};
var reqParams = {};
var state = 'recovering';
$(function () {
    $('.weishouDetail').hide();
    dataLoad(state);
    dataCount(state);
    $("#dateRange ul li").click(function(){
        $(this).addClass("cur").siblings().removeClass("cur");
        setDateRange($(this).text());
        dataLoad(state);
        dataCount(state);
    });

    $('.recoveryView').click(function () {
        var $this = $(this);
        state = $this.attr('state');
        console.log(1);
        if(state == 'recoveried'){
            $(".futureButton").hide();
            $('#recoveriedPayDate').show();
            $('#recoveringPayDate').hide();
            $('#recoveriedCapitalInterest').show();
            $('#recoveringCapitalInterest').hide();
            $('#recoveriedCapital').show();
            $('#recoveringCapital').hide();
            $('#recoveriedInterest').show();
            $('#recoveringInterest').hide();
        }else {
            $(".futureButton").show();
            $('#recoveriedPayDate').hide();
            $('#recoveringPayDate').show();
            $('#recoveriedCapitalInterest').hide();
            $('#recoveringCapitalInterest').show();
            $('#recoveriedCapital').hide();
            $('#recoveringCapital').show();
            $('#recoveriedInterest').hide();
            $('#recoveringInterest').show();
        }
        $this.addClass('cur').siblings('li').removeClass('cur');
        dataLoad(state);
        dataCount(state);
    });

    $("#riqiSearch").click(function(){
        reqParams = {};
        var start = $('#startDate').val();
        startRecoveryTime = new Date(Date.parse(start)).format('yyyy-MM-dd 00:00:00');
        if (start != '') {
            reqParams.filter_GED_pay_date = startRecoveryTime;
        }
        var end = $('#endDate').val();
        endRecoveryTime = new Date(Date.parse(end)).format('yyyy-MM-dd 23:59:59');
        if (end != '') {
            reqParams.filter_LED_pay_date = endRecoveryTime;
        }
        dataLoad(state);
        dataCount(state);
    });

});

function dataLoad(state) {
    var params = $.extend({
        page: 1,
        rows: 5
    }, reqParams);
    $.getJSON(
        ctx + '/uc/recovery/' + state,
        params,
        function (res) {
            if (res.total > 0) {
                $('.answer').show();
                $('#pagination').show();
                $('#recoveriesEmpty').hide();
            } else {
                $('.answer').hide();
                $('#pagination').hide();
                $('#recoveriesEmpty').show();
            }
            var tpl = document.getElementById("recoveryTpl");
            laytpl(tpl.innerHTML).render(res, function (string) {
                $('#recoveryCont').html(string);
            });
            laypage({
                skin: '#00AA91',
                cont: 'pagination',
                pages: res.pages,
                jump: function (e, first) { //触发分页后的回调
                    if (!first) {
                        $.getJSON(ctx + '/uc/recovery/' + state, $.extend({
                            page: e.curr,//当前页
                            rows: res.pageSize
                        }, reqParams), function (rest) {
                            e.pages = e.last = rest.pages; //重新获取总页数，一般不用写
                            //解析数据
                            laytpl(tpl.innerHTML).render(rest, function (string) {
                                $('#recoveryCont').html(string);
                            });
                        });
                    }
                }
            });
        }
    );
}

function dataCount(state) {
    var params = reqParams;
    $.getJSON(
        ctx + '/uc/recovery/count/' + state,
        params,
        function (res) {
            var $that = $('.' + state + 'Detail');
            $that.show().siblings('.weishouDetail').hide();
            $that.find('.capitals').text(currency(res.data.capitals));
            $that.find('.interests').text(currency(res.data.interests));
        }
    );
}

function setDateRange(time){
    var currentTime = new Date();
    switch (time){
        case '全部': {
            $("#startDate").val('');
            $("#endDate").val('');
            reqParams = {};
            break;
        }
        case '未来1个月':{
            $("#startDate").val(currentTime.format('yyyy-MM-dd'));
            startRecoveryTime = currentTime.format('yyyy-MM-dd 00:00:00');
            var date1Month = new Date(currentTime.setMonth(currentTime.getMonth()+1));
            $("#endDate").val(date1Month.format('yyyy-MM-dd'));
            endRecoveryTime = date1Month.format('yyyy-MM-dd 23:59:59');
            searchRecoveryParams.next_one_month.filter_GED_pay_date = startRecoveryTime;
            searchRecoveryParams.next_one_month.filter_LED_pay_date = endRecoveryTime;
            reqParams = searchRecoveryParams.next_one_month;
            break;
        }
        case '未来3个月':{
            $("#startDate").val(currentTime.format('yyyy-MM-dd'));
            startRecoveryTime = currentTime.format('yyyy-MM-dd 00:00:00');
            var date3Month = new Date(currentTime.setMonth(currentTime.getMonth()+3));
            $("#endDate").val(date3Month.format('yyyy-MM-dd'));
            endRecoveryTime = date3Month.format('yyyy-MM-dd 23:59:59');
            searchRecoveryParams.next_three_month.filter_GED_pay_date = startRecoveryTime;
            searchRecoveryParams.next_three_month.filter_LED_pay_date = endRecoveryTime;
            reqParams = searchRecoveryParams.next_three_month;
            break;
        }
        case '未来1年':{
            $("#startDate").val(currentTime.format('yyyy-MM-dd'));
            startRecoveryTime = currentTime.format('yyyy-MM-dd 00:00:00');
            var dateYear = new Date(currentTime.setFullYear(currentTime.getFullYear()+1));
            $("#endDate").val(dateYear.format('yyyy-MM-dd'));
            endRecoveryTime = dateYear.format('yyyy-MM-dd 23:59:59');
            searchRecoveryParams.next_year.filter_GED_pay_date = startRecoveryTime;
            searchRecoveryParams.next_year.filter_LED_pay_date = endRecoveryTime;
            reqParams = searchRecoveryParams.next_year;
            break;
        }
    }
}