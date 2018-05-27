/*
 * Copyright 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 *
 * JavaScript - Project List
 * Version: 3.0
 */
$().ready(function() {

	[#-- 搜索项表单 --]
	var $searchForm = $("#searchForm");
	var $type = $("#type");
	var $periodUnit = $("#periodUnit");
	var $scope = $("#scope");
	var $progre = $("#progre");
	var $period = $("#period");
	var $rate = $("#rate");
	var $sortBy = $("#sortBy");
	var $sortMethod = $("#sortMethod");
	
	var $previewing = $("#previewing");
	$(".search .previewing").click(function(e) {
		e.preventDefault();
		$previewing.val($(this).attr("val"));
		$progre.val("");
		$searchForm.submit();
	});
	
	$(".search .type").click(function(e) {
		e.preventDefault();
		$type.val($(this).attr("val"));
		$searchForm.submit();
	});
	
	$(".search .periodUnit").click(function(e) {
		e.preventDefault();
		$periodUnit.val($(this).attr("val"));
		$searchForm.submit();
	});
	
	$(".search .scope").click(function(e) {
		e.preventDefault();
		$scope.val($(this).attr("val"));
		$searchForm.submit();
	});
	
	$(".search .progre").click(function(e) {
		e.preventDefault();
		$progre.val($(this).attr("val"));
		$previewing.val("");
		$searchForm.submit();
	});
	
	$(".search .period").click(function(e) {
		e.preventDefault();
		$period.val($(this).attr("val"));
		$searchForm.submit();
	});
	
	$(".search .rate").click(function(e) {
		e.preventDefault();
		$rate.val($(this).attr("val"));
		$searchForm.submit();
	});
	
	$(".sorting .sort").click(function(e) {
		e.preventDefault();
		var sortBy = $(this).attr("val");
		if($sortBy.val() == sortBy) {
			$sortMethod.val($sortMethod.val() == "desc" ? "asc" : "desc");
		} else {
			$sortBy.val(sortBy);
			$sortMethod.val("desc");
		}
		$searchForm.submit();
	});
	
	$(".sorting dl dd a").click(function() {
		var obj = $(this).parent().siblings().children();
		if($(this).hasClass('active') || $(this).hasClass('ascending')) {
			obj.removeAttr("class");
		} else {
			$(this).addClass('ascending');
			obj.removeAttr("class");
		}
		if($(this).hasClass('active')) { 
			$(this).removeClass('active');
			$(this).addClass('ascending');
			obj.removeAttr("class");
		} else if($(this).hasClass('ascending')) {
			$(this).removeClass('ascending');
			$(this).addClass('active');
			obj.removeAttr("class");
		};
	});


	var $progre = $("#progre"),$period = $("#period"),$rate = $("#rate");

	layui.use(['laypage', 'laytpl'], function () {
		var laypage = layui.laypage,
			laytpl = layui.laytpl;
		$.getJSON('${ctx}/investment/data',
			{page: 1, rows: 10,
			progre: $progre.val(),
			period: $period.val(),
			rate: $rate.val()
			},
			function (res) {
				//使用方式跟独立组件完全一样
				var tpl = document.getElementById("dataTpl");
				laytpl(tpl.innerHTML).render(res, function (string) {
					$('#data').html(string);
				});
				laypage({
					skin: '#B81B26',
					cont: 'pagination'
					, pages: res.pages
					, jump: function (e, first) { //触发分页后的回调
						if (!first) {
							$.getJSON('${ctx}/investment/data', {
								page: e.curr,//当前页
								rows: res.pageSize,
								progre: $progre.val(),
								period: $period.val(),
								rate: $rate.val()
								}, function (rest) {
								e.pages = e.last = rest.pages; //重新获取总页数，一般不用写
								//解析数据
								laytpl(tpl.innerHTML).render(rest, function (stringt) {
									$('#data').html(stringt);
								});
							});
						}
					}
				});
			}
		);
		countdown();

	});


	[#-- 预告标倒计时 --]
	function countdown() {
	var nowTime = ${setting.now.getTime() / 1000};
	intervalId = window.setInterval(function() {
	$(".forecasting").each(function(index, obj) {
	$obj = $(obj);
	var timeDifference = $obj.attr("startTime") - nowTime;
	if (timeDifference > 1) {
	var days = Math.floor((timeDifference / 3600) / 24);
	var hours = Math.floor((timeDifference / 3600) % 24);
	var minutes = Math.floor((timeDifference / 60) % 60);
	var seconds = Math.floor(timeDifference % 60);
	$obj.text((days < 10 ? "0" + days : days) + "天-" + (hours < 10 ? "0" + hours : hours) + "时-" + (minutes < 10 ? "0" + minutes : minutes) + "分-" + (seconds < 10 ? "0" + seconds : seconds) + "秒");
	} else {
	$obj.text("立即投标").removeClass('ivenfrm-btn1 forecasting').addClass('ivenfrm-btn2').attr('style', '').attr('href', $(this).attr('link'));
	}
	});
	nowTime ++;
	}, 1000);

	}

});