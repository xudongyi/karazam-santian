/**
 * Created by suhao on 2017/5/15.
 */
$(function () {
    var $investmentConfirmForm = $("#investmentConfirmForm"), $balanceAmount = $investmentConfirmForm.find("[name='amount']"),
        $investmentAmount = $("#investmentAmount"),
        $investmentConfirmFromSubmit = $investmentConfirmForm.find(":submit"), rsaKey = new RSAKey(),
        $preferentialAmount = $("#preferentialAmount"),$availableAmount = $("#availableAmount"),
        $balanceParts = $investmentConfirmForm.find("[name='parts']");
    var tenderAmount = $('#tender_amount');
    var errorTips = $('#errorTips');
    var previewInterest = $('#preview_interest');
    tenderAmount.on('focus', function () {
        errorTips.text('');
    });
    // 加减投资金额
    $('#reduce').on('click', function () {
        errorTips.text('');
        var tamount = new Number(tenderAmount.val());
        var value = tamount - minAmount;
        if (maxAmount == 0) {
            errorTips.text('项目剩余可投金额' + maxAmount + '元');
            previewInterest.parent().parent().hide();
            return;
        }
        if (value < minAmount) {
            errorTips.text('投标金额不能小于起投金额' + minAmount + '元');
            previewInterest.parent().parent().hide();
            tenderAmount.val(minAmount);
            return;
        } else {
            tenderAmount.val(value);
        }
        tenderAmount.change();
    });
    $("#tender_amount").on('input change',function(e){
        var value = new Number($(this).val());
        if (value > maxAmount) {
            errorTips.text('投标金额不能大于可投金额' + maxAmount + '元');
            previewInterest.parent().parent().hide();
            tenderAmount.val(minAmount);
        } else {
            tenderAmount.val(value);
        }
    });
    $('#plus').on('click', function () {
        errorTips.text('');
        var tamount = new Number(tenderAmount.val());
        var value = tamount + minAmount;
        if (maxAmount == 0) {
            errorTips.text('项目剩余可投金额' + maxAmount + '元');
            previewInterest.parent().parent().hide();
            return;
        }
        if (value > maxAmount) {
            errorTips.text('投标金额不能大于可投金额' + maxAmount + '元');
            previewInterest.parent().parent().hide();
            tenderAmount.val(minAmount);
            return;
        } else {
            tenderAmount.val(value);
        }
        tenderAmount.change();
    });
    $('#tender_all_btn').on('click', function () {
        errorTips.text('');
        if (surplusCapital <= 0) {
            errorTips.text('超过剩余可投金额');
        }
        tenderAmount.val(surplusCapital);
        tenderAmount.change();
    });

    countingDown(nowTime);

    var $investmentFromValidate = $investmentConfirmForm.validate({
        rules: {
            parts: {
                required: true,
                positiveInteger: true,
                min: 1,
                max: validParts
            }
        },
        messages: {
            parts: {
                required: "请输入投资金额",
                positiveInteger: "请输入正确的投资金额",
                min: "投资最小为1份",
                max: "超过剩余可投金额，请重新输入"
            }
        },
        errorPlacement: function(error, element) {
            errorTips.text(error.text());
        },
        unhighlight: function(element) {
            errorTips.text('');
        },
        submitHandler: function(form) {
            $investmentConfirmFromSubmit.prop("disabled", true);
            form.submit();
        }
    });

    $('#tender_buy_now').on('click', function () {
        var tamount = tenderAmount.val();
        if (!tamount) {
            errorTips.text('请输入投标金额');
            return;
        }
        $investmentConfirmForm.find("[name='parts']").val(tamount/100);
        if (!$investmentFromValidate.element($investmentConfirmForm.find("[name='parts']"))) {
            return false;
        }

        var parts = $investmentConfirmForm.find("[name='parts']").val();
        if(!parts){
            layer.open({
                content: "请输入投资份数",
                title: '',
                area: '360px',
                type: "1"
            });
            return;
        }
        $balanceParts.val(parts);

        $.ajax({
            url: ctx + "/transfer/cal",
            type:"post",
            dataType:'json',
            data:{id:$(this).attr('transferId'),parts:parts},
            success:function(data){
                if(!data.success){
                    layer.msg(data.message);
                    return;
                }
                if(data.success){
                    var message = $.parseJSON(data.message);
                    // 债权价值 债权价格 服务费 支付金额
                    $("#transferWorth").text(message.transferWorth.toFixed(2) + "元");
                    $("#transferCapital").text(message.transferCapital.toFixed(2) + "元");
                    $("#transferFee").text(message.transferFee.toFixed(2) + "元");
                    $("#transferAmount").text(message.transferAmount.toFixed(2) + "元");

                    // 确认投资
                    layer.open({
                        content: $('#balance_div'),
                        title: '确认投资',
                        area: '360px',
                        type: "1"
                    });
                }
            }
        });
    });

    $.getJSON(ctx + '/investment/records/' + $projectId,
        {
            page: 1,
            rows: 5,
            isTransfer: true
        },
        function (res) {
            var tpl = document.getElementById("pageInvestmentRecordsTpl");
            laytpl(tpl.innerHTML).render(res, function (string) {
                $('#pageInvestmentRecords').html(string);
            });
            laypage({
                skin: '#50a846',
                cont: 'paginationInvestment'
                , pages: res.pages
                , jump: function (e, first) { //触发分页后的回调
                    if (!first) {
                        $.getJSON(ctx + '/investment/records/' + $projectId, {
                            page: e.curr,//当前页
                            rows: 5,
                            isTransfer: true
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
            var timeDifference = new Date($obj.attr("expireDate"))/1000 - nowTime;
            if (timeDifference > 1) {
                var days = Math.floor((timeDifference / 3600) / 24);
                var hours = Math.floor((timeDifference / 3600) % 24);
                var minutes = Math.floor((timeDifference / 60) % 60);
                var seconds = Math.floor(timeDifference % 60);
                $('#expireLastD').text(days < 10 ? "0" + days : days);
                $('#expireLastH').text(hours < 10 ? "0" + hours : hours);
                $('#expireLastM').text(minutes < 10 ? "0" + minutes : minutes);
                $('#expireLastS').text(seconds < 10 ? "0" + seconds : seconds);
            } else {
                $('#expireLastD').text("00");
                $('#expireLastH').text("00");
                $('#expireLastM').text("00");
                $('#expireLastS').text("00");
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