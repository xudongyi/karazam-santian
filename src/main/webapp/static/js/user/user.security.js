function banlanceQuery() {
    var index = layer.load(2,{time: 10*1000}); //换了种风格
    $.getJSON(ctx + '/pay/bal_query', {}, function (res) {
        //使用方式跟独立组件完全一样
        var tpl = document.getElementById("balanceTpl");
        laytpl(tpl.innerHTML).render(res, function (string) {
            if (res.success) {
                layer.close(index);
                layer.open({
                    type: 1,
                    title: '托管账户[' + payAccountNo + ']',
                    area: ['600px', '150px'],
                    closeBtn: 1,
                    shadeClose: true,
                    content: string
                });
            } else {
                layer.msg("托管账户余额查询失败");
            }
        });
    });

}

$(document).ready(function () {

    $('.investTermination').click(function () {
        $('#affirmInvestTerminationModal').modal({
            backdrop : 'static'
        });
    });
    $('#investTerminationBtn').click(function () {
        $.ajax({
            type: "POST",
            url: ctx + "/pay/termination",
            data: {agreementNo: investAgreementNo},
            success: function(msg){
                msg = JSON.parse(msg);
                if(msg.code == '000'){
                    layer.msg("解约成功");
                    window.location.reload();
                    return ;
                }
                layer.msg("解约失败");
            }
        });
    });

    $('.chargeTermination').click(function () {
        $('#affirmChargeTerminationModal').modal({
            backdrop : 'static'
        });
    });
    $('#chargeTerminationBtn').click(function () {
        $.ajax({
            type: "POST",
            url: ctx + "/pay/termination",
            data: {agreementNo: chargeAgreementNo},
            success: function(msg){
                msg = JSON.parse(msg);
                if(msg.code == '000'){
                    layer.msg("解约成功");
                    window.location.reload();
                    return ;
                }
                layer.msg("解约失败");
            }
        });
    });

    $('#authReal, #authRealToOpenAcc').click(function () {
        $('#hasAuthModal').modal({
            backdrop : 'static'
        });
        $('#hasAuthModal').find('.modal-footer').show();
        $('#hasAuthModal').find('input').prop("disabled", false);
    });
    $('#hasAuthReal').click(function () {
        $('#hasAuthModal').modal({
            backdrop : 'static'
        });
        $('#hasAuthModal').find('.modal-footer').hide();
        $('#hasAuthModal').find('input').prop("disabled", true);
    });
    $('#modifyPassword').click(function () {
        $('#modifyPasswordModal').modal({
            backdrop : 'static'
        });
    });
    $('#autoInvestmentSign').click(function () {
        $('#autoInvestmentSignForm').find('input').prop('disabled', false);
        $('#autoInvestmentSignForm').find('.autoSign').eq(0).show();
        $('#autoInvestmentSignForm').find('.autoSign').eq(1).hide();
        $('#autoInvestmentSignModal').modal({
            backdrop : 'static'
        });
    });

    $('#autoInvestmentSignView').click(function () {
        $('#autoInvestmentSignForm').find('input').prop('disabled', true);
        $('#autoInvestmentSignForm').find('.autoSign').eq(0).hide();
        $('#autoInvestmentSignForm').find('.autoSign').eq(1).show();
        $('#autoInvestmentSignModal').modal({
            backdrop : 'static'
        });
    });

    $('#autoRepaymentSign').click(function () {
        $('#autoRepaymentSignForm').find('input').prop('disabled', false);
        $('#autoRepaymentSignForm').find('.autoSign').eq(0).show();
        $('#autoRepaymentSignForm').find('.autoSign').eq(1).hide();
        $('#autoRepaymentSignModal').modal({
            backdrop : 'static'
        });
    });

    $('#autoRepaymentSignView').click(function () {
        $('#autoRepaymentSignForm').find('input').prop('disabled', true);
        $('#autoRepaymentSignForm').find('.autoSign').eq(0).hide();
        $('#autoRepaymentSignForm').find('.autoSign').eq(1).show();
        $('#autoRepaymentSignModal').modal({
            backdrop : 'static'
        });
    });

    var $autoInvestmentSignForm = $("#autoInvestmentSignForm"),
        $autoInvestmentSignFormSubmit = $autoInvestmentSignForm.find(":submit");
    var $autoRepaymentSignForm = $("#autoRepaymentSignForm"),
        $autoRepaymentSignFormSubmit = $autoRepaymentSignForm.find(":submit");
    $autoInvestmentSignForm.validate({
        rules: {
            projectMinCyc: {
                required: true,
                 min: 0
            },
            projectMaxCyc: {
                required: true,
                min: 0
            },
            investMinAmount: {
                required: true,
                min: 0
            },
            investMaxAmount: {
                required: true,
                min: 0
            },
            interestRateMinRate: {
                required: true,
                min: 0
            },
            interestRateMaxRate: {
                required: true,
                min: 0
            },
            investmentAomunt: {
                required: true,
                min: 0
            },
            validity: {
                required: true,
                min: 0
            }
        },
        messages: {
            projectMinCyc: {
                required: "输入标的周期最小值",
                 min: "标的周期最小值至少为0"
            },
            projectMaxCyc: {
                required: "输入标的周期最大值",
                min: "标的周期最小值至少为0"
            },
            investMinAmount: {
                required: "输入投标限额最小值",
                min: "投标限额最小值至少为0"
            },
            investMaxAmount: {
                required: "输入投标限额最大值",
                min: "投标限额最小值至少为0"
            },
            interestRateMinRate: {
                required: "输入利率最小值",
                min: "利率最小值最小值至少为0"
            },
            interestRateMaxRate: {
                required: "输入利率最大值",
                min: "利率最大值最小值至少为0"
            },
            investmentAomunt: {
                required: "输入自动投票金额",
                min: "自动投票金额最小值至少为100"
            },
            validity: {
                required: "输入有效期",
                min: "有效期最小值至少为0"
            }
        },
        errorPlacement: function (error, element) {
            // element.tooltip('show', error.text());
            layer.tips(error.text(), element, {
                tipsMore: true
            });
        },
        unhighlight: function (element) {
            // element.tooltip('show', error.text());
        },
        submitHandler: function (form) {
            $autoInvestmentSignFormSubmit.prop("disabled", true);
            form.submit();
        }
    });

    $autoRepaymentSignForm.validate({
        rules: {
            validity: {
                required: true,
                min: 0
            }
        },
        messages: {
            validity: {
                required: "输入有效期",
                min: "有效期最小值至少为0"
            }
        },
        errorPlacement: function (error, element) {
            // element.tooltip('show', error.text());
            layer.tips(error.text(), element, {
                tipsMore: true
            });
        },
        unhighlight: function (element) {
            // element.tooltip('show', error.text());
        },
        submitHandler: function (form) {
            $autoRepaymentSignFormSubmit.prop("disabled", true);
            form.submit();
        }
    });

});