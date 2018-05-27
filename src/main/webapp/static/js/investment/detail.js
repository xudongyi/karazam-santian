/**
 * Created by suhao on 2017/4/19.
 */
$(function () {
    var $investmentConfirmForm = $("#investmentConfirmForm"), $balanceAmount = $investmentConfirmForm.find("[name='amount']"),
        $investmentAmount = $("#investmentAmount"),
        $investmentConfirmFromSubmit = $investmentConfirmForm.find(":submit"), rsaKey = new RSAKey(),
        $preferentialAmount = $("#preferentialAmount"),$availableAmount = $("#availableAmount");
    var tenderAmount = $('#tender_amount');
    var errorTips = $('#errorTips');
    var previewInterest = $('#preview_interest');
    var userAmount = $("#userAmount").val();//用户余额
    var num = parseInt(userAmount/multiple);//用户余额除以最小投资额
    tenderAmount.on('focus', function () {
        errorTips.text('');
    });
    // 加减投资金额
    $('#reduce').on('click', function () {
        errorTips.text('');
        // if (userAmount<=0) {
        //     errorTips.text('余额不足');
        //     previewInterest.parent().parent().hide();
        //     return;
        // }
        var tamount = new Number(tenderAmount.val());
        var value = tamount - multiple;
        if (maxAmout == 0) {
            errorTips.text('项目剩余可投金额' + maxAmout + '元');
            previewInterest.parent().parent().hide();
            return;
        }
        if (value < multiple) {
            errorTips.text('投标金额不能小于起投金额' + multiple + '元');
            previewInterest.parent().parent().hide();
            tenderAmount.val(multiple);
            return;
        } else {
            tenderAmount.val(value);
        }
        tenderAmount.change();
    });
    $("#tender_amount").on('input change',function(e){
        errorTips.text('');
        var value = new Number($(this).val());
        if (value > maxAmout) {
            errorTips.text('投标金额不能大于可投金额' + maxAmout + '元');
            previewInterest.parent().parent().hide();
            tenderAmount.val(maxAmout);
        } else {
            tenderAmount.val(value);
            if(!/^[0-9]*$/.test($(this).val())){
                previewInterest.parent().parent().hide();
                errorTips.text('输入金额格式错误');
                return;
            }
            calPrrofit($(this).val());
        }
    });
    $('#plus').on('click', function () {
        errorTips.text('');
        var tamount = new Number(tenderAmount.val());
        var value = tamount + multiple;
        // if (userAmount<=0) {
        //     errorTips.text('余额不足');
        //     previewInterest.parent().parent().hide();
        //     return;
        // }
        // if(maxAmout>userAmount && value>userAmount){
        //     errorTips.text('余额不足，只可投资' + num*multiple + '元，请充值');
        //     previewInterest.parent().parent().hide();
        //     return;
        // }

        if (maxAmout == 0) {
            errorTips.text('项目剩余可投金额' + maxAmout + '元');
            previewInterest.parent().parent().hide();
            return;
        }
        if (value > maxAmout) {
            errorTips.text('投标金额不能大于可投金额' + maxAmout + '元');
            previewInterest.parent().parent().hide();
            tenderAmount.val(maxAmout);
            return;
        } else {
            tenderAmount.val(value);
        }
        tenderAmount.change();
    });
    $('#tender_all_btn').on('click', function () {
        errorTips.text('');
        // if (userAmount<=0) {
        //     errorTips.text('余额不足');
        //     return;
        // }
        // if(maxAmout>userAmount){
        //     tenderAmount.val(num*multiple);
        // }else {
        //     tenderAmount.val(maxAmout);
        // }
        tenderAmount.val(maxAmout);
        tenderAmount.change();
    });

    countingDown(nowTime);

    $investmentConfirmForm.validate({
        rules: {
            agreement: "required"
        },
        messages: {
            agreement: "请先阅读《平台投资协议》"
        },
        errorPlacement: function(error, element) {
            element.closest("td").find(".annotate").text(error.text());
        },
        unhighlight: function(element) {
            $(element).closest("td").find(".annotate").text("");
        },
        submitHandler: function(form) {
            $investmentConfirmFromSubmit.prop("disabled", true);
            form.submit();
        }
    });

    $('#tender_buy_now').on('click', function () {
        var tamount = tenderAmount.val();
        // if (tamount > userAmount) {
        //     errorTips.text('余额不足');
        //     previewInterest.parent().parent().hide();
        //     return;
        // }

        if (typeof($("#hideUserCoupons").val()) == "undefined") {

        }else{
            $("#coupon").empty();
            $("#coupon").append("<option value=''>---不使用---</option>");
            var userCouponJson = $("#hideUserCoupons").val();
            var userCouponObj = eval('(' + userCouponJson + ')');
            for (var i=0;i<userCouponObj.length;i++){
                console.log(eval('(' + userCouponObj[i].userRule + ')').beginAmount);
                if(eval('(' + userCouponObj[i].userRule + ')').beginAmount<=tamount){
                    $("#coupon").append("<option value='"+ userCouponObj[i].id +"'>"+ userCouponObj[i].title +"</option>");
                }
            }
        }
        if (!tamount || tamount==0) {
            previewInterest.parent().parent().hide();
            errorTips.text('请输入投标金额');
            return;
        }
        if(!/^[0-9]*$/.test(tamount)){
            previewInterest.parent().parent().hide();
            errorTips.text('输入金额格式错误');
            return;
        }
        $investmentAmount.text(tamount);

        var balancePreferentialAmount = 0;
        $preferentialAmount.text(balancePreferentialAmount);

        $availableAmount.text(floatSub($balanceAmount.val(), balancePreferentialAmount));
        $investmentConfirmForm.find('input[name=amount]').val(tamount);
        layer.open({
            content: $('#balance_div'),
            title: '确认投资',
            area: '350px',
            type: "1"
        });
    });

    $.getJSON(ctx + '/investment/records/' + $projectId,
        {
            page: 1,
            rows: 5,
            isTransfer: false
        },
        function (res) {
            var tpl = document.getElementById("pageInvestmentRecordsTpl");
            laytpl(tpl.innerHTML).render(res, function (string) {
                $('#pageInvestmentRecords').html(string);
            });
            laypage({
                skin: '#50a846',
                cont: 'pagination'
                , pages: res.pages
                , jump: function (e, first) { //触发分页后的回调
                    if (!first) {
                        $.getJSON(ctx + '/investment/records/' + $projectId, {
                            page: e.curr,//当前页
                            rows: 5,
                            isTransfer: false
                        }, function (rest) {
                            e.pages = e.last = rest.pages; //重新获取总页数，一般不用写
                            laytpl(tpl.innerHTML).render(rest, function (stringt) {
                                $('#pageInvestmentRecords').html(stringt);
                            });
                        });
                    }
                }
            });
        }
    );

});

function quickLogin() {
    $('#quickLoginModal').modal({
        backdrop : 'static'
    });
    var $quickLoginForm = $("#quickLoginForm"), $quickLoginSubmit = $quickLoginForm.find(":submit"), $flashMessage = $("#flashMessage");
    var $username = $("#username"), $password = $("#password");
    $quickLoginSubmit.prop("disabled", false);
    $quickLoginForm.validate({
        errorClass: "errorMessage",
        rules: {
            username: {
                required: true,
                pattern: /^[0-9a-z_A-Z\u4e00-\u9fa5]+$/,
                minlength: 2
            },
            password: {
                required: true,
                minlength: 4
            }
        },
        messages: {
            username: {
                required: "请输入用户名",
                pattern: "用户名只允许包含中文、英文、数字、下划线",
                minlength: "用户名最小长度为2"
            },
            password: {
                required: "请输入密码",
                minlength: "密码最小长度为4"
            }
        },
        errorPlacement: function(error, element) {
            element.closest(".errorcls").find(".errorMessage").text(error.text());
        },
        unhighlight: function(element) {
            $(element).closest(".errorcls").find(".errorMessage").text("");
        },
        submitHandler: function(form) {
            $quickLoginSubmit.prop("disabled", true);

            var rsaKey = new RSAKey();
            rsaKey.setPublic(b64tohex(modules), b64tohex(exponent));

            $.ajax({
                url: ctx + "/login",
                data: {
                    username: $username.val(),
                    password: hex2b64(rsaKey.encrypt($password.val())),
                    type:$('input:radio:checked').val()
                },
                type: "post",
                dataType: "json",
                cache: false,
                beforeSend: function(request, settings) {
                    request.setRequestHeader("token", $.cookie("token"));
                    $quickLoginSubmit.val(" 登 录 中 ... ");
                },
                success: function (result) {
                    if (result.status == "success") {
                        // top.location.href = ctx + "/investment/" + $projectId;
                        window.location.reload();
                    } else {
                        layer.msg(result.message);
                        modules = result.data.modules, exponent = result.data.exponent;
                        $quickLoginSubmit.val(" 登 录 ");
                        $quickLoginSubmit.prop("disabled", false);
                    }
                },
                error: function() {
                    layer.msg("登录失败，请刷新页面重试");
                    $quickLoginSubmit.val(" 登 录 失 败 ");
                }
            });
        }
    });
}

function countingDown(nowTime) {
    var intervalId = window.setInterval(function() {
        $(".countdown").each(function(index, obj) {
            var $obj = $(obj);
            var timeDifference = new Date($obj.attr("startTime"))/1000 - nowTime;
            if (timeDifference > 1) {
                var days = Math.floor((timeDifference / 3600) / 24);
                var hours = Math.floor((timeDifference / 3600) % 24);
                var minutes = Math.floor((timeDifference / 60) % 60);
                var seconds = Math.floor(timeDifference % 60);
                // $obj.text((days < 10 ? "0" + days : days) + "天-" + (hours < 10 ? "0" + hours : hours) + "时-" + (minutes < 10 ? "0" + minutes : minutes) + "分-" + (seconds < 10 ? "0" + seconds : seconds) + "秒");
                if (hours > 1) {
                    $obj.text(datetimeFormatter(new Date($obj.attr("startTime"))));
                } else {
                    $obj.text((hours < 10 ? "0" + hours : hours) + "时-" + (minutes < 10 ? "0" + minutes : minutes) + "分-" + (seconds < 10 ? "0" + seconds : seconds) + "秒");
                }
            } else {
                $obj.text("立即购买").hide().siblings('a').show();
            }
        });
        nowTime++;
    }, 1000);
}

function calPrrofit(amount) {
    var rate = $('#calInterestRate').val(),
        repaymentMethod = $('#calRepaymentMethod').val(),
        period = $('#calPeriod').val(),
        periodUnit = $('#calPeriodUnit').val();

    $.getJSON(ctx + '/investment/calculator/result', {
            amount : amount,
            rate : rate,
            repaymentMethod : repaymentMethod,
            borrowingPeriod : period,
            periodUnit : periodUnit
        },
        function (res) {
            var previewInterest = $('#preview_interest');
            previewInterest.text(currency(res.sum_invests, "￥")).parent().parent().show();
        }
    );
}