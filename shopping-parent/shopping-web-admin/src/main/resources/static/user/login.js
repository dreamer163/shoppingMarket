$(function () {
    if (error){
        layer.alert(error);
    }
    //登录
    $("#login-btn").click(function () {
        //校验
        $("#login-form").submit();
    });

});
