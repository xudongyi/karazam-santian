/**
 * Created by limat on 2017/4/19.
 */
var action = {
    rootPath: urlbase + '/uc/assets',
    timeout: 2000,
    method: 'post', //default method
    interface: {
        userInfo: '/userInfo',
        receivableDetail: '/receivableDetail4Calendar',
        zichanAll: '/assets/accountFlowList',
        zichanAllExport: '/assets/exportFlowExcel',
        zichanChongzhi: '/assets/rechargeRecordList',
        zichanTixian: '/assets/withdrawalRecordList',
        cancelTixian: '/assets/cancelWithdrawal',
        touziAll: '/tender/tenderDetailList',
        touziAllExport: '/tender/exportTenderExcel',
        touziYouxuan: '/tender/tenderDetailList',
        touziHuikuanPlan:'/tender/investorRecoverList',
        collectedDetail: '/receivableDetail4List',
        shoukuanDaishou:'/receivables/receivablesDetailList',
        shoukuanDaishouExport: '/receivables/exportReceivablesExcel',
        shoukuanYishou:'/receivables/receivedDetailList',
        shoukuanYishouExport: '/receivables/exportReceivedExcel',
        zhaiquanKezhuan:'/debtRight/canTransferList',
        zhaiquanZhuanzhong:'/debtRight/transferingList',
        zhaiquanYizhuan:'/debtRight/transferedList',
        zhaiquanYijie:'/debtRight/undertakeList',
        zidongToubiao:"/tender/autoTenderList",
        zidongToubiaoExport:"/tender/exportAutoTenderExcel",
        redPackageList:'/redPackage/list',
        redPackageCount:'/redPackage/count',
        innerMessageList:'/systemMessageList',   ///account/innerMessageList
        inviteRecord:'/accountInfo.html' ,  //
        sendMessage:'/getMessageSwitch',//获取消息开关
        updateMessageSwitch:'/updateMessageSwitch',
        summaryInviteInfo:'/accountInfo.html',
        assetPacketList:'/list/assetPacketList',
        bidList:'/list/bidList',
        transferList:'/list/transferList',
        viewTransferedBid:'/bid/showTransferDetail',
        interestRateCouponlist:'/interestRateCouponlist',
        interestRateCouponRecovery:'/interestRateCouponRecovery',
        bidInterestRateCouponRecovery:'/bidInterestRateCouponRecovery',
        couponSummaryCount:'/couponSummaryCount',
        useableCount:'/redPackage/useableCount',
        authUserExist:'/regs/authUserExist',
        personalSafety:'/personal/safety',
        sendMobileCode:'/getBackPasswd/sendMobileCode',
        verifyCode:'/getBackPasswd/verifyCode',
        resetLoginPasswd:'/getBackPasswd/resetLoginPasswd',
        sendMessage:'/getMessageSwitch',
    }
}

var centerNavSetting = {
    "iconfont": [],
    "icon":["fa fa-eye;","&#xe648;","&#xe607;","&#xe608;","&#xe60c;","&#xe649;","&#xe611;","&#xe601;","&#xe647;","&#xe60b;","&#xe60a;"],
    "name": ["账户总览", "资金管理", "投资记录", "收款明细", "个人资料", "邀请奖励"],
    "href": ["/webstormpro/Sailor/account/assets/accountInfo.html", "/webstormpro/Sailor/account/assets/accountFlow.html", "/webstormpro/Sailor/account/assets/tenderDetailAll.html", "/webstormpro/Sailor/account/assets/receivablesDetail.html",
          "/webstormpro/Sailor/account/assets/safety.html", "/webstormpro/Sailor/account/assets/myInvitation.html"]

};

// module.exports = action;