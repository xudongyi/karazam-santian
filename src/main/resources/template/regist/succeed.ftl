[@insert template="layout/regist_layout" title="注册成功"]
<div class="content">
	<div class="rows">
        <div class="login-one">
            <div class="title">
            	<div class="name pull-left">账户注册</div>
                <div class="pull-right">已有平台账户？  <a href="${base}/login" class="color-orange">马上登录</a></div>
            </div>
            <!--步骤-->
            <div class="step">
            	<div class="line-step"></div>
            	<ul>
                	<li>
                    	<div class="steo-ico-box">
                        	<a class="step-ico">1</a>
                            <p>填写账户信息</p>
                        </div>
                    </li>
                    <li>
                    	<div class="steo-ico-box">
                        	<a class="step-ico">2</a>
                            <p>验证账户信息</p>
                        </div>
                    </li>
                   
                    <li>
                    	<div class="steo-ico-box active">
                        	<a class="step-ico">3</a>
                            <p>注册成功</p>
                        </div>
                    </li>
                </ul>
            </div>
            
            <!--注册成功-->
            <div class="result pull-left">
                <div class="icon pull-left"></div>
                <div class="description pull-left">
                	<div class="wz">尊敬的 <span class="color-orange">${currentUser.mobile}</span>，恭喜您成为会员!</div>
                    <div class="link">
                    	[#--<div class="pull-left"><a href="${base}/" class="btn-four-b">进入首页</a></div>--]
                            <div class="pull-left" style="text-align:center;"><a href="${ctx}/uc/security" class="btn-four-b">立即实名认证</a></div>
                        [#--<div class="pull-left" style="text-align:center;"><a href="${base}/account/bank_card/new" class="btn-four-b">绑定银行卡</a></div>--]
                    </div>
                </div>
            </div>
        </div>
        <div class="contact clear">
        	<div class="pull-right">
                <div class="phone pull-left">客服热线</div>
                <div class="customer-service pull-left"><a href="#" class="color-black">在线客服</a></div>
            </div>
        </div>
    </div>
</div>
[/@insert]