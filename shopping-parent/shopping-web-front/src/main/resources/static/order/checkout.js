$(function () {
    //地址下拉改变时，数据回填
    $("#user-address").change(function () {
        let uaId = $(this).val();
        if (uaId === "") {
            $(".checkout-form form")[0].reset();
            return;
        }
        let url = ctx + "/api/users/address";
        $.ajax(url, {
            method: "get",
            dataType: "json",
            data: {
                addressId: uaId
            },
            success(resp) {
                let ua = resp.address;
                $(".checkout-form #name").val(ua.name);
                $(".checkout-form #phone").val(ua.phone);
                $(".checkout-form #email").val(ua.email);
                $(".checkout-form #address").val(ua.address);
                //省份选中
                $(".checkout-form #province").val(ua.provinceId).trigger("change");

                //将cityId设置到市表单上
                $(".checkout-form #city").data("selected-id", ua.cityId);
                $(".checkout-form #area").data("selected-id", ua.areaId);
            }
        });
    });

    //省变化，加载市事件
    $(".checkout-form #province").change(function () {
        let pid = $(this).val();
        let url = ctx + "/api/address/cities"
        $.ajax(url, {
            method: "get",
            dataType: "json",
            data: {
                provinceId: pid
            },
            success(resp) {
                let $city = $(".checkout-form #city");
                $city.empty();
                resp.cities.forEach(function (item) {
                    let option = $("<option>").val(item.id).text(item.name);
                    $city.append(option);
                });

                let cityId = $city.data("selected-id");
                if (cityId) {
                    $city.val(cityId).trigger("change");
                    $city.removeData("selected-id");
                }
            }
        });
    });

    //市改变，加载区
    $(".checkout-form #city").change(function () {
        let pid = $(this).val();
        let url = ctx + "/api/address/areas"
        $.ajax(url, {
            method: "get",
            dataType: "json",
            data: {
                cityId: pid
            },
            success(resp) {
                let $area = $(".checkout-form #area");
                $area.empty();
                resp.areas.forEach(function (item) {
                    let option = $("<option>").val(item.id).text(item.name);
                    $area.append(option);
                });

                let areaId = $area.data("selected-id");
                if (areaId) {
                    $area.val(areaId);
                    $area.removeData("selected-id");
                }

            }
        });
    });

    //支付事件
    $("#pay-btn").click(function () {
        let data = {
            id: orderId,
            payType: $(":input[name=payType]").val(),
            name: $(".checkout-form #name").val(),
            email: $(".checkout-form #email").val(),
            phone: $(".checkout-form #phone").val(),
            state: "1"
        };

        let p = $(".checkout-form #province>option:selected").text();
        let c = $(".checkout-form #city>option:selected").text();
        let a = $(".checkout-form #area>option:selected").text();
        let d = $(".checkout-form #address").val();
        data.address = p + c + a + d;

        let url = ctx + "/api/orders";
        $.ajax(url, {
            method: "patch",
            dataType: "json",
            data,
            success(resp) {
                if (resp.success) {
                    location.href = ctx + "/order/complete";
                }
            },
            error(resp) {
                layer.msg(resp.responseJSON.error);
            }
        });
    });
});