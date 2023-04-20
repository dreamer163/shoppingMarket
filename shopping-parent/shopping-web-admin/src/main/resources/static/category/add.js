//使用ajax上传文件
function submitForm(callback) {
    let url = ctx + "/api/categories";

    let formData = new FormData($("#category-form")[0]);

    $.ajax(url, {
        method: "post",
        dataType: "json",
        data: formData,
        success(resp) {
            callback(resp);
        },
        error(resp) {
            callback(resp.responseJSON);
        }
    });
}