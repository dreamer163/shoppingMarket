$(function () {

});

function submitForm(callback) {
    let formData = new FormData($("#customer-form")[0]);
    let url = ctx + "/api/customers";

    $.ajax(url, {
        method: "post",
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