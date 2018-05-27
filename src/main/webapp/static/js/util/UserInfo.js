/**
 * Created by limat on 2017/4/19.
 */
var tools = {
    /*
     * 用*隐藏姓名中名的部分
     * @param name 姓名
     * @return string
     */
    hideName: function(name) {
        if (name == '' || name == null || name == undefined) return;
        var _hName = '';
        var _length = name.length,
            _startHide = 1; //开始带*的位置
        if (_length == 4) { //如果姓名长度是四个字，从第三位开始打*
            _startHide = 2;
        } else if (_length > 4 && name.indexOf('.') > -1) {
            _startHide = name.indexOf('.') + 1;
        }
        _hName = name.substring(0, _startHide); //姓的部分
        for (var i = _startHide; i < name.length; i++) {
            _hName += '*';
        }
        return _hName;
    },
    hideTelephone: function(telephone) {
        if (telephone == '' || telephone == null || telephone == undefined) return;
        return telephone.substring(0, 3) + '****' + telephone.substring(7);
    },
    hideIdcard: function(Idcard) {
        if (Idcard == '' || Idcard == null || Idcard == undefined) return;
        return Idcard.substring(0, 1) + '*************' + Idcard.substring(14);
    },
    hideEmail: function(Email) {
        if (Email == '' || Email == null || Email == undefined) return;
        var x = Email.indexOf("@");
        return Email.substring(0, 3) + '****' + Email.substring(x);
    },
    hideBank: function(bank) {
        if (bank == '' || bank == null || bank == undefined) return;
        return bank.substring(0, 3) + '****************' + bank.substring(15);
    },
    //获取url参数
    getQueryString: function(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return (r[2]);
        return null;
    }
}
//ajax请求公共配置
var ajaxParam = {
    type: 'post',
    timeout: 3000,
    dataType: 'json',
    error: function(xhr, textStatus, errorThrown) {
        alert('提交失败。原因为：' + textStatus);
    }
}
//placeholder兼容IE8
$(function () {
    if (!('placeholder' in document.createElement('input'))) {

        $('input[placeholder],textarea[placeholder]').each(function () {
            var that = $(this),
                text = that.attr('placeholder');
            if (that.val() === "") {
                that.val(text).addClass('placeholder');
            }
            that.focus(function () {
                    if (that.val() === text) {
                        that.val("").removeClass('placeholder');
                    }
                })
                .blur(function () {
                    if (that.val() === "") {
                        that.val(text).addClass('placeholder');
                    }
                })
                .closest('form').submit(function () {
                if (that.val() === text) {
                    that.val('');
                }
            });
        });
    }
})
//返回数组长度
function lengthNum(num){
    var num = num.length-1;
    return num;
}
/**
 * 时间戳转换时间格式yy-mm-dd
 *@param{Object}time
 */
function timeFormat(time){
    var now = new Date(parseInt(time));
    var yy = now.getFullYear();      //年
    var mm = now.getMonth() + 1;     //月
    var dd = now.getDate();          //日
    var clock = yy + "-";
    if(mm < 10) clock += "0";
    clock += mm + "-";
    if(dd < 10) clock += "0";
    clock += dd + " ";
    return clock;
}
/**
 * 时间戳转换时间格式yy.mm.dd
 *@param{Object}time
 */
function timeFormatDot(time){
    var now = new Date(parseInt(time));
    var yy = now.getFullYear();      //年
    var mm = now.getMonth() + 1;     //月
    var dd = now.getDate();          //日
    var clock = yy + ".";
    if(mm < 10) clock += "0";
    clock += mm + ".";
    if(dd < 10) clock += "0";
    clock += dd + " ";
    return clock;
}
/**
 * 时间戳转换时间格式yy.mm.dd
 *@param{Object}time
 */
function timeFormatDott(time){
    var now = new Date(parseInt(time));
    var mm = now.getMonth() + 1;     //月
    var dd = now.getDate();          //日
    if(mm < 10) mm = "0" + mm;
    var clock = mm + ".";
    if(dd < 10) clock += "0";
    clock += dd + ".";
    return clock;
}
/**
 * 时间戳转换时间格式yy-mm-dd hh-ii-ss
 *@param{Object}time
 */
function timeNormal(time){
    var now = new Date(parseInt(time));
    var yy = now.getFullYear();      //年
    var mm = now.getMonth() + 1;     //月
    var dd = now.getDate();          //日
    var hh = now.getHours();         //时
    var ii = now.getMinutes();       //分
    var ss = now.getSeconds();       //秒
    var clock = yy + "-";
    if(mm < 10) clock += "0";
    clock += mm + "-";
    if(dd < 10) clock += "0";
    clock += dd + " ";
    if(hh < 10) clock += "0";
    clock += hh + ":";
    if(ii < 10) clock += "0";
    clock += ii + ":";
    if(ss < 10) clock += "0";
    clock += ss + " ";
    return clock;
}

