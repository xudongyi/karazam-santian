[#assign nav="investment" /]
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <link href="[@img src='favicon.ico'/]" rel="icon" type="image/x-icon"/>
    <link href="[@img src='favicon.ico'/]" rel="shortcut icon" type="image/x-icon"/>
    [#include "include/meta.ftl" /]
        <title>${title}</title>
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
    [#-- validate 验证器 --]
    [@js src="lib/validate/jquery.validate.min.js" /]
    [@js src="lib/validate/jquery.validate.method.min.js" /]
    [@js src="lib/tools/rsa/jsbn.min.js,
        lib/tools/rsa/prng4.min.js,
        lib/tools/rsa/rng.min.js,
        lib/tools/rsa/rsa.min.js,
        lib/tools/base/base64.min.js" /]
    [@js src="js/index/index.js" /]
    [#-- project.invest 项目投资 --]
    [#include "include/script_bottom.ftl" /]
    [@script /]
</html>