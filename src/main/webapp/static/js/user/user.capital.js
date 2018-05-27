var dateSearchParams = {
    all: {},
    last_one_month: {
        filter_GED_create_date: '',
        filter_LED_create_date: ''
    },
    last_three_month: {
        filter_GED_create_date: '',
        filter_LED_create_date: ''
    },
    last_year: {
        filter_GED_create_date: '',
        filter_LED_create_date: ''
    }
};

var action = {
    capital: 'capital',
    recharge: 'recharge',
    withdraw: 'withdraw'
};
var actionUrl = {
    capital: ctx + '/uc/capital/data',
    recharge: ctx + '/uc/recharge/json',
    withdraw: ctx + '/uc/withdraw/json'
};
var countActionUrl = {
    capital: '',
    recharge: ctx + '/uc/recharge/count',
    withdraw: ctx + '/uc/withdraw/count'
};
var dataTpl = {
    capital: 'capitalTpl',
    recharge: 'rechargeTpl',
    withdraw: 'withdrawTpl'
};
var typeSearchParams = {
    capital: {
        name : 'method'
    },
    recharge:  {
        name : 'status'
    },
    withdraw:  {
        name : 'status'
    }
};
var currentAction = action.capital;
var reqParams = {};

$(function () {
    var searchType, dateSearch, typecapitalchange;

    dataLoad(currentAction, reqParams);

    $('.typecapital').click(function () {
        var $this = $(this);
        searchType = $this.attr("searchType");
        if (searchType != "") {
            $('.riqi').hide();
        }
        else {
            $('.riqi').show();
        }
        currentAction = action[searchType];
        $('.' + currentAction + 'Type').show().siblings('.riqi').hide();
        $('.' + currentAction + '-records-count').show().siblings('.records-count').hide();
        $this.addClass('cur').siblings('.typecapital').removeClass('cur');
        $('#' + currentAction + 'Title').show().siblings('.title').hide();
        dataLoad(currentAction, reqParams);
        dataCount(currentAction);
    });
    $('.dateSearch').click(function () {
        var $this = $(this);
        $this.addClass('cur').siblings().removeClass('cur');
        dateSearch = $this.attr("value");
        dateSearchChange(dateSearch);
        var name = typeSearchParams[currentAction].name;
        var tmp = {};
        tmp[name] = typecapitalchange;
        dataLoad(currentAction, $.extend(reqParams, tmp));
    });

    $.each(action, function (index, val) {
        $('.' + val + '-form-control').bind("change", function () {
            var $this = $(this);
            typecapitalchange = $this.val();
            var name = typeSearchParams[currentAction].name;
            var tmp = {};
            tmp[name] = typecapitalchange;
            dataLoad(currentAction, $.extend(reqParams, tmp));
        });
    });
    $(document).on('click', '.detail-title', function () {
        var index = $(this).attr('detailIndex');
        var domdetail = "zichan-detail-" + index;
        console.log(domdetail);
        $('.have-more').hide();
        $(('.zichan-detail-') + index).show();
    });
    $("body").on("click", function () {
        $(".zichan-detail").hide();
        $(".zichan-detail").removeClass('cur');
    });
    $('.button-special').click(function () {
        console.log(111);
        $.get('/uc/capital/ucexportCapitalRecord', {
            typecapital: searchType,
            method: searchType,
        })
    });
});

var dataLoad = function (action, params) {
    $.getJSON(
        actionUrl[action],
        $.extend({
            page: 1,
            rows: 5
        }, params),
        function (res) {
            if (res.total > 0) {
                $('.answer').show();
                $('#pagination').show();
                $('.empty').hide();
            } else {
                $('.answer').hide();
                $('#pagination').hide();
                $('.empty').show();
            }
            //使用方式跟独立组件完全一样
            var tpl = document.getElementById(dataTpl[action]);
            laytpl(tpl.innerHTML).render(res, function (string) {
                if (string.trim()) {
                    $("#noDataTip").hide();
                } else {
                    $("#noDataTip").show();
                }
                $('#capitals').html(string);
            });
            laypage({
                skin: '#B81B26',
                cont: 'pagination'
                , pages: res.pages
                , jump: function (e, first) { //触发分页后的回调
                    if (!first) {
                        $.getJSON(actionUrl[action], $.extend({
                                page: e.curr,//当前页
                                rows: res.pageSize
                            }, params),
                            function (rest) {
                                e.pages = e.last = rest.pages; //重新获取总页数，一般不用写
                                //解析数据
                                laytpl(tpl.innerHTML).render(rest, function (stringt) {
                                    $('#capitals').html(stringt);
                            });
                        });
                    }
                }
            });
        });
};

var dataCount = function (action) {
    var countActUrl = countActionUrl[action];
    if (countActUrl == '') {
        return;
    }
    $.getJSON(
        countActUrl,
        {},
        function (res) {
            $('.' + action + '-records-count').find('em').eq(0).text(formatMoney(res.data.allAmount));
            $('.' + action + '-records-count').find('em').eq(1).text(formatMoney(res.data.successAmount));
        });
};

function dateSearchChange(value) {
    var currentTime = new Date();
    switch (value) {
        case 'all': {
            reqParams = {};
            break;
        }
        case 'last_one_month': {
            var endTime = currentTime.format('yyyy-MM-dd 23:59:59') ;
            var date1Month = new Date(currentTime.setMonth(currentTime.getMonth() - 1));
            var startTime = date1Month.format('yyyy-MM-dd 00:00:00');
            dateSearchParams.last_one_month.filter_GED_create_date = startTime;
            dateSearchParams.last_one_month.filter_LED_create_date = endTime;
            reqParams = dateSearchParams.last_one_month;
            break;
        }
        case 'last_three_month': {
            var endTime = currentTime.format('yyyy-MM-dd 23:59:59');
            var date3Month = new Date(currentTime.setMonth(currentTime.getMonth() - 3));
            var startTime = date3Month.format('yyyy-MM-dd 00:00:00');
            dateSearchParams.last_three_month.filter_GED_create_date = startTime;
            dateSearchParams.last_three_month.filter_LED_create_date = endTime;
            reqParams = dateSearchParams.last_three_month;
            break;
        }
        case 'last_year': {
            var endTime = currentTime.format('yyyy-MM-dd 23:59:59');
            var dateYear = new Date(currentTime.setFullYear(currentTime.getFullYear() - 1));
            var startTime = dateYear.format('yyyy-MM-dd 00:00:00');
            dateSearchParams.last_year.filter_GED_create_date = startTime;
            dateSearchParams.last_year.filter_LED_create_date = endTime;
            reqParams = dateSearchParams.last_year;
            break;
        }
    }
}

$(function(){
    // 点击提醒查看详情
    $(".fa-question-circle-o").hover(function(){
        var index = $(this).parent().index();
        $(".showbox"+index).fadeIn();
    },function(){
        var index = $(this).parent().index();
        $(".showbox"+index).hide();
    });
    $(".asset_title .fa-question-circle-o").hover(function(){
        $(this).siblings(".tixing").toggle();
    },function(){
        $(this).siblings(".tixing").toggle();
    });

    $(".otherBox .fa-question-circle-o").hover(function(){
        $(this).parents("li").children(".tixing").toggle();
    },function(){
        $(this).parents("li").children(".tixing").toggle();
    });

    $(".fa .fa-question-circle-o").hover(function(){
        $(this).siblings(".feetip .tixing .poundage").toggle();
    },function(){
        $(this).siblings(".feetip .tixing .poundage").toggle();
    });

    $(".fa .fa-question-circle-o").hover(function(){
        $(this).parents("li").children(".feetip .tixing .poundage").toggle();
    },function(){
        $(this).parents("li").children(".feetip .tixing .poundage").toggle();
    });
});
