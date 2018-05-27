[#-- {{{"use":"category","name":"default","des":"默认分类"}}} --]
[@nestedstyle]
    [@css href="css/flexslider.css" /]
    [@css href="css/cubeportfolio.min.css" /]
    [@css href="css/style.css" /]
    [@css href="css/floatbutton.css" /]
    [@css href="css/article/style.css" /]
    [@css href="css/skins/default.css" /]
[/@nestedstyle]
[@nestedscript]
    [@js src="js/posts/category/default.js" /]
[/@nestedscript]
[@insert template="layout/taxonomy_layout" title="${taxonomy.title}" metaKey=taxonomy.metaKeywords metaDes=taxonomy.metaDescription currentUser=kuser user=kuser]
<div class="sub-nav">
    <div class="security-nav-container container">
        <ul>
            [@k_taxonomy type="category"  slug=slug whois=whois]
                [#list taxonomies as _taxonomy ]
                    [#if whois=="brothers"]
                        [#assign _slug = taxonomy.slug /]
                    [#else]
                        [#if _taxonomy_index == 0]
                            [#assign _slug = _taxonomy.slug /]
                        [/#if]
                    [/#if]
                    <li taxonomyType="${_taxonomy.type}" taxonomySlug="${_taxonomy.slug}" class="taxonomy security-nav article_active [#if _taxonomy.slug==_slug]active[/#if]">
                        <a href="javascript:void(0);" class="secondCategorie">${_taxonomy.title}</a>
                    </li>
                [/#list]
            [/@k_taxonomy]
        </ul>
    </div>
</div>
<div class="container issue-container">
    <div class="row">
        <article class="col-md-12 info-list">
            <div id="dataBox" class="issue-list"></div>
            <div class="pageList" id="pagination" style="text-align: right; margin-top: 5px;"></div>
        </article>
    </div>
</div>
<script id="dataTpl" type="text/html">
    {{#  $.each(d.contents.rows, function(index, item){ }}
    <section class="issue-section">
        <div class="issue-ever">
            {{# if(d.contents.rows.length!=1){ }}
                <div class="issue-list-title">
                    <span class="title">
                        <a target="_blank" href="${ctx}/c/{{item.slug}}">{{item.title}}</a>
                        {{# if (index==0 && d.contents.pageNum==1) { }}
                            <i class="hot"><img src="${ctx}/static/images/safe/hot.png"></i>
                        {{# } }}
                    </span>
                </div>
                <small class="muted">{{item.createDate}}</small>
                <p>
                    {{# if (item.summary != '') { }}
                        {{item.summary}}
                    {{# } }}
                </p>
            {{# }else{ }}
                <p style=""> {{item.text}}</p>
            {{# } }}
        </div>
    </section>
    {{#  }); }}
</script>
[/@insert]