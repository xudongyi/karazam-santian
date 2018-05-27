$(document).ready(function(){


    $("#isRandomAmount").change(function () {
        console.log("jquery change...");
    });

    console.log(111);
    $("#amount").attr("disabled",true);

    $("#isRandomAmount").combobox({
        onChange: function (n, o) {
            if (n == 'true'){
                console.log(111);
                $("#amount").attr("disabled",true);
                $("#randomAmountMix").attr("disabled",false);
                $("#randomAmountMax").attr("disabled",false);
            }else {
                console.log(222);
                $("#randomAmountMix").attr("disabled",true);
                $("#randomAmountMax").attr("disabled",true);
                $("#amount").attr("disabled",false);
            }
        }
    })

});
