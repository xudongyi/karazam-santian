[#if !nav??]
    [#assign nav="index" /]
[/#if]
<!DOCTYPE html>
<html lang="zh-CN" class="login">
<head>
    <link href="[@img src='favicon.ico'/]" rel="icon" type="image/x-icon"/>
    <link href="[@img src='favicon.ico'/]" rel="shortcut icon" type="image/x-icon"/>
    [#include "include/meta.ftl" /]
    <title>${title}</title>
    [#include "include/link_top.ftl" /]
    [@css href="css/mate/common.css" /]
    [@css href="css/mate/reset.css" /]
    [@css href="css/index_login.css" /]
    [@stylesheet /]
    <script>
        var ctx = "${ctx}";
    </script>
</head>
<body class="">

    [#-- 页眉 --]
    [#include "include/header.ftl" /]
        [@body /]
    [#-- Footer 页脚 --]
    [#include "include/footer.ftl" /]

</body>
    [#include "include/script_top.ftl" /]
    [#include "include/script_bottom.ftl" /]
    [@js src="lib/jquery/validate/jquery.validate.min.js,
              lib/jquery/validate/jquery.validate.common.min.js,
              lib/jquery/validate/jquery.validate.method.min.js,
              lib/jquery/cookie/jquery.cookie.min.js" /]
    [@js src="lib/tools/rsa/jsbn.min.js,
        lib/tools/rsa/prng4.min.js,
        lib/tools/rsa/rng.min.js,
        lib/tools/rsa/rsa.min.js,
        lib/tools/base/base64.min.js" /]
    [@js src="js/index/index.js" /]
    <script>
        $(document).ready(function() {
            countingDown(nowTime);
        });
        function countingDown(nowTime) {
            var intervalId = window.setInterval(function() {
                $(".countdown").each(function(index, obj) {
                    var $obj = $(obj);
                    var timeDifference = new Date($obj.attr("startTime"))/1000 - nowTime;
                    if (timeDifference > 1) {
                        var days = Math.floor((timeDifference / 3600) / 24);
                        var hours = Math.floor((timeDifference / 3600) % 24);
                        var minutes = Math.floor((timeDifference / 60) % 60);
                        var seconds = Math.floor(timeDifference % 60);
                        // $obj.text((days < 10 ? "0" + days : days) + "天-" + (hours < 10 ? "0" + hours : hours) + "时-" + (minutes < 10 ? "0" + minutes : minutes) + "分-" + (seconds < 10 ? "0" + seconds : seconds) + "秒");
                        if (hours > 1) {
                            $obj.text(datetimeFormatter(new Date($obj.attr("startTime"))));
                        } else {
                            $obj.text((hours < 10 ? "0" + hours : hours) + "时-" + (minutes < 10 ? "0" + minutes : minutes) + "分-" + (seconds < 10 ? "0" + seconds : seconds) + "秒");
                        }
                        $obj.parent().show();
                    } else {
                        $obj.text("").parent().hide();
                    }
                });
                nowTime++;
            }, 1000);
        }

        var nowTime = nowTime / 1000;
    </script>
    [@script /]
</html>