$().ready(function () {
    var $calculatorForm = $("#calculatorForm"),
        $amount = $calculatorForm.find("[name='amount']"),
        $period = $calculatorForm.find("[name='period']"),
        $dateUnit = $calculatorForm.find("[name='dateUnit']"),
        $rate = $calculatorForm.find("[name='rate']");
    var $submit = $calculatorForm.find(":submit");
    var repaymentMethod = 'EACH_PERIOD_INTEREST_AND_LAST_PERIOD_PLUS_CAPITAL';
    var dateUnit = 'MONTH';
    var defaultRepaymentMethod = {
        MONTH:'EACH_PERIOD_INTEREST_AND_LAST_PERIOD_PLUS_CAPITAL',
        DAY:'FIXED_PERIOD_INTEREST_AND_LAST_PERIOD_PLUS_CAPITAL'
    };

    $(document).on("change", ":radio[name=interestMethod]", function(){
        repaymentMethod = $(this).val();
    });

    $(document).on("change", "#dateUnit", function(){
        dateUnit = $(this).val();
        repaymentMethod = defaultRepaymentMethod[dateUnit];
        $('#repaymentMethodBox').html($('#repaymentMethod' + dateUnit).html());
    });

    $calculatorForm.find('button[type=reset]').on('click', function () {
        $('.payPlan').hide();
    });

    $submit.prop("disabled", false);
    $calculatorForm.validate({
        rules: {
            amount: {
                required: true,
                positive: true,
                decimal: {
                    integer: 12,
                    fraction: 2
                }
            },
            period: {
                required: true,
                positiveInteger: true
            },
            rate: {
                required: true,
                positive: true,
                decimal: {
                    integer: 12,
                    fraction: 2
                }
            },
            interestMethod: "required",
            feeRate: {
                required: true,
                nonnegativeNumber: true,
                decimal: {
                    integer: 12,
                    fraction: 2
                }
            }
        },
        messages: {
            amount: {
                required: "必填",
                positive: "必须为正数",
                decimal: "超出位数限制"
            },
            period: {
                required: "必填",
                positiveInteger: "必须为正整数"
            },
            rate: {
                required: "必填",
                positive: "必须为正数",
                decimal: "超出位数限制"
            },
            interestMethod: "必选",
            feeRate: {
                required: "必填",
                nonnegativeNumber: "必须为零或正数",
                decimal: "超出位数限制"
            }
        },
        errorPlacement: function (error, element) {
            element.siblings(".error").text(error.text()).show();
        },
        unhighlight: function (element) {
            $(element).siblings(".error").text('').hide();
        },
        submitHandler: function (form) {
            $submit.prop("disabled", true);
            $.getJSON(ctx + '/investment/calculator/result', {
                    amount : $amount.val(),
                    rate : $rate.val(),
                    repaymentMethod : repaymentMethod,
                    borrowingPeriod : $period.val(),
                    periodUnit : $dateUnit.val()
                },
                function (res) {
                    $submit.prop("disabled", false);
                    $('.result').find('.income em').text(res.sum_invests_capital);
                    $('.result').find('.interest em').text(res.sum_invests);
                    if (res.records.length > 0) {
                        $('.payPlan').show();
                    } else {
                        $('.payPlan').hide();
                    }
                    var tpl = document.getElementById("plansTpl");
                    laytpl(tpl.innerHTML).render(res, function (string) {
                        $('#planCont').html(string);
                    });
                }
            );

        }
    });
});
