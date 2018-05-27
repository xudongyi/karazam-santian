this.userType = 'GENERAL';

$(document).ready(function(){
    $("#loginName").textbox({
        required: true,
        validType:['remote["'+ctx+'/admin/user/validataLoginName/'+userType +'"' + ',"loginName"]','mobile']
    });
    $("#mobile").textbox({
        required: true,
        validType:['remote["'+ctx+'/admin/user/validataMobile/'+userType +'"' + ',"mobile"]','mobile']
    });
    $(".realyInfo").hide();
});


function changeType() {
    this.userType = $("#userType").val();
    $("#loginName").textbox({
        required: true,
        validType:['remote["'+ctx+'/admin/user/validataLoginName/'+userType +'"' + ',"loginName"]','mobile']
    });
    $("#mobile").textbox({
        required: true,
        validType:['remote["'+ctx+'/admin/user/validataMobile/'+userType +'"' + ',"mobile"]','mobile']
    });
    $("input[name='identifyFlag']").attr("checked", false);
    $("input[name='identifyFlag']").val(0);
    $(".realyInfo").hide();
}

function isIdentify() {
    var flag = $("input[name='identifyFlag']").val();
    if(flag==0){
        $("input[name='identifyFlag']").val(1);
        $(".realyInfo").show();
        $("#realName").textbox({ required: true });
        $("#idNo").textbox({
            required: true,
            validType:['idCard','remote["'+ctx+'/admin/user/checkIdNo/'+userType +'"' + ',"idNo"]']
        });
        //是否实名认证标志
        $('#flag').val(true);
    }else {
        $("input[name='identifyFlag']").val(0);
        $(".realyInfo").hide();
        $('#flag').val(false);
        $("#realName").textbox({ required: false });
        $("#idNo").textbox({ required: false });
    }
}