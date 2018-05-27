[#assign nav=nav!"index" /]
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no,minimal-ui">
    <meta name="renderer" content="webkit">
    <title>${title} 手机客户端下载</title>
    <link href="[@img src='favicon.ico'/]" rel="icon" type="image/x-icon"/>
    <link href="[@img src='favicon.ico'/]" rel="shortcut icon" type="image/x-icon"/>
[@stylesheet /]
    <script>
        var ctx = "${ctx}";
    </script>
</head>
<body id="down_body" style="overflow: scroll">
    [@body /]
</body>
</html>