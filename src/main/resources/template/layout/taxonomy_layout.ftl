<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <link href="[@img src='favicon.ico'/]" rel="icon" type="image/x-icon"/>
    <link href="[@img src='favicon.ico'/]" rel="shortcut icon" type="image/x-icon"/>
    [#include "include/meta.ftl" /]
    <title>${title}-${setting.basic.siteName}</title>
    [#include "include/link_top.ftl" /]
    [@css href="css/mate/common.css" /]
    [@stylesheet /]
    <script>
        var ctx = "${ctx}";
        var contentUrlPrefix = "${setting.postsSetting.contentUrlPrefix}";
        var taxonomyUrlPrefix = "${setting.postsSetting.taxonomyUrlPrefix}";
        var type = "${type}";
        var slug = "${slug}";
    </script>
</head>
<body>
<div id="wrapper">
    [#-- 页眉 --]
    [#include "include/header.ftl" /]
        [#--内容--]
        [@body /]
    [#-- Footer 页脚 --]
    [#include "include/footer.ftl" /]
</div>
</body>
    [#include "include/script_top.ftl" /]
    [@js src="lib/jquery/validate/jquery.validate.min.js,
              lib/jquery/validate/jquery.validate.common.min.js,
              lib/jquery/validate/jquery.validate.method.min.js" /]
    [@js src="lib/tools/rsa/jsbn.min.js,
        lib/tools/rsa/prng4.min.js,
        lib/tools/rsa/rng.min.js,
        lib/tools/rsa/rsa.min.js,
        lib/tools/base/base64.min.js" /]
    [@js src="lib/layui/layui.js" /]
    [@js src="js/index/index.js" /]
    [@js src="js/posts/cms.js" /]
    [@script /]
</html>
