[@nestedstyle]
    [@css href="css/center_index.css" /]
[/@nestedstyle]
[@nestedscript]
    [@js src="lib/echarts/echarts.min.js" /]
    [@js src="lib/echarts/macarons.js" /]
    [@js src="js/user/user.pandect.js" /]
    [@js src="js/util/assetDetail.js" /]
[/@nestedscript]
[@insert template="layout/uc_layout" title="账户总览" memberContentId="zichan-all" module="index" currentUser=kuser user=kuser]
<div class="asset_title normal_border">
    总资产明细
</div>
<div class="toolBox clearfix">
    <div id="main1" class="pieView fl">
    </div>
    <div class="toolList fl">
        <ul>
            <li><i class="icon_1"></i><label>可用余额(元)</label><span id="available">0.00</span></li>
            <li><i class="icon_2"></i><label>待收本金(元)</label><span id="watingCapital">0.00</span></li>
            <li><i class="icon_3"></i><label>待收收益(元)</label><span id="watingProfits">0.00</span></li>
            <li><i class="icon_4"></i><label>投资冻结金额(元)</label><span id="investFrozen">0.00</span></li>
            <li><i class="icon_5"></i><label>提现中金额(元)</label><span id="withdrawing">0.00</span></li>
        </ul>
    </div>
</div>
<div class="asset_title normal_border">
    累计收益明细<i class="fa fa-question-circle-o"></i>
    <div class="tixing">
        <i></i>
        累计收益=已收收益[#--+已使用红包--]+活动奖励+利息管理费+提现手续费+转让手续费+债权转让承接差额+其他费用
    </div>
</div>
<div class="toolBox clearfix otherBox">
    <ul>
        <li><label>已收收益(元)<i class="fa fa-question-circle-o"></i></label><span id="alreadyProfits">0.00</span>
            <div class="tixing">
                <i></i>
                已收收益=已回款收益+提前还款补偿金+提前回款
            </div>
        </li>
        <li><label>提现手续费(元)</label><span id="withdrawFee">0.00</span>
        </li>
        [#--<li><label>已使用红包(元)<i class="fa fa-question-circle-o"></i></label><span>0.00</span>
            <div class="tixing usedRed">
                <i></i>
                已使用红包=抵扣红包+邀请奖励+加息红包-红包返还
            </div>
        </li>--]
        <li><label>转让手续费(元)</label><span>0.00</span>
        </li>
        <li><label>活动奖励(元)<i class="fa fa-question-circle-o"></i></label><span>0.00</span>
            <div class="tixing">
                <i></i>
                活动奖励=活动加息+活动奖励+活动金打款+网站奖励-活动奖励返还
            </div>
        </li>
        <li><label>债权转让承接差额(元)<i class="fa fa-question-circle-o"></i></label><span>0.00</span>
            <div class="tixing transfer">
                <i></i>
                债权转让承接差额=转让差额+承接差额
            </div>
        </li>
        <li><label>推荐费(元)</label><span id="referralFee">0.00</span>
        </li>
        <li><label>其他费用(元)<i class="fa fa-question-circle-o"></i></label><span>0.00</span>
            <div class="tixing">
                <i></i>
                其他费用=账户减免+补偿款+特殊原因资金变动（冲正）
            </div>
        </li>
    </ul>

</div>
<div class="asset_title normal_border">
    其他资产明细
</div>
<div class="otherBox clearfix">
    <ul>
       [#-- <li><label>投资积分</label><span>${investPoint!'0'}</span></li>--]
        <li><label>净赚利息(元)</label><span>0.00</span></li>
       [#-- <li><label>可使用红包(元)</label><span>0.00</span></li>--]
        <li><label>提前还款补偿金(元)</label><span>0.00</span></li>
    </ul>
</div>
[/@insert]