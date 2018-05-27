[@nestedscript]
	[@js src="js/admin/login.min.js" /]
[/@nestedscript]
[@insert template="admin/layout/login_layout" title="登录"]
	[#-- 对话框 --]
	<div class="dialog-box">
		[#-- FORM表单 --]
		<form id="loginForm">

			[#-- 标题栏 --]
			<div class="title-bar">
				管理中心
			</div>

			[#-- 瞬时消息 --]
			<div id="flashMessage" style="padding-left:50px;">${error!""}</div>
			<div class="form-groups">
				[#-- 用户名 --]
				<div class="form-group">
					<div class="input-group">
						<label for="username" class="input-group-addon input-sm">
							<i class="glyphicon glyphicon-user"></i>
						</label>
						<input id="username" class="form-control input-sm" type="text" name="username" placeholder="用户名"
							   maxlength="20"/>
					</div>
				</div>

				[#-- 密码 --]
				<div class="form-group">
					<div class="input-group">
						<label for="password" class="input-group-addon input-sm">
							<i class="glyphicon glyphicon-lock"></i>
						</label>
						<input id="password" class="form-control input-sm" type="password" name="password"
							   placeholder="密&nbsp;&nbsp;&nbsp;码" maxlength="20" autocomplete="off"/>
					</div>
				</div>

				[#-- 验证码 --]
				<div class="form-group">
					<div class="input-group">
						<label for="captcha" class="input-group-addon input-sm">
							<i class="glyphicon glyphicon-barcode"></i>
						</label>
						<input id="captcha" class="form-control input-sm" type="text" name="captcha"
							   placeholder="验证码" maxlength="4" autocomplete="off" style="width:160px;"/>

						<div class="input-group-btn">
							<img id="captchaImage" class="captchaImage" title="点击更换验证码" alt="点击更换验证码"
								 src="${ctx}/admin/captcha?type=ADMIN_LOGIN" />
						</div>
					</div>
				</div>

				[#-- 记住用户名 --]
				<div class="form-group">
					<label class="checkbox-inline">
						<input id="remember" type="checkbox" value="true"/>
						记住用户名
					</label>
				</div>

			</div>

			[#-- 表单控件栏 --]
			<div class="control-toolbar clearfix">
				[#-- 登录 --]
				<button class="btn btn-primary btn-lg login_main_submit" type="submit">&nbsp;登&nbsp;录&nbsp;</button>
			</div>

		</form>

		[#-- 版权信息 --]
		<div class="copyright clearfix">
			<span class="linkform">Copyright 2016-${.now?string("yyyy")} Karazam. All Rights Reserved.</span>
		</div>
	</div>
	<script>
		if (window != top) {
			top.location.href = location.href;
		}
	</script>
[/@insert]