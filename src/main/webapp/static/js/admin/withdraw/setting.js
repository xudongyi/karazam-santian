/**
 * Created by suhao on 2017/2/8.
 */
$(function () {
    var $feeRate = $("#feeRate");
    var $minAmount = $("#minAmount");
    var $maxAmount = $("#maxAmount");

    $feeRate.textbox('textbox').bind('input propertychange', function() {
        $("#feeRateShow").textbox('setValue', $(this).val());
    });

    $minAmount.textbox('textbox').bind('input propertychange', function() {
        $("#minAmountShow").textbox('setValue', $(this).val());
    });

    $maxAmount.textbox('textbox').bind('input propertychange', function() {
        $("#maxAmountShow").textbox('setValue', $(this).val());
    });
});