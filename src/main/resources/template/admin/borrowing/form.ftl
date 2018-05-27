[#--
[#include "layout/default_layout.ftl" /]
[@template title="借款申请"]
<div>
    <form id="borrowingForm" action="${ctx}/mng/borrowing/${action}" method="POST">
        <div class="easyui-tabs" style="width:auto;height:auto;">
            <div title="基本信息" style="padding:auto;">
                <table class="tbform">
                    <tr>
                        <td>借款人：</td>
                        <td>
                            <input id="borrower" name="borrower" value="${borrowing.borrower}" data-options="required:'required'"/>
                        </td>
                    </tr>
                    <tr>
                        <td>借款类型：</td>
                        <td>
                            <select name="type" value="${borrowing.type}" class="easyui-combobox" data-options="width:180,required:'required'">
                                [#list types as type]
                                    <option value="${type}">${type.displayName}</option>
                                [/#list]
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>借款标题：</td>
                        <td>
                            <input name="title" value="${borrowing.title}" type="text" class="easyui-validatebox" data-options="width:180,required:'required',validType:'length[2,20]'"/>
                        </td>
                    </tr>
                    <tr>
                        <td>借款金额：</td>
                        <td>
                            <input name="amount" value="${borrowing.amount}" type="text" class="easyui-numberbox" value="" data-options="width: 180,required:'required',min:0,precision:2,prefix:'￥'"></input>
                        </td>
                    </tr>
                    <tr>
                        <td>借款期限：</td>
                        <td>
                            <input name="period" value="${borrowing.period}" type="text" class="easyui-numberbox" value="" data-options="width:180,required:'required',min:0,precision:0"></input>
                            <select name="periodUnit" class="easyui-combobox" style="width:60px;height:auto;">
                                [#list periodUnits as periodUnit]
                                    <option value="${periodUnit}" [#if borrowing.periodUnit==periodUnit]selected="selected"[/#if]>${periodUnit.displayName}</option>
                                [/#list]
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>基本利率：</td>
                        <td>
                            <input name="interestRate" value="${borrowing.interestRate}" type="text" class="easyui-numberbox" data-options="width: 180,required:'required',min:1,precision:2,suffix:''"></input>%/年
                        </td>
                    </tr>
                    <tr>
                        <td>奖励利率：</td>
                        <td>
                            <input name="rewardInterestRate" value="${borrowing.rewardInterestRate}" type="text" class="easyui-numberbox" data-options="width: 180,min:0,precision:2,suffix:''"></input>%/年
                        </td>
                    </tr>
                    <tr>
                        <td>借款描述：</td>
                        <td>
                            <textarea name="description" value="${borrowing.description}" ></textarea>
                        </td>
                    </tr>
                </table>
            </div>
            <div title="调查信息" data-options="closable:true" style="overflow:auto;padding:20px;">
                <table class="tbform">
                    <tr>
                        <td>借款用途：</td>
                        <td>
                            <textarea name="purpose" value="${borrowing.purpose}" placeholder="请输入您的借款用途，字数在1~21字以内"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td>实地调查：</td>
                        <td>
                            <textarea name="fieldInquiry" value="${borrowing.fieldInquiry}" ></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td>信用调查：</td>
                        <td>
                            <textarea name="creditInquiry" value="${borrowing.creditInquiry}" ></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td>还款调查：</td>
                        <td>
                            <textarea name="repaymentInquiry" value="${borrowing.repaymentInquiry}" ></textarea>
                        </td>
                    </tr>
                </table>
            </div>
            <div title="担保信息" style="padding:20px;">
                <table class="tbform">
                    <tr>
                        <td>担保方式：</td>
                        <td>
                            <select name="guaranteeMethod" class="easyui-combobox" data-options="width: 180,required:'required'">
                                [#list guaranteeMethods as guaranteeMethod]
                                    <option value="${guaranteeMethod}" [#if guaranteeMethod==borrowing.guaranteeMethod]selected="selected"[/#if]>${guaranteeMethod.displayName}</option>
                                [/#list]
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>担保公司：</td>
                        <td>
                            <input id="guaranteeCorp" name="guaranteeCorp" value="${borrowing.guaranteeCorp}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>担保措施：</td>
                        <td>
                            <textarea name="guarantee" value="${borrowing.guarantee}"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td>抵押物：</td>
                        <td>
                            <textarea name="collateral" value="${borrowing.collateral}"></textarea>
                        </td>
                    </tr>
                </table>
            </div>
            <div title="风控信息" style="padding:auto;">
                <table class="tbform">
                    <tr>
                        <td>风险度：</td>
                        <td>
                            <input name="riskDegree" value="${borrowing.riskDegree}" type="text" class="easyui-numberbox" data-options="width: 180,min:0,precision:2,suffix:''"></input>%
                        </td>
                    </tr>
                    <tr>
                        <td>信用评级：</td>
                        <td>
                            <select name="creditRating" class="easyui-combobox" data-options="width: 180,required:'required'">
                                [#list creditRatings as creditRating]
                                    <option value="${creditRating}" [#if creditRating==borrowing.creditRating]selected="selected"[/#if]>${creditRating.displayName}</option>
                                [/#list]
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>投资方式：</td>
                        <td>
                            <select name="investmentMethod" class="easyui-combobox" data-options="width: 180,required:'required'">
                                [#list investmentMethods as investmentMethod]
                                    <option value="${investmentMethod}" [#if investmentMethod==borrowing.investmentMethod]selected="selected"[/#if]>${investmentMethod.displayName}</option>
                                [/#list]
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>最低投资：</td>
                        <td>
                            <input name="investmentMinimum" value="${borrowing.investmentMinimum}" type="text" class="easyui-numberbox" data-options="width: 180,required:'required',min:0,precision:2,prefix:'￥'"></input>
                        </td>
                    </tr>
                    <tr>
                        <td>加价幅度：</td>
                        <td>
                            <input name="multiple" value="${borrowing.multiple}" type="text" class="easyui-numberbox" data-options="width: 180,required:'required',min:0"></input>
                        </td>
                    </tr>
                    <tr>
                        <td>最高投资：</td>
                        <td>
                            <input name="investmentMaximum" value="${borrowing.investmentMaximum}" type="text" class="easyui-numberbox" data-options="width: 180,required:'required',min:0,precision:2,prefix:'￥'"></input>
                        </td>
                    </tr>
                    <tr>
                        <td>投资开始日期：</td>
                        <td>
                            <input name="investmentStartDate" value="${borrowing.investmentStartDate}" type="text" class="easyui-datebox" required="required"></input>
                        </td>
                    </tr>
                    <tr>
                        <td>投资结束日期：</td>
                        <td>
                            <input name="investmentEndDate" value="${borrowing.investmentEndDate}" type="text" class="easyui-datebox" required="required"></input>
                        </td>
                    </tr>
                    <tr>
                        <td>投资返利率：</td>
                        <td>
                            <input name="investmentRebateRate" value="${borrowing.investmentRebateRate}" type="text" class="easyui-numberbox" data-options="width: 180,required:'required',min:0,precision:2,suffix:''"></input>%
                        </td>
                    </tr>
                    <tr>
                        <td>投资密码：</td>
                        <td>
                            <input name="investmentPassword" value="${borrowing.investmentPassword}" type="text" class="easyui-validatebox" data-options="width:180,required:'required',validType:'length[2,20]'"/>
                        </td>
                    </tr>
                    <tr>
                        <td>出借时间：</td>
                        <td>
                            <select name="lendingTime" class="easyui-combobox" data-options="width: 180,required:'required'">
                                [#list lendingTimes as lendingTime]
                                    <option value="${lendingTime}" [#if lendingTime==borrowing.lendingTime]selected="selected"[/#if]>${lendingTime.displayName}</option>
                                [/#list]
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>还款方式：</td>
                        <td>
                            <select name="repaymentMethod" class="easyui-combobox" data-options="width: 180,required:'required'">
                                [#list repaymentMethods as repaymentMethod]
                                    <option value="${repaymentMethod}" [#if repaymentMethod==borrowing.repaymentMethod]selected="selected"[/#if]>${repaymentMethod.displayName}</option>
                                [/#list]
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>借款服务费率：</td>
                        <td>
                            <input name="feeRate" value="${borrowing.feeRate}" type="text" class="easyui-numberbox" data-options="width: 180,required:'required',min:0,precision:2,suffix:''"></input>%
                        </td>
                    </tr>
                    <tr>
                        <td>还款服务费收取方式：</td>
                        <td>
                            <select name="repaymentFeeMethod" class="easyui-combobox" data-options="width: 180,required:'required'">
                                [#list repaymentFeeMethods as repaymentFeeMethod]
                                    <option value="${repaymentFeeMethod}" [#if repaymentFeeMethod==borrowing.repaymentFeeMethod]selected="selected"[/#if]>${repaymentFeeMethod.displayName}</option>
                                [/#list]
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>还款服务费率：</td>
                        <td>
                            <input name="repaymentFeeRate" value="${borrowing.repaymentFeeRate}" type="text" class="easyui-numberbox" data-options="width: 180,required:'required',min:0,precision:2,suffix:''"></input>%
                        </td>
                    </tr>
                    <tr>
                        <td>回收服务费率：</td>
                        <td>
                            <input name="recoveryFeeRate" value="${borrowing.recoveryFeeRate}" type="text" class="easyui-numberbox" data-options="width: 180,required:'required',min:0,precision:2,suffix:''"></input>%
                        </td>
                    </tr>
                    <tr>
                        <td>转入方服务费率：</td>
                        <td>
                            <input name="inTransferFeeRate" value="${borrowing.inTransferFeeRate}" type="text" class="easyui-numberbox" data-options="width: 180,required:'required',min:0,precision:2,suffix:''"></input>%
                        </td>
                    </tr>
                    <tr>
                        <td>转出方服务费率：</td>
                        <td>
                            <input name="outTransferFeeRate" value="${borrowing.outTransferFeeRate}" type="text" class="easyui-numberbox" data-options="width: 180,required:'required',min:0,precision:2,suffix:''"></input>%
                        </td>
                    </tr>
                    <tr>
                        <td>逾期利率：</td>
                        <td>
                            <input name="overdueInterestRate" value="${borrowing.overdueInterestRate}" type="text" class="easyui-numberbox" data-options="width: 180,required:'required',min:0,precision:2,suffix:''"></input>%/天
                        </td>
                    </tr>
                    <tr>
                        <td>严重逾期开始期限：</td>
                        <td>
                            <input name="seriousOverdueStartPeriod" value="${borrowing.seriousOverdueStartPeriod}" type="text" class="easyui-numberbox" data-options="width: 180,required:'required',min:0,suffix:''"></input>天
                        </td>
                    </tr>
                    <tr>
                        <td>严重逾期利率：</td>
                        <td>
                            <input name="seriousOverdueInterestRate" value="${borrowing.seriousOverdueInterestRate}" type="text" class="easyui-numberbox" data-options="width: 180,required:'required',min:1,precision:2,suffix:''"></input>%/天
                        </td>
                    </tr>
                    <tr>
                        <td>风险分析：</td>
                        <td>
                            <textarea name="riskAnalysis" value="${borrowing.riskAnalysis}"></textarea>
                        </td>
                    </tr>
                </table>
            </div>
            <div title="材料信息" style="padding:auto;">
                <table class="tbform">

                </table>
            </div>


            <div title="筹备意见" data-options="iconCls:'icon-add',closable:true" style="padding:auto;">
                <table class="tbform">
                    <tr>
                        <td>筹备状态：</td>
                        <td>
                            <select name="prepareState" class="easyui-combobox">
                                <option value="inquiring">待调查</option>
                                <option value="confirming">待审批</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>筹备意见：</td>
                        <td>
                            <textarea name="opinion" ></textarea>
                        </td>
                    </tr>
                </table>
            </div>

        </div>
    </form>
</div>

<script type="text/javascript">

    --]
[#--$(document).ready(function(){--][#--

        --]
[#--var action = ${action};--][#--

        --]
[#--console.log(action);--][#--

        --]
[#--if(action=='view'){--][#--

--]
[#--//        $("#borrowingForm :input").attr("readonly", "readonly"); //对form里面的禁用--][#--

            --]
[#--$("#borrowingForm textarea").attr("disabled", "disabled"); //对form里面的禁用--][#--

            --]
[#--$("#borrowingForm select").attr("disabled", "disabled"); //对form里面的禁用--][#--

            --]
[#--$("#borrowingForm input").attr("disabled", "disabled"); //对form里面的禁用--][#--

        --]
[#--}else{--][#--

            --]
[#--$("#borrowingForm textarea").attr("disabled", ""); //对form里面的禁用--][#--

            --]
[#--$("#borrowingForm select").attr("disabled", ""); //对form里面的禁用--][#--

            --]
[#--$("#borrowingForm input").attr("disabled", ""); //对form里面的禁用--][#--

        --]
[#--}--][#--

    --]
[#--});--][#--


    //借款人
    $('#borrower').combobox({
        width: 180,
        method: 'GET',
        url: '${ctx}/mng/borrowing/borrower/json',
        valueField: 'id',
        textField: 'name',
    });

    //担保公司
    $('#guaranteeCorp').combobox({
        width: 180,
        method: 'GET',
        url: '${ctx}/mng/borrowing/guaranteeCorp/json',
        valueField: 'id',
        textField: 'name',
    });

    //表单提交
    $('#borrowingForm').form({
        onSubmit: function () {
            var isValid = $(this).form('validate');
            return isValid;	// 返回false终止表单提交
        },
        success: function (data) {
            data = JSON.parse(data);
            successTip(data, orgDg, d);
        }
    });

</script>
[/@template]--]
