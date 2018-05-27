[#-- 隐藏元素 --]
<div id="balance_div" style="display:none;">
	[#-- 投资会话 --]
	<form id="investmentConfirmForm" class="investmentForm" action="${ctx}/transfer/${transfer.id}/invest" method="POST">
        <input type="hidden" name="parts" />
        <div class="table confirm">
            <table class="table-condensed" style="margin: 0 auto;">
                <tr>
                    <td width="30%" align="right">原借款用户：</td>
                    <td>${secrecy("mobile", borrower.loginName!"")}</td>
                </tr>
                <tr>
                    <td align="right">转让人：</td>
                    <td>${secrecy("mobile", transferUser.loginName!"")}</td>
                </tr>
                <tr>
                    <td align="right">借款类型：</td>
                    <td>${borrowing.typeDes}（债权转让）</td>
                </tr>
                <tr>
                    <td align="right">年利率：</td>
                    <td>${borrowing.realInterestRate}%/年</td>
                </tr>
                <tr>
                    <td align="right">剩余期限：</td>
                    <td>${transfer.surplusPeriod}期 </td>
                </tr>
                <tr>
                    <td align="right">还款方式：</td>
                    <td>${borrowing.repaymentMethodDes}</td>
                </tr>
                <tr>
                    <td align="right">债权价值：</td>
                    <td class="color-one" id="transferWorth">***元</td>
                </tr>
                <tr>
                    <td align="right">债权价格：</td>
                    <td class="color-one" id="transferCapital">***元</td>
                </tr>
                <tr>
                    <td align="right">服务费：</td>
                    <td class="color-one" id="transferFee">***元</td>
                </tr>
                <tr>
                    <td align="right">支付金额：</td>
                    <td class="color-one" id="transferAmount">***元</td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: center;" align="center">
                        <label style="font-weight: 500"><input type="checkbox" name="agreement" checked="checked" />我已阅读并同意<a href="${base}/transfer/agreement/${transfer.id}" target="_blank" class="color-blue">《平台债权转让协议》</a></label>
                        <p class="annotate" style="color: red; line-height: 14px; height: 14px; margin: 5px 0;"></p>
                    </td>
                </tr>
				<tr>
					<td colspan="2" align="center">
						<div>
                            <button type="submit" class="btn btn-primary" style="width: 100%">提交</button>
						</div>
					</td>
				</tr>
            </table>
        </div>
	</form>
</div>