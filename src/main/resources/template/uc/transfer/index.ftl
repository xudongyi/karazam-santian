[@nestedscript]
    [@js src="js/user/user.transfer.js" /]
[/@nestedscript]
[@insert template="layout/uc_layout" title="用户中心-债权转让" memberContentId="zhaiquan-kezhuan" module="transfer" nav = "transfer" currentUser=kuser user=kuser]
<div class="chongzhiTop">
    <h3>债权转让</h3>
</div>
<div class="chongzhiNav clearfix" id="chongzhiNav">
    <ul id="tableHeader">
        <li class="transferMenu cur" searchType="cantransfer">
            <a href="javascript:void(0);">可转让</a>
        </li>
        <li class="transferMenu" searchType="transfering">
            <a href="javascript:void(0);">转让中</a>
        </li>
        <li class="transferMenu" searchType="transferOut">
            <a href="javascript:void(0);">已转让</a>
        </li>
        <li class="transferMenu" searchType="transferIn">
            <a href="javascript:void(0);">已承接</a>
        </li>
    </ul>
</div>
<div class="answer">
    <ul class="title" id="cantransferTitle">
        <li class="tal" style="width:16%;box-sizing:border-box;padding-left:2%;+width:14%;">项目名称</li>
        <li>预期年化</li>
        <li>剩余期数</li>
        <li>待收本息(元)</li>
        <li>已收回款(元)</li>
        <li>剩余本金(元)</li>
        <li>下一个回款日</li>
        <li>操作</li>
    </ul>

    <ul class="title" id="transferingTitle" style="display: none">
        <li class="tal" style="width:16%;box-sizing:border-box;padding-left:2%;+width:14%;">原标项目</li>
        <li>剩余期限</li>
        <li>转让本金(元)</li>
        <li>转让价格(元)</li>
        <li>已转让本金(元)</li>
        <li>转让进度</li>
        <li>操作</li>
    </ul>

    <ul class="title" id="transferOutTitle" style="display: none">
        <li class="tal" style="width:16%;box-sizing:border-box;padding-left:2%;+width:14%;">原标项目</li>
        <li class="tar">成交本金(元)</li>
        <li class="tar">成交价格(元)</li>
        <li class="tar">实收金额(元)</li>
        <li class="tar" style="width:12%;">手续费(元)</li>
        <li style="width:20%;">转让成交时间</li>
        <li style="width:8%;">详情</li>
        <li style="width:8%;">协议</li>
    </ul>

    <ul class="title" id="transferInTitle" style="display: none">
        <li class="tal" style="width:11%;box-sizing:border-box;padding-left:2%;+width:9%;">债权ID</li>
        <li>债权项目</li>
        <li>剩余期限</li>
        <li class="tar">债权本金(元)</li>
        <li class="tar">承接价格(元)</li>
        <li class="tar">待收总额(元)</li>
        <li class="tar">待收利息(元)</li>
        <li style="width:6%;">协议</li>
        <li style="width:11%;">承接时间</li>
    </ul>

    <div id="dataConts"></div>
</div>
<div class="pageList" id="pagination"></div>
<div class="empty">
    <img src="${ctx}/static/images/safe/empty.png" alt="">
    <p class="text">您距离躺着赚钱，只剩动动手指投资啦！</p>
    <p class="btn">
        <a href="${ctx}/investment">
            <button class="center_btn_a">立即投资</button>
        </a>
    </p>
</div>

<div class="modal fade" id="transferModal">
    <div class="modal-dialog" style="width:800px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title">债权转让<em>&gt;</em><span>发起转让</span></h4>
            </div>
            <div class="modal-body">
                <div class="auto-sale clearfix" style="background-color: #eeeeee;padding-left: 20px;">
                    <div class="statues statues-add">
                        <h3>
                            <span>原标预期年化 <strong class="font-orange" id="interest"></strong>%</span>
                        </h3>
                        <ul class="clearfix des-ul">
                            <li>
                                <label for="">待收本金(元)</label>
                                <span id="daishoubenjin" class="edu"></span>
                            </li>
                            <li>
                                <label for="">待收利息(元)</label>
                                <span id="daishoulixi" class="edu"></span>
                            </li>
                            <li>
                                <label for="">待收总额(元)</label>
                                <span id="daishouzonge" class="edu"></span>
                            </li>
                            <li>
                                <label for="">剩余期限</label>
                                <span id="lowPeriod"></span>
                            </li>
                            <li>
                                <label for="">还款方式</label>
                                <span id="repaymentMethodDes"></span>
                            </li>
                            <li>
                                <label for="">下一个回款日</label>
                                <span id="nextRepaymentDate"></span>
                            </li>

                        </ul>
                    </div>
                </div>

                <form id="transferForm" action="${ctx}/uc/transfer/trans" method="post">
                    <input type="hidden" name="borrowingId" id="borrowingIdParam" value=""/>
                    <ul class="seting-form pay-content">
                        <li class="clearfix">
                            <label for="" class="name">转让本金：</label>
                            [#--<input type="text" name="name" value="0" class="input-select short-input" id="trasfer-money">--]
                            <span class="pull-left ui-value" id="zhuanrangbenjin"></span>
                            <span class="fl">元</span>
                            <div class="red-erron" style="display: none;"><i class="notok"></i>请输入转让本金</div>
                        </li>
                        <li class="clearfix">
                            <label for="" class="name">转让价格：</label>
                            [#--<input type="text" name="name" value="" class="input-select short-input" id="trans_em_total" placeholder="0~0">--]
                            <span class="pull-left ui-value" id="trans_em_total"></span>
                            <span class="fl">元</span>
                            <span class="fl little-word">(目前仅支持平价转让)</span>
                            <div class="red-erron2 fl" style="display: none;"></div>
                        </li>
                        <li class="clearfix">
                            <label for="" class="name">手续费: </label>
                            <span class="rate">
                                <strong class="charge" id="trans_em_fee">0</strong>元
                                <em class="helpTipsIcon fa fa-question-circle-o helpOne">
                                    <div class="helpTipsDiv helpTipsOne" style="display: none;"> 转让本金*<i class="helpTipsI"></i>转让服务费率</div>
                                </em>
                            </span>
                        </li>
                        <li class="clearfix">
                            <label for="" class="name">预期到账金额: </label>
                            <span class="rate"><strong id="trans_em_yuji" class="formatMoney font-bold expect-money">0</strong>元
                                <em class="helpTipsIcon fa fa-question-circle-o helpTow">
                                    <div class="helpTipsDiv helpTipsTwo" style="display: none;">预期到账金额=出让价格-手续费 <i class="helpTipsI helpTipsItall"></i></div>
                                </em>
                            </span>
                        </li>
                    </ul>
                    <div class="save save-short" style="border: none;">
                        <button type="submit" name="button" class="button button-save radius-ssm button-normal transfer-button">确认转让</button>
                    </div>
                    <p class="submit-reading" style="padding-left: 135px;">
                        <input type="checkbox" name="agreement" value="" checked="checked" id="xieyi" class="">
                        <label for="xieyi" style="font-weight: 500">我已阅读并同意
                            <a id="agreement" href="javascript:void(0);" class="blue" target="_blank">《债权转让及受让协议》</a>
                        </label>
                    </p>
                    <div class="order-intro-ul" style="padding-top: 10px;">
                        <h6>温馨提示：</h6>
                        <ul>
                            <li>1、当前转让期间回款，转让自动失效，债权转让标有效期限是24小时，24小时后转让没成交则自动失效。</li>
                        </ul>
                    </div>

                </form>
            </div>
        </div>
    </div>
</div>

[#-- 可转让 --]
<script id="cantransferTpl" type="text/html">
    {{#  $.each(d.rows, function(index, item){ }}
    <ul class="content">
        <li class="tal" style="width:16%;box-sizing:border-box;padding-left:2%;+width:14%;">
            <div class="titleName">
                <a href=${ctx}"/investment/{{item.borrowingId}} ">{{ item.title}}</a>
            </div>
        </li>
        <li>{{ item.interestRate }}%</li>
        <li>{{ item.surplusPeriod }}</li>
        <li>{{ currency(item.capitalInterest, '￥') }}</li>
        <li>{{ currency(item.recoveriedAmount, '￥') }}</li>
        <li>{{ currency(item.residualCapital, '￥') }}</li>
        <li>{{ item.nextRepaymentDateDes }}</li>
        <li>
            {{#  if(item.canTransfer && item.residualCapital != '0' ){ }}
                {{#  if(item.transfering){ }}
                <a href="javascript:void(0);" name="transferCancel" value="{{ item.borrowingId }}" class="blue">撤销</a>
                    {{#  }else{ }}
                <a href="javascript:void(0);" name="transfer" value="{{ item.borrowingId }}" class="blue">转让</a>
                {{#  } }}
            {{#  }else{ }}
                    <a href="javascript:void(0);" style="color:#9e9e9e;">转让</a>
            {{#  } }}
        </li>
    </ul>
    {{#  }); }}

</script>

[#-- 转让中 --]
<script id="transferingTpl" type="text/html">
    {{#  $.each(d.rows, function(index, item){ }}
    <ul class="content">
        <li class="tal" style="width:16%;box-sizing:border-box;padding-left:2%;+width:14%;">
            <div class="titleName">
                <a href=${ctx}"/investment/{{item.borrowing}} ">{{ item.title}}</a>
            </div>
        </li>
        <li>{{ item.surplusPeriod }}</li>
        <li>{{ currency(item.capital, '￥') }}</li>
        <li>{{ currency(item.capital, '￥') }}</li>
        <li>{{ currency(item.transferedCapital, '￥') }}</li>
        <li>{{ item.transferProgress*100 }}%</li>
        <li>
            <a target="_blank" href="${ctx}/transfer/{{ item.id }}" class="blue">查看</a>
            {{#  if(item.state == 'TRANSFERING') { }}
                <a href="javascript:void(0);" name="transferCancel" value="{{ item.borrowing }}" class="blue">撤销</a>
            {{#  } }}
        </li>
    </ul>
    {{#  }); }}

</script>

[#-- 已转让 --]
<script id="transferOutTpl" type="text/html">
    {{#  $.each(d.rows, function(index, item){ }}
    <ul class="content">
        <li class="tal" style="width:16%;box-sizing:border-box;padding-left:2%;+width:14%;">
            <div class="titleName">
                <a href=${ctx}"/investment/{{item.borrowing}} ">{{ item.title}}</a>
            </div>
        </li>
        <li class="tar">{{ currency(item.transferedCapital, '￥') }}</li>
        <li class="tar">{{ currency(item.transferedCapital, '￥') }}</li>
        <li class="tar">{{ currency(item.transferedCapital, '￥') }}</li>
        <li class="tar" style="width:12%;">{{ currency(item.outFee, '￥') }}</li>
        <li style="width:20%;">{{item.createDate}}</li>
        <li style="width:8%;">
            <a href="${ctx}/transfer/{{item.id}}">查看</a>
        </li>
        <li style="width:8%;">
            <a href="${ctx}/uc/transfer/transferin/agreement/list/{{item.borrowing}}">查看协议</a>
        </li>
    </ul>
    {{#  }); }}

</script>

[#-- 已承接 --]
<script id="transferInTpl" type="text/html">
    {{#  $.each(d.rows, function(index, item){ }}
    <ul class="content">
        <li class="tal" style="width:11%;box-sizing:border-box;padding-left:2%;+width:9%;">
            <div class="titleName">
                <a href=${ctx}"/investment/{{item.borrowingId}} ">{{ item.transfer}}</a>
            </div>
        </li>
        <li>{{ item.title }}</li>
        <li>{{ item.surplusPeriod }}</li>
        <li class="tar">{{ currency(item.capital, '￥') }}</li>
        <li class="tar">{{ currency(item.capital, '￥') }}</li>
        <li class="tar">{{ currency(item.capitalInterest, '￥') }}</li>
        <li class="tar">{{ currency(item.interest, '￥') }}</li>
        <li style="width:6%;">
            <a target="_blank" href="${ctx}/uc/transfer/transferin/agreement/{{ item.transfer }}/{{item.investId}}?type=IN">查看</a>
        </li>
        <li style="width:11%;">{{ item.investmentDate }}</li>
    </ul>
    {{#  }); }}

</script>

[/@insert]