[@nestedstyle]
    [@css href="css/bankcard.css" /]
[/@nestedstyle]
[@nestedscript]
    [@js src="js/user/avatar.min.js" /]
    [@js src="js/user/user.bankcard.js" /]
[/@nestedscript]
[@insert template="layout/uc_layout" title="用户中心 - 银行卡管理" memberContentId="safety" module="security" nav="uc" currentUser=kuser user=kuser]
[#--列表--]
<div class="chongzhiTop">
    <h3>我的银行卡</h3>
</div>

<div class="drawcash">
    <div class="drawcash-bank">
        <ul class="bankCards_list">
            <dt id="dataCards"></dt>
            <li class="bankCards_list_li">
                <div class="div-one"></div>
                <div class="div-two">
                    <div class="add-bank add-bankCard-shortcut"><a href="${ctx}/pay/bankcard/bind" class="color-orange">+添加银行卡</a></div>
                </div>
            </li>

        </ul>
    </div>
</div>

<script id="dataTpl" type="text/html">
    {{#  $.each(d.data, function(index, item){ }}
    <li class="bankCards_list_li">
        <div class="div-one">
            <div class="pull-left">
                <img src="{{ item.bankLogo }}" />
                <span>{{ item.bankName }}</span>
            </div>
            [#--<div class="pull-right binding">快捷</div>--]
        </div>
        <div class="div-two">
            <div class="li">
                <div class="name">卡号</div>
                <div class="cont size-18">{{ item.bankAccountNumber }}</div>
            </div>
            <div class="li">
                <div class="name">开户行</div>
                <div class="cont">{{ item.bankName }}</div>
            </div>
            <div class="li">
                {{#  if(item.status==10){ }}
                    <span class="pull-right delete-bankCard color-orange" bankCardId="{{ item.id }}" style="margin-left:10px;">
                        <a href="${ctx}/pay/bankcard/unbind?bindingSystemNo={{ item.bindingSystemNo }}" >
                            删除&nbsp&nbsp&nbsp&nbsp
                        </a>
                    </span>
                {{#  } }}
                {{#  if(item.status==30){ }}
                    <span class="pull-right delete-bankCard color-orange" bankCardId="{{ item.id }}" style="margin-left:10px;">
                        <a href="${ctx}/pay/bankcard/bind?verify=true&bindingSystemNo={{ item.bindingSystemNo }}" >
                            验证&nbsp&nbsp&nbsp&nbsp
                        </a>
                    </span>
                {{#  } }}
            </div>
        </div>
    </li>
    {{#  }); }}
</script>

[/@insert]

