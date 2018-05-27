[@nestedstyle]
    [@css href="css/help.css" /]
    [@css href="css/build.css" /]
[/@nestedstyle]
[@nestedscript]
    [@js src="js/calculator/calculator.js" /]
[/@nestedscript]
[@insert template="layout/invest_layout" title="收益计算器" user=kuser]
<div class="height18"></div>
<div class="layout" id="calculator">
    <div class="calculator">
        <h3>投资计算器</h3>
        <div class="calcuContent">
            <form id="calculatorForm">
                <div class="jisuan">
                    <p>
                        <label>投资金额:</label>
                        <input type="text" class="cash" id="cash" name="amount"/>元
                        <label for="cash" class="error"></label>
                    </p>
                    <p>
                        <label>年化利率:</label>
                        <input type="text" calss="rate" id="rate" name="rate"/>%
                        <label for="rate" class="error"></label>
                    </p>
                    <p>
                        <label>投资时长:</label>
                        <input type="text" calss="time" id="time" name="period"/>
                        <select id="dateUnit" name="dateUnit" style="border-radius: 3px; border: 1px solid #e6e6e6; height: 30px;">
                            <option value="MONTH">月</option>
                            <option value="DAY">天</option>
                        </select>
                        <label for="time" class="error"></label>
                    </p>
                    <p>
                        <label>还款方式:</label>
                        <span id="repaymentMethodBox">
                            <input type="radio" name="interestMethod" value="EACH_PERIOD_INTEREST_AND_LAST_PERIOD_PLUS_CAPITAL" class="type" checked="checked"/>
                            <label>按月付息，到期还本</label>
                            <input type="radio" name="interestMethod" value="EACH_PERIOD_AVG_CAPITAL_PLUS_INTEREST" class="type"/>
                            <label>等额本息</label>
                            <input type="radio" name="interestMethod" value="LAST_PERIOD_CAPITAL_PLUS_INTEREST" class="type"/>
                            <label>到期还本付息</label>
                        </span>
                    </p>
                    <p>
                        <button class="button button-mormal rasius-ssm work" type="submit">计算</button>
                        <button class="button button-mormal rasius-ssm  reset"  type="reset" value="reset">重置</button>
                    </p>
                </div>
                <div class="result">
                    <h3>计算结果</h3>
                    <table>
                        <tr>
                            <th>预期本息收益</th>
                            <th>利息</th>
                        </tr>
                        <tr>
                            <td class="income"><em style="font-style: normal">0</em>元</td>
                            <td class="interest"><em  style="font-style: normal">0</em>元</td>
                        </tr>
                    </table>
                </div>
            </form>
        </div>
    </div>
    <div class="payPlan" style="display: none;">
        <h3>还款详细计划</h3>
        <table>
            <tr>
                <th>期</th>
                <th>还款时间</th>
                <th>还款总额</th>
                <th>本金</th>
                <th>利息</th>
            </tr>
            <tbody id="planCont">

            </tbody>
        </table>
    </div>
</div>
<div class="space"></div>

<span id="repaymentMethodMONTH" style="display: none;">
    <input type="radio" name="interestMethod" value="EACH_PERIOD_INTEREST_AND_LAST_PERIOD_PLUS_CAPITAL" class="type" checked="checked"/>
    <label>按月付息，到期还本</label>
    <input type="radio" name="interestMethod" value="EACH_PERIOD_AVG_CAPITAL_PLUS_INTEREST" class="type"/>
    <label>等额本息</label>
    <input type="radio" name="interestMethod" value="LAST_PERIOD_CAPITAL_PLUS_INTEREST" class="type"/>
    <label>到期还本付息</label>
</span>
<span id="repaymentMethodDAY" style="display: none;">
    <input type="radio" name="interestMethod" value="FIXED_PERIOD_INTEREST_AND_LAST_PERIOD_PLUS_CAPITAL" class="type" checked="checked"/>
    <label>按期付息，到期还本</label>
    <input type="radio" name="interestMethod" value="LAST_PERIOD_CAPITAL_PLUS_INTEREST" class="type"/>
    <label>到期还本付息</label>
</span>

<script id="plansTpl" type="text/html">
    {{#  $.each(d.records, function(index, item){ }}
    <tr>
        <td>第{{ item.period }}期</td>
        <td>{{ dateFormatter(item.payDate) }}</td>
        <td>{{ currency(item.payAmount) }}</td>
        <td>{{ currency(item.capital) }}</td>
        <td>{{ currency(item.interest) }}</td>
    </tr>
    {{#  }); }}

</script>

[/@insert]