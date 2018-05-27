$(function(){
	$(".bank span.morebtn").click(function(){
		$(this).hide();
		$(this).siblings("span.more").show();
	});
	$(".bank span").not(".morebtn").click(function(){
		var father=$(this).parent();
		$(this).addClass("cur").siblings().removeClass("cur");
		$(this).siblings().hide();
		father.children(".bank .select-other").show();
		father.children(".bank .rules").show();
		father.children(".bank .banktel").show();
		var timelimit=$(".bank span.cur img").attr("pertxnmaxamt");
		$("#timelimit").text(timelimit);
		var daylimit=$(".bank span.cur img").attr("perdaymaxamt");
		$("#daylimit").text(daylimit);
		$("#bankCode").val($(".bank span.cur img").attr("bankcode"));
		$("#bankName").val($(".bank span.cur img").attr("bankname"));
	});
	$(".select-other").click(function(){
		var father=$(this).parent();
		$(this).addClass("cur").siblings().removeClass("cur");
		$(this).siblings().show();
		$(this).hide();
	});
});
