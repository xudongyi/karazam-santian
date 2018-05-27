[@compress single_line = !systemDevelopment]
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>${setting.basic.siteName}-借款协议</title>
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

	<h1 align="center">华善金融借款协议</h1>
	<p style="text-align:right;">协议编号：<span>HSJR-${((investment.createDate)?string("yyyyMMdd"))!"XXXXXXXX"}-${(borrowing.id)!"XXXX"}[#---${(recovery.id)!"XXXX"}--]</span></p>
	[#--<p>签约日期：<span>${(recovery.createDate?string("yyyy年MM月dd日"))!"XXXX年XX月XX日"}</span></p>--]
	<br/>
	[#--<p>协议三方定义：</p>--]
	[#--<p>甲方（投资人）：<span>${(investment.investorRealName)!"XXX"}</span></p>
		[#if investment.investorCrop?? && investment.investorCrop]
        <p>营业执照 &nbsp;：<span></span>${(investment.investorCorporationIdCard)!"XXXXXX XXXXXXXX XXXX"}</p>
		[#else]
        <p>身份证号 &nbsp;：<span></span>${(investment.investorIdNo)!"XXXXXX XXXXXXXX XXXX"}</p>
		[/#if]--]
		[#if investment.investorCrop?? && investment.investorCrop]
			<p>甲方（投资人）：<span>${(investment.investorCorporationName)!"XXX"}</span></p>
			<p>营业执照 &nbsp;：<span></span>${(investment.investorCorporationIdCard)!"XXXXXX XXXXXXXX XXXX"}</p>
		[#else]
			<p>甲方（投资人）：<span>${(investment.investorRealName)!"XXX"}</span></p>
			<p>身份证号 &nbsp;：<span></span>${(investment.investorIdNo)!"XXXXXX XXXXXXXX XXXX"}</p>
		[/#if]
	<br/>
	<br/>
		[#if borrower.corp?? && borrower.corp]
			<p>乙方（借款人）：<span>${(corporation.corpName)!"XXX"}</span></p>
			<p>营业执照 &nbsp;：<span></span>${(corporation.corpLicenseNo)!"XXXXXX XXXXXXXX XXXX"}</p>
		[#else]
			<p>乙方（借款人）：<span>${(bUserInfo.realName)!"XXX"}</span></p>
			<p>身份证号 &nbsp;：<span></span>${(bUserInfo.idNo)!"XXXXXX XXXXXXXX XXXX"}</p>
		[/#if]
	<br/>
	<br/>
	<p>丙方（服务方）：<span>上海${setting.basic.siteName}</span>信息服务有限公司</p>
	<br/>
	
	<h4>鉴于:</h4>
	<p class="p" style="text-indent:2em;">甲方、乙方双方均为具有完全民事权利能力和民事行为能力，能够独立承担民事责任的自然人或系依法成立的、能够独立承担法律责任的法人组织，且甲方、乙方双方均为丙方运营管理的华善金融网站（www.huashanjinrong.com，下称华善金融平台）注册用户；</p>
	<p class="p" style="text-indent:2em;">丙方是依法成立且有效存续的有限责任公司，运营管理华善金融平台，为甲方、乙方的交易提供信息服务。</p>
	<p class="p" style="text-indent:2em;">本协议项下，甲方、乙方、丙方合称为各方。</p>
	<p class="p" style="text-indent:2em;">为此，经各方协商一致，于${(investment.createDate?string("yyyy年MM月dd日"))!"XXXX年XX月XX日"}签订如下协议，以共同遵照履行：</p>
	<h4>第一条借款基本信息及费用</h4>
	<p class="p" style="text-indent:2em;">1.1 借款明细情况：</p>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" style="font-size: 12px; line-height: 36px;">
	    <tr>
	        <td align="right" width="150px" style="border: 1px solid #ccc; padding-right: 10px; border-width: 1px 1px 0px 1px;">项目名称：</td>
	        <td align="left" width="680px" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 0px 0px;"><label>${(borrowing.title)!"XXXX"}</label></td>
	    </tr>
	    <tr>
	        <td align="right" width="150px" style="border: 1px solid #ccc; padding-right: 10px; border-width: 1px 1px 0px 1px;">编号：</td>
	        <td align="left" width="680px" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 0px 0px;"><label>${(borrowing.id)!"XXXX"}</label></td>
	    </tr>
	    <tr>
	        <td align="right" width="150px" style="border: 1px solid #ccc; padding-right: 10px;">借款用途：</td>
	        <td align="left" width="680px" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;"><label>${(borrowing.purpose)!"XXXX"}</label></td>
	    </tr>
	    <tr>
	        <td align="right" style="border-color: #ccc; border-style: solid; padding-right: 10px; border-width: 0px 1px 1px;">借款金额（人民币）：</td>
	        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${(investment.amount?string("currency"))!"￥XXXX.XX"}&nbsp;&nbsp;大写:${numToRMB(investment.amount)!"￥XXXX.XX"}</td>
	    </tr>
	    <tr>
	        <td align="right" style="border-color: #ccc; border-style: solid; padding-right: 10px; border-width: 0px 1px 1px;">还款本息（人民币）：</td>
	        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">
	        	[#--[#if repayment.state == "repaid"]
					${(repayment.paidAmount)?string("currency")!"￥XXXX.XX"}&nbsp;&nbsp;大写:${numToRMB(repayment.paidAmount)!"￥XXXX.XX"}
				[#else]
					${countAmount?string("currency")!"￥XXXX.XX"}&nbsp;&nbsp;大写:${numToRMB(countAmount)!"￥XXXX.XX"}
				[/#if]--]
				${repayAmount?string("currency")!"￥XXXX.XX"}&nbsp;&nbsp;大写:${numToRMB(repayAmount)!"￥XXXX.XX"}
	        </td>
	    </tr>
	    <tr>
	        <td align="right" style="border-color: #ccc; border-style: solid; padding-right: 10px; border-width: 0px 1px 1px;">借款利率（年利率）：</td>
	        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${(borrowing.realInterestRate)!"XX.XX"}%/年</td>
	    </tr>
      [#if borrowing.periodUnit??]
	    <tr>
	        <td align="right" style="border-color: #ccc; border-style: solid; padding-right: 10px; border-width: 0px 1px 1px;">还款期数（${borrowing.periodUnitDes}[#--${message("PeriodUnit." + borrowing.periodUnit)}--]）：</td>
            <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${(borrowing.period)!"XX"}${borrowing.periodUnitDes}</td>
	    </tr>
		[#else]
	    <tr>
	        <td align="right" style="border-color: #ccc; border-style: solid; padding-right: 10px; border-width: 0px 1px 1px;">还款期数（月）：</td>
	        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">XX月</td>
	    </tr>
	    [/#if]
	    <tr>
	        <td align="right" style="border-color: #ccc; border-style: solid; padding-right: 10px; border-width: 0px 1px 1px;">还款方式：</td>
	        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">
	        	[#if borrowing != null]
				${borrowing.repaymentMethodDes}
	        	[#else]
	        		XXXX
	        	[/#if]
	        </td>
	    </tr>
        <tr>
            <td align="right" style="border-color: #ccc; border-style: solid; padding-right: 10px; border-width: 0px 1px 1px;">计息开始日期：</td>
            <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${(investment.createDate?string("yyyy年MM月dd日"))!"XXXX年XX月XX日"}</td>
        </tr>
        <tr>
            <td align="right" style="border-color: #ccc; border-style: solid; padding-right: 10px; border-width: 0px 1px 1px;">借款到期日期：</td>
            <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${(repaymentEndDate?string("yyyy年MM月dd日"))!"XXXX年XX月XX日"}</td>
        </tr>
	</table>
	<br/>
	<p class="p" style="text-indent:5em;">还款计划：</p>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" style="font-size: 12px; line-height: 36px;">
        <tr>
            <td align="right" style="border: 1px solid #ccc; padding-right: 10px;">还款期数</td>
            <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;">还款时间</td>
            <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;">还款利率（年利率）</td>
            <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;">还款本金（人民币）</td>
            <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;">还款利息（人民币）</td>
            <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;">还款本息（人民币）</td>
        </tr>
		[#if repaymentPlans != null && repaymentPlans?size gt 0]
			[#list repaymentPlans as repaymentPlan]
                <tr>
                    <td align="right" style="border-color: #ccc; border-style: solid; padding-right: 10px; border-width: 0px 1px 1px;">第${repaymentPlan.repaymentRecordPeriod}期/共${borrowing.period}期</td>
                    <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">[#if repaymentPlan.paidDate??]（实际）${repaymentPlan.paidDate?string("yyyy年MM月dd日")}[#else]${repaymentPlan.repaymentRecord.payDate?string("yyyy年MM月dd日")}[/#if]</td>
                    <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${borrowing.realInterestRate}%/年</td>
                    <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${repaymentPlan.repaymentRecord.capital?string("currency")}</td>
                    <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${repaymentPlan.repaymentRecord.interest?string("currency")}</td>
                    <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">[#--${repaymentPlan.recoveryAmount?string("currency")}--]
						[#if repaymentPlan.state == "repaid"]（实际）${repaymentPlan.capitalInterestOverdueAhead?string("currency")}[#else]${repaymentPlan.capitalInterest?string("currency")}[/#if]
                    </td>
                </tr>
			[/#list]
		[#else]
            <tr>
                <td align="right" style="border-color: #ccc; border-style: solid; padding-right: 10px; border-width: 0px 1px 1px;">第XX期/共XX期</td>
                <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">XXXX-XX-XX</td>
                <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">XX.XX%/年</td>
                <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">￥XXXX.XX</td>
                <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">￥XXXX.XX</td>
                <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">￥XXXX.XX</td>
            </tr>
		[/#if]
	    [#--[#if recoveries != null && recoveries?size gt 0]
	        [#list repaymentPlans as repaymentPlan]
	        	<tr>
	            	<td align="right" style="border-color: #ccc; border-style: solid; padding-right: 10px; border-width: 0px 1px 1px;">第${repaymentPlan.period}期/共${repayment.period}期</td>
	            	<td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">[#if repaymentPlan.paidDate??]（实际）${repaymentPlan.paidDate?string("yyyy年MM月dd日")}[#else]${repaymentPlan.date?string("yyyy年MM月dd日")}[/#if]</td>
	                <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${borrowing.interestRate}%/年</td>
	                <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${repaymentPlan.capital?string("currency")}</td>
	                <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${repaymentPlan.interest?string("currency")}</td>
	                <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">[#if repaymentPlan.state == "repaid"]${repaymentPlan.paidAmount?string("currency")}[#else]${repaymentPlan.capitalInterest?string("currency")}[/#if]</td>
	            </tr>
	        [/#list]
        [#else]
		    <tr>
		        <td align="right" style="border-color: #ccc; border-style: solid; padding-right: 10px; border-width: 0px 1px 1px;">第XX期/共XX期</td>
		        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">XXXX-XX-XX</td>
		        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">XX.XX%/年</td>
		        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">￥XXXX.XX</td>
		        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">￥XXXX.XX</td>
		        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">￥XXXX.XX</td>
		    </tr>
        [/#if]--]
	</table>
	<h4>投资人列表：（具体以在线签署时载明为准）</h4>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" style="font-size: 12px; line-height: 36px;">
	    <tr>
	        <td align="left" style="border: 1px solid #ccc; padding-left: 10px;">投资人</td>
	        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;">身份证号码</td>
	        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;">出借金额（人民币）</td>
	        [#if borrowing.periodUnit??]
	        	<td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;">借款期限（${borrowing.periodUnitDes}）</td>
	        [#else]
	        	<td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;">借款期限（月）</td>
	        [/#if]
	        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;">借款利率（年利率）</td>
	        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;">总还款本息（人民币）</td>
	    </tr>
	    [#if investments != null && investments?size gt 0]
			[#assign investmentInvestor = investment.investor]
	        [#list investments as invest]
	        	<tr>
					[#if investmentInvestor == invest.investor]
						[#if invest.investorCorp?? && invest.investorCorp]
                            <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0px 1px 1px;">${invest.investorCorporationName!"XXX"}</td>
                            <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${invest.investorCorporationIdCard!"XXXXXX XXXXXXXX XXXX"}</td>
						[#else]
                            <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0px 1px 1px;">${invest.investorRealName!"XXX"}</td>
                            <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${invest.investorIdNo!"XXXXXX XXXXXXXX XXXX"}</td>
						[/#if]
					[#else]
						[#if invest.investorCorp?? && invest.investorCorp]
                            <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0px 1px 1px;">${secrecy("fullName",invest.investorCorporationName)}</td>
                            <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${secrecy("fullName",invest.investorCorporationIdCard)!"XXXXXX XXXXXXXX XXXX"}</td>
						[#else]
                            <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0px 1px 1px;">${secrecy("fullName",invest.investorRealName)}</td>
                            <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${secrecy("idNo",invest.investorIdNo)!"XXXXXX XXXXXXXX XXXX"}</td>
						[/#if]
					[/#if]
                    <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${investment.amount?string("currency")}</td>
                    <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${(borrowing.period)!"XX"}${borrowing.periodUnitDes}</td>
                    <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${(borrowing.realInterestRate)!"XX.XX"}%</td>
                    <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${(investment.planIncome)?string("currency")}</td>
	            </tr>
	        [/#list]
	    [#else]
        	<tr>
            	<td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0px 1px 1px;">XXXX</td>
                <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">XXXXXXXX</td>
                <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">XX个月</td>
                <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">XX.XX%/年</td>
                <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">XXXX年XX月XX日</td>
                <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">XXXX年XX月XX日</td>
            </tr>
	    [/#if]
	</table>
	[#--<p>贷款人 ：<span>${(borrower.username)!borrower.corpName}</span><label>（${(borrower.idNo)!"XXXXXX XXXXXXXX XXXX"}）</label></p>
	<p>管理方：<span>${setting.basic.siteName}</span></p>--]
	<p class="p" style="text-indent:2em;">甲方应当保证其出借资金来源合法，如第三方对资金归属、支配权、合法性等问题提出异议，给本协议其他方造成损失的，甲方应当赔偿损失。</p>
	<p class="p" style="text-indent:2em;">1.2 借款用途</p>
	<p class="p" style="text-indent:2em;">本协议项下的借款，借款用途为：${(borrowing.purpose)!"XXXXX（例如：扩大生产经营）"}。乙方应当按照借款用途使用款项，不得从事非法活动，如从事非法活动，乙方应承担从事非法活动造成的一切法律后果，给本协议的其他方造成损失的，应当赔偿其损失。未经甲方、丙方事先书面同意，乙方不得改变借款用途。</p>
	<p class="p" style="text-indent:2em;">1.3 借款币种、金额和放款</p>
	<p class="p" style="text-indent:2em;">1.3.1 本协议项下的借款币种为人民币。乙方借款人借款金额为（${(investment.amount?string("currency"))!"￥XXXX.XX"}）借款期限：${(investment.createDate?string("yyyy年MM月dd日"))!"XXXX年XX月XX日"}至${(repaymentEndDate?string("yyyy年MM月dd日"))!"XXXX年XX月XX日"}。</p>
	<p class="p" style="text-indent:2em;">1.3.2 甲方决定出借时，应自行登录华善金融平台账号并亲自操作从自己账户内支付相应款项，此笔款项在甲方点击投标后至该标最终审核通过之前，暂时由丙方在银行开设的资金存管专户予以冻结。待审核通过后且符合放款条件时，丙方将给存管银行发出放款指令，由存管银行将出借款项解冻并从存管账户划入乙方账户。该款项进入乙方账户后，乙方不支取款项不影响协议成立和协议效力。</p>
	<p class="p" style="text-indent:2em;">1.3.3 在以下条件完全得到满足的情况下，丙方将给存管银行发出放款指令（即放款）：</p>
	<p class="p" style="text-indent:2em;">丙方要求乙方提供的文件、资料，乙方已全部提供，并且其所载明的情况没有发生变化，该等文件、资料持续有效，或者乙方已就发生的变化作出令丙方满意的解释和说明；本协议项下借款本金自借款项目满标计息时视为借款成功，本电子协议自动生成，各方当事人可自行登录华善金融平台账号查看、下载、打印。由于协议涉及所有甲方信息，为了保护其隐私，各方当事人所查看的本电子协议，华善金融平台将屏蔽掉其他人的部分或全部信息，甲方，乙方表示认可并不持异议。乙方应从借款成功日起计付利息。如投标时间内，乙方的需求资金在规定期限内没有筹集满，华善金融平台宣布流标则协议不生效，在此期间不产生利息。</p>
	<p class="p" style="text-indent:2em;">1.4 费用</p>
	<p class="p" style="text-indent:2em;">1.4.1 平台服务费</p>
	<p class="p" style="text-indent:2em;">乙方应当在每月偿还利息时向丙方支付平台服务费。</p>
	<p class="p" style="text-indent:2em;">1.4.2 征信服务费</p>
	<p class="p" style="text-indent:2em;">丙方向乙方收取征信服务费。</p>
	<p class="p" style="text-indent:2em;">1.4.3 借款保证金</p>
	<p class="p" style="text-indent:2em;">乙方应当向丙方支付借款保证金。丙方在放款时通过第三方支付直接从乙方借款中一次性扣除借款保证金（相当于一个月的利息和平台服务费之和）。如乙方逾期还款，丙方动用该借款保证金偿还甲方借款及丙方的平台服务费；如乙方未逾期还款，该借款保证金可以冲抵到期借款本金。如该借款保证金不足予冲抵应付费用/借款利息/本金，冲抵顺序为：先冲应付费用、再冲利息，最后冲本金。</p>
	<p class="p" style="text-indent:2em;">1.4.4 其他费用</p>
	<p class="p" style="text-indent:2em;">甲、乙双方应根据华善金融平台公布的网站资费向丙方支付相关费用（包括但不限于充值手续费、提现手续费、债权转让费等）。</p>
	<h4>第二条 借款利率和计息方法</h4>
	<p class="p" style="text-indent:2em;">2.1 乙方应按照本协议第一条的约定，按时、足额向甲方支付借款本息。</p>
	<p class="p" style="text-indent:2em;">2.2 各方一致同意在本协议借款期限内，无论国家相关政策是否发生变化，本协议项下借款利率均不作调整。</p>
	<p class="p" style="text-indent:2em;">2.3 本协议项下的借款利率按年利率表示。月利率＝年利率/12,日利率＝年利率/360。利息＝本金×利率×期限，乙方实际应还利息按照投资人收益四舍五入精确到0.01元后计算。</p>
	<h4>第三条 还款方式</h4>
	<p class="p" style="text-indent:2em;">3.1 经协商一致，甲方同意乙方按下列方式的约定偿还借款本息。具体还款方式以本协议第一条确定的为准。</p>
	<p class="p" style="text-indent:2em;">A. 按月付息，到期还本。即借款人按月付息，借款到期后一次性偿还本金。借款人每月应还利息按下列公式计算：</p>
	<h4 style="text-align:center;">每月应还利息＝本金×月利率</h4>
	<p class="p" style="text-indent:2em;">B. 按季付息，到期还本。即借款人按季付息，借款到期后一次性偿还本金。借款人每季应还利息按下列公式计算：</p>
	<h4 style="text-align:center;">每季应还利息＝本金×月利率×3</h4>
	<p class="p" style="text-indent:2em;">C．按月等额还款。即借款人按月等额还本付息。借款人每月应还本息按下列公式计算：</p>
	<h4 style="text-align:center;">月利率×（1+月利率）^N</h4>
	<h4 style="text-align:center;">每月还本付息额= ------------------------------------ × 借款本金</h4>
	<h4 style="text-align:center;">（1+月利率）^N-1</h4>
	<p class="p" style="text-indent:2em;">备注：</p>
	<p class="p" style="text-indent:2em;">月利率=年利率/12</p>
	<p class="p" style="text-indent:2em;">N=还款期数</p>
	<p class="p" style="text-indent:2em;">D.到期一次性还本付息。即借款到期后，一次性归还借款本息。到期一次性归还本息按下列公式计算：</p>
	<h4 style="text-align:center;">到期一次性归还本息（月标）＝本金＋本金×月利率×借款月数</h4>
	<h4 style="text-align:center;">到期一次性归还本息（天标）＝本金＋本金×年利率/360×借款天数</h4>
	<h4>第四条 还款日</h4>
	<p class="p" style="text-indent:2em;">4.1 采用按月等额还款方式还款的，还款日为借款成功日的对应日，无对应日的，当月最后一日为还款日。末次还款日为借款到期日。乙方应从借款发放的次月开始还款。</p>
	<p class="p" style="text-indent:2em;">4.2 采用按月或按季付息，到期还本方式还款的，还款日为借款成功日的对应日，无对应日的，当月最后一日为还款日。末次还款日为借款到期日。乙方应从借款成功的次月/次季开始还款。</p>
	<p class="p" style="text-indent:2em;">4.3 采用到期一次性还本付息的，借款到期日为还款日，乙方应一次性偿付借款本金和借款利息。</p>
	<p class="p" style="text-indent:2em;">4.4 乙方在偿还最后一期借款本金时，应利随本清。乙方应于每期还款日前24小时在丙方在银行开设的存管账户上备足当期应付之本金及其他应付款项，还款后相应资金将通过中金支付从丙方在银行开设的存管账户中划转至甲方账户。</p>
	<h4>第五条 提前还款</h4>
	<p class="p" style="text-indent:2em;">5.1 乙方提前还款，应当一次性偿还全部借款本金，并支付提前还款所在当期的借款利息和平台服务费。</p>
	<p class="p" style="text-indent:2em;">5.2 如乙方提前还款，应先结清拖欠的费用/罚息/逾期利息/借款本金等相关款项。</p>
	<p class="p" style="text-indent:2em;">5.3 丙方已经收取的费用均不予退还。</p>
	<h4>第六条 债权转让</h4>
	<p class="p" style="text-indent:2em;">6.1 经各方同意，甲方可通过华善金融平台将本协议项下的未发放利息的借款债权转让给第三方，无需另行向乙方通知债权转让情况。甲方转让借款债权，本协议项下其他条款不受影响，乙方需向借款债权受让人偿还剩余借款，并向华善金融平台支付平台服务费，乙方不得以未接到债权转让通知为由拒绝还款。</p>
	<h4>第七条 违约责任</h4>
	<p class="p" style="text-indent:2em;">7.1 协议各方均应严格履行协议义务，非经各方协商一致或依照本协议约定，任何一方不得解除本协议。</p>
	<p class="p" style="text-indent:2em;">7.2 任何一方违约，违约方应承担因违约使得其他各方产生的费用和损失，包括但不限于调查费、起诉费、律师费等。如违约方为乙方，甲方授权丙方代表甲方立即解除本协议，并要求乙方偿还剩余借款本金、利息、征信服务费、罚息、违约金、以及其他费用，以及支付拖欠的费用、利息等款项。乙方已支付的中介服务费用、平台服务费、业务审查费以及征信服务费等费用均不予退还。</p>
	<p class="p" style="text-indent:2em;">7.3 乙方在还款日（不得迟于24:00）未足额还款，则视为逾期还款。如丙方未垫付还款，乙方自应还款次日起按照下述计算方式向甲方支付逾期罚息。如丙方垫付还款，乙方应按下述计算方式向丙方支付罚息：</p>
	<p class="p" style="text-indent:2em;">罚息=逾期借款本息×罚息利率×逾期天数</p>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" style="font-size: 12px; line-height: 36px;">
		<tr>
	    	<td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 1px;">逾期天数</td>
	    	<td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0;">1-7天</td>
	        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0;">8天及以上</td>
        </tr>
        <tr>
	    	<td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0px 1px 1px;">罚息利率</td>
	    	<td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">0.1%</td>
	        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">0.2%</td>
        </tr>
    </table>
	<p class="p" style="text-indent:2em;">7.4 如乙方逾期还款，无论丙方是否代乙方还借款本金、利息，乙方应按照下述计算方式向丙方支付逾期管理费。</p>
	<p class="p" style="text-indent:2em;">逾期管理费=逾期借款本息×逾期管理费率×逾期天数</p>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" style="font-size: 12px; line-height: 36px;">
		<tr>
	    	<td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 1px;">逾期天数</td>
	    	<td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0;">1-30天</td>
	        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0;">31天及以上</td>
        </tr>
        <tr>
	    	<td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0px 1px 1px;">逾期管理费率</td>
	    	<td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">0.3%</td>
	        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">0.5%</td>
        </tr>
    </table>
	<p class="p" style="text-indent:2em;">乙方偿还全部借款之前，丙方有权根据华善金融平台规则调整罚息利率以及逾期管理费率。</p>
	<p class="p" style="text-indent:2em;">7.5 乙方还款按照如下顺序清偿；当乙方存在多期逾期时，按到期先后顺序偿还：</p>
	<p class="p" style="text-indent:2em;">7.5.1 所应付费用；</p>
	<p class="p" style="text-indent:2em;">7.5.2 罚息；</p>
	<p class="p" style="text-indent:2em;">7.5.3 拖欠的利息；</p>
	<p class="p" style="text-indent:2em;">7.5.4 正常的利息；</p>
	<p class="p" style="text-indent:2em;">7.5.5借款本金。</p>
	<p class="p" style="text-indent:2em;">7.6 乙方逾期还款，丙方上门催收提醒，乙方应当按照人民币2000元/次支付上门费用。此外，乙方应当支付上门催收提醒服务的差旅费（包括但不限于交通费、食宿费等）。</p>
	<p class="p" style="text-indent:2em;">7.7 乙方逾期还款、或者逃避、拒绝沟通或者拒绝承认欠款等情形，丙方有权将乙方违约失信的相关信息及乙方其他信息披露给媒体、用人单位、公安机关、检查机关、法律机关、人民银行、P2P行业个人征信系统、核心企业。</p>
	<p class="p" style="text-indent:2em;">7.8 乙方提供虚假资料或隐瞒重要事实，甲方授权丙方解除本协议，并授权丙方代表甲方收取乙方提前归还剩余借款，乙方应在解除协议后五日内一次性支付全部剩余款项（包括但不限于借款本金、利息、罚息、征信服务费、平台服务费、违约金及其他费用）。乙方已支付的费用不予退还。</p>
	<p class="p" style="text-indent:2em;">7.9 乙方提供虚假材料，乙方应当按照借款金额10%向丙方支付违约金。</p>
	<p class="p" style="text-indent:2em;">7.10 借款协议中的全部甲方与乙方之间的借款均相互独立。乙方逾期还款，任何一个甲方有权单独向乙方追索或者提起诉讼。如乙方逾期支付丙方款项的，丙方亦可单独向乙方追索或者提前诉讼。</p>
	<p class="p" style="text-indent:2em;">7.11 乙方在本协议借款后向第三方借款或者为第三方提供担保，须事先以书面形式告知丙方，否则甲方有权立即终止本协议，并要求乙方立即归还剩余未到期借款本息，并支付丙方剩余的平台服务费。甲方授权丙方代为办理提前终止协议手续或者提前催收工作。</p>
	<p class="p" style="text-indent:2em;">7.12 若乙方违约或根据甲方、丙方的合理判断乙方可能发生违约事件，经丙方同意，甲方委托丙方采取下列一项或几项措施：</p>
	<p class="p" style="text-indent:2em;">7.12.1 立即暂停、取消发放全部或部分借款；</p>
	<p class="p" style="text-indent:2em;">7.12.2 提前收取借款。乙方应在华善金融平台通知（包括但不限于口头、电子邮件、短信任何一种形式）后三日内一次性清偿付款项（包括但不限于借款本金、利息、罚息、征信服务费、平台服务费及其他费用）。乙方已支付的其他费用不予退还；</p>
	<p class="p" style="text-indent:2em;">7.12.3 解除本协议；</p>
	<p class="p" style="text-indent:2em;">7.12.4 采取有效的救济措施。</p>
	<p class="p" style="text-indent:2em;">7.13 在乙方偿付全部款项前（本协议项下借款本金、利息、罚息、乙方应支付的其他款项），罚息及逾期管理费不停止计算。</p>
	<p class="p" style="text-indent:2em;">7.14 丙方垫付还款后，甲方应积极协助丙方向乙方追偿（包括但不限于签署、提供各种书面文件、相应授权书、提供书面材料等）。若因甲方原因导致丙方不能追偿（包括但不限于正常催收、诉讼、仲裁等），甲方应当向丙方承担赔偿责任或返回垫付款项。</p>
	<h4>第八条 陈述、声明和保证</h4>
	<p class="p" style="text-indent:2em;">8.1 甲方陈述、声明和保证</p>
	<p class="p" style="text-indent:2em;">8.1.1 甲方系具有完全民事权利能力和完全民事行为能力，能够独立承担民事责任，有权签订并履行本协议。</p>
	<p class="p" style="text-indent:2em;">8.1.2 甲方保证在本协议有效期内保持华善金融平台注册用户身份。</p>
	<p class="p" style="text-indent:2em;">8.1.3 甲方向乙方提供的出借资金来源合法，如果第三人对资金归属、合法性等问题提出异议，甲方自行承担责任。</p>
	<p class="p" style="text-indent:2em;">8.1.4 甲方保证不利用华善金融平台进行信用卡套现、洗钱或其他违法行为。</p>
	<p class="p" style="text-indent:2em;">8.1.5 甲方保证其向丙方提供的所有证件、资料均合法、真实、准确、完整和有效。若上述材料发生变化时，甲方保证及时登录会员中心更新。</p>
	<p class="p" style="text-indent:2em;">8.1.6 甲方已详细阅读了华善金融平台所发布的交易规则和内容，充分知悉和了解本协议项下各方的权利和义务，对融资项目内容全部认可并接受，在本协议项下的全部意思表示真实。</p>
	<p class="p" style="text-indent:2em;">8.1.7 甲方清楚可能面临的出借风险。甲方在此确认，乙方或者担保人可能因为经营不善、丧失商业信誉等因素，存在着部分或全部借款不能回收的风险，甲方已充分了解并认识到了本次借款的特殊性、风险的不确定性以及回收该等借款可能面临的困难，经独立慎重判断后仍做出出借决定，自愿签署本协议。</p>
	<p class="p" style="text-indent:2em;">8.1.8 甲方若成功出借，保证采取合法的手段和方式向乙方或者担保人主张权利。并且保证不得因未实现预期投资收益或者投资受损而对丙方提出任何诉讼、仲裁或索赔，丙方不承担任何责任。</p>
	<p class="p" style="text-indent:2em;">8.1.9 在乙方逾期还款时，丙方有权代表投资人进行催收（包括但不限于短信、电话、上门催收、发律师函、将乙方逾期信息通报人民银行、P2P行业信用征信系统或核心企业、申请仲裁、诉讼等方式）。</p>
	<p class="p" style="text-indent:2em;">8.1.10 甲方不可撤销授权丙方在还款日（不得迟于24:00）或之前代为收取乙方的还款（包括但不限于乙方正常还款、提前还款情形）。丙方按约定将代为收取款项交付给甲方。</p>
	<p class="p" style="text-indent:2em;">8.1.11 甲方同意并确认，乙方逾期还款后，丙方垫付还款。丙方垫付后，甲方有义务配合丙方追偿，否则，丙方有权延迟或不垫付后续逾期款项，并且有权要求甲方返还垫付款项。</p>
	<p class="p" style="text-indent:2em;">8.1.12 甲方承诺，甲方因授权和/或委托丙方根据本协议所采取的全部行动和措施的法律后果均归属于甲方本人。</p>
	<p class="p" style="text-indent:2em;">8.2 乙方陈述、声明和保证</p>
	<p class="p" style="text-indent:2em;">8.2.1 乙方系依法成立的、能够独立承担法律责任的法人组织或具有完全民事权利能力和完全民事行为能力，能够独立承担民事责任的自然人。</p>
	<p class="p" style="text-indent:2em;">8.2.2 乙方保证在本协议有效期内保持华善金融平台注册用户身份。</p>
	<p class="p" style="text-indent:2em;">8.2.3 乙方已仔细阅读并完全理解和接受本协议的内容，乙方签署和履行本协议是自愿的，其在本协议项下的全部意思表示真实。</p>
	<p class="p" style="text-indent:2em;">8.2.4 乙方为了进行本协议项下的交易及签署本协议向丙方与担保人提供的所有文件、资料和凭证等都是真实、完整、准确和有效的，乙方所提交的财务报表真实地反映了该财务报表在出具时乙方的财务状况。若上述文件、资料发生变化时，甲方保证及时登录会员中心更新。</p>
	<p class="p" style="text-indent:2em;">8.2.5 乙方应按照本协议约定，在还款日（不得迟于24:00）或之前足额清偿借款本金、利息、罚息、中介服务费、借款保证金、征信服务费及其他费用，否则，视为乙方逾期还款。</p>
	<p class="p" style="text-indent:2em;">8.2.6 乙方承诺所借款项不用于任何违法活动，仅能用于乙方注明的借款用途。乙方应确保提供信息和资料的真实性，不提供虚假信息或隐瞒重要事实。否则，一经发现，甲方授权丙方以丙方名义提前收回借款。乙方应当立即偿还全部借款本金和利息、平台服务费、罚息、逾期管理费等款项。</p>
	<p class="p" style="text-indent:2em;">8.2.7 乙方承诺并保证在资金借入中产生的相关税费，由乙方自行向其主管税务机关申报、缴纳，丙方不负责相关事务处理。</p>
	<p class="p" style="text-indent:2em;">8.2.8 乙方承诺，乙方因授权和/或委托丙方根据本协议所采取的全部行动和措施的法律后果均归属于乙方本人。</p>
	<p class="p" style="text-indent:2em;">8.2.9 乙方承诺配合丙方进行借款支付管理，保证自觉接受并积极配合丙方或担保人对其有关生产、经营及财务情况的调查、了解、监督及贷后管理；并按丙方的要求实时提供《资产负债表》、《损益表》等财务报表或其他反映乙方资信情况的资料。</p>
	<p class="p" style="text-indent:2em;">8.2.10 乙方保证在清偿其在本协议项下的全部债务之前如进行对外投资、实质性增加债务融资，以及用于反担保的经营组织进行合并、分立、减资、股权转让、资产转让、申请停业整顿、申请解散、申请破产等足以引起本协议之债权、债务关系变化或者可能足以影响甲方权益的行动时，应提前三十个工作日以书面形式通知丙方，同时落实债务清偿责任或者提前清偿责任，未经丙方书面同意，不得进行上述行动。</p>
	<p class="p" style="text-indent:2em;">8.2.11 乙方保证在本协议有效期间内，未经丙方书面同意不得为其他企业法人、其他组织或个人承担足以影响乙方偿还本协议项下借款能力的债务，或提供足以影响乙方偿还本协议项下借款能力的保证担保，或以乙方资产、权益设定足以影响乙方偿还本协议项下借款能力的抵押或质押。</p>
	<p class="p" style="text-indent:2em;">8.2.12 乙方如出现下列事项对其履行本协议项下还款义务构成威胁的任何事件时，乙方应当立即书面通知丙方，并积极配合丙方的要求落实好本协议项下的借款及其他一切应付款项按期足额偿还的保障措施：</p>
	<p class="p" style="text-indent:2em;">A.乙方在与银行等金融机构签订的借款合同、授信额度合同及其项下授信合同、担保合同或与其他任何债权人所签订的融资合同项下发生任何违约事项；</p>
	<p class="p" style="text-indent:2em;">B.乙方发生违法或被索赔事件；</p>
	<p class="p" style="text-indent:2em;">C.乙方经营出现严重困难和财务状况发生恶化；</p>
	<p class="p" style="text-indent:2em;">D.在本协议项下的债务未清偿前，乙方已承担或将要承担重大债务或或有债务；</p>
	<p class="p" style="text-indent:2em;">E.乙方发生重大债权债务纠纷引起诉讼、仲裁等事件；</p>
	<p class="p" style="text-indent:2em;">F.其他可能影响乙方财务状况和偿债能力的情况。</p>
	<p class="p" style="text-indent:2em;">8.2.13 乙方应按照丙方平台的规则及本协议约定支付相关费用。</p>
	<p class="p" style="text-indent:2em;">8.3 平台陈述、声明和保证</p>
	<p class="p" style="text-indent:2em;">8.3.1 丙方（上海华善金融信息服务有限公司）是依法成立且有效存续的有限责任公司，运营管理华善金融平台（www.huashanjinrong.com），为甲、乙方的交易提供信息服务。</p>
	<p class="p" style="text-indent:2em;">8.3.2 丙方严格按照本协议的约定，恪尽职守，以诚实、信用、谨慎、有效管理的原则，为甲方、乙方提供服务，并据以收取相应的服务费用。</p>
	<p class="p" style="text-indent:2em;">8.3.3 在任何情形下，丙方不是本协议项下任何借款或债务的债务人或需要以其自有资产偿还本协议项下的任何借款或债务。中金支付亦不是本协议项下的担保人或需要以其自有资产承担本协议项下的担保责任。</p>
	<p class="p" style="text-indent:2em;">8.3.4 丙方积极协助各方办理各项信息对接及变更手续。</p>
	<p class="p" style="text-indent:2em;">8.3.5 妥善保存本协议及相关的全部资料以备查阅。对协议各方的个人信息、资产情况及其他服务相关事务的情况和资料依法保密；非因促进交易、一方违约，或相关权力部门要求（包括但不限于法院、仲裁机构、金融监管机构等），不得对外披露。</p>
	<p class="p" style="text-indent:2em;">8.3.6 丙方保留对可疑交易、非法交易、高风险交易或交易纠纷的独立判断和确定，并有权依法采取暂停交易、终止交易、向有关单位报告等必要处理措施或依有关单位合法指示行事的权利。</p>
	<p class="p" style="text-indent:2em;">8.3.7 乙方不可撤销授权丙方，通过第三方支付平台将其出借资金划扣给乙方，以及在还款日（不得迟于24:00）或之前代为收取乙方还款本金、利息、罚息、中介服务费、借款保证金、征信服务费，并通过华善金融平台支付给甲方。</p>
	<p class="p" style="text-indent:2em;">8.3.8 如乙方逾期偿还利息（包括不按时还款和不按约定的方式还款），甲方、乙方不可撤销授权丙方在次日起代为偿还借款利息。如乙方逾期偿还本金（包括不按时还款和不按照约定的方式还款）满25日，甲方、乙方不可撤销授权丙方在当日起从乙方在核心企业应收账款中扣除或以丙方风险备付金为限代为偿还借款本金。在丙方垫付之日起，丙方有权向乙方追偿垫付款项。</p>
	<p class="p" style="text-indent:2em;">8.3.9 乙方逾期偿还借款利息满15日，甲方不可撤销授权丙方提前收回借款，乙方应当在15日内通过丙方向甲方偿还借款本金以及至实际清偿之日期间的利息。丙方在乙方逾期偿还借款利息满30日后或者乙方逾期偿还借款本金满25日后有权要求担保人代偿或要求核心企业以乙方在核心企业的应收账款代偿，用于偿还借款本金以及拖欠的利息、罚息、逾期管理费、平台服务费、违约金等费用。在担保人清偿所有债务前，继续由丙方以风险备付金为限垫付借款本金或者借款利息。</p>
	<p class="p" style="text-indent:2em;">8.3.10 在乙方偿还全部借款本金及利息（含逾期后乙方自愿还款、以及逾期后由担保人或核心企业应收账款代偿）前，本协议约定的借款利息、罚息、平台服务费以及逾期管理费均不停止计算。</p>
	<p class="p" style="text-indent:2em;">8.3.11 甲方、乙方双方同意，丙方有权以丙方名义对乙方进行借款的违约提醒及催收工作（包括但不限于短信、电话、上门催收、发律师函、将乙方逾期信息通报人民银行、P2P行业信用征信系统或核心企业、申请仲裁、诉讼等方式）。</p>
	<p class="p" style="text-indent:2em;">8.3.12 甲方、乙方同意，担保人提供担保的或乙方以在核心企业应收账款对借款担保的，则乙方授权丙方或丙方指定的第三方作为应收账款的代持人。乙方逾期还款，由丙方或丙方指定的第三方代表有权根据本协议第八条8.3.9约定追偿，偿还乙方借款本金利息，以及丙方根据本协议有权收取的费用以及垫付还款金额。</p>
	<p class="p" style="text-indent:2em;">8.3.13 丙方有权按照华善金融平台规则及本协议的约定向甲方、乙方收取相关费用，不因甲方、乙方双方任何纠纷受影响。</p>
	<p class="p" style="text-indent:2em;">8.3.14 丙方接受甲方、乙方委托行为产生的法律后果，由相应委托方承担。如因乙方或甲方或其他方（包括但不限于技术问题）造成的延误或错误，丙方不承担任何责任。</p>
	<h4>第九条 其他</h4>
	<p class="p" style="text-indent:2em;">9.1 甲方、乙方均同意并确认，通过华善金融平台账户和其他银行账户的行为均通过第三方支付平台进行，所产生的法律效果及法律责任归属于甲方、乙方。同时，甲方、乙方委托丙方根据本协议采取的全部行动和措施的法律后果均归属于甲方、乙方，丙方不因此承担责任。</p>
	<p class="p" style="text-indent:2em;">9.2 协议各方应当对为签署和履行本协议的目的而了解到的对方有关其债务、财务、生产、经营资料及情况等进行保密，非因促进交易、一方违约，或相关权力部门要求（包括但不限于法院、仲裁机构、金融监管机构等），不得对外披露。</p>
	<p class="p" style="text-indent:2em;">9.3 乙方在本协议项下应付的所有款项应全额支付，不得作任何性质的冲抵、扣减或预提，亦不得同甲方所欠乙方的任何债务相抵销。如果任何法律要求乙方对其支付给甲方的任何款项进行扣减或预提，则乙方应向甲方支付一笔额外的款项，以保证甲方收到的金额相等于在不作此类扣减或预提时所应收到的款项。</p>
	<p class="p" style="text-indent:2em;">9.4 各方确认并同意，委托丙方对本协议项下的任何金额进行计算；在无明显错误的情况下，丙方对本协议项下任何数额的任何证明或确定，应作为该数额有关事项的终局证明。</p>
	<p class="p" style="text-indent:2em;">9.5 本协议及本协议所涉及的任何事项适用中国法律，并按照中国法律进行解释。各方在履行本协议过程中所发生的争议，首先应协商解决；协商无法达成一致的，各方一致同意将争议提交给丙方经营地人民法院予以裁决。</p>
	<p class="p" style="text-indent:2em;">9.6 除非法律另有规定，本协议任何条款的无效或不可执行，不影响其他条款的有效性和可执行性，也不影响整个协议的效力。</p>
	<p class="p" style="text-indent:2em;">9.7 本协议未尽事宜，由各方协商一致后另行签订补充协议。本协议的注解、附件、补充规定为本协议组成部分，与本协议具有同等法律效力。</p>
	<p class="p" style="text-indent:2em;">9.8 本协议采用电子文本形式制成，自乙方借款标满标计息之日起成立生效。</p>
</body>
</html>
[/@compress]