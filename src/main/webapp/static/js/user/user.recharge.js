$(function() {

    var $rechargeForm = $("#netPayFrom"), $submit = $rechargeForm.find(":submit"),$amount = $("#amount");
    var $rechargeType = "";
    $submit.prop("disabled", false);
    $amount.val("");
    $rechargeForm.validate({
        rules: {
            amount: {
                required: true,
                positive: true,
                decimal: {
                    integer: 12,
                    fraction: 2
                },
                min: 0.01
            }
        },
        messages: {
            amount: {
                required: "请输入充值金额",
                positive: "请输入正确的充值金额",
                decimal: "充值金额最小单位为分",
                min: "充值金额最小单位为分"
            }
        },
        errorPlacement: function(error, element) {
            $(element).closest(".bank").find(".annotate").text(error.text());
        },
        unhighlight: function(element) {
            $(element).closest(".bank").find(".annotate").text("");
        },
        submitHandler: function(form) {
            // if ($rechargeType == '') {
            //     new $.zui.Messager('银行选择出问题啦。', {
            //         icon: 'heart',
            //         placement: 'center'
            //     }).show();
            //     return;
            // }
            $.ajax({
                url: ctx + "/pay/recharge",
                type:"post",
                async: false,
                data:{
                    amount:$("#amount").val()
                },
                dataType:"json",
                success:function(data){
                    console.log(data);
                    if(data.success){
                        rechargeFormSubmit(data.data.requestUrl, data.data.parameterMap);
                        $('#rechargeTipBox').modal({
                            backdrop:'static'
                        });
                    }else {
                        var tips = $("#tips-message");
                        tips.html('充值金额大于<font style="color: red">10,000</font>元时需先绑定银行卡和上传身份证信息，您尚未完成相关操作，请前往托管账户上传。' +
                            '<p style="color: #34cbee">当前银行卡状态: <font style="color: red">'+ data.data.bankCardStatus +'</font></p>'+
                                '<p style="color: #34cbee">身份证状态: <font style="color: red">'+ data.data.idCardStatus +'</font></p>'
                        );
                        $('#hasIdCard').modal({backdrop: 'static'});
                    }
                }
            });
            // $('#rechargeType').val($rechargeType);
            // $submit.prop("disabled", true);
            // $('#rechargeTipBox').modal({
            //     backdrop:'static'
            // });
        }
    });

    // $(".bank span").click(function(){
    //     $rechargeType = $(this).attr('rechargeType');
    //     $("#bankCode").val($(this).attr('bankcode'));
    //     $("#bankId").val($(this).attr('bankId'));
    // });

    // $("#save-sure").click(function () {
    //     $rechargeForm.submit();
    // });

    // $(".chooseType").click(function () {
    //     $(this).find('.rechargeBusType').addClass('cur');
    //     $(this).siblings('a').find('.rechargeBusType').removeClass('cur');
    //     $('#rechargeBusType').val($(this).attr('rechargeBusType'));
    // });

    function rechargeFormSubmit(url, data) {
        var form = document.createElement("form");
        document.body.appendChild(form);
        form.method = 'post';
        form.action = url;
        form.target = '_blank';
        //创建隐藏表单
        $.each(data, function (key, value) {
            var element = document.createElement("input");
            element.setAttribute("name", key);
            element.setAttribute("value", value);
            element.setAttribute("type","hidden");
            form.appendChild(element);
        });

        form.submit();
    }
});