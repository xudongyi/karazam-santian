/**
 * Created by limat on 2017/4/19.
 */
var page=1;//页数

$(function() {
    // 左侧导航栏显示当前内容
    mnav.activity(4);

    $("#excel").click(function(){
        var endTime = $("#endDate").val();
        var startTime = $("#startDate").val();
        var url = window.action.rootPath + window.action.interface.shoukuanDaishouExport;
        if(startTime!=null&&endTime!=null){
            url+="?startTime="+startTime+"&endTime="+endTime;
        }
        window.location.href=url;
    });

    $("#dateRange ul li").click(function(){
        $(this).addClass("cur").siblings().removeClass("cur");
        setDateRange($(this).text());
        page = 1;
        reload();
    });

    reload();
});

$("#riqiSearch").click(function(){
    page = 1;
    reload();
});

function reload(){
    var endTime = $('#endDate').val();
    if(endTime != null && endTime != ""){
        endTime = new Date(Date.parse(endTime)).format("yyyy-MM-dd 23:59:59");
    }
    $.ajax({
        type: 'POST',
        url : 'receivablesDetailList.json',
        data: {
            "startTime": $('#startDate').val(),
            "endTime": endTime,
            "page": page,
            "rows":"10"
        },
        success: function(result) {
            if(result.success){
                if(!result.data || result.data.count == 0){
                    title();
                    $(".empty").show();
                    $(".answer").hide();
                    $(".pageList").hide();
                }else {
                    title();
                    $(".empty").hide();
                    $(".answer").show();
                    $(".pageList").show();
                    allData(result.data);
                    // 分页效果
                    $.creatPageNav({
                        selector: '.pageList',
                        page: page,
                        totalpage: Math.ceil(result.data.count / 10),
                        clickCallback: function (newpage) {
                            page = newpage;
                            reload();
                        }
                    });
                }
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });
}

function title(){
    var title = '<ul class="title">'+
        '<li class="tal" style="width:11%;box-sizing:border-box;padding-left:2%;+width:10%;">期数</li>'+
        '<li class="tal" style="width:17%;">项目名称</li>'+
        '<li>待收日期</li>'+
        '<li class="tar">待收本息(元)</li>'+
        '<li class="tar">待收本金(元)</li>'+
        '<li class="tar">待收利息(元)</li>'+
        '<li style="width:15%;">状态</li>'+
        '</ul>';
    $(".answer").html(title);
}

function allData(result) {
    if($(".content")){
        $(".content").remove();
    }

    for (var i = 0; i < result.data.length; i++) {
        var viewUrl = urlbase+'/bid/showBidDetail?hash='+result.data[i].hashAssetId;

        var content = '<ul class="content">';
        content+='<li class="tal" style="width:11%;box-sizing:border-box;padding-left:2%;+width:9%;">'+result.data[i].period+"/"+result.data[i].periods+'</li>'+
            '<li class="tal" style="width:17%;"><a href="'+viewUrl+'">'+formatBidName(result.data[i].assetName)+'</a></li>'+
            '<li>'+formatDate(result.data[i].recoverTime)+'</li>'+
            '<li class="tar">'+formatMoney(result.data[i].recoverAmount)+'</li>'+
            '<li class="tar">'+formatMoney(result.data[i].recoverPrincipal)+'</li>'+
            '<li class="tar">'+formatMoney(result.data[i].recoverInterest)+'</li>'+
            '<li style="width:15%;">未回款</li>';
        content += '</ul>';

        $('.answer').append($(content));
    }
}

function setDateRange(time){
    var currentTime = new Date();
    switch (time){
        case '全部': {
            $("#startDate").val('');
            $("#endDate").val('');
            break;
        };
        case '未来1个月':{
            $("#startDate").val(currentTime.format('yyyy-MM-dd'));
            $("#endDate").val(new Date(currentTime.setMonth(currentTime.getMonth()+1)).format('yyyy-MM-dd'));
            break;
        };
        case '未来3个月':{
            $("#startDate").val(currentTime.format('yyyy-MM-dd'));
            $("#endDate").val(new Date(currentTime.setMonth(currentTime.getMonth()+3)).format('yyyy-MM-dd'));
            break;
        };
        case '未来1年':{
            $("#startDate").val(currentTime.format('yyyy-MM-dd'));
            $("#endDate").val(new Date(currentTime.setFullYear(currentTime.getFullYear()+1)).format('yyyy-MM-dd'));
            break;
        };
    }
}