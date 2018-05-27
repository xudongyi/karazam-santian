<div class="mui-content">
    <h1 class="mui-content-padded"></h1>
    <h1 class="title-1">债权转让及受让协议</h1>
    <div class="instr">甲方（债权受让人）：<span style="color: gray">${investRealName}</span></div>
    <div class="instr">身份证号/机构代码：${investIdNo}</div>
    <div class="instr">乙方（债务人）:<span style="color: gray">[#if borrowingType=="GENERAL"]${secrecy("username",borrowingRealName)}[#else ]${secrecy("corpName",borrowingCorpName)}[/#if]</span></div>
    <div class="instr">身份证号/机构代码：[#if borrowingType=="GENERAL"]${secrecy("idNo",borrowingIdNo)}[#else ]${secrecy("corpLicenseNo",borrowingCorpLicenseNo)}[/#if]</div>
    <div class="instr">
        丙方(债权转让人)：[#if transferType=="GENERAL"]${secrecy("username",transferRealName)}[#else ]${secrecy("corpName",transferCorpName)}[/#if]
        <span style="color: gray"> （详见拍拖贷《借款协议》电子合同）</span></div>
    <div class="instr"> 丙方代理人（居间服务人）：拍拖贷</div>
    <br>
    <ul class="instr-2">
        <li class="no-1">
            <p>1、乙方于<span class="xian">${year}</span> 年<span class="xian"> ${mouth}</span> 月<span class="xian"> ${day}</span> 日通过拍拖贷的居间服务向出借人借款并以电子数据形式于拍拖贷网站签订电子合同《借款协议》。乙方确认该《借款协议》是乙方真实意思表示并有效签订的合同，且乙方已经收到出借人通过拍拖贷支付的全部出借款项。</p>
            <p>2、按照上述《借款协议》的约定，出借人委托拍拖贷负责上述借款的债权转让事宜，包括签订债权转让相关协议及债权转让通知等。乙方亦明确知晓并认可拍拖贷的受委托权限。</p>
            <p>3、现拍拖贷依照委托，将出借人对借款人因上述《借款协议》而享有的债权及一切附随权利，转让给甲方。乙方确认在本协议上签字即表示已收到债权转让通知并认可债权转让的效力。</p>
            <p>4、自乙方签署本通知书及协议日起，乙方应按原《借款协议》的约定向甲方按期还本付息，如逾期乙方应依逾期情况按约定向甲方支付各项罚息、违约金等款项。乙方应将全部款项支付至甲方的平台账户中，并以甲方所在地为本协议履行地。
                按原《借款协议》约定及乙方实际还款情况，甲乙双方确认：截止本协议签订之日，乙方尚欠甲方本金人民币<span class="xian">${totalCurrentClaimTotalPrice}</span>元（大写：${numToRMB(totalCurrentClaimTotalPrice)!"￥XXXX.XX"} ），尚欠利息<span class="xian">${repayingInterest}</span>元（大写：${numToRMB(repayingInterest)!"￥XXXX.XX"} ）。
            </p>
            <p>5、乙方应承担甲方为实现债权而发生的一切费用（包括但不限于诉讼费、财产保全费、律师费、差旅费、执行费、评估鉴定费、拍卖费等）。为保障上述所有债务的履行，乙方应向甲方提供担保。如乙方此前已与甲方签订包括最高额抵押合同在内的抵押、质押合同的，则甲方有权依约定优先受偿。</p>
            <p>6、凡因本协议导致诉讼的，由当地人民法院管辖。</p>
        </li>
    </ul>
    <ul class="instr-2">
        <div style="float: right;margin-bottom: 20px">
            <p>签订时间：
                <span class="xian">${year}</span>年
                <span class="xian">${mouth}</span>月
                <span class="xian">${day}</span>日
            </p>
        </div>
    </ul>
</div>