<div class="mui-content" xmlns="http://www.w3.org/1999/html">
    <h1 class="mui-content-padded"></h1>
    <h1 class="title-1">债权转让及受让协议</h1>
    <div class="instr">甲方（债权受让人）：<span style="color: gray">${investRealName}</span></div>
    <div class="instr">身份证号/机构代码：${investIdNo}</div>
    <div class="instr">乙方（债务人）:<span style="color: gray">[#if borrowingType=="GENERAL"]${secrecy("username",borrowingRealName)}[#else ]${secrecy("corpName",borrowingCorpName)}[/#if]</span></div>
    <div class="instr">身份证号/机构代码：[#if borrowingType=="GENERAL"]${secrecy("idNo",borrowingIdNo)}[#else ]${secrecy("corpLicenseNo",borrowingCorpLicenseNo)}[/#if]</div>

    <br>
    <div class="instr">
        就甲方通过深圳市三田雍泓互联网金融服务有限公司（“智链金融”）所有和负责运营的网络平台（网址：www.isantian.com，下称“智链金融网站”）向乙方转让债权相关事宜，经双方友好协商，达成约定如下：
    </div>
    <br>
    <ul class="instr-2">
        <h4 class="title-2">第一条债权转让内容</h4>

        <li class="no-1">

            <p>1.1 本协议项下的转让债权为发布在智链金融网站上的融资标的《${contractName}》；发布时间：${year}年${mouth}月${day}日。</p>
            <p>1.2 甲方同意按本协议的条款和条件向乙方转让如下债权（“标的债权”），乙方同意按本协议的条款和条件从甲方受让标的债权。标的债权的具体信息如下：</p>

            <table class="table">
                <thead>
                <tr>

                    <td>
                        借款人
                    </td>
                    <td>
                    [#if borrowingType=="GENERAL"]${secrecy("fullName",borrowingRealName)}[#else ]${secrecy("corpName",borrowingCorpName)}[/#if]
                    </td>
                </tr>
                <tr>

                    <td>
                        借款本金金额（人民币）

                    </td>
                    <td>
                        <span class="xian">${borrowingAmount}</span>元，大写:
                        <span class="xian">${numToRMB(borrowingAmount)!"￥XXXX.XX"}</span>
                    </td>
                </tr>
                <tr>

                    <td>
                        借款利率（年利率）
                    </td>
                    <td>
                    ${interestRate}%
                    </td>
                </tr>
                <tr>

                    <td>
                        原借款还款方式
                    </td>
                    <td>
                        ${repaymentMethod}
                    </td>
                </tr>
                <tr>

                    <td>
                        原借款期限
                    </td>
                    <td>
                        <span class="xian">${period}</span>${periodUnit}
                    </td>
                </tr>
                <tr>

                    <td>
                        剩余期限
                    </td>
                    <td>
                        <span class="xian">${surplusPeriod}</span>${periodUnit}
                    </td>
                </tr>
                <tr>

                    <td>
                        还款时间及期数
                    </td>
                    <td>
                        ${planRepaymentStr}
                    </td>
                </tr>

                </thead>

            </table>
        </li>
    </ul>
    <h4 class="title-2">第二条债权转让价款及支付</h4>
    <p>2.1 甲乙双方约定标的债权的转让价款为￥${totalCurrentClaimTotalPrice}元（“债权转让价款”）。转让管理费用为￥${outFee}元。</p>
    <p>2.2 自签订本协议之日起，乙方应保证账户余额足以支付本协议项下债权转让价款，且不可撤销地授权智链金融自行或通过合作的第三方支付机构或/和合作的金融机构，从乙方账户中将债权转让价款支付给甲方，转让管理费用从乙方支付款项中由智链金融自动扣取。</p>
    <h4 class="title-2">第三条债权转让流程</h4>
    <p>3.1 本协议项下全部转让价款划转完成即视为本协议生效且标的债权转让成功；同时甲方不可撤销地授权智链金融将记录的甲方与标的债权借款人签署的电子文本形式的相关协议及借款人相关信息通过智链金融平台向乙方提供查阅服务。</p>
    <p>3.2 本协议生效且标的债权转让成功后，双方特此委托智链金融将标的债权转让事宜通知标的债权的借款人，通知方式包括但不限于口头、电子邮件、短信、在标的债权借款人智链金融平台账户显示等任何一种形式。</p>
    <p>3.3 自标的债权转让成功之日起，乙方成为标的债权的债权人，承继原借款协议项下出借人的权利并承担出借人的义务。</p>
    <h4 class="title-2">第四条陈述、保证和承诺</h4>
    <p>4.1 甲方承诺并保证其系依法设立并有效存续的实体或系具有完全民事行为能力的自然人，有权实施本协议项下的债权转让并能够独立承担民事责任。其转让的债权系合法、有效的债权。</p>
    <p>4.2 乙方承诺并保证其系依法设立并有效存续的实体或系具有完全民事行为能力的自然人，有权受让本协议项下的债权并能独立承担民事责任；其受让本协议项下的债权已经获得其内部相关权力机构的授权或批准（如适用）。乙方进一步承诺并保证，其用于受让标的债权的资金来源合法，乙方是该资金的合法所有人，如果第三方对乙方受让标的债权的资金归属、支配权、合法性等问题提出异议，乙方应自行承担相关责任，给本协议其他方造成损失的，乙方应当赔偿损失。</p>
    <h4 class="title-2">第五条违约责任</h4>
    <p> 各方同意，如果一方违反其在本协议中所作的陈述、保证、承诺或任何其他义务，致使其他方遭受或发生损害、损失、索赔、处罚、诉讼仲裁、费用、义务和/或责任，违约方须向另一方作出全面赔偿并使之免受其害。</p>
    <h4 class="title-2">第六条其他规定</h4>
    <p> 6.1 对本协议所作的任何修改及补充必须采用书面形式并由各方合法授权代表签署。</p>
    <p> 6.2 本协议的订立、效力、解释、履行、及与本协议有关的争议解决均适用中华人民共和国法律（为本协议之目的，不含香港特别行政区、澳门特别行政区及台湾地区）。</p>
    <p> 6.3  在本协议履行过程中发生的纠纷，双方应友好协商解决；协商不成的，任何一方均有权向【深圳市前海合作区】人民法院提起诉讼。</p>
    <p> 6.4 本协议一式三份，甲乙双方以及智链金融各执一份，具有同等效力。</p>
    <p> 6.5 本协议经各方以电子形式签署后，于乙方向甲方支付债权转让价款之日起生效。</p>
    <p class="nextpage">&nbsp;</p>

    <div class="instr">甲方（债权受让方）：
        <span style="color: gray">
        [#if investType=="GENERAL"]
                ${investRealName}
            [#else ]
        ${investCorpName}
        [/#if]

        </span></div>
    <div class="instr">身份证号/统一社会信用代码：
    [#if investType=="GENERAL"]
    ${investIdNo}
    [#else ]
    ${investCorpLicenseNo}
    [/#if]

    </div>
    </br>
    <div class="instr">乙方（债权出让方）:
        <span style="color: gray">
        [#if borrowingType=="GENERAL"]
                ${secrecy("fullName",borrowingRealName)}
            [#else ]
        ${secrecy("corpName",borrowingCorpName)}
        [/#if]
        </span>
    </div>
    <div class="instr" style="margin-bottom: 50px">身份证号/统一社会信用代码：
    [#if borrowingType=="GENERAL"]
    ${secrecy("idNo",borrowingIdNo)}
    [#else ]
    ${secrecy("corpLicenseNo",borrowingCorpLicenseNo)}
    [/#if]
        </br>
        <p>签订时间：
            <span class="xian">${year}</span>年
            <span class="xian">${mouth}</span>月
            <span class="xian">${day}</span>日
        </p>
    </div>

</div>