/**
 * 分页相关的配置
 **/
const pagination = {
    // 分页方式：[client] 客户端分页，[server] 服务端分页
    sidePagination: "server",
    // 初始化加载第一页，默认第一页
    pageNumber: 1,
    // 每页的记录行数
    pageSize: 10,
    // 可供选择的每页的行数 - (亲测大于1000存在渲染问题)
    pageList: [5, 10, 25, 50, 100],
    // 在上百页的情况下体验较好 - 能够显示首尾页
    paginationLoop: true,
    // 展示首尾页的最小页数
    paginationPagesBySide: 2
};

/**
 * 按钮相关配置
 **/
const button = {
    // 按钮的类
    buttonsClass: 'default',
    // 类名前缀
    buttonsPrefix: 'btn'
}

/**
 * 图标相关配置
 **/
const icon = {
    // 图标前缀
    iconsPrefix: 'mdi',
    // 图标大小
    iconSize: 'mini',
    // 图标的设置
    icons: {
        paginationSwitchDown: 'mdi-door-closed',
        paginationSwitchUp: 'mdi-door-open',
        refresh: 'mdi-refresh',
        toggleOff: 'mdi-toggle-switch-off',
        toggleOn: 'mdi-toggle-switch',
        columns: 'mdi-table-column-remove',
        detailOpen: 'mdi-plus',
        detailClose: 'mdi-minus',
        fullscreen: 'mdi-monitor-screenshot',
        search: 'mdi-table-search',
        clearSearch: 'mdi-trash-can-outline'
    }
};

/**
 * 表格相关的配置
 **/
const table = {
    classes: 'table table-bordered table-hover table-striped lyear-table',
    // 请求地址
    url: ctx + '/api/orders',
    // 唯一ID字段
    uniqueId: 'id',
    // 每行的唯一标识字段
    idField: 'id',
    // 是否启用点击选中行
    clickToSelect: true,
    // 是否显示详细视图和列表视图的切换按钮(clickToSelect同时设置为true时点击会报错)
    // showToggle: true,
    // 请求得到的数据类型
    dataType: 'json',
    // 请求方法
    method: 'get',
    // 工具按钮容器
    toolbar: '#toolbar',
    // 是否分页
    pagination: true,
    // 是否显示所有的列
    showColumns: true,
    // 是否显示刷新按钮
    showRefresh: true,
    // 显示图标
    showButtonIcons: true,
    // 显示文本
    showButtonText: false,
    // 显示全屏
    //showFullscreen: true,
    // 开关控制分页
    //showPaginationSwitch: true,
    // 总数字段
    totalField: 'total',
    // 当字段为 undefined 显示
    undefinedText: '-',
    // 排序方式
    sortOrder: "asc",
    ...icon,
    ...pagination,
    ...button
};

/**
 * 用于演示的列信息
 **/
const columns = [{
    field: 'any',
    checkbox: true,
    // 列的宽度
    width: 5,
    // 宽度单位
    widthUnit: 'rem'
}, {
    field: 'id',
    title: '编号',
    // 使用[align]，[halign]和[valign]选项来设置列和它们的标题的对齐方式。h表示横向，v表示垂直
    //align: 'center',
    // 是否作为排序列
    sortable: true,
    switchable: false,
    // 列的宽度
    width: 5,
    // 宽度单位
    widthUnit: 'rem'
}, {
    field: 'orderId',
    title: '订单编号',
    formatter(value) {
        return "<div style='width:360px'>" + value + "</div>";
    }
}, {
    field: 'userId',
    title: '用户编号'
}, {
    field: 'payType',
    title: '支付方式'
}, {
    field: 'total',
    title: '订单总价'
}, {
    field: 'gmtCreate',
    title: '下单时间',
    formatter(value) {
        return "<div style='width: 240px'>" + value + "</div>";
    }
}, {
    field: 'state',
    title: '订单状态',
    formatter(value) {
        switch (value) {
            case "0":
                return "未支付";
            case "1":
                return "已支付";
            case "2":
                return "已发货";
            case "3":
                return "已确认收货";
            case "4":
                return "已评价";
            case "5":
                return "已结束";
            case "6":
                return "已冻结";
        }
        return "未知";
    }
}, {
    field: 'name',
    title: '收货人'
}, {
    field: 'phone',
    title: '收货人手机号'
}, {
    field: 'email',
    title: '收货人邮箱',
}, {
    field: 'address',
    title: '收货人地址',
}, {
    field: 'description',
    title: '备注',
}, {
    field: 'operate',
    title: '操作',
    formatter: btnGroup,  // 自定义方法
    events: {
        'click .edit-btn': function (event, value, row, index) {
            event.stopPropagation();
            editUser(row);
        },
        'click .del-btn': function (event, value, row, index) {
            event.stopPropagation();
            delUser(row);
        }
    }
}];

// 自定义操作按钮
function btnGroup() {
    let html =
        '<a href="javascript:void(0)" class="btn btn-sm btn-default me-1 edit-btn" title="编辑" data-bs-toggle="tooltip"><i class="mdi mdi-pencil"></i></a>' +
        '<a href="javascript:void(0)" class="btn btn-sm btn-default del-btn" title="删除" data-bs-toggle="tooltip"><i class="mdi mdi-window-close"></i></a>';
    return html;
}

// 操作方法 - 编辑
function editUser() {
    alert('跳转修改信息');
}

// 操作方法 - 删除
function delUser() {
    alert('信息删除成功');
}

//初始化表格
$('#table').bootstrapTable({
    ...table,
    // 自定义的查询参数
    queryParams: function (params) {
        //返回的对象的key用作请求参数的名称
        return {
            // 每页数据量
            pageSize: params.limit,
            // sql语句起始索引
            offset: params.offset,
            pageNo: (params.offset / params.limit) + 1,
            // 排序的列名
            sort: params.sort,
            // 排序方式 'asc' 'desc'
            sortOrder: params.order,
            state
        };
    },
    columns,
    onLoadSuccess: function (data) {
        $("[data-bs-toggle='tooltip']").tooltip();
    }
});

//发货
$("#btn_send").click(function () {
    let selected = $("#table").bootstrapTable('getSelections');
    if (selected.length === 0) {
        layer.msg("请选中您要发货的记录");
    } else {
        layer.confirm("是否确认发货选中的记录?", function (index) {
            let ids = [];
            selected.forEach(function (item) {
                if (item.state === "1") {//已支付
                    ids.push(item.id);
                }
            });

            if (ids.length > 0) {
                let url = ctx + "/api/orders/send";
                $.ajax(url, {
                    method: "patch",
                    dataType: "json",
                    data: {
                        ids
                    },
                    traditional: true,
                    success(resp) {
                        layer.msg("发货成功，共发货" + resp.rows + "条记录");
                        $("#table").bootstrapTable('refresh');
                    },
                    error(resp) {
                        console.log(resp);
                    }
                });
            } else {
                layer.msg("只能对已支付的订单进行发货");
            }
        });
    }
});

//新建
// $("#btn_add").click(function () {
//     layer.open({
//         type: 2,
//         title: '新增品牌',
//         maxmin: true, //开启最大化最小化按钮
//         area: ['600px', '500px'],
//         content: ctx + '/brand/add',
//         btn: ["确定", "关闭"],
//         yes: function (index, obj) {
//             obj.find("iframe")[0].contentWindow.submitForm(function (resp) {
//                 if (resp.error) {
//                     layer.msg(resp.error);
//                 } else {
//                     $("#table").bootstrapTable('refresh');//刷新表格
//                     layer.close(index);
//                 }
//             });
//         },
//         btn2: function (index) {
//             layer.close(index);
//         }
//     });
// });
//
// //删除
// $("#btn_delete").click(function () {
//     let selected = $("#table").bootstrapTable('getSelections');
//     if (selected.length === 0) {
//         layer.msg("请选中您要删除的行");
//     } else {
//         layer.confirm("是否确认删除选中的行(使用中的品牌无法删除)?", function () {
//             let ids = [];
//             selected.forEach(function (item) {
//                 ids.push(item.id);
//             });
//
//             if (ids.length > 0) {
//                 let url = ctx + "/api/brands";
//                 $.ajax(url, {
//                     method: "delete",
//                     dataType: "json",
//                     data: {
//                         ids
//                     },
//                     traditional: true,
//                     success(resp) {
//                         layer.msg("删除成功，共删除" + resp.rows + "条记录(使用中的品牌无法删除)");
//                         $("#table").bootstrapTable('refresh');
//                     },
//                     error(resp) {
//                         console.log(resp);
//                     }
//                 });
//             }
//         });
//     }
// });
//
// //查询
$("#btn_search").click(function () {
    $("#table").bootstrapTable('refresh');
});

// //修改
// $("#btn_edit").click(function () {
//     let selected = $("#table").bootstrapTable('getSelections');
//     if (selected.length === 0) {
//         layer.msg("请选中您要修改的记录");
//     } else if (selected.length > 1) {
//         layer.msg("您一次只能选中一条记录进行修改");
//     } else {
//         let id = selected[0].id;
//         layer.open({
//             type: 2,
//             title: '修改后台用户',
//             maxmin: true, //开启最大化最小化按钮
//             area: ['600px', '500px'],
//             content: ctx + '/brand/edit?id=' + id,
//             btn: ["确定", "关闭"],
//             yes: function (index, obj) {
//                 obj.find("iframe")[0].contentWindow.submitForm(function (resp) {
//                     if (resp.error) {
//                         layer.msg(resp.error);
//                     } else {
//                         $("#table").bootstrapTable('refresh'); //刷新表格
//                         layer.close(index);
//                     }
//                 });
//             },
//             btn2: function (index) {
//                 layer.close(index);
//             }
//         });
//     }
// });
//
// //发货
// $("#btn_send").click(function () {
//     let selected = $("#table").bootstrapTable('getSelections');
//     if (selected.length === 0) {
//         layer.msg("请选中您要发货的记录");
//     } else {
//         layer.confirm("是否确认发货选中的记录?", function (index) {
//             let ids = [];
//             selected.forEach(function (item) {
//                 if (item.state === "1") {//已支付
//                     ids.push(item.id);
//                 }
//             });
//
//             if (ids.length > 0) {
//                 let url = ctx + "/api/orders/send";
//                 $.ajax(url, {
//                     method: "patch",
//                     dataType: "json",
//                     data: {
//                         ids
//                     },
//                     traditional: true,
//                     success(resp) {
//                         layer.msg("发货成功，共发货" + resp.rows + "条记录");
//                         $("#table").bootstrapTable('refresh');
//                     },
//                     error(resp) {
//                         console.log(resp);
//                     }
//                 });
//             } else {
//                 layer.msg("只能对已支付的订单进行发货");
//             }
//         });
//     }
// });



