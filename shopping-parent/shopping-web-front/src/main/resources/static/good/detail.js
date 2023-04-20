$(function () {
    if (error) {
        layer.alert(error);
        return;
    }
    //添加到购物车
    $("#add-to-cart").click(function () {
        if (username) {
            let amount = $(":input[name=good-amount]").val();
            //添加到购物车操作
            let url = ctx + "/cart/add"
            $.ajax(url, {
                method: "post",
                dataType: "json",
                data: {
                    goodId,
                    amount
                },
                success(resp) {
                    layer.msg("已添加到购物车");
                },
                error(resp){
                    console.log(resp);
                }
            });
        } else {
            location.href = ctx + "/user/login";
        }
    });
});