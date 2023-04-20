$(function () {
    //获取所有类别
    // let url = ctx + "/api/categories"
    // $.ajax(url, {
    //     method: "get",
    //     dataType: "json",
    //     data: {},
    //     success(resp) {
    //         let rows = resp.rows;
    //         if (rows.length > 0) {
    //             $("#categoryId").empty();
    //             rows.forEach(function (item) {
    //                 let $option = $("<option>").val(item.id).text(item.name);
    //                 $("#categoryId").append($option);
    //             });
    //         }
    //     }
    // });

    //初始化富文本编辑器
    let ue = UE.getEditor('editor', {
        serverUrl: ctx + "/rich-editor"
    });
});

//使用ajax上传文件
function submitForm(callback) {
    let formData = new FormData($("#good-form")[0]);//表单数据
    let url = ctx + "/api/goods"

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