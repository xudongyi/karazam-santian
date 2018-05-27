[#-- {{{"use":"content","name":"mobile","des":"移动端默认模板"}}} --]
[@nestedstyle]
    [@css href="css/article/style.css" /]
[/@nestedstyle]
[@nestedscript]
    [@js src="js/article/article.js" /]
[/@nestedscript]
[@insert template="layout/content_mobile_layout" title="${content.title}" metaKey=content.metaKeywords metaDes=content.metaDescription user=kuser]
<div class="mui-content">
    <h1 style="font-weight: bold;padding-left: 10px;padding-top: 10px;text-align: center;">${content.title}</h1>
    <div class="instr">
        <span style="color: gray;padding: 10px;">[#noescape]${content.text}[/#noescape]</span>
    </div>
</div>
[/@insert]