/**
 * Created by suhao on 2017/4/19.
 */
var types = {
    codes : ['MORTGAGE', 'GUARANTEE', 'CREDIT', 'TRANSFER'],
    des : ['抵押贷', '质押贷', '车商贷', '债权转让']
};
var dataConf = {
    type: 'project',
    url: {
        project: ctx + '/investment/data',
        transfer: ctx + '/transfer/data'
    },
    tpl: {
        project: 'projectTpl',
        transfer: 'transferTpl'
    }
};
var showSizes = [3, 4, 4, 4];
var showPercents = [4, 3, 3, 3];
var data = {
    des : '',
    showPercent : 4,
    rows : []
};
var baseParams = {};
var searchParams = {
    typeSearch : {
        MORTGAGE: {
            type : 'MORTGAGE'
        },
        GUARANTEE:{
            type : 'GUARANTEE'
        },
        CREDIT:{
            type : 'CREDIT'
        },
        TRANSFER: {
            type : 'TRANSFER'
        }
    },
    timeSearch : {
        between_1_to_3 : {
            scope:'BETWEEN_1MONTH_AND_3MONTH'
        },
        between_4_to_12 : {
            filter_GEI_period : 4,
                filter_LEI_period : 12,
            scope:'BETWEEN_3MONTH_AND_12MONTH'
        },
        over_12 : {
            filter_GEI_period : 13,
            scope:'OVER_12MONTH'
        }
    },
    filedSort : {
        sort : 'id',
        order : 'asc'
    }
};
var isSort = false;
var typeSearch;
var periodScope;
$(function () {
    var $progre = $("#progre"),$period = $("#period"),$rate = $("#rate");
    getPageProjects({});
    $('.typeSearch').on('click', function () {
        var $this = $(this);
        $this.addClass('active').siblings('.typeSearch').removeClass('active');
        typeSearch = $this.attr('typeSearch');
        dataConf.type = typeSearch;
        getPageProjects({});
    });
});

var getPageProjects = function (searchParams) {
    var type = dataConf.type;
    baseParams = {
        page: 1,
        rows: 10
    };
    var params = $.extend(baseParams, searchParams);
    var sort = '';
    if (isSort) {
        sort = '&sort=' + this.searchParams.filedSort.sort + '&order=' + this.searchParams.filedSort.order;
    }

    $.getJSON(dataConf.url[type] + sort,
        params,
        function (res) {
            var total = res.total;
            data.rows = res.rows;
            var tpl = document.getElementById(dataConf.tpl[type]);
            laytpl(tpl.innerHTML).render(data, function (string) {
                $('#projectBox').html(string);
                if (total == 0) {
                    $('#projectBox').hide();
                    $('#projectPagination').hide();
                } else {
                    $('#projectBox').show();
                    $('#projectPagination').show();
                }
                countdown();
            });
            laypage({
                skin: '#50a846',
                cont: 'projectPagination'
                , pages: res.pages
                , jump: function (e, first) { //触发分页后的回调
                    if (!first) {
                        $.getJSON(dataConf.url[type] + sort,
                            $.extend(params, {page: e.curr}),
                            function (rest) {
                                e.pages = e.last = rest.pages; //重新获取总页数，一般不用写
                                // //解析数据
                                data.rows = rest.rows;
                                laytpl(tpl.innerHTML).render(data, function (stringt) {
                                    $('#projectBox').html(stringt);
                                    countdown();
                                });
                            });
                    }
                }
            });
        }
    );
};

function countdown() {
    var intervalId = window.setInterval(function() {
        $(".mycountdown").each(function(index, obj) {
            var $obj = $(obj);
            var timeDifference = new Date($obj.attr("startTime"))/1000 - nowTime;
            if (timeDifference > 1) {
                var days = Math.floor((timeDifference / 3600) / 24);
                var hours = Math.floor((timeDifference / 3600) % 24);
                var minutes = Math.floor((timeDifference / 60) % 60);
                var seconds = Math.floor(timeDifference % 60);
                // $obj.text((days < 10 ? "0" + days : days) + "天-" + (hours < 10 ? "0" + hours : hours) + "时-" + (minutes < 10 ? "0" + minutes : minutes) + "分-" + (seconds < 10 ? "0" + seconds : seconds) + "秒");
                if (hours > 1) {
                    $obj.text(datetimeFormatter(new Date($obj.attr("startTime")), 'hh:mm:ss'));
                } else {
                    $obj.text((hours < 10 ? "0" + hours : hours) + ":" + (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds));
                }
            } else {
                $obj.text("立即抢购").removeClass('btn-warning').addClass('btn-theme');
            }
        });
        nowTime++;
    }, 1000);
}