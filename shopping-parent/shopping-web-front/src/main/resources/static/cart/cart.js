$(function () {
    let updateTimer;

    //购物车商品数量改变事件
    $(".cart-table :input[name=amount]").change(function () {
        let goodId = $(this).data("good-id");
        let amount = parseInt($(this).val());

        if (amount < 1) {
            amount = 1;
            $(this).val(amount);
        }

        if (amount > 99) {
            amount = 99;
            $(this).val(amount);
        }


        //更改购物车项总价
        let price = $(this).closest("td").prev().children(".product-price").text();
        let total = price * amount;
        $(this).closest("tr").children("td:last")
            .children(".product-price").text(total);


        //计算总价
        calcTotal();
        clearTimeout(updateTimer);

        updateTimer = setTimeout(function () {
            updateCartItemCount(goodId, amount);
        }, 200);
    });


    //购物车商品项删除
    $(".btn-remove").click(function () {
        let me = this;
        let id = $(this).closest("tr").data("cartitem-id");
        let url = ctx + "/cart/delete"
        $.ajax(url, {
            method: "post",
            dataType: "json",
            data: {
                id
            },
            success(resp) {
                $(me).closest("tr").remove();
                calcTotal();
            }
        });
    });

    //全选与取消全选
    $("#checkAll").click(function () {
        let checked = $(this).prop("checked");
        $(".cart-table tbody>tr>td:first-child>:checkbox").prop("checked", checked);

        calcTotal();
    });

    //单个复选框选中事件
    $(".cart-table tbody>tr>td:first-child>:checkbox").click(function () {
        calcTotal();
    });

    //结算点击事件
    $("#btn-checkout").click(function (e) {
        e.preventDefault();

        let orderData = {
            userId,
            cartItemIds: []
        };

        //获取选中元素
        let $allChecked = $(".cart-table tbody>tr>td:first-child>:checked");
        $allChecked.each(function () {
            let $tr = $(this).closest("tr");
            let cartItemId = $tr.data("cartitem-id");//购物车项主键
            orderData.cartItemIds.push(cartItemId);
        });

        let url = ctx + "/api/orders";
        $.ajax(url, {
            method: "post",
            dataType: "json",
            data: orderData,
            traditional: true,
            success(resp) {
                if (resp.success) {
                    location.href = ctx + "/order/checkout?orderId=" + resp.orderId;
                }
            },
            error(resp) {
                //todo
            }
        });
    });
});

function updateCartItemCount(goodId, amount) {
    let url = ctx + "/cart/edit";
    $.ajax(url, {
        method: "post",
        dataType: "json",
        data: {
            goodId,
            amount
        },
        success(resp) {
            //
        }
    });
}

//计算总价
function calcTotal() {
    let $allChecked = $(".cart-table tbody>tr>td:first-child>:checked");
    let total = 0;
    $allChecked.each(function () {
        let singleTotal = $(this).closest("tr").children("td:last").children(".product-price").text();
        total += parseFloat(singleTotal);
    });

    $(".cart-total .total").text(total);
}