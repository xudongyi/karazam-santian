/*
 * Copyright 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 *
 * JavaScript - Project Invest
 * Version: 3.0
 */
$().ready(function() {
	
	[#-- 网银投资提交 --]
	var $investmentConfirmForm = $("#investmentConfirmForm"), $amount = $investmentConfirmForm.find("[name='amount']"),
    $coupon = $investmentConfirmForm.find("[name='coupon']"), $password = $investmentConfirmForm.find("[name='payPassword']"),
    $investmentAmount = $("#investmentAmount"), $preferentialAmount = $("#preferentialAmount"), $availableAmount = $("#availableAmount"),
    $investmentConfirmFromSubmit = $investmentConfirmForm.find(":submit"), rsaKey = new RSAKey();
	$investmentConfirmFromSubmit.prop("disabled", false);
	rsaKey.setPublic(b64tohex(modulus), b64tohex(exponent));
	
	$investmentConfirmForm.validate({
		rules: {
			payPassword: {
				required: true,
				pattern: /^[^\s&\"<>]+$/
				[#--	,
				minlength: ${setting.security.passwordMinLength},
				maxlength: ${setting.security.passwordMaxLength}--]
			},
			bankCode:"required",
            captcha: {
            	required: true,
				remote: {
					url: "${ctx}/investment/verify_captcha",
					type: "get",
					cache: false
				}
			},
            agreement: "required"
		},
		messages: {
			payPassword: {
				required: "请输入支付密码",
				pattern: "密码包含非法字符",
				minlength: "请输入${setting.security.passwordMinLength}-${setting.security.passwordMaxLength}位密码",
				maxlength: "请输入${setting.security.passwordMinLength}-${setting.security.passwordMaxLength}位密码",
				remote: "支付密码错误"
			},
			bankCode:"请选择银行",
            captcha: {
            	required: "请输入验证码",
            	remote: "验证码错误"
    		},
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
			$password.val(hex2b64(rsaKey.encrypt($password.val())));
			form.submit();
		}
	});

	[#-- 投资弹窗 --]
	var $investmentFrom = $("#investmentFrom"), $investmentFromSubmit = $investmentFrom.find(":submit");
	$investmentFromSubmit.prop("disabled", false);
	var $investmentFromValidate = $investmentFrom.validate({
		rules: {
			amount: {
				required: true,
				positiveInteger: true,
				min: multiples,
				progressiveIncrease: multiple,
				max: maxAmout
			}
		},
		messages: {
			amount: {
				required: "请输入投资金额",
				positiveInteger: "请输入正确的投资金额",
				min: "投资金额最小为" + multiples + "元",
				max: "超过剩余可投金额，请重新输入"
			}
		},
		errorPlacement: function(error, element) {
			element.next().text(error.text());
		},
		unhighlight: function(element) {
			$(element).next().text("");
		},
		submitHandler: function(form) {
			[#--
			$investmentFromSubmit.prop("disabled", true);
			--]
			
			[#-- 投资金额 --][#--
			$amount.val($investmentFrom.find("[name='amount']").val());
			$investmentAmount.text($amount.val());
			$password.val("");
			
			--][#-- 优惠金额 --][#--
			var coupon = $investmentFrom.find("[name='coupon']").val();
			var preferentialAmount = 0;
			if(coupon) {
				$coupon.val(coupon);
				preferentialAmount = $investmentFrom.find("[name='coupon'] option:selected").attr("amount");
				if(floatSub($amount.val(), preferentialAmount) < 0) {
					preferentialAmount = $amount.val();
				}
			}
			$preferentialAmount.text(preferentialAmount);

			--][#-- 有效金额 --][#--
			$availableAmount.text(floatSub($amount.val(), preferentialAmount));
			
			--][#-- 确认投资 --][#--
            layer.open({
                content: $('#pop-up-layer'),
                title: '确认投资',
                type: "1"
            });--]
		}
	});
	
	jQuery.validator.addMethod("progressiveIncrease", function(value, element, param) {
		if(this.optional(element)) {
			return true;
		}
		return  ((value - param) % multiple) == 0;
		}, "加价幅度必须为"+ multiple +"的倍数");
		
		jQuery.validator.addMethod("maxInvestmentAmount", function(value, element, param) {
			if(this.optional(element)) {
				return true;
			}
			return  value <= param;
		}, "超过最大投资金额");
	
	[#-- 优惠券 --]
	var $coupons = $investmentFrom.find("[name='coupon']");

    [#-- 更换验证码 --]
    var $captchaImage = $("#captchaImage");
    $captchaImage.click(function() {
        $captchaImage.prop("src", "${ctx}/captcha?type=INVESTMENT");
    });

    [#-- 余额投资提交 --]
    var $balanceInvestmentConfirmForm = $("#balanceInvestmentConfirmForm"), $balanceAmount = $balanceInvestmentConfirmForm.find("[name='amount']"),
        $balanceCoupon = $balanceInvestmentConfirmForm.find("[name='coupon']"), $balanceInvestmentAmount = $("#balanceInvestmentAmount"),
        $balanceInvestmentConfirmFromSubmit = $balanceInvestmentConfirmForm.find(":submit"), rsaKey = new RSAKey(),

    $balancePreferentialAmount = $("#balancePreferentialAmount"),$balanceAvailableAmount = $("#balanceAvailableAmount");
    $balanceInvestmentConfirmFromSubmit.prop("disabled", false);
    rsaKey.setPublic(b64tohex(modulus), b64tohex(exponent));

    $balanceInvestmentConfirmForm.validate({
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
            $balanceInvestmentConfirmFromSubmit.prop("disabled", true);
            form.submit();
        }
    });

	[#-- 余额投资弹窗 --]
	var $balancePay = $investmentFrom.find(".balancePay");
	$balancePay.click(function(){
		if (!$investmentFromValidate.element($investmentFrom.find("[name='amount']"))) {
			return false;
		}

		[#-- 投资金额 --]
		$balanceAmount.val($investmentFrom.find("[name='amount']").val());
		$balanceInvestmentAmount.text($balanceAmount.val());

		[#-- 优惠金额 --]
		var balanceCoupon = $investmentFrom.find("[name='coupon']").val();
		var balancePreferentialAmount = 0;
		if(balanceCoupon) {
			$balanceCoupon.val(balanceCoupon);
            balancePreferentialAmount = $investmentFrom.find("[name='coupon'] option:selected").attr("amount");
			if(floatSub($balanceAmount.val(), balancePreferentialAmount) < 0) {
                balancePreferentialAmount = $balanceAmount.val();
			}
		}
		$balancePreferentialAmount.text(balancePreferentialAmount);

		[#-- 有效金额 --]
		$balanceAvailableAmount.text(floatSub($balanceAmount.val(), balancePreferentialAmount));

		[#-- 确认投资 --]
		[#--layui.use(['layer', 'laytpl', 'element'], function () {--]
			[#--var layer = layui.layer;--]
			[#--layer.open({--]
				[#--content: $('#balance_div'),--]
				[#--title: '确认投资',--]
				[#--area: '360px',--]
				[#--type: "1"--]
			[#--});--]
		[#--})--]
		$('#balance_div').modal({
			backdrop:'static'
		});
	});
	
});