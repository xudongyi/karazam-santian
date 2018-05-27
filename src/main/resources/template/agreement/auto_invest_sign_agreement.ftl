<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>智链金融</title>
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <!--标准mui.css-->
    <link rel="stylesheet" href="/static/css/agreement/mui.min.css">
    <link rel="stylesheet" href="/static/css/agreement/app.css">
</head>
<body>

<div class="mui-content">
    <div class="mui-content-padded">
        <h4 align="center" style="font-weight: bold">自动投标授权书</h4>
        <ul class="egalPerson">
            <li>委托人：${user.realName}</li>
            <li>身份证号:${user.idNo}</li>
            <li>联系方式:${user.mobile}</li>
            <li>受托人：青海高领网络科技有限公司（以下简称“拍拖贷”）:</li>
        </ul>

        <ul class="instr-2 egalPerson">
            <li class="no-1"><p>
                <p>鉴于委托人为在拍拖贷平台上实名注册的用户，现拟参与拍拖贷平台开发的“自动投标”功能，为优化委托人的用户体验，委托人就“自动投标”的相关投标、资金划转、退出等相关事项向受托人做出如下授权：</p>
                <p>一、授权范围</p>
                <p>（一）代委托人在拍拖贷平台上点击确认投标、出借资金、借款协议、债权转让协议等业务流程中的全部协议。</p>
                <p>（二）代委托人进行划转出借资金、收回借款本息、收取债权转让款项等业务流程中涉及的全部资金管理。</p>
                <p>二、授权出借信息</p>
                <p>（一）授权出借的账户</p>
                <p>拍拖贷平台账号：${user.mobile}</p>
                <p>（二）授权出借的金额：以委托人自行设置自动投标金额限制为准</p>
                <p>（三）授权期限：以委托人自行设置投标的期限（包括开启/关闭自动投标设置及出借期限设置）为准</p>
                <p>三、本委托书经委托人在拍拖贷平台以线上点击确认的方式签署。</p>
            </li>
        </ul>
        <ul class="instr-2 egalPerson" style="float: right;">
            <li>委托人: ${user.realName}</li>
            <li>${.now?string("yyyy年MM月dd日")}</li>
        </ul>
    </div>
</div>
</body>
<script src="/static/js/agreement/mui.min.js"></script>
</html>