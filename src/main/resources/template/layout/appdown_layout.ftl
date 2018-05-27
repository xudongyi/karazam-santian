[#assign nav=nav!"index" /]
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <link href="[@img src='favicon.ico'/]" rel="icon" type="image/x-icon"/>
    <link href="[@img src='favicon.ico'/]" rel="shortcut icon" type="image/x-icon"/>
    [#include "include/meta.ftl" /]
        <title>${title} - 手机客户端|${setting.basic.siteName}</title>
    [#include "include/link_top.ftl" /]
    [@css href="css/mate/common.css" /]
    [@stylesheet /]
    <script>
        var ctx = "${ctx}";
    </script>
</head>
<body>
<div id="wrapper">
    [#-- 页眉 --]
    [#include "include/header.ftl" /]
        [@body /]
    [#-- Footer 页脚 --]
    [#include "include/footer.ftl" /]
</div>
</body>
    [#include "include/script_top.ftl" /]
    [#--<script type="text/javascript">--]
        [#--[#include "/template/investment/list_js.ftl"]--]
    [#--</script>--]
    <script type="text/javascript">
        var nowTime = ${setting.now.getTime() / 1000};
    </script>
    [#include "include/script_bottom.ftl" /]
    [@js src="lib/jquery/validate/jquery.validate.min.js,
              lib/jquery/validate/jquery.validate.common.min.js,
              lib/jquery/validate/jquery.validate.method.min.js" /]
    [@js src="js/index/index.js" /]
    [@script /]
</html>