<p class="mui-content">
    <h1 class="mui-content-padded">
        <h1 class="title-1">借款协议</h1>
<p class="number">合同编号：${contractNo}</p>
<br/>
<p class="instr">甲方(出借人)：
 [#if borrowing.borrower==currentUser.id]
    [#--[#if borrowUserType=="GENERAL"]${borrowRealName}[#else ]${borrowCorpName}[/#if]</p>--]
 [#else ]
     [#if investmentType=="GENERAL"]${realName}[#else ]${corpName}[/#if]</p>
 [/#if]
 <br/>
<table class="table">
    <thead>
    <tr>
        <th>拍拖贷用户名</th>
        <th>借款本金(元)</th>
        <th>借款期限</th>
        <th>年利率(%)</th>
        <th>应收本息</th>
    </tr>
    [#if investments != null && investments?size gt 0]
        [#list investments as investment]
        [#--[#if recovery.parent??]
        [#else]--]
        <tr>
            [#if investment.investor==currentUser.id]
                <td align="left">${investment.investorLoginName}</td>
            [#else ]
                <td align="left">${secrecy("mobile",investment.investorLoginName)}</td>
            [/#if]
            <td align="left">${investment.amount?string("currency")}</td>
            <td align="left">${borrowing.period}${borrowing.periodUnitDes}</td>
            <td align="left">${borrowing.realInterestRate}</td>
            <td align="left">${investment.planIncome?string("currency")}</td>
        </tr>
        [#--[/#if]--]
        [/#list]
    [#else]
    <tr>
        <td>XXXX</td>
        <td>￥XXXX.XX</td>
        <td>XX个月</td>
        <td>XX.XX%/年</td>
        <td>￥XXXX.XX</td>
    </tr>
    [/#if]
    </thead>
</table>
<ul class="egalPerson">

    <li>乙方(借款人)：
    [#if borrowing.borrower!=currentUser.id]
        [#if borrowUserType=="GENERAL"]${secrecy("fullName",borrowRealName)}
            [#else ]${secrecy("corpName",borrowCorpName)}
        [/#if]
    [#else ]
        [#if borrowUserType=="GENERAL"]${borrowRealName}
        [#else ]${borrowCorpName}
        [/#if]
    [/#if]

    </li>
    <li>
        拍拖贷用户名：
    [#if borrowing.borrower!=currentUser.id]
         ${secrecy("mobile",borrowLoginName)}
    [#else ]
        ${borrowLoginName}
    [/#if]
    </li>
    [#if borrowUserType=="GENERAL"]
    <li>
        [#if borrowing.borrower!=currentUser.id]
        身份证号码：${secrecy("idNo",borrowIdNo)}
        [#else ]
            身份证号码：${borrowIdNo}
        [/#if]
    </li>
    [#else ]
        <li>营业执照号：
        [#if borrowing.borrower!=currentUser.id]
        ${secrecy("corpLicenseNo",borrowCorpLicenseNo)}
        [#else ]
        ${borrowCorpLicenseNo}
        [/#if]
        </li>
    [/#if]
</ul>
<br/>
<ul class="clearfix legalPerson-2">
    <li>丙方（平台方）：青海高领网络科技有限公司</li>
</ul>
<ul class="instr-2">
    <li>鉴于：</li>
    <li class="no-1"><p>
        1、甲、乙双方均为具有完全民事权利能力和民事行为能力的自然人、法人或其他组织，且甲、乙双方均为丙方运营管理的拍拖贷平台【指拍拖贷网站（www.paituodai.com）及其移动应用APP、微信客户端等，下称“拍拖贷平台”】注册用户，并承诺所提供给丙方的信息真实、有效；</p>
        <p>2、丙方是依法成立且有效存续的有限责任公司，运营管理拍拖贷平台，为甲、乙方的交易提供居间信息服务。</p>
        <p>3、乙方有借款需求，甲方有合法所得的自有资金予以出借，双方有意成立借贷关系。</p></li>
</ul>
<p class="instr mt-30 mb-30">为此，经各方协商一致，于
    <span class="xian-2">${contractYear}</span>年
    <span class="xian-2">${contractMonth}</span>月
    <span class="xian-2">${contractDay}</span>日签订如下协议，以共同遵照履行：
</p>
<ul class="instr-2">
    <li>
        <h4 class="title-2">第一条 借款基本信息及费用</h4>
        <p class="instr">1.1 借款明细情况</p>
        <table width="100%" class="table-2 mb-30">
            <thead>
            <tr>
                <th scope="row" style="width: 120px">合同金额</th>
                <td colspan="5">
                    人民币<span class="xian">${amount}</span>元，大写:
                    <span class="xian">${numToRMB(amount)!"￥XXXX.XX"}</span><br/>
                    （各出借人出借金额详见本协议文首表格）
                </td>
            </tr>
            <tr>
                <th scope="row">年利率(%)</th>
                <td colspan="5">&nbsp;${realInterestRate}%</td>
            </tr>
            <tr>
                <th scope="row">借款期限</th>
                <td colspan="5">
                    <span class="xian-2">${period}</span>${periodUnitDes}, 自
                    <span class="xian-2">${contractBeginDateYear}</span>年
                    <span class="xian-2">${contractBeginDateMonth}</span>月
                    <span class="xian-2">${contractBeginDateDay}</span>日至
                    <span class="xian-2">${contractEndDateYear}</span>年
                    <span class="xian-2">${contractEndDateMonth}</span>月
                    <span class="xian-2">${contractEndDateDay}</span>日
                </td>
            </tr>
            <tr>
                <th scope="row">借款用途</th>
                <td colspan="5">&nbsp;${purpose}</td>
            </tr>
            <tr>
                <th scope="row">还款日</th>
                <td colspan="5">
                    自<span class="xian-2">${contractBeginDateYear}</span>
                    年<span class="xian-2">${contractBeginDateMonth}</span>
                    月<span class="xian-2">${contractBeginDateDay}</span>日起，每月
                    <span class="xian-2">${mouthRepaymentDay}</span>日（24:00前,节假日不顺延）。如该自然月无对应日，则为该月最后一日，最后一期以实际借款期限为准。
                </td>
            </tr>
            <tr>
                <th scope="row">还款方式</th>
                <td colspan="5">${repaymentMethod}</td>
            </tr>
            <tr>
                [#if isBorrowing]
                    <th rowspan="${repaymentsSize}">还款计划</th>
                [#else ]
                    <th rowspan="${repaymentPlanSize}">还款计划</th>
                [/#if]
                    <td>
                        <table style="margin: 0">
                            <tr>
                                <th>期数</th>
                                <th>还款时间</th>
                                <th>还款本金（人民币）</th>
                                <th>还款利息（人民币）</th>
                                <th>还款本息（人民币）</th>
                            </tr>
                        [#if isBorrowing]
                            [#list repayments as repayment]
                                <tr>
                                    <td>第${repayment.period}期/共${periods}期</td>
                                    <td>[#if repayment.paidDate??]
                                        （实际）${repayment.paidDate?string("yyyy年MM月dd日")}[#else]${repayment.payDate?string("yyyy年MM月dd日")}[/#if]</td>
                                    <td>${repayment.capital?string("currency")}</td>
                                    <td>${repayment.interest?string("currency")}</td>
                                    <td>${repayment.capitalInterest?string("currency")}</td>
                                </tr>
                            [/#list]
                        [#else ]
                            [#list repaymentPlans as repaymentPlan]
                                <tr>
                                    <td>第${repaymentPlan.repaymentRecordPeriod}期/共${periods}期</td>
                                    <td>[#if repaymentPlan.paidDate??]
                                        （实际）${repaymentPlan.paidDate?string("yyyy年MM月dd日")}[#else]${repaymentPlan.repaymentRecord.payDate?string("yyyy年MM月dd日")}[/#if]</td>
                                    <td>${repaymentPlan.repaymentRecord.capital?string("currency")}</td>
                                    <td>${repaymentPlan.repaymentRecord.interest?string("currency")}</td>
                                    <td>[#if repaymentPlan.state == "repaid"]
                                        （实际）${repaymentPlan.capitalInterestOverdueAhead?string("currency")}[#else]${repaymentPlan.capitalInterest?string("currency")}[/#if]</td>
                                </tr>
                            [/#list]
                        [/#if]
                        </table>
                    </td>
            </tr>
            </thead>
        </table>
        <p>
            注：上述借款明细中列明的每月偿还本息金额可能因借款期限不同而发生变化，或因借款利率的调整而调整。出借人、借款人和服务方不可撤销委托拍拖贷平台计算相关金额，并在拍拖贷平台公布或更新借款明细。上述借款明细中列明每月还款本息金额与拍拖贷平台公布或更新的还款明细不一致，以拍拖贷平台公布或更新的还款明细为准。</p>
        <p>
            　　乙方须在每月还款日或之前(不得迟于24:00)通过指定的第三方支付平台还款。
        </p>
    </li>
    <li>1.2 费用</li>
    <li class="no-1">
        1.2.1 平台服务费
        <p>
            丙方为促成甲乙双方的民间借贷交易，提供了相应的居间服务，乙方应于乙方平台账户收到借款本金当日向丙方一次性支付平台服务费，平台服务费收取标准根据借款类型、借款期限确定。根据本协议的借款类型与借款期限，平台服务费为人民币
            <span class="xian-2">${borrowServiceFee}</span>元，按照本协议借款本金总额的
            <span class="xian-2">${borrowing.feeRate}%</span>计算。
        </p>
    </li>
    <li class="no-1">
        1.2.2 平台管理费
        <p>除本协议第1.2.1条约定的平台服务费外，乙方应于本协议第1.1条约定的还款日向丙方支付平台管理费。收费标准为借款本金总额<span class="xian-2">${repayServiceFee}%</span>/月。
        </p>
    </li>
    <li class="no-1">
        1.2.3 其他费用
        <p>
            除本协议第1.2.1及1.2.2条约定的平台服务费及平台管理费外，甲、乙双方还应根据拍拖贷平台公布的网站资费向丙方支付其他相关费用（包括但不限于充值手续费、提现手续费、债权转让费、甲方出借咨询服务费等）。</p>
    </li>
    <li class="no-2">
        <h4 class="title-2">第二条 各方的权利和义务</h4>
        <p>2.1 甲方的权利和义务</p>
        <p>2.1.1
            除本协议另有约定外，甲方应于投标前确保其在拍拖贷平台的资金账户（以下简称“甲方平台账户”）中的资金余额不低于甲方拟投标/出借的借款本金金额。甲方在拍拖贷平台点击“投标”并输入相应出借金额后，甲方平台账户内的相应资金冻结，甲方不得提现或转账。甲方最终出借本金以实际划付至乙方指定收款账户的资金数额为准。</p>
        <p>2.1.2
            甲方在拍拖贷平台上点击“确认”即接受本协议条款后，不可撤销授权丙方/第三方支付机构在本协议涉及的投资标的成立后将甲方平台账户内的冻结资金划付给乙方指定收款账户,乙方指定收款账户收到借款本金即视为乙方收到借款本金，也即甲方出借成功，本协议正式生效。</p>
        <p>2.1.3在乙方发生逾期还款情形时，甲方授权丙方或丙方委托第三方代表甲方进行催收（包括但不限于短信、电话、上门催收、发律师函、将乙方逾期信息通报人民银行或P2P行业个人信用征信系统、提起诉讼或者仲裁等方式），乙方愿意接受前述催收方式所带来的不利影响，且同意甲方、丙方及丙方委托的第三方无需因催收行为向其他方披露乙方信息而承担责任。</p>
        <p>2.1.4 甲方不可撤销地授权丙方委托银行或第三方支付机构在还款日（不得迟于24:00）或之前从乙方平台账户划扣乙方应还款项（包括但不限于乙方正常还款、提前还款情形）至甲方平台账户。</p>
        <p>2.1.5 甲方应按照拍拖贷平台的规则及本协议约定支付相关费用。</p>
        <p>2.1.6 甲方应保管好其平台账户密码并注意查收任何其账户的站内信息、通知、邮件，若因账户密码保管不善导致的损失由甲方自行承担。</p>
        <p>2.2 乙方的权利和义务</p>
        <p>2.2.1
            乙方不可撤销地授权丙方委托银行或第三方支付机构根据本协议约定，于乙方指定收款账户收到借款本金当日将本协议第1.2.1条约定的平台服务费及乙方按照拍拖贷平台规则及本协议约定应当支付的费用划付至丙方指定账户。前述费用划扣完毕后，乙方对剩余资金可进行提现或其他合法操作。</p>
        <p>2.2.2
            乙方应按照本协议约定，在还款日（不得迟于24:00）或之前向与乙方平台账户内存入相应款项并执行“还款”操作，该等相应款项应包括但不限于月偿还本息、罚息（若有）、违约金（若有）、平台管理费（如有）等其他相关费用。</p>
        <p>
            2.2.3 乙方不可撤销地授权丙方委托银行或第三方支付机构于还款当日（包括提前还款）（不得迟于24:00）将乙方平台账户绑定的银行卡中等同于本协议第2.2.2条约定的相应款项的金额划转至丙方在第三方支付机构开立的收款账户，并将该等资金充值至乙方平台账户，其中等同于月偿还本息、罚息（若有）、违约金（若有）等金额，乙方不可撤销地授权丙方委托第三方支付机构将该等款项于同日划转至甲方平台账户，甲方即取得相应金额的提现权及其他合法处置权利；相应款项中的其他款项划转至丙方在第三方支付机构开立的收款账户即视为乙方支付完毕当月的平台管理费及其他费用。</p>
        <p>
            2.2.4 除本协议另有约定外，乙方不可撤销授权丙方，在乙方未按本协议第2.2.2条约定及时足额地向乙方平台账户存入相应款项或非因丙方原因导致丙方委托的银行或第三方支付机构无法扣划该等款项时，丙方有权随时划扣乙方平台账户或者与乙方平台账户绑定的银行卡账户内资金，直至乙方偿付完毕所有本金、利息、平台费用（如有）、违约金（如有）等一切乙方应付款项为止。</p>
        <p>2.2.5 乙方承诺，借款款项仅用于本协议第1.1条表内明确的借款用途，不得用于任何违法活动或者其他用途。否则甲方或丙方有权要求乙方提前清偿全部借款本金及利息。</p>
        <p>2.2.6 乙方应确保提供信息和资料的真实性，不提供虚假信息或隐瞒重要事项。</p>
        <p>2.2.7
            乙方借款全部清偿之前，乙方发生个人信息变更（包括但不限于乙方本人、乙方的家庭联系人及紧急联系人工作单位、居住地址、电话、手机号码、电子邮箱的变更），应在变更后三日内告知丙方。否则，乙方应当承担丙方或丙方委托的第三方所支出的相应的调查费用、律师费用及诉讼费用等费用,且应承担违约责任。</p>
        <p>2.2.8 乙方为甲方合作方推介的借款人的，乙方还须遵守其与甲方合作方之间的协议约定的权利与义务。</p>
        <p>2.2.9 乙方不得将本协议项下的任何权利义务转让给任何其他方。</p>
        <p>2.2.10 乙方应配合并确保甲方或甲方指定第三方可以定期对乙方的信用状况、还款能力进行调查。</p>
        <p>2.2.11 乙方应保管好其平台账户密码并注意查收其账户相关任何站内信息、通知、邮件，若因账户密码保管不善导致的损失由乙方自行承担。</p>
        <p>2.3 丙方的权利和义务</p>
        <p>2.3.1 丙方有权按照拍拖贷平台及本协议的约定向甲、乙方收取相关费用，不因甲、乙双方的任何纠纷受影响。</p>
        <p>2.3.2 申请提现金额将划转至申请人平台账户绑定的银行卡内。</p>
        <p>2.3.3 丙方接受甲、乙方委托行为所产生的法律后果，由相应委托方承担。如因乙方或甲方或其他方（包括但不限于技术问题）造成的延误或错误，丙方不承担任何责任。</p>
        <p>2.3.4 丙方应对甲方和乙方信息及本协议内容保密，如因相关权力部门要求（包括但不限于法院、仲裁机构、金融监管机构等），丙方有权披露。</p>
        <p>2.3.5 各方同意，丙方有权委托第三方支付公司代为划转相关款项，并享有转委托权。</p>

    </li>
    <li class="no-2">
        <h4 class="title-2">第三条 提前还款</h4>
        <p>3.1 各方确认，乙方有权按照丙方运营管理的拍拖贷平台规则提前偿还剩余借款，详见拍拖贷官网（www.paituodai.com）。</p>
        <p>3.2 乙方提前还款的，应按照如下顺序清偿：拖欠的平台相关费用、逾期罚息、违约金、拖欠的利息、拖欠的本金、正常的利息、正常的本金等相关款项。</p>
        <p>3.3 乙方结清本协议第3.2条约定的全部款项后，丙方退还乙方根据提前还款日期折算的剩余期次所对应的平台服务费（如有），否则，剩余平台服务费按照上述顺序用于抵偿相关应付款项，丙方不予退还任何费用。</p>
        <p>3.4 乙方为甲方合作方推介的借款人的，乙方提前还款还须遵守其与甲方合作方之间的协议约定。</p>
    </li>
    <li class="no-1">
        <h4 class="title-2">第四条 债权转让</h4>
        <p>
            经各方同意，经丙方审核通过，甲方可以通过拍拖贷平台将本协议项下的全部或部分债权转让给第三方。甲方债权转让完成后，相关债权转让情况将在拍拖贷披露，乙方可以在拍拖贷平台查询知悉，拍拖贷平台公示债权转让的行为将视为甲方就债权转让通知乙方的行为，且公示之日即视为通知乙方，而甲方无需另行再向乙方通知债权转让情况。甲方转让债权后，本协议项下其他条款不受影响，乙方仍应按照本协议第1.1.条约定向债权受让人偿还剩余借款并支付利息，并向丙方支付平台管理费，乙方不得以未看到债权转让通知为由拒绝还款。</p>
    </li>
    <li class="no-2">
        <h4 class="title-2">第五条 违约责任</h4>
        <p>5.1 协议各方均应严格履行合同义务，非经各方协商一致或依照本协议约定，任何一方不得解除本协议。</p>
        <p>5.2 任何一方违约，违约方应承担因违约使得其他各方产生的费用和损失，包括但不限于调查、诉讼费、律师费等。</p>
        <p>5.3
            若乙方在任一还款日（不得迟于24:00）未足额还款（包括当期借款本金、利息、平台管理费、罚息、违约金等乙方一切应付款项，下同），则视为逾期还款，构成违约。乙方自应还款之次日起按照下述计算方式向甲方支付罚息。</p>
        <p class="mb-20 mt-20">罚息 = 逾期借款本金×罚息利率×逾期天数（如出现严重逾期，超出天数则按照严重逾期天数计算）</p>
        <p>乙方足额还款之前，丙方有权根据拍拖贷平台规则调整罚息利率。丙方调整罚息利率的，将在拍拖贷平台进行公示，或通过乙方与丙方确认的其他方式告知乙方，相关公示或通知自公布或发送之日即生效，对乙方产生法律效力。</p>
        <p>5.4 若乙方逾期还款，乙方应按照下述计算方式向丙方支付逾期管理费。</p>
        <p class="mb-20 mt-20">逾期管理费 = 逾期借款本金×逾期管理费率×逾期天数</p>
        <table width="100%" class="table-2 mb-30">
            <thead>
            <tr>
                <th>逾期天数</th>
                <td>1-30天</td>
                <td>31天及以上</td>
            </tr>
            <tr>
                <th>逾期管理费率</th>
                <td>0.3%</td>
                <td>0.5%</td>
            </tr>
            </thead>
        </table>
        <p>乙方足额还款之前，丙方有权根据拍拖贷平台规则调整逾期管理费率。丙方调整逾期管理费率的，将在拍拖贷平台进行公示，或通过乙方与丙方确认的其他方式告知乙方，相关公示或通知自公布或发送之日即生效，对乙方产生法律效力。</p>
        <p>5.5 每期还款按照如下顺序清偿；当乙方存在多期逾期时，按到期先后顺序偿还：</p>
        <p>5.5.1 所有对丙方的应付费用（包括逾期管理费）；</p>
        <p>5.5.2 罚息、逾期管理费；</p>
        <p>5.5.3 拖欠的利息；</p>
        <p>5.5.4 拖欠的本金；</p>
        <p>5.5.5 正常的利息；</p>
        <p>5.5.6 正常的本金。</p>
        <p>5.6
            如乙方逾期还款满【30】日，甲方有权解除本协议，并授权丙方于前述期限届满之日向乙方发送解除通知，本协议项下的全部借款本金、利息及其他相关应付款项提前到期。乙方应在本协议解除之日起五日内一次性偿还剩余的全部借款本金、利息、平台管理费（包括逾期管理费）、罚息、违约金、以及其他费用。如本协议提前解除时，乙方平台账户里有任何余款的，丙方有权根据本协议约定指令支付机构将乙方账户内的余款划扣至甲方或丙方平台账户。</p>
        <p>5.7
            如乙方存在逃避、拒绝沟通或拒绝承认欠款事实等恶意行为，甲方有权解除本协议，并授权丙方在发现前述事项后向乙方发送解除通知，本协议项下的全部借款本金、利息及其他相关应付款项于解除通知送达乙方之日起提前到期，乙方应于本协议解除之日起五日内清偿尚未偿付部分借款本金、利息及其他应付款项。丙方有权将乙方违约失信的相关信息披露给媒体、乙方的用人单位、公安机关、检察院、法院、人民银行或P2P行业个人信用征信系统以及根据拍拖贷平台逾期黑名单的规则纳入逾期黑名单。</p>
        <p>5.8
            若乙方提供虚假资料或隐瞒重要事实，甲方有权解除本协议，并授权丙方在发现前述事项后向乙方发送解除通知，本协议项下的全部借款本金、利息及其他相关应付款项于解除通知送达乙方之日起提前到期，乙方应在本协议解除之日起五日内一次性支付全部剩余款项（包括但不限于借款本金、利息、平台管理费、罚息、违约金以及其他费用）并按借款金额的10%向丙方支付违约金。乙方已支付给丙方的费用不予退还。</p>
        <p>5.9 本协议中组成甲方的全部出借人与乙方之间的借款均相互独立，一旦乙方逾期还款的，任何一个出借人有权单独向乙方追索或者提起诉讼。如乙方逾期支付丙方款项的，丙方亦可单独向乙方追索或者提起诉讼。</p>
        <p>5.10 乙方在根据本协议借款后向第三方借款或为第三方提供担保，须事先取得丙方书面同意。否则，甲方授权丙方有权立即终止本协议，并要求乙方立即归还剩余未到期借款本息，同时支付丙方剩余的平台管理费。</p>
        <p>5.11 甲方授权丙方代为作出是否解除本协议的决定、代为向乙方发出本协议解除通知、代为办理提前终止协议手续和提前催收工作。</p>
        <p>5.12 若乙方已构成违约或根据甲方、丙方的合理判断乙方可能发生违约事件，甲方授权丙方可根据丙方判断采取下列一项或几项措施：</p>
        <p>5.12.1 立即暂停、取消发放全部或部分借款；</p>
        <p>5.12.2 解除本协议；</p>
        <p>5.12.3 采取有效的救济措施。</p>
        <p>5.13 在乙方清偿全部款项（本协议项下借款本金、利息、平台管理费、罚息、违约金等乙方应支付的其他款项）前,罚息及逾期管理费不停止计算。 </p>

    </li>
    <li class="no-1">
        <h4 class="title-2">第六条 法律及争议解决</h4>
        <p>
            本协议的签订、履行、终止、解释均适用中华人民共和国法律，因本协议引起的或与本协议有关的任何争议，首先应当通过友好协商解决。各方协商不成或协商时间超过15个工作日，任何一方均有权向青海省西宁市城西区人民法院提请诉讼。诉讼费用、律师费以及其他相关费用，由败诉方承担。</p>
    </li>
    <li class="no-2">
        <h2 class="title-2">第七条 生效及其他</h2>
        <p>
            7.1 本协议由丙方制定并发布，先由乙方单方签署确认，乙方同意丙方根据其与丙方的约定，将借款信息发布在拍拖贷平台，并同意，自乙方平台账户收到本协议借款金额（以乙方实际收到的金额为准）时，对三方生效，同时，本协议及其附件经甲方确认后，在丙方运营的拍拖贷平台以电子文本形式发布，保存在丙方设立的专用服务器上备查。各方协议内容，以丙方拍拖贷平台最终生成的协议内容为准。协议生效后，各方均认可电子借款协议及其附件效力。甲方根据平台操作指示在平台通过点击“确认”认本协议条款，视为甲方认可本电子借款协议以及本协议及其附件。各方委托丙方保管借款协议等纸质文件和电子信息。本协议生效前，丙方有权撤销本协议。乙方不得要求丙方或甲方支付赔偿金或者违约金。
        </p>
        <p>7.2
            甲方、乙方均同意并确认，双方平台账户的资金划付均通过第三方支付机构进行，所产生的法律效果及法律责任归属于甲方、乙方。同时，甲方、乙方委托丙方根据本协议采取的全部行动和措施的法律后果均归属于甲方、乙方，丙方不因此承担责任。</p>
        <p>7.3 本协议的任何条款的无效不影响本协议其他条款的效力。</p>
        <p>7.4 若本协议生效前，乙方认可对本协议第1.1条表中内容的修改，则修改后的内容生成本协议附件，该附件构成本协议不可分割的一部分，该协议与本协议第1.1条表中内容不一致的，以附件内容为准。</p>
        <p>7.5
            丙方根据乙方的资信情况确定乙方借款金额。丙方以电话或短信或电子邮件形式通知乙方的审批结果。如乙方通过丙方审批，则丙方在拍拖贷平台上发布乙方借款信息。待借款满标（即甲方愿意出借的资金不少于乙方借款金额）后，系统自动生成本协议首部甲方（出借人）的信息。如纸质版的本协议附件内容与电子版的本协议附件内容不一致的，以电子版的本协议附件内容为准。</p>
        <p>7.6
            乙方在本协议首部预留的所有信息与联系方式均真实有效，如有变更，乙方应立即以书面方式通知其他各方，如怠于通知的，应承担一切不利法律后果。如一方采取邮寄方式送达的，自寄出之日起3日后视为已送达，采用电子邮件或短信方式送达的，自邮件到达收件人邮箱系统或短信到达收信人手机系统之日视为已送达。</p>

        <br/>
    </li>

    <p class="nextpage">&nbsp;</p>
    <p>（以下无正文）</p>
    <p>乙方承诺，作为借款人完全同意本协议的所有内容,认可借款协议及其附件的法律效力及承诺签署本协议（含附件)均为本人真实意思的表示，愿承担因违反本借款协议约定及承诺而产生的一切责任和损失。 </p>
    <p style="padding:20px 0;">甲方：${realName}（以在拍拖贷平台网站上在线确认作为签署生效） </p>
    <p style="padding:20px 0;">乙方（签名、捺印）：
    [#if borrowUserType=="GENERAL"]
        [#if borrowing.borrower!=currentUser.id]
            ${secrecy("fullName",borrowRealName)}
        [#else]
        ${borrowRealName}
        [/#if]
    [#else]
        [#if borrowing.borrower!=currentUser.id]
         ${secrecy("corpName",borrowCorpName)}
        [#else]
        ${borrowCorpName}
        [/#if]
    [/#if]
    </p>
    <p style="padding:20px 0;">丙方（盖章）：青海高领网络科技有限公司 </p>
    <p style="float: right">
    <span>
        签署地：<span class="xian">${borrowing.agreementPlace}</span>
    </span>
        <br/>
        <span>
        签署日期：<span class="xian" style="width:70px; min-width:inherit;">${contractYear}</span>年
        <span class="xian" style="width:30px; min-width:inherit;">${contractMonth}</span>月
        <span class="xian" style="width:30px; min-width:inherit;">${contractDay}</span>日
    </span>
    </p>
</ul>
</h1>
</p>