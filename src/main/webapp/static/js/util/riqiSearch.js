/**
 * Created by limat on 2017/4/19.
 */
    // 日期选择
$(function(){

    var datapicker = new DatePickerInput({
        container: 'datapicker',
        startInput: 'startDate',
        endInput: 'endDate'
    });

    // 选择日期
    $("#startDate").focus(function() {
        $("#datapicker").show();
    });

    $("#endDate").focus(function() {
        $("#datapicker").show();
    });

    $(document).on("click", ".preMonth", function() {
        datapicker.toPrevMonth();
    });

    $(document).on("click", ".nextMonth", function() {
        datapicker.toNextMonth();
    });

    $(document).on("click", ".preYear", function() {
        var datapickerDate = datapicker.slideDate;
        var newYear = (datapickerDate.year - 1) + "年" + datapickerDate.month + "月";
        $("#datapicker .nowYearMonth").text(newYear);
        datapicker.toNewYearAndMonth(datapickerDate.year - 1, datapickerDate.month);
    });

    $(document).on("click", ".nextYear", function() {
        var datapickerDate = datapicker.slideDate;
        var newYear = (datapickerDate.year + 1) + "年" + datapickerDate.month + "月";
        $("#datapicker .nowYearMonth").text(newYear);
        datapicker.toNewYearAndMonth(datapickerDate.year + 1, datapickerDate.month);
    });

    $(document).on("click", "#datapicker .paituodai-datepicker-day span", function() {
        $("#datapicker").hide();
    });

    $(document).click(function(e) {
        if ($(e.target).parents('#datapicker').attr('id') == 'datapicker' || $(e.target).parents('#datapicker1').attr('id') == 'datapicker1') {
            return;
        }
        if (e.target.className == 'preYear' || e.target.className == 'nextYear') {
            return;
        }
        if (e.target == $('#startDate')[0] || e.target == $('#endDate')[0]) {
            return;
        }
        $("#datapicker").hide();
        $("#datapicker1").hide();
    })

});