[@nestedstyle]
    [@css href="css/cubeportfolio.min.css" /]
    [@css href="css/mate/base.css" /]
[/@nestedstyle]
[@nestedscript]
    [@js src= "js/jquery.cubeportfolio.min.js"/]
<script>
    var type = "${type}";
</script>
    [@js src= "js/investment/list.js"/]
[/@nestedscript]
[@insert template="layout/invest_layout" title="我要投资" user=kuser]
<div class="wrapper ff-layout">
    <div class="ff-finacing-wrapper">
        <div id="topBanner" class="banner">
            <div class="ff-carousel">
                <div class="ff-carousel-inner"><a class="ff-carousel-href" href="javascript:;" target="_self"
                                                  style="cursor:default;"><p
                        style="background: url(${ctx}/static/images/index/investAD.jpg) center center no-repeat;opacity: 1;filter:alpha(opacity=100);"
                        data-href=""></p></a></div>
                <div class="ff-carousel-list">
                    <ul class="ff-carousel-control clearfix">
                        <li class="ff-carousel-lunboone"></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="ff-panel"> <!-- 列表部分 -->
            <div class="list-panel">
                <div class="panel-tab" id="panelTab">
                    <ul class="clearfix">
                        <li typeSearch="project" class="typeSearch active">
                            <i class="typeSearch line"></i>散标市场
                        </li>

                        <li typeSearch="transfer" class="typeSearch">
                            <i class="typeSearch line"></i>转让市场
                        </li>
                    </ul>
                </div>
                <div class="panel-content clearfix">
                    <ul>
                        <li class="comment"><p>丰富理财选择 100元起投 期限任选</p></li>

                        <span id="projectBox"></span>
                    </ul>
                </div>
            </div>
            <div class="page-container clearfix">
                <div id="projectPagination" class="page-bar"></div>
            </div>
        </div>
    </div>
</div>

<script id="projectTpl" type="text/html">
    {{#  $.each(d.rows, function(index, item){ }}
        <li class="animated fadeInRightS"
            style="-webkit-animation-delay:0.00s;animation-delay:0.00s;">
            <div class="pro-name">
                <a fen="link_01"
                   href="${ctx}/investment/{{ item.id }}"
                   target="_blank">
                    {{#  if(item.title.length>20){ }}
                        {{ item.title.substring(0,20) }}...
                    {{#  }else{ }}
                        {{ item.title }}
                    {{#  } }}
                </a>
                {{# var labels = split(item.labels,","); }}
                {{# $.each(labels, function(index1, item1){ }}
                    {{# if (index1==0) { }}
                        <span class="ctag">{{ item1 }}</span>
                    {{# } else { }}
                        <span class="tag">{{ item1 }}</span>
                    {{# } }}
                {{#  }); }}
            </div>
            <div class="det-tb">
                <table>
                    <tbody>
                    <tr>
                        <td class="orange fst"><span class="ratNumber">{{ item.realInterestRate }}</span><span
                                class="unit">%</span></td>
                        <td><span class="val">{{ item.period }}</span><span class="unit">{{ item.periodUnitDes }}</span></td>
                        <td><span class="val">{{ item.investmentMinimum }}</span><span class="unit">元</span></td>
                        <td class="tips-cont">
                            <span class="val">{{ item.repaymentMethodDes }}</span><span class="unit"></span></td>
                        <td><span class="val">{{ item.surplusInvestmentAmount }}</span><span class="unit">元</span></td>
                    </tr>
                    <tr class="lab">
                        <td class="fst">约定年化利率</td>
                        <td>期限</td>
                        <td>起投金额</td>
                        <td>收益方式</td>
                        <td>剩余可购金额</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            {{#  if(item.isFailure){ }}
                <a href="${ctx}/investment/{{ item.id }}" target="_blank" class="linkbtn undefined disabled">已流标</a>
            {{#  }else{ }}
                {{#  if(item.progress == 'PREVIEWING'){ }}
                    <a href="${ctx}/investment/{{ item.id }}" target="_blank" class="linkbtn undefined mycountdown" startTime="{{ item.investmentStartDate }}">预告中</a>
                {{#  }else if(item.progress == 'AUTOINVESTING'){ }}
                    <a href="${ctx}/investment/{{ item.id }}" target="_blank" class="linkbtn undefined">自动投标中</a>
                {{#  }else if(item.progress == 'INVESTING'){ }}
                    <a href="${ctx}/investment/{{ item.id }}" target="_blank" class="linkbtn undefined">立即抢购</a>
                {{#  }else if(item.progress == 'LENDING'){ }}
                    <a href="${ctx}/investment/{{ item.id }}" target="_blank" class="linkbtn undefined disabled">已满额</a>
                {{#  }else if(item.progress == 'REPAYING'){ }}
                    <a href="${ctx}/investment/{{ item.id }}" target="_blank" class="linkbtn undefined disabled">还款中</a>
                {{#  }else if(item.progress == 'COMPLETED'){ }}
                    <a href="${ctx}/investment/{{ item.id }}" target="_blank" class="linkbtn undefined disabled">已完成</a>
                {{#  } }}
            {{#  } }}

        </li>
    {{#  }); }}
</script>

<script id="transferTpl" type="text/html">
    {{#  $.each(d.rows, function(index, item){ }}
    <li class="animated fadeInRightS"
        style="-webkit-animation-delay:0.00s;animation-delay:0.00s;">
        <div class="pro-name">
            <a fen="link_01"
               href="${ctx}/transfer/{{ item.id }}"
               target="_blank">
                {{#  if(item.title.length>12){ }}
                    {{ item.title.substring(0,12) }}...
                {{#  }else{ }}
                    {{ item.title }}
                {{#  } }}
            </a>
            {{# var labels = split(item.labels,","); }}
            {{# $.each(labels, function(index1, item1){ }}
                {{# if (index1==0) { }}
                <span class="ctag">{{ item1 }}</span>
                {{# } else { }}
                <span class="tag">{{ item1 }}</span>
                {{# } }}
            {{#  }); }}
        </div>
        <div class="det-tb">
            <table>
                <tbody>
                <tr>
                    <td class="orange fst"><span class="ratNumber">{{ item.interestRate }}</span><span
                            class="unit">%</span></td>
                    <td><span class="val">{{ item.residualPeriod }}</span></td>
                    <td><span class="val">{{ dateFormatter(item.nextRepaymentDate) }}</span></td>
                    <td class="tips-cont">
                        <span class="val">{{ item.repaymentMethodDes }}</span><span class="unit"></span></td>
                    <td><span class="val">{{ item.surplusCapital }}</span><span class="unit">元</span></td>
                </tr>
                <tr class="lab">
                    <td class="fst">约定年化利率</td>
                    <td>剩余期限({{ item.residualUnit }})</td>
                    <td>下个回款日</td>
                    <td>收益方式</td>
                    <td>剩余可购金额</td>
                </tr>
                </tbody>
            </table>
        </div>

        {{#  if(item.state == 'TRANSFERED'){ }}
            <a href="${ctx}/transfer/{{ item.id }}" target="_blank" class="linkbtn undefined disabled">已售完</a>
        {{#  }else if(item.state == 'CANCEL'){ }}
            <a href="${ctx}/transfer/{{ item.id }}" target="_blank" class="linkbtn undefined disabled">已撤销</a>
        {{#  }else{ }}
            <a href="${ctx}/transfer/{{ item.id }}" target="_blank" class="linkbtn undefined">立即抢购</a>
        {{#  } }}
    </li>
    {{#  }); }}
</script>

[/@insert]