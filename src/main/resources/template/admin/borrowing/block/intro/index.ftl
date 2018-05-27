<div class="easyui-panel" data-options="height:460,border:false">
    <textarea id="container" name="intro" style="width: 99%;">
        [#noescape]${borrowing.intro}[/#noescape]
    </textarea>
</div>
<script type="text/javascript" charset="UTF-8">
    if (typeof ue != 'undefined') {
        ue.destroy();
    }
    var ue = UE.getEditor('container', {
        initialFrameHeight:350,
        initialFrameWidth:980
    });
</script>