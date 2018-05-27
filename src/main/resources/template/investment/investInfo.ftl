[#--项目信息展示--]
<div class="Details">
	[#--页签标题--]
	<div class="title">
		<ul id="footNav">
			<li><a href="#Details-content-one" class="active">项目信息</a></li>
			[#if project.guarantee??]
				<li><a href="#Details-content-three">担保方信息</a></li>
			[/#if]
			[#if project.progress == "INVESTING" && materials?size gt 0]
				<li><a href="#Details-content-four">相关材料</a></li>
			[/#if]
			[#if investmentRecords?size gt 0 &&  project.progress == "INVESTING"]
				<li><a href="#Details-content-five" id="fuck">投标记录</a></li>
			[/#if]
			[#if repaymentPlans?size gt 0]
				<li><a href="#Details-content-six">还款计划</a></li>
			[/#if]
			<li><a href="#Details-content-seven">项目历程</a></li>
		</ul>
	</div>

	[#--项目信息--]
	<div id="Details-content-one" class="container">
		[#if project.description??]
			<h3>项目描述</h3>
			<p>${project.description}</p>
		[/#if]
		[#if project.purpose??]
			<h3>资金用途</h3>
			<p>${project.purpose}</p>
		[/#if]
		[#if project.repaymentInquiry??]
			<h3>还款来源</h3>
			<p>${project.repaymentInquiry}</p>
		[/#if]
		 [#if project.borrower.corpIntro??]
			<h3>企业背景</h3>
			<p>${project.borrower.corpIntro}</p>
		[/#if]
		<h3>借款方简介</h3>
		<p>${borrowerInfo!"暂无信息"}</p>
		<h3>经营状况</h3>
		<p>${project.fieldInquiry!"暂无信息"}</p>
	</div>

   [#-- 担保方信息 --]
	<div id="Details-content-three" class="container hide">
		<h3>担保机构</h3>
		[#if project.guaranteeCorp??]
		<p>${project.guaranteeCorp}</p>
		[#else]
		<p>暂无信息</p>
		[/#if]
		[#if project.guaranteeCorp??]
			<h3>担保机构简介</h3>
			<p>${project.guaranteeCorp}</p>
		[#else]
			<h3>担保机构简介</h3>
			<p>暂无信息</p>
		[/#if]
		[#if project.guarantee??]
			<h3>风险控制措施</h3>
			<p>${project.guarantee}</p>
		[/#if]
		[#if project.collateral??]
			<h3>抵押物</h3>
			<p>${project.collateral}</p>
		[/#if]
	</div>

	[#-- 相关材料 --]
	[#if project.progress == "INVESTING" && materials?size gt 0]
		<div id="Details-content-four" class="container hide">
			<h3>相关材料展示</h3>
			<ul class="images-ul">
				[#list materials as material]
					<li>
						<a href="${dfsUrl}${material.source}" class="image" target="_blank">
							<img src="${dfsUrl}${material.source}" [#if material.title??]alt="${material.title}"[/#if] />
						</a>
						[#if material.title??]
							<div><a title="${material.title}">${abbreviate(material.title, 30, "...")}</a></div>
						[/#if]
					</li>
				[/#list]
			</ul>
		</div>
	[/#if]

	[#-- 投标记录 --]
	[#if investmentRecords?size gt 0 ]
		<div id="Details-content-five" class="container hide">
			<div class="table">
				<table class="Recordbid-table">
					<tr>
						<th>投标人</th>
						<th>投标金额</th>
						<th>操作方式</th>
						<th>投标时间</th>
					</tr>
					[#list investmentRecords as investmentRecord]
						[#--[#if !investmentRecord.investment.recovery.parent??]--]
							<tr>
								<td>${secrecy("mobile", investmentRecord.investorLoginName)}</td>
								<td>${investmentRecord.amount?string("currency")}</td>
								<td>${investmentRecord.operationMethodDes}[#--${message("OperationMethod." + investmentRecord.operationMethod)}--]</td>
								<td>${investmentRecord.createDate?string("yyyy年MM月dd日 HH时mm分")}</td>
							</tr>
						[#--[/#if]--]
					[/#list]
				</table>
			</div>
		</div>
	[/#if]

	[#-- 还款计划 --]
	[#if repaymentPlans?size gt 0]
		<div id="Details-content-six" class="container hide">
			<div class="table">
				<table class="Recordbid-table">
					<tr>
						<th>期数</th>
						<th>还款日期</th>
						<th>计划本金</th>
                        <th>计划利息</th>
						<th>逾期利息</th>
                        [#--<th>服务费</th>--]
                        <th>实际还款</th>
						<th>状态</th>
					</tr>
					[#list repayments as repayment]
                        <tr>
                            <td>${repayment.period}/${project.period}</td>
							[#if repayment.paidDate??]
                                <td>实际还款：${repayment.paidDate?string("yyyy-MM-dd")}（${repayment.payDate?string("yyyy-MM-dd")}）</td>
							[#else]
                                <td>${repayment.payDate?string("yyyy-MM-dd")}</td>
							[/#if]
                            <td>${repayment.capital?string("currency")}</td>
                            <td>${repayment.interest?string("currency")}</td>
                            <td>${repayment.repaymentOverdueInterest()?string("currency")}</td>
                            [#--<td>${repayment.repaymentFee()?string("currency")}</td>--]
							[#if repayment.paidDate??]
                                <td>${repayment.getCapitalInterestOverdueAhead()?string("currency")}</td>
							[#else]
                                <td>-</td>
							[/#if]
                            <td>${repayment.stateDes}</td>
                        </tr>
					[/#list]
				</table>
			</div>
		</div>
	[/#if]

	[#-- 项目历程 --]
	<div id="Details-content-seven" class="container hide">
		<ul class="project-process">
			[#-- 申请意见 --]
			[#if applyOpinion??]
				<li>
					<div class="proje-icon-a"></div>
					<div class="proje-cert">
						<div class="div-one">项目发起</div>
						<div class="div-two">
							发起时间：${applyOpinion.createDate?string("yyyy年MM月dd日 HH时mm分")}
							|
							发起人：${secrecy("username", applyOpinion.operator)}
						</div>
					</div>
				</li>
			[/#if]
			[#-- 调查意见 --]
			[#if inquiryOpinion??]
				<li>
					<div class="proje-icon-c"></div>
					<div class="proje-cert">
						<div class="div-one">项目调查</div>
						<div class="div-two">
							调查时间：${inquiryOpinion.createDate?string("yyyy年MM月dd日 HH时mm分")}
							|
							调查人：${secrecy("username", inquiryOpinion.operator)}
						</div>
					</div>
				</li>
			[/#if]
			[#-- 审批意见 --]
			[#if confirmOpinion??]
				<li>
					<div class="proje-icon-c"></div>
					<div class="proje-cert">
						<div class="div-one">项目审批</div>
						<div class="div-two">
							审批时间：${confirmOpinion.createDate?string("yyyy年MM月dd日 HH时mm分")}
							|
							审批人：${secrecy("username", confirmOpinion.operator)}
						</div>
					</div>
				</li>
			[/#if]
			[#-- 项目募集 --]
			[#if project.investmentStartDate??]
				<li>
					<div class="proje-icon-b"></div>
					<div class="proje-cert">
						<div class="div-one">项目募集</div>
						<div class="div-two">
							开始时间：${project.investmentStartDate?string("yyyy年MM月dd日 HH时mm分")}
							[#if project.investmentFinishDate??]
								|
								完成时间：${project.investmentFinishDate?string("yyyy年MM月dd日 HH时mm分")}
							[#elseif project.investmentEndDate??]
								|
								结束时间：${project.investmentEndDate?string("yyyy年MM月dd日 HH时mm分")}
							[/#if]
						</div>
					</div>
				</li>
			[/#if]
			[#-- 放款意见 --]
			[#if lendOpinion??]
				<li>
					<div class="proje-icon-a"></div>
					<div class="proje-cert">
						<div class="div-one">项目放款</div>
						<div class="div-two">
							放款时间：${lendOpinion.createDate?string("yyyy年MM月dd日 HH时mm分")}
							|
							放款人：${secrecy("username", lendOpinion.operator)}
						</div>
					</div>
				</li>
			[/#if]
			[#-- 项目还款 --]
			[#list repayments as repayment]
				[#if repayment.paidDate??]
					<li>
						<div class="proje-icon-d"></div>
						<div class="proje-cert">
							<div class="div-one">项目还款</div>
							<div class="div-two">
								还款期数：${repayment.period}/${project.period}
								|
								还款金额：${repayment.getRepaymentAmount()?string("currency")}[#if repayment.repaymentOverdueInterest() gt 0]（逾期罚息：${repaymentPlan.repaymentOverdueInterest()?string("currency")}）[/#if]
								|
								还款时间：${repayment.paidDate?string("yyyy年MM月dd日 HH时mm分")}
							</div>
						</div>
					</li>
				[/#if]
			[/#list]
		</ul>
	</div>

</div>