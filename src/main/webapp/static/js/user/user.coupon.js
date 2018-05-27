$(function () {
    var state = "UNUSED";
    dataLoad(state);
    $('.recoveryView').click(function () {
        var $this = $(this);
        state = $this.attr('state');
        $this.addClass('cur').siblings('li').removeClass('cur');
        dataLoad(state);
    });
});

var reqParams = {};
function dataLoad(state) {
    var params = $.extend({
        page: 1,
        rows: 5
    }, reqParams);
    $.getJSON(ctx + '/uc/userCoupon/data/'+state, params, function (res) {

        //使用方式跟独立组件完全一样
        var tpl = document.getElementById("dataTpl");
        laytpl(tpl.innerHTML).render(res, function (string) {
            console.log(res);
            if(res.total>0){
                $("#noDataTip").hide();
                $("#pagination").show();
                $(".answer").show();
                if (state=='USED'){
                    $("#usedDate").show();
                    $("#createDate").hide();
                }else {
                    $("#usedDate").hide();
                    $("#createDate").show();
                }
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
                    $.getJSON(ctx + '/uc/userCoupon/data/' + state, $.extend({
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