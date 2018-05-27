[#-- {{{"use":"category","name":"help_detail","des":"帮助中心详情"}}} --]
[@nestedstyle]
    [@css href="css/flexslider.css" /]
    [@css href="css/cubeportfolio.min.css" /]
    [@css href="css/style.css" /]
    [@css href="css/floatbutton.css" /]
    [@css href="css/article/style.css" /]
    [@css href="css/skins/default.css" /]
[/@nestedstyle]
[@nestedscript]
    [@js src="js/posts/category/help.detail.js" /]
[/@nestedscript]
[@insert template="layout/taxonomy_layout" title="帮助中心" metaKey=taxonomy.metaKeywords metaDes=taxonomy.metaDescription currentUser=kuser user=kuser]
<div class="help-category">
    <div class="category-wrap">
        <div class="container">
            <div class="row">
                <div class="span2 help-menu-wrap">
                    <ul id="help-menu">
                        <span id="problemCategoryBox"></span>
                        <hr>
                        <span id="flowBootCategoryBox"></span>
                        <span id="selfServiceCategoryBox"></span>
                    </ul>
                </div>
                <div class="span10" style="width: 925px">
                    <div class="qa-panel" style="min-height: 404px;" id="problemChildTaxBox"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<script id="problemCategoryTpl" type="text/html">
    {{# $.each(d.children, function(index, item) { }}
        <li class="test"><a class="category {{# if(item.slug==slug) { }} active {{# } }}" taxonomyType="{{ item.type }}" taxonomySlug="{{ item.slug }}">{{ item.title }}</a></li>
    {{# }); }}
</script>
<script id="problemChildTaxTpl" type="text/html">
    {{# if (d.contents.total==1) { }}
        <div class="info"><span>{{ d.contents.rows[0].title }}</span></div>
        <hr>
        <p style="text-align: center">{{ d.contents.rows[0].text }}</p>
    {{# } else { }}
        {{# $.each(d.contents.rows, function(index, item) { }}
            <div class="question">
                <h3><a href="javascript:void(0)" contentId="{{ item.id }}" class="contentDetail">{{item.title}}</a></h3>
                <p>{{item.summary}}</p>
            </div>
        {{# }); }}
    {{# } }}
</script>
<script id="problemContentTpl" type="text/html">
    <div class="info"><span>{{ d.title }}</span></div>
    <hr>
    <p style="text-align: center">{{ d.text }}</p>
</script>

<script id="flowBootCategoryTpl" type="text/html">
    <li class="test"><a class="category {{# if(d.slug==slug) { }} active {{# } }}" taxonomyType="{{ d.type }}" taxonomySlug="{{ d.slug }}">{{ d.title }}</a></li>
</script>

[/@insert]
