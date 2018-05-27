[#-- 隐藏元素 --]
<div id="balance_div" style="display:none;">
	[#-- 投资会话 --]
	<form id="investmentConfirmForm" class="investmentForm" action="${ctx}/investment/${project.id}/invest" method="POST">
		<input type="hidden" name="amount" value="" />
		<div class="table confirm">
			<table class="table-condensed" style="width: 100%">
				<tr>
					<td width="25%" align="center">借款标题：</td>
					<td title="${project.title}">${abbreviate(project.title, 20, "...")}</td>
				</tr>
				<tr>
					<td align="center">借款用户：</td>
					<td>${secrecy("mobile", borrower.loginName!"")}</td>
				</tr>
				<tr>
					<td align="center">年利率：</td>
					<td>${project.interestRate + project.rewardInterestRate}%/年</td>
				</tr>
				<tr>
					<td align="center">还款期限：</td>
					<td>${project.period}${project.periodUnit.getDisplayName()}</td>
				</tr>
				<tr>
					<td align="center">还款方式：</td>
					<td>${project.repaymentMethod.getDisplayName()}</td>
				</tr>
				<tr>
					<td align="center">投资金额：</td>
					<td class="color-one"><label id="investmentAmount">0</label>&nbsp;元</td>
				</tr>
				[#if currentUser?? && project.surportCoupon]
					<tr>
						<td align="right">优惠券：</td>
						[#if userCouponsSizen>0]
							<td class="color-one">
								<input hidden id="hideUserCoupons" value="${userCoupons}"/>
								<select id="coupon" name="coupon">
                                    	<option value="">---不使用---</option>
									[#--[#list userCoupons as userCoupon]--]
										[#--<option value="${userCoupon.id}">${userCoupon.title}</option>--]
									[#--[/#list]--]
								</select>
							</td>
						[#else ]
							<td>暂无可用优惠券</td>
						[/#if]
					</tr>
				[/#if]
				<tr style="display: none">
					<td align="right">优惠金额：</td>
					<td class="color-one"><label id="preferentialAmount">0</label>&nbsp;元</td>
				</tr>
				<tr style="display: none">
					<td align="right">支付金额：</td>
					<td class="color-one"><label id="availableAmount">0</label>&nbsp;元</td>
				</tr>
                <tr>
                    <td colspan="2" align="center">
                        <label style="font-weight: 500"><input id="agreement" name="agreement" type="checkbox" checked="checked"/>我已阅读并同意<a href="${ctx}/investment/agreement/${project.id}" target="_blank" class="color-blue">《平台投资协议》</a></label>
                        <p class="annotate" style="color: red; line-height: 14px; height: 14px; margin: 5px 0;"></p>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<button type="submit" class="btn btn-primary" style="width: 100%;padding: 6px 0px">提交</button>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>