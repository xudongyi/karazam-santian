<div class="mui-content">
    <h1 class="mui-content-padded"></h1>
    <h1 class="title-1">债权转让协议</h1>
    <div class="instr">甲方（债权受让人）：
        <span style="color: gray">
        [#if investType=="GENERAL"]
                ${secrecy("fullName",investRealName)}
            [#else ]
        ${secrecy("corpName",investCorpName)}
        [/#if]

        </span></div>
    <div class="instr">身份证号/机构代码：
    [#if investType=="GENERAL"]
    ${secrecy("idNo",investIdNo)}
    [#else ]
    ${secrecy("corpLicenseNo",investCorpLicenseNo)}
    [/#if]

    </div>
    <div class="instr">乙方（债务人）:
        <span style="color: gray">
        [#if borrowingType=="GENERAL"]
                ${secrecy("fullName",borrowingRealName)}
            [#else ]
        ${secrecy("corpName",borrowingCorpName)}
        [/#if]
        </span></div>
    <div class="instr">身份证号/机构代码：
    [#if borrowingType=="GENERAL"]
    ${secrecy("idNo",borrowingIdNo)}
    [#else ]
    ${secrecy("corpLicenseNo",borrowingCorpLicenseNo)}
    [/#if]
    </div>
    <div class="instr">居间服务人：<span style="color: gray">拍拖贷</span></div>
    <br>
    <ul class="instr-2">
        <li class="no-1">
            <p>1、乙方于<span class="xian">${year}</span>年<span class="xian">${mouth}</span>月<span class="xian">${day}</span>日通过拍拖贷的居间服务与借款人签订《借款协议》并以电子数据形式存证于拍拖贷平台。乙方确认该《借款协议》是乙方真实意思表示并有效签订的合同，且借款人已经收到乙方通过拍拖贷支付的全部出借款项，该借款协议项下的债权真实、合法、有效。</p>
            <p>2、按照上述编号<span class="xian">${contractNo}</span>《借款协议》的约定，乙方通过拍拖贷居间服务将上述债权转让给甲方，拍拖贷根据《借款协议书》的约定完成债权转让通知及相关事宜。甲、乙双方均明确知晓并认可拍拖贷的受托权限。</p>
            <p>3、甲、乙双方需按照拍拖贷平台的收费标准向其支付居间服务费、账户管理费等，甲、乙并不可撤销地确认授权拍拖贷代扣相关费用。</p>
            <p>4、自甲、乙双方签署本协议及债权转让通知送达借款人后，借款人即按原《借款协议》的约定向甲方按期还本付息。根据《借款协议》及借款人的还款情况，转让债权的具体情况如下： 借款人：<span class="xian">[#if borrowingType=="GENERAL"]${secrecy("fullName",borrowingRealName)}[#else ]${secrecy("corpName",borrowingCorpName)}[/#if]</span>；
                债权转让本金：<span class="xian">${totalCurrentClaimTotalPrice}</span>元（人民币）；利率<span class="xian">${interestRate}%</span>（年）；剩余期限<span class="xian">${surplusPeriod}</span>${periodUnit}。</p>
            <p>5、借款人未按约定还款的，借款人应承担甲方或其他债权受让方为实现债权而发生的一切费用（包括但不限于诉讼费、财产保全费、律师费、差旅费、执行费、评估鉴定费、拍卖费等）。</p>
            <p>6、本协议未约定内容，以拍拖贷注册协议及《借款协议》约定为准。</p>
            <p>7、凡因本协议导致诉讼的，由当地人民法院管辖。</p>
            <p>8、本协议由甲方、乙方在拍拖贷平台点击确认后生效。</p>
        </li>
    </ul>
    <ul class="instr-2">
        <div style="float: right;margin-bottom: 20px">
            <p>居间服务人：<span class="xian">拍拖贷</span></p>
            <p>签订时间：
                <span class="xian" style="width:70px; min-width:inherit;">${year}</span>年
                <span class="xian" style="width:30px; min-width:inherit;">${mouth}</span>月
                <span class="xian" style="width:30px; min-width:inherit;">${day}</span>日
            </p>
        </div>
    </ul>
</div>