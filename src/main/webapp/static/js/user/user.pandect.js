$(function () {
    $.ajax({
        url: ctx + "/uc/assets",
        data: {},
        type: "post",
        dataType: "json",
        cache: false,
        beforeSend: function (request, settings) {

        },
        success: function (result) {
            if (result.status == "success") {
                console.log(result.data);
                $('#available').text(result.data.available);
                $('#watingCapital').text(result.data.watingCapital);
                $('#watingProfits').text(result.data.watingProfits);
                $('#investFrozen').text(result.data.investFrozen);
                $('#withdrawing').text(result.data.withdrawing);
                $('#alreadyProfits').text(result.data.alreadyProfits);
                $('#withdrawFee').text(result.data.withdrawFee);
                $('#referralFee').text(result.data.alreadyReferralFees);

                var myChart_01 = echarts.init(document.getElementById('main1'));//总资产明细
                var data_01 = [  //总资产明细
                    {value: result.data.available, name: '可用余额（元）'},//value是指对应的金额
                    {value: result.data.watingCapital, name: '待收本金（元）'},
                    {value: result.data.watingProfits, name: '待收收益（元）'},
                    {value: result.data.investFrozen, name: '投资冻结金额（元）'},
                    {value: result.data.withdrawing, name: '提现中金额（元）'}];

                var sum_01 = result.data.allCapitalSum; //总资产金额
                var color_01 = ["#8ebcf1", "#ff6e9f", "#b88df3", "#f8cb60", "#ff8c6d"];//版块所对应的颜色

                var option_01 = createAssetsOption(sum_01, color_01, data_01, "总资产（元）");

                myChart_01.setOption(option_01);
            } else {
                layer.msg(result.message);
            }
        },
        error: function () {
            layer.msg("服务正忙，请刷新页面重试");
        }
    });

});

var assetsEcharts = function (data) {

};


//资产明细
function createAssetsOption(sum, color, data, title) {
    var option = {
        tooltip: {
            trigger: 'item',
            formatter: "{b}:{c}"
        },
        itemStyle: {
            trigger: 'item',
            borderColor: "#3cf"
        },
        calculable: false,
        title: {
            text: title,
            subtext: sum,
            x: 185,
            y: 122,
            textAlign: 'center',
            itemGap: 10,
            textStyle: {
                fontSize: 14,
                color: '#9e9e9e',
                fontFamily: 'Microsoft YaHei',
                align: 'center',
                fontWeight: 'normal'
            },
            subtextStyle: {
                fontSize: 18,
                color: '#212121',
                fontFamily: 'DIN-Medium,sans-serif',
                align: 'center'

            }
        },
        color: color,
        series: [
            {
                name: '',
                type: 'pie',
                center: ['183', '142'],
                radius: ['72', '90'],
                itemStyle: {
                    normal: {
                        label: {
                            position: 'center',
                            show: false,
                            textStyle: {
                                fontSize: '16',
                                color: '#212121'
                            }
                        },
                        labelLine: {
                            show: false
                        },
                        borderWidth: 0,
                        borderColor: '#fff',
                        itemGap: 5,
                        padding: 10,
                        selectedMode: false
                    },
                    emphasis: {
                        padding: 0,
                        label: {
                            show: false,
                            distance: 10
                        }
                    }
                },
                data: data
            }
        ]
    };
    return option;
}


