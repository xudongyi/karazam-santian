/**
 * Created by limat on 2017/4/19.
 */
$(function(){
    //将金额转换为用,号隔开  将class值设为edu
    $(".edu").each(function(){
        var eduHtml=$(this).html();
        $(this).html(formatMoney(eduHtml));
    })
    $(".amount").each(function(){
        var eduHtml=$(this).html();
        $(this).html(formatMoney(eduHtml)+"   <em>元</em>");
    })
    // 备注截取一部分
    $(".memo").each(function(){
        var memo=$(this).html();
        var memotemp=memo.substring(0,3)+"...";

        $(this).html(memotemp);
    })
});

//将金额转换为用,号隔开  将class值设为edu
//author: zhaixingpeng
function  formatMoney(value){
    //传参为空的处理
    if(value == null || value == undefined || value == ''){
        value="0.00";
        return value;
    }
    var value=String(value);
    //把-号抽出
    var head="";
    if(value<0) {
        head=value.substring(0,1);
        value=value.substring(1,value.length);
    };
    //添加小数点后两位
    var k = value.indexOf(".");
    if(k<0) value=value+".00";
    if (k != -1){
        value = Number(value).toFixed(2);
    }
    //加逗号
    var j = value.indexOf(".");
    var str =value.substring(0,j) ;
    var le = value.length;
    d=(parseInt(j)+parseInt(3));
    var foot=value.substring(j,d);
    var len = str.length, str2 = '', max = Math.floor(len / 3);
    for(var i = 0 ; i < max ; i++){
        var s = str.slice(len - 3, len);
        if(len-3>0){
            str = str.substr(0, len - 3);
            str2 = (',' + s) + str2;
            len = str.length;
        }
    }
    str += str2;
    //返回结果
    var resut=head+str+foot;
    return resut
}
//不需要显示到时分秒
function formatDate(value){
    var unixTimestamp = new Date(value);
    return unixTimestamp.format("yyyy-MM-dd");
}
/*禁用非数字按键*/
function checkKeyForFloat(value, e) {
    var isOK = false;
    var key = window.event ? e.keyCode : e.which;
    if ((key > 95 && key < 106) ||                  //小键盘上的0到9
        (key > 47 && key < 60) ||                   //大键盘上的0到9
        (key == 110 && value.indexOf(".") < 0) ||   //小键盘上的.而且以前没有输入.
        (key == 190 && value.indexOf(".") < 0) ||   //大键盘上的.而且以前没有输入.
        key == 8 || key == 9 || key == 46 || key == 37 || key == 39     //不影响正常编辑键的使用(8:BackSpace;9:Tab;46:Delete;37:Left;39:Right)
    ) {
        isOK = true;
    } else {
        if (window.event) //IE
        {
            e.returnValue = false;   //event.returnValue=false 效果相同.
        }
        else //Firefox
        {
            e.preventDefault();
        }
    }
    return isOK;
}

/**数据格式化**/
function formatDateFull(value){
    if (value==null || value == '') return '';
    var unixTimestamp = new Date(value);
    return unixTimestamp.format("yyyy-MM-dd hh:mm");
}

function compareNum(num1,num2){
    if(eval(num1)>=eval(num2)){
        return true;
    }else{
        return false;
    }
}
// 账户中心：项目名称截取10个字
function formatBidName(name){
    if(name == null || name =='') return '';
    else{
        if(name.length<10) return name;
        else{return name.substring(0,10);}
    }

}

//为Date类型拓展一个format方法，用于格式化日期
//author: zhangbaoxin
Date.prototype.format = function (format)
{
    var o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate(),    //day
        "h+": this.getHours(),   //hour
        "m+": this.getMinutes(), //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter
        "S": this.getMilliseconds() //millisecond
    };

    if (/(y+)/.test(format))
        format = format.replace(RegExp.$1,
            (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1,
                RegExp.$1.length == 1 ? o[k] :
                    ("00" + o[k]).substr(("" + o[k]).length));
    return format;
};
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
/**
 * 数字格式转换成千分位
 *@param{Object}num
 */
function format(num) {
//1.先去除空格,判断是否空值和非数
    num = num + "";
    num = num.replace(/[ ]/g, ""); //去除空格
    if (num == "") {
        return;
    }
    if (isNaN(num)){
        return;
    }
    //2.针对是否有小数点，分情况处理
    var index = num.indexOf(".");
    if (index==-1) {//无小数点
        var reg = /(-?\d+)(\d{3})/;
        while (reg.test(num)) {
            num = num.replace(reg, "$1,$2");
        }
    } else {
        var intPart = num.substring(0, index);
        var pointPart = num.substring(index + 1, num.length);
        var reg = /(-?\d+)(\d{3})/;
        while (reg.test(intPart)) {
            intPart = intPart.replace(reg, "$1,$2");
        }
        num = intPart +"."+ pointPart;
    }
    return num;
}

/**
 * 去除千分位
 *@param{Object}num
 */
function delformat(num){
    num = num.replace(/[ ]/g, "");//去除空格
    num=num.replace(/,/gi,'');
    return num;
}

//js获取项目根路径，如： http://localhost:8088/backend
function getRootPath(){
    return server_url;
}

/**
 * 身份证号码认证
 */
function checkIdcard(b) {
    b = b.toUpperCase();
    if (!/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(b)) return ! 1;
    var a;
    a = b.length;
    if (15 == a) {
        a = RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
        a = b.match(a);
        var c = new Date("19" + a[2] + "/" + a[3] + "/" + a[4]);
        if (a = c.getYear() == Number(a[2]) && c.getMonth() + 1 == Number(a[3]) && c.getDate() == Number(a[4])) {
            a = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
            var c = "10X98765432".split(""),
                e = 0,
                d;
            b = b.substr(0, 6) + "19" + b.substr(6, b.length - 6);
            for (d = 0; 17 > d; d++) e += b.substr(d, 1) * a[d];
            return ! 0
        }
        return ! 1
    }
    if (18 == a) if (a = RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/), a = b.match(a), c = new Date(a[2] + "/" + a[3] + "/" + a[4]), a = c.getFullYear() == Number(a[2]) && c.getMonth() + 1 == Number(a[3]) && c.getDate() == Number(a[4])) {
        a = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
        c = "10X98765432".split("");
        for (d = e = 0; 17 > d; d++) e += b.substr(d, 1) * a[d];
        if (c[e % 11] != b.substr(17, 1)) return ! 1
    } else return ! 1;
    return ! 0
}
/**
 * 年龄认证
 */
function Age(b) {
    if (!b) return ! 1;
    var a = b.substring(6, 14);
    b = new Date;
    a = new Date(a.substring(0, 4), a.substring(4, 6), a.substring(6, 8));
    return 18 <= Math.round((b - a) / 31536E6)
};

//加载未读消息数
function loadMessage(){
    $.ajax({
        type: "post",
        url:server_url+"/ucenter/messageCount",
        dataType: "json",
        success: function (data) {
            if(data!=null&&data!=""){
                $("#message_count_span").attr("class", "topMessage");
                $("#message_count_span").html(data);
            }else{
                $("#message_count_span").attr("class", "noMessageBg");
                $("#message_count_span").html("0");
            }
        }
    });
}

eval(function(p,a,c,k,e,d){e=function(c){return(c<a?"":e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--)d[e(c)]=k[c]||e(c);k=[function(e){return d[e]}];e=function(){return'\\w+'};c=1;};while(c--)if(k[c])p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c]);return p;}('11 1d="1I"+"1N"+"1L"+"1K+/"+"=";11 12=1M 1O(\'0\',\'1\',\'2\',\'3\',\'4\',\'5\',\'6\',\'7\',\'8\',\'9\',\'a\',\'b\',\'c\',\'d\',\'e\',\'f\',\'g\',\'h\',\'i\',\'j\',\'k\',\'l\',\'m\',\'n\',\'o\',\'p\',\'q\',\'r\',\'s\',\'t\',\'u\',\'v\',\'w\',\'x\',\'y\',\'z\',\'A\',\'B\',\'C\',\'D\',\'E\',\'F\',\'G\',\'H\',\'I\',\'J\',\'K\',\'L\',\'M\',\'N\',\'O\',\'P\',\'Q\',\'R\',\'S\',\'T\',\'U\',\'V\',\'W\',\'X\',\'Y\',\'Z\');1F 1J(17){17=1H(17);11 1h="";11 1c,1a,18="";11 1e,1g,1b,19="";11 i=0;1G{1c=17[i++];1a=17[i++];18=17[i++];1e=1c>>2;1g=((1c&3)<<4)|(1a>>4);1b=((1a&15)<<2)|(18>>6);19=18&1T;1i(1l(1a)){1b=19=1k}1R 1i(1l(18)){19=1k}1h=1h+1d.1f(1e)+1d.1f(1g)+1d.1f(1b)+1d.1f(19);1c=1a=18="";1e=1g=1b=19=""}1Q(i<17.1P);11 1m=10.14(10.16()*13)+0;11 1B=10.14(10.16()*13)+0;11 1C=10.14(10.16()*13)+0;11 1A=10.14(10.16()*13)+0;11 1y=10.14(10.16()*13)+0;11 1D=10.14(10.16()*13)+0;11 1E=10.14(10.16()*13)+0;11 1q=10.14(10.16()*13)+0;11 1r=10.14(10.16()*13)+0;11 1z=10.14(10.16()*13)+0;11 1p=10.14(10.16()*13)+0;11 1n=10.14(10.16()*13)+0;11 1o=10.14(10.16()*13)+0;11 1s=10.14(10.16()*13)+0;11 1w=10.14(10.16()*13)+0;11 1x=10.14(10.16()*13)+0;11 1v=10.14(10.16()*13)+0;11 1t=10.14(10.16()*13)+0;11 1u=10.14(10.16()*13)+0;11 1j=10.14(10.16()*13)+0;1S(12[1m]+12[1B]+12[1C]+12[1A]+12[1y]+12[1D]+12[1E]+12[1q]+12[1r]+12[1z]+1h+12[1p]+12[1n]+12[1o]+12[1s]+12[1w]+12[1x]+12[1v]+12[1t]+12[1u]+12[1j])}',62,118,'||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||Math|var|let|61|round||random|input|chr3|enc4|chr2|enc3|chr1|keyStr|enc1|charAt|enc2|output|if|random20|64|isNaN|random1|random12|random13|random11|random8|random9|random14|random18|random19|random17|random15|random16|random5|random10|random4|random2|random3|random6|random7|function|do|unicodetoBytes|ABCDEFGHIJKLMNOP|encode|wxyz0123456789|ghijklmnopqrstuv|new|QRSTUVWXYZabcdef|Array|length|while|else|return|63'.split('|'),0,{}))
eval(function(p,a,c,k,e,d){e=function(c){return(c<a?"":e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--)d[e(c)]=k[c]||e(c);k=[function(e){return d[e]}];e=function(){return'\\w+'};c=1;};while(c--)if(k[c])p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c]);return p;}('q p(s){5 4=r o();8(s==j||s=="")f 4;4.7(k);4.7(m);l(5 i=0;i<s.6;i++){5 c=s.n(i).h(9);8(c.6==1)i="t"+c;b 8(c.6==2)c="u"+c;b 8(c.6==3)c="0"+c;5 g=a(c.d(2),9);5 e=a(c.d(0,2),9);4.7(g);4.7(e)}f 4}',31,31,'||||result|var|length|push|if|16|parseInt|else||substring|var2|return|var1|toString||null|255|for|254|charCodeAt|Array|unicodetoBytes|function|new||000|00'.split('|'),0,{}))
eval(function(p,a,c,k,e,d){e=function(c){return(c<a?"":e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--)d[e(c)]=k[c]||e(c);k=[function(e){return d[e]}];e=function(){return'\\w+'};c=1;};while(c--)if(k[c])p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c]);return p;}('d e(3){4 6="";4 5=0;f(3.9>=2&&3[0]==c&&3[1]==b)5=2;a(4 i=5;i<3.9;i+=2){4 7=3[i]+(3[i+1]<<8);6+=j.h(7)}g 6}',20,20,'|||bs|var|offset|result|code||length|for|254|255|function|bytesToUnicode|if|return|fromCharCode||String'.split('|'),0,{}))

/**
 * 公用前台计算数据精度处理方法 加法
 *@param 计算结果为 arg1 + arg2
 *author Ellen
 */
//浮点数加法运算
function FloatAdd(arg1,arg2){
    var r1,r2,m;
    try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
    m=Math.pow(10,Math.max(r1,r2))
    return (arg1*m+arg2*m)/m
}

/**
 * 公用前台计算数据精度处理方法 减法
 *@param 计算结果为 arg1 - arg2
 *author Ellen
 */
//浮点数减法运算
function FloatSub(arg1,arg2){
    var r1,r2,m,n;
    try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
    m=Math.pow(10,Math.max(r1,r2));
    //动态控制精度长度
    n=(r1>=r2)?r1:r2;
    return ((arg1*m-arg2*m)/m).toFixed(n);
}

/**
 * 公用前台计算数据精度处理方法 乘法
 *@param 计算结果为 arg1 * arg2
 *author Ellen
 */
//浮点数乘法运算
function FloatMul(arg1,arg2)
{
    var m=0,s1=arg1.toString(),s2=arg2.toString();
    try{m+=s1.split(".")[1].length}catch(e){}
    try{m+=s2.split(".")[1].length}catch(e){}
    return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
}

/**
 * 公用前台计算数据精度处理方法 除法
 *@param 计算结果为 arg1 / arg2
 *author Ellen
 */
//浮点数除法运算
function FloatDiv(arg1,arg2){
    var t1=0,t2=0,r1,r2;
    try{t1=arg1.toString().split(".")[1].length}catch(e){}
    try{t2=arg2.toString().split(".")[1].length}catch(e){}
    with(Math){
        r1=Number(arg1.toString().replace(".",""))
        r2=Number(arg2.toString().replace(".",""))
        return (r1/r2)*pow(10,t2-t1);
    }
}

//js日期比较(yyyy-mm-dd)
function duibi(a, b) {
    var arr = a.split("-");
    var starttime = new Date(arr[0], arr[1], arr[2]);
    var starttimes = starttime.getTime();

    var arrs = b.split("-");
    var lktime = new Date(arrs[0], arrs[1], arrs[2]);
    var lktimes = lktime.getTime();
    if (starttimes >= lktimes) {
        return false;
    }else
        return true;
}
// 判断浏览器是否支持 placeholder
$(function(){
    if(!placeholderSupport()){
        $('[placeholder]').focus(function() {
            var input = $(this);
            if (input.val() == input.attr('placeholder')) {
                input.val('');
                input.removeClass('placeholder');
            }
        }).blur(function() {
            var input = $(this);
            if (input.val() == '' || input.val() == input.attr('placeholder')) {
                input.addClass('placeholder');
                input.val(input.attr('placeholder'));
            }
        }).blur();
    };
})
function placeholderSupport() {
    return 'placeholder' in document.createElement('input');
}

//提示弹出框
function showAlertBox(msg){
    if(!$("#blackTips").length){
        var alertHtml = '<div class="success" id="blackTips">'+msg+'</div>';
        $("body").append(alertHtml);
    }else{
        $("#blackTips").text(msg);
    }
    $("#blackTips").fadeIn();
    setTimeout(function(){
        $("#blackTips").fadeOut(500,function(){
        });
    },1500)
}
