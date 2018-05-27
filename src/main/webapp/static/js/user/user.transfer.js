/**
 * Created by suhao on 2017/5/14.
 */
var action = {
    cantransfer: 'cantransfer',
    transfering: 'transfering',
    transferOut: 'transferOut',
    transferIn: 'transferIn'
};
var actionUrl = {
    cantransfer: ctx + '/uc/transfer/data',
    transfering: ctx + '/uc/transfer/list/data?state=TRANSFERING',
    transferOut: ctx + '/uc/transfer/list/data?state=TRANSFERED',
    transferIn: ctx + '/uc/transfer/data?searchBuyIn=true'
};

var dataTpl = {
    cantransfer: 'cantransferTpl',
    transfering: 'transferingTpl',
    transferOut: 'transferOutTpl',
    transferIn: 'transferInTpl'
};

var currentAction = action.cantransfer;
var reqParams = {};

$(function () {
    dataLoad(currentAction, reqParams);

    $('.helpOne').mouseover(function () {
        $('.helpTipsOne').show();
    })
    $('.helpOne').mouseout(function () {
        $('.helpTipsOne').hide();
    })
    $('.helpTow').mouseover(function () {
        $('.helpTipsTwo').show();
    })
    $('.helpTow').mouseout(function () {
        $('.helpTipsTwo').hide();
    })

    $('.transferMenu').click(function () {
        var $this = $(this);
        var searchType = $this.attr("searchType");
        currentAction = action[searchType];
        $this.addClass('cur').siblings('.transferMenu').removeClass('cur');
        $('#' + currentAction + 'Title').show().siblings('.title').hide();
        console.log(currentAction);
        console.log(reqParams);
        dataLoad(currentAction, reqParams);
    });

    var $transferForm = $("#transferForm");
    $transferForm.validate({
        rules: {
            agreement: "required"
        },
        messages: {
            agreement: "请先阅读《债权转让及受让协议》并确认同意"
        },
        errorPlacement: function(error, element) {
            layer.tips(error.text(), element, {
                tipsMore: true
            });
        },
        unhighlight: function(element) {
            // $(element).closest(".bank").find(".annotate").text("");
        },
        submitHandler: function(form) {
            form.submit();
        }
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
                $('#dataConts').html(string);
                transfer();
            });
            laypage({
                skin: '#B81B26',
                cont: 'pagination',
                pages: res.pages,
                jump: function (e, first) { //触发分页后的回调
                    if (!first) {
                        $.getJSON(actionUrl[action], $.extend({
                                page: e.curr,//当前页
                                rows: res.pageSize
                            }, params),
                            function (rest) {
                                e.pages = e.last = rest.pages; //重新获取总页数，一般不用写
                                //解析数据
                                laytpl(tpl.innerHTML).render(rest, function (stringt) {
                                    $('#dataConts').html(stringt);
                                    transfer();
                                });
                            }
                        );
                    }
                }
            });
        }
    );
};

function transfer() {

    $("a[name=transfer]").click(function () {
        $.ajax({
            url: ctx + "/uc/transfer/transfer.json",
            type:"post",
            data:{id:$(this).attr('value')},
            success:function(data){
                if(!data.success){
                    // layer.msg(data.message,3,-1);
                    layer.msg(data.message);
                    return;
                }
                if(data.success){
                    // var index = layer.open({
                    //     type: 1,   //0-4的选择,
                    //     title: '',
                    //     shift:'top',
                    //     border: [3,0.9,'#CCCCCC'],
                    //     closeBtn: [0],
                    //     shade:[0.9,'#FDFDFD'],
                    //     shadeClose: true,
                    //     closeBtn:[1,true],
                    //     area: ['600px', '530px'],
                    //     content: $('#trasfer'),
                    // });
                    $('#transferModal').modal({
                        backdrop : 'static'
                    });
                    var message = $.parseJSON(data.message);
                    $("#borrowingId").text("ID  " + message.borrowingId);
                    $("#borrowingIdParam").val(message.borrowingId);
                    $("#yuanAmount").text(message.yuanAmount+"元");
                    $("#yishouLixi").text(message.revoveryedInterest);
                    $("#interest").text(message.interest);
                    $("#lowPeriod").text(message.lowPeriod);
                    $("#daishoulixi").text(message.daishoulixi);
                    $("#daishoubenjin").text(message.daishoubenjin);
                    $("#daishouzonge").text(message.daishoubenjin+message.daishoulixi);
                    $("#zhuanrangbenjin").text(message.daishoubenjin);
                    $("#totalPrice").text(message.totalPrice);
                    $("#totalValue").text(message.totalValue);
                    $("#trans_em_fee").text(message.fee);
                    $("#repaymentMethodDes").text(message.repaymentMethodDes);
                    $("#nextRepaymentDate").text(dateFormatter(new Date(message.nextRepaymentDate)));
                    $("#agreement").attr("href",ctx + "/uc/transfer/agreement/" + message.borrowingId);
                    // $("#recovery_id").val(message.id);
                    // $("#trans_em_fee").attr('value',message.fee);
                    // $("#trans_em_yujixiaci").text(message.yujixiaci);
                    // jisuanAmount(1,1);

                    $("#trans_em_total").text(message.totalPrice);
                    $("#trans_em_yuji").text((message.totalPrice - message.fee).toFixed(2));
                }
            }
        });
    });

    $("a[name=transferCancel]").click(function () {

        var bid = $(this).attr('value');

        layer.open({
            title: '提示'
            ,content: '确认撤销?'
            ,btn: ['确认', '取消']
            ,yes: function(index, layero){

                $.ajax({
                    url: ctx + "/uc/transfer/transferCancel",
                    type:"post",
                    data:{id:bid},
                    dataType: "json",
                    success:function(data){
                        if(!data.success){
                            layer.msg(data.message);
                            return;
                        }
                        if(data.success){
                            layer.msg(data.message);
                            // setTimeout("location.reload()", 2000);
                            setTimeout("location.href='/uc/transfer'", 2000);
                        }
                    },
                    error: function(data) {
                        layer.msg('撤销失败');
                        // setTimeout("location.reload()", 2000);
                        setTimeout("location.href='/uc/transfer'", 2000);
                    }
                });
            },no: function(index, layero){

            }
            ,cancel: function(){

            }
        });

    });

}