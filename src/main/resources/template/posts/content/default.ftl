[#-- {{{"use":"content","name":"default","des":"默认模板"}}} --]
[@nestedstyle]
    [@css href="css/article/style.css" /]
[/@nestedstyle]
[@nestedscript]
    [#--[@js src="js/article/article.js" /]--]
[/@nestedscript]
[@insert template="layout/content_layout" title="${content.title}" metaKey=content.metaKeywords metaDes=content.metaDescription user=kuser]
<div class="container issue-article-container">
    <div class="article" style="margin: 30px 30px 30px 0">
        <div class="article-title">
            <h2>${content.title}</h2>
            <small class="muted"> ${(content.createDate?date)?string("yyyy-MM-dd HH:mm:ss")}</small>
        </div>
        <section class="article-content" id="article-content">
            [#noescape]${content.text}[/#noescape]
        </section>
    </div>
    <div class="link" style="margin: 30px 0 0 0">
        <h3>更多消息</h3>
        [@k_contents taxonomyType="category" taxonomySlug=content.category listSize=10]
            [#list contents as content]
                <div class="link-container">
                    <div class="link-ever">
                        <div class="title">
                            <a href="${ctx}/c/${content.slug}">${content.title}</a>
                        </div>
                        <div class="date">
                            ${(content.createDate?date)?string("yyyy-MM-dd HH:mm:ss")}
                        </div>
                    </div>
                </div>
            [/#list]
        [/@k_contents]
    </div>
</div>
[/@insert]