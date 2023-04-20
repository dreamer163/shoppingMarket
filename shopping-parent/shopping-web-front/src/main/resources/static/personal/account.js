$(function () {
    $(".btn-confirm").click(function (e) {
        let $tr = $(this).closest("tr");
        e.preventDefault();
        let orderId = $(this).data("order-id");

        let url = ctx + "/api/orders/confirm";
        $.ajax(url, {
            method: "patch",
            dataType: "json",
            data: {
                orderId
            },
            success(resp) {
                if (resp.success) {
                    layer.msg("确认收货成功");
                    //location.reload();
                    $tr.children("td:eq(2)").text("已确认收货");
                    $tr.find(".btn-confirm").remove();
                }
            },
            error(resp) {
                layer.msg("确认收货失败");
            }
        });
    });
});