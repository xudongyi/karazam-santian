[@compress single_line = !systemDevelopment]
[#assign investor = (recovery.investor)! /]
[#assign borrowing = (recovery.borrowing)! /]
[#assign borrower = (borrowing.borrower)! /]
[#assign recoveries = (borrowing.recoveries)! /]
[#assign repayment = (recovery.repayment)! /]
[#assign repaymentPlans = (repayment.plans)! /]
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
	<div style="text-align:right;">协议编号：<span>HSJR-${((recovery.createDate)?string("yyyyMMdd"))!"XXXXXXXX"}-${(borrowing.id)!"XXXX"}[#---${(recovery.id)!"XXXX"}--]</span></div>
	[#--<div>签约日期：<span>${(recovery.createDate?string("yyyy年MM月dd日"))!"XXXX年XX月XX日"}</span></div>--]
	<br/>
	[#--<div>协议三方定义：</div>--]
	<div>出借人（投资人）：<span>${(investor.username)!"XXX"}</span></div>
	[#if investor.memberType =="enterprise"]
	<div>华善金融注册号 &nbsp;：<span></span>${(investor.corporationIdCard)!"XXXXXX XXXXXXXX XXXX"}</div>
	[#else]
	<div>华善金融注册号 &nbsp;：<span></span>${(investor.idNo)!"XXXXXX XXXXXXXX XXXX"}</div>
	[/#if]
	<div>借款人（融资人）：<span>${(borrower.username)!"XXX"}</span></div>
	[#if borrower.memberType =="enterprise"]
	<div>华善金融注册号 &nbsp;：<span></span>${(borrower.corporationIdCard)!"XXXXXX XXXXXXXX XXXX"}</div>
	[#else]
	<div>华善金融注册号 &nbsp;：<span></span>${(borrower.idNo)!"XXXXXX XXXXXXXX XXXX"}</div>
	[/#if]
	<div>平台（华善金融）：<span>上海${setting.basic.siteName}</span>有限公司</div>
	<br/>
	<p class="p" style="text-indent:2em;">根据《中华人民共和国合同法》及相关法律法规的规定，上述三方遵循诚实信用、平等自愿、互惠互利的原则，就出借人通过华善金融下网络投融资交易平台（即www.huashanjinrong.com网站）向借款人出借资金，平台为借款人提供担保及贷前、贷中、贷后管理等相关服务，为交易各方提供居间服务的合作事宜，经协商一致，达成如下合同，以资共同信守。</p>
	
	<h3>合同说明：</h3>
	<p class="p" style="text-indent:5em;">1、出借人账户，是指出借人（投资人）通过在“华善金融”网络投融资交易平台注册，并在中金支付平台开通的个人资金专属账号。</p>
	<p class="p" style="text-indent:5em;">2、借款人托管账户，是指借款人（融资人）通过“华善金融”网络投融资交易平台注册，并在中金支付开通的资金账户。</p>
	<p class="p" style="text-indent:5em;">3、居间平台服务商，指上海华善金融信息服务有限公司，为华善金融平台（互联网域名为 www.huashanjinrong.com 网站）的运营管理人，提供金融信息咨询及相关服务。</p>
	<p class="p" style="text-indent:5em;">4、本合同出借人、借款人、均为华善金融平台的注册用户，即出借人、借款人、各方均同意接受华善金融各项公告的行为准则、运行规则、签约流程及资费标准等。</p>
	<p class="p" style="text-indent:5em;">5、中金支付，即中金支付有限公司，华善金融资金第三方托管方。出借人、借款人、均同意遵守该第三方支付服务协议及资费标准，同意并不可撤销地授权中金支付对其账户资金予以托管、划扣。</p>
	<p class="p" style="text-indent:5em;">6、本合同以电子文本形式生成。任何时候须出具本合同书面形式的（如需在相关部门办理抵/质押登记等），本合同项下的所有出借人同意并授权平台委托的主体代表出借人与借款人签订借款合同、抵押合同等相关文件，平台委托的主体代表出借人对借款人行使债权人的权利。</p>
	<p class="p" style="text-indent:5em;">协议各方对上述行为表示认可并不持异议。如本合同的书面形式与本电子合同不一致的，各方均同意以本电子合同的内容为准。</p>
	<h4>第一章 借款基本情况</h4>
	<p class="p" style="text-indent:5em;">第一条借款金额、期限、利率等：</p>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" style="font-size: 12px; line-height: 36px;">
	    <tr>
	        <td align="right" width="150px" style="border: 1px solid #ccc; padding-right: 10px;">项目名称：</td>
	        <td align="left" width="680px" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;"><label>${(borrowing.title)!"XXXX"}</label></td>
	    </tr>
	    <tr>
	        <td align="right" width="150px" style="border: 1px solid #ccc; padding-right: 10px;">编号：</td>
	        <td align="left" width="680px" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;"><label>${(borrowing.id)!"XXXX"}</label></td>
	    </tr>
	    <tr>
	        <td align="right" width="150px" style="border: 1px solid #ccc; padding-right: 10px;">借款用途：</td>
	        <td align="left" width="680px" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;"><label>${(borrowing.purpose)!"XXXX"}</label></td>
	    </tr>
	    <tr>
	        <td align="right" style="border-color: #ccc; border-style: solid; padding-right: 10px; border-width: 0px 1px 1px;">借款金额（人民币）：</td>
	        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${(repayment.capital?string("currency"))!"￥XXXX.XX"}</td>
	    </tr>
	    <tr>
	        <td align="right" style="border-color: #ccc; border-style: solid; padding-right: 10px; border-width: 0px 1px 1px;">还款本息（人民币）：</td>
	        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${(repayment.capitalInterest?string("currency"))!"￥XXXX.XX"}</td>
	    </tr>
	    <tr>
	        <td align="right" style="border-color: #ccc; border-style: solid; padding-right: 10px; border-width: 0px 1px 1px;">借款利率（年利率）：</td>
	        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${(borrowing.interestRate)!"XX.XX"}%/年</td>
	    </tr>
      [#if borrowing.periodUnit??]
	    <tr>
	        <td align="right" style="border-color: #ccc; border-style: solid; padding-right: 10px; border-width: 0px 1px 1px;">还款期数（${message("PeriodUnit." + borrowing.periodUnit)}）：</td>
	        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${(borrowing.period)!"XX"}${message("PeriodUnit." + borrowing.periodUnit)}</td>
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
	        		${message("RepaymentMethod." + borrowing.repaymentMethod)}
	        	[#else]
	        		XXXX
	        	[/#if]
	        </td>
	    </tr>
	    <tr>
	        <td align="right" style="border-color: #ccc; border-style: solid; padding-right: 10px; border-width: 0px 1px 1px;">计息开始日期：</td>
	        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${(repayment.createDate?string("yyyy年MM月dd日"))!"XXXX年XX月XX日"}</td>
	    </tr>
	    <tr>
	        <td align="right" style="border-color: #ccc; border-style: solid; padding-right: 10px; border-width: 0px 1px 1px;">借款到期日期：</td>
	        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">[#--${(repayment.createDate?string("yyyy年MM月dd日"))!"XXXX年XX月XX日"}至--]${(repayment.endDate?string("yyyy年MM月dd日"))!"XXXX年XX月XX日"}</td>
	    </tr>
	</table>
	<br/>
	<p class="p" style="text-indent:5em;">还款计划：</p>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" style="font-size: 12px; line-height: 36px;">
	    <tr>
	        <td align="right" style="border: 1px solid #ccc; padding-right: 10px;">还款期数</td>
	        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;">还款利率（年利率）</td>
	        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;">还款时间</td>
	        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;">还款本息（人民币）</td>
	        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;">还款本金（人民币）</td>
	        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;">还款利息（人民币）</td>
	    </tr>
	    [#if recoveries != null && recoveries?size gt 0]
	        [#list repaymentPlans as repaymentPlan]
	        	<tr>
	            	<td align="right" style="border-color: #ccc; border-style: solid; padding-right: 10px; border-width: 0px 1px 1px;">第${repaymentPlan.period}期/共${repayment.period}期</td>
	                <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${borrowing.interestRate}%/年</td>
	                <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">[#if repaymentPlan.paidDate??]（实际）${repaymentPlan.paidDate?string("yyyy年MM月dd日")}[#else]${repaymentPlan.date?string("yyyy年MM月dd日")}[/#if]</td>
	                <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${repaymentPlan.capitalInterest?string("currency")}</td>
	                <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${repaymentPlan.capital?string("currency")}</td>
	                <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${repaymentPlan.interest?string("currency")}</td>
	            </tr>
	        [/#list]
        [#else]
		    <tr>
		        <td align="right" style="border-color: #ccc; border-style: solid; padding-right: 10px; border-width: 0px 1px 1px;">第XX期/共XX期</td>
		        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">XX.XX%/年</td>
		        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">XXXX-XX-XX</td>
		        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">￥XXXX.XX</td>
		        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">￥XXXX.XX</td>
		        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">￥XXXX.XX</td>
		    </tr>
        [/#if]
	</table>
	<h3>投资人列表：（具体以在线签署时载明为准）</h3>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" style="font-size: 12px; line-height: 36px;">
	    <tr>
	        <td align="left" style="border: 1px solid #ccc; padding-left: 10px;">投资人</td>
	        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;">身份证号码</td>
	        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;">出借金额（人民币）</td>
	        [#if borrowing.periodUnit??]
	        	<td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;">借款期限（${message("PeriodUnit." + borrowing.periodUnit)}）</td>
	        [#else]
	        	<td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;">借款期限（月）</td>
	        [/#if]
	        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;">借款利率（年利率）</td>
	        <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 1px 1px 1px 0px;">总还款本息（人民币）</td>
	    </tr>
	    [#if recoveries != null && recoveries?size gt 0]
	        [#list recoveries as recovery]
	        	[#if recovery.parent??]
	        	[#else]
	        	<tr>
	            	<td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0px 1px 1px;">${(recovery.investor.username)!recovery.investor.corpName}</td>
	            	<td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${(recovery.investor.idNo)!"XXXXXX XXXXXXXX XXXX"}</td>
	                <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${recovery.capital?string("currency")}</td>
	                <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${borrowing.period}${message("PeriodUnit." + borrowing.periodUnit)}</td>
	                <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${borrowing.interestRate}%/年</td>
	                <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">${recovery.capitalInterest?string("currency")}</td>
	            </tr>
	            [/#if]
	        [/#list]
	    [#else]
        	<tr>
            	<td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0px 1px 1px;">XXXX</td>
                <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">XXXX.XXXX</td>
                <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">XX个月</td>
                <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">XX.XX%/年</td>
                <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">XXXX年XX月XX日</td>
                <td align="left" style="border-color: #ccc; border-style: solid; padding-left: 10px; border-width: 0 1px 1px 0;">XXXX年XX月XX日</td>
            </tr>
	    [/#if]
	</table>
	[#--<p>贷款人 ：<span>${(borrower.username)!borrower.corpName}</span><label>（${(borrower.idNo)!"XXXXXX XXXXXXXX XXXX"}）</label></p>
	<p>管理方：<span>${setting.basic.siteName}</span></p>--]
	<p class="p" style="text-indent:2em;">出借方应当保证其出借资金来源合法，如第三方对资金归属、支配权、合法性等问题提出异议，给本合同其他方造成损失的，应当赔偿损失。</p>
	<h4>第二条 借款用途</h4>
	<p class="p" style="text-indent:2em;">第二条本合同项下的借款，借款用途为：${(borrowing.purpose)!"XXXX"}。借款人应当按照借款用途使用款项，不得从事非法活动，如从事非法活动，借款人应承担从事非法活动造成的一切法律后果，给本合同的其他方造成损失的，应当赔偿其损失。未经出借人、华善金融事先书面同意，借款人不得改变借款用途。</p>
	<h4>第三章  借款币种、金额和放款</h4>
	<p class="p" style="text-indent:2em;">第三条本合同项下的借款币种为人民币。借款人借款金额为（${(repayment.capital?string("currency"))!"￥XXXX.XX"}）借款期限：${(repayment.createDate?string("yyyy年MM月dd日"))!"XXXX年XX月XX日"}至${(repayment.endDate?string("yyyy年MM月dd日"))!"XXXX年XX月XX日"}。</p>
	<p class="p" style="text-indent:2em;">第四条出借人决定出借时，应自行登录华善金融账号并亲自操作从自己账户内支付相应款项，此笔款项在出借人点击投标后至该标最终审核通过之前，暂时由中金支付予以冻结。待审核通过后且符合放款条件时，华善金融将给中金支付发出放款指令，由中金支付将出借款项解冻并从出借人账户划入借款人账户。该款项进入借款人账户后，由借款人自行登录华善金融账号并亲自操作提取现金，但借款人不支取款项不影响合同成立和合同效力。</p>
	<p class="p" style="text-indent:2em;">在以下条件完全得到满足的情况下，华善金融将给中金支付发出放款指令（即放款）：</p>
	<p class="p" style="text-indent:2em;">华善金融要求借款人提供的文件、资料，借款人已全部提供，并且其所载明的情况没有发生变化，该等文件、资料持续有效，或者借款人已就发生的变化作出令华善金融满意的解释和说明；本合同项下借款本金自华善金融给中金支付支付发出放款指令、款项入借款人中金支付账户时即视为借款成功（称为放款日），本电子合同自动生成各方当事人可自行登录华善金融账号查看、下载、打印。由于合同涉及所有出借人信息，为了保护其隐私，各方当事人所查看的本电子合同，华善金融将屏蔽掉其他人的部分或全部信息，出借人，借款人表示认可并不持异议。借款人应从放款日起计付利息。如投标时间内，借款人的需求资金在规定期限内没有筹集满，华善金融宣布流标则合同不生效，在此期间不产生利息。</p>
	<h4>第四章  借款利率和计息方法</h4>
	<p class="p" style="text-indent:2em;">第五条借款人应按照本合同第一条的约定，按时、足额向出借人支付借款本息。</p>
	<p class="p" style="text-indent:2em;">第六条各方一致同意在本合同借款期限内，无论国家相关政策是否发生变化，本合同项下借款利率均不作调整。</p>
	<p class="p" style="text-indent:2em;">第七条本合同项下的借款利率按年利率表示。月利率＝年利率/12,日利率＝年利率/360。利息＝本金×利率×期限，借款人实际应还利息按照投资人收益四舍五入精确到 0.01 元后计算。</p>
	<h4>第五章  还款</h4>
	<p class="p" style="text-indent:2em;">第八条经协商一致，出借人同意借款人按下列第种方式的约定偿还借款本息。具体还款方式以本合同第一条确定的为准。</p>
	<p class="p" style="text-indent:2em;">A. 按月付息，到期还本。即借款人按月付息，借款到期后一次性偿还本金。借款人每月应还利息按下列公式计算：</p>
	<h4 style="text-align:center;">每月应还利息＝本金×月利率</h4>
	<p class="p" style="text-indent:2em;">B. 按季付息，到期还本。即借款人按季付息，借款到期后一次性偿还本金。借款人每季应还利息按下列公式计算：</p>
	<h4 style="text-align:center;">每季应还利息＝本金×月利率×3</h4>
	<p class="p" style="text-indent:2em;">C．按月等额还款。即借款人按月等额还本付息。借款人每月应还本息按下列公式计算：</p>
	<h4 style="text-align:center;">月利率×（1+月利率）^N</h4>
	<h4 style="text-align:center;">每月还本付息额= ------------------------------------ × 借款本金</h4>
	<h4 style="text-align:center;">（1+月利率）^N-1</h4>
	<h4 style="text-align:center;">月利率=年利率/12</h4>
	<h4 style="text-align:center;">N=还款期数</h4>
	<p class="p" style="text-indent:2em;">D.到期一次性还本付息。即借款到期后，一次性归还借款本息。到期一次性归还本息按下列公式计算：</p>
	<h4 style="text-align:center;">到期一次性归还本息（月标）＝本金＋本金×月利率×借款月数</h4>
	<h4 style="text-align:center;">到期一次性归还本息（天标）＝本金＋本金×年利率/360×借款天数</h4>
	<p class="p" style="text-indent:2em;">第九条还款日</p>
	<p class="p" style="text-indent:2em;">1.采用按月等额还款方式还款的，还款日为借款发放日的对应日，无对应日的，当月最后一日为还款日。末次还款日为借款到期日。借款人应从借款发放的次月开始还款。</p>
	<p class="p" style="text-indent:2em;">2.采用按月或按季付息，到期还本方式还款的，还款日为借款发放日的对应日，无对应日的，当月最后一日为还款日。末次还款日为借款到期日。借款人应从借款发放的次月/次季开始还款。</p>
	<p class="p" style="text-indent:2em;">3.采用到期一次性还本付息的，借款到期日为还款日，借款人应一次性偿付借款本金和借款利息。</p>
	<p class="p" style="text-indent:2em;">4.借款人在偿还最后一期借款本金时，应利随本清。借款人应于每期还款日前24小时在其中金支付托管账户上备足当期应付之本金及其他应付款项，并于还款日前24小时内登陆华善金融网站进行还款操作，还款后相应资金将从借款人账户中划转至出借人账户。当借款人托管账户中的资金余额不足时，借款人不可撤销地授权华善金融从借款人指定的其他银行还款账户（“借款人银行还款账户”）中代扣差额（应付款项与借款人托管账户中余额之差）。</p>
	<p class="p" style="text-indent:2em;">第十条借款人的任何一期还款不足以偿还应还本金、利息、罚息的，各出借人同意按照其出借金额占借款总额的相应比例收取还款。</p>
	<h4>第六章  逾期还款</h4>
	<p class="p" style="text-indent:2em;">第十一条借款人未在还款日（包括被宣布提前到期）前24小时内，在平台资金存管账户上备足当期应付之本金及其他应付款项的，视为借款人逾期还款。出款人、华善金融给与借款人在12小时内备足应付款项的宽限期，在宽限期内，借款人必须备足所有应付款项。华善金融在借款人逾期还款时可向担保人发出《逾期催收及代偿通知书》。担保人对华善金融发出的《逾期催收及代偿通知书》表示认可、不持异议并自行决定是否代偿。借款人也可请求担保人予以代偿。</p>
	<p class="p" style="text-indent:2em;">第十二条借款人到期应付而未付的借款本息，自逾期之日起，按本合同第一条约定的借款利率的300%按日计收逾期罚息，直至借款人清偿本息为止。逾期罚息不计复利。</p>
	<p class="p" style="text-indent:2em;">第十三条借款人发生逾期还款，出借人既可以选择自行向借款人追讨，也可以选择由担保人按担保条款的约定代偿相关款项。出借人选择自行追讨的，由出借人自行承担损失和风险，同时自行享有借款人所支付的利息和逾期罚息。出借人选择自行追讨的，应在借款人逾期之日起2日内向担保人或华善金融平台办妥移转追讨手续并提交《自行追讨通知书》，华善金融平台将协助办理。出借人在借款人逾期之日起2日内，未向华善金融提交《自行追讨通知书》的，视为出借人放弃自行追讨，并不可撤销地选择由担保人按担保条款的约定代偿相关款项。</p>
	<p class="p" style="text-indent:2em;">第十四条担保人按约定代偿相关款项后，出借人对借款人的债权（包括借款本金、利息和逾期罚息等）自动转让给担保人，担保人其后向借款人追讨上述债权所得全部归担保人所有。担保人向借款人追讨所得多于其代偿金额的，多出部分将作为出借人支付给担保人的追讨服务费用；担保人追讨所得少于其代偿金额的，损失由担保人自行承担。</p>
	<p class="p" style="text-indent:2em;">第十五条借款人无条件同意，若借款人任何一期应付款逾期或借款人在逾期后出现逃避、拒绝沟通或拒不承认欠款等恶意行为的，华善金融有权将借款人违约失信的相关信息在www.huashanjinrong.com网站上进行公告，且不限于向媒体、公安机关、检察机关等披露，或将借款人的“逾期记录”上报中国人民银行征信系统及/或信用数据库。同时，出借人、担保人可以通过发布借款人的相关信息或悬赏等方式追索债权。</p>
	<p class="p" style="text-indent:2em;">第十六条借款人同意，如借款人的还款金额不足以足额清偿到期应付款项的，借款人的每期还款均应按照如下顺序清偿：实现债权费用、滞纳金、违约金、逾期罚息、利息、本金。</p>
	<h4>第七章  陈述、声明和保证</h4>
	<p class="p" style="text-indent:2em;">第十七条出借人陈述、声明和保证</p>
	<p class="p" style="text-indent:2em;">1.出借人系具有完全民事权利能力和完全民事行为能力，能够独立承担民事责任，有权签订并履行本合同。</p>
	<p class="p" style="text-indent:2em;">2.出借人保证在本合同有效期内保持华善金融注册用户身份。</p>
	<p class="p" style="text-indent:2em;">3.出借人向借款人提供的出借资金来源合法，如果第三人对资金归属、合法性等问题提出异议，出借人自行承担责任。</p>
	<p class="p" style="text-indent:2em;">4.出借人保证不利用华善金融进行信用卡套现、洗钱或其他违法行为。</p>
	<p class="p" style="text-indent:2em;">5.出借人保证其向华善金融提供的所有证件、资料均合法、真实、准确、完整和有效。若上述材料发生变化时，出借人保证及时登录会员中心更新。</p>
	<p class="p" style="text-indent:2em;">6.出借人已详细阅读了华善金融在其网站所发布的交易规则和内容，充分知悉和了解本合同项下各方的权利和义务，对融资包内容全部认可并接受，在本合同项下的全部意思表示真实。</p>
	<p class="p" style="text-indent:2em;">7.出借人清楚可能面临的出借风险。出借人在此确认，借款人或者担保人可能因为经营不善、丧失商业信誉等因素，存在着部分或全部借款不能回收的风险，出借人已充分了解并认识到了本次借款的特殊性、风险的不确定性以及回收该等借款可能面临的困难，经独立慎重判断后仍做出出借决定，自愿签署本合同。</p>
	<p class="p" style="text-indent:2em;">8.出借人若成功出借，保证采取合法的手段和方式向借款人或者担保人主张权利。并且保证不得因未实现预期投资收益或者投资受损而对华善金融提出任何诉讼、仲裁或索赔，华善金融不承担任何责任。</p>
	<p class="p" style="text-indent:2em;">9.出借人了解并同意：华善金融不收取投资服务费。</p>
	<p class="p" style="text-indent:2em;">10.出借人同意，当其通过华善金融操作出借步骤后，出借款项将由中金支付冻结在资金托管账户，华善金融在放款条件成就时将通知中金支付解除冻结，出借资金将划入借款人托管账户，借款人即可自由支取此款项。借款人还款时须将还款资金划入其借款人资金托管账户，华善金融有权委托中金支付将此笔还款划入各出借人账户。</p>
	<p class="p" style="text-indent:2em;">11.出借人承诺并保证在资金出借中产生的相关税费，由出借人自行向其主管税务机关申报、缴纳，华善金融不负责相关事务处理。</p>
	<p class="p" style="text-indent:2em;">12.出借人承诺，出借人因授权和/或委托华善金融根据本合同所采取的全部行动和措施的法律后果均归属于出借人本人。</p>
	<p class="p" style="text-indent:2em;">第十八条借款人陈述、声明和保证</p>
	<p class="p" style="text-indent:2em;">1.借款人系依法成立的、能够独立承担法律责任的法人组织或具有完全民事权利能力和完全民事行为能力，能够独立承担民事责任的自然人。</p>
	<p class="p" style="text-indent:2em;">2.借款人保证在本合同有效期内保持华善金融注册用户身份。</p>
	<p class="p" style="text-indent:2em;">3.借款人已仔细阅读并完全理解和接受本合同的内容，借款人签署和履行本合同是自愿的，其在本合同项下的全部意思表示真实。</p>
	<p class="p" style="text-indent:2em;">4.借款人为了进行本合同项下的交易及签署本合同向华善金融与担保人提供的所有文件、资料和凭证等都是真实、完整、准确和有效的，借款人所提交的财务报表真实地反映了该财务报表在出具时借款人的财务状况。若上述文件、资料发生变化时，出借人保证及时登录会员中心更新。</p>
	<p class="p" style="text-indent:2em;">5.借款人保证按本合同约定的用途使用借款并按时足额归还借款本息。借款人保证在每期还款日24小时前将足额款项存入其中金支付监管账户并在华善金融网站进行还款操作，同意并授权华善金融将对应本息划至各出借人账户。</p>
	<p class="p" style="text-indent:2em;">6.借款人保证不会利用华善金融进行诈骗、非法集资或其他违法行为，否则应依法独立承担法律责任。</p>
	<p class="p" style="text-indent:2em;">7.借款人承诺并保证在资金借入中产生的相关税费，由借款人自行向其主管税务机关申报、缴纳，华善金融不负责相关事务处理。</p>
	<p class="p" style="text-indent:2em;">8.借款人承诺，借款人因授权和/或委托华善金融根据本合同所采取的全部行动和措施的法律后果均归属于借款人本人。</p>
	<p class="p" style="text-indent:2em;">9.借款人承诺配合华善金融进行借款支付管理，保证自觉接受并积极配合华善金融或担保人对其有关生产、经营及财务情况的调查、了解、监督及贷后管理；并按华善金融的要求实时提供《资产负债表》、《损益表》等财务报表或其他反映借款人资信情况的资料。</p>
	<p class="p" style="text-indent:2em;">10.借款人保证在清偿其在本合同项下的全部债务之前如进行对外投资、实质性增加债务融资，以及用于反担保的经营组织进行合并、分立、减资、股权转让、资产转让、申请停业整顿、申请解散、申请破产等足以引起本合同之债权、债务关系变化或者可能足以影响出借人权益的行动时，应提前三十个工作日以书面形式通知华善金融，同时落实债务清偿责任或者提前清偿责任，未经华善金融书面同意，不得进行上述行动。</p>
	<p class="p" style="text-indent:2em;">11.借款人保证在本合同有效期间内，未经华善金融书面同意不得为其他企业法人、其他组织或个人承担足以影响借款人偿还本合同项下借款能力的债务，或提供足以影响借款人偿还本合同项下借款能力的保证担保，或以借款人资产、权益设定足以影响借款人偿还本合同项下借款能力的抵押或质押。</p>
	<p class="p" style="text-indent:2em;">12.借款人如出现下列事项对其履行本合同项下还款义务构成威胁的任何事件时，借款人应当立即书面通知华善金融，并积极华善金融的要求落实好本合同项下的借款及其他一切应付款项按期足额偿还的保障措施：</p>
	<p class="p" style="text-indent:2em;">（1）借款人在与银行等金融机构签订的借款合同、授信额度合同及其项下授信合同、担保合同或与其他任何债权人所签订的融资合同项下发生任何违约事项；</p>
	<p class="p" style="text-indent:2em;">（2）借款人发生违法或被索赔事件；</p>
	<p class="p" style="text-indent:2em;">（3）借款人经营出现严重困难和财务状况发生恶化；</p>
	<p class="p" style="text-indent:2em;">（4）在本合同项下的债务未清偿前，借款人已承担或将要承担重大债务或或有债务；</p>
	<p class="p" style="text-indent:2em;">（5）借款人发生重大债权债务纠纷引起诉讼、仲裁等事件；</p>
	<p class="p" style="text-indent:2em;">（6）其他可能影响借款人财务状况和偿债能力的情况。</p>
	<p class="p" style="text-indent:2em;">第十九条平台陈述、声明和保证</p>
	<p class="p" style="text-indent:2em;">1.华善金融专注于为有理财需求的投资人和有资金需求的融资人搭建一个安全、透明、便捷的投融资互联网交易平台,华善金融结合金融服务经验，利用先进的资信管理技术，为平台两端的客户提供投融资咨询、资信评估、投融资方案设计、投融资需求对接、资信管理、财富管理等服务。</p>
	<p class="p" style="text-indent:2em;">2.华善金融严格按照本合同的约定，恪尽职守，以诚实、信用、谨慎、有效管理的原则，为出借人、借款人提供服务，并据以收取相应的服务费用。</p>
	<p class="p" style="text-indent:2em;">3.在任何情形下，华善金融不是本合同项下任何借款或债务的债务人或需要以其自有资产偿还本合同项下的任何借款或债务。中金支付亦不是本合同项下的担保人或需要以其自有资产承担本合同项下的担保责任。</p>
	<p class="p" style="text-indent:2em;">4.华善金融积极协助各方办理各项信息对接及变更手续。</p>
	<p class="p" style="text-indent:2em;">5.妥善保存本合同及相关的全部资料以备查阅。对合同各方的个人信息、资产情况及其他服务相关事务的情况和资料依法保密；非因促进交易、一方违约，或相关权力部门要求（包括但不限于法院、仲裁机构、金融监管机构等），不得对外披露。</p>
	<p class="p" style="text-indent:2em;">6.华善金融保留对可疑交易、非法交易、高风险交易或交易纠纷的独立判断和确定，并有权依法采取暂停交易、终止交易、向有关单位报告等必要处理措施或依有关单位合法指示行事的权利。</p>
	<h4>第九章  违约事件</h4>
	<p class="p" style="text-indent:2em;">第二十条借款人存在下述任一事件或者几项事件的，均构成本合同项下的违约事件：</p>
	<p class="p" style="text-indent:2em;">1.借款人在本合同项下作出的陈述、声明和保证被证明是不真实的，或是具有误导性的，或者借款人未依据其陈述、声明和保证履行的；</p>
	<p class="p" style="text-indent:2em;">2.借款人涉及将会对其财务状况或借款人根据本合同履行其义务的能力构成严重不利影响的任何诉讼、仲裁或行政程序的；</p>
	<p class="p" style="text-indent:2em;">3.借款人的财产遭受没收、征用、被查封、冻结、扣押或监管、遭受处罚，已经或可能影响借款人在本合同项下义务的履行，且不能及时提供有效补救措施的；</p>
	<p class="p" style="text-indent:2em;">4.借款人任何一笔应付款在还款日后 12 小时前未还清的；</p>
	<p class="p" style="text-indent:2em;">5.借款人擅自改变借款用途的；</p>
	<p class="p" style="text-indent:2em;">6.借款人被依法吊销企业营业执照或者丧失法人资格的；</p>
	<p class="p" style="text-indent:2em;">7.发生任何其他事件或情况，实质性地对出借人在本合同项下的权利产生不利影响的；</p>
	<p class="p" style="text-indent:2em;">第二十一条上述违约事件是否发生，由华善金融作出判断。上述任何违约事件发生后，出借人授权华善金融采取以下任何一项或多项措施：</p>
	<p class="p" style="text-indent:2em;">1.立即停止本合同项下借款的划付；</p>
	<p class="p" style="text-indent:2em;">2.宣布借款立即到期，并要求借款人立即偿还全部已发放的借款本金、利息或其他实现债权的费用；</p>
	<p class="p" style="text-indent:2em;">3.要求借款人追加或更换担保人、抵押物、质物/出质权利。</p>
	<h4>第十章  其他</h4>
	<p class="p" style="text-indent:2em;">第二十二条合同各方应当对为签署和履行本合同的目的而了解到的对方有关其债务、财务、生产、经营资料及情况等进行保密，非因促进交易、一方违约，或相关权力部门要求（包括但不限于法院、仲裁机构、金融监管机构等），不得对外披露。</p>
	<p class="p" style="text-indent:2em;">第二十三借款人在本合同项下应付的所有款项应全额支付，不得作任何性质的冲抵、扣减或预提，亦不得同出借人所欠借款人的任何债务相抵销。如果任何法律要求借款人对其支付给出借人的任何款项进行扣减或预提，则借款人应向出借人支付一笔额外的款项，以保证出借人收到的金额相等于在不作此类扣减或预提时所应收到的款项。</p>
	<p class="p" style="text-indent:2em;">第二十四条各方确认并同意，委托华善金融对本合同项下的任何金额进行计算；在无明显错误的情况下，华善金融对本合同项下任何数额的任何证明或确定，应作为该数额有关事项的终局证明。</p>
	<p class="p" style="text-indent:2em;">第二十五条出借人确认并同意其出借人平台资金存管账户为其资金划出、划入或回收专用账户。借款人确认并同意其借款人平台资金存管账户为其资金划出、划入或回收专用账户。</p>
	<p class="p" style="text-indent:2em;">第二十六条本合同及本合同所涉及的任何事项适用中国法律，并按照中国法律进行解释。各方在履行本合同过程中所发生的争议，首先应协商解决；协商无法达成一致的，各方一致同意将争议提交给借款人所在地人民法院予以裁决。</p>
	<p class="p" style="text-indent:2em;">第二十七条除非法律另有规定，本合同任何条款的无效或不可执行，不影响其他条款的有效性和可执行性，也不影响整个合同的效力。</p>
	<p class="p" style="text-indent:2em;">第二十八条本合同未尽事宜，由各方协商一致后另行签订补充合同。本合同的注解、附件、补充规定为本合同组成部分，与本合同具有同等法律效力。</p>
	<p class="p" style="text-indent:2em;">第二十九条本合同采用电子文本形式制成，自借款人借款标满标之日起成立，自款项进入借款人中金支付账户时即生效。本合同保存在华善金融为此设立的专用服务器上备查，各方均认可该形式的合同效力。</p>
	
</body>
</html>
[/@compress]