[@nestedstyle]
    <link rel="stylesheet" href="${ctx}/static/lib/layui/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/mall/address.css">
[/@nestedstyle]
[@nestedscript]
    [@js src="js/jquery.lSelect.js" /]
    [@js src= "lib/layui/lay/dest/layui.all.js" /]
    [@js src="lib/validate/jquery.validate.min.js" /]
    [@js src="lib/validate/jquery.validate.method.min.js" /]
    [@js src="js/user/user.address.js" /]
[/@nestedscript]
[@insert template="layout/uc_layout" title="用户中心-地址管理" memberContentId="zichan-all" module="point" nav = "point" currentUser=kuser user=kuser]

<div class="account-layout-wrap">
    <div class="account-layout-wrap">
        <div class="account-container account page-account-invest"><h1 class="new-title">地址管理</h1>
            <div class="jifen_part">
                <div class="space s-15"></div>
                <div style="display:block;" class="jifen_detail_area">
                    <div class="jifen_add_topaddress">
                        [#if addresses?size < 10]
                            <span class="left_add_adress add_common_ad_yes">新增收货地址</span>
                        [/#if]
                        <span>您已创建</span><span class="number_adress">${addresses?size!0}</span>
                        <span>个收货地址，最多可创建</span><span>10</span><span>个</span>
                    </div>
                    <div class="jifen_add_item data">
                        [#list addresses as address]
                            <div class="address_item_list">
                                [#if address.preferred]<div class="normalIcon"></div>[/#if]
                                <ul class="address_alist">
                                    <li><span class="left">收货人：</span> <span class="right">${address.consignee}</span></li>
                                    <li><span class="left">手机：</span> <span class="right">${address.mobile}</span></li>
                                    <li><span class="left">地址：</span> <span class="right">${address.areaObj.fullName}${address.address}</span></li>
                                </ul>
                                <p class="add_operation">
                                <span class="editAdress" data-id="${address.id}" data-consignee="${address.consignee}" data-mobile="${address.mobile}"
                                      data-area="${address.area}" data-area-tree="${address.areaTreePath}" data-address="${address.address}" data-consignee="${address.consignee}" data-preferred="${address.preferred}">
                                    编辑</span>
                                    <span class="deleteAdress" data-id="${address.id}">删除</span>
                                </p>
                            </div>
                        [/#list]
                    </div>
                    [#if addresses?size < 10]
                        <div class="jifen_add_footer">
                            <span class="add_common_ad_yes">新增收货地址</span>
                        </div>
                    [/#if]
                    <div class="height_20"></div>
                </div>
                [#--<div class="nodeal_bg">
                    <p>您还没有相关订单，前往<a href="" class="orange">积分商城</a>随便逛逛吧！</p>
                </div>
                <div class="noattention">
                    <p>您还没有添加关注商品，前往<a href="" class="orange">积分商城</a>随便逛逛吧！</p>
                </div>
                <div class="pagination-wrapper"></div>--]

            </div>
        </div>

        <div class="jifen_common_mask" style="display: none;"></div>

        <div class="jifen_common_delete" style="display: none;">
            <form id="addressDeleteForm" type="get">
                <a class="jifen_close del_ad_no"></a>
                <h2>提示</h2>
                <div class="tip_error">您确定要删除该收货地址吗？</div>
                <p class="btn_adress">
                    <span class="le del_ad_su">确 定</span>
                    <span class="ri del_ad_no">取 消</span>
                </p>
            </form>
        </div>

        [#--<div class="jifen_common_attention">
            <a class="jifen_close dele_atten_no"></a>
            <h2>提示</h2>
            <div class="tip_error">确定取消关注吗？</div>
            <p class="btn_adress">
                <span class="le dele_atten_sure">确 定</span>
                <span class="ri dele_atten_no">取 消</span>
            </p>
        </div>--]

        <div class="jifen_error_network"><a class="jifen_close error_atten_no"></a>
            <h2>提示</h2>
            <div class="tip_error error_net_tip"></div>
            <p class="btn_adress">
                <span class="le error_atten_no">确 定</span>
            </p>
        </div>

        <div class="jifen_common_add" style="display: none;height: auto;">
            <form id="addressBox">
            <input type="hidden" name="addressId" />
            <a class="jifen_close ad_adr_noa"></a>
            <h2>收货地址</h2>
            <ul class="addlist">
                <li>
                    <span class="l">收货人：</span>
                    <input type="text" id="consignee" name="consignee" maxlength="15" class="common_inp consigneeName">
                    <span class="aderror"></span>
                </li>
                <li>
                    <span class="l">手机号码：</span>
                    <input type="text" id="mobile" name="mobile" maxlength="11" class="common_inp consigneeMobile">
                    <span class="aderror"></span>
                </li>
                <li [#--style="padding-bottom:16px;" class="clearfix speail-common-list"--]>
                    <span class="l">地区：</span>
                    <input type="hidden" id="area" name="area" class="common_inp consigneeDetail" />
                    <span class="aderror"></span>
                </li>
                <li style="padding-bottom:16px;" class="clearfix speail-common-list">
                    <span class="l">详细地址：</span>
                    <input id="address" name="address" type="text" maxlength="120" class="common_inp consigneeDetail">
                    <span class="aderror"></span>
                </li>
                <li style="padding-bottom:16px">
                    <span class="l"></span>
                    <input id="preferred" name="preferred" value="true" type="checkbox" class="jifencheck">
                    <span class="foo">设为默认地址</span>
                </li>
                <li>
                    <span class="l"></span>
                    <a href="javascript:;" class="saveAdress">保存收货人信息</a>
                </li>
            </ul>
            </form>
        </div>

    </div>
</div>

[/@insert]

