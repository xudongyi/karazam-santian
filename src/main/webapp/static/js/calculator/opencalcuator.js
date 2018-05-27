function opencalculator(obj) {
    var repaymentMethod =$(obj).attr("project-repaymentMethod");
    var interestRate=$(obj).attr("project-interestRate");
    var projectPeriod=$(obj).attr("project-period");
    var projectPeriodUnitDisplayName=$(obj).attr("project-periodUnit-displayName");//年月日
    var projectPeriodUnit=$(obj).attr("project-periodUnit");
    var projectRepaymentMethodDisplayName=$(obj).attr("project-repaymentMethod-displayName");
    layer.open({
        content: $('#calculatorBox'),
        title: '收益计算器',
        area: ['550px', '500px'],
        type: "1",
        anim: 5,
        maxmin:false,
    });
    $('#investmentrate').val(interestRate);
    $('#preiod').val(projectPeriod);
    $('#periodUnit').text(projectPeriodUnitDisplayName);
    $('#periodUnit').val(projectPeriodUnit);
    $('#interestMethod').val(repaymentMethod);
    $('#interestMethod').text(projectRepaymentMethodDisplayName);
}

$().ready(function() {
    var $statisticsPanel = $("#statisticsPanel"), $capitalInterests = $statisticsPanel.find(".capitalInterests"), $periods = $statisticsPanel.find(".periods"), $periodsUnit = $statisticsPanel.find(".periodsUnit"), $monthlyRate = $statisticsPanel.find(".monthlyRate"), $interests = $statisticsPanel.find(".interests"), $fees = $statisticsPanel.find(".fees"), $incomes = $statisticsPanel.find(".incomes");
    var $planPanel = $("#planPanel"), $planTable = $planPanel.find(".planTable");
    var monthlyRate = 0, feeRate = 0;
    var $calculatorForm = $("#calculatorForm"), $amount = $calculatorForm.find("[name='amount']"), $period = $calculatorForm.find("[name='period']"), $periodUnit = $calculatorForm.find("[name='periodUnit']"), $rate = $calculatorForm.find("[name='rate']"), $interestMethod = $calculatorForm.find("[name='interestMethod']"), $feeRate = $calculatorForm.find("[name='feeRate']");
    var $submit = $calculatorForm.find(":submit");
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
                required :true,
                positive: true,
                decimal: {
                    integer: 12,
                    fraction: 2
                }
            },
            interestMethod: "required",
            feeRate: {
                required :true,
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
        errorPlacement: function(error, element) {
            element.closest("tr").find(".color-red").text(error.text());
        },
        unhighlight: function(element) {
            $(element).closest("tr").find(".color-red").text("");
        },
        submitHandler: function(form) {
          //  $submit.prop("disabled", true);
             monthlyRate = floatDiv(floatDiv($rate.val(), 12), 100);
            switch($periodUnit.val()) {
                case "MONTH": {
                    if($interestMethod.val() == "EACH_PERIOD_AVG_CAPITAL_PLUS_INTEREST") {
                        $each_period_avg_capital_plus_interest($amount.val(), $period.val(), monthlyRate, feeRate);
                    }
                    else if($interestMethod.val() == "EACH_PERIOD_INTEREST_AND_LAST_PERIOD_PLUS_CAPITAL") {
                        $each_period_interest_and_last_period_plus_capital($amount.val(), $period.val(), monthlyRate, feeRate);
                    }
                    else if($interestMethod.val() == "CURRENT_AND_EACH_PERIOD_INTEREST_AND_LAST_PERIOD_CAPITAL") {
                        $current_and_each_period_interest_and_last_period_capital($amount.val(), $period.val(), monthlyRate, feeRate);
                    }
                    else if($interestMethod.val() == "LAST_PERIOD_CAPITAL_PLUS_INTEREST") {
                        $last_period_capital_plus_interest($amount.val(), $period.val(), monthlyRate, feeRate);
                    }
                    break;
                }
                case "DAY": {
                    if($interestMethod.val() == "LAST_PERIOD_CAPITAL_PLUS_INTEREST") {
                        $last_period_capital_plus_interest($amount.val(), $period.val(), floatDiv(floatDiv($rate.val(), 365), 100), feeRate);
                    }
                    break;
                }
                default: {
                    break;
                }
            }

            // $periods.text($period.val());
            // $monthlyRate.text(currency(floatMul(monthlyRate, 100), false, false));
        }

    });

    //
    // var $interestMethodAsDay = $("#interestMethodAsDay");
    // var $interestMethodAsMonth = $("#interestMethodAsMonth");
    // $periodUnit.change(function() {
    //     var $this = $(this);
    //     switch($this.val()) {
    //         case "month": {
    //             $interestMethod.html($interestMethodAsMonth.html());
    //             break;
    //         }
    //         case "day": {
    //             $interestMethod.html($interestMethodAsDay.html());
    //             break;
    //         }
    //         default: {
    //             break;
    //         }
    //     }
    // });

    $each_period_avg_capital_plus_interest = function(amount, period, rate, feeRate) {
        var monthlyRateMultiples = 1 + rate;
        var periodCapitalInterest = floatDiv(floatMul(floatMul(amount, rate), Math.pow(monthlyRateMultiples, period)), floatSub(Math.pow(monthlyRateMultiples, period), 1));
        var remainingPrincipal = amount;
        var remainingInterest = floatSub(floatMul(periodCapitalInterest, period), amount);
        $capitalInterests.text(currency(floatAdd(remainingPrincipal, remainingInterest), true, true));
        $interests.text(currency(remainingInterest, true, true));
    };

    $each_period_interest_and_last_period_plus_capital = function(amount, period, rate, feeRate) {
        var remainingPrincipal = amount;
        var remainingInterest = floatMul(floatMul(amount, rate), period);
        $capitalInterests.text(currency(floatAdd(remainingPrincipal, remainingInterest), true, true));//回收总金额本金+利息
        $interests.text(currency(remainingInterest, true, true));
    };

    $current_and_each_period_interest_and_last_period_capital = function(amount, period, rate, feeRate) {
        var remainingPrincipal = amount;
        var remainingInterest = floatMul(floatMul(amount, rate), period);
        $capitalInterests.text(currency(floatAdd(remainingPrincipal, remainingInterest), true, true));
        $interests.text(currency(remainingInterest, true, true));
    };

    $last_period_capital_plus_interest = function(amount, period, rate, feeRate) {
        var remainingPrincipal = amount;
        var remainingInterest = floatMul(floatMul(amount, rate), period);
        var remainingFee = floatMul(remainingInterest, feeRate);

        $capitalInterests.text(currency(floatAdd(remainingPrincipal, remainingInterest), true, true));
        $interests.text(currency(remainingInterest, true, true));
    };

});
