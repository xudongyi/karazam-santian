$(function(){
    $(".asset_title .fa-question-circle-o").hover(function(){
        $(this).siblings(".tixing").toggle();
    },function(){
        $(this).siblings(".tixing").toggle();
    });
    
    $(".otherBox .fa-question-circle-o").hover(function(){
        $(this).parents("li").children(".tixing").toggle();
    },function(){
        $(this).parents("li").children(".tixing").toggle();
    });

    $(".fa .fa-question-circle-o").hover(function(){
        $(this).siblings(".feetip .tixing .poundage").toggle();
    },function(){
        $(this).siblings(".feetip .tixing .poundage").toggle();
    });

    $(".fa .fa-question-circle-o").hover(function(){
        $(this).parents("li").children(".feetip .tixing .poundage").toggle();
    },function(){
        $(this).parents("li").children(".feetip .tixing .poundage").toggle();
    });
});