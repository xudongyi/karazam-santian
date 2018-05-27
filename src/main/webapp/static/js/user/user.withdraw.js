$(function() {

    var $withdrawalForm = $("#withdrawalForm"),
        $submit = $withdrawalForm.find(":submit"),
        rsaKey = new RSAKey(),
        $amount = $("#amount");
    $submit.prop("disabled", false);
    rsaKey.setPublic(b64tohex(modules), b64tohex(exponent));
    $amount.val("");

    var $validate = $withdrawalForm.validate({
        rules: {
            amount: {
                required: true,
                positive: true,
                decimal: {
                    integer: 12,
                    fraction: 2
                },
                min: beginAmount,
                max: 100000000,
                remote: {
                    url: ctx + "/uc/withdraw/check_balance",
                    type: "post",
                    cache: false
                }
            }
        },
        messages: {
            amount: {
                required: "请输入提现金额",
                positive: "请输入正确的提现金额",
                decimal: "提现金额最小单位为分",
                min: "最小提现金额为"+beginAmount+"元",
                max: "最大提现金额为100000000元",
                remote: "可用余额不足"
            }
        },
        errorPlacement: function(error, element) {
            $(".annotate").text(error.text());
            $(".annotate").addClass("errorIcon");
            $(".successIcon").hide();
        },
        unhighlight: function(element) {
            $(".annotate").text("");
            $(".annotate").removeClass("errorIcon");
            $(".successIcon").show();
        },
        submitHandler: function(form) {
            $submit.prop("disabled", true);
            form.submit();
        }
    });

    $amount.bind("input propertychange change", function(event) {
        if (event.type != "propertychange" || event.originalEvent.propertyName == "value") {
            $calculateFee();
        }
    });

    var $fee = $("#fee");
    var $paymentAmout = $("#paymentAmout");
    var timeout;
    $calculateFee = function() {
        clearTimeout(timeout);
        timeout = setTimeout(function() {
            // if ($validate.element($amount)) {
            //     $.ajax({
            //         url: ctx + "/uc/withdraw/calculate_fee",
            //         type: "POST",
            //         data: {amount: $amount.val()},
            //         dataType: "json",
            //         cache: false,
            //         success: function(data) {
            //             $fee.text(data.fee);
            //             $paymentAmout.text(data.amount);
            //         }
            //     });
            // } else {
            //     $fee.text("0.00");
            //     $paymentAmout.text("0.00");
            // }
            $.ajax({
                url: ctx + "/uc/withdraw/calculate_fee",
                type: "POST",
                data: {amount: $amount.val()},
                dataType: "json",
                cache: false,
                success: function(data) {
                    $fee.text(data.fee);
                    $paymentAmout.text(data.amount);
                }
            });
        }, 500);
    };

});