// /**
//  * Created by chenxinglin
//  */
// var data = {
//     des : '',
//     showPercent : 4,
//     rows : []
// };
// // var baseParams = {
// //     page: 1,
// //     // rows: 4
// // };
//
// var url = ctx + '/gift/data';
// var params = {
//     page: 1,
//     sort : "createDate",
//     order : "desc",
//     filter_GEI_point : "",
//     filter_LEI_point : ""
// };
// var typeSearchLabel = "";
//
// $(function () {
//
//     getPageData();
//
//     $('.typeSearch').on('click', function () {
//         //获取参数
//         params.sort = $(this).attr("data-type");
//         pointParamDo();
//         params.filter_GEI_point = $("#point-min").val();
//         params.filter_LEI_point = $("#point-max").val();
//
//         //排序标记
//         if(typeSearchLabel && typeSearchLabel == params.sort){
//             if(params.order == "asc"){
//                 params.order = "desc";
//             }else {
//                 params.order = "asc";
//             }
//         }else{
//             params.order = "desc";
//             typeSearchLabel = params.sort;
//         }
//         //重置排序
//         $('.typeSearch').removeClass("current");
//         $(this).addClass("current");
//         $('.typeSearch .fa').removeClass("fa-caret-up").removeClass("fa-caret-down");
//         $('.typeSearch .fa').addClass("fa-caret-down");
//         $(this).find(".fa").removeClass(".fa-caret-down");
//         if(params.order == "asc"){
//             $(this).find(".fa").addClass("fa-caret-up");
//         }else {
//             $(this).find(".fa").addClass("fa-caret-down");
//         }
//
//         getPageData();
//     });
//
//     $('.btn-search').on('click', function () {
//         pointParamDo();
//         params.filter_GEI_point = $("#point-min").val();
//         params.filter_LEI_point = $("#point-max").val();
//         getPageData();
//     });
//
//     followDo();
//
// });
//
// var getPageData = function() {
//     // var params = $.extend(baseParams, params);
//     $.getJSON(url,
//         params,
//         function (res) {
//             var total = res.total;
//             $('#dataCount').text(total);
//             data.rows = res.rows;
//             var tpl = document.getElementById("dataTpl");
//             laytpl(tpl.innerHTML).render(data, function (string) {
//                 $('#dataBox').html(string);
//                 if (total == 0) {
//                     $('#dataBox').hide();
//                     $('#pagination').hide();
//                     $('#noDataTip').show();
//                 } else {
//                     $('#dataBox').show();
//                     $('#pagination').show();
//                     $('#noDataTip').hide();
//                 }
//                 followDo();
//             });
//             laypage({
//                 skin: '#d0d0d0',
//                 cont: 'pagination'
//                 , pages: res.pages
//                 , jump: function (e) { //触发分页后的回调
//                     $.getJSON(url,
//                         $.extend(params, {page: e.curr}),
//                         function (rest) {
//                             e.pages = e.last = rest.pages; //重新获取总页数，一般不用写
//                             $('#dataCount').text(rest.total);
//                             // //解析数据
//                             data.rows = rest.rows;
//                             laytpl(tpl.innerHTML).render(data, function (stringt) {
//                                 $('#dataBox').html(stringt);
//                                 followDo();
//                         });
//                     });
//                 }
//             });
//         }
//     );
// };
//
// /**
//  * 积分区间处理
//  */
// var pointParamDo = function() {
//     var pointMin = $("#point-min").val();
//     var pointMax = $("#point-max").val();
//     if (isNaN(pointMin)) {
//         $("#point-min").val("");
//     }
//     if (isNaN(pointMax)) {
//         $("#point-max").val("");
//     }
//     pointMin = $("#point-min").val();
//     pointMax = $("#point-max").val();
//     if(pointMin){
//         $("#point-min").val(parseInt(pointMin));
//     }
//     if(pointMax){
//         $("#point-max").val(parseInt(pointMax));
//     }
// }
//
// /**
//  * 关注
//  */
// var followDo = function() {
//     $('.follow').on('click', function () {
//
//         if ($(this).hasClass("fa-heart-o")){ //未关注
//             $(this).removeClass("fa-heart-o").addClass("fa-heart orange");
//             showFocusTip($(this).parent().find(".focus-tip"), "\u5173\u6ce8\u6210\u529f");
//             $(this).attr("title", "\u5df2\u5173\u6ce8");
//         }else { //已关注
//             $(this).removeClass("fa-heart orange").addClass("fa-heart-o");
//             showFocusTip($(this).parent().find(".focus-tip"), "取消关注");
//             $(this).attr("title", "未关注");
//         }
//
//
//         // $.getJSON(url,
//         //     params,
//         //     function (res) {
//         //
//         //     });
//         // console.log($(this).find(""));
//
//         // $(this).removeClass("fa-heart-o").addClass("fa-heart orange");
//
//
//         //     t.opts.myFavorites.push(i)) :
//         // showFocusTip(n.parent().find(".focus-tip"), "\u5173\u6ce8\u5931\u8d25")
//
//     });
// }
//
// /**
//  * 关注提示
//  */
// var showFocusTip = function (e, t) {
//     e.html(t).animate({"opacity": 1, "right": 50}, 500), e.html(t).animate({
//         "opacity": 0,
//         "right": 55
//     }, 500, function () {
//         e.css({"top": 20, "right": 40, "opacity": 0})
//     })
// }
