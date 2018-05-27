<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <meta name="author" content="Karazam Team" />
    <meta name="copyright" content="Karazam" />
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>${setting.basic.siteName}</title>
    <link href="${ctx}/static/css/mui.min.css" rel="stylesheet" />
    <link href="//at.alicdn.com/t/font_398979_uu7hixqa7coywrk9.css" rel="stylesheet" />
    <style type="text/css">
        body {
            text-align: center;
        }

        .comtent {
            margin: 50% auto;
        }

        .comtent .span1 {
            font-size: 32px;
        }

        .comtent .span2 {
            margin-left: 10px;
            font-size: 32px;
            font-weight: 700;
        }

        .time {
            margin-top: 20px;
        }
    </style>
</head>
<body>
	<p id="resultIos" hidden>${resultStr!""}<p>
	<div hidden>
		<input id="resultAnd" type="button" onclick="SANTIAN.complete('${resultStr!""}')" value="调用数据"/>
	</div>
	<div class="comtent">
		[#if result.code == "000"]
			<span class="span1 mui-icon iconfont icon-right" style="color: #3CB034;"></span>
			<span class="span2">处理成功</span>
		[#elseif result.code == "002"]
            <span class="span1 mui-icon iconfont icon-chulizhong" style="color: #4E8CEE;"></span>
            <span class="span2">处理中</span>
		[#elseif result.code == "001"]
            <span class="span1 mui-icon iconfont icon-shibai" style="color: #FF3C66;"></span>
            <span class="span2">处理失败</span>
            <br/><span>${result.message}</span>
		[#else]
			<span class="span1 mui-icon iconfont icon-shibai"></span>
			<span class="span2">系统错误</span>
		[/#if]
	</div>

    <script type="text/javascript">
        document.getElementById("resultAnd").onclick();
    </script>
</body>
</html>