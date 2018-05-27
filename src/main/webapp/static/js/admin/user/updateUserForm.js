$(document).ready(function(){

    var userType = $("#userType").val();
    var id = $("#pk").val();
    $("#idNo").textbox({
        validType:['idCard','remote["'+ctx+'/admin/user/checkIdNo/'+userType +'"' + ',"idNo"]']
    });
    $("#loginName").textbox({
        required: true,
        validType:['remote["'+ctx+'/admin/user/validataLoginName/'+userType+ '/'+ id +'"' + ',"loginName"]','mobile']
    });
    $("#mobile").textbox({
        required: true,
        validType:['remote["'+ctx+'/admin/user/validataMobile/'+userType+'/'+ id +'"' + ',"mobile"]','mobile']
    });
});