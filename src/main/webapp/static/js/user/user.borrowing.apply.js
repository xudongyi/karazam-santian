var startRecoveryTime, endRecoveryTime;
var searchRecoveryParams = {
    all : {},
    next_one_month : {
        filter_GED_create_date : '',
        filter_LED_create_date : ''
    },
    next_three_month : {
        filter_GED_create_date : '',
        filter_LED_create_date : ''
    },
    next_year : {
        filter_GED_create_date : '',
        filter_LED_create_date : ''
    }
};
var reqParams = {};
var state = 'auditing';
$(function () {
    dataLoad(state);
    $("#dateRange ul li").click(function(){
        $(this).addClass("cur").siblings().removeClass("cur");
        setDateRange($(this).text());
        dataLoad(state);
    });

    $('.recoveryView').click(function () {
        var $this = $(this);
        state = $this.attr('state');
        $this.addClass('cur').siblings('li').removeClass('cur');
        dataLoad(state);
    });

    $("#riqiSearch").click(function(){
        reqParams = {};
        var start = $('#startDate').val();
        startRecoveryTime = new Date(Date.parse(start)).format('yyyy-MM-dd 00:00:00');
        if (start != '') {
            reqParams.filter_GED_create_date = startRecoveryTime;
        }
        var end = $('#endDate').val();
        endRecoveryTime = new Date(Date.parse(end)).format('yyyy-MM-dd 23:59:59');
        if (end != '') {
            reqParams.filter_LED_create_date = endRecoveryTime;
        }
        dataLoad(state);
    });

});

function dataLoad(state) {
    var params = $.extend({
        page: 1,
        rows: 5
    }, reqParams);
    $.getJSON(ctx + '/uc/borrowingApply/data/'+state, params, function (res) {

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
            $('#data').html(string);
        });
        laypage({
            skin: '#B81B26',
            cont: 'pagination'
            , pages: res.pages
            , jump: function (e, first) { //触发分页后的回调
                if (!first) {
                    $.getJSON(ctx + '/uc/borrowingApply/data/' + state, $.extend({
                        page: e.curr,//当前页
                        rows: res.pageSize
                    }, reqParams), function (rest) {
                        e.pages = e.last = rest.pages; //重新获取总页数，一般不用写
                        //解析数据
                        laytpl(tpl.innerHTML).render(rest, function (stringt) {
                            $('#data').html(stringt);
                        });
                    });
                }
            }
        });
    });
}

function setDateRange(time){
    var currentTime = new Date();
    console.log(time);
    switch (time){
        case '全部': {
            $("#startDate").val('');
            $("#endDate").val('');
            reqParams = {};
            break;
        }
        case '近1个月':{
            $("#startDate").val(currentTime.format('yyyy-MM-dd'));
            endRecoveryTime = currentTime.format('yyyy-MM-dd 23:59:59');
            var date1Month = new Date(currentTime.setMonth(currentTime.getMonth()-1));
            $("#endDate").val(date1Month.format('yyyy-MM-dd'));
            startRecoveryTime = date1Month.format('yyyy-MM-dd 00:00:00');
            searchRecoveryParams.next_one_month.filter_GED_create_date = startRecoveryTime;
            searchRecoveryParams.next_one_month.filter_LED_create_date = endRecoveryTime;
            reqParams = searchRecoveryParams.next_one_month;
            break;
        }
        case '近3个月':{
            $("#startDate").val(currentTime.format('yyyy-MM-dd'));
            endRecoveryTime = currentTime.format('yyyy-MM-dd 23:59:59');
            var date3Month = new Date(currentTime.setMonth(currentTime.getMonth()-3));
            $("#endDate").val(date3Month.format('yyyy-MM-dd'));
            startRecoveryTime = date3Month.format('yyyy-MM-dd 00:00:00');
            searchRecoveryParams.next_three_month.filter_GED_create_date = startRecoveryTime;
            searchRecoveryParams.next_three_month.filter_LED_create_date = endRecoveryTime;
            reqParams = searchRecoveryParams.next_three_month;
            break;
        }
        case '近1年':{
            $("#startDate").val(currentTime.format('yyyy-MM-dd'));
            endRecoveryTime = currentTime.format('yyyy-MM-dd 23:59:59');
            var dateYear = new Date(currentTime.setFullYear(currentTime.getFullYear()-1));
            $("#endDate").val(dateYear.format('yyyy-MM-dd'));
            startRecoveryTime = dateYear.format('yyyy-MM-dd 00:00:00');
            searchRecoveryParams.next_year.filter_GED_create_date = startRecoveryTime;
            searchRecoveryParams.next_year.filter_LED_create_date = endRecoveryTime;
            reqParams = searchRecoveryParams.next_year;
            break;
        }
    }
}