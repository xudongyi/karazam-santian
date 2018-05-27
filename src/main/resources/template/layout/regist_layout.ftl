[#assign nav="regist" /]
<!DOCTYPE html>
<html lang="zh-CN" class="login">
<head>
    <link href="[@img src='favicon.ico'/]" rel="icon" type="image/x-icon"/>
    <link href="[@img src='favicon.ico'/]" rel="shortcut icon" type="image/x-icon"/>
[#include "include/meta.ftl" /]
    <title>${title}-${setting.basic.siteName}</title>
[#include "include/login_regist_link_top.ftl" /]
[@stylesheet][/@stylesheet]
    <script>
        var ctx = "${ctx}";
    </script>
</head>
<body style="background:#fff">
    [#-- 页眉 --]
    [#include "include/login_regist_header.ftl" /]
    [@body /]
    [#-- Footer 页脚 --]
    [#include "include/login_regist_footer.ftl" /]
</body>
[@js src="lib/jquery/jquery-1.11.3.js,
    lib/jquery/validate/jquery.validate.min.js,
    lib/jquery/validate/jquery.validate.common.min.js,
    lib/jquery/validate/jquery.validate.method.min.js,
    lib/jquery/cookie/jquery.cookie.min.js" /]
[@js src="lib/tools/rsa/jsbn.min.js,
    lib/tools/rsa/prng4.min.js,
    lib/tools/rsa/rng.min.js,
    lib/tools/rsa/rsa.min.js,
    lib/tools/base/base64.min.js" /]
<script type="text/javascript">
    var modules = "${modules}", exponent = "${exponent}";
</script>
[@script /]
</html>