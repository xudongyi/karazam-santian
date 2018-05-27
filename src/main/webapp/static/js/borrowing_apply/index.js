$(function () {
	/**
	 * 项目相关
	 */
	var preBorrowProject = _preBorrowProject_();
	// 设置当前项目
	preBorrowProject.setActiveObject();
	// 绑定hashchange事件
	if (window.addEventListener) {
		window.addEventListener('hashchange', function () {
			preBorrowProject.setActiveObject();
		}, false)
	} else {
		window.attachEvent('onhashchange', function () {
			preBorrowProject.setActiveObject();
		}, false)
	}
	// checkbox
	$("#preBorrow-project-content-form .checkboxGroup .checkbox").click(function () {
		$("#preBorrow-project-content-form .checkboxGroup .checkbox").removeClass("checked");
		$(this).addClass("checked");
		$("#carSex").val($(this).index());
		$(this).parents("li").addClass("actived");
	})

	/**
	 * form-layer相关
	 */
	// checkbox
	$("#preBorrow-apply-layer-form .checkboxGroup .checkbox").click(function () {
		$("#preBorrow-apply-layer-form .checkboxGroup .checkbox").removeClass("checked");
		$(this).addClass("checked");
		$("#formSex").val($(this).index());
		$(this).parents("li").addClass("actived");
	})
	// 关闭
	$("#preBorrow-apply-layer-form .iconfont.layerClose").click(function () {
		$("#preBorrow-apply-layer").fadeOut();
	})
	// 打开
	$(".preBorrow-banner-entry button").click(function () {
		$("#preBorrow-apply-layer").fadeIn();
		HTTPGO.imgCode($("#layerFormImgCode"));
	})

	/**
	 * form-layer项目选项
	 */
	// 插入
	for (var i in projectTypes) {
		if (i != "秒速贷") {
			var li = '<li data-code="' + projectTypes[i].code + '">' + projectTypes[i].txt + '</li>';
			$("#preBorrow-apply-layer-form-cont-option-items ul").append(li);
		}
	}
	// 展开form-layer项目选项
	$("#preBorrow-apply-layer-form .preBorrow-apply-type").click(function () {
		formProjectList.show();
	})
	// 收回form-layer项目选项
	$("#preBorrow-apply-layer-form-cont-option-head").click(function () {
		formProjectList.hide();
	})
	// 点击其他部分收回form-layer项目选项
	$("#preBorrow-apply-layer").on("click", function (e) {
		if (e.target != $(".preBorrow-apply-type")[0] && $(e.target).parent(".preBorrow-apply-type")[0] != $(".preBorrow-apply-type")[0]) {
			formProjectList.hide();
		}
	})
	// 选中
	$("#preBorrow-apply-layer-form-cont-option-items").on("click", "li", function () {
		formProjectList.value = "" + $(this).data("code");
		$("#preBorrow-apply-layer-form-cont-option-head span").text($(this).text());
		$("#preBorrow-apply-layer-form .preBorrow-apply-type span").text($(this).text());
		formProjectList.hide();
	})

	/**
	 * 输入框交互
	 */
	$(".carWrite").on("focus", "input", function () {
		$(this).parent("li").addClass("focus");
	})
	$(".carWrite").on("blur", "input", function () {
		$(this).parent("li").removeClass("focus");
		if ($(this).val()) {
			$(this).parent("li").addClass("actived");
		} else {
			$(this).parent("li").removeClass("actived");
		}
	})

	/**
	 * http
	 */
	HTTPGO.imgCode($("#formImgCode"));

	$("#formImgCode").click(function () {
		HTTPGO.imgCode($("#formImgCode"));
	})
	$("#layerFormImgCode").click(function () {
		HTTPGO.imgCode($("#layerFormImgCode"));
	})
});

// 产品相关
var _preBorrowProject_ = function () {
	// 获取url的hash的json
	var getHashJson = function () {
		var obj = {};
		// 得到hash
		var hash = window.location.hash;
		// （获取hash中#之后的字符串） && （分割&成数组）
		var hashItems = hash.substr(1).split("&");

		// 分割=成数组
		for (var i = 0, len = hashItems.length; i < len; i++) {
			var item = hashItems[i];
			var keyValue = item.split("=");

			if (keyValue.length == 2) {
				var key = keyValue[0];
				var val = keyValue[1];
				obj[key] = val;
			}
		}

		return obj;
	}

	// 设置当前项目
	var setActiveObject = function () {
		// 获取薪项目索引
		var objectIndex = getHashJson().project;
		// 如果索引不存在则默认为0
		objectIndex = objectIndex || 0;
		// 获取之前项目索引
		var oldIndex = $("#preBorrow-project-nav-cont li.active").index();
		// 设置项目头图
		$("#preBorrow-project-head").fadeOut(0, function () {
			$(this).removeClass("detail-banner" + oldIndex).addClass("detail-banner" + objectIndex).fadeIn(0);
		})
		// 设置项目详情图
		$("#preBorrow-project-content-info").fadeOut(0, function () {
			$(this).removeClass("detail-info" + oldIndex).addClass("detail-info" + objectIndex).fadeIn(0);
		})
		// 设置导航位置
		$("#preBorrow-project-nav-cont li").removeClass("active").eq(objectIndex).addClass("active");

		// 设置form内类型值
		var typeCode = getHashJson().type;
		typeCode = typeCode || 0;
		$("#preBorrow-project-content-form input[name='project']").val(typeCode);
	}

	return {
		setActiveObject: setActiveObject
	}
}

// form选择项目列表
var formProjectList = {
	value: "",
	show: function () {
		$("#preBorrow-apply-layer-form-cont-option").fadeIn(200);
		$("#preBorrow-apply-layer-form-cont-option-items").slideDown(200);
	},
	hide: function () {
		var _this = this;
		if (this.value != "选择借款类型" && this.value != "") {
			$(".preBorrow-apply-type").addClass("actived");
			$(".preBorrow-apply-type input").val(_this.value);
		}
		$("#preBorrow-apply-layer-form-cont-option").fadeOut(200);
		$("#preBorrow-apply-layer-form-cont-option-items").slideUp(200);
	}
}

var formType = null;

//表单提交
$("#preBorrow-project-content-form").on("click", ".confirm", function () {
	var data = checkForm($(this).parents("#preBorrow-project-content-form"));
	// 如果有数据，则提交
	console.log(data);
	if (data) {
		$("#preBorrow-project-content-form .errorTip").hide();
		console.info(data);
		HTTPGO.formSubmit(data);
		formType = 1;
	}
})

// 检测表单
var checkForm = function ($form) {
	//报错提示文案方法
	var remaindErron = function (text) {
		$form.find(".errorTip span").html(text);
		$form.find(".errorTip").show();
	}

	// 提交数据
	var data = {};
	// 是否可以提交
	var canSubmit = true;

	$form.find("input").each(function () {
		var _this = this;

		// input值
		var InputValue = function () {
			return $.trim($(_this).val());
		}

		// input名字
		var name = $(this).prop("name");

		switch (name) {
			case "amount":
				var amountReg = /^[1-9]*[1-9][0-9]*$/;
				if (!InputValue()||!amountReg.test(InputValue())) {
					remaindErron("请输入正确的金额！");
					canSubmit = false;
				}
				break;
			case "name":
				if (!InputValue()) {
					remaindErron("请输入您的姓名！");
					canSubmit = false;
				}
				break;
			case "deadline":
				var deadlineReg = /^[1-9]*[1-9][0-9]*$/;
				if (!InputValue()||	!deadlineReg.test(InputValue())) {
					remaindErron("请输入正确的期望贷款期限");
					canSubmit = false;
				}
				break;
			case "phone":
				var telReg = /^(13|15|18|14|17)[0-9]{9}$/;
				if (!InputValue() || !telReg.test(InputValue())) {
					remaindErron("请输入正确的手机号！");
					canSubmit = false;
				}
				break;
			case "rate":
				var rateReg = /^[1-9]*[1-9][0-9]*$/;
				if (!InputValue()||	!rateReg.test(InputValue())) {
					remaindErron("请输入正确的期望贷款利率");
					canSubmit = false;
					break;
				}
				if(InputValue()>100||InputValue()<=0){
					remaindErron("请输入1~100之间的数字");
					canSubmit = false;
				}
				break;
			case "sex":
				if (!InputValue()) {
					remaindErron("请输入您的性别！");
					canSubmit = false;
				}
				break;
			case "code":
				if (!InputValue()) {
					remaindErron("请输入验证码！");
					canSubmit = false;
				}
				break;
			case "project":
				if (!InputValue()) {
					remaindErron("请选择申请项目！");
					canSubmit = false;
				}
				break;
			case  "messageCode":
                if (!InputValue()) {
                    remaindErron("请输入短信验证码！");
                    canSubmit = false;
                }
                break;
		}

		// 中断循环，不覆盖提示
		if (!canSubmit) {
			return false;
		}

		data[name] = InputValue();
	})

	// 如果数据不完整，则直接返回false
	if (!canSubmit) {
		return false;
	}

	return data;
}

// http请求
var HTTPGO = (function () {
	var root = window.location.search.indexOf('env=dev') > -1 ? "//www.huashanjinrong.com/" :
		(window.location.search.indexOf('env=test') > -1 ? "//www.huashanjinrong.com/" : "//www.huashanjinrong.com/");

	var path = {
		submit: ctx + "borrowingApply/apply",
		imgCode: ctx + "captcha?type=BORROWING_APPLY",
		borrowData: root + "borrow/data"
	}

	// 表单提交
	var formSubmit = function (data) {
		var gender;
		if(data.sex==0){
			gender = 'MALE';
		}else if (data.sex==1){
			gender = 'FEMALE';
		}else {
			gender = 'UNKNOWN';
		}
		$.ajax({
			url: path.submit,
			type: "post",
			data: {
				userName: data.name,
				mobile: data.phone,
				type: data.project,
				imgCode: data.code,
				genderTypeEnum:gender,
				amount:data.amount,
				deadline:data.deadline,
				rate:data.rate,
				userId:data.userId,
                captcha:data.messageCode
			},
			dataType:"json",
			xhrFields: {
				withCredentials: true
			},
			crossDomain: true,
			success: function (result) {
				if (result.success) {
					layer.msg(result.message,{time:5000});
				}else{
					layer.msg(result.message,{time:5000});
				}
				HTTPGO.imgCode($("#formImgCode"));
			}
		})
	};

	var imgCode = function (imgDom) {
		imgDom.attr("src", path.imgCode );
	}

	return {
		formSubmit: formSubmit,
		imgCode: imgCode
	}
})()

var projectTypes = {};
projectTypes["车抵贷"] = { txt: "车辆抵押贷款", code: "0", id: "10000" };
projectTypes["秒速贷"] = { txt: "车辆抵押贷款", code: "1", id: "10001" };
// projectTypes["拍易融"] = { txt: "房屋抵押贷款", code: "3", id: "30001" };
// projectTypes["房拖贷"] = { txt: "企事业公务员信用贷款", code: "1", id: "20001" };
projectTypes["出行派"] = { txt: "二手车分期贷款", code: "2", id: "10002" };
projectTypes["车分期"] = { txt: "新车分期贷款", code: "3", id: "10003" };

var ck = 0;
var ck1 = 0;

function sendMsg() {

    var userMobile=$("#deadline").attr('mobile');
    if(typeof(userMobile)=="undefined"){
        userMobile =$("#carTel").val();
	}
    $.ajax({
        type: "post",
        dataType: "json",
        url: ctx + "/borrowingApply/sendMessage",
        async: false,
        data: {
            'mobile': userMobile,
        },
        success: function (datas) {
            if (datas.status == "success") {
                alert(datas.message);
                show_time(60);//成功将按钮修改
            } else {
                $("#sendMessageCode").prop('disabled', false).removeClass("ui-disabled");
                alert(datas.message);
                ck = 0;
            }
        }
    });
}

//发送倒计时
function show_time(n) {
    n = n - 1;
    var timer = document.getElementById("sendMessageCode");
    var str_time = n + "秒后重发";
    timer.innerHTML = str_time;
    $("#sendMessageCode").text(str_time);
    $("#sendMessageCode").prop('disabled', false)
    if (n == 0) {
        ck = 0;
        ck1 = 0;
        timer.innerHTML = "发送验证码";
        $("#sendMessageCode").text("发送验证码");
        $("#sendMessageCode").prop('disabled', false).removeClass("ui-disabled");
        $("#sendMessageCode").removeClass("disabled");
        return;
    }
    setTimeout("show_time(" + n + ")", 1000);
}