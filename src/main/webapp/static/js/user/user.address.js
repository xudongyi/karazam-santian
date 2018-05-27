/**
 * Created by chenxinglin
 */

var isAdd = true;
var url = "";
var params = {
    id: "",
    consignee : "",
    mobile : "",
    area: "1",
    address : "",
    preferred : "",
}

var $addressBox = $("#addressBox"),$addressId = $addressBox.find("[name='addressId']"),$consignee = $addressBox.find("[name='consignee']"),
    $mobile = $addressBox.find("[name='mobile']"),$area = $addressBox.find("[name='area']"),$address = $addressBox.find("[name='address']"),
    $preferred = $addressBox.find("[name='preferred']");
var $validate = null;

$(function () {

    $validate = $addressBox.validate({
        rules: {
            consignee: "required",
            mobile: {
                required: true,
                pattern: /^1[3|4|5|7|8][0-9]{9}$/
            },
            address: "required",
            locality: "required",
        },
        messages: {
            consignee: "请输入收货人",
            mobile: {
                required: "请输入您的手机号码",
                pattern: "请输入正确的手机号码"
            },
            address: "请输入收货地址",
            locality: "请选择地区"
        },
        errorPlacement: function(error, element) {
            element.parent().find(".aderror").text(error.text());
        },
        unhighlight: function(element) {
            $(element).parent().find(".aderror").text("");
        },
        submitHandler: function(form) {
            if(isAdd){
                url = ctx + "/uc/shipping_address/add";
                params.consignee = $consignee.val();
                params.mobile = $mobile.val();
                params.area = $area.val();
                params.address = $address.val();
                params.preferred = $("input[type='checkbox']").is(':checked');
            }else {
                url = ctx + "/uc/shipping_address/update";
                params.id = $addressId.val();
                params.consignee = $consignee.val();
                params.mobile = $mobile.val();
                params.area = $area.val();
                params.address = $address.val();
                params.preferred = $("input[type='checkbox']").is(':checked');
            }

            $.ajax({
                url: url,
                data: params,
                type: "post",
                dataType: "json",
                cache: false,
                beforeSend: function(request, settings) {
                },
                success: function (res) {
                    if(res.status == 'success'){
                        layer.msg(res.message,{icon: 1});
                        location.href = ctx + "/uc/shipping_address";
                    }else {
                        layer.msg(res.message,{icon: 1});
                    }
                },
                error: function() {
                    layer.msg("操作失败",{icon: 1});
                }
            });
            $(".jifen_common_mask").hide();
            $('#jifen_common_add').hide();
        }
    });

    arealSelect();

    /** 新增 */
    $('.add_common_ad_yes').on('click', function () {
        isAdd = true;

        $addressId.val("");
        $consignee.val("");
        $mobile.val("");
        $area.val("");
        $address.val("");
        $preferred.val("");
        arealSelect();
        $(".aderror").text("");
        $(".jifen_common_mask").show();
        $(".jifen_common_add").show();
    });

    /** 修改 */
    $('.editAdress').on('click', function () {
        isAdd = false;
        $addressId.val($(this).attr("data-id"));
        $consignee.val($(this).attr("data-consignee"));
        $mobile.val($(this).attr("data-mobile"));
        $area.val($(this).attr("data-area"));
        $address.val($(this).attr("data-address"));
        $preferred.val($(this).attr("data-preferred"));
        $area.attr({"value": $(this).attr("data-area"), "treePath": $(this).attr("data-area-tree")});
        $(".aderror").text("");
        arealSelect();
        $(".jifen_common_mask").show();
        $(".jifen_common_add").show();
    });

    /** 保存 */
    $('.saveAdress').on('click', function() {
        $addressBox.submit();
    });

    /** 删除 */
    var deleteId = "";
    $('.deleteAdress').on('click', function () {
        deleteId = $(this).attr("data-id");
        $("#addressDeleteForm").attr("action", ctx + "/uc/shipping_address/delete/"+deleteId);
        $(".jifen_common_mask").show();
        $(".jifen_common_delete").show();
    });
    /** 删除 */
    $('.del_ad_su').on('click', function () {
        $("#addressDeleteForm").submit();
    });

    /** 新增/修改 关闭 */
    $('.jifen_close').on('click', function () {
        $(".jifen_common_mask").hide();
        $(".jifen_common_add").hide();
    });
    /** 删除 关闭 */
    $('.del_ad_no').on('click', function () {
        $(".jifen_common_mask").hide();
        $(".jifen_common_delete").hide();
    });



});

function arealSelect() {
    $("#area").lSelect({
        url: "/area/jsons",
        className: "ignore"
    });
    $("#area").change(function() {
        $validate.element(this);
    });
}