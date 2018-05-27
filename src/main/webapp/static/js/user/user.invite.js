/**
 * Created by limat on 2017/4/19.
 */
$(function () {
    // 复制到剪切板
    if (window.clipboardData) {
        $('#copyBtn').click(function () {
            window.clipboardData.setData("Text", $(this).prev('input').val());
            layer.msg('复制成功！');
        });
    } else {
        var clip = new ZeroClipboard($("#copyBtn"), {
             moviePath: "../../lib/zeroclipboard/ZeroClipboard.swf"
        });
        clip.on('error', function (event) {
            layer.msg('复制失败，请手动复制。');
        });
        clip.on('aftercopy', function (event) {
            layer.msg('复制成功！');
        });

    }
    //分享
    $('#share').share({sites: ['wechat', 'weibo', 'qzone']});
    //初始化邀请记录列表
    // var page = 1;
    // inviteRecordAjax(1, 10);
    // inviteSumAjax();
    //
    // // 消息列表请求
    // function inviteRecordAjax(pageNum, rowsNum) {
    //     $.ajax({
    //         type: 'get',
    //         url: window.action.rootPath + window.action.interface.inviteRecord,
    //         data: {
    //             'page': pageNum,
    //             'rows': rowsNum
    //         },
    //         success: function (result) {
    //             if (result.success) {
    //                 allData(result.data);
    //             } else {
    //                 alert(result.message);
    //             }
    //         },
    //         error: function (jqXHR, textStatus, errorThrown) {
    //             alert(errorThrown);
    //         }
    //     });
    // }
    //
    // function allData(result) {
    //     if ($("#inviteListInfo")) {
    //         $("#inviteListInfo").empty();
    //     }
    //     for (var i = 0; i < result.data.length; i++) {
    //         // console.log(result.data[i].mobile);
    //         var _phone = result.data[i].mobile, //推荐账户
    //             _createdTime = timeFormatDot(result.data[i].createTime), //注册时间
    //             _status = result.data[i].isSuc; //是否符合奖励
    //         _phone = tools.hideTelephone(_phone); //*号隐藏
    //         var statusClass = '';
    //         if (_status == 1) {
    //             statusClass = '否';
    //         } else if (_status == 0) {
    //             statusClass = '是';
    //         }
    //         var inviteListInfoHtml = '<li class=' + statusClass + '>' +
    //             '<span class="phone">' + _phone + '</span><span class="time">' + _createdTime + '</span><span class="boolean">' + statusClass + '</span></li>';
    //         $("#inviteListInfo").append(inviteListInfoHtml);
    //         if (result.count < 10) {
    //             $(".pageList").hide();
    //         }
    //     }
    //     $.creatPageNav({
    //         selector: '.pageList',
    //         page: page,
    //         totalpage: Math.ceil(result.count / 10),
    //         clickCallback: function (newpage) {
    //             page = newpage;
    //             inviteRecordAjax(newpage, 10);
    //         }
    //     });
    // }
    //
    // //累积推荐
    // function inviteSumAjax() {
    //     $.ajax({
    //         type: 'get',
    //         url: window.action.rootPath + window.action.interface.summaryInviteInfo,
    //         success: function (result) {
    //             if (result.success) {
    //                 if (result.success) {
    //                     ajaxSucc(result);
    //                 } else {
    //                     alert(result.message);
    //                 }
    //             } else {
    //                 alert(result.message);
    //             }
    //         },
    //         error: function (jqXHR, textStatus, errorThrown) {
    //             alert(errorThrown);
    //         }
    //     });
    // }
    //
    // function ajaxSucc(result) {
    //     if (result.success) {
    //         //allData(result);
    //         var _jiangli = result.data.sumAmount, //我的奖励
    //             _tuijian = result.data.inviteTotal, //累积推荐
    //             _youxiao = result.data.succInviteTotal; //有效推荐
    //         $("#jiangli").html(_jiangli);
    //         $("#leiji").html(_tuijian);
    //         $("#youxiao").html(_youxiao);
    //
    //     } else {
    //         alert(result.message);
    //     }
    // }

})