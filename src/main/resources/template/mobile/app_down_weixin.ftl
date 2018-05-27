[@nestedstyle]
    [@css href="css/appdown_weixin.css" /]
[#--../images/appdown/bg_img.jpg--]
[/@nestedstyle]
[@insert template="layout/appdown_layout_weixin" title="${setting.basic.siteName}" user=kuser]
<div class="box" xmlns="http://www.w3.org/1999/html" style="height:500px;background:url(${ctx}/static/images/appdown/bg_img.jpg)no-repeat center center;background-size: 100%">
    <div class="tip" id="tip"></div>
    <div class="cont_mes">
        <div class="i_cont">
            <a href="${ctx}/index"><img src="${ctx}/static/images/appdown/logo.png"></a>
        </div>
    </div>
</div>
<div class="inv-fbtn" id="p_btn" style="height: 110px;position: static;margin-top: 10px">
    <a id="a_link" class="ui_red_btn" >客户端下载</a>
</div>
<div class="foot_tips" style="position: static;margin-top: 10px">© 2017 ${setting.basic.siteName} All rights reserved 市场有风险，投资需谨慎</div>
<script type="text/javascript">
    var tip = document.getElementById("tip");
    var a_link = document.getElementById("a_link");
    //判断是否微信
    function weixinFacility() {
        var userAgentString = window.navigator ? window.navigator.userAgent : "";
        var weixinreg = /MicroMessenger/i;
        return weixinreg.test(userAgentString);
    };
    //跳转监听
    if (weixinFacility()) {
        document.getElementById('a_link').onclick = function (e) {
            document.getElementById("down_body").style.overflow = "hidden";
            tip.style.display = "block";
            e.preventDefault();
            e.stopPropagation();
        }
    } else {
        a_link.onclick = function () {
            // 获取终端的相关信息
            var Terminal = {
                // 辨别移动终端类型
                platform: function () {
                    var u = navigator.userAgent, app = navigator.appVersion;
                    return {
                        // android终端或者uc浏览器
                        android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1,
                        // 是否为iPhone或者QQHD浏览器
                        iPhone: u.indexOf('iPhone') > -1,
                        // 是否iPad
                        iPad: u.indexOf('iPad') > -1
                    };
                }(),
            }
            // 根据不同的终端，跳转到不同的地址
            var theUrl = '';
            if (Terminal.platform.android) {
                theUrl = '${ctx}/mobile/others/appDownLoad/ANDROID';
            } else {
                theUrl = 'itms-apps://itunes.apple.com/cn/app/zhilianjinrong/id1339091447?l=zh&ls=1&mt=8';
            }
//            else{
//                theUrl = 'http://www.isantian.com';
//                console.log(1122)
//            }

            location.href = theUrl;
        }
    }
    //tip消失
    tip.onclick = function () {
        document.getElementById("down_body").style.overflow = "auto";
        this.style.display = "none";
    }
</script>
[/@insert]