[#--借款 调查信息--]
<div class="easyui-panel" data-options="height:460,border:false">
    <table class="tbform">
        <tr>
            <td>借款用途：</td>
            <td>
                <textarea name="purpose" placeholder="请输入您的借款用途，字数在1~21字以内">${borrowing.purpose!""}</textarea>
            </td>
        </tr>
        <tr>
            <td>实地调查：</td>
            <td>
                <textarea name="fieldInquiry">${borrowing.fieldInquiry!""}</textarea>
            </td>
        </tr>
        <tr>
            <td>信用调查：</td>
            <td>
                <textarea name="creditInquiry">${borrowing.creditInquiry!""}</textarea>
            </td>
        </tr>
        <tr>
            <td>还款调查：</td>
            <td>
                <textarea name="repaymentInquiry" >${borrowing.repaymentInquiry!""}</textarea>
            </td>
        </tr>
        <tr>
            <td>交易合同编号：</td>
            <td>
                <input name="tradingContractNo" value="${borrowing.tradingContractNo}" type="text" class="easyui-textbox" data-options="validType:'length[2,50]'"/>
            </td>
        </tr>
        <tr>
            <td>商业保理合同编号：</td>
            <td>
                <input name="commercialFactoringContractNo" value="${borrowing.commercialFactoringContractNo}" type="text" class="easyui-textbox" data-options="validType:'length[2,50]'"/>
            </td>
        </tr>
        <tr>
            <td>商业保理公司营业执照号：</td>
            <td>
                <input name="factoringComBussinessNo" value="${borrowing.factoringComBussinessNo}" type="text" class="easyui-textbox" data-options="validType:'length[2,50]'"/>
            </td>
        </tr>
        <tr>
            <td>商业保理合同中应收账款：</td>
            <td>
                <input name="receivables" value="${borrowing.receivables}" type="text" class="easyui-numberbox" value="" data-options="min:0,precision:2,prefix:'￥'"></input>
            </td>
        </tr>
    </table>
</div>