[@nestedscript]
    [@js src="js/user/user.order.js" /]
[/@nestedscript]
[@insert template="layout/uc_layout" title="用户中心-资金记录" memberContentId="zichan-all" module="capital" nav = "capital" currentUser=kuser user=kuser]
<div class="chongzhiTop">
    <h3>资产管理</h3>
</div>
<div class="chongzhiNav clearfix">
    <ul>
        <li class="typecapital cur" searchType="capital">
            <a href="javascript:void(0);">资金明细</a>
        </li>
        <li class="typecapital" searchType="recharge">
            <a href="javascript:void(0);">充值</a>
        </li>
        <li class="typecapital" searchType="withdraw">
            <a href="javascript:void(0);">提现记录</a>
        </li>
    </ul>
</div>

<div class="records-count capital-records-count"></div>
<div class="records-count recharge-records-count" style="display: none;">
    <p class="weishouDetail">
        <span>充值流水总金额：<em>0.00</em>元 <i class="fa fa-question-circle-o"></i></span>
        <span>充值总金额：<em>0.00</em>元 <i class="fa fa-question-circle-o"></i></span>
    </p>
    <div class="tixing showbox0" style="display: none;">注：平台流水计算时间起点为2017年4月，不包含此前充值金额</div>
    <div class="tixing showbox1" style="display: none;">注：包括历史所有充值记录</div>
</div>

<div class="records-count withdraw-records-count" style="display: none;">
    <p class="weishouDetail">
        <span>提现流水总金额：<em>0.00</em>元 <i class="fa fa-question-circle-o"></i></span>
        <span>提现总金额：<em>0.00</em>元 <i class="fa fa-question-circle-o"></i></span>
    </p>
    <div class="tixing showbox0" style="display: none;">注：平台流水计算时间起点为2017年4月，不包含此前提现金额</div>
    <div class="tixing showbox1" style="display: none;">注：包括历史所有提现记录</div>
</div>

<div class="tiaojian clearfix" id="dateRange">
    <ul>
        <li class="dateSearch cur" value="all">全部</li>
        <li class="dateSearch" value="last_one_month">近1个月</li>
        <li class="dateSearch" value="last_three_month">近3个月</li>
        <li class="dateSearch" value="last_year">近1年</li>
    </ul>
    [#--<div class="excel">--]
        [#--<button type="button" class="button button-sm button-special" id="excel">导出excel</button>--]
        [#--<span>提示：最多导出近一年数据</span>--]
    [#--</div>--]
    <div class="riqi capitalType">
        类型：
        <select class="form-control selector capital-form-control" style="font-size: 12px;height:30px;vertical-align: middle">
            <option class="selector-title" value="">全部</option>
            [#list orderTypes as _method]
                <option value="${_method}"[#if _method == method] selected="selected"[/#if]>${_method.displayName}</option>
            [/#list]
        </select>
    </div>
    <div class="riqi rechargeType" style="display: none;">
        类型：
        <select class="form-control selector recharge-form-control" style="font-size: 12px;height:30px;vertical-align: middle">
            <option class="selector-title" value="">全部</option>
            [#list recordStatus as _method]
                <option value="${_method}"[#if _method == method] selected="selected"[/#if]>${_method.displayName}</option>
            [/#list]
        </select>
    </div>
    <div class="riqi withdrawType" style="display: none;">
        类型：
        <select class="form-control selector withdraw-form-control" style="font-size: 12px; height:30px;vertical-align: middle">
            <option class="selector-title" value="">全部</option>
            [#list recordStatus as _method]
                <option value="${_method}"[#if _method == method] selected="selected"[/#if]>${_method.displayName}</option>
            [/#list]
        </select>
    </div>
</div>

<div class="answer">
    <ul class="title" id="capitalTitle">
        <li class="tal" style="box-sizing:border-box;padding-left:2%;+width:17%;">时间</li>
        <li class="tar" style="width:17%;">类型</li>
        <li class="tar" style="width:17%;">交易金额(元)</li>
        [#--<li class="tar" style="width:21%;">交易后可用金额(元)</li>--]
        <li class="tar" style="width:21%;">服务费(元)</li>
        <li class="tar" style="width:13%;">状态</li>
        <li class="detail tar" style="width: 14%;box-sizing:border-box;padding-right:1%;">操作</li>
    </ul>
    <ul class="title" id="rechargeTitle" style="display: none;">
        <li class="tal" style="box-sizing:border-box;padding-left:2%;+width:18%;">创建时间</li>
        <li class="tar" style="width:20%;">交易金额(元)</li>
        <li class="tar" style="width:20%;">充值方式</li>
        <li class="tar" style="width:20%;">充值银行</li>
        <li class="tar" style="width: 19%;box-sizing:border-box;padding-right:1%;">状态</li>
    </ul>
    <ul class="title" id="withdrawTitle" style="display: none;">
        <li class="tal" style="box-sizing:border-box;padding-left:2%;+width:18%;">提现时间</li>
        <li class="tar" style="width:20%;">提现金额(元)</li>
        <li class="tar" style="width:20%;">手续费(元)</li>
        <li class="tar" style="width: 19%;box-sizing:border-box;padding-right:1%;">实际到账金额(元)</li>
        <li class="tar" style="width:15%;">状态</li>
    </ul>
    <div id="capitals"></div>
</div>
<div class="pageList" id="pagination"   style="text-align: right; margin-top: 5px;"></div>
<div class="empty" style="">
    <img src="${ctx}/static/images/safe/empty.png" alt="">
    <p class="text">暂时没有您的记录</p>
</div>


[#-- 资金记录 --]
<script id="capitalTpl" type="text/html">
    {{#  $.each(d.rows, function(index, item){ }}
        <ul class="content">
            <li class="tal" style="box-sizing:border-box;padding-left:2%;+width:17%;">{{ item.createDate }}</li>
            <li class="tar" style="width:17%;">{{ item.typeDes }}</li>
            <li class="tar" style="width:17%;">{{ currency(item.amount, '￥') }}</li>
            <li class="tar" style="width:21%;">{{ currency(item.totalFee, '￥') }}</li>
            <li class="tar" style="width:13%;">{{ item.statusDes }}</li>
            <li class="detail tar" style="width: 14%;box-sizing:border-box;padding-right:1%;+width:10%;">
                <div class="detail-title blue" detailIndex="{{ index }}">详情</div>
                <div class="zichan-detail have-more zichan-detail-{{ index }}"><i></i>
                    <p>
                        <span class="fl">时间</span>
                        <span class="fr">{{ item.createDate }}</span>
                    </p>
                    <p>
                        <span class="fl">类型</span>
                        <span class="fr">{{ item.typeDes }}</span>
                    </p>
                    <p>
                        <span class="fl jine-name">{{ item.orderSignDes }}</span>
                        <span class="fr">{{ currency(item.amountReceived) }}</span>
                    </p>
                    <p>
                        <span class="fl">手续费</span>
                        <span class="fr">
                           {{# if(item.type=="TRANSFER_IN"){}}
                             {{ currency( item.payerFee + item.payeeThirdFee + item.payerThirdFee) }}
                             {{# }else if(item.type=="TRANSFER_OUT"){}}
                             {{ currency(item.payeeFee + item.payeeThirdFee + item.payerThirdFee) }}
                            {{#}else{}}
                             {{ currency(item.payerFee+item.payeeFee + item.payeeThirdFee + item.payerThirdFee) }}
                           {{# } }}

                        </span>
                    </p>
                    <p>
                        <span class="fl">流水</span>
                        <span class="fr">{{ item.orderNo }}</span>
                    </p>
                    <p>
                        <span class="fl">备注</span>
                        {{# if(item.memo==null){ }}
                            <span class="fr">无</span>
                        {{# }else{ }}
                            <span class="fr">{{ item.memo }}</span>
                        {{# } }}

                    </p>
                </div>
            </li>
        </ul>
    {{#  }); }}

</script>

[#-- 充值记录 --]
<script id="rechargeTpl" type="text/html">
    {{#  $.each(d.rows, function(index, item){ }}
        <ul class="content">
            <li class="tal" style="box-sizing:border-box;padding-left:2%;+width:18%;">{{ item.createDate }}</li>
            <li class="tar" style="width:20%;">{{ currency(item.amount, '￥') }}</li>
            <li class="tar" style="width:20%;">{{ item.businessTypeDes }}</li>
            <li class="tar" style="width:20%;">{{ item.bankName }}</li>
            <li class="tar" style="width: 19%;box-sizing:border-box;padding-right:1%;">{{ item.statusDes }}</li>
        </ul>
    {{#  }); }}

</script>

[#-- 提现记录 --]
<script id="withdrawTpl" type="text/html">
    {{#  $.each(d.rows, function(index, item){ }}
        <ul class="content">
            <li class="tal" style=box-sizing:border-box;padding-left:2%;+width:18%;">{{ item.createDate }}</li>
            <li class="tar" style="width:20%;">{{ currency(item.amount, '￥') }}</li>
            <li class="tar" style="width:20%;">{{ currency(item.allFee, '￥') }}</li>
            <li class="tar" style="width:20%;">{{ currency(item.actualAmount, '￥') }}</li>
            <li class="tar" style="width: 19%;box-sizing:border-box;padding-right:1%;">{{ item.statusDes }}</li>
        </ul>
    {{#  }); }}

</script>
[/@insert]

