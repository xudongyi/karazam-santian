/**
 * Created by limat on 2017/5/16.
 */
/**
 * Created by limat on 2017/5/16.
 */
var newStyle = 0;
var galleryTop = new Swiper('.gallery-top', {
    nextButton: '.swiper-button-next',
    prevButton: '.swiper-button-prev',
    spaceBetween: 10,
    loop: true,
    autoplay: 3000,
    paginationClickable: true,
    pagination: '.imgBanner .pagination'
});

var noticeList = new Swiper('.noticeList .swiper-container', {
    spaceBetween: 10,
    loop: true,
    mode: 'vertical',
    autoplay: 3000
});

$(function () {
    //banner 分页器初始化
    var pageList = $(".swiper-pagination-switch").length;
    var pageWidth = (1140 - pageList) / pageList;
    pageWidth = Math.floor(pageWidth * 100) / 100
    $(".swiper-pagination-switch, .menu_banner .textList span").css("width", pageWidth);

    $(".menu_banner .textList span").bind("click", function () {
        var $this = $(this);
        var liIndex = $this.index(".menu_banner .textList span");

        $(".swiper-pagination-switch").eq(liIndex).click();
    })

    if (newStyle) {
        newStyleCss();
    }
})