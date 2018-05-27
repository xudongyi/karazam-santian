[@compress single_line = !systemDevelopment]
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>${setting.basic.siteName}-应收账款转让合同</title>
    <style type="text/css">
        body {
            font-family: SimSun;
            font-size: 12px;
            line-height: 24px;
            width: 842px;
            border: 1px solid #ccc;
            padding: 10px;
            margin: 0 auto;
        }
    </style>
</head>
<body>

<h1 align="center">应收账款转让合同</h1>
<p style="text-align:right;">
    合同编号：<span>HSJR-${((borrowing.lendingDate)?string("yyyyMMdd"))!"XXXXXXXX"}-${(borrowing.id)!"XXXX"}</span>
</p>
[#--<p>签约日期：<span>${(recovery.createDate?string("yyyy年MM月dd日"))!"XXXX年XX月XX日"}</span></p>--]
<br/>
[#--<p>协议三方定义：</p>--]
<p>应收账款转让人：<span style="text-decoration:underline">深圳交投商业保理有限公司</span><span>（以下简称甲方）</span></p>
<p>营业执照号码：<span style="text-decoration:underline">&nbsp;&nbsp;${borrowing.factoringComBussinessNo!"XXXX"}&nbsp;&nbsp;</span></p>
<br/>
<p>应收账款受让人：<span>见《附件2：投资人列表》（以下简称乙方）</span></p>
<br/>

<p class="p">定义（如上下文无其他解释，本文相关定义如下）：</p>
<p class="p">出卖人：基于基础交易而取得应收账款的法人。</p>
<p class="p">买受人：基于接受基础交易中的服务或货物而应支付对价的法人。</p>
<p class="p">保理商：在工商管理部门登记的，拥有保理资质的法人。</p>
<p class="p">应收账款受让人：通过华善金融平台（www.haushanjinrong.com）受让应收账款的投资人。</p>

<h4>鉴于:</h4>
<p class="p" style="text-indent:2em;">1、出卖人与买受人已签署编号为<span style="text-decoration:underline">&nbsp;&nbsp;${borrowing.tradingContractNo!"XXXX"}&nbsp;&nbsp;</span>等与应收账款有关的法律文件，并已建立基础交易关系（以下简称“基础交易关系”）。</p>
<p class="p" style="text-indent:2em;">2、出卖人与甲方签署的编号为<span style="text-decoration:underline">&nbsp;&nbsp;${borrowing.commercialFactoringContractNo!"XXXX"}&nbsp;&nbsp;</span>的《商业保理合同》（以下简称“《商业保理合同》”）将基础交易项下应收账款人民币<span style="text-decoration:underline">&nbsp;&nbsp;${numToRMB(borrowing.receivables)!"XXXX"}&nbsp;&nbsp;</span>元整（<span style="text-decoration:underline">&nbsp;&nbsp;${(borrowing.receivables?string("currency"))!"XXXX"}&nbsp;&nbsp;</span>）转让给甲方，由甲方为出卖人提供保理服务。</p>
<p class="p" style="text-indent:2em;">现经甲乙双方协商一致，甲方将上述受让的应收账款再转让给乙方，为保证本业务的顺利进行，双方约定如下：</p>
<h4>第一条  业务简介</h4>
<p class="p" style="text-indent:2em;">本合同业务为应收账款转让及应收账款委托管理业务，即乙方在本合同有效期及延续期间受让甲方的应收账款，并委托甲方代为管理和催收应收账款。</p>
<h4>第二条  应收账款转让标的</h4>
<p class="p" style="text-indent:2em;">本合同项下甲方向乙方转让基于《商业保理合同》中甲方受让的应收账款金额为人民币 <span style="text-decoration:underline">&nbsp;&nbsp;${numToRMB(borrowing.amount)!"￥XXXX.XX"}&nbsp;&nbsp;</span>元整（<span style="text-decoration:underline">&nbsp;&nbsp;${(borrowing.amount?string("currency"))!"￥XXXX.XX"}&nbsp;&nbsp;</span>）。</p>
<h4>第三条  应收账款到期日</h4>
<p class="p" style="text-indent:2em;">本合同转让的应收账款期限自本合同签署之日起计算，应收账款到期日为<span style="text-decoration:underline">&nbsp;&nbsp;${(repaymentEndDate?string("yyyy年MM月dd日"))!"XXXX年XX月XX日"}&nbsp;&nbsp;</span>。</p>
<h4>第四条  融资金额及收益率</h4>
<p class="p" style="text-indent:2em;">本合同项下甲方转让应收账款进行融资的金额为人民币<span style="text-decoration:underline">&nbsp;&nbsp;${numToRMB(borrowing.amount)!"￥XXXX.XX"}&nbsp;&nbsp;</span>元整（<span style="text-decoration:underline">&nbsp;&nbsp;${(borrowing.amount?string("currency"))!"￥XXXX.XX"}&nbsp;&nbsp;</span>），预期年化收益率为<span style="text-decoration:underline">&nbsp;&nbsp;${(borrowing.realInterestRate)!"XX.XX"}%&nbsp;&nbsp;</span>。</p>
<h4>第五条  支付方式</h4>
<p class="p" style="text-indent:2em;">乙方应当通过华善金融平台（https://www.huashanjinrong.com）规定的支付方式一次性支付转让款，乙方资金为自有且合法取得。</p>
<p class="p" style="text-indent:2em;">在应收账款到期日，甲方应当通过上海华善金融信息服务有限公司管理的“华善金融”平台（https://www.huashanjinrong.com）规定的方式一次性支付乙方按照本合同约定应获相关款项。</p>
<h4>第六条  提前回款</h4>
<p class="p" style="text-indent:2em;">本合同项下应收账款在本合同第三条约定的应收账款到期日之前回款的，甲方将按照实际转让天数支付乙方按照本合同约定应获相关款项。</p>
<h4>第七条  逾期处理</h4>
<p class="p" style="text-indent:2em;">1、本合同项下应收账款到期后，买受人未回款的，自应收账款到期日次日起算十个工作日作为宽限期，宽限期内依照本合同第四条规定收益率计算收益。</p>
<p class="p" style="text-indent:2em;">2、宽限期内，出卖人或甲方应当按照约定的年化收益率溢价接受乙方对本合同项下应收账款的反转让，并依据华善金融平台（https://www.huashanjinrong.com）发出的付款通知，将反转让资金支付到指定账户。</p>
<h4>第八条  逾期收益</h4>
<p class="p" style="text-indent:2em;">应收账款宽限期届满后，甲方仍无法足额按本合同约定溢价接受乙方对本合同约定应收账款反转让的，应当通过华善金融平台（https://www.huashanjinrong.com）指定的方式向乙方支付逾期罚息。</p>
<p class="p" style="text-indent:2em;">逾期罚息=拖欠款项 ×逾期天数 ×罚息利率。</p>
<p class="p" style="text-indent:2em;">拖欠款项是指截止应收账款宽限期届满，甲方应当支付而未支付的部分；</p>
<p class="p" style="text-indent:2em;">逾期天数是指从宽限期次日起至拖欠款项（包括本金及收益）及逾期利息等清偿完毕之日止（含到账日）的自然日天数。</p>
<p class="p" style="text-indent:2em;">罚息利率：本合同项下罚息利率为每日${borrowing.seriousOverdueInterestRate}%。</p>
<h4>第九条  应收账款管理及催收服务</h4>
<p class="p" style="text-indent:2em;">本合同项下应收账款管理及催收服务是指对于甲方向乙方转让的应收账款，甲方及甲方委托的第三方应当尽到管理义务，及时将应收账款在有关部门进行登记并签署相关合同，同时甲方及甲方委托的第三方应当尽到催收义务，应收账款到期后应当及时催收。</p>
<h4>第十条  应收账款瑕疵担保</h4>
<p class="p" style="text-indent:2em;">甲方保证每一笔应收账款均符合以下全部条件：</p>
<p class="p" style="text-indent:2em;">1、基础合同真实、合法且有效，没有禁止或限制该合同项下的应收账款转让，不存在任何不利于乙方行使应收账款项下权利的条款，出卖人将会适当的履行其在基础合同项下的义务；</p>
<p class="p" style="text-indent:2em;">2、保理合同真实、合法且有效，保理合同没有禁止或限制该合同项下应收账款的再次转让，保理合同中不存在其他任何不利于乙方行使应收账款项下权利的条款；</p>
<p class="p" style="text-indent:2em;">3、甲方转让的应收账款债权不存在任何质押或其他担保，也不存在任何权属争议；就已在本合同项下转让给乙方的每一笔应收账款（甲方已接受反转让的应收账款除外）而言，甲方不会将其另行转让给任何其他第三人。</p>
<h4>第十一条  应收账款代位追偿权</h4>
<p class="p" style="text-indent:2em;">如出现以下情形，乙方可通过华善金融平台（https://www.huashanjinrong.com）规定的方式向出卖人和买受人代为行使追偿权，无需征得甲方同意：</p>
<p class="p" style="text-indent:2em;">1、本合同约定的宽限期届满后，甲方未能接受乙方对本合同约定应收账款的反转让。</p>
<p class="p" style="text-indent:2em;">2、甲方被人民法院宣布破产。 </p>
<p class="p" style="text-indent:2em;">3、买受人应付账款到期后，甲方怠于行使债权而影响到乙方的合法权利。</p>
<h4>第十二条  再转让通知</h4>
<p class="p" style="text-indent:2em;">本合同签署后，由甲方通知买受人应收账款转让事宜。</p>
<h4>第十三条  合同的生效、终止和变更</h4>
<p class="p" style="text-indent:2em;">1、本合同经各方签字、盖章后生效（乙方按照上海华善金融信息服务有限公司管理的“华善金融”平台（https://www.huashanjinrong.com）规定的方式进行投标并经过“华善金融”平台确认投标成功后即视为有效签字），至本合同项下各方权利义务履行完毕之日止。</p>
<p class="p" style="text-indent:2em;">2、本合同的有效性不因个别条款无效而受影响。</p>
<p class="p" style="text-indent:2em;">3、除本合同另有规定外，任何一方未经另一方书面同意，无权单方面更改本合同的任何条款；一方要求对本合同条款进行任何修改，应书面通知另一方，在取得另一方的书面同意后方可进行。</p>
<p class="p" style="text-indent:2em;">4、有关本合同的所有通知、变更等应采取书面形式。</p>
<h4>第十四条  风险提示</h4>
<p class="p" style="text-indent:2em;">1、因国家宏观政策、财政政策、货币政策、行业政策、地区发展、法律法规等因素引起的政策风险。</p>
<p class="p" style="text-indent:2em;">2、如转让人发生资金状况或经营状况的风险，或者转让人接受反转让的意愿发生消极的变化时，乙方可能无法按时获得应收账款项下款项。需经过司法程序对甲方、出卖人及买受人财产进行清偿后才能收回融资款。如各方合法财产清偿完毕仍然不足以偿还乙方损失的，乙方可能无法获得应收账款项下款项。</p>
<p class="p" style="text-indent:2em;">3、由于战争、动乱、自然灾害等不可抗力因素的出现而可能导致乙方无法按时获得应收账款项下款项的风险。</p>
<h4>第十五条  税务处理</h4>
<p class="p" style="text-indent:2em;">乙方在资金出借过程产生的相关税费，由乙方自行向税务机关申报、缴纳，甲方不负责相关事宜处理。</p>
<h4>第十六条  保密条款</h4>
<p class="p" style="text-indent:2em;">未经双方同意，任何一方不得将本合同内容泄露给第三方（法律、行政法规、司法解释另有规定的情形除外）。</p>
<h4>第十七条  日期</h4>
<p class="p" style="text-indent:2em;">本合同中所有涉及的日期若恰为非银行营业日，则顺延至下一个最近的银行营业日。</p>
<h4>第十八条  争议解决</h4>
<p class="p" style="text-indent:2em;">与本合同相关的任何争议均可向青海省西宁市城西区人民法院提起诉讼。</p>
<h4>第十九条  适用法律、法规</h4>
<p class="p" style="text-indent:2em;">本合同适用的是中国的法律、法规和司法解释，以及服务区域的地方法规、地方规章，包括甲方所在地的人民法院所做的指导意见、批复。上述法律、法规、文件没有规定的，适用行业惯例。</p>
<h4>第二十条  补充约定</h4>
<p class="p" style="text-indent:2em;">乙方可将本协议约定的债权转让给第三人，具体转让程序依据华善金融平台（https://www.huashanjinrong.com）的有关规定。本合同一式二份，双方各执一份，具有同等法律效力。</p>

<p class="p" style="text-indent:2em;">（以下无正文）</p>
<p class="p" style="text-indent:2em;"><span width="100%">甲方：<span style="color: #8f8f8f;">（公章）</span></span></p>
<br/>
<p class="p" style="text-indent:2em;"><span>法定代表人（或授权委托人）：<span style="color: #8f8f8f;">（签印）</span></span></p>
<br/>
<p class="p" style="text-indent:2em;"><span style="margin-left: 600px;">签署日期：${(borrowing.lendingDate?string("yyyy年MM月dd日"))!"XXXX年XX月XX日"}</span></p>
<h4>附件1：还款计划列表 </h4>
<table width="100%" border="0" cellpadding="0" cellspacing="0" style="font-size: 12px; line-height: 36px;">
    <tr>
        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 1px;">还款时间</td>
        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;">偿还本金（元）</td>
        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;">溢价收益（元）</td>
        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;">平台服务费（元）</td>
        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;">偿还金额（元）</td>
    </tr>
	[#if repayments != null && repayments?size gt 0]
		[#list repayments as repayment]
			[#if repayment.state == "repaid"]
                <tr>
                    <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 1px;">（实际）${repayment.paidDate?string("yyyy年MM月dd日")}</td>
                    <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0px;">（实际）${repayment.capital?string("currency")}</td>
                    <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0px;">（实际）${repayment.interest?string("currency")}</td>
                    <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0px;">（实际）${repayment.repaymentFee()?string("currency")}</td>
                    <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0px;">（实际）${repayment.capitalInterestOverdueAheadFee?string("currency")}</td>
                </tr>
			[#else]
                <tr>
                    <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 1px;">${repayment.payDate?string("yyyy年MM月dd日")}</td>
                    <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0px;">${repayment.capital?string("currency")}</td>
                    <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0px;">${repayment.interest?string("currency")}</td>
                    <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0px;">${repayment.repaymentFee()?string("currency")}</td>
                    <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0px;">${(repayment.capitalInterestFee)?string("currency")}</td>
                </tr>
			[/#if]
		[/#list]
	[#else]
        <tr>
            <td align="right" style="border-color: #ccc; border-style: solid; padding-right: 10px; border-width: 0px 1px 1px 1px;">XX年XX月XX日</td>
            <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0px;">￥XXXX.XX</td>
            <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0px;">￥XXXX.XX</td>
            <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0px;">￥XXXX.XX</td>
            <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0px;">￥XXXX.XX</td>
        </tr>
	[/#if]
</table>
<h4>附件2：投资列表</h4>
<table width="100%" border="0" cellpadding="0" cellspacing="0" style="font-size: 12px; line-height: 36px;">
    <tr>
        <td align="left" style="border: 1px solid #ccc; padding-left: 10px;">用户名</td>
        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;">姓名</td>
        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;">身份证号</td>
        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;">投资金额</td>
        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;">投资时间</td>
        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;">预期收益</td>
    </tr>
	[#if investments != null && investments?size gt 0]
		[#list investments as investment]
			[#--[#if recovery.parent??]
			[#else]--]
                <tr>
                    <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0px 1px 1px 1px;">
                        ${secrecy("mobile",investment.investorLoginName)!XXXXXX}
                    </td>
                    <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0px 1px 1px 0px;">
                        ${secrecy("fullName",investment.investorRealName)!XXXXXX}
                    </td>
                    <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0px 1px 1px 0px;">
                        ${secrecy("idNo",investment.investorIdNo)!"XXXXXX XXXXXXXX XXXX"}
                    </td>
                    <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0px;">${investment.amount?string("currency")}</td>
                    <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0px;">${investment.createDate?string("yyyy年MM月dd日")!"XXXX年XX月XX日"}</td>
                    <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0px;">${(investment.planIncome)?string("currency")}</td>
                </tr>
			[#--[/#if]--]
		[/#list]
	[#else]
        <tr>
            <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0px 1px 1px;">XXXX</td>
            <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">XXXX</td>
            <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">XXXX</td>
            <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">￥X,XXX.XX</td>
            <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">XXXX年XX月XX日</td>
            <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">￥X,XXX.XX</td>
        </tr>
	[/#if]
</table>
</body>
</html>
[/@compress]