<!DOCTYPE html>
<html lang="zh-CN" class="login">
<head>
    <link href="[@img src='favicon.ico'/]" rel="icon" type="image/x-icon"/>
    <link href="[@img src='favicon.ico'/]" rel="shortcut icon" type="image/x-icon"/>
    <title>${title}-管理中心</title>
    [@css href="lib/bootstrap/css/bootstrap.min.css,
                lib/bootstrap/css/bootstrap.fix.min.css,
                lib/jquery/validate/validate.min.css" /]
    [@css href="css/admin/login.min.css" /]
    [@stylesheet /]
    <script>
        var ctx = "${ctx}";
    </script>
</head>
<body>
    [@body /]
</body>
    [@js src="lib/jquery/jquery-1.11.3.js,
              lib/bootstrap/js/bootstrap.min.js,
              lib/jquery/validate/jquery.validate.min.js,
              lib/jquery/validate/jquery.validate.common.min.js,
              lib/jquery/validate/jquery.validate.method.min.js,
              lib/jquery/cookie/jquery.cookie.min.js" /]

    [@js src="lib/tools/rsa/jsbn.min.js,
        lib/tools/rsa/prng4.min.js,
        lib/tools/rsa/rng.min.js,
        lib/tools/rsa/rsa.min.js,
        lib/tools/base/base64.min.js" /]
    [#-- 登录 --]
    <script type="text/javascript">
        var modules = "${modules}", exponent = "${exponent}";
    </script>
    [@script /]
</html>