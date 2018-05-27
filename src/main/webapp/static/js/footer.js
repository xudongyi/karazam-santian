$().ready(function(){
  $( '#tijiao').on('click', function (){
          var phone=$('#phone_num').val();
          if(!phone.match(/^1\d{10}$/)){
              layer.msg('手机号不匹配');
          }else{
              window.open('/article/help/handbook');
          }
   });
})