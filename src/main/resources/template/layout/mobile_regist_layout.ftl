<!DOCTYPE html>
<html lang="zh-CN" class="${htmlClass}">
<head>

[#include "/include/meta.ftl" /]
    <link rel="icon" type="image/png" href="${ctx}/static/assets/i/ico.png">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
[@css href="css/mobile/amazeui.min.css" /]
[@css href="css/mobile/app.css" /]
[@stylesheet /]
    <script>
        var ctx = "${ctx}";
    </script>
[@js src="js/mobile/jquery.min.js,
             js/mobile/amazeui.js,
              js/mobile/template-debug.js,
              js/mobile/amazeui.min.js" /]
[@script /]
</head>
<body style="${bodyStyle}" class="${bodyClass}">
[#--内容--]

    [@body /]
</body>
</html>
