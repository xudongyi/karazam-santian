    $(function() {
        // 左侧导航栏显示当前内容
    	if(typeof(mnav) != "undefined"){
    		mnav.activity(5);
    	}
    });

  //发送短信
    function sendSMS(mobile,cate){
    	return $.ajax({
            type: "post",
            url: urlbase+'/reg/mobileCode',
            dataType: "json",
            data:{mobiles:mobile,cate : cate,scode: 1},
            async:false,
            success: function (data) {
               return data.msg;
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            	showAlertBox("网络错误！请稍候重试！");
            }
        });
    }
	// 银行卡输入放大显示效果
    $("#bankCodeNumber").focus(function(evt){
        if(this.value.length>0){
          visible(this);
          big(this);
        }
    })
    // 银行大框效果
    $("#bankCodeNumber").keyup(function(evt){

        if(this.value.length==0){
          unvisible();
        }else{
          visible(this);
        }
        big(this);
    })
    $("#bankCodeNumber").blur(function(evt){
      unvisible();
      this.value=this.value;
    })
    // $("#undateBank").click(function(){
    //   $(this).remove();
    //   $("#bankCodeNumber").removeAttr("disabled").addClass("inputTextCurr").val("").focus();
    // })

    //计算div的left和top，显示div
    function visible(evt){
      $("#bankKhTips").show();
    }
    //隐藏div
    function unvisible(){
      $("#bankKhTips").hide();
    }
    //控制div里显示的数字
    function big(e){
      var i = e.value;
      i=$.trim(i);
      var h=i.substring(0,4);
      i=i.substring(4);
      while(i&&i.length>0){
        h+=" "+i.substring(0,4);
        i=i.substring(4)
      }
      $("#bankKhTips").html(h);
    }

//@ tipObj string
  //@ status  string 定义状态值
  //@ words   string 定义提示语


  function inputFeeedback(obj,status,words){
    switch(status){
      case 'notok':{
        $(obj).addClass("notok").removeClass("ok");
        $(obj).html('<i data="status-logo"></i>'+words);
        break;
      }
      case 'ok':{
        $(obj).addClass("ok").removeClass("notok");
         $(obj).html('<i data="status-logo"></i>'+words);
        break;
      }
    }
  }

var formValid={
  bankCodeValid :function(id,tipId){
    var bankCode=$.trim($(id).val());
    var reg_card = /^[0-9]{16,19}$/;
	  if(bankCode=='')
	  {
      inputFeeedback(tipId,"notok","请输入卡号");
		  return false;
	  }else{
      if(!reg_card.test(bankCode)){

        inputFeeedback(tipId,"notok","银行卡号格式不正确");
        return false;
      }else{
        inputFeeedback(tipId,"ok","");
        return true;
      }
	 }
  },
  mobileValid : function(id,tipId){
    var flag=false;
     mobile=$.trim($(id).val());
        if(mobile.length==0){
          inputFeeedback(tipId,"notok","手机号码不能为空");
          $("#getCode").attr("disabled","disabled").addClass("noBut");
        }
        else{
          var verify=/^(13|15|18|14|17)[0-9]{9}$/;
          var vm=verify.test(mobile);
          if(vm){
              inputFeeedback(tipId,"ok","");
              $("#getCode").removeAttr("disabled").removeClass("noBut");
              flag=true;
            }else{
              inputFeeedback(tipId,"notok","请输入正确的手机号码");
              $("#getCode").attr("disabled","disabled");
            }
        }
    return flag;
  },
  mobileCodeValid :function(id,tipId){
    var flag=false;
    var mobileCode=$.trim($(id).val());
      if(mobileCode.length==0){
          inputFeeedback(tipId,"notok","请输入验证码");
        } else{
          if(mobileCode.length!=6){
            inputFeeedback(tipId,"notok","验证码输入不正确");
          }else{
             inputFeeedback(tipId,"ok","");
            flag=true;
          }
        }
    return flag;
  },
  amountValid : function(id,tipId,type){
	   var exp = new RegExp("^[0-9]+(.[0-9]{1,2})?$");
		 var amount=$.trim($(id).val());
     var timelimit=(+$("#timelimit").text());
		 if(!amount){
        inputFeeedback(tipId,"notok","请输入充值金额");
 			  return false;
		 }else{
       if(amount<=0){
          inputFeeedback(tipId,"notok","充值金额输入不正确");
          return false;
       }else{
          if(!exp.test(amount)){
            inputFeeedback(tipId,"notok","请重新输入");
            return false;
          }else{
        	  //timelimit为0，则代表不限制
            if(type=="easySave" && amount>timelimit && timelimit!=0){
                // 没有的话默认是0
               inputFeeedback(tipId,"notok","单笔充值金额过大，请更换充值方式或降低充值金额后重试");
               return false;
            }else{
              inputFeeedback(tipId,"ok","");
              return true;
            }
          }
       }
     }


  },
  telcodeValid : function(id,tipId){
      var telcode=$.trim($(id).val());
      if(!telcode){
        inputFeeedback(tipId,"notok","请输入短信验证码");
        return false;
      }else{
        if(telcode.length!=6){
          inputFeeedback(tipId,"notok","短信验证码为6位");
          return false;
        }else{
        	inputFeeedback(tipId,"ok","");
        	return true;
        }
      }
  },

  bankValid : function(id,tipId){
	var flag = false;
	var mobileCode = $.trim($(id).val());
	if (mobileCode.length == 0) {
    inputFeeedback(id,"notok","请选择银行卡");
	} else  {
		flag = true;
	}
	return flag;
  }
}
// 身份证不能为空验证
    $("#bankCodeNumber").blur(function(){

      formValid.bankCodeValid("#bankCodeNumber","#bt");

    });
// 手机号码与按钮效果
    var mobile;
    $("#phoneNum").blur(function(){
        formValid.mobileValid("#phoneNum","#pt");
    });

// 验证码不能为空
    $("#mobileCode").blur(function(){
      formValid.mobileCodeValid("#mobileCode","#ct");
    });
// 金额检验
    var type=$(".pay-type span.cur").attr("name");

    if(type=="easySave"){
      // 快捷充值金额校验
      $("#amount").blur(function(){
        formValid.amountValid("#amount","#at","easySave");
      });
    }else{
      // 网银充值金额校验
      $("#amount").blur(function(){
        formValid.amountValid("#amount","#at","lineSave");
      });
    }

    // 快捷充值已经绑定
    $("#easyPaySaveBtn").click(function(){
   	  var depositStatus = $("#depositStatus").val();
 	  var depositSwitch = $("#depositSwitch").val();
      var savenum=formValid.amountValid("#amount","#at","easySave");
      if(savenum){
		   	 //存管通判断 0, "未开通";1, "未激活";2, "开通并且激活"
		   	 if(depositStatus == -1){
		   		 showAlertBox("获取存管信息失败");
		   		 return ;
		   	 }if(depositStatus == 0 || depositStatus == 1){
	     		$("#cunguanTips").fadeIn();
	     	}else {
	     		$("#cunguanResult").show();
	     		var amount = $("#amount").val();
	     		$("#toRecharge").submit();
	     	}
      }
    });

    //网银充值
    $(".savebtn").click(function(){
        // var type=$(".pay-type span.cur").attr("name");
	   	 //存管通判断 0, "未开通";1, "未激活";2, "开通并且激活"
    	 var depositStatus = $("#depositStatus").val();
     	 var depositSwitch = $("#depositSwitch").val();
    	 
        var savenum,bank;
        var saveType = $(".pay-types-span span.cur").attr("id");
        if(!saveType){
          if( ! $(".bank span.cur").length ){
            $(".bank p.noselect").css("display","block");
            inputFeeedback("#banktype-tip","notok","请选择银行");
            bank=false;
          }else{
            inputFeeedback("#banktype-tip","ok","");
            bank=true;
          }
        }else{
          if( ! $("#"+saveType+"-content span.cur").length ){
            
            var bankTipObj =  "#"+saveType+"-content p.noselect";
            $(bankTipObj).css("display","block");
            inputFeeedback(bankTipObj+">label","notok","请选择银行");
            bank=false;
          }else{
            inputFeeedback(bankTipObj+">label","ok","");
            bank=true;
          }          
        }
        
        savenum=formValid.amountValid("#amount","#at","lineSave");
	      if(savenum && bank){
	    	  if(depositStatus == -1){
	        		 showAlertBox("获取存管信息失败");
			   		 return ;
			   	}else if(depositStatus == 0 || depositStatus == 1){
	 	     		$("#cunguanTips").fadeIn();
	 	     	}else {
	 	     		$("#cunguanResult").show();
	 	     		$("#netPayFrom").submit();
	 	     	}
	      }
    });
    
    // 快捷充值已经绑定
    $(".saveBtn").click(function(){
      var savenum=formValid.amountValid("#amount","#at","easySave");
      if(!$("#payword-input").val()){
        inputFeeedback("#tradepwd-tip","notok","请输入6位交易密码")
      }
      var tradepwd=$("#tradepwd-tip").attr("class");
      if(savenum && tradepwd=="ok"){
    	  $("#payword-input").val(encode($("#payword-input").val()));
    	  $("#formValidation").submit();
      }
    });
/*    // 设置交易密码
    $(".set-tradepwd-btn").click(function(){
      var pwd1 = $('#payword-input').val();
      var pwd2 = $('#re-pay-input').val();
      if(!pwd1){
        inputFeeedback("#tradepwd-tip","notok","请输入6位交易密码");
      }
      if(!pwd2){
        inputFeeedback("#tradepwd1-tip","notok","请输入6位交易密码");
      }
      if(pwd1==pwd2&&pwd2&&pwd1){
        $('#payword-input').val(encode(pwd1));
        $('#re-pay-input').val(encode(pwd2));
        $("#setPayGood").submit();
      }
    });*/
    
   /* // 快捷支付未绑卡和网银支付
    $(".paybtn").click(function(){
      var bankid,phonenum,savenum,telcode,bank;
        if( ! $(".bank span.cur").length ){
          $(".bank p.noselect").css("display","block");
          inputFeeedback("#banktype-tip","notok","请选择银行");
          bank=false;
        }else{
          inputFeeedback("#banktype-tip","ok","");
          bank=true;
        }

        if(type=="easyPay"){
           bankid=formValid.bankCodeValid("#bankCodeNumber","#bt");
           phonenum=formValid.mobileValid("#phoneNum","#pt");
           telcode=formValid.telcodeValid("#telcode","#telcodeTip");
          if(bankid && phonenum && telcode && bank){
            //alert("提交后台处理4");
        	  $("#payGood").submit();
          }
        }else{ 
          if(!$("#payword-input").val()){
            inputFeeedback("#tradepwd-tip","notok","请输入6位交易密码");
          }
          var tradepwd=$("#tradepwd-tip").attr("class");
          if(tradepwd=="ok" && bank){
        	  $("#payword-input").val(encode($("#payword-input").val()));
        	  $("#payGood").submit();
            //alert("提交后台处理5");//网银  余额  快捷已绑卡
//            $("#pay-inform").show();
          }
        }
    });*/
    
    $("#amountPay").click(function(){
	    if(!$("#payword-input").val()){
	        inputFeeedback("#tradepwd-tip","notok","请输入6位交易密码");
	    }
        var tradepwd=$("#tradepwd-tip").attr("class");
        if(tradepwd=="ok" ){
        	$("#payword-input").val(encode($("#payword-input").val()));
        	$("#payGood").submit();
          $("#pay-inform").show();
        }
	});
    
 // 个人中心绑定银行卡
    $("#bankInput").click(function(event){
      $(".bank-items").toggle();
    });
     $(".bank-items li").click(function(){
        var bank=$("#bank").val();
        var bankId=$("#bankCode").val();
        var name=$(this).text();
        var id=$(this).attr("bank-code");
        $("#bankInput").val(name);
        $("#bankCode").val(id);
        bank=name;
        bankId=id;
        if(!bank){
          inputFeeedback("#banktype-tip","notok","请选择银行");
        }else{
          inputFeeedback("#banktype-tip","ok","");
          $(".bank-items").hide();
        }
      });
   //添加银行卡绑卡
    $(".bindbtn").click(function(){
        $(".bank-items").hide();
        var bankid,phonenum,savenum;
        if(!$("#bankInput").val()){
          inputFeeedback("#banktype-tip","notok","请选择银行");
        }
         bankid=formValid.bankCodeValid("#bankCodeNumber","#bt");
         phonenum=formValid.mobileValid("#phoneNum","#pt");
         mobilecode=formValid.mobileCodeValid("#mobileCode","#ct");
        if(bankid && phonenum && mobilecode&&$("#banktype-tip").hasClass("ok")){
        	$.ajax({
                type: "post",
                url:urlbase+"/account/bankAdd",
                dataType: "json",
                data:$("form").serialize(),
                success: function (data) {
                	if(data.success){
                		$('#code-correct').show();
                        setTimeout(function(){
                          $('#code-correct').hide();
                        },3000)
                	}else{
                		inputFeeedback("#ct","notok",data.message);
                	}
                	
                }
            });
        }
    });
    
    // 个人中心提现
    function takeAmount(id,tipId){
      var exp = new RegExp("^[0-9]+(.[0-9]{1,2})?$");
      var amount=+$.trim($(id).val());
      var transferMoney=+$.trim($("#cantake-money").text());
      var historyTransferMoney=+$.trim($("#history-transfer-money").text());
      //historyTransferMoney 今日线上已转可提金额
      var limit=+$("#left-money").text();
      if(!amount){
         inputFeeedback(tipId,"notok","请输入金额");
         return false;
      }else{
        if(amount<50){
           inputFeeedback(tipId,"notok","50元起提");
           return false;
        }else{
           if(!exp.test(amount)){
           inputFeeedback(tipId,"notok","请重新输入");
             return false;
           }else{
             if( amount>limit){
                 // 没有的话默认是0
                inputFeeedback(tipId,"notok","剩余金额不足");
                return false;
             }
             else{
	              // 线上单日历史已转可提金额+（输入金额-可提现）大于1万报错
	              if( (historyTransferMoney+(amount-transferMoney)) > 1000000000 ){
	                inputFeeedback(tipId,"notok","超过线上单日转可提最高金额10000元，大额转可提提现请联系客服");
	                return false;
	              }else{
	                inputFeeedback(tipId,"ok","");
	                return true;
	             }
	              
             }
        }
      }
    }
    }

    // $("#take-amount").blur(function(){
    //   takeAmount("#take-amount","#take-amount-tip");
    // });
   //提现
    $(".takebtn").click(function(){
     	 var depositStatus = $("#depositStatus").val();
     	 var depositSwitch = $("#depositSwitch").val();
     	 
        var amount=takeAmount("#take-amount","#take-amount-tip");
        if(amount){
        	if(depositSwitch == -1 || depositStatus == -1){
        		 showAlertBox("未获取到银行存管状态，请稍后重试");
        		 return ;
        	 }
        	//未开通或者未激活时，弹出提示框
	    	if(depositStatus === 0 || depositStatus === 1 ){
		      $("#cunguanTips").fadeIn();
		    }else{
		    	//激活后
		    	$("#cunguanResult").show();
		    	var amount = $("#take-amount").val();
		    	window.open('/deposit/toWithdraw?m='+amount);
		    }
        }
    })

//完善开户行
$('#complete-bank').click(function(){
    $('#complete-bankInfo').show();
})

//点击支付银行跳出信息
$('#payBank').click(function(){
    $('#bank-type').show();
})
//绑卡发送短信
$("#mobile-code-button").click(function(){
	var data=sendSMS($('#phoneNum').val(), 4);
	inputFeeedback("#bank_error_msg","notok",data.msg);
    public.downTime("#getMessage",60);
});

// 提现收费提醒
 $(".fa-question-circle-o").hover(function(){
   $(this).siblings(".feetip").toggle();
},function(){
   $(this).siblings(".feetip").toggle();
});

