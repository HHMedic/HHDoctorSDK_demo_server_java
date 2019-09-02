$(function () {
    //console.log(11);
    initMsg();
})

function initMsg() {
    var html = '<div class="js_dialog msg-dialog" id="msg" style="display: none;">'
        + '<div class="weui-mask"></div><div class="weui-dialog">'
        + '<div class="weui-dialog__bd msg-text" id="msgText"></div>'
        + '<div class="weui-dialog__ft">'
        + '<a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_primary">知道了</a>'
        + '</div></div></div>';
    $('body').append(html);
}

//点击"知道了",隐藏消息窗口
$('body').on('touchstart click', '.weui-dialog__btn', function () {
    $(this).parents('.js_dialog').fadeOut(200);
    return false;
});

//显示消息
function showMsg(msg) {
    if (msg) {
        $('.msg-text').text(msg);
    }
    $('.msg-dialog').fadeIn(200);
}


