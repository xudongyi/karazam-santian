<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<meta name="author" content="Karazam Team" />
		<meta name="copyright" content="Karazam" />
		<title>${setting.basic.siteName}</title>
	</head>
	<body onload="javascript: document.forms[0].submit();">
		<form action="${requestUrl}" method="post" accept-charset="UTF-8">
			[#list parameterMap.entrySet() as entry]
				<input type="hidden" name="${entry.key}" value="${entry.value}" />
			[/#list]
		</form>
	</body>
</html>