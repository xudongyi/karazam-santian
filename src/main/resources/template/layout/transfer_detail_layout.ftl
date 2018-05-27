[#assign nav="investment" /]
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <link href="[@img src='favicon.ico'/]" rel="icon" type="image/x-icon"/>
    <link href="[@img src='favicon.ico'/]" rel="shortcut icon" type="image/x-icon"/>
    [#include "include/meta.ftl" /]
        <title>${title} - 投资详情</title>
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
    <script type="text/javascript">
        var modules = "${modules}", exponent = "${exponent}";
        var $projectId = "${borrowing.id}";
        var	isLogin = [#if currentUser??]true[#else]false[/#if];
            available = ${(currentUser.available)!0};

        var minAmount = 100;  //项目总份数
        var maxParts = ${transfer.getMaxParts()};  //项目总份数
        var maxAmount = ${transfer.getMaxParts()*100};
        var validParts = ${transfer.getSurplusParts()};  //项目剩余份数
        var residualAmount = ${transfer.getSurplusParts()*100};
        var inTransferFeeRate = ${transfer.inTransferFeeRate!"0"}; //转入方服务费率
        var worthTotal = ${transfer.worth}; //总价值
        var surplusWorth = ${transfer.surplusWorth}; //剩余价值
        var surplusCapital = ${transfer.surplusCapital}; //剩余价值
        var nowTime = ${setting.now.getTime() / 1000};
        var expireDate = ${expireDate.getTime() / 1000};
        /*余额显示隐藏切换*/
        function showshow(){
            $('#_number').show();
            $('#_asterisk').hide();
        }
        function hidehide(){
            $('#_number').hide();
            $('#_asterisk').show();
        }
        hidehide();
    </script>
    <script type="text/javascript">
        $(function() {
            var nowTime = ${setting.now.getTime() / 1000};
            intervalId = window.setInterval(function() {
                $(".forecasting").each(function(index, obj) {
                    $obj = $(obj);
                    var timeDifference = $obj.attr("startTime") - nowTime;
                    if (timeDifference > 1) {
                        var days = Math.floor((timeDifference / 3600) / 24);
                        var hours = Math.floor((timeDifference / 3600) % 24);
                        var minutes = Math.floor((timeDifference / 60) % 60);
                        var seconds = Math.floor(timeDifference % 60);
                        $obj.text((days < 10 ? "0" + days : days) + "天-" + (hours < 10 ? "0" + hours : hours) + "时-" + (minutes < 10 ? "0" + minutes : minutes) + "分-" + (seconds < 10 ? "0" + seconds : seconds) + "秒");
                    } else {
                        $obj.text("投标开始");
                        location.reload();
                    }
                });
                nowTime ++;
            }, 1000);

        });
    </script>
    [#include "include/script_bottom.ftl" /]
    [@script /]
</html>