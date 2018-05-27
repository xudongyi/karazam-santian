
/**
 * Created by suhao on 2017/4/21.
 */

$(function () {
    var ucconf = {
        action : {
            index : ctx + "/uc/index",
            capital : ctx + "/uc/order",
            investments : ctx + "/uc/investment",
            recovery : ctx + "/uc/recovery",
            borrowing: ctx + "/uc/borrowing",
            transfer : ctx + "/uc/transfer",
            autoInvestment : ctx + "/uc/autoInvestment",
            security : ctx + "/uc/security",
            referral : ctx + "/uc/referral",
            point : ctx + "/uc/point",
            coupon : ctx + "/uc/userCoupon",
            address : ctx + "/uc/shipping_address"
        }
    };

    var menu = $('#mNavId').find('ul li');
    menu.on('click', function () {
        var $this = $(this);
        $this.addClass('cur').siblings('li').removeClass('cur');

        var action = $this.attr('action');
        window.location.href = ucconf.action[action];
    })
});
