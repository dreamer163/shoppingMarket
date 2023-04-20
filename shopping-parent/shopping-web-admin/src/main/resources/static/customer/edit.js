$(function () {
    if (error) {
        layer.msg(error);
    }

    if (logoUrl) {
        $('.customer-pic').css("background-image", "url(" + (upload_server + logoUrl) + ")")
    }
});

function submitForm(callback) {
    let formData = new FormData($("#customer-form")[0]);
    let url = ctx + "/api/customers";

    $.ajax(url, {
        method: "put",
        dataType: "json",
        data: formData,
        cache: false,
        processData: false,
        contentType: false,
        success(resp) {
            callback(resp);
        },
        error(resp) {
            callback(resp.responseJSON);
        }
    });
}