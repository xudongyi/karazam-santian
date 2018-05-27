[@insert template="admin/layout/default_layout" title="修改分类"]
<div>
    <form>
        <input name="action" value="update" hidden/>
        <input name="pk" value="${goods.id}" hidden/>
        <table id="basicInfo" class="tbform">
            <tr>
                <td>商品分类：</td>
                <td>
                    <input id="goodsCategory" name="goodsCategory" value="${goods.goodsCategory}" data-options="required:'required'"/>
                </td>
            </tr>
            <tr>
                <td>商品类型：</td>
                <td>
                    <select name="type" value="${goods.type}" class="easyui-combobox approvalDisabledElement" data-options="required:'required', width:150">
                        [#list types as type]
                            <option value="${type}" [#if goods.type==type]selected="selected"[/#if]>${type.displayName}</option>
                        [/#list]
                    </select>
                </td>
            </tr>
            <tr>
                <td>是否虚拟商品：</td>
                <td>
                    <select name="dummy" class="easyui-combobox approvalDisabledElement" data-options="required:'required', width:150">
                        <option value="1" [#if goods.dummy]selected="selected"[/#if]>是</option>
                        <option value="0" [#if !goods.dummy]selected="selected"[/#if]>否</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>是否上架：</td>
                <td>
                    <select name="putaway" class="easyui-combobox approvalDisabledElement" data-options="required:'required', width:150">
                        <option value="1" [#if goods.putaway]selected="selected"[/#if]>是</option>
                        <option value="0" [#if !goods.putaway]selected="selected"[/#if]>否</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>是否热门：</td>
                <td>
                    <select name="hot" class="easyui-combobox approvalDisabledElement" data-options="required:'required', width:150">
                        <option value="1" [#if goods.hot]selected="selected"[/#if]>是</option>
                        <option value="0" [#if !goods.hot]selected="selected"[/#if]>否</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>名称：</td>
                <td>
                    <input name="name" value="${goods.name}" class="easyui-textbox" data-options="required:'required',validType:'maxLength[20]'"/>

                </td>
            </tr>
            <tr>
                <td>原价格：</td>
                <td>
                    <input name="originalPrice" value="${goods.originalPrice}" class="easyui-textbox" data-options="validType:'checkFloat'"/>
                </td>
            </tr>
            <tr>
                <td>价格：</td>
                <td>
                    <input name="price" value="${goods.price}" class="easyui-textbox" data-options="validType:'checkFloat'"/>
                </td>
            </tr>
            <tr>
                <td>原积分：</td>
                <td>
                    <input name="originalPoint" value="${goods.originalPoint}" class="easyui-textbox" data-options="validType:'integer'"/>
                </td>
            </tr>
            <tr>
                <td>积分：</td>
                <td>
                    <input name="point" value="${goods.point}" class="easyui-textbox" data-options="validType:'integer'"/>
                </td>
            </tr>
            [#--<tr>
                <td>展示图片：</td>
                <td>
                    <input name="image" value="${goods.image}" class="easyui-textbox" />
                </td>
            </tr>--]
            <tr>
                <td>展示图片：</td>
                <td style="width: auto">
                    <div class="input-group">
                        <input id="image" name="image" class="easyui-textbox" value="${goods.image}"/>
                        <label class="input-group-addon" onclick="uploadLogo('image');">上传</label>
                    </div>
                </td>
            </tr>
            <tr>
                <td>展示图片(大)：</td>
                <td style="width: auto">
                    <div class="input-group">
                        <input id="imageLarge" name="imageLarge" class="easyui-textbox" value="${goods.imageLarge}" />
                        <label class="input-group-addon" onclick="uploadLogo('imageLarge');">上传</label>
                    </div>
                </td>
            </tr>
            <tr>
                <td>展示图片(详情)：</td>
                <td style="width: auto">
                    <div class="input-group">
                        <input id="imageDetail" name="imageDetail" class="easyui-textbox" value="${goods.imageDetail}" />
                        <label class="input-group-addon" onclick="uploadLogo('imageDetail');">上传</label>
                    </div>
                </td>
            </tr>
            <tr>
                <td>重量：</td>
                <td>
                    <input name="weight" value="${goods.weight}" class="easyui-textbox" data-options="validType:'checkFloat'"/>
                </td>
            </tr>
            <tr>
                <td>库存：</td>
                <td>
                    <input name="stock" value="${goods.stock}" class="easyui-textbox" data-options="validType:'integer'"/>
                </td>
            </tr>
           [#-- <tr>
                <td>已分配库存：</td>
                <td>
                    <input name="allocatedStock" value="${goods.allocatedStock}" class="easyui-textbox" data-options="validType:'integer'"/>
                </td>
            </tr>--]
            [#--<tr>
                <td>是否上架：</td>
                <td>
                    <input name="putaway" value="${goods.putaway}" class="easyui-textbox" />
                </td>
            </tr>--]
            <tr>
                <td>商品属性：</td>
                <td>
                    <input name="goodsAttr" value="${goods.goodsAttr}" class="easyui-textbox" />
                </td>
            </tr>
            <tr>
                <td>排序：</td>
                <td>
                    <input name="sort" value="${goods.sort}" class="easyui-textbox" data-options="validType:'integer'"/>
                </td>
            </tr>
            <tr>
                <td>页面标题：</td>
                <td>
                    <input name="seoTitle" value="${goods.seoTitle}" class="easyui-textbox" [#--data-options="validType:'chsEngNum'"--]/>
                </td>
            </tr>
            <tr>
                <td>页面关键字：</td>
                <td>
                    <input name="seoKeywords" value="${goods.seoKeywords}" class="easyui-textbox" [#--data-options="validType:'chsEngNum'"--]/>
                </td>
            </tr>
            <tr>
                <td>描述：</td>
                <td>
                    <textarea name="seoDescription"  class="easyui-validatebox" data-options="validType:'maxLength[50]'">${goods.seoDescription}</textarea>
                </td>
            </tr>
            [#--<tr>
                <td>介绍：</td>
                <td>
                    <textarea name="introduction" class="easyui-validatebox" data-options="validType:'maxLength[200]'">${goods.introduction}</textarea>
                </td>
            </tr>--]
            <tr>
                <td>备注：</td>
                <td>
                    <input name="memo" value="${goods.memo}" class="easyui-textbox" [#--data-options="validType:'chsEngNum'"--]/>
                </td>
            </tr>
            <tr>
                <td>介绍：</td>
                <td>
                    <div class="easyui-panel" data-options="height:460,border:false">
                        <textarea id="container" name="introduction" style="width: 99%;">
                            [#noescape]${goods.introduction}[/#noescape]
                        </textarea>
                    </div>
                </td>
            </tr>
        </table>
    </form>
</div>

[@js src="lib/jquery/jquery.ajaxfileupload.js" /]
<script type="text/javascript" charset="UTF-8">
    if (typeof ue != 'undefined') {
        ue.destroy();
    }
    var ue = UE.getEditor('container', {
        initialFrameHeight:350,
        initialFrameWidth:980
    });
</script>
<script type="text/javascript">

    //商品分类
    $('#goodsCategory').combobox({
        width: 200,
        method: 'GET',
        url: '${ctx}/admin/goods_category/json',
        valueField: 'id',
        textField: 'name',
        formatter: function(row){
            return row.name;
        }
    });


</script>
[/@insert]