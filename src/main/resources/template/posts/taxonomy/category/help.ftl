[#-- {{{"use":"category","name":"help","des":"帮忙中心分类"}}} --]
[@nestedstyle]
    [@css href="css/flexslider.css" /]
    [@css href="css/cubeportfolio.min.css" /]
    [@css href="css/style.css" /]
    [@css href="css/floatbutton.css" /]
    [@css href="css/article/style.css" /]
    [@css href="css/skins/default.css" /]
[/@nestedstyle]
[@nestedscript]
    [@js src="js/posts/category/help.js" /]
[/@nestedscript]
[@insert template="layout/taxonomy_layout" title="${taxonomy.title}" metaKey=taxonomy.metaKeywords metaDes=taxonomy.metaDescription currentUser=kuser user=kuser]
<div class="help-center">
    <div class="center-wrap">
        <div class="container">
            <div class="row">
                <div class="span9">
                    <span id="problemCategoryBox"></span>
                    <span id="flowBootCategoryBox"></span>
                    <div class="self-service box">
                        <h2>自助服务</h2>

                        <div class="box-content">
                            <div class="row-fluid">
                                <div class="span4">
                                    <a class="item" href="${ctx}/forgetPassword" target="_blank">
                                        <img src="${ctx}/static/images/search.png" alt="找回登录密码">

                                        <div class="text">
                                            <h4>找回登录密码</h4>
                                            <span>通过验证绑定手机找回登录密码</span>
                                        </div>
                                    </a>
                                </div>
                                <div class="span4">
                                    <a class="item" href="${ctx}/uc/security" target="_blank">
                                        <img src="${ctx}/static/images/account.png" alt="修改登录密码">

                                        <div class="text">
                                            <h4>修改登录密码</h4>
                                            <span>通过验证原密码修改登录密码</span>
                                        </div>
                                    </a>
                                </div>
                                <div class="span4">
                                    <a class="item" href="${ctx}/c/xiu_gai_shou_ji_hao_ma" target="_blank">
                                        <img src="${ctx}/static/images/mobile.png" alt="修改手机号码">
                                        <div class="text">
                                            <h4>修改手机号码</h4>
                                            <span>联系平台管理员绑定新的手机号码</span>
                                        </div>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="span3">
                    [#if !user??]
                        <div class="qq-service box">
                            <a href="${ctx}/login" class="btn btn-primary">登录后联系客服</a>
                        </div>
                    [/#if]
                    <div class="hot-question box">
                        <h2>热门问题</h2>
                        [@k_contents taxonomyType="category" taxonomySlug=slug listSize=10]
                            [#list contents as content]
                                <a href="${ctx}/c/${content.slug}" target="_blank">${content.title}</a>
                            [/#list]
                        [/@k_contents]
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

[#-- 常见问题begin --]
<script id="problemCategoryTpl" type="text/html">
    <div class="common-question box">
        <h2>{{ d.taxonomy.text }}</h2>
        <div class="box-content">
            <div class="row-fluid">
            {{#  $.each(d.children, function(index, item){ }}
                <span id="problemChildTaxBox{{ index }}"></span>
                {{# getProblemChildTaxContents(item.type, item.slug, index) }}
            {{#  }); }}
            </div>
        </div>
    </div>
</script>

<script id="problemChildTaxTpl" type="text/html">
    <div class="span4">
        <h3><a href="{{ ctx }}/t/{{ d.taxonomy.type }}/{{ d.taxonomy.slug }}">{{ d.taxonomy.text }}</a></h3>
        {{#  $.each(d.contents.rows, function(index, item){ }}
            {{# if (index<4) { }}
                <a href="{{ ctx }}/c/{{ item.slug }}" target="_blank">{{ item.title }}</a>
            {{# } }}
        {{#  }); }}
    </div>
</script>
[#-- 常见问题end --]

[#-- 流程指引begin --]
<script id="flowBootCategoryTpl" type="text/html">
    <div class="flow-intro box">
        <h2>{{ d.taxonomy.text }}</h2>
        <div class="row-fluid">
            <div class="box-content">
                {{#  $.each(d.children, function(index, item){ }}
                    <div class="span4">
                        <div class="item">
                            <span id="flowBootChildTaxBox{{ index }}"></span>
                            {{# getFlowBootChildTaxContents(item.type, item.slug, index) }}
                        </div>
                    </div>
                {{#  }); }}
            </div>
        </div>
    </div>
</script>

<script id="flowBootChildTaxTpl" type="text/html">
    <div class="type">
        <span>{{ d.taxonomy.text }}</span>
    </div>
    <div class="branches">
        <div class="row-fluid">
            {{#  $.each(d.contents.rows, function(index, item){ }}
                <div class="span12">
                    <a href="{{ ctx }}/c/{{ item.slug }}" target="_blank">{{ item.title }}</a>
                </div>
            {{#  }); }}
        </div>
    </div>
</script>
[#-- 流程指引end --]

[/@insert]
