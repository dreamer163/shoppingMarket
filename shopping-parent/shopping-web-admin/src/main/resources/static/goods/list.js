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
    url: ctx + '/api/goods',
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
    showFullscreen: true,
    // 开关控制分页
    showPaginationSwitch: true,
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
    width: 3,
    // 宽度单位
    widthUnit: 'rem'
}, {
    field: 'id',
    title: '编号',
    // 使用[align]，[halign]和[valign]选项来设置列和它们的标题的对齐方式。
    // h表示横向，v标识垂直
    align: 'center',
    // 是否作为排序列
    sortable: true,
    // 当列名称与实际名称不一致时可用
    sortName: 'sortId',
    switchable: false,
    // 列的宽度
    width: 5,
    // 宽度单位
    widthUnit: 'rem'
}, {
    field: 'picUrl',
    title: '图片',
    width: 60,
    widthUnit: "px",
    formatter(value, row, index) {
        if (value) {
            return "<div class='product-pic' style='background:url(" + (upload_server + value) + ") no-repeat center center/cover'></div>";
        } else {
            return "";
        }
    }
}, {
    field: 'spuNo',
    title: '商品编号',
}, {
    field: 'name',
    title: '商品名称',
}, {
    field: 'categoryName',
    title: '类别名称',
}, {
    field: 'brandName',
    title: '品牌名称',
}, {
    field: 'markPrice',
    title: '标价',
}, {
    field: 'price',
    title: '实际价格',
}, {
    field: 'bestSeller',
    title: '是否热销',
    formatter(value, row, index) {
        return value === true ? "是" : "否";
    }
}, {
    field: 'newProduct',
    title: '是否新品',
    formatter(value, row, index) {
        return value === true ? "是" : "否";
    }
}, {
    field: 'takeOff',
    title: '是否下架',
    formatter(value, row, index) {
        return value === true ? "是" : "否";
    }
}, {
    field: 'description',
    title: '商品介绍',
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
        '<a href="#!" class="btn btn-sm btn-default me-1 edit-btn" title="编辑" data-bs-toggle="tooltip"><i class="mdi mdi-pencil"></i></a>' +
        '<a href="#!" class="btn btn-sm btn-default del-btn" title="删除" data-bs-toggle="tooltip"><i class="mdi mdi-window-close"></i></a>';
    return html;
}

// 操作方法 - 编辑
function editUser(row) {
    editById(row.id);
}

// 操作方法 - 删除
function delUser(row) {
    layer.confirm("是否确认删除选中的商品？",function () {
        deleteByIds([row.id]);
    })
}

//表格的配置
$('#table').bootstrapTable({
    ...table,
    // 自定义的查询参数
    queryParams: function (params) {
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
            id: $("#id").val(),
            name: $("#name").val(),

        };
    },
    columns,
    onLoadSuccess: function (data) {
        $("[data-bs-toggle='tooltip']").tooltip();
    }
});

//添加
$("#btn_add").click(function () {
    layer.open({
        title: "添加商品信息",
        type: 2,
        area: ["700px", "550px"],
        maxmin: true,
        content: ctx + "/goods/add",
        btn: ["确定", "取消"],
        yes: function (index, $obj) {
            //正则表达式验证
            //
            //dom元素有个属性为contentWindow，子窗口window对象就可以调用add.js的submitForm,一个异步无刷新的ajax请求
            $obj.find("iframe").get(0).contentWindow.submitForm(function (resp) {
                if (resp.error) {
                    layer.msg(resp.error);
                } else {
                    layer.close(index);
                    layer.msg("新增商品信息成功！");
                    //表格刷新
                    $("#table").bootstrapTable("refresh");
                }
            });
        },

        btn2: function (index) {
            layer.close(index);
        }
    });
});


//删除
$("#btn_delete").click(function (){
    //获取所有选中的对象，选中的行
    //每一行的数据都是json数据
    let selected = $('#table').bootstrapTable("getSelections");
    //只需要一行的id
    let ids = [];

    //循环迭代selected 以item为参数向ids中push，id
    selected.forEach(function (item) {
        ids.push(item.id);
    });

    if (ids.length > 0) {
        deleteByIds(ids);
    }
});

function deleteByIds(ids){
    let url = ctx + "/api/goods";
    $.ajax(url, {
        method: "delete",
        dataType: "json",
        data: {
            ids
        },
        traditional: true,
        success(resp) {
            layer.msg("删除成功，共删除" + resp.rows + "条记录");
            $("#table").bootstrapTable('refresh');
        },
        error(resp) {
            console.log(resp);
        }
    });
}

//修改
$("#btn_edit").click(function () {
    let selected = $('#table').bootstrapTable("getSelections");
    if (selected.length === 0) {
        layer.msg("请选中您要修改的记录！");
    } else if (selected.length > 1) {
        layer.msg("您一次只能修改一条记录！");
    } else {
        let id = selected[0].id;
        editById(id);
    }
});
function editById(id) {
    layer.open({
        title: "修改商品信息",
        type: 2,
        area: ["700px", "550px"],
        maxmin: true,
        content: ctx + "/goods/edit?id=" + id, //后台传递id
        btn: ["确定", "取消"],
        yes: function (index, $obj) {
            //正则表达式验证
            //
            //dom元素有个属性为contentWindow，子窗口window对象就可以调用add.js的submitForm,一个异步无刷新的ajax请求
            $obj.find("iframe").get(0).contentWindow.submitForm(function (resp) {
                if (resp.error) {
                    layer.msg(resp.error);
                } else {
                    layer.close(index);
                    layer.msg("修改商品信息成功！");
                    //表格刷新
                    $("#table").bootstrapTable("refresh");
                }
            });
        },

        btn2: function (index) {
            layer.close(index);
        }
    });
}

//
$("#btn_search").click(function () {
    $("#table").bootstrapTable("refresh");
});
$("#btn_take_off").click(function () {
    let selected = $("#table").bootstrapTable('getSelections');
    if (selected.length === 0) {
        layer.msg("请选中您要下架的商品");
    } else {
        layer.confirm("是否确认下架选中的商品?", function () {
            let ids = [];
            selected.forEach(function (item) {
                ids.push(item.id);
            });

            if (ids.length > 0) {
                let url = ctx + "/api/goods";
                $.ajax(url, {
                    method: "patch",
                    dataType: "json",
                    data: {
                        ids,
                        act: "takeOff"
                    },
                    traditional: true,
                    success(resp) {
                        layer.msg("操作成功，共下架成功" + resp.rows + "条记录");
                        $("#table").bootstrapTable('refresh');
                    },
                    error(resp) {
                        console.log(resp);
                    }
                });
            }
        });
    }
});
$("#btn_take_on").click(function () {
    let selected = $("#table").bootstrapTable('getSelections');
    if (selected.length === 0) {
        layer.msg("请选中您要上架的商品");
    } else {
        layer.confirm("是否确认上架选中的商品?", function () {
            let ids = [];
            selected.forEach(function (item) {
                ids.push(item.id);
            });

            if (ids.length > 0) {
                let url = ctx + "/api/goods";
                $.ajax(url, {
                    method: "patch",
                    dataType: "json",
                    data: {
                        ids,
                        act: "takeOn"
                    },
                    traditional: true,
                    success(resp) {
                        layer.msg("操作成功，共上架成功" + resp.rows + "条记录");
                        $("#table").bootstrapTable('refresh');
                    },
                    error(resp) {
                        console.log(resp);
                    }
                });
            }
        });
    }
});